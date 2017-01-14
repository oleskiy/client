package weartest.com.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.rey.material.app.ToolbarManager;

/**
 * Created by alekseyg on 17/02/2016.
 */
public class Deliver extends ActionBarActivity implements ToolbarManager.OnToolbarGroupChangedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.deliver);

        Fragment fragment = new ListLockerStationDeliver().newInstance();
        weartest.com.client.MyFragmentTransaction.StartFragmentTransaction(fragment, this, "listDelivers",R.id.deliver_fragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // ??????? ? ??? ???????? requestCode ? resultCode
        if(data!=null&&data.getStringExtra("Format")!=null)
            Toast.makeText(this, "Contents = " + data.getStringExtra("Contents") +
                    ", Format = " + data.getStringExtra("Format"), Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onToolbarGroupChanged(int oldGroupId, int groupId) {

    }
}
