package weartest.com.client;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;

import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.notifications.NotificationsManager;
import com.rey.material.app.ToolbarManager;
import com.rey.material.widget.Button;
import com.rey.material.widget.SnackBar;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import weartest.com.client.dialog.Dialog;
import weartest.com.client.language.CustomViewPager;
import weartest.com.client.language.LanguageManager;
import weartest.com.client.language.LanguageObj;
import weartest.com.client.menuitems.HoursTAKVISA;
import weartest.com.client.menuitems.Privacy;
import weartest.com.client.menuitems.Price;
import weartest.com.client.menuitems.Terms;
import weartest.com.client.push.MyHandler;
import weartest.com.client.push.RegisterClient;
import weartest.com.client.push.ToDoItem;
import weartest.com.client.zxing.MyLocationListener;

public class MainActivity extends ActionBarActivity implements ToolbarManager.OnToolbarGroupChangedListener {

    EditText phone_number;
    TextView code, timerText;
    android.support.v4.app.FragmentTransaction ft;
    private Toolbar mToolbar;
    private ToolbarManager mToolbarManager;
    private DrawerLayout dl_navigator;
    MenuItem item = null;
    Menu mMenu;
    Button btn;
    public static MobileServiceClient mClient;
    public static final String SENDER_ID = "3866251382";
    private MobileServiceTable<ToDoItem> mToDoTable;
    private RegisterClient registerClient;
    private static final String BACKEND_ENDPOINT = "<Enter Your Backend Endpoint>";
    LinearLayout menuLeft;
    ToolbarManager.BaseNavigationManager tt;
    GoogleCloudMessaging gcm;
    ImageView next;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        mMenu = menu;
       /* menuItem = menu.findItem(R.id.imgorder);
        menuItem.setIcon(R.drawable.wash);*/
        return super.onCreateOptionsMenu(menu);
    }

    static Handler handler;
    Fragment frag3, frag2;
    ListView lv_drawer;

    void changeFragment() {
        ft.replace(R.id.login_fragment11, frag3);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentByTag(Price.LOG_TAG) != null && getSupportFragmentManager().findFragmentByTag(Price.LOG_TAG).isVisible()
                || getSupportFragmentManager().findFragmentByTag(Privacy.LOG_TAG) != null && getSupportFragmentManager().findFragmentByTag(Privacy.LOG_TAG).isVisible()
                || getSupportFragmentManager().findFragmentByTag(HoursTAKVISA.LOG_TAG) != null && getSupportFragmentManager().findFragmentByTag(HoursTAKVISA.LOG_TAG).isVisible()
                ) {
            if (!flagFirstFragment) {
                goToFragmentMenu(ChooseLockerFragment.newInstance(), ChooseLockerFragment.newInstance().getTag());

            } else {
                goToFragmentMenu(frag2, frag2.getTag());
            }
        }
        Log.d("Test", "Onback press");
    }

    public void goToProfile() {
        Fragment fragment;
        String tag = null;
        if (flagFirstFragment) {
            fragment = new Profile().newInstance();
            tag = LoginFragment1.LOG_TAG;
        } else {
            fragment = new ChooseLockerFragment().newInstance();
            tag = ChooseLockerFragment.LOG_TAG;
            if (mMenu != null)
                mMenu.getItem(0).setVisible(false);
        }

        MyFragmentTransaction.StartFragmentTransaction(fragment, this, tag, R.id.login_fragment11);
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.login_fragment11, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    boolean menuopen = false;
    boolean flagItem = true;
    boolean flagFirstFragment = false;
    // PagerAdapter mPagerAdapter;
    private SnackBar mSnackBar;
    public static android.os.Handler handOs;
    static Message msg;

    @Override
    public void onResume() {
        super.onResume();
        try {
            setNewLang(ValuesAndPreferencesManager.getLangId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        fl_drawer.setLayoutParams(new DrawerLayout.LayoutParams(350, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.START));
        if(Profile.newInstance().isVisible()) {
            if (ValuesAndPreferencesManager.getLangId() == 1) {
                //menuLeft.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

                if (next != null) {
                    next.setImageResource(R.drawable.nextheb1);

                    //  dl_navigator.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                   // next.setLayoutParams(new Toolbar.LayoutParams(150, 100, Gravity.LEFT));
                }
            } else {
              //  menuLeft.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                if (next != null) {
                    next.setImageResource(R.drawable.save_en);

                    // dl_navigator.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                  //  next.setLayoutParams(new Toolbar.LayoutParams(150, 100, Gravity.RIGHT));
                    next.setPadding(0, 0, 30, 0);
                }
            }
        }
    }

    void setNewLang(int position) throws JSONException {

        LanguageObj obj = LanguageObj.getLanguageObjectInstance();
        if (position == 1) {
            obj.setNewLang(2);
        } else {
            obj.initDefaultLanguage();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        msg = new Message();
        MyLocationListener ll = new MyLocationListener(this);
        LocationManager locationManager = ll.getLocationManager();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, MyLocationListener.MIN_TIME_BW_UPDATES,
                MyLocationListener.MIN_DISTANCE_CHANGE_FOR_UPDATES, ll);

        if (locationManager != null) {
            Location location = locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                       // latitude = location.getLatitude();
                       // longitude = location.getLongitude();
                        Log.d("TEst", "Co-ordinates are: " + location.getLatitude() + " " + location.getLongitude());
                        ValuesAndPreferencesManager.saveLocation((float)location.getLatitude(),(float)location.getLongitude());
                        locationManager.removeUpdates(ll);;
                    }
                }
        //getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        handler = new Handler() {
            @Override
            public void close() {

            }

            @Override
            public void flush() {
              //  mDrawerAdapter= new DrawerAdapter();
              //  lv_drawer.setAdapter(mDrawerAdapter);
                next.setVisibility(View.VISIBLE);
                if(Profile.newInstance().isVisible()) {
                    if (ValuesAndPreferencesManager.getLangId() == 1) {
                        next.setImageResource(R.drawable.save_il);
                       // next.setLayoutParams(new Toolbar.LayoutParams(150, 100, Gravity.LEFT));
                    } else {

                        next.setImageResource(R.drawable.save_en);
                        //next.setLayoutParams(new Toolbar.LayoutParams(150, 100, Gravity.RIGHT));
                    }

                /*f (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                    getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                mToolbar.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);*/

              /*  Locale locale = new Locale("iw_IL");
                Configuration configuration = getResources().getConfiguration();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    configuration.setLayoutDirection(locale);
                }
                configuration.locale = locale;
                getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());*/
                }
                else
                {
                    next.setImageResource(R.drawable.logo_ta);
                }
            }

            @Override
            public void publish(LogRecord record) {

            }


        };
        menuHandler = new Handler() {
            @Override
            public void close() {




            }

            @Override
            public void flush() {

                mItems = new String[]{LanguageManager.getLanguageStringValue(LanguageManager.PROFILE),LanguageManager.getLanguageStringValue(LanguageManager.PRIVACY),
                        LanguageManager.getLanguageStringValue(LanguageManager.PRICE), LanguageManager.getLanguageStringValue(LanguageManager.TERMS_OF_SERVICE),LanguageManager.getLanguageStringValue(LanguageManager.LOG_OUT)};
            if(mDrawerAdapter!=null&&lv_drawer!=null)
                setAdapterProfile();
if(Profile.newInstance().isVisible()) {
    if (ValuesAndPreferencesManager.getLangId() == 1) {

        //if (menuLeft != null) menuLeft.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        if (next != null) {
            next.setImageResource(R.drawable.save_il);

            //  dl_navigator.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
      //      next.setLayoutParams(new Toolbar.LayoutParams(150, 100, Gravity.LEFT));
          //  fl_drawer.setLayoutParams(new DrawerLayout.LayoutParams(350, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.END));
        }
    } else {
       // if (menuLeft != null) menuLeft.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        if (next != null) {
            next.setImageResource(R.drawable.save_en);

            // dl_navigator.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
         //   next.setLayoutParams(new Toolbar.LayoutParams(150, 100, Gravity.RIGHT));
            next.setPadding(0, 0, 30, 0);

        }
    }


             /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                    getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);*/
}
            }

            @Override
            public void publish(LogRecord record) {

            }



        };

       // menuLeft.setLayoutDirection(LinearLayout.LAYOUT_DIRECTION_RTL);
      //  MyHandler.mainActivity = this;
       // NotificationsManager.handleNotifications(this, SENDER_ID, MyHandler.class);
      //  gcm = GoogleCloudMessaging.getInstance(this);
        ValuesAndPreferencesManager.initPrefManager(this);

      //  registerClient = new RegisterClient(this, BACKEND_ENDPOINT);

        ValuesAndPreferencesManager.initPrefManager(this);
        try {
            setNewLang(ValuesAndPreferencesManager.getLangId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mItems = new String[]{LanguageManager.getLanguageStringValue(LanguageManager.PROFILE),LanguageManager.getLanguageStringValue(LanguageManager.PRIVACY),
                LanguageManager.getLanguageStringValue(LanguageManager.PRICE), LanguageManager.getLanguageStringValue(LanguageManager.TERMS_OF_SERVICE),LanguageManager.getLanguageStringValue(LanguageManager.LOG_OUT)};
        setContentView(R.layout.activity_main);

        dl_navigator = (DrawerLayout)findViewById(R.id.main_dl);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        //dl_navigator.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        // mToolbar.setTitle("TA-KVISA");
      //  mToolbar.setLogo(R.drawable.logosmallwhite);
     //   mToolbar.setTitle("");


          frag2 = new LoginFragment1().newInstance();
        vp = (CustomViewPager)findViewById(R.id.main_vp);
        lv_drawer = (ListView)findViewById(R.id.main_lv_drawer);
        mDrawerAdapter= new DrawerAdapter();
        lv_drawer.setAdapter(mDrawerAdapter);
        menuLeft = (LinearLayout)findViewById(R.id.leyout_menu_left);
        next = (ImageView)findViewById(R.id.next_btn_main);
        next.setLayoutParams(new Toolbar.LayoutParams(150, 100, Gravity.RIGHT));
         frag3 = new Profile().newInstance();//new Settings().newInstance();
         ft = getSupportFragmentManager().beginTransaction();
        if(ValuesAndPreferencesManager.gertUserID()==null||ValuesAndPreferencesManager.gertUserID().equals("userID")){
        ft.add(R.id.login_fragment11, frag2, LoginFragment1.LOG_TAG);
            profileFlag = false;
        }
        else
        {
            ft.add(R.id.login_fragment11, new ChooseLockerFragment().newInstance(),ChooseLockerFragment.LOG_TAG);
            flagItem=false;
            profileFlag = true;
            mDrawerAdapter= new DrawerAdapter();
            next.setImageResource(R.drawable.logo_ta);
            //lv_drawer.getAdapter().getView(0,null,lv_drawer).setVisibility(View.VISIBLE);
            //lv_drawer.setLayoutParams(new AbsListView.LayoutParams(-1, -2));
        }

        ft.commit();

        frag2.getId();
        frag3.getId();
        fl_drawer = (FrameLayout)findViewById(R.id.main_fl_drawer);
        mToolbarManager = new ToolbarManager(this, mToolbar, 0, R.style.ToolbarRippleStyle, R.anim.abc_fade_in, R.anim.abc_fade_out);

        /*if(ValuesAndPreferencesManager.getLangId()==1)
         fl_drawer.setLayoutParams(new DrawerLayout.LayoutParams(350,ViewGroup.LayoutParams.MATCH_PARENT, Gravity.END));
        else  fl_drawer.setLayoutParams(new DrawerLayout.LayoutParams(350,ViewGroup.LayoutParams.MATCH_PARENT, Gravity.START));*/
         tt = new ToolbarManager.BaseNavigationManager(R.style.NavigationDrawerDrawable, this, mToolbar, dl_navigator) {
            @Override
            public void onNavigationClick() {
                if (mToolbarManager.getCurrentGroup() != 0) {
                    mToolbarManager.setCurrentGroup(0);
                    dl_navigator.closeDrawer(fl_drawer);
                    menuopen = false;
                } else {
                    if (menuopen&&fl_drawer.isShown()) {
                        dl_navigator.closeDrawer(fl_drawer);
                        menuopen = false;
                    } else {
                        fl_drawer.setLayoutParams(new DrawerLayout.LayoutParams(350,ViewGroup.LayoutParams.MATCH_PARENT, Gravity.START));
                           dl_navigator.openDrawer(Gravity.START);
                       /* if(ValuesAndPreferencesManager.getLangId()==1){
                        //     fl_drawer.setLayoutParams(new DrawerLayout.LayoutParams(350,ViewGroup.LayoutParams.MATCH_PARENT, Gravity.END));
                         //   dl_navigator.openDrawer(Gravity.END);}
                        else
                        {
                         //    fl_drawer.setLayoutParams(new DrawerLayout.LayoutParams(350,ViewGroup.LayoutParams.MATCH_PARENT, Gravity.START));
                         //   dl_navigator.openDrawer(Gravity.START);
                        }*/
                        menuopen = true;
                    }
                    //   mToolbar.bringToFront();
                }
                //mToolbar.bringToFront();

               /* if (mToolbarManager.getCurrentGroup() != 0) {
                    mToolbarManager.setCurrentGroup(0);
                    dl_navigator.closeDrawer(fl_drawer);
                } else {
                    dl_navigator.openDrawer(Gravity.START);
                    //   mToolbar.bringToFront();
                }*/

            }

            @Override
            public boolean isBackState() {
                return super.isBackState() || mToolbarManager.getCurrentGroup() != 0;
            }

            @Override
            protected boolean shouldSyncDrawerSlidingProgress() {
                return super.shouldSyncDrawerSlidingProgress() && mToolbarManager.getCurrentGroup() == 0;
            }


        };

        mToolbarManager.setNavigationManager(tt);

      //  getSupportActionBar().setHomeButtonEnabled(false);
        dailog =     LoginFragment1.createLocalLoadingDialogInstance(this,LanguageManager.getLanguageStringValue(LanguageManager.WAIT),true);
      /*  try {
            GetData ss = new GetData();
            String str =   ss.get();
            try {
                parseServiceItem(str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Profile.handOs != null)
                    Profile.handOs.sendEmptyMessage(1);
            }
        });
        //next.setImageResource(R.drawable.logo_ta);
        handOs = new android.os.Handler()
        {
            @Override
            public void dispatchMessage(Message msg) {
                super.dispatchMessage(msg);
            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            public void handleMessage(Message msg) {
                if (Profile.newInstance().isVisible()) {
                    if (msg.what == 1) {
                        next.setImageResource(R.drawable.logo_ta);
                    } else {
                        next.setVisibility(View.VISIBLE);
                        if (ValuesAndPreferencesManager.getLangId() == 1) {

                            if (next != null) {
                                next.setImageResource(R.drawable.save_il);

                                next.setLayoutParams(new Toolbar.LayoutParams(150, 100, Gravity.LEFT));//ImageView.LayoutParams(75, 40, Gravity.LEFT));

                            }
                        } else {

                            if (next != null)
                                next.setImageResource(R.drawable.save_en);
                            next.setLayoutParams(new Toolbar.LayoutParams(150, 100, Gravity.RIGHT));
                        }
                    }
                    //  mToolbar.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

               /* Locale locale = new Locale("iw_IL");
                Configuration configuration = getResources().getConfiguration();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    configuration.setLayoutDirection(locale);
                }
                configuration.locale = locale;
                getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());*/
                }
            }
        };



    }
  static  Dialog dailog;
    void parseServiceItem(String str) throws JSONException {
        JSONArray data = new JSONArray(str);
        for(int i=0;i<data.length();i++)
        {
            JSONArray items = data.getJSONObject(i).getJSONArray("Items");
            Log.d("items",items.toString());
            Gson gson = new Gson();
            Type listType = new TypeToken<List<ChooseLockerFragment.ItemService>>(){}.getType();
            List<ChooseLockerFragment.ItemService> posts = (List<ChooseLockerFragment.ItemService>) gson.fromJson(items.toString(), listType);



            ChooseLockerFragment.ItemsService.put(data.getJSONObject(i).getString("Name"), (ArrayList<ChooseLockerFragment.ItemService>) posts);
            //Log.d("item",posts.toString());
        }
        Log.d("Test",data.toString());



    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onPrepareOptionsMenu( Menu aMenu) {
         //MenuItem menuItem = aMenu.findItem(R.id.tb_contextual);
        //mMenu.getItem(0).setTitle("jlhl");
        //aMenu.size() = (int) this.getResources().getDimension(R.dimen.navigation_icon_size);
       // menuItem.setIcon(R.drawable.logo_ta);
         //TextView textView = (TextView) menuItem.getActionView();

        //textView.setText("rewfsfdg");
        return super.onPrepareOptionsMenu(aMenu);
    }
     MenuItem menuItem;

    public class GetData extends AsyncTask<String, String, String> {

        String url ="https://ta-kvisa.com/api/common/GetServiceList";

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... params) {
            String json = null;
            try {
                String line, newjson = "";
                URL urls = new URL(url);
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(urls.openStream(), "UTF-8")))
                {
                    while ((line = reader.readLine()) != null) {
                        newjson += line;
                    }

                    json = newjson.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return json;
        }

        @Override
        protected void onPreExecute()
        {
            //Log.d("Test","onPreExecute");
           // dailog.show();
        }

        @Override
        protected void onPostExecute(String result)
        {
            Log.d("Test","onPostExecute");
            dailog.dismiss();
        }


    }
    void setAdapterProfile()
    {
        mDrawerAdapter= new DrawerAdapter();
        lv_drawer.setAdapter(mDrawerAdapter);
    }
    void clickItem(int position)
    {
        if(getSupportFragmentManager().findFragmentByTag(LoginFragment1.LOG_TAG)!=null&&getSupportFragmentManager().findFragmentByTag(LoginFragment1.LOG_TAG).isVisible())
        {
            flagFirstFragment  = true;
        }
        else {flagFirstFragment=false;}
        Fragment fragment=null;
        String tag = null;
        Intent intent = new Intent(this, WebViewActivity.class);
        switch( position)
        {
            case 0: fragment = new Profile().newInstance();
                tag = Profile.LOG_TAG;
                intent = null;
                //        mMenu.getItem(0).setVisible(true);
                goToFragmentMenu(fragment,tag);
                next.setImageResource(R.drawable.button_next);
                break;
            case 1:
                intent.putExtra("url","https://ta-kvisa.com/"+LanguageObj.langURL+"/Privacy");
            /*fragment = new Privacy().newInstance();
                tag = Privacy.LOG_TAG;*/
//                mMenu.getItem(0).setVisible(true);
                break;
            case 2:
                intent.putExtra("url","https://www.ta-kvisa.com/"+LanguageObj.langURL+"/Prices");
            /*fragment = new Price().newInstance()
                tag = Price.LOG_TAG;*/
                //       mMenu.getItem(0).setVisible(true);
                break;
            case 3:
                intent.putExtra("url","https://ta-kvisa.com/"+LanguageObj.langURL+"/TermsOfServices");
                //fragment = new HoursTAKVISA().newInstance();
                //tag = HoursTAKVISA.LOG_TAG;
                //       mMenu.getItem(0).setVisible(true);
                break;
            case 4:
                ValuesAndPreferencesManager.saveId(null);
                intent = new Intent(this, MainActivity.class);


                break;
        }
        if(intent!=null)
        startActivity(intent);
        dl_navigator.closeDrawer(fl_drawer);
        menuopen=false;
    }
    FragmentManager fragmentManager;
    public void goToFragmentMenu(Fragment fragment, String tag)
    {
        fragmentManager = this.getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.login_fragment11, fragment);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
        if(tag.equals(ChooseLockerFragment.LOG_TAG))
        {
            profileFlag = true;
            mDrawerAdapter= new DrawerAdapter();
        }
       /* MyFragmentTransaction.StartFragmentTransaction(fragment,this,tag,R.id.login_fragment11);
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(tag.equals(ChooseLockerFragment.LOG_TAG))
        {
            profileFlag = true;
            mDrawerAdapter= new DrawerAdapter();
        }
        fragmentTransaction.replace(R.id.login_fragment11, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/
    }
    public SnackBar getSnackBar(){
        return mSnackBar;
    }


    private String[] mItems = new String[]{LanguageManager.getLanguageStringValue(LanguageManager.PROFILE),LanguageManager.getLanguageStringValue(LanguageManager.PRIVACY),
                                            LanguageManager.getLanguageStringValue(LanguageManager.PRICE), LanguageManager.getLanguageStringValue(LanguageManager.TERMS_OF_SERVICE)
            ,   LanguageManager.getLanguageStringValue(LanguageManager.WORK_HOURS),  LanguageManager.getLanguageStringValue(LanguageManager.LOG_OUT)};
    public static Handler menuHandler;
    private CustomViewPager vp;
    class DrawerAdapter extends BaseAdapter  {
class ViewHolder
{
    TextView txt;
}


        @Override
        public int getCount() {
            return mItems.length;
        }

        @Override
        public Object getItem(int position) {
            return mItems[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;
            ViewHolder vv = new ViewHolder();
            if(v == null) {
                v = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_menu, null);
                if(vv!=null){
                vv.txt = (TextView)v.findViewById(R.id.txt_item_menu);}

            }

            v.setTag(position);

                v.setBackgroundResource(0);
            if(vv!=null&&vv.txt!=null) {
                vv.txt.setTextColor(0xFF000000);
                vv.txt.setText(mItems[position]);
                vv.txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickItem(position);

                    }
                });
/*
if((position==0||position==5))
    vv.txt.setVisibility(View.GONE);
*/

            }

            return v;
        }


    }
    private FrameLayout fl_drawer;
     DrawerAdapter mDrawerAdapter;

static boolean profileFlag = false;

ArrayList<ToDoItem> pushMes = new ArrayList<ToDoItem>();
    private void refreshItemsFromTable() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final MobileServiceList<ToDoItem> result = mToDoTable.where().field("complete").eq(false).execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {


                            for (ToDoItem item : result) {
                                pushMes.add(item);
                            }
                        }
                    });
                } catch (Exception exception) {
                    createAndShowDialog(exception, "Error");
                }
                return null;
            }
        }.execute();
    }
    private void createAndShowDialog(Exception exception, String title) {
        Throwable ex = exception;
        if(exception.getCause() != null) {
            ex = exception.getCause();
        }
        createAndShowDialog(ex.getMessage(), title);
    }
    private void createAndShowDialog(String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }


    @Override
    public void onToolbarGroupChanged(int oldGroupId, int groupId) {
        mToolbarManager.notifyNavigationStateChanged();
    }
    private void startTimer(long duration, long interval) {

        CountDownTimer timer = new CountDownTimer(duration, interval) {

            @Override
            public void onFinish() {
                //TODO Whatever's meant to happen when it finishes
            }

            @Override
            public void onTick(long millisecondsLeft) {
                int secondsLeft = (int) Math.round((millisecondsLeft / (double) 1000));
               // timerText.setText(secondsToString(secondsLeft));
              if(item!=null)
                item.setTitle(secondsToString(secondsLeft));
            }
        };

        timer.start();
    }
    private String secondsToString(int improperSeconds) {

        //Seconds must be fewer than are in a day

        Time secConverter = new Time();

        secConverter.hour = 0;
        secConverter.minute = 0;
        secConverter.second = 0;

        secConverter.second = improperSeconds;
        secConverter.normalize(true);

        String hours = String.valueOf(secConverter.hour);
        String minutes = String.valueOf(secConverter.minute);
        String seconds = String.valueOf(secConverter.second);

        if (seconds.length() < 2) {
        }
        if (minutes.length() < 2) {
            minutes = "0" + minutes;
        }
        if (hours.length() < 2) {
            hours = "0" + hours;
        }

        String timeString = hours + ":" + minutes + ":" + seconds;
        return timeString;
    }

    public void login()  {
        this.registerClient.setAuthorizationHeader("TEst");

        final Context context = this;
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... params) {
                try {

                    String regid = gcm.register(SENDER_ID);
                    registerClient.register(regid, new HashSet<String>());
                } catch (Exception e) {
                    Log.d("Test", "push errore"+e.getMessage().toString());
                    return e;
                }
                return null;
            }


        }.execute(null, null, null);
    }

    private String getAuthorizationHeader()  {
        String username = "0504083393";
        String password = "123456";
        String basicAuthHeader = username+":"+password;
        try {
            basicAuthHeader = Base64.encodeToString(basicAuthHeader.getBytes("UTF-8"), Base64.NO_WRAP);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return basicAuthHeader;
    }

    public void DialogNotify(final String title, final String message) {
      /*  if (isVisible == false)
            return;*/

        final AlertDialog.Builder dlg;
        dlg = new AlertDialog.Builder(this);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog dlgAlert = dlg.create();
                dlgAlert.setTitle(title);
                dlgAlert.setButton(DialogInterface.BUTTON_POSITIVE,
                        (CharSequence) "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                dlgAlert.setMessage(message);
                dlgAlert.setCancelable(false);
                dlgAlert.show();
            }
        });
    }



    }

