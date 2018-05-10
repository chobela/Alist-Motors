package appexpress.com.matebeto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static appexpress.com.matebeto.R.id.meal;

public class SearchActivity extends AppCompatActivity {

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

    EditText txtMeal, txtModel, txtPrice, txtHprice;


    JsonArrayRequest jsonArrayRequest ;

    RequestQueue requestQueue ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);

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

        Deals = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        txtMeal = (EditText) findViewById(meal);
        txtModel = (EditText) findViewById(R.id.model);
        txtPrice = (EditText) findViewById(R.id.price);
        txtHprice = (EditText) findViewById(R.id.hprice);

        String indicator=getIntent().getStringExtra("loading");
        avii= (AVLoadingIndicatorView) findViewById(R.id.avii);
        if (avii != null) {
            avii.setIndicator(indicator);
        }

        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(recyclerViewlayoutManager);
    }

    public void search (View v) {
        JSON_DATA_WEB_CALL();
    }

    public void JSON_DATA_WEB_CALL() {

        Deals.clear();

        showDialog();

        String meal = txtMeal.getText().toString().trim();
        String model = txtModel.getText().toString().trim();
        String price = txtPrice.getText().toString().trim();
        String hprice = txtHprice.getText().toString().trim();

        if (!meal.isEmpty() && !model.isEmpty() && !price.isEmpty() && !hprice.isEmpty()) {


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
        } else {
            avii.setVisibility(INVISIBLE);
            avii.hide();
            Toast.makeText(getApplicationContext(), "Please enter all fields", Toast.LENGTH_LONG).show();
        }

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

    public void onClick(View view){
        switch (view.getId()){

            case (R.id.advanced): Intent intent2 = new Intent(this,Advanced.class);
                startActivity(intent2);
                break;

        }
    }
}
