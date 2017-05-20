package com.example.nguyennam.financialbook.accounttab;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyennam.financialbook.MainActivity;
import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.adapters.AccountFinancialAdapter;
import com.example.nguyennam.financialbook.database.AccountRecyclerViewDAO;
import com.example.nguyennam.financialbook.database.ExpenseDAO;
import com.example.nguyennam.financialbook.database.IncomeDAO;
import com.example.nguyennam.financialbook.model.AccountRecyclerView;
import com.example.nguyennam.financialbook.model.Expense;
import com.example.nguyennam.financialbook.model.FinancialRecyclerView;
import com.example.nguyennam.financialbook.model.Income;
import com.example.nguyennam.financialbook.recordtab.FinancialHistoryDetail;
import com.example.nguyennam.financialbook.utils.CalculatorSupport;
import com.example.nguyennam.financialbook.utils.CalendarSupport;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AccountFinancialHistory extends Fragment implements AccountFinancialAdapter.FinancialOnClickListener, View.OnClickListener {

    List<FinancialRecyclerView> data = new ArrayList<>();
    Context context;
    AccountRecyclerViewDAO accountDAO;
    AccountRecyclerView account;

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
        View v = inflater.inflate(R.layout.account_financial_history, container, false);
        ImageView btnBack = (ImageView) v.findViewById(R.id.txtBack);
        btnBack.setOnClickListener(this);
        accountDAO = new AccountRecyclerViewDAO(context);
        account = accountDAO.getAccountById(Integer.parseInt(FileHelper.readFile(context, Constant.TEMP_ID)));
        TextView txtAccountName = (TextView) v.findViewById(R.id.txtAccountName);
        txtAccountName.setText(account.getAccountName());
        TextView txtMoneyStart = (TextView) v.findViewById(R.id.txtMoneyStart);
        txtMoneyStart.setText(account.getMoneyStart());
        TextView txtAmountMoney = (TextView) v.findViewById(R.id.txtAmountMoney);
        txtAmountMoney.setText(account.getAmountMoney());
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        // clear old data and Calculate history amount money of account
        data.clear();
        getDataFinancialAccount();
        AccountFinancialAdapter myAdapter = new AccountFinancialAdapter(context, data);
        myAdapter.setMyOnClickListener(this);
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        return v;
    }

    private void getDataFinancialAccount() {
        // hiện tại 3 tr, trước đó tiêu 200k -> cộng thêm 200k để remain = 3tr2
        // hiện tại 3tr, trước đó thu 200k -> trừ đi 200k để remain = 2tr8
        String remainMoney = account.getAmountMoney();
        String tempAmountMoney = "";
        ExpenseDAO expenseDAO = new ExpenseDAO(context);
        IncomeDAO incomeDAO = new IncomeDAO(context);
        // get date from income and expense
        List<String> dateExpenseList = expenseDAO.getDateExpenseByAccountID(account.getId());
        List<String> dateIncomeList = incomeDAO.getDateIncomeByAccountID(account.getId());
        // sort date from now to past and avoid duplicate date
        CalendarSupport.sortDateList(dateExpenseList, dateIncomeList);
        boolean isFirstRow = true;
        boolean isIncome = true;
        for (String date : dateExpenseList) {
            List<Income> incomeList;
            incomeList = incomeDAO.getIncomeByAccountID(account.getId(), date);
            for (Income income : incomeList) {
                if (isFirstRow) {
                    data.add(new FinancialRecyclerView(income.get_id(),
                            context.getResources().getString(R.string.tvIncome), income.get_category(),
                            income.get_date(), income.get_amountMoney(), remainMoney));
                    tempAmountMoney = income.get_amountMoney();
                    isFirstRow = false;
                    isIncome = true;
                } else if (isIncome){
                    //calculate remain money
                    remainMoney = calculateRemainMoneyIncome(tempAmountMoney, remainMoney);
                    data.add(new FinancialRecyclerView(income.get_id(),
                            context.getResources().getString(R.string.tvIncome), income.get_category(),
                            income.get_date(), income.get_amountMoney(), remainMoney));
                    tempAmountMoney = income.get_amountMoney();
                    isIncome = true;
                } else {
                    remainMoney = calculateRemainMoneyExpense(tempAmountMoney, remainMoney);
                    data.add(new FinancialRecyclerView(income.get_id(),
                            context.getResources().getString(R.string.tvIncome), income.get_category(),
                            income.get_date(), income.get_amountMoney(), remainMoney));
                    tempAmountMoney = income.get_amountMoney();
                    isIncome = true;
                }
            }
            List<Expense> expenseList;
            expenseList = expenseDAO.getExpenseByAccountID(account.getId(), date);
            for (Expense expense : expenseList) {
                if (isFirstRow) {
                    if ("".equals(expense.get_categoryChild())) {
                        data.add(new FinancialRecyclerView(expense.get_id(),
                                context.getResources().getString(R.string.tvExpense), expense.get_category(),
                                expense.get_date(), expense.get_amountMoney(), remainMoney));
                    } else {
                        data.add(new FinancialRecyclerView(expense.get_id(),
                                context.getResources().getString(R.string.tvExpense), expense.get_categoryChild(),
                                expense.get_date(), expense.get_amountMoney(), remainMoney));
                    }
                    tempAmountMoney = expense.get_amountMoney();
                    isFirstRow = false;
                    isIncome = false;
                } else if (isIncome){
                    //calculate remain money
                    remainMoney = calculateRemainMoneyIncome(tempAmountMoney, remainMoney);
                    if ("".equals(expense.get_categoryChild())) {
                        data.add(new FinancialRecyclerView(expense.get_id(),
                                context.getResources().getString(R.string.tvExpense), expense.get_category(),
                                expense.get_date(), expense.get_amountMoney(), remainMoney));
                    } else {
                        data.add(new FinancialRecyclerView(expense.get_id(),
                                context.getResources().getString(R.string.tvExpense), expense.get_categoryChild(),
                                expense.get_date(), expense.get_amountMoney(), remainMoney));
                    }
                    tempAmountMoney = expense.get_amountMoney();
                    isIncome = false;
                } else {
                    remainMoney = calculateRemainMoneyExpense(tempAmountMoney, remainMoney);
                    if ("".equals(expense.get_categoryChild())) {
                        data.add(new FinancialRecyclerView(expense.get_id(),
                                context.getResources().getString(R.string.tvExpense), expense.get_category(),
                                expense.get_date(), expense.get_amountMoney(), remainMoney));
                    } else {
                        data.add(new FinancialRecyclerView(expense.get_id(),
                                context.getResources().getString(R.string.tvExpense), expense.get_categoryChild(),
                                expense.get_date(), expense.get_amountMoney(), remainMoney));
                    }
                    tempAmountMoney = expense.get_amountMoney();
                    isIncome = false;
                }
            }
        }
    }

    private String calculateRemainMoneyExpense(String tempAmountMoney, String remainMoney) {
        //remain Money = present money + expense money;
        double remainMoneyNumber = Double.parseDouble(CalculatorSupport.formatExpression(remainMoney))
                + Double.parseDouble(CalculatorSupport.formatExpression(tempAmountMoney));
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        return nf.format(remainMoneyNumber);
    }

    private String calculateRemainMoneyIncome(String tempAmountMoney, String remainMoney) {
        //remain Money = present money - expense money;
        double remainMoneyNumber = Double.parseDouble(CalculatorSupport.formatExpression(remainMoney))
                - Double.parseDouble(CalculatorSupport.formatExpression(tempAmountMoney));
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        return nf.format(remainMoneyNumber);
    }

    @Override
    public void onClick(String financial, int position) {
        if ("Thu:".equals(financial)) {
            FileHelper.writeFile(context, Constant.TEMP_ISEXPENSE, "false");
            FileHelper.writeFile(context, Constant.TEMP_INCOME_ID, "" + position);
        } else {
            FileHelper.writeFile(context, Constant.TEMP_ISEXPENSE, "true");
            FileHelper.writeFile(context, Constant.TEMP_EXPENSE_ID, "" + position);
        }
        ((MainActivity) context).replaceFragment(new FinancialHistoryDetail(), true);
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
