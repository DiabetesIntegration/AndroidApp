package com.example.kbb12.dms.basalInsulinModelBuilder.model;

import com.example.kbb12.dms.baseScreen.model.ErrorReadWriteModel;
import com.example.kbb12.dms.model.basalInsulinModel.DuplicateDoseException;

/**
 * Created by kbb12 on 27/01/2017.
 */
public interface BasalInsulinReadWriteModel extends BasalInsulinReadModel,ErrorReadWriteModel {
    void setHour(int hour);
    void setMinute(int minute);
    void deselectTime();
    void setSelectedTime(int entryNumber);
    void setDoseToBeDeleted(int entryNumber);
    void cancelDelete();
    void deleteDose();
    void setDose(double dose,int entryNumber);
    void setBasalBrandName(String brandName);
    void cancelSelection();
    void saveDoses() throws DuplicateDoseException;
}
