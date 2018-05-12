package appexpress.com.matebeto;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.HashMap;

public class CarDetalis extends AppCompatActivity {

    private ImageView imgHeader;
    TextView tvMeal;
    TextView tvPrice;
    TextView tvVenue;
    TextView tvImage;
    TextView tvPhone;
    TextView tvEmail;
    TextView tvTown;
    private DBManager dbManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detalis);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        Deals carDetails = (Deals) getIntent().getSerializableExtra("jobs");

        imgHeader = (ImageView) findViewById(R.id.backdrop);


        initCollapsingToolbar();

        dbManager = new DBManager(this);
        dbManager.open();

        tvMeal = (TextView) findViewById(R.id.car);
        tvMeal.setText(carDetails.getMeal());

        tvPrice = (TextView) findViewById(R.id.price);
        tvPrice.setText(carDetails.getPrice());

        tvVenue = (TextView) findViewById(R.id.venue);
        tvVenue.setText(carDetails.getVenue());

        tvImage = (TextView) findViewById(R.id.image);
        tvImage.setText(carDetails.getImage());

        tvPhone = (TextView) findViewById(R.id.phone);
        tvPhone.setText(carDetails.getPhone());

        tvEmail = (TextView) findViewById(R.id.email);
        tvEmail.setText(carDetails.getEmail());

        tvTown = (TextView) findViewById(R.id.townr);
        tvTown.setText(carDetails.getTown());

        Glide.with(getApplicationContext()).load(carDetails.getImage())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgHeader);
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the txtPostTitle when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("Details");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    public void onClick (View view) {

        final String car = tvMeal.getText().toString();
        final String price = tvPrice.getText().toString();
        final String description = tvVenue.getText().toString();
        final String image = tvImage.getText().toString();
        final String phone = tvPhone.getText().toString();
        final String email = tvEmail.getText().toString();
        final String town = tvTown.getText().toString();

        dbManager.insert(car, price, description, image, phone, email, town);

        Toast.makeText(getApplicationContext(),"Saved to my favourites", Toast.LENGTH_LONG).show();
    }

}
