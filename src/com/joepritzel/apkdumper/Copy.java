package com.joepritzel.apkdumper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * This class is used to store methods to copy files.
 * 
 * @author Joe Pritzel
 * 
 */
public class Copy {

	/**
	 * Attempts to copy the specified file to the specified location.
	 * 
	 * @param src
	 *            The file to copy.
	 * @param dest
	 *            The destination that the file should be copied to.
	 * @throws IOException
	 */
	public static void copy(String src, String dest) throws IOException {
		copy(src, dest, false);
	}

	/**
	 * Attempts to copy the specified file to the specified location.
	 * 
	 * @param src
	 *            The file to copy.
	 * @param dest
	 *            The destination that the file should be copied to.
	 * @param su
	 *            Should the method use su -c busybox cp?
	 * @throws IOException
	 */
	public static void copy(String src, String dest, boolean su)
			throws IOException {
		if (su) {
			suCopy(src, dest);
		} else {
			nioCopy(src, dest);
		}
	}

	/**
	 * Attempts to copy the file by elevating privileges and using busybox.
	 * 
	 * @param src
	 *            The file to copy.
	 * @param dest
	 *            The destination that the file should be copied to.
	 * @throws IOException
	 */
	private static void suCopy(String src, String dest) throws IOException {
		String[] command = { "su", "-c", "busybox cp " + src + " " + dest };
		Runtime.getRuntime().exec(command);
	}

	/**
	 * Attempts to copy the file by using NIO.
	 * 
	 * @param src
	 *            The file to copy.
	 * @param dest
	 *            The destination that the file should be copied to.
	 * @throws IOException
	 */
	private static void nioCopy(String src, String dest) throws IOException {
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
