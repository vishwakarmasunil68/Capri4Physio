package com.capri4physio.model.assessment;

import com.google.gson.annotations.SerializedName;

/**
 * Created by prabhunathyadav on 05/06/16.
 */
public class HistoryItem {

    @SerializedName("id")
    public String id;
    @SerializedName("diabetes")
    public String diabetes;
    @SerializedName("blood_pressure")
    public String blood_pressure;
    public String getBp() {
        return bp;
    }
    @SerializedName("bp")
    public String bp;
    @SerializedName("smoking")
    public String smoking;
    @SerializedName("fever_and_chill")
    public String fever_and_chill;
    @SerializedName("heart_diseases")
    public String heart_diseases;
    @SerializedName("bleeding_disorder")
    public String bleeding_disorder;
    @SerializedName("recent_infection")
    public String recent_infection;
    @SerializedName("pregnancy")
    public String pregnancy;
    @SerializedName("htn")
    public String htn;
    @SerializedName("tb")
    public String tb;
    @SerializedName("cancer")
    public String cancer;
    @SerializedName("hiv_aids")
    public String hiv_aids;
    @SerializedName("past_surgery")
    public String past_surgery;
    @SerializedName("allergies")
    public String allergies;
    @SerializedName("osteoporotic")
    public String osteoporotic;
    @SerializedName("depression")
    public String depression;
    @SerializedName("hepatitis")
    public String hepatitis;
    @SerializedName("any_implants")
    public String any_implants;
    @SerializedName("present_illness")
    public String present_illness;
    @SerializedName("past_illness")
    public String past_illness;


    public String getDiabetes() {
        return diabetes;
    }

    public String getBlood_pressure() {
        return blood_pressure;
    }

    public String getSmoking() {
        return smoking;
    }

    public String getFever_and_chill() {
        return fever_and_chill;
    }

    public String getHeart_diseases() {
        return heart_diseases;
    }

    public String getBleeding_disorder() {
        return bleeding_disorder;
    }

    public String getRecent_infection() {
        return recent_infection;
    }

    public String getPregnancy() {
        return pregnancy;
    }

    public String getHtn() {
        return htn;
    }

    public String getTb() {
        return tb;
    }

    public String getCancer() {
        return cancer;
    }

    public String getHiv_aids() {
        return hiv_aids;
    }

    public String getPast_surgery() {
        return past_surgery;
    }

    public String getAllergies() {
        return allergies;
    }

    public String getOsteoporotic() {
        return osteoporotic;
    }

    public String getDepression() {
        return depression;
    }

    public String getHepatitis() {
        return hepatitis;
    }

    public String getAny_implants() {
        return any_implants;
    }

    public String getHereditary_disease() {
        return hereditary_disease;
    }

    @SerializedName("hereditary_disease")
    public String hereditary_disease;

    @SerializedName("patient_id")
    public String patientId;
    @SerializedName("medical_history")
    public String medicalHistory;
    @SerializedName("surgical_history")
    public String surgicalHistory;
    @SerializedName("other_history")
    public String otherHistory;
    @SerializedName("medicine_used")
    public String medicineUsed;
    @SerializedName("date")
    public String date;

    public String getId() {
        return id;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public String getSurgicalHistory() {
        return surgicalHistory;
    }

    public String getOtherHistory() {
        return otherHistory;
    }

    public String getMedicineUsed() {
        return medicineUsed;
    }

    public String getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPresent_illness() {
        return present_illness;
    }

    public void setPresent_illness(String present_illness) {
        this.present_illness = present_illness;
    }

    public String getPast_illness() {
        return past_illness;
    }

    public void setPast_illness(String past_illness) {
        this.past_illness = past_illness;
    }
}
