package weartest.com.client;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.JsonObject;
import com.rey.material.app.ToolbarManager;
import com.rey.material.widget.Button;
import com.rey.material.widget.FloatingActionButton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import weartest.com.client.delivery.ProgressWheel;
import weartest.com.client.dialog.Dialog;
import weartest.com.client.language.LanguageManager;
import weartest.com.client.language.LanguageObj;
import weartest.com.client.timer.AlarmReceiver;
import weartest.com.client.timer.CustomProgressBar;
import weartest.com.client.timer.PositionTimerView;
import weartest.com.client.timer.SixtySecondSmallView;
import weartest.com.client.timer.SixtySecondViewManager;

import static java.lang.System.lineSeparator;

/**
 * Created by alekseyg on 10/04/2016.
 */
public class Timer extends Activity implements ToolbarManager.OnToolbarGroupChangedListener, PositionTimerView.RemoveTimerCallback, SixtySecondSmallView.TimerFinishCallback, PositionTimerView.TimerClickListener {
    TimePicker time;
    private ProgressWheel pwOne;
    Button next, cancel, update;
    TextView adress;
    public static Handler showEndTimeDialog;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        return false;
    }*/
void startTimer(){
    if(sixtySecondsSmallViewsHolder==null)
    sixtySecondsSmallViewsHolder = (LinearLayout) findViewById(R.id.sixtySecondsTimersHolderView);
    //largeTimerHolder = (LinearLayout) findViewById(R.id.largeTimerHolder);
    //sixtySecondsSmallViewsHolder.removeAllViews();
    CustomProgressBar sixtySecondLargeViewLoadingProgressBar = null;


    sixtySecondLargeViewLoadingProgressBar = new CustomProgressBar(
            this);
    Point point = new Point();
    getWindowManager().getDefaultDisplay().getSize(point);
    width = point.x;

    manager = new SixtySecondViewManager(Timer.this,
            sixtySecondsSmallViewsHolder, sixtySecondsSmallViewsHolder,
            250,sixtySecondLargeViewLoadingProgressBar);
    SixtySecondSmallView.managerInstance = manager;


    manager.initSmallViewBitmaps();
    manager.createSixtySecondView("1", this, this);
    manager.initBigViewBitmaps(500);

    manager.createBigSixtySecondView("1");

    manager.setOnLargeSixtyViewStatusUpdateCallback(new SixtySecondViewManager.LargeSixtyViewStatusUpdateCallback() {
        @Override
        public void onStatusChanged(final String positionId) {

            if (!isFinishing()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Timer", "run");
                        // updateExpandedTimerDataFields(positionId);
                    }
                });
            } else {
                Log.d("Timer", "isFinish");
            }
        }
    });
    manager
            .setOnInnerCloseRequestListener(new SixtySecondViewManager.CloseViewCallback() {
                @Override
                public void onCloseRequest() {
                    Log.d("Timer", "closeRequest");
                    // closeExpandedTimerView();
                }
            });

    manager.setOnScrollRequestCallback(new SixtySecondViewManager.ScrollCallback() {
        @Override
        public void onScrollRequest(final int scrollValue) {
            Log.d("Timer", "onScrollRequest");
               /* horizontalSixtyTimersScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        horizontalSixtyTimersScrollView
                                .scrollTo(scrollValue, 0);
                    }
                });*/
        }
    });
    Calendar calendar = Calendar.getInstance();
    Intent myIntent = new Intent(Timer.this, AlarmReceiver.class);
     pendingIntent = PendingIntent.getBroadcast(Timer.this, 0, myIntent, 0);
    alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis()+10000, pendingIntent);

}
    @Override
    public void onToolbarGroupChanged(int oldGroupId, int groupId) {

    }

    // int nextMinute = 0;
    // private ProgressView pv_circular;
    LinearLayout sixtySecondsSmallViewsHolder;
    int width;
    SixtySecondViewManager manager;
    AlarmManager  alarmManager;
    PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);
        /*time = (TimePicker)findViewById(R.id.timer);
        time.setIs24HourView(true);
        time.setCurrentHour(0);*/
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        startTimer();


        pwOne = (ProgressWheel) findViewById(R.id.progressBarTwo);
        cancel = (Button) findViewById(R.id.cancel_timer);
        next = (Button) findViewById(R.id.next_timer);
        adress = (TextView) findViewById(R.id.adress_timer);

        adress.setText("Tel-Aviv, Arlozorov st. 59 " + lineSeparator() + lineSeparator() +
                "Locker number - " + ValuesAndPreferencesManager.getLockerNumber());

        update = (Button) findViewById(R.id.update_button);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Timer.this)
                        .setTitle("Update order ")
                        .setMessage("do You want update order")

                        .setPositiveButton(LanguageObj.getLanguageValue(LanguageManager.OK), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                manager.stopUpdatingViews();

                                startActivity(new Intent(Timer.this, Order.class));
                                //    timer.cancel();


                            }
                        })
                        .setNegativeButton(LanguageObj.getLanguageValue(LanguageManager.CANCEL), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)

                        .show();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Timer.this)
                        .setTitle("Cancel order ")
                        .setMessage("do You want cancel order")

                        .setPositiveButton(LanguageObj.getLanguageValue(LanguageManager.OK), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                MyInvoice.clearInvoice();
                                startActivity(new Intent(Timer.this, MainActivity.class));
                                manager.stopUpdatingViews();


                            }
                        })
                        .setNegativeButton(LanguageObj.getLanguageValue(LanguageManager.CANCEL), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)

                        .show();
                //   timer.cancel();
            }
        });
        // pwOne.setBarColor(R.drawable.background3color);
        /*analogClock = (TimePicker)findViewById(R.id.timePicker);
        analogClock.getChildAt(0)
       // analogClock.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
     //   analogClock.setCurrentHour(0);
        //analogClock.setIs24HourView(true);
        //analogClock.callOnClick();*/
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Timer.this, SimpleScannerActivity.class);
                startActivityForResult(intent, 0, null);
            }
        });
        final int time = 100000;
        //     ststartTimer(time);


         final AlertDialog.Builder builder = new AlertDialog.Builder(Timer.this)
                .setTitle("Expired time ")
                .setMessage("You want continue more 15 min?")

                .setPositiveButton(LanguageObj.getLanguageValue(LanguageManager.OK), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startTimer();

                        //   ststartTimer(100000);
                        //  shouTime();
                    }
                })
                .setNegativeButton(LanguageObj.getLanguageValue(LanguageManager.CANCEL), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);

        showEndTimeDialog = new Handler() {
            @Override
            public void close() {

            }

            @Override
            public void flush() {
                builder.show();
                alarmManager.cancel(pendingIntent);

            }

            @Override
            public void publish(LogRecord record) {

            }
        };

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /*    void shouTime()
        {
            TimePickerDialog.OnTimeSetListener ss = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    pwOne.setText( hourOfDay + ":" + minute);
                    pwOne.clearAnimation();
                    pwOne.clearFocus();
                    pwOne.setBarColor(R.color.TEXT_TITLES_COLOR);
                    ststartTimer(minute * 1000);
                }
            };



            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;

            mTimePicker = new TimePickerDialog(Timer.this, ss, hour, minute,false);
            mTimePicker.setTitle("Select Time");
            mTimePicker.setCanceledOnTouchOutside(true);


            mTimePicker.show();
        }*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // ??????? ? ??? ???????? requestCode ? resultCode
        if (data != null && data.getStringExtra("Format") != null) {
            Log.d("test", "Contents = " + data.getStringExtra("Contents") +
                    ", Format = " + data.getStringExtra("Format"));
            Toast.makeText(this, "Contents = " + data.getStringExtra("Contents") +
                    ", Format = " + data.getStringExtra("Format"), Toast.LENGTH_SHORT).show();
            String obj = data.getStringExtra("Contents");

            try {
                JSONObject bb = new JSONObject(obj);
                Long fromQrCode = bb.getLong("id2");
                if (ValuesAndPreferencesManager.getLockerId2() == fromQrCode) {
                    Log.d("Test", "locker open");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    CountDownTimer timer;

    void ststartTimer(final int time) {

        pwOne = (ProgressWheel) findViewById(R.id.progressBarTwo);
        timer = new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {

                // time.getFormat24Hour();
                // time.setCurrentMinute((int)millisUntilFinished / 1000);//setText(String.valueOf(millisUntilFinished / 1000));
                //  pwOne.setRimColor(R.color.TEXT_TITLES_COLOR);

                pwOne.incrementProgress(-360 / (time / 1000));
                pwOne.setText("00:" + (int) millisUntilFinished / 1000);
                //setCurrentHour((int)millisUntilFinished/100);
                //  analogClock.setCurrentHour(0);
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {

                pwOne.setText("Done");
                Calendar cal = Calendar.getInstance();

                              /* Intent intent = new Intent(Timer.this, SimpleScannerActivity.class);
                startActivityForResult(intent, 0, null);*/
                // mTextField.setText("done!");
            }

        };
        timer.start();
    }

    @Override
    public void onBackPressed() {


        // super.onBackPressed();
    }

    @Override
    public void onRemove(String positionId) {
        Log.d("Timer", "remove");
    }

    @Override
    public void onTimerClick(String positionId) {
        Log.d("Timer", "onTimerClick");
    }

    @Override
    public void onTimerFinish(String optionId) {
        Log.d("Timer", "onTimerFinish");
        /*new AlertDialog.Builder(Timer.this)
                .setTitle("Expired time ")
                .setMessage("You want continue more 15 min?")

                .setPositiveButton(LanguageObj.getLanguageValue(LanguageManager.OK), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                     //   ststartTimer(100000);
                        //  shouTime();
                    }
                })
                .setNegativeButton(LanguageObj.getLanguageValue(LanguageManager.CANCEL), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();*/
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Timer Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
