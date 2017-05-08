package com.example.nguyennam.financialbook.budgettab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.example.nguyennam.financialbook.model.BudgetRecyclerView;
import com.example.nguyennam.financialbook.recordtab.Accounts;
import com.example.nguyennam.financialbook.recordtab.Calculator;
import com.example.nguyennam.financialbook.recordtab.ExpenseCategory;
import com.example.nguyennam.financialbook.reporttab.ReportPickTimeDialog;
import com.example.nguyennam.financialbook.utils.CalculatorSupport;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

import java.text.NumberFormat;
import java.util.Locale;

public class EditBudget extends Fragment implements View.OnClickListener,
        ReportPickTimeDialog.ReportPickTimeListener, DeleteBudgetDialog.DeleteDialogListener {

    Context context;
    EditText txtBudgetName;
    TextView txtAmount;
    TextView txtAccount;
    TextView txtCategory;
    TextView txtDate;
    BudgetRecyclerView budget = new BudgetRecyclerView();
    TextView txtCancel;
    TextView txtDone;

    String temp_new_account_id;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FileHelper.readFile(context, Constant.TEMP_BUDGET_ID);
        budget = new BudgetRecyclerViewDAO(context).getBudgetById(
                Integer.parseInt(FileHelper.readFile(context, Constant.TEMP_BUDGET_ID)));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.budget_edit, container, false);
        txtBudgetName = (EditText) view.findViewById(R.id.txtBudgetName);
        txtBudgetName.setText(budget.getBudgetName());
        txtAmount = (TextView) view.findViewById(R.id.txtAmount);
        txtAmount.setText(budget.getAmountMoney());
        txtAmount.setOnClickListener(this);
        txtAccount = (TextView) view.findViewById(R.id.txtAccount);
        AccountRecyclerViewDAO accountDAO = new AccountRecyclerViewDAO(context);
        txtAccount.setText(accountDAO.getAccountById(budget.getAccountID()).getAccountName());
        txtCategory = (TextView) view.findViewById(R.id.txtCategory);
        txtCategory.setText(budget.getCategory());
        txtDate = (TextView) view.findViewById(R.id.txtDate);
        txtDate.setText(budget.getDate());
        RelativeLayout rlAccount = (RelativeLayout) view.findViewById(R.id.rlAccount);
        rlAccount.setOnClickListener(this);
        RelativeLayout rlCategory = (RelativeLayout) view.findViewById(R.id.rlCategory);
        rlCategory.setOnClickListener(this);
        RelativeLayout rlDate = (RelativeLayout) view.findViewById(R.id.rlDate);
        rlDate.setOnClickListener(this);
        LinearLayout lnSave = (LinearLayout) view.findViewById(R.id.lnSave);
        lnSave.setOnClickListener(this);
        LinearLayout lnDelete = (LinearLayout) view.findViewById(R.id.lnDelete);
        lnDelete.setOnClickListener(this);
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
        } else {
            txtAmount.setText(budget.getAmountMoney());
            FileHelper.writeFile(context, Constant.TEMP_CALCULATOR_EDIT, budget.getAmountMoney());
        }
        if (!"".equals(FileHelper.readFile(context, Constant.TEMP_CATEGORY_CHILD))) {
            txtCategory.setText(FileHelper.readFile(context, Constant.TEMP_CATEGORY_CHILD));
        } else if (!"".equals(FileHelper.readFile(context, Constant.TEMP_CATEGORY))) {
            txtCategory.setText(FileHelper.readFile(context, Constant.TEMP_CATEGORY));
        }
        if (!"".equals(FileHelper.readFile(context, Constant.TEMP_ACCOUNT_ID_EDIT))) {
            AccountRecyclerViewDAO accountDAO = new AccountRecyclerViewDAO(context);
            temp_new_account_id = FileHelper.readFile(context, Constant.TEMP_ACCOUNT_ID_EDIT);
            txtAccount.setText(accountDAO.getAccountById(Integer.parseInt(temp_new_account_id)).getAccountName());
        } else {
            temp_new_account_id = String.valueOf(budget.getAccountID());
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
                reportPickTimeDialog.setTargetFragment(EditBudget.this, 271);
                reportPickTimeDialog.show(fm, "pick_time");
                break;
            case R.id.txtCancel:
                clearTempFile();
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.lnDelete:
                DeleteBudgetDialog deleteBudgetDialog = new DeleteBudgetDialog();
                deleteBudgetDialog.setTargetFragment(EditBudget.this, 271);
                deleteBudgetDialog.show(getActivity().getSupportFragmentManager(), "delete_budget");
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
        //update remain money
        updateRemainMoney();
        //set budget
        setBudget();
        //add budget into database
        BudgetRecyclerViewDAO budgetDAO = new BudgetRecyclerViewDAO(context);
        budgetDAO.updateBudget(budget);
        //clear temp file
        clearTempFile();
        //exit
        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private void setBudget() {
        budget.setBudgetName(txtBudgetName.getText().toString());
        budget.setAmountMoney(txtAmount.getText().toString());
        budget.setCategory(txtCategory.getText().toString());
        budget.setDate(txtDate.getText().toString());
    }

    private void updateRemainMoney() {
        if (temp_new_account_id.equals(String.valueOf(budget.getAccountID()))) {
            double remainMoneyNumber = Double.parseDouble(CalculatorSupport.formatExpression(txtAmount.getText().toString()))
                    - Double.parseDouble(CalculatorSupport.formatExpression(budget.getAmountMoney()))
                    + Double.parseDouble(CalculatorSupport.formatExpression(budget.getRemainMoney()));
            NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
            budget.setRemainMoney(nf.format(remainMoneyNumber));
        } else {
            budget.setRemainMoney(txtAmount.getText().toString());
        }
    }

    private void clearTempFile() {
        FileHelper.deleteFile(context, Constant.TEMP_CALCULATOR);
        FileHelper.deleteFile(context, Constant.TEMP_CALCULATOR_EDIT);
        FileHelper.deleteFile(context, Constant.TEMP_CATEGORY);
        FileHelper.deleteFile(context, Constant.TEMP_CATEGORY_CHILD);
        FileHelper.deleteFile(context, Constant.TEMP_BUDGET_DATE);
        FileHelper.deleteFile(context, Constant.TEMP_ACCOUNT_ID_EDIT);
    }

    @Override
    public void onFinishPickTime(String inputText) {
        FileHelper.writeFile(context, Constant.TEMP_BUDGET_DATE, inputText);
        txtDate.setText(inputText);
    }

    @Override
    public void onFinishDeleteDialog(boolean isDelete) {
        if (isDelete) {
            BudgetRecyclerViewDAO budgetDAO = new BudgetRecyclerViewDAO(context);
            budgetDAO.deleteBudget(budget);
            clearTempFile();
            Toast.makeText(context, R.string.deleteSuccessfully, Toast.LENGTH_SHORT).show();
            getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}
