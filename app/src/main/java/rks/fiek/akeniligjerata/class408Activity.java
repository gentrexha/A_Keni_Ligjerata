package rks.fiek.akeniligjerata;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class class408Activity extends AppCompatActivity {

    ListView list;
    ListView listComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class408);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        list = (ListView)findViewById(R.id.list);
        listComments = (ListView)findViewById(R.id.listComments);
        DBHelper objDB = new DBHelper(this);

        Cursor lectureCursor = objDB.getTodayLectures("408");
        Cursor commentsCursor = objDB.getClassComments("408");

        if (lectureCursor.getCount()>0) {
            lectureCursorAdapter todoAdapter = new lectureCursorAdapter(this, lectureCursor);
            list.setAdapter(todoAdapter);
        }

        if (commentsCursor.getCount()>0) {
            commentCursorAdapter todoAdapter = new commentCursorAdapter(this, commentsCursor);
            listComments.setAdapter(todoAdapter);
        }
    }

    public class lectureCursorAdapter extends CursorAdapter {
        public lectureCursorAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        // The newView method is used to inflate a new view and return it,
        // you don't bind any data to the view at this point.
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.class_info, parent, false);
        }

        // The bindView method is used to bind all data to a given view
        // such as setting the text on a TextView.
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Find fields to populate in inflated template
            TextView txvClassname = (TextView) view.findViewById(R.id.classname);
            TextView txvStarttime = (TextView) view.findViewById(R.id.starttime);
            TextView txvEndtime = (TextView) view.findViewById(R.id.endtime);
            // Extract properties from cursor
            String classname = cursor.getString(cursor.getColumnIndexOrThrow("classname"));
            String starttime = cursor.getString(cursor.getColumnIndexOrThrow("starttime"));
            String endtime = cursor.getString(cursor.getColumnIndexOrThrow("endtime"));
            // Populate fields with extracted properties
            txvClassname.setText(classname);
            txvStarttime.setText(starttime);
            txvEndtime.setText(endtime);
        }
    }

    public class commentCursorAdapter extends CursorAdapter {
        public commentCursorAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        // The newView method is used to inflate a new view and return it,
        // you don't bind any data to the view at this point.
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.comment_info, parent, false);
        }

        // The bindView method is used to bind all data to a given view
        // such as setting the text on a TextView.
        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // Find fields to populate in inflated template
            TextView txvComment = (TextView) view.findViewById(R.id.txvComment);
            TextView txvDate = (TextView) view.findViewById(R.id.txvDate);
            // Extract properties from cursor
            String content = cursor.getString(cursor.getColumnIndexOrThrow("commentcontent"));
            String reg_date = cursor.getString(cursor.getColumnIndexOrThrow("reg_date"));
            // Populate fields with extracted properties
            txvComment.setText(content);
            txvDate.setText("Commetend on: "+reg_date);
        }
    }
}
