package com.capri4physio.model.assessment;

import com.google.gson.annotations.SerializedName;

/**
 * Created by prabhunathyadav on 05/06/16.
 */
public class TreatmentGivenItem {

    @SerializedName("id")
    public String id;
    @SerializedName("patient_id")
    public String patientId;
    @SerializedName("therapist")
    public String therapist;
    @SerializedName("therapy")
    public String therapy;

    public String getTrea_staff_name() {
        return trea_staff_name;
    }

    @SerializedName("trea_staff_name")
    public String trea_staff_name;

    public String getQuan_item() {
        return quan_item;
    }

    @SerializedName("trea_amount")
    public String quan_item;
    @SerializedName("comment")
    public String comment;
    @SerializedName("time_in")
    public String timeIn;
    @SerializedName("time_out")
    public String timeOut;
    @SerializedName("date")
    public String date;

    public String getId() {
        return id;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getTherapist() {
        return therapist;
    }

    public String getTherapy() {
        return therapy;
    }

    public String getComment() {
        return comment;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public String getDate() {
        return date;
    }
}