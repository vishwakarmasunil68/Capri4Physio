package com.capri4physio.pojo.notes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 26-09-2017.
 */

public class CaseNoteResultPOJO {
    @SerializedName("moter_exam_case_id")
    private String moterExamCaseId;
    @SerializedName("moter_exam_casedate")
    private String moterExamCasedate;
    @SerializedName("moter_exam_casedesc")
    private String moterExamCasedesc;
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

    public String getMoterExamCaseId() {
        return moterExamCaseId;
    }

    public void setMoterExamCaseId(String moterExamCaseId) {
        this.moterExamCaseId = moterExamCaseId;
    }

    public String getMoterExamCasedate() {
        return moterExamCasedate;
    }

    public void setMoterExamCasedate(String moterExamCasedate) {
        this.moterExamCasedate = moterExamCasedate;
    }

    public String getMoterExamCasedesc() {
        return moterExamCasedesc;
    }

    public void setMoterExamCasedesc(String moterExamCasedesc) {
        this.moterExamCasedesc = moterExamCasedesc;
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
