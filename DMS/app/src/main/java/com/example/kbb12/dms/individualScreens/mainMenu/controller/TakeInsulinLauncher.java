package com.example.kbb12.dms.individualScreens.mainMenu.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.kbb12.dms.individualScreens.takeInsulin.TakeInsulin;

/**
 * Created by kbb12 on 24/02/2017.
 */
public class TakeInsulinLauncher implements View.OnClickListener {
    Context context;

    public TakeInsulinLauncher(Context context){
        this.context=context;
    }


    @Override
    public void onClick(View v) {
        Intent nextIntent = new Intent(context, TakeInsulin.class);
        context.startActivity(nextIntent);
    }
}
