package com.example.nguyennam.financialbook.settingtab;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.utils.Constant;
import com.example.nguyennam.financialbook.utils.FileHelper;

import java.util.Locale;

/**
 * Created by NguyenNam on 2/10/2017.
 */

public class SettingMain extends Fragment implements View.OnClickListener,
        SettingLanguageDialog.SettingLanguageDialogListener, SettingResetDataDialog.ResetDataDialogListener {

    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_main, container, false);
        LinearLayout lnLanguage = (LinearLayout) view.findViewById(R.id.lnLanguage);
        lnLanguage.setOnClickListener(this);
        LinearLayout lnResetData = (LinearLayout) view.findViewById(R.id.lnResetData);
        lnResetData.setOnClickListener(this);
        LinearLayout lnAbout = (LinearLayout) view.findViewById(R.id.lnAbout);
        lnAbout.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lnLanguage:
                SettingLanguageDialog settingLanguageDialog = new SettingLanguageDialog();
                settingLanguageDialog.setTargetFragment(SettingMain.this, 271);
                settingLanguageDialog.show(getActivity().getSupportFragmentManager(), "setting_language");
                break;
            case R.id.lnResetData:
                SettingResetDataDialog settingResetDataDialog = new SettingResetDataDialog();
                settingResetDataDialog.setTargetFragment(SettingMain.this, 271);
                settingResetDataDialog.show(getActivity().getSupportFragmentManager(), "reset_data");
                break;
            case R.id.lnAbout:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                SettingAboutDialog settingAboutDialog = new SettingAboutDialog();
                settingAboutDialog.show(fm, "about");
                break;
        }
    }

    public void reStart() {
        Intent i = context.getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void onFinishSettingLanguageDialog(int which) {
        // setting language
        if (which == 1) {
            FileHelper.writeFile(context, Constant.TEMP_LANGUAGE, "" + which);
            String languageToLoad = "en"; // your language
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            context.getResources().updateConfiguration(config,
                    context.getResources().getDisplayMetrics());
        } else {
            FileHelper.writeFile(context, Constant.TEMP_LANGUAGE, "" + which);
            String languageToLoad = "vi"; // your language
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            context.getResources().updateConfiguration(config,
                    context.getResources().getDisplayMetrics());
        }
        reStart();
    }

    @Override
    public void onFinishResetDialog(boolean isDelete) {
        // reset data

    }
}
