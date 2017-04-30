package com.example.nguyennam.financialbook.reporttab;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.database.AccountRecyclerViewDAO;
import com.example.nguyennam.financialbook.database.ExpenseDAO;
import com.example.nguyennam.financialbook.database.IncomeDAO;
import com.example.nguyennam.financialbook.model.AccountRecyclerView;
import com.example.nguyennam.financialbook.model.CategoryGroup;
import com.example.nguyennam.financialbook.model.Expense;
import com.example.nguyennam.financialbook.model.Income;
import com.example.nguyennam.financialbook.utils.CalculatorSupport;
import com.example.nguyennam.financialbook.utils.CalendarSupport;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportPeriodTime extends Fragment implements OnChartValueSelectedListener {

    Context context;
    PieChart mChart;
    LinearLayout lnIncome;
    LinearLayout lnExpense;
    TextView txtExpenseMoney;
    TextView txtIncomeMoney;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.report_period_time, container, false);
        lnIncome = (LinearLayout) v.findViewById(R.id.lnIncome);
        lnExpense = (LinearLayout) v.findViewById(R.id.lnExpense);
        txtExpenseMoney = (TextView) v.findViewById(R.id.txtExpenseMoney);
        txtIncomeMoney = (TextView) v.findViewById(R.id.txtIncomeMoney);
        mChart = (PieChart) v.findViewById(R.id.pieChart);
        setPieChart();
        return v;
    }

    private void setPieChart() {
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);
        mChart.setHoleRadius(60f);
        mChart.setTransparentCircleRadius(63f);
        mChart.setDrawCenterText(true);
        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);
        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);
        setData();
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        setLegend();
        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTextSize(12f);
    }

    private void setLegend() {
        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setTextSize(12f);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
    }

    private void setData() {
        // get date start and date end from view by
        String viewByDate = FileHelper.readFile(context, Constant.TEMP_VIEW_BY);
        String [] dateArray = viewByDate.split("-");
        for (int i = 0; i < dateArray.length; i++) {
            dateArray[i] = dateArray[i].trim();
        }
        Date startDate = CalendarSupport.convertStringToDate(dateArray[0]);
        Date endDate = CalendarSupport.convertStringToDate(dateArray[1]);
        // get id account from account name form
        AccountRecyclerViewDAO accountDAO = new AccountRecyclerViewDAO(context);
        AccountRecyclerView accountRecyclerView;
        String idAccount = FileHelper.readFile(context, Constant.TEMP_ID);
        String[] mangId = idAccount.split(";");
        // get date from income and expense
        ExpenseDAO expenseDAO = new ExpenseDAO(context);
        IncomeDAO incomeDAO = new IncomeDAO(context);
        List<String> dateExpenseList = expenseDAO.getDateExpense();
        List<String> dateIncomeList = incomeDAO.getDateIncome();
        // sort date from now to past and avoid duplicate date
        CalendarSupport.sortDateList(dateExpenseList, dateIncomeList);


        List<String> myGroupList = Arrays.asList(getResources().getStringArray(R.array.group_row_category));
        List<CategoryGroup> categoryGroupList = new ArrayList<>();
        for (int i = 0; i < myGroupList.size(); i++) {
            categoryGroupList.add(new CategoryGroup(myGroupList.get(i), "0"));
        }
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        double amountMoneyExpense = 0;
        double amountMoneyIncome = 0;
        for (String dateExpense : dateExpenseList) {
            Date date = CalendarSupport.convertStringToDate(dateExpense);
            for (int i = 0; i < mangId.length; i++) {
                if (!date.before(startDate) && !date.after(endDate)) {
                    List<Expense> expenseList = expenseDAO.getExpenseByAccountID(Integer.parseInt(mangId[i]), dateExpense);
                    for (Expense expense : expenseList) {
                        for (CategoryGroup categoryGroup : categoryGroupList) {
                            if (categoryGroup.getName().equals(expense.get_category())) {
                                double temp = Double.parseDouble(CalculatorSupport.formatExpression(categoryGroup.getMoney()))
                                        + Double.parseDouble(CalculatorSupport.formatExpression(expense.get_amountMoney()));
                                categoryGroup.setMoney(nf.format(temp));
                            }
                        }
                        amountMoneyExpense += Double.parseDouble(CalculatorSupport.formatExpression(expense.get_amountMoney()));
                    }
                    List<Income> incomeList = incomeDAO.getIncomeByAccountID(Integer.parseInt(mangId[i]), dateExpense);
                    for (Income income : incomeList) {
                        amountMoneyIncome += Double.parseDouble(CalculatorSupport.formatExpression(income.get_amountMoney()));
                    }
                }
            }
        }
        if (amountMoneyExpense == 0) {
            lnExpense.setVisibility(View.GONE);
        } else {
            txtExpenseMoney.setText(nf.format(amountMoneyExpense));
        }
        if (amountMoneyIncome == 0) {
            lnIncome.setVisibility(View.GONE);
        } else {
            txtIncomeMoney.setText(nf.format(amountMoneyIncome));
        }
        // remove object money = 0
        for (int i = categoryGroupList.size() - 1; i >= 0 ; i--) {
            if ("0".equals(categoryGroupList.get(i).getMoney())) {
                categoryGroupList.remove(i);
            }
        }

        ArrayList<PieEntry> entries = new ArrayList<>();
        for (CategoryGroup categoryGroup : categoryGroupList) {
            entries.add(new PieEntry(Float.parseFloat(categoryGroup.getMoney()), categoryGroup.getName()));
        }

        mChart.setDrawEntryLabels(false); //dont show xdata in chart
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);
        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
//        data.setValueTypeface(mTfLight);
        mChart.setData(data);
        // undo all highlights
        mChart.highlightValues(null);
        mChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

}
