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

/**
 * Created by NguyenNam on 3/7/2017.
 */

public class TransferFormInput extends Fragment implements View.OnClickListener {

    Context context;
    Calendar myCalendar;
    TextView txtAmount;
    TextView txtFromAccount;
    TextView txtToAccount;
    TextView txtDescription;
    TextView txtTransferTime;

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
        View view = inflater.inflate(R.layout.record_input_transfer, container, false);
        txtAmount = (TextView) view.findViewById(R.id.txtAmount);
        txtAmount.setOnClickListener(this);
        txtFromAccount = (TextView) view.findViewById(R.id.txtFromAccount);
        txtToAccount = (TextView) view.findViewById(R.id.txtToAccount);
        txtDescription = (TextView) view.findViewById(R.id.txtDescription);
        txtTransferTime = (TextView) view.findViewById(R.id.txtTime);
        txtTransferTime.setText(getDate());
        RelativeLayout rlFromAccount = (RelativeLayout) view.findViewById(R.id.rlFromAccount);
        rlFromAccount.setOnClickListener(this);
        RelativeLayout rlToAccount = (RelativeLayout) view.findViewById(R.id.rlToAccount);
        rlToAccount.setOnClickListener(this);
        RelativeLayout rlDescription = (RelativeLayout) view.findViewById(R.id.rlDescription);
        rlDescription.setOnClickListener(this);
        RelativeLayout rlSelectTime = (RelativeLayout) view.findViewById(R.id.rlSelectTime);
        rlSelectTime.setOnClickListener(this);
        LinearLayout lnAddTransfer = (LinearLayout) view.findViewById(R.id.lnSave);
        lnAddTransfer.setOnClickListener(this);
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
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            txtAmount.setText(bundle.getString(Constant.KEY_MONEY));
//        }
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
        txtTransferTime.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtAmount:

                break;
            case R.id.rlFromAccount:
                break;
            case R.id.rlToAccount:
                break;
            case R.id.rlDescription:
                break;
            case R.id.rlSelectTime:
                new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.lnSave:
                break;
        }
    }
}
