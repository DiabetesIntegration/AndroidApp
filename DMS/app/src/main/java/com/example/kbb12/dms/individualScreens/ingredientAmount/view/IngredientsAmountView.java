package com.example.kbb12.dms.individualScreens.ingredientAmount.view;

import android.app.FragmentManager;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.controller.IErrorController;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.MasterView;
import com.example.kbb12.dms.individualScreens.ingredientAmount.model.IngredientsAmountReadWriteModel;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.ModelObserver;

/**
 * Created by Ciaran on 3/6/2017.
 */
public class IngredientsAmountView extends MasterView implements ModelObserver  {
    private ToggleButton weightPrecentage;
    private TextView unit;
    private IngredientsAmountReadWriteModel model;


    public IngredientsAmountView(ToggleButton b, TextView units, IngredientsAmountReadWriteModel model,
                                 FragmentManager fm, IErrorController errorC) {
        super(fm,errorC,model);
        weightPrecentage = b;
        unit = units;
        this.model = model;
        unit.setText("g");
    }


    @Override
    public void update() {
        super.update();
        unit.setText(model.getUnits());
        if(model.getUnits().equals("g")) {
            weightPrecentage.setText("Weight");
            unit.setText("g");
        }
        else {
            weightPrecentage.setText("Percentage");
            unit.setText("%");
        }
    }
}
