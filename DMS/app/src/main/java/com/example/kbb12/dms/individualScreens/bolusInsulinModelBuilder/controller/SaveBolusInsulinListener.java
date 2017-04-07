package com.example.kbb12.dms.individualScreens.bolusInsulinModelBuilder.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.kbb12.dms.individualScreens.bolusInsulinModelBuilder.model.BolusInsulinReadWriteModel;
import com.example.kbb12.dms.individualScreens.mainMenu.MainMenuActivity;

/**
 * Created by kbb12 on 11/03/2017.
 */
public class SaveBolusInsulinListener implements View.OnClickListener {

    private BolusInsulinReadWriteModel model;
    private Activity context;

    public SaveBolusInsulinListener(BolusInsulinReadWriteModel model,Activity context){
        this.model=model;
        this.context=context;
    }

    @Override
    public void onClick(View v) {
        if(model.saveValues()){
            Intent installed = new Intent("com.DMS.installed");
            context.sendBroadcast(installed);
            context.startActivity(new Intent(context, MainMenuActivity.class));
            context.finish();
        }
    }
}
