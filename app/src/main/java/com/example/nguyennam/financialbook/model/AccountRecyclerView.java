package com.example.nguyennam.financialbook.model;


public class AccountRecyclerView {
    private int id;
    private String accountName;
    private String accountType;
    private String moneyType;
    private String moneyStart;
    private String description;
    private String amountMoney;

    public AccountRecyclerView(){

    }

    public AccountRecyclerView(int id, String accountName, String accountType, String moneyType, String moneyStart, String description, String amountMoney) {
        this.id = id;
        this.accountName = accountName;
        this.accountType = accountType;
        this.moneyType = moneyType;
        this.moneyStart = moneyStart;
        this.description = description;
        this.amountMoney = amountMoney;
    }

    public AccountRecyclerView(String accountName, String accountType, String moneyType, String moneyStart, String description, String amountMoney) {
        this.accountName = accountName;
        this.accountType = accountType;
        this.moneyType = moneyType;
        this.moneyStart = moneyStart;
        this.description = description;
        this.amountMoney = amountMoney;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }

    public String getMoneyStart() {
        return moneyStart;
    }

    public void setMoneyStart(String moneyStart) {
        this.moneyStart = moneyStart;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(String amountMoney) {
        this.amountMoney = amountMoney;
    }
}
