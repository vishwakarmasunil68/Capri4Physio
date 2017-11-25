package com.capri4physio.pojo.notes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 26-09-2017.
 */

public class ProgressNotePOJO {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<ProgressNoteResultPOJO> progressNoteResultPOJOList;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ProgressNoteResultPOJO> getProgressNoteResultPOJOList() {
        return progressNoteResultPOJOList;
    }

    public void setProgressNoteResultPOJOList(List<ProgressNoteResultPOJO> progressNoteResultPOJOList) {
        this.progressNoteResultPOJOList = progressNoteResultPOJOList;
    }
}
