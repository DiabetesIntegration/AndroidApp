package com.example.kbb12.dms.MealAmount;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.kbb12.dms.ErrorHandling.DefaultErrorController;
import com.example.kbb12.dms.IngredientAmount.IngredientsAmountView;
import com.example.kbb12.dms.R;
import com.example.kbb12.dms.StartUp.ModelHolder;
import com.example.kbb12.dms.StartUp.UserModel;

public class MealAmountActivity extends AppCompatActivity {
    private Button saveMeal, eatMeal;
    private EditText mealAmount;

    private UserModel model;
    private MealAmountView view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_amount);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        model = ModelHolder.model;

        saveMeal = (Button) findViewById(R.id.saveMealButton);
        eatMeal = (Button) findViewById(R.id.eatMealButton);
        mealAmount = (EditText) findViewById(R.id.mealAmountPercent);

        MealAmountController controller = new MealAmountController(model,this);
        saveMeal.setOnClickListener(controller);
        eatMeal.setOnClickListener(controller);
        mealAmount.addTextChangedListener(controller);

        android.app.FragmentManager fm = getFragmentManager();
        DefaultErrorController c = new DefaultErrorController(model);

        view = new MealAmountView(mealAmount,model,fm,c);
        model.registerObserver(view);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.removeObserver(view);
    }
}