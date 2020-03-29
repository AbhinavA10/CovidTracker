package com.opensourcebrothers.covidkwtracker;

import android.arch.lifecycle.ViewModelProviders;
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

    private static final String TAG = "MainActivity";

    PatientViewModel patientViewModel;

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
        // Retrieve the Earthquake View Model for this Activity.
        patientViewModel = ViewModelProviders.of(this)
                .get(PatientViewModel.class);
        //TODO: add refresh button
    }
}