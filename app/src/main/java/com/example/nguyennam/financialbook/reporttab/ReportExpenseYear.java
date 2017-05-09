package com.example.nguyennam.financialbook.reporttab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nguyennam.financialbook.MainActivity;
import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.adapters.ReportExpenseYearAdapter;
import com.example.nguyennam.financialbook.database.ExpenseDAO;
import com.example.nguyennam.financialbook.database.IncomeDAO;
import com.example.nguyennam.financialbook.model.Expense;
import com.example.nguyennam.financialbook.model.ReportYear;
import com.example.nguyennam.financialbook.utils.CalculatorSupport;
import com.example.nguyennam.financialbook.utils.CalendarSupport;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReportExpenseYear extends Fragment implements ReportExpenseYearAdapter.ReportExpenseYearOnClickListener {

    Context context;
    ExpenseDAO expenseDAO;
    IncomeDAO incomeDAO;
    List<String> dateExpenseList;
    String[] mangId;
    double amountMoneyExpense = 0;
    RecyclerView recyclerView;
    List<ReportYear> data;

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
        View v = inflater.inflate(R.layout.report_view_by, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerviewReport);
        data = new ArrayList<>();
        data.clear();
        getDateExpenseIncome();
        setDataForReport();
        setAdapter();
        return v;
    }

    private void setAdapter() {
        ReportExpenseYearAdapter myAdapter = new ReportExpenseYearAdapter(context, data);
        myAdapter.setMyOnClickListener(this);
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void evalAmountMoney(String date) {
        for (int j = 0; j < mangId.length; j++) {
            List<Expense> expenseList = expenseDAO.getExpenseByAccountID(Integer.parseInt(mangId[j]), date);
            for (Expense expense : expenseList) {
                if ("".equals(FileHelper.readFile(context, Constant.TEMP_CATEGORY_CHILD))) {
                    if (FileHelper.readFile(context, Constant.TEMP_CATEGORY).equals(expense.get_category())) {
                        amountMoneyExpense += Double.parseDouble(CalculatorSupport.formatExpression(expense.get_amountMoney()));
                    }
                } else {
                    if (FileHelper.readFile(context, Constant.TEMP_CATEGORY_CHILD).equals(expense.get_categoryChild())) {
                        amountMoneyExpense += Double.parseDouble(CalculatorSupport.formatExpression(expense.get_amountMoney()));
                    }
                }
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
            String year = CalendarSupport.getYear(dateExpenseList.get(i));
            if (i == dateExpenseList.size() - 1) {
                if (dateExpenseList.size() == 1) {
                    evalAmountMoney(dateExpenseList.get(i));
                    data.add(new ReportYear(year, nf.format(amountMoneyExpense)));
                } else if (year.equals(CalendarSupport.getYear(dateExpenseList.get(i - 1)))) {
                    evalAmountMoney(dateExpenseList.get(i));
                    data.add(new ReportYear(year, nf.format(amountMoneyExpense)));
                } else {
                    amountMoneyExpense = 0;
                    evalAmountMoney(dateExpenseList.get(i));
                    data.add(new ReportYear(year, nf.format(amountMoneyExpense)));
                }
            } else if (year.equals(CalendarSupport.getYear(dateExpenseList.get(i + 1)))) {
                evalAmountMoney(dateExpenseList.get(i));
            } else {
                evalAmountMoney(dateExpenseList.get(i));
                data.add(new ReportYear(year, nf.format(amountMoneyExpense)));
                amountMoneyExpense = 0;
            }
        }
        findMaxExpense();
    }

    private void findMaxExpense() {
        double maxExpense = 0;
        for (int i = data.size() - 1; i >= 0 ; i--) {
            if ("0".equals(data.get(i).getMoneyExpense())) {
                data.remove(i);
            } else {
                if (Double.parseDouble(CalculatorSupport.
                        formatExpression(data.get(i).getMoneyExpense())) > maxExpense) {
                    maxExpense = Double.parseDouble(CalculatorSupport.
                            formatExpression(data.get(i).getMoneyExpense()));
                }
            }
        }
        FileHelper.writeFile(context, Constant.TEMP_MAX, "" + maxExpense);
    }

    @Override
    public void onClick(String year) {
        FileHelper.writeFile(context, Constant.TEMP_BUDGET_DATE, year);
        ((MainActivity) context).replaceFragment(new ReportExpenseYearDetail(), true);
    }
}
