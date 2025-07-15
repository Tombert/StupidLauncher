package com.nook.bootlauncher;

import android.accessibilityservice.AccessibilityService;
import android.content.ComponentName;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

public class ButtonInterceptorService extends AccessibilityService {

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN &&
                event.getKeyCode() == KeyEvent.KEYCODE_F10) {

            Intent launchIntent = new Intent(Intent.ACTION_MAIN);
            launchIntent.setComponent(new ComponentName(
                    "com.simplemobiletools.applauncher",
                    "com.simplemobiletools.applauncher.activities.SplashActivity.Orange"
            ));

            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(launchIntent);

            return true; // Consume the event
        }
        return super.onKeyEvent(event);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // Not needed for key interception
    }

    @Override
    public void onInterrupt() {
        // Not needed for key interception
    }
}
