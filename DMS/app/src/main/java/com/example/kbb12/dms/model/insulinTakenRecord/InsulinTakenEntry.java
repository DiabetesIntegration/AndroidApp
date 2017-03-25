package com.example.kbb12.dms.model.insulinTakenRecord;

import com.example.kbb12.dms.takeInsulin.model.TakeInsulinReadModel;

import java.util.Calendar;

/**
 * Created by kbb12 on 24/03/2017.
 */

public class InsulinTakenEntry implements IInsulinTakenEntry {

    private Calendar time;
    private TakeInsulinReadModel.InsulinType type;
    private Float amount;

    public InsulinTakenEntry(Calendar time, TakeInsulinReadModel.InsulinType type,Float amount){
        this.time=time;
        this.type=type;
        this.amount=amount;
    }

    public Calendar getTime(){
        return time;
    }

    public TakeInsulinReadModel.InsulinType getType(){
        return type;
    }
    public Float getAmount(){
        return amount;
    }
}
