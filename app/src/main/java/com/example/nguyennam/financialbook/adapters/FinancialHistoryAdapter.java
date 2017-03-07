package com.example.nguyennam.financialbook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nguyennam.financialbook.model.Expense;
import com.example.nguyennam.financialbook.R;

import java.util.List;

/**
 * Created by NguyenNam on 1/17/2017.
 */

public class FinancialHistoryAdapter extends RecyclerView.Adapter<FinancialHistoryAdapter.HolderHistory> {
    Context context;
    List<Expense> data;
    MyOnClick myOnClick;

    public void setMyOnClick(MyOnClick myOnClick) {
        this.myOnClick = myOnClick;
    }

    public FinancialHistoryAdapter(Context context, List<Expense> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public HolderHistory onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_financial_history, parent, false);
        return new HolderHistory(view);
    }

    @Override
    public void onBindViewHolder(HolderHistory holder, int position) {
        Expense expense = data.get(position);
        holder.tvCategory.setText(expense.get_expenseCategory());
        holder.tvMoney.setText(expense.get_amountMoney());
        holder.tvTypeAccount.setText(expense.get_fromAccount());
        holder.tvDate.setText(expense.get_expenseDate());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class HolderHistory extends RecyclerView.ViewHolder{
        TextView tvCategory;
        TextView tvMoney;
        TextView tvTypeAccount;
        TextView tvDate;
        public HolderHistory(View itemView) {
            super(itemView);
            tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
            tvMoney = (TextView) itemView.findViewById(R.id.tvMoney);
            tvTypeAccount = (TextView) itemView.findViewById(R.id.tvTypeAccount);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnClick.onClick(data.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface MyOnClick{
        void onClick(Expense expense);
    }
}
