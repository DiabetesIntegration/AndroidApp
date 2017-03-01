package com.example.kbb12.dms.StartUp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kbb12.dms.MealList.MealListActivity;
import com.example.kbb12.dms.R;
import com.example.kbb12.dms.Template.TemplateActivity;

import java.util.ArrayList;
import java.util.List;

/*
This will be the activity that creates the user model at the start and passes it on.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //checking for saved meals should be done here
        List<Meal> meals = new ArrayList<Meal>();



        //Creates user model.
        ModelHolder.model = new UserModel("60", meals);
        Intent mealListIntent = new Intent(this,MealListActivity.class);
        //Passes the model to the intent.
        //templateIntent.putExtra("UserModel", newModel);
        //Launches the next activity.
        startActivity(mealListIntent);
    }
}
