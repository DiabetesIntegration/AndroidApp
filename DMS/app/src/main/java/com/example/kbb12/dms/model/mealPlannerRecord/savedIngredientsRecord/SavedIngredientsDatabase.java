package com.example.kbb12.dms.model.mealPlannerRecord.savedIngredientsRecord;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ciaran on 3/28/2017.
 */
public class SavedIngredientsDatabase implements SavedIngredientsRecord {
    private SQLiteDatabase write;

    public SavedIngredientsDatabase(SQLiteDatabase write) {
        this.write = write;
    }

    @Override
    public long saveIngredient(String n, String cVal, String pVal, String pWeight) {
        ContentValues values = new ContentValues();
        values.put(SavedIngredientsContract.ContentsDefinition.COLUMN_NAME_INGNAME, n);
        values.put(SavedIngredientsContract.ContentsDefinition.COLUMN_NAME_CARBVAL, cVal);
        values.put(SavedIngredientsContract.ContentsDefinition.COLUMN_NAME_PACKETVAL, pVal);
        values.put(SavedIngredientsContract.ContentsDefinition.COLUMN_NAME_WEIGHT, pWeight);

        // insert row
        long id = write.insert(SavedIngredientsContract.ContentsDefinition.TABLE_NAME, null, values);

        return id;
    }

    @Override
    public Map<Integer, List<String>> getAllSavedIngredients() {
        Map<Integer, List<String>> basicData = new HashMap<Integer, List<String>>();

        String selectQuery = "SELECT  * FROM " + SavedIngredientsContract.ContentsDefinition.TABLE_NAME;

        Cursor c = write.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                List<String> values = new ArrayList<String>();
                int id = c.getInt(c.getColumnIndex(SavedIngredientsContract.ContentsDefinition._ID));
                values.add(c.getString(c.getColumnIndex(SavedIngredientsContract.ContentsDefinition.COLUMN_NAME_INGNAME)));
                values.add(c.getString(c.getColumnIndex(SavedIngredientsContract.ContentsDefinition.COLUMN_NAME_CARBVAL)));
                values.add(c.getString(c.getColumnIndex(SavedIngredientsContract.ContentsDefinition.COLUMN_NAME_PACKETVAL)));
                values.add(c.getString(c.getColumnIndex(SavedIngredientsContract.ContentsDefinition.COLUMN_NAME_WEIGHT)));
                basicData.put(id,values);
            } while (c.moveToNext());
        }

        return basicData;
    }
}
