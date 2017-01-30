package com.example.kbb12.dms.LongActingInsulinModelBuilder.Controller;

import android.text.TextWatcher;
import android.widget.AdapterView;

/**
 * Created by kbb12 on 20/01/2017.
 */
public interface IEntryControllerFactory {

    public AdapterView.OnItemSelectedListener createTypeListener(int entryNumber);

    public TextWatcher createBrandListener(int entryNumber);
}
