package weartest.com.client;

import android.annotation.TargetApi;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.app.ToolbarManager;
import com.rey.material.util.ThemeUtil;
import com.rey.material.widget.Button;
import com.rey.material.widget.SnackBar;
import com.rey.material.widget.TabPageIndicator;

import java.lang.reflect.Field;
import java.util.ArrayList;

import weartest.com.client.language.CustomViewPager;

public class Order extends ActionBarActivity implements ToolbarManager.OnToolbarGroupChangedListener {

    private DrawerLayout dl_navigator;
    private FrameLayout fl_drawer;
    private ListView lv_drawer;
    private CustomViewPager vp;
    private TabPageIndicator tpi;

    private DrawerAdapter mDrawerAdapter;
    private PagerAdapter mPagerAdapter;

    private Toolbar mToolbar;
    private ToolbarManager mToolbarManager;
    private SnackBar mSnackBar;
    Button orderbtn;
    CountDownTimer mCountDownTimer;
    ProgressBar  mProgressBar;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    int i=0;
    private Tab[] mItems = new Tab[]{Tab.WASH, Tab.IRON, Tab.DRYWASH,Tab.OPENHOURS};
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        if(item.getItemId()==R.id.tb_refresh)
        {
            launchActivity(SimpleScannerActivity.class);
        }else { startTimer(90000, 1000);
        }
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.order);
        ProgressBar img = (ProgressBar)findViewById(R.id.chronometer);
        img.setIndeterminate(true);
        img.setIndeterminateDrawable(getResources().getDrawable(R.drawable.animation));

        dl_navigator = (DrawerLayout)findViewById(R.id.main_dl);
        fl_drawer = (FrameLayout)findViewById(R.id.main_fl_drawer);
        lv_drawer = (ListView)findViewById(R.id.main_lv_drawer);

        vp = (CustomViewPager)findViewById(R.id.main_vp);
        tpi = (TabPageIndicator)findViewById(R.id.main_tpi);
        mSnackBar = (SnackBar)findViewById(R.id.main_sn);



        mDrawerAdapter = new DrawerAdapter();
        lv_drawer.setAdapter(mDrawerAdapter);

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), mItems);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mToolbarManager = new ToolbarManager(this, mToolbar, 0, R.style.ToolbarRippleStyle, R.anim.abc_fade_in, R.anim.abc_fade_out);
        vp.setAdapter(mPagerAdapter);
        tpi.setViewPager(vp);
        tpi.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                mDrawerAdapter.setSelected(mItems[position]);
                mSnackBar.dismiss();
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });

        mDrawerAdapter.setSelected(Tab.WASH);
        vp.setCurrentItem(0);



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
    }
MenuItem item;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        item=menu.findItem(R.id.tb_contextual);
        item.setTitle("Order");


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
    @Override
    public void onToolbarGroupChanged(int oldGroupId, int groupId) {
        mToolbarManager.notifyNavigationStateChanged();
    }
    public int doSomeTasks() {

        /*while (fileSize <= 1000000) {

            fileSize++;

            if (fileSize == 100000) {
                return 10;
            } else if (fileSize == 200000) {
                return 20;
            } else if (fileSize == 300000) {
                return 30;
            }
            // ...add your own

        }*/

        return 100;

    }
    public SnackBar getSnackBar(){
        return mSnackBar;
    }

    public enum Tab {
        WASH ("Wash"),
        IRON ("Iron"),
        DRYWASH ("Dry wash"),
        OPENHOURS("Open hours");

        private final String name;

        private Tab(String s) {
            name = s;
        }

        public boolean equalsName(String otherName){
            return (otherName != null) && name.equals(otherName);
        }

        public String toString(){
            return name;
        }

    }

    class DrawerAdapter extends BaseAdapter implements View.OnClickListener {

        private Tab mSelectedTab;

        public void setSelected(Tab tab){
            if(tab != mSelectedTab){
                mSelectedTab = tab;
                notifyDataSetInvalidated();
            }
        }

        public Tab getSelectedTab(){
            return mSelectedTab;
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
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if(v == null) {
                v = LayoutInflater.from(Order.this).inflate(R.layout.row_drawer, null);
                v.setOnClickListener(this);
            }

            v.setTag(position);
            Tab tab = (Tab)getItem(position);
            ((TextView)v).setText(tab.toString());

            if(tab == mSelectedTab) {
                v.setBackgroundColor(ThemeUtil.colorPrimary(Order.this, 0));
                ((TextView)v).setTextColor(0xFFFFFFFF);
            }
            else {
                v.setBackgroundResource(0);
                ((TextView)v).setTextColor(0xFF000000);
            }

            return v;
        }

        @Override
        public void onClick(View v) {
            int position = (Integer)v.getTag();
            vp.setCurrentItem(position);
            dl_navigator.closeDrawer(fl_drawer);
        }
    }

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

        public PagerAdapter(FragmentManager fm, Tab[] tabs) {
            super(fm);
            mTabs = tabs;
            mFragments = new Fragment[mTabs.length];


            //dirty way to get reference of cached fragment
            try{
                ArrayList<Fragment> mActive = (ArrayList<Fragment>)sActiveField.get(fm);
                if(mActive != null){
                    for(Fragment fragment : mActive){
                        if(fragment instanceof Wash)
                            setFragment(Tab.WASH, fragment);
                        else if(fragment instanceof Iron)
                            setFragment(Tab.IRON, fragment);
                        else if(fragment instanceof DryWash)
                            setFragment(Tab.DRYWASH, fragment);
                        else if(fragment instanceof OpenHours)
                            setFragment(Tab.OPENHOURS, fragment);

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
                    case OPENHOURS:
                        mFragments[position] = new OpenHours();//FabFragment.newInstance();
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

}
