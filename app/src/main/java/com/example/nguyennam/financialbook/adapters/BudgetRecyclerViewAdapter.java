package com.example.nguyennam.financialbook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nguyennam.financialbook.R;
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
        BudgetRecyclerView budget = data.get(position);
        holder.txtBudgetName.setText(budget.getTxtBudgetName());
        holder.txtBudgetMoney.setText(budget.getTxtBudgetMoney());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //TODO
    public class BudgetViewHolder extends RecyclerView.ViewHolder {
        TextView txtBudgetName;
        TextView txtBudgetMoney;
        public BudgetViewHolder(View itemView) {
            super(itemView);
            txtBudgetName = (TextView) itemView.findViewById(R.id.txtBudgetName);
            txtBudgetMoney = (TextView) itemView.findViewById(R.id.txtBudgetMoney);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnClickListener.onClick(getAdapterPosition());
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
