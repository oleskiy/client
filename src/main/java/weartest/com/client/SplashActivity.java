package weartest.com.client;

import android.*;
import android.Manifest;
import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Observable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.appstate.AppState;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import weartest.com.client.dialog.CrushReportManager;
import weartest.com.client.dialog.CustomUncaughtExpetionHandler;
import weartest.com.client.language.LanguageManager;
import weartest.com.client.language.LanguageObj;
import weartest.com.client.timer.CustomProgressBar;
import weartest.com.client.timer.PositionTimerView;
import weartest.com.client.timer.SixtySecondLargeView;
import weartest.com.client.timer.SixtySecondSmallView;
import weartest.com.client.timer.SixtySecondViewManager;
import weartest.com.client.zxing.MyLocationListener;

import static android.R.attr.delay;

/**
 * Created by oleskiy on 04.08.16.
 */
public class SplashActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    LocationListener locationListener;
    String lang;
    GoogleApiClient mGoogleApiClient;
    private Timer updateTimer;

     private GoogleApiClient client;


     boolean largeViewBitmapsInitiated = true;
    int largesixtyViewMAxSize;


    public static int pxFromDp(Context ctx, float dp) {
        return (int) (dp * ctx.getResources().getDisplayMetrics().density);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);




       /* ll.getLongitude();
        ll.getLatitude();
        ll.stopUsingGPS();*/


        ValuesAndPreferencesManager.initPrefManager(this);
        Thread.setDefaultUncaughtExceptionHandler(new CustomUncaughtExpetionHandler("stucktrace.txt"));

        lang = Locale.getDefault().getDisplayLanguage();
        //setJson();
        //      LanguageManager.initLanguageManager();
        //   new getGeo().execute();


//new Sleep().execute();
        //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        // startActivity(intent);
        // new Sleep().execute();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    CrushReportManager crushReportManager;

    @Override
    protected void onResume() {
        super.onResume();
        final Intent intent = new Intent(SplashActivity.this, MainActivity.class);

        try {
            if (ValuesAndPreferencesManager.getLangId() == 1)
                LanguageObj.getLanguageObjectInstance().setNewLang(2);
            else LanguageObj.getLanguageObjectInstance().setNewLang(1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        crushReportManager = new CrushReportManager(SplashActivity.this,
                "stucktrace.txt");
        crushReportManager
                .setCrushReportDialogActionCallback(new CrushReportManager.OnReportActionDialogCallback() {
                    @Override
                    public void onCancel() {
                        startActivity(intent);
                    }
                });

        if (crushReportManager.isToShowCrushNotification()) {
            crushReportManager.startCrushNotificationDialog(SplashActivity.this);
        } else {
            startActivity(intent);
        }

    }

    Location mLastLocation;

    protected void onStart() {

        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    protected void onStop() {
        //   mGoogleApiClient.disconnect();
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            Log.d("Test", "location1 = " + mLastLocation.getLatitude());
            float[] ff = new float[7];
            Location loc1 = new Location("");
            Location loc2 = new Location("");

            loc1.setLatitude(32.085299899999995);
            loc1.setLongitude(34.781767599999995);

            loc1.setLatitude(32.428907);
            loc1.setLongitude(34.931035);

            ;
            Location.distanceBetween(32.428907, 34.931035,
                    32.085299899999995, 34.781767599999995,
                    ff);
            DecimalFormat format = new DecimalFormat("#.##");
            double distanceInMeters = loc1.distanceTo(loc2) / 100000;
            Log.d("test", "ff" + ff[0] + " " + format.format(distanceInMeters));
            //    mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            //  mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        } else {
            Log.d("Test", "location1 = null");

            MyLocationListener mylistner = new MyLocationListener(SplashActivity.this);
            Log.d("Test", "location1 = null " + mylistner.longitude);
        }
    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.d("Test", "location1 = onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("Test", "location1 = onConnectionFailed");
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Splash Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }




    class getGeo extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(Void... params) {
            if (lang.equals("עברית")) {
                ValuesAndPreferencesManager.saveLangId(1);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Getting the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);

            // Getting Current Location
            if (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            //Location location = locationManager.getLastKnownLocation(provider);
            // if(location!=null)
            // Log.d("Test", "location1 = " + location.toString());
            // else Log.d("Test", "location1 = null" );
            final LocationManager[] locationManager1 = {(LocationManager) getSystemService(Context.LOCATION_SERVICE)};
            locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    // Called when a new location is found by the network location provider.
                    //  makeUseOfNewLocation(location);
                    Log.d("Test", "location1 = " + location.toString());
                    Intent intent1 = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent1);
                    if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    //locationManager[0].removeUpdates(locationListener);
                    // locationManager[0] =null;
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                    Log.d("Test", "location2 = " + provider.toString());
                }

                public void onProviderEnabled(String provider) {
                    Log.d("Test", "location3 = " + provider.toString());
                }

                public void onProviderDisabled(String provider) {
                    Log.d("Test", "location4 = " + provider.toString());
                }
            };


// Define a listener that responds to location updates


// Register the listener with the Location Manager to receive location updates
            if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager1[0].requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
    }









   /* class Sleep extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

// Define a listener that responds to location updates


// Register the listener with the Location Manager to receive location updates
            if (ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            return null;
        }
    }*/


    /*  void setJson() throws JSONException {
        LanguageObj lang = LanguageObj.getLanguageObjectInstance();
        JSONObject obj = new JSONObject();
        String str;
        RestAdapter adapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)

                .setClient(new OkClient(new OkHttpClient()))
                .setEndpoint("https://ta-kvisa.com/api/common") //Setting the Root URL
                .build(); //Finally building the adapter
        RegisterAPI api = adapter.create(RegisterAPI.class);

             api.getLanguage(new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Log.d("test", s + " " + response.getStatus());
                if (response.getStatus() == 200) {

                    try {
                        JSONArray obj = new JSONArray(s);
                        for (int i = 0; i < obj.length(); i++) {
                            //  lang.setNewLang(obj);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("TEst", "Error " + error.toString());
            }
        });


*//*

      *//*  for(int i=0;i<ss.length-1;i++)
        {
            String[]ar1 = ss[i].split(":");

            if(ar1.length!=2)
            {
                Log.d("Test", ar1[0]);
            }
            else {Log.d("Test", ar1[0] + " " + ar1[1]);
                obj.putOpt(ar1[0], ar1[1]);}
        }
        Log.d("Test", obj.toString());*/
    // }
}
