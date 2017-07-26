package com.capri4physio.model.appointment;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 25-07-2017.
 */

public class AppointmentResultPOJO {
    @SerializedName("id")
    private String id;
    @SerializedName("patient_id")
    private String patientId;
    @SerializedName("doctor_id")
    private String doctorId;
    @SerializedName("booking_date")
    private String bookingDate;
    @SerializedName("booking_starttime")
    private String bookingStarttime;
    @SerializedName("booking_endtime")
    private String bookingEndtime;
    @SerializedName("reason")
    private String reason;
    @SerializedName("status")
    private String status;
    @SerializedName("appointment_branch_code")
    private String appointmentBranchCode;
    @SerializedName("visit_type")
    private String visitType;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("email")
    private String email;


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

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingStarttime() {
        return bookingStarttime;
    }

    public void setBookingStarttime(String bookingStarttime) {
        this.bookingStarttime = bookingStarttime;
    }

    public String getBookingEndtime() {
        return bookingEndtime;
    }

    public void setBookingEndtime(String bookingEndtime) {
        this.bookingEndtime = bookingEndtime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAppointmentBranchCode() {
        return appointmentBranchCode;
    }

    public void setAppointmentBranchCode(String appointmentBranchCode) {
        this.appointmentBranchCode = appointmentBranchCode;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "AppointmentResultPOJO{" +
                "id='" + id + '\'' +
                ", patientId='" + patientId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", bookingDate='" + bookingDate + '\'' +
                ", bookingStarttime='" + bookingStarttime + '\'' +
                ", bookingEndtime='" + bookingEndtime + '\'' +
                ", reason='" + reason + '\'' +
                ", status='" + status + '\'' +
                ", appointmentBranchCode='" + appointmentBranchCode + '\'' +
                ", visitType='" + visitType + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
