package com.example.kbb12.dms.InsulinModelBuilder.Controller;

import android.widget.AdapterView;

/**
 * Created by kbb12 on 20/01/2017.
 */
public interface IEntryControllerFactory {

    public AdapterView.OnItemClickListener createTypeListener(int entryNumber);
}
