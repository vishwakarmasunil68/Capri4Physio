package com.capri4physio.fragment.assessment;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.capri4physio.R;
import com.capri4physio.activity.ManageAppointmentActivity;
import com.capri4physio.activity.ManagePatientAppointmentsActivity;
import com.capri4physio.activity.StudentDashboardActivity;
import com.capri4physio.database.DatabaseHelper;
import com.capri4physio.model.appointment.AppointmentResultPOJO;
import com.capri4physio.model.chat.ChatPOJO;
import com.capri4physio.util.AppPreferences;
import com.capri4physio.util.StringUtils;
import com.capri4physio.util.TagUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by Emobi-Android-002 on 9/19/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    public static String messagebody, Title;
    DatabaseHelper databaseHelper;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
//        String result=remoteMessage.getData().get("result");
//        Log.d(TagUtils.getTag(), "From: " + remoteMessage.getFrom());
////        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
////        Title = remoteMessage.getNotification().getTitle();
//        //Calling method to generate notification
//        sendNotification(result);
        try {
            Log.d(TagUtils.getTag(), "remote msg:-" + remoteMessage.getData().toString());
            Log.d(TagUtils.getTag(), "success:-" + remoteMessage.getData().get("success"));
            Log.d(TagUtils.getTag(), "message:-" + remoteMessage.getData().get("result"));
            Log.d(TagUtils.getTag(), "type:-" + remoteMessage.getData().get("type"));
            checkType(remoteMessage.getData().get("type"), remoteMessage.getData().get("result"));
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            try {
                Log.d(TAG, "From: " + remoteMessage.getFrom());
                Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
            } catch (Exception e1) {
                Log.d(TAG, e1.toString());
            }
        }
    }

    public void checkType(String type, String message) {
        try {
            if (type.equals("chat")) {
                sendNotification(new JSONObject(message).optString("chat_msg"));
                updateChatActivity(getApplicationContext(), message);
                Gson gson1 = new Gson();
                ChatPOJO chatPOJO = gson1.fromJson(message, ChatPOJO.class);
                databaseHelper = new DatabaseHelper(getApplicationContext());
                databaseHelper.insertchatdata(chatPOJO);

            } else {
                if (type.equals("bookappointment")) {
                    if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("2")) {
                        AppointmentResultPOJO appointmentResultPOJO = new Gson().fromJson(message, AppointmentResultPOJO.class);
                        sendAppointmentBookedNotification("The appointment of date " + appointmentResultPOJO.getBookingDate() +
                                " " + appointmentResultPOJO.getBookingStarttime() + " has been Booked.");
                        updateBookedAppointment(getApplicationContext(), StringUtils.BOOKED_APPOINTMENT);
                    }
                } else {
                    if (type.equals("appointmentdelete")) {
                        if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("0")) {
                            AppointmentResultPOJO appointmentResultPOJO = new Gson().fromJson(message, AppointmentResultPOJO.class);
                            sendCancelAppointment("Your appointment of date " + appointmentResultPOJO.getBookingDate() +
                                    " " + appointmentResultPOJO.getBookingStarttime() + " is Cancelled.");
                            updateconfirmAppointment(getApplicationContext(), message);

                        }
                    } else {
                        if (type.equals("appointmentconfirm")) {
                            if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("0")) {
                                AppointmentResultPOJO appointmentResultPOJO = new Gson().fromJson(message, AppointmentResultPOJO.class);
                                sendConfirmAppointment("Your appointment of date " + appointmentResultPOJO.getBookingDate() +
                                        " " + appointmentResultPOJO.getBookingStarttime() + " is confirmed.");
                                updateconfirmAppointment(getApplicationContext(), message);

                            }
                        }else if(type.equals("courseapproved")){
                            if(AppPreferences.getInstance(getApplicationContext()).getUserType().equals("5")){
                                JSONObject jsonObject=new JSONObject(message);
                                sendCourseApprovedNotification(jsonObject.optString("message"));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendCourseApprovedNotification(String messageBody) {
        Intent intent = new Intent(this, StudentDashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Course Approved.")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

    public void sendConfirmAppointment(String messageBody) {
        Intent intent = new Intent(this, ManagePatientAppointmentsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Appointment Confirmed")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

    public void sendCancelAppointment(String messageBody) {
        Intent intent = new Intent(this, ManagePatientAppointmentsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Appointment Cancelled")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

    public void sendAppointmentBookedNotification(String messageBody) {
        Intent intent = new Intent(this, ManageAppointmentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Appointment Booked")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String messageBody) {
//        updateMyActivity(getApplicationContext(), messageBody);
//            if (message.equalsIgnoreCase("Client::" + message)) {
//        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        Log.d("sunil", "message in fragment:-" + messageBody);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Chat")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());

    }

    public void updateChatActivity(Context context, String message) {
        Intent intent = new Intent(StringUtils.CHAT_CLASS);

        //put whatever data you want to send, if any
        intent.putExtra("message", message);

        //send broadcast
        context.sendBroadcast(intent);
    }

    public void updateBookedAppointment(Context context, String message) {
        Intent intent = new Intent(StringUtils.BOOKED_APPOINTMENT);

        //put whatever data you want to send, if any
        intent.putExtra("message", message);

        //send broadcast
        context.sendBroadcast(intent);
    }

    public void updateconfirmAppointment(Context context, String message) {
        Intent intent = new Intent(StringUtils.APPOINTMENT_CONFIRMED);

        //put whatever data you want to send, if any
        intent.putExtra("message", message);

        //send broadcast
        context.sendBroadcast(intent);
    }

    public void cancelledAppointment(Context context, String message) {
        Intent intent = new Intent(StringUtils.APPOINTMENT_CANCELLED);

        //put whatever data you want to send, if any
        intent.putExtra("message", message);

        //send broadcast
        context.sendBroadcast(intent);
    }

    static void updateMyActivity(Context context, String message) {
        Log.e("data", "no");
        Intent serviceIntent = new Intent(context, FilterService.class);
        context.startService(serviceIntent);
        /*Intent intent = new Intent("client");

        //put whatever data you want to send, if any
        intent.putExtra("message", message);

        //send broadcast
        context.sendBroadcast(intent);*/
    }
}