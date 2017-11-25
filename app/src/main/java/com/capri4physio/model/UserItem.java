package com.capri4physio.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by prabhunathyadav on 28/04/16.
 */
public class UserItem {

    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("email")
    public String email;
    @SerializedName("profile_pic")
    public String profilePic;
    @SerializedName("mobile")
    public String mobile;
    @SerializedName("bracch_code")
    public String bracch_code;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getMobile() {
        return mobile;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBracch_code() {
        return bracch_code;
    }

    public void setBracch_code(String bracch_code) {
        this.bracch_code = bracch_code;
    }
}