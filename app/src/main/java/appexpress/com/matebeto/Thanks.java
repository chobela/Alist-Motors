package appexpress.com.matebeto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Thanks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);
    }

    public void onClick(View view) {
        switch (view.getId()) {

            case (R.id.home):
                Intent intent3 = new Intent(this, MainActivity.class);
                startActivity(intent3);
                break;


        }
    }
}
