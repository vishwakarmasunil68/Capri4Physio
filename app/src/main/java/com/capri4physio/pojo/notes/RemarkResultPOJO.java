package com.capri4physio.pojo.notes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 26-09-2017.
 */

public class RemarkResultPOJO {
    @SerializedName("moter_exam_remark_id")
    private String moterExamRemarkId;
    @SerializedName("moter_exam_remarkdate")
    private String moterExamRemarkdate;
    @SerializedName("moter_exam_remarkdesc")
    private String moterExamRemarkdesc;
    @SerializedName("patient_id")
    private String patientId;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("age")
    private String age;
    @SerializedName("gender")
    private String gender;
    @SerializedName("patient_code")
    private String patientCode;

    public String getMoterExamRemarkId() {
        return moterExamRemarkId;
    }

    public void setMoterExamRemarkId(String moterExamRemarkId) {
        this.moterExamRemarkId = moterExamRemarkId;
    }

    public String getMoterExamRemarkdate() {
        return moterExamRemarkdate;
    }

    public void setMoterExamRemarkdate(String moterExamRemarkdate) {
        this.moterExamRemarkdate = moterExamRemarkdate;
    }

    public String getMoterExamRemarkdesc() {
        return moterExamRemarkdesc;
    }

    public void setMoterExamRemarkdesc(String moterExamRemarkdesc) {
        this.moterExamRemarkdesc = moterExamRemarkdesc;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }
}
