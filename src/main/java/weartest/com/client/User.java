package weartest.com.client;

/**
 * Created by alekseyg on 24/01/2016.
 */
public class User {

    public int UserID ;
    public String FirstName ;
    public String LastName ;
    public boolean IsActive ;
    public boolean IsLocked ;
    public String Currency;
    public String Language ;
    public String AccountType ;
    public String Email ;
    public String Country ;
    public String City ;

    public User(int UserID, String FirstName, String LastName, boolean IsActive, boolean IsLocked, String Currency, String Language, String AccountType, String Email, String Country, String City) {
        this.UserID = UserID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.IsActive = IsActive;
        this.IsLocked = IsLocked;
        this.Currency = Currency;
        this.Language = Language;
        this.AccountType = AccountType;
        this.Email = Email;
        this.Country = Country;
        this.City = City;
    }

    public User(int userID, String firstName, String lastName) {
        UserID = userID;
        FirstName = firstName;
        LastName = lastName;
    }
    public User(int userID, String firstName, String lastName,String mail,String country, String city) {
        UserID = userID;
        FirstName = firstName;
        LastName = lastName;
        Email = mail;
        Country = country;
        City = city;
    }
    public int getUserID() {
        return UserID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public boolean isActive() {
        return IsActive;
    }

    public boolean isLocked() {
        return IsLocked;
    }

    public String getCurrency() {
        return Currency;
    }

    public String getLanguage() {
        return Language;
    }

    public String getAccountType() {
        return AccountType;
    }

    public String getEmail() {
        return Email;
    }

    public String getCountry() {
        return Country;
    }

    public String getCity() {
        return City;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserID='" + UserID + '\'' +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", IsActive=" + IsActive +
                ", IsLocked=" + IsLocked +
                ", Currency='" + Currency + '\'' +
                ", Language='" + Language + '\'' +
                ", AccountType='" + AccountType + '\'' +
                ", Email='" + Email + '\'' +
                ", Country='" + Country + '\'' +
                ", City='" + City + '\'' +
                '}';
    }
}
