package com.ibm.eannounce.lenovo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

	public static int INFO = 0;
	public static int DEBUG = 1;

	static SimpleDateFormat dateFormat = new SimpleDateFormat("M/dd/yyyy HH:mm:ss.S");

	static SimpleDateFormat rptDateFormat = new SimpleDateFormat("yyyy-MM-dd_hh.mm.ss.S");

	static PrintStream printstream;

	static int level;

	static boolean persistent;

	public static void init(int level, boolean persistent, File directory, String name) {
		Log.level = level;
		Log.persistent = persistent;
		if (persistent) {
			if (!directory.exists()) {
				directory.mkdirs();
			}
			File file = new File(directory, name + "_" + rptDateFormat.format(new Date()) + ".log");
			try {
				printstream = new PrintStream(new FileOutputStream(file));
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}

	public static void close() {
		if (printstream != null) {
			printstream.flush();
			printstream.close();
		}
	}

	public static void i(String tag, String info) {
		String msg = dateFormat.format(new Date()) + " [I] " + tag + ": " + info;
		System.out.println(msg);
		if (persistent) {
			printstream.println(msg);
			printstream.flush();
		}
	}

	public static void d(String tag, String text) {
		if (level >= DEBUG) {
			String msg = dateFormat.format(new Date()) + " [D] " + tag + ": " + text;
			System.out.println(msg);
			if (persistent) {
				printstream.println(msg);
				printstream.flush();
			}
		}
	}

	public static void e(String tag, String error) {
		String msg = dateFormat.format(new Date()) + " " + tag + ": " + error;
		System.err.println(msg);
		if (persistent) {
			printstream.println(msg);
			printstream.flush();
		}
	}

	public static void e(String tag, String error, Throwable exception) {
		String msg = dateFormat.format(new Date()) + " " + tag + ": " + error;
		System.err.println(msg);
		exception.printStackTrace(System.err);
		if (persistent) {
			printstream.println(msg);
			exception.printStackTrace(printstream);
			printstream.flush();
		}
	}

}
