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
import com.example.nguyennam.financialbook.model.ReportMonth;

import java.util.List;

public class ReportViewByMonthAdapter extends RecyclerView.Adapter<ReportViewByMonthAdapter.ReportViewByMonthViewHolder> {

    Context context;
    List<ReportMonth> data;

    public ReportViewByMonthAdapter(Context context, List<ReportMonth> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ReportViewByMonthAdapter.ReportViewByMonthViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_view_by_month_item, parent, false);
        return new ReportViewByMonthAdapter.ReportViewByMonthViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReportViewByMonthAdapter.ReportViewByMonthViewHolder holder, int position) {
        ReportMonth reportMonth = data.get(position);
        holder.txtMonth.setText(reportMonth.getMonth());
        holder.txtYear.setText(reportMonth.getYear());
        holder.txtMoneyIncome.setText(reportMonth.getMoneyIncome());
        holder.txtMoneyExpense.setText(reportMonth.getMoneyExpense());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ReportViewByMonthViewHolder extends RecyclerView.ViewHolder {
        TextView txtYear;
        TextView txtMonth;
        TextView txtMoneyIncome;
        TextView txtMoneyExpense;
        public ReportViewByMonthViewHolder(View itemView) {
            super(itemView);
            txtYear = (TextView) itemView.findViewById(R.id.txtYear);
            txtMonth = (TextView) itemView.findViewById(R.id.txtMonth);
            txtMoneyIncome = (TextView) itemView.findViewById(R.id.txtMoneyIncome);
            txtMoneyExpense = (TextView) itemView.findViewById(R.id.txtMoneyExpense);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnClickListener.onClick();
                }
            });
        }
    }

    public interface ReportViewByMonthOnClickListener {
        void onClick();
    }

    ReportViewByMonthAdapter.ReportViewByMonthOnClickListener myOnClickListener;

    public void setMyOnClickListener(ReportViewByMonthAdapter.ReportViewByMonthOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }
}
