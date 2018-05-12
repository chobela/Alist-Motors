package appexpress.com.matebeto;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class Favourites extends AppCompatActivity {


    private DBManager dbManager;

    private ListView listView;

    private SimpleCursorAdapter adapter;

    final String[] from = new String[] { DatabaseHelper._ID,
            DatabaseHelper.CAR, DatabaseHelper.PRICE, DatabaseHelper.DESCRIPTION, DatabaseHelper.IMAGE, DatabaseHelper.PHONE, DatabaseHelper.EMAIL, DatabaseHelper.TOWN };

    final int[] to = new int[] { R.id.id, R.id.car, R.id.price, R.id.description, R.id.image, R.id.phone, R.id.email, R.id.town};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        dbManager = new DBManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this, R.layout.view_record, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        // OnCLickListiner For List Items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                TextView idTextView = (TextView) view.findViewById(R.id.id);
                TextView carTextView = (TextView) view.findViewById(R.id.car);
                TextView priceTextView = (TextView) view.findViewById(R.id.price);
                TextView descriptionTextView = (TextView) view.findViewById(R.id.description);
                TextView imageTextView = (TextView) view.findViewById(R.id.image);
                TextView phoneTextView = (TextView) view.findViewById(R.id.phone);
                TextView emailTextView = (TextView) view.findViewById(R.id.email);
                TextView townTextView = (TextView) view.findViewById(R.id.town);


                String id = idTextView.getText().toString();
                String car = carTextView.getText().toString();
                String price = priceTextView.getText().toString();
                String description = descriptionTextView.getText().toString();
                String image = imageTextView.getText().toString();
                String phone = phoneTextView.getText().toString();
                String email = emailTextView.getText().toString();
                String town = townTextView.getText().toString();

                Intent modify_intent = new Intent(getApplicationContext(), Modify.class);
                modify_intent.putExtra("car", car);
                modify_intent.putExtra("price", price);
                modify_intent.putExtra("id", id);
                modify_intent.putExtra("description", description);
                modify_intent.putExtra("image", image);
                modify_intent.putExtra("phone", phone);
                modify_intent.putExtra("email", email);
                modify_intent.putExtra("town", town);


                startActivity(modify_intent);
            }
        });
    }
}
