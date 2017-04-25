package com.example.nguyennam.financialbook.reporttab;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nguyennam.financialbook.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

public class ReportExpenseIncome extends Fragment implements View.OnClickListener, ReportViewByDialog.ReportViewByDialogListener {

    Context context;
    TextView txtAccount;
    TextView txtViewBy;
    String[] arrayViewBy;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayViewBy = getResources().getStringArray(R.array.report_view_by);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.report_expense_income, container, false);
        txtAccount = (TextView) v.findViewById(R.id.txtAccount);
        txtViewBy = (TextView) v.findViewById(R.id.txtViewBy);
        txtViewBy.setText(arrayViewBy[0]);
        RelativeLayout rlAccount = (RelativeLayout) v.findViewById(R.id.rlAccount);
        rlAccount.setOnClickListener(this);
        RelativeLayout rlViewBy = (RelativeLayout) v.findViewById(R.id.rlViewBy);
        rlViewBy.setOnClickListener(this);
        return v;
    }

    private void insertNestedFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentReport, fragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlAccount:
                break;
            case R.id.rlViewBy:
                ReportViewByDialog reportViewByDialog = new ReportViewByDialog();
                reportViewByDialog.setTargetFragment(ReportExpenseIncome.this, 271);
                reportViewByDialog.show(getActivity().getSupportFragmentManager(), "report_view_by");
                break;
        }

    }

    @Override
    public void onFinishReportDialog(int which, String inputText) {
        txtViewBy.setText(inputText);
        if (which == 4) {
            insertNestedFragment(new ReportPeriodTime());
        }
    }
}
