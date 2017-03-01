package com.example.kbb12.dms.MealList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.StartUp.ModelHolder;
import com.example.kbb12.dms.StartUp.UserModel;

public class MealListActivity extends AppCompatActivity {
    private ListView mealList;
    private ImageButton addMeal;
    private TextView emptyMealList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);

        UserModel model = ModelHolder.model;

        mealList = (ListView) findViewById(R.id.mealsList);
        addMeal = (ImageButton) findViewById(R.id.addMealButton);
        emptyMealList = (TextView) findViewById(R.id.emptyMealList);

        MealListController controller = new MealListController(model,this);
        addMeal.setOnClickListener(controller);
        mealList.setOnItemClickListener(controller);


        MealListView view = new MealListView(emptyMealList,mealList,addMeal,model);
        model.registerObserver(view);

    }
}
