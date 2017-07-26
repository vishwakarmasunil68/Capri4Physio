package com.capri4physio.model.chat;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 26-07-2017.
 */

public class ChatConPOJO {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<ChatPOJO> chatPOJOList;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ChatPOJO> getChatPOJOList() {
        return chatPOJOList;
    }

    public void setChatPOJOList(List<ChatPOJO> chatPOJOList) {
        this.chatPOJOList = chatPOJOList;
    }

    @Override
    public String toString() {
        return "ChatConPOJO{" +
                "success='" + success + '\'' +
                ", chatPOJOList=" + chatPOJOList +
                '}';
    }
}
