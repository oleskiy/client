package weartest.com.client;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rey.material.widget.Button;

/**
 * Created by alekseyg on 18/01/2016.
 */
public class Settings extends Fragment {

    Button profile,openHour,information, price, contacts;
    public static Fragment newInstance(){
        Fragment fragment = new Settings();

        return fragment;
    }
    LinearLayout ll, loginLl;
    TextView login, howItsWork, locker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.settings, container, false);

        profile = (Button)v.findViewById(R.id.profile);
        openHour = (Button)v.findViewById(R.id.open_hours);
        information = (Button)v.findViewById(R.id.information);
        price = (Button)v.findViewById(R.id.price);
        contacts = (Button)v.findViewById(R.id.our_contact);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Profile().newInstance();
                MyFragmentTransaction.StartFragmentTransaction(fragment, getActivity(),"profile",R.id.login_fragment11);

                /**FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.login_fragment11, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();**/
            }
        });
        return v;
    }
}
