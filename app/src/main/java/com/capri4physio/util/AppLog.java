package com.capri4physio.util;


/**
 * Logger class to print logs
 *
 * @version 1.0
 * @author prabhunathy
 * @since 12/22/15.
 */
public class AppLog {
    static final boolean iLOG = true;
    static final boolean eLOG = true;
    static final boolean dLOG = true;
    static final boolean vLOG = true;
    static final boolean wLOG = true;

    public static void i(String tag, String string) {
        if (iLOG) android.util.Log.i(tag, string);
    }
    public static void e(String tag, String string) {
        if (eLOG) android.util.Log.e(tag, string);
    }
    public static void d(String tag, String string) {
        if (dLOG) android.util.Log.d(tag, string);
    }
    public static void v(String tag, String string) {
        if (vLOG) android.util.Log.v(tag, string);
    }
    public static void w(String tag, String string) {
        if (wLOG) android.util.Log.w(tag, string);
    }
}
