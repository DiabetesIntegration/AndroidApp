package com.example.kbb12.dms.individualScreens.mainMenu.model;

import com.example.kbb12.dms.database.bloodGlucoseRecord.BGReading;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseReadWriteModel;

import java.util.Calendar;

/**
 * Created by kbb12 on 07/04/2017.
 */

public interface MainMenuReadWriteModel extends BaseReadWriteModel,MainMenuReadModel {
    void addRawData(Calendar c, String data);
    void addHistoryReading(Calendar c, double reading);
    void addCurrentReading(Calendar c, double reading);
    BGReading getMostRecentHistoryReading();
}
