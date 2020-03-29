package com.opensourcebrothers.covidkwtracker;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.support.annotation.NonNull;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Stores details of a single Patient.
 */
@Entity
public class Patient {
    @NonNull
    @PrimaryKey
    private String mId;
    private Date mDate;
    private String mPatientDetails;
    private String mStatus;
    private String mLocation;
    private String mTransmission;
    private String mCaseNumber;

    public String getId() {
        return mId;
    }

    public Date getDate() {
        return mDate;
    }

    public String getmLocation() {
        return mLocation;
    }

    public String getmTransmission() {
        return mTransmission;
    }

    public String getmCaseNumber() {
        return mCaseNumber;
    }

    public Patient(String id, Date date, String patientDetails,
                   String status, String location, String transmission,
                   String caseNumber) {
        mId = id;
        mDate = date;
        mPatientDetails = patientDetails;
        mStatus = status;
        mLocation = location;
        mTransmission = transmission;
        mCaseNumber = caseNumber; // case number may have an asterisk
    }

    /**
     * Override string method to return details about this Patient.
     *
     * @return
     */
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM.DD", Locale.US);
        String dateString = sdf.format(mDate);
        return dateString + ": " + mPatientDetails + ", " + mStatus;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Patient)
            return (((Patient) obj).getId().contentEquals(mId));
        else
            return false;
    }

}

