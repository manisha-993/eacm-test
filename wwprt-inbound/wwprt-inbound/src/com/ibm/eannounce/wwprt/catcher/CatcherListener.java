package com.ibm.eannounce.wwprt.catcher;

import com.ibm.eannounce.wwprt.Context;
import com.ibm.eannounce.wwprt.Log;
import com.ibm.eannounce.wwprt.MQ;

public class CatcherListener implements Runnable {
	
	private boolean running;

	private long interval = 10000;
	
	private Catcher catcher;
	
	public CatcherListener() {
		catcher = new Catcher();
	}
	
	public void setInterval(long interval) {
		this.interval = interval;
	}
	
	public void start() {
		if (running) {
			Log.i("Catcher is already running");
		} else {
			Thread thread = new Thread(this);
			thread.start();
		}
	}
	
	public void stop() {
		running = false;
	}

	public void run() {
		running = true;
		Log.i("Catcher is now running...");
		while (running) {
			try {
				checkMessages();
				onCheck();
				Thread.sleep(interval);
			} catch (InterruptedException e) {
			} catch (Throwable e) {
				Log.e( "Error on catcher loop", e);
				running = false;
			}
			if (!running)
				break;
		}
		Log.i("Catcher has stopped.");
		onStop();
	}
	
	public void onCheck() {}
	
	public void onStop() {}

	public void checkMessages() throws Exception {
		MQ mq = Context.get().getMq();
		if (mq.getQueuedMessageCount() > 0) {
			// Has messages in queue
			if (catcher.initConnection()) {
				mq.getMessages(catcher);
			}
		} else {
			catcher.noMessagesToRead();
		}
	}
	
}
