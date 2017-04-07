package com.example.kbb12.dms.individualScreens.ingredientAmount;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.controller.DefaultErrorController;
import com.example.kbb12.dms.individualScreens.ingredientAmount.controller.IngredientsAmountController;
import com.example.kbb12.dms.individualScreens.ingredientAmount.model.IngredientsAmountModel;
import com.example.kbb12.dms.individualScreens.ingredientAmount.model.IngredientsAmountReadWriteModel;
import com.example.kbb12.dms.individualScreens.ingredientAmount.view.IngredientsAmountView;
import com.example.kbb12.dms.model.ModelHolder;
import com.example.kbb12.dms.individualScreens.startUp.ModelObserver;

public class IngredientsAmountActivity extends AppCompatActivity {

    IngredientsAmountReadWriteModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_amount);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        model = new IngredientsAmountModel(ModelHolder.model);
        ToggleButton wOrp = (ToggleButton) findViewById(R.id.toggleButton);
        ImageButton confirmIngredientAmount = (ImageButton) findViewById(R.id.ingredientAmountConfirmButton);
        EditText amountEntry = (EditText) findViewById(R.id.amountUsedEntry);
        TextView amountUnit = (TextView) findViewById(R.id.amountUsedUnits);

        IngredientsAmountController controller = new IngredientsAmountController(model,this);
        wOrp.setOnCheckedChangeListener(controller);
        confirmIngredientAmount.setOnClickListener(controller);
        amountEntry.addTextChangedListener(controller);

        android.app.FragmentManager fm = getFragmentManager();
        DefaultErrorController c = new DefaultErrorController(model);


        ModelObserver view = new IngredientsAmountView(wOrp,amountUnit,model,fm,c);
        model.registerObserver(view);

    }

    @Override
    public void onBackPressed(){
        model.removeActiveIngredient();
        super.onBackPressed();
    }

    @Override
    public void onResume(){
        super.onResume();
        if(model.getActiveIngredient()==null||model.getActiveMeal()==null){
            //Can't set an amount if there isn't an ingredient
            finish();
        }
    }
}
