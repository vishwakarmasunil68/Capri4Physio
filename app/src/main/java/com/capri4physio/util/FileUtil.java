package com.capri4physio.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by sunil on 28-07-2017.
 */

public class FileUtil {
    public static String getBaseFilePath() {
        try {
            File f = new File(Environment.getExternalStorageDirectory() + File.separator + "Physio");
            if (!f.exists())
                f.mkdirs();
            return f.getPath();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getChatDirPath() {
        try {
            String base_path=getBaseFilePath();
            if(base_path!=null) {
                File f = new File(base_path+File.separator+"chat");
                if (!f.exists())
                    f.mkdirs();
                return f.getPath();
            }else{
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
    public static String getVideoChatDir() {
        try {
            String base_path=getChatDirPath();
            if(base_path!=null) {
                File f = new File(base_path+File.separator+"video");
                if (!f.exists())
                    f.mkdirs();
                return f.getPath();
            }else{
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static String getImageDir() {
        try {
            String base_path=getChatDirPath();
            if(base_path!=null) {
                File f = new File(base_path+File.separator+"image");
                if (!f.exists())
                    f.mkdirs();
                return f.getPath();
            }else{
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }



}
