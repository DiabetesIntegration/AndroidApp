package com.example.kbb12.dms.individualScreens.mealList;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.reusableFunctionality.customListView.CustomAdapter;
import com.example.kbb12.dms.reusableFunctionality.customListView.CustomListViewControllerFactory;
import com.example.kbb12.dms.individualScreens.mealList.controller.MealListController;
import com.example.kbb12.dms.individualScreens.mealList.model.MealListModel;
import com.example.kbb12.dms.individualScreens.mealList.model.MealListReadWriteModel;
import com.example.kbb12.dms.individualScreens.mealList.view.MealListView;
import com.example.kbb12.dms.model.ModelHolder;
import com.example.kbb12.dms.individualScreens.startUp.ModelObserver;

public class MealListActivity extends AppCompatActivity {

    private MealListReadWriteModel model;
    private ModelObserver view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_list);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        model = new MealListModel(ModelHolder.model);
        ListView mealList = (ListView) findViewById(R.id.mealsList);
        ImageButton addMeal = (ImageButton) findViewById(R.id.addMealButton);
        TextView addMealText = (TextView) findViewById(R.id.addMealText);
        ImageButton addCarb = (ImageButton) findViewById(R.id.addCustomCarbMealButton);
        TextView addCarbText = (TextView) findViewById(R.id.addCustomCarbMealText);
        TextView emptyMealList = (TextView) findViewById(R.id.emptyMealList);

        CustomAdapter cAdapter = new CustomAdapter(this, new CustomListViewControllerFactory(model));
        mealList.setAdapter(cAdapter);

        MealListController controller = new MealListController(model,this);
        addMeal.setOnClickListener(controller);
        addMealText.setOnClickListener(controller);
        addCarb.setOnClickListener(controller);
        addCarbText.setOnClickListener(controller);
        mealList.setOnItemClickListener(controller);


        view = new MealListView(emptyMealList,cAdapter,model);
        model.registerObserver(view);

    }

    @Override
    public void onResume(){
        super.onResume();
        model.clearActives();
        view.update();
    }

}
