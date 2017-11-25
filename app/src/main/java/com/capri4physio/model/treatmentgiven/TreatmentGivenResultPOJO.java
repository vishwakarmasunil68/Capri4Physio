package com.capri4physio.model.treatmentgiven;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 30-08-2017.
 */

public class TreatmentGivenResultPOJO {
    @SerializedName("id")
    private String id;
    @SerializedName("patient_id")
    private String patientId;
    @SerializedName("therapy")
    private String therapy;
    @SerializedName("therapist")
    private String therapist;
    @SerializedName("trea_staff_name")
    private String treaStaffName;
    @SerializedName("comment")
    private String comment;
    @SerializedName("time_in")
    private String timeIn;
    @SerializedName("time_out")
    private String timeOut;
    @SerializedName("date")
    private String date;
    @SerializedName("trea_amount")
    private String treaAmount;
    @SerializedName("signature")
    private String signature;
    @SerializedName("trea_status")
    private String treaStatus;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getTherapy() {
        return therapy;
    }

    public void setTherapy(String therapy) {
        this.therapy = therapy;
    }

    public String getTherapist() {
        return therapist;
    }

    public void setTherapist(String therapist) {
        this.therapist = therapist;
    }

    public String getTreaStaffName() {
        return treaStaffName;
    }

    public void setTreaStaffName(String treaStaffName) {
        this.treaStaffName = treaStaffName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTreaAmount() {
        return treaAmount;
    }

    public void setTreaAmount(String treaAmount) {
        this.treaAmount = treaAmount;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTreaStatus() {
        return treaStatus;
    }

    public void setTreaStatus(String treaStatus) {
        this.treaStatus = treaStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "TreatmentGivenResultPOJO{" +
                "id='" + id + '\'' +
                ", patientId='" + patientId + '\'' +
                ", therapy='" + therapy + '\'' +
                ", therapist='" + therapist + '\'' +
                ", treaStaffName='" + treaStaffName + '\'' +
                ", comment='" + comment + '\'' +
                ", timeIn='" + timeIn + '\'' +
                ", timeOut='" + timeOut + '\'' +
                ", date='" + date + '\'' +
                ", treaAmount='" + treaAmount + '\'' +
                ", signature='" + signature + '\'' +
                ", treaStatus='" + treaStatus + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
