package com.capri4physio.pojo.notes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 03-10-2017.
 */

public class NeuroExamPOJO implements Serializable{
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<com.capri4physio.pojo.notes.NeuroExamResultPOJO> neuroExamResultPOJOList;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }


    public List<com.capri4physio.pojo.notes.NeuroExamResultPOJO> getNeuroExamResultPOJOList() {
        return neuroExamResultPOJOList;
    }

    public void setNeuroExamResultPOJOList(List<com.capri4physio.pojo.notes.NeuroExamResultPOJO> neuroExamResultPOJOList) {
        this.neuroExamResultPOJOList = neuroExamResultPOJOList;
    }
}
