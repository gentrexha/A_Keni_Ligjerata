package rks.fiek.akeniligjerata;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import java.util.Date;

//Reference:
//http://stackoverflow.com/questions/1200621/how-to-declare-an-array
//http://stackoverflow.com/questions/3481828/how-to-split-a-string-in-java
//http://stackoverflow.com/questions/13397709/android-hide-imageview

public class fifthFloorActivity extends AppCompatActivity {

    ImageView imgvFifthFloor;
    ImageView imgvFifthFloorArea;
    ImageView imageView;

    private DBHelper objDB;
    private static final String dbURL = "http://200.6.254.247/my-service.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth_floor);

        imgvFifthFloor = (ImageView) findViewById(R.id.imgvFifth);
        imgvFifthFloorArea = (ImageView) findViewById(R.id.imgvFifthArea);

        objDB = new DBHelper(this);
        Cursor cursor = objDB.getAllLectures();

        if (isNetworkAvailable())
        {
            new RetrieveSchedule().execute();
        }


        colorAvailability("507");
        colorAvailability("511");
        colorAvailability("521");
        colorAvailability("526");

        imgvFifthFloor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
                        final int x = (int) motionEvent.getX();
                        final int y = (int) motionEvent.getY();
                        int touch_color = getHotspotColor(R.id.imgvFifthArea, x, y);
                        int tolerance = 25;
                        if (closeMatch(Color.BLUE, touch_color, tolerance)) {
                            Log.d("TOUCH", "Touched the screen at" + x + " and " + y);
                            Intent intent = new Intent(getApplicationContext(), fourthFloorActivity.class);
                            startActivity(intent);
                        }
                        if (closeMatch(Color.CYAN, touch_color,tolerance)) {
                            Intent int507 = new Intent(getApplicationContext(), class507Activity.class);
                            startActivity(int507);
                            Log.d("Klasa: ", "507");
                        }
                        if (closeMatch(Color.RED, touch_color,tolerance)) {
                            Intent int521 = new Intent(getApplicationContext(), class521Activity.class);
                            startActivity(int521);
                            Log.d("Klasa: ", "521");
                        }
                        if (closeMatch(Color.YELLOW, touch_color,tolerance)) {
                            Intent int526 = new Intent(getApplicationContext(), class526Activity.class);
                            startActivity(int526);
                            Log.d("Klasa: ", "526");
                        }
                        if (closeMatch(Color.GREEN, touch_color,tolerance)) {
                            Intent int511 = new Intent(getApplicationContext(), class511Activity.class);
                            startActivity(int511);
                            Log.d("Klasa: ", "511");
                        }
                        break;
                }
                return true;
            }
        });


    }

    public int getHotspotColor (int hotspotId, int x, int y) {
        ImageView img = (ImageView) findViewById (hotspotId);
        img.setDrawingCacheEnabled(true);
        Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
        img.setDrawingCacheEnabled(false);
        return hotspots.getPixel(x, y);
    }

    public boolean closeMatch (int color1, int color2, int tolerance) {
        if ( Math.abs (Color.red (color1) - Color.red (color2)) > tolerance )
            return false;
        if ( Math.abs (Color.green (color1) - Color.green (color2)) > tolerance )
            return false;
        if ( Math.abs (Color.blue (color1) - Color.blue (color2)) > tolerance )
            return false;
        return true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public class RetrieveSchedule extends AsyncTask<Void, Void, JSONArray> {

        ProgressDialog progressDialog;
        Exception mException;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.mException = null;
            progressDialog = progressDialog.show(fifthFloorActivity.this,
                    "Loading schedule...","Please wait while the schedule is downloading", true);
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
            progressDialog.dismiss();

            if (this.mException != null) {
                Log.e("JSON Exception", this.mException.toString());
            }
            try {
                for (int i = 0; i < result.length(); i++) {
                    JSONObject jsonObjectLecture = result.getJSONObject(i);
                    int lectureID = jsonObjectLecture.getInt("id");
                    String lectureDay = jsonObjectLecture.getString("day");
                    String lectureClassNumber = jsonObjectLecture.getString("classnumber");
                    String lectureClassName = jsonObjectLecture.getString("classname");
                    String lectureStartTime = jsonObjectLecture.getString("starttime");
                    String lectureEndTime = jsonObjectLecture.getString("endtime");
                    objDB.insertLecture(lectureID, lectureDay, lectureClassNumber,lectureClassName, lectureStartTime, lectureEndTime);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void colorAvailability(String classnumber) {

        Cursor objCursor = objDB.getTodayLecturesTimes(classnumber);
        ArrayList<String> startTime = new ArrayList<>();
        ArrayList<String> endTime = new ArrayList<>();

        int nrRows = objCursor.getCount();

        if(nrRows >0 ) {
            for (objCursor.moveToFirst(); !objCursor.isAfterLast(); objCursor.moveToNext()) {
                // TO DO code here
                startTime.add(objCursor.getString(0));
                endTime.add(objCursor.getString(1));
            }
        }

        objCursor.close();
        if(nrRows < 1) {
            String nrClass = "imgvClass" + classnumber +"Green";
            int resID = getResources().getIdentifier(nrClass, "id", getPackageName());
            imageView = (ImageView) findViewById(resID);
            imageView.setVisibility(View.VISIBLE);
        }
        else
        {
            int numberOfRows = startTime.size();
            for (int i = 0; i < numberOfRows; i++)
            {
                // DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                int hour = date.getHours();
                int minutes = date.getMinutes();
                int seconds = date.getSeconds();
                String[] parts1 = startTime.get(i).split(":");
                int[] NrStartTime = {Integer.parseInt(parts1[0]), Integer.parseInt(parts1[1]), Integer.parseInt(parts1[2])};
                String[] parts2 = endTime.get(i).split(":");
                int[] NrEndTime = {Integer.parseInt(parts2[0]), Integer.parseInt(parts2[1]), Integer.parseInt(parts2[2])};
                if(NrStartTime[0] <= hour && hour <= NrEndTime[0])
                {
                    if(NrStartTime[1] <= minutes && NrEndTime[1] <= minutes)
                    {
                        if(NrStartTime[2] <= seconds)
                        {
                            String nrClass = "imgvClass" + classnumber +"Red";
                            int resID = getResources().getIdentifier(nrClass, "id", getPackageName());
                            imageView = (ImageView) findViewById(resID);
                            imageView.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            String nrClass = "imgvClass" + classnumber +"Green";
                            int resID = getResources().getIdentifier(nrClass, "id", getPackageName());
                            imageView = (ImageView) findViewById(resID);
                            imageView.setVisibility(View.VISIBLE);
                        }
                    }
                    else if(NrStartTime[0] < hour && NrEndTime[0] <= hour)
                    {
                        if(NrEndTime[1] <= minutes)
                        {
                            if(NrStartTime[2] <= seconds)
                            {
                                String nrClass = "imgvClass" + classnumber +"Red";
                                int resID = getResources().getIdentifier(nrClass, "id", getPackageName());
                                imageView = (ImageView) findViewById(resID);
                                imageView.setVisibility(View.VISIBLE);

                            }
                            else
                            {
                                String nrClass = "imgvClass" + classnumber +"Green";
                                int resID = getResources().getIdentifier(nrClass, "id", getPackageName());
                                imageView = (ImageView) findViewById(resID);
                                imageView.setVisibility(View.VISIBLE);
                            }
                        }
                        else
                        {
                            String nrClass = "imgvClass" + classnumber +"Green";
                            int resID = getResources().getIdentifier(nrClass, "id", getPackageName());
                            imageView = (ImageView) findViewById(resID);
                            imageView.setVisibility(View.VISIBLE);
                        }

                    }
                    else
                    {
                        String nrClass = "imgvClass" + classnumber +"Green";
                        int resID = getResources().getIdentifier(nrClass, "id", getPackageName());
                        imageView = (ImageView) findViewById(resID);
                        imageView.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    String nrClass = "imgvClass" + classnumber +"Green";
                    int resID = getResources().getIdentifier(nrClass, "id", getPackageName());
                    imageView = (ImageView) findViewById(resID);
                    imageView.setVisibility(View.VISIBLE);
                }


            }
        }

    }
}
