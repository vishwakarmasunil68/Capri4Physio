package com.capri4physio.model.patient;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 29-08-2017.
 */

public class PatientAmount {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<PatientAmountPOJO> patientAmountPOJOs;
    @SerializedName("total")
    String total;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<PatientAmountPOJO> getPatientAmountPOJOs() {
        return patientAmountPOJOs;
    }

    public void setPatientAmountPOJOs(List<PatientAmountPOJO> patientAmountPOJOs) {
        this.patientAmountPOJOs = patientAmountPOJOs;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
