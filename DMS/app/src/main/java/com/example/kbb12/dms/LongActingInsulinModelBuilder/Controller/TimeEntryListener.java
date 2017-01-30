package com.example.kbb12.dms.LongActingInsulinModelBuilder.Controller;

import android.view.View;
import android.widget.AdapterView;

import com.example.kbb12.dms.LongActingInsulinModelBuilder.Model.LongActingInsulinReadWriteModel;
import com.example.kbb12.dms.LongActingInsulinModelBuilder.View.LongActingInsulinEntry;

/**
 * Created by kbb12 on 27/01/2017.
 */
public class TimeEntryListener implements AdapterView.OnItemSelectedListener {

    private int entryNumber;
    private LongActingInsulinReadWriteModel model;
    private int used;

    public TimeEntryListener(int entryNumber, LongActingInsulinReadWriteModel model){
        this.entryNumber=entryNumber;
        this.model=model;
        this.used=0;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(used>1){
            return;
        }
        switch (position){
            case 0:
                model.setType(entryNumber, LongActingInsulinEntry.InsulinType.NOT_SET);
                break;
            case 1:
                model.setType(entryNumber, LongActingInsulinEntry.InsulinType.SHORT_ACTING);
                break;
            case 2:
                model.setType(entryNumber, LongActingInsulinEntry.InsulinType.LONG_ACTING);
                break;
        }
        used++;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
