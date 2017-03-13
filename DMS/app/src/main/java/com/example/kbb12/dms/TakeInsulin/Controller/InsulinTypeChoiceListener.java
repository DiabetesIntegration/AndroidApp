package com.example.kbb12.dms.takeInsulin.controller;

import android.view.View;
import android.widget.AdapterView;

import com.example.kbb12.dms.takeInsulin.model.TakeInsulinReadModel;
import com.example.kbb12.dms.takeInsulin.model.TakeInsulinReadWriteModel;

/**
 * Created by kbb12 on 24/02/2017.
 */
public class InsulinTypeChoiceListener implements AdapterView.OnItemSelectedListener {

    TakeInsulinReadWriteModel model;

    public InsulinTypeChoiceListener(TakeInsulinReadWriteModel model){
        this.model=model;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                model.setTypeTaken(TakeInsulinReadModel.InsulinType.NOT_SET);
                break;
            case 1:
                model.setTypeTaken(TakeInsulinReadModel.InsulinType.SHORT_ACTING);
                break;
            case 2:
                model.setTypeTaken(TakeInsulinReadModel.InsulinType.BASAL);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
