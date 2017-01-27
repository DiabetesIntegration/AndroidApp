package com.example.kbb12.dms.Model.Insulin;

import com.example.kbb12.dms.InsulinModelBuilder.View.InsulinEntry;

/**
 * Created by kbb12 on 27/01/2017.
 */
public abstract class InsulinDose implements InsulinEntry {
    private String brandName;

    public InsulinDose(String name){
        this.brandName=name;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
