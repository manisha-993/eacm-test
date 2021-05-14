//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.objects;

import COM.ibm.opicmpdh.middleware.Profile;
import java.util.Comparator;

/*********************************************************************
 * This is used to sort profiles by WGName
 * @author Wendy Stimpson
 */
//$Log: ProfComparator.java,v $
//Revision 1.1  2012/09/27 19:39:12  wendy
//Initial code
//
public class ProfComparator implements Comparator<Profile>   {
	private boolean useOpName = false;
	public ProfComparator(){}
	public ProfComparator(boolean useOp){
		useOpName = useOp;  // allow using opname instead
	}
    public int compare(Profile o1, Profile o2) {
        String nfo1 = null;
        String nfo2 = null;
        if (useOpName){
        	nfo1 = o1.getOPName();
            nfo2 = o2.getOPName();
        }else{
        	nfo1 = o1.getWGName() + o1.toString();
            nfo2 = o2.getWGName() + o2.toString();
        }
       
        return nfo1.toUpperCase().compareTo(nfo2.toUpperCase());
    }
} 