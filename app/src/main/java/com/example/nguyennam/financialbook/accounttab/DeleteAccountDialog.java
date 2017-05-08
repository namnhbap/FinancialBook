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

public class DeleteAccountDialog extends DialogFragment {

    public DeleteAccountDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.stat_notify_error)
                .setTitle(R.string.deleteTitle)
                .setMessage(R.string.deleteMessage)
                .setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sendBackResult(true);
                    }
                })
                .setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        sendBackResult(false);
                    }
                }).create();
    }

    public interface DeleteDialogListener {
        void onFinishDeleteDialog(boolean isDelete);
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackResult(boolean isDelete) {
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        DeleteAccountDialog.DeleteDialogListener listener = (DeleteAccountDialog.DeleteDialogListener) getTargetFragment();
        listener.onFinishDeleteDialog(isDelete);
        dismiss();
    }
}