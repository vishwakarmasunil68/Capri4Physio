package com.capri4physio.model.allchatusers;

import com.capri4physio.model.chat.ChatPOJO;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunil on 17-08-2017.
 */

public class AllChatPOJO implements Serializable{
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
        return "AllChatPOJO{" +
                "success='" + success + '\'' +
                ", chatPOJOList=" + chatPOJOList +
                '}';
    }
}
