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
public class Patient {
    private int mId;
    private Date mDate;
    private String mPatientDetails;
    private String mStatus;
    private String mLocation;
    private String mTransmission;
    private String mCaseNumber;

    public int getId() {
        return mId;
    }

    public Date getDate() {
        return mDate;
    }
    public String getLocation() {
        return mLocation;
    }
    public String getTransmission() {
        return mTransmission;
    }
    public String getCaseNumber() {
        return mCaseNumber;
    }
    public String getPatientDetails() {
        return mPatientDetails;
    }
    public String getStatus() {
        return mStatus;
    }

    public Patient(int id, Date date, String patientDetails,
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
        return  mPatientDetails + ", " + mStatus;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Patient)
            return (((Patient) obj).getId()==(mId));
        else
            return false;
    }

}

