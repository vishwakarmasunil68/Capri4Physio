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
public class ClinicsModel extends BaseModel {
    @SerializedName("result")
    public List<ClinicAttribute> result = new ArrayList<ClinicAttribute>();


    public List<ClinicAttribute> getResult() {
        return result;
    }

    public void setResult(List<ClinicAttribute> result) {
        this.result = result;
    }
}