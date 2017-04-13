package com.example.kbb12.dms.individualScreens.mainMenu.model;

import com.example.kbb12.dms.database.bloodGlucoseRecord.BGReading;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseReadModel;

import java.util.Calendar;
import java.util.List;

/**
 * Created by kbb12 on 07/04/2017.
 */

public interface MainMenuReadModel extends BaseReadModel {
    List<BGReading> getHistoryBetween(Calendar from, Calendar to);
}
