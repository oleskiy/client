package weartest.com.client;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by alekseyg on 21/12/2015.
 */
public class LoginActivity2 extends Activity{
TextView info, wrongNumber;
    EditText code;
    Button logIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity2);
        info = (TextView)findViewById(R.id.info_login2);
        wrongNumber = (TextView)findViewById(R.id.wrong_number);
        code = (EditText)findViewById(R.id.phone2);

        String phone = getIntent().getExtras().getString("phone");
        info.setText(info.getText().toString()+ phone);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(code.getText().toString().equals("1234"))
                {
                   /* Intent intent = new Intent(LoginActivity2.this, OrderActivity.class);
                    startActivity(intent);*/
                }
                else
                {
                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(LoginActivity2.this);
                    builderSingle.setIcon(R.drawable.ic_launcher);
                    builderSingle.setTitle("Code is wrong");
                    builderSingle.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builderSingle.show();
                }
            }
        });

    }
}
