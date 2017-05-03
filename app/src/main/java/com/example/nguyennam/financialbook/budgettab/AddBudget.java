package com.example.nguyennam.financialbook.budgettab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyennam.financialbook.MainActivity;
import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.database.AccountRecyclerViewDAO;
import com.example.nguyennam.financialbook.database.BudgetRecyclerViewDAO;
import com.example.nguyennam.financialbook.database.ExpenseDAO;
import com.example.nguyennam.financialbook.model.BudgetRecyclerView;
import com.example.nguyennam.financialbook.recordtab.Accounts;
import com.example.nguyennam.financialbook.recordtab.Calculator;
import com.example.nguyennam.financialbook.recordtab.ExpenseCategory;
import com.example.nguyennam.financialbook.reporttab.ReportExpenseIncome;
import com.example.nguyennam.financialbook.reporttab.ReportPickTimeDialog;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

public class AddBudget extends Fragment implements View.OnClickListener,
        ReportPickTimeDialog.ReportPickTimeListener {

    Context context;
    EditText txtBudgetName;
    TextView txtAmount;
    TextView txtAccount;
    TextView txtCategory;
    TextView txtDate;
    BudgetRecyclerView budget = new BudgetRecyclerView();
    TextView txtCancel;
    TextView txtDone;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.budget_add, container, false);
        txtBudgetName = (EditText) view.findViewById(R.id.txtBudgetName);
        txtAmount = (TextView) view.findViewById(R.id.txtAmount);
        txtAmount.setOnClickListener(this);
        txtAccount = (TextView) view.findViewById(R.id.txtAccount);
        txtCategory = (TextView) view.findViewById(R.id.txtCategory);
        txtDate = (TextView) view.findViewById(R.id.txtDate);
        RelativeLayout rlAccount = (RelativeLayout) view.findViewById(R.id.rlAccount);
        rlAccount.setOnClickListener(this);
        RelativeLayout rlCategory = (RelativeLayout) view.findViewById(R.id.rlCategory);
        rlCategory.setOnClickListener(this);
        RelativeLayout rlDate = (RelativeLayout) view.findViewById(R.id.rlDate);
        rlDate.setOnClickListener(this);
        LinearLayout lnSave = (LinearLayout) view.findViewById(R.id.lnSave);
        lnSave.setOnClickListener(this);
        txtCancel = (TextView) view.findViewById(R.id.txtCancel);
        txtCancel.setOnClickListener(this);
        txtDone = (TextView) view.findViewById(R.id.txtDone);
        txtDone.setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!"".equals(FileHelper.readFile(context, Constant.TEMP_CALCULATOR))) {
            txtAmount.setText(FileHelper.readFile(context, Constant.TEMP_CALCULATOR));
        }
        if (!"".equals(FileHelper.readFile(context, Constant.TEMP_CATEGORY_CHILD))) {
            txtCategory.setText(FileHelper.readFile(context, Constant.TEMP_CATEGORY_CHILD));
        } else if (!"".equals(FileHelper.readFile(context, Constant.TEMP_CATEGORY))) {
            txtCategory.setText(FileHelper.readFile(context, Constant.TEMP_CATEGORY));
        }
        if (!"".equals(FileHelper.readFile(context, Constant.TEMP_ACCOUNT_ID))) {
            AccountRecyclerViewDAO accountDAO = new AccountRecyclerViewDAO(context);
            budget.setAccountID(Integer.parseInt(FileHelper.readFile(context, Constant.TEMP_ACCOUNT_ID)));
            txtAccount.setText(accountDAO.getAccountById(budget.getAccountID()).getAccountName());
        }
        if (!"".equals(FileHelper.readFile(context, Constant.TEMP_BUDGET_DATE))) {
            txtDate.setText(FileHelper.readFile(context, Constant.TEMP_BUDGET_DATE));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtAmount:
                ((MainActivity) context).replaceFragment(new Calculator(), true);
                break;
            case R.id.rlAccount:
                ((MainActivity) context).replaceFragment(new Accounts(), true);
                break;
            case R.id.rlCategory:
                ((MainActivity) context).replaceFragment(new ExpenseCategory(), true);
                break;
            case R.id.rlDate:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ReportPickTimeDialog reportPickTimeDialog = new ReportPickTimeDialog();
                reportPickTimeDialog.setTargetFragment(AddBudget.this, 271);
                reportPickTimeDialog.show(fm, "pick_time");
                break;
            case R.id.txtCancel:
                clearTempFile();
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.txtDone:
            case R.id.lnSave:
                if ("".equals(txtBudgetName.getText().toString())) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noticeNoBudget),
                            Toast.LENGTH_LONG).show();
                } else if ("".equals(txtAmount.getText().toString())) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noticeNoMoney),
                            Toast.LENGTH_LONG).show();
                } else if ("".equals(txtCategory.getText().toString())) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noticeNoCategory),
                            Toast.LENGTH_LONG).show();
                } else if ("".equals(txtAccount.getText().toString())) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noticeNoAccount),
                            Toast.LENGTH_LONG).show();
                } else if ("".equals(txtDate.getText().toString())) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noticeNoDate),
                            Toast.LENGTH_LONG).show();
                } else {
                    saveData();
                }
                break;

        }

    }

    private void saveData() {
        //set budget
        setBudget();
        //add budget into database
        BudgetRecyclerViewDAO budgetDAO = new BudgetRecyclerViewDAO(context);
        budgetDAO.addBudget(budget);
        Log.d(Constant.TAG, "onClick: " + budgetDAO.getAllBudget());
        //clear temp file
        clearTempFile();
        //exit
        getActivity().getSupportFragmentManager().popBackStack();
    }

    private void setBudget() {
        budget.setBudgetName(txtBudgetName.getText().toString());
        budget.setAmountMoney(txtAmount.getText().toString());
        budget.setCategory(txtCategory.getText().toString());
        budget.setDate(txtDate.getText().toString());
        budget.setRemainMoney(txtAmount.getText().toString());
        budget.setExpenseMoney("0");

    }

    private void clearTempFile() {
        FileHelper.deleteFile(context, Constant.TEMP_CALCULATOR);
        FileHelper.deleteFile(context, Constant.TEMP_CATEGORY);
        FileHelper.deleteFile(context, Constant.TEMP_CATEGORY_CHILD);
        FileHelper.deleteFile(context, Constant.TEMP_BUDGET_DATE);
    }

    @Override
    public void onFinishPickTime(String inputText) {
        FileHelper.writeFile(context, Constant.TEMP_BUDGET_DATE, inputText);
        txtDate.setText(inputText);
    }
}

