package com.example.nguyennam.financialbook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.model.ReportMonth;
import com.example.nguyennam.financialbook.model.ReportYear;

import java.util.List;

public class ReportViewByYearAdapter extends RecyclerView.Adapter<ReportViewByYearAdapter.ReportViewByYearViewHolder> {

    Context context;
    List<ReportYear> data;

    public ReportViewByYearAdapter(Context context, List<ReportYear> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ReportViewByYearAdapter.ReportViewByYearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_view_by_year_item, parent, false);
        return new ReportViewByYearAdapter.ReportViewByYearViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReportViewByYearAdapter.ReportViewByYearViewHolder holder, int position) {
        ReportYear reportYear = data.get(position);
        holder.txtYear.setText(reportYear.getYear());
        holder.txtMoneyIncome.setText(reportYear.getMoneyIncome());
        holder.txtMoneyExpense.setText(reportYear.getMoneyExpense());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ReportViewByYearViewHolder extends RecyclerView.ViewHolder {
        TextView txtYear;
        TextView txtMoneyIncome;
        TextView txtMoneyExpense;
        public ReportViewByYearViewHolder(View itemView) {
            super(itemView);
            txtYear = (TextView) itemView.findViewById(R.id.txtYear);
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

    public interface ReportYearOnClickListener {
        void onClick();
    }

    ReportViewByYearAdapter.ReportYearOnClickListener myOnClickListener;

    public void setMyOnClickListener(ReportViewByYearAdapter.ReportYearOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }
}
