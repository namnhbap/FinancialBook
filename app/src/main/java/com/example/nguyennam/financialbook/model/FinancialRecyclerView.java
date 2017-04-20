package com.example.nguyennam.financialbook.model;

public class FinancialRecyclerView {
    private int id;
    private String financial;
    private String category;
    private String date;
    private String amountMoney;
    private String remainMoney;

    public FinancialRecyclerView(){

    }

    public FinancialRecyclerView(int id, String financial, String category, String date, String amountMoney, String remainMoney) {
        this.id = id;
        this.financial = financial;
        this.category = category;
        this.date = date;
        this.amountMoney = amountMoney;
        this.remainMoney = remainMoney;
    }

    public FinancialRecyclerView(String financial, String category, String date, String amountMoney, String remainMoney) {
        this.financial = financial;
        this.category = category;
        this.date = date;
        this.amountMoney = amountMoney;
        this.remainMoney = remainMoney;
    }

    public String toString() {
        return id + ";" + financial +";" + category
                + ";" + date +";" + amountMoney
                + ";" + remainMoney ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFinancial() {
        return financial;
    }

    public void setFinancial(String financial) {
        this.financial = financial;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(String amountMoney) {
        this.amountMoney = amountMoney;
    }

    public String getRemainMoney() {
        return remainMoney;
    }

    public void setRemainMoney(String remainMoney) {
        this.remainMoney = remainMoney;
    }
}
