package com.example.kbb12.dms.InsulinModelBuilder.Controller;

import android.view.View;
import android.widget.AdapterView;

import com.example.kbb12.dms.InsulinModelBuilder.Model.InsulinReadWriteModel;
import com.example.kbb12.dms.InsulinModelBuilder.View.InsulinEntry;

/**
 * Created by kbb12 on 27/01/2017.
 */
public class TypeListener implements AdapterView.OnItemSelectedListener {

    private int entryNumber;
    private InsulinReadWriteModel model;

    public TypeListener(int entryNumber,InsulinReadWriteModel model){
        this.entryNumber=entryNumber;
        this.model=model;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                model.setType(entryNumber, InsulinEntry.InsulinType.NOT_SET);
                break;
            case 1:
                model.setType(entryNumber, InsulinEntry.InsulinType.LONG_ACTING);
                break;
            case 2:
                model.setType(entryNumber, InsulinEntry.InsulinType.SHORT_ACTING);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
