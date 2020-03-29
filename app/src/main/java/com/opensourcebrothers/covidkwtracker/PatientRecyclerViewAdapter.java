package com.opensourcebrothers.covidkwtracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PatientRecyclerViewAdapter extends
        RecyclerView.Adapter<PatientRecyclerViewAdapter.ViewHolder> {

    private final List<Patient> mPatients;

    public PatientRecyclerViewAdapter(List<Patient> patients) {
        mPatients = patients;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_patient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Patient patient = mPatients.get(position);
        holder.detailsView.setText(mPatients.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return mPatients.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View parentView;
        public final TextView detailsView;
        public Patient patient;

        public ViewHolder(View view) {
            super(view);
            parentView = view;
            detailsView = (TextView) view.findViewById(R.id.list_item_patient_details);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + detailsView.getText() + "'";
        }
    }
}
