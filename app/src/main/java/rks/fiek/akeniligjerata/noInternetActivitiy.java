package rks.fiek.akeniligjerata;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class noInternetActivitiy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        Typeface tfBlackboard = Typeface.createFromAsset(getAssets(),"fonts/BlackBoard.ttf");
        TextView txvInfo = (TextView) findViewById(R.id.txvInfo);
        txvInfo.setTypeface(tfBlackboard);
    }
}
