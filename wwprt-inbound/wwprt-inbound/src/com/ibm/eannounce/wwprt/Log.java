package com.ibm.eannounce.wwprt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

	public static int INFO = 0;
	public static int VERBOSE = 1;
	public static int DEBUG = 2;

	static SimpleDateFormat dateFormat = new SimpleDateFormat("M/dd/yyyy HH:mm:ss.S");

	static SimpleDateFormat rptDateFormat = new SimpleDateFormat("yyyy-MM-dd_hh.mm.ss.S");

	static PrintStream printstream;

	static int level;

	static boolean persistent;

	public static void init(int level, String name) {
		init(level, true, new File("logs"), name);
	}

	public static void init(int level, boolean persistent, File directory, String name) {
		Log.level = level;
		Log.persistent = persistent;
		if (persistent) {
			if (!directory.exists()) {
				directory.mkdirs();
			}
			File file = new File(directory, name + "." + rptDateFormat.format(new Date()) + ".log");
			try {
				printstream = new PrintStream(new FileOutputStream(file));
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}
	
	public static boolean isLevel(int level) {
		return Log.level >= level;
	}

	public static void close() {
		if (printstream != null) {
			printstream.flush();
			printstream.close();
		}
	}

	public static void i(String info) {
		String msg = dateFormat.format(new Date()) + " INFO: " + info;
		System.out.println(msg);
		if (persistent) {
			printstream.println(msg);
			printstream.flush();
		}
	}
	
	public static void v(String text) {
		if (level >= VERBOSE) {
			String msg = dateFormat.format(new Date()) + " FINE: " + text;
			System.out.println(msg);
			if (persistent) {
				printstream.println(msg);
				printstream.flush();
			}
		}
	}

	public static void d(String text) {
		if (level >= DEBUG) {
			String msg = dateFormat.format(new Date()) + " DEBUG: " + text;
			System.out.println(msg);
			if (persistent) {
				printstream.println(msg);
				printstream.flush();
			}
		}
	}

	public static void e(String error) {
		String msg = dateFormat.format(new Date()) + " ERROR: " + error;
		System.err.println(msg);
		if (persistent) {
			printstream.println(msg);
			printstream.flush();
		}
	}

	public static void e(String error, Throwable exception) {
		String msg = dateFormat.format(new Date()) + " ERROR: " + error;
		System.err.println(msg);
		exception.printStackTrace(System.err);
		if (persistent) {
			printstream.println(msg);
			exception.printStackTrace(printstream);
			printstream.flush();
		}
	}

}
