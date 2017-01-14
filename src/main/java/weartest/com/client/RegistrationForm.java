package weartest.com.client;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by alekseyg on 18/01/2016.
 */
public class RegistrationForm extends Fragment {

    public static Fragment newInstance(){
        Fragment fragment = new RegistrationForm();

        return fragment;
    }
    LinearLayout ll, loginLl;
    TextView login, howItsWork, locker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.registration_form, container, false);
    return v;
    }
}
