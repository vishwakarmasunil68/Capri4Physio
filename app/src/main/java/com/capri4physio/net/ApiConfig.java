package com.capri4physio.net;

/**
 * This class is used to web service APIs
 *
 * @author prabhunathyadav
 * @version 1.0
 * @since 24/04/16.
 */
public class ApiConfig {

    public static final int ID1 = 1;
    public static final int ID2 = 2;
    public static final int ID3 = 3;
    public static final int ID4 = 4;
    //http://caprispine.in/
    public static final String BASE_URL = "http://caprispine.in/fijiyo/index.php/";
    public static final String IMAGE_BASE_URL = "http://caprispine.in/fijiyo/";
    public static final String PROFILE_PIC_BASE_URL = "http://caprispine.in/fijiyo/app/webroot/upload/";

    public static final String LOGIN_URL = BASE_URL + "users/login";
    public static final String REGISTER_URL = BASE_URL + "users/registration";
    public static final String CHANGE_PASSWORD_URL = BASE_URL + "users/changeadminpassword";
    public static final String USER_DETAIL_URL = BASE_URL + "users/getuserdetail";

    public static final String CLINICS_URL = BASE_URL + "users/cliniclisting";
    public static final String CLINIC_URL = BASE_URL + "users/clinicdetail";
    public static final String AVAILIBILITY_URL = BASE_URL + "users/getavailibility";
    public static final String PRACTICE_TIME_URL = BASE_URL + "users/getmyavailibility";
    public static final String CHANGE_AVAILIBILITY_URL = BASE_URL + "users/addavailibility";

    public static final String BOOK_APPOINTMNET_URL = BASE_URL + "users/addbookings";
    public static final String VIEW_BILLING_URL = BASE_URL + "users/invoice";
    public static final String VIEWSEARCHPATIENT = BASE_URL + "users/searchpatient";

    public static final String CLINIC_MANAGE_URL = BASE_URL + "users/manageclinic";
    public static final String VIEW_STAFF_URL = BASE_URL + "users/getclinicuserlist";  // by branch admin user id
    public static final String VIEW_PATIENT_URL = BASE_URL + "users/getpatientlist";  // by branch clinic id
    public static final String VIEW_PATIENT_STAFF_FILTERATION = BASE_URL + "users/staffpatientviewbranchcodewise";
    public static final String VIEW_STAFF_FILTERATION = BASE_URL + "users/staffviewbranchcodewise";
    public static final String VIEW_ASSESSMENT_URL = BASE_URL + "users/getpatienthistory";
    public static final String ADD_ASSESSMENT_URL = BASE_URL + "users/managepatienthistory";
    public static final String DELETE_ASSESSMENT_URL = BASE_URL + "users/deletepatienthistory";
    public static final String ADD_NEURO_EXAM = BASE_URL + "users/addnewneuroexam";
    public static final String UPDATE_NEURO_EXAM = BASE_URL + "users/updateneuroexams";

    public static final String PROFILE_PIC = "profile_picture";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String MOBILE = "mobile";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String SHOW_PASSWORD = "show_password";
    public static final String std_amount = "std_amount";
    public static final String att_photo = "att_photo";
    public static final String date = "date";
    public static final String USER_TYPE = "user_type"; // 0,1,2
    public static final String LATITUDE = "lat";
    public static final String LONGITUDE = "lng";
    public static final String DEVICE_TOKEN = "device_token";
    public static final String DEVICE_TYPE = "device_type";
    public static final String STATUS = "status"; //status should be 0
    public static final String REG_AS_A_PATIENT = "treatment_type";
    public static final String ADDED_BY = "added_by";
    public static final String SIGNIN_URL = "http://caprispine.in/fijiyo/index.php/users/moterexam";
    public static final String MOTOR_HEAD_NECK_URL = "http://caprispine.in/fijiyo/index.php/users/moterexamhltrp";
    public static final String CASE_NOTES_URL = "http://caprispine.in/fijiyo/index.php/users/moterexamsclinicasenote";
    public static final String PROGRESS_NOTES_URL = "http://caprispine.in/fijiyo/index.php/users/moterexamsprogressnotes";
    public static final String GET_CASE_NOTES = "http://caprispine.in/fijiyo/index.php/users/getclniknote";
    public static final String GET_PROGRESS_NOTES = "http://caprispine.in/fijiyo/index.php/users/getprogressnotes";
    public static final String GET_REMARKS_NOTES = "http://caprispine.in/fijiyo/index.php/users/getremarks";
    public static final String REMARKS_NOTES_URL = "http://caprispine.in/fijiyo/index.php/users/moterexamsremarks";
    public static final String MOTOR_CERVICAL_URL = "http://caprispine.in/fijiyo/index.php/users/moterexamcervical";
    public static final String MOTOR__THORACCIC_URL = "http://caprispine.in/fijiyo/index.php/users/moterexamthoraccic";
    public static final String MOTOR__LUMBAR_URL = "http://caprispine.in/fijiyo/index.php/users/moterexamlumbar";
    public static final String MOTOR_HIP_URL = "http://caprispine.in/fijiyo/index.php/users/moterexamship";
    public static final String MOTOR_NEURO_URL = "http://caprispine.in/fijiyo/index.php/users/moterexamsneuro";
    public static final String MOTOR_KNEE_URL = "http://caprispine.in/fijiyo/index.php/users/moterexamknee";
    public static final String MOTOR_SHOULDER_URL = "http://caprispine.in/fijiyo/index.php/users/moterexamsshoulder";
    public static final String MOTOR_WRIST_URL = "http://caprispine.in/fijiyo/index.php/users/moterexamswrist";
    public static final String MOTOR_TOES_URL = "http://caprispine.in/fijiyo/index.php/users/moterexamstoes";
    public static final String MOTOR_THUMB_URL = "http://caprispine.in/fijiyo/index.php/users/moterexamsthumb";
    public static final String MOTOR_HALLUX_URL = "http://caprispine.in/fijiyo/index.php/users/moterexamshallux";
    public static final String MOTOR_SCAPULA_URL = "http://caprispine.in/fijiyo/index.php/users/moterexamsscapula";
    public static final String MOTOR_FOREARM_URL = "http://caprispine.in/fijiyo/index.php/users/moterexamsforearm";
    public static final String MOTOR_ANKLE_URL = "http://caprispine.in/fijiyo/index.php/users/moterexamsankle";
    public static final String MOTOR_RESPIRATION_URL = "http://caprispine.in/fijiyo/index.php/users/moterexamrespiration";
    public static final String MOTOR_MEASURE_URL = "http://caprispine.in/fijiyo/index.php/users/moterexamsmeasure";
    public static final String MOTER_EXAM_VIEW = "http://caprispine.in/fijiyo/index.php/users/moterexamview";
    public static final String MOTER_FINGER_URL = "http://caprispine.in/fijiyo/index.php/users/moterexamsfingers";
    public static final String MOTER_SACROLIC_URL = "http://caprispine.in/fijiyo/index.php/users/moterexamsacroilic";
    public static final String MOTOR_ELBOW_URL = "http://caprispine.in/fijiyo/index.php/users/moterexamselbow";
    public static final String MOTOR_SENSORY_URL = "http://caprispine.in/fijiyo/index.php/users/sensoryexam";
    public static final String SEND_REPORT = "http://caprispine.in/fijiyo/index.php/users/servervaluepost";
    public static final String INVOICE_P_VIEW = "http://caprispine.in/fijiyo/index.php/users/invoicepatientview";


    public static final String VIEW_CLINIC = "http://caprispine.in/fijiyo/index.php/users/clinicheadview";
    //Change password
    public static final String VIEW_STAFF = "http://caprispine.in/fijiyo/index.php/users/staff";
    public static final String CANCEL_INVO = "http://caprispine.in/fijiyo/index.php/users/deleteinvoice";
    public static final String VIEW_PATIENT = "http://caprispine.in/fijiyo/index.php/users/allpatient";
    public static final String UPDATE_PATIENT = "http://caprispine.in/fijiyo/index.php/users/newupdatePatient";
    public static final String VIEW_EDITABLE_INVO = "http://caprispine.in/fijiyo/index.php/users/updateinvoice";
    public static final String OLD_PASSWORD = "old_password";
    public static final String NEW_PASSWORD = "new_password";

    public static final String USER_DETAIL_ID = "id";
    public static final String USER_ID = "user_id";
    public static final String std_user_id = "std_user_id";
    public static final String CLINIC_ID = "id";
    public static final String PATIENT_TYPE_TO_VIEW_PATIENT = "treatment_type";
    public static final String CLINIC_ID_TO_VIEW_PATIENT = "clinic_id";
    public static final String CLINIC_NAME = "clinic_name";
    public static final String CLINIC_SHORT_DESC = "short_description";
    public static final String CLINIC_DESC = "description";
    public static final String CLINIC_LOCATION = "location";
    public static final String DATE = "date";

    //Report
    public static final String FORGOT_PASSWORD_URL = BASE_URL + "users/forgotpassword";
    public static final String VIEW_SENSORY_REPORT = BASE_URL + "users/report1";
    public static final String VIEW_HISTORY_REPORT = BASE_URL + "users/report2";
    public static final String ADD_BRANCH_HEAD = BASE_URL + "users/addbranch";
    public static final String REGISTER_STUDENT = BASE_URL + "users/addstudent";
    public static final String addstudentamount = BASE_URL + "users/addstudentamount";
    public static final String viewstudentamount = BASE_URL + "users/studentamountview";
    public static final String POST_ENQUIRY = BASE_URL + "users/addenquiry";
    public static final String GetURL = "http://caprispine.in/fijiyo/index.php/users/viewbranch";
    public static final String ADD_EXPENSE_REPORT = BASE_URL + "users/addexpense";
    public static final String ADD_CLINIC_HEAD = BASE_URL + "users/adminclinicheadadd";
    public static final String VIEW_PAIN_REPORT = BASE_URL + "users/report3";
    public static final String VIEW_TREATMENT_REPORT = BASE_URL + "users/report4";
    public static final String VIEW_PHYSICAL_REPORT = BASE_URL + "users/report7";
    public static final String VIEW_TREATMENT_GIVEN_REPORT = BASE_URL + "users/report5";
    public static final String VIEW_PHYSIO_THREAPISTIC_REPORT = BASE_URL + "users/report6";
    public static final String VIEW_INVO_REPORT = BASE_URL + "users/report8";
    public static final String VIEW_CHIEF_COMPLAIN_REPORT = BASE_URL + "users/report10";
    public static final String VIEW_MOTOR_REPORT1 = BASE_URL + "users/moterexamreport1";
    public static final String VIEW_INCOME_STATEMENT = BASE_URL + "users/incomestatement";
    public static final String VIEW_MOTOR_REPORT_ANKLE = BASE_URL + "users/moterexamreport2";
    public static final String VIEW_MOTOR_REPORT_ELBOW = BASE_URL + "users/moterexamreport3";
    public static final String VIEW_MOTOR_REPORT_FINGERS = BASE_URL + "users/moterexamreport4";
    public static final String VIEW_MOTOR_REPORT_FORE = BASE_URL + "users/moterexamreport5";
    public static final String VIEW_MOTOR_REPORT_HALLUX = BASE_URL + "users/moterexamreport6";
    public static final String VIEW_MOTOR_REPORT_KNEE = BASE_URL + "users/moterexamreport8";
    public static final String VIEW_MOTOR_REPORT_SCAPULA = BASE_URL + "users/moterexamreport13";
    public static final String VIEW_MOTOR_REPORT_TOES = BASE_URL + "users/moterexamreport16";
    public static final String VIEW_MOTOR_REPORT_HIP = BASE_URL + "users/moterexamreport7";
    public static final String VIEW_MOTOR_REPORT_THUMB = BASE_URL + "users/moterexamreport15";
    public static final String VIEW_MOTOR_REPORT_SHOULDER = BASE_URL + "users/moterexamreport14";
    public static final String VIEW_MOTOR_REPORT_WRIST = BASE_URL + "users/moterexamreport17";
    public static final String VIEW_MOTOR_REPORT_MEASURE = BASE_URL + "users/moterexamreport9";
    public static final String VIEW_INCOME_TREATMENT = BASE_URL + "users/incometreatementvise";
    public static final String VIEW_EXPENSE_REPORT = BASE_URL + "users/dateviseexpense";
    public static final String VIEW_SENSORY4_REPORT = BASE_URL + "users/soryexamlist";
    public static final String VIEW_NEURO4_REPORT = BASE_URL + "users/moterexamsneurolist";
    public static final String VIEW_PAYMENT_STATUS = BASE_URL + "users/studentamountview";
    public static final String VIEW_STAFF_INVOICESHEET = BASE_URL + "users/Staffproinvoiceview";
    public static final String VIEW_BALANCE_SHEET = BASE_URL + "users/balancesheet";
    public static final String VIEW_STAFF_SESSION_WISE = BASE_URL + "users/Staffprosessionview";


    //Delete
    public static final String DELETE_PHYSICAL_ = "http://caprispine.in/fijiyo/index.php/users/deletephysicalexaminations";
    public static final String DELETE_PROGRESS_NOTES = "http://caprispine.in/fijiyo/index.php/users/deleteprogressnotes";
    public static final String DELETE_CANCELAPPOINTMENTS = "http://caprispine.in/fijiyo/index.php/users/deleteappointments";
    public static final String DELETE_SENSORY_ = "http://caprispine.in/fijiyo/index.php/users/deletesensoryexam";
    public static final String DELETE_NEURO_ = "http://caprispine.in/fijiyo/index.php/users/deletemoterexamsneuro";
    public static final String DELETE_EXPENSE_ = "http://caprispine.in/fijiyo/index.php/users/deleteexpance";
    public static final String DELETE_PATIENT = "http://caprispine.in/fijiyo/index.php/users/deleteteuser";
    public static final String DELETE_REMARKS_NOTES = "http://caprispine.in/fijiyo/index.php/users/deleterexamsremarks";
    public static final String DELETE_CASE_NOTES = "http://caprispine.in/fijiyo/index.php/users/deletecasenote";
    public static final String SEND_INVOICE = "http://caprispine.in/fijiyo/index.php/users/updatepatientstatus";
    public static final String DELETE_INVOICE = "http://caprispine.in/fijiyo/index.php/users/deleteinvoice";

    //Edit
    public static final String EDIT_PROGRESS_NOTES = "http://caprispine.in/fijiyo/index.php/users/updateprogressnotes";
    public static final String EDITGST = "http://caprispine.in/fijiyo/index.php/users/updateadmingst";
    public static final String EDIT_REMARKS_NOTES = "http://caprispine.in/fijiyo/index.php/users/updaterexamsremarks";
    public static final String EDIT_CASE_NOTES = "http://caprispine.in/fijiyo/index.php/users/updatemoterexamsclinicasenote";
    public static final String EDIT_PAIN_ = "http://caprispine.in/fijiyo/index.php/users/updatepains";
    public static final String EDIT_HISTORY_ = "http://caprispine.in/fijiyo/index.php/users/updatehistories";
    public static final String EDIT_PHYSICAL_ = "http://caprispine.in/fijiyo/index.php/users/updatephysicalexaminations";
    public static final String EDIT_TREATMENTADVICE = "http://caprispine.in/fijiyo/index.php/users/updateTreatmentadvice";
    public static final String EDIT_TREATMENTGIVEN = "http://caprispine.in/fijiyo/index.php/users/updatetreamentgiven";
    public static final String EDIT_INVESTIGATION = "http://caprispine.in/fijiyo/index.php/users/updatenvestigations";
    public static final String EDIT_MEDICAL = "http://caprispine.in/fijiyo/index.php/users/updatemedicals";
    public static final String EDIT_CHIF_NOTES = "http://caprispine.in/fijiyo/index.php/users/updatechiefcomplaints";
    public static final String VIEW_EDITABLEEXPENSE = "http://caprispine.in/fijiyo/index.php/users/updateexpance";
    public static final String EDIT_PYSIOTHERAPUTIC_ = "http://caprispine.in/fijiyo/index.php/users/updatephysiotheraputics";
    public static final String EDIT_NEURO_ = "http://caprispine.in/fijiyo/index.php/users/updatemoterexamsneuro";
    public static final String EDIT_SENSORY = "http://caprispine.in/fijiyo/index.php/users/updatesensoryexam";
    public static final String ADD_SENSORY_EXAM = "http://caprispine.in/fijiyo/index.php/users/addsensoryexam";

    //Add availibility
    public static final String DAY = "day";
    public static final String START_TIME = "start_time";
    public static final String END_TIME = "end_time";

    //BOOK AN APPOINTMNET
    public static final String PATIENT_ID = "patient_id";
    public static final String REASON = "reason";
    public static final String BOOKING_DAY = "booking_day";
    public static final String BOOKING_DATE = "booking_date";
    public static final String BOOKING_START_TIME = "booking_start_time";
    public static final String BOOKING_END_TIME = "booking_end_time";
    public static final String BOOKING_TYPE = "booking_type";
    public static final String BOOKING_PAYMENT_TYPE = "payment_type";


    //Assessments
    public static final String ASSESSMENT_TYPE = "type";
    public static final String COMPLAINTS = "complaints";
    public static final String PROBLEM_DURATION = "problem_duration";
    public static final String PROBLEM_BEFORE = "problem_before";
    public static final String DELET_ID = "id";

    //History
    public static final String MEDICAL_HISTORY = "medical_history";
    public static final String SURGICAL_HISTORY = "surgical_history";
    public static final String OTHER_HISTORY = "other_history";
    public static final String MEDICINE_USED = "medicine_used";

    //Add pain constant
    public static final String PAIN_SIDE = "pain_side";
    public static final String SEVERITY_PAIN = "severity_pain";
    public static final String PRESURE_PAIN = "pressure_pain";
    public static final String THRESHOLD_SITE = "threshold_site";
    public static final String PAIN_NATURE = "pain_nature";
    public static final String PAIN_ONSET = "pain_onset";
    public static final String PAIN_DURATION = "pain_duration";
    public static final String PAIN_LOCATION = "location";
    public static final String DIURNAL_VARIATION = "diurnal_variations";
    public static final String TRIGGER_POINT = "trigger_point";
    public static final String AGGRAVATING_FACTORS = "aggravating_factors";
    public static final String RELIEVING_FACTORS = "relieving_factors";


    //Treatement advice
    public static final String TREATMENT_GOAL = "goal";
    public static final String TREATMENT_THERAPY = "therapy";
    public static final String TREATMENT_DOSES = "dose";
    public static final String THERAPY_THERAPIST = "therapist";
    public static final String TREATMENT_COMMENT = "comment";
    public static final String TREATMENT_TIME_IN = "time_in";
    public static final String TREATMENT_TIME_OUT = "time_out";


    //Constants
    public static final String ASSESSMENT_CHEIF_COMPLAINTS = "ChiefComplaint";
    public static final String ASSESSMENT_CHEIF_HISTORY = "History";
    public static final String ASSESSMENT_CHEIF_PAIN = "Pain";
    public static final String ASSESSMENT_CHEIF_PHYSICAL_EXAMINATION = "PhysicalExamination";
    public static final String ASSESSMENT_INVESTIGATION = "Investigation";
    public static final String search_user_api = "http://caprispine.in/chat/searchuser.php";
    public static final String get_all_users_chats = "http://caprispine.in/chat/getalluserschats.php";
    public static final String chat_api = "http://caprispine.in/chat/chatcapri.php";
    public static final String ADD_CASE_NOTES = "http://caprispine.in/chat/addcasenotes.php";
    public static final String ADD_PROGRESS_NOTES = "http://caprispine.in/chat/addprogressnotes.php";
    public static final String ADD_REMARK_NOTES = "http://caprispine.in/chat/addremarks.php";
    public static final String PATIENT_REGISTRATION = "http://caprispine.in/fijiyo/index.php/users/patientreg";
    public static final String all_chat_users = "http://caprispine.in/chat/allchat.php";
    public static final String get_all_users = "http://caprispine.in/chat/getChatUsers.php";
    public static final String logout_users = "http://caprispine.in/chat/logoutuser.php";
    public static final String getallchat = "http://caprispine.in/chat/getallchat.php";
    public static final String view_all_appointments = "http://caprispine.in/chat/getallappointments.php";
    public static final String get_all_patients = "http://caprispine.in/chat/getallpatients.php";
    public static final String GET_PATIENT_BY_BRANCH_CODE = "http://caprispine.in/fijiyo/index.php/users/getPatientListByBranch";
    public static final String get_timings_api = "http://caprispine.in/fijiyo/index.php/users/appoimentlistdatewise";
    public static final String add_appointment_api = "http://caprispine.in/fijiyo/index.php/users/addappointment";
    public static final String delete_appointment_api = "http://caprispine.in/chat/deleteappointment.php";
    public static final String otp_verify_api = "http://caprispine.in/fijiyo/index.php/users/otpverify";
    public static final String resend_otp = "http://caprispine.in/fijiyo/index.php/users/resendotp";
    public static final String chat_base = "http://caprispine.in/chat/";
    public static final String add_new_course_api = "http://caprispine.in/fijiyo/index.php/users/addcourse";
    public static final String update_course_api = "http://caprispine.in/fijiyo/index.php/users/updatecourse";
    public static final String list_couces = "http://caprispine.in/fijiyo/index.php/users/courselist";
    public static final String add_student_course = "http://caprispine.in/fijiyo/index.php/users/addstudentcourse";
    public static final String update_student_course = "http://caprispine.in/fijiyo/index.php/users/updatestudentcourse";
    public static final String student_course_list_by_id_api = "http://caprispine.in/fijiyo/index.php/users/studentcourselistbyid";
    public static final String add_application_form_api = "http://caprispine.in/fijiyo/index.php/users/addapplication_form";
    public static final String update_application_form_api = "http://caprispine.in/fijiyo/index.php/users/updateapplicationform";
    public static final String add_attachment_api = "http://caprispine.in/fijiyo/index.php/users/addstudent_attachment";
    public static final String get_application_form_api = "http://caprispine.in/fijiyo/index.php/users/studentapplicationform3Byid";
    public static final String get_student_by_course_id = "http://caprispine.in/fijiyo/index.php/users/studentcourse1id";
    public static final String get_branch_doctor = "http://caprispine.in/fijiyo/index.php/users/bracchcodewisetherpist";
    public static final String add_therapist_api = "http://caprispine.in/fijiyo/index.php/users/addtherapist";
    public static final String update_therapist_api = "http://caprispine.in/fijiyo/index.php/users/updatetheripst";
    public static final String get_doctor_booked_appointment = "http://caprispine.in/fijiyo/index.php/users/appointmentlistdatewise";
    public static final String get_appointments_by_patients = "http://caprispine.in/fijiyo/index.php/users/getappoimentbypatientid";
    public static final String get_appointments_by_doctor_id = "http://caprispine.in/fijiyo/index.php/users/getappoimentbydoctorid";
    public static final String update_appointment = "http://caprispine.in/fijiyo/index.php/users/updateappointmentstatus";
    public static final String get_application_form_api2 = "http://caprispine.in/fijiyo/index.php/users/getapplicationformbyid";
    public static final String get_doses = "http://caprispine.in/fijiyo/index.php/users/treatmentsBydose";
    public static final String add_treatment_api = "http://caprispine.in/fijiyo/index.php/users/addtreatmentgivens";
    public static final String add_admin_treatment = "http://caprispine.in/fijiyo/index.php/users/addadmintreatment";
    public static final String delete_admin_treatment = "http://caprispine.in/chat/deletetreatment.php";
    public static final String get_all_admin_treatment = "http://caprispine.in/chat/getalladmintreatment.php";
    public static final String update_admin_treatment = "http://caprispine.in/fijiyo/index.php/users/updateadmintreatment";
    public static final String GET_LAST_PATIENT_TRANSACTION_AMOUNT = "http://caprispine.in/fijiyo/index.php/users/patienttranstionlistbyid";
    public static final String GET_ALL_PATIENTS = "http://caprispine.in/fijiyo/index.php/users/patienlistbyuserid";
    public static final String ADD_PATIENT_AMOUNT_API = "http://caprispine.in/fijiyo/index.php/users/addpatienttranstion";
    public static final String GET_PATIENT_TRANS = "http://caprispine.in/fijiyo/index.php/users/patienttranstionlist";
    public static final String GET_PATIENTS_BY_BRANCH_CODE = "http://caprispine.in/fijiyo/index.php/users/patienlistbybrachcode";

    public static final String ADD_INVOICE_CREATED = "http://caprispine.in/fijiyo/index.php/users/addnewinvoice";

    public static final String VIEW_INVOICE_BY_THERAPIST_ID = "http://caprispine.in/fijiyo/index.php/users/newinvoicelistbytherapistid";
    public static final String ALL_TREATMENT_INCOME = "http://caprispine.in/fijiyo/index.php/users/getincometreatmentwise";
    public static final String GET_INVOICE_THERAPIST_WISE = "http://caprispine.in/fijiyo/index.php/users/incometherapistwise";
    public static final String GET_INCOME_PATIENT_WISE = "http://caprispine.in/fijiyo/index.php/users/incomepatientwise";
    public static final String GET_INVOICE_BY_DATE = "http://caprispine.in/fijiyo/index.php/users/invoicebetweentwodate";
    public static final String DELETE_COURSE_API = "http://caprispine.in/fijiyo/index.php/users/deletecourse";

    public static final String GET_INCOME_BRANCH_WISE = "http://caprispine.in/fijiyo/index.php/users/incometreatgivenbranchwise";
    public static final String SEND_NOTIFICATION = "http://caprispine.in/chat/sendCourseApprovedNotification.php";
    public static final String ALL_PATIENT_WALLET_AMOUNT = "http://caprispine.in/fijiyo/index.php/users/patientmodetrans";
    public static final String SEARCH_PATIENTS = "http://caprispine.in/chat/searchpatients.php";
    public static final String CALL_BRANCH_DELETE_API = "call_branch_delete_api";
    public static final String UPDATE_BRANCH = "http://caprispine.in/fijiyo/index.php/users/callUpdateFragment";
    public static final String DELETE_BRANCH_API = "http://caprispine.in/fijiyo/index.php/users/callBranchDeleteFragment";
    public static final String GET_CASE_NOTES_API = "http://caprispine.in/fijiyo/index.php/users/getcasenotes";
    public static final String GET_PROGRESS_NOTES_API = "http://caprispine.in/fijiyo/index.php/users/getnewprogressnotes";
    public static final String GET_REMARK_NOTES_API = "http://caprispine.in/fijiyo/index.php/users/getnewremarks";
    public static final String ADD_NTP_EXAM = "http://caprispine.in/chat/neuro/addndt.php";
    public static final String UPDATE_NTP_EXAM = "http://caprispine.in/chat/neuro/updatendtexam.php";
    public static final String GET_NTP_EXAM = "http://caprispine.in/chat/neuro/getndtexam.php";
    public static final String ADD_NEURO_EXM = "http://caprispine.in/chat/neuro/addneuro.php";
    public static final String UPDATE_ADL_EXAM = "http://caprispine.in/chat/neuro/updateadl.php";
    public static final String GET_ADL_EXAM = "http://caprispine.in/chat/neuro/getadl.php";
    public static final String ADD_ADL_EXAM = "http://caprispine.in/chat/neuro/addadl.php";
    public static final String GET_NEURO_EXAM = "http://caprispine.in/chat/neuro/getneuroexam.php";
    public static final String UPDATE_NEURO_EXM = "http://caprispine.in/chat/neuro/updateneuroexam.php";
    public static final String DELETE_NEURO_EXAM = "http://caprispine.in/chat/neuro/deleteneuroexam.php";
    public static final String DELETE_NDT_EXAM = "http://caprispine.in/chat/neuro/deletendtexam.php";
    public static final String DELETE_ADL_EXAM = "http://caprispine.in/chat/neuro/deleteadlexam.php";

    public static String ADD_NEW_PATIENT = "http://caprispine.in/fijiyo/index.php/users/addnewpatient";
}