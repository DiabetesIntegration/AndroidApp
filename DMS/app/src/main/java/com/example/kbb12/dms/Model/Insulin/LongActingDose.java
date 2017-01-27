package com.example.kbb12.dms.Model.Insulin;


public class LongActingDose extends InsulinDose {
    public LongActingDose(String name){
        super(name);
    }

    @Override
    public InsulinType getType() {
        return InsulinType.SHORT_ACTING;
    }
}
