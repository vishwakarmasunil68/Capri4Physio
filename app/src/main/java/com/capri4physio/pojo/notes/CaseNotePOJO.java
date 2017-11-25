package com.capri4physio.pojo.notes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 26-09-2017.
 */

public class CaseNotePOJO {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<CaseNoteResultPOJO> caseNoteResultPOJOList;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<CaseNoteResultPOJO> getCaseNoteResultPOJOList() {
        return caseNoteResultPOJOList;
    }

    public void setCaseNoteResultPOJOList(List<CaseNoteResultPOJO> caseNoteResultPOJOList) {
        this.caseNoteResultPOJOList = caseNoteResultPOJOList;
    }
}
