package com.example.nguyennam.financialbook.recordtab;

public class SpinnerModel {
    private  String recordName ="";
    private  String description ="";

    public SpinnerModel(String recordName, String description) {
        this.recordName = recordName;
        this.description = description;
    }

    /*********** Set Methods ******************/
    public void setRecordName(String recordName)
    {
        this.recordName = recordName;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    /*********** Get Methods ****************/
    public String getRecordName()
    {
        return this.recordName;
    }

    public String getDescription()
    {
        return this.description;
    }
}