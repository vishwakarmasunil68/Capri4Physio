package com.capri4physio.Services;

/**
 * Created by sunil on 28-12-2016.
 */

import android.util.Log;

import com.capri4physio.net.ApiConfig;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class WebServices {
    public static final String ADD_STAFF_URL = ApiConfig.BASE_URL+"users/addstaff";


    static HttpClient httpClient;
    static HttpPost httppost;
    static HttpResponse response;
    JSONObject jsonOBJ = null;
    JSONObject merge_vars = null;

    static BufferedReader bufferedReader;
    InputStream is;


    private static String convertToString(HttpResponse response) {

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer stringBuffer = new StringBuffer("");
            String line = "";
            String LineSeparator = System.getProperty("line.separator");
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + LineSeparator);
            }
            bufferedReader.close();
            return stringBuffer.toString();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }

    }

    public static String httpCall(String url, ArrayList<NameValuePair> postParameters) {
        String result = "";
        try {
            httpClient = new DefaultHttpClient();
            httppost = new HttpPost(url);

            httppost.setEntity(new UrlEncodedFormEntity(postParameters));

            // Execute HTTP Post Request
            response = httpClient.execute(httppost);

            //converting response into string
            result = convertToString(response);
            return result;
        } catch (IOException e) {
            Log.i("Io", e.toString());

            return "";
        }
    }

    public static String httpGetCall(String url) {
        String result = "";
        try {
            HttpParams httpParameters = new BasicHttpParams();

            //Setup timeouts
            HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
            HttpConnectionParams.setSoTimeout(httpParameters, 15000);
            HttpClient httpclient = new DefaultHttpClient(httpParameters);

            HttpGet httpget = new HttpGet(url);

            HttpResponse response = httpclient.execute(httpget);
            //converting response into string
            result = convertToString(response);
            return result;
        } catch (IOException e) {
            Log.i("Io", e.toString());

            return "";
        }
    }


    /**
     * @param theUrl
     * @return
     */
    public static String executeHttpGetWithHeader(String theUrl, String apiKey, String keyword) {
        BufferedReader bufferedReader = null;
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(theUrl + keyword);
            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();
            urlConnection = (URLConnection) url.openConnection();
            urlConnection.setRequestProperty("Api-Key", apiKey);

            // wrap the urlconnection in a bufferedreader
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                    System.gc();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }


    public static String httpCallforImage(String url, String action_setimage, String userid, String imgPath) {
        String result = "";
        try {
            httpClient = new DefaultHttpClient();
            httppost = new HttpPost(url);
            //httppost.setEntity(new UrlEncodedFormEntity(postParameters));


            //MultipartEntityBuilder builder = MultipartEntityBuilder.create();

			/* example for setting a HttpMultipartMode */
            //builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

			/* example for adding an image part */
            //FileBody fileBody = new FileBody(new File(image)); //image should be a String
            //builder.addPart("my_file", fileBody);

            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

            //MultipartEntity multipartEntity = new MultipartEntity();
//			File image1 = new File(imgPath);

//			 entity.addPart("upload_image", new FileBody(new File (imgPath)));

            //MultipartEntity entity = new MultipartEntity();

            entity.addPart("action", new StringBody(action_setimage));
            entity.addPart("user_id", new StringBody(userid));
            entity.addPart("upload_image", new FileBody(new File(imgPath)));
//			entity.addPart("upload_image", new FileBody(imgPath));
            httppost.setEntity(entity);


            // Execute HTTP Post Request
            response = httpClient.execute(httppost);

            //converting response into string
            result = convertToString(response);
            return result;
        } catch (IOException e) {
            Log.i("Io", e.toString());

            return "";
        }
    }



    final static String TAG = "webservice";

}
