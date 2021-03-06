package com.example.kbb12.dms.individualScreens.customIngredient.view;

import android.app.FragmentManager;
import android.widget.EditText;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.controller.IErrorController;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.MasterView;
import com.example.kbb12.dms.individualScreens.customIngredient.model.AddCustomIngredientReadWriteModel;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.ModelObserver;

/**
 * Created by Ciaran on 3/1/2017.
 */
public class AddCustomIngredientView extends MasterView implements ModelObserver {
    private AddCustomIngredientReadWriteModel model;

    public AddCustomIngredientView(EditText name, EditText carb, EditText packet, EditText weight, AddCustomIngredientReadWriteModel model, FragmentManager fm, IErrorController errorC) {
        super(fm,errorC,model);
        this.model = model;
        if(model.hasExisting()){
            name.setText(model.getIngredientName());
            carb.setText(String.format("%d",model.getCarbPerHundred().intValue()));
            packet.setText("100");
            weight.setText(model.getPacketWeight().toString());
        }
    }

    @Override
    public void update() {
        super.update();
    }
}
