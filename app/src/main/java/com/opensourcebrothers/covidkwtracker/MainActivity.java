
package com.opensourcebrothers.covidkwtracker;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

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