package com.example.nguyennam.financialbook.model;


public class CategoryIncome {
    private String name;
    private String money;
    private String percent;

    public CategoryIncome(String name, String money, String percent) {
        this.name = name;
        this.money = money;
        this.percent = percent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
