package s.hfad.com.myapplicationpaveltest.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import s.hfad.com.myapplicationpaveltest.R;

public class Activity_Web extends AppCompatActivity {

    private String url;
    private static final String TAG_URL=" s.hfad.com.myapplicationpaveltest.Activity/url_web.";
    private ProgressBar mProgressBar;
    private WebView mWebView;

    public static Intent newIntentActivityWeb(Context context,String url){
        Intent intent=new Intent(context,Activity_Web.class);
        intent.putExtra(TAG_URL,url);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__web);

        Intent intent=getIntent();
        url=intent.getStringExtra(TAG_URL);
        Log.e("Load",url);
        loadingWeb();

    }


    @SuppressLint("SetJavaScriptEnabled")
    private void loadingWeb(){
        mProgressBar=findViewById(R.id.progress_web);
        mProgressBar.setMax(100);

        mWebView=findViewById(R.id.page_web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if (newProgress==100){
                  mProgressBar.setVisibility(View.GONE);
                }else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });

        webShow();
    }

    private void webShow(){
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });

        mWebView.loadUrl(url);
    }
}












