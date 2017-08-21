package com.capri4physio.model.cources;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sunil on 29-07-2017.
 */

public class CourcesResultPOJO implements Serializable{
    @SerializedName("c_id")
    String c_id;
    @SerializedName("c_name")
    String c_name;
    @SerializedName("c_comt")
    String c_comt;
    @SerializedName("c_from_date")
    String c_from_date;
    @SerializedName("c_to_date")
    String c_to_date;
    @SerializedName("c_place")
    String c_place;
    @SerializedName("c_sheet_available")
    String c_sheet_available;
    @SerializedName("c_rem_seat")
    String c_rem_seat;
    @SerializedName("c_showing_sheet")
    String c_showing_sheet;
    @SerializedName("c_fees")
    String c_fees;
    @SerializedName("c_reg_fees")
    String c_reg_fees;
    @SerializedName("c_rem_fees")
    String c_rem_fees;
    @SerializedName("c_pno")
    String c_pno;


    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_comt() {
        return c_comt;
    }

    public void setC_comt(String c_comt) {
        this.c_comt = c_comt;
    }

    public String getC_from_date() {
        return c_from_date;
    }

    public void setC_from_date(String c_from_date) {
        this.c_from_date = c_from_date;
    }

    public String getC_to_date() {
        return c_to_date;
    }

    public void setC_to_date(String c_to_date) {
        this.c_to_date = c_to_date;
    }

    public String getC_place() {
        return c_place;
    }

    public void setC_place(String c_place) {
        this.c_place = c_place;
    }

    public String getC_sheet_available() {
        return c_sheet_available;
    }

    public void setC_sheet_available(String c_sheet_available) {
        this.c_sheet_available = c_sheet_available;
    }

    public String getC_rem_seat() {
        return c_rem_seat;
    }

    public void setC_rem_seat(String c_rem_seat) {
        this.c_rem_seat = c_rem_seat;
    }

    public String getC_showing_sheet() {
        return c_showing_sheet;
    }

    public void setC_showing_sheet(String c_showing_sheet) {
        this.c_showing_sheet = c_showing_sheet;
    }

    public String getC_fees() {
        return c_fees;
    }

    public void setC_fees(String c_fees) {
        this.c_fees = c_fees;
    }

    public String getC_reg_fees() {
        return c_reg_fees;
    }

    public void setC_reg_fees(String c_reg_fees) {
        this.c_reg_fees = c_reg_fees;
    }

    public String getC_rem_fees() {
        return c_rem_fees;
    }

    public void setC_rem_fees(String c_rem_fees) {
        this.c_rem_fees = c_rem_fees;
    }

    public String getC_pno() {
        return c_pno;
    }

    public void setC_pno(String c_pno) {
        this.c_pno = c_pno;
    }

    @Override
    public String toString() {
        return "CourcesResultPOJO{" +
                "c_id='" + c_id + '\'' +
                ", c_name='" + c_name + '\'' +
                ", c_comt='" + c_comt + '\'' +
                ", c_from_date='" + c_from_date + '\'' +
                ", c_to_date='" + c_to_date + '\'' +
                ", c_place='" + c_place + '\'' +
                ", c_sheet_available='" + c_sheet_available + '\'' +
                ", c_rem_seat='" + c_rem_seat + '\'' +
                ", c_showing_sheet='" + c_showing_sheet + '\'' +
                ", c_fees='" + c_fees + '\'' +
                ", c_reg_fees='" + c_reg_fees + '\'' +
                ", c_rem_fees='" + c_rem_fees + '\'' +
                ", c_pno='" + c_pno + '\'' +
                '}';
    }
}
