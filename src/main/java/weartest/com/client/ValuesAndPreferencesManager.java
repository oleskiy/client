package weartest.com.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import java.util.Set;

public final class ValuesAndPreferencesManager {

	private static String AND = "&";
	private static SharedPreferences prefs;
	private static Resources resources;

	/**
	 * Initiate preferences
	 *
	 * */
	public static void initPrefManager(Context context) {



		if (resources == null)
			resources = context.getResources();
		if (prefs == null)
			prefs = context.getSharedPreferences(
					"ss",
					Context.MODE_PRIVATE);
	}
	public static void saveCountry(Set<String> ttl) {
		// Save expiration TTL
		SharedPreferences.Editor editor = prefs.edit();
		editor.putStringSet("country", ttl);
		editor.commit();
	}
	public static Set<String> getCountry() {
		return prefs.getStringSet("country", null);
	}
	public static void saveId(String id)
	{
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("userID", id);
		editor.commit();
	}
	public static String gertUserID(){return prefs.getString("userID", "0");}

	public static void saveName(String id)
	{
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("name", id);
		editor.commit();
	}
	public static String gertName(){return prefs.getString("name", "name");}

	public static void saveLastName(String id)
	{
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("LastName", id);
		editor.commit();
	}
	public static String getLastName(){return prefs.getString("LastName", "LastName");}

	public static void savemail(String id)
	{
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("Mail", id);
		editor.commit();
	}
	public static String getMail(){return prefs.getString("Mail", "mail");}

	public static void saveTel(String id)
	{
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("Tel", id);
		editor.commit();
	}
	public static String getTel(){return prefs.getString("Tel", "Tel");}

	public static void saveLangId(int id)
	{
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("lanId", id);
		editor.commit();
	}
	public static int getLangId(){return prefs.getInt("lanId", 0);}

	public static void saveLockerId2(long id)
	{
		SharedPreferences.Editor editor = prefs.edit();
		editor.putLong("LockerId2", id);
		editor.commit();
	}
	public static long getLockerId2(){return prefs.getLong("LockerId2", 0);}

	public static void saveLockerNumber(int id)
	{
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt("LockerNumber", id);
		editor.commit();
	}
	public static int getLockerNumber(){return prefs.getInt("LockerNumber", 0);}

	public static void saveLocation(float lat,float lng)
	{
		SharedPreferences.Editor editor = prefs.edit();
		editor.putFloat("lat", lat);
		editor.putFloat("lng", lng);
		editor.commit();
	}
	public static double[] getLocation()
	{
		double [] loc = new double[2];
		loc[0] = prefs.getFloat("lat", 0);
		loc[1] = prefs.getFloat("lng", 0);
		return loc;
	}
}




	/**
	 * Save DB data expiration time stamp received from platform data result
	 *
	 *
	public static void setNewDBExpiredTTL(long ttl) {
		// Save expiration TTL
		Editor editor = prefs.edit();
		editor.putLong(resources.getString(R.string.SAVED_TTL), ttl);
		editor.commit();
	}

	*
	 * Check if data that DB contains had expired or not
	 *
	 * @param true or false
	 *
	 *
	public static boolean isDBExpiredTTL() {
		long savedTTL = prefs.getLong(resources.getString(R.string.SAVED_TTL),-1);
		if (AppConfigAndVars.getServerRealGMTtime() > savedTTL) {
			return true;
		} else
			return false;
	}

	*
	 * Set countries data last update time point
	 *
	 * @param time
	 *            time in milliseconds
	 *
	 *
	public static void setLastCountriesUpdateTime(long time) {
		// Save expiration TTL
		Editor editor = prefs.edit();
		editor.putLong(resources.getString(R.string.COUNTRIES_LAST_UPDATE),
				time);
		editor.commit();
	}

	*
	 * Check if data that DB contains had expired or not
	 * @param true or false
	 *
	public static boolean isToRefreshCountriesTable(boolean isNeedRefresh) {
		long last_updateMillis = prefs.getLong(resources.getString(R.string.COUNTRIES_LAST_UPDATE), 0);
		if ((AppConfigAndVars.getServerRealGMTtime() - last_updateMillis) > AppConfigAndVars.COUNTRIES_UPDATE_INTERVAL || isNeedRefresh) {
			return true;
		} else
			return false;
	}

	*
	 * Save logged in user id
	 *
	 * @param user_id
	 *            user id
	 *
	 *
	public static void saveCurrentUserLoginID(int user_id) {
		Editor editor = prefs.edit();
		editor.putInt(resources.getString(R.string.USER_LOGIN_ID), user_id);
		editor.commit();
	}

	*
	 * Get saved user id (If exist than user is logged into the system and no
	 * verification needed)
	 *
	 * @return user <b>id</b> if logged in or <b>null</b> otherwise
	 *
	 *
	public static int getUserLoginID() {
		return prefs.getInt(resources.getString(R.string.USER_LOGIN_ID), -1);
	}

	*
	 * Save current user VIPGroup
	 *
	 * @param user VIPGroup
	 *            user VIPGroup
	 *
	 *
	public static void saveCurrentUserVIPGroup(String vipGroup) {
		Editor editor = prefs.edit();
		editor.putString(resources.getString(R.string.VIPGroup), vipGroup);
		editor.commit();
	}

	*
	 * Get current user VIPGroup (If exist than return user VIPGroup)
	 *
	 * @return user <b>VIPGroup</b> if logged in or <b>Regular</b> otherwise
	 *
	 *
	public static String getCurrentUserVIPGroup() {
		return prefs.getString(resources.getString(R.string.VIPGroup), "Regular");
	}

	*
	 * Save last expired sixty second and binary position fetching date ( The
	 * last date that we have fetched expired position up to eat)
	 *
	 * @param millis
	 *            date in milliseconds
	 *
	 *
	public static void saveLastExpiredSixtyBinaryPositionsFetchDate(long millis) {
		Editor editor = prefs.edit();
		editor.putLong(
				resources.getString(R.string.EXPIRED_POS_MAX_DATE_SIXTYBINARY),
				millis);
		editor.commit();
	}

	*
	 * Get last expired sixty second and binary position fetching date ( The
	 * last date that we have fetched expired position up to eat)
	 *
	 * @return date in milliseconds
	 *
	 *
	public static long getLastExpiredSixtyBinaryPositionsFetchDate() {

		return prefs.getLong(
				resources.getString(R.string.EXPIRED_POS_MAX_DATE_SIXTYBINARY),
				0);
	}

	*
	 * Save last expired one touch position fetching date ( The last date that
	 * we have fetched expired position up to eat)
	 *
	 * @param millis
	 *            date in milliseconds
	 *
	 *
	public static void saveLastExpiredOneTouchPositionsFetchDate(long millis) {
		Editor editor = prefs.edit();
		editor.putLong(
				resources.getString(R.string.EXPIRED_POS_MAX_DATE_ONETOUCH),
				millis);
		editor.commit();
	}

	*
	 * Get last expired one touch position fetching date ( The last date that we
	 * have fetched expired position up to eat)
	 *
	 * @param millis
	 *            date in milliseconds
	 *
	 *
	public static long getLastExpiredOneTouchPositionsFetchDate() {
		return prefs.getLong(
				resources.getString(R.string.EXPIRED_POS_MAX_DATE_ONETOUCH), 0);
	}

	*
	 * Save user token and expire time
	 *
	 * @param token
	 *            token
	 * @param exiprationDate
	 *            token expiration date/time
	 *
	 *
	public static void saveUserTokenData(String token, long exiprationDate) {
		Editor editor = prefs.edit();
		editor.putString(resources.getString(R.string.TK_VALUE), token);
		editor.commit();

		long val = getTokenExpirationDate();
		if (val != 0) {
			if (exiprationDate >= val
					&& AppConfigAndVars.getServerRealGMTtime() < val)
				saveTokenExpirationDate(val);
			else
				saveTokenExpirationDate(exiprationDate);
		} else
			saveTokenExpirationDate(exiprationDate);
	}

	*
	 * Get token expiration date
	 *
	 * @return date in milliseconds
	 *
	 *
	public static long getTokenExpirationDate() {
		return prefs.getLong(resources.getString(R.string.TK_EXPIRY), 0);
	}

	*
	 * Get token expiration date
	 *
	 * @return date in milliseconds
	 *
	 *
	public static void saveTokenExpirationDate(long exiprationDate) {
		// mLogger.printInfo("@@@@@@@@@@@@@@@@@  EXPIRATION TOEN !SAVE! : " +
		// exiprationDate);
		Editor editor = prefs.edit();
		editor.putLong(resources.getString(R.string.TK_EXPIRY), exiprationDate);
		editor.commit();
	}

	*
	 * Get token
	 *
	 * @return token
	 *
	 *
	public static String getToken() {
		return prefs.getString(resources.getString(R.string.TK_VALUE), null);
	}

	*
	 * Reset token data
	 *
	 *
	public static void resetTokenData() {
		Editor editor = prefs.edit();
		editor.putString(resources.getString(R.string.TK_VALUE), null);
		editor.putLong(resources.getString(R.string.TK_EXPIRY), 0);
		editor.commit();
	}

	*
	 * Get Session
	 * @return session key
	public static String getSessionKey() {
		return prefs.getString(resources.getString(R.string.SESSION_VALUE), null);
	}

	*
	 * set Session key
	 * @param sessionKey
	public static void setSessionKey(String sessionKey) {
		Editor editor = prefs.edit();
		editor.putString(resources.getString(R.string.SESSION_VALUE), sessionKey);
		editor.commit();
	}

	*
	 * Check if constant login preference checked (Allow user to stay "always"
	 * logged in)"
	 *
	 * @return true or false
	 *
	 *
	public static boolean isConstantLogin() {
		return prefs.getBoolean(resources.getString(R.string.CONSTANT_LOGIN),
				true);
	}

	*
	 * Save current logged in user credentials
	 *
	public static void saveUserCredentials(String username, String password) {
		Editor editor = prefs.edit();
		editor.putString(resources.getString(R.string.USRN), username);
		editor.putString(resources.getString(R.string.PSSW), password);
		editor.commit();
	}

	*
	 * Get current logged in user login user name
	 *
	 * @return user name
	 *
	 *
	public static String getCurrentUserLoginName() {
		return prefs.getString(resources.getString(R.string.USRN), null);
	}

	*
	 * Get current logged in user login user name
	 *
	 * @return password
	 *
	 *
	public static String getCurrentUserLoginPass() {
		return prefs.getString(resources.getString(R.string.PSSW), null);
	}

	*
	 * Clear user saved credentials
	 *
	 * @return onlyPass is to remove only pass
	 *
	 *
	public static void clearCurrentUserLoginCredentials(boolean onlyPass) {
		Editor editor = prefs.edit();
		if (!onlyPass)
			editor.putString(resources.getString(R.string.USRN), null);
		editor.putString(resources.getString(R.string.PSSW), null);
		editor.commit();
	}

	*
	 * Clear time correction gap
	 *
	 * @return milliseconds
	 *
	public static void clearServerTimeCorrectionGap() {
		Editor editor = prefs.edit();
		editor.putLong(resources.getString(R.string.CORRECTION_TIME_GAP), 0);
		editor.commit();
	}

	*
	 * Set that tutorial has been views
	 *
	 * @param viewed
	 *            true or false
	 *
	public static void setTutorialEnabled(boolean viewed) {
		Editor editor = prefs.edit();
		editor.putBoolean(resources.getString(R.string.HELP_TUTORIAL), viewed);
		editor.commit();

	}

	*
	 * Is that tutorial has been views
	 *
	 * @return true or false
	 *
	public static boolean isStartTutorial() {
		return prefs.getBoolean(resources.getString(R.string.HELP_TUTORIAL),
				true);
	}

	*
	 * Save user picked UI language
	 *
	 * @param laguageLocale
	 *            language locale
	 *
	public static void saveUILanguage(String laguageLocale) {
		Editor editor = prefs.edit();
		editor.putString(resources.getString(R.string.UI_LANGUAGE_PICK),
				laguageLocale);
		editor.commit();
	}

	*
	 * Get user picked UI language
	 *
	 * @return language locale
	 *
	public static String getUILanguage() {
		return prefs.getString(resources.getString(R.string.UI_LANGUAGE_PICK),
				null);
	}

	// Meta trader specific
	*
	 * Get the meta trader server used for the login
	 *
	 * @return meta trader server
	 *
	public static String getMetaTraderServer() {
		return prefs.getString(
				resources.getString(R.string.META_TRADER_SERVER), null);
	}

	*
	 * Save meta trader server used for the login
	 *
	 * @param server
	 *            meta trader server
	 *
	public static void saveMetaTraderServer(String server) {
		Editor editor = prefs.edit();
		editor.putString(resources.getString(R.string.META_TRADER_SERVER),
				server);
		editor.commit();
	}

	*
	 * Save last good streamer port that worked
	 *
	 * @param port
	 *            valid and good streamer port
	 *
	public static void saveIndicateAboutUserExisting() {
		Editor editor = prefs.edit();
		editor.putBoolean(resources.getString(R.string.EXISTING_USER),true);
		editor.commit();

	}

	*
	 * Get true if its his first time
	 *
	 * @return valid port
	 *
	 *
	public static boolean getIndicateAboutUserExisting() {
		return prefs.getBoolean(resources.getString(R.string.EXISTING_USER), false);
	}

	*
	 * save gcm register state
	 * @param
	 *
	public static void saveIndicateAboutGCMRegisteration() {
		Editor editor = prefs.edit();
		editor.putBoolean(resources.getString(R.string.GCM_REGISTER),true);
		editor.commit();

	}
	*
	 * Get false if its his first GCM registration
	 * @return false if its first registration,true otherwise
	 *
	public static boolean getIndicateAboutGCMRegisteration() {
		return prefs.getBoolean(resources.getString(R.string.GCM_REGISTER), false);
	}

	*
	 * save first time app loaded from the store
	 * @param
	 *
	public static void saveFirstTimeAppLoaded() {
		Editor editor = prefs.edit();
		editor.putBoolean(resources.getString(R.string.FIRST_TIME_APP_LOADED),true);
		editor.commit();

	}
	*
	 * Get boolean if first time app loaded from the store
	 * @return false if its first registration,true otherwise
	 *
	public static boolean getIfFirstTimeAppLoaded() {
		return prefs.getBoolean(resources.getString(R.string.FIRST_TIME_APP_LOADED), false);
	}

	*
	 * save gcm register state in spotoption Server
	 * @param
	 *
	public static void saveIndicateAboutGCMRegisterationInSpotOptionServer() {
		Editor editor = prefs.edit();
		editor.putBoolean(resources.getString(R.string.GCM_REGISTER_SPOTOPTION_SERVER),true);
		editor.commit();

	}
	*
	 * Get false if its his first GCM registration in spotoption Server
	 * @return false if its first registration in spotoption Server,true otherwise
	 *
	public static boolean getIndicateAboutGCMRegisterationInSpotOptionServer() {
		return prefs.getBoolean(resources.getString(R.string.GCM_REGISTER_SPOTOPTION_SERVER), false);
	}
	////////////////////

	*
	 * Save registration Id that we got from GCM after we registered
	 *
	public static void saveRegistrationGCMId(String registrationId) {
		Editor editor = prefs.edit();
		editor.putString(resources.getString(R.string.GCM_REGISTER_KEY), registrationId);
		editor.commit();
	}

	*
	 * Get registration Id that we got from GCM after we registered
	 * @return GCM registration Id
	 *
	public static String getRegistrationGCMId() {
		return prefs.getString(resources.getString(R.string.GCM_REGISTER_KEY), null);
	}

	*
	 * save if adwords request was sent
	 * @param
	 *
	public static void saveAdwaordsWasSended() {
		Editor editor = prefs.edit();
		editor.putBoolean(resources.getString(R.string.ADS_REGISTER), true);
		editor.commit();

	}
	*
	 * Get false if its his first adwords request
	 * @return false if its first registration,true otherwise
	 *
	public static boolean getIndicateIfAdwordsWasSent() {
		return prefs.getBoolean(resources.getString(R.string.ADS_REGISTER), false);
	}
	///////////////////////////////////////////////////////////////
	*
	 * Save Campaign
	 * @param Campaign
	 *
	public static void saveCampaign(String Campaign) {
		Editor editor = prefs.edit();
		editor.putString(resources.getString(R.string.Campaign),
				Campaign);
		editor.commit();
	}

	*
	 * Get Campaign
	 * @return number Campaign
	 *
	public static String getCampaign() {
		return prefs.getString(resources.getString(R.string.Campaign),
				null);
	}

	*
	 * Save InvitationCode
	 * @param InvitationCode
	 *
	public static void saveInvatationCode(String InvitationCode) {
		Editor editor = prefs.edit();
		editor.putString(resources.getString(R.string.InvitationCode), InvitationCode);
		editor.commit();
	}

	*
	 * Get InvitationCode
	 * @return number InvitationCode
	 *
	public static String getInvitationCode() {
		return prefs.getString(resources.getString(R.string.InvitationCode), null);
	}
	*
	 * Save SubCampaign
	 * @param SubCampaign
	 *
	public static void saveSubCampaign(String sub_Campaign) {
		Editor editor = prefs.edit();
		editor.putString(resources.getString(R.string.sub_Campaign),
				sub_Campaign);
		editor.commit();
	}

	*
	 * Get SubCampaign
	 * @return number SubCampaign
	 *
	public static String getSubCampaign() {
		return prefs.getString(resources.getString(R.string.sub_Campaign),
				null);
	}
	//////////////////////////////////isNeed Rgresh////////////
	*
	 * set true if its his same language choosen else false
	 *
	public static void saveIfTheySamelang(boolean isTheSame) {
		Editor editor = prefs.edit();
		editor.putBoolean(resources.getString(R.string.NEED_LANG_REFRESH), isTheSame);
		editor.commit();
	}
	*
	 * Get true if its his same language choosen
	 * @return valid port
	 *
	public static boolean isSameLang() {
		return prefs.getBoolean(resources.getString(R.string.NEED_LANG_REFRESH), false);
	}
	////////////////@@@@@@@INTERNOVUS@@@@@@///////////////////////must to deleted if you can,only for 23trade lable!
	public static void saveAppsflyerInternovusFromOut(String f, String k, String u, String adv, String a, String p, String ad_id, String adgroup_id, String Ud, String pid) {
		Editor editor = prefs.edit();
		if(f != null)
			editor.putString("F", f);
		if(k != null)
			editor.putString("K", k);
		if(u != null)
			editor.putString("U", u);
		if(adv != null)
			editor.putString("Adv", adv);
		if(a != null)
			editor.putString("A", a);
		if(p != null)
			editor.putString("P", p);
		if(ad_id != null)
			editor.putString("ad_id", ad_id);
		if(adgroup_id != null)
			editor.putString("adgroup_id", adgroup_id);
		if(Ud != null)
			editor.putString("Ud", Ud);
		if(pid != null)
			editor.putString("Pid", pid);
		editor.commit();
	}
	@SuppressWarnings("deprecation")
	public static String getAppsflyerInternovusFromOut()
	{
		return "f="+ URLEncoder.encode(prefs.getString("F", "-151"))+AND+"k="+ URLEncoder.encode(prefs.getString("K", ""))+AND+"u="+ URLEncoder.encode(prefs.getString("U", "empty"))+AND+"au="+ URLEncoder.encode(prefs.getString("U", "empty"))+AND+"adv="+ URLEncoder.encode(prefs.getString("Adv", "1"))+AND+"a="+ URLEncoder.encode(prefs.getString("A", "23"))+AND+"p="+ URLEncoder.encode(prefs.getString("P", ""))+AND+"ad_id="+ URLEncoder.encode(prefs.getString("ad_id", "-10"))+AND+"adgroup_id="+ URLEncoder.encode(prefs.getString("adgroup_id", ""))+AND+"ud="+ URLEncoder.encode(prefs.getString("Ud", ""))+AND+"Pid="+ URLEncoder.encode(prefs.getString("Pid", ""));
	}

	public static void saveAppsflyerInternovusFromIn(String device_os,String device_os_version,String device_brand, String deviceId, String tz,String android_id_sha) {
		Editor editor = prefs.edit();
		if(device_os != null)
			editor.putString("device_os", device_os);
		if(device_os_version != null)
			editor.putString("device_os_version", device_os_version);
		if(device_brand != null)
			editor.putString("device_brand", device_brand);
		if(deviceId != null)
			editor.putString("deviceId", deviceId);
		if(tz != null)
			editor.putString("tz", tz);
		if(android_id_sha != null)
			editor.putString("android_id_sha1", android_id_sha);
		editor.commit();
	}
	@SuppressWarnings("deprecation")
	public static String getAppsflyerInternovusFromIn()
	{
		return AND+"device_os="+ URLEncoder.encode(prefs.getString("device_os", "Android"))+AND+"device_os_version="+ URLEncoder.encode(prefs.getString("device_os_version", "Android"))+AND+"device_brand="+ URLEncoder.encode(prefs.getString("device_brand", "0"))+AND+"android_id="+ URLEncoder.encode(prefs.getString("deviceId", "0"))+AND+"rand="+ URLEncoder.encode(String.valueOf(100000 + new Random().nextInt(900000)))+AND+"tz="+ URLEncoder.encode(prefs.getString("tz", "0"))+AND+"android_id_sha1="+ URLEncoder.encode(prefs.getString("android_id_sha1", "0"));
	}


	* autologin
	 * 0 - Default
	 * 1 - AutoLogin
	 * 2 - Touch ID
	 * 3 - Password
	 *
	 *
	public static void saveLoginType(int type)
	{
		Editor editor = prefs.edit();
		editor.putInt(resources.getString(R.string.LOGIN_TYPE),type);
		editor.commit();
		Log.d("TEst", "Save = " + prefs.getInt(resources.getString(R.string.LOGIN_TYPE), 0));
	}
	public static int getLoginType() {
		return prefs.getInt(resources.getString(R.string.LOGIN_TYPE), 0);
	}
	//////////////////////////////////////////////////////////////////

}

package com.spotoption.net.utils;

import java.net.URLEncoder;
import java.util.Random;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.util.Log;

import com.spotoption.net.R;
import com.spotoption.net.config.AppConfigAndVars;

public final class ValuesAndPreferencesManager {

	private static String AND = "&";
	private static SharedPreferences prefs;
	private static Resources resources;

*
	 * Initiate preferences
	 * 
	 *

	public static void initPrefManager(Context context) {

		if (resources == null)
			resources = context.getResources();
		if (prefs == null)
			prefs = context.getSharedPreferences(
					resources.getString(R.string.SHARED_PREFS_TAG),
					Context.MODE_PRIVATE);
	}

*
	 * Save DB data expiration time stamp received from platform data result
	 * 
	 *

	public static void setNewDBExpiredTTL(long ttl) {
		// Save expiration TTL
		Editor editor = prefs.edit();
		editor.putLong(resources.getString(R.string.SAVED_TTL), ttl);
		editor.commit();
	}

*
	 * Check if data that DB contains had expired or not
	 * 
	 * @param true or false
	 * 
	 *

	public static boolean isDBExpiredTTL() {
		long savedTTL = prefs.getLong(resources.getString(R.string.SAVED_TTL),-1);
		if (AppConfigAndVars.getServerRealGMTtime() > savedTTL) {
			return true;
		} else
			return false;
	}

*
	 * Set countries data last update time point
	 * 
	 * @param time
	 *            time in milliseconds
	 * 
	 *

	public static void setLastCountriesUpdateTime(long time) {
		// Save expiration TTL
		Editor editor = prefs.edit();
		editor.putLong(resources.getString(R.string.COUNTRIES_LAST_UPDATE),
				time);
		editor.commit();
	}

*
	 * Check if data that DB contains had expired or not
	 * @param true or false
	 *

	public static boolean isToRefreshCountriesTable(boolean isNeedRefresh) {
		long last_updateMillis = prefs.getLong(resources.getString(R.string.COUNTRIES_LAST_UPDATE), 0);
		if ((AppConfigAndVars.getServerRealGMTtime() - last_updateMillis) > AppConfigAndVars.COUNTRIES_UPDATE_INTERVAL || isNeedRefresh) {
			return true;
		} else
			return false;
	}

*
	 * Save logged in user id
	 * 
	 * @param user_id
	 *            user id
	 * 
	 *

	public static void saveCurrentUserLoginID(int user_id) {
		Editor editor = prefs.edit();
		editor.putInt(resources.getString(R.string.USER_LOGIN_ID), user_id);
		editor.commit();
	}

*
	 * Get saved user id (If exist than user is logged into the system and no
	 * verification needed)
	 * 
	 * @return user <b>id</b> if logged in or <b>null</b> otherwise
	 * 
	 *

	public static int getUserLoginID() {
		return prefs.getInt(resources.getString(R.string.USER_LOGIN_ID), -1);
	}
	
*
	 * Save current user VIPGroup 
	 * 
	 * @param user VIPGroup 
	 *            user VIPGroup 
	 * 
	 *

	public static void saveCurrentUserVIPGroup(String vipGroup) {
		Editor editor = prefs.edit();
		editor.putString(resources.getString(R.string.VIPGroup), vipGroup);
		editor.commit();
	}

*
	 * Get current user VIPGroup (If exist than return user VIPGroup)
	 * 
	 * @return user <b>VIPGroup</b> if logged in or <b>Regular</b> otherwise
	 * 
	 *

	public static String getCurrentUserVIPGroup() {
		return prefs.getString(resources.getString(R.string.VIPGroup), "Regular");
	}

*
	 * Save last expired sixty second and binary position fetching date ( The
	 * last date that we have fetched expired position up to eat)
	 * 
	 * @param millis
	 *            date in milliseconds
	 * 
	 *

	public static void saveLastExpiredSixtyBinaryPositionsFetchDate(long millis) {
		Editor editor = prefs.edit();
		editor.putLong(
				resources.getString(R.string.EXPIRED_POS_MAX_DATE_SIXTYBINARY),
				millis);
		editor.commit();
	}

*
	 * Get last expired sixty second and binary position fetching date ( The
	 * last date that we have fetched expired position up to eat)
	 * 
	 * @return date in milliseconds
	 * 
	 *

	public static long getLastExpiredSixtyBinaryPositionsFetchDate() {

		return prefs.getLong(
				resources.getString(R.string.EXPIRED_POS_MAX_DATE_SIXTYBINARY),
				0);
	}

*
	 * Save last expired one touch position fetching date ( The last date that
	 * we have fetched expired position up to eat)
	 * 
	 * @param millis
	 *            date in milliseconds
	 * 
	 *

	public static void saveLastExpiredOneTouchPositionsFetchDate(long millis) {
		Editor editor = prefs.edit();
		editor.putLong(
				resources.getString(R.string.EXPIRED_POS_MAX_DATE_ONETOUCH),
				millis);
		editor.commit();
	}

*
	 * Get last expired one touch position fetching date ( The last date that we
	 * have fetched expired position up to eat)
	 * 
	 * @param millis
	 *            date in milliseconds
	 * 
	 *

	public static long getLastExpiredOneTouchPositionsFetchDate() {
		return prefs.getLong(
				resources.getString(R.string.EXPIRED_POS_MAX_DATE_ONETOUCH), 0);
	}

*
	 * Save user token and expire time
	 * 
	 * @param token
	 *            token
	 * @param exiprationDate
	 *            token expiration date/time
	 * 
	 *

	public static void saveUserTokenData(String token, long exiprationDate) {
		Editor editor = prefs.edit();
		editor.putString(resources.getString(R.string.TK_VALUE), token);
		editor.commit();

		long val = getTokenExpirationDate();
		if (val != 0) {
			if (exiprationDate >= val
					&& AppConfigAndVars.getServerRealGMTtime() < val)
				saveTokenExpirationDate(val);
			else
				saveTokenExpirationDate(exiprationDate);
		} else
			saveTokenExpirationDate(exiprationDate);
	}

*
	 * Get token expiration date
	 * 
	 * @return date in milliseconds
	 * 
	 *

	public static long getTokenExpirationDate() {
		return prefs.getLong(resources.getString(R.string.TK_EXPIRY), 0);
	}

*
	 * Get token expiration date
	 * 
	 * @return date in milliseconds
	 * 
	 *

	public static void saveTokenExpirationDate(long exiprationDate) {
		// mLogger.printInfo("@@@@@@@@@@@@@@@@@  EXPIRATION TOEN !SAVE! : " +
		// exiprationDate);
		Editor editor = prefs.edit();
		editor.putLong(resources.getString(R.string.TK_EXPIRY), exiprationDate);
		editor.commit();
	}

*
	 * Get token
	 * 
	 * @return token
	 * 
	 *

	public static String getToken() {
		return prefs.getString(resources.getString(R.string.TK_VALUE), null);
	}

*
	 * Reset token data
	 * 
	 *

	public static void resetTokenData() {
		Editor editor = prefs.edit();
		editor.putString(resources.getString(R.string.TK_VALUE), null);
		editor.putLong(resources.getString(R.string.TK_EXPIRY), 0);
		editor.commit();
	}
	
*
	 * Get Session
	 * @return session key


	public static String getSessionKey() {
		return prefs.getString(resources.getString(R.string.SESSION_VALUE), null);
	}
	
*
	 * set Session key
	 * @param sessionKey


	public static void setSessionKey(String sessionKey) {
		Editor editor = prefs.edit();
		editor.putString(resources.getString(R.string.SESSION_VALUE), sessionKey);
		editor.commit();
	}

*
	 * Check if constant login preference checked (Allow user to stay "always"
	 * logged in)"
	 * 
	 * @return true or false
	 * 
	 *

	public static boolean isConstantLogin() {
		return prefs.getBoolean(resources.getString(R.string.CONSTANT_LOGIN),
				true);
	}

*
	 * Save current logged in user credentials
	 *

	public static void saveUserCredentials(String username, String password) {
		Editor editor = prefs.edit();
		editor.putString(resources.getString(R.string.USRN), username);
		editor.putString(resources.getString(R.string.PSSW), password);
		editor.commit();
	}

*
	 * Get current logged in user login user name
	 * 
	 * @return user name
	 * 
	 *

	public static String getCurrentUserLoginName() {
		return prefs.getString(resources.getString(R.string.USRN), null);
	}

*
	 * Get current logged in user login user name
	 * 
	 * @return password
	 * 
	 *

	public static String getCurrentUserLoginPass() {
		return prefs.getString(resources.getString(R.string.PSSW), null);
	}
	
*
	 * Clear user saved credentials
	 * 
	 * @return onlyPass is to remove only pass
	 * 
	 *

	public static void clearCurrentUserLoginCredentials(boolean onlyPass) {
		Editor editor = prefs.edit();
		if (!onlyPass)
			editor.putString(resources.getString(R.string.USRN), null);
		editor.putString(resources.getString(R.string.PSSW), null);
		editor.commit();
	}

*
	 * Clear time correction gap
	 * 
	 * @return milliseconds
	 *

	public static void clearServerTimeCorrectionGap() {
		Editor editor = prefs.edit();
		editor.putLong(resources.getString(R.string.CORRECTION_TIME_GAP), 0);
		editor.commit();
	}

*
	 * Set that tutorial has been views
	 * 
	 * @param viewed
	 *            true or false
	 *

	public static void setTutorialEnabled(boolean viewed) {
		Editor editor = prefs.edit();
		editor.putBoolean(resources.getString(R.string.HELP_TUTORIAL), viewed);
		editor.commit();

	}

*
	 * Is that tutorial has been views
	 * 
	 * @return true or false
	 *

	public static boolean isStartTutorial() {
		return prefs.getBoolean(resources.getString(R.string.HELP_TUTORIAL),
				true);
	}

*
	 * Save user picked UI language
	 * 
	 * @param laguageLocale
	 *            language locale
	 *

	public static void saveUILanguage(String laguageLocale) {
		Editor editor = prefs.edit();
		editor.putString(resources.getString(R.string.UI_LANGUAGE_PICK),
				laguageLocale);
		editor.commit();
	}

*
	 * Get user picked UI language
	 * 
	 * @return language locale
	 *

	public static String getUILanguage() {
		return prefs.getString(resources.getString(R.string.UI_LANGUAGE_PICK),
				null);
	}

	// Meta trader specific
*
	 * Get the meta trader server used for the login
	 * 
	 * @return meta trader server
	 *

	public static String getMetaTraderServer() {
		return prefs.getString(
				resources.getString(R.string.META_TRADER_SERVER), null);
	}

*
	 * Save meta trader server used for the login
	 * 
	 * @param server
	 *            meta trader server
	 *

	public static void saveMetaTraderServer(String server) {
		Editor editor = prefs.edit();
		editor.putString(resources.getString(R.string.META_TRADER_SERVER),
				server);
		editor.commit();
	}
	
*
	 * Save last good streamer port that worked
	 * 
	 * @param port
	 *            valid and good streamer port
	 *

	public static void saveIndicateAboutUserExisting() {
		Editor editor = prefs.edit();
		editor.putBoolean(resources.getString(R.string.EXISTING_USER),true);
		editor.commit();

	}

*
	 * Get true if its his first time
	 * 
	 * @return valid port
	 * 
	 *

	public static boolean getIndicateAboutUserExisting() {
		return prefs.getBoolean(resources.getString(R.string.EXISTING_USER), false);
	}
	
*
	 * save gcm register state 
	 * @param  
	 *

	public static void saveIndicateAboutGCMRegisteration() {
		Editor editor = prefs.edit();
		editor.putBoolean(resources.getString(R.string.GCM_REGISTER),true);
		editor.commit();

	}
*
	 * Get false if its his first GCM registration
	 * @return false if its first registration,true otherwise 
	 *

	public static boolean getIndicateAboutGCMRegisteration() {
		return prefs.getBoolean(resources.getString(R.string.GCM_REGISTER), false);
	}
	
*
	 * save first time app loaded from the store 
	 * @param  
	 *

	public static void saveFirstTimeAppLoaded() {
		Editor editor = prefs.edit();
		editor.putBoolean(resources.getString(R.string.FIRST_TIME_APP_LOADED),true);
		editor.commit();

	}
*
	 * Get boolean if first time app loaded from the store 
	 * @return false if its first registration,true otherwise 
	 *

	public static boolean getIfFirstTimeAppLoaded() {
		return prefs.getBoolean(resources.getString(R.string.FIRST_TIME_APP_LOADED), false);
	}
	
*
	 * save gcm register state in spotoption Server
	 * @param  
	 *

	public static void saveIndicateAboutGCMRegisterationInSpotOptionServer() {
		Editor editor = prefs.edit();
		editor.putBoolean(resources.getString(R.string.GCM_REGISTER_SPOTOPTION_SERVER),true);
		editor.commit();

	}
*
	 * Get false if its his first GCM registration in spotoption Server
	 * @return false if its first registration in spotoption Server,true otherwise 
	 *

	public static boolean getIndicateAboutGCMRegisterationInSpotOptionServer() {
		return prefs.getBoolean(resources.getString(R.string.GCM_REGISTER_SPOTOPTION_SERVER), false);
	}
	////////////////////

*
	 * Save registration Id that we got from GCM after we registered 
	 *

	public static void saveRegistrationGCMId(String registrationId) {
		Editor editor = prefs.edit();
		editor.putString(resources.getString(R.string.GCM_REGISTER_KEY), registrationId);
		editor.commit();
	}

*
	 * Get registration Id that we got from GCM after we registered
	 * @return GCM registration Id
	 *

	public static String getRegistrationGCMId() {
		return prefs.getString(resources.getString(R.string.GCM_REGISTER_KEY), null);
	}
	
*
	 * save if adwords request was sent
	 * @param  
	 *

	public static void saveAdwaordsWasSended() {
		Editor editor = prefs.edit();
		editor.putBoolean(resources.getString(R.string.ADS_REGISTER), true);
		editor.commit();

	}
*
	 * Get false if its his first adwords request
	 * @return false if its first registration,true otherwise 
	 *

	public static boolean getIndicateIfAdwordsWasSent() {
		return prefs.getBoolean(resources.getString(R.string.ADS_REGISTER), false);
	}
	///////////////////////////////////////////////////////////////
*
	 * Save Campaign
	 * @param Campaign
	 *

	public static void saveCampaign(String Campaign) {
		Editor editor = prefs.edit();
		editor.putString(resources.getString(R.string.Campaign), Campaign);
		editor.commit();
	}

*
	 * Get Campaign
	 * @return number Campaign
	 *

	public static String getCampaign() {
		return prefs.getString(resources.getString(R.string.Campaign),
				null);
	}
*
	 * Save InvitationCode
	 * @param InvitationCode
	 *

	public static void saveInvatationCode(String InvitationCode) {
		Editor editor = prefs.edit();
		editor.putString(resources.getString(R.string.InvitationCode), InvitationCode);
		editor.commit();
	}

*
	 * Get InvitationCode
	 * @return number InvitationCode
	 *

	public static String getInvitationCode() {
		return prefs.getString(resources.getString(R.string.InvitationCode), null);
	}
*
	 * Save SubCampaign
	 * @param SubCampaign
	 *

	public static void saveSubCampaign(String sub_Campaign) {
		Editor editor = prefs.edit();
		editor.putString(resources.getString(R.string.sub_Campaign),
				sub_Campaign);
		editor.commit();
	}

*
	 * Get SubCampaign
	 * @return number SubCampaign
	 *

	public static String getSubCampaign() {
		return prefs.getString(resources.getString(R.string.sub_Campaign),
				null);
	}
	//////////////////////////////////isNeed Rgresh////////////
*
	 * set true if its his same language choosen else false
	 *

	public static void saveIfTheySamelang(boolean isTheSame) {
		Editor editor = prefs.edit();
		editor.putBoolean(resources.getString(R.string.NEED_LANG_REFRESH), isTheSame);
		editor.commit();
	}
*
	 * Get true if its his same language choosen
	 * @return valid port
	 *

	public static boolean isSameLang() {
		return prefs.getBoolean(resources.getString(R.string.NEED_LANG_REFRESH), false);
	}
	////////////////@@@@@@@INTERNOVUS@@@@@@///////////////////////must to deleted if you can,only for 23trade lable!
	public static void saveAppsflyerInternovusFromOut(String f, String k, String u, String adv, String a, String p, String ad_id, String adgroup_id, String Ud, String pid) {
		Editor editor = prefs.edit();
		if(f != null)
			editor.putString("F", f);
		if(k != null)
			editor.putString("K", k);
		if(u != null)
			editor.putString("U", u);
		if(adv != null)
			editor.putString("Adv", adv);
		if(a != null)
			editor.putString("A", a);
		if(p != null)
			editor.putString("P", p);
		if(ad_id != null)
			editor.putString("ad_id", ad_id);
		if(adgroup_id != null)
			editor.putString("adgroup_id", adgroup_id);
		if(Ud != null)
			editor.putString("Ud", Ud);
		if(pid != null)
			editor.putString("Pid", pid);
		editor.commit();
	}
	@SuppressWarnings("deprecation")
	public static String getAppsflyerInternovusFromOut()
	{
		return "f="+URLEncoder.encode(prefs.getString("F","-151"))+AND+"k="+URLEncoder.encode(prefs.getString("K",""))+AND+"u="+URLEncoder.encode(prefs.getString("U","empty"))+AND+"au="+URLEncoder.encode(prefs.getString("U","empty"))+AND+"adv="+URLEncoder.encode(prefs.getString("Adv","1"))+AND+"a="+URLEncoder.encode(prefs.getString("A","23"))+AND+"p="+URLEncoder.encode(prefs.getString("P",""))+AND+"ad_id="+URLEncoder.encode(prefs.getString("ad_id","-10"))+AND+"adgroup_id="+URLEncoder.encode(prefs.getString("adgroup_id",""))+AND+"ud="+URLEncoder.encode(prefs.getString("Ud",""))+AND+"Pid="+URLEncoder.encode(prefs.getString("Pid",""));
	}
	
	public static void saveAppsflyerInternovusFromIn(String device_os,String device_os_version,String device_brand, String deviceId, String tz,String android_id_sha) {
		Editor editor = prefs.edit();
		if(device_os != null)
			editor.putString("device_os", device_os);
		if(device_os_version != null)
			editor.putString("device_os_version", device_os_version);
		if(device_brand != null)
			editor.putString("device_brand", device_brand);
		if(deviceId != null)
			editor.putString("deviceId", deviceId);
		if(tz != null)
			editor.putString("tz", tz);
		if(android_id_sha != null)
			editor.putString("android_id_sha1", android_id_sha);
		editor.commit();
	}
	@SuppressWarnings("deprecation")
	public static String getAppsflyerInternovusFromIn()
	{
		return AND+"device_os="+URLEncoder.encode(prefs.getString("device_os","Android"))+AND+"device_os_version="+URLEncoder.encode(prefs.getString("device_os_version","Android"))+AND+"device_brand="+URLEncoder.encode(prefs.getString("device_brand","0"))+AND+"android_id="+URLEncoder.encode(prefs.getString("deviceId","0"))+AND+"rand="+URLEncoder.encode(String.valueOf(100000 + new Random().nextInt(900000)))+AND+"tz="+URLEncoder.encode(prefs.getString("tz","0"))+AND+"android_id_sha1="+URLEncoder.encode(prefs.getString("android_id_sha1","0"));
	}
	//////////////////////////////////////////////////////////////////
* autologin
	 * 0 - Default
	 * 1 - AutoLogin
	 * 2 - Touch ID
	 * 3 - Password
	 *
	 *

	public static void saveLoginType(int type)
	{
		Editor editor = prefs.edit();
		editor.putInt(resources.getString(R.string.LOGIN_TYPE),type);
		editor.commit();

	}
	public static int getLoginType() {
		return prefs.getInt(resources.getString(R.string.LOGIN_TYPE), 0);
	}
}
*/