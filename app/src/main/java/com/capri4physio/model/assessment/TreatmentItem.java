package com.capri4physio.model.assessment;

import com.google.gson.annotations.SerializedName;

/**
 * Created by prabhunathyadav on 05/06/16.
 */
public class TreatmentItem {

    @SerializedName("id")
    public String id;
    @SerializedName("patient_id")
    public String patientId;
    @SerializedName("goal")
    public String goal;
    @SerializedName("therapy")
    public String therapy;
    @SerializedName("dose")
    public String dose;
    @SerializedName("date")
    public String date;

    public String getId() {
        return id;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getGoal() {
        return goal;
    }

    public String getTherapy() {
        return therapy;
    }

    public String getDose() {
        return dose;
    }

    public String getDate() {
        return date;
    }
}