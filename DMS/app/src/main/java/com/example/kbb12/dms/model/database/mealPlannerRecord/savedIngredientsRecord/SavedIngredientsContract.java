package com.example.kbb12.dms.model.database.mealPlannerRecord.savedIngredientsRecord;

import android.provider.BaseColumns;

/**
 * Created by Ciaran on 3/28/2017.
 */
public class SavedIngredientsContract {

    public class ContentsDefinition implements BaseColumns {
        public static final String TABLE_NAME = "savedIngredients";
        public static final String COLUMN_NAME_INGNAME = "name";
        public static final String COLUMN_NAME_CARBVAL = "carbVal";
        public static final String COLUMN_NAME_WEIGHT = "packetWeight";
        public static final String COLUMN_NAME_BARCODE = "barcode";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ContentsDefinition.TABLE_NAME + " (" +
                    ContentsDefinition.COLUMN_NAME_INGNAME + " TEXT," +
                    ContentsDefinition.COLUMN_NAME_CARBVAL + " TEXT," +
                    ContentsDefinition.COLUMN_NAME_WEIGHT + " TEXT," +
                    ContentsDefinition.COLUMN_NAME_BARCODE + " TEXT," +
                    "PRIMARY KEY( "+ ContentsDefinition.COLUMN_NAME_INGNAME +" ));";


    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContentsDefinition.TABLE_NAME;
}
