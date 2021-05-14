/**
 *
 * Copyright (c) 2003-2004 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * e-announce chat functionality
 *
 * @version 1.3  2004/02/10
 * @author Anthony C. Liberto
 *
 * $Log: ChatAction.java,v $
 * Revision 1.1  2007/04/18 19:46:48  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:53  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:12  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/01/28 17:54:21  tony
 * JTest Fromat step 2
 *
 * Revision 1.3  2005/01/26 17:18:50  tony
 * JTest Formatting modifications
 *
 * Revision 1.2  2004/02/23 21:30:53  tony
 * e-announce13
 *
 * Revision 1.1  2004/02/20 22:17:59  tony
 * chatAction
 *
 *
 */
package com.ibm.eannounce.eserver;
import java.io.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ChatAction implements Serializable {
    static final long serialVersionUID = 1L;
    private byte[] actionArray = null;
    private byte[] selArray = null;

    /**
     * chatAction
     * @author Anthony C. Liberto
     */
    public ChatAction() {
        return;
    }

    private Object toObject(byte[] _data) {
        return EChatClientIO.toObject(_data);
    }

    private byte[] toByteArray(Object _o) {
        return EChatClientIO.toByte(_o);
    }

    /**
     * setAction
     * @param _o
     * @author Anthony C. Liberto
     */
    public void setAction(Object _o) {
        if (_o != null) {
            actionArray = toByteArray(_o);
        } else {
            actionArray = null;
        }
        return;
    }

    /**
     * getAction
     * @return
     * @author Anthony C. Liberto
     */
    public Object getAction() {
        if (actionArray != null) {
            return toObject(actionArray);
        }
        return null;
    }

    /**
     * hasAction
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasAction() {
        return actionArray != null;
    }

    /**
     * setSelectedItems
     * @param _o
     * @author Anthony C. Liberto
     */
    public void setSelectedItems(Object _o) {
        if (_o != null) {
            selArray = toByteArray(_o);
        } else {
            selArray = null;
        }
        return;
    }

    /**
     * getSelectedItems
     * @return
     * @author Anthony C. Liberto
     */
    public Object getSelectedItems() {
        if (selArray != null) {
            return toObject(selArray);
        }
        return null;
    }

    /**
     * hasSelection
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasSelection() {
        return selArray != null;
    }
}
