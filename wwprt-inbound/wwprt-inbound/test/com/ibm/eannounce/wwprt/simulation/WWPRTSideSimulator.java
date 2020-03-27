package com.ibm.eannounce.wwprt.simulation;

import java.io.File;

import com.ibm.eannounce.wwprt.MQ;
import com.ibm.eannounce.wwprt.test.TestUtils;


public class WWPRTSideSimulator implements Runnable {
	
	private MQ mq;
	
	private long interval = 2000;
	
	private boolean running;

	public void setInterval(long interval) {
		this.interval = interval;
	}

	public void start() {
		if (running) {
			System.out.println("WWPRT Simulator is already running");
		} else {
			Thread thread = new Thread(this);
			thread.start();
		}
	}
	
	public void stop() {
		running = false;
	}
		
	public void run() {
		mq = new MQ(TestUtils.MQ_WWPRT);
		running = true;
		System.out.println("Simulator is now running...");
		while (running) {
			try {
				mq.putMessage(new File("test_data/prices.xml"));
				Thread.sleep(interval);
			} catch (Exception e) {
				e.printStackTrace();
				running = false;
			}
		}
		mq.close();
		System.err.println("WWPRT Simulator has stopped.");
	}
	
}
