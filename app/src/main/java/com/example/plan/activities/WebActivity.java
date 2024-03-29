package com.example.plan.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.example.plan.R;

public class WebActivity extends AppCompatActivity {

    private final String URL = "url";

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        webView = findViewById(R.id.web_view);

        // 自动选择打开方式（由于本地 webview 无法打开某些视频）
        webView.loadUrl(getIntent().getStringExtra(URL));

    }

    public static void actionStart(Activity activity, String url){
        Intent intent = new Intent(activity, WebActivity.class);
        intent.putExtra("url", url);
        activity.startActivity(intent);
    }

}
