package com.example.nguyennam.financialbook.recordtab;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nguyennam.financialbook.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class IncomeFormInput extends Fragment implements View.OnClickListener {

    Context context;
    Calendar myCalendar;
    TextView txtAmount;
    TextView txtIncomeType;
    TextView txtIncomeDescription;
    TextView txtAccountName;
    TextView txtIncomeTime;
    TextView txtIncomeEvent;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record_input_income, container, false);
        txtAmount = (TextView) view.findViewById(R.id.txtAmount);
        txtAmount.setOnClickListener(this);
        txtIncomeType = (TextView) view.findViewById(R.id.txtCategory);
        txtIncomeDescription = (TextView) view.findViewById(R.id.txtDescription);
        txtAccountName = (TextView) view.findViewById(R.id.txtAccountName);
        txtIncomeTime = (TextView) view.findViewById(R.id.txtTime);
        txtIncomeTime.setText(getDate());
        txtIncomeEvent = (TextView) view.findViewById(R.id.txtEvent);
        RelativeLayout rlSelectCategory = (RelativeLayout) view.findViewById(R.id.rlSelectCategory);
        rlSelectCategory.setOnClickListener(this);
        RelativeLayout rlDescription = (RelativeLayout) view.findViewById(R.id.rlDescription);
        rlDescription.setOnClickListener(this);
        RelativeLayout rlSelectAccount = (RelativeLayout) view.findViewById(R.id.rlSelectAccount);
        rlSelectAccount.setOnClickListener(this);
        RelativeLayout rlSelectTime = (RelativeLayout) view.findViewById(R.id.rlSelectTime);
        rlSelectTime.setOnClickListener(this);
        RelativeLayout rlIncomeEvent = (RelativeLayout) view.findViewById(R.id.rlEvent);
        rlIncomeEvent.setOnClickListener(this);
        LinearLayout lnAddIncome = (LinearLayout) view.findViewById(R.id.lnSave);
        lnAddIncome.setOnClickListener(this);
        return view;
    }

    String getDate(){
        myCalendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(myCalendar.getTime());
        return formattedDate;
    }

    @Override
    public void onStart() {
        super.onStart();
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
        txtIncomeTime.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtAmount:
                break;
            case R.id.rlSelectCategory:
                break;
            case R.id.rlDescription:
                break;
            case R.id.rlSelectAccount:
                break;
            case R.id.rlSelectTime:
                new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.rlEvent:
                break;
            case R.id.lnSave:
                break;
        }
    }
}
