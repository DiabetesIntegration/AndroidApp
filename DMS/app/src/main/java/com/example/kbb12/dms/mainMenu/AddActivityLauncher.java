package com.example.kbb12.dms.mainMenu;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.kbb12.dms.addFitness.AddFitnessActivity;
import com.example.kbb12.dms.fitnessInfo.FitnessInfoActivity;
import com.example.kbb12.dms.takeInsulin.TakeInsulin;

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