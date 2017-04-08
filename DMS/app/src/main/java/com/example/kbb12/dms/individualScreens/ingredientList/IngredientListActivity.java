package com.example.kbb12.dms.individualScreens.ingredientList;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.controller.DefaultErrorController;
import com.example.kbb12.dms.reusableFunctionality.customListView.CustomAdapter;
import com.example.kbb12.dms.reusableFunctionality.customListView.CustomListViewControllerFactory;
import com.example.kbb12.dms.individualScreens.ingredientList.controller.IngredientListController;
import com.example.kbb12.dms.individualScreens.ingredientList.model.IngredientListModel;
import com.example.kbb12.dms.individualScreens.ingredientList.model.IngredientListReadWriteModel;
import com.example.kbb12.dms.individualScreens.ingredientList.view.IngredientListView;
import com.example.kbb12.dms.model.ModelHolder;
import com.example.kbb12.dms.reusableFunctionality.baseScreen.view.ModelObserver;

public class IngredientListActivity extends AppCompatActivity {

    private IngredientListReadWriteModel model;
    private ModelObserver view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_list);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        model = new IngredientListModel(ModelHolder.model);

        EditText mealName = (EditText) findViewById(R.id.mealNameEntry);
        ListView currentIngredients = (ListView) findViewById(R.id.ingredientsList);
        ImageButton anotherIngredient = (ImageButton) findViewById(R.id.addAnotherIngredientButton);
        TextView addText = (TextView) findViewById(R.id.addAnotherIngredientText) ;
        TextView eatMeal = (TextView) findViewById(R.id.eatMealButton);
        TextView saveMeal = (TextView) findViewById(R.id.saveMealButton);

        IngredientListController controller = new IngredientListController(model,this);
        mealName.addTextChangedListener(controller);
        currentIngredients.setOnItemClickListener(controller);
        anotherIngredient.setOnClickListener(controller);
        addText.setOnClickListener(controller);
        eatMeal.setOnClickListener(controller);
        saveMeal.setOnClickListener(controller);


        CustomAdapter cAdapter = new CustomAdapter(this, new CustomListViewControllerFactory(model));
        currentIngredients.setAdapter(cAdapter);

        android.app.FragmentManager fm = getFragmentManager();
        DefaultErrorController c = new DefaultErrorController(model);

        view = new IngredientListView(mealName,cAdapter,model,fm,c);
        model.registerObserver(view);

    }

    @Override
    public void onResume(){
        super.onResume();
        if(model.getActiveMeal()==null){
            //Can't add an ingredient without a meal
            finish();
        }
        view.update();
    }
}
