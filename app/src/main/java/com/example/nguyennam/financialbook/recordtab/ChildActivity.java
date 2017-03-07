package com.example.nguyennam.financialbook.recordtab;

import android.app.Activity;
import android.os.Bundle;

import com.example.nguyennam.financialbook.R;

/**
 * Created by NguyenNam on 2/16/2017.
 */

public class ChildActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_child);
    }
}