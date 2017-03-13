package com.example.kbb12.dms.CustomIngredient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.StartUp.ModelHolder;
import com.example.kbb12.dms.StartUp.UserModel;

public class AddCustomIngredientActivity extends AppCompatActivity {
    private EditText ingredientName, carbVal, packVal, sugarVal, packetWeight;
    private ImageButton createCustom, cancelCustom;
    private UserModel model;
    private AddCustomIngredientView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_custom_ingredient);

        model = ModelHolder.model;


        ingredientName = (EditText) findViewById(R.id.customItemName);
        carbVal = (EditText) findViewById(R.id.carbValNum);
        packVal = (EditText) findViewById(R.id.packetValNum);
        sugarVal = (EditText) findViewById(R.id.sugarValNum);
        packetWeight = (EditText) findViewById(R.id.packetWeightNum);


        createCustom = (ImageButton) findViewById(R.id.addCustomButton);
        cancelCustom = (ImageButton) findViewById(R.id.cancelCustomButton);

        AddCustomIngredientButtonController controller = new AddCustomIngredientButtonController(model,this);
        createCustom.setOnClickListener(controller);
        cancelCustom.setOnClickListener(controller);



        ingredientName.addTextChangedListener(new AddCustomIngredientNameController(model,this));
        carbVal.addTextChangedListener(new AddCustomIngredientCarbController(model,this));
        packVal.addTextChangedListener(new AddCustomIngredientPacketController(model,this));
        sugarVal.addTextChangedListener(new AddCustomIngredientSugarController(model,this));
        packetWeight.addTextChangedListener(new AddCustomIngredientPacketWeightController(model,this));



        view = new AddCustomIngredientView(ingredientName,carbVal,packVal,sugarVal,packetWeight,model);
        model.registerObserver(view);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.removeObserver(view);
    }
}
