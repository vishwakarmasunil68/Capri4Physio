package com.capri4physio.model.patient;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 29-08-2017.
 */

public class PatientPOJO implements Serializable{
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<PatientResultPOJO> patientResultPOJOs;


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<PatientResultPOJO> getPatientResultPOJOs() {
        return patientResultPOJOs;
    }

    public void setPatientResultPOJOs(List<PatientResultPOJO> patientResultPOJOs) {
        this.patientResultPOJOs = patientResultPOJOs;
    }
}
