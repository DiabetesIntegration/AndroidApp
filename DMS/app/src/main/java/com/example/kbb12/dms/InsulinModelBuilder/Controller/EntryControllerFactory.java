package com.example.kbb12.dms.InsulinModelBuilder.Controller;

import android.text.TextWatcher;
import android.widget.AdapterView;

import com.example.kbb12.dms.InsulinModelBuilder.Model.InsulinReadWriteModel;


/**
 * Created by kbb12 on 20/01/2017.
 */
public class EntryControllerFactory implements IEntryControllerFactory {

    InsulinReadWriteModel model;
    int id;

    public EntryControllerFactory(InsulinReadWriteModel model){
        this.model=model;
        this.id=0;
    }

    @Override
    public AdapterView.OnItemSelectedListener createTypeListener(int entryNumber) {
        id++;
        return new TypeListener(entryNumber,model,id);
    }

    @Override
    public TextWatcher createBrandListener(int entryNumber){
        return new BrandListener(entryNumber,model);
    }
}
