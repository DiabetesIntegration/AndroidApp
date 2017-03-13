package com.example.kbb12.dms.CustomListView;

import android.util.Log;
import android.view.View;

import com.example.kbb12.dms.R;

/**
 * Created by Ciaran on 3/12/2017.
 */
public class CustomListViewController implements View.OnClickListener{
    private int index;
    private IDeleteCustomItem model;

    public CustomListViewController(int position, IDeleteCustomItem model) {
        index = position;
        this.model = model;
    }

    @Override
    public void onClick(View v) {
        if(model.isIngredientList()) {
            model.removeIngredient(index);
        }
        else {
            model.removeMeal(index);
        }
    }
}
