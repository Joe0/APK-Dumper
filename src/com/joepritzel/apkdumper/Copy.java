package com.joepritzel.apkdumper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.util.Log;

public class Copy {

	public static void copy(String src, String dest) throws IOException {
		copy(src, dest, false);
	}

	public static void copy(String src, String dest, boolean su)
			throws IOException {
		if (su) {
			String[] command = { "su", "-c", "busybox cp " + src + " " + dest };
			Runtime.getRuntime().exec(command);
			Log.d("APKDUMPER", "loc = " + src);
		} else {
			Log.d("APKDUMPER", "loc = " + src);
			Log.d("APKDUMPER", "dest = " + dest);
			FileChannel in = null, out = null;
			try {
				in = new FileInputStream(src).getChannel();
				File destF = new File(dest);
				if (!destF.exists())
					destF.createNewFile();
				out = new FileOutputStream(destF).getChannel();
				out.transferFrom(in, 0L, in.size());
			} finally {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
			}
		}
	}
}
