package com.example.kbb12.dms.individualScreens.mealAmount.view;

import android.app.FragmentManager;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.controller.IErrorController;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.MasterView;
import com.example.kbb12.dms.individualScreens.mealAmount.model.MealAmountReadWriteModel;
import com.example.kbb12.dms.individualScreens.startUp.ModelObserver;

/**
 * Created by Ciaran on 3/8/2017.
 */
public class MealAmountView extends MasterView implements ModelObserver {

    private MealAmountReadWriteModel model;


    public MealAmountView(MealAmountReadWriteModel model, FragmentManager fm, IErrorController errorC) {
        super(fm,errorC);
        this.model = model;
    }


    @Override
    public void update() {
        handleError(model.getError());
    }
}
