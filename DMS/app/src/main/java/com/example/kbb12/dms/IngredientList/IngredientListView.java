package com.example.kbb12.dms.IngredientList;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.kbb12.dms.StartUp.ModelObserver;

/**
 * Created by Ciaran on 3/6/2017.
 */
public class IngredientListView implements ModelObserver {
    private EditText mName;
    private ListView iList;
    private ImageButton addIng;
    private Button finishList;
    private IIngredientList model;


    public IngredientListView(EditText mn, ListView entries, ImageButton aIng, Button eat, IIngredientList model) {
        mName = mn;
        iList = entries;
        addIng = aIng;
        finishList = eat;
        this.model = model;
    }



    @Override
    public void update() {
        //upate listView here
    }
}
