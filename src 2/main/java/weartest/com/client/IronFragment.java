package weartest.com.client;

/**
 * Created by alekseyg on 28/01/2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rey.material.widget.Button;

public class IronFragment extends Fragment {
Button next, back;
    com.rey.material.widget.FloatingActionButton pl2, min2,pl1, min1;
        LinearLayout counterLay, commentsLay;
        TextView title, count1, count2;
@Override
public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.iron_fragment, container, false);
        min2 = (com.rey.material.widget.FloatingActionButton)v.findViewById(R.id.amountBtMinus2);
        pl2 = (com.rey.material.widget.FloatingActionButton)v.findViewById(R.id.amountBtPlus2);

        min1 = (com.rey.material.widget.FloatingActionButton)v.findViewById(R.id.amountBtMinus1);
        pl1 = (com.rey.material.widget.FloatingActionButton)v.findViewById(R.id.amountBtPlus1);
        count1 = (TextView)v.findViewById(R.id.amountView1);
        count2 = (TextView)v.findViewById(R.id.amountView2);
        counterLay = (LinearLayout)v.findViewById(R.id.count_layout_iron);
        commentsLay = (LinearLayout)v.findViewById(R.id.comments_layout);
        title = (TextView)v.findViewById(R.id.title_iron);
       /* next = (Button)v.findViewById(R.id.next_step_iron);
        back = (Button)v.findViewById(R.id.back_step_iron);*/
        View.OnClickListener listenerPl=null;

        listenerPl = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                       setCount(v);
                }
        };


        pl1.setOnClickListener(listenerPl);
        pl2.setOnClickListener(listenerPl);
        min1.setOnClickListener(listenerPl);
        min2.setOnClickListener(listenerPl);
       /* next.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(counterLay.getVisibility()==View.VISIBLE)
            {
                counterLay.setVisibility(View.GONE);
                commentsLay.setVisibility(View.VISIBLE);
                title.setText("Comments");
            }
        }
    });*/

        return v;
}

        public void setCount(View v)
        {

                switch (v.getId())
                {
                        case R.id.amountBtPlus1: count1.setText(String.valueOf(Integer.parseInt(count1.getText().toString())+1));break;
                        case R.id.amountBtMinus1: if(count1.getText().toString().equals("0"))  count1.setText("0"); else count1.setText(String.valueOf(Integer.parseInt(count1.getText().toString())-1));break;
                        case R.id.amountBtPlus2: count2.setText(String.valueOf(Integer.parseInt(count2.getText().toString())+1));break;
                        case R.id.amountBtMinus2: if(count1.getText().toString().equals("0"))count2.setText("0"); else count2.setText(String.valueOf(Integer.parseInt(count2.getText().toString())-1));break;
                }


        }
}
