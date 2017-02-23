package com.example.kbb12.dms.LongActingInsulinModelBuilder.Controller;

import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;

import com.example.kbb12.dms.LongActingInsulinModelBuilder.Model.LongActingInsulinReadWriteModel;

/**
 * Created by kbb12 on 23/02/2017.
 */
public class ConfirmDeleteListener implements DialogInterface.OnClickListener {

    private LongActingInsulinReadWriteModel model;

    public ConfirmDeleteListener(LongActingInsulinReadWriteModel model){
        this.model=model;
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        model.deleteDose();
    }
}
