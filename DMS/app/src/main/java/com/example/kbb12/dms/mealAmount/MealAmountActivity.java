package com.example.kbb12.dms.mealAmount;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.baseScreen.controller.DefaultErrorController;
import com.example.kbb12.dms.mealAmount.controller.MealAmountController;
import com.example.kbb12.dms.mealAmount.model.MealAmountModel;
import com.example.kbb12.dms.mealAmount.model.MealAmountReadWriteModel;
import com.example.kbb12.dms.mealAmount.view.MealAmountView;
import com.example.kbb12.dms.startUp.ModelHolder;
import com.example.kbb12.dms.model.UserModel;

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

        MealAmountReadWriteModel model = new MealAmountModel(ModelHolder.model);

        saveMeal = (Button) findViewById(R.id.saveMealButton);
        eatMeal = (Button) findViewById(R.id.eatMealButton);
        mealAmount = (EditText) findViewById(R.id.mealAmountPercent);
        MealAmountController controller = new MealAmountController(model,this);
        eatMeal.setOnClickListener(controller);
        mealAmount.addTextChangedListener(controller);

        android.app.FragmentManager fm = getFragmentManager();
        DefaultErrorController c = new DefaultErrorController(model);

        view = new MealAmountView(model,fm,c);
        model.registerObserver(view);
    }

}
