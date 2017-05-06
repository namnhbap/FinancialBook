package com.example.nguyennam.financialbook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.model.ReportYear;
import com.example.nguyennam.financialbook.utils.CalculatorSupport;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

import java.util.List;

public class ReportExpenseYearAdapter extends RecyclerView.Adapter<ReportExpenseYearAdapter.ReportExpenseYearViewHolder> {

    Context context;
    List<ReportYear> data;

    public ReportExpenseYearAdapter(Context context, List<ReportYear> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ReportExpenseYearAdapter.ReportExpenseYearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_expense_year_item, parent, false);
        return new ReportExpenseYearAdapter.ReportExpenseYearViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReportExpenseYearAdapter.ReportExpenseYearViewHolder holder, int position) {
        ReportYear reportYear = data.get(position);
        holder.txtYear.setText(reportYear.getYear());
        holder.txtMoneyExpense.setText(reportYear.getMoneyExpense());
        // convert dp to pxl and calculate percent to display the width of line
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        double sum = Double.parseDouble(FileHelper.readFile(context, Constant.TEMP_MAX));

        String expensePercent = Double.toString((double) Math.round(
                Double.parseDouble(CalculatorSupport.formatExpression(reportYear.getMoneyExpense()))
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

    public class ReportExpenseYearViewHolder extends RecyclerView.ViewHolder {
        TextView txtYear;
        TextView txtMoneyExpense;
        LinearLayout lnExpense;

        public ReportExpenseYearViewHolder(View itemView) {
            super(itemView);
            txtYear = (TextView) itemView.findViewById(R.id.txtYear);
            txtMoneyExpense = (TextView) itemView.findViewById(R.id.txtMoneyExpense);
            lnExpense = (LinearLayout) itemView.findViewById(R.id.lnExpense);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnClickListener.onClick();
                }
            });
        }
    }

    public interface ReportExpenseYearOnClickListener {
        void onClick();
    }

    ReportExpenseYearAdapter.ReportExpenseYearOnClickListener myOnClickListener;

    public void setMyOnClickListener(ReportExpenseYearAdapter.ReportExpenseYearOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }
}
