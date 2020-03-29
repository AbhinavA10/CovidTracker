package com.opensourcebrothers.covidkwtracker;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class PatientListFragment extends Fragment {
    private ArrayList<Patient> mPatients =
            new ArrayList<Patient>();
    private RecyclerView mRecyclerView;
    private PatientRecyclerViewAdapter mPatientAdapter = new PatientRecyclerViewAdapter(mPatients);
    protected PatientViewModel patientViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_list,
                container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = view.getContext();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(mPatientAdapter);
    }


    public void setPatients(List<Patient> patients) {
        for (Patient patient : patients) {
            if (!mPatients.contains(patient)) {
                mPatients.add(patient);
                mPatientAdapter
                        .notifyItemInserted(mPatients.indexOf(patient));
            }
        }
        mPatientAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        patientViewModel = ViewModelProviders.of(getActivity())
                .get(PatientViewModel.class);
        patientViewModel.getPatients()
                .observe(this, new Observer<List<Patient>>() {
                    @Override
                    public void onChanged(@Nullable List<Patient> patients) {
                        if (patients != null)
                            setPatients(patients);
                    }
                });
    }

}
