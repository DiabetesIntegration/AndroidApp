package com.example.kbb12.dms.Template;

import android.widget.EditText;
import android.widget.TextView;

import com.example.kbb12.dms.StartUp.ModelObserver;

/**
 * Created by kbb12 on 17/01/2017.
 */
public class TemplateView implements ModelObserver {

    //The model to question about updates
    private ITemplateModel model;
    //The view elements that will be edited
    private EditText textBoxOne;
    private TextView textBoxTwo;

    public TemplateView(EditText textBoxOne,TextView textBoxTwo,ITemplateModel model){
        this.textBoxOne=textBoxOne;
        this.textBoxTwo=textBoxTwo;
        this.model=model;
        //Sets both boxes to the initial value taken from the model.
        textBoxOne.setText(model.getExampleData());
        textBoxTwo.setText(model.getExampleData());
    }

    public void update(){
        //Ask the model for all of the info you're currently displaying in this
        //section using the relevant model interface then update stuff as necessary.
        textBoxTwo.setText(model.getExampleData());
    }
}
