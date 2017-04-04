package com.example.kbb12.dms.mealList;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kbb12.dms.customListView.CustomAdapter;
import com.example.kbb12.dms.R;
import com.example.kbb12.dms.mealList.controller.MealListController;
import com.example.kbb12.dms.mealList.model.MealListModel;
import com.example.kbb12.dms.mealList.model.MealListReadWriteModel;
import com.example.kbb12.dms.mealList.view.MealListView;
import com.example.kbb12.dms.startUp.ModelHolder;
import com.example.kbb12.dms.startUp.ModelObserver;

public class MealListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        MealListReadWriteModel model = new MealListModel(ModelHolder.model);

        ListView mealList = (ListView) findViewById(R.id.mealsList);
        ImageButton addMeal = (ImageButton) findViewById(R.id.addMealButton);
        ImageButton addCarb = (ImageButton) findViewById(R.id.addCustomCarbMealButton);
        TextView emptyMealList = (TextView) findViewById(R.id.emptyMealList);

        CustomAdapter cAdapter = new CustomAdapter(this, model);
        mealList.setAdapter(cAdapter);

        MealListController controller = new MealListController(model,this);
        addMeal.setOnClickListener(controller);
        addCarb.setOnClickListener(controller);
        mealList.setOnItemClickListener(controller);


        ModelObserver view = new MealListView(emptyMealList,cAdapter,model);
        model.registerObserver(view);

    }

}
