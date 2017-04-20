package com.example.nguyennam.financialbook.accounttab;

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
import com.example.nguyennam.financialbook.adapters.AccountRecyclerViewAdapter;
import com.example.nguyennam.financialbook.database.AccountRecyclerViewDAO;
import com.example.nguyennam.financialbook.model.AccountRecyclerView;
import com.example.nguyennam.financialbook.recordtab.FinancialHistory;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

import java.util.List;

public class AccountMain extends Fragment implements AccountRecyclerViewAdapter.AccountOnClickListener, View.OnClickListener {

    List<AccountRecyclerView> data;
    Context context;
    String filename = "temp_ID.tmp";

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
        ImageView btnAddAccount = (ImageView) v.findViewById(R.id.btnAddAccount);
        btnAddAccount.setOnClickListener(this);
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
        //display financial history of this account
        FileHelper.writeFile(context, filename, "" + position);
        ((MainActivity) context).replaceFragment(new AccountFinancialHistory(), true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddAccount:
                ((MainActivity) context).replaceFragment(new AddAccount(), true);
                break;
        }
    }
}
