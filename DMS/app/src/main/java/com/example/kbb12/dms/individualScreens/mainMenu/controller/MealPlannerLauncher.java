package com.example.kbb12.dms.individualScreens.mainMenu.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.kbb12.dms.individualScreens.mealList.MealListActivity;

/**
 * Created by kbb12 on 24/02/2017.
 */
public class MealPlannerLauncher implements View.OnClickListener {
    Context context;

    public MealPlannerLauncher(Context context){
        this.context=context;
    }


    @Override
    public void onClick(View v) {
        Intent nextIntent = new Intent(context, MealListActivity.class);
        context.startActivity(nextIntent);
    }
}
