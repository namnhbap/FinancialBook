package com.example.nguyennam.financialbook.recordtab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.adapters.IncomeCategoryAdapter;
import com.example.nguyennam.financialbook.utils.FileHelper;

import java.util.ArrayList;
import java.util.List;

public class IncomeCategory extends Fragment implements IncomeCategoryAdapter.IncomeCategoryOnClickListener, View.OnClickListener {

    List<String> data;
    Context context;

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
        View v = inflater.inflate(R.layout.record_income_category, container, false);
        ImageView btnPrevious = (ImageView) v.findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(this);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerviewIncomeCategory);
        data = new ArrayList<>();
        for (String a : getResources().getStringArray(R.array.income_category)) {
            data.add(a);
        }
        IncomeCategoryAdapter myAdapter = new IncomeCategoryAdapter(context, data);
        myAdapter.setMyOnClickListener(this);
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        return v;
    }

    @Override
    public void onClick(int position) {
        String filename = "temp_category.tmp";
        FileHelper.writeFile(context, filename, data.get(position));
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPrevious:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }
}
