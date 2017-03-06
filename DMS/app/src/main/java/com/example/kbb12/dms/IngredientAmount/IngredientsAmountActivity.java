package com.example.kbb12.dms.IngredientAmount;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.StartUp.ModelHolder;
import com.example.kbb12.dms.StartUp.UserModel;

public class IngredientsAmountActivity extends AppCompatActivity {
    private ToggleButton wOrp;
    private ImageButton exitIngredientAmount, confirmIngredientAmount;
    private EditText amountEntry;
    private TextView amountUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_amount);

        UserModel model = ModelHolder.model;

        wOrp = (ToggleButton) findViewById(R.id.toggleButton);
        exitIngredientAmount = (ImageButton) findViewById(R.id.ingredientAmountBackButton);
        confirmIngredientAmount = (ImageButton) findViewById(R.id.ingredientAmountConfirmButton);
        amountEntry = (EditText) findViewById(R.id.amountUsedEntry);
        amountUnit = (TextView) findViewById(R.id.amountUsedUnits);

        IngredientsAmountController controller = new IngredientsAmountController(model,this);
        wOrp.setOnCheckedChangeListener(controller);
        exitIngredientAmount.setOnClickListener(controller);
        confirmIngredientAmount.setOnClickListener(controller);
        amountEntry.addTextChangedListener(controller);


        IngredientsAmountView view = new IngredientsAmountView(wOrp,amountEntry,amountUnit,model);
        model.registerObserver(view);


    }
}
