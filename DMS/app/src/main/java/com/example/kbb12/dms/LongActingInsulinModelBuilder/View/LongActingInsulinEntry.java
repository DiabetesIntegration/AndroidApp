package com.example.kbb12.dms.LongActingInsulinModelBuilder.View;

/**
 * Created by kbb12 on 27/01/2017.
 */
public interface LongActingInsulinEntry {

    public enum InsulinType{
        NOT_SET,LONG_ACTING,SHORT_ACTING
    }

    public InsulinType getType();

    public String getBrandName();
}
