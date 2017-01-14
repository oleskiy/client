package weartest.com.client;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.rey.material.widget.RadioButton;

public class OrderFragment extends Fragment implements View.OnClickListener {
    LinearLayout washL, ironL, dryWashL;
    RadioGroup washGroup, ironGroup, dryWashGroup;
    Boolean washB = false, ironB = false, dryWashB = false;
    ImageView counterWash, counterIron1, counterIron2;
    Spinner spinerDryWash;
    Activity activity;
    RadioButton time11, time17, time21,dryNotes,dryCleaning,ironWashIron, ironIroning, ironNotes, washMany, washConditioner, washNotes;
    public static Fragment newInstance() {
        Fragment fragment = new OrderFragment();

        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_order, container, false);
        washL = (LinearLayout) v.findViewById(R.id.wash_layout);
        ironL = (LinearLayout) v.findViewById(R.id.iron_layout);
        dryWashL = (LinearLayout) v.findViewById(R.id.dry_wash_layout);

        washGroup = (RadioGroup) v.findViewById(R.id.wash_radiobuttongroupe);
        ironGroup = (RadioGroup) v.findViewById(R.id.iron_radiobuttongroupe);
        dryWashGroup = (RadioGroup) v.findViewById(R.id.dry_wash_radiobuttongroupe);

        counterWash = (ImageView) v.findViewById(R.id.counter_wash);
        counterIron1 = (ImageView) v.findViewById(R.id.counter_iron1);
        counterIron2 = (ImageView) v.findViewById(R.id.counter_iron2);
        spinerDryWash = (Spinner) v.findViewById(R.id.spiner_dry_wash);

        time11=(RadioButton)v.findViewById(R.id.time_11);
        time17=(RadioButton)v.findViewById(R.id.time_17);
        time21=(RadioButton)v.findViewById(R.id.time_21);

        dryNotes=(RadioButton)v.findViewById(R.id.dry_notes);
        dryCleaning=(RadioButton)v.findViewById(R.id.dry_clening);

        ironWashIron=(RadioButton)v.findViewById(R.id.iron_wash_iron);
        ironIroning=(RadioButton)v.findViewById(R.id.iron_ironing);
        ironNotes=(RadioButton)v.findViewById(R.id.iron_notes);

        washMany=(RadioButton)v.findViewById(R.id.wash_many);
        washConditioner=(RadioButton)v.findViewById(R.id.wash_conditioner);
        washNotes=(RadioButton)v.findViewById(R.id.wash_notes);




        washL.setOnClickListener(this);
        ironL.setOnClickListener(this);
        dryWashL.setOnClickListener(this);

        CompoundButton.OnCheckedChangeListener listenerTime = new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    time11.setChecked(time11 == buttonView);
                    time17.setChecked(time17 == buttonView);
                    time21.setChecked(time21 == buttonView);
                }

            }
        };

        CompoundButton.OnCheckedChangeListener listenerDry = new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dryNotes.setChecked(dryNotes == buttonView);
                    dryCleaning.setChecked(dryCleaning == buttonView);

                }

            }
        };

        CompoundButton.OnCheckedChangeListener listenerIron = new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ironWashIron.setChecked(ironWashIron == buttonView);
                    ironIroning.setChecked(ironIroning == buttonView);
                    ironNotes.setChecked(ironNotes == buttonView);
                }

            }
        };


        CompoundButton.OnCheckedChangeListener listenerWash = new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    washMany.setChecked(washMany == buttonView);
                    washConditioner.setChecked(washConditioner == buttonView);
                    washNotes.setChecked(washNotes == buttonView);
                }

            }
        };
        time11.setOnCheckedChangeListener(listenerTime);
        time17.setOnCheckedChangeListener( listenerTime);
        time21.setOnCheckedChangeListener(listenerTime);

        dryCleaning.setOnCheckedChangeListener( listenerDry);
        dryNotes.setOnCheckedChangeListener( listenerDry);

        ironWashIron.setOnCheckedChangeListener( listenerIron);
        ironIroning.setOnCheckedChangeListener(listenerIron);
        ironNotes.setOnCheckedChangeListener( listenerIron);

        washMany.setOnCheckedChangeListener( listenerWash);
        washConditioner.setOnCheckedChangeListener( listenerWash);
        washNotes.setOnCheckedChangeListener( listenerWash);

            return v;
    }



    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.wash_layout: if(!washB){washGroup.setVisibility(View.VISIBLE);counterWash.setVisibility(View.VISIBLE);washB=true;}else{washGroup.setVisibility(View.GONE);counterWash.setVisibility(View.INVISIBLE);washB=false;}ironGroup.setVisibility(View.GONE); dryWashGroup.setVisibility(View.GONE);counterIron1.setVisibility(View.INVISIBLE);counterIron2.setVisibility(View.INVISIBLE);spinerDryWash.setVisibility(View.INVISIBLE);break;
            case R.id.iron_layout:if(!ironB){ironGroup.setVisibility(View.VISIBLE);counterIron1.setVisibility(View.VISIBLE);counterIron2.setVisibility(View.VISIBLE);ironB=true;}else{ironGroup.setVisibility(View.GONE);counterIron1.setVisibility(View.INVISIBLE);counterIron2.setVisibility(View.INVISIBLE);ironB=false;}washGroup.setVisibility(View.GONE);dryWashGroup.setVisibility(View.GONE);counterWash.setVisibility(View.INVISIBLE);spinerDryWash.setVisibility(View.INVISIBLE);break;
            case R.id.dry_wash_layout: if(!dryWashB){dryWashGroup.setVisibility(View.VISIBLE);spinerDryWash.setVisibility(View.VISIBLE);dryWashB=true;}else{dryWashGroup.setVisibility(View.GONE);spinerDryWash.setVisibility(View.INVISIBLE);dryWashB=false;}washGroup.setVisibility(View.GONE);ironGroup.setVisibility(View.GONE);counterWash.setVisibility(View.INVISIBLE);counterIron1.setVisibility(View.INVISIBLE);counterIron2.setVisibility(View.INVISIBLE);break;
        }
    }
}
