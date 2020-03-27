package com.ibm.eannounce.miw;

import junit.framework.TestCase;

public class CatcherTestCase extends TestCase {

	public void testCatcher() {
		Catcher catcher = new Catcher("test_data/miw.properties",
				new String[] { "test_data/Product.xml" });
		catcher.execute();
		catcher.dispose();
	}

	public void testInvalidChars() {
		Catcher catcher;
		try {
			catcher = new Catcher("test_data/miw.properties",
					new String[] { "catch", "test_data/SkipChars.xml" });
			catcher.dispose();
			fail("Expected an exception");
		} catch (Throwable e) {
		}
	}
	
	public void testSkipChars() {
		Catcher catcher;
		catcher = new Catcher("test_data/miw.skip.properties",
				new String[] { "catch", "test_data/SkipChars.xml" });
		catcher.dispose();
	}
	
}
