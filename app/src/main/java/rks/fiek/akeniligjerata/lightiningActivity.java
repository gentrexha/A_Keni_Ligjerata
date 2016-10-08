package rks.fiek.akeniligjerata;

import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class lightiningActivity extends AppCompatActivity {

    private TextView txvInfo2;
    private float LightValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lightining);

        Typeface tfBlackboard = Typeface.createFromAsset(getAssets(),"fonts/BlackBoard.ttf");
        TextView txvInfo = (TextView) findViewById(R.id.txvInfo);
        txvInfo.setTypeface(tfBlackboard);

        txvInfo2 = (TextView)findViewById(R.id.txvInfo2);
        txvInfo2.setTypeface(tfBlackboard);

        SensorManager mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor LightSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if(LightSensor != null){
            mySensorManager.registerListener(
                    LightSensorListener,
                    LightSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    // Suppressed because that's the default signature for onClick methods
    public void btnTestLightOnClick(@SuppressWarnings("UnusedParameters") View v)
    {
        if(LightValue <= 8)
        {
            txvInfo2.setText(R.string.bad_lightning);
        }
        else if(LightValue > 30 && LightValue <= 120)
        {
            txvInfo2.setText(R.string.ok_lightning);
        }
        else if ( LightValue > 120 && LightValue <= 250)
        {
            txvInfo2.setText(R.string.good_lightning);
        }
        else
        {
            txvInfo2.setText(R.string.verygood_lightning);
        }
    }

    private final SensorEventListener LightSensorListener = new SensorEventListener(){

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_LIGHT){
                LightValue = event.values[0];
            }
        }

    };
}
