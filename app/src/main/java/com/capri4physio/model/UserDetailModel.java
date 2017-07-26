package com.capri4physio.model;

import com.google.gson.annotations.SerializedName;


/**
 * Data model to hold a login response
 *
 * @author prabhunathy
 * @version 1.0
 * @since 12/24/15.
 */
public class UserDetailModel extends BaseModel {
    @SerializedName("result")
    public Result result;
    @SerializedName("otp")
    public String otp;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public String toString() {
        return "UserDetailModel{" +
                "result=" + result +
                ", otp='" + otp + '\'' +
                '}';
    }
}
