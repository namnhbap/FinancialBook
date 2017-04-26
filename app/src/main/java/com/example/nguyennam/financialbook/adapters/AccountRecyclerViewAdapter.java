package com.example.nguyennam.financialbook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyennam.financialbook.MainActivity;
import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.accounttab.EditAccount;
import com.example.nguyennam.financialbook.model.AccountRecyclerView;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;


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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_item_recyclerview, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AccountRecyclerViewAdapter.AccountViewHolder holder, int position) {
        AccountRecyclerView account = data.get(position);
        final String[] array = context.getApplicationContext().getResources().getStringArray(R.array.account_type);
        holder.txtAccountType.setText(account.getAccountName());
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
        holder.imgEditAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long dataPosition = getItemId(holder.getAdapterPosition());
                FileHelper.writeFile(context, Constant.TEMP_ID, "" + dataPosition);
                ((MainActivity) context).replaceFragment(new EditAccount(), true);
            }
        });
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

    public class AccountViewHolder extends RecyclerView.ViewHolder {
        TextView txtAccountType;
        TextView txtAmountMoney;
        ImageView imgEditAccount;
        ImageView imgAccountType;
        public AccountViewHolder(View itemView) {
            super(itemView);
            txtAccountType = (TextView) itemView.findViewById(R.id.txtAccountType);
            txtAmountMoney = (TextView) itemView.findViewById(R.id.txtAmountMoney);
            imgEditAccount = (ImageView) itemView.findViewById(R.id.btnEditAccount);
            imgAccountType = (ImageView) itemView.findViewById(R.id.imgAccountType);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnClickListener.onClick(data.get(getAdapterPosition()).getId());
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
