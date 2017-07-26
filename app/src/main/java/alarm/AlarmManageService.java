package alarm;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.WindowManager;
import android.widget.SeekBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;


public class AlarmManageService extends Service {
    SeekBar seekbar;
    WindowManager wm;
    ScheduledExecutorService mScheduledExecutorService;
    int mCurrentposition = 0;

    public static enum State {
        STATE_RINGING,
        STATE_OFFHOOK,
        STATE_IDLE
    }

    public static AlarmManageService mInstance = null;
    final Handler handler  = new Handler();
    public static AlarmManageService getInstance() {
        return mInstance;
    }
   int time=10;
    Runnable runnable;

    public AlarmManageService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        StartForground();
        checklocation(time);
//        Log.e("datainonSTartCommand","incomin");
//        showDiaolg();
        // do your jobs here

        return  START_STICKY;

    }

    private void checklocation(int time) {
        new CountDownTimer(30000000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("shubham","It's true");
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }


    public void showDiaolg() {

        new CountDownTimer(30000000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("shubhamdataare", "datain");

            }

            @Override
            public void onFinish() {

            }
        };
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        try {
            String datetime=System.currentTimeMillis()+"";
            Log.d("datetime",datetime);
            Date d=new Date();
            String date_time=simpleDateFormat.format(d);
            Log.e("c_date_time", "date:time:-"+date_time+"");
            Date date1 = simpleDateFormat.parse(date_time);
            Date date2 = simpleDateFormat.parse("24/03/2017 19:18:20");

            printDifference(date1, date2);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.e("datain","incomin");
//        PackageManager smspm4 = getPackageManager();
//
//        smspm4.setComponentEnabledSetting(smsreceiversms4,
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);



    }
    public void printDifference(Date startDate, Date endDate){

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        Log.e("different : " , different+"");

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;
        Log.e("time", (elapsedDays*24*60*60)+"Second");
        Log.e("time", (elapsedHours*60*60)+ "Second");
        Log.e("time", (elapsedMinutes*60)+"Second");
        Log.e("time", (elapsedSeconds)+"Second");
        Log.e("time", (elapsedDays*24*60*60)+(elapsedHours*60*60)+(elapsedMinutes*60)+(elapsedSeconds)+"total Second");



        long timeInMillis4currentt =(elapsedDays*24*60*60)+(elapsedHours*60*60)+(elapsedMinutes*60)+(elapsedSeconds)*(1000);
        Log.e("timeInMillis", timeInMillis4currentt+ "totalSecond");






        Log.e("datain","datainservicenow");
    }
    private void StartForground() {
//        LocationChangeDetector locationChangeDetector = new LocationChangeDetector(context);
//        locationChangeDetector.getLatAndLong();
        Notification notification = new NotificationCompat.Builder(this)
                .setOngoing(false)
                .setSmallIcon(android.R.color.transparent)

                //.setSmallIcon(R.drawable.picture)
                .build();
        startForeground(101,  notification);

    }

}
