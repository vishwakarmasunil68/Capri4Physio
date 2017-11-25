package com.capri4physio.model.branch;

import java.io.Serializable;

/**
 * Created by sunil on 25-07-2017.
 */

public class BranchPOJO implements Serializable{
    String branch_id;
    String branch_name;
    String branch_code;
    String branch_status;
    String mobile;
    String phone;
    String email;
    String address;
    String city;
    String state;
    String country;
    String pincode;
    String website;

    public BranchPOJO(String branch_id, String branch_name, String branch_code, String branch_status) {
        this.branch_id = branch_id;
        this.branch_name = branch_name;
        this.branch_code = branch_code;
        this.branch_status = branch_status;
    }

    public BranchPOJO(String branch_id, String branch_name, String branch_code, String branch_status, String mobile, String phone, String email, String address, String city, String state, String country, String pincode, String website) {
        this.branch_id = branch_id;
        this.branch_name = branch_name;
        this.branch_code = branch_code;
        this.branch_status = branch_status;
        this.mobile = mobile;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
        this.website = website;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getBranch_code() {
        return branch_code;
    }

    public void setBranch_code(String branch_code) {
        this.branch_code = branch_code;
    }

    public String getBranch_status() {
        return branch_status;
    }

    public void setBranch_status(String branch_status) {
        this.branch_status = branch_status;
    }

    @Override
    public String toString() {
        return "BranchPOJO{" +
                "branch_id='" + branch_id + '\'' +
                ", branch_name='" + branch_name + '\'' +
                ", branch_code='" + branch_code + '\'' +
                ", branch_status='" + branch_status + '\'' +
                '}';
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
