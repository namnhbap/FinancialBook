package com.example.nguyennam.financialbook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguyennam.financialbook.MainActivity;
import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.accounttab.EditAccount;
import com.example.nguyennam.financialbook.model.AccountRecyclerView;
import com.example.nguyennam.financialbook.model.FinancialRecyclerView;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

import java.util.List;

public class AccountFinancialAdapter extends RecyclerView.Adapter<AccountFinancialAdapter.FinancialViewHolder> {

    Context context;
    List<FinancialRecyclerView> data;

    public AccountFinancialAdapter(Context context, List<FinancialRecyclerView> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public AccountFinancialAdapter.FinancialViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_financial_history_itemexpense, parent, false);
        return new FinancialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AccountFinancialAdapter.FinancialViewHolder holder, int position) {
        FinancialRecyclerView financialRecyclerView = data.get(position);
//        if ("Thu:".equals(financialRecyclerView.getFinancial())) {
//            holder.txtFinancial.setTextColor(R.color.textTab);
//        }
        holder.txtFinancial.setText(financialRecyclerView.getFinancial());
        holder.txtCategory.setText(financialRecyclerView.getCategory());
        holder.txtAmountMoney.setText(financialRecyclerView.getAmountMoney());
        holder.txtDate.setText(financialRecyclerView.getDate());
        holder.txtRemainMoney.setText(financialRecyclerView.getRemainMoney());
    }

    //get id of item recyclerview
    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class FinancialViewHolder extends RecyclerView.ViewHolder {
        TextView txtFinancial;
        TextView txtCategory;
        TextView txtAmountMoney;
        TextView txtDate;
        TextView txtRemainMoney;
        public FinancialViewHolder(View itemView) {
            super(itemView);
            txtFinancial = (TextView) itemView.findViewById(R.id.txtFinancial);
            txtCategory = (TextView) itemView.findViewById(R.id.txtCategory);
            txtAmountMoney = (TextView) itemView.findViewById(R.id.txtAmountMoney);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtRemainMoney = (TextView) itemView.findViewById(R.id.txtRemainMoney);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnClickListener.onClick(getAdapterPosition());
                }
            });
        }
    }

    public interface FinancialOnClickListener {
        void onClick(int position);
    }

    FinancialOnClickListener myOnClickListener;

    public void setMyOnClickListener(FinancialOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }
}
