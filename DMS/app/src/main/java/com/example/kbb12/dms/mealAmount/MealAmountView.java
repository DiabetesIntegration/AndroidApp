package com.example.kbb12.dms.mealAmount;

import android.app.FragmentManager;
import android.widget.EditText;

import com.example.kbb12.dms.baseScreen.controller.IErrorController;
import com.example.kbb12.dms.baseScreen.view.MasterView;
import com.example.kbb12.dms.startUp.ModelObserver;

/**
 * Created by Ciaran on 3/8/2017.
 */
public class MealAmountView extends MasterView implements ModelObserver {

    private EditText amount;
    private IMealAmount model;


    public MealAmountView(EditText entry, IMealAmount model, FragmentManager fm, IErrorController errorC) {
        super(fm,errorC);
        amount = entry;
        this.model = model;
    }


    @Override
    public void update() {
        handleError(model.getError());
    }
}
