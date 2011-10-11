package com.joepritzel.apkdumper;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.widget.Button;

/**
 * Wrapper for button that holds the application information.
 * 
 * @author Joe Pritzel
 * 
 */
public class CustomButton extends Button {

	/**
	 * Application information.
	 */
	public final ApplicationInfo appInfo;

	public CustomButton(Context context, ApplicationInfo appInfo) {
		super(context);
		this.appInfo = appInfo;
	}

}
