package com.example.kbb12.dms.mealList;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kbb12.dms.customListView.CustomAdapter;
import com.example.kbb12.dms.R;
import com.example.kbb12.dms.startUp.ModelHolder;
import com.example.kbb12.dms.model.UserModel;

public class MealListActivity extends AppCompatActivity {
    private ListView mealList;
    private ImageButton addMeal, addCarb;
    private TextView emptyMealList;

    private CustomAdapter cAdapter;

    private UserModel model;
    private MealListView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        model = ModelHolder.model;

        mealList = (ListView) findViewById(R.id.mealsList);
        addMeal = (ImageButton) findViewById(R.id.addMealButton);
        addCarb = (ImageButton) findViewById(R.id.addCustomCarbMealButton);
        emptyMealList = (TextView) findViewById(R.id.emptyMealList);

        cAdapter = new CustomAdapter(this, model);
        mealList.setAdapter(cAdapter);

        MealListController controller = new MealListController(model,this);
        addMeal.setOnClickListener(controller);
        addCarb.setOnClickListener(controller);
        mealList.setOnItemClickListener(controller);


        view = new MealListView(emptyMealList,mealList,addMeal,cAdapter,model);
        model.registerObserver(view);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
