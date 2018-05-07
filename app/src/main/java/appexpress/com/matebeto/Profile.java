package appexpress.com.matebeto;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class Profile extends AppCompatActivity {

    private EditText txtUid, txtName, txtEmail, txtPhone, txtTown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtUid = (EditText) findViewById(R.id.uid);
        txtName = (EditText) findViewById(R.id.name);
        txtEmail = (EditText) findViewById(R.id.email);
        txtPhone = (EditText) findViewById(R.id.phone);
        txtTown = (EditText) findViewById(R.id.town);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Profile.this);
        String name = sp.getString("name", "anonymous");
        String uid = sp.getString("uid", "anonymous");
        String phone = sp.getString("phone", "anonymous");
        String town = sp.getString("town", "anonymous");
        String email = sp.getString("email", "anonymous");

        txtName.setText(name);
        txtUid.setText(uid);
        txtPhone.setText(phone);
        txtTown.setText(town);
        txtEmail.setText(email);
    }
}
