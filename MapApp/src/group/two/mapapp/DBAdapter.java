package group.two.mapapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;


public class DBAdapter {
public static final String KEY_ROWID = "_id";
public static final String KEY_ROOM = "room";
public static final String KEY_BUILDING = "building";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_TYPE1 = "type1";
    public static final String KEY_TYPE2 = "type2";
    public static final String KEY_TYPE3 = "type3";
    private static final String TAG = "DBAdapter";
    
    private static final String DATABASE_NAME = "places_on_campus";
    private static final String DATABASE_TABLE = "locations";
    private static final String DATABASE_TABLE2 = "bookmarks";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE_T1 =
     "create table "+ DATABASE_TABLE+" (_id integer primary key autoincrement, "
        + "room text null, building text not null, latitude float not null, "
        + "longitude float not null, type1 text null, type2 text null, type3 text null);";
    private static final String DATABASE_CREATE_T2 =
        "create table "+ DATABASE_TABLE2+" (_id integer primary key autoincrement, "
        + "room text null, building text not null, latitude float not null, "
        + "longitude float not null, type1 text null, type2 text null, type3 text null);";

    private final Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        dbHelper = new DatabaseHelper(context);
    }
    
    // opens the database
    public DBAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close() {
        dbHelper.close();
    }
    
    //---insert a title into the database---
    public long insertEntry(String room, String building, double latitude,
     double longitude, String type1, String type2, String type3) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ROOM, room);
        initialValues.put(KEY_BUILDING, building);
        initialValues.put(KEY_LATITUDE, latitude);
        initialValues.put(KEY_LONGITUDE, longitude);
        initialValues.put(KEY_TYPE1, type1);
        initialValues.put(KEY_TYPE1, type2);
        initialValues.put(KEY_TYPE1, type3);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }
    
    public long insertBookmarks(String room, String building, double latitude,
     double longitude, String type1, String type2, String type3) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ROOM, room);
        initialValues.put(KEY_BUILDING, building);
        initialValues.put(KEY_LATITUDE, latitude);
        initialValues.put(KEY_LONGITUDE, longitude);
        initialValues.put(KEY_TYPE1, type1);
        initialValues.put(KEY_TYPE1, type2);
        initialValues.put(KEY_TYPE1, type3);
        return db.insert(DATABASE_TABLE2, null, initialValues);
    }

    //---deletes a particular title---
    public boolean deleteEntry(long rowId) {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }
    
    public boolean deleteBookmark(long rowId) {
     return db.delete(DATABASE_TABLE2, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all the entries---
    public Cursor getAllEntries() {
        return db.query(DATABASE_TABLE, new String[] {
         KEY_ROWID,
         KEY_ROOM,
         KEY_BUILDING,
                KEY_LATITUDE,
                KEY_LONGITUDE,
                KEY_TYPE1,
                KEY_TYPE2,
                KEY_TYPE3},
                null,
                null,
                null,
                null,
                null);
    }
    public Cursor getAllBookmarks() {
        return db.query(DATABASE_TABLE2, new String[] {
         KEY_ROWID,
         KEY_ROOM,
         KEY_BUILDING,
                KEY_LATITUDE,
                KEY_LONGITUDE,
                KEY_TYPE1,
                KEY_TYPE2,
                KEY_TYPE3},
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
                 KEY_BUILDING,
                 KEY_LATITUDE,
                 KEY_LONGITUDE,
                 KEY_TYPE1,
                 KEY_TYPE2,
                 KEY_TYPE3,
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
    public Cursor getBookmark(long rowId) throws SQLException {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE2, new String[] {
                 KEY_ROWID,
                 KEY_ROOM,
                 KEY_BUILDING,
                 KEY_LATITUDE,
                 KEY_LONGITUDE,
                 KEY_TYPE1,
                 KEY_TYPE2,
                 KEY_TYPE3,
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
     String building, double latitude, double longitude, String type1, String type2, String type3) {
        ContentValues args = new ContentValues();
        args.put(KEY_ROOM, room);
        args.put(KEY_BUILDING, building);
        args.put(KEY_LATITUDE, latitude);
        args.put(KEY_LONGITUDE, longitude);
        args.put(KEY_TYPE1, type1);
        args.put(KEY_TYPE2, type2);
        args.put(KEY_TYPE3, type3);
        return db.update(DATABASE_TABLE, args,
                         KEY_ROWID + "=" + rowId, null) > 0;
    }
    //---updates an entry---
    public boolean updateBookmark(long rowId, String room,
     String building, double latitude, double longitude, String type1, String type2, String type3) {
        ContentValues args = new ContentValues();
        args.put(KEY_ROOM, room);
        args.put(KEY_BUILDING, building);
        args.put(KEY_LATITUDE, latitude);
        args.put(KEY_LONGITUDE, longitude);
        args.put(KEY_TYPE1, type1);
        args.put(KEY_TYPE2, type2);
        args.put(KEY_TYPE3, type3);
        return db.update(DATABASE_TABLE2, args,
                         KEY_ROWID + "=" + rowId, null) > 0;
    }
    
    private class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        /**
* Creates a new database if required database is not present.
*/
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE_T1);
            db.execSQL(DATABASE_CREATE_T2);
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
        
        public Cursor getBuildingMatches(String query, String[] columns) {
        	String selection = KEY_BUILDING + " MATCH ?";
        	String[] selectionArgs = new String[] {query+"*"};
        	
        	return query(selection, selectionArgs, columns);
        }
        
        private Cursor query(String selection, String[] selectionArgs, String[] columns) {
        	SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        	builder.setTables(DATABASE_TABLE);
        	
        	Cursor cursor = builder.query(dbHelper.getReadableDatabase(),
        			columns, selection, selectionArgs, null, null, null);
        	
        	if (cursor == null) {
        		return null;
        	} else if (!cursor.moveToFirst()) {
        		cursor.close();
        		return null;
        	}
        	
        	return cursor;
        	        	
        }
        
    }
}

