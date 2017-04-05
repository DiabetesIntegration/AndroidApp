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
import com.example.kbb12.dms.addIngredient.model.AddIngredientModel;
import com.example.kbb12.dms.addIngredient.model.AddIngredientReadWriteModel;
import com.example.kbb12.dms.addIngredient.view.AddIngredientView;
import com.example.kbb12.dms.ingredientAmount.IngredientsAmountActivity;
import com.example.kbb12.dms.startUp.ModelHolder;
import com.example.kbb12.dms.startUp.ModelObserver;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

public class AddIngredientActivity extends AppCompatActivity {
    private AddIngredientReadWriteModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        model =new AddIngredientModel(ModelHolder.model);

        ImageButton addCustom = (ImageButton) findViewById(R.id.addCustomIngredientButton);
        ImageButton scanItem = (ImageButton) findViewById(R.id.scanBarcodeButton);
        EditText searchSavedIng = (EditText) findViewById(R.id.searchDatabaseEntry);
        ListView savedIngredientList = (ListView) findViewById(R.id.listView2);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        savedIngredientList.setAdapter(adapter);

        AddIngredientController controller = new AddIngredientController(model, this);
        addCustom.setOnClickListener(controller);
        scanItem.setOnClickListener(controller);
        searchSavedIng.addTextChangedListener(controller);
        savedIngredientList.setOnItemClickListener(controller);


        ModelObserver view = new AddIngredientView(adapter,model);
        model.registerObserver(view);
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
