package com.example.kbb12.dms.Template;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kbb12.dms.R;
import com.example.kbb12.dms.StartUp.UserModel;

/**
 * Created by kbb12 on 17/01/2017.
 * An example of how to get the user model through and create individual views and
 * controllers for a section.
 */
public class TemplateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        Intent intent = getIntent();
        UserModel model = (UserModel) intent.getParcelableExtra("UserModel");
        EditText proof = (EditText) findViewById(R.id.textView);
        proof.setText(Integer.toString(model.getExampleData()));
    }
}

