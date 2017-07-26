package com.capri4physio.model.assessment;

import com.google.gson.annotations.SerializedName;

/**
 * Created by prabhunathyadav on 05/06/16.
 */
public class CheifComplaint {

    @SerializedName("id")
    public String id;
    @SerializedName("patient_id")
    public String patientId;
    @SerializedName("complaints")
    public String complaints;
    @SerializedName("problem_duration")
    public String problemDuration;
    @SerializedName("problem_before")
    public String problemBefore;
    @SerializedName("date")
    public String date;


    public String getId() {
        return id;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getComplaints() {
        return complaints;
    }

    public String getProblemDuration() {
        return problemDuration;
    }

    public String getProblemBefore() {
        return problemBefore;
    }

    public String getDate() {
        return date;
    }
}
