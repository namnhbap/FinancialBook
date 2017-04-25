package com.example.nguyennam.financialbook.reporttab;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.utils.Constant;


public class ReportViewByDialog extends DialogFragment {

    public ReportViewByDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String[] arrays = getResources().getStringArray(R.array.report_view_by);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.report_view_by_title, null);
        builder.setCustomTitle(view);
        builder.setItems(arrays, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        sendBackResult(which, arrays[which]);
                    }
                });
        return builder.create();
    }

    public interface ReportViewByDialogListener {
        void onFinishReportDialog(int which, String inputText);
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackResult(int which, String viewBy) {
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        ReportViewByDialogListener listener = (ReportViewByDialogListener) getTargetFragment();
        listener.onFinishReportDialog(which, viewBy);
        dismiss();
    }

}
