package rks.fiek.akeniligjerata;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class mainActivity extends AppCompatActivity
{
    private int locationRequestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnPlanOnClick(View v)
    {
        Intent intPlan = new Intent(this, fourthFloorActivity.class);
        startActivity(intPlan);
    }

    public void btnMapOnClick(View v)
    {
        Intent intent = new Intent(this, mapsActivity.class);
        // Asks for permission to use location services
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},locationRequestCode);
            // locationRequestCode is an app-defined int constant. The callback method gets the result of the request.
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            startActivity(intent);
        else
            Toast.makeText(getApplicationContext(), "Maps doesn't work without location access!", Toast.LENGTH_LONG).show();
    }

}
