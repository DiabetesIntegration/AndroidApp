package com.example.kbb12.dms.InsulinModelBuilder.Model;

import com.example.kbb12.dms.InsulinModelBuilder.View.InsulinEntry;
import com.example.kbb12.dms.ErrorHandling.ErrorReadModel;

import java.util.List;

/**
 * Created by kbb12 on 20/01/2017.
 */
public interface InsulinReadModel extends ErrorReadModel {

    public List<InsulinEntry> getInsulinEntries();
}
