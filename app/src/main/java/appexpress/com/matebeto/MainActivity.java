package appexpress.com.matebeto;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;


public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    HashMap<String, String> Hash_file_maps;
    SliderLayout sliderLayout;
    List<Deals> Deals;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recyclerViewadapter;

    private AVLoadingIndicatorView avii;

    String GET_JSON_DATA_HTTP_URL = "http://app-express.net/matebeto/jsoncars.php";
    public static final String JSON_MEAL = "meal";
    public static final String JSON_OLDPRICE = "oldprice";
    public static final String JSON_PRICE = "price";
    public static final String JSON_VENUE = "venue";
    public static final String JSON_IMAGE = "image";
    public static final String JSON_MEALID = "mealid";
    public static final String JSON_TRANS = "tranmission";
    public static final String JSON_PHONE = "phone";
    public static final String JSON_EMAIL = "email";
    public static final String JSON_TOWN = "town";



    JsonArrayRequest jsonArrayRequest ;

    RequestQueue requestQueue ;

    private Toolbar mToolbar;
    public static DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.design.widget.FloatingActionButton fab = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddCar.class); startActivity(i);
            }
        });


        // Setup Actionbar / Toolbar
        mToolbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.wd);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Setup Navigation Drawer Layout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        Hash_file_maps = new HashMap<String, String>();

        sliderLayout = (SliderLayout) findViewById(R.id.slider);



        Hash_file_maps.put("FNB", "http://app-express.net/matebeto/fnb.jpg");
        Hash_file_maps.put("Zoona", "http://app-express.net/matebeto/zoona.jpg");
        Hash_file_maps.put("Airtel", "http://app-express.net/matebeto/airtel.jpg");
        Hash_file_maps.put("Hot Deals", "http://app-express.net/matebeto/3.jpg");
        Hash_file_maps.put("MTN", "http://app-express.net/matebeto/mtn.jpg");
        Hash_file_maps.put("African Voices", "http://app-express.net/matebeto/images.jpg");
        Hash_file_maps.put("Kwese", "http://app-express.net/matebeto/download.jpg");
        Hash_file_maps.put("MTN sms", "http://app-express.net/matebeto/smz.jpg");
        Hash_file_maps.put("Nkwazi", "http://app-express.net/matebeto/nkwazi.png");
        Hash_file_maps.put("Kwese TV", "http://app-express.net/matebeto/9.png");
        Hash_file_maps.put("mtn", "http://app-express.net/matebeto/10.jpg");


        for (String name : Hash_file_maps.keySet()) {

            TextSliderView textSliderView = new TextSliderView(MainActivity.this);
            textSliderView
                    .description(name)
                    .image(Hash_file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Stack);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(10000);
        sliderLayout.addOnPageChangeListener(this);



        Deals = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        String indicator=getIntent().getStringExtra("loading");
        avii= (AVLoadingIndicatorView) findViewById(R.id.avii);
        if (avii != null) {
            avii.setIndicator(indicator);
        }

        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        showDialog();

        JSON_DATA_WEB_CALL();

    }

    public void JSON_DATA_WEB_CALL(){

        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL,

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

                Deal.setPhone(json.getString(JSON_PHONE));

                Deal.setEmail(json.getString(JSON_EMAIL));

                Deal.setTown(json.getString(JSON_TOWN));

            } catch (JSONException e) {

                e.printStackTrace();
            }
            Deals.add(Deal);
        }

        recyclerViewadapter = new RecyclerViewAdapter(Deals, this);

        recyclerView.setAdapter(recyclerViewadapter);

    }


    @Override
    protected void onStop() {

        sliderLayout.startAutoCycle();

        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
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

            case (R.id.orders): Intent intent3 = new Intent(this,FoodDetails.class);
                startActivity(intent3);
                break;

            case (R.id.wallet): Intent intent4 = new Intent(this,FoodDetails.class);
                startActivity(intent4);
                break;

            case (R.id.cart): Intent inten = new Intent(this, FoodDetails.class);
                startActivity(inten);
                break;

            case (R.id.profile): Intent inten2 = new Intent(this, FoodDetails.class);
                startActivity(inten2);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      if (id == R.id.action_search) {
            Intent intent = new Intent(MainActivity.this,SearchActivity.class);
            startActivity(intent);

        } else if (id == R.id.about) {
            Intent intent = new Intent(MainActivity.this,About.class);
            startActivity(intent);

        } else if (id == R.id.profile) {
          Intent intent = new Intent(MainActivity.this, Profile.class);
          startActivity(intent);

      } else if (id == R.id.add) {
          Intent intent = new Intent(MainActivity.this, CarInfo.class);
          startActivity(intent);

      } else if (id == R.id.buy) {
              Intent intent = new Intent(MainActivity.this, Advanced.class);
              startActivity(intent);

      } else if (id == R.id.search) {
          Intent intent = new Intent(MainActivity.this, SearchActivity.class);
          startActivity(intent);
      }

          else if (id == R.id.fav) {
          Intent intent = new Intent(MainActivity.this, Favourites.class);
          startActivity(intent);
      }

        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        session.setLogin(false);

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finish();
    }


}
