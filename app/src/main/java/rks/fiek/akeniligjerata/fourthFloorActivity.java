package rks.fiek.akeniligjerata;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class fourthFloorActivity extends AppCompatActivity {

    ImageView imgv4thFloor;
    ImageView imgv4thFloor_Area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD:app/src/main/java/rks/fiek/akeniligjerata/planActivity.java
        setContentView(R.layout.activity_plan);
=======
        setContentView(R.layout.activity_fourthfloor);
>>>>>>> origin/master:app/src/main/java/rks/fiek/akeniligjerata/fourthFloorActivity.java

        imgv4thFloor = (ImageView)findViewById(R.id.imgvPlan);
        imgv4thFloor_Area = (ImageView)findViewById(R.id.imgvPlan_Area);

<<<<<<< HEAD:app/src/main/java/rks/fiek/akeniligjerata/planActivity.java

        //
=======
>>>>>>> origin/master:app/src/main/java/rks/fiek/akeniligjerata/fourthFloorActivity.java
        imgv4thFloor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                final int x = (int)motionEvent.getX();
                final int y = (int)motionEvent.getY();
                int touch_color = getHotspotColor(R.id.imgvPlan_Area,x,y);
                int tolerance = 25;
                if (closeMatch(Color.BLUE, touch_color,tolerance))
                    Log.d("TOUCH","Touched the screen at"+x+" and "+y);
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
