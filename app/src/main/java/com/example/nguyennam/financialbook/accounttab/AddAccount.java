package com.example.nguyennam.financialbook.accounttab;

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
import com.example.nguyennam.financialbook.model.AccountRecyclerView;
import com.example.nguyennam.financialbook.recordtab.Calculator;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

public class AddAccount extends Fragment implements View.OnClickListener,
        AccountTypeDialog.AccountTypeDialogListener, MoneyTypeDialog.MoneyTypeDialogListener {

    Context context;
    EditText txtAccountName;
    TextView txtAccountType;
    TextView txtMoneyType;
    TextView txtAmount;
    EditText txtDescription;
    TextView txtCancel;
    TextView txtDone;
    AccountRecyclerView account = new AccountRecyclerView();

    String temp_calculator = "temp_calculator.tmp";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_add, container, false);
        txtAccountName = (EditText) view.findViewById(R.id.txtAccountName);
        txtAccountType = (TextView) view.findViewById(R.id.txtAccountType);
        txtMoneyType = (TextView) view.findViewById(R.id.txtMoneyType);
        txtAmount = (TextView) view.findViewById(R.id.txtAmount);
        txtAmount.setOnClickListener(this);
        txtDescription = (EditText) view.findViewById(R.id.txtDescription);
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
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        account.setMoneyStart(FileHelper.readFile(context, temp_calculator));

        txtAccountType.setText(account.getAccountType());
        txtAmount.setText(account.getMoneyStart());
        txtMoneyType.setText(account.getMoneyType());
        Log.d(Constant.TAG, "onStart: ");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtAmount:
                ((MainActivity) context).replaceFragment(new Calculator(), true);
                break;
            case R.id.rlAccountType:
                AccountTypeDialog accountTypeDialog = new AccountTypeDialog();
                accountTypeDialog.setTargetFragment(AddAccount.this, 271);
                accountTypeDialog.show(getActivity().getSupportFragmentManager(), "account_type");
                break;
            case R.id.rlMoneyType:
                MoneyTypeDialog moneyTypeDialog = new MoneyTypeDialog();
                moneyTypeDialog.setTargetFragment(AddAccount.this, 271);
                moneyTypeDialog.show(getActivity().getSupportFragmentManager(), "money_type");
                break;
            case R.id.txtCancel:
                getActivity().getSupportFragmentManager().popBackStack();
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
        account.setAmountMoney(txtAmount.getText().toString());
        account.setDescription(txtDescription.getText().toString());
        AccountRecyclerViewDAO accountRecyclerViewDAO = new AccountRecyclerViewDAO(context);
        accountRecyclerViewDAO.addAccount(account);
        FileHelper.deleteFile(context, temp_calculator);
        getActivity().getSupportFragmentManager().popBackStack();
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
}

