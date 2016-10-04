package rks.fiek.akeniligjerata;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class fourthFloorActivity extends AppCompatActivity {

    TouchImageView imgv4thFloor;
    TouchImageView imgv4thFloor_Area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourthfloor);

        imgv4thFloor = (TouchImageView)findViewById(R.id.imgvPlan);
        imgv4thFloor_Area = (TouchImageView)findViewById(R.id.imgvPlan_Area);

        imgv4thFloor.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {
            @Override
            public void onMove() {
                imgv4thFloor_Area.setZoom(imgv4thFloor);
            }
        });

        imgv4thFloor_Area.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {
            @Override
            public void onMove() {
                imgv4thFloor.setZoom(imgv4thFloor_Area);
            }
        });

        imgv4thFloor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                final int x = (int)motionEvent.getX();
                final int y = (int)motionEvent.getY();
                int touch_color = getHotspotColor(R.id.imgvPlan_Area,x,y);
                int tolerance = 25;
                if (closeMatch(Color.BLUE, touch_color,tolerance))
                    Toast.makeText(getApplicationContext(),"Pressed a blue box!",Toast.LENGTH_LONG).show();
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
}
