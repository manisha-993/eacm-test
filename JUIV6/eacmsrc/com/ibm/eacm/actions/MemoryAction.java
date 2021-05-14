//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.actions;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.eacm.objects.EACMProperties;
import com.ibm.eacm.objects.Utils;

/*********************************************************************
 * This is used for memory info request
 * @author Wendy Stimpson
 */
//$Log: MemoryAction.java,v $
//Revision 1.2  2014/01/17 20:40:44  wendy
//display as MB
//
//Revision 1.1  2012/09/27 19:39:17  wendy
//Initial code
//
public class MemoryAction extends EACMAction
{
    private float maxMem = 0F;
	private static final long serialVersionUID = 1L;
	private static NumberFormat numformat;
	private static final long MEGABYTE = 1024L * 1024L;

	private static float bytesToMegabytes(float bytes) {
		return bytes / MEGABYTE;
	}
	
	static {
		numformat = NumberFormat.getInstance();
		numformat.setGroupingUsed(false);
		numformat.setMaximumFractionDigits(2);
		//numformat.setMinimumFractionDigits(1);
	}
	
	public MemoryAction() {
		super(MEMORY_ACTION,KeyEvent.VK_M, Event.CTRL_MASK);
		
        maxMem = Runtime.getRuntime().maxMemory();
        if(maxMem==Long.MAX_VALUE){
        	//If there is no inherent limit then the value is Long.MAX_VALUE, so use fixed value-seems meaningless though
        	maxMem = Float.valueOf(EACMProperties.getProperty("maxMemory","196000000")).floatValue();
        }
	}
	public void actionPerformed(ActionEvent e) {
		memory(true);
	}
	public void listMemory(){
		memory(false);
	}
    private void memory(boolean _bShow) {
        System.gc();
        Runtime r = Runtime.getRuntime();
        long lFree = r.freeMemory();
        long lTotal = r.totalMemory();
        long consMem = (lTotal - lFree);
        float pctUsed = ((consMem / maxMem) * 100);
        float curAvail = ((lFree * 100) / lTotal);
                
        Object parms[] = new Object[6];
        //  memoryDisplay = Maximum memory: {0}\n
        //Total available memory: {1}\n
        //Free available memory: {2}\n
        //Consumed memory: {3}\n
        //Memory used: {4}%\n
        //Currently available: {5}%

        parms[0] = Float.toString(bytesToMegabytes((long)maxMem));
        parms[1] = Float.parseFloat(numformat.format(bytesToMegabytes(lTotal)));
        parms[2] = Float.parseFloat(numformat.format(bytesToMegabytes(lFree)));
        parms[3] = Float.parseFloat(numformat.format(bytesToMegabytes(consMem)));
        parms[4] = Float.parseFloat(numformat.format(pctUsed));
        parms[5] = Float.parseFloat(numformat.format(curAvail));
     
        String meminfo = Utils.getResource("memoryDisplay", parms);
		Logger.getLogger(APP_PKG_NAME).log(Level.CONFIG,meminfo);
        if (_bShow) {
        	com.ibm.eacm.ui.UI.showFYI(null, meminfo);
        }
    }
}