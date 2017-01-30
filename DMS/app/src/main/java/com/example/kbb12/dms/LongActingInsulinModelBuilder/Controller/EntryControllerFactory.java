package com.example.kbb12.dms.LongActingInsulinModelBuilder.Controller;

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
    public AdapterView.OnItemSelectedListener createTimeEntryListener(int entryNumber) {
        return new TimeEntryListener(entryNumber,model);
    }

    @Override
    public TextWatcher createAmountListener(int entryNumber){
        return new AmountListener(entryNumber,model);
    }
}
