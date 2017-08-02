package com.capri4physio.model.studentcourse;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 31-07-2017.
 */

public class StudentCourseResultPOJO implements Serializable {
    @SerializedName("sc_id")
    private String scId;
    @SerializedName("sc_sid")
    private String scSid;
    @SerializedName("sc_sname")
    private String scSname;
    @SerializedName("sc_semail")
    private String scSemail;
    @SerializedName("sc_sapplicationform_fill")
    private String scSapplicationformFill;
    @SerializedName("sc_photo_upload")
    private String scPhotoUpload;
    @SerializedName("sc_cerifiato_upload")
    private String scCerifiatoUpload;
    @SerializedName("sc_idcard_upload")
    private String scIdcardUpload;
    @SerializedName("sc_reg_fees")
    private String scRegFees;
    @SerializedName("sc_fullfees")
    private String scFullfees;
    @SerializedName("sc_cid")
    private String scCid;
    @SerializedName("sc_date")
    private String sc_date;
    @SerializedName("sc_time")
    private String sc_time;
    @SerializedName("admin_application_form")
    private String adminApplicationForm;
    @SerializedName("admin_photo_upload")
    private String adminPhotoUpload;
    @SerializedName("admin_certificate_upload")
    private String adminCertificateUpload;
    @SerializedName("admin_icard")
    private String adminIcard;
    @SerializedName("admin_reg_fees")
    private String adminRegFees;
    @SerializedName("admin_full_fees")
    private String adminFullFees;
    @SerializedName("admin_status")
    private String adminStatus;

    public String getScId() {
        return scId;
    }

    public void setScId(String scId) {
        this.scId = scId;
    }

    public String getScSid() {
        return scSid;
    }

    public void setScSid(String scSid) {
        this.scSid = scSid;
    }

    public String getScSname() {
        return scSname;
    }

    public void setScSname(String scSname) {
        this.scSname = scSname;
    }

    public String getScSemail() {
        return scSemail;
    }

    public void setScSemail(String scSemail) {
        this.scSemail = scSemail;
    }

    public String getScSapplicationformFill() {
        return scSapplicationformFill;
    }

    public void setScSapplicationformFill(String scSapplicationformFill) {
        this.scSapplicationformFill = scSapplicationformFill;
    }

    public String getScPhotoUpload() {
        return scPhotoUpload;
    }

    public void setScPhotoUpload(String scPhotoUpload) {
        this.scPhotoUpload = scPhotoUpload;
    }

    public String getScCerifiatoUpload() {
        return scCerifiatoUpload;
    }

    public void setScCerifiatoUpload(String scCerifiatoUpload) {
        this.scCerifiatoUpload = scCerifiatoUpload;
    }

    public String getScIdcardUpload() {
        return scIdcardUpload;
    }

    public void setScIdcardUpload(String scIdcardUpload) {
        this.scIdcardUpload = scIdcardUpload;
    }

    public String getScRegFees() {
        return scRegFees;
    }

    public void setScRegFees(String scRegFees) {
        this.scRegFees = scRegFees;
    }

    public String getScFullfees() {
        return scFullfees;
    }

    public void setScFullfees(String scFullfees) {
        this.scFullfees = scFullfees;
    }

    public String getScCid() {
        return scCid;
    }

    public void setScCid(String scCid) {
        this.scCid = scCid;
    }

    public String getSc_date() {
        return sc_date;
    }

    public void setSc_date(String sc_date) {
        this.sc_date = sc_date;
    }

    public String getSc_time() {
        return sc_time;
    }

    public void setSc_time(String sc_time) {
        this.sc_time = sc_time;
    }

    public String getAdminApplicationForm() {
        return adminApplicationForm;
    }

    public void setAdminApplicationForm(String adminApplicationForm) {
        this.adminApplicationForm = adminApplicationForm;
    }

    public String getAdminPhotoUpload() {
        return adminPhotoUpload;
    }

    public void setAdminPhotoUpload(String adminPhotoUpload) {
        this.adminPhotoUpload = adminPhotoUpload;
    }

    public String getAdminCertificateUpload() {
        return adminCertificateUpload;
    }

    public void setAdminCertificateUpload(String adminCertificateUpload) {
        this.adminCertificateUpload = adminCertificateUpload;
    }

    public String getAdminIcard() {
        return adminIcard;
    }

    public void setAdminIcard(String adminIcard) {
        this.adminIcard = adminIcard;
    }

    public String getAdminRegFees() {
        return adminRegFees;
    }

    public void setAdminRegFees(String adminRegFees) {
        this.adminRegFees = adminRegFees;
    }

    public String getAdminFullFees() {
        return adminFullFees;
    }

    public void setAdminFullFees(String adminFullFees) {
        this.adminFullFees = adminFullFees;
    }

    public String getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(String adminStatus) {
        this.adminStatus = adminStatus;
    }

    @Override
    public String toString() {
        return "StudentCourseResultPOJO{" +
                "scId='" + scId + '\'' +
                ", scSid='" + scSid + '\'' +
                ", scSname='" + scSname + '\'' +
                ", scSemail='" + scSemail + '\'' +
                ", scSapplicationformFill='" + scSapplicationformFill + '\'' +
                ", scPhotoUpload='" + scPhotoUpload + '\'' +
                ", scCerifiatoUpload='" + scCerifiatoUpload + '\'' +
                ", scIdcardUpload='" + scIdcardUpload + '\'' +
                ", scRegFees='" + scRegFees + '\'' +
                ", scFullfees='" + scFullfees + '\'' +
                ", scCid='" + scCid + '\'' +
                ", sc_date='" + sc_date + '\'' +
                ", sc_time='" + sc_time + '\'' +
                ", adminApplicationForm='" + adminApplicationForm + '\'' +
                ", adminPhotoUpload='" + adminPhotoUpload + '\'' +
                ", adminCertificateUpload='" + adminCertificateUpload + '\'' +
                ", adminIcard='" + adminIcard + '\'' +
                ", adminRegFees='" + adminRegFees + '\'' +
                ", adminFullFees='" + adminFullFees + '\'' +
                ", adminStatus='" + adminStatus + '\'' +
                '}';
    }
}
