package com.example.nguyennam.financialbook.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.model.FinancialHistoryChild;
import com.example.nguyennam.financialbook.model.FinancialHistoryGroup;

import java.util.ArrayList;

public class FinancialHistoryAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<FinancialHistoryGroup> financialHistoryGroups;
    private ArrayList<FinancialHistoryGroup> originalList;

    public FinancialHistoryAdapter(Context context, ArrayList<FinancialHistoryGroup> financialHistoryGroups){
        this.context = context;
        this.financialHistoryGroups = new ArrayList<>();
        this.financialHistoryGroups.addAll(financialHistoryGroups);
        this.originalList = new ArrayList<>();
        this.originalList.addAll(financialHistoryGroups);
    }

    @Override
    public int getGroupCount() {
        return financialHistoryGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<FinancialHistoryChild> financialHistoryChildList = financialHistoryGroups.get(groupPosition).getFinancialHistoryChildList();
        return financialHistoryChildList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return financialHistoryGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<FinancialHistoryChild> financialHistoryChildList = financialHistoryGroups.get(groupPosition).getFinancialHistoryChildList();
        return financialHistoryChildList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        FinancialHistoryGroup financialHistoryGroup = (FinancialHistoryGroup) getGroup(groupPosition);
//        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if ("0".equals(financialHistoryGroup.getMoneyExpense())) {
                convertView = layoutInflater.inflate(R.layout.financial_history_incomegroup, parent, false);
                TextView txtIncome = (TextView) convertView.findViewById(R.id.txtIncome);
                txtIncome.setText(financialHistoryGroup.getMoneyIncome().trim());
            } else if ("0".equals(financialHistoryGroup.getMoneyIncome())) {
                convertView = layoutInflater.inflate(R.layout.financial_history_expensegroup, parent, false);
                TextView txtExpense = (TextView) convertView.findViewById(R.id.txtExpense);
                txtExpense.setText(financialHistoryGroup.getMoneyExpense().trim());
            } else {
                convertView = layoutInflater.inflate(R.layout.financial_history_group, parent, false);
                TextView txtIncome = (TextView) convertView.findViewById(R.id.txtIncome);
                txtIncome.setText(financialHistoryGroup.getMoneyIncome().trim());
                TextView txtExpense = (TextView) convertView.findViewById(R.id.txtExpense);
                txtExpense.setText(financialHistoryGroup.getMoneyExpense().trim());
            }
//        }
        TextView dateOfMonth = (TextView) convertView.findViewById(R.id.dateOfMonth);
        dateOfMonth.setText(financialHistoryGroup.getDateOfMonth().trim());
        TextView dateOfWeek = (TextView) convertView.findViewById(R.id.dateOfWeek);
        dateOfWeek.setText(financialHistoryGroup.getDateOfWeek().trim());
        TextView date = (TextView) convertView.findViewById(R.id.date);
        date.setText(financialHistoryGroup.getDate().trim());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        FinancialHistoryChild financialHistoryChild = (FinancialHistoryChild) getChild(groupPosition, childPosition);
//        if (convertView == null){
            LayoutInflater  layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (financialHistoryChild.isExpense()) {
                convertView = layoutInflater.inflate(R.layout.financial_history_expensechild, parent, false);
            } else {
                convertView = layoutInflater.inflate(R.layout.financial_history_incomechild, parent, false);
            }
//        }
        TextView txtCategory = (TextView) convertView.findViewById(R.id.txtCategory);
        txtCategory.setText(financialHistoryChild.getCategory().trim());
        TextView txtDescription = (TextView) convertView.findViewById(R.id.txtDescription);
        txtDescription.setText(financialHistoryChild.getDescription().trim());
        TextView txtMoney = (TextView) convertView.findViewById(R.id.txtMoney);
        txtMoney.setText(financialHistoryChild.getMoneyAmount().trim());
        TextView txtAccountType = (TextView) convertView.findViewById(R.id.txtAccountType);
        txtAccountType.setText(financialHistoryChild.getAccount().trim());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void filterData(String query){
        query = query.toLowerCase();
        Log.v("MyListAdapter", String.valueOf(financialHistoryGroups.size()));
        financialHistoryGroups.clear();
        if(query.isEmpty()){
            financialHistoryGroups.addAll(originalList);
        }
        else {
            for(FinancialHistoryGroup financialHistoryGroup: originalList){
                ArrayList<FinancialHistoryChild> financialHistoryChildList = financialHistoryGroup.getFinancialHistoryChildList();
                ArrayList<FinancialHistoryChild> newList = new ArrayList<>();
                for(FinancialHistoryChild financialHistoryChild: financialHistoryChildList){
                    if(financialHistoryChild.getCategory().toLowerCase().contains(query)){
                        newList.add(financialHistoryChild);
                    }
                }
                if(newList.size() > 0){
                    FinancialHistoryGroup ncategoryGroup =
                            new FinancialHistoryGroup(financialHistoryGroup.getDateOfWeek(),financialHistoryGroup.getDateOfMonth(),
                                    financialHistoryGroup.getDate(),financialHistoryGroup.getMoneyExpense(),financialHistoryGroup.getMoneyIncome(),newList);
                    financialHistoryGroups.add(ncategoryGroup);
                }
            }
        }
        Log.v("MyListAdapter", String.valueOf(financialHistoryGroups.size()));
        notifyDataSetChanged();
    }
}
