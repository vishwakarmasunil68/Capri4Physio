package com.capri4physio.fragment.assessment;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.WindowManager;
import android.widget.SeekBar;

import java.util.concurrent.ScheduledExecutorService;


public class FilterService extends Service {
    SeekBar seekbar;
    WindowManager wm;
    ScheduledExecutorService mScheduledExecutorService;
    int mCurrentposition = 0;

    public static enum State {
        STATE_RINGING,
        STATE_OFFHOOK,
        STATE_IDLE
    }

    public static FilterService mInstance = null;

    public static FilterService getInstance() {
        return mInstance;
    }

    public void onCreate() {
        super.onCreate();

        FilterService.mInstance = this;

    }

    public FilterService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("datainonSTartCommand","incomin");
        showDiaolg();
        // do your jobs here

        return super.onStartCommand(intent, flags, startId);

    }



    public void showDiaolg() {
//        Intent intent =(new Intent(getApplicationContext(), VideoActivity.class));
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Log.e("datain","incomin");
//        startActivity(intent);
    }



}
