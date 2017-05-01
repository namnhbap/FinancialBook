package com.example.nguyennam.financialbook.reporttab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.adapters.ReportSpinnerAdapter;
import com.example.nguyennam.financialbook.model.ReportSpinner;

import java.util.ArrayList;

public class ReportMain extends Fragment implements AdapterView.OnItemSelectedListener {

    Context context;
    public ArrayList<ReportSpinner> customListViewValuesArr = new ArrayList<>();
    ReportSpinnerAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.report_main, container, false);
        Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
        // Create custom adapter object ( see below RecordSpinnerAdapter.java )
        adapter = new ReportSpinnerAdapter(getActivity(), R.layout.report_spinner_rows, customListViewValuesArr);
        // Set adapter to spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        return v;
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        insertNestedFragment(new ReportExpenseIncome());
//    }

    // Embeds the child fragment dynamically
    private void insertNestedFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.reportExpenseIncome, fragment).commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
//        String item = parent.getItemAtPosition(position).toString();
        ReportExpenseIncome reportExpenseIncome = new ReportExpenseIncome();
        ReportExpenseAnalysis reportExpenseAnalysis = new ReportExpenseAnalysis();
        switch (position) {
            case 0:
                insertNestedFragment(reportExpenseIncome);
                break;
            case 1:
                insertNestedFragment(reportExpenseAnalysis);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setListData() {
        customListViewValuesArr.add(new ReportSpinner(getResources().getString(R.string.ReportExpenseIncome)));
        customListViewValuesArr.add(new ReportSpinner(getResources().getString(R.string.ReportExpenseAnalysis)));
    }
}
