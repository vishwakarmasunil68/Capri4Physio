package com.capri4physio.fragment.assessment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.capri4physio.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by Emobi-Android-002 on 10/10/2016.
 */
public class PDFFromServerActivity extends Activity {
    String file_name;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        file_name=getIntent().getStringExtra("file_path");
        Log.d("sunil","url:"+file_name);
        String PATH = Environment.getExternalStorageDirectory()+ File.separator+"bjain";
        File folder=new File(PATH);
        if(!folder.exists()){
            folder.mkdirs();
        }
        File f=new File(PATH+file_name);
        if(f.exists()){
            showPdf();
            finish();
        }
        else {
            new DownloadFile().execute();
        }
        /*String extStorageDirectory = Environment.getExternalStorageDirectory()
                .toString();
        File folder = new File(extStorageDirectory, "pdf");
        folder.mkdir();
        File file = new File(folder, "Read.pdf");
        try {
            file.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        DownloadManager.DownloadFile(file_name, file);*/


    }
    public void showPdf()
    {
        Log.d("file","showfile");
        String PATH = Environment.getExternalStorageDirectory()+ File.separator+"bjain";
        File folder = new File(PATH);
        if(!folder.exists()){
            folder.mkdir();//If there is no folder it will be created.
        }
        File file = new File(PATH+ File.separator+file_name);
        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        startActivity(intent);

    }


    class DownloadFile extends AsyncTask<String,Integer,Long> {
        ProgressDialog mProgressDialog = new ProgressDialog(PDFFromServerActivity.this);// Change Mainactivity.this with your activity name.
        String strFolderName;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.setMessage("Downloading");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.show();
        }
        @Override
        protected Long doInBackground(String... aurl) {
            int count;
            try {
                URL url = new URL("http://www.bjain.com/doctor/pdf/"+file_name);
                URLConnection conexion = url.openConnection();
                conexion.connect();
                String targetFileName="Name"+".pdf";//Change name and subname
                int lenghtOfFile = conexion.getContentLength();
                String PATH = Environment.getExternalStorageDirectory()+ File.separator+"bjain";
                File folder = new File(PATH);
                if(!folder.exists()){
                    folder.mkdir();//If there is no folder it will be created.
                }
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(PATH+ File.separator+file_name);
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress ((int)(total*100/lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {}
            return null;
        }
        protected void onProgressUpdate(Integer... progress) {
            mProgressDialog.setProgress(progress[0]);
            if(mProgressDialog.getProgress()==mProgressDialog.getMax()){
                mProgressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "File Downloaded", Toast.LENGTH_SHORT).show();
                showPdf();
            }
        }
        protected void onPostExecute(String result) {


        }
    }
}
