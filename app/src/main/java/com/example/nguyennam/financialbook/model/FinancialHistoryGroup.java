package com.example.nguyennam.financialbook.model;

import java.util.ArrayList;

public class FinancialHistoryGroup {

    private String dateOfWeek;
    private String dateOfMonth;
    private String date;
    private String moneyExpense;
    private String moneyIncome;
    private ArrayList<FinancialHistoryChild> financialHistoryChildList = new ArrayList<>();

    public FinancialHistoryGroup(String dateOfWeek, String dateOfMonth, String date,
                                 String moneyExpense, String moneyIncome, ArrayList<FinancialHistoryChild> financialHistoryChildList) {
        this.dateOfWeek = dateOfWeek;
        this.dateOfMonth = dateOfMonth;
        this.date = date;
        this.moneyExpense = moneyExpense;
        this.moneyIncome = moneyIncome;
        this.financialHistoryChildList = financialHistoryChildList;
    }

    public String getDateOfWeek() {
        return dateOfWeek;
    }

    public void setDateOfWeek(String dateOfWeek) {
        this.dateOfWeek = dateOfWeek;
    }

    public String getDateOfMonth() {
        return dateOfMonth;
    }

    public void setDateOfMonth(String dateOfMonth) {
        this.dateOfMonth = dateOfMonth;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMoneyExpense() {
        return moneyExpense;
    }

    public void setMoneyExpense(String moneyExpense) {
        this.moneyExpense = moneyExpense;
    }

    public String getMoneyIncome() {
        return moneyIncome;
    }

    public void setMoneyIncome(String moneyIncome) {
        this.moneyIncome = moneyIncome;
    }

    public ArrayList<FinancialHistoryChild> getFinancialHistoryChildList() {
        return financialHistoryChildList;
    }

    public void setFinancialHistoryChildList(ArrayList<FinancialHistoryChild> financialHistoryChildList) {
        this.financialHistoryChildList = financialHistoryChildList;
    }
}
