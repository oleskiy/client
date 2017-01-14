package weartest.com.client;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
            products.add(new Item("addresss"+i,(87+1)+"%",(15+i)+".00"));
        }
        listLockers = (ListView)v.findViewById(R.id.list_lockers_for_deliver);
        boxAdapter = new ListItems(getActivity(), products);
        listLockers.setAdapter(boxAdapter);

        listLockers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = new GetPut().newInstance();
                MyFragmentTransaction.StartFragmentTransaction(fragment, getActivity(), "GetPut", R.id.deliver_fragment);
            }
        });
        return v;
    }
    class Item
    {
        String address;
        String procent;
        String time;

        public Item(String address, String procent, String time) {
            this.address = address;
            this.procent = procent;
            this.time = time;
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
            vh.addres = (TextView)view.findViewById(R.id.deliver_addres_item);
            vh.procent = (TextView)view.findViewById(R.id.deliver_procent_item);
            vh.time = (TextView)view.findViewById(R.id.deliver_time_item);

            vh.addres.setText(objects.get(position).address);
            vh.procent.setText(objects.get(position).procent);
            vh.time.setText(objects.get(position).time);


            return view;
        }
    }
    class ViewHolder
    {
        TextView addres, procent, time;
    }

}
