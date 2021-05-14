/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: SubAction.java,v $
 * Revision 1.1  2007/04/18 19:42:18  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:41  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:58:57  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/02 17:30:22  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:19  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:26  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2003/03/03 18:03:41  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2002/11/07 16:58:31  tony
 * added/adjusted copyright statement
 *
 */
package com.elogin;
import COM.ibm.eannounce.objects.EANActionItem;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public abstract interface SubAction {
	/**
     * setKey
     * @param _s
     * @author Anthony C. Liberto
     */
    void setKey(String _s);
	/**
     * getKey
     * @return
     * @author Anthony C. Liberto
     */
    String getKey();
	/**
     * getText
     * @return
     * @author Anthony C. Liberto
     */
    String getText();
	/**
     * setActionItem
     * @param _eai
     * @author Anthony C. Liberto
     */
    void setActionItem(EANActionItem _eai);
}
