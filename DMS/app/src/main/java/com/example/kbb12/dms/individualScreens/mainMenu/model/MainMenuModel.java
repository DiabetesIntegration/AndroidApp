package com.example.kbb12.dms.individualScreens.mainMenu.model;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseModel;
import com.example.kbb12.dms.model.IBloodGlucoseModel;
import com.example.kbb12.dms.database.bloodGlucoseRecord.BGReading;

import java.util.Calendar;
import java.util.List;

/**
 * Created by kbb12 on 07/04/2017.
 */

public class MainMenuModel extends BaseModel implements MainMenuReadWriteModel {
    private IBloodGlucoseModel model;

    public MainMenuModel(IBloodGlucoseModel model){
        this.model=model;
    }


    @Override
    public void addRawData(Calendar c, String data) {
        model.addRawData(c,data);
        notifyObserver();
    }

    @Override
    public void addHistoryReading(Calendar c, double reading) {
        model.addHistoryReading(c,reading);
        notifyObserver();
    }

    @Override
    public void addCurrentReading(Calendar c, double reading) {
        model.addCurrentReading(c,reading);
        notifyObserver();
    }

    @Override
    public BGReading getMostRecentHistoryReading() {
        return model.getMostRecentHistoryReading();
    }

    @Override
    public List<BGReading> getHistoryBetween(Calendar from, Calendar to) {
        return model.getHistoryBetween(from,to);
    }
}
