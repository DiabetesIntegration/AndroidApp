package com.example.kbb12.dms.CustomIngredient;

import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.kbb12.dms.ErrorHandling.DefaultErrorController;
import com.example.kbb12.dms.R;
import com.example.kbb12.dms.StartUp.ModelHolder;
import com.example.kbb12.dms.StartUp.UserModel;

public class AddCustomIngredientActivity extends AppCompatActivity {
    private EditText ingredientName, carbVal, packVal, packetWeight;
    private ImageButton createCustom;
    private UserModel model;
    private AddCustomIngredientView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_custom_ingredient);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        model = ModelHolder.model;


        ingredientName = (EditText) findViewById(R.id.customItemName);
        carbVal = (EditText) findViewById(R.id.carbValNum);
        packVal = (EditText) findViewById(R.id.packetValNum);
        packetWeight = (EditText) findViewById(R.id.packetWeightNum);


        createCustom = (ImageButton) findViewById(R.id.addCustomButton);

        AddCustomIngredientNameController nameC = new AddCustomIngredientNameController(model,this);
        AddCustomIngredientCarbController carbC = new AddCustomIngredientCarbController(model,this);
        AddCustomIngredientPacketController packetC = new AddCustomIngredientPacketController(model,this);
        AddCustomIngredientPacketWeightController packetWeightC = new AddCustomIngredientPacketWeightController(model,this);
        ingredientName.addTextChangedListener(nameC);
        carbVal.addTextChangedListener(carbC);
        packVal.addTextChangedListener(packetC);
        packetWeight.addTextChangedListener(packetWeightC);

        AddCustomIngredientButtonController controller = new AddCustomIngredientButtonController(model,this,nameC,carbC,packetC,packetWeightC);
        createCustom.setOnClickListener(controller);

        android.app.FragmentManager fm = getFragmentManager();
        DefaultErrorController c = new DefaultErrorController(model);



        view = new AddCustomIngredientView(ingredientName,carbVal,packVal,packetWeight,model,fm,c);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        model.clearActiveIngreident();
    }
}
