package weartest.com.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.rey.material.widget.Button;
import com.rey.material.widget.ImageButton;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONException;

import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import weartest.com.client.delivery.Main;
import weartest.com.client.language.LanguageManager;
import weartest.com.client.language.LanguageObj;

import static android.R.layout.simple_spinner_item;

/**
 * Created by alekseyg on 18/01/2016.
 */
public class Profile extends Fragment {
    EditText firstName, lastName, mail, country,  password, confirmPassword, countryCode, phoneArea, city, creditCard,HandcreditCard;
    TextView phone,fNTitle, lNTitle,mailTitel,phoneNumber,credit_card_privacy;
    TextView userId;
   // Button next;
    ImageButton cardIO;
    LinearLayout llFN,llFNIl,llLN,llLNIl,llMail,llMailIl,llPhone,llPhoneIl;
    public static  android.os.Handler handOs;
    int MY_SCAN_REQUEST_CODE=5;
    final static public String LOG_TAG = "profile";
    static Fragment fragment;

    public static Handler hamdler;
    public static Fragment newInstance(){
        if(fragment==null)
         fragment = new Profile();

        return fragment;
    }

    public void onResume() {
        super.onResume();
        try {
            setNewLang(ValuesAndPreferencesManager.getLangId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    View v;
    LayoutInflater Minflater;
    ViewGroup Mcontainer;
    void chanLay(int position)
    {

        llFN.removeAllViews();
        llLN.removeAllViews();
        llMail.removeAllViews();
        llPhone.removeAllViews();

        if(llFN!=null&&llMail!=null&&llPhone!=null)
        if(ValuesAndPreferencesManager.getLangId()==1)
        {


            llFN.addView(getActivity().getLayoutInflater().inflate(R.layout.first_name_il, null));
            llLN.addView(getActivity().getLayoutInflater().inflate(R.layout.last_name_il, null));
            llMail.addView(getActivity().getLayoutInflater().inflate(R.layout.mail_il, null));
            llPhone.addView(getActivity().getLayoutInflater().inflate(R.layout.phone_number_il, null));
         //   next.setBackgroundResource(R.drawable.next_btn_il);


        }
        else
        {
            llFN.addView(getActivity().getLayoutInflater().inflate(R.layout.first_name, null));
            llLN.addView(getActivity().getLayoutInflater().inflate(R.layout.last_name, null));
            llMail.addView(getActivity().getLayoutInflater().inflate(R.layout.mail, null));
            llPhone.addView(getActivity().getLayoutInflater().inflate(R.layout.phone_number, null));
           // next.setBackgroundResource(R.drawable.button_next);
        }

        phone = (TextView)llPhone.findViewById(R.id.phoneNumber1);
        phoneNumber = (TextView)llPhone.findViewById(R.id.phoneNumber);

        fNTitle = (TextView)llFN.findViewById(R.id.f_name_title);
        lNTitle = (TextView)llLN.findViewById(R.id.l_name_title);
        mailTitel =(TextView)llMail.findViewById(R.id.mail_title);

        fNTitle.setText(LanguageManager.getLanguageStringValue(LanguageManager.FIRST_NAME)+":");
        lNTitle.setText(LanguageManager.getLanguageStringValue(LanguageManager.LAST_NAME)+":");
        mailTitel.setText(LanguageManager.getLanguageStringValue(LanguageManager.MAIL)+":");

        Set<String> ss = ValuesAndPreferencesManager.getCountry();
        String[]ar = new String[ss.size()];
        ss.toArray(ar);

          firstName = (EditText)llFN.findViewById(R.id.firstNameET1);
         lastName = (EditText)llLN.findViewById(R.id.lastNameET1);
          mail = (EditText)llMail.findViewById(R.id.emailET1);
        phoneNumber = (TextView)llPhone.findViewById(R.id.phoneNumber);
        credit_card_privacy.setText(LanguageManager.getLanguageStringValue(LanguageManager.CREDIT_CARD_PRIVACY));
        //country = (EditText)v.findViewById(R.id.countryET1);
        //city = (EditText)v.findViewById(R.id.cityET1);

        firstName.setText(ValuesAndPreferencesManager.gertName());
        lastName.setText(ValuesAndPreferencesManager.getLastName());
        mail.setText(ValuesAndPreferencesManager.getMail());

        phone.setText(LanguageManager.getLanguageStringValue(LanguageManager.PHONE)+": ");
        phoneNumber.setText(ar[0] + ar[1]);
        Log.d("test", ss.toArray().toString());
    }
    void changeUILang( int position)
    {
        llFN = (LinearLayout)v.findViewById(R.id.ll_first_name);
       // llFNIl = (LinearLayout)v.findViewById(R.id.ll_first_name_il);

        llLN = (LinearLayout)v.findViewById(R.id.ll_last_name);
       // llLNIl = (LinearLayout)v.findViewById(R.id.ll_last_name_il);

        llMail = (LinearLayout)v.findViewById(R.id.ll_mail);
       // llMailIl = (LinearLayout)v.findViewById(R.id.ll_mail_il);

        llPhone = (LinearLayout)v.findViewById(R.id.ll_phone);
     //   llPhoneIl = (LinearLayout)v.findViewById(R.id.ll_phone_il);
      //  Mcontainer.removeAllViews();
       // next = (Button)v.findViewById(R.id.btn_profile);
        chanLay(position);
        initUI();
      //  if(position!=1)
     ////   v = /*getActivity().getLayoutInflater().inflate(R.layout.profile, null);*/Minflater.inflate(R.layout.profile, Mcontainer, false);
     //   else v = /*getActivity().getLayoutInflater().inflate(R.layout.profile_il, null);*/Minflater.inflate(R.layout.profile_il, Mcontainer, false);
     //   Mcontainer.addView(v);
        //v = Minflater.inflate(R.layout.profile, Mcontainer, false);




    }
    void initUI()
    {
      //  firstName = (EditText)llFN.findViewById(R.id.firstNameET1);
      //  lastName = (EditText)llLN.findViewById(R.id.lastNameET1);
      //  mail = (EditText)llMail.findViewById(R.id.emailET1);
        //country = (EditText)v.findViewById(R.id.countryET1);
        //city = (EditText)v.findViewById(R.id.cityET1);
      //  phone = (TextView)llPhone.findViewById(R.id.phoneNumber1);
      //  phoneNumber = (TextView)llPhone.findViewById(R.id.phoneNumber);
      //  fNTitle = (TextView)llFN.findViewById(R.id.f_name_title);
      //  lNTitle = (TextView)llLN.findViewById(R.id.l_name_title);
      //  mailTitel =(TextView)llMail.findViewById(R.id.mail_title);

      //  fNTitle.setText(LanguageManager.getLanguageStringValue(LanguageManager.FIRST_NAME));
     //   lNTitle.setText(LanguageManager.getLanguageStringValue(LanguageManager.LAST_NAME));
     //   mailTitel.setText(LanguageManager.getLanguageStringValue(LanguageManager.MAIL));




        cardIO = (ImageButton)v.findViewById(R.id.credit_scan);
        creditCard = (EditText)v.findViewById(R.id.credit_card);
        HandcreditCard = (EditText)v.findViewById(R.id.hand_credit_card);
        creditCard.setHint(LanguageManager.getLanguageStringValue(LanguageManager.CREDIT_CARD_NUMBER));
        HandcreditCard.setText(LanguageManager.getLanguageStringValue(LanguageManager.TYPE_CREDIT_CARD) + ":");
        if(getActivity().getClass().getName().equals("weartest.com.client.Order"))
        {
            Order.handOs.sendEmptyMessage(0);
        }
       else  MainActivity.handOs.sendEmptyMessage(0);

        /*next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });*/
        cardIO.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          sendRequestCard();
                                      }
                                  }
        );


        LanguageAdapter adapter1 = new LanguageAdapter();
        Spinner spinner = (Spinner)v.findViewById(R.id.spinner_language);
        spinner.setAdapter(adapter1);
        spinner.setSelection(ValuesAndPreferencesManager.getLangId());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                try {
                    setNewLang(position);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
LinearLayout profileLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Minflater = inflater;
        Mcontainer = container;
         v = inflater.inflate(R.layout.profile, container, false);
        profileLayout = (LinearLayout)v.findViewById(R.id.profile_layout);
        credit_card_privacy = (TextView)v.findViewById(R.id.credit_card_privacy);
      //  profileLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
if(ValuesAndPreferencesManager.getLangId()==1)
    changeUILang(1);
        else changeUILang(0);
        hamdler = new java.util.logging.Handler() {
            @Override
            public void close() {

            }

            @Override
            public void flush() {
                chanLay(ValuesAndPreferencesManager.getLangId());
                // MainActivity.menuHandler.flush();
//if(ValuesAndPreferencesManager.getLangId()==1) changeUILang(1);
                // v=getActivity().getLayoutInflater().inflate(R.layout.profile_il, null);
                //     else changeUILang(0);//getActivity().getLayoutInflater().inflate(R.layout.profile, null);
                fNTitle.setText(LanguageManager.getLanguageStringValue(LanguageManager.FIRST_NAME)+":");
                lNTitle.setText(LanguageManager.getLanguageStringValue(LanguageManager.LAST_NAME)+":");
                mailTitel.setText(LanguageManager.getLanguageStringValue(LanguageManager.MAIL)+":");
                //phone.setText(LanguageManager.getLanguageStringValue(LanguageManager.PHONE)+": " + ar[0] + ar[1]);
                creditCard.setText(LanguageManager.getLanguageStringValue(LanguageManager.CREDIT_CARD_NUMBER)+":");
                HandcreditCard.setText(LanguageManager.getLanguageStringValue(LanguageManager.TYPE_CREDIT_CARD)+":");
                phone.setText(LanguageManager.getLanguageStringValue(LanguageManager.PHONE) + ": ");
                phoneNumber = (TextView)llPhone.findViewById(R.id.phoneNumber);
                credit_card_privacy.setText(LanguageManager.getLanguageStringValue(LanguageManager.CREDIT_CARD_PRIVACY));
                    /*if(Order.handlerMenu!=null)        Order.handlerMenu.flush();
                    if(Wash.hand!=null)Wash.hand.flush();
                    if(DryWashFragment.hand!=null)DryWashFragment.hand.flush();
                    if(CarpetBlanket.hand!=null)CarpetBlanket.hand.flush();
                    if(IronFragment.handler!=null)IronFragment.handler.flush();*/
            }

            @Override
            public void publish(LogRecord record) {

            }
        };


        handOs = new android.os.Handler()
        {
            public void handleMessage(android.os.Message msg) {
                if(msg.what==1)
                {
                   sendRequest();
                }
                else
                {

                }
            }
        };

        return v;
    }
    String[] data = {"English", "Israel"};
    int []images = {R.drawable.uk,R.drawable.he};
    class LanguageAdapter extends BaseAdapter {
        class ViewHolder
        {
            TextView txt;
            ImageView img;
        }


        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int position) {
            return data[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;
            ViewHolder vv = new ViewHolder();
            if(v == null) {
                v = LayoutInflater.from(getActivity()).inflate(R.layout.lang_item, null);
                if(vv!=null){
                    // vv.img = (ImageView)v.findViewById(R.id.img_item_menu);
                    vv.txt = (TextView)v.findViewById(R.id.country_name);
                    vv.img = (ImageView)v.findViewById(R.id.country_flag);
                }
                //v.setOnClickListener(this);

            }

            // v.setTag(position);
            //Tab tab = (Tab)getItem(position);
            //((TextView)v).setText("65");

         /*   if(tab == mSelectedTab) {
                v.setBackgroundColor(ThemeUtil.colorPrimary(MainActivity.this, 0));
                ((TextView)v).setTextColor(0xFFFFFFFF);
                ((TextView)v).setText("54321");
            }
            else {*/
            v.setBackgroundResource(0);
            if(vv!=null&&vv.txt!=null) {
                /*vv.txt.setTextColor(0xFF000000);
                vv.txt.setText(data[position]);*/
                vv.img.setImageResource(images[position]);
                //    vv.img.setImageResource(images[position]);
            }
            //  }

            return v;
        }

       /* @Override
        public void onClick(View v) {
            int position = (Integer)v.getTag();
            vp.setCurrentItem(position);
            dl_navigator.closeDrawer(fl_drawer);
        }*/
    }
    void setNewLang(int position) throws JSONException {
        LanguageObj obj = LanguageObj.getLanguageObjectInstance();
        ValuesAndPreferencesManager.saveLangId(position);
        if(position==1){
        obj.setNewLang(2);}
            else
            {
                obj.initDefaultLanguage();
            }
        if(MainActivity.handler!=null)MainActivity.handler.flush();

       /* MainActivity.menuHandler.flush();
        fNTitle.setText(LanguageManager.getLanguageStringValue(LanguageManager.FIRST_NAME)+":");
        lNTitle.setText(LanguageManager.getLanguageStringValue(LanguageManager.LAST_NAME)+":");
        mailTitel.setText(LanguageManager.getLanguageStringValue(LanguageManager.MAIL)+":");
        //phone.setText(LanguageManager.getLanguageStringValue(LanguageManager.PHONE)+": " + ar[0] + ar[1]);
        creditCard.setText(LanguageManager.getLanguageStringValue(LanguageManager.CREDIT_CARD_NUMBER)+":");
        phone.setText(LanguageManager.getLanguageStringValue(LanguageManager.PHONE) + ": ");

        if(Order.handlerMenu!=null)        Order.handlerMenu.flush();
        if(Wash.hand!=null)Wash.hand.flush();
        if(DryWashFragment.hand!=null)DryWashFragment.hand.flush();
        if(CarpetBlanket.hand!=null)CarpetBlanket.hand.flush();
        if(IronFragment.handler!=null)IronFragment.handler.flush();*/

    }
      public void sendRequest()
    {
        int id = 0;
        if(ValuesAndPreferencesManager.gertUserID()!=null) {
            User user = new User(Integer.parseInt(ValuesAndPreferencesManager.gertUserID()), firstName.getText().toString(), lastName.getText().toString(), mail.getText().toString(), "Israel", "Tesl Aviv");
            RestAdapter adapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)

                    .setClient(new OkClient(new OkHttpClient()))
                    .setEndpoint("https://ta-kvisa.com/api/accounts") //Setting the Root URL
                    .build(); //Finally building the adapter

            //Creating object for our interface
            RegisterAPI api = adapter.create(RegisterAPI.class);
            api.updateUser(user, new Callback<User>() {
                @Override
                public void success(User user, Response response) {
                    Log.d("TEst", "User = " + user.toString() + " Response " + response.toString());
                    ValuesAndPreferencesManager.saveName(user.getFirstName());
                    ValuesAndPreferencesManager.saveLastName(user.getLastName());
                    ValuesAndPreferencesManager.savemail(user.getEmail());
                    Fragment list = new ChooseLockerFragment().newInstance();

                    getActivity().getClass().getName();
                    if (getActivity().getClass().getName().equals("weartest.com.client.Order")) {
                        //getActivity().onBackPressed();
                        getActivity().getSupportFragmentManager().popBackStack();
                        // Order.handlerMenu.flush();
                    } else {
                        //getActivity().getSupportFragmentManager().popBackStack();
                        MyFragmentTransaction.StartFragmentTransaction(list, getActivity(), "list", R.id.login_fragment11);
                        /*FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        fragmentTransaction.replace(R.id.login_fragment11, list);
                        fragmentTransaction.addToBackStack(null);

                        fragmentTransaction.commit();*/
                        if (MainActivity.handler != null) {

                            MainActivity.handler.flush();
                            MainActivity.profileFlag = true;
                        }
                    }
               /* else if(user.getUserID()==-9999)
                {
                    Log.d("TEst","Code is not correct");
                    Intent intent = new Intent(getActivity(), Deliver.class);
                    startActivity(intent);
                }
                if(user.getUserID()!=0&&user.getUserID()!=-9999&user.getFirstName()==null)
                {
                    *//*ValuesAndPreferencesManager.saveId(String.valueOf(user.getUserID()));
                    Fragment fragment = new Profile().newInstance();
                    MyFragmentTransaction.StartFragmentTransaction(fragment,getActivity(),"Login3");*//*
                    Intent intent = new Intent(getActivity(), Deliver.class);
                    startActivity(intent);

                }*/
                    if (mActivity.getClass().getName().equals("weartest.com.client.Order")) {
                        Order.handOs.sendEmptyMessage(1);
                    } else MainActivity.handOs.sendEmptyMessage(0);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("TEst", "Error " + error.toString());
                }
            });
        }
    }
Activity mActivity;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }


void sendRequestCard()
{
    Intent scanIntent = new Intent(getContext(), CardIOActivity.class);

    // customize these values to suit your needs.
    scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
    scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true); // default: false
    scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false
    scanIntent.putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE, false);
    scanIntent.putExtra(CardIOActivity.EXTRA_HIDE_CARDIO_LOGO, true);
    scanIntent.putExtra(CardIOActivity.EXTRA_USE_PAYPAL_ACTIONBAR_ICON, false);
    scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO, true);
    // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
    startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_SCAN_REQUEST_CODE) {
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                resultDisplayStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";
                creditCard.setText(scanResult.getRedactedCardNumber());
                HandcreditCard.setText(scanResult.getRedactedCardNumber());
                // Do something with the raw number, e.g.:
                // myService.setCardNumber( scanResult.cardNumber );

                if (scanResult.isExpiryValid()) {
                    resultDisplayStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
                }

                if (scanResult.cvv != null) {
                    // Never log or display a CVV
                    resultDisplayStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
                }

                if (scanResult.postalCode != null) {
                    resultDisplayStr += "Postal Code: " + scanResult.postalCode + "\n";
                }
            }
            else {
                resultDisplayStr = "Scan was canceled.";
            }
            // do something with resultDisplayStr, maybe display it in a textView
            // resultTextView.setText(resultDisplayStr);
        }
        // else handle other activity results
    }
}
