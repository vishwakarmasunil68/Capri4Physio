package com.capri4physio.model;

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
public class UserListModel extends BaseModel {

    @SerializedName("result")
    public List<UserItem> result = new ArrayList<UserItem>();

}