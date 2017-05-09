package com.example.nguyennam.financialbook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.model.ReportQuarter;
import com.example.nguyennam.financialbook.utils.CalculatorSupport;

import java.util.List;

public class ReportViewByQuarterAdapter extends RecyclerView.Adapter<ReportViewByQuarterAdapter.ReportViewByQuarterViewHolder> {

    Context context;
    List<ReportQuarter> data;

    public ReportViewByQuarterAdapter(Context context, List<ReportQuarter> data) {
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
        ReportQuarter reportQuarter = data.get(position);
        holder.txtQuarter.setText(reportQuarter.getQuarter());
        holder.txtYear.setText(reportQuarter.getYear());
        holder.txtMoneyIncome.setText(reportQuarter.getMoneyIncome());
        holder.txtMoneyExpense.setText(reportQuarter.getMoneyExpense());
        // convert dp to pxl and calculate percent to display the width of line
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        float sum = Float.parseFloat(CalculatorSupport.formatExpression(reportQuarter.getMoneyExpense()))
                + Float.parseFloat(CalculatorSupport.formatExpression(reportQuarter.getMoneyIncome()));
        String incomePercent = Double.toString((double) Math.round(
                Double.parseDouble(CalculatorSupport.formatExpression(reportQuarter.getMoneyIncome()))
                        / sum * 100
                        * 10) / 10);
        String expensePercent = Double.toString((double) Math.round(
                Double.parseDouble(CalculatorSupport.formatExpression(reportQuarter.getMoneyExpense()))
                        / sum * 100
                        * 10) / 10);
        int widthIncome = (int) (1.2 * Float.parseFloat(incomePercent) * scale + 0.5f);
        int widthExpense = (int) (1.2 * Float.parseFloat(expensePercent) * scale + 0.5f);
        if (widthExpense > widthIncome) {
            widthExpense = (int) (120 * scale + 0.5f);
            widthIncome = (int) (120 * Float.parseFloat(incomePercent) * scale / Float.parseFloat(expensePercent) + 0.5f);
        } else if (widthExpense < widthIncome) {
            widthIncome = (int) (120 * scale + 0.5f);
            widthExpense = (int) (120 * Float.parseFloat(expensePercent) * scale / Float.parseFloat(incomePercent) + 0.5f);
        } else {
            widthExpense = widthIncome = (int) (120 * scale + 0.5f);
        }
        int height = (int) (10 * scale + 0.5f);
        holder.lnIncome.setLayoutParams(new LinearLayout.LayoutParams(widthIncome, height));
        holder.lnExpense.setLayoutParams(new LinearLayout.LayoutParams(widthExpense, height));
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
        LinearLayout lnIncome;
        LinearLayout lnExpense;

        public ReportViewByQuarterViewHolder(View itemView) {
            super(itemView);
            txtYear = (TextView) itemView.findViewById(R.id.txtYear);
            txtQuarter = (TextView) itemView.findViewById(R.id.txtQuarter);
            txtMoneyIncome = (TextView) itemView.findViewById(R.id.txtMoneyIncome);
            txtMoneyExpense = (TextView) itemView.findViewById(R.id.txtMoneyExpense);
            lnIncome = (LinearLayout) itemView.findViewById(R.id.lnIncome);
            lnExpense = (LinearLayout) itemView.findViewById(R.id.lnExpense);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnClickListener.onClick(data.get(getAdapterPosition()).getQuarter(),
                            data.get(getAdapterPosition()).getYear());
                }
            });
        }
    }

    public interface ReportQuarterViewHolderOnClickListener {
        void onClick(String quarter, String year);
    }

    ReportViewByQuarterAdapter.ReportQuarterViewHolderOnClickListener myOnClickListener;

    public void setMyOnClickListener(ReportViewByQuarterAdapter.ReportQuarterViewHolderOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }
}
