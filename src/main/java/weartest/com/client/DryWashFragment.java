package weartest.com.client;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rey.material.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.LogRecord;

import weartest.com.client.language.LanguageManager;

/**
 * Created by alekseyg on 31/01/2016.
 */
public class DryWashFragment extends Fragment {
    Button next, back;
   // com.rey.material.widget.FloatingActionButton pl2, min2,pl1, min1;
    LinearLayout counterLay, commentsLay;
    ///TextView title, count1, count2;
    MyInvoice invoice;
    ProgressBar progress;
    com.rey.material.widget.EditText com;
    ImageView comments_img ;
   // HashMap<String,TextView> vv = new HashMap<>();
   // String[] products = new String[]{"short","pants","Day Dress","evening dress","suit","jacket"};
    HashMap<Integer,Integer> ar = null;
    com.rey.material.widget.TextView titleComment;
    ArrayList<ChooseLockerFragment.ItemService> items;
    LinearLayout ll;
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
                invoice.getVvDryWash().put(items.get(i).id, count);
                ar.put(items.get(i).id, 0);
            }

        }
    }
   public static  java.util.logging.Handler hand;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dry_wash_fragment, container, false);
     //   min2 = (com.rey.material.widget.FloatingActionButton)v.findViewById(R.id.amountBtMinus2);
    //    pl2 = (com.rey.material.widget.FloatingActionButton)v.findViewById(R.id.amountBtPlus2);

     //   min1 = (com.rey.material.widget.FloatingActionButton)v.findViewById(R.id.amountBtMinus1);
     //   pl1 = (com.rey.material.widget.FloatingActionButton)v.findViewById(R.id.amountBtPlus1);
     //   count1 = (TextView)v.findViewById(R.id.amountView1);
     //   count2 = (TextView)v.findViewById(R.id.amountView2);
        invoice = MyInvoice.newInstance();
        items = ChooseLockerFragment.ItemsService.get("Dry Wash");
        //if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            ar = new HashMap<Integer,Integer>();
       // }
        hand =  new java.util.logging.Handler() {
            @Override
            public void close() {

            }

            @Override
            public void flush() {

                setProducts();
            }

            @Override
            public void publish(LogRecord record) {

            }
        };
         ll = (LinearLayout)v.findViewById(R.id.dry_view);
        for(int i=0;i<items.size();i++){
            View view = getActivity().getLayoutInflater().inflate(R.layout.item_order, null);
            Button pl= (Button)view.findViewById(R.id.amountBtPlus1);
            com.rey.material.widget.Button min= (com.rey.material.widget.Button)view.findViewById(R.id.amountBtMinus1);
            final TextView count = (TextView)view.findViewById(R.id.amountView1);
            TextView name = (TextView)view.findViewById(R.id.name_product);
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
            name.setText(LanguageManager.getLanguageStringValue(items.get(i).name));
            pl.setTag(pl.getId(), items.get(i).volume);
            min.setTag(min.getId(), items.get(i).volume);
            pl.setTag(pl.getId() + 1, items.get(i).id);
            min.setTag(min.getId() + 1, items.get(i).id);
            ll.addView(view);
            invoice.getVvDryWash().put(items.get(i).id, count);
            ar.put(items.get(i).id, 0);
            // ar.add(Integer.parseInt(count2.getText().toString()));


        }
        if(invoice.getDryWashCount().isEmpty())
        invoice.setDryWashCount(ar);

        counterLay = (LinearLayout)v.findViewById(R.id.count_layout_iron);
        commentsLay = (LinearLayout)v.findViewById(R.id.comments_layout);
        titleComment = (com.rey.material.widget.TextView)v.findViewById(R.id.title_comments);
        titleComment.setText(LanguageManager.getLanguageStringValue(LanguageManager.TYPE_YOU_COMMENTS_HERE));
        comments_img = (ImageView)v.findViewById(R.id.img_comments_dry_wash);
        com = (com.rey.material.widget.EditText)v.findViewById(R.id.coments_wash);
        comments_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comments_img.setVisibility(View.GONE);
                com.setVisibility(View.VISIBLE);
                titleComment.setVisibility(View.VISIBLE);
            }
        });
        for(int str:invoice.getDryWashCount().keySet())
        {

            invoice.getVvDryWash().get(str).setText(String.valueOf(invoice.getDryWashCount().get(str)));
        }
     //   count1.setText(String.valueOf(invoice.getDryWashCount().get(0)));
     //   count2.setText(String.valueOf(invoice.getDryWashCount().get(1)));
        progress = invoice.getProgress();
        progress.setProgress(invoice.getTotalVolume());
       /* next = (Button)v.findViewById(R.id.next_step_iron);
        back = (Button)v.findViewById(R.id.back_step_iron);*/
        View.OnClickListener listenerPl=null;

       /* listenerPl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCount(v);
            }
        };*/


     //   pl1.setOnClickListener(listenerPl);
     //   pl2.setOnClickListener(listenerPl);
     //   min1.setOnClickListener(listenerPl);
     //   min2.setOnClickListener(listenerPl);
       /* next.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(counterLay.getVisibility()==View.VISIBLE)
            {
                counterLay.setVisibility(View.GONE);
                commentsLay.setVisibility(View.VISIBLE);
                title.setText("Comments");
            }
        }
    });*/

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        for(int str:invoice.getDryWashCount().keySet())
        {

            invoice.getVvDryWash().get(str).setText(String.valueOf(invoice.getDryWashCount().get(str)));
        }
     //   count1.setText(String.valueOf(invoice.getDryWashCount().get(0)));
   //     count2.setText(String.valueOf(invoice.getDryWashCount().get(1)));
        if(invoice!=null&&invoice.coments.get("dry")!=null&&com!=null&&!invoice.coments.get("dry").isEmpty()){
            comments_img.setVisibility(View.GONE);
            com.setVisibility(View.VISIBLE);
            com.setText(invoice.coments.get("dry"));
            titleComment.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if(invoice.coments!=null)
            invoice.coments.put("dry",com.getText().toString());

    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void setCount(View v, TextView count1)
    {
        /* if(invoice.getMaxCount()>=invoice.getCount())
        switch (v.getId())
        {

           case R.id.amountBtPlus1: count1.setText(String.valueOf(Integer.parseInt(count1.getText().toString())+1));invoice.setCount(invoice.getCount()+1);break;
            case R.id.amountBtMinus1:
                if(count1.getText().toString().equals("0"))  {count1.setText("0");}
                else {count1.setText(String.valueOf(Integer.parseInt(count1.getText().toString())-1));invoice.setCount(invoice.getCount()-1);};
                break;*/
            /*case R.id.amountBtPlus2: count2.setText(String.valueOf(Integer.parseInt(count2.getText().toString())+1));invoice.setCount(invoice.getCount()+1);break;
            case R.id.amountBtMinus2:
                if(count2.getText().toString().equals("0"))  {count2.setText("0");}
                else {count2.setText(String.valueOf(Integer.parseInt(count2.getText().toString())-1));invoice.setCount(invoice.getCount()-1);};
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
                        invoice.setTotalVolume(invoice.getTotalVolume()+(int)v.getTag(v.getId()));
                    }
                    break;
                case R.id.amountBtMinus1:


                    if((invoice.getTotalVolume()- (int)v.getTag(v.getId()))<0)
                    {

                    }
                    else
                    {
                        if(count1.getText().toString().equals("0"))  {count1.setText("0");}
                        else {count1.setText(String.valueOf(Integer.parseInt(count1.getText().toString())-1));invoice.setCount(invoice.getCount()-1);
                        invoice.setTotalVolume(invoice.getTotalVolume() - (int) v.getTag(v.getId()));};
                    }
                    break;


            }
        Log.d("Test", "item value = " + (int)v.getTag(v.getId()));
        Log.d("Test", "Total val = " + invoice.getTotalVolume());
        progress.setProgress(invoice.getTotalVolume());
       Integer k=invoice.getDryWashCount().get((int) count1.getTag());
        k=Integer.parseInt(count1.getText().toString());
        int str =1;
               str= (int)count1.getTag();
        invoice.getDryWashCount().put(str,k);
        if(k==0)
        {
            if(invoice.getTotalInvoice().get((int) v.getTag(v.getId() + 1))!=null )  invoice.getTotalInvoice().remove((int) v.getTag(v.getId() + 1));
        }
        else
        {
            invoice.setTotalInvoice((int) v.getTag(v.getId() + 1), k);
        }
           /* case R.id.amountBtPlus1: count1.setText(String.valueOf(Integer.parseInt(count1.getText().toString())+1));break;
            case R.id.amountBtMinus1: if(count1.getText().toString().equals("0"))  count1.setText("0"); else count1.setText(String.valueOf(Integer.parseInt(count1.getText().toString())-1));break;
            case R.id.amountBtPlus2: count2.setText(String.valueOf(Integer.parseInt(count2.getText().toString())+1));break;
            case R.id.amountBtMinus2: if(count1.getText().toString().equals("0"))count2.setText("0"); else count2.setText(String.valueOf(Integer.parseInt(count2.getText().toString())-1));break;*/



    }
}