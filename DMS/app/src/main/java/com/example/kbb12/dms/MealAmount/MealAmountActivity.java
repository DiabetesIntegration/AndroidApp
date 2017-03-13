package com.example.kbb12.dms.MealAmount;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.kbb12.dms.IngredientAmount.IngredientsAmountView;
import com.example.kbb12.dms.R;
import com.example.kbb12.dms.StartUp.ModelHolder;
import com.example.kbb12.dms.StartUp.UserModel;

public class MealAmountActivity extends AppCompatActivity {
    private ImageButton confirmMealAmount;
    private EditText mealAmount;

    private UserModel model;
    private MealAmountView view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_amount);

        model = ModelHolder.model;

        confirmMealAmount = (ImageButton) findViewById(R.id.confirmMealAmountButton);
        mealAmount = (EditText) findViewById(R.id.mealAmountPercent);

        MealAmountController controller = new MealAmountController(model,this);
        confirmMealAmount.setOnClickListener(controller);
        mealAmount.addTextChangedListener(controller);

        view = new MealAmountView(mealAmount,model);
        model.registerObserver(view);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.removeObserver(view);
    }
}
