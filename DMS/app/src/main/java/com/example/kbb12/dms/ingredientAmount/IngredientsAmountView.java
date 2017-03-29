package com.example.kbb12.dms.ingredientAmount;

import android.app.FragmentManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.kbb12.dms.errorHandling.IErrorController;
import com.example.kbb12.dms.errorHandling.MasterView;
import com.example.kbb12.dms.startUp.ModelObserver;

/**
 * Created by Ciaran on 3/6/2017.
 */
public class IngredientsAmountView extends MasterView implements ModelObserver  {
    private ToggleButton weightPrecentage;
    private EditText amount;
    private TextView unit;
    private IIngredientsAmount model;


    public IngredientsAmountView(ToggleButton b, EditText entry, TextView units, IIngredientsAmount model, FragmentManager fm, IErrorController errorC) {
        super(fm,errorC);
        weightPrecentage = b;
        amount = entry;
        unit = units;
        this.model = model;
        unit.setText("g");
    }


    @Override
    public void update() {
        handleError(model.getError());
        unit.setText(model.getUnits());
        if(unit.getText().equals("g")) {
            weightPrecentage.setText("Weight");
        }
        else {
            weightPrecentage.setText("Percentage");
        }
    }
}
