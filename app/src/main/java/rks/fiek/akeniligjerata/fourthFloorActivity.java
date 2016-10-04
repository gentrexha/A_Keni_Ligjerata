package rks.fiek.akeniligjerata;

import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class fourthFloorActivity extends AppCompatActivity {

    ImageView imgv4thFloor;
    ImageView imgv4thFloor_Area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth_floor);

        imgv4thFloor = (ImageView)findViewById(R.id.imgvPlan);
        imgv4thFloor_Area = (ImageView)findViewById(R.id.imgvPlan_Area);

        imgv4thFloor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int x = (int)motionEvent.getX();
                final int y = (int)motionEvent.getY();
                int touch_color = getHotspotColor(R.id.imgvPlan_Area,x,y);
                int tolerance = 25;
                if (closeMatch(Color.BLUE, touch_color,tolerance)) {
                    Log.d("TOUCH","Touched the screen at"+x+" and "+y);
                    return true;
                    // Intent intent = new Intent(getApplicationContext(), fifthFloorActivity.class);
                    // startActivity(intent);

                }
                return false;
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
}
