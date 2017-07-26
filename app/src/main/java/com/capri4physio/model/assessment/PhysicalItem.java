package com.capri4physio.model.assessment;

import com.google.gson.annotations.SerializedName;

/**
 * Created by prabhunathyadav on 05/06/16.
 */
public class PhysicalItem {


    @SerializedName("id")
    public String id;
    @SerializedName("patient_id")
    public String patientId;
    @SerializedName("blood_pressure")
    public String bloodPressure;
    @SerializedName("temperature")
    public String temperature;
    @SerializedName("heart_rate")
    public String heartRate;
    @SerializedName("respiratory_rate")
    public String respiratoryRate;
    @SerializedName("built_patient")
    public String builtPatient;
    @SerializedName("posture")
    public String posture;
    @SerializedName("galt")
    public String galt;
    @SerializedName("scare_type")
    public String scareType ;

    public String getSwelling() {
        return swelling;
    }

    @SerializedName("swelling")
    public String swelling;
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

    public String getBloodPressure() {
        return bloodPressure;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public String getRespiratoryRate() {
        return respiratoryRate;
    }

    public String getBuiltPatient() {
        return builtPatient;
    }

    public String getPosture() {
        return posture;
    }

    public String getGalt() {
        return galt;
    }

    public String getScareType() {
        return scareType;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }
}