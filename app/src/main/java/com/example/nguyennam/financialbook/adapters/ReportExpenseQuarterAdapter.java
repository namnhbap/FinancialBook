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
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

import java.util.List;

public class ReportExpenseQuarterAdapter extends RecyclerView.Adapter<ReportExpenseQuarterAdapter.ReportExpenseQuarterViewHolder> {

    Context context;
    List<ReportQuarter> data;

    public ReportExpenseQuarterAdapter(Context context, List<ReportQuarter> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ReportExpenseQuarterAdapter.ReportExpenseQuarterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_expense_quarter_item, parent, false);
        return new ReportExpenseQuarterAdapter.ReportExpenseQuarterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReportExpenseQuarterAdapter.ReportExpenseQuarterViewHolder holder, int position) {
        ReportQuarter reportQuarter = data.get(position);
        holder.txtQuarter.setText(reportQuarter.getQuarter());
        holder.txtYear.setText(reportQuarter.getYear());
        holder.txtMoneyExpense.setText(reportQuarter.getMoneyExpense());
        // convert dp to pxl and calculate percent to display the width of line
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        double sum = Double.parseDouble(FileHelper.readFile(context, Constant.TEMP_MAX));

        String expensePercent = Double.toString((double) Math.round(
                Double.parseDouble(CalculatorSupport.formatExpression(reportQuarter.getMoneyExpense()))
                        / sum * 100
                        * 10) / 10);
        // total width = 300dp
        int widthExpense = (int) (3 * Float.parseFloat(expensePercent) * scale + 0.5f);
        int height = (int) (10 * scale + 0.5f);
        holder.lnExpense.setLayoutParams(new LinearLayout.LayoutParams(widthExpense, height));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ReportExpenseQuarterViewHolder extends RecyclerView.ViewHolder {
        TextView txtYear;
        TextView txtQuarter;
        TextView txtMoneyExpense;
        LinearLayout lnExpense;

        public ReportExpenseQuarterViewHolder(View itemView) {
            super(itemView);
            txtYear = (TextView) itemView.findViewById(R.id.txtYear);
            txtQuarter = (TextView) itemView.findViewById(R.id.txtQuarter);
            txtMoneyExpense = (TextView) itemView.findViewById(R.id.txtMoneyExpense);
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

    public interface ReportExpenseQuarterOnClickListener {
        void onClick(String quarter, String year);
    }

    ReportExpenseQuarterAdapter.ReportExpenseQuarterOnClickListener myOnClickListener;

    public void setMyOnClickListener(ReportExpenseQuarterAdapter.ReportExpenseQuarterOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }
}
