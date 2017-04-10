package com.example.nguyennam.financialbook.recordtab;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SearchView;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.adapters.FinancialHistoryAdapter;
import com.example.nguyennam.financialbook.database.ExpenseDAO;
import com.example.nguyennam.financialbook.model.FinancialHistoryChild;
import com.example.nguyennam.financialbook.model.FinancialHistoryGroup;
import com.example.nguyennam.financialbook.utils.CalculatorSupport;
import com.example.nguyennam.financialbook.utils.CalendarSupport;
import com.example.nguyennam.financialbook.utils.Constant;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FinancialHistory extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    Context context;
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
        myList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                TextView textView = (TextView) v.findViewById(R.id.groupname);
//                String groupname = (String) textView.getText();
//                Toast.makeText(getActivity().getApplicationContext(), "child clicked " + groupname , Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent();
//                intent.putExtra(Constant.KEY_CATEGORY, groupname);
//                setResult(RESULT_OK, intent);
//                finish();
                return false;
            }
        });
        myList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                TextView textView = (TextView) v.findViewById(R.id.childrow);
//                String childrow = (String) textView.getText();
//                Toast.makeText(getApplicationContext(), "child clicked " + childrow , Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent();
//                intent.putExtra(Constant.KEY_CATEGORY, childrow);
//                setResult(RESULT_OK, intent);
//                finish();
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
        //TODO
        String dateOfWeek;
        String dateOfMonth;
        String month;
        String moneyExpense;
        String moneyIncome = "20.000.000";
        FinancialHistoryGroup financialHistoryGroup;
        ExpenseDAO expenseDAO = new ExpenseDAO(context);
        Log.d(Constant.TAG, "loadHistoryData: " + expenseDAO.getAllExpense());
        List<String> dateExpenseList = expenseDAO.getDateExpense();
        for (String date: dateExpenseList) {
            dateOfWeek = CalendarSupport.getDateOfWeek(context, date);
            dateOfMonth = CalendarSupport.getDateOfMonth(date);
            month = CalendarSupport.getMonth(date);
            List<String> moneyExpenseList = expenseDAO.getMoneyByDate(date);
            double moneyNumber = 0; //money expense format to calculate and format to display
            for (String money : moneyExpenseList) {
                moneyNumber += Double.parseDouble(CalculatorSupport.formatExpression(money));
            }
            //format to 1.000.000
            NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
            moneyExpense = nf.format(moneyNumber);
            Log.d(Constant.TAG, "loadHistoryData: " + moneyExpense);
            financialHistoryGroup = new FinancialHistoryGroup(dateOfWeek, dateOfMonth, month, moneyExpense, moneyIncome, getChildList());
            financialGroupList.add(financialHistoryGroup);
        }
    }

    private ArrayList<FinancialHistoryChild> getChildList() {
        String description = "ab cd";
        String moneyAmount = "3.000.000";
        String account = "Ví";
        String category = "Ăn";
        ArrayList<FinancialHistoryChild> financialChildList = new ArrayList<>();
        FinancialHistoryChild financialChild = new FinancialHistoryChild(description, moneyAmount, account, category);
        financialChildList.add(financialChild);
        financialChild = new FinancialHistoryChild("ok ok", "20.000.000", "ATM", "Lương");
        financialChildList.add(financialChild);
        return financialChildList;
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
}