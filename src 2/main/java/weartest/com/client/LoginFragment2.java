package weartest.com.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.rey.material.app.Dialog;

import java.util.HashSet;
import java.util.Set;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by alekseyg on 21/12/2015.
 */
public class LoginFragment2 extends Fragment{
    TextView info, wrongNumber;
    EditText code;
    Dialog.Builder builder = null;
    com.rey.material.widget.Button next;
    public static Fragment newInstance(){
        Fragment fragment = new LoginFragment2();

        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_activity2, container, false);
        info = (TextView)v.findViewById(R.id.info_login2);
        wrongNumber = (TextView)v.findViewById(R.id.wrong_number);
        code = (EditText)v.findViewById(R.id.phone2);
        code.setText("1234");



        String phone = getArguments().getString("1")+getArguments().getString("2");// getActivity().getIntent().getExtras().getString("phone");
        Set<String> ss = new HashSet<String>();
        ss.add(getArguments().getString("1"));
        ss.add(getArguments().getString("2"));
        ss.add(getArguments().getString("3"));
        ValuesAndPreferencesManager.saveCountry(ss);
        info.setText(info.getText().toString() + phone);

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
        RestAdapter adapter = new RestAdapter.Builder()

                .setEndpoint("http://takvisa.azurewebsites.net/api/accounts") //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        RegisterAPI api = adapter.create(RegisterAPI.class);
        api.verificationCode(new VerificationCode("53", Integer.parseInt(code.getText().toString())), new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                Log.d("TEst", "User = " + user.toString() + " Response " + response.toString());
                if (user.getUserID() != 0 &&user.getUserID()!=-9999&& user.getFirstName() != null) {
                    Fragment list = new ChooseLockerFragment().newInstance();
                    MyFragmentTransaction.StartFragmentTransaction(list,getActivity(),"list",R.id.login_fragment11);
                    /*Intent intent = new Intent(getActivity(), Order.class);
                    startActivity(intent);*/
                }
                else if(user.getUserID()==-9999)
                {
                    Log.d("TEst","Code is not correct");
                    Intent intent = new Intent(getActivity(), Deliver.class);
                    startActivity(intent);
                }
                if(user.getUserID()!=0&&user.getUserID()!=-9999&user.getFirstName()==null)
                {
                    /*ValuesAndPreferencesManager.saveId(String.valueOf(user.getUserID()));
                    Fragment fragment = new Profile().newInstance();
                    MyFragmentTransaction.StartFragmentTransaction(fragment,getActivity(),"Login3");*/
                    Intent intent = new Intent(getActivity(), Deliver.class);
                    startActivity(intent);

                }

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("TEst", "Error " + error.toString());
            }
        });
    }

}
