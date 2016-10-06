package rks.fiek.akeniligjerata;

import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class fourthFloorActivity extends AppCompatActivity {

    // ImageViews
    ImageView imgv4thFloor;
    ImageView imgv4thFloor_Area;

    private DBHelper objDB;
    public int nrRowsInDatabase;

    private static final String dbURL = "http://200.6.254.247/my-service.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth_floor);

        imgv4thFloor = (ImageView) findViewById(R.id.imgvPlan);
        imgv4thFloor_Area = (ImageView) findViewById(R.id.imgvPlan_Area);
        objDB = new DBHelper(this);

        Cursor cursor = objDB.getAllLectures();
        nrRowsInDatabase = cursor.getCount();

        int nrRows = cursor.getCount();

        if(!isNetworkAvailable()) {
            if (nrRows != nrRowsInDatabase) {
                objDB.dropLectures();
                new RetrieveSchedule().execute();
            } else if (nrRows < 1) {
                new RetrieveSchedule().execute();
                Log.d("nrRows<1: ", "pa u insert asniher");
            } else {
                colorRoom("411");
                Log.d("nrRows>=1: ", "masi te insertohen");
            }
        }
        else
        {
            if(nrRows>0)
            {
                colorRoom("411");
            }

        }

        imgv4thFloor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        final int x = (int) motionEvent.getX();
                        final int y = (int) motionEvent.getY();
                        int touch_color = getHotspotColor(R.id.imgvPlan_Area, x, y);
                        int tolerance = 25;
                        if (closeMatch(Color.BLUE, touch_color, tolerance)) {
                            Intent intFifthFloor = new Intent(getApplicationContext(), fifthFloorActivity.class);
                            startActivity(intFifthFloor);
                        }
                        if (closeMatch(Color.RED, touch_color, tolerance)) {
                            Intent int401 = new Intent(getApplicationContext(), class401Activity.class);
                            startActivity(int401);
                        }
                        if (closeMatch(Color.YELLOW, touch_color, tolerance)) {
                            Intent int414 = new Intent(getApplicationContext(), class414Activity.class);
                            startActivity(int414);
                        }
                        if (closeMatch(Color.GREEN, touch_color, tolerance)) {
                            Intent int408 = new Intent(getApplicationContext(), class408Activity.class);
                            startActivity(int408);
                        }
                        if (closeMatch(Color.BLACK, touch_color, tolerance)) {
                            Intent int411 = new Intent(getApplicationContext(), class411Activity.class);
                            startActivity(int411);
                        }
                        if (closeMatch(Color.CYAN, touch_color, tolerance)) {
                            Intent int415 = new Intent(getApplicationContext(), class415Activity.class);
                            startActivity(int415);
                        }
                        break;
                }
                return true;
            }
        });
    }

    public int getHotspotColor(int hotspotId, int x, int y) {
        ImageView img = (ImageView) findViewById(hotspotId);
        img.setDrawingCacheEnabled(true);
        Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
        img.setDrawingCacheEnabled(false);
        return hotspots.getPixel(x, y);
    }

    public boolean closeMatch(int color1, int color2, int tolerance) {
        if (Math.abs(Color.red(color1) - Color.red(color2)) > tolerance)
            return false;
        if (Math.abs(Color.green(color1) - Color.green(color2)) > tolerance)
            return false;
        if (Math.abs(Color.blue(color1) - Color.blue(color2)) > tolerance)
            return false;
        return true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public class RetrieveSchedule extends AsyncTask<Void, Void, JSONArray> {

        // ProgressDialog progressDialog;
        Exception mException;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.mException = null;
            // progressDialog = progressDialog.show(fourthFloorActivity.this, "Loading schedule...","TEST", true);
        }

        @Override
        protected JSONArray doInBackground(Void... voids) {

            StringBuilder urlString = new StringBuilder();
            urlString.append(dbURL);

            HttpURLConnection objURLConnection = null;
            URL objURL;
            JSONArray objJSON = null;
            InputStream objInStream = null;

            try {
                objURL = new URL(urlString.toString());
                objURLConnection = (HttpURLConnection) objURL.openConnection();
                objURLConnection.setRequestMethod("GET");
                objURLConnection.setDoOutput(true);
                objURLConnection.setDoInput(true);
                objURLConnection.connect();
                objInStream = objURLConnection.getInputStream();
                BufferedReader objBReader = new BufferedReader(new InputStreamReader(objInStream));
                String line;
                String response = "";
                while ((line = objBReader.readLine()) != null) {
                    response += line;
                }
                objJSON = (JSONArray) new JSONTokener(response).nextValue();
            } catch (Exception e) {
                this.mException = e;
            } finally {
                if (objInStream != null) {
                    try {
                        objInStream.close(); // this will close the bReader as well
                    } catch (IOException ignored) {
                    }
                }
                if (objURLConnection != null) {
                    objURLConnection.disconnect();
                }
            }
            return objJSON;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            super.onPostExecute(result);
            // progressDialog.dismiss();
            if (this.mException != null) {
                Log.e("JSON Exception", this.mException.toString());
            }
            // Log.d("JSONARRAY",""+result.length());
            try {
                for (int i = 0; i < result.length(); i++) {
                    JSONObject jsonObjectLecture = result.getJSONObject(i);
                    int lectureID = jsonObjectLecture.getInt("id");
                    String lectureDay = jsonObjectLecture.getString("day");
                    String lectureClassNumber = jsonObjectLecture.getString("classnumber");
                    String lectureClassName = jsonObjectLecture.getString("classname");
                    String lectureStartTime = jsonObjectLecture.getString("starttime");
                    String lectureEndTime = jsonObjectLecture.getString("endtime");
                    Lecture lecture = new Lecture(lectureID, lectureDay, lectureClassNumber,
                            lectureClassName, lectureStartTime, lectureEndTime);
                    objDB.insertLecture(lecture);
                }
                nrRowsInDatabase = result.length();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void colorRoom(String classnumber) {

        Cursor objCursor = objDB.getTodayLecturesTimes(classnumber);
        ArrayList<String> startTime = new ArrayList<String>();
        ArrayList<String> endTime = new ArrayList<String>();

        for (objCursor.moveToFirst(); !objCursor.isAfterLast(); objCursor.moveToNext()) {
            // TO DO code here
            startTime.add(objCursor.getString(0));
            endTime.add(objCursor.getString(1));
        }

        objCursor.close();
        int fara = startTime.size();
        String florim = startTime.get(0);
        Log.d("Florim: ", fara + " " + florim + "  " + endTime.size() + endTime.get(0));
    }


}