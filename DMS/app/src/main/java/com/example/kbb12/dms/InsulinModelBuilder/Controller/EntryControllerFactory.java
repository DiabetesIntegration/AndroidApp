package com.example.kbb12.dms.InsulinModelBuilder.Controller;

import android.text.TextWatcher;
import android.widget.AdapterView;

import com.example.kbb12.dms.InsulinModelBuilder.Model.InsulinReadWriteModel;

/**
 * Created by kbb12 on 20/01/2017.
 */
public class EntryControllerFactory implements IEntryControllerFactory {

    InsulinReadWriteModel model;

    public EntryControllerFactory(InsulinReadWriteModel model){
        this.model=model;
    }

    @Override
    public AdapterView.OnItemSelectedListener createTypeListener(int entryNumber) {
        return new TypeListener(entryNumber,model);
    }

    @Override
    public TextWatcher createBrandListener(int entryNumber){
        return new BrandListener(entryNumber,model);
    }
}
