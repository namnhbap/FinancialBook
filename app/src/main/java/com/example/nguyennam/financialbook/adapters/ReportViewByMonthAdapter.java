package com.example.nguyennam.financialbook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.model.AccountRecyclerView;
import com.example.nguyennam.financialbook.model.ReportMonth;
import com.example.nguyennam.financialbook.utils.CalculatorSupport;

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
        // convert dp to pxl and calculate percent to display the width of line
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        float sum = Float.parseFloat(CalculatorSupport.formatExpression(reportMonth.getMoneyExpense()))
                + Float.parseFloat(CalculatorSupport.formatExpression(reportMonth.getMoneyIncome()));
        String incomePercent = Double.toString((double) Math.round(
                Double.parseDouble(CalculatorSupport.formatExpression(reportMonth.getMoneyIncome()))
                        / sum * 100
                        * 10) / 10);
        String expensePercent = Double.toString((double) Math.round(
                Double.parseDouble(CalculatorSupport.formatExpression(reportMonth.getMoneyExpense()))
                        / sum * 100
                        * 10) / 10);
        int widthIncome = (int) (1.5 * Float.parseFloat(incomePercent) * scale + 0.5f);
        int widthExpense = (int) (1.5 * Float.parseFloat(expensePercent) * scale + 0.5f);
        if (widthExpense > widthIncome) {
            widthExpense = (int) (150 * scale + 0.5f);
            widthIncome = (int) (150 * Float.parseFloat(incomePercent) * scale / Float.parseFloat(expensePercent) + 0.5f);
        } else if (widthExpense < widthIncome) {
            widthIncome = (int) (150 * scale + 0.5f);
            widthExpense = (int) (150 * Float.parseFloat(expensePercent) * scale / Float.parseFloat(incomePercent) + 0.5f);
        } else {
            widthExpense = widthIncome = (int) (150 * scale + 0.5f);
        }
        int height = (int) (10 * scale + 0.5f);
        holder.lnIncome.setLayoutParams(new LinearLayout.LayoutParams(widthIncome, height));
        holder.lnExpense.setLayoutParams(new LinearLayout.LayoutParams(widthExpense, height));
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
        LinearLayout lnIncome;
        LinearLayout lnExpense;

        public ReportViewByMonthViewHolder(View itemView) {
            super(itemView);
            txtYear = (TextView) itemView.findViewById(R.id.txtYear);
            txtMonth = (TextView) itemView.findViewById(R.id.txtMonth);
            txtMoneyIncome = (TextView) itemView.findViewById(R.id.txtMoneyIncome);
            txtMoneyExpense = (TextView) itemView.findViewById(R.id.txtMoneyExpense);
            lnIncome = (LinearLayout) itemView.findViewById(R.id.lnIncome);
            lnExpense = (LinearLayout) itemView.findViewById(R.id.lnExpense);
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
