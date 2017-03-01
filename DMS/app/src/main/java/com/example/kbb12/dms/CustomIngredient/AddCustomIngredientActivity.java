package com.example.kbb12.dms.CustomIngredient;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.StartUp.ModelHolder;
import com.example.kbb12.dms.StartUp.UserModel;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class AddCustomIngredientActivity extends AppCompatActivity {
    private EditText ingredientName, carbVal, packVal, sugarVal;
    private ImageButton createCustom, cancelCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_custom_ingredient);

        UserModel model = ModelHolder.model;


        ingredientName = (EditText) findViewById(R.id.customItemName);
        carbVal = (EditText) findViewById(R.id.carbValNum);
        packVal = (EditText) findViewById(R.id.packetValNum);
        sugarVal = (EditText) findViewById(R.id.sugarValNum);

        createCustom = (ImageButton) findViewById(R.id.addCustomIngredientButton);
        cancelCustom = (ImageButton) findViewById(R.id.cancelCustomButton);

        AddCustomIngredientController controller = new AddCustomIngredientController(model,this);

        ingredientName.addTextChangedListener(controller);
        carbVal.addTextChangedListener(controller);
        packVal.addTextChangedListener(controller);
        sugarVal.addTextChangedListener(controller);

        createCustom.setOnClickListener(controller);
        cancelCustom.setOnClickListener(controller);

        AddCustomIngredientView view = new AddCustomIngredientView(ingredientName,carbVal,packVal,sugarVal,model);
        model.registerObserver(view);
    }
}
