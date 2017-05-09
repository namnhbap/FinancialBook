package com.example.nguyennam.financialbook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.model.ReportMonth;
import com.example.nguyennam.financialbook.utils.CalculatorSupport;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

import java.util.List;

public class ReportExpenseMonthAdapter extends RecyclerView.Adapter<ReportExpenseMonthAdapter.ReportExpenseMonthViewHolder> {

    Context context;
    List<ReportMonth> data;

    public ReportExpenseMonthAdapter(Context context, List<ReportMonth> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ReportExpenseMonthAdapter.ReportExpenseMonthViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_expense_month_item, parent, false);
        return new ReportExpenseMonthAdapter.ReportExpenseMonthViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReportExpenseMonthAdapter.ReportExpenseMonthViewHolder holder, int position) {
        ReportMonth reportMonth = data.get(position);
        holder.txtMonth.setText(reportMonth.getMonth());
        holder.txtYear.setText(reportMonth.getYear());
        holder.txtMoneyExpense.setText(reportMonth.getMoneyExpense());
        // convert dp to pxl and calculate percent to display the width of line
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        double sum = Double.parseDouble(FileHelper.readFile(context, Constant.TEMP_MAX));

        String expensePercent = Double.toString((double) Math.round(
                Double.parseDouble(CalculatorSupport.formatExpression(reportMonth.getMoneyExpense()))
                        / sum * 100
                        * 10) / 10);
        // total width = 150dp
        int widthExpense = (int) (3 * Float.parseFloat(expensePercent) * scale + 0.5f);
        int height = (int) (10 * scale + 0.5f);
        holder.lnExpense.setLayoutParams(new LinearLayout.LayoutParams(widthExpense, height));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ReportExpenseMonthViewHolder extends RecyclerView.ViewHolder {
        TextView txtYear;
        TextView txtMonth;
        TextView txtMoneyExpense;
        LinearLayout lnExpense;

        public ReportExpenseMonthViewHolder(View itemView) {
            super(itemView);
            txtYear = (TextView) itemView.findViewById(R.id.txtYear);
            txtMonth = (TextView) itemView.findViewById(R.id.txtMonth);
            txtMoneyExpense = (TextView) itemView.findViewById(R.id.txtMoneyExpense);
            lnExpense = (LinearLayout) itemView.findViewById(R.id.lnExpense);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnClickListener.onClick(data.get(getAdapterPosition()).getMonth(),
                            data.get(getAdapterPosition()).getYear());
                }
            });
        }
    }

    public interface ReportExpenseMonthOnClickListener {
        void onClick(String month, String year);
    }

    ReportExpenseMonthAdapter.ReportExpenseMonthOnClickListener myOnClickListener;

    public void setMyOnClickListener(ReportExpenseMonthAdapter.ReportExpenseMonthOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }
}
