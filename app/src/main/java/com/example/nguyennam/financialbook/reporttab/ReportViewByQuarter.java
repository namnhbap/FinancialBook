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
import com.example.nguyennam.financialbook.adapters.ReportViewByQuarterAdapter;
import com.example.nguyennam.financialbook.model.ReportMonth;
import com.example.nguyennam.financialbook.model.ReportQuater;

import java.util.ArrayList;
import java.util.List;

public class ReportViewByQuarter extends Fragment implements ReportViewByQuarterAdapter.ReportQuarterViewHolderOnClickListener {

    Context context;
    List<ReportQuater> data;

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
        View v = inflater.inflate(R.layout.report_view_by_quarter, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerviewQuarterReport);
        data = new ArrayList<>();
        data.add(new ReportQuater("IV","2017","10.000.000", "10.000.000"));
        data.add(new ReportQuater("III","2017","20.000.000", "20.000.000"));

        ReportViewByQuarterAdapter myAdapter = new ReportViewByQuarterAdapter(context, data);
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
