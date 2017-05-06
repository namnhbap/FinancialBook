package com.example.nguyennam.financialbook.model;


public class ReportQuarter {
    private String quarter;
    private String year;
    private String moneyIncome;
    private String moneyExpense;

    public ReportQuarter(String quarter, String year, String moneyIncome, String moneyExpense) {
        this.quarter = quarter;
        this.year = year;
        this.moneyIncome = moneyIncome;
        this.moneyExpense = moneyExpense;
    }

    public ReportQuarter(String quarter, String year, String moneyExpense) {
        this.quarter = quarter;
        this.year = year;
        this.moneyExpense = moneyExpense;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
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
