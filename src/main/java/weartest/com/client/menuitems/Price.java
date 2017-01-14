package weartest.com.client.menuitems;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


import weartest.com.client.Profile;
import weartest.com.client.R;

/**
 * Created by oleskiy on 01.08.16.
 */
public class Price extends Fragment {
String str = "Laundry and ironing\n" +
        "\n" +
        "Shirt \\ pants + ironing - 19 Nis\n" +
        "\n" +
        "Ironing\n" +
        "\n" +
        "Shirt- 9 NIS\n" +
        "\n" +
        "Day Dress- 15 NIS\n" +
        "\n" +
        "Pants- 10 NIS\n" +
        "\n" +
        "Dress - 20 NIS\n" +
        "\n" +
        "Sheet- 19 NIS\n" +
        "\n" +
        "Bed linen- 25 NIS\n" +
        "\n" +
        "Pillowcase- 8 NIS\n" +
        "\n" +
        "Jacket- 23 NIS\n" +
        "\n" +
        "Dry Cleaning-\n" +
        "\n" +
        "Trousers \\ shirt- 28-38 NIS\n" +
        "\n" +
        "Coat- 60-75 NIS\n" +
        "\n" +
        "Dress- 49-89 NIS\n" +
        "\n" +
        "Skirt- 35-45 NIS\n" +
        "\n" +
        "Jackets- 45-65 NIS\n" +
        "\n" +
        "Jumper- 28-38 NIS\n" +
        "\n" +
        "Sweater- 35-49 NIS\n" +
        "\n" +
        "West- 28-38 NIS\n" +
        "\n" +
        "Tie- 18 NIS\n" +
        "\n" +
        "Map- 68 NIS\n" +
        "\n" +
        "Sheet- 38 NIS\n" +
        "\n" +
        "Bed linen- 49 NIS\n" +
        "\n" +
        "Pillowcase- 19 NIS\n" +
        "\n" +
        "Tallit- 39 NIS\n" +
        "\n" +
        "Suit starting from- 65 NIS\n" +
        "\n" +
        "Starting wedding dress 129 NIS\n" +
        "\n" +
        "Curtain ranging from- 26 NIS per meter.\n" +
        "\n" +
        "blankets-\n" +
        "\n" +
        "Akrilin (wool) Single / Double- 59-69 NIS\n" +
        "\n" +
        "Synthetic feather Single / Double- 69-79 NIS\n" +
        "\n" +
        "Down feathers Single / Double- 85-99 NIS\n" +
        "\n" +
        "Synthetic Pillow 39 NIS\n" +
        "\n" +
        "Feather pillow- 49 NIS\n" +
        "\n" +
        "Carpets- per meter-\n" +
        "\n" +
        "Wool, Normal 68 NIS\n" +
        "\n" +
        "Chinese / Persian / Shaggy 89 NIS\n" +
        "\n" +
        "Silk / handmade / kilim 140 NIS\n" +
        "\n" +
        "• The minimum price for an order ₪ 63.\n" +
        "\n" +
        "• After we check the weight and type of items, we know the final price.\n" +
        "\n" +
        "• price may change from time to time.";
    Activity activity;
    WebView web;
    public static Fragment newInstance(){
        Fragment fragment = new Price();

        return fragment;
    }
    final static public String LOG_TAG = "price";
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
            web.loadUrl("https://www.ta-kvisa.com/En/Prices");
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
