package com.example.nguyennam.financialbook.budgettab;

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
import android.widget.ImageView;

import com.example.nguyennam.financialbook.MainActivity;
import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.adapters.BudgetRecyclerViewAdapter;
import com.example.nguyennam.financialbook.database.BudgetRecyclerViewDAO;
import com.example.nguyennam.financialbook.model.BudgetRecyclerView;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

import java.util.List;

public class BudgetMain extends Fragment implements View.OnClickListener, BudgetRecyclerViewAdapter.BudgetOnClickListener {

    Context context;
    List<BudgetRecyclerView> data;

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
        View v = inflater.inflate(R.layout.budget_main, container, false);
        ImageView imgAddBudget = (ImageView) v.findViewById(R.id.imgAddBudget);
        imgAddBudget.setOnClickListener(this);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerviewBudget);
        BudgetRecyclerViewDAO allBudget = new BudgetRecyclerViewDAO(context);
        data = allBudget.getAllBudget();
        BudgetRecyclerViewAdapter myAdapter = new BudgetRecyclerViewAdapter(context, data);
        myAdapter.setMyOnClickListener(this);
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgAddBudget:
                ((MainActivity) context).replaceFragment(new AddBudget(), true);
                break;
        }
    }

    @Override
    public void onClick(int position) {
        FileHelper.writeFile(context, Constant.TEMP_BUDGET_ID, "" + position);
        ((MainActivity)context).replaceFragment(new BudgetExpenseHistory(), true);
    }
}
