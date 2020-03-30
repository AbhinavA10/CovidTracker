package com.opensourcebrothers.covidkwtracker;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PatientListFragment extends Fragment {
    private ArrayList<Patient> mPatients =
            new ArrayList<Patient>();
    private RecyclerView mRecyclerView;
    private PatientRecyclerViewAdapter mPatientAdapter = new PatientRecyclerViewAdapter(mPatients);
    protected PatientViewModel patientViewModel;

    public PatientListFragment(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_list,
                container, false);
        mRecyclerView = view.findViewById(R.id.list);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = view.getContext();
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(mPatientAdapter);
    }


    public void setPatients(List<Patient> patients) {
        mPatients.clear();
        mPatientAdapter.notifyDataSetChanged();
        for (Patient patient : patients) {
            if (!mPatients.contains(patient)) {
                mPatients.add(patient);
                mPatientAdapter
                        .notifyItemInserted(mPatients.indexOf(patient));
            }
        }
        Toast.makeText(this.getContext(),"Update finished", Toast.LENGTH_LONG).show();
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
