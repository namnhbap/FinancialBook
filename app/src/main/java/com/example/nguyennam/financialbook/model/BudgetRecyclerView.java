package com.example.nguyennam.financialbook.model;


public class BudgetRecyclerView {
    private int id;
    private String budgetName;
    private String amountMoney;
    private int accountID;
    private String category;
    private String date;
    private String remainMoney;
    private String expenseMoney;

    public BudgetRecyclerView(){

    }

    public BudgetRecyclerView(int id, String budgetName, String amountMoney,
                              int accountID, String category, String date,
                              String remainMoney, String expenseMoney) {
        this.id = id;
        this.budgetName = budgetName;
        this.amountMoney = amountMoney;
        this.accountID = accountID;
        this.category = category;
        this.date = date;
        this.remainMoney = remainMoney;
        this.expenseMoney = expenseMoney;
    }

    public BudgetRecyclerView(String budgetName, String amountMoney,
                              int accountID, String category, String date,
                              String remainMoney, String expenseMoney) {
        this.budgetName = budgetName;
        this.amountMoney = amountMoney;
        this.accountID = accountID;
        this.category = category;
        this.date = date;
        this.remainMoney = remainMoney;
        this.expenseMoney = expenseMoney;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBudgetName() {
        return budgetName;
    }

    public void setBudgetName(String budgetName) {
        this.budgetName = budgetName;
    }

    public String getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(String amountMoney) {
        this.amountMoney = amountMoney;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
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

    public String getRemainMoney() {
        return remainMoney;
    }

    public void setRemainMoney(String remainMoney) {
        this.remainMoney = remainMoney;
    }

    public String getExpenseMoney() {
        return expenseMoney;
    }

    public void setExpenseMoney(String expenseMoney) {
        this.expenseMoney = expenseMoney;
    }

}
