package com.opensourcebrothers.covidkwtracker;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG_LIST_FRAGMENT = "TAG_LIST_FRAGMENT";
    private PatientListFragment mPatientListFragment;

    private ArrayList<Patient> mPatients =
            new ArrayList<Patient>();

    private PatientRecyclerViewAdapter mPatientAdapter =
            new PatientRecyclerViewAdapter(mPatients);

    private RecyclerView mRecyclerView;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();

        if (savedInstanceState == null) {
            FragmentTransaction ft = fm.beginTransaction();
            mPatientListFragment = new PatientListFragment();
            ft.add(R.id.main_activity_frame, mPatientListFragment, TAG_LIST_FRAGMENT);
            ft.commitNow();
        } else {
            mPatientListFragment = (PatientListFragment) fm.findFragmentByTag(TAG_LIST_FRAGMENT);
        }
        mPatients.add(new Patient(1,null,"d","d","d","d","d"));
        mPatientListFragment.setPatients(mPatients);
        //TODO: add refresh button
        new JsonTask().execute("");
    }
}

class JsonTask extends AsyncTask<String, String, ArrayList<Patient>> {
    protected ArrayList<Patient> doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL("https://covid-kw.herokuapp.com/api/v1/cases/all");
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            JSONArray json_array = new JSONArray(builder.toString());
            ArrayList<Patient> list = new ArrayList<Patient>();
            for (int i = 0; i < json_array.length(); i++) {
                JSONObject row = json_array.getJSONObject(i);
                Patient patient = new Patient(row.getInt("id"), new Date(), row.getString("Patient"),
                        row.getString("Status"), row.getString("Testing location"),
                        row.getString("Transmission"), row.getString("Waterloo Region Case Number"));
                list.add(patient);
            }
            return list;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Patient> result) {
        try {
            for (int i = 0; i < result.size(); i++) {
                Log.d("Json Task result:", result.get(i).toString());
            }
            //TODO: update recycler view list
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}