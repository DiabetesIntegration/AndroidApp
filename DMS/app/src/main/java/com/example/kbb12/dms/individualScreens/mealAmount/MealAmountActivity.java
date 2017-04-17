package com.example.kbb12.dms.individualScreens.mealAmount;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.controller.DefaultErrorController;
import com.example.kbb12.dms.individualScreens.mealAmount.controller.MealAmountController;
import com.example.kbb12.dms.individualScreens.mealAmount.model.MealAmountModel;
import com.example.kbb12.dms.individualScreens.mealAmount.model.MealAmountReadWriteModel;
import com.example.kbb12.dms.model.UserModel;
import com.example.kbb12.dms.model.ModelHolder;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.MasterView;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.ModelObserver;

public class MealAmountActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_amount);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        MealAmountReadWriteModel model = new MealAmountModel(ModelHolder.model);
        Button eatMeal = (Button) findViewById(R.id.eatMealButton);
        EditText mealAmount = (EditText) findViewById(R.id.mealAmountPercent);
        MealAmountController controller = new MealAmountController(model,this);
        eatMeal.setOnClickListener(controller);
        mealAmount.addTextChangedListener(controller);

        android.app.FragmentManager fm = getFragmentManager();
        DefaultErrorController c = new DefaultErrorController(model);

        ModelObserver view = new MasterView(fm,c,model);
        model.registerObserver(view);
    }

}
