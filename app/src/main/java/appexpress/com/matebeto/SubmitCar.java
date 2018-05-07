package appexpress.com.matebeto;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SubmitCar extends AsyncTask<String, Void, String> {

    private Context context;

    // Progress Dialog
    private ProgressDialog pDialog;

    public SubmitCar(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {

        super.onPreExecute();
        pDialog = new ProgressDialog(this.context);
        pDialog.setMessage("Submitting..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected String doInBackground(String... arg0) {

        String uid = arg0[0];
        String meal = arg0[1];
        String price = arg0[2];
        String venue = arg0[3];


        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "?uid=" + URLEncoder.encode(uid, "UTF-8");
            data += "&meal=" + URLEncoder.encode(meal, "UTF-8");
            data += "&price=" + URLEncoder.encode(price, "UTF-8");
            data += "&venue=" + URLEncoder.encode(venue, "UTF-8");



            link = "http://app-express.net/matebeto/submitcar.php" + data;
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
                    Toast.makeText(context, "Submitted!", Toast.LENGTH_SHORT).show();
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