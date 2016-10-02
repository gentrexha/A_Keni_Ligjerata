package rks.fiek.akeniligjerata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class mainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnPlanOnClick(View v)
    {
        Intent intPlan = new Intent(this, planActivity.class);
        startActivity(intPlan);
    }

    public void btnMapOnClick(View v)
    {
        Intent intent = new Intent(this, mapsActivity.class);
        startActivity(intent);
    }

}
