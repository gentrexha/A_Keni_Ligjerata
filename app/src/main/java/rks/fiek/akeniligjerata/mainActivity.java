package rks.fiek.akeniligjerata;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class mainActivity extends AppCompatActivity
{
    private int locationRequestCode;
    TextView txvTitle;
    private DBHelper objDB;
    float testLight = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        objDB = new DBHelper(this);

        // Asks for permission to use location services
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},locationRequestCode);
            // locationRequestCode is an app-defined int constant. The callback method gets the result of the request.
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        Sensor LightSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if(LightSensor != null){
            mySensorManager.registerListener(
                    LightSensorListener,
                    LightSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }


        Typeface tfBlackboard = Typeface.createFromAsset(getAssets(),"fonts/BlackBoard.ttf");
        txvTitle = (TextView)findViewById(R.id.txvTitle);
        txvTitle.setTypeface(tfBlackboard);
    }

    public void btnPlanOnClick(View v)
    {
        Cursor objC = objDB.getAllLectures();
        if (objC.getCount()>0 || isNetworkAvailable()) {
            Intent intPlan = new Intent(this, fourthFloorActivity.class);
            startActivity(intPlan);
        }
        else {
            Intent intNoInt = new Intent(this, noInternetActivitiy.class);
            startActivity(intNoInt);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public void btnTestLight(View view)
    {
        if(testLight <= 8)
        {
            Toast.makeText(getApplicationContext(), "The light in this place is not preferable to learn.", Toast.LENGTH_SHORT).show();
        }
        else if(testLight > 30 && testLight <= 120)
        {
            Toast.makeText(getApplicationContext(), "The light in this place is almost good to learn.", Toast.LENGTH_SHORT).show();
        }
        else if ( testLight > 120 && testLight <= 250)
        {
            Toast.makeText(getApplicationContext(), "The light in this place is good to learn.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "The light in this place is very good to learn.", Toast.LENGTH_SHORT).show();
        }


    }

    private final SensorEventListener LightSensorListener
            = new SensorEventListener(){

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_LIGHT){
                testLight = event.values[0];
            }
        }

    };

    public void btnMapOnClick(View v)
    {
        Intent intent = new Intent(this, mapsActivity.class);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            startActivity(intent);
        else if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Maps doesn't work without location access!", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},locationRequestCode);
        }
    }

}
