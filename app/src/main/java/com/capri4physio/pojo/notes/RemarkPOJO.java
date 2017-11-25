package com.capri4physio.pojo.notes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 26-09-2017.
 */

public class RemarkPOJO {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<RemarkResultPOJO> remarkResultPOJOList;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<RemarkResultPOJO> getRemarkResultPOJOList() {
        return remarkResultPOJOList;
    }

    public void setRemarkResultPOJOList(List<RemarkResultPOJO> remarkResultPOJOList) {
        this.remarkResultPOJOList = remarkResultPOJOList;
    }
}
