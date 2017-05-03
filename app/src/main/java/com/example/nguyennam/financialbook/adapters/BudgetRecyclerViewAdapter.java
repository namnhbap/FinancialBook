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

import java.util.List;

public class BudgetRecyclerViewAdapter extends RecyclerView.Adapter<BudgetRecyclerViewAdapter.BudgetViewHolder> {

    Context context;
    List<BudgetRecyclerView> data;

    public BudgetRecyclerViewAdapter(Context context, List<BudgetRecyclerView> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public BudgetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.budget_item_recyclerview, parent, false);
        BudgetViewHolder myViewHolder = new BudgetViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(BudgetViewHolder holder, int position) {
        AccountRecyclerViewDAO accountDAO = new AccountRecyclerViewDAO(context);
        BudgetRecyclerView budget = data.get(position);
        holder.txtBudgetName.setText(budget.getBudgetName());
        holder.txtAmountMoney.setText(budget.getAmountMoney());
        holder.txtAccount.setText(accountDAO.getAccountById(budget.getAccountID()).getAccountName());
        holder.txtCategory.setText(budget.getCategory());
        holder.txtDate.setText(budget.getDate());
        holder.txtRemainMoney.setText(budget.getRemainMoney());
        holder.txtExpenseMoney.setText(budget.getExpenseMoney());
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //TODO
    public class BudgetViewHolder extends RecyclerView.ViewHolder {
        TextView txtBudgetName;
        TextView txtAmountMoney;
        TextView txtAccount;
        TextView txtCategory;
        TextView txtDate;
        TextView txtRemainMoney;
        TextView txtExpenseMoney;
        public BudgetViewHolder(View itemView) {
            super(itemView);
            txtBudgetName = (TextView) itemView.findViewById(R.id.txtBudgetName);
            txtAmountMoney = (TextView) itemView.findViewById(R.id.txtAmountMoney);
            txtAccount = (TextView) itemView.findViewById(R.id.txtAccount);
            txtCategory = (TextView) itemView.findViewById(R.id.txtCategory);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtRemainMoney = (TextView) itemView.findViewById(R.id.txtRemainMoney);
            txtExpenseMoney = (TextView) itemView.findViewById(R.id.txtExpenseMoney);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnClickListener.onClick(data.get(getAdapterPosition()).getId());
                }
            });
        }
    }

    public interface BudgetOnClickListener {
        void onClick(int position);
    }

    BudgetOnClickListener myOnClickListener;

    public void setMyOnClickListener(BudgetOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }
}
