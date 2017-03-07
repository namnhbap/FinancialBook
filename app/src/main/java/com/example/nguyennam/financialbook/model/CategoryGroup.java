package com.example.nguyennam.financialbook.model;

import java.util.ArrayList;

/**
 * Created by NguyenNam on 1/14/2017.
 */

public class CategoryGroup {
    private String name;
    private ArrayList<CategoryChild> categoryChildList = new ArrayList<CategoryChild>();

    public CategoryGroup(String name, ArrayList<CategoryChild> categoryChildList) {
        this.name = name;
        this.categoryChildList = categoryChildList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<CategoryChild> getCategoryChildList() {
        return categoryChildList;
    }

    public void setCategoryChildList(ArrayList<CategoryChild> categoryChildList) {
        this.categoryChildList = categoryChildList;
    }
}
