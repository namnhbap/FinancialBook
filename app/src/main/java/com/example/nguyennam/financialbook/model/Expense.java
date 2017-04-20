package com.example.nguyennam.financialbook.model;

public class Expense {
    private int _id;
    private String _amountMoney;
    private String _category;
    private String _description;
    private int _accountID;
    private String _date;
    private String _event;

    public Expense(){

    }

    public Expense(int _id, String _amountMoney, String _category, String _description, int _accountID, String _date, String _event) {
        this._id = _id;
        this._amountMoney = _amountMoney;
        this._category = _category;
        this._description = _description;
        this._accountID = _accountID;
        this._date = _date;
        this._event = _event;
    }

    public Expense(String _amountMoney, String _category, String _description, int _accountID, String _date, String _event) {
        this._amountMoney = _amountMoney;
        this._category = _category;
        this._description = _description;
        this._accountID = _accountID;
        this._date = _date;
        this._event = _event;
    }

    public String toString() {
        return _id + ";" + _amountMoney +";" + _category
                + ";" + _description +";" + _accountID
                + ";" + _date +";" + _event;
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

    public String get_category() {
        return _category;
    }

    public void set_category(String _category) {
        this._category = _category;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public int get_accountID() {
        return _accountID;
    }

    public void set_accountID(int _accountID) {
        this._accountID = _accountID;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public String get_event() {
        return _event;
    }

    public void set_event(String _event) {
        this._event = _event;
    }
}
