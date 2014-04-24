package com.example.mapapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBAdapter {
	public static final String KEY_ROWID = "_id";
	public static final String KEY_ROOM = "room";
    public static final String KEY_LATITUDE = "latitude";  // this may change.
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_TYPE = "type"; // lecture hall, food place, etc.
    private static final String TAG = "DBAdapter";
    
    private static final String DATABASE_NAME = "places_on_campus";
    private static final String DATABASE_TABLE = "locations";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
        "create table titles (_id integer primary key autoincrement, "
        + "room text null, latitude float not null, " 
        + "longitude float not null, type text null);";
	        
    private final Context context; 
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
    
    // opens the database
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---    
    public void close() {
        DBHelper.close();
    }
    
    //---insert a title into the database---
    public long insertEntry(String room, double latitude, 
    		double longitude, String type) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ROOM, room);
        initialValues.put(KEY_LATITUDE, latitude);
        initialValues.put(KEY_LONGITUDE, longitude);
        initialValues.put(KEY_TYPE, type);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular title---
    public boolean deleteEntry(long rowId) {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all the entries---
    public Cursor getAllEntries() {
        return db.query(DATABASE_TABLE, new String[] {
        		KEY_ROWID, 
        		KEY_ROOM,
                KEY_LATITUDE,
                KEY_LONGITUDE,
                KEY_TYPE}, 
                null, 
                null, 
                null, 
                null, 
                null);
    }

    //---retrieves a particular entry---
    public Cursor getEntry(long rowId) throws SQLException {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {
                		KEY_ROWID,
                		KEY_ROOM, 
                		KEY_LATITUDE,
                		KEY_LONGITUDE,
                		KEY_TYPE
                		}, 
                		KEY_ROWID + "=" + rowId, 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates an entry---
    public boolean updateEntry(long rowId, String room, 
    String title, double latitude, double longitude, String type) {
        ContentValues args = new ContentValues();
        args.put(KEY_ROOM, room);
        args.put(KEY_LATITUDE, latitude);
        args.put(KEY_LONGITUDE, longitude);
        args.put(KEY_TYPE, type);
        return db.update(DATABASE_TABLE, args, 
                         KEY_ROWID + "=" + rowId, null) > 0;
    }
    
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        /**
         * Creates a new database if required database is not present.
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }
        
        /**
         * The onUpgrade() method is called when the database 
         * needs to be upgraded. This is achieved by checking the
         * value defined in the DATABASE_VERSION constant. For 
         * this implementation of the onUpgrade()method, you will 
         * simply drop the table and create the table again.
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, 
                              int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion 
                  + " to "
                  + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS titles");
            onCreate(db);
        }
    }  
}
