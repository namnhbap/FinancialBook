package com.example.nguyennam.financialbook.reporttab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.adapters.ReportViewByMonthAdapter;
import com.example.nguyennam.financialbook.adapters.ReportViewByYearAdapter;
import com.example.nguyennam.financialbook.model.ReportMonth;
import com.example.nguyennam.financialbook.model.ReportYear;

import java.util.ArrayList;
import java.util.List;

public class ReportViewByYear extends Fragment implements ReportViewByYearAdapter.ReportYearOnClickListener {

    Context context;
    List<ReportYear> data;

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
        data.add(new ReportYear("2017","100.000.000", "100.000.000"));
        data.add(new ReportYear("2016","200.000.000", "200.000.000"));

        ReportViewByYearAdapter myAdapter = new ReportViewByYearAdapter(context, data);
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
