package com.example.kbb12.dms.mealAmount.view;

import android.app.FragmentManager;

import com.example.kbb12.dms.baseScreen.controller.IErrorController;
import com.example.kbb12.dms.baseScreen.view.MasterView;
import com.example.kbb12.dms.mealAmount.model.MealAmountReadWriteModel;
import com.example.kbb12.dms.startUp.ModelObserver;

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
