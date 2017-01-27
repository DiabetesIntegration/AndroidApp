package com.example.kbb12.dms.Model.Insulin;

/**
 * Created by kbb12 on 27/01/2017.
 */
public class ShortActingDose extends InsulinDose{
    public ShortActingDose(String name){
        super(name);
    }

    @Override
    public InsulinType getType() {
        return InsulinType.SHORT_ACTING;
    }
}
