package com.capri4physio.model.treatmentgiven;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 30-08-2017.
 */

public class TreatmentGivenPOJO {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<TreatmentGivenResultPOJO> treatmentGivenResultPOJOList;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<TreatmentGivenResultPOJO> getTreatmentGivenResultPOJOList() {
        return treatmentGivenResultPOJOList;
    }

    public void setTreatmentGivenResultPOJOList(List<TreatmentGivenResultPOJO> treatmentGivenResultPOJOList) {
        this.treatmentGivenResultPOJOList = treatmentGivenResultPOJOList;
    }
}
