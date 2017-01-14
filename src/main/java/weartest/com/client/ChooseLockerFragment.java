package weartest.com.client;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.widget.FloatingActionButton;
import com.rey.material.widget.Slider;
import com.rey.material.widget.SnackBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.LogRecord;

import com.google.gson.annotations.SerializedName;
import com.squareup.okhttp.OkHttpClient;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import weartest.com.client.language.LanguageManager;
import weartest.com.client.language.LanguageObj;

/**
 * Created by alekseyg on 15/02/2016.
 */

public class ChooseLockerFragment extends Fragment implements Handler.Callback{
    Dialog.Builder builder = null;
    com.rey.material.widget.Button plus, minus;
    TextView countText,text_choos_locker;
   //Slider sl_discrete;
    String tag;
    SnackBar mSnackBar;
    public  static java.util.logging.Handler handler;
    boolean flag = true;
    final static public String LOG_TAG = "ChooseLockerFragment";
    public static Fragment newInstance(){
        Fragment fragment = new ChooseLockerFragment();

        return fragment;
    }
    ListView listLockers;
    ArrayList<Item> products = new ArrayList<Item>();
    ListAdapter boxAdapter;

    @Override
    public void onResume() {
        super.onResume();
        if(builder!=null)
        builder.title(LanguageManager.getLanguageStringValue(LanguageManager.CHOOSE_COUNT))
                .positiveAction(LanguageManager.getLanguageStringValue(LanguageManager.BLOCK_LOCKER))
                .negativeAction(LanguageManager.getLanguageStringValue(LanguageManager.CANCEL));

        if(boxAdapter!=null)
        for(int i=0;i<boxAdapter.getCount();i++){
         View view=   boxAdapter.getView(i,null,listLockers);
           TextView av =  (TextView)view.findViewById(R.id.available);
            av.setText(LanguageManager.getLanguageStringValue(LanguageManager.AVAILEBLE_LOCKERS)+" 0");
            TextView dis = (TextView)view.findViewById(R.id.km);
            dis.setText("3.0 " + LanguageManager.getLanguageStringValue(LanguageManager.KM));
        //vh.distance.setText("2.0 " + LanguageManager.getLanguageStringValue(LanguageManager.KM));
        //vh.available_lockers.setText(LanguageManager.getLanguageStringValue(LanguageManager.AVAILEBLE_LOCKERS)+" 0");

        }
    }

    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.list_lockers, container, false);
        LanguageObj  languageObj=  LanguageObj.getLanguageObjectInstance();
      HashMap<String,String>ss =  languageObj.language;
      //  final View v1 = inflater.inflate(R.layout.layout_dialog_custom, container, false);
        listLockers = (ListView)v.findViewById(R.id.list_locker_items);
        products.clear();
 tag = getTag();
        text_choos_locker  = (TextView)v.findViewById(R.id.text_choos_locker);
        text_choos_locker.setText(LanguageManager.getLanguageStringValue(LanguageManager.TEXT_CHOOSE_LOCKER));


           // products.add(new Item("name"+i,"addresss"+i,""+(i+1.5),0,"www.www"));

        RestAdapter adapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)

                .setClient(new OkClient(new OkHttpClient()))
                .setEndpoint("https://ta-kvisa.com") //Setting the Root URL
                .build(); //Finally building the adapter

        RegisterAPI api = adapter.create(RegisterAPI.class);

        api.getLockers("1",new Callback<Item>() {
            @Override
            public void success(Item s, Response response) {
                Log.d("test", s.toString() + " " + response.getStatus());
                if (response.getStatus() == 200) {
                    products.add(s);
                    products.add(new Item(2,"TelAviv KOMING SOON","0",0,"https://ta-kvisa.com/Images/Stands/Arlozorov72_corner_min.png",32.0861031,34.7775158));//int StandId, String adress, String distance, int count, String urlPicture,double Latitude ,double Longitude
                    boxAdapter = new ListAdapter(getActivity(), products);
                    listLockers.setAdapter(boxAdapter);

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("test", "error"+error.toString() );
            }
        });

      //  final TextView txt  = (TextView)getActivity().findViewById(R.id.amountView2);
      /*  plus = (Button)v1.findViewById(R.id.amountBtPlusDialog);
        minus = (Button)v1.findViewById(R.id.amountBtMinusDialog);
        countText = (TextView)v1.findViewById(R.id.amountViewdialog);*/

        listLockers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // final View v1 = inflater.inflate(R.layout.layout_dialog_custom, container, false);

                //   txt.setText(String.format("pos=%.1f value=%d", sl_discrete.getPosition(), sl_discrete.getValue()));
               /* sl_discrete.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
                    @Override
                    public void onPositionChanged(Slider view, float oldPos, float newPos, int oldValue, int newValue) {
                       // txt.setText(String.format("pos=%.1f value=%d", newPos, newValue));
                        Log.d("Test", String.format("pos=%.1f value=%d", newPos, newValue));//.setText(String.format("pos=%.1f value=%d", newPos, newValue));
                    }
                });*/
                if (flag) {
                    if(products.get(position).AvailableLockersCount>0){
                    if (tag == null || !tag.equals("deliver")) {
                        builder = new SimpleDialog.Builder(R.style.Theme_AppTheme) {

                            @Override
                            protected Dialog onBuild(Context context, int styleId) {
                                Dialog dialog = super.onBuild(context, styleId);
                                dialog.layoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                dialog.contentView(R.layout.layout_dialog_custom);
                                plus = (com.rey.material.widget.Button) dialog.findViewById(R.id.amountBtPlusDialog);
                                minus = (com.rey.material.widget.Button) dialog.findViewById(R.id.amountBtMinusDialog);
                                countText = (TextView) dialog.findViewById(R.id.amountViewdialog);
                                // sl_discrete = (Slider) dialog.findViewById(R.id.slider_sl_discrete);
                                mSnackBar = (SnackBar) dialog.findViewById(R.id.main_sn);
                                plus.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        coiunterListener(v);
                                    }
                                });
                                minus.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        coiunterListener(v);
                                    }
                                });
                                dialog.getPositivButton().setEnabled(false);
                                return dialog;
                            }

                            @Override
                            public void onPositiveActionClicked(DialogFragment fragment) {

                                if(Integer.parseInt(countText.getText().toString())>0){

                                    super.onPositiveActionClicked(fragment);
                                Intent intent = new Intent(getActivity(), Order.class);
                                startActivity(intent);
                                getActivity().finish();}

                            }

                            @Override
                            public void onNegativeActionClicked(DialogFragment fragment) {

                                super.onNegativeActionClicked(fragment);
                            }

                        };
                        k = products.get(position).AvailableLockersCount;

                        builder.title(LanguageManager.getLanguageStringValue(LanguageManager.CHOOSE_COUNT))
                                .positiveAction(LanguageManager.getLanguageStringValue(LanguageManager.BLOCK_LOCKER))
                                .negativeAction(LanguageManager.getLanguageStringValue(LanguageManager.CANCEL))
                        //  .contentView(R.layout.layout_dialog_custom)
                        ;

                        DialogFragment fragment = DialogFragment.newInstance(builder);


                        Log.d("TEst", "click item " + position);
                /*Intent intent = new Intent(getActivity(), Order.class);
                startActivity(intent);*/
                        fragment.show(getFragmentManager(), null);
                    } else {
                        Fragment fragment = new ListLockerStationDeliver().newInstance();
                        weartest.com.client.MyFragmentTransaction.StartFragmentTransaction(fragment, getActivity(), "deliver", R.id.deliver_fragment);
                    }
                    }
                    //else{ view.setBackgroundResource(R.drawable.coming_soon);}
                } else {
                    builder = new SimpleDialog.Builder(R.style.SimpleDialogLight) {
                        @Override
                        public void onPositiveActionClicked(DialogFragment fragment) {
                            Toast.makeText(fragment.getDialog().getContext(), "Agreed", Toast.LENGTH_SHORT).show();
                            super.onPositiveActionClicked(fragment);
                        }

                        @Override
                        public void onNegativeActionClicked(DialogFragment fragment) {
                            Toast.makeText(fragment.getDialog().getContext(), "Disagreed", Toast.LENGTH_SHORT).show();
                            super.onNegativeActionClicked(fragment);
                        }
                    };

                    ((SimpleDialog.Builder) builder).message("Select a different address")
                            .title("All lockers are occupied")
                            .positiveAction("AGREE")
                            .negativeAction("DISAGREE");
                    DialogFragment fragment = DialogFragment.newInstance(builder);

                    fragment.show(getFragmentManager(), null);
                }
                if(builder!=null)
                    if(builder.getPosButton()!=null)
                        builder.getPosButton().setEnabled(false);
            }


        });
        GetData data = new GetData();
        try {
            String str =    data.execute().get();
            try {
                parseServiceItem(str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        handler = new java.util.logging.Handler() {
            @Override
            public void close() {

            }

            @Override
            public void flush() {
                if(builder!=null){
                    builder.title(LanguageManager.getLanguageStringValue(LanguageManager.CHOOSE_COUNT))
                            .positiveAction(LanguageManager.getLanguageStringValue(LanguageManager.BLOCK_LOCKER))
                            .negativeAction(LanguageManager.getLanguageStringValue(LanguageManager.CANCEL));

                if(boxAdapter!=null)
                    for(int i=0;i<boxAdapter.getCount();i++){
                        View view=   boxAdapter.getView(i,null,listLockers);
                        TextView av =  (TextView)view.findViewById(R.id.available);
                        av.setText(LanguageManager.getLanguageStringValue(LanguageManager.AVAILEBLE_LOCKERS)+" 0");
                        TextView dis = (TextView)view.findViewById(R.id.km);
                        dis.setText("3.0 " + LanguageManager.getLanguageStringValue(LanguageManager.KM));
                    }
                    if(builder.getPosButton()!=null){
             //   builder.getPosButton().setClickable(false);
             //   builder.getPosButton().setTextColor(R.color.GREY_COLOR);
                    }
                }
            }

            @Override
            public void publish(LogRecord record) {

            }
        };
       // MainActivity.menuHandler.close();
        MainActivity.handOs.sendEmptyMessage(1);
        return v;
    }
    int k = 0;
public void coiunterListener(View v)
{
   // sl_discrete.setValue(k,true);
    switch (v.getId())
    {
        case R.id.amountBtPlusDialog: if(Integer.parseInt(countText.getText().toString())<=k)
            countText.setText(String.valueOf(Integer.parseInt(countText.getText().toString())+1));break;
        case R.id.amountBtMinusDialog: if(countText.getText().toString().equals("0"))  countText.setText("0"); else countText.setText(String.valueOf(Integer.parseInt(countText.getText().toString()) - 1));

            break;
    }
    //sl_discrete.setMaxValue(Integer.parseInt(countText.getText().toString()));
    if(builder!=null&&countText.getText().toString().equals("0"))
        builder.getPosButton().setEnabled(false);
    else  builder.getPosButton().setEnabled(true);
  //  sl_discrete.setPosition((float) (Integer.parseInt(countText.getText().toString())/2),true);
    if(Integer.parseInt(countText.getText().toString())>1)
    {

        //mSnackBar.textColor(R.color.RED_COLOR);
        mSnackBar.applyStyle(R.style.Material_Widget_SnackBar_Tablet_MultiLineMy).text(LanguageManager.getLanguageStringValue(LanguageManager.MORE_ONE_LOCKERS)).
                duration(0).textColor(R.color.RED_BLINK_COLOR).setBackgroundResource(R.color.colorAccent);
        mSnackBar.show();

    //    builder.getPosButton().setTextColor(android.R.color.holo_red_dark);


    }
    else {mSnackBar.dismiss();}

    if(Integer.parseInt(countText.getText().toString())==0)
    {
        mSnackBar.dismiss();
        builder.getPosButton().setClickable(false);
    //    builder.getPosButton().setTextColor(R.color.GREY_COLOR);
    }
    else builder.getPosButton().setClickable(true);


   // sl_discrete.setMvalue(countText.getText().toString());
  /* if(Integer.parseInt(countText.getText().toString())==(k))
       sl_discrete.setValue((float)10,true);
else
   {

       sl_discrete.setValue((float) ((10/k)+Integer.parseInt(countText.getText().toString())),true);
   }*/
    //sl_discrete.setValue(Float.parseFloat(countText.getText().toString()),true);
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
    private DisplayImageOptions options;
    private   final String[] IMAGE_URLS = Constants.IMAGES;
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    ListAdapter(Context context, ArrayList<Item> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new CircleBitmapDisplayer(Color.WHITE, 5))
                .build();

    }
    @Override
    public int getCount() {
        return objects.size();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //case R.id.black_button_selector: break;
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
        vh.adress = (TextView)view.findViewById(R.id.textView3);
        vh.distance = (TextView)view.findViewById(R.id.km);
        vh.distance.setText("2.0 "+LanguageManager.getLanguageStringValue(LanguageManager.KM));
        vh.available_lockers = (TextView)view.findViewById(R.id.available);
        vh.available_lockers.setText(LanguageManager.getLanguageStringValue(LanguageManager.AVAILEBLE_LOCKERS)+" "+ products.get(position).AvailableLockersCount);

        float [] ff= new float[7];
        Location locMy = new Location("");
        Location loc2 = new Location("");

        locMy.setLatitude(ValuesAndPreferencesManager.getLocation()[0]);
        locMy.setLongitude(ValuesAndPreferencesManager.getLocation()[1]);

        loc2.setLatitude(products.get(position).Latitude);
        loc2.setLongitude(products.get(position).Longitude);

        ;
        Location.distanceBetween(locMy.getLatitude(), locMy.getLongitude(),
                loc2.getLatitude(), loc2.getLongitude(),
                ff);
        DecimalFormat format=new DecimalFormat("#.##");
        double distanceInMeters = locMy.distanceTo(loc2)/1000;
        Log.d("test", "ff"+ff[0]+" "+format.format(distanceInMeters));

        vh.distance.setText(format.format(distanceInMeters)+" "+LanguageManager.getLanguageStringValue(LanguageManager.KM));
           // products.get(position).AvailableLockersCount = position;
        vh.adress.setText(products.get(position).Address);
        if(products.get(position).AvailableLockersCount>0){
        ImageLoader.getInstance().
                displayImage( products.get(position).ImgUrl, vh.logo, options, animateFirstListener);
        vh.logo.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+products.get(position).Latitude+","+products.get(position).Longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        startActivity(mapIntent);
    }
});} else vh.logo.setBackgroundResource(R.drawable.coming_soon);
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
        TextView available_lockers;
     //   ImageButton plus, minus;
    //    FloatingActionButton plus, minus;

    }
    class Item
    {

        @SerializedName("Address")
        String Address;
        @SerializedName("StandId")
int StandId;
        @SerializedName("AvailableLockersCount")
        int AvailableLockersCount;
        @SerializedName("ImgUrl")
        String ImgUrl;
        @SerializedName("Latitude")
        double Latitude ;
        @SerializedName("Longitude")
        double Longitude ;

        //   TextView countTxt;

        public Item(int StandId, String adress, String distance, int count, String urlPicture,double Latitude ,double Longitude ) {

            this.Address = adress;
            this.StandId = StandId;
            this.AvailableLockersCount = count;
            this.ImgUrl = urlPicture;
            this.Latitude = Latitude;
            this.Longitude = Longitude;
        }

       /* public void setCountTxt(TextView countTxt) {
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
        }*/



        @Override
        public String toString() {
            return "Item{" +

                    ", adress='" + Address + '\'' +
                    ", distance='" +  + '\'' +

                    ", urlPicture='" + ImgUrl + '\'' +
                    '}';
        }
    }

    private  static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

         final static List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

    ///////////
///GETSERVICELIST
    static HashMap<String,ArrayList<ItemService>> ItemsService = new HashMap();

    void parseServiceItem(String str) throws JSONException {
        JSONArray data = new JSONArray(str);
        for(int i=0;i<data.length();i++)
        {
            JSONArray items = data.getJSONObject(i).getJSONArray("Items");
          //  Log.d("items",items.toString());
            Gson gson = new Gson();
            Type listType = new TypeToken<List<ItemService>>(){}.getType();
            List<ItemService> posts = (List<ItemService>) gson.fromJson(items.toString(), listType);


            ItemsService.put(data.getJSONObject(i).getString("Name"), (ArrayList<ItemService>) posts);
            //Log.d("item",posts.toString());
        }
       // Log.d("Test",data.toString());



    }

    class ItemService
    {
        @SerializedName("Id")
        int id;
        @SerializedName("Name")
        String name;
        @SerializedName("Volume")
        int volume;
        @SerializedName("ServiceId")
        int serviceId;

        public ItemService(int id, String name, int volume, int serviceId) {
            this.id = id;
            this.name = name;
            this.volume = volume;
            this.serviceId = serviceId;
        }

        @Override
        public String toString() {
            return "ItemService{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", volume=" + volume +
                    ", serviceId=" + serviceId +
                    '}';
        }
    }
    static public class GetData extends AsyncTask<String, String, String> {

        String url ="https://ta-kvisa.com/api/common/GetServiceList";

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... params) {
            String json = null;
            try {
                String line, newjson = "";
                URL urls = new URL(url);
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(urls.openStream(), "UTF-8")))
                {
                    while ((line = reader.readLine()) != null) {
                        newjson += line;
                    }

                    json = newjson.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }




            return json;
        }




        @Override
        protected void onPostExecute(String result)
        {

        }


    }
}
