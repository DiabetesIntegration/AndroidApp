package com.example.kbb12.dms.individualScreens.customIngredient;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.controller.DefaultErrorController;
import com.example.kbb12.dms.individualScreens.customIngredient.controller.AddCustomIngredientController;
import com.example.kbb12.dms.individualScreens.customIngredient.model.AddCustomIngredientModel;
import com.example.kbb12.dms.individualScreens.customIngredient.model.AddCustomIngredientReadWriteModel;
import com.example.kbb12.dms.individualScreens.customIngredient.view.AddCustomIngredientView;
import com.example.kbb12.dms.model.ModelHolder;
import com.example.kbb12.dms.individualScreens.startUp.ModelObserver;

public class AddCustomIngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_custom_ingredient);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AddCustomIngredientReadWriteModel model = new AddCustomIngredientModel(ModelHolder.model);


        EditText ingredientName = (EditText) findViewById(R.id.customItemName);
        EditText carbVal = (EditText) findViewById(R.id.carbValNum);
        EditText packVal = (EditText) findViewById(R.id.packetValNum);
        EditText packetWeight = (EditText) findViewById(R.id.packetWeightNum);


        ImageButton createCustom = (ImageButton) findViewById(R.id.addCustomButton);

        AddCustomIngredientController controller = new AddCustomIngredientController(model,this);
        createCustom.setOnClickListener(controller);
        ingredientName.addTextChangedListener(controller.getNameWatcher());
        carbVal.addTextChangedListener(controller.getCarbWatcher());
        packVal.addTextChangedListener(controller.getPacketWatcher());
        packetWeight.addTextChangedListener(controller.getPacketWeightWatcher());
        android.app.FragmentManager fm = getFragmentManager();
        DefaultErrorController c = new DefaultErrorController(model);



        ModelObserver view = new AddCustomIngredientView(ingredientName,carbVal,packVal,packetWeight,model,fm,c);
        model.registerObserver(view);
    }

}
