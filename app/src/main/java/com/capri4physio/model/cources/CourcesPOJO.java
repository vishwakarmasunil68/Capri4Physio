package com.capri4physio.model.cources;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 29-07-2017.
 */

public class CourcesPOJO implements Serializable {
    @SerializedName("Success")
    String Success;
    @SerializedName("Result")
    List<CourcesResultPOJO> courcesPOJOList;

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }

    public List<CourcesResultPOJO> getCourcesPOJOList() {
        return courcesPOJOList;
    }

    public void setCourcesPOJOList(List<CourcesResultPOJO> courcesPOJOList) {
        this.courcesPOJOList = courcesPOJOList;
    }

    @Override
    public String toString() {
        return "CourcesPOJO{" +
                "Success='" + Success + '\'' +
                ", courcesPOJOList=" + courcesPOJOList +
                '}';
    }
}
