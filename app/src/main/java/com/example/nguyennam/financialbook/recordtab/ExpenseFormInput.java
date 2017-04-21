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

    String temp_calculator = "temp_calculator.tmp";
    String temp_category = "temp_category.tmp";
    String temp_account_id = "temp_account_id.tmp";
    String temp_description = "temp_description.tmp";
    String temp_event = "temp_event.tmp";

    //    final Calculator record_calculator = new Calculator();
//    final Description record_description = new Description();
//    final ExpenseEvent expenseEvent = new ExpenseEvent();
//    final ListAccount listAccount = new ListAccount();
//    ExpenseBEAN expenseBEAN = new ExpenseBEAN();
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

        if (!"".equals(FileHelper.readFile(context, temp_calculator))) {
            txtAmount.setText(FileHelper.readFile(context, temp_calculator));
        }
        if (!"".equals(FileHelper.readFile(context, temp_category))) {
            txtCategory.setText(FileHelper.readFile(context, temp_category));
        }
        if (!"".equals(FileHelper.readFile(context, temp_account_id))) {
            AccountRecyclerViewDAO accountDAO = new AccountRecyclerViewDAO(context);
            expense.set_accountID(Integer.parseInt(FileHelper.readFile(context, temp_account_id)));
            txtAccountName.setText(accountDAO.getAccountById(expense.get_accountID()).getAccountName());
        }
        if (!"".equals(FileHelper.readFile(context, temp_description))) {
            txtDescription.setText(FileHelper.readFile(context, temp_description));
        }
        if (!"".equals(FileHelper.readFile(context, temp_event))) {
            txtExpenseEvent.setText(FileHelper.readFile(context, temp_event));
        }
//        expense.set_amountMoney(FileHelper.readFile(context, temp_calculator));
//        expense.set_category(FileHelper.readFile(context, temp_category));
//        expense.set_accountID(Integer.parseInt(FileHelper.readFile(context, temp_account_id)));
//        expense.set_description(FileHelper.readFile(context, temp_description));
//        expense.set_event(FileHelper.readFile(context, temp_event));
//
//        txtAmount.setText(expense.get_amountMoney());
//        txtCategory.setText(expense.get_category());
//        txtAccountName.setText(accountDAO.getAccountById(expense.get_accountID()).getAccountName());
//        txtDescription.setText(expense.get_description());
//        txtExpenseEvent.setText(expense.get_event());
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
                    Toast.makeText(getActivity(), getResources().getString(R.string.noticeNoCategory),
                            Toast.LENGTH_LONG).show();
                } else if ("".equals(txtAccountName.getText().toString())) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noticeNoAccount),
                            Toast.LENGTH_LONG).show();
                } else {
                    saveData();
                }
                break;
        }
    }

    public void saveData() {
        //clear temp file
        clearTempFile();
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
        expense.set_category(txtCategory.getText().toString());
        expense.set_event(txtExpenseEvent.getText().toString());
    }

    private void clearTempFile() {
        FileHelper.deleteFile(context, temp_calculator);
        FileHelper.deleteFile(context, temp_category);
        FileHelper.deleteFile(context, temp_description);
        FileHelper.deleteFile(context, temp_event);
    }

}
