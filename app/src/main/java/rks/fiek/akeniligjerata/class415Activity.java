package rks.fiek.akeniligjerata;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class class415Activity extends AppCompatActivity {

    ListView list;
    ListView listComments;
    EditText content;
    private static final String commentDBURL = "http://200.6.254.247/comments.php?t=1&classroom=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class415);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        list = (ListView)findViewById(R.id.list);
        listComments = (ListView)findViewById(R.id.listComments);
        content = (EditText)findViewById(R.id.editText);
        DBHelper objDB = new DBHelper(this);

        Cursor lectureCursor = objDB.getTodayLectures("415");
        Cursor commentsCursor = objDB.getClassComments("415");

        if (lectureCursor.getCount()>0) {
            lectureCursorAdapter todoAdapter = new lectureCursorAdapter(this, lectureCursor);
            list.setAdapter(todoAdapter);
        }

        if (commentsCursor.getCount()>0) {
            commentCursorAdapter todoAdapter = new commentCursorAdapter(this, commentsCursor);
            listComments.setAdapter(todoAdapter);
        }
    }

    public void btnAddOnClick(View v)
    {
        String strContent = content.getText().toString();
        content.setText("");
        new InsertComment().execute("415",strContent);
        Toast.makeText(this,"Successfully posted comment!",Toast.LENGTH_SHORT).show();
    }

    public class InsertComment extends AsyncTask<String,Void,Void> {

        @Override
        protected Void doInBackground(String... strparams) {

            StringBuilder urlString = new StringBuilder();
            urlString.append(commentDBURL);
            urlString.append(strparams[0]);
            urlString.append("&commentcontent=");
            try {
                strparams[1] = URLEncoder.encode(strparams[1],"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            urlString.append(strparams[1]);
            Log.d("URL", urlString.toString());

            // Execute URL here...

            HttpURLConnection objURLConnection = null;
            URL objURL;
            InputStream objInStream = null;

            try {
                objURL = new URL(urlString.toString());
                objURLConnection = (HttpURLConnection) objURL.openConnection();
                objURLConnection.setRequestMethod("GET");
                objURLConnection.setDoOutput(true);
                objURLConnection.setDoInput(true);
                objURLConnection.connect();
                objURLConnection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (objURLConnection != null) {
                objURLConnection.disconnect();
            }
            return null;
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
