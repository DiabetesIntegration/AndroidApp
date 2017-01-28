package com.example.kbb12.dms.Model.Insulin;

import com.example.kbb12.dms.InsulinModelBuilder.View.InsulinEntry;

/**
 * Created by kbb12 on 27/01/2017.
 */
public class InsulinDose implements InsulinEntry {
    private String brandName;
    private InsulinType type;

    public InsulinDose(InsulinType type){
        this.type=type;
        this.brandName=null;
    }

    public InsulinDose(InsulinType type,String brandName){
        this.brandName=brandName;
        this.type=type;
    }

    public InsulinDose clone(){
        return new InsulinDose(type,brandName);
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Override
    public InsulinType getType() {
        return type;
    }

    public void setType(InsulinType type) {
        this.type = type;
    }
}
