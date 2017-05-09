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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.adapters.ReportExpenseIncomeHistoryAdapter;
import com.example.nguyennam.financialbook.database.AccountRecyclerViewDAO;
import com.example.nguyennam.financialbook.database.IncomeDAO;
import com.example.nguyennam.financialbook.model.AccountRecyclerView;
import com.example.nguyennam.financialbook.model.FinancialHistoryChild;
import com.example.nguyennam.financialbook.model.Income;
import com.example.nguyennam.financialbook.utils.CalendarSupport;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportPeriodTimeIncomeDetail extends Fragment {

    Context context;
    IncomeDAO incomeDAO;
    List<String> dateIncomeList;
    String[] mangId;
    RecyclerView recyclerView;
    List<FinancialHistoryChild> data;
    ImageView btnBack;
    TextView txtDateReport;
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.report_expense_history, container, false);
        txtDateReport = (TextView) v.findViewById(R.id.txtDateReport);
        btnBack = (ImageView) v.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileHelper.deleteFile(context, Constant.TEMP_CATEGORY);
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerviewReport);
        data = new ArrayList<>();
        getDateIncome();
        setDataForReport();
        txtDateReport.setText(FileHelper.readFile(context, Constant.TEMP_VIEW_BY));
        setAdapter();
        return v;
    }

    private void setAdapter() {
        ReportExpenseIncomeHistoryAdapter myAdapter = new ReportExpenseIncomeHistoryAdapter(context, data);
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
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

    private void getDateIncome() {
        // get date Income
        incomeDAO = new IncomeDAO(context);
        dateIncomeList = incomeDAO.getDateIncome();
        // sort date from now to past
        CalendarSupport.sortDateList(dateIncomeList);
    }

    private void addData(String date) {
        for (int j = 0; j < mangId.length; j++) {
            AccountRecyclerView account = new AccountRecyclerViewDAO(context).
                    getAccountById(Integer.parseInt(mangId[j]));
            List<Income> incomeList = incomeDAO.getIncomeByAccountID(Integer.parseInt(mangId[j]), date);
            for (Income income : incomeList) {
                if (FileHelper.readFile(context, Constant.TEMP_CATEGORY).equals(income.get_category())) {
                    data.add(new FinancialHistoryChild(false, income.get_amountMoney(),
                            account.getAccountName(), income.get_category(), income.get_date()));
                }
            }
        }
    }

    private void setDataForReport() {
        getDateStartEnd();
        // get id account from account name form
        String idAccount = FileHelper.readFile(context, Constant.TEMP_ID);
        mangId = idAccount.split(";");
        for (int i = 0; i < dateIncomeList.size(); i++) {
            Date date = CalendarSupport.convertStringToDate(dateIncomeList.get(i));
            if (!date.before(startDate) && !date.after(endDate)) {
                addData(dateIncomeList.get(i));
            }
        }
    }
}
