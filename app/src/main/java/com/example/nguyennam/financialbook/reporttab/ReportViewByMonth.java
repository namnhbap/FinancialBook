package com.example.nguyennam.financialbook.reporttab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.adapters.ReportViewByMonthAdapter;
import com.example.nguyennam.financialbook.model.Income;
import com.example.nguyennam.financialbook.model.ReportMonth;
import com.example.nguyennam.financialbook.utils.CalendarSupport;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

import java.util.ArrayList;
import java.util.List;

public class ReportViewByMonth extends Fragment implements ReportViewByMonthAdapter.ReportViewByMonthOnClickListener {

    Context context;
    List<ReportMonth> data;

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
        View v = inflater.inflate(R.layout.report_view_by_month, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerviewMonthReport);
        data = new ArrayList<>();
        data.add(new ReportMonth("4","2017","1.000.000", "1.000.000"));
        data.add(new ReportMonth("3","2017","2.000.000", "2.000.000"));
        String viewByDate = FileHelper.readFile(context, Constant.TEMP_VIEW_BY);
        ReportViewByMonthAdapter myAdapter = new ReportViewByMonthAdapter(context, data);
        myAdapter.setMyOnClickListener(this);
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        return v;
    }

    @Override
    public void onClick() {

    }
}
