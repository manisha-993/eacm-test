//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.ui;

import com.ibm.eacm.*;
import com.ibm.eacm.actions.*;

import com.ibm.eacm.objects.*;
import com.ibm.eacm.table.BaseTable;

/******************************************************************************
* This is used to display the find/replace frame. 
* @author Wendy Stimpson
*/
// $Log: FindRepFrame.java,v $
// Revision 1.1  2012/09/27 19:39:11  wendy
// Initial code
//

public class FindRepFrame extends EACMFrame 
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2012  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.1 $";
    
    public FindRepFrame(FindRepMgr frm)  {
        super("find.panel");
        
    	closeAction = new CloseFrameAction(this);
    	
    	setIconImage(Utils.getImage("find.gif"));
    	
    	getContentPane().add(frm);
    	
        finishSetup(EACM.getEACM()); 
    }
    
    public EACMAction getCloseAction(){
    	return closeAction;
    }
    
    protected BaseTable getJTable() { return null;}
}