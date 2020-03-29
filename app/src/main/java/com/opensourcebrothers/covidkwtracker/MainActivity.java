package com.opensourcebrothers.covidkwtracker;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new RetrieveFeedTask().execute("");
    }
}


class RetrieveFeedTask extends AsyncTask<String, Void, JSONArray> {
    private static final String TAG_TASK = "---ASYNC_TASK---";
    private Exception exception;

    protected JSONArray doInBackground(String... urls) {
        try {
            URL url = new URL("https://covid-kw.herokuapp.com/api/v1/cases/all");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            conn.connect();
            InputStream is = conn.getInputStream();

            // Read the stream
            byte[] b = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while ( is.read(b) != -1)
                baos.write(b);

            String JSONResp = new String(baos.toByteArray());
            JSONArray jsonArray = new JSONArray(JSONResp);

            return jsonArray;
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }

    protected void onPostExecute(JSONArray json) {
        if (this.exception != null) {
            Log.e(TAG_TASK, "Error getting feed");
            this.exception.printStackTrace();
        } else {
            try {
                Log.d(TAG_TASK, json.toString());
            }
            catch(Exception e) {
                System.out.println("whoop");
            }
        }
    }

}
