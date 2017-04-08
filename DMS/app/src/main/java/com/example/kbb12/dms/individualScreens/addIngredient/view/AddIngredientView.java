package com.example.kbb12.dms.individualScreens.addIngredient.view;

import android.app.FragmentManager;
import android.widget.ArrayAdapter;

import com.example.kbb12.dms.individualScreens.addIngredient.model.AddIngredientReadWriteModel;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.controller.IErrorController;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.MasterView;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.ModelObserver;

/**
 * Created by Ciaran on 3/1/2017.
 */
public class AddIngredientView extends MasterView implements ModelObserver {

    private ArrayAdapter<String> adapter;
    private AddIngredientReadWriteModel model;

    public AddIngredientView(ArrayAdapter adapter, AddIngredientReadWriteModel model,
                             FragmentManager fm, IErrorController errorController) {
        super(fm,errorController,model);
        this.adapter = adapter;
        if(!model.getSavedIngredients().isEmpty()) {
            adapter.addAll(model.getSavedIngredients());
        }
        this.model = model;
    }

    @Override
    public void update() {
        super.update();
        if(!adapter.isEmpty()) {
            adapter.clear();
        }
        adapter.addAll(model.getSavedIngredients());
    }
}

