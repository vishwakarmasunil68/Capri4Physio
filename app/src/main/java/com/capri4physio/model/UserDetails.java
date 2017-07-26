package com.capri4physio.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by prabhunathyadav on 28/04/16.
 */
public class UserDetails implements Serializable{

    @SerializedName("id")
    public String id;
    @SerializedName("added_by")
    public Object addedBy;
    @SerializedName("first_name")
    public String firstName;
    @SerializedName("last_name")
    public String lastName;
    @SerializedName("mobile")
    public String mobile;
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;
    @SerializedName("address")
    public String address;
    @SerializedName("user_type")
    public String userType;
    @SerializedName("profile_pic")
    public String profilePic;
    @SerializedName("lat")
    public String lat;
    @SerializedName("lng")
    public String lng;
    @SerializedName("device_type")
    public String deviceType;
    @SerializedName("device_token")
    public String deviceToken;
    @SerializedName("access_token")
    public String accessToken;
    @SerializedName("status")
    public String status;

    public String getAdmin_permisstion_status() {
        return admin_permisstion_status;
    }

    @SerializedName("admin_permisstion_status")
    public String admin_permisstion_status;
    @SerializedName("show_password")
    public String showPassword;
    @SerializedName("created")
    public String created;
    @SerializedName("modified")
    public String modified;
    @SerializedName("dob")
    public String dob;
    @SerializedName("gender")
    public String gender;
    @SerializedName("age")
    public String age;
    @SerializedName("marital_status")
    public String maritalStatus;
    @SerializedName("height")
    public String height;
    @SerializedName("weight")
    public String weight;
    @SerializedName("bmi")
    public String bmi;
    @SerializedName("food_habit")
    public String foodHabit;
    @SerializedName("occupation")
    public String occupation;
    @SerializedName("aadhar_id")
    public String aadharId;
    @SerializedName("address2")
    public String address2;
    @SerializedName("city")
    public String city;



    @SerializedName("bracch_code")
    public String bracch_code;
    @SerializedName("pincode")
    public String pincode;
    @SerializedName("phone")
    public String phone;
    @SerializedName("cliniccount")
    public Integer cliniccount;
    @SerializedName("clinic_id")
    public String clinicId;

    public String getContact_person() {
        return contact_person;
    }

    public String getContact_person_mob() {
        return contact_person_mob;
    }

    @SerializedName("contact_person")
    public String contact_person;
    @SerializedName("contact_person_mob")
    public String contact_person_mob;

    public String getRef_source() {
        return ref_source;
    }

    @SerializedName("ref_source")
    public String ref_source;

    public String getTreatment_type() {
        return treatment_type;
    }

    @SerializedName("treatment_type")
    public String treatment_type;

    public String getPatient_code() {
        return patient_code;
    }

    @SerializedName("patient_code")
    public String patient_code;

    public String getId() {
        return id;
    }

    public Object getAddedBy() {
        return addedBy;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getUserType() {
        return userType;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getStatus() {
        return status;
    }

    public String getShowPassword() {
        return showPassword;
    }

    public String getCreated() {
        return created;
    }

    public String getModified() {
        return modified;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getBmi() {
        return bmi;
    }

    public String getFoodHabit() {
        return foodHabit;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getAadharId() {
        return aadharId;
    }

    public String getAddress2() {
        return address2;
    }

    public String getCity() {
        return city;
    }

    public String getPincode() {
        return pincode;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getCliniccount() {
        return cliniccount;
    }

    public String getClinicId() {
        return clinicId;
    }

    public String getBracch_code() {
        return bracch_code;
    }

    public void setBracch_code(String bracch_code) {
        this.bracch_code = bracch_code;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "id='" + id + '\'' +
                ", addedBy=" + addedBy +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", userType='" + userType + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", deviceToken='" + deviceToken + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", status='" + status + '\'' +
                ", admin_permisstion_status='" + admin_permisstion_status + '\'' +
                ", showPassword='" + showPassword + '\'' +
                ", created='" + created + '\'' +
                ", modified='" + modified + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", age='" + age + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", bmi='" + bmi + '\'' +
                ", foodHabit='" + foodHabit + '\'' +
                ", occupation='" + occupation + '\'' +
                ", aadharId='" + aadharId + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", bracch_code='" + bracch_code + '\'' +
                ", pincode='" + pincode + '\'' +
                ", phone='" + phone + '\'' +
                ", cliniccount=" + cliniccount +
                ", clinicId='" + clinicId + '\'' +
                ", contact_person='" + contact_person + '\'' +
                ", contact_person_mob='" + contact_person_mob + '\'' +
                ", ref_source='" + ref_source + '\'' +
                ", treatment_type='" + treatment_type + '\'' +
                ", patient_code='" + patient_code + '\'' +
                '}';
    }
}