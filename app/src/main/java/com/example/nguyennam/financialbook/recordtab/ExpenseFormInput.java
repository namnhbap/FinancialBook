package com.example.nguyennam.financialbook.recordtab;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nguyennam.financialbook.MainActivity;
import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.database.ExpenseDAO;
import com.example.nguyennam.financialbook.model.Expense;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    String temp_account = "temp_account.tmp";
    String temp_description = "temp_description";
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
        txtExpenseTime = (TextView) view.findViewById(R.id.txtAccountType);
        txtExpenseTime.setText(getDate());
        txtExpenseEvent = (TextView) view.findViewById(R.id.txtMoneyType);
        RelativeLayout rlSelectCategory = (RelativeLayout) view.findViewById(R.id.rlSelectCategory);
        rlSelectCategory.setOnClickListener(this);
        RelativeLayout rlDescription = (RelativeLayout) view.findViewById(R.id.rlDescription);
        rlDescription.setOnClickListener(this);
        RelativeLayout rlSelectAccount = (RelativeLayout) view.findViewById(R.id.rlAccountName);
        rlSelectAccount.setOnClickListener(this);
        RelativeLayout rlSelectTime = (RelativeLayout) view.findViewById(R.id.rlAccountType);
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

        expense.set_amountMoney(FileHelper.readFile(context, temp_calculator));
        expense.set_expenseCategory(FileHelper.readFile(context, temp_category));
        expense.set_fromAccount(FileHelper.readFile(context, temp_account));
        expense.set_description(FileHelper.readFile(context, temp_description));
        expense.set_expenseEvent(FileHelper.readFile(context, temp_event));

        txtAmount.setText(expense.get_amountMoney());
        txtCategory.setText(expense.get_expenseCategory());
        txtAccountName.setText(expense.get_fromAccount());
        txtDescription.setText(expense.get_description());
        txtExpenseEvent.setText(expense.get_expenseEvent());
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
        expense.set_expenseDate(txtExpenseTime.getText().toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtAmount:
                ((MainActivity)context).replaceFragment(new Calculator(), true);
                break;
            case R.id.rlSelectCategory:
                ((MainActivity)context).replaceFragment(new ExpenseCategory(), true);
                break;
            case R.id.rlDescription:
                ((MainActivity)context).replaceFragment(new Description(), true);
                break;
            case R.id.rlAccountName:
                ((MainActivity)context).replaceFragment(new Accounts(), true);
                break;
            case R.id.rlAccountType:
                new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.rlEvent:
                ((MainActivity)context).replaceFragment(new Event(), true);
                break;
            case R.id.lnSave:
                FileHelper.deleteFile(context, temp_calculator);
                FileHelper.deleteFile(context, temp_description);
                FileHelper.deleteFile(context, temp_event);
                ExpenseDAO expenseDAO = new ExpenseDAO(context);
                expenseDAO.addExpense(expense);
                txtAmount.setText("");
                txtExpenseEvent.setText("");
                txtDescription.setText("");
                txtExpenseTime.setText(getDate());
                break;
        }
    }
}
