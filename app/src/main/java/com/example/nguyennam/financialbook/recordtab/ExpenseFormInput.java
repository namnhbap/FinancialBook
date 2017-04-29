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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ExpenseFormInput extends Fragment implements View.OnClickListener {

    Context context;
    Calendar myCalendar;
    TextView txtAmount;
    TextView txtCategory;
    TextView txtDescription;
    TextView txtAccountName;
    TextView txtExpenseTime;
    TextView txtExpenseEvent;
    Expense expense = new Expense();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record_input_expense, container, false);
        txtAmount = (TextView) view.findViewById(R.id.txtAmount);
        txtAmount.setOnClickListener(this);
        txtCategory = (TextView) view.findViewById(R.id.txtCategory);
        txtDescription = (TextView) view.findViewById(R.id.txtDescription);
        txtAccountName = (TextView) view.findViewById(R.id.txtAccountName);
        txtExpenseTime = (TextView) view.findViewById(R.id.txtExpenseTime);
        txtExpenseTime.setText(getDate());
        expense.set_date(txtExpenseTime.getText().toString());
        txtExpenseEvent = (TextView) view.findViewById(R.id.txtEvent);
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
        LinearLayout lnAddExpense = (LinearLayout) view.findViewById(R.id.lnSave);
        lnAddExpense.setOnClickListener(this);
        return view;
    }

    String getDate() {
        myCalendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(myCalendar.getTime());
        return formattedDate;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!"".equals(FileHelper.readFile(context, Constant.TEMP_CALCULATOR))) {
            txtAmount.setText(FileHelper.readFile(context, Constant.TEMP_CALCULATOR));
        }
        if (!"".equals(FileHelper.readFile(context, Constant.TEMP_CATEGORY_CHILD))) {
            txtCategory.setText(FileHelper.readFile(context, Constant.TEMP_CATEGORY_CHILD));
        } else if (!"".equals(FileHelper.readFile(context, Constant.TEMP_CATEGORY))) {
            txtCategory.setText(FileHelper.readFile(context, Constant.TEMP_CATEGORY));
        }
        if (!"".equals(FileHelper.readFile(context, Constant.TEMP_ACCOUNT_ID))) {
            AccountRecyclerViewDAO accountDAO = new AccountRecyclerViewDAO(context);
            expense.set_accountID(Integer.parseInt(FileHelper.readFile(context, Constant.TEMP_ACCOUNT_ID)));
            txtAccountName.setText(accountDAO.getAccountById(expense.get_accountID()).getAccountName());
        }
        if (!"".equals(FileHelper.readFile(context, Constant.TEMP_DESCRIPTION))) {
            txtDescription.setText(FileHelper.readFile(context, Constant.TEMP_DESCRIPTION));
        }
        if (!"".equals(FileHelper.readFile(context, Constant.TEMP_EVENT))) {
            txtExpenseEvent.setText(FileHelper.readFile(context, Constant.TEMP_EVENT));
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
        txtExpenseTime.setText(sdf.format(myCalendar.getTime()));
        expense.set_date(txtExpenseTime.getText().toString());
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
                    Toast.makeText(getActivity(), getResources().getString(R.string.noticeNoCategoryExpense),
                            Toast.LENGTH_LONG).show();
                } else if ("".equals(txtAccountName.getText().toString())) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noticeNoAccountExpense),
                            Toast.LENGTH_LONG).show();
                } else {
                    saveData();
                }
                break;
        }
    }

    public void saveData() {
        //set expense
        setExpense();
        //add expense into database
        ExpenseDAO expenseDAO = new ExpenseDAO(context);
        expenseDAO.addExpense(expense);
        Log.d(Constant.TAG, "onClick: " + expenseDAO.getAllExpense());
        //update amountmoney of account
        updateAmountMoneyAccount();
        //clear text
        clearTextView();
        //clear temp file
        clearTempFile();
    }

    private void clearTextView() {
        txtAmount.setText("");
        txtExpenseEvent.setText("");
        txtDescription.setText("");
        txtCategory.setText("");
        txtExpenseTime.setText(getDate());
    }

    private void updateAmountMoneyAccount() {
        AccountRecyclerView account;
        AccountRecyclerViewDAO accountDAO = new AccountRecyclerViewDAO(context);
        account = accountDAO.getAccountById(expense.get_accountID());
        //remain Money = present money - expense money;
        double remainMoneyNumber = Double.parseDouble(CalculatorSupport.formatExpression(account.getAmountMoney()))
                - Double.parseDouble(CalculatorSupport.formatExpression(expense.get_amountMoney()));
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        account.setAmountMoney(nf.format(remainMoneyNumber));
        accountDAO.updateAccount(account);
    }

    private void setExpense() {
        expense.set_amountMoney(txtAmount.getText().toString());
        expense.set_description(txtDescription.getText().toString());
        expense.set_category(FileHelper.readFile(context, Constant.TEMP_CATEGORY));
        expense.set_categoryChild(FileHelper.readFile(context, Constant.TEMP_CATEGORY_CHILD));
        expense.set_event(txtExpenseEvent.getText().toString());
    }

    private void clearTempFile() {
        FileHelper.deleteFile(context, Constant.TEMP_CALCULATOR);
        FileHelper.deleteFile(context, Constant.TEMP_CATEGORY);
        FileHelper.deleteFile(context, Constant.TEMP_CATEGORY_CHILD);
        FileHelper.deleteFile(context, Constant.TEMP_DESCRIPTION);
        FileHelper.deleteFile(context, Constant.TEMP_EVENT);
    }

}
