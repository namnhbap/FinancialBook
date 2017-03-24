package com.example.nguyennam.financialbook.model;

public class FinancialHistoryChild {

    private String moneyAmount;
    private String account;
    private String category;
    private String description;

    public FinancialHistoryChild(String description, String moneyAmount, String account, String category) {
        this.description = description;
        this.moneyAmount = moneyAmount;
        this.account = account;
        this.category = category;
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
