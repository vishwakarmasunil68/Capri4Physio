package com.capri4physio.model.assessment;

import com.google.gson.annotations.SerializedName;

/**
 * Created by prabhunathyadav on 05/06/16.
 */
public class InvestigationItem {

    @SerializedName("id")
    public String id;
    @SerializedName("patient_id")
    public String patientId;
    @SerializedName("report_type")
    public String reportType;
    @SerializedName("attachment")
    public String attachment;
    @SerializedName("description")
    public String description;
    @SerializedName("date")
    public String date;


    public String getId() {
        return id;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getReportType() {
        return reportType;
    }

    public String getAttachment() {
        return attachment;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }
}