package com.example.kbb12.dms.mealPlannerRecord.savedMealsRecord;

import android.provider.BaseColumns;

/**
 * Created by Ciaran on 3/28/2017.
 */
public class SavedMealsContract {
    public class ContentsDefinition implements BaseColumns {
        public static final String TABLE_NAME = "savedMeals";
        public static final String COLUMN_NAME_MEALNAME = "name";
        public static final String COLUMN_NAME_INGREDIENTS = "ingredients";
        public static final String COLUMN_NAME_INGREDIENTVALS = "ingredientVals";
        public static final String COLUMN_NAME_TOTALCARBS = "totalCarbs";
        public static final String COLUMN_NAME_CUSTOMCARBS = "customCarb";
    }

    public static final String SQL_CREATE_TABLE = "CREATE TABLE "
            + ContentsDefinition.TABLE_NAME + " (" + ContentsDefinition._ID + " INTEGER PRIMARY KEY," + ContentsDefinition.COLUMN_NAME_MEALNAME
            + " TEXT," + ContentsDefinition.COLUMN_NAME_INGREDIENTS
            + " TEXT," + ContentsDefinition.COLUMN_NAME_INGREDIENTVALS
            + " TEXT," + ContentsDefinition.COLUMN_NAME_TOTALCARBS
            + " TEXT," + ContentsDefinition.COLUMN_NAME_CUSTOMCARBS
            + " INTEGER" + ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContentsDefinition.TABLE_NAME;
}
