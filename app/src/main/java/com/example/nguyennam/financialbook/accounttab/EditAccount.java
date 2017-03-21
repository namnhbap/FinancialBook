package com.example.nguyennam.financialbook.accounttab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nguyennam.financialbook.R;

/**
 * Created by NguyenNam on 3/21/2017.
 */

public class EditAccount extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_edit, container, false);
        return view;
    }
}
