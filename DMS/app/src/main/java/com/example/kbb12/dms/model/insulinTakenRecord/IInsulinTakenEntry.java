package com.example.kbb12.dms.model.insulinTakenRecord;

import com.example.kbb12.dms.takeInsulin.model.TakeInsulinReadModel;

import java.util.Calendar;

/**
 * Created by kbb12 on 24/03/2017.
 */

public interface IInsulinTakenEntry {
    Calendar getTime();
    TakeInsulinReadModel.InsulinType getType();
    float getAmount();
}
