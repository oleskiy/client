/*
package weartest.com.client;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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

*/
/**
 * Created by alekseyg on 17/02/2016.
 *//*

public class GetFragment extends Fragment {
    public static Fragment newInstance(){
        Fragment fragment = new GetFragment();

        return fragment;
    }
    ListView items;
    ArrayList<Item> products = new ArrayList<Item>();
    ListAdapter boxAdapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.get_fragment, container, false);

        for(int i=0;i<10;i++)
        {
            products.add(new Item("addresssCutomer"+i,"short"+i,(15+i)+".00"));
        }
        items = (ListView)v.findViewById(R.id.list_gets);
        boxAdapter = new ListAdapter(getActivity(), products);
        items.setAdapter(boxAdapter);

        items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchActivity(SimpleScannerActivity.class);
            }
        });

        return v;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void launchActivity(Class<?> clss) {


        Intent intent = new Intent(getActivity(), clss);
        getActivity().startActivityForResult(intent, 0, null);
    }


    class ListAdapter extends BaseAdapter {
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
            vh.nameCustomer = (TextView)view.findViewById(R.id.deliver_addres_item);
            vh.product = (TextView)view.findViewById(R.id.deliver_procent_item);
            vh.time = (TextView)view.findViewById(R.id.deliver_time_item);

            vh.nameCustomer.setText(objects.get(position).nameCustomer);
            vh.product.setText(objects.get(position).product);
            vh.time.setText(objects.get(position).time);


            return view;
        }
    }
    class ViewHolder
    {
        TextView nameCustomer, product, time;
    }

    class Item
    {
        String nameCustomer;
        String product;
        String time;

        public Item(String nameCustomer, String product, String time) {
            this.nameCustomer = nameCustomer;
            this.product = product;
            this.time = time;
        }
    }
}
*/
