package com.example.kbb12.dms.customIngredient.controller;

import android.app.Activity;
import android.content.Intent;
import android.text.TextWatcher;
import android.view.View;

import com.example.kbb12.dms.baseScreen.controller.BasicTextWatcher;
import com.example.kbb12.dms.customIngredient.model.AddCustomIngredientReadWriteModel;
import com.example.kbb12.dms.ingredientAmount.IngredientsAmountActivity;

/**
 * Created by Ciaran on 3/1/2017.
 */
public class AddCustomIngredientController implements View.OnClickListener {
    private AddCustomIngredientReadWriteModel model;
    private Activity currentActivity;
    private BasicTextWatcher name;
    private BasicTextWatcher carb;
    private BasicTextWatcher packet;
    private BasicTextWatcher packetWeight;

    public AddCustomIngredientController(AddCustomIngredientReadWriteModel model,
                                         Activity currentActivity){
        this.model = model;
        this.currentActivity = currentActivity;
        this.name = new BasicTextWatcher();
        this.carb = new BasicTextWatcher();
        this.packet = new BasicTextWatcher();
        this.packetWeight = new BasicTextWatcher();
    }

    public TextWatcher getNameWatcher(){
        return name;
    }

    public TextWatcher getCarbWatcher(){
        return carb;
    }

    public TextWatcher getPacketWatcher(){
        return packet;
    }

    public TextWatcher getPacketWeightWatcher(){
        return packetWeight;
    }

    @Override
    public void onClick(View v) {
        int carbValue;
        int packetValue;
        int packetWeightValue;
        if(name.getEntry().isEmpty()) {
            model.setError("Error! Nothing was entered for the Name!");
        }
        else if(carb.getEntry().isEmpty()) {
            model.setError("Error! No carb value was entered!");
        }
        else if(packet.getEntry().isEmpty()) {
            model.setError("Error! No packet value was entered!");
        }
        else if(packetWeight.getEntry().isEmpty()) {
            model.setError("Error! No packet weight was entered!");
        }
        else {
            try {
                carbValue=Integer.parseInt(carb.getEntry());
                packetValue=Integer.parseInt(packet.getEntry());
                packetWeightValue=Integer.parseInt(packetWeight.getEntry());
                if(Integer.parseInt(carb.getEntry()) > Integer.parseInt(packet.getEntry())) {
                    model.setError("Error! The number of carbs cannot be greater than the packet value!");
                }
                else {
                    model.save(name.getEntry(),carbValue,packetValue,packetWeightValue);
                    nextActivity();
                }
            }
            catch (NumberFormatException e) {
                model.setError("Error! Some values entered are not integers!");
            }
        }

    }

    public void nextActivity(){
        Intent templateIntent = new Intent(currentActivity, IngredientsAmountActivity.class);
        //Launches the next activity.
        currentActivity.startActivity(templateIntent);
        currentActivity.finish();
    }
}
