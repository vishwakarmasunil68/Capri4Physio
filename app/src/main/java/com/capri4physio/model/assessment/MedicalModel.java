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
public class MedicalModel extends BaseModel {

    @SerializedName("result")
    public List<MedicalItem> result = new ArrayList<MedicalItem>();


}
