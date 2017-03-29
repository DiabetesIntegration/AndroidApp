package com.example.kbb12.dms.CustomIngredient;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.kbb12.dms.IngredientAmount.IngredientsAmountActivity;
import com.example.kbb12.dms.R;

/**
 * Created by Ciaran on 3/1/2017.
 */
public class AddCustomIngredientButtonController implements View.OnClickListener {
    private IAddCustomIngredient model;
    private Activity currentActivity;
    private AddCustomIngredientNameController name;
    private AddCustomIngredientCarbController carb;
    private AddCustomIngredientPacketController packet;
    private AddCustomIngredientPacketWeightController packetWeight;

    public AddCustomIngredientButtonController(IAddCustomIngredient model, Activity currentActivity, AddCustomIngredientNameController name, AddCustomIngredientCarbController carb, AddCustomIngredientPacketController packet, AddCustomIngredientPacketWeightController packetWeight) {
        this.model = model;
        this.currentActivity = currentActivity;
        this.name = name;
        this.carb = carb;
        this.packet = packet;
        this.packetWeight = packetWeight;
    }


    @Override
    public void onClick(View v) {
        if(name.getNameEntry().isEmpty()) {
            model.getCustomIngErrorMessage("Error! Nothing was entered for the Name!");
        }
        else if(carb.getCarbEntry().isEmpty()) {
            model.getCustomIngErrorMessage("Error! No carb value was entered!");
        }
        else if(packet.getPacketEntry().isEmpty()) {
            model.getCustomIngErrorMessage("Error! No packet value was entered!");
        }
        else if(packetWeight.getPacketWeightEntry().isEmpty()) {
            model.getCustomIngErrorMessage("Error! No packet weight was entered!");
        }
        else {
            try {
                Integer.parseInt(carb.getCarbEntry());
                Integer.parseInt(packet.getPacketEntry());
                Integer.parseInt(packetWeight.getPacketWeightEntry());
                if(Integer.parseInt(carb.getCarbEntry()) > Integer.parseInt(packet.getPacketEntry())) {
                    model.getCustomIngErrorMessage("Error! The number of carbs cannot be greater than the packet value!");
                }
                else {
                    model.setCustomIngredientName(name.getNameEntry());
                    String nutrients[] = {carb.getCarbEntry(), packet.getPacketEntry(), packetWeight.getPacketWeightEntry()};
                    model.setCustomIngredientNutrition(nutrients);
                    nextActivity();
                }
            }
            catch (NumberFormatException e) {
                model.getCustomIngErrorMessage("Error! Some values entered are not integers!");
            }
        }

    }

    public void nextActivity(){
        currentActivity.finish();
        Intent templateIntent = new Intent(currentActivity, IngredientsAmountActivity.class);
        //Launches the next activity.
        currentActivity.startActivity(templateIntent);
    }
}
