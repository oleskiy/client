package weartest.com.client;

/**
 * Created by alekseyg on 17/02/2016.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rey.material.widget.Button;

public class GetPut   extends Fragment {
    Button get, put;
    public static Fragment newInstance(){
        Fragment fragment = new GetPut();

        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.get_put, container, false);
        get = (Button)v.findViewById(R.id.get);
        put = (Button)v.findViewById(R.id.put);
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Fragment fragment = new GetFragment().newInstance();
                MyFragmentTransaction.StartFragmentTransaction(fragment,getActivity(),"getFragment",R.id.deliver_fragment);*/
            }
        });
    return v;
    }
}
