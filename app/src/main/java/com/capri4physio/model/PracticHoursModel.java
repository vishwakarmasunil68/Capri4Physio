package com.capri4physio.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prabhunathyadav on 25/05/16.
 */
public class PracticHoursModel extends BaseModel{

    @SerializedName("result")
    public List<PracticeItem> result = new ArrayList<>();
}