package com.opensourcebrothers.covidkwtracker;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientViewModel extends AndroidViewModel {
    private static final String TAG = "PatientUpdate";
    private MutableLiveData<List<Patient>> patients;
    public PatientViewModel(Application application) {
        super(application);
    }
    public LiveData<List<Patient>> getPatients() {
        if (patients == null) {
            patients = new MutableLiveData<List<Patient>>();
            loadPatients();
        }
        return patients;
    }
    // Asynchronously load the Patients from the feed.
    public void loadPatients() {
        new AsyncTask<Void, Void, List<Patient>>() {
            @Override
            protected List<Patient> doInBackground(Void... voids) {
                HttpURLConnection connection = null;
                BufferedReader reader = null;

                try {
                    URL url = new URL(getApplication().getString(R.string.covid_feed));
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
                    Log.d(TAG,"HAHAHHAHAHAHAHHAHAHAHH");
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
            protected void onPostExecute(List<Patient> result) {
                patients.setValue(result);
            }
        }.execute();
    }
}