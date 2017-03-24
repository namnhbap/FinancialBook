package com.example.nguyennam.financialbook.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguyennam.financialbook.R;
import com.example.nguyennam.financialbook.model.CategoryChild;
import com.example.nguyennam.financialbook.model.CategoryGroup;

import java.util.ArrayList;

public class ListCategoryAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<CategoryGroup> categoryGroups;
    private ArrayList<CategoryGroup> originalList;

    public ListCategoryAdapter(Context context, ArrayList<CategoryGroup> categoryGroups){
        this.context = context;
        this.categoryGroups = new ArrayList<>();
        this.categoryGroups.addAll(categoryGroups);
        this.originalList = new ArrayList<>();
        this.originalList.addAll(categoryGroups);
    }
    @Override
    public int getGroupCount() {
        return categoryGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<CategoryChild> categoryChildArrayList = categoryGroups.get(groupPosition).getCategoryChildList();
        return categoryChildArrayList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return categoryGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<CategoryChild> categoryChildList = categoryGroups.get(groupPosition).getCategoryChildList();
        return categoryChildList.get(childPosition);
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
        CategoryGroup categoryGroup = (CategoryGroup) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater  layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.category_group, parent, false);
        }
        ImageView imgGroup = (ImageView) convertView.findViewById(R.id.imgGroup);
        if (isExpanded) {
            imgGroup.setImageResource(R.drawable.group_expand);
        } else {
            imgGroup.setImageResource(R.drawable.group_collapse);
        }
        TextView name = (TextView) convertView.findViewById(R.id.groupname);
        name.setText(categoryGroup.getName().trim());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        CategoryChild categoryChild = (CategoryChild) getChild(groupPosition, childPosition);
        if (convertView == null){
            LayoutInflater  layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.category_child, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.childrow);
        name.setText(categoryChild.getName().trim());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void filterData(String query){
        query = query.toLowerCase();
        Log.v("MyListAdapter", String.valueOf(categoryGroups.size()));
        categoryGroups.clear();
        if(query.isEmpty()){
            categoryGroups.addAll(originalList);
        }
        else {
            for(CategoryGroup categoryGroup: originalList){

                ArrayList<CategoryChild> categoryChildArrayList = categoryGroup.getCategoryChildList();
                ArrayList<CategoryChild> newList = new ArrayList<>();
                for(CategoryChild categoryChildList: categoryChildArrayList){
                    if(categoryChildList.getName().toLowerCase().contains(query)){
                        newList.add(categoryChildList);
                    }
                }
                if(newList.size() > 0){
                    CategoryGroup ncategoryGroup = new CategoryGroup(categoryGroup.getName(),newList);
                    categoryGroups.add(ncategoryGroup);
                }
            }
        }
        Log.v("MyListAdapter", String.valueOf(categoryGroups.size()));
        notifyDataSetChanged();
    }
}
