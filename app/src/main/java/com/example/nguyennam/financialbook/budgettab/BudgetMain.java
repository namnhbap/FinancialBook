package com.example.nguyennam.financialbook.budgettab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nguyennam.financialbook.MainActivity;
import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.adapters.BudgetRecyclerViewAdapter;
import com.example.nguyennam.financialbook.database.BudgetRecyclerViewDAO;
import com.example.nguyennam.financialbook.database.ExpenseDAO;
import com.example.nguyennam.financialbook.model.BudgetRecyclerView;
import com.example.nguyennam.financialbook.model.Expense;
import com.example.nguyennam.financialbook.utils.CalculatorSupport;
import com.example.nguyennam.financialbook.utils.CalendarSupport;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BudgetMain extends Fragment implements View.OnClickListener,
        BudgetRecyclerViewAdapter.BudgetOnClickListener {

    Context context;
    List<BudgetRecyclerView> budgetList;
    Date startDate;
    Date endDate;

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.budget_main, container, false);
        ImageView imgAddBudget = (ImageView) v.findViewById(R.id.imgAddBudget);
        imgAddBudget.setOnClickListener(this);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerviewBudget);
        updateBudgetMoney();
        BudgetRecyclerViewAdapter myAdapter = new BudgetRecyclerViewAdapter(context, budgetList);
        myAdapter.setMyOnClickListener(this);
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        return v;
    }

    private void getDateStartEnd(BudgetRecyclerView budget) {
        // get date start and date end from view by
        String viewByDate = budget.getDate();
        String[] dateArray = viewByDate.split("-");
        for (int i = 0; i < dateArray.length; i++) {
            dateArray[i] = dateArray[i].trim();
        }
        startDate = CalendarSupport.convertStringToDate(dateArray[0]);
        endDate = CalendarSupport.convertStringToDate(dateArray[1]);
    }

    private void updateBudgetMoney() {
        BudgetRecyclerViewDAO budgetDAO = new BudgetRecyclerViewDAO(context);
        budgetList = budgetDAO.getAllBudget();
        List<Expense> expenseList = new ExpenseDAO(context).getAllExpense();
        for (BudgetRecyclerView budget : budgetList) {
            getDateStartEnd(budget);
            double moneyExpenseNumber = 0;
            for (Expense expense : expenseList) {
                Date date = CalendarSupport.convertStringToDate(expense.get_date());
                if (expense.get_accountID() == budget.getAccountID() &&
                        !date.before(startDate) && !date.after(endDate)) {
                    if (expense.get_category().equals(budget.getCategory()) ||
                            expense.get_categoryChild().equals(budget.getCategory())) {
                        moneyExpenseNumber += Double.parseDouble(CalculatorSupport.
                                formatExpression(expense.get_amountMoney()));
                    }
                }
            }
            double moneyRemainNumber = Double.parseDouble(CalculatorSupport.formatExpression(
                    budget.getRemainMoney())) - moneyExpenseNumber;
            NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
            budget.setRemainMoney(nf.format(moneyRemainNumber));
            budget.setExpenseMoney(nf.format(moneyExpenseNumber));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgAddBudget:
                ((MainActivity) context).replaceFragment(new AddBudget(), true);
                break;
        }
    }

    @Override
    public void onClick(int position) {
        FileHelper.writeFile(context, Constant.TEMP_BUDGET_ID, "" + position);
        ((MainActivity) context).replaceFragment(new BudgetExpenseHistory(), true);
    }
}
