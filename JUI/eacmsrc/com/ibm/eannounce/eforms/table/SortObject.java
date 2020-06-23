/**
 * Copyright (c) 2001 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 2.3
 * @author Anthony C. Liberto
 *
 * $Log: SortObject.java,v $
 * Revision 1.1  2007/04/18 19:45:12  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:09  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:59:07  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/01/31 20:47:48  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 22:17:57  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2004/02/10 16:59:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2003/03/03 18:03:51  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2002/02/13 18:33:09  tony
 * trace statement adjustment.
 *
 * Revision 1.1.1.1  2001/11/29 19:00:24  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2001/09/18 00:25:19  joan
 * eAnnounce1.1 9/17/01--add code to handle null pointer exception
 * when null objects are returned from middleware.
 *
 * Revision 1.1.1.1  2001/08/06 21:39:14  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2001/06/24 17:44:03  tony
 * adjusted sortObject logic to clarify what data was what.
 *
 * Revision 1.1.1.1  2001/04/19 00:59:01  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2001/03/22 22:18:02  tony
 * adjusted log information to remove duplicates
 *
 * Revision 1.3  2001/03/22 21:10:06  tony
 * Added standard copyright to all
 * modules
 *
 * Revision 1.2  2001/03/22 18:54:42  tony
 * added log keyword
 *
 */
package com.ibm.eannounce.eforms.table;

import java.io.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class SortObject implements Serializable, Cloneable{
	private String s = null;
	private int r = 0;	//old row model
	private int nr = 0; //new Row view
	private boolean visible = false;

	static final long serialVersionUID = 2577637642626754170L;

	/**
     * sortObject
     * @param ss
     * @param ii
     * @author Anthony C. Liberto
     */
    public SortObject(String ss, int ii) {
		setString(ss);
		setModel(ii);
		return;
	}

	/**
     * sortObject
     * @param ss
     * @param eng
     * @param forn
     * @author Anthony C. Liberto
     */
    public SortObject(String ss, int eng, int forn) {
		setString(ss);
		setEng(eng);
		setForeign(forn);
		return;
	}

	/**
     * sortObject
     * @param ss
     * @param ii
     * @param rr
     * @param vis
     * @author Anthony C. Liberto
     */
    public SortObject(String ss, int ii, int rr, boolean vis) {
		setString(ss);
		setModel(ii);
		setView(rr);
		setVisible(vis);
		return;
	}

	/**
     * setString
     * @param ss
     * @author Anthony C. Liberto
     */
    public void setString(String ss) {
		if(ss!=null) {										//eAnnounce1.1 9/27/01
			s = new String(ss);
		}
		return;
	}

	/**
     * @see java.lang.Object#toString()
     * @author Anthony C. Liberto
     */
    public String toString() {
		return s;
	}

	/**
     * setModel
     * @param ii
     * @author Anthony C. Liberto
     */
    public void setModel(int ii) {
		r = new Integer(ii).intValue();
		return;
	}

	/**
     * getModel
     * @return
     * @author Anthony C. Liberto
     */
    public int getModel() {
		return r;
	}

//	public void setCol(int ii) {
//		setInt(ii);
//		return;
//	}

	/**
     * setEng
     * @param ii
     * @author Anthony C. Liberto
     */
    public void setEng(int ii) {
		setModel(ii);
		return;
	}

	/**
     * setForeign
     * @param ii
     * @author Anthony C. Liberto
     */
    public void setForeign(int ii) {
		setView(ii);
		return;
	}

//	public int getCol() {
//		return getInt();
//	}

	/**
     * getLang
     * @param nls
     * @return
     * @author Anthony C. Liberto
     */
    public int getLang(int nls) {
		if (nls == 1) {
			return getEng();
		}
		return getForeign();
	}

	/**
     * getEng
     * @return
     * @author Anthony C. Liberto
     */
    public int getEng() {
		return getModel();
	}

	/**
     * getForeign
     * @return
     * @author Anthony C. Liberto
     */
    public int getForeign() {
		return nr;
	}

	/**
     * setView
     * @param rr
     * @author Anthony C. Liberto
     */
    public void setView(int rr) {
		nr = new Integer(rr).intValue();
		return;
	}

	/**
     * getView
     * @return
     * @author Anthony C. Liberto
     */
    public int getView() {
		return nr;
	}

	/**
     * setVisible
     * @param b
     * @author Anthony C. Liberto
     */
    public void setVisible(boolean b) {
		visible = new Boolean(b).booleanValue();
		return;
	}

	/**
     * isVisible
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isVisible() {
		return visible;
	}
}

