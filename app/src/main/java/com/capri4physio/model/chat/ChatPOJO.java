package com.capri4physio.model.chat;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 24-07-2017.
 */

public class ChatPOJO {
    @SerializedName("chat_id")
    String chat_id;
    @SerializedName("chat_user_id")
    String chat_user_id;
    @SerializedName("chat_fri_id")
    String chat_fri_id;
    @SerializedName("chat_date")
    String chat_date;
    @SerializedName("chat_time")
    String chat_time;
    @SerializedName("chat_msg")
    String chat_msg;

    public ChatPOJO(String chat_id, String chat_user_id, String chat_fri_id, String chat_date, String chat_time, String chat_msg) {
        this.chat_id = chat_id;
        this.chat_user_id = chat_user_id;
        this.chat_fri_id = chat_fri_id;
        this.chat_date = chat_date;
        this.chat_time = chat_time;
        this.chat_msg = chat_msg;
    }

    public ChatPOJO() {
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public String getChat_user_id() {
        return chat_user_id;
    }

    public void setChat_user_id(String chat_user_id) {
        this.chat_user_id = chat_user_id;
    }

    public String getChat_fri_id() {
        return chat_fri_id;
    }

    public void setChat_fri_id(String chat_fri_id) {
        this.chat_fri_id = chat_fri_id;
    }

    public String getChat_date() {
        return chat_date;
    }

    public void setChat_date(String chat_date) {
        this.chat_date = chat_date;
    }

    public String getChat_time() {
        return chat_time;
    }

    public void setChat_time(String chat_time) {
        this.chat_time = chat_time;
    }

    public String getChat_msg() {
        return chat_msg;
    }

    public void setChat_msg(String chat_msg) {
        this.chat_msg = chat_msg;
    }

    @Override
    public String toString() {
        return "ChatPOJO{" +
                "chat_id='" + chat_id + '\'' +
                ", chat_user_id='" + chat_user_id + '\'' +
                ", chat_fri_id='" + chat_fri_id + '\'' +
//                ", chat_date='" + chat_date + '\'' +
//                ", chat_time='" + chat_time + '\'' +
//                ", chat_msg='" + chat_msg + '\'' +
                '}';
    }
}
