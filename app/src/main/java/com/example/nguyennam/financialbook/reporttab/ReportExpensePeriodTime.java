package com.example.nguyennam.financialbook.reporttab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

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
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportExpensePeriodTime extends Fragment {

    Context context;
    ExpenseDAO expenseDAO;
    List<String> dateExpenseList;
    String[] mangId;
    Date startDate;
    Date endDate;
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
        View view = inflater.inflate(R.layout.report_expense_period_time, container, false);
        //clear old list, display the list
        financialGroupList.clear();
        loadHistoryData();
        //get reference to the ExpandableListView
        myList = (ExpandableListView) view.findViewById(R.id.expandableHistory);
        //create the adapter by passing your ArrayList data
        listAdapter = new FinancialHistoryAdapter(context, financialGroupList);
        //attach the adapter to the list
        myList.setAdapter(listAdapter);
        //expand all Groups
        expandAll();
        return view;
    }

    //method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            myList.expandGroup(i);
        }
    }

    private void getDateExpense() {
        // get date from income and expense
        expenseDAO = new ExpenseDAO(context);
        dateExpenseList = expenseDAO.getDateExpense();
        // sort date from now to past and avoid duplicate date
        CalendarSupport.sortDateList(dateExpenseList);
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

    private void loadHistoryData() {
        String dateOfWeek;
        String dateOfMonth;
        String month;
        String moneyExpense;
        double amountMoneyExpense = 0;
        FinancialHistoryGroup financialHistoryGroup;
        // get id account from account name form
        String idAccount = FileHelper.readFile(context, Constant.TEMP_ID);
        mangId = idAccount.split(";");
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        getDateStartEnd();
        getDateExpense();
        for (String dateExpense : dateExpenseList) {
            Date date = CalendarSupport.convertStringToDate(dateExpense);
            if (!date.before(startDate) && !date.after(endDate)) {
                dateOfWeek = CalendarSupport.getDateOfWeek(context, dateExpense);
                dateOfMonth = CalendarSupport.getDateOfMonth(dateExpense);
                month = CalendarSupport.getMonthOfYear(dateExpense);
                for (int i = 0; i < mangId.length; i++) {
                    List<Expense> expenseList = expenseDAO.getExpenseByAccountID(Integer.parseInt(mangId[i]), dateExpense);
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
                if (!(amountMoneyExpense == 0)) {
                    moneyExpense = nf.format(amountMoneyExpense);
                    financialHistoryGroup = new FinancialHistoryGroup(dateOfWeek, dateOfMonth, month, moneyExpense, "0", getChildList(mangId, dateExpense));
                    financialGroupList.add(financialHistoryGroup);
                    amountMoneyExpense = 0;
                }
            }
        }
    }

    private ArrayList<FinancialHistoryChild> getChildList(String[] mangId, String dateExpense) {
        //TODO
        String account;
        String category;
        FinancialHistoryChild financialChild;
        ArrayList<FinancialHistoryChild> financialChildList = new ArrayList<>();
        List<Expense> expenseList;
        ExpenseDAO expenseDAO = new ExpenseDAO(context);
        for (int i = 0; i < mangId.length; i++) {
            expenseList = expenseDAO.getExpenseByAccountID(Integer.parseInt(mangId[i]), dateExpense);
            for (Expense expense : expenseList) {
                if ("".equals(FileHelper.readFile(context, Constant.TEMP_CATEGORY_CHILD))) {
                    if (FileHelper.readFile(context, Constant.TEMP_CATEGORY).equals(expense.get_category())) {
                        AccountRecyclerViewDAO accountDAO = new AccountRecyclerViewDAO(context);
                        account = accountDAO.getAccountById(expense.get_accountID()).getAccountName();
                        financialChild = new FinancialHistoryChild(true, expense.get_amountMoney(),
                                account, expense.get_category(), expense.get_description(), expense.get_id());
                        financialChildList.add(financialChild);
                    }
                } else {
                    if (FileHelper.readFile(context, Constant.TEMP_CATEGORY_CHILD).equals(expense.get_categoryChild())) {
                        AccountRecyclerViewDAO accountDAO = new AccountRecyclerViewDAO(context);
                        account = accountDAO.getAccountById(expense.get_accountID()).getAccountName();
                        financialChild = new FinancialHistoryChild(true, expense.get_amountMoney(),
                                account, expense.get_categoryChild(), expense.get_description(), expense.get_id());
                        financialChildList.add(financialChild);
                    }
                }
            }
        }
        return financialChildList;
    }
}