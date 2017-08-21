package com.capri4physio.model.newappointment;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 03-08-2017.
 */

public class NewAppointmentPOJO {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<NewAppointmentResultPOJO> newAppointmentResultPOJOList;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<NewAppointmentResultPOJO> getNewAppointmentResultPOJOList() {
        return newAppointmentResultPOJOList;
    }

    public void setNewAppointmentResultPOJOList(List<NewAppointmentResultPOJO> newAppointmentResultPOJOList) {
        this.newAppointmentResultPOJOList = newAppointmentResultPOJOList;
    }

    @Override
    public String toString() {
        return "NewAppointmentPOJO{" +
                "success='" + success + '\'' +
                ", newAppointmentResultPOJOList=" + newAppointmentResultPOJOList +
                '}';
    }
}
