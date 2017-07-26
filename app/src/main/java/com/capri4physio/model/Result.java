package com.capri4physio.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by prabhunathyadav on 25/04/16.
 */


public class Result {

    @SerializedName("User")
    public UserDetails User;

    public UserDetails getUser() {
        return User;
    }

    public void setUser(UserDetails user) {
        User = user;
    }

    @Override
    public String toString() {
        return "Result{" +
                "User=" + User +
                '}';
    }
}
