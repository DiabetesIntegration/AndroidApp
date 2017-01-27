package com.example.kbb12.dms.InsulinModelBuilder.Controller;

import android.view.View;
import android.widget.AdapterView;

import com.example.kbb12.dms.InsulinModelBuilder.Model.InsulinReadWriteModel;

/**
 * Created by kbb12 on 27/01/2017.
 */
public class TypeListener implements AdapterView.OnItemClickListener {

    private int entryNumber;
    private InsulinReadWriteModel model;

    public TypeListener(int entryNumber,InsulinReadWriteModel model){
        this.entryNumber=entryNumber;
        this.model=model;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:

                break;
            case 1:

        }
    }
}
