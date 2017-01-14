package weartest.com.client;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.rey.material.widget.Button;

import java.util.Set;

/**
 * Created by alekseyg on 18/01/2016.
 */
public class Profile extends Fragment {
    EditText firstName, lastName, mail, country, phone, password, confirmPassword, countryCode, phoneArea, city;
    TextView userId;
    Button next;
    public static Fragment newInstance(){
        Fragment fragment = new Profile();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.profile, container, false);
      //  next = (com.rey.material.widget.Button) getActivity().findViewById(R.id.next);
       /* next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  if (code.getText().toString().equals("1234")) {
                RestAdapter adapter = new RestAdapter.Builder()

                        .setEndpoint("http://takvisa.azurewebsites.net/api/accounts") //Setting the Root URL
                        .build(); //Finally building the adapter

                //Creating object for our interface
                RegisterAPI api = adapter.create(RegisterAPI.class);
                User user = new User(Integer.parseInt(ValuesAndPreferencesManager.gertUserID()),
                                    firstName.getText().toString(),lastName.getText().toString()
                                   *//* false,false,"usd","eng","type",mail.getText().toString(),
                                    country.getText().toString(),"Tel Aviv"*//*);
                Log.d("Test","User request "+user.toString());
                api.updateUser(user, new Callback<User>() {
                    @Override
                    public void success(User user, Response response) {
                        if(user.getUserID()!=0&&user.getUserID()!=-9999)
                        {
                            Intent intent = new Intent(getActivity(), Order.class);
                            startActivity(intent);
                        }
                        Log.d("TEst", "Profile User = " + user.toString() + " Response " + response.toString());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("TEst", "Error " + error.toString());
                    }
                });
            }
        });*/
        userId = (TextView)v.findViewById(R.id.actionTitle1);
        userId.setText("id: "+ValuesAndPreferencesManager.gertUserID());
        firstName = (EditText)v.findViewById(R.id.firstNameET1);
        lastName = (EditText)v.findViewById(R.id.lastNameET1);
        mail = (EditText)v.findViewById(R.id.emailET1);
        country = (EditText)v.findViewById(R.id.countryET1);
        city = (EditText)v.findViewById(R.id.cityET1);
        phone = (EditText)v.findViewById(R.id.phoneNumber1);
        countryCode= (EditText)v.findViewById(R.id.phonePrefix1);


        Set<String> ss = ValuesAndPreferencesManager.getCountry();
        String[]ar = new String[ss.size()];
         ss.toArray(ar);
        country.setText(ar[2]);
        countryCode.setText(ar[0]);
        phone.setText(ar[1]);
        Log.d("test",ss.toArray().toString());
        return v;
    }

}
