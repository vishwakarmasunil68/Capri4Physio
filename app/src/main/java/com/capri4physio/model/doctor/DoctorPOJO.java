package com.capri4physio.model.doctor;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 02-08-2017.
 */

public class DoctorPOJO implements Serializable{
    @SerializedName("Success")
    String Success;
    @SerializedName("Result")
    List<DoctorResultPOJO> doctorResultPOJOList;

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }

    public List<DoctorResultPOJO> getDoctorResultPOJOList() {
        return doctorResultPOJOList;
    }

    public void setDoctorResultPOJOList(List<DoctorResultPOJO> doctorResultPOJOList) {
        this.doctorResultPOJOList = doctorResultPOJOList;
    }

    @Override
    public String toString() {
        return "DoctorPOJO{" +
                "Success='" + Success + '\'' +
                ", doctorResultPOJOList=" + doctorResultPOJOList +
                '}';
    }
}
