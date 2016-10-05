package rks.fiek.akeniligjerata;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class fifthFloorActivity extends AppCompatActivity {

    ImageView imgvFifthFloor;
    ImageView imgvFifthFloorArea;
    ImageView imgvClass507Red;
    ImageView imgvClass507Green;
    ImageView imgvClass511Red;
    ImageView imgvClass511Green;
    ImageView imgvClass521Red;
    ImageView imgvClass521Green;
    ImageView imgvClass526Red;
    ImageView imgvClass526Green;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth_floor);

        imgvFifthFloor = (ImageView) findViewById(R.id.imgvFifth);
        imgvFifthFloorArea = (ImageView) findViewById(R.id.imgvFifthArea);
        imgvClass507Red = (ImageView) findViewById(R.id.class507Red);
        imgvClass507Green = (ImageView) findViewById(R.id.class507Green);
        imgvClass511Red = (ImageView) findViewById(R.id.class511Red);
        imgvClass511Green = (ImageView) findViewById(R.id.class511Green);
        imgvClass521Red = (ImageView) findViewById(R.id.class521Red);
        imgvClass521Green = (ImageView) findViewById(R.id.class521Green);
        imgvClass526Red = (ImageView) findViewById(R.id.class526Red);
        imgvClass526Green = (ImageView) findViewById(R.id.class526Green);

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
                        else if (closeMatch(Color.RED, touch_color,tolerance)) {

                        }
                        else if (closeMatch(Color.YELLOW, touch_color,tolerance)) {

                        }
                        else if (closeMatch(Color.GREEN, touch_color,tolerance)) {

                        }
                        else if (closeMatch(Color.BLACK, touch_color,tolerance)) {

                        }
                        else if (closeMatch(Color.WHITE, touch_color,tolerance)) {

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
}
