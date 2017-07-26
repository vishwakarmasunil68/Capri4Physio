package com.capri4physio.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


/**
 * Data model to hold a login response
 *
 * @author prabhunathy
 * @version 1.0
 * @since 12/24/15.
 */
public class ClinicsDetailsModel extends BaseModel {
    @SerializedName("result")
    public ClinicDetails result;

    public ClinicDetails getResult() {
        return result;
    }
}