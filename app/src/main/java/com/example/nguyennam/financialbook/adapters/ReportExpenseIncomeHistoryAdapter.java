package com.example.nguyennam.financialbook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.model.FinancialHistoryChild;

import java.util.List;

public class ReportExpenseIncomeHistoryAdapter extends RecyclerView.Adapter
        <ReportExpenseIncomeHistoryAdapter.ReportExpenseIncomeViewHolder> {

    Context context;
    List<FinancialHistoryChild> data;

    public ReportExpenseIncomeHistoryAdapter(Context context, List<FinancialHistoryChild> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ReportExpenseIncomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.financial_history_expensechild, parent, false);
        return new ReportExpenseIncomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReportExpenseIncomeViewHolder holder, int position) {
        FinancialHistoryChild financialHistoryChild = data.get(position);
        holder.txtDescription.setText(financialHistoryChild.getDescription()); //not description, date instead
        holder.txtAccountType.setText(financialHistoryChild.getAccount());
        holder.txtCategory.setText(financialHistoryChild.getCategory());
        holder.txtMoney.setText(financialHistoryChild.getMoneyAmount());
        if (!financialHistoryChild.isExpense()) {
            holder.txtIsExpense.setText(R.string.tvIncome);
            holder.txtIsExpense.setTextColor(context.getResources().getColor(R.color.incomeColor));
            holder.txtCategory.setTextColor(context.getResources().getColor(R.color.incomeColor));
            holder.txtMoney.setTextColor(context.getResources().getColor(R.color.incomeColor));
            holder.lbCurrency.setTextColor(context.getResources().getColor(R.color.incomeColor));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //TODO
    public class ReportExpenseIncomeViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategory;
        TextView txtDescription;
        TextView txtMoney;
        TextView txtAccountType;
        TextView txtIsExpense;
        TextView lbCurrency;
        public ReportExpenseIncomeViewHolder(View itemView) {
            super(itemView);
            txtAccountType = (TextView) itemView.findViewById(R.id.txtAccountType);
            txtMoney = (TextView) itemView.findViewById(R.id.txtMoney);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            txtCategory = (TextView) itemView.findViewById(R.id.txtCategory);
            txtIsExpense = (TextView) itemView.findViewById(R.id.txtIsExpense);
            lbCurrency = (TextView) itemView.findViewById(R.id.lbCurrency);
        }
    }
}
