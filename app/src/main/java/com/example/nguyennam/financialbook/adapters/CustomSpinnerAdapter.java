package com.example.nguyennam.financialbook.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.nguyennam.financialbook.MainActivity;
import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.recordtab.RecordMain;
import com.example.nguyennam.financialbook.recordtab.SpinnerModel;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

    private Activity activity;
    private ArrayList data;
    SpinnerModel tempValues = null;
    LayoutInflater inflater;

    public CustomSpinnerAdapter(
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
        return getRecordName(position, convertView, parent);
    }

    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        View row = inflater.inflate(R.layout.spinner_rows, parent, false);

        /***** Get each Model object from Arraylist ********/
        tempValues = null;
        tempValues = (SpinnerModel) data.get(position);

        TextView label = (TextView) row.findViewById(R.id.recordName);
        TextView sub = (TextView) row.findViewById(R.id.description);

        // Set values for spinner each row
        label.setText(tempValues.getRecordName());
        sub.setText(tempValues.getDescription());
        return row;
    }

    public View getRecordName(int position, View convertView, ViewGroup parent) {
        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        View row = inflater.inflate(R.layout.spinner_selected, parent, false);
        /***** Get each Model object from Arraylist ********/
        tempValues = null;
        tempValues = (SpinnerModel) data.get(position);
        TextView label = (TextView) row.findViewById(R.id.recordNameSelected);
        label.setText(tempValues.getRecordName());
        return row;
    }
}