package appexpress.com.matebeto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Test extends AppCompatActivity {

    private TextView tvView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tvView = (TextView) findViewById(R.id.link);
        Intent intent = getIntent();
        final String link = intent.getStringExtra("link");
        tvView.setText(link);
    }
}
