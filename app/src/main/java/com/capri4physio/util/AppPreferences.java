package com.capri4physio.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Shared Preferences  class to  store linear type data.
 *
 * @author prabhunathy
 * @version 1.0
 * @since 12/22/15.
 */
public class AppPreferences {
    private static AppPreferences instance;
    private SharedPreferences sPreferences;
    private SharedPreferences.Editor sEditor;
    private String JOBSWOLF_PREFERENCES = "CapriPreferences";

    //Preference Key
    private final String LOGIN_STATUS = "loginStatus";
    private final String CHAT_SYNC = "chat_sync";
    private final String USERID = "userId";




    private final String USERDATE = "userdate";
    private final String USER_TYPE = "user_type";
    private final String USER_PASSWR = "user_pass";
    private final String USER_NAME = "userName";
    private final String FIRST_NAME = "firstName";
    private final String USER_AGE = "userAge";
    private final String LAST_NAME = "lastName";
    private final String USER_EMAIL = "userEmail";

    public String getUSER_BRANCH_CODE() {
        return sPreferences.getString(USER_BRANCH_CODE, "");
    }

    private final String USER_BRANCH_CODE = "bracch_code";
    private final String USER_MOBILE = "userMobile";
    private final String USER_PIC= "userPic";
    private final String USER_PAYMENT_STATUS= "userpaymentstatus";


    private final String USER_ADMIN_STATUS= "admin_permisstion_status";
    private final String USER_CLINIC_COUNT = "clincCount";
    private final String USER_CLINIC_ID = "clincId";
    private final String USER_DETAILS = "userDetails";
    private final String OTP_VERIFIED = "otpverified";
    private final String START_TIME = "start_time";
    private final String END_TIME = "end_time";
    private final String TREATMENT_TYPE = "treatment_type";

    private AppPreferences(Context ctx) {
        sPreferences = ctx.getSharedPreferences(JOBSWOLF_PREFERENCES, MODE_PRIVATE);
        sEditor = sPreferences.edit();
    }

    public static AppPreferences getInstance(Context ctx) {
        if (instance == null) {
            synchronized (AppPreferences.class) {
                if (instance == null) {
                    instance = new AppPreferences(ctx);
                }
            }
        }
        return instance;
    }

    public void clearAllData() {
        sEditor.clear();
        sEditor.commit();
    }

    public boolean isUserLogin() {
        return sPreferences.getBoolean(LOGIN_STATUS, false);
    }

    public boolean isChatSync() {
        return sPreferences.getBoolean(CHAT_SYNC, false);
    }

    public void setUserLogin(boolean isUserLogin) {
        sEditor.putBoolean(LOGIN_STATUS, isUserLogin);
        sEditor.commit();
    }
    public void setChatSync(boolean chatSync) {
        sEditor.putBoolean(CHAT_SYNC, chatSync);
        sEditor.commit();
    }
    public static void SaveDevicetoken(Context context,String value){
        SharedPreferences sharedPreferences=context.getSharedPreferences("devicetokencapri.txt",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("devicetoken",value);
        editor.commit();
    }
    public static String GetDeviceToken(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("devicetokencapri.txt",Context.MODE_PRIVATE);
        return sharedPreferences.getString("devicetoken","");
    }


    public void setUSER_BRANCH_CODE(String id) {
        sEditor.putString(USER_BRANCH_CODE, id);
        sEditor.commit();
    }
    public String getUserID() {
        return sPreferences.getString(USERID, null);
    }

    public void setUserID(String id) {
        sEditor.putString(USERID, id);
        sEditor.commit();
    }

    public String getUserType() {
        return sPreferences.getString(USER_TYPE, null);
    }

    public void setUserType(String userType) {
        sEditor.putString(USER_TYPE, userType);
        sEditor.commit();
    }public String getPassword() {
        return sPreferences.getString(USER_PASSWR, null);
    }

    public void setPassword(String userType) {
        sEditor.putString(USER_PASSWR, userType);
        sEditor.commit();
    }

    public String getUserName() {
        return sPreferences.getString(USER_NAME, "");
    }

    public void setUserName(String userName) {
        sEditor.putString(USER_NAME, userName);
        sEditor.commit();
    }

    public String getFirstName() {
        return sPreferences.getString(FIRST_NAME, "");
    }
    public String getSTART_TIME() {
        return sPreferences.getString(START_TIME, "");
    }
    public String getEND_TIME() {
        return sPreferences.getString(END_TIME, "");
    }
    public void setFirstName(String firstName) {
        sEditor.putString(FIRST_NAME, firstName);
        sEditor.commit();
    }
public String getaddress() {
        return sPreferences.getString("Address", "");
    }

    public void setaddress(String firstName) {
        sEditor.putString("Address", firstName);
        sEditor.commit();
    }

    public String getLastName() {
        return sPreferences.getString(LAST_NAME, "");
    }

    public void setLastName(String lastName) {
        sEditor.putString(LAST_NAME, lastName);
        sEditor.commit();
    }


    public String getAge() {
        return sPreferences.getString(USER_AGE, "");
    }

    public void setAge(String age) {
        sEditor.putString(USER_AGE, age);
        sEditor.commit();
    }

    public String getHeight() {
        return sPreferences.getString("height", "");
    }

    public void setHeight(String height) {
        sEditor.putString("height", height);
        sEditor.commit();
    }
    public String getweight() {
        return sPreferences.getString("weight", "");
    }

    public void setWeight(String weight) {
        sEditor.putString("weight", weight);
        sEditor.commit();
    }

    public String getGender() {
        return sPreferences.getString("gender", "");
    }

    public void setGender(String gender) {
        sEditor.putString("gender", gender);
        sEditor.commit();
    }

    public String getDate() {
        return sPreferences.getString("date", "");
    }

    public void setDate(String date) {
        sEditor.putString("date", date);
        sEditor.commit();
    }
    public String getDeviceToken() {
        return sPreferences.getString("devicetoken", "");
    }

    public void setDeviceToken(String date) {
        sEditor.putString("devicetoken", date);
        sEditor.commit();
    }


    public String getPatientCode() {
        return sPreferences.getString("patient_code", "");
    }

    public void setPatientCode(String patient_code) {
        sEditor.putString("patient_code", patient_code);
        sEditor.commit();
    }

    public String getMarital() {
        return sPreferences.getString("city", "");
    }

    public void setMarital(String height) {
        sEditor.putString("city", height);
        sEditor.commit();
    }





    public String getEmail() {
        return sPreferences.getString(USER_EMAIL, "");
    }

    public void setEmail(String email) {
        sEditor.putString(USER_EMAIL, email);
        sEditor.commit();
    }

    public String getMobile() {
        return sPreferences.getString(USER_MOBILE, "");
    }

    public void setMobile(String mobile) {
        sEditor.putString(USER_MOBILE, mobile);
        sEditor.commit();
    }

    public String getPropic() {
        return sPreferences.getString(USER_PIC, "");
    }

    public void setPropic(String clinicCount) {
        sEditor.putString(USER_PIC, clinicCount);
        sEditor.commit();
    }public String getUSER_PAYMENT_STATUS() {
        return sPreferences.getString(USER_PAYMENT_STATUS, "");
    }

    public void setUSER_PAYMENT_STATUS(String clinicCount) {
        sEditor.putString(USER_PAYMENT_STATUS, clinicCount);
        sEditor.commit();
    }
    public int getClinicCount() {
        return sPreferences.getInt(USER_CLINIC_COUNT, 0);
    }

    public void setClinicCount(int clinicCount) {
        sEditor.putInt(USER_CLINIC_COUNT, clinicCount);
        sEditor.commit();
    }


    public String getAdminStatsu() {
        return sPreferences.getString(USER_ADMIN_STATUS, "");
    }

    public void setADminSTAtus(String clinicId) {
        sEditor.putString(USER_ADMIN_STATUS, clinicId);
        sEditor.commit();
    }

    public String getClinicId() {
        return sPreferences.getString(USER_CLINIC_ID, "");
    }

    public void setClinicId(String clinicId) {
        sEditor.putString(USER_CLINIC_ID, clinicId);
        sEditor.commit();
    }

    public String getUserDetails() {
        return sPreferences.getString(USER_DETAILS, "");
    }

    public String getTREATMENT_TYPE() {
        return sPreferences.getString(TREATMENT_TYPE, "");
    }

    public void setUserDetails(String userDetails) {
        sEditor.putString(USER_DETAILS, userDetails);
        sEditor.commit();
    }


    public boolean getOtpVerifiedStatus(){
        return sPreferences.getBoolean(OTP_VERIFIED, false);
    }


    public void setOTPVerified(boolean isverified){
        sEditor.putBoolean(OTP_VERIFIED, isverified);
        sEditor.commit();
    }
    public void setStartTime(String startTime){
        sEditor.putString(START_TIME, startTime);
        sEditor.commit();
    }
    public void setEND_TIME(String end_time){
        sEditor.putString(END_TIME, end_time);
        sEditor.commit();
    }
    public void setTreatmentType(String treatmentType){
        sEditor.putString(TREATMENT_TYPE, treatmentType);
        sEditor.commit();
    }


}