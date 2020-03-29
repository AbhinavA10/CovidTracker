package com.opensourcebrothers.covidkwtracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opensourcebrothers.covidkwtracker.databinding.ListItemPatientBinding;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PatientRecyclerViewAdapter extends
        RecyclerView.Adapter<PatientRecyclerViewAdapter.ViewHolder> {

    private final List<Patient> mPatients;

    private static final SimpleDateFormat TIME_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);
    private static final NumberFormat MAGNITUDE_FORMAT =
            new DecimalFormat("0");

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
            binding.setTimeformat(TIME_FORMAT);
            binding.setMagnitudeformat(MAGNITUDE_FORMAT);
        }
    }

}
