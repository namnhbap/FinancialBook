package com.example.nguyennam.financialbook.settingtab;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.example.nguyennam.financialbook.R;


public class SettingResetDataDialog extends DialogFragment {

    public SettingResetDataDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.setting_reset_data_title, null);
        builder.setCustomTitle(view);
        builder.setMessage(R.string.resetDataMessage);
        builder.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                sendBackResult(true);
            }
        });
        builder.setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                sendBackResult(false);
            }
        });
        return builder.create();
    }

    public interface ResetDataDialogListener {
        void onFinishResetDialog(boolean isDelete);
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackResult(boolean isDelete) {
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        SettingResetDataDialog.ResetDataDialogListener listener = (SettingResetDataDialog.ResetDataDialogListener) getTargetFragment();
        listener.onFinishResetDialog(isDelete);
        dismiss();
    }

}
