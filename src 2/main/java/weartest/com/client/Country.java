package weartest.com.client;

/**
 * Created by alekseyg on 22/12/2015.
 */
public class Country {
    String name;
    String code;
    String deal_code;

    public Country(String name, String code, String deal_code) {
        this.name = name;
        this.code = code;
        this.deal_code = deal_code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getDeal_code() {
        return deal_code;
    }

    @Override
    public String toString() {
        return name+" ("+deal_code+")";
    }
}
