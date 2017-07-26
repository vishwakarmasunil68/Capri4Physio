package com.capri4physio.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by prabhunathyadav on 04/05/16.
 */
public class ClinicDetails {
    @SerializedName("id")
    public String id;
    @SerializedName("user_id")
    public String userId;
    @SerializedName("clinic_name")
    public String clinicName;
    @SerializedName("short_description")
    public String shortDescription;
    @SerializedName("description")
    public String description;
    @SerializedName("location")
    public String location;
    @SerializedName("profile_pic")
    public String profilePic;
    @SerializedName("lat")
    public String lat;
    @SerializedName("lng")
    public String lng;
    @SerializedName("rating")
    public String rating;
    @SerializedName("status")
    public String status;
    @SerializedName("created")
    public String created;


    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getClinicName() {
        return clinicName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getRating() {
        return rating;
    }

    public String getStatus() {
        return status;
    }

    public String getCreated() {
        return created;
    }
}