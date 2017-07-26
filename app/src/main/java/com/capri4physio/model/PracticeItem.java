package com.capri4physio.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by prabhunathyadav on 25/04/16.
 */


public class PracticeItem {

    @SerializedName("day")
    public String day;
    @SerializedName("start_time")
    public String startTime;
    @SerializedName("end_time")
    public String endTime;


    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
