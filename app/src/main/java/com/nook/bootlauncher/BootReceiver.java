package com.nook.bootlauncher;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;


public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                try {
                    Intent launchIntent = new Intent();
                    launchIntent.setComponent(new ComponentName(
                            "com.simplemobiletools.applauncher",
                            "com.simplemobiletools.applauncher.activities.SplashActivity.Orange"
                    ));
                    launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(launchIntent);
                } catch (Exception e) {
                    Log.e("BootReceiver", "Failed to launch app after boot", e);
                }
            }, 30_000); // 30 seconds
        }
    }
}


