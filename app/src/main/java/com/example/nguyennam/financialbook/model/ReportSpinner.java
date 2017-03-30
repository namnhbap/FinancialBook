package com.example.nguyennam.financialbook.model;

public class ReportSpinner {
    private  String reportName ="";

    public ReportSpinner(String reportName) {
        this.reportName = reportName;
    }

    public void setReportName(String reportName)
    {
        this.reportName = reportName;
    }

    public String getReportName()
    {
        return this.reportName;
    }

}