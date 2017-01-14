package weartest.com.client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alekseyg on 17/02/2016.
 */
public class ListLockerStationDeliver extends Fragment {
    public static Fragment newInstance(){
        Fragment fragment = new ListLockerStationDeliver();

        return fragment;
    }
    ListView listLockers;
    ArrayList<Item> products = new ArrayList<Item>();
    ListItems boxAdapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.deliver_list_lockers, container, false);
        for(int i=0;i<10;i++)
        {
            if((i/2)%10==0)
            products.add(new Item(i,true));
            else    products.add(new Item(i,false));
        }
        listLockers = (ListView)v.findViewById(R.id.list_lockers_for_deliver);
        boxAdapter = new ListItems(getActivity(), products);
        listLockers.setAdapter(boxAdapter);

        listLockers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SimpleScannerActivity.class);
                getActivity().startActivityForResult(intent, 0, null);
            }
        });
        return v;
    }


    class Item
    {
        int number;
        boolean status;


        public Item(int address, Boolean procent) {
            this.number = address;
            status = procent;

        }
    }

    class ListItems extends BaseAdapter
    {
        Context ctx;
        LayoutInflater lInflater;
        ArrayList<Item> objects;

        ListItems(Context context, ArrayList<Item> products) {
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
        public Object getItem(int position) {
            return objects.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh = new ViewHolder();
            View view = convertView;
            if (view == null) {
                view = lInflater.inflate(R.layout.deliver_item_locker, parent, false);
            }
            vh.deliver_image = (ImageView)view.findViewById(R.id.deliver_image);

            vh.status = (TextView)view.findViewById(R.id.deliver_status_locker);
            vh.number = (TextView)view.findViewById(R.id.deliver_number_locker);
            if(objects.get(position).status){
            vh.deliver_image.setBackgroundResource(R.drawable.pick);
                vh.status.setText("GET");}
            else{vh.deliver_image.setBackgroundResource(R.drawable.get);vh.status.setText("PICK");}

            vh.number.setText("num"+objects.get(position).number);
       //     vh.time.setText(objects.get(position).time);


            return view;
        }
    }
    class ViewHolder
    {
        TextView  status, number;
        ImageView deliver_image;
    }

}
