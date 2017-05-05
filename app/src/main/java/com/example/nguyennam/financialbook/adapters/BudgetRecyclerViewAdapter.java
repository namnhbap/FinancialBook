package com.example.nguyennam.financialbook.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.database.AccountRecyclerViewDAO;
import com.example.nguyennam.financialbook.model.BudgetRecyclerView;
import com.example.nguyennam.financialbook.utils.CalculatorSupport;
import com.example.nguyennam.financialbook.utils.CalendarSupport;
import com.example.nguyennam.financialbook.utils.Constant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BudgetRecyclerViewAdapter extends RecyclerView.Adapter<BudgetRecyclerViewAdapter.BudgetViewHolder> {

    Context context;
    List<BudgetRecyclerView> data;
    Date currentDate;
    Date startDate;
    Date endDate;
    Calendar myCalendar;

    public BudgetRecyclerViewAdapter(Context context, List<BudgetRecyclerView> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public BudgetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.budget_item_recyclerview, parent, false);
        return new BudgetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BudgetViewHolder holder, int position) {
        AccountRecyclerViewDAO accountDAO = new AccountRecyclerViewDAO(context);
        BudgetRecyclerView budget = data.get(position);
        holder.txtBudgetName.setText(budget.getBudgetName());
        holder.txtAmountMoney.setText(budget.getAmountMoney());
        holder.txtAccount.setText(accountDAO.getAccountById(budget.getAccountID()).getAccountName());
        holder.txtCategory.setText(budget.getCategory());
        holder.txtDate.setText(budget.getDate());
        holder.txtRemainMoney.setText(budget.getRemainMoney());
        holder.txtExpenseMoney.setText(budget.getExpenseMoney());
        // set width percent line
        final float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        String expensePercent = Double.toString((double) Math.round(
                Double.parseDouble(CalculatorSupport.formatExpression(budget.getExpenseMoney()))
                        / Double.parseDouble(CalculatorSupport.formatExpression(budget.getAmountMoney()))
                        * 100 * 10) / 10);
        // total width = 360dp
        int widthExpense = (int) (3 * Float.parseFloat(expensePercent) * scale + 0.5f);
        int height = (int) (10 * scale + 0.5f);
        LinearLayout.LayoutParams expenseLine = new LinearLayout.LayoutParams(widthExpense, height);
        expenseLine.setMargins((int) (scale + 0.5f),(int) (scale + 0.5f),0,(int) (scale + 0.5f));
        holder.lnTotalExpensedDetail.setLayoutParams(expenseLine);
        // set view date position
        long totalDates = countAmountDates(budget);
        long eachDayWidth = 300 / totalDates;
        currentDate = CalendarSupport.convertStringToDate(getDate());
        if (currentDate.before(startDate)) {
            holder.viewToday.setVisibility(View.GONE);
            holder.lnToday.setVisibility(View.GONE);
        } else if (currentDate.after(endDate)) {
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                    (int) (scale + 0.5f), (int) (33 * scale + 0.5f));
//            int marginLeft = (int) (300 * scale + 0.5f);
//            layoutParams.setMargins(marginLeft, 0, 0,(int) ((-18) * scale + 0.5f));
//            holder.viewToday.setLayoutParams(layoutParams);
//            LinearLayout.LayoutParams layoutLnToday = new LinearLayout.LayoutParams(
//                    (int) (36 * scale + 0.5f), (int) (36 * scale + 0.5f));
//            layoutLnToday.setMargins((int) (264 * scale + 0.5f), 0, 0, 0);
//            holder.lnToday.setLayoutParams(layoutLnToday);
            holder.viewToday.setVisibility(View.GONE);
            holder.lnToday.setVisibility(View.GONE);
        } else {
            long now = getAmountDates(startDate, currentDate);
            int nowPercent = (int) (now * eachDayWidth * scale + 0.5f);
            //set line vertical position
            LinearLayout.LayoutParams layoutViewToday = new LinearLayout.LayoutParams(
                    (int) (scale + 0.5f), (int) (33 * scale + 0.5f));
            layoutViewToday.setMargins(nowPercent, 0, 0,(int) ((-18) * scale + 0.5f));
            holder.viewToday.setLayoutParams(layoutViewToday);
            //set oval position
            LinearLayout.LayoutParams layoutLnToday = new LinearLayout.LayoutParams(
                    (int) (36 * scale + 0.5f), (int) (36 * scale + 0.5f));
            long leftLnToday;
            if (300 - now * eachDayWidth < 36) {
                leftLnToday = 264;
            } else {
                leftLnToday = now * eachDayWidth - 18;
            }
            int marginLeft = (int) (leftLnToday * scale + 0.5f);
            layoutLnToday.setMargins(marginLeft, 0, 0, 0);
            holder.lnToday.setLayoutParams(layoutLnToday);
            //set text in oval
            holder.txtToday.setText(CalendarSupport.getDateAndMonth(getDate()));
        }
    }

    String getDate() {
        myCalendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(myCalendar.getTime());
    }

    private long countAmountDates(BudgetRecyclerView budget) {
        // get date start and date end
        String viewByDate = budget.getDate();
        String [] dateArray = viewByDate.split("-");
        for (int i = 0; i < dateArray.length; i++) {
            dateArray[i] = dateArray[i].trim();
        }
        startDate = CalendarSupport.convertStringToDate(dateArray[0]);
        endDate = CalendarSupport.convertStringToDate(dateArray[1]);
        long msDiff = endDate.getTime() - startDate.getTime();
        return TimeUnit.MILLISECONDS.toDays(msDiff) + 1;
    }

    private long getAmountDates(Date start, Date end) {
        long msDiff =  end.getTime() - start.getTime();
        return TimeUnit.MILLISECONDS.toDays(msDiff) + 1;
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //TODO
    public class BudgetViewHolder extends RecyclerView.ViewHolder {
        TextView txtBudgetName;
        TextView txtAmountMoney;
        TextView txtAccount;
        TextView txtCategory;
        TextView txtDate;
        TextView txtRemainMoney;
        TextView txtExpenseMoney;
        TextView lnTotalExpensedDetail;
        TextView txtToday;
        View viewToday;
        LinearLayout lnToday;

        public BudgetViewHolder(View itemView) {
            super(itemView);
            txtBudgetName = (TextView) itemView.findViewById(R.id.txtBudgetName);
            txtAmountMoney = (TextView) itemView.findViewById(R.id.txtAmountMoney);
            txtAccount = (TextView) itemView.findViewById(R.id.txtAccount);
            txtCategory = (TextView) itemView.findViewById(R.id.txtCategory);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtRemainMoney = (TextView) itemView.findViewById(R.id.txtRemainMoney);
            txtExpenseMoney = (TextView) itemView.findViewById(R.id.txtExpenseMoney);
            lnTotalExpensedDetail = (TextView) itemView.findViewById(R.id.lnTotalExpensedDetail);
            viewToday = itemView.findViewById(R.id.viewToday);
            lnToday = (LinearLayout) itemView.findViewById(R.id.lnToday);
            txtToday = (TextView) itemView.findViewById(R.id.txtToday);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnClickListener.onClick(data.get(getAdapterPosition()).getId());
                }
            });
        }
    }

    public interface BudgetOnClickListener {
        void onClick(int position);
    }

    BudgetOnClickListener myOnClickListener;

    public void setMyOnClickListener(BudgetOnClickListener myOnClickListener) {
        this.myOnClickListener = myOnClickListener;
    }
}
