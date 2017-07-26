package com.capri4physio.util;

/**
 * Constants class to define key pair values
 *
 * @version 1.0
 * @author prabhunathy
 * @since 12/22/15.
 */
public class Constants {
	public static class GlobalConst {
		public static final String TAG = "Capri4Physio";
		public static final String OS = "android";
		public static final int SPLASHSCREENTIME = 4000;
		public static final String USER_PATIENT = "0"; // PATIENT - 0, STAFF - 1, THERAPIST - 2
		public static final String USER_STAFF = "1"; // PATIENT - 0, STAFF - 1, THERAPIST - 2
		public static final String USER_THERAPIST = "2"; // PATIENT - 0, STAFF - 1, THERAPIST - 2
		public static final String USER_BRANCH_MANAGER = "3"; // PATIENT - 0, STAFF - 1, THERAPIST - 2,BRANCH ADMIN -3
		public static final String USER_AMIN = "4"; // PATIENT - 0, STAFF - 1, THERAPIST - 2,BRANCH ADMIN -3
		public static final String USER_STUDENT = "5"; // PATIENT - 0, STAFF - 1, THERAPIST - 2,BRANCH ADMIN -3

		public static final String DEVICE_TYPE = "android";
		public static final String PATIENT_HOME = "home_visit";
		public static final String PATIENT_OPD = "opd_visit";
		public static final String MALE = "male";
		public static final String FEMALE = "female";
		public static final String SINGLE = "single";
		public static final String MARRIED = "married";

		public static final int FILE_SELECT_CODE = 101;
	}

	public class ParsingResultConst {
		public static final String SUCCESS = "success";
		public static final String PARSINGERROR = "parsing error";
		public static final String CONNEERROR = "conn error";
		public static final String URIERROR = "uri error";
		public static final String EXCEPTION = "exception";
	}
	public static class ClickIDConst {
		public static final int ID_VIEW_CLICK = 1;
		public static final int ID_DELETE_CLICK = 2;
		public static final int ID_ATTACHMENT_CLICK = 3;

	}
}