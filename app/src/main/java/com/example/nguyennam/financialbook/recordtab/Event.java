package com.example.nguyennam.financialbook.recordtab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.nguyennam.financialbook.R;

/**
 * Created by NguyenNam on 1/15/2017.
 */

public class Event extends Fragment implements View.OnClickListener {
    EditText editText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event, container, false);
        editText = (EditText) view.findViewById(R.id.txtEvent);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.lnDoneEvent);
        linearLayout.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lnDoneEvent:
//                String message = String.valueOf(editText.getText());
                break;
        }
    }
}
