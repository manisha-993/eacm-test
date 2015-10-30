package com.ibm.pprds.epimshw;

import java.io.*;
/**
 * This class contains Utilities for Exception handling
 */
public class ExceptionUtility {
	/**
	 * Get the stack trace in an exception and return it as a string.
	 * @param e the Throwable containing the stacktrace
	 * @return java.lang.String - the stack trace
	 */
	public static String getStackTrace(Throwable e) {

		CharArrayWriter ca = new CharArrayWriter();
		PrintWriter pw = new PrintWriter(ca);
		e.printStackTrace(pw);
		return ca.toString();
	}
	
	/**
	 * Return class Name without appending package specifier
	 * @param e the Throwable containing the stacktrace
	 * @return java.lang.String - class name
	 */

	public static String getShortClassName(Object obj) {

		String className = obj.getClass().getName();
		int lastIndexOfPackage = className.lastIndexOf(".");
		if (lastIndexOfPackage != -1) {
			return className.substring(lastIndexOfPackage + 1);
		} else {

			return className;
		}
	}
}
