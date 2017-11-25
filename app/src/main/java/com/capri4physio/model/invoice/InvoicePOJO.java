package com.capri4physio.model.invoice;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sunil on 29-08-2017.
 */

public class InvoicePOJO {
    @SerializedName("success")
    String success;
    @SerializedName("result")
    List<InvoiceResultPOJO> invoiceResultPOJOList;
    @SerializedName("total")
    int total;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<InvoiceResultPOJO> getInvoiceResultPOJOList() {
        return invoiceResultPOJOList;
    }

    public void setInvoiceResultPOJOList(List<InvoiceResultPOJO> invoiceResultPOJOList) {
        this.invoiceResultPOJOList = invoiceResultPOJOList;
    }

    @Override
    public String toString() {
        return "InvoicePOJO{" +
                "success='" + success + '\'' +
                ", invoiceResultPOJOList=" + invoiceResultPOJOList +
                '}';
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
