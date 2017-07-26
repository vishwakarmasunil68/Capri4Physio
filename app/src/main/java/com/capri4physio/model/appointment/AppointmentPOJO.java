package com.capri4physio.model.appointment;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 25-07-2017.
 */

public class AppointmentPOJO {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<AppointmentResultPOJO> appointmentResultPOJOList;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<AppointmentResultPOJO> getAppointmentResultPOJOList() {
        return appointmentResultPOJOList;
    }

    public void setAppointmentResultPOJOList(List<AppointmentResultPOJO> appointmentResultPOJOList) {
        this.appointmentResultPOJOList = appointmentResultPOJOList;
    }

    @Override
    public String toString() {
        return "AppointmentPOJO{" +
                "success='" + success + '\'' +
                ", appointmentResultPOJOList=" + appointmentResultPOJOList +
                '}';
    }
}
