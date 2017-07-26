package com.capri4physio.model.branch;

/**
 * Created by sunil on 25-07-2017.
 */

public class BranchPOJO {
    String branch_id;
    String branch_name;
    String branch_code;
    String branch_status;

    public BranchPOJO(String branch_id, String branch_name, String branch_code, String branch_status) {
        this.branch_id = branch_id;
        this.branch_name = branch_name;
        this.branch_code = branch_code;
        this.branch_status = branch_status;
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
}
