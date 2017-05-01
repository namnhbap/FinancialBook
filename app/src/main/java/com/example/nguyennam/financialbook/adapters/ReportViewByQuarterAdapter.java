package com.example.nguyennam.financialbook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.model.ReportMonth;
import com.example.nguyennam.financialbook.model.ReportQuater;

import java.util.List;

public class ReportViewByQuarterAdapter extends RecyclerView.Adapter<ReportViewByQuarterAdapter.ReportViewByQuarterViewHolder> {

    Context context;
    List<ReportQuater> data;

    public ReportViewByQuarterAdapter(Context context, List<ReportQuater> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ReportViewByQuarterAdapter.ReportViewByQuarterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_view_by_quarter_item, parent, false);
        return new ReportViewByQuarterAdapter.ReportViewByQuarterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReportViewByQuarterAdapter.ReportViewByQuarterViewHolder holder, int position) {
        ReportQuater reportMonth = data.get(position);
        holder.txtQuarter.setText(reportMonth.getQuarter());
        holder.txtYear.setText(reportMonth.getYear());
        holder.txtMoneyIncome.setText(reportMonth.getMoneyIncome());
        holder.txtMoneyExpense.setText(reportMonth.getMoneyExpense());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ReportViewByQuarterViewHolder extends RecyclerView.ViewHolder {
        TextView txtYear;
        TextView txtQuarter;
        TextView txtMoneyIncome;
        TextView txtMoneyExpense;
        public ReportViewByQuarterViewHolder(View itemView) {
            super(itemView);
            txtYear = (TextView) itemView.findViewById(R.id.txtYear);
            txtQuarter = (TextView) itemView.findViewById(R.id.txtQuarter);
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

    public interface ReportQuarterViewHolderOnClickListener {
        void onClick();
    }

    ReportViewByQuarterAdapter.ReportQuarterViewHolderOnClickListener myOnClickListener;

    public void setMyOnClickListener(ReportViewByQuarterAdapter.ReportQuarterViewHolderOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }
}
