package com.capri4physio.model.search;

import com.capri4physio.model.user.UserPOJO;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 24-07-2017.
 */

public class SearchPOJO {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<UserPOJO> userPOJOList;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<UserPOJO> getUserPOJOList() {
        return userPOJOList;
    }

    public void setUserPOJOList(List<UserPOJO> userPOJOList) {
        this.userPOJOList = userPOJOList;
    }

    @Override
    public String toString() {
        return "SearchPOJO{" +
                "success='" + success + '\'' +
                ", userPOJOList=" + userPOJOList +
                '}';
    }
}
