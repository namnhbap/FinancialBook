package com.example.nguyennam.financialbook.accounttab;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;
import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.utils.Constant;


public class AccountTypeDialog extends DialogFragment {

    public AccountTypeDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String[] arrays = getResources().getStringArray(R.array.account_type);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.accountType)
                .setItems(arrays, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        sendBackResult(arrays[which]);
                    }
                });
        return builder.create();
    }

    public interface AccountTypeDialogListener {
        void onFinishAccountDialog(String inputText);
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackResult(String accountType) {
        Log.d(Constant.TAG, "sendBackResult: " + accountType);
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        AccountTypeDialogListener listener = (AccountTypeDialogListener) getTargetFragment();
        listener.onFinishAccountDialog(accountType);
        dismiss();
    }

}
