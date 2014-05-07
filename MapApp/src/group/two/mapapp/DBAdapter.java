package group.two.mapapp;

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
	public static final String KEY_BUILDING = "building";
    public static final String KEY_LATITUDE = "latitude";  
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_TYPE1 = "type1"; 
    public static final String KEY_TYPE2 = "type2";
    public static final String KEY_TYPE3 = "type3";
    public static final String KEY_NAME = "name";
    protected static final String TAG = "DBAdapter";
    
    protected static final String DATABASE_NAME = "places_on_campus";
    protected static final String DATABASE_TABLE = "locations";
    protected static final String DATABASE_TABLE2 = "bookmarks";
    protected static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE_T1 = 
    	"create table "+ DATABASE_TABLE+" (_id integer primary key autoincrement, "
    	+ "name text not null, "
        + "room text null, building text not null, latitude float not null, " 
        + "longitude float not null, type1 text null, type2 text null, type3 text null);";
    private static final String DATABASE_CREATE_T2 =
        "create table "+ DATABASE_TABLE2+" (_id integer primary key autoincrement, "
        		+ "name text not null, "
        + "room text null, building text not null, latitude float not null, " 
        + "longitude float not null, type1 text null, type2 text null, type3 text null);";
	        
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
    public long insertEntry(String name, String room, String building, double latitude, 
    		double longitude, String type1, String type2, String type3) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_ROOM, room);
        initialValues.put(KEY_BUILDING, building);
        initialValues.put(KEY_LATITUDE, latitude);
        initialValues.put(KEY_LONGITUDE, longitude);
        initialValues.put(KEY_TYPE1, type1);
        initialValues.put(KEY_TYPE1, type2);
        initialValues.put(KEY_TYPE1, type3);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }
    
    public long insertBookmarks(String name, String room, String building, double latitude, 
    		double longitude, String type1, String type2, String type3) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
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
        		KEY_NAME,
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
        		KEY_NAME,
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

    //---retrieves a particular entry by id---
    public Cursor getEntry(long rowId) throws SQLException {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {
                		KEY_ROWID,
                		KEY_NAME,
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
                		KEY_NAME,
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
    
    // get the thing by building
    public Cursor getEntryByBuilding(String building) throws SQLException {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {
                		KEY_ROWID,
                		KEY_NAME,
                		KEY_ROOM,
                		KEY_BUILDING,
                		KEY_LATITUDE,
                		KEY_LONGITUDE,
                		KEY_TYPE1,
                		KEY_TYPE2,
                		KEY_TYPE3,
                		}, 
                		KEY_BUILDING + "='" + building+"'", 
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
    public Cursor getBookmarkByBuilding(String building) throws SQLException {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE2, new String[] {
                		KEY_ROWID,
                		KEY_NAME,
                		KEY_ROOM,
                		KEY_BUILDING,
                		KEY_LATITUDE,
                		KEY_LONGITUDE,
                		KEY_TYPE1,
                		KEY_TYPE2,
                		KEY_TYPE3,
                		}, 
                		KEY_BUILDING + "='" + building+"'", 
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

 // get the thing by name
    public Cursor getEntryByName(String name) throws SQLException {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {
                		KEY_ROWID,
                		KEY_NAME,
                		KEY_ROOM,
                		KEY_BUILDING,
                		KEY_LATITUDE,
                		KEY_LONGITUDE,
                		KEY_TYPE1,
                		KEY_TYPE2,
                		KEY_TYPE3,
                		}, 
                		KEY_NAME + "='" + name+"'", 
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
    public Cursor getBookmarkByName(String name) throws SQLException {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE2, new String[] {
                		KEY_ROWID,
                		KEY_NAME,
                		KEY_ROOM,
                		KEY_BUILDING,
                		KEY_LATITUDE,
                		KEY_LONGITUDE,
                		KEY_TYPE1,
                		KEY_TYPE2,
                		KEY_TYPE3,
                		}, 
                		KEY_NAME + "='" + name+"'", 
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
    
 // get the thing by types
    public Cursor getEntryByType(String t1) throws SQLException {
         Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
                		KEY_ROWID,
                		KEY_NAME,
                		KEY_ROOM,
                		KEY_BUILDING,
                		KEY_LATITUDE,
                		KEY_LONGITUDE,
                		KEY_TYPE1,
                		KEY_TYPE2,
                		KEY_TYPE3,
                		}, 
                   		KEY_TYPE1 + "='" + t1+"' OR " +
                		KEY_TYPE2 + "='" + t1+"' OR " +
                		KEY_TYPE3 + "='" + t1+"'", 
                		null,
                		null, 
                		null, 
                		null, 
                		null);/**/
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    
 // get the thing by types
    public Cursor getBookmarkByType(String t1) throws SQLException {
         Cursor mCursor = db.query(true, DATABASE_TABLE2, new String[] {
                		KEY_ROWID,
                		KEY_NAME,
                		KEY_ROOM,
                		KEY_BUILDING,
                		KEY_LATITUDE,
                		KEY_LONGITUDE,
                		KEY_TYPE1,
                		KEY_TYPE2,
                		KEY_TYPE3,
                		}, 
                   		KEY_TYPE1 + "='" + t1+"' OR " +
                		KEY_TYPE2 + "='" + t1+"' OR " +
                		KEY_TYPE3 + "='" + t1+"'", 
                		null,
                		null, 
                		null, 
                		null, 
                		null);/**/
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    
    //---updates an entry---
    public boolean updateEntry(long rowId, String name, String room, 
    		String building, double latitude, double longitude, String type1, String type2, String type3) {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
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
    public boolean updateBookmark(long rowId, String name, String room, 
    		String building, double latitude, double longitude, String type1, String type2, String type3) {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
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

    
    /**
     * 
     * @return false if empty, true otherwise
     */
    public boolean isEmpty() {
    	Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
        		KEY_ROWID,
        		KEY_NAME,
        		KEY_ROOM,
        		KEY_BUILDING,
        		KEY_LATITUDE,
        		KEY_LONGITUDE,
        		KEY_TYPE1,
        		KEY_TYPE2,
        		KEY_TYPE3,
        		}, 
           		"1", 
        		null,
        		null, 
        		null, 
        		null, 
        		null);/**/
    	if (mCursor != null) {
    		try {
    			mCursor.moveToFirst();
    			mCursor.getString(1);
    		} catch (Exception e) {
    			return false;
    		}
    	}
    	return true;
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
    }  
    
    public void insertEntries() {
    	
    	// insert entries
    	db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (1, 'Career Services Center', 212, 'Math and Psychology Building', 39.254108, -76.712449, null, null, null);");
    	db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (2, 'Registars Office', null, 'Academic Services Building', 39.256486, -76.712066, null, null, null);");
    	db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (3, 'Office for Academic and Pre-Professional Advising', 103, 'Academic Services Building', 39.256486, -76.712066, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (4, 'Academic Services Building', null, 'Academic Services Building', 39.256486, -76.712066, 'building', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (5, 'Administration Building', null, 'Administration Building', 39.253012, -76.713568, 'building', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (6, 'Lecture Hall 3', 101, 'Administration Building', 39.253012, -76.713568, 'lecture hall', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (7, 'International Educational Services', 224, 'Administration Building', 39.253012, -76.713568, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (8, 'Graduate Admissions', null, 'Administration Building', 39.253012, -76.713568, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (9, 'Student Business Services', null, 'Administration Building', 39.253012, -76.713568, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (10, 'Albin O. Kuhn Library and Gallery', null, 'Albin O. Kuhn Library and Gallery', 39.256723, -76.711449, 'building', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (11, 'Undergraduate Admissions', null, 'Albin O. Kuhn Library and Gallery', 39.256723, -76.711449, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (12, 'Financial Aid', null, 'Albin O. Kuhn Library and Gallery', 39.256723, -76.711449, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (13, 'Retriever Learning Center (RLC)', null, 'Albin O. Kuhn Library and Gallery', 39.256723, -76.711449, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (14, 'Biological Sciences Building', null, 'Biological Sciences Building', 39.254837, -76.712114, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (15, 'Lecture Hall 1', null, 'Biological Sciences Building', 39.254771, -76.711897, 'lecture hall', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (16, 'Off-Campus Student Services', 309, 'Commons', 39.254887, -76.710918, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (17, 'The Office of Student Life', 336, 'Commons', 39.254887, -76.710918, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (18, 'The Mosaic Center', '2B23', 'Commons', 39.254887, -76.710918, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (19, 'Women’s Center', 004, 'Commons', 39.254887, -76.710918, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (20, 'Bookstore ', null, 'Commons', 39.254887, -76.710918, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (21, 'Student Events Board (SEB)', '2B10', 'Commons', 39.254887, -76.710918, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (22, 'Game room', null, 'Commons', 39.254887, -76.710918, 'activities', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (23, 'Skylight Room', null, 'Commons', 39.254887, -76.710918, 'food', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (24, 'Sports Zone', null, 'Commons', 39.254887, -76.710918, 'activities', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (25, 'The Commons', null, 'Commons', 39.254887, -76.710918, 'building', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (26, 'Engineering Building', null, 'Engineering Building', 39.254478, -76.714029, 'building', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (27, 'Lecture Hall 5', 027, 'Engineering Building', 39.254478, -76.714029, 'lecture hall', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (28, 'Division of Information Technology (DoIT)', 125, 'Engineering Building', 39.254478, -76.714029, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (29, 'Facilities Management', null, 'Facilities Management', 39.252372, -76.70425, 'building', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (30, 'Parking Services', 100, 'Facilities Management', 39.252887, -76.704791, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (31, 'Fine Arts Building', null, 'Fine Arts Building', 39.255003, -76.713444, 'building', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (32, 'Center for Art Design and Visual Culture', 105, 'Fine Arts Building', 39.255003, -76.713444, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (33, 'Information Technology and Engineering', null, 'Information Technology and Engineering', 39.253739, -76.714324, null, 'Building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (34, 'Lecture Hall 7', 104, 'Information Technology and Engineering', 39.253739, -76.714324, 'lecture hall', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (35, 'Lecture Hall 8', 102, 'Information Technology and Engineering', 39.253739, -76.714324, 'lecture hall', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (36, 'CWIT Office', 452, 'Information Technology and Engineering', 39.253739, -76.714324, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (37, 'UMBC Center for Cybersecurity', 325, 'Information Technology and Engineering', 39.253739, -76.714324, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (38, 'Math & Psychology Building', null, 'Math and Psychology Building', 39.254108, -76.712449, 'building', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (39, 'Meyerhoff Chemistry Building', null, 'Meyerhoff Chemistry Building', 39.254881, -76.712717, 'building', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (40, 'Lecture Hall 2', 030, 'Meyerhoff Chemistry Building', 39.254881, -76.712717, 'lecture hall', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (41, 'Performing Arts and Humanities Building', null, 'Performing Arts and Humanities Building', 39.255165, -76.715115, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (42, 'Dresher Center for the Humanities', 215, 'Performing Arts and Humanities Building', 39.255165, -76.715115, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (43, 'Physics Building', null, 'Physics Building', 39.254432, -76.709689, 'building', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (44, 'Lecture Hall 6', 101, 'Physics Building', 39.254432, -76.709689, 'lecture hall', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (45, 'Center for Space Science and Technology', 211, 'Physics Building', 39.254432, -76.709689, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (46, 'Public Policy Building', null, 'Public Policy Building', 39.255165, -76.709051, 'building', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (47, 'Lecture Hall 9', 105, 'Public Policy Building', 39.255165, -76.709051, 'lecture hall', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (48, 'The Shriver Center', null, 'Public Policy Building', 39.255165, -76.709051, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (49, 'Alex Brown Center for Entrepreneurship', 125, 'Public Policy Building', 39.255165, -76.709051, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (50, 'Retriever Athletics Center', null, 'Retriever Athletics Center', 39.25286, -76.712508, 'building', 'activities', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (51, 'Recreation and Intramurals', 321, 'Retriever Athletics Center', 39.25286, -76.712508, 'activities', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (52, 'Academic Center for Student Athletes', null, 'Retriever Athletics Center', 39.25286, -76.712508, 'activities', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (53, 'Aquatic Complex', null, 'Retriever Athletics Center', 39.253153, -76.712342, 'activities', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (54, 'Soccer Park', null, 'Retriever Athletics Center', 39.251281, -76.705127, 'activities', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (55, 'Softball Stadium', null, 'Retriever Athletics Center', 39.249196, -76.709799, 'activities', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (56, 'Tennis Complex', null, 'Retriever Athletics Center', 39.25329, -76.711478, 'activities', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (57, 'Practice Fields', null, 'Retriever Athletics Center', 39.24829, -76.70783, 'activities', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (58, 'UMBC Stadium', null, 'Retriever Athletics Center', 39.250451, -76.707471, 'activities', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (59, 'Alumni Field', null, 'Retriever Athletics Center', 39.249576, -76.70838, 'activities', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (60, 'Club Sports Fields', null, 'Retriever Athletics Center', 39.257674, -76.716564, 'activities', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (61, 'Varsity Weight Room', null, 'Retriever Athletics Center', 39.25286, -76.712508, 'activities', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (62, 'Sherman Hall', null, 'Sherman Hall', 39.253647, -76.71346, 'building', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (63, 'Lecture Hall 4', 003, 'Sherman Hall', 39.253647, -76.71346, 'lecture hall', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (64, 'Emergency Health Services', 316, 'Sherman Hall', 39.253647, -76.71346, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (65, 'Learning Resources Center (LRC)', 345, 'Sherman Hall', 39.253647, -76.71346, 'lecture hall', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (66, 'Student Support Services (SSS)', 345, 'Sherman Hall', 39.253647, -76.71346, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (67, 'Division of Professional Studies', null, 'Sherman Hall', 39.253647, -76.71346, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (68, 'Sondheim Hall', null, 'Sondheim Hall', 39.253406, -76.712857, 'building', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (69, 'Student Development and Success Center', null, 'Student Development and Success Center', 39.256042, -76.70896, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (70, 'Student Judicial Programs', null, 'Student Development and Success Center', 39.256042, -76.70896, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (71, 'Counseling Center', null, 'Student Development and Success Center', 39.256042, -76.70896, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (72, 'Interfaith Center', null, 'Student Development and Success Center', 39.256042, -76.70896, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (73, 'Technology Research Center', null, 'Technology Research Center', 39.255064, -76.702404, 'building', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (74, 'Center for Urban Environmental Research and Education', 102, 'Technology Research Center', 39.255064, -76.702404, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (75, 'University Center', null, 'University Center', 39.25427, -76.713342, null, 'Building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (76, 'Ballroom ', null, 'University Center', 39.25427, -76.713342, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (77, 'Campus Card & Mail Services', null, 'University Center', 39.25427, -76.713342, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (78, 'Erickson Hall', null, 'Erickson Hall', 39.257136, -76.709864, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (79, 'Residential Life', 184, 'Erickson Hall', 39.257136, -76.709864, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (80, 'University Health Services', null, 'Erickson Hall', 39.25669, -76.709453, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (81, 'Harbor Hall', null, 'Harbor Hall', 39.257476, -76.70808, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (82, 'Chesapeake Hall', null, 'Chesapeake Hall', 39.25668, -76.708568, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (83, 'Potomac Hall', null, 'Potomac Hall', 39.256102, -76.706747, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (84, 'Patapsco Hall', null, 'Patapsco Hall', 39.255133, -76.706889, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (85, 'Susquehanna Hall', null, 'Susquehanna Hall', 39.255671, -76.708547, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (86, 'Walker Avenue Apartments I', null, 'Walker Avenue Apartments I', 39.259556, -76.713707, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (87, 'Walker Avenue Apartments II', null, 'Walker Avenue Apartments II', 39.259052, -76.714557, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (88, 'West Hill Apartments', null, 'West Hill Apartments', 39.258796, -76.712532, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (89, 'Severn', null, 'West Hill Apartments', 39.258796, -76.712532, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (90, 'Chester', null, 'West Hill Apartments', 39.258796, -76.712532, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (91, 'Wye', null, 'West Hill Apartments', 39.258796, -76.712532, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (92, 'Magothy', null, 'West Hill Apartments', 39.258796, -76.712532, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (93, 'Tangier ', null, 'West Hill Apartments', 39.258796, -76.712532, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (94, 'Choptank', null, 'West Hill Apartments', 39.258796, -76.712532, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (95, 'Terrace Apartments', null, 'Terrace Apartments', 39.257874, -76.710641, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (96, 'Nanticoke', null, 'Terrace Apartments', 39.257874, -76.710641, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (97, 'Gunpowder', null, 'Terrace Apartments', 39.257874, -76.710641, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (98, 'Monocacy', null, 'Terrace Apartments', 39.257874, -76.710641, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (99, 'Sassafras', null, 'Terrace Apartments', 39.257874, -76.710641, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (100, 'Antietam', null, 'Terrace Apartments', 39.257874, -76.710641, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (101, 'Chincoteague', null, 'Terrace Apartments', 39.257874, -76.710641, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (102, 'Tuckahoe', null, 'Terrace Apartments', 39.257874, -76.710641, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (103, 'Hillside Apartments', null, 'Hillside Apartments', 39.258044, -76.709107, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (104, 'Sideling', null, 'Hillside Apartments', 39.258044, -76.709107, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (105, 'Pocomoke', null, 'Hillside Apartments', 39.258044, -76.709107, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (106, 'Manokin', null, 'Hillside Apartments', 39.258044, -76.709107, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (107, 'Patuxent', null, 'Hillside Apartments', 39.258044, -76.709107, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (108, 'Elk', null, 'Hillside Apartments', 39.258044, -76.709107, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (109, 'Wicomico', null, 'Hillside Apartments', 39.258044, -76.709107, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (110, 'Deep Creek', null, 'Hillside Apartments', 39.258044, -76.709107, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (111, 'Casselman', null, 'Hillside Apartments', 39.258044, -76.709107, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (112, 'Breton', null, 'Hillside Apartments', 39.258044, -76.709107, 'residence hall', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (113, 'True Grit''s', null, 'Dining Hall ', 39.255899, -76.707712, 'food', 'building', null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (114, 'Outtakes Convenience Store', null, 'Dining Hall', 39.255899, -76.707712, 'food', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (115, 'Outtakes Quick Cuisine', null, 'Commons', 39.254887, -76.710918, 'food', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (116, 'Yum Shoppe ', null, 'Commons', 39.254887, -76.710918, 'food', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (117, 'Salsarita’s', null, 'Commons', 39.254887, -76.710918, 'food', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (118, 'Famous Famiglia', null, 'Commons', 39.254887, -76.710918, 'food', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (119, 'Wild Greens', null, 'Commons', 39.254887, -76.710918, 'food', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (120, 'Au Bon Pain', null, 'Commons', 39.254887, -76.710918, 'food', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (121, 'Mondo Subs', null, 'Commons', 39.254887, -76.710918, 'food', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (122, 'Jow Jing & Olo Sushi', null, 'Commons', 39.254887, -76.710918, 'food', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (123, 'Fresh Fusions', null, 'Commons', 39.254887, -76.710918, 'food', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (124, 'Mesquite Ranch', null, 'Commons', 39.254887, -76.710918, 'food', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (125, 'Chick-Fil-A', null, 'University Center', 39.25427, -76.713342, 'food', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (126, 'Starbucks ', null, 'University Center', 39.25427, -76.713342, 'food', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (127, 'Pura Vida Cafe', null, 'Albin O. Kuhn Library', 39.256723, -76.711449, 'food', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (128, 'The Coffee Shoppe', null, 'Administration Building', 39.253012, -76.713568, 'food', null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (132, 'The Computer Science Help Center (CSHC) ', '201E', 'Information Technology and Engineering', 39.253739, -76.714324, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (133, 'Disability Services', 213, 'Math and Psychology Building', 39.254108, -76.712449, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (134, 'Academic Support ', 345, 'Sherman Hall', 39.253647, -76.71346, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (135, 'Biological Sciences Tutorial Center', 011, 'Biological Sciences Building', 39.254837, -76.712114, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (136, 'Chemistry Tutorial Center', 145, 'Meyerhoff Chemistry Building', 39.254881, -76.712717, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (137, 'Computer Science Help Center', '201E', 'Information Technology and Engineering', 39.253739, -76.714324, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (138, 'Math Gym', 410, 'Math and Psychology Building', 39.254108, -76.712449, null, null, null);");
		db.execSQL("INSERT INTO [locations] ([_id], [name], [room], [building], [latitude], [longitude], [type1], [type2], [type3]) VALUES (139, 'Physics Tutorial Center', 226, 'Physics Building', 39.254432, -76.709689, null, null, null);");
/**/
    }
}
