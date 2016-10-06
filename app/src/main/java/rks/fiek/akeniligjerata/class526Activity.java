package rks.fiek.akeniligjerata;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class class526Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class526);

        DBHelper objDB = new DBHelper(this);
        final Cursor objCursor = objDB.getTodayLectures("526");

        if (objCursor.getCount()>0) {
            String[] columns = new String[]{
                    "classname",
                    "starttime",
                    "endtime"
            };

            int[] widgets = new int[]{
                    R.id.classname,
                    R.id.starttime,
                    R.id.endtime
            };

            ListView listView = (ListView) findViewById(R.id.list);
            SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this, R.layout.class_info, objCursor, columns, widgets, 0);
            listView.setAdapter(cursorAdapter);
        }
    }
}
