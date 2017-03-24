package com.example.nguyennam.financialbook.model;

public class Expense {
    private int _id;
    private String _amountMoney;
    private String _expenseCategory;
    private String _description;
    private String _fromAccount;
    private String _expenseDate;
    private String _expenseEvent;

    public Expense(){

    }

    public Expense(int _id, String _amountMoney, String _expenseCategory, String _description, String _fromAccount, String _expenseDate, String _expenseEvent) {
        this._id = _id;
        this._amountMoney = _amountMoney;
        this._expenseCategory = _expenseCategory;
        this._description = _description;
        this._fromAccount = _fromAccount;
        this._expenseDate = _expenseDate;
        this._expenseEvent = _expenseEvent;
    }
//
//    public Expense(String _amountMoney, String _expenseCategory, String _description, String _fromAccount, String _expenseDate, String _expenseEvent) {
//        this._amountMoney = _amountMoney;
//        this._expenseCategory = _expenseCategory;
//        this._description = _description;
//        this._fromAccount = _fromAccount;
//        this._expenseDate = _expenseDate;
//        this._expenseEvent = _expenseEvent;
//    }

    public String toString() {
        return _id + ";" + _amountMoney +";" + _expenseCategory
                + ";" + _description +";" + _fromAccount
                + ";" + _expenseDate +";" + _expenseEvent;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_amountMoney() {
        return _amountMoney;
    }

    public void set_amountMoney(String _amountMoney) {
        this._amountMoney = _amountMoney;
    }

    public String get_expenseCategory() {
        return _expenseCategory;
    }

    public void set_expenseCategory(String _expenseCategory) {
        this._expenseCategory = _expenseCategory;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public String get_fromAccount() {
        return _fromAccount;
    }

    public void set_fromAccount(String _fromAccount) {
        this._fromAccount = _fromAccount;
    }

    public String get_expenseDate() {
        return _expenseDate;
    }

    public void set_expenseDate(String _expenseDate) {
        this._expenseDate = _expenseDate;
    }

    public String get_expenseEvent() {
        return _expenseEvent;
    }

    public void set_expenseEvent(String _expenseEvent) {
        this._expenseEvent = _expenseEvent;
    }
}
