package weartest.com.client;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import weartest.com.client.language.LanguageObj;

/**
 * Created by alekseyg on 24/01/2016.
 */
public interface RegisterAPI {
    @POST("/SignUp")
    void createUser(@Body String tel, Callback<String> User);


    @POST("/CheckCode")
    void verificationCode(@Body VerificationCode verificationCode, Callback<User> User);

    @POST("/UpdateProfile")
    void updateUser(@Body User user, Callback<User> User);

    @GET("/GetDictionary/2")
    void getLanguage(Callback<ArrayList<LanguageObj.GtObject>> lanObj);

    @POST("/api/Order/SetOrder")
    void getInvoice(@Body String tel,Callback<SetOrder> order);

    @POST("/geolocation/v1/geolocate?key=AIzaSyCTNXbK3xdu56ildcrSnVu8AwUW22Ax7K0")
    void getLoc(@Body String str,Callback<JSONArray> order);

    @POST("/api/Stand/StandList")
    void getLockers(@Body String tel,Callback<ChooseLockerFragment.Item> order);
}
