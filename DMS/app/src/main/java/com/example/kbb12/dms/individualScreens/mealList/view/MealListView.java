package com.example.kbb12.dms.individualScreens.mealList.view;

import android.app.FragmentManager;
import android.widget.TextView;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.controller.IErrorController;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.MasterView;
import com.example.kbb12.dms.reusableFunctionality.customListView.CustomAdapter;
import com.example.kbb12.dms.individualScreens.mealList.model.MealListReadWriteModel;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.ModelObserver;

/**
 * Created by Ciaran on 2/6/2017.
 */
public class MealListView extends MasterView implements ModelObserver{

    private TextView noMeals;
    private CustomAdapter adapter;
    private MealListReadWriteModel model;

    public MealListView(TextView t, CustomAdapter adapter, MealListReadWriteModel model,
                        FragmentManager fm, IErrorController errorController) {
        super(fm,errorController,model);
        noMeals = t;
        this.adapter = adapter;
        adapter.addAll(model.getSavedMeals());
        this.model = model;
        update();
    }

    @Override
    public void update() {
        super.update();
        if(!adapter.isEmpty()) {
            adapter.clear();
        }
        if(model.getSavedMeals().isEmpty()) {
            noMeals.setText("No saved meals yet");
        }
        else {
            noMeals.setText("");
            adapter.addAll(model.getSavedMeals());
        }
    }
}
