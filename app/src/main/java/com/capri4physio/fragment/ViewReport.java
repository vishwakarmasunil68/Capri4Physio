//package com.capri4physio.fragment;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Typeface;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.anton46.whatsapp_profile.MainActivityDoctorProfile;
//import com.capri4physio.R;
//import com.facebook.login.LoginManager;
//
//import database.PreferenceData;
//
//public class ViewReport extends Activity {
//
//    String []IteamList = {"Profile","Patient","Appointment","Broadcast messages","Notification","Activation","Logout"};
//    Integer[] imageId = {
//            R.drawable.profile,
//            R.drawable.otherpatient,
//            R.drawable.bmsg,
//            R.drawable.email,
//            R.drawable.notification,
//            R.drawable.activation_new,
//            R.drawable.logoutnew
//
//    };
//    ListView list;
//    SharedPreferences settings;
//    SharedPreferences.Editor editor;
//    TextView textDoc;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_doctorsecond);
//
//        list = (ListView)findViewById(R.id.list);
//        textDoc= (TextView) findViewById(R.id.textDoc);
//        textDoc.setText(PreferenceData.getName(getApplicationContext()));
//        list.setAdapter(new customAdapter());
//        Typeface tf=Typeface.createFromAsset(getAssets(),"fonts/Montez-Regular.ttf");
//        textDoc.setTypeface(tf);
//    }
//
//    public class customAdapter extends BaseAdapter{
//
//        @Override
//        public int getCount() {
//            return IteamList.length;
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return IteamList;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//
//            LayoutInflater inflater = getLayoutInflater();
//            convertView = inflater.inflate(R.layout.listitem,null);
//            TextView text_listitem = (TextView)convertView.findViewById(R.id.text_listitem);
//            ImageView image_iteam = (ImageView)convertView.findViewById(R.id.image_pic);
//            text_listitem.setText(IteamList[position]);
//            Typeface tf=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
//            text_listitem.setTypeface(tf);
//            image_iteam.setImageResource(imageId [position]);
//            convertView.setTag(position);
//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int click = (Integer) v.getId();
//                    if (position == 0) {
//                        startActivity(new Intent(DoctorAccount.this,MainActivityDoctorProfile.class));
//                    }
//                    if (position == 1){
//                        startActivity(new Intent(DoctorAccount.this,ViewPatientList.class));
//                    }
//                    if (position == 2){
//                        startActivity(new Intent(DoctorAccount.this,ViewAppointmentList.class));
//                    }
//                    if (position == 3){
//                        startActivity(new Intent(DoctorAccount.this, BroadCast_Docview.class));
//                    }
//                    if (position == 4){
//                        startActivity(new Intent(DoctorAccount.this, View_notes.class));
//                    }
//                    if (position == 5) {
//                        startActivity(new Intent(DoctorAccount.this, ViewPatientListactivation.class));
//                    }
//                    if (position == 6) {
//                        settings = getSharedPreferences("locked", Context.MODE_PRIVATE);
//                        editor = settings.edit();
//                        editor.clear();
//                        editor.commit();
//                        Intent intent = new Intent(DoctorAccount.this, FirstPage.class);
//                        Toast.makeText(getApplicationContext(), "logout successfully", Toast.LENGTH_LONG).show();
//                        LoginManager.getInstance().logOut();
//                        startActivity(intent);
//                        finishAffinity();
//                    }
//                }
//            });
//            return convertView;
//        }
//    }
//}
