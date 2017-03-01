package com.example.kbb12.dms.LongActingInsulinModelBuilder.Model;

import com.example.kbb12.dms.ErrorHandling.ErrorReadWriteModel;
import com.example.kbb12.dms.Model.LongActingInsulinModel.DuplicateDoseException;

/**
 * Created by kbb12 on 27/01/2017.
 */
public interface LongActingInsulinReadWriteModel extends LongActingInsulinReadModel,ErrorReadWriteModel {
    void setHour(int hour);
    void setMinute(int minute);
    void deselectTime();
    void setSelectedTime(int entryNumber);
    void setDoseToBeDeleted(int entryNumber);
    void cancelDelete();
    void deleteDose();
    void setDose(double dose,int entryNumber);
    void setLongActingBrandName(String brandName);
    void saveDoses() throws DuplicateDoseException;
}
