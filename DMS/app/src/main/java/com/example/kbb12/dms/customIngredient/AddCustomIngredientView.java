package com.example.kbb12.dms.customIngredient;

import android.app.FragmentManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.kbb12.dms.baseScreen.controller.IErrorController;
import com.example.kbb12.dms.baseScreen.view.MasterView;
import com.example.kbb12.dms.startUp.ModelObserver;

/**
 * Created by Ciaran on 3/1/2017.
 */
public class AddCustomIngredientView extends MasterView implements ModelObserver {
    private EditText ingredientName, carbVal, packVal, weightVal;
    private ImageButton createCustom, cancelCustom;
    private IAddCustomIngredient model;

    public AddCustomIngredientView(EditText name, EditText carb, EditText packet, EditText weight, IAddCustomIngredient model, FragmentManager fm, IErrorController errorC) {
        super(fm,errorC);
        ingredientName = name;
        carbVal = carb;
        packVal = packet;
        weightVal = weight;
        this.model = model;
    }

    @Override
    public void update() {
        handleError(model.getError());
    }
}
