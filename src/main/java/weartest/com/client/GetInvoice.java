package weartest.com.client;

import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

import static java.util.Calendar.getInstance;


/**
 * Created by alekseyg on 08/05/2016.
 */
public class GetInvoice extends Fragment{

    DatePickerDialog datePickerDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.time_for_return, container, false);
        Calendar calendar  = getInstance();
        DatePicker picker = (DatePicker) v.findViewById(R.id.thePicker);

        picker.setMaxDate(calendar.getTimeInMillis() + (86400000 * 2));
     //   picker.setMinDate(calendar.getTimeInMillis());
       /* LinearLayout v1 = (LinearLayout)picker.getChildAt(0);
           LinearLayout v2 = (LinearLayout)v1.getChildAt(0);
            LinearLayout v3 = (LinearLayout)v2.getChildAt(0);*/
       // TextView v4 = (TextView)v3.getChildAt(1);
      //  v4.setText("TExt");
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            int daySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android");
            if (daySpinnerId != 0)
            {
                View daySpinner = picker.findViewById(daySpinnerId);
                if (daySpinner != null)
                {
                    daySpinner.setVisibility(View.GONE);
                }
            }
        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            int monthSpinnerId = Resources.getSystem().getIdentifier("month", "id", "android");
            if (monthSpinnerId != 0)
            {
                View monthSpinner = picker.findViewById(monthSpinnerId);
                if (monthSpinner != null)
                {
                    monthSpinner.setVisibility(View.GONE);
                }
            }
        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int yearSpinnerId = Resources.getSystem().getIdentifier("year", "id", "android");
            if (yearSpinnerId != 0) {
                View yearSpinner = picker.findViewById(yearSpinnerId);
                if (yearSpinner != null) {
                    yearSpinner.setVisibility(View.GONE);
                }
            }

        }

        //CalendarView vw =  picker.getCalendarView();/*

        /* datePickerDialog = new DatePickerDialog(getActivity(),  new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                if(dayOfMonth>10||dayOfMonth<6)
                {setDiaol();

                }

                Toast.makeText(getActivity(), "You must be 18 year old", Toast.LENGTH_SHORT).show();
            }} , 2016, 5, 8);
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis() + (86400000 * 2));
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis() - (86400000 * 2));
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());


        datePickerDialog.show();*/


        int k=0;
        /*try {
            Field[] f = picker.getClass().getDeclaredFields();
            for (Field field : f) {
                if (field.getName().equals("mYearPicker")) {
                    field.setAccessible(true);
                    Object yearPicker = new Object();
                    yearPicker = field.get(picker);
                    ((View) yearPicker).setVisibility(View.GONE);
                }
            }
        }
        catch (SecurityException e) {
            Log.d("ERROR", e.getMessage());
        }
        catch (IllegalArgumentException e) {
            Log.d("ERROR", e.getMessage());
        }
        catch (IllegalAccessException e) {
            Log.d("ERROR", e.getMessage());
        }*/
       /* Dialog.Builder    builder = new DatePickerDialog.Builder(){
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                DatePickerDialog dialog = (DatePickerDialog)fragment.getDialog();
                ((ViewGroup) container.getChildAt(0)).getChildAt(0).setVisibility(View.GONE);
                String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                Toast.makeText(fragment.getDialog().getContext(), "Date is " + date, Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment);
                Dialog.Builder  builder1 = new TimePickerDialog.Builder(00, 15){
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        TimePickerDialog dialog = (TimePickerDialog)fragment.getDialog();
                        Toast.makeText(fragment.getDialog().getContext(), "Time is " + dialog.getFormattedTime(SimpleDateFormat.getTimeInstance()), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), Timer.class);
                        startActivity(intent);
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        Toast.makeText(fragment.getDialog().getContext(), "Cancelled" , Toast.LENGTH_SHORT).show();
                        super.onNegativeActionClicked(fragment);
                    }
                };

                builder1.positiveAction("OK")
                        .negativeAction("CANCEL")
                        .title("Choose take time");
                DialogFragment fragment1 = DialogFragment.newInstance(builder1);
                fragment1.show(getFragmentManager(), null);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                Toast.makeText(fragment.getDialog().getContext(), "Cancelled" , Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.positiveAction("OK")
                .negativeAction("CANCEL")
                .title("Choose take time");
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getFragmentManager(), null);*/
        return v;
    }
}
