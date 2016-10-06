package rks.fiek.akeniligjerata;

/**
 * Created by GentR on 04-Oct-16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

// References:
// http://www.androidauthority.com/use-sqlite-store-data-app-599743/

class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "akeniligjerata.db";
    private static final int DB_VERSION = 3;

    public static final String SCHEDULE_TABLE_NAME = "schedule";
    public static final String SCHEDULE_COLUMN_ID = "_id";
    public static final String SCHEDULE_COLUMN_DAY = "day";
    public static final String SCHEDULE_COLUMN_CLASSNUMBER = "classnumber";
    public static final String SCHEDULE_COLUMN_CLASSNAME = "classname";
    public static final String SCHEDULE_COLUMN_STARTTIME = "starttime";
    public static final String SCHEDULE_COLUMN_ENDTIME = "endtime";

    public DBHelper(Context context) {
        super(context, DB_NAME , null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE schedule (_id INT PRIMARY KEY NOT NULL, day TEXT NOT NULL, classnumber TEXT NOT NULL, " +
                "classname TEXT NOT NULL, starttime TEXT NOT NULL, endtime TEXT NOT NULL)");
    }

    public void insertLecture(Lecture lecture) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCHEDULE_COLUMN_ID, lecture.getId());
        contentValues.put(SCHEDULE_COLUMN_DAY, lecture.getDay());
        contentValues.put(SCHEDULE_COLUMN_CLASSNUMBER, lecture.getClassnumber());
        contentValues.put(SCHEDULE_COLUMN_CLASSNAME, lecture.getClassname());
        contentValues.put(SCHEDULE_COLUMN_STARTTIME, lecture.getStarttime());
        contentValues.put(SCHEDULE_COLUMN_ENDTIME, lecture.getEndtime());
        db.insert(SCHEDULE_TABLE_NAME, null, contentValues);
    }

    public void insertLecture(int id, String day, String classnumber, String classname, String starttime, String endtime) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SCHEDULE_COLUMN_ID, id);
        contentValues.put(SCHEDULE_COLUMN_DAY, day);
        contentValues.put(SCHEDULE_COLUMN_CLASSNUMBER, classnumber);
        contentValues.put(SCHEDULE_COLUMN_CLASSNAME, classname);
        contentValues.put(SCHEDULE_COLUMN_STARTTIME, starttime);
        contentValues.put(SCHEDULE_COLUMN_ENDTIME, endtime);
        db.insert(SCHEDULE_TABLE_NAME, null, contentValues);
    }

    public Cursor getAllLectures() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery( "SELECT * FROM " + SCHEDULE_TABLE_NAME, null );
    }

    public Cursor getTodayLecturesTimes(String classnumber) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select starttime,endtime from schedule where day='"+day+"' and classnumber='"+classnumber+"'",null);
    }

    public Cursor getTodayLectures(String classnumber) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from schedule where day='"+day+"' and classnumber='"+classnumber+"'",null);
    }

    public Cursor dropLectures() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("DROP table " + SCHEDULE_TABLE_NAME,null );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // In case the db needs to upgraded, we just drop and recreate
        db.execSQL("DROP TABLE IF EXISTS " + SCHEDULE_TABLE_NAME);
        onCreate(db);
    }
}
