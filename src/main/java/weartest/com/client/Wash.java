package weartest.com.client;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.rey.material.widget.Button;
import com.rey.material.widget.CheckBox;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.LogRecord;

import weartest.com.client.language.LanguageManager;

/**
 * Created by alekseyg on 26/01/2016.
 */
public class Wash extends Fragment implements Handler.Callback {
    /*RadioButton rb1;
    RadioButton rb2;
    RadioButton rb3;*/
    Button  back, next;
    LinearLayout wash1, comments,timeForReturn;
    TextView count1;
            TextView title, titleComments;
    com.rey.material.widget.CheckBox moreConditioner, moreSpots;
   // com.rey.material.widget.Button pl1,min1;
    MyInvoice invoice;
    ProgressBar progress;
    com.rey.material.widget.EditText com;
    ImageView comments_img ;
    ScrollView scroll;
    ArrayList<ChooseLockerFragment.ItemService> items;
    static  HashMap<Integer,Integer> ar = null;
    public static  java.util.logging.Handler hand;
    void setProducts()
    {
        if(ll!=null)ll.removeAllViews();
        for(int i=0;i<items.size();i++){
            if(getActivity()!=null) {
                View view = getActivity().getLayoutInflater().inflate(R.layout.item_order, null);
                Button pl = (Button) view.findViewById(R.id.amountBtPlus1);
                com.rey.material.widget.Button min = (com.rey.material.widget.Button) view.findViewById(R.id.amountBtMinus1);
                final TextView count = (TextView) view.findViewById(R.id.amountView1);
                TextView name = (TextView) view.findViewById(R.id.name_product);
                count.setTag(items.get(i).id);
                invoice.totalServiceName.put(String.valueOf(items.get(i).id),items.get(i).name);
                pl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setCount(v, count);
                    }
                });
                min.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setCount(v, count);
                    }
                });
                pl.setTag(pl.getId(), items.get(i).volume);
                min.setTag(min.getId(), items.get(i).volume);
                pl.setTag(pl.getId() + 1, items.get(i).id);
                min.setTag(min.getId() + 1, items.get(i).id);
                name.setText(LanguageManager.getLanguageStringValue(items.get(i).name));
                ll.addView(view);
                invoice.getVviron().put(items.get(i).id, count);
                ar.put(items.get(i).id, 0);

            }
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);*/
    }
    LinearLayout ll;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v1 = inflater.inflate(R.layout.wash, container, false);
        hand =  new java.util.logging.Handler() {
            @Override
            public void close() {

            }

            @Override
            public void flush() {
                moreConditioner.setText(LanguageManager.getLanguageStringValue(LanguageManager.MORE_CONDITIONER));
                moreSpots.setText(LanguageManager.getLanguageStringValue(LanguageManager.MORE_SPOTS));
                titleComments.setText(LanguageManager.getLanguageStringValue(LanguageManager.TYPE_YOU_COMMENTS_HERE));
                title.setText(LanguageManager.getLanguageStringValue(LanguageManager.COUNT_BUGS));
                setProducts();
            }

            @Override
            public void publish(LogRecord record) {

            }
        };
     //   View view;
      //  view = getActivity().getLayoutInflater().inflate(R.layout.item_order, null);
 ll= (LinearLayout)v1.findViewById(R.id.add_view);
        //ll.addView(view);
       /* rb1 = (RadioButton)v.findViewById(R.id.switches_rb1);
        rb2 = (RadioButton)v.findViewById(R.id.switches_rb2);
        rb3 = (RadioButton)v.findViewById(R.id.switches_rb3);*/
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        invoice = MyInvoice.newInstance();
        wash1 = (LinearLayout)v1.findViewById(R.id.wash1);
        timeForReturn = (LinearLayout)v1.findViewById(R.id.time_for_return);
        comments = (LinearLayout)v1.findViewById(R.id.comments_layout);
        items = ChooseLockerFragment.ItemsService.get("Wash");
        scroll = (ScrollView) v1.findViewById(R.id.scroll_wash);
        ar = new HashMap<Integer,Integer>();

        for(int i=0;i<items.size();i++){
            View view = getActivity().getLayoutInflater().inflate(R.layout.item_order, null);
            Button pl= (Button)view.findViewById(R.id.amountBtPlus1);
            com.rey.material.widget.Button min= (com.rey.material.widget.Button)view.findViewById(R.id.amountBtMinus1);
            final TextView count = (TextView)view.findViewById(R.id.amountView1);
            TextView name = (TextView)view.findViewById(R.id.name_product);
            count.setTag(items.get(i).id);

            pl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setCount(v, count);
                }
            });
            min.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setCount(v, count);
                }
            });
            pl.setTag(pl.getId(), items.get(i).volume);
            min.setTag(min.getId(), items.get(i).volume);
            pl.setTag(pl.getId()+1,items.get(i).id);
            min.setTag(min.getId()+1,items.get(i).id);
            name.setText(LanguageManager.getLanguageStringValue(items.get(i).name));
            ll.addView(view);
            invoice.getVvWash().put(items.get(i).id, count);
            ar.put(items.get(i).id, 0);


        }
        if(invoice.getWashCount().isEmpty())
            invoice.setWashCount(ar);




        title = (TextView)v1.findViewById(R.id.title_wash);
        titleComments = (TextView)v1.findViewById(R.id.title_comments);
        titleComments.setText(LanguageManager.getLanguageStringValue(LanguageManager.TYPE_YOU_COMMENTS_HERE));
        title.setText(LanguageManager.getLanguageStringValue(LanguageManager.COUNT_BUGS));
        //min1 = (com.rey.material.widget.Button)view.findViewById(R.id.amountBtMinus1);
        //pl1 = (com.rey.material.widget.Button)view.findViewById(R.id.amountBtPlus1);
       // count1 = (TextView)view.findViewById(R.id.amountView1);
        //count1.setText(String.valueOf(invoice.getWashCount()));
        /*View.OnClickListener listenerPl=null;
        listenerPl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCount(v);
            }
        };*/
        comments.clearFocus();
        progress = invoice.getProgress();
        progress.setProgress(invoice.getTotalVolume());
        //pl1.setOnClickListener(listenerPl);
      //  min1.setOnClickListener(listenerPl);
      //  pl1.setFocusable(true);
        comments_img = (ImageView)v1.findViewById(R.id.img_comments_wash);
        com = (com.rey.material.widget.EditText)v1.findViewById(R.id.coments_wash);
        com.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                  scroll.scrollTo(0, com.getBottom());
            }
        });
        com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scroll.scrollTo(0, com.getBottom());
                //com.setVisibility(View.VISIBLE);

            }
        });

moreConditioner = (CheckBox)v1.findViewById(R.id.more_conditioner);
        moreSpots = (CheckBox)v1.findViewById(R.id.more_spots);
        moreConditioner.setText(LanguageManager.getLanguageStringValue(LanguageManager.MORE_CONDITIONER));
        moreSpots.setText(LanguageManager.getLanguageStringValue(LanguageManager.MORE_SPOTS));
        View view1 = getActivity().getCurrentFocus();

        return v1;
    }
    @Override
    public void onResume() {
        super.onResume();
        //count1.setText(String.valueOf(invoice.getWashCount()));
        for(Integer str:invoice.getWashCount().keySet())
        {
            invoice.getVvWash().get(str).setText(String.valueOf(invoice.getWashCount().get(str)));
        }

        if(invoice!=null&&invoice.coments.get("wash")!=null&&com!=null&&!invoice.coments.get("wash").isEmpty()){
            comments_img.setVisibility(View.GONE);
            com.setVisibility(View.VISIBLE);
        com.setText(invoice.coments.get("wash"));


        }
        moreConditioner.setText(LanguageManager.getLanguageStringValue(LanguageManager.MORE_CONDITIONER));
        moreSpots.setText(LanguageManager.getLanguageStringValue(LanguageManager.MORE_SPOTS));
        titleComments.setText(LanguageManager.getLanguageStringValue(LanguageManager.TYPE_YOU_COMMENTS_HERE));
        title.setText(LanguageManager.getLanguageStringValue(LanguageManager.COUNT_BUGS));
    }
    @Override
    public void onPause() {
        super.onPause();
        invoice.coments.put("wash",com.getText().toString());

    }

    public void setCount(View v,TextView count1)
    {
        /*if(invoice.getMaxCount()>invoice.getCount())
        switch (v.getId())
        {

            case R.id.amountBtPlus1: count1.setText(String.valueOf(Integer.parseInt(count1.getText().toString())+1));invoice.setCount(invoice.getCount()+1);break;


            case R.id.amountBtMinus1:
                if(count1.getText().toString().equals("0"))  {count1.setText("0");}
                else {count1.setText(String.valueOf(Integer.parseInt(count1.getText().toString())-1));invoice.setCount(invoice.getCount()-1);};

                break;
        }*/
        Log.d("Test", "Total val before = " + invoice.getTotalVolume());
        if(/*invoice.getMaxCount()>=invoice.getCount()*/invoice.getTotalVolume()<=100&&invoice.getTotalVolume()>=0)
            switch (v.getId())
            {
                case R.id.amountBtPlus1:

                    if((invoice.getTotalVolume()+ (int)v.getTag(v.getId()))>100)
                    {

                    }
                    else
                    {
                        count1.setText(String.valueOf(Integer.parseInt(count1.getText().toString()) + 1));
                        invoice.setCount(invoice.getCount() + 1);
                        invoice.setTotalVolume(invoice.getTotalVolume() + (int) v.getTag(v.getId()));
                    }
                    break;
                case R.id.amountBtMinus1:


                    if((invoice.getTotalVolume()- (int)v.getTag(v.getId()))<0)
                    {

                    }
                    else
                    {
                        if(count1.getText().toString().equals("0"))  {count1.setText("0");}
                        else {count1.setText(String.valueOf(Integer.parseInt(count1.getText().toString()) - 1));
                            invoice.setCount(invoice.getCount() - 1);
                            invoice.setTotalVolume(invoice.getTotalVolume() - (int) v.getTag(v.getId()));};

                    }
                    break;


            }
        Log.d("Test", "item value = " + (int)v.getTag(v.getId()));
        Log.d("Test", "Total val = " + invoice.getTotalVolume());
        //invoice.setWashCount(Integer.parseInt(count1.getText().toString()));
        progress.setProgress(invoice.getTotalVolume());

        Integer k=0;//invoice.getDryWashCount().get((String) count1.getTag());
        k=Integer.parseInt(count1.getText().toString());
        int str =1;
        str= (Integer)count1.getTag();
        invoice.getWashCount().put(str, k);
        ArrayList<Integer> ar = new ArrayList<Integer>();
        ar.add(Integer.parseInt(count1.getText().toString()));


        if(k==0)
        {
            if(invoice.getTotalInvoice().get((int) v.getTag(v.getId() + 1))!=null )  invoice.getTotalInvoice().remove((int) v.getTag(v.getId() + 1));
        }
        else
        {
            invoice.setTotalInvoice((int) v.getTag(v.getId() + 1), k);
        }



    }
    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }
}
