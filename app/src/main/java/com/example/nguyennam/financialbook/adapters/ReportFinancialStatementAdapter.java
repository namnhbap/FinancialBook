package com.example.nguyennam.financialbook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.model.AccountRecyclerView;

import java.util.List;

public class ReportFinancialStatementAdapter extends RecyclerView.Adapter<ReportFinancialStatementAdapter.RecordAccountViewHolder> {

    Context context;
    List<AccountRecyclerView> data;

    public ReportFinancialStatementAdapter(Context context, List<AccountRecyclerView> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ReportFinancialStatementAdapter.RecordAccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_financial_statement_item, parent, false);
        return new ReportFinancialStatementAdapter.RecordAccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReportFinancialStatementAdapter.RecordAccountViewHolder holder, int position) {
        AccountRecyclerView account = data.get(position);
        final String[] array = context.getApplicationContext().getResources().getStringArray(R.array.account_type);
        holder.txtAccountType.setText(account.getAccountType());
        holder.txtAmountMoney.setText(String.valueOf(account.getAmountMoney()));
        if (array[0].equals(account.getAccountType())) {
            holder.imgAccountType.setImageResource(R.drawable.account_wallet);
        } else if (array[1].equals(account.getAccountType())) {
            holder.imgAccountType.setImageResource(R.drawable.account_bank);
        } else if (array[2].equals(account.getAccountType())) {
            holder.imgAccountType.setImageResource(R.drawable.account_master);
        } else if (array[3].equals(account.getAccountType())) {
            holder.imgAccountType.setImageResource(R.drawable.account_invest);
        } else if (array[4].equals(account.getAccountType())) {
            holder.imgAccountType.setImageResource(R.drawable.account_pig);
        }  else if (array[5].equals(account.getAccountType())) {
            holder.imgAccountType.setImageResource(R.drawable.account_other);
        }
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecordAccountViewHolder extends RecyclerView.ViewHolder {
        TextView txtAccountType;
        TextView txtAmountMoney;
        ImageView imgAccountType;
        public RecordAccountViewHolder(View itemView) {
            super(itemView);
            txtAccountType = (TextView) itemView.findViewById(R.id.txtAccountType);
            txtAmountMoney = (TextView) itemView.findViewById(R.id.txtAmountMoney);
            imgAccountType = (ImageView) itemView.findViewById(R.id.imgAccountType);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnClickListener.onClick(data.get(getAdapterPosition()).getAccountType());
                }
            });
        }
    }

    public interface RecordAccountOnClickListener {
        void onClick(String accountType);
    }

    ReportFinancialStatementAdapter.RecordAccountOnClickListener myOnClickListener;

    public void setMyOnClickListener(ReportFinancialStatementAdapter.RecordAccountOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }
}
