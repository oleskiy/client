package weartest.com.client;

/**
 * Created by oleskiy on 23.09.16.
 */

public class SetOrder {
    String LockerNumber;
    String Id2;
    String IsAfternoon;

    public SetOrder(String lockerNumber, String id2, String isAfternoon) {
        LockerNumber = lockerNumber;
        Id2 = id2;
        IsAfternoon = isAfternoon;
    }

    public String getLockerNumber() {
        return LockerNumber;
    }

    public void setLockerNumber(String lockerNumber) {
        LockerNumber = lockerNumber;
    }

    public String getId2() {
        return Id2;
    }

    public void setId2(String id2) {
        Id2 = id2;
    }

    public String getIsAfternoon() {
        return IsAfternoon;
    }

    public void setIsAfternoon(String isAfternoon) {
        IsAfternoon = isAfternoon;
    }
}
