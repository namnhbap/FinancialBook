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
import com.example.nguyennam.financialbook.adapters.RecordAccountAdapter;
import com.example.nguyennam.financialbook.database.AccountRecyclerViewDAO;
import com.example.nguyennam.financialbook.model.AccountRecyclerView;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

import java.util.List;

public class Accounts extends Fragment implements RecordAccountAdapter.RecordAccountOnClickListener, View.OnClickListener {

    List<AccountRecyclerView> data;
    Context context;
    AccountRecyclerViewDAO account;

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
        View v = inflater.inflate(R.layout.record_account, container, false);
        ImageView txtCancel = (ImageView) v.findViewById(R.id.txtCancel);
        txtCancel.setOnClickListener(this);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerviewSelectAccount);
        account = new AccountRecyclerViewDAO(context);
        data = account.getAllAccount();
        RecordAccountAdapter myAdapter = new RecordAccountAdapter(context, data);
        myAdapter.setMyOnClickListener(this);
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        return v;
    }

    @Override
    public void onClick(int position) {
        String accountID = String.valueOf(account.getAccountById(position).getId());
        FileHelper.writeFile(context, Constant.TEMP_ACCOUNT_ID, accountID);
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtCancel:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }
}
