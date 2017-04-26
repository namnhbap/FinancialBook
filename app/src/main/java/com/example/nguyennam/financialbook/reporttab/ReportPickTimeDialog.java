package com.example.nguyennam.financialbook.reporttab;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyennam.financialbook.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReportPickTimeDialog extends DialogFragment implements View.OnClickListener {

    Calendar myCalendar;
    Calendar myCalendarToDate;
    TextView txtFromDate;
    TextView txtToDate;

    public ReportPickTimeDialog () {

    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.report_pick_time_dialog, container);
//        LinearLayout btnOk = (LinearLayout) view.findViewById(R.id.btnOk);
//        btnOk.setOnClickListener(this);
//        RelativeLayout rlFromDate = (RelativeLayout) view.findViewById(R.id.rlFromDate);
//        rlFromDate.setOnClickListener(this);
//        RelativeLayout rlToDate = (RelativeLayout) view.findViewById(R.id.rlToDate);
//        rlToDate.setOnClickListener(this);
//        return view;
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        // Get field from view
//        txtFromDate = (TextView) view.findViewById(R.id.txtFromDate);
//        txtToDate = (TextView) view.findViewById(R.id.txtToDate);
//        getDialog().setTitle(R.string.accountName);
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.report_pick_time_dialog, null);

        LinearLayout btnOk = (LinearLayout) view.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
        RelativeLayout rlFromDate = (RelativeLayout) view.findViewById(R.id.rlFromDate);
        rlFromDate.setOnClickListener(this);
        RelativeLayout rlToDate = (RelativeLayout) view.findViewById(R.id.rlToDate);
        rlToDate.setOnClickListener(this);
        txtFromDate = (TextView) view.findViewById(R.id.txtFromDate);
        txtFromDate.setText(getFirstDate());
        txtToDate = (TextView) view.findViewById(R.id.txtToDate);
        txtToDate.setText(getDate());

        builder.setView(view);
        return builder.create();
    }

    String getDate(){
        myCalendarToDate = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(myCalendarToDate.getTime());
        return formattedDate;
    }

    String getFirstDate() {
        myCalendar = Calendar.getInstance();
        myCalendar.set(Calendar.DATE, 1);
        myCalendar.set(Calendar.MONTH, myCalendar.get(Calendar.MONTH));
        myCalendar.set(Calendar.YEAR, myCalendar.get(Calendar.YEAR));
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(myCalendar.getTime());
    }

    DatePickerDialog.OnDateSetListener fromDate = new DatePickerDialog.OnDateSetListener() {
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
        txtFromDate.setText(sdf.format(myCalendar.getTime()));
    }

    DatePickerDialog.OnDateSetListener toDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendarToDate.set(Calendar.YEAR, year);
            myCalendarToDate.set(Calendar.MONTH, month);
            myCalendarToDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelToDate();
        }
    };

    private void updateLabelToDate() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        txtToDate.setText(sdf.format(myCalendarToDate.getTime()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOk:
                String periodTime = txtFromDate.getText().toString() + " - "
                        + txtToDate.getText().toString();
                sendBackResult(periodTime);
                break;
            case R.id.rlFromDate:
                new DatePickerDialog(getActivity(), fromDate, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.rlToDate:
                new DatePickerDialog(getActivity(), toDate, myCalendarToDate.get(Calendar.YEAR),
                        myCalendarToDate.get(Calendar.MONTH), myCalendarToDate.get(Calendar.DAY_OF_MONTH)).show();
                break;
        }
    }

    public interface ReportPickTimeListener {
        void onFinishPickTime(String inputText);
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackResult(String inputText) {
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        ReportPickTimeListener listener = (ReportPickTimeListener) getTargetFragment();
        listener.onFinishPickTime(inputText);
        dismiss();
    }
}