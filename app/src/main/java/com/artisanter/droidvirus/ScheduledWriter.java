package com.artisanter.droidvirus;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class ScheduledWriter extends BroadcastReceiver {
    public static void set(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ScheduledWriter.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        assert am != null;
        am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 30 * 60 * 1000, pIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        new WriteTask().execute(context);
    }
}
