package com.joepritzel.apkdumper;

import java.io.File;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

/**
 * The main activity that displays all the applications on the device, and
 * handles the events.
 * 
 * @author Joe Pritzel
 * 
 */
public class APKDumperActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final ScrollView sv = new ScrollView(this);
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		sv.addView(ll);

		// Creates a list of all apps, and their ApplicationInfo objects.
		PackageManager pm = getPackageManager();
		List<ApplicationInfo> appinfo_list = pm.getInstalledApplications(0);
		for (ApplicationInfo appinfo : appinfo_list) {
			final CustomButton b = new CustomButton(this, appinfo);
			b.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					// Try to copy the file
					try {
						// The location to save the APK.
						String fileLoc = Environment
								.getExternalStorageDirectory()
								+ File.separator
								+ b.appInfo.packageName + ".apk";

						// Copy the file. If it isn't readable try to use su &
						// busybox
						Copy.copy(b.appInfo.sourceDir, fileLoc, !new File(
								b.appInfo.sourceDir).canRead());

						// Display toast if successful
						Toast.makeText(sv.getContext(),
								"Successfully copied app to " + fileLoc + ".",
								Toast.LENGTH_SHORT).show();
					} catch (IOException e) {
						// If exception is thrown display toast stating it
						// failed.
						Toast.makeText(sv.getContext(),
								"Unable to copy app to SD card.",
								Toast.LENGTH_SHORT).show();
					}
				}

			});
			b.setText(appinfo.packageName);
			ll.addView(b);
		}
		this.setContentView(sv);
	}
}