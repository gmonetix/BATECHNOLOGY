package com.gmonetix.batechnology;

import android.app.ProgressDialog;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gmonetix.batechnology.helper.Utils;


public class AboutUsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AboutUsActivity.this.setTitle("About Us");

        Utils.applyFontForToolbarTitle(AboutUsActivity.this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final ProgressDialog progressDialog = new ProgressDialog(AboutUsActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Loading..Please wait..");
        progressDialog.show();

        webView = (WebView) findViewById(R.id.about_us_web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                progressDialog.dismiss();
                super.onPageFinished(view, url);
            }
        });
        webView.loadUrl(getResources().getString(R.string.about_us));

    }

}
