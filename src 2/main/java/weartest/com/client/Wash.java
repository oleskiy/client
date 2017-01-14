package weartest.com.client;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rey.material.widget.Button;

/**
 * Created by alekseyg on 26/01/2016.
 */
public class Wash extends Fragment implements Handler.Callback {
    /*RadioButton rb1;
    RadioButton rb2;
    RadioButton rb3;*/
    Button back, next;
    LinearLayout wash1, comments,timeForReturn;
    TextView title,count1;
    com.rey.material.widget.FloatingActionButton min1, pl1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.wash, container, false);
       /* rb1 = (RadioButton)v.findViewById(R.id.switches_rb1);
        rb2 = (RadioButton)v.findViewById(R.id.switches_rb2);
        rb3 = (RadioButton)v.findViewById(R.id.switches_rb3);*/
        wash1 = (LinearLayout)v.findViewById(R.id.wash1);
        timeForReturn = (LinearLayout)v.findViewById(R.id.time_for_return);
        comments = (LinearLayout)v.findViewById(R.id.comments_layout);
        title = (TextView)v.findViewById(R.id.title_wash);
        min1 = (com.rey.material.widget.FloatingActionButton)v.findViewById(R.id.amountBtMinus1);
        pl1 = (com.rey.material.widget.FloatingActionButton)v.findViewById(R.id.amountBtPlus1);
        count1 = (TextView)v.findViewById(R.id.amountView1);
        View.OnClickListener listenerPl=null;
        listenerPl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCount(v);
            }
        };

        pl1.setOnClickListener(listenerPl);
        min1.setOnClickListener(listenerPl);
        /*back = (Button)v.findViewById(R.id.back_step);
        next = (Button)v.findViewById(R.id.next_step);*/

       /* next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wash1.getVisibility()==View.VISIBLE){
                wash1.setVisibility(View.GONE);
                comments.setVisibility(View.VISIBLE);
                title.setText("Comments");
                }
               else if(comments.getVisibility()==View.VISIBLE)
                {
                    comments.setVisibility(View.GONE);
                    timeForReturn.setVisibility(View.VISIBLE);
                    title.setText("Time for return");
                }
            }
        });*/
       /* CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rb1.setChecked(rb1 == buttonView);
                    rb2.setChecked(rb2 == buttonView);
                    rb3.setChecked(rb3 == buttonView);
                }

            }

        };

        rb1.setOnCheckedChangeListener(listener);
        rb2.setOnCheckedChangeListener(listener);
        rb3.setOnCheckedChangeListener(listener);*/

        return v;
    }
    public void setCount(View v)
    {

        switch (v.getId())
        {
            case R.id.amountBtPlus1: count1.setText(String.valueOf(Integer.parseInt(count1.getText().toString())+1));break;
            case R.id.amountBtMinus1: if(count1.getText().toString().equals("0"))  count1.setText("0"); else count1.setText(String.valueOf(Integer.parseInt(count1.getText().toString())-1));break;
        }


    }
    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }
}
