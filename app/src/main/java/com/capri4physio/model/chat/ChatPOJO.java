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
    @SerializedName("chat_type")
    String chat_type;
    @SerializedName("chat_thumb")
    String chat_thumb;
    @SerializedName("chat_file")
    String chat_file;
    @SerializedName("admin")
    String admin;

    public ChatPOJO(String chat_id, String chat_user_id, String chat_fri_id, String chat_date, String chat_time, String chat_msg, String chat_type, String chat_file,String chat_thumb, String admin) {
        this.chat_id = chat_id;
        this.chat_user_id = chat_user_id;
        this.chat_fri_id = chat_fri_id;
        this.chat_date = chat_date;
        this.chat_time = chat_time;
        this.chat_msg = chat_msg;
        this.chat_type = chat_type;
        this.chat_thumb = chat_thumb;
        this.chat_file = chat_file;
        this.admin = admin;
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

    public String getChat_type() {
        return chat_type;
    }

    public void setChat_type(String chat_type) {
        this.chat_type = chat_type;
    }

    public String getChat_file() {
        return chat_file;
    }

    public void setChat_file(String chat_file) {
        this.chat_file = chat_file;
    }

    public String getChat_thumb() {
        return chat_thumb;
    }

    public void setChat_thumb(String chat_thumb) {
        this.chat_thumb = chat_thumb;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "ChatPOJO{" +
                "chat_id='" + chat_id + '\'' +
                ", chat_user_id='" + chat_user_id + '\'' +
                ", chat_fri_id='" + chat_fri_id + '\'' +
                ", chat_date='" + chat_date + '\'' +
                ", chat_time='" + chat_time + '\'' +
                ", chat_msg='" + chat_msg + '\'' +
                ", chat_type='" + chat_type + '\'' +
                ", chat_thumb='" + chat_thumb + '\'' +
                ", chat_file='" + chat_file + '\'' +
                ", admin='" + admin + '\'' +
                '}';
    }
}
