package com.example.nguyennam.financialbook.recordtab;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SearchView;

import com.example.nguyennam.financialbook.MainActivity;
import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.adapters.FinancialHistoryAdapter;
import com.example.nguyennam.financialbook.database.AccountRecyclerViewDAO;
import com.example.nguyennam.financialbook.database.ExpenseDAO;
import com.example.nguyennam.financialbook.database.IncomeDAO;
import com.example.nguyennam.financialbook.model.Expense;
import com.example.nguyennam.financialbook.model.FinancialHistoryChild;
import com.example.nguyennam.financialbook.model.FinancialHistoryGroup;
import com.example.nguyennam.financialbook.model.Income;
import com.example.nguyennam.financialbook.utils.CalculatorSupport;
import com.example.nguyennam.financialbook.utils.CalendarSupport;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FinancialHistory extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener, View.OnClickListener {

    Context context;
    ImageView txtBack;
    private FinancialHistoryAdapter listAdapter;
    private ExpandableListView myList;
    private ArrayList<FinancialHistoryGroup> financialGroupList = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.financial_history, container, false);
        txtBack = (ImageView) view.findViewById(R.id.txtBack);
        txtBack.setOnClickListener(this);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView search = (SearchView) view.findViewById(R.id.searchHistory);
        search.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(this);
        search.setOnCloseListener(this);
        //display the list, expand all groups
        loadHistoryData();
        //get reference to the ExpandableListView
        myList = (ExpandableListView) view.findViewById(R.id.expandableHistory);
        //create the adapter by passing your ArrayList data
        listAdapter = new FinancialHistoryAdapter(context, financialGroupList);
        //attach the adapter to the list
        myList.setAdapter(listAdapter);
        //expand all Groups
        expandAll();
        myList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                FinancialHistoryChild financialChild = financialGroupList.get(groupPosition)
                        .getFinancialHistoryChildList().get(childPosition);
                FileHelper.writeFile(context, Constant.TEMP_ISEXPENSE, String.valueOf(financialChild.isExpense()));
                if (financialChild.isExpense()) {
                    FileHelper.writeFile(context, Constant.TEMP_EXPENSE_ID, String.valueOf(financialChild.getId()));
                } else {
                    FileHelper.writeFile(context, Constant.TEMP_INCOME_ID, String.valueOf(financialChild.getId()));
                }
                ((MainActivity) context).replaceFragment(new FinancialHistoryDetail(), true);
                return false;
            }
        });
        return view;
    }

    //method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            myList.expandGroup(i);
        }
    }

    private void loadHistoryData() {
        String dateOfWeek;
        String dateOfMonth;
        String month;
        String moneyExpense;
        String moneyIncome;
        FinancialHistoryGroup financialHistoryGroup;
        ExpenseDAO expenseDAO = new ExpenseDAO(context);
        IncomeDAO incomeDAO = new IncomeDAO(context);
        // get date from income and expense
        List<String> dateExpenseList = expenseDAO.getDateExpense();
        List<String> dateIncomeList = incomeDAO.getDateIncome();
        // sort date from now to past and avoid duplicate date
        CalendarSupport.sortDateList(dateExpenseList, dateIncomeList);
        for (String date : dateExpenseList) {
            dateOfWeek = CalendarSupport.getDateOfWeek(context, date);
            dateOfMonth = CalendarSupport.getDateOfMonth(date);
            month = CalendarSupport.getMonthOfYear(date);
            List<String> moneyExpenseList = expenseDAO.getMoneyByDate(date);
            moneyExpense = getMoneyOneDate(moneyExpenseList);
            List<String> moneyIncomeList = incomeDAO.getMoneyByDate(date);
            moneyIncome = getMoneyOneDate(moneyIncomeList);
            financialHistoryGroup = new FinancialHistoryGroup(dateOfWeek, dateOfMonth, month, moneyExpense, moneyIncome, getChildList(date));
            financialGroupList.add(financialHistoryGroup);
        }
    }

    private ArrayList<FinancialHistoryChild> getChildList(String date) {
        //TODO
        String description;
        String moneyAmount;
        String account;
        String category;
        FinancialHistoryChild financialChild;
        ArrayList<FinancialHistoryChild> financialChildList = new ArrayList<>();
        List<Income> incomeList;
        IncomeDAO incomeDAO = new IncomeDAO(context);
        incomeList = incomeDAO.getIncomeByDate(date);
        for (Income income : incomeList) {
            moneyAmount = income.get_amountMoney();
            AccountRecyclerViewDAO accountDAO = new AccountRecyclerViewDAO(context);
            account = accountDAO.getAccountById(income.get_accountID()).getAccountName();
            category = income.get_category();
            description = income.get_description();
            financialChild = new FinancialHistoryChild(false, moneyAmount, account, category, description, income.get_id());
            financialChildList.add(financialChild);
        }
        List<Expense> expenseList;
        ExpenseDAO expenseDAO = new ExpenseDAO(context);
        expenseList = expenseDAO.getExpenseByDate(date);
        for (Expense expense : expenseList) {
            moneyAmount = expense.get_amountMoney();
            AccountRecyclerViewDAO accountDAO = new AccountRecyclerViewDAO(context);
            account = accountDAO.getAccountById(expense.get_accountID()).getAccountName();
            if ("".equals(expense.get_categoryChild())) {
                category = expense.get_category();
            } else {
                category = expense.get_categoryChild();
            }
            description = expense.get_description();
            financialChild = new FinancialHistoryChild(true, moneyAmount, account, category, description, expense.get_id());
            financialChildList.add(financialChild);
        }
        return financialChildList;
    }

    public String getMoneyOneDate(List<String> moneyExpenseList) {
        String moneyOnedate;
        double moneyNumber = 0; //money expense format to calculate and format to display
        for (String money : moneyExpenseList) {
            moneyNumber += Double.parseDouble(CalculatorSupport.formatExpression(money));
        }
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        moneyOnedate = nf.format(moneyNumber);
        return moneyOnedate;
    }

    @Override
    public boolean onClose() {
        listAdapter.filterData("");
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        listAdapter.filterData(query);
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        listAdapter.filterData(query);
        expandAll();
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtBack:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }
}