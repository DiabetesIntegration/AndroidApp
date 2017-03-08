package com.example.kbb12.dms.FitnessInfo;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

/**
 * Created by Garry on 07/03/2017.
 */

public class FitnessInfoController implements View.OnClickListener  {

    private IFitnessInfo model;
    private Activity currentActivity;

    public FitnessInfoController(IFitnessInfo model, Activity currentActivity){
        this.model = model;
        this.currentActivity = currentActivity;
    }


    @Override
    public void onClick(View view) {
        Intent addActivityIntent = new Intent(currentActivity, addActivityIntent.class);
        currentActivity.startActivity(addActivityIntent);
    }
}
