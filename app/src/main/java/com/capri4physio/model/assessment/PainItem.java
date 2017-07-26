package com.capri4physio.model.assessment;

import com.google.gson.annotations.SerializedName;

/**
 * Created by prabhunathyadav on 05/06/16.
 */
public class PainItem {

    @SerializedName("id")
    public String id;
    @SerializedName("patient_id")
    public String patientId;
    @SerializedName("pain_side")
    public String painSide;
    @SerializedName("severity_pain")
    public String severityPain;
    @SerializedName("pressure_pain")
    public String pressurePain;
    @SerializedName("threshold_site")
    public String thresholdSite;
    @SerializedName("pain_nature")
    public String painNature;
    @SerializedName("pain_onset")
    public String painOnset;
    @SerializedName("pain_duration")
    public String painDuration;
    @SerializedName("location")
    public String location;
    @SerializedName("diurnal_variations")
    public String diurnalVariations;
    @SerializedName("trigger_point")
    public String triggerPoint;
    @SerializedName("aggravating_factors")
    public String aggravatingFactors;
    @SerializedName("relieving_factors")
    public String relievingFactors;
    @SerializedName("da" +
            "te")
    public String date;

    public String getId() {
        return id;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getPainSide() {
        return painSide;
    }

    public String getSeverityPain() {
        return severityPain;
    }

    public String getPressurePain() {
        return pressurePain;
    }

    public String getThresholdSite() {
        return thresholdSite;
    }

    public String getPainNature() {
        return painNature;
    }

    public String getPainOnset() {
        return painOnset;
    }

    public String getPainDuration() {
        return painDuration;
    }

    public String getLocation() {
        return location;
    }

    public String getDiurnalVariations() {
        return diurnalVariations;
    }

    public String getTriggerPoint() {
        return triggerPoint;
    }

    public String getAggravatingFactors() {
        return aggravatingFactors;
    }

    public String getRelievingFactors() {
        return relievingFactors;
    }

    public String getDate() {
        return date;
    }
}