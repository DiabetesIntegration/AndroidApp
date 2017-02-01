package com.example.kbb12.dms.LongActingInsulinModelBuilder.Controller;

import android.app.TimePickerDialog;
import android.text.TextWatcher;
import android.widget.AdapterView;

import com.example.kbb12.dms.LongActingInsulinModelBuilder.Model.LongActingInsulinReadWriteModel;


/**
 * Created by kbb12 on 20/01/2017.
 */
public class EntryControllerFactory implements IEntryControllerFactory {

    LongActingInsulinReadWriteModel model;

    public EntryControllerFactory(LongActingInsulinReadWriteModel model){
        this.model=model;
    }

    @Override
    public AdapterView.OnClickListener createTimeEntryListener(int entryNumber) {
        return new TimeEntryListener(entryNumber,model);
    }

    @Override
    public TextWatcher createDoseListener(int entryNumber){
        return new DoseListener(entryNumber,model);
    }

    @Override
    public TimePickerDialog.OnTimeSetListener createTimeChangeListener() {
        return new TimeChangeListener(model);
    }
}
