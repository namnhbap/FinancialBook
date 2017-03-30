package com.example.nguyennam.financialbook.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.model.RecordSpinner;
import com.example.nguyennam.financialbook.model.ReportSpinner;

import java.util.ArrayList;

public class ReportSpinnerAdapter extends ArrayAdapter<String> {

    private Activity activity;
    private ArrayList data;
    ReportSpinner tempValues = null;
    LayoutInflater inflater;

    public ReportSpinnerAdapter(
            Activity activitySpinner,
            int textViewResourceId,
            ArrayList objects
    ) {
        super(activitySpinner, textViewResourceId, objects);

        /********** Take passed values **********/
        activity = activitySpinner;
        data = objects;

        /***********  Layout inflator to call external xml layout () **********************/
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getReportName(position, convertView, parent);
    }

    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        /********** Inflate record_spinner_rowser_rows.xml file for each row ( Defined below ) ************/
        View row = inflater.inflate(R.layout.report_spinner_rows, parent, false);

        /***** Get each Model object from Arraylist ********/
        tempValues = null;
        tempValues = (ReportSpinner) data.get(position);

        TextView label = (TextView) row.findViewById(R.id.reportName);

        // Set values for spinner each row
        label.setText(tempValues.getReportName());
        return row;
    }

    public View getReportName(int position, View convertView, ViewGroup parent) {
        /********** Inflate record_spinner_rows.xmlows.xml file for each row ( Defined below ) ************/
        View row = inflater.inflate(R.layout.spinner_selected, parent, false);
        /***** Get each Model object from Arraylist ********/
        tempValues = null;
        tempValues = (ReportSpinner) data.get(position);
        TextView label = (TextView) row.findViewById(R.id.recordNameSelected);
        label.setText(tempValues.getReportName());
        return row;
    }
}