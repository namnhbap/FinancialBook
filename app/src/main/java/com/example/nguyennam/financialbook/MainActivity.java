package com.example.nguyennam.financialbook;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.nguyennam.financialbook.accounttab.AccountMain;
import com.example.nguyennam.financialbook.model.AccountRecyclerView;
import com.example.nguyennam.financialbook.budgettab.BudgetMain;
import com.example.nguyennam.financialbook.database.AccountRecyclerViewDAO;
import com.example.nguyennam.financialbook.recordtab.RecordMain;
import com.example.nguyennam.financialbook.reporttab.ReportMain;
import com.example.nguyennam.financialbook.settingtab.SettingMain;

public class MainActivity extends FragmentActivity {

    SharedPreferences prefs = null;
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkFirstRun();
        initView();
    }

    private void checkFirstRun() {
        prefs = getSharedPreferences("com.example.nguyennam.financialbook", MODE_PRIVATE);
        AccountRecyclerViewDAO allAcount = new AccountRecyclerViewDAO(this);
        //check the first time run app (install?)
        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            allAcount.addAccount(new AccountRecyclerView(1, "Ví", 0));
            allAcount.addAccount(new AccountRecyclerView(2, "ATM", 0));
            allAcount.addAccount(new AccountRecyclerView(3, "Tiết Kiệm", 0));
            // using the following line to edit/commit prefs
            prefs.edit().putBoolean("firstrun", false).commit();
        }
    }

    private void initView() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab1").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.Records, R.drawable.note_selected)),
                RecordMain.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab2").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.Accounts, R.drawable.wallet)),
                AccountMain.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab3").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.Budget, R.drawable.tab_budget)),
                BudgetMain.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab4").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.Reports, R.drawable.pie_chart)),
                ReportMain.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("tab5").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.Setting, R.drawable.more)),
                SettingMain.class, null);
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                ImageView image = null;
                TextView iconTitle = null;
                int selectedIndex = mTabHost.getCurrentTab();
                setColorImage(image, iconTitle, selectedIndex);
            }
        });
    }

    private void setColorImage(ImageView image, TextView iconTitle, int selectedIndex) {
        for (int i = 0; i < mTabHost.getTabWidget().getTabCount(); i++) {
            image = (ImageView) mTabHost.getTabWidget().getChildAt(i).findViewById(R.id.img_icon);
            iconTitle = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(R.id.text_icon);
            if (i != selectedIndex) {
                switch (i) {
                    case 0:
                        image.setImageResource(R.drawable.note);
                        iconTitle.setTextColor(ResourcesCompat.getColor(getResources(), R.color.darkBlack, null));
                        break;
                    case 1:
                        image.setImageResource(R.drawable.wallet);
                        iconTitle.setTextColor(ResourcesCompat.getColor(getResources(), R.color.darkBlack, null));
                        break;
                    case 2:
                        image.setImageResource(R.drawable.tab_budget);
                        iconTitle.setTextColor(ResourcesCompat.getColor(getResources(), R.color.darkBlack, null));
                        break;
                    case 3:
                        image.setImageResource(R.drawable.pie_chart);
                        iconTitle.setTextColor(ResourcesCompat.getColor(getResources(), R.color.darkBlack, null));
                        break;
                    case 4:
                        image.setImageResource(R.drawable.more);
                        iconTitle.setTextColor(ResourcesCompat.getColor(getResources(), R.color.darkBlack, null));
                        break;
                }
            } else {
                switch (i) {
                    case 0:
                        image.setImageResource(R.drawable.note_selected);
                        iconTitle.setTextColor(ResourcesCompat.getColor(getResources(), R.color.niceBlue, null));
                        break;
                    case 1:
                        image.setImageResource(R.drawable.wallet_selected);
                        iconTitle.setTextColor(ResourcesCompat.getColor(getResources(), R.color.niceBlue, null));
                        break;
                    case 2:
                        image.setImageResource(R.drawable.tab_budget_selected);
                        iconTitle.setTextColor(ResourcesCompat.getColor(getResources(), R.color.niceBlue, null));
                        break;
                    case 3:
                        image.setImageResource(R.drawable.pie_chart_selected);
                        iconTitle.setTextColor(ResourcesCompat.getColor(getResources(), R.color.niceBlue, null));
                        break;
                    case 4:
                        image.setImageResource(R.drawable.more_selected);
                        iconTitle.setTextColor(ResourcesCompat.getColor(getResources(), R.color.niceBlue, null));
                        break;
                }
            }
        }
    }

    private View getTabIndicator(Context context, int title, int icon) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_icon, null);
        ImageView iv = (ImageView) view.findViewById(R.id.img_icon);
        iv.setImageResource(icon);
        TextView tv = (TextView) view.findViewById(R.id.text_icon);
        tv.setText(title);
        if (title == R.string.Records) {
            tv.setTextColor(ResourcesCompat.getColor(getResources(), R.color.niceBlue, null));
        }
        return view;
    }

    public void replaceFragment(Fragment someFragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //if (true) allow back else pop everything
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        } else {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        fragmentTransaction.replace(R.id.realtabcontent, someFragment, "SomeFragment");
        fragmentTransaction.commit();
    }

}
