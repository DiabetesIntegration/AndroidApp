package com.example.kbb12.dms.mealCarbohydrateValue;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.kbb12.dms.baseScreen.controller.DefaultErrorController;
import com.example.kbb12.dms.R;
import com.example.kbb12.dms.mealCarbohydrateValue.controller.MealCarbohydrateButtonController;
import com.example.kbb12.dms.mealCarbohydrateValue.model.MealCarbohydrateModel;
import com.example.kbb12.dms.mealCarbohydrateValue.model.MealCarbohydrateReadWriteModel;
import com.example.kbb12.dms.mealCarbohydrateValue.view.MealCarbohydrateView;
import com.example.kbb12.dms.startUp.ModelHolder;

public class MealCarbohydrateValueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_carbohydrate_value);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        MealCarbohydrateReadWriteModel model = new MealCarbohydrateModel(ModelHolder.model);

        EditText nameEntry = (EditText) findViewById(R.id.carbMealNameEntry);
        EditText numberEntry = (EditText) findViewById(R.id.carbMealCarbEntry);
        Button eatMeal = (Button) findViewById(R.id.eatCarbMealButton);
        Button saveMeal = (Button) findViewById(R.id.saveCarbMealButton);

        MealCarbohydrateButtonController controller = new MealCarbohydrateButtonController(model,this);

        eatMeal.setOnClickListener(controller);
        saveMeal.setOnClickListener(controller);
        nameEntry.addTextChangedListener(controller.getMealCarbohydrateNameController());
        numberEntry.addTextChangedListener(controller.getMealCarbohydrateValueController());


        android.app.FragmentManager fm = getFragmentManager();
        DefaultErrorController c = new DefaultErrorController(model);

        MealCarbohydrateView view = new MealCarbohydrateView(model,fm,c);
        model.registerObserver(view);
    }
}
