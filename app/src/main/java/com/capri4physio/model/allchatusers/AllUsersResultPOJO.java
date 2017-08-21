package com.capri4physio.model.allchatusers;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 17-08-2017.
 */

public class AllUsersResultPOJO {
    @SerializedName("value1")
    String value1;
    @SerializedName("value2")
    String value2;
    @SerializedName("user1first")
    String user1first;
    @SerializedName("user1last")
    String user1last;
    @SerializedName("user2first")
    String user2first;
    @SerializedName("user2last")
    String user2last;

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getUser1first() {
        return user1first;
    }

    public void setUser1first(String user1first) {
        this.user1first = user1first;
    }

    public String getUser1last() {
        return user1last;
    }

    public void setUser1last(String user1last) {
        this.user1last = user1last;
    }

    public String getUser2first() {
        return user2first;
    }

    public void setUser2first(String user2first) {
        this.user2first = user2first;
    }

    public String getUser2last() {
        return user2last;
    }

    public void setUser2last(String user2last) {
        this.user2last = user2last;
    }

    @Override
    public String toString() {
        return "AllUsersResultPOJO{" +
                "value1='" + value1 + '\'' +
                ", value2='" + value2 + '\'' +
                ", user1first='" + user1first + '\'' +
                ", user1last='" + user1last + '\'' +
                ", user2first='" + user2first + '\'' +
                ", user2last='" + user2last + '\'' +
                '}';
    }
}
