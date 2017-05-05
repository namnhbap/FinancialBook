package com.example.nguyennam.financialbook.reporttab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyennam.financialbook.MainActivity;
import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.database.AccountRecyclerViewDAO;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

public class ReportExpenseIncome extends Fragment implements View.OnClickListener,
        ReportViewByDialog.ReportViewByDialogListener, ReportPickTimeDialog.ReportPickTimeListener {

    Context context;
    TextView txtAccount;
    TextView txtViewBy;
    String[] arrayViewBy;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayViewBy = getResources().getStringArray(R.array.report_view_by);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.report_expense_income, container, false);
        txtAccount = (TextView) v.findViewById(R.id.txtAccount);
        txtViewBy = (TextView) v.findViewById(R.id.txtViewBy);
        RelativeLayout rlAccount = (RelativeLayout) v.findViewById(R.id.rlAccount);
        rlAccount.setOnClickListener(this);
        RelativeLayout rlViewBy = (RelativeLayout) v.findViewById(R.id.rlViewBy);
        rlViewBy.setOnClickListener(this);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        if ("".equals(FileHelper.readFile(context, Constant.TEMP_VIEW_BY))) {
            txtViewBy.setText(arrayViewBy[0]);
        } else {
            txtViewBy.setText(FileHelper.readFile(context, Constant.TEMP_VIEW_BY));
        }
        if (!"".equals(FileHelper.readFile(context, Constant.TEMP_ID))) {
            String accountList = "";
            AccountRecyclerViewDAO accountDAO = new AccountRecyclerViewDAO(context);
            String idAccount = FileHelper.readFile(context, Constant.TEMP_ID);
            String[] mangId = idAccount.split(";");
            for (int i = 0; i < mangId.length; i++) {
                if (i == 0) {
                    accountList += accountDAO.getAccountById(Integer.parseInt(mangId[i])).getAccountName();
                } else {
                    accountList += ", " + accountDAO.getAccountById(Integer.parseInt(mangId[i])).getAccountName();
                }
            }
            txtAccount.setText(accountList);
        } else {
            FileHelper.writeFile(context, Constant.TEMP_ID, "" + 1);
            txtAccount.setText(new AccountRecyclerViewDAO(context).getAccountById(1).getAccountName());
        }
        if (!"".equals(txtAccount.getText().toString()) && !"".equals(txtViewBy.getText().toString())) {
            if (arrayViewBy[0].equals(txtViewBy.getText().toString())) {
                insertNestedFragment(new ReportViewByMonth());
            } else if (arrayViewBy[1].equals(txtViewBy.getText().toString())) {
                insertNestedFragment(new ReportViewByQuarter());
            } else if (arrayViewBy[2].equals(txtViewBy.getText().toString())) {
                insertNestedFragment(new ReportViewByYear());
            } else {
                insertNestedFragment(new ReportPeriodTime());
            }
        }
    }

    private void insertNestedFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentReport, fragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlAccount:
                ((MainActivity) context).replaceFragment(new ReportSelectAccount(), true);
                break;
            case R.id.rlViewBy:
                ReportViewByDialog reportViewByDialog = new ReportViewByDialog();
                reportViewByDialog.setTargetFragment(ReportExpenseIncome.this, 271);
                reportViewByDialog.show(getActivity().getSupportFragmentManager(), "report_view_by");
                break;
        }

    }

    @Override
    public void onFinishReportDialog(int which, String inputText) {
        FileHelper.writeFile(context, Constant.TEMP_VIEW_BY, inputText);
        switch (which) {
            case 0:
                txtViewBy.setText(inputText);
                insertNestedFragment(new ReportViewByMonth());
                break;
            case 1:
                txtViewBy.setText(inputText);
                insertNestedFragment(new ReportViewByQuarter());
                break;
            case 2:
                txtViewBy.setText(inputText);
                insertNestedFragment(new ReportViewByYear());
                break;
            case 3:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ReportPickTimeDialog reportPickTimeDialog = new ReportPickTimeDialog();
                reportPickTimeDialog.setTargetFragment(ReportExpenseIncome.this, 271);
                reportPickTimeDialog.show(fm, "pick_time");
                break;
        }
    }

    @Override
    public void onFinishPickTime(String inputText) {
        FileHelper.writeFile(context, Constant.TEMP_VIEW_BY, inputText);
        txtViewBy.setText(inputText);
        if (!"".equals(txtAccount.getText().toString())) {
            insertNestedFragment(new ReportPeriodTime());
        }
    }
}
