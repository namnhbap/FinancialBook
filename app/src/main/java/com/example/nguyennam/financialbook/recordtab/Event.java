package com.example.nguyennam.financialbook.recordtab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.nguyennam.financialbook.R;

/**
 * Created by NguyenNam on 1/15/2017.
 */

public class Event extends Fragment {
    EditText editText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record_event, container, false);
        editText = (EditText) view.findViewById(R.id.txtMoneyType);
        return view;
    }
}
