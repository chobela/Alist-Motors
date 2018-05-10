package appexpress.com.matebeto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    private static final String TAG = Signup.class.getSimpleName();
    private Button btnRegister;

    private EditText inputname, inputemail, inputphone, inputtown, inputpassword;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        inputname = (EditText) findViewById(R.id.name);
        inputphone = (EditText) findViewById(R.id.phone);
        inputemail = (EditText) findViewById(R.id.email);
        inputtown = (EditText) findViewById(R.id.town);
        inputpassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.register);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = inputname.getText().toString().trim();
                String email = inputemail.getText().toString().trim();
                String phone = inputphone.getText().toString().trim();
                String town = inputtown.getText().toString().trim();
                String password = inputpassword.getText().toString().trim();

                if (!name.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !town.isEmpty() && !password.isEmpty()) {
                    registerUser(name, email, phone, town, password);
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter all your details!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void registerUser(final String name, final String email, final String phone, final String town,
                              final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Config.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Registration Successful!", Toast.LENGTH_LONG).show();
                hideDialog();
                Intent intents = new Intent(
                        Signup.this,
                        Login.class);
                startActivity(intents);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Unable to Register! Please check your internet connection.", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("phone", phone);
                params.put("town", town);
                params.put("password", password);

                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Signup.this, Login.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
