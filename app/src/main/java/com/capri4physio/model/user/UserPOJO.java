package com.capri4physio.model.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sunil on 24-07-2017.
 */

public class UserPOJO {
    @SerializedName("id")
    private String id;
    @SerializedName("added_by")
    private String addedBy;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("address")
    private String address;
    @SerializedName("user_type")
    private String userType;
    @SerializedName("profile_pic")
    private Object profilePic;
    @SerializedName("lat")
    private String lat;
    @SerializedName("lng")
    private String lng;
    @SerializedName("device_type")
    private String deviceType;
    @SerializedName("device_token")
    private String deviceToken;
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("status")
    private String status;
    @SerializedName("show_password")
    private String showPassword;
    @SerializedName("created")
    private String created;
    @SerializedName("modified")
    private String modified;
    @SerializedName("dob")
    private Object dob;
    @SerializedName("gender")
    private String gender;
    @SerializedName("age")
    private Object age;
    @SerializedName("marital_status")
    private Object maritalStatus;
    @SerializedName("height")
    private String height;
    @SerializedName("weight")
    private String weight;
    @SerializedName("bmi")
    private String bmi;
    @SerializedName("food_habit")
    private String foodHabit;
    @SerializedName("occupation")
    private Object occupation;
    @SerializedName("aadhar_id")
    private String aadharId;
    @SerializedName("address2")
    private String address2;
    @SerializedName("city")
    private String city;
    @SerializedName("pincode")
    private Object pincode;
    @SerializedName("phone")
    private Object phone;
    @SerializedName("treatment_type")
    private String treatmentType;
    @SerializedName("patient_treament_type")
    private String patientTreamentType;
    @SerializedName("ref_source")
    private String refSource;
    @SerializedName("contact_person")
    private String contactPerson;
    @SerializedName("contact_person_mob")
    private String contactPersonMob;
    @SerializedName("patient_code")
    private String patientCode;
    @SerializedName("bracch_code")
    private String bracchCode;
    @SerializedName("designation")
    private String designation;
    @SerializedName("designation1")
    private String designation1;
    @SerializedName("qualifation")
    private String qualifation;
    @SerializedName("exprience")
    private String exprience;
    @SerializedName("datejoing")
    private String datejoing;
    @SerializedName("end_date_contract")
    private String endDateContract;
    @SerializedName("degree")
    private String degree;
    @SerializedName("institution")
    private String institution;
    @SerializedName("university")
    private String university;
    @SerializedName("duration")
    private String duration;
    @SerializedName("average")
    private String average;
    @SerializedName("organisation")
    private String organisation;
    @SerializedName("nature_of_work")
    private String natureOfWork;
    @SerializedName("admin_permisstion_status")
    private String adminPermisstionStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Object getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Object profilePic) {
        this.profilePic = profilePic;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShowPassword() {
        return showPassword;
    }

    public void setShowPassword(String showPassword) {
        this.showPassword = showPassword;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public Object getDob() {
        return dob;
    }

    public void setDob(Object dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Object getAge() {
        return age;
    }

    public void setAge(Object age) {
        this.age = age;
    }

    public Object getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Object maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getFoodHabit() {
        return foodHabit;
    }

    public void setFoodHabit(String foodHabit) {
        this.foodHabit = foodHabit;
    }

    public Object getOccupation() {
        return occupation;
    }

    public void setOccupation(Object occupation) {
        this.occupation = occupation;
    }

    public String getAadharId() {
        return aadharId;
    }

    public void setAadharId(String aadharId) {
        this.aadharId = aadharId;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Object getPincode() {
        return pincode;
    }

    public void setPincode(Object pincode) {
        this.pincode = pincode;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

    public String getPatientTreamentType() {
        return patientTreamentType;
    }

    public void setPatientTreamentType(String patientTreamentType) {
        this.patientTreamentType = patientTreamentType;
    }

    public String getRefSource() {
        return refSource;
    }

    public void setRefSource(String refSource) {
        this.refSource = refSource;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPersonMob() {
        return contactPersonMob;
    }

    public void setContactPersonMob(String contactPersonMob) {
        this.contactPersonMob = contactPersonMob;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public String getBracchCode() {
        return bracchCode;
    }

    public void setBracchCode(String bracchCode) {
        this.bracchCode = bracchCode;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDesignation1() {
        return designation1;
    }

    public void setDesignation1(String designation1) {
        this.designation1 = designation1;
    }

    public String getQualifation() {
        return qualifation;
    }

    public void setQualifation(String qualifation) {
        this.qualifation = qualifation;
    }

    public String getExprience() {
        return exprience;
    }

    public void setExprience(String exprience) {
        this.exprience = exprience;
    }

    public String getDatejoing() {
        return datejoing;
    }

    public void setDatejoing(String datejoing) {
        this.datejoing = datejoing;
    }

    public String getEndDateContract() {
        return endDateContract;
    }

    public void setEndDateContract(String endDateContract) {
        this.endDateContract = endDateContract;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getNatureOfWork() {
        return natureOfWork;
    }

    public void setNatureOfWork(String natureOfWork) {
        this.natureOfWork = natureOfWork;
    }

    public String getAdminPermisstionStatus() {
        return adminPermisstionStatus;
    }

    public void setAdminPermisstionStatus(String adminPermisstionStatus) {
        this.adminPermisstionStatus = adminPermisstionStatus;
    }

    @Override
    public String toString() {
        return "UserPOJO{" +
                "id='" + id + '\'' +
//                ", addedBy='" + addedBy + '\'' +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", mobile='" + mobile + '\'' +
//                ", email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                ", address='" + address + '\'' +
//                ", userType='" + userType + '\'' +
//                ", profilePic=" + profilePic +
//                ", lat='" + lat + '\'' +
//                ", lng='" + lng + '\'' +
//                ", deviceType='" + deviceType + '\'' +
//                ", deviceToken='" + deviceToken + '\'' +
//                ", accessToken='" + accessToken + '\'' +
//                ", status='" + status + '\'' +
//                ", showPassword='" + showPassword + '\'' +
//                ", created='" + created + '\'' +
//                ", modified='" + modified + '\'' +
//                ", dob=" + dob +
//                ", gender='" + gender + '\'' +
//                ", age=" + age +
//                ", maritalStatus=" + maritalStatus +
//                ", height='" + height + '\'' +
//                ", weight='" + weight + '\'' +
//                ", bmi='" + bmi + '\'' +
//                ", foodHabit='" + foodHabit + '\'' +
//                ", occupation=" + occupation +
//                ", aadharId='" + aadharId + '\'' +
//                ", address2='" + address2 + '\'' +
//                ", city='" + city + '\'' +
//                ", pincode=" + pincode +
//                ", phone=" + phone +
//                ", treatmentType='" + treatmentType + '\'' +
//                ", patientTreamentType='" + patientTreamentType + '\'' +
//                ", refSource='" + refSource + '\'' +
//                ", contactPerson='" + contactPerson + '\'' +
//                ", contactPersonMob='" + contactPersonMob + '\'' +
//                ", patientCode='" + patientCode + '\'' +
//                ", bracchCode='" + bracchCode + '\'' +
//                ", designation='" + designation + '\'' +
//                ", designation1='" + designation1 + '\'' +
//                ", qualifation='" + qualifation + '\'' +
//                ", exprience='" + exprience + '\'' +
//                ", datejoing='" + datejoing + '\'' +
//                ", endDateContract='" + endDateContract + '\'' +
//                ", degree='" + degree + '\'' +
//                ", institution='" + institution + '\'' +
//                ", university='" + university + '\'' +
//                ", duration='" + duration + '\'' +
//                ", average='" + average + '\'' +
//                ", organisation='" + organisation + '\'' +
//                ", natureOfWork='" + natureOfWork + '\'' +
//                ", adminPermisstionStatus='" + adminPermisstionStatus + '\'' +
                '}';
    }
}
