package weartest.com.client;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.notifications.NotificationsManager;
import com.rey.material.app.ToolbarManager;
import com.rey.material.widget.Button;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;

import weartest.com.client.push.MyHandler;
import weartest.com.client.push.RegisterClient;
import weartest.com.client.push.ToDoItem;

public class MainActivity extends ActionBarActivity implements ToolbarManager.OnToolbarGroupChangedListener {

    EditText phone_number;
    TextView code,timerText;
    android.support.v4.app.FragmentTransaction ft;
    private Toolbar mToolbar;
    private ToolbarManager mToolbarManager;
    private DrawerLayout dl_navigator;
    MenuItem item=null;
    Button btn;
    public static MobileServiceClient mClient;
    public static final String SENDER_ID = "3866251382";
    private MobileServiceTable<ToDoItem> mToDoTable;
    private RegisterClient registerClient;
    private static final String BACKEND_ENDPOINT = "<Enter Your Backend Endpoint>";
    GoogleCloudMessaging gcm;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
         item=menu.findItem(R.id.tb_contextual);
        item.setTitle("");

      /*  MenuItem searchItem = menu.findItem(R.id.statusTextview);
        View txt = (View) MenuItemCompat.getActionView(searchItem);
     //   txt.setText("Test");
     *//**//*   EditText text = (EditText)findViewById(R.id.txt_search);
        text.setText("tteyrdxcj,");*//**//*

       *//**//* if (searchView != null) {
            Toast.makeText(getBaseContext(), "True", Toast.LENGTH_SHORT).show();
        }
        else { Toast.makeText(getBaseContext(), "false", Toast.LENGTH_SHORT).show();}*//**//*
        MenuItemCompat.getActionView(searchItem).setBackgroundColor(RED_COLOR);*/
        return super.onCreateOptionsMenu(menu);
    }
/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
       // View timerItem = menu.findItem(R.id.break_timer).getActionView();
       *//* timerText = (TextView)timerItem.fi// MenuItemCompat.getActionView(timerItem);

        timerText.setPadding(10, 0, 10, 0); //Or something like that...

        startTimer(30000, 1/* 000); //One tick every second for 30 seconds*//*
      //  return true;
        MenuItem tex = menu.findItem(R.id.statusTextview);tex.setVisible(true);
       // View v = (View) menu.findItem(R.id.statusTextview).getActionView();

        *//** Get the edit text from the action view *//*
        EditText txtSearch = ( EditText ) MenuItemCompat.getActionProvider(tex);// v.findViewById(R.id.txt_search);

        *//** Setting an action listener *//*
        txtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Toast.makeText(getBaseContext(), "Search : " + v.getText(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();

        if(item.getItemId()==R.id.tb_refresh&& getSupportFragmentManager().findFragmentByTag(LoginFragment1.LOG_TAG).isVisible())
        {
            Log.d("TEst","buuton frag1");
           // frag2 = (LoginFragment1)getSupportFragmentManager().findFragmentByTag(LoginFragment1.LOG_TAG);
            ((LoginFragment1)frag2).sendRequest();
        }
       else if(item.getItemId()==R.id.tb_refresh&&getSupportFragmentManager().findFragmentByTag("login2").isVisible())
        {
            Log.d("TEst","buuton frag2");
            LoginFragment2 frag = (LoginFragment2)getSupportFragmentManager().findFragmentByTag("login2");
            frag.sendRequest();
        }
        else if(item.getItemId()==R.id.tb_refresh&&getSupportFragmentManager().findFragmentByTag("list").isVisible())
        {
            Log.d("TEst","buuton list");
            ChooseLockerFragment frag = (ChooseLockerFragment)getSupportFragmentManager().findFragmentByTag("list");
           // frag.sendRequest();
            Intent intent = new Intent(this, Order.class);
                    startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
     Fragment frag3,frag2;
    void changeFragment()
    {
        ft.replace(R.id.login_fragment11,frag3);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyHandler.mainActivity = this;
        NotificationsManager.handleNotifications(this, SENDER_ID, MyHandler.class);
        gcm = GoogleCloudMessaging.getInstance(this);

        //hub = new NotificationHub(HubName, HubListenConnectionString, this);
        //registerWithNotificationHubs();

        registerClient = new RegisterClient(this, BACKEND_ENDPOINT);

        ValuesAndPreferencesManager.initPrefManager(this);
        setContentView(R.layout.activity_main);

        dl_navigator = (DrawerLayout)findViewById(R.id.main_dl);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
          frag2 = new LoginFragment1().newInstance();


         frag3 = new Settings().newInstance();
         ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.login_fragment11, frag2,LoginFragment1.LOG_TAG);

        ft.commit();

        frag2.getId();
        frag3.getId();
        mToolbarManager = new ToolbarManager(this, mToolbar, 0, R.style.ToolbarRippleStyle, R.anim.abc_fade_in, R.anim.abc_fade_out);
        //startTimer(150000,1000);
     //  TextView text = (TextView)mToolbar.get

       /* mToolbar = (Toolbar)findViewById(R.id.main_toolbar);
        mToolbarManager = new ToolbarManager(this, mToolbar, 0, R.style.ToolbarRippleStyle, R.anim.abc_fade_in, R.anim.abc_fade_out);
        mToolbarManager.setNavigationManager(new ToolbarManager.BaseNavigationManager(R.style.NavigationDrawerDrawable, this, mToolbar, dl_navigator) {
            @Override
            public void onNavigationClick() {
                if (mToolbarManager.getCurrentGroup() != 0)
                    mToolbarManager.setCurrentGroup(0);
                else
                    dl_navigator.openDrawer(Gravity.START);
            }

            @Override
            public boolean isBackState() {
                return super.isBackState() || mToolbarManager.getCurrentGroup() != 0;
            }

            @Override
            protected boolean shouldSyncDrawerSlidingProgress() {
                return super.shouldSyncDrawerSlidingProgress() && mToolbarManager.getCurrentGroup() == 0;
            }

        });
        mToolbarManager.registerOnToolbarGroupChangedListener(this);
    }

    @Override
    public void onToolbarGroupChanged(int oldGroupId, int groupId) {

    }*/

    }
ArrayList<ToDoItem> pushMes = new ArrayList<ToDoItem>();
    private void refreshItemsFromTable() {

        // Get the items that weren't marked as completed and add them in the
        // adapter
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
        if(exception.getCause() != null){
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
            seconds = "0" + seconds;
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

