/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: TransferObject.java,v $
 * Revision 1.1  2007/04/18 19:42:18  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:41  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:58:58  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/02 17:30:22  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:18  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:27  tony
 * This is the initial load of OPICM
 *
 * Revision 1.3  2003/04/16 22:42:16  tony
 * adjusted copy logic.
 *
 * Revision 1.2  2003/04/16 17:18:37  tony
 * cleaned-up code.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:41  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2002/11/07 16:58:12  tony
 * added/adjusted copyright statement
 *
 */
package com.elogin;
import java.awt.datatransfer.*;
import java.io.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class TransferObject extends StringSelection implements Transferable, ClipboardOwner {
//	private DataFlavor[] flavor = null;
	private Object o = null;
	private String[] value = null;
	private String type = null;
	private int OPWGID = -1;
	private int columns = -1;																		//19928
	private boolean tableCopy = false;

	/**
     * transferObject
     * @param _s
     * @param _type
     * @param _o
     * @param _id
     * @author Anthony C. Liberto
     */
    public TransferObject(String _s, String _type, Object _o, int _id) {
		super(_s);
		setObject(_o);
		setType(_type);
		setOPWGID(_id);
//		buildFlavor();
		return;
	}

	/**
     * transferObject
     * @param _s
     * @param _val
     * @param _type
     * @param _o
     * @param _cols
     * @param _id
     * @author Anthony C. Liberto
     */
    public TransferObject(String _s, String[] _val, String _type, Object _o, int _cols, int _id) {	//19928
		super(_s);																					//19928
		setArray(_val);																				//19928
		setObject(_o);																				//19928
		setType(_type);																				//19928
		setColumns(_cols);																			//19928
		setOPWGID(_id);																				//19928
//		buildFlavor();																				//19928
		tableCopy = true;																			//19928
		return;
	}																								//19928

	/**
     * setOPWGID
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setOPWGID(int _i) {
		OPWGID = _i;
	}

	/**
     * getOPWGID
     * @return
     * @author Anthony C. Liberto
     */
    public int getOPWGID() {
		return OPWGID;
	}

	/**
     * isTableCopy
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isTableCopy() {
		return tableCopy;
	}

	/**
     * setColumns
     * @param _i
     * @author Anthony C. Liberto
     */
    public void setColumns(int _i) {																//19928
		columns = _i;																				//19928
	}																								//19928

	/**
     * getColumns
     * @return
     * @author Anthony C. Liberto
     */
    public int getColumns() {																		//19928
		return columns;																				//19928
	}																								//19928

	/**
     * setArray
     * @param _val
     * @author Anthony C. Liberto
     */
    public void setArray(String[] _val) {															//19928
		value = _val;					 															//19928
	}									 															//19928

	/**
     * getArray
     * @return
     * @author Anthony C. Liberto
     */
    public String[] getArray() {		 															//19928
		return value;					 															//19928
	}									 															//19928


	/**
     * @see java.awt.datatransfer.Transferable#getTransferData(java.awt.datatransfer.DataFlavor)
     * @author Anthony C. Liberto
     */
    public Object getTransferData(DataFlavor _flavor) throws UnsupportedFlavorException, IOException {
		Object obj = null;
		try {
			obj = super.getTransferData(_flavor);
		} catch (UnsupportedFlavorException _ufe) {
			_ufe.printStackTrace();
			throw _ufe;
		} catch (IOException _ioe) {
			_ioe.printStackTrace();
			throw _ioe;
		}
		return obj;
	}

	/**
     * setObject
     * @param _o
     * @author Anthony C. Liberto
     */
    public void setObject(Object _o) {
		o = _o;
	}

	/**
     * getObject
     * @return
     * @author Anthony C. Liberto
     */
    public Object getObject() {
		return o;
	}

	/**
     * setType
     * @param _s
     * @author Anthony C. Liberto
     */
    public void setType(String _s) {
		type = new String(_s);
	}

	/**
     * getType
     * @return
     * @author Anthony C. Liberto
     */
    public String getType() {
		return type;
	}

/*
causes an exception
	public void buildFlavor() {
		flavor = new DataFlavor[1];
		try {
			flavor[0] = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType);
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			flavor = null;
		}
	}

	public DataFlavor[] getTransferDataFlavors() {
		DataFlavor[] orig = super.getTransferDataFlavors();
		if (flavor == null) return orig;
		int ii = flavor.length + 2;
		DataFlavor[] out = new DataFlavor[ii];
		out[0] = orig[0];
		out[1] = orig[1];
		for (int i=2;i<ii;++i) {
			out[i] = flavor[i-2];
		}
		return out;
	}

	public boolean isDataFlavorSupported(DataFlavor _flavor) {
		if (super.isDataFlavorSupported(_flavor)) return true;
		return isDataFlavorSupportedInternally(_flavor);
	}

	public boolean isDataFlavorSupportedInternally(DataFlavor _flavor) {
		if (flavor == null) return false;
		for (int i=0;i<flavor.length; ++i) {
			if (flavor[i].equals(_flavor))
				return true;
		}
		return false;
	}

	public Object getTransferData(DataFlavor _flavor) throws UnsupportedFlavorException, IOException {
		if (isDataFlavorSupportedInternally(_flavor)) {
			return getMyTransferData(_flavor);
		} else {
			Object o = null;
			try {
				o = super.getTransferData(_flavor);
			} catch (UnsupportedFlavorException ufe) {
				ufe.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			return o;
		}
	}

causes exception
	public Object getMyTransferData(DataFlavor _flavor) throws UnsupportedFlavorException, IOException {
		if (_flavor.equals(flavor[0])) {
			return getObject();
		} else {
			throw new UnsupportedFlavorException(_flavor);
		}
	}
*/
	/**
     * @see java.awt.datatransfer.ClipboardOwner#lostOwnership(java.awt.datatransfer.Clipboard, java.awt.datatransfer.Transferable)
     * @author Anthony C. Liberto
     */
    public void lostOwnership(Clipboard _clip, Transferable _trans) {}
}
