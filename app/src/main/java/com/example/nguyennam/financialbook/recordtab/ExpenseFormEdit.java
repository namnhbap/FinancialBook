package com.example.nguyennam.financialbook.recordtab;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyennam.financialbook.MainActivity;
import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.database.AccountRecyclerViewDAO;
import com.example.nguyennam.financialbook.database.ExpenseDAO;
import com.example.nguyennam.financialbook.model.AccountRecyclerView;
import com.example.nguyennam.financialbook.model.Expense;
import com.example.nguyennam.financialbook.utils.CalculatorSupport;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ExpenseFormEdit extends Fragment implements View.OnClickListener,
        DeleteFinancialHistoryDialog.DeleteDialogListener {

    Context context;
    Calendar myCalendar;
    TextView txtAmount;
    TextView txtCategory;
    TextView txtDescription;
    TextView txtAccountName;
    TextView txtExpenseTime;
    TextView txtExpenseEvent;
    Expense expense = new Expense();

    String temp_new_account_id;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expense = new ExpenseDAO(context).getExpenseById(
                Integer.parseInt(FileHelper.readFile(context, Constant.TEMP_EXPENSE_ID)));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record_edit_expense, container, false);
        txtAmount = (TextView) view.findViewById(R.id.txtAmount);
        txtAmount.setOnClickListener(this);
        txtCategory = (TextView) view.findViewById(R.id.txtCategory);
        if (!"".equals(expense.get_categoryChild())) {
            txtCategory.setText(expense.get_categoryChild());
        } else {
            txtCategory.setText(expense.get_category());
        }
        txtDescription = (TextView) view.findViewById(R.id.txtDescription);
        txtDescription.setText(expense.get_description());
        txtAccountName = (TextView) view.findViewById(R.id.txtAccountName);
        AccountRecyclerViewDAO accountDAO = new AccountRecyclerViewDAO(context);
        txtAccountName.setText(accountDAO.getAccountById(expense.get_accountID()).getAccountName());
        txtExpenseTime = (TextView) view.findViewById(R.id.txtExpenseTime);
        txtExpenseTime.setText(getDate());
        txtExpenseEvent = (TextView) view.findViewById(R.id.txtEvent);
        txtExpenseEvent.setText(expense.get_event());
        RelativeLayout rlSelectCategory = (RelativeLayout) view.findViewById(R.id.rlSelectCategory);
        rlSelectCategory.setOnClickListener(this);
        RelativeLayout rlDescription = (RelativeLayout) view.findViewById(R.id.rlDescription);
        rlDescription.setOnClickListener(this);
        RelativeLayout rlSelectAccount = (RelativeLayout) view.findViewById(R.id.rlAccountName);
        rlSelectAccount.setOnClickListener(this);
        RelativeLayout rlSelectTime = (RelativeLayout) view.findViewById(R.id.rlExpenseTime);
        rlSelectTime.setOnClickListener(this);
        RelativeLayout rlExpenseEvent = (RelativeLayout) view.findViewById(R.id.rlEvent);
        rlExpenseEvent.setOnClickListener(this);
        LinearLayout lnSaveExpense = (LinearLayout) view.findViewById(R.id.lnSave);
        lnSaveExpense.setOnClickListener(this);
        LinearLayout lnDeleteExpense = (LinearLayout) view.findViewById(R.id.lnDelete);
        lnDeleteExpense.setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!"".equals(FileHelper.readFile(context, Constant.TEMP_CALCULATOR))) {
            txtAmount.setText(FileHelper.readFile(context, Constant.TEMP_CALCULATOR));
        } else {
            txtAmount.setText(expense.get_amountMoney());
            FileHelper.writeFile(context, Constant.TEMP_CALCULATOR_EDIT, expense.get_amountMoney());
        }
        if (!"".equals(FileHelper.readFile(context, Constant.TEMP_CATEGORY_CHILD))) {
            txtCategory.setText(FileHelper.readFile(context, Constant.TEMP_CATEGORY_CHILD));
        } else if (!"".equals(FileHelper.readFile(context, Constant.TEMP_CATEGORY))) {
            txtCategory.setText(FileHelper.readFile(context, Constant.TEMP_CATEGORY));
        }
        if (!"".equals(FileHelper.readFile(context, Constant.TEMP_ACCOUNT_ID_EDIT))) {
            AccountRecyclerViewDAO accountDAO = new AccountRecyclerViewDAO(context);
            temp_new_account_id = FileHelper.readFile(context, Constant.TEMP_ACCOUNT_ID_EDIT);
            txtAccountName.setText(accountDAO.getAccountById(Integer.parseInt(temp_new_account_id)).getAccountName());
        } else {
            temp_new_account_id = String.valueOf(expense.get_accountID());
        }
        if (!"".equals(FileHelper.readFile(context, Constant.TEMP_DESCRIPTION))) {
            txtDescription.setText(FileHelper.readFile(context, Constant.TEMP_DESCRIPTION));
        }
        if (!"".equals(FileHelper.readFile(context, Constant.TEMP_EVENT))) {
            txtExpenseEvent.setText(FileHelper.readFile(context, Constant.TEMP_EVENT));
        }
    }

    String getDate() {
        myCalendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        if (!"".equals(FileHelper.readFile(context, Constant.TEMP_DATE))) {
            try {
                myCalendar.setTime(df.parse(FileHelper.readFile(context, Constant.TEMP_DATE)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return df.format(myCalendar.getTime());
        } else {
            try {
                myCalendar.setTime(df.parse(expense.get_date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return df.format(myCalendar.getTime());
        }
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        FileHelper.writeFile(context, Constant.TEMP_DATE, sdf.format(myCalendar.getTime()));
        txtExpenseTime.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtAmount:
                ((MainActivity) context).replaceFragment(new Calculator(), true);
                break;
            case R.id.rlSelectCategory:
                ((MainActivity) context).replaceFragment(new ExpenseCategory(), true);
                break;
            case R.id.rlDescription:
                ((MainActivity) context).replaceFragment(new Description(), true);
                break;
            case R.id.rlAccountName:
                ((MainActivity) context).replaceFragment(new Accounts(), true);
                break;
            case R.id.rlExpenseTime:
                new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.rlEvent:
                ((MainActivity) context).replaceFragment(new Event(), true);
                break;
            case R.id.lnSave:
                if ("".equals(txtAmount.getText().toString())) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noticeNoMoney),
                            Toast.LENGTH_LONG).show();
                } else if ("".equals(txtCategory.getText().toString())) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noticeNoCategory),
                            Toast.LENGTH_LONG).show();
                } else if ("".equals(txtAccountName.getText().toString())) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noticeNoAccount),
                            Toast.LENGTH_LONG).show();
                } else {
                    saveData();
                }
                break;
            case R.id.lnDelete:
                DeleteFinancialHistoryDialog deleteFinancial = new DeleteFinancialHistoryDialog();
                deleteFinancial.setTargetFragment(ExpenseFormEdit.this, 271);
                deleteFinancial.show(getActivity().getSupportFragmentManager(), "delete_financial");
                break;
        }
    }

    public void saveData() {
        // avoid bug can't set date current when click save
        FileHelper.deleteFile(context, Constant.TEMP_DATE);
        //update amountmoney of account
        updateAmountMoneyAccount();
        //set expense
        setExpense();
        //add expense into database
        ExpenseDAO expenseDAO = new ExpenseDAO(context);
        expenseDAO.updateExpense(expense);
        Toast.makeText(context, R.string.editSuccessfully, Toast.LENGTH_SHORT).show();
        Log.d(Constant.TAG, "onClick: " + expenseDAO.getAllExpense());
        //clear temp file
        clearTempFile();
        //exit
        getActivity().getSupportFragmentManager().popBackStack();
    }

    private void updateAmountMoneyAccount() {
        AccountRecyclerViewDAO accountDAO = new AccountRecyclerViewDAO(context);
        AccountRecyclerView accountOld = accountDAO.getAccountById(expense.get_accountID());
        AccountRecyclerView accountNew = accountDAO.getAccountById(Integer.parseInt(temp_new_account_id));
        //remain Money = present money - expense money;
        if (temp_new_account_id.equals(String.valueOf(expense.get_accountID()))) {
            updateMoneyOldAccount(accountDAO, accountOld);
        } else {
            updateMoneyNewAccount(accountDAO, accountOld, accountNew);
        }
    }

    private void updateMoneyNewAccount(AccountRecyclerViewDAO accountDAO, AccountRecyclerView accountOld, AccountRecyclerView accountNew) {
        double remainMoneyNumber = Double.parseDouble(CalculatorSupport.formatExpression(accountNew.getAmountMoney()))
                - Double.parseDouble(CalculatorSupport.formatExpression(txtAmount.getText().toString()));
        double remainOldMoney = Double.parseDouble(CalculatorSupport.formatExpression(accountOld.getAmountMoney()))
                + Double.parseDouble(CalculatorSupport.formatExpression(expense.get_amountMoney()));
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        accountNew.setAmountMoney(nf.format(remainMoneyNumber));
        accountOld.setAmountMoney(nf.format(remainOldMoney));
        accountDAO.updateAccount(accountNew);
        accountDAO.updateAccount(accountOld);
    }

    private void updateMoneyOldAccount(AccountRecyclerViewDAO accountDAO, AccountRecyclerView accountOld) {
        double remainMoneyNumber = Double.parseDouble(CalculatorSupport.formatExpression(accountOld.getAmountMoney()))
                - Double.parseDouble(CalculatorSupport.formatExpression(txtAmount.getText().toString()))
                + Double.parseDouble(CalculatorSupport.formatExpression(expense.get_amountMoney()));
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        accountOld.setAmountMoney(nf.format(remainMoneyNumber));
        accountDAO.updateAccount(accountOld);
    }

    private void setExpense() {
        expense.set_amountMoney(txtAmount.getText().toString());
        expense.set_description(txtDescription.getText().toString());
        expense.set_accountID(Integer.parseInt(temp_new_account_id));
        if (!"".equals(FileHelper.readFile(context, Constant.TEMP_CATEGORY))) {
            expense.set_category(FileHelper.readFile(context, Constant.TEMP_CATEGORY));
            expense.set_categoryChild(FileHelper.readFile(context, Constant.TEMP_CATEGORY_CHILD));
        }
        if (!"".equals(FileHelper.readFile(context, Constant.TEMP_CATEGORY_CHILD))) {
            expense.set_categoryChild(FileHelper.readFile(context, Constant.TEMP_CATEGORY_CHILD));
        }
        expense.set_event(txtExpenseEvent.getText().toString());
        expense.set_date(txtExpenseTime.getText().toString());
    }

    private void clearTempFile() {
        FileHelper.deleteFile(context, Constant.TEMP_CALCULATOR);
        FileHelper.deleteFile(context, Constant.TEMP_CATEGORY);
        FileHelper.deleteFile(context, Constant.TEMP_CATEGORY_CHILD);
        FileHelper.deleteFile(context, Constant.TEMP_DESCRIPTION);
        FileHelper.deleteFile(context, Constant.TEMP_EVENT);
        FileHelper.deleteFile(context, Constant.TEMP_ACCOUNT_ID_EDIT);
        FileHelper.deleteFile(context, Constant.TEMP_CALCULATOR_EDIT);
        FileHelper.deleteFile(context, Constant.TEMP_ISEXPENSE);
    }

    @Override
    public void onFinishDeleteDialog(boolean isDelete) {
        if (isDelete) {
            recoverMoney();
            ExpenseDAO expenseDAO = new ExpenseDAO(context);
            expenseDAO.deleteExpense(expense);
            clearTempFile();
            Toast.makeText(context, R.string.deleteSuccessfully, Toast.LENGTH_SHORT).show();
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    private void recoverMoney() {
        AccountRecyclerViewDAO accountDAO = new AccountRecyclerViewDAO(context);
        AccountRecyclerView account = accountDAO.getAccountById(expense.get_accountID());
        double remainMoneyNumber = Double.parseDouble(CalculatorSupport.formatExpression(account.getAmountMoney()))
                + Double.parseDouble(CalculatorSupport.formatExpression(expense.get_amountMoney()));
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        account.setAmountMoney(nf.format(remainMoneyNumber));
        accountDAO.updateAccount(account);
    }
}
