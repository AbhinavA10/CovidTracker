package com.opensourcebrothers.covidkwtracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opensourcebrothers.covidkwtracker.databinding.ListItemPatientBinding;

import java.util.List;

public class PatientRecyclerViewAdapter extends
        RecyclerView.Adapter<PatientRecyclerViewAdapter.ViewHolder> {

    private final List<Patient> mPatients;

    public PatientRecyclerViewAdapter(List<Patient> patients) {
        mPatients = patients;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemPatientBinding binding = ListItemPatientBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Patient patient = mPatients.get(position);
        holder.binding.setPatient(patient);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mPatients.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ListItemPatientBinding binding;
        public ViewHolder(ListItemPatientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
