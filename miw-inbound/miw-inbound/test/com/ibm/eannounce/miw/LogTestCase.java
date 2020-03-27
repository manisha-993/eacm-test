package com.ibm.eannounce.miw;

import java.io.File;

import junit.framework.TestCase;

public class LogTestCase extends TestCase {

	public void testDebugLevel() {
		Log.init(Log.DEBUG, true, new File("test_data/logs"), "catcher.debug");
		Log.d("Test", "Debug should be visible");
		Log.i("Test", "Info should be visible");
		Log.close();
	}

	public void testInfoLevel() {
		Log.init(Log.INFO, true, new File("test_data/logs"), "catcher.info");
		Log.d("Test", "Debug should be ignored");
		Log.i("Test", "Only Info should be visible");
		Log.close();
	}

}
