package appexpress.com.matebeto;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private TextView txtUid;

    private EditText txtMake, txtModel, txtEngine, txtColor, txtPrice, txtFeatures, txtDescription;
    private Spinner txtLocation, txtCurrency, txtDuty, txtFuel, txtInterior;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarf);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        txtUid = (TextView) findViewById(R.id.uid);
        txtMake = (EditText) findViewById(R.id.meal);
        txtModel = (EditText) findViewById(R.id.model);
        txtLocation = (Spinner) findViewById(R.id.location);
        txtEngine = (EditText) findViewById(R.id.engine);
        txtCurrency = (Spinner) findViewById(R.id.currency);
        txtDuty = (Spinner) findViewById(R.id.duty);
        txtFuel = (Spinner) findViewById(R.id.fuel);
        txtColor = (EditText) findViewById(R.id.color);
        txtInterior = (Spinner) findViewById(R.id.interior);
        txtPrice = (EditText) findViewById(R.id.price);
        txtDescription = (EditText) findViewById(R.id.venue);



        String uid = UUID.randomUUID().toString();

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(CarInfo.this);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("uid", uid);

        txtUid.setText(uid);

        // Spinner click listener
        txtLocation.setOnItemSelectedListener(this);
        txtCurrency.setOnItemSelectedListener(this);
        txtDuty.setOnItemSelectedListener(this);
        txtFuel.setOnItemSelectedListener(this);
        txtInterior.setOnItemSelectedListener(this);



        // Spinner Drop down elements
        List<String> location = new ArrayList<String>();
        location.add("");
        location.add("Lusaka");
        location.add("Copperbelt");
        location.add("Rest of Zambia");


        List<String> currency = new ArrayList<String>();
        currency.add("");
        currency.add("$");
        currency.add("ZMW");

        List<String> duty = new ArrayList<String>();
        duty.add("");
        duty.add("Duty Exempted");
        duty.add("Duty Not Paid");
        duty.add("Duty Paid");
        duty.add("Not Specified");

        List<String> fuel = new ArrayList<String>();
        fuel.add("");
        fuel.add("Petrol");
        fuel.add("Diesel");
        fuel.add("Hybrid");
        fuel.add("Electric");
        fuel.add("Other");

        List<String> interior = new ArrayList<String>();
        interior.add("");
        interior.add("Cloth");
        interior.add("Leather");
        interior.add("Other");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, location);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currency);
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, duty);
        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fuel);
        ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, interior);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        txtLocation.setAdapter(dataAdapter);
        txtCurrency.setAdapter(dataAdapter2);
        txtDuty.setAdapter(dataAdapter3);
        txtFuel.setAdapter(dataAdapter4);
        txtInterior.setAdapter(dataAdapter5);


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.addcar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.done:

                String uid = txtUid.getText().toString();
                String meal = txtMake.getText().toString();
                String model = txtModel.getText().toString();
                String location = txtLocation.getSelectedItem().toString();
                String currency = txtCurrency.getSelectedItem().toString();
                String engine = txtEngine.getText().toString();
                String duty = txtDuty.getSelectedItem().toString();
                String fuel = txtFuel.getSelectedItem().toString();
                String color = txtColor.getText().toString();
                String interior = txtInterior.getSelectedItem().toString();
                String price = txtPrice.getText().toString();
                String venue = txtDescription.getText().toString();

                if (!uid.isEmpty() && !meal.isEmpty() && !location.isEmpty() && !currency.isEmpty()) {
                    new SubmitInfo(this).execute(uid, meal, model, location, price, venue, currency, engine, duty, fuel, color, interior);

                } else {
                    Toast.makeText(getApplicationContext(), "Please enter all fields", Toast.LENGTH_LONG).show();
                }

                break;

        }

        return super.onOptionsItemSelected(item);
    }


    public class SubmitInfo extends AsyncTask<String, Void, String> {


        private Context context;

        // Progress Dialog
        private ProgressDialog pDialog;

        public SubmitInfo(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = new ProgressDialog(this.context);
            pDialog.setMessage("Please Wait..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... arg0) {

            final String uid = arg0[0];
            final String meal = arg0[1];
            final String model = arg0[2];
            String location = arg0[3];
            String price = arg0[4];
            String description = arg0[5];
            String currency = arg0[6];
            String engine = arg0[7];
            String duty = arg0[8];
            String fuel = arg0[9];
            String color = arg0[10];
            String interior = arg0[11];



            String link;
            String data;
            BufferedReader bufferedReader;
            String result;

            try {
                data = "?uid=" + URLEncoder.encode(uid, "UTF-8");
                data += "&meal=" + URLEncoder.encode(meal, "UTF-8");
                data += "&model=" + URLEncoder.encode(model, "UTF-8");
                data += "&location=" + URLEncoder.encode(location, "UTF-8");
                data += "&price=" + URLEncoder.encode(price, "UTF-8");
                data += "&venue=" + URLEncoder.encode(description, "UTF-8");
                data += "&currency=" + URLEncoder.encode(currency, "UTF-8");
                data += "&engine=" + URLEncoder.encode(engine, "UTF-8");
                data += "&duty=" + URLEncoder.encode(duty, "UTF-8");
                data += "&fuel=" + URLEncoder.encode(fuel, "UTF-8");
                data += "&color=" + URLEncoder.encode(color, "UTF-8");
                data += "&interior=" + URLEncoder.encode(interior, "UTF-8");


                link = "http://app-express.net/matebeto/submitinfo.php" + data;
                URL url = new URL(link);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                result = bufferedReader.readLine();
                return result;
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            pDialog.dismiss();
            String jsonStr = result;
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String query_result = jsonObj.getString("query_result");
                    if (query_result.equals("SUCCESS")) {


                        Intent i = new Intent(context, AddCar.class);
                        i.putExtra("uid", txtUid.getText().toString());
                        context.startActivity(i);


                    } else if (query_result.equals("FAILURE")) {
                        Toast.makeText(context, "Submission Failed.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Could not connect to remote database.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "No Internet Connection.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Could not connect to server.", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
