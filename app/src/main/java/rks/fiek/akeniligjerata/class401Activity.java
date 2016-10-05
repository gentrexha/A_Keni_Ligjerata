package rks.fiek.akeniligjerata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class class401Activity extends AppCompatActivity {

    private WebView webDisqus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class401);

        webDisqus = (WebView) findViewById(R.id.disqus);
        //set up disqus
        WebSettings webSettings2 = webDisqus.getSettings();

        webSettings2.setJavaScriptEnabled(true);

        webSettings2.setBuiltInZoomControls(true);

        webDisqus.requestFocusFromTouch();

        webDisqus.setWebViewClient(new WebViewClient());

        webDisqus.setWebChromeClient(new WebChromeClient());

        webDisqus.loadUrl("http://200.6.254.247//showcomments.php?disqus_id="+401);
    }
}
