package com.example.kbb12.dms.EnterWeight;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.kbb12.dms.R;

public class EnterWeightActivity extends AppCompatActivity {

    private EditText weighttext;
    private ImageButton savebut;
    private SharedPreferences.Editor spredit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_weight);

        spredit = getSharedPreferences("fitnessprefs", MODE_PRIVATE).edit();

        weighttext = (EditText) findViewById(R.id.enterWeightET);
        savebut = (ImageButton) findViewById(R.id.saveWeightIB);

        savebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    double weight = Double.parseDouble(weighttext.getText().toString());
                    spredit.putLong("weight", Double.doubleToLongBits(weight));
                    spredit.apply();
                } catch(NumberFormatException e){
                    Toast.makeText(getApplicationContext(), "Please enter a numeric value", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

    }
}
