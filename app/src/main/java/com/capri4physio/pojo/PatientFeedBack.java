package com.capri4physio.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 27-12-2016.
 */

public class PatientFeedBack {
    @SerializedName("success")
    String success;
    @SerializedName("Result")
    List<PatientFeedBackResult> result;

    @Override
    public String toString() {
        return "PatientFeedBack{" +
                "success='" + success + '\'' +
                ", result=" + result +
                '}';
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<PatientFeedBackResult> getResult() {
        return result;
    }

    public void setResult(List<PatientFeedBackResult> result) {
        this.result = result;
    }
}
