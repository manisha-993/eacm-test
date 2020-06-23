//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.objects;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import com.ibm.eacm.preference.BehaviorPref;


/**
 * this class formats log messages
 * @author Wendy Stimpson
 */
//$Log: LogFormatter.java,v $
//Revision 1.2  2015/03/03 15:48:29  stimpsow
//keep rmi logging silent unless rmiVerbose is armed
//
//Revision 1.1  2012/09/27 19:39:13  wendy
//Initial code
//
public class LogFormatter extends Formatter implements EACMGlobals
{
    private Date date = new Date();
    private SimpleDateFormat dateFormat = new SimpleDateFormat(LOGFORMAT_DATE);
 
    /**
     * @brief Format the given LogRecord.
     * @param record the log record to be formatted.
     * @return a formatted log record
     */
    public synchronized String format(LogRecord record) {
    	// if STDERR or STDOUT, just forward the msg
    	if( record.getLevel().equals(StdOutErrLevel.STDERR) ||
    			record.getLevel().equals(StdOutErrLevel.STDOUT)){
    		String msg = record.getMessage();
    		//RMI will not be silent for sun.rmi.server.UnicastRef messages
    		if (!Utils.isArmed(RMI_VERBOSE_ARM_FILE)) {
    			int unicastId=msg.indexOf("UnicastRef");
    			if(unicastId!=-1){
    				return "";
    			}
    		}
    		return msg+RETURN;
    	}
    	
        StringBuilder sb = new StringBuilder();
 
        date.setTime(record.getMillis());
        sb.append(dateFormat.format(date));
        sb.append(" ");
        if(BehaviorPref.showSrcMethodName()){
        	if (record.getSourceClassName() != null) {    
        		String name = record.getSourceClassName();
        		name = name.substring(name.lastIndexOf(".") + 1);
        		sb.append(name);
        	} else {
        		sb.append(record.getLoggerName());
        	}
        	if (record.getSourceMethodName() != null) {    
        		sb.append(".");
        		sb.append(record.getSourceMethodName());
        	}
        	sb.append(" ");
        }
        
		//RMI will not be silent for sun.rmi.server.UnicastRef messages
		if (!Utils.isArmed(RMI_VERBOSE_ARM_FILE)) {
			if (record.getSourceClassName() != null) {    
        		String name = record.getSourceClassName();
    			int unicastId=name.indexOf("UnicastRef");
    			if(unicastId!=-1){
    				return "";
    			}
        	} 
		}
		
        String message = formatMessage(record);
        if(!record.getLevel().equals(AlwaysLevel.ALWAYS)){  // hide the always level name
        	sb.append(record.getLevel().getLocalizedName());
        	sb.append(": ");
        }
        sb.append(message);
        sb.append(RETURN);
        if (record.getThrown() != null) {
            try {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                record.getThrown().printStackTrace(pw);
                pw.close();
                sb.append(sw.toString());
            } catch (Exception ex) {
            }
        }
        return sb.toString();
    }
}