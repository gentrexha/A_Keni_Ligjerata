package rks.fiek.akeniligjerata;

/**
 * Created by GentR on 04-Oct-16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// References:
// http://www.androidauthority.com/use-sqlite-store-data-app-599743/

class DBHelper extends SQLiteOpenHelper {
    // Variables for the database table
    private static final String DB_NAME = "schedule.db";
    private static final int DB_VERSION = 1;

    private static final String CLASSES_TABLE_NAME = "classes";
    private static final String CLASSES_COLUMN_ID = "_id";
    public static final String CLASSES_COLUMN_CLASSNUMBER = "classnumber";
    public static final String CLASSES_COLUMN_NAME = "classname";
    public static final String CLASSES_COLUMN_STARTTIME = "starttime";
    public static final String CLASSES_COLUMN_ENDTIME = "endtime";

    public DBHelper(Context context) {
        super(context, DB_NAME , null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CLASSES_TABLE_NAME + "(" +
                CLASSES_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                CLASSES_COLUMN_CLASSNUMBER + " TEXT, " +
                CLASSES_COLUMN_NAME + " TEXT, " +
                CLASSES_COLUMN_STARTTIME + " TEXT)"
        );
    }

    public void insertClass(String classnumber, String classname, String starttime, String endtime) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CLASSES_COLUMN_CLASSNUMBER, classnumber);
        contentValues.put(CLASSES_COLUMN_NAME, classname);
        contentValues.put(CLASSES_COLUMN_STARTTIME, starttime);
        contentValues.put(CLASSES_COLUMN_ENDTIME, endtime);
        db.insert(CLASSES_TABLE_NAME, null, contentValues);
    }

    public Cursor getAllClasses() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery( "SELECT * FROM " + CLASSES_TABLE_NAME, null );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // In case the db needs to upgraded, we just drop and recreate
        db.execSQL("DROP TABLE IF EXISTS " + CLASSES_TABLE_NAME);
        onCreate(db);
    }
}
