package com.example.kbb12.dms.mealCarbohydrateValue;

import android.app.FragmentManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.kbb12.dms.baseScreen.controller.IErrorController;
import com.example.kbb12.dms.baseScreen.view.MasterView;
import com.example.kbb12.dms.startUp.ModelObserver;

/**
 * Created by Ciaran on 3/14/2017.
 */
public class MealCarbohydrateView extends MasterView implements ModelObserver {
    private EditText nameEntry, numberEntry;
    private Button eat, save;
    private IMealCarbohydrateValue model;

    public MealCarbohydrateView(EditText nameEntry, EditText numberEntry, Button eat, Button save, IMealCarbohydrateValue model, FragmentManager fm, IErrorController errorC) {
        super(fm,errorC);
        this.nameEntry = nameEntry;
        this.numberEntry = numberEntry;
        this.eat = eat;
        this.save = save;
        this.model = model;
    }

    @Override
    public void update() {
        handleError(model.getError());
    }
}
