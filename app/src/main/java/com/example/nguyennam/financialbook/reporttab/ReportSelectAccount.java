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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.adapters.ReportSelectAccountAdapter;
import com.example.nguyennam.financialbook.database.AccountRecyclerViewDAO;
import com.example.nguyennam.financialbook.model.AccountRecyclerView;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

import java.util.List;

public class ReportSelectAccount extends Fragment implements View.OnClickListener, ReportSelectAccountAdapter.AccountOnClickListener, CompoundButton.OnCheckedChangeListener {

    List<AccountRecyclerView> data;
    Context context;
    AccountRecyclerViewDAO account;
    CheckBox checkBoxAll;
    ReportSelectAccountAdapter myAdapter;

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
        View v = inflater.inflate(R.layout.report_select_account, container, false);
        ImageView txtCancel = (ImageView) v.findViewById(R.id.txtCancel);
        txtCancel.setOnClickListener(this);
        TextView txtDone = (TextView) v.findViewById(R.id.txtDone);
        txtDone.setOnClickListener(this);
        checkBoxAll = (CheckBox) v.findViewById(R.id.checkboxAll);
        checkBoxAll.setChecked(true);
        checkBoxAll.setOnCheckedChangeListener(this);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerviewSelectAccount);
        account = new AccountRecyclerViewDAO(context);
        data = account.getAllAccount();
        myAdapter = new ReportSelectAccountAdapter(context, data);
        myAdapter.setMyOnClickListener(this);
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        return v;
    }

    @Override
    public void onClick(int position) {
        FileHelper.writeFile(context, Constant.TEMP_ID, "" + position);
        getActivity().getSupportFragmentManager().popBackStack();
//        for (AccountRecyclerView accountRecyclerView : data) {
//            if (position == accountRecyclerView.getId()) {
//                accountRecyclerView.setChecked(true);
//            } else {
//                accountRecyclerView.setChecked(false);
//            }
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtCancel:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.txtDone:
                String idAccount = "";
                for (AccountRecyclerView accountRecyclerView : data) {
                    if (accountRecyclerView.isChecked()) {
                        idAccount += accountRecyclerView.getId() + ";";
                    }
                }
                FileHelper.writeFile(context, Constant.TEMP_ID, idAccount);
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            myAdapter.selectAll();
            for (AccountRecyclerView accountRecyclerView : data) {
                accountRecyclerView.setChecked(true);
            }
        } else {
            myAdapter.unSelectAll();
            for (AccountRecyclerView accountRecyclerView : data) {
                accountRecyclerView.setChecked(false);
            }
        }
    }
}
