package weartest.com.client;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Handler.Callback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by alekseyg on 26/01/2016.
 */
public class Iron extends Fragment implements Callback {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_activity, container, false);
        return v;
    }
    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }
}
