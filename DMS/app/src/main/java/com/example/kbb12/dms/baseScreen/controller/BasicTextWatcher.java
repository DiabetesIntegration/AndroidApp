package com.example.kbb12.dms.baseScreen.controller;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Ciaran on 3/14/2017.
 */
public class BasicTextWatcher implements TextWatcher {
    private String entry ="";


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        entry = s.toString();
    }


    public String getEntry() {
        return entry;
    }
}
