package com.example.nguyennam.financialbook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.database.AccountRecyclerViewDAO;
import com.example.nguyennam.financialbook.model.BudgetRecyclerView;
import com.example.nguyennam.financialbook.model.Expense;

import java.util.List;

public class BudgetExpenseHistoryAdapter extends RecyclerView.Adapter<BudgetExpenseHistoryAdapter.BudgetExpenseViewHolder> {

    Context context;
    List<Expense> data;

    public BudgetExpenseHistoryAdapter(Context context, List<Expense> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public BudgetExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.financial_history_expensechild, parent, false);
        return new BudgetExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BudgetExpenseViewHolder holder, int position) {
        AccountRecyclerViewDAO accountDAO = new AccountRecyclerViewDAO(context);
        Expense expense = data.get(position);
        holder.txtMoney.setText(expense.get_amountMoney());
        holder.txtDescription.setText(expense.get_description());
        holder.txtAccountType.setText(accountDAO.getAccountById(expense.get_accountID()).getAccountName());
        if ("".equals(expense.get_categoryChild())) {
            holder.txtCategory.setText(expense.get_category());
        } else {
            holder.txtCategory.setText(expense.get_categoryChild());
        }
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).get_id();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //TODO
    public class BudgetExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategory;
        TextView txtDescription;
        TextView txtMoney;
        TextView txtAccountType;
        public BudgetExpenseViewHolder(View itemView) {
            super(itemView);
            txtAccountType = (TextView) itemView.findViewById(R.id.txtAccountType);
            txtMoney = (TextView) itemView.findViewById(R.id.txtMoney);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            txtCategory = (TextView) itemView.findViewById(R.id.txtCategory);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnClickListener.onClick(data.get(getAdapterPosition()).get_id());
                }
            });
        }
    }

    public interface BudgetExpenseOnClickListener {
        void onClick(int position);
    }

    BudgetExpenseOnClickListener myOnClickListener;

    public void setMyOnClickListener(BudgetExpenseOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }
}
