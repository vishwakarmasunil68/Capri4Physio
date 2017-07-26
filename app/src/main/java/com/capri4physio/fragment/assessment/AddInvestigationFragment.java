package com.capri4physio.fragment.assessment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.capri4physio.R;
import com.capri4physio.fragment.BaseFragment;
import com.capri4physio.listener.HttpUrlListener;
import com.capri4physio.model.BaseModel;
import com.capri4physio.net.ApiConfig;
import com.capri4physio.task.UrlConnectionTask;
import com.capri4physio.util.AppLog;
import com.capri4physio.util.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by jatinder on 12-06-2016.
 */
public class AddInvestigationFragment extends BaseFragment implements HttpUrlListener {
    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String KEY_TYPE = "type";
    private EditText mReportType;
    private EditText mDesc;
    private TextView mPath;
    private TextView mChooseFile;
    private String selectedImagePath = "";
    String imgString = null;
    private String patientId = "";
    HttpEntity resEntity;
    String response_str;
    Activity activity;
    private String assessmentType = "";
    private int PICK_IMAGE_CAPTURE = 1;
    private Button mSavebtn;
    private int PICK_IMAGE_REQUEST = 1;
    private ProgressDialog pDialog;

    @Override
    public void onPostSuccess(Object response, int id) {
        switch (id) {

            case ApiConfig.ID1:
                BaseModel baseModel = (BaseModel) response;
                AppLog.i("Capri4Physio", "Patient Response : " + baseModel.getStatus());

                getFragmentManager().popBackStack();

                break;
        }
    }

    @Override
    public void onPostError(String errMsg, int id) {

    }

    public static AddInvestigationFragment newInstance(String patientId, String assessmentType) {
        AddInvestigationFragment fragment = new AddInvestigationFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_PATIENT_ID, patientId);
        bundle.putString(KEY_TYPE, assessmentType);

        fragment.setArguments(bundle);
        return fragment;
    }

    public AddInvestigationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            patientId = getArguments().getString(KEY_PATIENT_ID);
            assessmentType = getArguments().getString(KEY_TYPE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_investigation, container, false);
        activity = getActivity();
        initView(rootView);
        setListener();
        return rootView;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mReportType = (EditText) view.findViewById(R.id.edtxt_reporttype);
        mDesc = (EditText) view.findViewById(R.id.edtxt_desc);
        mPath = (TextView) view.findViewById(R.id.file_path);
        mChooseFile = (TextView) view.findViewById(R.id.choose_file);
        mSavebtn = (Button) view.findViewById(R.id.btn_save);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOption();
            }
        });
        mSavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedImagePath != null) {
//                    progressBar.setVisibility(View.VISIBLE);
                    Thread thread2 = new Thread(new Runnable() {
                        public void run() {
                            doFileUpload2();
                            try {
                                getActivity().runOnUiThread(new Runnable() {
                                                                public void run() {
                                                                    try {
                                                                        initProgressDialog("Please wait...");
                                                                    } catch (Exception e) {
                                                                        e.printStackTrace();
                                                                    }

                                                                }
                                                            }

                                );
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread2.start();
                }
            }
        });
    }

    private void dialogOption() {
        final String[] items = new String[]{"Take from camera", "Select from gallery"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Image");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    dispatchTakePictureIntent();
                } else {
                    showFileChooser();
                }
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, PICK_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            /*try {
                startActivityForResult(Intent.createChooser(takePictureIntent, "Select Picture"), PICK_IMAGE_REQUEST);
//                photoFile = createImageFile();
            } catch (Exception ex) {
                ex.printStackTrace();
            }*/
            if (photoFile != null) {
                /*SplashActivity.mImageCaptureUri = Uri.fromFile(photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, SplashActivity.mImageCaptureUri);
                //takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                takePictureIntent.putExtra("return-data", true);
                startActivityForResult(takePictureIntent, PICK_FROM_CAMERA);*/

            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String imageFileName = "img" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStorageDirectory(), "CheckInMD");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        return image;
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        final String[] ACCEPT_MIME_TYPES = {
                "application/pdf",
                "image/*"
        };
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, ACCEPT_MIME_TYPES);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
//        Intent viewIntent = new Intent();
//        Intent editIntent = new Intent();
//        viewIntent.setType( "image/*");
//        editIntent.setType( "application/pdf");
//        viewIntent.setAction(Intent.ACTION_PICK);
//        editIntent.setAction(Intent.ACTION_PICK);
//        Intent chooserIntent = Intent.createChooser(editIntent, "Open in...");
//        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { viewIntent });
//        startActivity(chooserIntent);
    }

    public String getPath(Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        // check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    private void getRealPathFromUri(Context ctx, Uri uri) {

        String[] filePathColumn = {MediaStore.Files.FileColumns.DATA};

        Cursor cursor = ctx.getContentResolver().query(uri, filePathColumn,
                null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        selectedImagePath = cursor.getString(columnIndex);
        mPath.setText(selectedImagePath);
        Log.e("picturePath", "picturePath : " + selectedImagePath);
        cursor.close();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        try {
        if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
            Uri uri = data.getData();
            Log.d("TAG", "File Uri: " + uri.toString());
            // Get the path
            selectedImagePath = getPath(
                    getActivity(), uri);
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.PNG, 50, bytes);
            } catch (Exception e) {
                e.toString();
            }
            if (selectedImagePath != null && selectedImagePath != "") {
                mPath.setText(selectedImagePath);
                selectedImagePath = selectedImagePath.trim();
                Log.d("shubham", selectedImagePath);
            }
        } else if (requestCode == PICK_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
//            imageView.setImageBitmap(photo);
//            knop.setVisibility(Button.VISIBLE);


                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUri = getImageUri(getActivity(), photo);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                File finalFile = new File(getRealPathFromURI(tempUri));
            } catch (Exception e) {
                Toast.makeText(getActivity(), "File Selected is corrupted", Toast.LENGTH_LONG).show();
            }

//            System.out.println(mImageCaptureUri);
        } else {
            Toast.makeText(getActivity(), "File Selected is corrupted", Toast.LENGTH_LONG).show();
        }
//        }
//        catch (Exception e){
//            Toast.makeText(getActivity(), "File Selected is corrupted", Toast.LENGTH_LONG).show();
//        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 50, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        selectedImagePath = cursor.getString(columnIndex);
        mPath.setText(selectedImagePath);
        Log.e("picturePath", "picturePath : " + selectedImagePath);
//        cursor.close();
        return cursor.getString(columnIndex);
    }

//        if (requestCode == PICK_IMAGE_CAPTURE && data != null && data.getData() != null) {
            /*Uri uri = data.getData();

            selectedImagePath = getPath(
                    getActivity(), uri);
            if (selectedImagePath != null && selectedImagePath != "") {
                mPath.setText(selectedImagePath);
                selectedImagePath = selectedImagePath.trim();
                Log.d("shubham", selectedImagePath);
            }*/
            /*else{
                Toast.makeText
                        (getActivity(),"File Selected is corrupted",Toast.LENGTH_LONG).show();
            }
//            */

//        mPath.setText(path);


    private void addApiCall() {

        if (Utils.isNetworkAvailable(getActivity())) {

            try {
                JSONObject params = new JSONObject();
                params.put(ApiConfig.PATIENT_ID, patientId);
                params.put(ApiConfig.ASSESSMENT_TYPE, assessmentType);
                params.put(ApiConfig.DATE, Utils.getCurrentDate());
                params.put("attachment", selectedImagePath);
                params.put("report_type", mReportType.getText().toString());
                params.put("description", mDesc.getText().toString());
                new UrlConnectionTask(getActivity(), ApiConfig.ADD_ASSESSMENT_URL, ApiConfig.ID1, true, params, BaseModel.class, this).execute("");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Utils.showMessage(getActivity(), getResources().getString(R.string.err_network));
        }
    }

    private void initProgressDialog(String loadingText) {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(loadingText);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    private void doFileUpload2() {
        Log.i("RESPONSE", "file1");

        try {
            Log.i("RESPONSE", "file2");
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(ApiConfig.BASE_URL + "users/investigation");
            MultipartEntity reqEntity = new MultipartEntity();
            File file1 = new File(selectedImagePath);
            Log.e("file1", selectedImagePath);
            FileBody bin1 = new FileBody(file1);
            reqEntity.addPart("attachment", bin1);
            reqEntity.addPart(ApiConfig.PATIENT_ID, new StringBody(patientId));
            reqEntity.addPart(ApiConfig.ASSESSMENT_TYPE, new StringBody(assessmentType));
            reqEntity.addPart(ApiConfig.DATE, new StringBody(Utils.getCurrentDate()));
            reqEntity.addPart("report_type", new StringBody(mReportType.getText().toString()));
            reqEntity.addPart("description", new StringBody(mDesc.getText().toString()));
//            reqEntity.addPart("reg_mob", new StringBody(phone_number));
//            reqEntity.addPart("device_token", new StringBody(PreferenceData.getDevice_Token(getApplicationContext())));
            post.setEntity(reqEntity);
            HttpResponse response = client.execute(post);
            resEntity = response.getEntity();
            response_str = EntityUtils.toString(resEntity);
            getFragmentManager().popBackStack();
            try {
                pDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e("RESPONSE", response_str);


//            pd.hide();
//            progressBar.setVisibility(View.GONE);


            if (resEntity != null) {
//                pDialog.dismiss();
//                Log.e("RESP", response_str);
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            pDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            System.out.println("<><><>res" + response_str);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                Log.i("RESPONSE", "file4");
            }
        } catch (Exception ex) {
//            pDialog.hide();
            Log.e("Debug", "error: " + ex.getMessage(), ex);


        }
    }

    public String POST(String url) {
        Log.i("MINION", "inside POST()");

        File imageFile = new File(selectedImagePath);
        FileInputStream fis = null;
        JSONObject jsonObj = null;
        int responseCode = 0;
        String responseText = null;

        try {
            fis = new FileInputStream(imageFile);
        } catch (FileNotFoundException e) {
            Log.i("Minions", "File Not Found at: " + selectedImagePath);
            e.printStackTrace();
        }

        Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 50, baos);
        byte[] imgByte = baos.toByteArray();

        imgString = Base64.encodeToString(imgByte, Base64.DEFAULT);
        return imgString;
        //System.out.println("Base64 String image: " + imgString);
    }
}
