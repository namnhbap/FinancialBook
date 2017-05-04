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
import com.example.nguyennam.financialbook.database.IncomeDAO;
import com.example.nguyennam.financialbook.model.AccountRecyclerView;
import com.example.nguyennam.financialbook.model.Income;
import com.example.nguyennam.financialbook.utils.CalculatorSupport;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class IncomeFormEdit extends Fragment implements View.OnClickListener,
        DeleteFinancialHistoryDialog.DeleteDialogListener {

    Context context;
    Calendar myCalendar;
    TextView txtAmount;
    TextView txtIncomeCategory;
    TextView txtDescription;
    TextView txtAccountName;
    TextView txtIncomeTime;
    TextView txtEvent;
    Income income = new Income();
    String temp_new_account_id;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        income = new IncomeDAO(context).getIncomeById(Integer.parseInt(FileHelper.readFile(context, Constant.TEMP_INCOME_ID)));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record_edit_income, container, false);
        txtAmount = (TextView) view.findViewById(R.id.txtAmount);
        txtAmount.setOnClickListener(this);
        txtIncomeCategory = (TextView) view.findViewById(R.id.txtIncomeCategory);
        txtIncomeCategory.setText(income.get_category());
        txtDescription = (TextView) view.findViewById(R.id.txtDescription);
        txtDescription.setText(income.get_description());
        txtAccountName = (TextView) view.findViewById(R.id.txtAccountName);
        AccountRecyclerViewDAO accountDAO = new AccountRecyclerViewDAO(context);
        txtAccountName.setText(accountDAO.getAccountById(income.get_accountID()).getAccountName());
        txtIncomeTime = (TextView) view.findViewById(R.id.txtIncomeTime);
        txtIncomeTime.setText(getDate());
        txtEvent = (TextView) view.findViewById(R.id.txtEvent);
        txtEvent.setText(income.get_event());
        RelativeLayout rlSelectCategory = (RelativeLayout) view.findViewById(R.id.rlSelectCategory);
        rlSelectCategory.setOnClickListener(this);
        RelativeLayout rlDescription = (RelativeLayout) view.findViewById(R.id.rlDescription);
        rlDescription.setOnClickListener(this);
        RelativeLayout rlSelectAccount = (RelativeLayout) view.findViewById(R.id.rlAccountName);
        rlSelectAccount.setOnClickListener(this);
        RelativeLayout rlSelectTime = (RelativeLayout) view.findViewById(R.id.rlIncomeTime);
        rlSelectTime.setOnClickListener(this);
        RelativeLayout rlIncomeEvent = (RelativeLayout) view.findViewById(R.id.rlEvent);
        rlIncomeEvent.setOnClickListener(this);
        LinearLayout lnSaveIncome = (LinearLayout) view.findViewById(R.id.lnSave);
        lnSaveIncome.setOnClickListener(this);
        LinearLayout lnDeleteIncome = (LinearLayout) view.findViewById(R.id.lnDelete);
        lnDeleteIncome.setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!"".equals(FileHelper.readFile(context, Constant.TEMP_CALCULATOR))) {
            txtAmount.setText(FileHelper.readFile(context, Constant.TEMP_CALCULATOR));
        } else {
            txtAmount.setText(income.get_amountMoney());
            FileHelper.writeFile(context, Constant.TEMP_CALCULATOR_EDIT, income.get_amountMoney());
        }
        if (!"".equals(FileHelper.readFile(context, Constant.TEMP_CATEGORY))) {
            txtIncomeCategory.setText(FileHelper.readFile(context, Constant.TEMP_CATEGORY));
        }
        if (!"".equals(FileHelper.readFile(context, Constant.TEMP_ACCOUNT_ID_EDIT))) {
            AccountRecyclerViewDAO accountDAO = new AccountRecyclerViewDAO(context);
            temp_new_account_id = FileHelper.readFile(context, Constant.TEMP_ACCOUNT_ID_EDIT);
            txtAccountName.setText(accountDAO.getAccountById(Integer.parseInt(temp_new_account_id)).getAccountName());
        } else {
            temp_new_account_id = String.valueOf(income.get_accountID());
        }
        if (!"".equals(FileHelper.readFile(context, Constant.TEMP_DESCRIPTION))) {
            txtDescription.setText(FileHelper.readFile(context, Constant.TEMP_DESCRIPTION));
        }
        if (!"".equals(FileHelper.readFile(context, Constant.TEMP_EVENT))) {
            txtEvent.setText(FileHelper.readFile(context, Constant.TEMP_EVENT));
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
                myCalendar.setTime(df.parse(income.get_date()));
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
        txtIncomeTime.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtAmount:
                ((MainActivity)context).replaceFragment(new Calculator(), true);
                break;
            case R.id.rlSelectCategory:
                ((MainActivity)context).replaceFragment(new IncomeCategory(), true);
                break;
            case R.id.rlDescription:
                ((MainActivity)context).replaceFragment(new Description(), true);
                break;
            case R.id.rlAccountName:
                ((MainActivity)context).replaceFragment(new Accounts(), true);
                break;
            case R.id.rlIncomeTime:
                new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.rlEvent:
                ((MainActivity)context).replaceFragment(new Event(), true);
                break;
            case R.id.lnSave:
                if ("".equals(txtAmount.getText().toString())) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noticeNoMoney),
                            Toast.LENGTH_LONG).show();
                } else if ("".equals(txtIncomeCategory.getText().toString())){
                    Toast.makeText(getActivity(), getResources().getString(R.string.noticeNoCategory),
                            Toast.LENGTH_LONG).show();
                } else if ("".equals(txtAccountName.getText().toString())){
                    Toast.makeText(getActivity(), getResources().getString(R.string.noticeNoAccount),
                            Toast.LENGTH_LONG).show();
                } else {
                    saveData();
                }
                break;
            case R.id.lnDelete:
                DeleteFinancialHistoryDialog deleteFinancial = new DeleteFinancialHistoryDialog();
                deleteFinancial.setTargetFragment(IncomeFormEdit.this, 271);
                deleteFinancial.show(getActivity().getSupportFragmentManager(), "delete_financial");
                break;
        }
    }

    public void saveData() {
        // avoid bug can't set date current when click save
        FileHelper.deleteFile(context, Constant.TEMP_DATE);
        //clear temp file
        clearTempFile();
        //update amountmoney of account
        updateAmountMoneyAccount();
        //set expense
        setExpense();
        //add expense into database
        IncomeDAO incomeDAO = new IncomeDAO(context);
        incomeDAO.updateIncome(income);
        //exit
        getActivity().getSupportFragmentManager().popBackStack();
    }

    private void updateAmountMoneyAccount() {
        AccountRecyclerViewDAO accountDAO = new AccountRecyclerViewDAO(context);
        AccountRecyclerView accountOld = accountDAO.getAccountById(income.get_accountID());
        AccountRecyclerView accountNew = accountDAO.getAccountById(Integer.parseInt(temp_new_account_id));
        if (temp_new_account_id.equals(String.valueOf(income.get_accountID()))) {
            updateMoneyOldAccount(accountDAO, accountOld);
        } else {
            updateMoneyNewAccount(accountDAO, accountOld, accountNew);
        }
    }

    private void updateMoneyNewAccount(AccountRecyclerViewDAO accountDAO, AccountRecyclerView accountOld, AccountRecyclerView accountNew) {
        double remainMoneyNumber = Double.parseDouble(CalculatorSupport.formatExpression(accountNew.getAmountMoney()))
                + Double.parseDouble(CalculatorSupport.formatExpression(txtAmount.getText().toString()));
        double remainOldMoney = Double.parseDouble(CalculatorSupport.formatExpression(accountOld.getAmountMoney()))
                - Double.parseDouble(CalculatorSupport.formatExpression(income.get_amountMoney()));
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        accountNew.setAmountMoney(nf.format(remainMoneyNumber));
        accountOld.setAmountMoney(nf.format(remainOldMoney));
        accountDAO.updateAccount(accountNew);
        accountDAO.updateAccount(accountOld);
    }

    private void updateMoneyOldAccount(AccountRecyclerViewDAO accountDAO, AccountRecyclerView accountOld) {
        double remainMoneyNumber = Double.parseDouble(CalculatorSupport.formatExpression(accountOld.getAmountMoney()))
                + Double.parseDouble(CalculatorSupport.formatExpression(txtAmount.getText().toString()))
                - Double.parseDouble(CalculatorSupport.formatExpression(income.get_amountMoney()));
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        accountOld.setAmountMoney(nf.format(remainMoneyNumber));
        accountDAO.updateAccount(accountOld);
    }

    private void setExpense() {
        income.set_amountMoney(txtAmount.getText().toString());
        income.set_description(txtDescription.getText().toString());
        income.set_accountID(Integer.parseInt(temp_new_account_id));
        income.set_category(txtIncomeCategory.getText().toString());
        income.set_event(txtEvent.getText().toString());
        income.set_date(txtIncomeTime.getText().toString());
    }

    private void clearTempFile() {
        FileHelper.deleteFile(context, Constant.TEMP_CALCULATOR);
        FileHelper.deleteFile(context, Constant.TEMP_CATEGORY);
        FileHelper.deleteFile(context, Constant.TEMP_DESCRIPTION);
        FileHelper.deleteFile(context, Constant.TEMP_EVENT);
        FileHelper.deleteFile(context, Constant.TEMP_ACCOUNT_ID_EDIT);
        FileHelper.deleteFile(context, Constant.TEMP_ISEXPENSE);
    }

    @Override
    public void onFinishDeleteDialog(boolean isDelete) {
        if (isDelete) {
            recoverMoney();
            IncomeDAO incomeDAO = new IncomeDAO(context);
            incomeDAO.deleteIncome(income);
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    private void recoverMoney() {
        AccountRecyclerViewDAO accountDAO = new AccountRecyclerViewDAO(context);
        AccountRecyclerView account = accountDAO.getAccountById(income.get_accountID());
        double remainMoneyNumber = Double.parseDouble(CalculatorSupport.formatExpression(account.getAmountMoney()))
                - Double.parseDouble(CalculatorSupport.formatExpression(income.get_amountMoney()));
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        account.setAmountMoney(nf.format(remainMoneyNumber));
        accountDAO.updateAccount(account);
    }
}
