package com.example.kbb12.dms.Template;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;

/**
 * Created by kbb12 on 17/01/2017.
 * Fairly simple just here to pass through info to the model when stuff changes.
 * Obviously the real controllers will have to be a bit more complex, maybe having
 * to store text until a button is pressed and then pass it over to the model
 * or something but this one is pretty simple.
 */
public class TemplateController implements ITemplateController {

    private ITemplateModel model;
    //Need this to launch next activity.
    private Activity currentActivity;

    public TemplateController(ITemplateModel model,Activity currentActivity){
        this.model=model;
        this.currentActivity=currentActivity;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        model.setExampleData(s.toString());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    /*
    Not actually used in the template but an example of how to
    launch the next activity from the controller.
     */
    public void nextActivity(){
        Intent templateIntent = new Intent(currentActivity, TemplateActivity.class);
        //Passes the model to the intent.
        templateIntent.putExtra("UserModel", model);
        //Launches the next activity.
        currentActivity.startActivity(templateIntent);
    }
}
