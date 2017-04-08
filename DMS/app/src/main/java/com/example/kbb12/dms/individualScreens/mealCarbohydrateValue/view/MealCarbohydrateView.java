package com.example.kbb12.dms.individualScreens.mealCarbohydrateValue.view;

import android.app.FragmentManager;
import android.widget.EditText;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.controller.IErrorController;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.MasterView;
import com.example.kbb12.dms.individualScreens.mealCarbohydrateValue.model.MealCarbohydrateReadWriteModel;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.ModelObserver;

/**
 * Created by Ciaran on 3/14/2017.
 */
public class MealCarbohydrateView extends MasterView implements ModelObserver {
    private MealCarbohydrateReadWriteModel model;

    public MealCarbohydrateView(MealCarbohydrateReadWriteModel model,
                                FragmentManager fm, IErrorController errorC, EditText nameEntry,
                                EditText numberEntry) {
        super(fm,errorC,model);
        this.model = model;
        if(model.hasMeal()){
            nameEntry.setText(model.getMealName());
            numberEntry.setText(String.format("%d",model.getMealCarbs()));
        }
    }

}
