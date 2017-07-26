package com.capri4physio.model;

import com.google.gson.annotations.SerializedName;


/**
 * Data model to hold a login response
 *
 * @author prabhunathy
 * @version 1.0
 * @since 12/24/15.
 */
public class AvailibilityModel extends BaseModel {
    @SerializedName("result")
    public String result;

    public String getResult() {
        return result;
    }
}