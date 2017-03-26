package com.example.nguyennam.financialbook.model;


public class BudgetRecyclerView {
    private int id;
    private String txtBudgetName;
    private String txtBudgetMoney;

    public BudgetRecyclerView(){

    }

    public BudgetRecyclerView(int id, String txtBudgetName, String txtBudgetMoney) {
        this.id = id;
        this.txtBudgetName = txtBudgetName;
        this.txtBudgetMoney = txtBudgetMoney;
    }

    public BudgetRecyclerView(String txtBudgetName, String txtBudgetMoney) {
        this.txtBudgetName = txtBudgetName;
        this.txtBudgetMoney = txtBudgetMoney;
    }

    public String toString() {
        return id + ";" + txtBudgetName +";" + txtBudgetMoney;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTxtBudgetName() {
        return txtBudgetName;
    }

    public void setTxtBudgetName(String txtBudgetName) {
        this.txtBudgetName = txtBudgetName;
    }

    public String getTxtBudgetMoney() {
        return txtBudgetMoney;
    }

    public void setTxtBudgetMoney(String txtBudgetMoney) {
        this.txtBudgetMoney = txtBudgetMoney;
    }
}
