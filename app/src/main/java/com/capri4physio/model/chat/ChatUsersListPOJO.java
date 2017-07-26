package com.capri4physio.model.chat;

import com.capri4physio.model.user.UserPOJO;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 25-07-2017.
 */

public class ChatUsersListPOJO {
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
        return "ChatUsersListPOJO{" +
                "success='" + success + '\'' +
                ", userPOJOList=" + userPOJOList +
                '}';
    }
}
