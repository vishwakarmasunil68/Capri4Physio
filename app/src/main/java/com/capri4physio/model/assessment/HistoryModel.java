package com.capri4physio.model.assessment;

import com.capri4physio.model.BaseModel;
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
public class HistoryModel extends BaseModel {

    @SerializedName("result")
    public List<HistoryItem> result = new ArrayList<HistoryItem>();


}
