package com.example.kbb12.dms.individualScreens.mainMenu.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.kbb12.dms.individualScreens.fitnessInfo.FitnessInfoActivity;

/**
 * Created by kbb12 on 24/02/2017.
 */
public class AddActivityLauncher implements View.OnClickListener {
    Context context;

    public AddActivityLauncher(Context context){
        this.context=context;
    }


    @Override
    public void onClick(View v) {
        Intent nextIntent = new Intent(context, FitnessInfoActivity.class);
        context.startActivity(nextIntent);
    }
}
