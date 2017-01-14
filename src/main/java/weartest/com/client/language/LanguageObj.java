package weartest.com.client.language;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import weartest.com.client.CarpetBlanket;
import weartest.com.client.ChooseLockerFragment;
import weartest.com.client.DryWashFragment;
import weartest.com.client.IronFragment;
import weartest.com.client.LoginFragment1;
import weartest.com.client.MainActivity;
import weartest.com.client.Order;
import weartest.com.client.Profile;
import weartest.com.client.RegisterAPI;
import weartest.com.client.ValuesAndPreferencesManager;
import weartest.com.client.Wash;
import weartest.com.client.stringss;

/**
 * Created by alekseyg on 20/12/2015.
 */
public class LanguageObj {
    public static HashMap<String, String> language=null;

    static String name = "name";
    static String logIn="LogIn";
    static String logOut="LogOut";
    static String howItsWork="How its work";
    static String locker="Locker";
    static String wash="Wash";
    static String dryWash="DryCleaning";
    static String iron="Iron";
    static String notes="Notes";
    static String moreConditioner="more conditioner";
    static String washAndIroning="Wash & Ironing";
    static String dryCleaning="dry cleaning";
    static String choeseTimeReturnWash="Choese time return wash";
    static String invoice="Invoice";
    static String country = "Country";
    static String countries = "Countries";
    static String phone_number = "Phone number";
    static String your_country = "Your country";
    static String ok = "Ok";
    static String cancel = "Cancel";
    static  String Please_enter_you_phone_number = "Please, enter you phone number";
    static  String next = "Next";
    static  String privacy = "Privacy";
    static String price = "prices";
    static  String Terms_of_Service = "How it's work";
    static String wait = "Please wait";
    static String we_sent_an_SMS_with_an_activation_code_to_your_phone = "We sent SMSwith activation code to your phone";
    static String code = "Code";
    static String wrong_number = "Wrong number";
    static String first_name = "First Name";
    static String last_name = "Last Name";
    static  String mail = "Mail";
    static String phone = "Phone";
    static String credit_card_number = "Credit card number";
    static  String km = "Km";
    static String Available_lockers = "Available lockers";
    static String choose_count = "How many Lockers?";
    static String block_locker = "Save it";
    static String If_you_choose_more_one_locker_you_need_pay_40_nis_per_locker = "If you choose more one locker, you need pay 40 nis per locker";
    static  String complite_order = "available space";
    static  String count_bugs = "laundry bags";
    static String more_conditioner = "More conditioner";
    static String more_spots = "More Spots";
    static String type_your_comments_here = "Type your comments here";
    static String dry_wash = "Dry Wash";
    static  String capter_blanket = "Carpet blanket";
    static  String getInvoice = "Add to order";
    static  String profile = "Profile";
    static  String code_not_correct = "Code not correct";
    static  String scan_door = "Scan Door";
    static  String according_price = "The price will be according to the weight of the laundry";
    static  String separate_bags = "Please place in separate bags items for laundry and ironing";
    static  String text_choos_locker = "Hello, choose locker";
    static  String enterCode   = "Enter Code";
    static  String selectLanguage   = "Select language";
    static  String typeCreditCartNumber     = "Type credit Card number";
    static  String scanCreditCard      = "Scan credit card";
    static  String creditCardPrivacy       = "Credit card Privacy";
    static  String workHours         = "Work Hours";
    static  String orderNumber           = "Order Number";
    static  String lockerLocation             = "Locker Location";
    static  String lockerNumber                = "Locker Number";
    static  String date                  = "Date";
    static  String orderType                    = "Order type";
    static  String bagStickers                    = "Bag Sticker";
    static  String putSticker                     = "Put Sticker";




    static LanguageObj languageObj;


    static public String langURL = "en";
    public static LanguageObj getLanguageObjectInstance() {
        if(languageObj==null){
            languageObj = new LanguageObj();
        languageObj.initDefaultLanguage();
        }
        return languageObj;
    }


    public static String getLanguageValue(String key) {
        if(language==null){
        initDefaultLanguage();
        }
        String value = language.get(key);
        if(value==null||value.equals(""))
        {
            return key;
        }

        return value;
    }


    public static void initDefaultLanguage() {
        language = new HashMap<String, String>();

        language.put(LanguageManager.NAME, name);
        language.put(LanguageManager.LOG_IN, logIn);
        language.put(LanguageManager.HOW_ITS_WORK, howItsWork);
        language.put(LanguageManager.LOCKER, locker);
        language.put(LanguageManager.WASH, wash);
        language.put(LanguageManager.DRY_WASH, dryWash);
        language.put(LanguageManager.IRON, iron);
        language.put(LanguageManager.NOTES, notes);
        language.put(LanguageManager.MORE_CONDITIONER, moreConditioner);
        language.put(LanguageManager.WASH_AND_IRON, washAndIroning);
        language.put(LanguageManager.DRY_CLINING, dryCleaning);
        language.put(LanguageManager.CHOESE_TIME_RETURN_WASH, choeseTimeReturnWash);
        language.put(LanguageManager.INVOICE, invoice);
        language.put(LanguageManager.COUNTRY,country );
        language.put(LanguageManager.COUNTRIES,countries );
        language.put(LanguageManager.PHONE_NUMBER,phone_number );
        language.put(LanguageManager.YOUR_COUNTRY, your_country );
        language.put(LanguageManager.OK, ok );
        language.put(LanguageManager.CANCEL, cancel );
        language.put(LanguageManager.PLEASE_ENTER_YOUR_PHONE_NUMBER,  Please_enter_you_phone_number );
        language.put(LanguageManager.NEXT,  next );
        language.put(LanguageManager.PRIVACY,privacy );
        language.put(LanguageManager.PRICE, price );
        language.put(LanguageManager.TERMS_OF_SERVICE, Terms_of_Service );
        language.put(LanguageManager.WAIT,  wait );
        language.put(LanguageManager.WE_SENT_SMS,  we_sent_an_SMS_with_an_activation_code_to_your_phone );
        language.put(LanguageManager.CODE, code );
        language.put(LanguageManager.WRONG_NUMBER,  wrong_number );
        language.put(LanguageManager.FIRST_NAME, first_name );
        language.put(LanguageManager.LAST_NAME,  last_name);
        language.put(LanguageManager.MAIL, mail );
        language.put(LanguageManager.PHONE, phone );
        language.put(LanguageManager.CREDIT_CARD_NUMBER, credit_card_number);
        language.put(LanguageManager.KM, km );
        language.put(LanguageManager.AVAILEBLE_LOCKERS, Available_lockers );
        language.put(LanguageManager.CHOOSE_COUNT,  choose_count );
        language.put(LanguageManager.BLOCK_LOCKER,  block_locker );
        language.put(LanguageManager.MORE_ONE_LOCKERS,  If_you_choose_more_one_locker_you_need_pay_40_nis_per_locker);
        language.put(LanguageManager.COMPLITE_ORDER, complite_order );
        language.put(LanguageManager.COUNT_BUGS, count_bugs);
        language.put(LanguageManager.MORE_CONDITIONER, more_conditioner );
        language.put(LanguageManager.MORE_SPOTS,  more_spots );
        language.put(LanguageManager.TYPE_YOU_COMMENTS_HERE,   type_your_comments_here );

        language.put(LanguageManager.CAPTER_BLANKET,   capter_blanket);
        language.put(LanguageManager.GET_IMVOICE,   getInvoice );
        language.put(LanguageManager.PROFILE,   profile );
        language.put(LanguageManager.CODE_NOT_CORRECT,   code_not_correct );
        language.put(LanguageManager.LOG_OUT,   logOut );
        language.put(LanguageManager.SCAN_DOOR,   scan_door );
        language.put(LanguageManager.ACCORDING_PRICE,   according_price );
        language.put(LanguageManager.SEPARATE_BAGS,   separate_bags );
        language.put(LanguageManager.TEXT_CHOOSE_LOCKER,   text_choos_locker );


        language.put(LanguageManager.ENTER_CODE,enterCode);
        language.put(LanguageManager.SELECT_LANGUAGE,selectLanguage);
        language.put(LanguageManager.TYPE_CREDIT_CARD,typeCreditCartNumber);
        language.put(LanguageManager.SCAN_CREDIT_CARD, scanCreditCard);
        language.put(LanguageManager.CREDIT_CARD_PRIVACY,creditCardPrivacy);
        language.put(LanguageManager.WORK_HOURS, workHours);
        language.put(LanguageManager.ORDER_NUMBER, orderNumber);
        language.put(LanguageManager.LOCKER_LOCATION, lockerLocation);
        language.put(LanguageManager.LOCKER_NUMBER, lockerNumber);
        language.put(LanguageManager.DATE, date);
        language.put(LanguageManager.ORDER_TYPE, orderType);
        language.put(LanguageManager.BAG_STYCKER,bagStickers);
        language.put(LanguageManager.PUT_STICKER, putSticker);



        updateUI();
    }


    public boolean isContainsMapping(String key) {
        return language.containsKey(key);
    }
    public String getLanguageMapping(String key) {
        return language.get(key);
    }

public void setNewLang(int lang) throws JSONException {

    if(lang == 2&& language.get(LanguageManager.NAME).equals(LanguageManager.NAME)/*ValuesAndPreferencesManager.getLangId()!=1*/){
    RestAdapter adapter = new RestAdapter.Builder()
            .setLogLevel(RestAdapter.LogLevel.FULL)

            .setClient(new OkClient(new OkHttpClient()))
            .setEndpoint("https://ta-kvisa.com/api/common") //Setting the Root URL
            .build(); //Finally building the adapter
    RegisterAPI api = adapter.create(RegisterAPI.class);

    api.getLanguage(new Callback<ArrayList<GtObject>>() {
        @Override
        public void success(ArrayList<GtObject> s, Response response) {
            Log.d("test",  " " + response.toString());
            Log.d("test",  " " + s.toString());
            if (response.getStatus() == 200) {

                for(int i=0;i<s.size();i++){
                language.put(s.get(i).getKey(),s.get(i).getValue());
                }

                updateUI();
                langURL = "he";
            }
        }

        @Override
        public void failure(RetrofitError error) {
            Log.d("TEst", "Error " + error.toString());
        }
    });

    }

    else if(lang == 1)
    {
        initDefaultLanguage();
        updateUI();

        langURL = "en";

    }

    //JSONObject obj = new JSONObject();
   // obj=    setJson();

   //if(obj!=null) {

     //  Iterator<String> keys = obj.keys();

       //while( keys.hasNext() ) {
         //  String str = (String)keys.next();
  //  for(int i=0;i<array.length();i++)

   //            language.put(array.getJSONObject(i).getString("Key"), array.getJSONObject(i).getString("Value"));
       //}
  // }
}

static void updateUI()
{
    if(Order.handlerMenu!=null)        Order.handlerMenu.flush();
    if(Wash.hand!=null)Wash.hand.flush();
    if(DryWashFragment.hand!=null)DryWashFragment.hand.flush();
    if(CarpetBlanket.hand!=null)CarpetBlanket.hand.flush();
    if(IronFragment.handler!=null)IronFragment.handler.flush();
    if(Profile.hamdler!=null)Profile.hamdler.flush();
    if(ChooseLockerFragment.handler!=null)ChooseLockerFragment.handler.flush();
    if(MainActivity.menuHandler!=null)MainActivity.menuHandler.flush();
    if(LoginFragment1.handlerl!=null)
    {
        LoginFragment1.handlerl.flush();
    }
}
    JSONObject setJson() throws JSONException {
        JSONObject obj = new JSONObject();
        String str = stringss.str;

        String[] ss=  str.split(",");

        for(int i=0;i<ss.length-1;i++)
        {
            String[]ar1 = ss[i].split(":");

            if(ar1.length!=2)
            {
                Log.d("Test", ar1[0]);
            }
            else {Log.d("Test", ar1[0] + " " + ar1[1]);
                obj.putOpt(ar1[0], ar1[1]);}
        }
        Log.d("Test", obj.toString());
        return obj;
    }

public static class GtObject
{ @SerializedName("Key")
    String key;
    @SerializedName("Value")
    String value;

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}

}
