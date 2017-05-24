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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguyennam.financialbook.MainActivity;
import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.accounttab.AccountFinancialHistory;
import com.example.nguyennam.financialbook.adapters.RecordAccountAdapter;
import com.example.nguyennam.financialbook.database.AccountRecyclerViewDAO;
import com.example.nguyennam.financialbook.model.AccountRecyclerView;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

import java.util.List;

public class ReportFinancialStatementDetail extends Fragment implements RecordAccountAdapter.RecordAccountOnClickListener, View.OnClickListener {

    List<AccountRecyclerView> data;
    Context context;
    AccountRecyclerViewDAO account;
    String lbAccount;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lbAccount = FileHelper.readFile(context, Constant.TEMP_ACCOUNT_TYPE);
        account = new AccountRecyclerViewDAO(context);
        data = account.getAccountByAccountType(lbAccount);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.record_account, container, false);
        ImageView txtCancel = (ImageView) v.findViewById(R.id.txtCancel);
        txtCancel.setOnClickListener(this);
        TextView lbSelectAccount = (TextView) v.findViewById(R.id.lbSelectAccount);
        lbSelectAccount.setText(lbAccount);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerviewSelectAccount);
        RecordAccountAdapter myAdapter = new RecordAccountAdapter(context, data);
        myAdapter.setMyOnClickListener(this);
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        return v;
    }

    @Override
    public void onClick(int position) {
        FileHelper.writeFile(context, Constant.TEMP_ID, "" + position);
        ((MainActivity) context).replaceFragment(new AccountFinancialHistory(), true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtCancel:
                FileHelper.deleteFile(context, Constant.TEMP_ACCOUNT_TYPE);
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }
}
