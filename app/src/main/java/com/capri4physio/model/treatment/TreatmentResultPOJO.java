package com.capri4physio.model.treatment;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 14-08-2017.
 */

public class TreatmentResultPOJO implements Serializable{
    @SerializedName("id")
    String id;
    @SerializedName("treatment_name")
    String treatment_name;
    @SerializedName("treatment_price")
    String treatment_price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTreatment_name() {
        return treatment_name;
    }

    public void setTreatment_name(String treatment_name) {
        this.treatment_name = treatment_name;
    }

    public String getTreatment_price() {
        return treatment_price;
    }

    public void setTreatment_price(String treatment_price) {
        this.treatment_price = treatment_price;
    }

    @Override
    public String toString() {
        return "TreatmentResultPOJO{" +
                "id='" + id + '\'' +
                ", treatment_name='" + treatment_name + '\'' +
                ", treatment_price='" + treatment_price + '\'' +
                '}';
    }
}
