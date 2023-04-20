package com.ibm.eannounce.wwprt.catcher;

import com.ibm.eannounce.wwprt.*;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class CloudantListener implements Runnable {

	private boolean running;
	private String t1=null;
	private String t2=null;

	private long interval = 10000;

	private CloudantCatcher catcher;

	public String getT1() {
		return t1;
	}

	public void setT1(String t1) {
		this.t1 = t1;
	}

	public String getT2() {
		return t2;
	}

	public void setT2(String t2) {
		this.t2 = t2;
	}

	public CloudantListener() {
		catcher = new CloudantCatcher();
		catcher.setCatcherListener(this);
		catcher.initConnection();
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
				int span = CloudantUtil.getSpan();
				if(t1==null||t2==null){
				 String[] times=getPreviousTimePeriod(span);
				 t1 = times[0];
				 t2=times[1];
				}
				Log.i("Pulling data t1="+t1+" t2="+t2);
				long start = System.currentTimeMillis();
				pullPrice(t1,t2);
				onCheck();
				Thread.sleep(interval);
				long end = System.currentTimeMillis();
				long timeDiff = (end - start) / 1000; //
				Log.i("Time cost:" + timeDiff + " seconds");
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

	public void pullPrice(String t1,String t2){
		catcher.pullPricetoXML(t1,t2);
	}
	public static String[] getPreviousTimePeriod(int span) {
		String timePeriod[] = new String[2];
		OffsetDateTime now = OffsetDateTime.now();
		int hour = now.getHour();
		if (hour < span) {
			now = now.minusHours(span);
		}
		int roundedHour = hour - (hour % span);
		OffsetDateTime t1 = now.withHour(roundedHour).withMinute(0).withSecond(0).withNano(0); // 设置整点时间
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnnnnnnnnnnnXXX");
		OffsetDateTime t2 = t1.plusHours(span);
		timePeriod[0]=t1.format(formatter);;
		timePeriod[1]=t2.format(formatter);
		return timePeriod;
	}
	
}
