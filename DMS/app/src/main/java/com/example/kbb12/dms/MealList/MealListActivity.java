package com.example.kbb12.dms.MealList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kbb12.dms.CustomListView.CustomAdapter;
import com.example.kbb12.dms.R;
import com.example.kbb12.dms.StartUp.ModelHolder;
import com.example.kbb12.dms.StartUp.UserModel;

public class MealListActivity extends AppCompatActivity {
    private ListView mealList;
    private ImageButton addMeal;
    private TextView emptyMealList;
    //private ArrayAdapter<String> adapter;

    private CustomAdapter cAdapter;

    private UserModel model;
    private MealListView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);

        model = ModelHolder.model;

        mealList = (ListView) findViewById(R.id.mealsList);
        addMeal = (ImageButton) findViewById(R.id.addMealButton);
        emptyMealList = (TextView) findViewById(R.id.emptyMealList);

        //adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        //mealList.setAdapter(adapter);

        cAdapter = new CustomAdapter(this, model);
        mealList.setAdapter(cAdapter);

        MealListController controller = new MealListController(model,this);
        addMeal.setOnClickListener(controller);
        mealList.setOnItemClickListener(controller);


        view = new MealListView(emptyMealList,mealList,addMeal,cAdapter,model);
        model.registerObserver(view);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.removeObserver(view);
    }
}
