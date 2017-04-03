package com.example.kbb12.dms.ingredientList;

import android.app.FragmentManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.kbb12.dms.customListView.CustomAdapter;
import com.example.kbb12.dms.baseScreen.controller.IErrorController;
import com.example.kbb12.dms.baseScreen.view.MasterView;
import com.example.kbb12.dms.startUp.ModelObserver;

/**
 * Created by Ciaran on 3/6/2017.
 */
public class IngredientListView extends MasterView implements ModelObserver {
    private EditText mName;
    private ListView iList;
    private ImageButton addIng;
    private Button finishList;
    private CustomAdapter adapter;
    private IIngredientList model;


    public IngredientListView(EditText mealName, ListView currentIngredients, ImageButton anotherIngredient, Button completeMeal, CustomAdapter adapter, IIngredientList model, FragmentManager fm, IErrorController errorC) {
        super(fm,errorC);
        mName = mealName;
        iList = currentIngredients;
        addIng = anotherIngredient;
        finishList = completeMeal;
        this.adapter = adapter;
        this.adapter.addAll(model.getIngredientsInMeal());
        this.model = model;
        adapter.addAll(model.getIngredientsInMeal());
    }



    @Override
    public void update() {
        handleError(model.getError());

        if(!adapter.isEmpty()) {
            adapter.clear();
        }
        adapter.addAll(model.getIngredientsInMeal());

        mName.setText(model.getMealName());

    }
}
