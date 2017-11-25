package com.capri4physio.pojo.notes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 03-10-2017.
 */

public class NDTPOJO implements Serializable {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<NdtResultPOJO> ndtResultPOJOList;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<NdtResultPOJO> getNdtResultPOJOList() {
        return ndtResultPOJOList;
    }

    public void setNdtResultPOJOList(List<NdtResultPOJO> ndtResultPOJOList) {
        this.ndtResultPOJOList = ndtResultPOJOList;
    }
}
