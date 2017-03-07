package com.example.nguyennam.financialbook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.model.AccountRecyclerView;


import java.util.List;

public class AccountRecyclerViewAdapter extends RecyclerView.Adapter<AccountRecyclerViewAdapter.AccountViewHolder> {

    Context context;
    List<AccountRecyclerView> data;

    public AccountRecyclerViewAdapter(Context context, List<AccountRecyclerView> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public AccountRecyclerViewAdapter.AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account_recyclerview, parent, false);
        AccountViewHolder myViewHolder = new AccountViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(AccountRecyclerViewAdapter.AccountViewHolder holder, int position) {
        AccountRecyclerView account = data.get(position);
        holder.txtAccountType.setText(account.getAccountType());
        holder.txtAmountMoney.setText(String.valueOf(account.getAmountMoney()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class AccountViewHolder extends RecyclerView.ViewHolder {
        TextView txtAccountType;
        TextView txtAmountMoney;
        public AccountViewHolder(View itemView) {
            super(itemView);
            txtAccountType = (TextView) itemView.findViewById(R.id.txtAccountType);
            txtAmountMoney = (TextView) itemView.findViewById(R.id.txtAmountMoney);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnClickListener.onClick(getAdapterPosition());
                }
            });
        }
    }

    public interface AccountOnClickListener {
        void onClick(int position);
    }

    AccountOnClickListener myOnClickListener;

    public void setMyOnClickListener(AccountOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }
}
