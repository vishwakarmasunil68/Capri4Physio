package com.capri4physio.model.treatment;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 14-08-2017.
 */

public class TreatmentPOJO implements Serializable{
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<TreatmentResultPOJO> treatmentResultPOJOList;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<TreatmentResultPOJO> getTreatmentResultPOJOList() {
        return treatmentResultPOJOList;
    }

    public void setTreatmentResultPOJOList(List<TreatmentResultPOJO> treatmentResultPOJOList) {
        this.treatmentResultPOJOList = treatmentResultPOJOList;
    }

    @Override
    public String toString() {
        return "TreatmentPOJO{" +
                "success='" + success + '\'' +
                ", treatmentResultPOJOList=" + treatmentResultPOJOList +
                '}';
    }
}
