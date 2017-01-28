package com.example.kbb12.dms.InsulinModelBuilder.Model;

import com.example.kbb12.dms.InsulinModelBuilder.View.InsulinEntry;

/**
 * Created by kbb12 on 27/01/2017.
 */
public interface InsulinReadWriteModel extends InsulinReadModel {
    public void setType(int position,InsulinEntry.InsulinType type);
}
