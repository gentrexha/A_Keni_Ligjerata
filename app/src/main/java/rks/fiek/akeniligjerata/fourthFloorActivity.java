package rks.fiek.akeniligjerata;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Context;
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
import android.widget.Toast;

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

public class fourthFloorActivity extends AppCompatActivity {

    ImageView imgv4thFloor;
    ImageView imgv4thFloor_Area;
    private static final String dbURL = "http://200.6.254.247/my-service.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth_floor);

        imgv4thFloor = (ImageView)findViewById(R.id.imgvPlan);
        imgv4thFloor_Area = (ImageView)findViewById(R.id.imgvPlan_Area);
        
        imgv4thFloor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        final int x = (int)motionEvent.getX();
                        final int y = (int)motionEvent.getY();
                        int touch_color = getHotspotColor(R.id.imgvPlan_Area,x,y);
                        int tolerance = 25;
                        if (closeMatch(Color.BLUE, touch_color,tolerance)) {
                            Intent intent = new Intent(getApplicationContext(), fifthFloorActivity.class);
                            startActivity(intent);
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

    public class RetrieveSchedule extends AsyncTask<Void,Void,JSONObject> {

        ProgressDialog progressDialog;
        Exception mException;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.mException = null;
            progressDialog = progressDialog.show(fourthFloorActivity.this, "Loading schedule...","TEST", true);
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {

            StringBuilder urlString = new StringBuilder();
            urlString.append(dbURL);

            HttpURLConnection objURLConnection = null;
            URL objURL;
            JSONObject objJSON = null;
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
                while ((line = objBReader.readLine()) != null)
                {
                    response += line;
                }
                objJSON = (JSONObject) new JSONTokener(response).nextValue();
            } catch (Exception e) {
                this.mException = e;
            }
            finally {
                if (objInStream != null) {
                    try {
                        objInStream.close(); // this will close the bReader as well
                    }
                    catch (IOException ignored) {
                    }
                }
                if (objURLConnection != null) {
                    objURLConnection.disconnect();
                }
            }
            return objJSON;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (this.mException != null) {
                Log.println(Log.ERROR,"JSON",this.mException.toString());
            }
            try {
                JSONArray objJSONArray = result.optJSONArray("411");
                JSONObject objJSONObject = objJSONArray.getJSONObject(0);
                Toast.makeText(getApplicationContext(),objJSONObject.getString("classname"),Toast.LENGTH_LONG).show();
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
