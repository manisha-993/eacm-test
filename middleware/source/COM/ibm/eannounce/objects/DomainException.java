// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: DomainException.java,v $
// Revision 1.2  2008/02/11 19:56:27  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2007/08/02 21:21:48  wendy
// RQ0713072645
//
//
package COM.ibm.eannounce.objects;

import COM.ibm.opicmpdh.middleware.MiddlewareException;
import java.util.Vector;

/**
 * This exception is thrown when the users domain does not contain the PDHDomain of the EntityItem
 */
public class DomainException extends MiddlewareException {
	private static final long serialVersionUID = 7492606967608922586L;
	private Vector eiVct = new Vector();
	private Vector eiMsgVct = new Vector();

    /**
    * Constructor
    */
    public DomainException() {}
    /**
    * Constructor
    * @param msg String the specified detail message
    */
    private DomainException(String msg) { // don't allow an exception with a msg
        super(msg);
    }

    /**
    * Add msg for this item
    * This interface is to support adding this exception to an EntityItemException
    * @param item EntityItem
    * @param msg String the specified detail message
    */
    public void add(EntityItem item, String msg) {
		eiVct.add(item);
		eiMsgVct.add(msg);
	}

    /**
    * Get msg for this exception
    * @return String the message
    */
    public String getMessage() {
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<eiMsgVct.size(); i++){
			sb.append(getItem(i).toString()+getMessage(i)+"\n");
		}
		return sb.toString();
	}

    /**
    * Get item for this index
    * This interface is to support adding this exception to an EntityItemException
    * @param i int
    * @return EntityItem
    */
    public EntityItem getItem(int i) {
		EntityItem item = null;
		if (i < eiVct.size()){
			item = (EntityItem)eiVct.elementAt(i);
		}
		return item;
	}
    /**
    * Get msg for this index
    * This interface is to support adding this exception to an EntityItemException
    * @param i int
    * @return String the message
    */
    public String getMessage(int i) {
		String item = null;
		if (i < eiMsgVct.size()){
			item = (String)eiMsgVct.elementAt(i);
		}
		return item;
	}
    /**
    * getErrorCount
    * This interface is to support adding this exception to an EntityItemException
    *
    * @return int
    */
    public int getErrorCount() {
        return eiMsgVct.size();
    }
    /**
    * release memory
    */
    public void dereference() {
        eiMsgVct.clear();
        eiVct.clear();
    }
    /**
    * @return the date/time this class was generated
    */
    public String getVersion() {
        return new String("$Id: DomainException.java,v 1.2 2008/02/11 19:56:27 wendy Exp $");
    }
}
