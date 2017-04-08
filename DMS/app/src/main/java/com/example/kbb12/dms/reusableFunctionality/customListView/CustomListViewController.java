package com.example.kbb12.dms.reusableFunctionality.customListView;

import android.view.View;

/**
 * Created by Ciaran on 3/12/2017.
 */
public class CustomListViewController implements View.OnClickListener{
    private int index;
    private ICustomListViewModel model;

    public CustomListViewController(int position, ICustomListViewModel model) {
        index = position;
        this.model = model;
    }

    @Override
    public void onClick(View v) {
        model.removeItem(index);
    }
}
