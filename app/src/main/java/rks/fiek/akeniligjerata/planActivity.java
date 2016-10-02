package rks.fiek.akeniligjerata;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class planActivity extends AppCompatActivity implements View.OnTouchListener
{
    //ImageView imageView;

    float downx = 0;
    float downy = 0;
    float upx = 0;
    float upy = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        //imageView = (ImageView) this.findViewById(R.id.imgview);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        final int action = motionEvent.getAction();
        final int evX = (int) motionEvent.getX();
        final int evY = (int) motionEvent.getY();
        
        switch (action)
        {
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    public int getHotspotColor (int hotspotId, int x, int y)
    {
        ImageView img = (ImageView) findViewById(hotspotId);
        img.setDrawingCacheEnabled(true);
        Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
        img.setDrawingCacheEnabled(false);
        return hotspots.getPixel(x, y);
    }
}
