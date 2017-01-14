package weartest.com.client;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by alekseyg on 19/01/2016.
 */
class MyFragmentTransaction {

   static   MyFragmentTransaction myfragmentTransaction = null;
    static android.support.v4.app.FragmentTransaction fragmentTransaction=null;
    public static MyFragmentTransaction getInstance()
    {

           return myfragmentTransaction;

    }

    public static void StartFragmentTransaction(Fragment fragments, FragmentActivity activity,String tag, int id)
    {

        if(myfragmentTransaction==null) myfragmentTransaction = new MyFragmentTransaction();

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(id, fragments,tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
