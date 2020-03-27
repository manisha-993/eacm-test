package com.ibm.eannounce.wwprt.testcase;

import org.junit.Test;

public class TestCases extends AbstractTestCase {

	@Test
	public void testCase1() throws Exception {
		process("test_data/testcases/case1_step1.xml");
		printPriceTable();
		process("test_data/testcases/case1_step2.xml");
		printPriceTable();
	}
	
	@Test
	public void testCase2() throws Exception {
		process("test_data/testcases/case2_step1.xml");
		printPriceTable();
		process("test_data/testcases/case2_step2.xml");
		printPriceTable();
	}
	
	@Test
	public void testCase3() throws Exception {
		process("test_data/testcases/case3_step1.xml");
		printPriceTable();
		process("test_data/testcases/case3_step2.xml");
		printPriceTable();
	}
	
	@Test
	public void testCase4() throws Exception {
		process("test_data/testcases/case4_step1.xml");
		printPriceTable();
		process("test_data/testcases/case4_step2.xml");
		printPriceTable();
	}
	
	@Test
	public void testCase5() throws Exception {
		process("test_data/testcases/case5_step1.xml");
		printPriceTable();
		process("test_data/testcases/case5_step2.xml");
		printPriceTable();
	}
	
	@Test
	public void testCase6() throws Exception {
		process("test_data/testcases/case6_step1.xml");
		printPriceTable();
		process("test_data/testcases/case6_step2.xml");
		printPriceTable();
	}
}
