package com.gmonetix.batechnology;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class Home extends Fragment implements View.OnClickListener {

    private FloatingActionMenu fabMenu;
    private FloatingActionButton fabChat, fabQuote;
    private WebView webView;

    private Bundle webViewBundle;
    private View view;
    private ProgressDialog dialog;

    public Home() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view =  inflater.inflate(R.layout.fragment_home, container, false);

            fabChat = (FloatingActionButton) view.findViewById(R.id.fab_chat);
            fabQuote = (FloatingActionButton) view.findViewById(R.id.fab_quote);
            fabMenu = (FloatingActionMenu) view.findViewById(R.id.fab_more);
            fabMenu.setClosedOnTouchOutside(true);
            fabChat.setOnClickListener(this);
            fabQuote.setOnClickListener(this);

            webView = (WebView) view.findViewById(R.id.homeWebView);
            WiseWeWebClient myWebClient = new WiseWeWebClient();
            webView.setWebViewClient(myWebClient);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setDomStorageEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            } else {
                webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }

            dialog = new ProgressDialog(getActivity());
            dialog.setTitle("Loading...");
            dialog.setMessage("Loading... Please wait ...");
            dialog.show();

            if (webViewBundle != null)
            {
                webView.restoreState(webViewBundle);
            }
            else
            {
                webView.loadUrl("http://www.batechnology.org/");
            }
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webViewBundle = new Bundle();
        webView.saveState(webViewBundle);
    }

    @Override
    public void onPause() {
        super.onPause();
        webViewBundle = new Bundle();
        webView.saveState(webViewBundle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webViewBundle = new Bundle();
        webView.saveState(webViewBundle);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_chat:
                startActivity(new Intent(getActivity(),ChatActivity.class));
                break;

            case R.id.fab_quote:
                startActivity(new Intent(getActivity(), SendQuoteActivity.class));
                break;
        }
        fabMenu.close(true);
    }

    public class WiseWeWebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            dialog.dismiss();
            view.loadUrl("javascript:var nav = document.getElementById(\"main-nav-bg\"); nav.parentNode.removeChild(nav); javascript:var toTop = document.getElementById(\"toTop\"); toTop.parentNode.removeChild(toTop); javascript:var footer = document.getElementById(\"footer\"); footer.parentNode.removeChild(footer); javascript:var csbwfs = document.getElementById(\"csbwfs-delaydiv\"); csbwfs.parentNode.removeChild(csbwfs);");
        }

    }

}
