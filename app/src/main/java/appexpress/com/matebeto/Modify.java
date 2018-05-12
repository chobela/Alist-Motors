package appexpress.com.matebeto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class Modify extends AppCompatActivity {



    private Button deleteBtn;
    private TextView carText;
    private TextView priceText;
    private TextView descriptionText;
    private TextView imageText;
    private TextView phoneText;
    private TextView emailText;
    private TextView townText;
    private ImageView imgHeader;

    private long _id;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        dbManager = new DBManager(this);
        dbManager.open();

        carText = (TextView) findViewById(R.id.car);
        priceText = (TextView) findViewById(R.id.price);
        descriptionText = (TextView) findViewById(R.id.description);
        imageText = (TextView) findViewById(R.id.image);
        phoneText = (TextView) findViewById(R.id.phone);
        emailText = (TextView) findViewById(R.id.email);
        townText = (TextView) findViewById(R.id.town);
        imgHeader = (ImageView) findViewById(R.id.backdrop);

        deleteBtn = (Button) findViewById(R.id.delete);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        final String car = intent.getStringExtra("car");
        String price = intent.getStringExtra("price");
        String description = intent.getStringExtra("description");
        final String image = intent.getStringExtra("image");
        final String phone = intent.getStringExtra("phone");
        final String email = intent.getStringExtra("email");
        final String town = intent.getStringExtra("town");

        _id = Long.parseLong(id);

        carText.setText(car);
        priceText.setText(price);
        descriptionText.setText(description);
        imageText.setText(image);
        phoneText.setText(phone);
        emailText.setText(email);
        townText.setText(town);

        Glide.with(getApplicationContext()).load(image)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgHeader);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {

                    case R.id.delete:
                        dbManager.delete(_id);
                        this.returnHome();
                        break;
                }
            }
            public void returnHome() {
                Intent home_intent = new Intent(getApplicationContext(), Favourites.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home_intent);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
