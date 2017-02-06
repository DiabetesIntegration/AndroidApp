package com.example.kbb12.dms.MealList;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.kbb12.dms.IngredientList.IngredientList;
import com.example.kbb12.dms.Template.ITemplateModel;
import com.example.kbb12.dms.Template.TemplateActivity;

/**
 * Created by Ciaran on 2/6/2017.
 */
public class MealListController implements TextWatcher, View.OnClickListener {
    private ITemplateModel model;
    private Activity currentActivity;
    private View v;

    public MealListController(ITemplateModel model, Activity currentActivity, View v) {
        this.model = model;
        this.currentActivity = currentActivity;
        this.v = v;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        Intent templateIntent = new Intent(currentActivity, IngredientList.class);
        //Launches the next activity.
        currentActivity.startActivity(templateIntent);
    }
}
