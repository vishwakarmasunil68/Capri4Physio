package com.capri4physio.model.patient;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 29-08-2017.
 */

public class PatientAmountPOJO {
    @SerializedName("pt_id")
    String pt_id;
    @SerializedName("pt_p_id")
    String pt_p_id;
    @SerializedName("pt_amount")
    String pt_amount;
    @SerializedName("pt_submittedby")
    String pt_submittedby;
    @SerializedName("pt_deduetedby")
    String pt_deduetedby;
    @SerializedName("pt_date")
    String pt_date;
    @SerializedName("pt_time")
    String pt_time;
    @SerializedName("mode")
    String mode;
    @SerializedName("branch_code")
    String branch_code;
    @SerializedName("pt_reason")
    String pt_reason;
    @SerializedName("pt_total_amount")
    String pt_total_amount;
    @SerializedName("receipt_no")
    String receipt_no;

    public String getPt_id() {
        return pt_id;
    }

    public void setPt_id(String pt_id) {
        this.pt_id = pt_id;
    }

    public String getPt_p_id() {
        return pt_p_id;
    }

    public void setPt_p_id(String pt_p_id) {
        this.pt_p_id = pt_p_id;
    }

    public String getPt_amount() {
        return pt_amount;
    }

    public void setPt_amount(String pt_amount) {
        this.pt_amount = pt_amount;
    }

    public String getPt_submittedby() {
        return pt_submittedby;
    }

    public void setPt_submittedby(String pt_submittedby) {
        this.pt_submittedby = pt_submittedby;
    }

    public String getPt_deduetedby() {
        return pt_deduetedby;
    }

    public void setPt_deduetedby(String pt_deduetedby) {
        this.pt_deduetedby = pt_deduetedby;
    }

    public String getPt_date() {
        return pt_date;
    }

    public void setPt_date(String pt_date) {
        this.pt_date = pt_date;
    }

    public String getPt_time() {
        return pt_time;
    }

    public void setPt_time(String pt_time) {
        this.pt_time = pt_time;
    }

    public String getPt_reason() {
        return pt_reason;
    }

    public void setPt_reason(String pt_reason) {
        this.pt_reason = pt_reason;
    }

    public String getPt_total_amount() {
        return pt_total_amount;
    }

    public void setPt_total_amount(String pt_total_amount) {
        this.pt_total_amount = pt_total_amount;
    }

    public String getReceipt_no() {
        return receipt_no;
    }

    public void setReceipt_no(String receipt_no) {
        this.receipt_no = receipt_no;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getBranch_code() {
        return branch_code;
    }

    public void setBranch_code(String branch_code) {
        this.branch_code = branch_code;
    }
}
