package com.capri4physio.pojo.notes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 03-10-2017.
 */

public class ADLPOJO implements Serializable {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<ADLResultPOJO> adlResultPOJOList;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ADLResultPOJO> getAdlResultPOJOList() {
        return adlResultPOJOList;
    }

    public void setAdlResultPOJOList(List<ADLResultPOJO> adlResultPOJOList) {
        this.adlResultPOJOList = adlResultPOJOList;
    }
}
