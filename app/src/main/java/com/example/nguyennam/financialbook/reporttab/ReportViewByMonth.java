package com.example.nguyennam.financialbook.reporttab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.adapters.ReportViewByMonthAdapter;
import com.example.nguyennam.financialbook.database.ExpenseDAO;
import com.example.nguyennam.financialbook.database.IncomeDAO;
import com.example.nguyennam.financialbook.model.Expense;
import com.example.nguyennam.financialbook.model.Income;
import com.example.nguyennam.financialbook.model.ReportMonth;
import com.example.nguyennam.financialbook.utils.CalculatorSupport;
import com.example.nguyennam.financialbook.utils.CalendarSupport;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReportViewByMonth extends Fragment implements ReportViewByMonthAdapter.ReportViewByMonthOnClickListener {

    Context context;
    ExpenseDAO expenseDAO;
    IncomeDAO incomeDAO;
    List<String> dateExpenseList;
    String[] mangId;
    double amountMoneyExpense = 0;
    double amountMoneyIncome = 0;
    RecyclerView recyclerView;
    List<ReportMonth> data;

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
        View v = inflater.inflate(R.layout.report_view_by_month, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerviewMonthReport);
        data = new ArrayList<>();
        getDateExpenseIncome();
        setDataForReport();
        setApdater();
        return v;
    }

    private void setApdater() {
        ReportViewByMonthAdapter myAdapter = new ReportViewByMonthAdapter(context, data);
        myAdapter.setMyOnClickListener(this);
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void evalAmountMoney(String date) {
        for (int j = 0; j < mangId.length; j++) {
            List<Expense> expenseList = expenseDAO.getExpenseByAccountID(Integer.parseInt(mangId[j]), date);
            for (Expense expense : expenseList) {
                amountMoneyExpense += Double.parseDouble(CalculatorSupport.formatExpression(expense.get_amountMoney()));
            }
            List<Income> incomeList = incomeDAO.getIncomeByAccountID(Integer.parseInt(mangId[j]), date);
            for (Income income : incomeList) {
                amountMoneyIncome += Double.parseDouble(CalculatorSupport.formatExpression(income.get_amountMoney()));
            }
        }
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

    private void setDataForReport() {
        // get id account from account name form
        String idAccount = FileHelper.readFile(context, Constant.TEMP_ID);
        mangId = idAccount.split(";");
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        for (int i = 0; i < dateExpenseList.size(); i++) {
            String month = CalendarSupport.getMonth(dateExpenseList.get(i));
            String year = CalendarSupport.getYear(dateExpenseList.get(i));
            if (i == dateExpenseList.size() - 1) {
                if (CalendarSupport.getMonthOfYear(dateExpenseList.get(i))
                        .equals(CalendarSupport.getMonthOfYear(dateExpenseList.get(i - 1)))) {
                    evalAmountMoney(dateExpenseList.get(i));
                    data.add(new ReportMonth(month, year, nf.format(amountMoneyIncome), nf.format(amountMoneyExpense)));
                } else {
                    amountMoneyExpense = 0;
                    amountMoneyIncome = 0;
                    evalAmountMoney(dateExpenseList.get(i));
                    data.add(new ReportMonth(month, year, nf.format(amountMoneyIncome), nf.format(amountMoneyExpense)));
                }
            } else if (CalendarSupport.getMonthOfYear(dateExpenseList.get(i))
                    .equals(CalendarSupport.getMonthOfYear(dateExpenseList.get(i + 1)))) {
                evalAmountMoney(dateExpenseList.get(i));
            } else {
                evalAmountMoney(dateExpenseList.get(i));
                data.add(new ReportMonth(month, year, nf.format(amountMoneyIncome), nf.format(amountMoneyExpense)));
                amountMoneyExpense = 0;
                amountMoneyIncome = 0;
            }
        }
    }

    @Override
    public void onClick() {

    }
}
