# Stupid Launcher

So I bought a Nook Glowlight 4 Plus recently.  Despite it running Android, and despite it being easy to install stuff via ADB, it's actually hard to *use* the stuff sideloaded, like KOReader. Stuff is easy to launch via ADB but I don't want to plug in my computer every time I reboot the Nook. 

My solution? Map the top left button on the Glowlight to launch a launcher so I can install vanilla Android applications. 

It's inelegant, but I couldn't get rooting to work, so this will have to work. 

This requires that you have [Simple App Launcher](https://f-droid.org/en/packages/com.simplemobiletools.applauncher/) installed.  

# Install

I found the easiest way to install it was to get my Nook into USB debug mode, then do 

```
$ adb push the_apk.apk /sdcard/

$ adb shell

E70P74_android:/ $ cd sdcard
E70P74_android:/sdcard $ pm install -r the_apk.apk

```

From there, you need to enable it in accessibility settings: 

```
adb shell pm grant com.nook.bootlauncher android.permission.WRITE_SECURE_SETTINGS
```

This should pop up the screen for accessibility settings.  There should be something called "Library Tools", click on that and enable that. 

Once you do that, you should be able to click the top left button and the simple launcher will pop up. 

# Disclaimer

I am not an Android developer.  I know basically nothing about making Android apps.  While this is in user space and should be relatively harmless, I take absolutely no responsibility if you break your device. 

# License

This code is hereby released into the public domain, without warranty, and may be used with or without attribution or licensing for any purpose. 
