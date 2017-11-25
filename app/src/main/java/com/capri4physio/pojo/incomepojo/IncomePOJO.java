package com.capri4physio.pojo.incomepojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 07-09-2017.
 */

public class IncomePOJO {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<IncomeResultPOJO> incomeResultPOJOList;
    @SerializedName("total")
    int total;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<IncomeResultPOJO> getIncomeResultPOJOList() {
        return incomeResultPOJOList;
    }

    public void setIncomeResultPOJOList(List<IncomeResultPOJO> incomeResultPOJOList) {
        this.incomeResultPOJOList = incomeResultPOJOList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
