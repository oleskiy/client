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
import android.widget.TextView;

import weartest.com.client.R;

/**
 * Created by oleskiy on 01.08.16.
 */
public class Terms extends Fragment {
    final static public String LOG_TAG = "terms";
String str = "Your part-\n" +
        "\n" +
        "Dowload the app TA-Kvisa\n" +
        "\n" +
        "- .Register and enter your credit card information\n" +
        "\n" +
        "- You will fill out your information only one time, we don&#39;t save your\n" +
        "\n" +
        "credit card details in the system. (Your payment is secure)\n" +
        "\n" +
        "- Reserve your locker.\n" +
        "\n" +
        "- You can reserve your locker for 15-25 minutes ONLY.\n" +
        "\n" +
        "- After this, the system will save an avilable locker for you to use.\n" +
        "\n" +
        "- Make the reservation using the application.\n" +
        "\n" +
        "- When you arrive to the locker, Scan the QR code to open it.\n" +
        "\n" +
        "- Make sure you lock it before you go!\n" +
        "\n" +
        "- Within the next 72 hours we will collect your laundry and will update you\n" +
        "\n" +
        "Our Part-\n" +
        "\n" +
        "- We will collect your laundry from the locker and will transport it to the\n" +
        "\n" +
        "- We will review the items that your provide and will abide to the\n" +
        "\n" +
        "- At the end of the process you will recieve a message that your clothes are\n" +
        "\n" +
        "- Your clothes will be returned clean and with a great smell!\n" +
        "\n" +
        "- A copy of the reciet wil be sent to your email.\n" +
        "\n" +
        "How to use-\n" +
        "\n" +
        "- .Each locker can only be opned through the application\n" +
        "\n" +
        "- Openning and closing the locker is personal to each customer.\n" +
        "\n" +
        "- You may only use the locker when there are avilable lockers.\n" +
        "\n" +
        "- .Your laundry will be returned to whichever locker is avilable at that time\n" +
        "\n" +
        "when its ready. It will be returned at the approximate time that you chose.\n" +
        "\n" +
        "landromat.\n" +
        "\n" +
        "instructions your provided to us on the application.\n" +
        "\n" +
        "returned to an avilable locker.";
    Activity activity;
    public static Fragment newInstance(){
        Fragment fragment = new Terms();

        return fragment;
    }
    WebView web;
    weartest.com.client.dialog.Dialog customDialog;
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
        web.loadUrl("https://ta-kvisa.com/En/TermsOfServices");
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
