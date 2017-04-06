package com.example.kbb12.dms.mealCarbohydrateValue.view;

import android.app.FragmentManager;
import android.widget.EditText;

import com.example.kbb12.dms.baseScreen.controller.IErrorController;
import com.example.kbb12.dms.baseScreen.view.MasterView;
import com.example.kbb12.dms.mealCarbohydrateValue.model.MealCarbohydrateReadWriteModel;
import com.example.kbb12.dms.startUp.ModelObserver;

/**
 * Created by Ciaran on 3/14/2017.
 */
public class MealCarbohydrateView extends MasterView implements ModelObserver {
    private MealCarbohydrateReadWriteModel model;

    public MealCarbohydrateView(MealCarbohydrateReadWriteModel model,
                                FragmentManager fm, IErrorController errorC, EditText nameEntry,
                                EditText numberEntry) {
        super(fm,errorC);
        this.model = model;
        if(model.hasMeal()){
            nameEntry.setText(model.getMealName());
            numberEntry.setText(String.format("%d",model.getMealCarbs()));
        }
    }

    @Override
    public void update() {
        handleError(model.getError());
    }
}
