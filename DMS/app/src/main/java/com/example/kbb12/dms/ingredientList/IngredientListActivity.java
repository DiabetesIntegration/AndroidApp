package com.example.kbb12.dms.ingredientList;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.kbb12.dms.baseScreen.controller.DefaultErrorController;
import com.example.kbb12.dms.R;
import com.example.kbb12.dms.customListView.CustomAdapter;
import com.example.kbb12.dms.startUp.ModelHolder;
import com.example.kbb12.dms.model.UserModel;

public class IngredientListActivity extends AppCompatActivity {
    private EditText mealName;
    private ListView currentIngredients;
    private ImageButton anotherIngredient;
    private Button completeMeal;
    private UserModel model;
    private IngredientListView view;

    private CustomAdapter cAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_list);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        model = ModelHolder.model;

        mealName = (EditText) findViewById(R.id.mealNameEntry);
        currentIngredients = (ListView) findViewById(R.id.ingredientsList);
        anotherIngredient = (ImageButton) findViewById(R.id.addAnotherIngredientButton);
        completeMeal = (Button) findViewById(R.id.eatMealButton);

        IngredientListController controller = new IngredientListController(model,this);
        mealName.addTextChangedListener(controller);
        currentIngredients.setOnItemClickListener(controller);
        anotherIngredient.setOnClickListener(controller);
        completeMeal.setOnClickListener(controller);


        cAdapter = new CustomAdapter(this, model);
        currentIngredients.setAdapter(cAdapter);

        android.app.FragmentManager fm = getFragmentManager();
        DefaultErrorController c = new DefaultErrorController(model);

        view = new IngredientListView(mealName,currentIngredients,anotherIngredient,completeMeal,cAdapter,model,fm,c);
        model.registerObserver(view);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.removeObserver(view);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cAdapter.clear();
        if(model.fromIngredient()) {
            model.removeIngredientFromMeal();
        }
    }
}
