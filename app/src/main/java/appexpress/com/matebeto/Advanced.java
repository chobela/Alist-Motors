package appexpress.com.matebeto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class Advanced extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    List<Deals> Deals;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recyclerViewadapter;

    private AVLoadingIndicatorView avii;

    String URL = "http://app-express.net/matebeto/searchcars.php";
    public static final String JSON_MEAL = "meal";
    public static final String JSON_OLDPRICE = "oldprice";
    public static final String JSON_PRICE = "price";
    public static final String JSON_VENUE = "venue";
    public static final String JSON_IMAGE = "image";
    public static final String JSON_MEALID = "mealid";

    private EditText txtMake, txtModel, txtEngine, txtColor, txtPrice, txtHprice, txtDescription;
    private Spinner txtLocation, txtCurrency, txtDuty, txtFuel, txtInterior;

    JsonArrayRequest jsonArrayRequest ;

    RequestQueue requestQueue ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);

        Deals = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

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
        txtHprice = (EditText) findViewById(R.id.hprice);

        String indicator=getIntent().getStringExtra("loading");
        avii= (AVLoadingIndicatorView) findViewById(R.id.avii);
        if (avii != null) {
            avii.setIndicator(indicator);
        }

        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(recyclerViewlayoutManager);

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
        currency.add("K");

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

    public void searchadvanced (View view){
        WEB_CALL();
    }

    public void WEB_CALL(){

        Deals.clear();

        showDialog();

        String meal = txtMake.getText().toString().trim();
        String model = txtModel.getText().toString().trim();
        String price = txtPrice.getText().toString().trim();
        String hprice = txtHprice.getText().toString().trim();
        String location = txtLocation.getSelectedItem().toString().trim();
        String currency = txtCurrency.getSelectedItem().toString().trim();
        String engine = txtEngine.getText().toString().trim();
        String duty = txtDuty.getSelectedItem().toString().trim();
        String fuel = txtFuel.getSelectedItem().toString().trim();
        String color = txtColor.getText().toString().trim();
        String interior = txtInterior.getSelectedItem().toString().trim();


        jsonArrayRequest = new JsonArrayRequest(URL + "?meal=" + "'" + meal + "'" + "&model=" + "'" + model + "'" + "&price=" + "'" + price + "'" + "&hprice=" + "'" + hprice + "'",


                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        hideDialog();

                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }



    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            Deals Deal = new Deals();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                Deal.setMeal(json.getString(JSON_MEAL));

                Deal.setOldprice(json.getString(JSON_OLDPRICE));

                Deal.setPrice(json.getString(JSON_PRICE));

                Deal.setVenue(json.getString(JSON_VENUE));

                Deal.setImage(json.getString(JSON_IMAGE));

                Deal.setMealid(json.getString(JSON_MEALID));

            } catch (JSONException e) {

                e.printStackTrace();
            }
            Deals.add(Deal);
        }

        recyclerViewadapter = new RecyclerViewAdapter(Deals, this);

        recyclerView.setAdapter(recyclerViewadapter);

    }

    private void showDialog() {

        avii.setVisibility(VISIBLE);
        avii.show();
    }

    private void hideDialog() {

        avii.setVisibility(INVISIBLE);
        avii.hide();
    }
}
