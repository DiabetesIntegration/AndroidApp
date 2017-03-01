package com.example.kbb12.dms.AddIngredient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.StartUp.ModelHolder;
import com.example.kbb12.dms.StartUp.UserModel;

public class AddIngredientActivity extends AppCompatActivity {
    private ImageButton addCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        UserModel model = ModelHolder.model;

        addCustom = (ImageButton) findViewById(R.id.addCustomIngredientButton);

        AddIngredientController controller = new AddIngredientController(model,this);
        addCustom.setOnClickListener(controller);


        AddIngredientView view = new AddIngredientView(addCustom,model);
        model.registerObserver(view);
    }
}
