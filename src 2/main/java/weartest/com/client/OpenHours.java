package weartest.com.client;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by alekseyg on 03/02/2016.
 */
public class OpenHours extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.time_for_return, container, false);
        return v;
    }
}
