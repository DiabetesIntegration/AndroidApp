package com.example.kbb12.dms.IngredientList;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Ciaran on 3/6/2017.
 */
public class IngredientListController implements View.OnClickListener, TextWatcher, AdapterView.OnItemClickListener {
    private IIngredientList model;
    private Activity currentActivity;

    public IngredientListController(IIngredientList model, Activity currentActivity) {
        this.model = model;
        this.currentActivity = currentActivity;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
}
