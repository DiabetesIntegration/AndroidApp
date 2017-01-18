package com.example.kbb12.dms.Template;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.StartUp.ModelHolder;
import com.example.kbb12.dms.StartUp.UserModel;

/**
 * Created by kbb12 on 17/01/2017.
 * An example of how to get the user model through and create individual views and
 * controllers for a section. This particular activity just puts the pre-set example
 * data value in the text boxes and then updates the second one (through the model and
 * observer pattern) whenever the first one changes.
 */
public class TemplateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        /* How to get the model */
        UserModel model = ModelHolder.model;
        /* Grabs the view elements you need to listen to in the controller
        or update in the view
         */
        EditText textBoxOne = (EditText) findViewById(R.id.textBoxOne);
        TextView textBoxTwo = (TextView) findViewById(R.id.textBoxTwo);

        /*
        Creates the controller and adds it as a listener to the
        necessary view elements.
         */
        TemplateController controller = new TemplateController(model,this);
        textBoxOne.addTextChangedListener(controller);

        /*Creates the view and adds it as an observer to the model */
        TemplateView view = new TemplateView(textBoxOne,textBoxTwo,model);
        model.registerObserver(view);
    }
}

