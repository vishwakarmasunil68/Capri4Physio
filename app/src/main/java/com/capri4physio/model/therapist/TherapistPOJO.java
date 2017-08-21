package com.capri4physio.model.therapist;

/**
 * Created by sunil on 09-08-2017.
 */

public class TherapistPOJO {
    String id;
    String name;
    String email;
    String address;
    String device_token;
    String branch;

    public TherapistPOJO(String id, String name, String email, String address, String device_token, String branch) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.device_token = device_token;
        this.branch = branch;
    }

    public TherapistPOJO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    @Override
    public String toString() {
        return "TherapistPOJO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", device_token='" + device_token + '\'' +
                ", branch='" + branch + '\'' +
                '}';
    }
}
