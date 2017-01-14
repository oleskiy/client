package weartest.com.client;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.app.TimePickerDialog;
import com.rey.material.app.ToolbarManager;
import com.rey.material.widget.Button;
import com.rey.material.widget.SnackBar;
import com.rey.material.widget.TabPageIndicator;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.LogRecord;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import weartest.com.client.dialog.CrushReportManager;
import weartest.com.client.dialog.CustomUncaughtExpetionHandler;
import weartest.com.client.language.CustomViewPager;
import weartest.com.client.language.LanguageManager;
import weartest.com.client.language.LanguageObj;
import weartest.com.client.menuitems.HoursTAKVISA;
import weartest.com.client.menuitems.Privacy;
import weartest.com.client.menuitems.Price;
import weartest.com.client.zxing.MySimpleDialog;

import static weartest.com.client.ChooseLockerFragment.*;
import static weartest.com.client.Profile.newInstance;

public class Order extends ActionBarActivity implements ToolbarManager.OnToolbarGroupChangedListener {
    static MenuItem menuItem,txtorder;

    private DrawerLayout dl_navigator;
    private FrameLayout fl_drawer;
    private ListView lv_drawer;
    public CustomViewPager vp;
    private TabPageIndicator tpi;
    public static  android.os.Handler handOs;
    private DrawerAdapter mDrawerAdapter;
     static PagerAdapter mPagerAdapter;
ImageView nextBtn;
    LinearLayout menuLeft;
    private Toolbar mToolbar;
    private ToolbarManager mToolbarManager;
    private SnackBar mSnackBar;
    Button orderbtn;
    CountDownTimer mCountDownTimer;
    ProgressBar  mProgressBar;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    int i=0;
    int currentFragment = 0;
    ProgressBar progress;
    MyInvoice invoice;
    Button getInvoice;
    com.rey.material.widget.TextView name, phone,complite;
    public static java.util.logging.Handler handlerMenu;
    boolean menuopen = false;
    private Tab[] mItems;// = new Tab[]{Tab.WASH, Tab.IRON, Tab.DRYWASH,Tab.CARPET_BLANKET};
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        Log.d("TEst", "utem "+item.toString());
       /* if(item.getItemId()==R.id.tb_refresh)
        {
            launchActivity(SimpleScannerActivity.class);
        }else {
       TextView txt=     (TextView) item.getActionView();
            startTimer(90000, 1000);
        }*/
        Log.d("TEst", "buuton frag2");

       /* if(item.getItemId()==R.id.tb_refresh&& getSupportFragmentManager().findFragmentByTag(LoginFragment1.LOG_TAG).isVisible())
        {
            Log.d("TEst","buuton frag1");
            // frag2 = (LoginFragment1)getSupportFragmentManager().findFragmentByTag(LoginFragment1.LOG_TAG);
        }
        else if(item.getItemId()==R.id.tb_refresh&&getSupportFragmentManager().findFragmentByTag("login2").isVisible())
        {
            Log.d("TEst","buuton frag2");
        }*/

        return super.onOptionsItemSelected(item);
    }
    void setNewLang(int position) throws JSONException {

       /* LanguageObj obj = LanguageObj.getLanguageObjectInstance();
        if(position==1){
            obj.setNewLang(2);}
        else
        {
            obj.initDefaultLanguage();
        }*/

        mItems1 = new String[]{LanguageManager.getLanguageStringValue(LanguageManager.PROFILE),LanguageManager.getLanguageStringValue(LanguageManager.PRIVACY),
                LanguageManager.getLanguageStringValue(LanguageManager.PRICE), LanguageManager.getLanguageStringValue(LanguageManager.TERMS_OF_SERVICE),LanguageManager.getLanguageStringValue(LanguageManager.LOG_OUT)};
        //Tab tt = new Tab();
        mItems = new Tab[]{Tab.WASH, Tab.IRON, Tab.DRYWASH,Tab.CARPET_BLANKET};
        String[]name = new String[]{LanguageManager.getLanguageStringValue(LanguageManager.WASH),LanguageManager.getLanguageStringValue(LanguageManager.IRON),LanguageManager.getLanguageStringValue(LanguageManager.DRY_WASH),LanguageManager.getLanguageStringValue(LanguageManager.CAPTER_BLANKET)};
        mDrawerAdapter = new DrawerAdapter();
       if(lv_drawer!=null)
        lv_drawer.setAdapter(mDrawerAdapter);
        if(complite!=null)complite.setText(LanguageManager.getLanguageStringValue(LanguageManager.COMPLITE_ORDER));
       if(getInvoice!=null) getInvoice.setText(LanguageManager.getLanguageStringValue(LanguageManager.GET_IMVOICE));
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), mItems);
        if (mPagerAdapter!=null)mPagerAdapter.notifyDataSetChanged();
       if(vp!=null){ vp.invalidate(); if(txtorder!=null) txtorder.setTitle(name[vp.getCurrentItem()]);}

if(tpi!=null) {
    LinearLayout ll = (LinearLayout) tpi.getChildAt(0);
    if (ll != null) {
        for(i=0;i<ll.getChildCount();i++){
        TextView txt = (TextView)ll.getChildAt(i);
        txt.setText(name[i].toUpperCase());
        }
    }

} if(ValuesAndPreferencesManager.getLangId()==1)
        {
            nextBtn.setImageResource(R.drawable.save_il);
        }
        else{nextBtn.setImageResource(R.drawable.save_en);}
       /* if(tpi!=null)


        for(int i=0;i<tpi.getChildCount();i++)
        {
            View child = tpi.getChildAt(i);
            LinearLayout ll = (LinearLayout) tpi.getChildAt(i);
            for(int j = 0;j<ll.getChildCount();j++)
            if (ll.getChildAt(j) instanceof TextView)
                ((TextView) ll.getChildAt(j)).setText(mItems[j].name);
        }*/
     //   if(vp!=null)vp.setAdapter(mPagerAdapter);
      // if(tpi!=null) tpi.setViewPager(vp);
    }
    @Override
    public void onResume() {
        super.onResume();
        ChooseLockerFragment.GetData data1 = new ChooseLockerFragment.GetData();
        JSONArray data = null;
        try {
            data = new JSONArray( data1.execute().get());
            for(int i=0;i<data.length();i++)
            {
                JSONArray items = data.getJSONObject(i).getJSONArray("Items");
                //  Log.d("items",items.toString());
                Gson gson = new Gson();
                Type listType = new TypeToken<List<ChooseLockerFragment.ItemService>>(){}.getType();
                List<ChooseLockerFragment.ItemService> posts = (List<ChooseLockerFragment.ItemService>) gson.fromJson(items.toString(), listType);



                ItemsService.put(data.getJSONObject(i).getString("Name"), (ArrayList<ChooseLockerFragment.ItemService>) posts);
                //Log.d("item",posts.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // Log.d("Test",data.toString());




        try {
            setNewLang(ValuesAndPreferencesManager.getLangId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
if(invoice.getTotalVolume()>0)getInvoice.setVisibility(View.VISIBLE);

    if (ValuesAndPreferencesManager.getLangId() == 1) {
      //  menuLeft.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

            fl_drawer.setLayoutParams(new DrawerLayout.LayoutParams(350, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.END));
       // dl_navigator.openDrawer(Gravity.END);
    } else {
        //menuLeft.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

            fl_drawer.setLayoutParams(new DrawerLayout.LayoutParams(350, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.START));

        //dl_navigator.openDrawer(Gravity.START);
    }
}
    @Override
    public void onBackPressed() {
    //    super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.order);
        ValuesAndPreferencesManager.initPrefManager(this);
       /* try {Lo
            setNewLang(ValuesAndPreferencesManager.getLangId());
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        mItems = new Tab[]{Tab.WASH, Tab.IRON, Tab.DRYWASH,Tab.CARPET_BLANKET};
complite = (com.rey.material.widget.TextView)findViewById(R.id.complite);
        complite.setText(LanguageManager.getLanguageStringValue(LanguageManager.COMPLITE_ORDER));
        dl_navigator = (DrawerLayout)findViewById(R.id.main_dl);
        fl_drawer = (FrameLayout)findViewById(R.id.main_fl_drawer);
        lv_drawer = (ListView)findViewById(R.id.main_lv_drawer);
        menuLeft = (LinearLayout)findViewById(R.id.leyout_menu_left);
        vp = (CustomViewPager)findViewById(R.id.main_vp);
        tpi = (TabPageIndicator)findViewById(R.id.main_tpi);
        mSnackBar = (SnackBar)findViewById(R.id.main_sn);
       // name = (com.rey.material.widget.TextView)findViewById(R.id.name_menu_order);
        //phone = (com.rey.material.widget.TextView)findViewById(R.id.phone_menu_order);
        //name.setText(ValuesAndPreferencesManager.gertName());
        //phone.setText(ValuesAndPreferencesManager.getTel());
        getInvoice = (Button)findViewById(R.id.get_invoice);
        getInvoice.setText(LanguageManager.getLanguageStringValue(LanguageManager.GET_IMVOICE));
Date date = new Date();
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        getInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InvoiceDialog();
                JSONObject order = new JSONObject(invoice.totalServiceName);
                StringBuilder str = new StringBuilder();
                for(int i:invoice.getTotalInvoice().keySet())
                {
                    try {
                        order.put(String.valueOf(i),invoice.getTotalInvoice().get(i));
                        str.append(invoice.totalServiceName.get(String.valueOf(i))+" - "+invoice.getTotalInvoice().get(i)+'\n');

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("Test","order = "+order.toString()+" hvhjv "+str.toString());
                /*Dialog allService = new SimpleDialog(Order.this);
                allService.setContentView(R.layout.invoice);
                com.rey.material.widget.TextView txt = (com.rey.material.widget.TextView)findViewById(R.id.all_items);
                txt.setText(order.toString());*/



                new AlertDialog.Builder(Order.this)
                        .setTitle("")

                        .setMessage(LanguageManager.getLanguageStringValue(LanguageManager.ACCORDING_PRICE))
                        .setNegativeButton(LanguageManager.getLanguageStringValue(LanguageManager.CANCEL),null)
                        .setPositiveButton(LanguageManager.getLanguageStringValue(LanguageManager.OK), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                RestAdapter adapter = new RestAdapter.Builder()
                                        .setLogLevel(RestAdapter.LogLevel.FULL)

                                        .setClient(new OkClient(new OkHttpClient()))
                                        .setEndpoint("https://ta-kvisa.com") //Setting the Root URL
                                        .build();
                                RegisterAPI api = adapter.create(RegisterAPI.class);
                                api.getInvoice("1",new Callback<SetOrder>() {
                                    /**
                                     * Successful HTTP response.
                                     *
                                     * @param setOrder
                                     * @param response
                                     */
                                    @Override
                                    public void success(SetOrder setOrder, Response response) {

                                        ValuesAndPreferencesManager.saveLockerId2(Long.decode(setOrder.getId2()));
                                        ValuesAndPreferencesManager.saveLockerNumber(Integer.parseInt(setOrder.getLockerNumber()));
                                        int endDate =1;
                                        if(!Boolean.parseBoolean(setOrder.getIsAfternoon()))

                                        {endDate = 0;}
                                        final Dialog.Builder builder1 = new TimePickerDialog.Builder(00, 00) {
                                            @Override
                                            public void onPositiveActionClicked(DialogFragment fragment) {
                                                TimePickerDialog dialog = (TimePickerDialog) fragment.getDialog();
                                                Toast.makeText(fragment.getDialog().getContext(), "Time is " + dialog.getFormattedTime(SimpleDateFormat.getTimeInstance()), Toast.LENGTH_SHORT).show();

                                                super.onPositiveActionClicked(fragment);
                                                Intent intent = new Intent(Order.this, Timer.class);
                                                startActivity(intent);
                                            }

                                            @Override
                                            public void onNegativeActionClicked(DialogFragment fragment) {
                                                Toast.makeText(fragment.getDialog().getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                                                super.onNegativeActionClicked(fragment);
                                            }
                                        }.
                                                positiveAction(LanguageObj.getLanguageValue(LanguageManager.OK)).negativeAction(LanguageObj.getLanguageValue(LanguageManager.CANCEL));

                                        Dialog.Builder builder = new DatePickerDialog.Builder(R.style.MyCalendarView) {
                                            @Override
                                            public void onPositiveActionClicked(DialogFragment fragment) {
                                                DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                                                String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                                                dialog.date(cal.get(Calendar.DAY_OF_MONTH)+1, cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
                                                DialogFragment fragment1 = DialogFragment.newInstance(builder1);
                                                fragment1.show(getSupportFragmentManager(), null);
                                                Toast.makeText(fragment.getDialog().getContext(), "Date is " + date, Toast.LENGTH_SHORT).show();

                                                super.onPositiveActionClicked(fragment);
                                            }

                                            @Override
                                            public void onNegativeActionClicked(DialogFragment fragment) {
                                                Toast.makeText(fragment.getDialog().getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                                                super.onNegativeActionClicked(fragment);
                                            }


                                        }.

                                        date(cal.get(Calendar.DAY_OF_MONTH)+1, cal.get(Calendar.MONTH), cal.get(Calendar.YEAR)).
                                                dateRange(cal.get(Calendar.DAY_OF_MONTH)+1+endDate, cal.get(Calendar.MONTH), cal.get(Calendar.YEAR), cal.get(Calendar.DAY_OF_MONTH)+4, cal.get(Calendar.MONTH), cal.get(Calendar.YEAR)/*13,8,2016,8,10,2016*/);//.onBuild(Order.this,R.style.MyCalendarView);


                                        Log.d("Test",   cal.get(Calendar.DAY_OF_MONTH)+" "+cal.get(Calendar.MONTH)+" "+cal.get(Calendar.YEAR));

                                        builder.positiveAction(LanguageObj.getLanguageValue(LanguageManager.OK))
                                                .negativeAction(LanguageObj.getLanguageValue(LanguageManager.CANCEL));

                                        DialogFragment fragment = DialogFragment.newInstance(builder);
                                        fragment.show(fragmentManager, null);


                                        builder.positiveAction(LanguageObj.getLanguageValue(LanguageManager.OK))
                                                .negativeAction(LanguageObj.getLanguageValue(LanguageManager.CANCEL));
                                        //(getFragmentManager(), null);

                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        Log.d("TEst", "Error " + error.toString());
                                    }
                                });


                            }
                        })

                        .setIcon(android.R.drawable.ic_dialog_alert)

                        .show();







        }
        });
        mDrawerAdapter = new DrawerAdapter();
        lv_drawer.setAdapter(mDrawerAdapter);
        progress = (ProgressBar)findViewById(R.id.progressBar3);
        invoice = MyInvoice.newInstance();
        invoice.setProgress(progress);
        progress.setProgress(invoice.myCount);
        invoice.setGetInvoice(getInvoice);
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), mItems);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        nextBtn = (ImageView)findViewById(R.id.next_btn_order);
        nextBtn.setVisibility(View.GONE);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Profile.handOs != null)
                    Profile.handOs.sendEmptyMessage(1);
            }
        });
        handOs = new android.os.Handler()
        {
            public void handleMessage(android.os.Message msg) {
                if(msg.what==0)
                {

                    if(ValuesAndPreferencesManager.getLangId()==1)
                    {
                        nextBtn.setImageResource(R.drawable.save_il);
                    }
                    else{nextBtn.setImageResource(R.drawable.save_en);}
                    mToolbar.setTitle("");
                   // mToolbar.setLogo(R.drawable.logosmallwhite);
                    nextBtn.setVisibility(View.VISIBLE);
                    menuItem.setVisible(false);
                    txtorder.setVisible(false);


                }
                else
                {
                    nextBtn.setVisibility(View.GONE);
                   // mToolbar.setTitle("Tel Aviv, Arlozorov");
                    menuItem.setVisible(true);
                    txtorder.setVisible(true);
                    mToolbar.setLogo(android.R.color.transparent);
                }
            }
        };
        //mToolbar.setTitle("Tel Aviv, Arlozorov");
        mToolbarManager = new ToolbarManager(this, mToolbar, 0, R.style.ToolbarRippleStyle, R.anim.abc_fade_in, R.anim.abc_fade_out);
        mToolbarManager.setNavigationManager(new ToolbarManager.BaseNavigationManager(R.style.NavigationDrawerDrawable, this, mToolbar, dl_navigator) {
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
                        if(ValuesAndPreferencesManager.getLangId()==1){
                             fl_drawer.setLayoutParams(new DrawerLayout.LayoutParams(350,ViewGroup.LayoutParams.MATCH_PARENT, Gravity.END));
                            dl_navigator.openDrawer(Gravity.END);}

                        else
                        {
                             fl_drawer.setLayoutParams(new DrawerLayout.LayoutParams(350,ViewGroup.LayoutParams.MATCH_PARENT, Gravity.START));
                            dl_navigator.openDrawer(Gravity.START);
                        }
                        menuopen = true;
                    }
                    //   mToolbar.bringToFront();
                }

            }

            @Override
            public boolean isBackState() {
               /* try {
                    setNewLang(ValuesAndPreferencesManager.getLangId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                return super.isBackState() || mToolbarManager.getCurrentGroup() != 0;
            }

            @Override
            protected boolean shouldSyncDrawerSlidingProgress() {
                return super.shouldSyncDrawerSlidingProgress() && mToolbarManager.getCurrentGroup() == 0;
            }


        });

        vp.setAdapter(mPagerAdapter);
        tpi.setViewPager(vp);
        tpi.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
              //  mDrawerAdapter.setSelected(mItems[position]);
                mSnackBar.dismiss();
                currentFragment = position;
                if (menuItem != null) {
                    switch (position) {
                        case 0: {
                            menuItem.setIcon(R.drawable.wash);
                            if (txtorder != null) txtorder.setTitle(LanguageManager.getLanguageStringValue(LanguageManager.WASH));

                            break;
                        }
                        case 1: {
                            menuItem.setIcon(R.drawable.iron);
                            if (txtorder != null) txtorder.setTitle(LanguageManager.getLanguageStringValue(LanguageManager.IRON));
                            break;
                        }
                        case 2: {
                            menuItem.setIcon(R.drawable.dry_wash);
                            if (txtorder != null) txtorder.setTitle(LanguageManager.getLanguageStringValue(LanguageManager.DRY_WASH));
                            break;
                        }

                        case 3: {
                            menuItem.setIcon(R.drawable.carpet);
                            if (txtorder != null) txtorder.setTitle(LanguageManager.getLanguageStringValue(LanguageManager.CAPTER_BLANKET));
                            break;
                        }
                    }

                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });

        //mDrawerAdapter.setSelected(Tab.WASH);
        vp.setCurrentItem(0);
        handlerMenu = new java.util.logging.Handler() {
            @Override
            public void close() {

            }

            @Override
            public void flush() {
                try {
                    setNewLang(ValuesAndPreferencesManager.getLangId());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(ValuesAndPreferencesManager.getLangId()==1)
                {
                  //  menuLeft.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                   // dl_navigator.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                    if(nextBtn!=null){
                        nextBtn.setImageResource(R.drawable.save_il);

                        nextBtn.setLayoutParams(new Toolbar.LayoutParams(150  , 100, Gravity.LEFT));}
                    fl_drawer.setLayoutParams(new DrawerLayout.LayoutParams(350,ViewGroup.LayoutParams.MATCH_PARENT, Gravity.END));
                }
                else{
                 //   menuLeft.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                   // dl_navigator.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                    if(nextBtn!=null){
                        nextBtn.setImageResource(R.drawable.save_en);

                        nextBtn.setLayoutParams(new Toolbar.LayoutParams(150  , 100, Gravity.RIGHT));
                        nextBtn.setPadding(0,0,30,0);
                    }
                    fl_drawer.setLayoutParams(new DrawerLayout.LayoutParams(350,ViewGroup.LayoutParams.MATCH_PARENT, Gravity.START));
                }
            }

            @Override
            public void publish(LogRecord record) {

            }
        };


/*        orderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //     launchActivity(SimpleScannerActivity.class);

                mProgressBar=(ProgressBar)findViewById(R.id.progressBar);
                orderbtn.setVisibility(View.GONE);
               //ProgressBar.setVisibility(View.VISIBLE);
                LinearLayout ll = (LinearLayout)findViewById(R.id.backprogress);
                Button openLocker = (Button)findViewById(R.id.open_locker);
                openLocker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        launchActivity(SimpleScannerActivity.class);
                    }
                });
                ll.setVisibility(View.VISIBLE);
                mProgressBar.setProgress(i);
                mCountDownTimer=new CountDownTimer(20000,1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        Log.v("Log_tag", "Tick of Progress" + i + millisUntilFinished);
                        i++;
                        mProgressBar.setProgress(i);

                    }

                    @Override
                    public void onFinish() {
                        //Do what you want
                        i++;
                        mProgressBar.setProgress(i);
                    }
                };
                mCountDownTimer.start();



            }

        });*/

          /*  InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindowToken(), 0);*/
        fragmentManager = this.getSupportFragmentManager();
        if(ValuesAndPreferencesManager.getLangId()==1)
        {
          //  menuLeft.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            // dl_navigator.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            if(nextBtn!=null){
                nextBtn.setImageResource(R.drawable.save_il);

                nextBtn.setLayoutParams(new Toolbar.LayoutParams(150  , 100, Gravity.LEFT));}
            fl_drawer.setLayoutParams(new DrawerLayout.LayoutParams(350,ViewGroup.LayoutParams.MATCH_PARENT, Gravity.END));
        }
        else{
           // menuLeft.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            // dl_navigator.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            if(nextBtn!=null){
                nextBtn.setImageResource(R.drawable.save_en);

                nextBtn.setLayoutParams(new Toolbar.LayoutParams(150  , 100, Gravity.RIGHT));
                nextBtn.setPadding(0,0,30,0);
            }
            fl_drawer.setLayoutParams(new DrawerLayout.LayoutParams(350,ViewGroup.LayoutParams.MATCH_PARENT, Gravity.START));
        }
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onPrepareOptionsMenu(final Menu aMenu) {
        /*final MenuItem menuItem = this.menu.findItem(R.id.tb_contextual);
        menuItem.setTitle("tes");
        final TextView textView = (TextView) menuItem.getActionView();

        textView.setText("rewfsfdg");*/
        return super.onPrepareOptionsMenu(aMenu);
    }
MenuItem item;
   Menu menu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Typeface face = Typeface.createFromAsset(this.getAssets(),"fonts/myFont.ttf");    //  THIS
        //TypefaceSpan face = new TypefaceSpan("<REPLACE_WITH_FONT_NAME>"); // OR  THIS
       // SpannableStringBuilder title = new SpannableStringBuilder("tets");
     //   title.setSpan(face, 0, title.length(), 0);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_order, menu);
        //menu.add(Menu.NONE, R.id.txtorder, 0, title); // THIS
         menuItem = menu.findItem(R.id.imgorder);
        menuItem.setIcon(R.drawable.wash);

        txtorder = menu.findItem(R.id.txtorder);
        txtorder.setTitle(LanguageManager.getLanguageStringValue(LanguageManager.WASH));
         // OR THIS
        //menuItem.setTitle(title);
        
       /* MenuItem menuVal = menu.findItem(R.id.tb_contextual);
        MenuItemCompat.setActionView(menuVal, R.id.tb_contextual);
        View menu_hotlist = (View) MenuItemCompat.getActionView(menuVal);*/
      //  TextView ui_hot = (TextView) menu_hotlist.findViewById(R.id.tb_contextual);
      /*  MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        this.menu = menu;*/
      //  TextView item=(TextView)menu.findItem(R.id.tb_contextual).getActionView();
     /*   Typeface mFont = Typeface.createFromAsset(Order.this.getAssets(), "fonts/myFont.ttf");
        ((TextView)item).setTypeface(mFont);*/
       // item.setText("Order");
         //view = item.getActionView();
       /* //Log.d("TEst","view "+view.toString());
        TextView tvMiTitle = new TextView(Order.this);
        tvMiTitle.setText("testeu");
        tvMiTitle.setTextColor(Color.WHITE);
        inflater.setActionView(tvMiTitle);*/

        return super.onCreateOptionsMenu(menu);
    }



   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.tb_contextual:
              *//*  mToolbarManager.setCurrentGroup(R.id.tb_group_contextual);
                break;
            case R.id.tb_done:
            case R.id.tb_done_all:*//*
                mToolbarManager.setCurrentGroup(0);
                break;
        }
        return true;
    }*/
void setI()
{
    i++;
}
    int getI(){return i;}

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        return false;
    }*/

    @Override
    public void onToolbarGroupChanged(int oldGroupId, int groupId) {
        mToolbarManager.notifyNavigationStateChanged();
    }

    public SnackBar getSnackBar(){
        return mSnackBar;
    }

    public enum Tab {
        WASH (LanguageManager.getLanguageStringValue(LanguageManager.WASH)),
        IRON (LanguageManager.getLanguageStringValue(LanguageManager.IRON)),
        DRYWASH (LanguageManager.getLanguageStringValue(LanguageManager.DRY_WASH)),
        CARPET_BLANKET(LanguageManager.getLanguageStringValue(LanguageManager.CAPTER_BLANKET));

        public void setName(String name1)
        {
            name = name1;
        }
        private  String name;

         Tab(String s) {
            name = s;
        }

        Tab() {

        }

        public void setChangeTab()
{
    Tab.WASH.name=(LanguageManager.getLanguageStringValue(LanguageManager.WASH));
    Tab.IRON.name=(LanguageManager.getLanguageStringValue(LanguageManager.IRON));
    Tab.DRYWASH.name=(LanguageManager.getLanguageStringValue(LanguageManager.DRY_WASH));
    Tab.CARPET_BLANKET.name=(LanguageManager.getLanguageStringValue(LanguageManager.CAPTER_BLANKET));
}
        public boolean equalsName(String otherName){
            return (otherName != null) && name.equals(otherName);
        }

        public String toString(){
            return name;
        }

    }
    private String[] mItems1 = new String[]{LanguageManager.getLanguageStringValue(LanguageManager.PROFILE),LanguageManager.getLanguageStringValue(LanguageManager.PRIVACY),
            LanguageManager.getLanguageStringValue(LanguageManager.PRICE), LanguageManager.getLanguageStringValue(LanguageManager.TERMS_OF_SERVICE),LanguageManager.getLanguageStringValue(LanguageManager.LOG_OUT)};;
    class DrawerAdapter extends BaseAdapter  {
        class ViewHolder
        {
            TextView txt;
        }
        // private Tab mSelectedTab;

       /* public void setSelected(Tab tab){
            if(tab != mSelectedTab){
                mSelectedTab = tab;
                notifyDataSetInvalidated();
            }
        }

        public Tab getSelectedTab(){
            return mSelectedTab;
        }*/

        @Override
        public int getCount() {
            return mItems1.length;
        }

        @Override
        public Object getItem(int position) {
            return mItems1[position];
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
                v = LayoutInflater.from(Order.this).inflate(R.layout.item_menu, null);
                if(vv!=null){
                    // vv.img = (ImageView)v.findViewById(R.id.img_item_menu);
                    vv.txt = (TextView)v.findViewById(R.id.txt_item_menu);}
                //v.setOnClickListener(this);

            }

            v.setTag(position);
            //Tab tab = (Tab)getItem(position);
            //((TextView)v).setText("65");

         /*   if(tab == mSelectedTab) {
                v.setBackgroundColor(ThemeUtil.colorPrimary(MainActivity.this, 0));
                ((TextView)v).setTextColor(0xFFFFFFFF);
                ((TextView)v).setText("54321");
            }
            else {*/
            v.setBackgroundResource(0);
            if(vv!=null&&vv.txt!=null) {
                vv.txt.setTextColor(0xFF000000);
                vv.txt.setText(mItems1[position]);
                vv.txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickItem(position);

                    }
                });
                //    vv.img.setImageResource(images[position]);
            }
            //  }

            return v;
        }

       /* @Override
        public void onClick(View v) {
            int position = (Integer)v.getTag();
            vp.setCurrentItem(position);
            dl_navigator.closeDrawer(fl_drawer);
        }*/
    }
    void clickItem(int position)
    {

        Fragment fragment=null;
        String tag = null;
        Intent intent = new Intent(this, WebViewActivity.class);
        switch( position)
        {
            case 0: fragment = new Profile().newInstance();
                tag = Profile.LOG_TAG;
                goToFragmentMenu(fragment,tag);
                intent = null;
                //        mMenu.getItem(0).setVisible(true);
                break;
            case 1:
                intent.putExtra("url","https://ta-kvisa.com/"+LanguageObj.langURL+"/Privacy");
            /*fragment = new Privacy().newInstance();
                tag = Privacy.LOG_TAG;*/
//                mMenu.getItem(0).setVisible(true);
                break;
            case 2:
                intent.putExtra("url","https://www.ta-kvisa.com/"+LanguageObj.langURL+"/Prices");
            /*fragment = new Price().newInstance();
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
       // goToFragmentMenu(fragment,tag);
        dl_navigator.closeDrawer(fl_drawer);
        menuopen=false;
    }
    FragmentManager fragmentManager;
    public void goToFragmentMenu(Fragment fragment, String tag)
    {
       // MyFragmentTransaction.StartFragmentTransaction(fragment,this,tag,R.id.switch_wash);

        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.switch_wash, fragment);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    /*@Override
    public void onBackPressed() {
        if(getSupportFragmentManager().findFragmentByTag(Price.LOG_TAG)!=null&&getSupportFragmentManager().findFragmentByTag(Price.LOG_TAG).isVisible()
                ||getSupportFragmentManager().findFragmentByTag(Privacy.LOG_TAG)!=null&&getSupportFragmentManager().findFragmentByTag(Privacy.LOG_TAG).isVisible()
                ||getSupportFragmentManager().findFragmentByTag(HoursTAKVISA.LOG_TAG) != null &&getSupportFragmentManager().findFragmentByTag(HoursTAKVISA.LOG_TAG).isVisible()
                ||getSupportFragmentManager().findFragmentByTag(Profile.LOG_TAG)!=null&&getSupportFragmentManager().findFragmentByTag(Profile.LOG_TAG).isVisible()
                )
        {
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(getSupportFragmentManager().findFragmentByTag(Price.LOG_TAG));
            fragmentTransaction.commit();
            //vp.setCurrentItem(0);
          //  mPagerAdapter.setFragment(mPagerAdapter.mTabs[currentFragment],mPagerAdapter.mFragments[currentFragment]);
               //         goToFragmentMenu(mPagerAdapter.getItem(currentFragment),mPagerAdapter.getItem(currentFragment).getTag());
        }
        Log.d("Test", "Onback press");
    }*/
    static ArrayList<Fragment> mActive = new ArrayList<Fragment>();
    private static class PagerAdapter extends FragmentStatePagerAdapter {

        Fragment[] mFragments;
        Tab[] mTabs;

        private static final Field sActiveField;

        static {
            Field f = null;
            try {
                Class<?> c = Class.forName("android.support.v4.app.FragmentManagerImpl");
                f = c.getDeclaredField("mActive");
                f.setAccessible(true);
            } catch (Exception e) {}

            sActiveField = f;
        }

        public PagerAdapter(FragmentManager fm, Tab[] tabs ) {
            super(fm);
            mTabs = tabs;
            mFragments = new Fragment[mTabs.length];


            //dirty way to get reference of cached fragment
            try{
                 mActive = (ArrayList<Fragment>)sActiveField.get(fm);
                if(mActive != null){
                    for(Fragment fragment : mActive){
                        if(fragment instanceof Wash){
                            setFragment(Tab.WASH, fragment);

                        }
                        else if(fragment instanceof Iron){
                            setFragment(Tab.IRON, fragment);

                        }
                        else if(fragment instanceof DryWash){
                            setFragment(Tab.DRYWASH, fragment);

                        }
                        else if(fragment instanceof CarpetBlanket)
                            setFragment(Tab.CARPET_BLANKET, fragment);

                    }
                }
            }
            catch(Exception e){}
        }

        private void setFragment(Tab tab, Fragment f){
            for(int i = 0; i < mTabs.length; i++)
                if(mTabs[i] == tab){
                    mFragments[i] = f;
                    break;
                }
        }

        @Override
        public Fragment getItem(int position) {
            if(mFragments[position] == null){
                switch (mTabs[position]) {
                    case WASH:
                        mFragments[position] = new Wash();//ProgressFragment.newInstance();
                        break;
                    case IRON:
                        mFragments[position] = new IronFragment();//ButtonFragment.newInstance();
                        break;
                    case DRYWASH:
                        mFragments[position] = new DryWashFragment();//FabFragment.newInstance();
                        break;
                    case CARPET_BLANKET:
                        mFragments[position] = new CarpetBlanket();//FabFragment.newInstance();
                        break;

                }
            }

            return mFragments[position];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabs[position].toString().toUpperCase();
        }

        @Override
        public int getCount() {
            return mFragments.length;
        }
    }
    private static final int ZXING_CAMERA_PERMISSION = 1;
    private Class<?> mClss;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void launchActivity(Class<?> clss) {


            Intent intent = new Intent(this, clss);
            startActivityForResult(intent, 0, null);
        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // ??????? ? ??? ???????? requestCode ? resultCode
        if(data!=null&&data.getStringExtra("Format")!=null)
        Toast.makeText(this, "Contents = " + data.getStringExtra("Contents") +
                ", Format = " + data.getStringExtra("Format"), Toast.LENGTH_SHORT).show();
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



    public interface OnTimeChangedListener{

        public void onTimeChanged(int oldHour, int oldMinute, int newHour, int newMinute);

    }

    void InvoiceDialog()
    {
        android.app.Dialog dialog = new android.app.Dialog(this);
        String text = "Get invoice '/n '" +
                "point: Arlozorov '/n'" +
                "locker: 10 '/n'" +
                "date: 12.3.16 '/n'" +
                "type: wash, iron";

        dialog.setTitle(text);
        dialog.show();

    }

}
