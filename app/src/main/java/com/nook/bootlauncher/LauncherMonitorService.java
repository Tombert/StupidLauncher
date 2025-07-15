package com.nook.bootlauncher;
import static android.app.Service.START_STICKY;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.ActivityManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.os.Handler;
import android.os.Looper;
import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;
import android.util.Log;
import android.content.Intent;
import android.content.ComponentName;
import android.content.Context;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.List;

public class LauncherMonitorService extends Service {
    private Handler handler = new Handler();
    private final long INTERVAL = 60 * 1000; // 60 seconds

    private Runnable poller = new Runnable() {
        @Override
        public void run() {
            String topApp = getForegroundAppPackage(getApplicationContext());
            if (topApp == null || (!topApp.equals("com.simplemobiletools.applauncher") &&
                    !topApp.equals("org.koreader.launcher"))) {
                Intent launchIntent = new Intent();
                launchIntent.setComponent(new ComponentName(
                        "com.simplemobiletools.applauncher",
                        "com.simplemobiletools.applauncher.activities.SplashActivity.Orange"
                ));
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(launchIntent);
            }

            handler.postDelayed(this, INTERVAL);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.post(poller);
        return START_STICKY;
    }
    public String getForegroundAppPackage(Context context) {
        long end = System.currentTimeMillis();
        long begin = end - 10 * 1000; // last 10 seconds

        UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        UsageEvents events = usm.queryEvents(begin, end);

        String lastApp = null;
        UsageEvents.Event event = new UsageEvents.Event();
        while (events.hasNextEvent()) {
            events.getNextEvent(event);
            if (event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                lastApp = event.getPackageName();
            }
        }

        return lastApp;
    }
    private boolean isAppRunning(String packageName) {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processes = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo process : processes) {
            if (process.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND &&
                    process.processName.toLowerCase().contains(packageName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

