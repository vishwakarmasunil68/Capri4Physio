package com.capri4physio.pojo.notes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 03-10-2017.
 */

public class ADLResultPOJO implements Serializable{

    @SerializedName("adlid")
    private String adlid;
    @SerializedName("adlname")
    private String adlname;
    @SerializedName("adldescription")
    private String adldescription;
    @SerializedName("specialtest")
    private String specialtest;
    @SerializedName("specialdescription")
    private String specialdescription;
    @SerializedName("patient_id")
    private String patientId;
    @SerializedName("adldate")
    private String adldate;

    public String getAdlid() {
        return adlid;
    }

    public void setAdlid(String adlid) {
        this.adlid = adlid;
    }

    public String getAdlname() {
        return adlname;
    }

    public void setAdlname(String adlname) {
        this.adlname = adlname;
    }

    public String getAdldescription() {
        return adldescription;
    }

    public void setAdldescription(String adldescription) {
        this.adldescription = adldescription;
    }

    public String getSpecialtest() {
        return specialtest;
    }

    public void setSpecialtest(String specialtest) {
        this.specialtest = specialtest;
    }

    public String getSpecialdescription() {
        return specialdescription;
    }

    public void setSpecialdescription(String specialdescription) {
        this.specialdescription = specialdescription;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getAdldate() {
        return adldate;
    }

    public void setAdldate(String adldate) {
        this.adldate = adldate;
    }
}
