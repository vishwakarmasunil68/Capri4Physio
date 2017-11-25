package com.capri4physio.model.invoice;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 29-08-2017.
 */

public class InvoiceResultPOJO {
    @SerializedName("invo_id")
    private String invoId;
    @SerializedName("invo_patient_id")
    private String invoPatientId;
    @SerializedName("invo_patient_name")
    private String invoPatientName;
    @SerializedName("invo_thurapy")
    private String invoThurapy;
    @SerializedName("invo_amount")
    private String invoAmount;
    @SerializedName("invo_payment_mode")
    private String invoPaymentMode;
    @SerializedName("invo_status")
    private String invoStatus;
    @SerializedName("invo_therapist_id")
    private String invoTherapistId;
    @SerializedName("invo_trans_id")
    private String invoTransId;
    @SerializedName("invo_branch_code")
    private String invoBranchCode;
    @SerializedName("invo_date")
    private String invo_date;
    @SerializedName("invo_time")
    private String invo_time;


    public String getInvoId() {
        return invoId;
    }

    public void setInvoId(String invoId) {
        this.invoId = invoId;
    }

    public String getInvoPatientId() {
        return invoPatientId;
    }

    public void setInvoPatientId(String invoPatientId) {
        this.invoPatientId = invoPatientId;
    }

    public String getInvoPatientName() {
        return invoPatientName;
    }

    public void setInvoPatientName(String invoPatientName) {
        this.invoPatientName = invoPatientName;
    }

    public String getInvoThurapy() {
        return invoThurapy;
    }

    public void setInvoThurapy(String invoThurapy) {
        this.invoThurapy = invoThurapy;
    }

    public String getInvoAmount() {
        return invoAmount;
    }

    public void setInvoAmount(String invoAmount) {
        this.invoAmount = invoAmount;
    }

    public String getInvoPaymentMode() {
        return invoPaymentMode;
    }

    public void setInvoPaymentMode(String invoPaymentMode) {
        this.invoPaymentMode = invoPaymentMode;
    }

    public String getInvoStatus() {
        return invoStatus;
    }

    public void setInvoStatus(String invoStatus) {
        this.invoStatus = invoStatus;
    }

    public String getInvoTherapistId() {
        return invoTherapistId;
    }

    public void setInvoTherapistId(String invoTherapistId) {
        this.invoTherapistId = invoTherapistId;
    }

    public String getInvoTransId() {
        return invoTransId;
    }

    public void setInvoTransId(String invoTransId) {
        this.invoTransId = invoTransId;
    }

    public String getInvoBranchCode() {
        return invoBranchCode;
    }

    public void setInvoBranchCode(String invoBranchCode) {
        this.invoBranchCode = invoBranchCode;
    }

    public String getInvo_date() {
        return invo_date;
    }

    public void setInvo_date(String invo_date) {
        this.invo_date = invo_date;
    }

    public String getInvo_time() {
        return invo_time;
    }

    public void setInvo_time(String invo_time) {
        this.invo_time = invo_time;
    }

    @Override
    public String toString() {
        return "InvoiceResultPOJO{" +
                "invoId='" + invoId + '\'' +
                ", invoPatientId='" + invoPatientId + '\'' +
                ", invoPatientName='" + invoPatientName + '\'' +
                ", invoThurapy='" + invoThurapy + '\'' +
                ", invoAmount='" + invoAmount + '\'' +
                ", invoPaymentMode='" + invoPaymentMode + '\'' +
                ", invoStatus='" + invoStatus + '\'' +
                ", invoTherapistId='" + invoTherapistId + '\'' +
                ", invoTransId='" + invoTransId + '\'' +
                ", invoBranchCode='" + invoBranchCode + '\'' +
                ", invo_date='" + invo_date + '\'' +
                ", invo_time='" + invo_time + '\'' +
                '}';
    }
}
