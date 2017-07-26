package com.capri4physio.model.assessment;

import com.google.gson.annotations.SerializedName;

/**
 * Created by prabhunathyadav on 25/04/16.
 */


public class PhysiotheraputicItem {

    @SerializedName("id")
    public String id;
    @SerializedName("patient_id")
    public String patientId;
    @SerializedName("description")
    public String description;
    @SerializedName("date")
    public String date;

    public String getId() {
        return id;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }
}
