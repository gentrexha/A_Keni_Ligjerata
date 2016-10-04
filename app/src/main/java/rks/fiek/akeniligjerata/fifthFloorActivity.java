package rks.fiek.akeniligjerata;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class fifthFloorActivity extends AppCompatActivity {

    TouchImageView imgvFifthFloor;
    TouchImageView imgvFifthFloorArea;
    TouchImageView imgvClass507Red;
    TouchImageView imgvClass507Green;
    TouchImageView imgvClass511Red;
    TouchImageView imgvClass511Green;
    TouchImageView imgvClass521Red;
    TouchImageView imgvClass521Green;
    TouchImageView imgvClass526Red;
    TouchImageView imgvClass526Green;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth_floor);

        imgvFifthFloor = (TouchImageView) findViewById(R.id.imgvFifth);
        imgvFifthFloorArea = (TouchImageView) findViewById(R.id.imgvFifthArea);
        imgvClass507Red = (TouchImageView) findViewById(R.id.class507Red);
        imgvClass507Green = (TouchImageView) findViewById(R.id.class507Green);
        imgvClass511Red = (TouchImageView) findViewById(R.id.class511Red);
        imgvClass511Green = (TouchImageView) findViewById(R.id.class511Green);
        imgvClass521Red = (TouchImageView) findViewById(R.id.class521Red);
        imgvClass521Green = (TouchImageView) findViewById(R.id.class521Green);
        imgvClass526Red = (TouchImageView) findViewById(R.id.class526Red);
        imgvClass526Green = (TouchImageView) findViewById(R.id.class526Green);

        imgvFifthFloor.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {
            @Override
            public void onMove() {
                imgvFifthFloorArea.setZoom(imgvFifthFloor);
                imgvClass507Red.setZoom(imgvFifthFloor);
                imgvClass507Green.setZoom(imgvFifthFloor);
                imgvClass511Red.setZoom(imgvFifthFloor);
                imgvClass511Green.setZoom(imgvFifthFloor);
                imgvClass521Red.setZoom(imgvFifthFloor);
                imgvClass521Green.setZoom(imgvFifthFloor);
                imgvClass526Red.setZoom(imgvFifthFloor);
                imgvClass526Green.setZoom(imgvFifthFloor);
            }
        });

        imgvFifthFloorArea.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {
            @Override
            public void onMove() {
                imgvFifthFloor.setZoom(imgvFifthFloorArea);
            }
        });

        imgvClass507Red.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {
            @Override
            public void onMove() {
                imgvFifthFloor.setZoom(imgvClass507Red);
                imgvFifthFloorArea.setZoom(imgvClass507Red);
            }
        });

        imgvClass507Green.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {
            @Override
            public void onMove() {
                imgvFifthFloor.setZoom(imgvClass507Green);
            }
        });

        imgvClass511Red.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {
            @Override
            public void onMove() {
                imgvFifthFloor.setZoom(imgvClass511Red);
                imgvFifthFloorArea.setZoom(imgvClass511Red);
            }
        });

        imgvClass511Green.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {
            @Override
            public void onMove() {
                imgvFifthFloor.setZoom(imgvClass511Green);
            }
        });

        imgvClass521Red.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {
            @Override
            public void onMove() {
                imgvFifthFloor.setZoom(imgvClass521Red);
            }
        });

        imgvClass521Green.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {
            @Override
            public void onMove() {
                imgvFifthFloor.setZoom(imgvClass521Green);
            }
        });

        imgvClass526Red.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {
            @Override
            public void onMove() {
                imgvFifthFloor.setZoom(imgvClass526Red);
            }
        });

        imgvClass526Green.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {
            @Override
            public void onMove() {
                imgvFifthFloor.setZoom(imgvClass526Green);
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
