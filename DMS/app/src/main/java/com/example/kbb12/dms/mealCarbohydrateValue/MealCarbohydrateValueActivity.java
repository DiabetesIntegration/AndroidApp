package com.example.kbb12.dms.mealCarbohydrateValue;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.kbb12.dms.baseScreen.controller.DefaultErrorController;
import com.example.kbb12.dms.R;
import com.example.kbb12.dms.startUp.ModelHolder;
import com.example.kbb12.dms.model.UserModel;

public class MealCarbohydrateValueActivity extends AppCompatActivity {
    private EditText nameEntry, numberEntry;
    private Button eatMeal, saveMeal;
    private ImageButton confirmMeal;

    private UserModel model;
    private MealCarbohydrateView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_carbohydrate_value);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        model = ModelHolder.model;

        nameEntry = (EditText) findViewById(R.id.carbMealNameEntry);
        numberEntry = (EditText) findViewById(R.id.carbMealCarbEntry);
        eatMeal = (Button) findViewById(R.id.eatCarbMealButton);
        saveMeal = (Button) findViewById(R.id.saveCarbMealButton);

        MealCarbohydrateNameController nameC = new MealCarbohydrateNameController(model,this);
        MealCarbohydrateValueController valueC = new MealCarbohydrateValueController(model,this);
        MealCarbohydrateButtonController controller = new MealCarbohydrateButtonController(model,this,nameC,valueC);

        eatMeal.setOnClickListener(controller);
        saveMeal.setOnClickListener(controller);
        nameEntry.addTextChangedListener(nameC);
        numberEntry.addTextChangedListener(valueC);


        android.app.FragmentManager fm = getFragmentManager();
        DefaultErrorController c = new DefaultErrorController(model);

        view = new MealCarbohydrateView(nameEntry,numberEntry,eatMeal,saveMeal,model,fm,c);
        model.registerObserver(view);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.removeObserver(view);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        model.setStraightCarbs(false);
    }
}
