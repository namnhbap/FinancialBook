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
import com.example.nguyennam.financialbook.model.CategoryIncome;

import java.util.List;

public class ReportCategoryIncomeAdapter extends RecyclerView.Adapter<ReportCategoryIncomeAdapter.ReportIncomeViewHolder> {

    Context context;
    List<CategoryIncome> data;

    public ReportCategoryIncomeAdapter(Context context, List<CategoryIncome> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ReportCategoryIncomeAdapter.ReportIncomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_period_time_income_item, parent, false);
        ReportCategoryIncomeAdapter.ReportIncomeViewHolder myViewHolder = new ReportCategoryIncomeAdapter.ReportIncomeViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ReportCategoryIncomeAdapter.ReportIncomeViewHolder holder, int position) {
        CategoryIncome categoryIncome = data.get(position);
        holder.txtCategoryIncome.setText(categoryIncome.getName());
        holder.txtAmountMoney.setText(categoryIncome.getMoney());
        holder.txtMoneyPercent.setText(categoryIncome.getPercent());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ReportIncomeViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategoryIncome;
        TextView txtAmountMoney;
        TextView txtMoneyPercent;
        public ReportIncomeViewHolder(View itemView) {
            super(itemView);
            txtCategoryIncome = (TextView) itemView.findViewById(R.id.txtCategoryIncome);
            txtAmountMoney = (TextView) itemView.findViewById(R.id.txtAmountMoney);
            txtMoneyPercent = (TextView) itemView.findViewById(R.id.txtMoneyPercent);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnClickListener.onClick(data.get(getAdapterPosition()).getName());
                }
            });
        }
    }

    public interface ReportIncomeOnClickListener {
        void onClick(String name);
    }

    ReportCategoryIncomeAdapter.ReportIncomeOnClickListener myOnClickListener;

    public void setMyOnClickListener(ReportCategoryIncomeAdapter.ReportIncomeOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }
}
