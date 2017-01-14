package weartest.com.client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.app.Dialog;
import com.rey.material.widget.Button;
import com.squareup.okhttp.OkHttpClient;

import java.util.HashSet;
import java.util.Set;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import weartest.com.client.language.LanguageManager;

/**
 * Created by alekseyg on 21/12/2015.
 */
public class LoginFragment2 extends Fragment{
    TextView info, wrongNumber,pleaseCode;
    EditText code;

    Dialog.Builder builder = null;
    com.rey.material.widget.Button next;
    weartest.com.client.dialog.Dialog customDialog;
    public static Fragment newInstance(){
        Fragment fragment = new LoginFragment2();

        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_activity2, container, false);
        info = (TextView)v.findViewById(R.id.info_login2);
        pleaseCode = (TextView)v.findViewById(R.id.please_code);
        wrongNumber = (TextView)v.findViewById(R.id.wrong_number);
        code = (EditText)v.findViewById(R.id.phone2);
        code.setHint(LanguageManager.getLanguageStringValue(LanguageManager.CODE));
        wrongNumber.setText(LanguageManager.getLanguageStringValue(LanguageManager.WRONG_NUMBER));
        pleaseCode.setText(LanguageManager.getLanguageStringValue(LanguageManager.ENTER_CODE));
        wrongNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new LoginFragment1().newInstance();
        /*Bundle bndl = new Bundle();
        bndl.putString("1", code.getText().toString());
        bndl.putString("2",phone_number.getText().toString());
        bndl.putString("3",countryName.getText().toString());
        fragment.setArguments(bndl);*/
                MyFragmentTransaction.StartFragmentTransaction(fragment, getActivity(), Profile.LOG_TAG, R.id.login_fragment11);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.login_fragment11, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.remove(LoginFragment2.newInstance());
                fragmentTransaction.commit();
            }
        });
        //code.setText("1234");
        next = (Button)v.findViewById(R.id.next_login2);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TEst", "buuton frag2");
                //LoginFragment2 frag = (LoginFragment2)getSupportFragmentManager().findFragmentByTag("login2");
                sendRequest();
            }
        });

        String phone = getArguments().getString("1")+getArguments().getString("2");// getActivity().getIntent().getExtras().getString("phone");
        Set<String> ss = new HashSet<String>();
        ss.add(getArguments().getString("1"));
        ss.add(getArguments().getString("2"));
        ss.add(getArguments().getString("3"));
        ValuesAndPreferencesManager.saveCountry(ss);
        info.setText(LanguageManager.getLanguageStringValue(LanguageManager.WE_SENT_SMS) + phone);
        customDialog = createLocalLoadingDialogInstance(getContext(),LanguageManager.getLanguageStringValue(LanguageManager.WAIT),true);
        if(ValuesAndPreferencesManager.getLangId()==1)
        {
            next.setBackgroundResource(R.drawable.next_btn_il);
        }
        else{next.setBackgroundResource(R.drawable.button_next);}
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

    public void sendRequest()
    {
        customDialog.show();
        RestAdapter adapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)

                .setClient(new OkClient(new OkHttpClient()))
                .setEndpoint("https://ta-kvisa.com/api/accounts") //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        RegisterAPI api = adapter.create(RegisterAPI.class);
        api.verificationCode(new VerificationCode(ValuesAndPreferencesManager.getTel(), Integer.parseInt(code.getText().toString())), new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                Log.d("TEst", "User = " + user.toString() + " Response " + response.toString());
                if (user.getUserID() != 0 && user.getAccountType().equals("3")/*&&user.getUserID()!=-9999&& user.getFirstName() != null*/) {
                    ValuesAndPreferencesManager.saveName(user.getFirstName());
                    ValuesAndPreferencesManager.saveLastName(user.getLastName());
                    ValuesAndPreferencesManager.savemail(user.getEmail());

                    ValuesAndPreferencesManager.saveId(String.valueOf(user.getUserID()));
                    customDialog.dismiss();
                    goToProfile();
                    /*Fragment list = new ChooseLockerFragment().newInstance();
                    MyFragmentTransaction.StartFragmentTransaction(list, getActivity(), "list", R.id.login_fragment11);*/
                    /*Intent intent = new Intent(getActivity(), Order.class);
                    startActivity(intent);*/
                }
                else
                {
                    customDialog.dismiss();
                    Log.d("TEst", "Code is not correct");
                    Toast.makeText(getContext(), "Code is not correct",Toast.LENGTH_SHORT).show();
                }
               /* else if(user.getUserID()==-9999)
                {
                    Log.d("TEst","Code is not correct");
                    Intent intent = new Intent(getActivity(), Deliver.class);
                    startActivity(intent);
                }
                if(user.getUserID()!=0&&user.getUserID()!=-9999&user.getFirstName()==null)
                {
                   ValuesAndPreferencesManager.saveId(String.valueOf(user.getUserID()));
                    Fragment fragment = new Profile().newInstance();
                    MyFragmentTransaction.StartFragmentTransaction(fragment,getActivity(),"Login3");
                    Intent intent = new Intent(getActivity(), Deliver.class);
                    startActivity(intent);

                }*/

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("TEst", "Error " + error.toString());
            }
        });
    }

    public void goToProfile()
    {
        Fragment fragment = new Profile().newInstance();
        /*Bundle bndl = new Bundle();
        bndl.putString("1", code.getText().toString());
        bndl.putString("2",phone_number.getText().toString());
        bndl.putString("3",countryName.getText().toString());
        fragment.setArguments(bndl);*/
        MyFragmentTransaction.StartFragmentTransaction(fragment, getActivity(), Profile.LOG_TAG, R.id.login_fragment11);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.login_fragment11, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.remove(LoginFragment2.newInstance());
        fragmentTransaction.commit();
    }
    public static weartest.com.client.dialog.Dialog createLocalLoadingDialogInstance(
            Context context, String res_id, boolean isCancelable) {
        weartest.com.client.dialog.Dialog customDialog = new weartest.com.client.dialog.Dialog(context,
                Color.parseColor("#000000"),
                R.drawable.logo_small_purple,
                Color.parseColor("#ffffff"));

        customDialog.setCancelable(isCancelable);
        customDialog.setText(res_id);

        return customDialog;
    }

}
