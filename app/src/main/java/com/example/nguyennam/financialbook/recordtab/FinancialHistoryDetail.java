package com.example.nguyennam.financialbook.recordtab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

public class FinancialHistoryDetail extends Fragment implements View.OnClickListener {

    Context context;
    ImageView btnBack;
    TextView lbDescription;

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
        View v = inflater.inflate(R.layout.financial_history_detail, container, false);
        btnBack = (ImageView) v.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        boolean isExpense = Boolean.parseBoolean(FileHelper.readFile(context, Constant.TEMP_ISEXPENSE));
        if (isExpense) {
            lbDescription = (TextView) view.findViewById(R.id.lbDescription);
            lbDescription.setText(getResources().getString(R.string.Expense));
            insertNestedFragment(new ExpenseFormEdit());
        } else {
            lbDescription = (TextView) view.findViewById(R.id.lbDescription);
            lbDescription.setText(getResources().getString(R.string.Income));
            insertNestedFragment(new IncomeFormEdit());
        }
    }

    // Embeds the child fragment dynamically
    private void insertNestedFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.formInputExpenseIncome, fragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                clearTempFile();
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }

    private void clearTempFile() {
        FileHelper.deleteFile(context, Constant.TEMP_ISEXPENSE);
        FileHelper.deleteFile(context, Constant.TEMP_CALCULATOR);
        FileHelper.deleteFile(context, Constant.TEMP_CALCULATOR_EDIT);
        FileHelper.deleteFile(context, Constant.TEMP_CATEGORY);
        FileHelper.deleteFile(context, Constant.TEMP_CATEGORY_CHILD);
        FileHelper.deleteFile(context, Constant.TEMP_ACCOUNT_ID_EDIT);
        FileHelper.deleteFile(context, Constant.TEMP_DESCRIPTION);
        FileHelper.deleteFile(context, Constant.TEMP_EVENT);
    }
}
