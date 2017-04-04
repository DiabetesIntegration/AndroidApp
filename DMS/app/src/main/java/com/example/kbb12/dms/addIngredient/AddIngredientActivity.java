package com.example.kbb12.dms.addIngredient;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.addIngredient.controller.AddIngredientController;
import com.example.kbb12.dms.addIngredient.model.IAddIngredient;
import com.example.kbb12.dms.addIngredient.view.AddIngredientView;
import com.example.kbb12.dms.ingredientAmount.IngredientsAmountActivity;
import com.example.kbb12.dms.startUp.ModelHolder;
import com.example.kbb12.dms.model.UserModel;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class AddIngredientActivity extends AppCompatActivity {
    private ImageButton addCustom, scanItem;
    private EditText searchSavedIng;
    private ListView savedIngredientList;
    private ArrayAdapter<String> adapter;
    private IAddIngredient model;
    private AddIngredientView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        model =(IAddIngredient) ModelHolder.model;

        addCustom = (ImageButton) findViewById(R.id.addCustomIngredientButton);
        scanItem = (ImageButton) findViewById(R.id.scanBarcodeButton);
        searchSavedIng = (EditText) findViewById(R.id.searchDatabaseEntry);
        savedIngredientList = (ListView) findViewById(R.id.listView2);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        savedIngredientList.setAdapter(adapter);

        AddIngredientController controller = new AddIngredientController(model, this);
        addCustom.setOnClickListener(controller);
        scanItem.setOnClickListener(controller);
        searchSavedIng.addTextChangedListener(controller);
        savedIngredientList.setOnItemClickListener(controller);


        view = new AddIngredientView(adapter,model);
        model.registerObserver(view);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        model.removeIngListView();
        model.setAddIngredient(false);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() != null) {
                if(model.setScannedIngredient(result.getContents())) {
                    Intent intent = new Intent(this, IngredientsAmountActivity.class);
                    startActivity(intent);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
