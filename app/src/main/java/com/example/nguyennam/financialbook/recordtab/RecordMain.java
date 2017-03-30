package com.example.nguyennam.financialbook.recordtab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.nguyennam.financialbook.MainActivity;
import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.adapters.RecordSpinnerAdapter;
import com.example.nguyennam.financialbook.model.RecordSpinner;

import java.util.ArrayList;

public class RecordMain extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Context context;
    ImageView imgHistory;
    public ArrayList<RecordSpinner> customListViewValuesArr = new ArrayList<>();
    RecordSpinnerAdapter adapter;
//    Bundle bundle;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set data in arraylist
        setListData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.record_main, container, false);
        imgHistory = (ImageView) v.findViewById(R.id.imgHistory);
        imgHistory.setOnClickListener(this);
        Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
        // Create custom adapter object ( see below RecordSpinnerAdapter.java )
        adapter = new RecordSpinnerAdapter(getActivity(), R.layout.record_spinner_rows, customListViewValuesArr);
        // Set adapter to spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        insertNestedFragment(new ExpenseFormInput());
    }

    // Embeds the child fragment dynamically
    private void insertNestedFragment(Fragment fragment) {
//        Fragment childFragment = new ExpenseFormInput();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.formInputExpenseIncome, fragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgHistory:
                ((MainActivity) context).replaceFragment(new FinancialHistory(), true);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
//        String item = parent.getItemAtPosition(position).toString();
        ExpenseFormInput expenseFormInput = new ExpenseFormInput();
        IncomeFormInput incomeFormInput = new IncomeFormInput();
        TransferFormInput transferFormInput = new TransferFormInput();
        switch (position) {
            case 0:
                insertNestedFragment(expenseFormInput);
                break;
            case 1:
                insertNestedFragment(incomeFormInput);
                break;
            case 2:
                insertNestedFragment(transferFormInput);
                break;
        }
        // Showing selected spinner item
//        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setListData() {
        customListViewValuesArr.add(new RecordSpinner(getResources().getString(R.string.Expense), getResources().getString(R.string.ExpenseDescription)));
        customListViewValuesArr.add(new RecordSpinner(getResources().getString(R.string.Income), getResources().getString(R.string.IncomeDescription)));
        customListViewValuesArr.add(new RecordSpinner(getResources().getString(R.string.Transfer), getResources().getString(R.string.TransferDescription)));
    }
}
