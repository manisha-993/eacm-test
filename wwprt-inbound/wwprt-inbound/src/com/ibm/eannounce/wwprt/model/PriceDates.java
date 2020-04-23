package com.ibm.eannounce.wwprt.model;

import java.text.ParseException;
import java.util.Date;

import com.ibm.eannounce.wwprt.Context;

public class PriceDates {


	private String startValue;
	private String endValue;
	
	private Date start;
	
	private Date end;

	public Date start() {
		return start;
	}

	public void setStart(Date start) throws ParseException {
		startValue = Context.XML_DATE_FORMAT.format(start);
		this.start = start;
	}

	public Date end() {
		return end;
	}

	public void setEnd(Date end) throws ParseException  {
		endValue = Context.XML_DATE_FORMAT.format(end);
		this.end = end;
	}
	
	public String startAsText() {
		return startValue;
	}
	
	public String endAsText() {
		return endValue;
	}
	
	@Override
	public String toString() {
		return "["+startValue+" -> "+endValue+"]";
	}
	
}
