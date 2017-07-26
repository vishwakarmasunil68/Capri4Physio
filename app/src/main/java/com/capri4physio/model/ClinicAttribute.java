package com.capri4physio.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by prabhunathyadav on 02/05/16.
 */
public class ClinicAttribute {

    @SerializedName("id")
    public String id;
    @SerializedName("clinic_name")
    public String clinicName;
    @SerializedName("short_description")
    public String shortDescription;
    @SerializedName("lat")
    public String lat;
    @SerializedName("lng")
    public String lng;
    @SerializedName("rating")
    public Object rating;

    public String getId() {
        return id;
    }

    public String getClinicName() {
        return clinicName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public Object getRating() {
        return rating;
    }
}
