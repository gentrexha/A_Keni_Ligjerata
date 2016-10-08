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

public class mainActivity extends AppCompatActivity
{
    private int locationRequestCode;
    private TextView txvTitle;
    private DBHelper objDB;
    // float testLight = 15;

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



        Typeface tfBlackboard = Typeface.createFromAsset(getAssets(),"fonts/BlackBoard.ttf");
        txvTitle = (TextView)findViewById(R.id.txvTitle);
        txvTitle.setTypeface(tfBlackboard);
    }

    // Suppressed because that's the default signature for onClick methods
    public void btnPlanOnClick(@SuppressWarnings("UnusedParameters") View v)
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

    // Suppressed because that's the default signature for onClick methods
    public void btnTestLight(@SuppressWarnings("UnusedParameters") View view)
    {
        Intent intLightning = new Intent(this, lightiningActivity.class);
        startActivity(intLightning);
    }

    // Suppressed because that's the default signature for onClick methods
    public void btnMapOnClick(@SuppressWarnings("UnusedParameters") View v)
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
