package com.example.kbb12.dms.IngredientList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.CustomListView.CustomAdapter;
import com.example.kbb12.dms.StartUp.ModelHolder;
import com.example.kbb12.dms.StartUp.UserModel;

public class IngredientListActivity extends AppCompatActivity {
    private EditText mealName;
    private ListView currentIngredients;
    private ImageButton anotherIngredient;
    private Button completeMeal;
    //private ArrayAdapter<String> adapter;
    private UserModel model;
    private IngredientListView view;

    private CustomAdapter cAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_list);

        model = ModelHolder.model;

        mealName = (EditText) findViewById(R.id.mealNameEntry);
        currentIngredients = (ListView) findViewById(R.id.ingredientsList);
        anotherIngredient = (ImageButton) findViewById(R.id.addAnotherIngredientButton);
        completeMeal = (Button) findViewById(R.id.eatMealButton);

        //adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        //currentIngredients.setAdapter(adapter);

        IngredientListController controller = new IngredientListController(model,this);
        mealName.addTextChangedListener(controller);
        currentIngredients.setOnItemClickListener(controller);
        anotherIngredient.setOnClickListener(controller);
        completeMeal.setOnClickListener(controller);


        cAdapter = new CustomAdapter(this, model);
        currentIngredients.setAdapter(cAdapter);

        view = new IngredientListView(mealName,currentIngredients,anotherIngredient,completeMeal,cAdapter,model);
        model.registerObserver(view);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("tester", "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //currentIngredients.invalidateViews();
        //adapter.clear();
        //adapter.notifyDataSetChanged();
        //currentIngredients.setAdapter(null);
        //currentIngredients.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>()));
        model.removeObserver(view);
    }
}