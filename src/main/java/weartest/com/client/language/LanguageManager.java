package weartest.com.client.language;

import android.content.Context;

/**
 * Created by alekseyg on 20/12/2015.
 */
public class LanguageManager {
    public static final String NAME = "name";
    public static final String LOG_IN="logIn";
    public static final String LOG_OUT="LogOut";
    public static final String HOW_ITS_WORK="howItsWork";
    public static final String LOCKER="locker";
    public static final String WASH="wash";
    public static final String DRY_WASH="dryWash";
    public static final String IRON="iron";
    public static final String NOTES="notes";
    public static final String MORE_CONDITIONER="moreConditioner";
    public static final String WASH_AND_IRON="washAndIroning";
    public static final String DRY_CLINING="dryCleaning";
    public static final String CHOESE_TIME_RETURN_WASH="choeseTimeReturnWash";
    public static final String INVOICE="invoice";
    public static final String COUNTRY = "country";
    public static final String COUNTRIES = "countries";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String YOUR_COUNTRY = "yourCountry";
    public static final String OK = "ok";
    public static final String CANCEL = "cancel";
    public static final String PLEASE_ENTER_YOUR_PHONE_NUMBER = "pleaseEnterYouPhoneNumber";
    public static final String NEXT = "next";
    public static final String PRIVACY = "Privacy";
    public static final String PRICE = "price";
    public static final String TERMS_OF_SERVICE = "termsOfService";
    public static final String WAIT = "wait";
    public static final String WE_SENT_SMS = "weSentSmsWithActivationCodeToYourPhone";
    public static final String CODE = "code";
    public static final String WRONG_NUMBER = "wrongNumber";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String MAIL = "mail";
    public static final String PHONE = "phone";
    public static final String CREDIT_CARD_NUMBER = "creditCardNumber";
    public static final String KM = "km";
    public static final String AVAILEBLE_LOCKERS = "availableLockers";
    public static final String CHOOSE_COUNT = "chooseCount";
    public static final String BLOCK_LOCKER = "blockLocker";
    public static final String MORE_ONE_LOCKERS = "ifYouChooseMoreOneLockerYouNeedPayPerLocker";
    public static final String COMPLITE_ORDER = "compliteOrder";
    public static final String COUNT_BUGS = "countOfBag";
    public static final String MORE_SPOTS = "moreSpots";
    public static final String TYPE_YOU_COMMENTS_HERE = "Type your comments here";
    public static final String CAPTER_BLANKET = "capterBlanket";
    public static final String GET_IMVOICE = "getInvoice";
    public static final String PROFILE = "Profile";
    public static final String CODE_NOT_CORRECT = "codeNotCorrect";
    public static final String SCAN_DOOR = "scanDoor";
    public static final String ACCORDING_PRICE = "accordingPrice";
    public static final String SEPARATE_BAGS = "separateBags";
    public static final String TEXT_CHOOSE_LOCKER = "chooseLockerText";

    public static final  String ENTER_CODE   = "Enter Code";
    public static final  String SELECT_LANGUAGE   = "Select language";
    public static final  String TYPE_CREDIT_CARD     = "Type credit Card number";
    public static final  String SCAN_CREDIT_CARD      = "Scan credit card";
    public static final  String CREDIT_CARD_PRIVACY       = "CreditcardPrivacy";
    public static final  String WORK_HOURS         = "WorkHours";
    public static final  String ORDER_NUMBER           = "OrderNumber";
    public static final  String LOCKER_LOCATION             = "LockerLocation";
    public static final  String LOCKER_NUMBER                = "LockerNumber";
    public static final  String DATE                  = "Date";
    public static final  String ORDER_TYPE                    = "Ordertype";
    public static final  String BAG_STYCKER                    = "BagSticker";
    public static final  String PUT_STICKER                     = "PutSticker";



    static LanguageObj languageObj;

    public static String getLanguageStringValue(String key) {
        if (languageObj == null) {
            languageObj = new LanguageObj();
        }

        if (languageObj.getLanguageObjectInstance().getLanguageValue(key)!=null&&!languageObj.getLanguageObjectInstance().getLanguageValue(key).equals("")){
            String s = languageObj.getLanguageMapping(key);
        if(s==null||s.equals(""))s = key;
            return s;}

        /*else if (languageObj.isContainsMapping(LanguageManager.)) {
           // mLogger.printError(languageObj.getLanguageMapping(LanguageManager.ERROR_OPERATION_FAILED));
            return key;
        }*/
        else
            return key;
    }



    public static void initLanguageManager()
    {
        if(languageObj == null) {
            languageObj = new LanguageObj();

        }
        languageObj.getLanguageObjectInstance();
    }

}
