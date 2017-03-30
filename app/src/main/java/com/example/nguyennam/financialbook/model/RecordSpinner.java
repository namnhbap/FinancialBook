package com.example.nguyennam.financialbook.model;

public class RecordSpinner {
    private  String recordName ="";
    private  String description ="";

    public RecordSpinner(String recordName, String description) {
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