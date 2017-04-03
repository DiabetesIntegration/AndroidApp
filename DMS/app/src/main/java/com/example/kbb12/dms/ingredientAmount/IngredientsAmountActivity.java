package com.example.kbb12.dms.ingredientAmount;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.kbb12.dms.baseScreen.controller.DefaultErrorController;
import com.example.kbb12.dms.R;
import com.example.kbb12.dms.startUp.ModelHolder;
import com.example.kbb12.dms.model.UserModel;

public class IngredientsAmountActivity extends AppCompatActivity {
    private ToggleButton wOrp;
    private ImageButton confirmIngredientAmount;
    private EditText amountEntry;
    private TextView amountUnit;
    private UserModel model;
    private IngredientsAmountView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_amount);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        model = ModelHolder.model;

        wOrp = (ToggleButton) findViewById(R.id.toggleButton);
        confirmIngredientAmount = (ImageButton) findViewById(R.id.ingredientAmountConfirmButton);
        amountEntry = (EditText) findViewById(R.id.amountUsedEntry);
        amountUnit = (TextView) findViewById(R.id.amountUsedUnits);

        IngredientsAmountController controller = new IngredientsAmountController(model,this);
        wOrp.setOnCheckedChangeListener(controller);
        confirmIngredientAmount.setOnClickListener(controller);
        amountEntry.addTextChangedListener(controller);

        android.app.FragmentManager fm = getFragmentManager();
        DefaultErrorController c = new DefaultErrorController(model);


        view = new IngredientsAmountView(wOrp,amountEntry,amountUnit,model,fm,c);
        model.registerObserver(view);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.removeObserver(view);
    }
}
