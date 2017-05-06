package com.example.nguyennam.financialbook.model;


public class ReportYear {
    private String year;
    private String moneyIncome;
    private String moneyExpense;

    public ReportYear(String year, String moneyIncome, String moneyExpense) {
        this.year = year;
        this.moneyIncome = moneyIncome;
        this.moneyExpense = moneyExpense;
    }

    public ReportYear(String year, String moneyExpense) {
        this.year = year;
        this.moneyExpense = moneyExpense;
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
