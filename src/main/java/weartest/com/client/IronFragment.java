package weartest.com.client;

/**
 * Created by alekseyg on 28/01/2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import weartest.com.client.language.LanguageManager;

public class IronFragment extends Fragment {
Button next, back;
    LinearLayout ll;
    public static Handler handler;
   // com.rey.material.widget.FloatingActionButton pl2, min2,pl1, min1;
        LinearLayout counterLay, commentsLay;
     //   TextView title, count1, count2;
    MyInvoice invoice;
    ProgressBar progress;
    com.rey.material.widget.EditText com;
    ImageView comments_img ;
    //String[] products ;//= new String[]{"short","pants","Day Dress","evening dress","suit","jacket"};
    ArrayList<ChooseLockerFragment.ItemService> items;
   static HashMap<String,TextView> vv = new HashMap<>();
    static  HashMap<Integer,Integer> ar = null;
    com.rey.material.widget.TextView titleComment;
    View v;
    @Override
public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
         v = inflater.inflate(R.layout.iron_fragment, container, false);
      //  min2 = (com.rey.material.widget.FloatingActionButton)v.findViewById(R.id.amountBtMinus2);
      //  pl2 = (com.rey.material.widget.FloatingActionButton)v.findViewById(R.id.amountBtPlus2);
    invoice = MyInvoice.newInstance();
   // if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
        ar = new HashMap<Integer,Integer>();
    items = ChooseLockerFragment.ItemsService.get("Iron");
    //products = ChooseLockerFragment.ItemsService.get("Iron").toArray()
   // }
         ll = (LinearLayout)v.findViewById(R.id.iron_view);
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
            invoice.getVviron().put(items.get(i).id, count);
            ar.put(items.get(i).id, 0);
            // ar.add(Integer.parseInt(count2.getText().toString()));


        }
handler = new Handler() {
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

    if(invoice.getIronCount().isEmpty())
    invoice.setIronCount(ar);

        counterLay = (LinearLayout)v.findViewById(R.id.count_layout_iron);
        commentsLay = (LinearLayout)v.findViewById(R.id.comments_layout);
    comments_img = (ImageView)v.findViewById(R.id.img_comments_iron);
    titleComment = (com.rey.material.widget.TextView)v.findViewById(R.id.title_comments);
    titleComment.setText(LanguageManager.getLanguageStringValue(LanguageManager.TYPE_YOU_COMMENTS_HERE));
    com = (com.rey.material.widget.EditText)v.findViewById(R.id.coments_wash);
    comments_img.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            comments_img.setVisibility(View.GONE);
            com.setVisibility(View.VISIBLE);
            titleComment.setVisibility(View.VISIBLE);

        }
    });
    for(int str:invoice.getIronCount().keySet())
    {//String str1 = "0";
     //   if(String.valueOf(invoice.getIronCount().get(str))==null) str1 = String.valueOf(invoice.getIronCount().get(str));
        invoice.getVviron().get(str).setText(String.valueOf(invoice.getIronCount().get(str)));
    }


    progress = invoice.getProgress();
    progress.setProgress(invoice.getTotalVolume());

        View.OnClickListener listenerPl=null;



        return v;
}

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
                invoice.getVviron().put(items.get(i).id, count);
                ar.put(items.get(i).id, 0);

            }
        }
    }
    @Override
      public void onResume() {
        super.onResume();
        for(Integer str:invoice.getIronCount().keySet())
        {
            invoice.getVviron().get(str).setText(String.valueOf(invoice.getIronCount().get(str)));
        }
        //   count1.setText(String.valueOf(invoice.getIronCount().get(0)));
        //   count2.setText(String.valueOf(invoice.getIronCount().get(1)));
        if(invoice!=null&&invoice.coments.get("iron")!=null&&com!=null&&!invoice.coments.get("iron").isEmpty()){
            comments_img.setVisibility(View.GONE);
            com.setVisibility(View.VISIBLE);
            com.setText(invoice.coments.get("iron"));
            titleComment.setVisibility(View.VISIBLE);
            titleComment.setVisibility(View.VISIBLE);
        }
    //    setProducts();
    }
    @Override
    public void onPause() {
        super.onPause();
        if(invoice.coments!=null)
        invoice.coments.put("iron",com.getText().toString());

    }

        public void setCount(View v, TextView count1)
        {
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

            ArrayList<Integer> ar = new ArrayList<Integer>();
            ar.add(Integer.parseInt(count1.getText().toString()));
           // invoice.setIronCount(ar);
            progress.setProgress(invoice.getTotalVolume());

            int k=invoice.getIronCount().get((int) count1.getTag());
            k=Integer.parseInt(count1.getText().toString());
            int str =1;
            str= (Integer) count1.getTag();

            if(k==0)
            {
             if(invoice.getTotalInvoice().get((int) v.getTag(v.getId() + 1))!=null )  invoice.getTotalInvoice().remove((int) v.getTag(v.getId() + 1));
            }
            else
            {
                invoice.setTotalInvoice((int) v.getTag(v.getId() + 1), k);
            }
            invoice.getIronCount().put(str, k);

            invoice.getTotalInvoice();
                /*switch (v.getId())
                {
                        case R.id.amountBtPlus1: count1.setText(String.valueOf(Integer.parseInt(count1.getText().toString())+1));break;
                        case R.id.amountBtMinus1: if(count1.getText().toString().equals("0"))  count1.setText("0"); else count1.setText(String.valueOf(Integer.parseInt(count1.getText().toString())-1));break;

                }*/


        }
}
