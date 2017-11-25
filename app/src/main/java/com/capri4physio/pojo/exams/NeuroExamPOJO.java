package com.capri4physio.pojo.exams;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 29-09-2017.
 */

public class NeuroExamPOJO implements Serializable {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<NeuroExamResultPOJO> neuroExamResultPOJOList;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<NeuroExamResultPOJO> getNeuroExamResultPOJOList() {
        return neuroExamResultPOJOList;
    }

    public void setNeuroExamResultPOJOList(List<NeuroExamResultPOJO> neuroExamResultPOJOList) {
        this.neuroExamResultPOJOList = neuroExamResultPOJOList;
    }
}
