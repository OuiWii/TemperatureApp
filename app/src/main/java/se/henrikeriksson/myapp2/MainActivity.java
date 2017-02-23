package se.henrikeriksson.myapp2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    ProgressDialog dialog;
    TextView myAwesomeTextView, max, min;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void onClick2(MenuItem mi) {
        Intent i = new Intent(getApplicationContext(), GraphActivity.class);
        startActivity(i);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        max = (TextView)findViewById(R.id.max);
        min = (TextView)findViewById(R.id.min);
        myAwesomeTextView = (TextView)findViewById(R.id.myAwesomeTextView);



        updateTemp();


    }
    public void onClick(View view) {

        updateTemp();


    }



    
    private void updateTemp() {
        dialog = new ProgressDialog(this); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


        String resp = null;
        RequestParams rp = new RequestParams(resp);

        HttpUtils.get("subdomain/weather/tempapp.json", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                // If the response is JSONObject instead of expected JSONArray
                Log.d("asd", "---------------- this is response : " + response);
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    myAwesomeTextView = (TextView)findViewById(R.id.myAwesomeTextView);
                    myAwesomeTextView.setText(serverResp.getString("temperature")+"°C");
                    String maxvalue = "Högst idag: " + serverResp.getString("max")+ "°C";
                    String minvalue = "Lägst idag: " + serverResp.getString("min")+ "°C";
                    max.setText(maxvalue);
                    min.setText(minvalue);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                dialog.dismiss();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline

            }
        });



    }
}
