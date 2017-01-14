package weartest.com.client.language;

import java.util.HashMap;

/**
 * Created by alekseyg on 20/12/2015.
 */
public class LanguageObj {
    private static HashMap<String, String> language=null;

    static String name = "name";
    static String logIn="LogIn";
    static String howItsWork="How its work";
    static String locker="Locker";
    static String wash="Wash";
    static String dryWash="Dry & Wash";
    static String iron="Iron";
    static String notes="Notes";
    static String moreConditioner="more conditioner";
    static String washAndIroning="Wash & Ironing";
    static String dryCleaning="dry cleaning";
    static String choeseTimeReturnWash="Choese time return wash";
    static String invoice="Invoice";

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


    private static void initDefaultLanguage() {
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
    }







}
