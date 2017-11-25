package com.capri4physio.pojo.notes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 26-09-2017.
 */

public class ProgressNoteResultPOJO {
    @SerializedName("moter_exam_progress_id")
    private String moterExamProgressId;
    @SerializedName("moter_exam_progressdate")
    private String moterExamProgressdate;
    @SerializedName("moter_exam_progressdesc")
    private String moterExamProgressdesc;
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

    public String getMoterExamProgressId() {
        return moterExamProgressId;
    }

    public void setMoterExamProgressId(String moterExamProgressId) {
        this.moterExamProgressId = moterExamProgressId;
    }

    public String getMoterExamProgressdate() {
        return moterExamProgressdate;
    }

    public void setMoterExamProgressdate(String moterExamProgressdate) {
        this.moterExamProgressdate = moterExamProgressdate;
    }

    public String getMoterExamProgressdesc() {
        return moterExamProgressdesc;
    }

    public void setMoterExamProgressdesc(String moterExamProgressdesc) {
        this.moterExamProgressdesc = moterExamProgressdesc;
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
