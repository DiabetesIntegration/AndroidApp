package com.example.kbb12.dms.ingredientAmount.model;

import com.example.kbb12.dms.baseScreen.model.BaseModel;
import com.example.kbb12.dms.model.IngredientsAmountMainModel;

/**
 * Created by kbb12 on 05/04/2017.
 */

public class IngredientsAmountModel extends BaseModel implements IngredientsAmountReadWriteModel {

    private IngredientsAmountMainModel model;
    private String units;

    public IngredientsAmountModel(IngredientsAmountMainModel model){
        this.model=model;
        units="g";
    }


    @Override
    public String getUnits() {
        return units;
    }

    @Override
    public void saveAmount(int amount) {
        model.setIngredientAmount(amount);
    }

    @Override
    public void setUnits(String units) {
        this.units=units;
    }
}
