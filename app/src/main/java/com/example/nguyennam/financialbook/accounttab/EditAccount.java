package com.example.nguyennam.financialbook.accounttab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.nguyennam.financialbook.database.ExpenseDAO;
import com.example.nguyennam.financialbook.database.IncomeDAO;
import com.example.nguyennam.financialbook.model.AccountRecyclerView;
import com.example.nguyennam.financialbook.model.Expense;
import com.example.nguyennam.financialbook.model.Income;
import com.example.nguyennam.financialbook.recordtab.Calculator;
import com.example.nguyennam.financialbook.utils.CalculatorSupport;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class EditAccount extends Fragment implements View.OnClickListener,
        AccountTypeDialog.AccountTypeDialogListener, MoneyTypeDialog.MoneyTypeDialogListener,
        DeleteAccountDialog.DeleteDialogListener {

    Context context;
    EditText txtAccountName;
    TextView txtAccountType;
    TextView txtMoneyType;
    TextView txtAmount;
    EditText txtDescription;
    TextView txtCancel;
    TextView txtDone;
    AccountRecyclerViewDAO accountDAO;
    AccountRecyclerView account;
    String tempMoneyStart;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_edit, container, false);
        //get account by id
        accountDAO = new AccountRecyclerViewDAO(context);
        account = accountDAO.getAccountById(Integer.parseInt(FileHelper.readFile(context, Constant.TEMP_ID)));
        tempMoneyStart = account.getMoneyStart();
//        Log.d(Constant.TAG, "onCreateView: " + account);
        //set view
        txtAccountName = (EditText) view.findViewById(R.id.txtAccountName);
        txtAccountName.setText(account.getAccountName());
        txtAccountType = (TextView) view.findViewById(R.id.txtAccountType);
        txtAccountType.setText(account.getAccountType());
        txtMoneyType = (TextView) view.findViewById(R.id.txtMoneyType);
        txtMoneyType.setText(account.getMoneyType());
        txtAmount = (TextView) view.findViewById(R.id.txtAmount);
        txtAmount.setText(account.getMoneyStart());
        txtAmount.setOnClickListener(this);
        txtDescription = (EditText) view.findViewById(R.id.txtDescription);
        txtDescription.setText(account.getDescription());
        txtCancel = (TextView) view.findViewById(R.id.txtCancel);
        txtCancel.setOnClickListener(this);
        txtDone = (TextView) view.findViewById(R.id.done);
        txtDone.setOnClickListener(this);
        RelativeLayout rlAccountType = (RelativeLayout) view.findViewById(R.id.rlAccountType);
        rlAccountType.setOnClickListener(this);
        RelativeLayout rlMoneyType = (RelativeLayout) view.findViewById(R.id.rlMoneyType);
        rlMoneyType.setOnClickListener(this);
        LinearLayout lnSave = (LinearLayout) view.findViewById(R.id.lnSave);
        lnSave.setOnClickListener(this);
        LinearLayout lnDelete = (LinearLayout) view.findViewById(R.id.lnDelete);
        lnDelete.setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!"".equals(FileHelper.readFile(context, Constant.TEMP_CALCULATOR))) {
            txtAmount.setText(FileHelper.readFile(context, Constant.TEMP_CALCULATOR));
        }
        txtAccountType.setText(account.getAccountType());
        txtMoneyType.setText(account.getMoneyType());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtAmount:
                FileHelper.writeFile(context, Constant.TEMP_CALCULATOR, txtAmount.getText().toString());
                ((MainActivity) context).replaceFragment(new Calculator(), true);
                break;
            case R.id.rlAccountType:
                AccountTypeDialog accountTypeDialog = new AccountTypeDialog();
                accountTypeDialog.setTargetFragment(EditAccount.this, 271);
                accountTypeDialog.show(getActivity().getSupportFragmentManager(), "account_type");
                break;
            case R.id.rlMoneyType:
                MoneyTypeDialog moneyTypeDialog = new MoneyTypeDialog();
                moneyTypeDialog.setTargetFragment(EditAccount.this, 271);
                moneyTypeDialog.show(getActivity().getSupportFragmentManager(), "money_type");
                break;
            case R.id.txtCancel:
                FileHelper.deleteFile(context, Constant.TEMP_CALCULATOR);
                FileHelper.deleteFile(context, Constant.TEMP_ID);
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.lnDelete:
                DeleteAccountDialog deleteAccountDialog = new DeleteAccountDialog();
                deleteAccountDialog.setTargetFragment(EditAccount.this, 271);
                deleteAccountDialog.show(getActivity().getSupportFragmentManager(), "delete_account");
                break;
            case R.id.done:
            case R.id.lnSave:
                if ("".equals(txtAccountName.getText().toString())) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noticeNoAccountName),
                            Toast.LENGTH_LONG).show();
                } else if ("".equals(txtAmount.getText().toString())) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noticeNoMoney),
                            Toast.LENGTH_LONG).show();
                } else if ("".equals(txtAccountType.getText().toString())) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noticeNoAccountType),
                            Toast.LENGTH_LONG).show();
                } else if ("".equals(txtMoneyType.getText().toString())) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.noticeNoMoneyType),
                            Toast.LENGTH_LONG).show();
                } else {
                    saveData();
                }
                break;
        }
    }

    public void saveData() {
        account.setAccountName(txtAccountName.getText().toString());
        account.setMoneyStart(txtAmount.getText().toString());
        account.setDescription(txtDescription.getText().toString());
        account.setAmountMoney(getAmountMoneyUpdate(tempMoneyStart, txtAmount.getText().toString(),account.getAmountMoney()));
        accountDAO.updateAccount(account);
//        Log.d(Constant.TAG, "saveData: " + account);
        FileHelper.deleteFile(context, Constant.TEMP_CALCULATOR);
        FileHelper.deleteFile(context, Constant.TEMP_ID);
        getActivity().getSupportFragmentManager().popBackStack();
    }

    public String getAmountMoneyUpdate(String tempMoneyStart, String moneyStart, String amountMoney) {
        String moneyUpdate;
        double moneyNumber; //moneystart to calculate and format to display
        moneyNumber = Double.parseDouble(CalculatorSupport.formatExpression(moneyStart))
                - Double.parseDouble(CalculatorSupport.formatExpression(tempMoneyStart))
                + Double.parseDouble(CalculatorSupport.formatExpression(amountMoney));
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMANY);
        moneyUpdate = nf.format(moneyNumber);
        return moneyUpdate;
    }

    @Override
    public void onFinishAccountDialog(String inputText) {
        txtAccountType.setText(inputText);
        account.setAccountType(inputText);
    }

    @Override
    public void onFinishMoneyDialog(String inputText) {
        txtMoneyType.setText(inputText);
        account.setMoneyType(inputText);
    }

    @Override
    public void onFinishDeleteDialog(boolean isDelete) {
        if (isDelete) {
            String temp_account_id = "temp_account_id.tmp";
            deleteAllFinancial();
            if (account.getId() == Integer.parseInt(FileHelper.readFile(context, temp_account_id))) {
                FileHelper.deleteFile(context, temp_account_id);
            }
            accountDAO.deleteAccount(account);
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    private void deleteAllFinancial() {
        List<Expense> expenseList;
        List<Income> incomeList;
        ExpenseDAO expenseDAO = new ExpenseDAO(context);
        expenseList = expenseDAO.getExpenseByAccountID(account.getId());
        IncomeDAO incomeDAO = new IncomeDAO(context);
        incomeList = incomeDAO.getIncomeByAccountID(account.getId());
        for (Expense expense : expenseList) {
            expenseDAO.deleteExpense(expense);
        }
        for (Income income : incomeList) {
            incomeDAO.deleteIncome(income);
        }
    }
}
