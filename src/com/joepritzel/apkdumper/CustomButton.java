package com.joepritzel.apkdumper;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.widget.Button;

public class CustomButton extends Button {
	
	public ApplicationInfo appInfo;

	public CustomButton(Context context, ApplicationInfo appInfo) {
		super(context);
		this.appInfo = appInfo;
	}

}
