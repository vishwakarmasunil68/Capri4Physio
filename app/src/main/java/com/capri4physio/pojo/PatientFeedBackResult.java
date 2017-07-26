package com.capri4physio.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 27-12-2016.
 */

public class PatientFeedBackResult {
    @SerializedName("feedbackID")
    String feedbackID;
    @SerializedName("therapist_time")
    String therapist_time;
    @SerializedName("therapist_knowledge")
    String therapist_knowledge;
    @SerializedName("therapist_handlingskill")
    String therapist_handlingskill;
    @SerializedName("therapist_behavior")
    String therapist_behavior;
    @SerializedName("therapist_overal")
    String therapist_overal;
    @SerializedName("centre_time")
    String centre_time;
    @SerializedName("centre_facilities")
    String centre_facilities;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    @SerializedName("first_name")
    String first_name;
    @SerializedName("centre_cleanness")
    String centre_cleanness;
    @SerializedName("exp")
    String exp;

    public String getFeedback_total() {
        return feedback_total;
    }

    @SerializedName("feedback_total")
    String feedback_total;
    @SerializedName("feedback_dateadded")
    String feedback_dateadded;
    @SerializedName("feedback_p_id")
    String feedback_p_id;

    public String getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(String feedbackID) {
        this.feedbackID = feedbackID;
    }

    public String getTherapist_time() {
        return therapist_time;
    }

    public void setTherapist_time(String therapist_time) {
        this.therapist_time = therapist_time;
    }

    public String getTherapist_knowledge() {
        return therapist_knowledge;
    }

    public void setTherapist_knowledge(String therapist_knowledge) {
        this.therapist_knowledge = therapist_knowledge;
    }

    public String getTherapist_handlingskill() {
        return therapist_handlingskill;
    }

    public void setTherapist_handlingskill(String therapist_handlingskill) {
        this.therapist_handlingskill = therapist_handlingskill;
    }

    public String getTherapist_behavior() {
        return therapist_behavior;
    }

    public void setTherapist_behavior(String therapist_behavior) {
        this.therapist_behavior = therapist_behavior;
    }

    public String getTherapist_overal() {
        return therapist_overal;
    }

    public void setTherapist_overal(String therapist_overal) {
        this.therapist_overal = therapist_overal;
    }

    public String getCentre_time() {
        return centre_time;
    }

    public void setCentre_time(String centre_time) {
        this.centre_time = centre_time;
    }

    public String getCentre_facilities() {
        return centre_facilities;
    }

    public void setCentre_facilities(String centre_facilities) {
        this.centre_facilities = centre_facilities;
    }

    public String getCentre_cleanness() {
        return centre_cleanness;
    }

    public void setCentre_cleanness(String centre_cleanness) {
        this.centre_cleanness = centre_cleanness;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getFeedback_dateadded() {
        return feedback_dateadded;
    }

    public void setFeedback_dateadded(String feedback_dateadded) {
        this.feedback_dateadded = feedback_dateadded;
    }

    public String getFeedback_p_id() {
        return feedback_p_id;
    }

    public void setFeedback_p_id(String feedback_p_id) {
        this.feedback_p_id = feedback_p_id;
    }

    @Override
    public String toString() {
        return "PatientFeedBackResult{" +
                "feedbackID='" + feedbackID + '\'' +
                ", therapist_time='" + therapist_time + '\'' +
                ", therapist_knowledge='" + therapist_knowledge + '\'' +
                ", therapist_handlingskill='" + therapist_handlingskill + '\'' +
                ", therapist_behavior='" + therapist_behavior + '\'' +
                ", therapist_overal='" + therapist_overal + '\'' +
                ", centre_time='" + centre_time + '\'' +
                ", centre_facilities='" + centre_facilities + '\'' +
                ", centre_cleanness='" + centre_cleanness + '\'' +
                ", exp='" + exp + '\'' +
                ", feedback_dateadded='" + feedback_dateadded + '\'' +
                ", feedback_p_id='" + feedback_p_id + '\'' +
                '}';
    }
}
