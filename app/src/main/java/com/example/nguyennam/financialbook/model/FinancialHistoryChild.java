package com.example.nguyennam.financialbook.model;

public class FinancialHistoryChild {

    private int id;
    private boolean isExpense;
    private String moneyAmount;
    private String account;
    private String category;
    private String description;

//    public FinancialHistoryChild(boolean isExpense, String moneyAmount, String account, String category, String description) {
//        this.isExpense = isExpense;
//        this.moneyAmount = moneyAmount;
//        this.account = account;
//        this.category = category;
//        this.description = description;
//    }


    public FinancialHistoryChild(boolean isExpense, String moneyAmount, String account, String category, String description, int id) {
        this.isExpense = isExpense;
        this.moneyAmount = moneyAmount;
        this.account = account;
        this.category = category;
        this.description = description;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isExpense() {
        return isExpense;
    }

    public void setExpense(boolean expense) {
        this.isExpense = expense;
    }

    public String getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(String moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
