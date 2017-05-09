package com.example.nguyennam.financialbook.reporttab;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyennam.financialbook.MainActivity;
import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.adapters.ReportCategoryIncomeAdapter;
import com.example.nguyennam.financialbook.database.ExpenseDAO;
import com.example.nguyennam.financialbook.database.IncomeDAO;
import com.example.nguyennam.financialbook.model.CategoryGroup;
import com.example.nguyennam.financialbook.model.CategoryIncome;
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

public class ReportPeriodTime extends Fragment implements OnChartValueSelectedListener, ReportCategoryIncomeAdapter.ReportIncomeOnClickListener {

    Context context;
    PieChart mChart;
    LinearLayout lnIncome;
    LinearLayout lnExpense;
    TextView txtExpenseMoney;
    TextView txtIncomeMoney;
    Date startDate;
    Date endDate;
    String[] mangId;
    List<String> dateExpenseList;
    List<CategoryGroup> categoryGroupList;
    List<CategoryIncome> categoryIncomeList;
    ExpenseDAO expenseDAO;
    IncomeDAO incomeDAO;

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
        getDataReport();
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerviewIncomeReport);
        ReportCategoryIncomeAdapter myAdapter = new ReportCategoryIncomeAdapter(context, categoryIncomeList);
        myAdapter.setMyOnClickListener(this);
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
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
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (CategoryGroup categoryGroup : categoryGroupList) {
            entries.add(new PieEntry(Float.parseFloat(CalculatorSupport.formatExpression(categoryGroup.getMoney())), categoryGroup.getName()));
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
        data.setValueTextColor(Color.BLACK);
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
                "Value: " + e.getY() + ", index: " + categoryGroupList.get((int) h.getX()).getName()
                        + ", DataSet index: " + h.getDataSetIndex());
        FileHelper.writeFile(context, Constant.TEMP_CATEGORY, categoryGroupList.get((int) h.getX()).getName());
        ((MainActivity) context).replaceFragment(new ReportPeriodTimeExpenseDetail(), true);

    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

    private void getDateStartEnd() {
        // get date start and date end from view by
        String viewByDate = FileHelper.readFile(context, Constant.TEMP_VIEW_BY);
        String[] dateArray = viewByDate.split("-");
        for (int i = 0; i < dateArray.length; i++) {
            dateArray[i] = dateArray[i].trim();
        }
        startDate = CalendarSupport.convertStringToDate(dateArray[0]);
        endDate = CalendarSupport.convertStringToDate(dateArray[1]);
    }

    private void getDateExpenseIncome() {
        // get date from income and expense
        expenseDAO = new ExpenseDAO(context);
        incomeDAO = new IncomeDAO(context);
        dateExpenseList = expenseDAO.getDateExpense();
        List<String> dateIncomeList = incomeDAO.getDateIncome();
        // sort date from now to past and avoid duplicate date
        CalendarSupport.sortDateList(dateExpenseList, dateIncomeList);
    }

    private void addCategoryGroupList() {
        List<String> myGroupList = Arrays.asList(getResources().getStringArray(R.array.group_row_category));
        categoryGroupList = new ArrayList<>();
        for (int i = 0; i < myGroupList.size(); i++) {
            categoryGroupList.add(new CategoryGroup(myGroupList.get(i), "0"));
        }
    }

    private void addCategoryIncomeList() {
        List<String> myGroupList = Arrays.asList(getResources().getStringArray(R.array.income_category));
        categoryIncomeList = new ArrayList<>();
        for (int i = 0; i < myGroupList.size(); i++) {
            categoryIncomeList.add(new CategoryIncome(myGroupList.get(i), "0", "0"));
        }
    }

    private void getDataReport() {
        getDateStartEnd();
        getDateExpenseIncome();
        addCategoryGroupList();
        addCategoryIncomeList();
        // get id account from account name form
        String idAccount = FileHelper.readFile(context, Constant.TEMP_ID);
        mangId = idAccount.split(";");
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
                        for (CategoryIncome categoryIncome : categoryIncomeList) {
                            if (categoryIncome.getName().equals(income.get_category())) {
                                double temp = Double.parseDouble(CalculatorSupport.formatExpression(categoryIncome.getMoney()))
                                        + Double.parseDouble(CalculatorSupport.formatExpression(income.get_amountMoney()));
                                categoryIncome.setMoney(nf.format(temp));
                            }
                        }
                        amountMoneyIncome += Double.parseDouble(CalculatorSupport.formatExpression(income.get_amountMoney()));
                    }
                }
            }
        }
        // remove view or not
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
        for (int i = categoryGroupList.size() - 1; i >= 0; i--) {
            if ("0".equals(categoryGroupList.get(i).getMoney())) {
                categoryGroupList.remove(i);
            }
        }
        for (int i = categoryIncomeList.size() - 1; i >= 0; i--) {
            if ("0".equals(categoryIncomeList.get(i).getMoney())) {
                categoryIncomeList.remove(i);
            }
        }
        // calculate percent
        for (int i = categoryIncomeList.size() - 1; i >= 0; i--) {
            String temp = Double.toString((double) Math.round(
                    Double.parseDouble(CalculatorSupport.formatExpression(categoryIncomeList.get(i).getMoney()))
                            / amountMoneyIncome * 100
                            * 10) / 10);
            categoryIncomeList.get(i).setPercent(temp);
        }
        findMaxIncome();
    }

    private void findMaxIncome() {
        double maxIncome = 0;
        for (int i = categoryIncomeList.size() - 1; i >= 0; i--) {
            if (Double.parseDouble(CalculatorSupport.
                    formatExpression(categoryIncomeList.get(i).getMoney())) > maxIncome) {
                maxIncome = Double.parseDouble(CalculatorSupport.
                        formatExpression(categoryIncomeList.get(i).getMoney()));
            }
        }
        FileHelper.writeFile(context, Constant.TEMP_MAX, "" + maxIncome);
    }

    @Override
    public void onClick(String name) {
        FileHelper.writeFile(context, Constant.TEMP_CATEGORY, name);
        ((MainActivity) context).replaceFragment(new ReportPeriodTimeIncomeDetail(), true);
    }
}
