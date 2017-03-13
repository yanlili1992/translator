package com.example.myapplicationtranslator.ui;

import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import com.example.myapplicationtranslator.R;

/**
 * Created by liuht on 2017/3/13.
 */

public class OpenSourceLicenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_source_licenses);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WebView webView = (WebView) findViewById(R.id.web_view);

        webView.getSettings().setBuiltInZoomControls(false);
        webView.loadUrl("file:///android_asset/open_source_licenses.html");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }

            return super.onOptionsItemSelected(menuItem);

        }

}
