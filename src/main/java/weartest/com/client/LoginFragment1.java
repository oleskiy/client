package weartest.com.client;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.widget.Button;

import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import weartest.com.client.delivery.Main;
import weartest.com.client.language.LanguageManager;
import weartest.com.client.language.LanguageObj;
import weartest.com.client.zxing.MySimpleDialog;

import static android.R.layout.simple_spinner_item;

//import android.app.Fragment;

/**
 * Created by alekseyg on 11/01/2016.
 */
public class LoginFragment1 extends Fragment{

Activity activity;
    weartest.com.client.dialog.Dialog customDialog;
    public static Fragment newInstance(){
        Fragment fragment = new LoginFragment1();

        return fragment;
    }
void setNewLang(int position) throws JSONException {

    LanguageObj obj = LanguageObj.getLanguageObjectInstance();
    if(position==1){
        obj.setNewLang(2);}
    else
    {
        obj.initDefaultLanguage();
    }






}



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }
    static com.rey.material.widget.EditText code;
    static com.rey.material.widget.EditText phone_number;
    TextView countryName, countryTitle;// please;

    Button next;
    LinearLayout selectCountry,numberLayout;
    final static public String LOG_TAG = "Login1";
    String[] data = {"English", "Israel"};
    int []images = {R.drawable.uk,R.drawable.he};

    class LanguageAdapter extends BaseAdapter  {
        class ViewHolder
        {
            TextView txt;
            ImageView img;
        }


        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int position) {
            return data[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;
            ViewHolder vv = new ViewHolder();
            if(v == null) {
                v = LayoutInflater.from(getActivity()).inflate(R.layout.lang_item, null);
                if(vv!=null){
                    // vv.img = (ImageView)v.findViewById(R.id.img_item_menu);
                    vv.txt = (TextView)v.findViewById(R.id.country_name);

                    vv.img = (ImageView)v.findViewById(R.id.country_flag);
                }
                //v.setOnClickListener(this);

            }

           // v.setTag(position);
            //Tab tab = (Tab)getItem(position);
            //((TextView)v).setText("65");

         /*   if(tab == mSelectedTab) {
                v.setBackgroundColor(ThemeUtil.colorPrimary(MainActivity.this, 0));
                ((TextView)v).setTextColor(0xFFFFFFFF);
                ((TextView)v).setText("54321");
            }
            else {*/
            v.setBackgroundResource(0);
            if(vv!=null&&vv.txt!=null) {
                /*vv.txt.setTextColor(0xFF000000);
                vv.txt.setText(data[position]);*/
              vv.img.setImageResource(images[position]);
                //    vv.img.setImageResource(images[position]);
                vv.txt.setText(data[position]);
            }
            //  }

            return v;
        }

       /* @Override
        public void onClick(View v) {
            int position = (Integer)v.getTag();
            vp.setCurrentItem(position);
            dl_navigator.closeDrawer(fl_drawer);
        }*/
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_activity, container, false);
        choiceCountry();

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
numberLayout = (LinearLayout)v.findViewById(R.id.number_layout);
        numberLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        LanguageAdapter adapter1 = new LanguageAdapter();

        Spinner spinner = (Spinner)v.findViewById(R.id.spinner_language);
        spinner.setPrompt("Change language");
        spinner.setAdapter(adapter1);
        spinner.setSelection(ValuesAndPreferencesManager.getLangId());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    try {

                        ValuesAndPreferencesManager.saveLangId(position);
                        setNewLang(position);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /*spinner.setOnItemClickListener(new Spinner.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }


        });*/

        code = (com.rey.material.widget.EditText)v.findViewById(R.id.country_code);
        phone_number =(com.rey.material.widget.EditText)v.findViewById(R.id.phone_number);
        next = (com.rey.material.widget.Button)v.findViewById(R.id.next_login1);
        countryTitle = (com.rey.material.widget.TextView)v.findViewById(R.id.country);
       // please = (com.rey.material.widget.TextView)v.findViewById(R.id.please_phome_number);

        countryTitle.setText(LanguageManager.getLanguageStringValue(LanguageManager.COUNTRY));
        phone_number.setHint(LanguageManager.getLanguageStringValue(LanguageManager.PHONE_NUMBER));

     //   please.setText(LanguageManager.getLanguageStringValue(LanguageManager.PLEASE_ENTER_YOUR_PHONE_NUMBER));
        //next.setText(LanguageManager.getLanguageStringValue(LanguageManager.NEXT));



        phone_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String st = s.toString();
                if (st.startsWith("0")) {

                    s.clear();

                }
            }
        });
       // phone_number.setHintTextColor(R.color.TEXT_TITLES_COLOR);
        code.setHintTextColor(R.color.TEXT_TITLES_COLOR);
        selectCountry = (LinearLayout)v.findViewById(R.id.select_country_list);
        countryName = (TextView)v.findViewById(R.id.country_name);

        selectCountry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                builder = new MySimpleDialog.Builder(R.style.SimpleDialogLight) {
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        //      Toast.makeText(fragment.getDialog().getContext(), "You have selected " + getSelectedValue() + " as phone ringtone.", Toast.LENGTH_SHORT).show();
                        Log.d("TEst", "ok");
                        String item1 = (String) ((MySimpleDialog.Builder) builder).getSelectedValue();
                        if (!item1.equals("-1"))
                            //  code.setText(countries.get(item).deal_code);
                            code.setText(getCountyry(item1).getDeal_code());
                        //   fragment.dismiss();
                        super.onPositiveActionClicked(fragment);

                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        //   Toast.makeText(fragment.getDialog().getContext(), "Cancelled" , Toast.LENGTH_SHORT).show();
                        Log.d("TEst", "cancel");
                        // fragment.dismiss();
                        super.onNegativeActionClicked(fragment);

                    }
                };

                ((MySimpleDialog.Builder) builder).items(ss, 0)

                        .title(LanguageManager.getLanguageStringValue(LanguageManager.COUNTRIES))
                                //  .contentView(R.layout.search_layout)
                        .positiveAction(LanguageManager.getLanguageStringValue(LanguageManager.OK))
                        .negativeAction(LanguageManager.getLanguageStringValue(LanguageManager.CANCEL))
                        .build(activity);

                DialogFragment fragment = DialogFragment.newInstance(builder);
                fragment.show(getFragmentManager(), null);
                Log.d("Test", "Fragment click next");

            }
        });
        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            public void afterTextChanged(Editable s) {
                String st = s.toString();
                if (!st.startsWith("+")) {
                    st = "+" + st;
                    code.setText(st);
                    code.setSelection(1);
                }
                for (int i = 0; i < countries.size(); i++) {
                    if (countries.get(i).getDeal_code().equals(code.getText().toString())) {
                        countryName.setText(countries.get(i).getName());
                        break;
                    } else {
                        countryName.setText(LanguageManager.getLanguageStringValue(LanguageManager.CODE_NOT_CORRECT));
                    }
                }
            }
        });

            code.setText("+972");
           // phone_number.setText("52408393");
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TEst", "buuton frag2");
if(phone_number.getText().toString()!=null&&!phone_number.getText().toString().isEmpty()){
                next.setEnabled(false);
                sendRequest();
}

            }
        });
        getItem();
        customDialog = createLocalLoadingDialogInstance(getContext(),LanguageManager.getLanguageStringValue(LanguageManager.WAIT),true);
        handlerl = new Handler() {
            @Override
            public void close() {

            }

            @Override
            public void flush() {
               // please.setText(LanguageManager.getLanguageStringValue(LanguageManager.PLEASE_ENTER_YOUR_PHONE_NUMBER));
                countryTitle.setText(LanguageManager.getLanguageStringValue(LanguageManager.COUNTRY));
                phone_number.setHint(LanguageManager.getLanguageStringValue(LanguageManager.PHONE_NUMBER));
                phone_number.setFocusable(true);
                if(ValuesAndPreferencesManager.getLangId()==1)
                {
                    next.setBackgroundResource(R.drawable.next_btn_il);
                }
                else{next.setBackgroundResource(R.drawable.button_next);}
            }

            @Override
            public void publish(LogRecord record) {

            }

            };
        if(ValuesAndPreferencesManager.getLangId()==1)
        {
            next.setBackgroundResource(R.drawable.next_btn_il);
        }
        else{next.setBackgroundResource(R.drawable.button_next);}
            return v;
        }
    public static Handler handlerl;
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            setNewLang(ValuesAndPreferencesManager.getLangId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    Dialog.Builder builder = null;
    ArrayList<Country> countries ;
    String country = null;
    String strName=null;
    ArrayAdapter<Country> arrayAdapter;
    ArrayList<String> countryString = new ArrayList<String>();
int item=-1;
    String []ss;
    public void choiceCountry()
    {

        countries = new ArrayList<Country>();
        JSONArray ar=null;
        arrayAdapter = new ArrayAdapter<Country>(
                activity,
                android.R.layout.select_dialog_singlechoice);
        try {
            // Log.d("TEst", ""+CountryCode.code);
            JSONObject obj = new JSONObject(CountryCode.code);
            ar = obj.getJSONArray("counties");
        } catch (JSONException e) {
            e.printStackTrace();
        }
       ss = new String[ar.length()];
        for(int i=0;i<ar.length();i++)
        {
            try {
                JSONObject country = (JSONObject) ar.get(i);
                String name = country.getString("name");
                String dealCode = country.getString("dial_code");
                String code = country.getString("code");
                ss[i]=name;
                Country countryObj = new Country(name, code, dealCode);
                countries.add(countryObj);
                arrayAdapter.add(/*name+"("+dealCode+")"*/countryObj);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        Log.d("TEst", "" + countries.size());




    }

public void getItem()
{



}
    public  static com.rey.material.widget.EditText getCountryCode(){return  code;}
    public static com.rey.material.widget.EditText phoneView(){return phone_number;}
    public void sendRequest()
    {
        final weartest.com.client.dialog.Dialog dd =  createLocalLoadingDialogInstance(getContext(), "ss", false);
        dd.show();
        String phone = code.getText().toString()+phone_number.getText().toString();
        phone = phone.substring(1);
        RestAdapter adapter = new RestAdapter.Builder()
        .setLogLevel(RestAdapter.LogLevel.FULL)

                .setClient(new OkClient(new OkHttpClient()))
                .setEndpoint("https://ta-kvisa.com/api/accounts") //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        ValuesAndPreferencesManager.saveTel(phone);
        RegisterAPI api = adapter.create(RegisterAPI.class);

        api.createUser(phone, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Log.d("test", s + " " + response.getStatus());
                if (response.getStatus() == 200) {
                    dd.dismiss();
                    goToNextFragment();
                    next.setEnabled(true);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("TEst", "Error " + error.toString());next.setEnabled(true);
            }
        });





    }
    public static weartest.com.client.dialog.Dialog createLocalLoadingDialogInstance(
            Context context, String res_id, boolean isCancelable) {
        weartest.com.client.dialog.Dialog customDialog = new weartest.com.client.dialog.Dialog(context,
                Color.parseColor("#000000"),
                R.drawable.logo_small_purple,
                Color.parseColor("#ffffff"));

        customDialog.setCancelable(isCancelable);
        customDialog.setText(LanguageManager.getLanguageStringValue(LanguageManager.WAIT));

        return customDialog;
    }
void goToNextFragment()
{
    // final TextView countryCode = (TextView) ((LoginFragment1)frag2).getCountryCode();//(TextView) ((LoginFragment1)frag2).getView().findViewById(R.id.country_code);
    // final EditText telephone = (EditText) ((LoginFragment1)frag2).phoneView();//(TextView) ((LoginFragment1)frag2).getView().findViewById(R.id.phone_number);
    if (code!=null&&!code.getText().toString().equals("+") && phone_number.getText().toString() != null && !phone_number.getText().toString().equals("")) {
        Fragment fragment = new LoginFragment2().newInstance();
        Bundle bndl = new Bundle();
        bndl.putString("1", code.getText().toString());
        bndl.putString("2",phone_number.getText().toString());
        bndl.putString("3",countryName.getText().toString());
        fragment.setArguments(bndl);
        MyFragmentTransaction.StartFragmentTransaction(fragment,getActivity(),"login2",R.id.login_fragment11);
         FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.login_fragment11, fragment);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.remove(LoginFragment1.newInstance());
        fragmentTransaction.commit();
        Log.d("TEst", "True");
    } else {
        Log.d("TEst", "False");
    }
}

    public Country getCountyry(String value)
    {Country country=null;
        for(int i=0;i<countries.size();i++)
        {
           if( countries.get(i).getName().equals(value))
            {
                country=countries.get(i);
                return country;

            }
        }
        return country;
    }


}
