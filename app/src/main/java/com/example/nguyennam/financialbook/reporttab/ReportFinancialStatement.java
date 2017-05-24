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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.adapters.RecordAccountAdapter;
import com.example.nguyennam.financialbook.adapters.ReportFinancialStatementAdapter;
import com.example.nguyennam.financialbook.database.AccountRecyclerViewDAO;
import com.example.nguyennam.financialbook.model.AccountRecyclerView;
import com.example.nguyennam.financialbook.utils.CalculatorSupport;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReportFinancialStatement extends Fragment implements
        ReportFinancialStatementAdapter.RecordAccountOnClickListener {

    List<AccountRecyclerView> data;
    List<AccountRecyclerView> accountList;
    Context context;
    AccountRecyclerViewDAO account;
    String totalMoney;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        account = new AccountRecyclerViewDAO(context);
        accountList = account.getAllAccount();
        totalMoney = getTotalMoney();
        data = getFinancialStatement();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.report_financial_statement, container, false);
        TextView txtTotalMoney = (TextView) v.findViewById(R.id.txtTotalMoney);
        txtTotalMoney.setText(totalMoney);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        ReportFinancialStatementAdapter myAdapter = new ReportFinancialStatementAdapter(context, data);
        myAdapter.setMyOnClickListener(this);
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        return v;
    }

    private List<AccountRecyclerView> getFinancialStatement() {
        List<AccountRecyclerView> dataTemp = new ArrayList<>();
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        String[] array = context.getApplicationContext().getResources().getStringArray(R.array.account_type);
        for (int i = 0; i < array.length; i++) {
            dataTemp.add(new AccountRecyclerView(array[i],"0"));
        }
        for (AccountRecyclerView account : accountList) {
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(account.getAccountType())) {
                    double temp = Double.parseDouble(CalculatorSupport.formatExpression(account.getAmountMoney()))
                            + Double.parseDouble(CalculatorSupport.formatExpression(dataTemp.get(i).getAmountMoney()));
                    dataTemp.get(i).setAmountMoney(nf.format(temp));
                }
            }
        }
        for (int i = dataTemp.size() - 1; i >= 0; i--) {
            if ("0".equals(dataTemp.get(i).getAmountMoney())) {
                dataTemp.remove(i);
            }
        }
        return dataTemp;
    }

    private String getTotalMoney() {
        double money = 0;
        for (AccountRecyclerView account : accountList) {
            money += Double.parseDouble(CalculatorSupport.formatExpression(account.getAmountMoney()));
        }
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        return nf.format(money);
    }

    @Override
    public void onClick(String accountType) {
//
//        FileHelper.writeFile(context, Constant.TEMP_ACCOUNT_ID, accountID);
    }
}
