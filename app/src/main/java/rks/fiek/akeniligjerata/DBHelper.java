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
    private static final int DB_VERSION = 2;

    private static final String SCHEDULE_TABLE_NAME = "schedule";
    private static final String SCHEDULE_COLUMN_ID = "_id";
    private static final String SCHEDULE_COLUMN_DAY = "day";
    private static final String SCHEDULE_COLUMN_CLASSNUMBER = "classnumber";
    private static final String SCHEDULE_COLUMN_CLASSNAME = "classname";
    private static final String SCHEDULE_COLUMN_STARTTIME = "starttime";
    private static final String SCHEDULE_COLUMN_ENDTIME = "endtime";

    public DBHelper(Context context) {
        super(context, DB_NAME , null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + SCHEDULE_TABLE_NAME + "(" +
                SCHEDULE_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                SCHEDULE_COLUMN_DAY + " TEXT, " +
                SCHEDULE_COLUMN_CLASSNUMBER + " TEXT, " +
                SCHEDULE_COLUMN_CLASSNAME + " TEXT, " +
                SCHEDULE_COLUMN_STARTTIME + " TEXT, " +
                SCHEDULE_COLUMN_ENDTIME + " TEXT)"
        );
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
        return db.rawQuery("select classname,starttime,endtime from schedule where day='"+day+"' and classnumber='"+classnumber+"'",null);
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
