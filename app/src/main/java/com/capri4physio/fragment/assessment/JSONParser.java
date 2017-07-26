package com.capri4physio.fragment.assessment;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Emobi-Android-002 on 7/13/2016.
 */
public class JSONParser  {

    InputStream is = null;
    JSONObject jObj;
    String response = "";
    Context context;


    public JSONParser(Context context) {
        this.context = context;
    }

    public String getJSONFromUrl(final String url) {
        // Making HTTP request
        try {
            URL urltoConnect = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urltoConnect.openConnection();
            response = readStream(con.getInputStream());
            // Give output for the command line

        } catch (Exception e) {
            e.printStackTrace();

        }

        return response;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String readStream(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in));) {

            String nextLine = "";
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
