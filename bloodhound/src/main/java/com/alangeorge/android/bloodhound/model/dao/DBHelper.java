package com.alangeorge.android.bloodhound.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";
    private static final String DATABASE_NAME = "locations.db";
    private static final int DATABASE_VERSION = 10;

    // locations Table
    public static final String TABLE_LOCATIONS = "locations";
    public static final String LOCATIONS_COLUMN_ID = "_id";
    public static final String LOCATIONS_COLUMN_LATITUDE = "latitude";
    public static final String LOCATIONS_COLUMN_LONGITUDE = "longitude";
    public static final String LOCATIONS_COLUMN_TIME = "time";
    public static final String LOCATIONS_COLUMN_TIME_STRING = "time_str";
    @SuppressWarnings("WeakerAccess")
    public static final String[] LOCATIONS_ALL_COLUMNS = {
            LOCATIONS_COLUMN_ID,
            LOCATIONS_COLUMN_LATITUDE,
            LOCATIONS_COLUMN_LONGITUDE,
            LOCATIONS_COLUMN_TIME,
            LOCATIONS_COLUMN_TIME_STRING
    };

    public static final String LOCATIONS_DATABASE_CREATE = "create table " + TABLE_LOCATIONS + " (" + LOCATIONS_COLUMN_ID
            + " integer primary key autoincrement, " + LOCATIONS_COLUMN_LATITUDE + " real not null, " + LOCATIONS_COLUMN_LONGITUDE
            + " real not null, " + LOCATIONS_COLUMN_TIME + " integer not null, " + LOCATIONS_COLUMN_TIME_STRING + " text not null);";

    // loc_diff view
    public static final String TABLE_LOCATIONS_DIFF = "loc_diff";
    public static final String LOCATIONS_DIFF_COLUMN_ID = "_id";
    public static final String LOCATIONS_DIFF_COLUMN_ID1 = "id1";
    public static final String LOCATIONS_DIFF_COLUMN_LATITUDE1 = "latitude1";
    public static final String LOCATIONS_DIFF_COLUMN_LONGITUDE1 = "longitude1";
    public static final String LOCATIONS_DIFF_COLUMN_TIME1 = "time1";
    public static final String LOCATIONS_DIFF_COLUMN_LATITUDE_DIFF = "latitude_diff";
    public static final String LOCATIONS_DIFF_COLUMN_LONGITUDE_DIFF = "longitude_diff";
    public static final String LOCATIONS_DIFF_COLUMN_ID2 = "id2";
    public static final String LOCATIONS_DIFF_COLUMN_LATITUDE2 = "latitude2";
    public static final String LOCATIONS_DIFF_COLUMN_LONGITUDE2 = "longitude2";
    public static final String LOCATIONS_DIFF_COLUMN_TIME2 = "time2";

    @SuppressWarnings("WeakerAccess")
    public static final String[] LOCATIONS_DIFF_ALL_COLUMNS = {
            LOCATIONS_DIFF_COLUMN_ID,
            LOCATIONS_DIFF_COLUMN_ID1,
            LOCATIONS_DIFF_COLUMN_LATITUDE1,
            LOCATIONS_DIFF_COLUMN_LONGITUDE1,
            LOCATIONS_DIFF_COLUMN_TIME1,
            LOCATIONS_DIFF_COLUMN_LATITUDE_DIFF,
            LOCATIONS_DIFF_COLUMN_LONGITUDE_DIFF,
            LOCATIONS_DIFF_COLUMN_ID2,
            LOCATIONS_DIFF_COLUMN_LATITUDE2,
            LOCATIONS_DIFF_COLUMN_LONGITUDE2,
            LOCATIONS_DIFF_COLUMN_TIME2
    };

    public static final String LOCATIONS_DIFF_DATABASE_CREATE = "create view " + TABLE_LOCATIONS_DIFF + " as select l2._id as " + LOCATIONS_DIFF_COLUMN_ID + ",l1._id as " + LOCATIONS_DIFF_COLUMN_ID1 +
            ", l1.latitude as " + LOCATIONS_DIFF_COLUMN_LATITUDE1 + ", l1.longitude as " + LOCATIONS_DIFF_COLUMN_LONGITUDE1 + ", l1.time as " + LOCATIONS_DIFF_COLUMN_TIME1 +
            ", round((l1.latitude - l2.latitude),4) as " + LOCATIONS_DIFF_COLUMN_LATITUDE_DIFF + ", round((l1.longitude - l2.longitude),4) as " +
            LOCATIONS_DIFF_COLUMN_LONGITUDE_DIFF + ", l2._id as " + LOCATIONS_DIFF_COLUMN_ID2 + ", l2.latitude as " + LOCATIONS_DIFF_COLUMN_LATITUDE2 + ", l2.longitude as " + LOCATIONS_DIFF_COLUMN_LONGITUDE2 +
            ", l2.time as " + LOCATIONS_DIFF_COLUMN_TIME2 + " from " + TABLE_LOCATIONS + " l1, " + TABLE_LOCATIONS + " l2 where l1.rowid = l2.rowid-1 order by l1.time";


    private Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate()");
        //db.execSQL(LOCATIONS_DATABASE_CREATE);
        db.execSQL(LOCATIONS_DIFF_DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
        db.execSQL("DROP VIEW IF EXISTS " + TABLE_LOCATIONS_DIFF);
        onCreate(db);
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        Log.d(TAG, "getWritableDatabase(DATABASE_VERSION:" + DATABASE_VERSION + ")");
        return super.getWritableDatabase();
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        Log.d(TAG, "getReadableDatabase(DATABASE_VERSION:" + DATABASE_VERSION + ")");
        return super.getReadableDatabase();
    }
}