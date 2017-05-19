package com.gmonetix.batechnology.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gmonetix.batechnology.AboutUsActivity;
import com.gmonetix.batechnology.MainActivity;
import com.gmonetix.batechnology.R;
import com.gmonetix.batechnology.SendQuoteActivity;
import com.gmonetix.batechnology.helper.Const;
import com.gmonetix.batechnology.helper.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ServiceClickedActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private WebView webView;
    private Button button;

    private String title, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_clicked);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().hasExtra(Const.INTENT_SERVICE_TITLE)) {
            title = getIntent().getExtras().getString(Const.INTENT_SERVICE_TITLE);
        } else {
            title = "";
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (title.equals(getResources().getString(R.string.web_development))) {
            url = getResources().getString(R.string.web_development_content_url);
        } else if (title.equals(getResources().getString(R.string.e_commerce_solution))) {
            url = getResources().getString(R.string.e_commerce_solution_content_url);
        } else if (title.equals(getResources().getString(R.string.mobile_app_dev))) {
            url = getResources().getString(R.string.mobile_app_dev_content_url);
        } else if (title.equals(getResources().getString(R.string.seo))) {
            url = getResources().getString(R.string.seo_content_url);
        } else if (title.equals(getResources().getString(R.string.logo_branding))) {
            url = getResources().getString(R.string.logo_branding_content_url);
        }

        ServiceClickedActivity.this.setTitle(title);
        Utils.applyFontForToolbarTitle(ServiceClickedActivity.this);

        final ProgressDialog progressDialog = new ProgressDialog(ServiceClickedActivity.this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Loading..Please wait..");
        progressDialog.show();

        webView = (WebView) findViewById(R.id.webView);
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
        webView.loadUrl(url);

        button = (Button) findViewById(R.id.rqst_a_quote_btn);
        Utils.setFont(this,button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceClickedActivity.this, SendQuoteActivity.class);
                intent.putExtra(Const.INTENT_SERVICE_TITLE,title);
                startActivity(intent);

            }
        });

    }

}
