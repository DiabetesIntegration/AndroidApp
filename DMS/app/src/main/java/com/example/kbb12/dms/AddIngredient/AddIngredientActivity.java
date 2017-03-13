package com.example.kbb12.dms.AddIngredient;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.StartUp.ModelHolder;
import com.example.kbb12.dms.StartUp.UserModel;

public class AddIngredientActivity extends AppCompatActivity {
    private ImageButton addCustom;
    private EditText searchSavedIng;
    private ListView savedIngredientList;
    private ArrayAdapter<String> adapter;
    private UserModel model;
    private AddIngredientView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        model = ModelHolder.model;

        addCustom = (ImageButton) findViewById(R.id.addCustomIngredientButton);
        searchSavedIng = (EditText) findViewById(R.id.searchDatabaseEntry);
        savedIngredientList = (ListView) findViewById(R.id.listView2);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        savedIngredientList.setAdapter(adapter);

        AddIngredientController controller = new AddIngredientController(model, this);
        addCustom.setOnClickListener(controller);
        searchSavedIng.addTextChangedListener(controller);
        savedIngredientList.setOnItemClickListener(controller);


        view = new AddIngredientView(addCustom,searchSavedIng,savedIngredientList,adapter,model);
        model.registerObserver(view);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.removeObserver(view);
    }
}
