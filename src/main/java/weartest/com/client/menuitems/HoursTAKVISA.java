package weartest.com.client.menuitems;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import weartest.com.client.R;

/**
 * Created by oleskiy on 07.08.16.
 */
public class HoursTAKVISA extends Fragment {
    String str = "Hours TA-KVISA -\n" +
            "\n" +
            "• Station Arlozorov 62 Tel.\n" +
            "\n" +
            "Sunday to Thursday\n" +
            "\n" +
            "• 6:00 to 24:00\n" +
            "\n" +
            "Friday\n" +
            "\n" +
            "• 7:00 to 19:00\n" +
            "\n" +
            "Saturday\n" +
            "\n" +
            "• 8:30 to 22:00\n" +
            "\n" +
            "Time of pickup and return of items to the lockers or\n" +
            "\n" +
            "destination are:\n" +
            "\n" +
            "Sunday to Thursday\n" +
            "\n" +
            "• 11:00\n" +
            "\n" +
            "• 19:00\n" +
            "\n" +
            "• 24:00\n" +
            "\n" +
            "Friday\n" +
            "\n" +
            "• 10:00\n" +
            "\n" +
            "• 15:00\n" +
            "\n" +
            "Saturday\n" +
            "\n" +
            "• 12:00\n" +
            "\n" +
            "• 24:00\n" +
            "\n" +
            "When all lockers are occupied, we make them\n" +
            "\n" +
            "available in an hour Every day until 21:00 on Friday\n" +
            "\n" +
            "until 15:00.";
    Activity activity;
    public static Fragment newInstance(){
        Fragment fragment = new HoursTAKVISA();

        return fragment;
    }
    final static public String LOG_TAG = "HoursTAKVISA";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.menu_item, container, false);
        TextView txt = (TextView)v.findViewById(R.id.menu_item);
        txt.setText(str);
        return v;
    }

}
