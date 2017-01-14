package weartest.com.client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.rey.material.widget.FloatingActionButton;
import java.util.ArrayList;

/**
 * Created by alekseyg on 15/02/2016.
 */

public class ChooseLockerFragment extends Fragment implements Handler.Callback{
    public static Fragment newInstance(){
        Fragment fragment = new ChooseLockerFragment();

        return fragment;
    }
    ListView listLockers;
    ArrayList<Item> products = new ArrayList<Item>();
    ListAdapter boxAdapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_lockers, container, false);
        listLockers = (ListView)v.findViewById(R.id.list_locker_items);

        for(int i=0;i<10;i++)
        {
            products.add(new Item("name"+i,"addresss"+i,"distance"+(i+1.5),0,"www.www"));
        }
        boxAdapter = new ListAdapter(getActivity(), products);
        listLockers.setAdapter(boxAdapter);

        listLockers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Order.class);
                startActivity(intent);
            }
        });
    return v;
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }



    class ListAdapter extends BaseAdapter implements View.OnClickListener
{
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Item> objects;

    ListAdapter(Context context, ArrayList<Item> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return objects.size();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.black_button_selector: break;
            case R.id.amountBtMinus1: break;

        }
    }
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    ViewHolder    vh;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
            vh = new ViewHolder();
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.list_item_locker, parent, false);
        }
                    vh.logo = (ImageView)view.findViewById(R.id.logo_item);
        vh.logo = (ImageView)view.findViewById(R.id.logo_item);
        vh.next = (ImageView)view.findViewById(R.id.nextimg);
        vh.name = (TextView)view.findViewById(R.id.name_item);
        vh.distance = (TextView)view.findViewById(R.id.distance_item);
        vh.adress = (TextView)view.findViewById(R.id.adress_item);
        vh.count = (TextView)view.findViewById(R.id.amountView1);
        //vh.plus = (ImageButton)view.findViewById(R.id.amountBtPlus1);
        vh.minus = (FloatingActionButton)view.findViewById(R.id.amountBtMinus1);
        vh.plus= (FloatingActionButton)view.findViewById(R.id.black_button_selector);
       // vh.plus.setIcon(getResources().getDrawable(R.drawable.ic_plus_white_24dp), true);
       // vh.minus.setIcon(getResources().getDrawable(R.drawable.ic_minus_white_24dp), true);

      //  vh.minus.setRadius(75);
       // vh.plus.setRadius(75);

        vh.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ( (Item)getItem(position)).setCount(((Item) getItem(position)).getCount() - 1);

            }
        });
        vh.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ( (Item)getItem(position)).setCount( ( (Item)getItem(position)).getCount()+1);
                setCount(position);
            }
        });
        vh.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Order.class);
                startActivity(intent);
            }
        });
        vh.name.setText(((Item)getItem(position)).name);
        vh.distance.setText(((Item)getItem(position)).distance);
        vh.adress.setText(((Item) getItem(position)).adress);
        final int k = ((Item)getItem(position)).count;
        vh.count.setText(String.valueOf(String.valueOf(k)));
        ((Item)getItem(position)).countTxt=(vh.count);


        return view;
    }


    public void setCount(int position)
{




}
}

    class ViewHolder
    {
    ImageView logo, next;
        TextView name;
        TextView adress;
        TextView distance;
        TextView count;
     //   ImageButton plus, minus;
        FloatingActionButton plus, minus;

    }
    class Item
    {
        String name;
        String adress;
        String distance;
        int count;
        String urlPicture;
        TextView countTxt;

        public Item(String name, String adress, String distance, int count, String urlPicture) {
            this.name = name;
            this.adress = adress;
            this.distance = distance;
            this.count = count;
            this.urlPicture = urlPicture;

        }

        public void setCountTxt(TextView countTxt) {
            this.countTxt = countTxt;
        }

        public TextView getCountTxt() {
            return countTxt;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
            countTxt.setText(String.valueOf(count));
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "name='" + name + '\'' +
                    ", adress='" + adress + '\'' +
                    ", distance='" + distance + '\'' +
                    ", count=" + count +
                    ", urlPicture='" + urlPicture + '\'' +
                    '}';
        }
    }
}
