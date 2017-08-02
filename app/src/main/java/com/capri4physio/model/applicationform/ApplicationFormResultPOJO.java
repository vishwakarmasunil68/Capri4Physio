package com.capri4physio.model.applicationform;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 01-08-2017.
 */

public class ApplicationFormResultPOJO implements Serializable{
    @SerializedName("a_id")
    private String aId;
    @SerializedName("a_c_id")
    private String aCId;
    @SerializedName("a_s_id")
    private String aSId;
    @SerializedName("a_course")
    private String aCourse;
    @SerializedName("a_first_name")
    private String aFirstName;
    @SerializedName("a_middle_name")
    private String aMiddleName;
    @SerializedName("a_last_name")
    private String aLastName;
    @SerializedName("a_email")
    private String aEmail;
    @SerializedName("a_city_date")
    private String aCityDate;
    @SerializedName("a_transtionid")
    private String aTranstionid;
    @SerializedName("a_amount")
    private String aAmount;
    @SerializedName("a_bank_name")
    private String aBankName;
    @SerializedName("a_date")
    private String aDate;
    @SerializedName("a_present_address")
    private String aPresentAddress;
    @SerializedName("a_city")
    private String aCity;
    @SerializedName("a_state")
    private String aState;
    @SerializedName("a_zip_code")
    private String aZipCode;
    @SerializedName("a_country")
    private String aCountry;
    @SerializedName("a_qualification")
    private String aQualification;
    @SerializedName("a_college")
    private String aCollege;
    @SerializedName("a_clinic")
    private String aClinic;
    @SerializedName("a_comment")
    private String aComment;
    @SerializedName("a_certificate")
    private String aCertificate;

    public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId;
    }

    public String getaCId() {
        return aCId;
    }

    public void setaCId(String aCId) {
        this.aCId = aCId;
    }

    public String getaSId() {
        return aSId;
    }

    public void setaSId(String aSId) {
        this.aSId = aSId;
    }

    public String getaCourse() {
        return aCourse;
    }

    public void setaCourse(String aCourse) {
        this.aCourse = aCourse;
    }

    public String getaFirstName() {
        return aFirstName;
    }

    public void setaFirstName(String aFirstName) {
        this.aFirstName = aFirstName;
    }

    public String getaMiddleName() {
        return aMiddleName;
    }

    public void setaMiddleName(String aMiddleName) {
        this.aMiddleName = aMiddleName;
    }

    public String getaLastName() {
        return aLastName;
    }

    public void setaLastName(String aLastName) {
        this.aLastName = aLastName;
    }

    public String getaEmail() {
        return aEmail;
    }

    public void setaEmail(String aEmail) {
        this.aEmail = aEmail;
    }

    public String getaCityDate() {
        return aCityDate;
    }

    public void setaCityDate(String aCityDate) {
        this.aCityDate = aCityDate;
    }

    public String getaTranstionid() {
        return aTranstionid;
    }

    public void setaTranstionid(String aTranstionid) {
        this.aTranstionid = aTranstionid;
    }

    public String getaAmount() {
        return aAmount;
    }

    public void setaAmount(String aAmount) {
        this.aAmount = aAmount;
    }

    public String getaBankName() {
        return aBankName;
    }

    public void setaBankName(String aBankName) {
        this.aBankName = aBankName;
    }

    public String getaDate() {
        return aDate;
    }

    public void setaDate(String aDate) {
        this.aDate = aDate;
    }

    public String getaPresentAddress() {
        return aPresentAddress;
    }

    public void setaPresentAddress(String aPresentAddress) {
        this.aPresentAddress = aPresentAddress;
    }

    public String getaCity() {
        return aCity;
    }

    public void setaCity(String aCity) {
        this.aCity = aCity;
    }

    public String getaState() {
        return aState;
    }

    public void setaState(String aState) {
        this.aState = aState;
    }

    public String getaZipCode() {
        return aZipCode;
    }

    public void setaZipCode(String aZipCode) {
        this.aZipCode = aZipCode;
    }

    public String getaCountry() {
        return aCountry;
    }

    public void setaCountry(String aCountry) {
        this.aCountry = aCountry;
    }

    public String getaQualification() {
        return aQualification;
    }

    public void setaQualification(String aQualification) {
        this.aQualification = aQualification;
    }

    public String getaCollege() {
        return aCollege;
    }

    public void setaCollege(String aCollege) {
        this.aCollege = aCollege;
    }

    public String getaClinic() {
        return aClinic;
    }

    public void setaClinic(String aClinic) {
        this.aClinic = aClinic;
    }

    public String getaComment() {
        return aComment;
    }

    public void setaComment(String aComment) {
        this.aComment = aComment;
    }

    public String getaCertificate() {
        return aCertificate;
    }

    public void setaCertificate(String aCertificate) {
        this.aCertificate = aCertificate;
    }

    @Override
    public String toString() {
        return "ApplicationFormResultPOJO{" +
                "aId='" + aId + '\'' +
                ", aCId='" + aCId + '\'' +
                ", aSId='" + aSId + '\'' +
                ", aCourse='" + aCourse + '\'' +
                ", aFirstName='" + aFirstName + '\'' +
                ", aMiddleName='" + aMiddleName + '\'' +
                ", aLastName='" + aLastName + '\'' +
                ", aEmail='" + aEmail + '\'' +
                ", aCityDate='" + aCityDate + '\'' +
                ", aTranstionid='" + aTranstionid + '\'' +
                ", aAmount='" + aAmount + '\'' +
                ", aBankName='" + aBankName + '\'' +
                ", aDate='" + aDate + '\'' +
                ", aPresentAddress='" + aPresentAddress + '\'' +
                ", aCity='" + aCity + '\'' +
                ", aState='" + aState + '\'' +
                ", aZipCode='" + aZipCode + '\'' +
                ", aCountry='" + aCountry + '\'' +
                ", aQualification='" + aQualification + '\'' +
                ", aCollege='" + aCollege + '\'' +
                ", aClinic='" + aClinic + '\'' +
                ", aComment='" + aComment + '\'' +
                ", aCertificate='" + aCertificate + '\'' +
                '}';
    }
}
