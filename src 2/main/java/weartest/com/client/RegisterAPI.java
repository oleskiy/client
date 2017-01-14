package weartest.com.client;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
/**
 * Created by alekseyg on 24/01/2016.
 */
public interface RegisterAPI {
    @POST("/CheckCode")
    void verificationCode(@Body VerificationCode verificationCode, Callback<User> User);

    @POST("/UpdateProfile")
    void updateUser(@Body User user, Callback<User> User);
}
