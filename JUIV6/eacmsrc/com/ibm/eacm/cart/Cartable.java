//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.cart;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;

/**
 * This is used for objects that can put items on the cart
 * @author Wendy Stimpson
 */
//$Log: Cartable.java,v $
//Revision 1.1  2012/09/27 19:39:18  wendy
//Initial code
//
public interface Cartable {
	EntityItem[] getAllEntityItems();
	boolean supportsGetAll();
	boolean hasCartableData();
	EntityItem[] getSelectedEntityItems(boolean showException);
	EntityGroup getSelectedEntityGroup();
	Profile getProfile();
}
