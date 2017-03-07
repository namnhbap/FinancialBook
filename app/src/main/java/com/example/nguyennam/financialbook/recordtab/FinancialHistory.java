package com.example.nguyennam.financialbook.recordtab;

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

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.adapters.FinancialHistoryAdapter;
import com.example.nguyennam.financialbook.model.Expense;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.database.ExpenseDAO;

import java.util.List;

public class FinancialHistory extends Fragment implements FinancialHistoryAdapter.MyOnClick {

    Context context;
    List<Expense> data;

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
        View view = inflater.inflate(R.layout.list_financial_history, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerviewHistory);
        ExpenseDAO expenseDAO = new ExpenseDAO(context);
//        expenseDAO.addExpense(new ExpenseBEAN(1,"100000","Ăn uống", "ac1", "Ví", "12/1/2017", "ok"));
//        expenseDAO.addExpense(new ExpenseBEAN(2,"200000","Đi lại", "ac2", "Ví", "12/1/2017", "ok"));
//        expenseDAO.addExpense(new ExpenseBEAN(3,"350000","Điện nước", "ac3", "ATM", "13/1/2017", "ok"));
        data = expenseDAO.getAllExpense();
        FinancialHistoryAdapter myAdapter = new FinancialHistoryAdapter(context, data);
        myAdapter.setMyOnClick(this);
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        Log.d(Constant.TAG, "onCreate: " + data);
        return view;
    }

    @Override
    public void onClick(Expense expenseBEAN) {

    }
}
