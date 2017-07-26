package com.capri4physio.model;

import com.google.gson.annotations.SerializedName;

/**
 * Data model to hold basic response status and message
 *
 * @version 1.0
 * @author prabhunathy
 * @since 12/24/15.
 */
public class BaseModel {
    @SerializedName("status")
    public Integer status;

    @SerializedName("message")
    public String message;


    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
