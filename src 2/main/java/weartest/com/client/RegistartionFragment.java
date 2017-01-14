package weartest.com.client;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import weartest.com.client.language.LanguageManager;
import weartest.com.client.language.LanguageObj;

/**
 * Created by alekseyg on 18/01/2016.
 */
public class RegistartionFragment extends Fragment {
    android.support.v4.app.FragmentTransaction ft;
    public static Fragment newInstance(){
        Fragment fragment = new RegistartionFragment();

        return fragment;
    }
    LinearLayout ll, loginLl;
    TextView login, howItsWork, locker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.registration, container, false);
        login = (TextView)v.findViewById(R.id.login);
        howItsWork = (TextView)v.findViewById(R.id.how_it_worktxt);
        locker = (TextView)v.findViewById(R.id.locker);

        login.setText(LanguageObj.getLanguageValue(LanguageManager.LOG_IN));
        howItsWork.setText(LanguageObj.getLanguageValue(LanguageManager.HOW_ITS_WORK));
        locker.setText(LanguageObj.getLanguageValue(LanguageManager.LOCKER));

        ll = (LinearLayout)v.findViewById(R.id.how_it_workll);
        loginLl = (LinearLayout)v.findViewById(R.id.login_ll);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intyent = new Intent(FirstActivity.this,OrderActivity.class);
                startActivity(intyent);*/
            }
        });

        loginLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Fragment frag = new RegistrationForm().newInstance();
                ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.add(R.id.login_fragment11, frag);
                ft.commit();
            }
        });
        return v;
    }

}
