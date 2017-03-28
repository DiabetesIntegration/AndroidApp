package com.example.kbb12.dms.model.basalInsulinModel;

import android.provider.BaseColumns;

/**
 * Created by kbb12 on 07/02/2017.
 */
public final class BasalInsulinModelContractHolder {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private BasalInsulinModelContractHolder() {}

    /* Inner class that defines the table contents */
    public static class ContentsDefinition implements BaseColumns {
        public static final String TABLE_NAME="BasalModel";
        public static final String COLUMN_INSULIN_NAME = "Name";
        public static final String COLUMN_TIME = "Time";
        public static final String COLUMN_IMPROVED_DOSE = "ImprovedAmount";
        public static final String COLUMN_ORIG_DOSE ="OrigAmount";
        public static final String COLUMN_DATE_LAST_TAKEN = "LastTaken";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + BasalInsulinModelContractHolder.ContentsDefinition.TABLE_NAME + " (" +
                    BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_INSULIN_NAME + " VARCHAR(1000)," +
                    BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_TIME + " VARCHAR(5)," +
                    BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_IMPROVED_DOSE + " FLOAT," +
                    BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_ORIG_DOSE + " FLOAT," +
                    BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_DATE_LAST_TAKEN + " DATE," +
                    "PRIMARY KEY( "+ BasalInsulinModelContractHolder.ContentsDefinition.COLUMN_TIME +" ));";


    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + BasalInsulinModelContractHolder.ContentsDefinition.TABLE_NAME;
}