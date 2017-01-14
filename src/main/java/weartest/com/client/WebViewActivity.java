package weartest.com.client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by oleskiy on 25.08.16.
 */
public class WebViewActivity extends Activity {
    WebView web;
    weartest.com.client.dialog.Dialog customDialog;
   static  String url = null;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_item);

        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        web = (WebView)findViewById(R.id.web);
        web.setWebViewClient(new PostWebClient());
        customDialog = createLocalLoadingDialogInstance(this,"Wait",true);
        customDialog.show();


        web.getSettings().setJavaScriptEnabled(true);
        web.invalidate();
        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setUseWideViewPort(true);
        //  web.setWebViewClient(new PostWebClient());
        web.getSettings().setPluginState(WebSettings.PluginState.ON);

        web.getSettings().setDomStorageEnabled(true);
        web.loadUrl(url);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }
    public class PostWebClient extends WebViewClient {

        @JavascriptInterface
        @Override
        public void onPageFinished(WebView view, String url)
        {
            super.onPageFinished(view, url);

            web.setWebViewClient(new LoadWebClient());

            //web.loadUrl(WebRegistrationActivity.url + "&lang=" + ValuesAndPreferencesManager.getUILanguage());

            customDialog.dismiss();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {

            super.onReceivedError(view, errorCode, description, failingUrl);

        }



    }

    public class LoadWebClient extends WebViewClient {

        @JavascriptInterface
        @Override
        public void onPageFinished(WebView view, String url)
        {
            super.onPageFinished(view, WebViewActivity.url);
            customDialog.dismiss();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }


    }


    public static weartest.com.client.dialog.Dialog createLocalLoadingDialogInstance(
            Context context, String res_id, boolean isCancelable) {
        weartest.com.client.dialog.Dialog customDialog = new weartest.com.client.dialog.Dialog(context,
                Color.parseColor("#000000"),
                R.drawable.logo_small_purple,
                Color.parseColor("#ffffff"));

        customDialog.setCancelable(isCancelable);
        customDialog.setText(res_id);

        return customDialog;
    }
}
