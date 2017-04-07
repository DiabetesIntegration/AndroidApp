package com.example.kbb12.dms.individualScreens.ingredientAmount.model;

import com.example.kbb12.dms.reusableFunctionality.baseScreen.model.BaseModel;
import com.example.kbb12.dms.model.IngredientsAmountMainModel;
import com.example.kbb12.dms.model.database.mealPlannerRecord.IIngredient;
import com.example.kbb12.dms.model.database.mealPlannerRecord.IMeal;

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
    public IIngredient getActiveIngredient() {
        return model.getActiveIngredient();
    }

    @Override
    public void saveAmount(int amount) {
        if(units.equals("g")) {
            model.setIngredientAmount(amount);
        }else{
            model.setIngredientAmount((int)(((double)model.getActiveIngredientPacketWeight()/100)*amount));
        }
    }

    @Override
    public void setUnits(String units) {
        this.units=units;
        notifyObserver();
    }

    @Override
    public void removeActiveIngredient() {
        model.removeActiveIngredient();
    }

    @Override
    public IMeal getActiveMeal() {
        return model.getActiveMeal();
    }
}
