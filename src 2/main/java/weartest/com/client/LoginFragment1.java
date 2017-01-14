package weartest.com.client;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import weartest.com.client.zxing.MySimpleDialog;

//import android.app.Fragment;

/**
 * Created by alekseyg on 11/01/2016.
 */
public class LoginFragment1 extends Fragment{

Activity activity;
    public static Fragment newInstance(){
        Fragment fragment = new LoginFragment1();

        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }
    static com.rey.material.widget.EditText code;
    static com.rey.material.widget.EditText phone_number;
    TextView countryName;
    Button next;
    LinearLayout selectCountry;
    final static public String LOG_TAG = "Login1";
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_activity, container, false);
        choiceCountry();

        code = (com.rey.material.widget.EditText)v.findViewById(R.id.country_code);
        phone_number =(com.rey.material.widget.EditText)v.findViewById(R.id.phone_number);
        selectCountry = (LinearLayout)v.findViewById(R.id.select_country_list);
        countryName = (TextView)v.findViewById(R.id.country_name);
        selectCountry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                builder = new MySimpleDialog.Builder(R.style.SimpleDialogLight){
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        //      Toast.makeText(fragment.getDialog().getContext(), "You have selected " + getSelectedValue() + " as phone ringtone.", Toast.LENGTH_SHORT).show();
                        Log.d("TEst", "ok");
                       String item1=  (String)((MySimpleDialog.Builder)builder).getSelectedValue();
                        if(!item1.equals("-1"))
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

                ((MySimpleDialog.Builder)builder).items(ss,0)

                        .title("Countries")
                      //  .contentView(R.layout.search_layout)
                        .positiveAction("OK")
                        .negativeAction("CANCEL")
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
                        countryName.setText("Code not correct");
                    }
                }
            }
        });

            code.setText("+972");
            phone_number.setText("52408393");
        getItem();
            return v;
        }
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
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

   /*Button btn = (Button) getActivity().findViewById(R.id.next);
    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // final TextView countryCode = (TextView) ((LoginFragment1)frag2).getCountryCode();//(TextView) ((LoginFragment1)frag2).getView().findViewById(R.id.country_code);
            // final EditText telephone = (EditText) ((LoginFragment1)frag2).phoneView();//(TextView) ((LoginFragment1)frag2).getView().findViewById(R.id.phone_number);
            if (code != null && !code.getText().toString().equals("+") && phone_number.getText().toString() != null && !phone_number.getText().toString().equals("")) {
                Fragment fragment = new LoginFragment2().newInstance();
                Bundle bndl = new Bundle();
                bndl.putString("1", code.getText().toString());
                bndl.putString("2", phone_number.getText().toString());
                bndl.putString("3", countryName.getText().toString());
                fragment.setArguments(bndl);
                MyFragmentTransaction.StartFragmentTransaction(fragment, getActivity(), "login2");
                *//**  FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                 FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                 fragmentTransaction.replace(R.id.login_fragment11, fragment);
                 fragmentTransaction.addToBackStack(null);
                 fragmentTransaction.commit();**//*
                Log.d("TEst", "True");
            } else {
                Log.d("TEst", "False");
            }
        }
    });*/

}
    public  static com.rey.material.widget.EditText getCountryCode(){return  code;}
    public static com.rey.material.widget.EditText phoneView(){return phone_number;}
    public void sendRequest()
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
            /**  FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
             FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

             fragmentTransaction.replace(R.id.login_fragment11, fragment);
             fragmentTransaction.addToBackStack(null);
             fragmentTransaction.commit();**/
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
