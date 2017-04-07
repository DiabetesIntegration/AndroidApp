package com.example.kbb12.dms.model.database.insulinTakenRecord;

import com.example.kbb12.dms.individualScreens.takeInsulin.model.TakeInsulinReadModel;

import java.util.Calendar;

/**
 * Created by kbb12 on 24/03/2017.
 */

public interface IInsulinTakenEntry {
    Calendar getTime();
    TakeInsulinReadModel.InsulinType getType();
    float getAmount();
}
