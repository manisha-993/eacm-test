/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: UIPreferences.java,v $
 * Revision 1.1  2007/04/18 19:43:03  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:56  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:58:58  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/02/04 15:22:07  tony
 * JTest Format Third Pass
 *
 * Revision 1.4  2005/02/02 17:30:23  tony
 * JTest Second Pass
 *
 * Revision 1.3  2005/01/27 23:18:20  tony
 * JTest Formatting
 *
 * Revision 1.2  2004/02/19 21:34:52  tony
 * e-announce1.3
 *
 * Revision 1.1.1.1  2004/02/10 17:00:28  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2003/10/29 00:22:23  tony
 * removed System.out. statements.
 *
 * Revision 1.2  2003/05/30 21:09:17  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.1.1.1  2003/03/03 18:04:02  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2002/11/07 16:58:10  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce;
import com.elogin.*;
import java.io.*;
import java.util.HashMap;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.objects.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class UIPreferences extends HashMap implements Serializable, EAccessConstants {
	private static final String ATTCODE = "UIPREFERENCES";
	private static final String E_TYPE = "OPWG";
	private static final int NLS = 1;
	static final long serialVersionUID = 19721222L;

	private transient boolean changed = false;

	/**
     * UIPreferences
     * @author Anthony C. Liberto
     */
    public UIPreferences() {
		super();
	}

	/**
     * eaccess
     * @return
     * @author Anthony C. Liberto
     */
    public static EAccess eaccess() {
		return EAccess.eaccess();
	}

	/**
     * getAttributeCode
     * @return
     * @author Anthony C. Liberto
     */
    public String getAttributeCode() {
		return ATTCODE;
	}

	/**
     * getEntityType
     * @return
     * @author Anthony C. Liberto
     */
    public String getEntityType() {
		return E_TYPE;
	}

	/**
     * setPreference
     * @param _key
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setPreference(String _key, boolean _b) {
		setPreference(_key, new Boolean(_b));
	}

	/**
     * isPreference
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isPreference(String _key) {
		Object o = getPreference(_key);
		if (o != null && o instanceof Boolean) {
			return ((Boolean)o).booleanValue();
		}
		return false;
	}

	/**
     * setPreference
     * @param _key
     * @param _o
     * @author Anthony C. Liberto
     */
    public void setPreference(String _key, Object _o) {
		if (containsKey(_key)) {
			remove(_key);
		}
		put(_key,_o);
		changed = true;
	}

	/**
     * getPreference
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public Object getPreference(String _key) {
		if (containsKey(_key)) {
			return get(_key);
		}
		return null;
	}

	/**
     * contains
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public boolean contains(String _key) {
		return containsKey(_key);
	}

	/**
     * writeDatabaseObject
     * @author Anthony C. Liberto
     */
    public void writeDatabaseObject() {
		Profile prof = null;
        int OPWGID = -1;
        String now = null;
        ControlBlock cb = null;
        Blob blob = null;
        if (!changed) {
            return;
		}
		prof = eaccess().getActiveProfile();
		OPWGID = prof.getOPWGID();
		now = eaccess().getNow(ISO_DATE);
		cb = new ControlBlock(now,FOREVER,now,FOREVER,0);
		blob = new Blob(prof.getEnterprise(), E_TYPE, OPWGID, ATTCODE, Zip.zipObject(this), ATTCODE, NLS, cb);
		eaccess().dBase().putBlob(blob,NLS,null);
		return;
	}

	/**
     * readDatabaseObject
     * @return
     * @author Anthony C. Liberto
     */
    public static UIPreferences readDatabaseObject() {
		Profile prof = eaccess().getActiveProfile();
		int OPWGID = prof.getOPWGID();
		UIPreferences oPref = null;
		Blob b = eaccess().dBase().getBlob(E_TYPE,OPWGID,ATTCODE,NLS,null);
		if (b == null || b.size() <= 0) {
			return new UIPreferences();
		}
		oPref = (UIPreferences)Unzip.unzipObject(b.m_baAttributeValue);
		return oPref;
	}
}
