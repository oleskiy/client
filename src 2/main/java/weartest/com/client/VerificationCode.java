package weartest.com.client;

/**
 * Created by alekseyg on 24/01/2016.
 */
public class VerificationCode {
    int UserID;

    public String Cellular ;
    public int Code ;

    public VerificationCode(int userID, String cellular, int code) {
        UserID = userID;
        Cellular = cellular;
        Code = code;
    }
    public VerificationCode( String cellular, int code) {

        Cellular = cellular;
        Code = code;
    }

    @Override
    public String toString() {
        return "VerificationCode{" +
                "UserID=" + UserID +
                ", Cellular='" + Cellular + '\'' +
                ", Code=" + Code +
                '}';
    }
}
