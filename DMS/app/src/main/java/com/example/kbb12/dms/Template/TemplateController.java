package com.example.kbb12.dms.Template;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by kbb12 on 17/01/2017.
 * Fairly simple just here to pass through info to the model when stuff changes.
 * Obviously the real controllers will have to be a bit more complex, maybe having
 * to store text until a button is pressed and then pass it over to the model
 * or something but this one is pretty simple.
 */
public class TemplateController implements ITemplateController {

    private ITemplateModel model;

    public TemplateController(ITemplateModel model){
        this.model=model;
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
}
