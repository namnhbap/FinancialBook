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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyennam.financialbook.MainActivity;
import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.adapters.AccountFinancialAdapter;
import com.example.nguyennam.financialbook.adapters.AccountRecyclerViewAdapter;
import com.example.nguyennam.financialbook.database.AccountRecyclerViewDAO;
import com.example.nguyennam.financialbook.model.AccountRecyclerView;
import com.example.nguyennam.financialbook.model.FinancialRecyclerView;
import com.example.nguyennam.financialbook.utils.FileHelper;

import java.util.ArrayList;
import java.util.List;

public class AccountFinancialHistory extends Fragment implements AccountFinancialAdapter.FinancialOnClickListener, View.OnClickListener {

    List<FinancialRecyclerView> data = new ArrayList<>();
    Context context;
    AccountRecyclerViewDAO accountDAO;
    AccountRecyclerView account;
    String temp_ID = "temp_ID.tmp";

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
        View v = inflater.inflate(R.layout.account_financial_history, container, false);
        Toast.makeText(context, FileHelper.readFile(context,temp_ID), Toast.LENGTH_SHORT).show();
        ImageView btnBack = (ImageView) v.findViewById(R.id.txtBack);
        btnBack.setOnClickListener(this);
        accountDAO = new AccountRecyclerViewDAO(context);
        account = accountDAO.getAccountById(Integer.parseInt(FileHelper.readFile(context, temp_ID)));
        TextView txtMoneyStart = (TextView) v.findViewById(R.id.txtMoneyStart);
        txtMoneyStart.setText(account.getMoneyStart());
        TextView txtAmountMoney = (TextView) v.findViewById(R.id.txtAmountMoney);
        txtAmountMoney.setText(account.getAmountMoney());
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        //mock data
        FinancialRecyclerView financialRecyclerView = new FinancialRecyclerView("Chi:","Ăn","27/4/2017","30.000","2.000.000");
        data.add(financialRecyclerView);
        financialRecyclerView = new FinancialRecyclerView("Thu:","Lương","27/4/2017","30.000","2.000.000");
        data.add(financialRecyclerView);

        AccountFinancialAdapter myAdapter = new AccountFinancialAdapter(context, data);
        myAdapter.setMyOnClickListener(this);
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        return v;
    }

    @Override
    public void onClick(int position) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtBack:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }
}
