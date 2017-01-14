package weartest.com.client.menuitems;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import weartest.com.client.R;

/**
 * Created by oleskiy on 07.08.16.
 */
public class Privacy extends Fragment {

    Activity activity;
    public static Fragment newInstance(){
        Fragment fragment = new Privacy();

        return fragment;
    }
    final static public String LOG_TAG = "Privacy";
        WebView web;
        weartest.com.client.dialog.Dialog customDialog;
        static  String  url = "https://ta-kvisa.com/En/Privacy";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.menu_item, container, false);
            // TextView txt = (TextView)v.findViewById(R.id.menu_item);
            //txt.setText(str);
             web = (WebView)v.findViewById(R.id.web);
            web.setWebViewClient(new PostWebClient());
            customDialog = createLocalLoadingDialogInstance(getContext(),"Wait",true);
            customDialog.show();


            web.getSettings().setJavaScriptEnabled(true);
            web.invalidate();
            web.getSettings().setLoadWithOverviewMode(true);
            web.getSettings().setUseWideViewPort(true);
            //  web.setWebViewClient(new PostWebClient());
            web.getSettings().setPluginState(WebSettings.PluginState.ON);

            web.getSettings().setDomStorageEnabled(true);
            web.loadUrl("https://ta-kvisa.com/En/Privacy");


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    WebView.setWebContentsDebuggingEnabled(true);
            }

        return v;
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
                        super.onPageFinished(view, Privacy.url);
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



