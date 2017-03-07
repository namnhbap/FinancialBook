package com.example.nguyennam.financialbook.accounttab;

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
import com.example.nguyennam.financialbook.adapters.AccountRecyclerViewAdapter;
import com.example.nguyennam.financialbook.database.AccountRecyclerViewDAO;
import com.example.nguyennam.financialbook.model.AccountRecyclerView;

import java.util.List;

public class AccountMain extends Fragment implements AccountRecyclerViewAdapter.AccountOnClickListener{

    List<AccountRecyclerView> data;
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
        View v = inflater.inflate(R.layout.account_main, container, false);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        AccountRecyclerViewDAO allAcount = new AccountRecyclerViewDAO(context);
        data = allAcount.getAllAccount();
        AccountRecyclerViewAdapter myAdapter = new AccountRecyclerViewAdapter(context, data);
        myAdapter.setMyOnClickListener(this);
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        return v;
    }

    @Override
    public void onClick(int position) {

    }
}
