package com.example.nguyennam.financialbook.recordtab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.utils.FileHelper;

/**
 * Created by NguyenNam on 1/15/2017.
 */

public class Event extends Fragment implements View.OnClickListener {

    EditText editText;
    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record_event, container, false);
        editText = (EditText) view.findViewById(R.id.txtMoneyType);
        TextView txtCancel = (TextView) view.findViewById(R.id.txtCancel);
        txtCancel.setOnClickListener(this);
        TextView txtDone = (TextView) view.findViewById(R.id.txtDone);
        txtDone.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtCancel:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
            case R.id.txtDone:
                String filename = "temp_event.tmp";
                FileHelper.writeFile(context, filename, editText.getText().toString());
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }
}
