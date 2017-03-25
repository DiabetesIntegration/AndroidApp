package com.example.kbb12.dms.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.kbb12.dms.model.basalInsulinModel.BasalInsulinModel;
import com.example.kbb12.dms.model.basalInsulinModel.BasalInsulinModelContractHolder;
import com.example.kbb12.dms.model.basalInsulinModel.IBasalInsulinModel;
import com.example.kbb12.dms.model.bolusInsulinModel.BolusInsulinModel;
import com.example.kbb12.dms.model.bolusInsulinModel.BolusInsulinModelContractHolder;
import com.example.kbb12.dms.model.bolusInsulinModel.IBolusInsulinModel;
import com.example.kbb12.dms.model.insulinTakenRecord.InsulinTakenContract;
import com.example.kbb12.dms.model.insulinTakenRecord.InsulinTakenDatabase;
import com.example.kbb12.dms.model.insulinTakenRecord.InsulinTakenRecord;

/**
 * Created by kbb12 on 24/03/2017.
 */

public class DatabaseBuilder extends SQLiteOpenHelper {


    private static final String DATABASE_NAME="BackingStorage";
    private static final int versionNumber=4;
    private IBolusInsulinModel bolusInsulinModel;
    private IBasalInsulinModel basalInsulinModel;
    private InsulinTakenRecord insulinTakenRecord;

    public IBolusInsulinModel getBolusInsulinModel() {
        return bolusInsulinModel;
    }

    public IBasalInsulinModel getBasalInsulinModel() {
        return basalInsulinModel;
    }

    public InsulinTakenRecord getInsulinTakenRecord() {
        return insulinTakenRecord;
    }

    public DatabaseBuilder(Context context){
        super(context,DATABASE_NAME,null,versionNumber);
        bolusInsulinModel=new BolusInsulinModel(getWritableDatabase());
        basalInsulinModel=new BasalInsulinModel(getWritableDatabase());
        insulinTakenRecord=new InsulinTakenDatabase(getWritableDatabase());

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BolusInsulinModelContractHolder.SQL_CREATE_ENTRIES);
        for(int i=0;i<24;i++){
            db.execSQL("INSERT INTO "
                    + BolusInsulinModelContractHolder.ContentsDefinition.TABLE_NAME +
                    " (" + BolusInsulinModelContractHolder.ContentsDefinition.COLUMN_TIME +
                    ")VALUES(\"" + String.format("%2d:00",i) + "\")");
        }
        db.execSQL(BasalInsulinModelContractHolder.SQL_CREATE_ENTRIES);
        db.execSQL(InsulinTakenContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(BolusInsulinModelContractHolder.SQL_DELETE_ENTRIES);
        db.execSQL(BasalInsulinModelContractHolder.SQL_DELETE_ENTRIES);
        db.execSQL(InsulinTakenContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
