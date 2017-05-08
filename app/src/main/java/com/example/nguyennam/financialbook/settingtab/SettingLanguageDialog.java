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


public class SettingLanguageDialog extends DialogFragment {

    public SettingLanguageDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String[] arrays = getResources().getStringArray(R.array.setting_language);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.setting_language_title, null);
        builder.setCustomTitle(view);
        builder.setItems(arrays, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        sendBackResult(which);
                    }
                });
        return builder.create();
    }

    public interface SettingLanguageDialogListener {
        void onFinishSettingLanguageDialog(int which);
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackResult(int which) {
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        SettingLanguageDialogListener listener = (SettingLanguageDialogListener) getTargetFragment();
        listener.onFinishSettingLanguageDialog(which);
        dismiss();
    }

}
