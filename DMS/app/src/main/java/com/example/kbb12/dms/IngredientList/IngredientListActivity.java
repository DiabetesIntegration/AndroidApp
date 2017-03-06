package com.example.kbb12.dms.IngredientList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.StartUp.ModelHolder;
import com.example.kbb12.dms.StartUp.UserModel;

public class IngredientListActivity extends AppCompatActivity {
    private EditText mealName;
    private ListView currentIngredients;
    private ImageButton anotherIngredient;
    private Button completeMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_list);

        UserModel model = ModelHolder.model;

        mealName = (EditText) findViewById(R.id.mealNameEntry);
        currentIngredients = (ListView) findViewById(R.id.ingredientsList);
        anotherIngredient = (ImageButton) findViewById(R.id.addAnotherIngredientButton);
        completeMeal = (Button) findViewById(R.id.eatMealButton);

        IngredientListController controller = new IngredientListController(model,this);
        mealName.addTextChangedListener(controller);
        currentIngredients.setOnItemClickListener(controller);
        anotherIngredient.setOnClickListener(controller);
        completeMeal.setOnClickListener(controller);




    }
}
