package com.capri4physio.model.allchatusers;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 17-08-2017.
 */

public class AllUsersPOJO {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<AllUsersResultPOJO> allUsersResultPOJOs;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<AllUsersResultPOJO> getAllUsersResultPOJOs() {
        return allUsersResultPOJOs;
    }

    public void setAllUsersResultPOJOs(List<AllUsersResultPOJO> allUsersResultPOJOs) {
        this.allUsersResultPOJOs = allUsersResultPOJOs;
    }

    @Override
    public String toString() {
        return "AllUsersPOJO{" +
                "success='" + success + '\'' +
                ", allUsersResultPOJOs=" + allUsersResultPOJOs +
                '}';
    }
}
