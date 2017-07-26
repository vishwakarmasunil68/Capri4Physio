package com.capri4physio.net;

import android.util.Log;


import com.capri4physio.listener.UploadListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * This class is used to call web service APIs with respective methods GET | POST | DELETE | PUT
 *
 * @version 1.0
 * @author prabhunathyadav
 * @since 24/04/16.
 */

public class HttpApiCall implements  IConnectionStatus {

    public int serverResponseCode;
    public String connectionStatus = "";

    public String doPost(String urlString, String paramString) {

        String sResponse = "";
        HttpURLConnection conn = null;
        try {

            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Referer", "http://caprispine.in/");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(paramString);
            writer.flush();
            writer.close();
            os.close();
            conn.connect();

            serverResponseCode = conn.getResponseCode();
            InputStream input = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            sResponse = result.toString();

            connectionStatus = SUCCESS;

        } catch (java.net.SocketTimeoutException e) {

            e.printStackTrace();
            connectionStatus = EXCETION;
            return EX_TIMEOUT;

        } catch (IOException e) {

            e.printStackTrace();
            connectionStatus = EXCETION;
            return EX_IO;

        } catch (Exception e) {

            e.printStackTrace();
            connectionStatus = EXCETION;
            return EX_CONNECTION;

        } finally {
            conn.disconnect();
        }

        return sResponse;
    }
}