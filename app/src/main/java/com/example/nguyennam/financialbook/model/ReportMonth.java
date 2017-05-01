package com.example.nguyennam.financialbook.model;


public class ReportMonth {
    private String month;
    private String year;
    private String moneyIncome;
    private String moneyExpense;

    public ReportMonth(String month, String year, String moneyIncome, String moneyExpense) {
        this.month = month;
        this.year = year;
        this.moneyIncome = moneyIncome;
        this.moneyExpense = moneyExpense;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMoneyIncome() {
        return moneyIncome;
    }

    public void setMoneyIncome(String moneyIncome) {
        this.moneyIncome = moneyIncome;
    }

    public String getMoneyExpense() {
        return moneyExpense;
    }

    public void setMoneyExpense(String moneyExpense) {
        this.moneyExpense = moneyExpense;
    }
}
