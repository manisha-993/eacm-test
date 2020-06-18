//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: PackageID.java,v $
// Revision 1.27  2005/03/11 22:42:54  dave
// removing some auto genned stuff
//
// Revision 1.26  2005/02/08 21:51:46  dave
// Jtest clean up
//
// Revision 1.25  2005/01/27 04:02:36  dave
// removing automated readObject from Jtest
//
// Revision 1.24  2005/01/27 00:34:55  dave
// Jtest on Translation area (formatting and clean up)
//
// Revision 1.23  2004/11/04 18:57:36  dave
// OK Billingcode is now an attribute on the translation entity
// itself and is now part of the PackageID constructor
//
// Revision 1.22  2003/09/08 06:51:21  dave
// changes per ETS request
//
// Revision 1.21  2003/09/04 16:53:15  dave
// changing the toString
//
// Revision 1.20  2003/09/03 17:03:54  dave
// minor changes based upon Hans comments
//
// Revision 1.19  2003/08/21 21:11:06  dave
// adding 11,12,13 language translations
//
// Revision 1.18  2003/08/13 22:31:17  dave
// making changes for set status in TranslationII
//
// Revision 1.17  2003/07/15 20:24:40  bala
// change constructor from protected to public
//
// Revision 1.16  2003/07/08 21:28:42  dave
// minor syntax
//
// Revision 1.15  2003/07/08 21:00:43  dave
// driving to the net
//
// Revision 1.14  2003/07/08 20:32:00  dave
// syntax fixes
//
// Revision 1.13  2003/07/08 20:14:00  dave
// translation package changes II
//
// Revision 1.12  2003/07/07 23:01:19  dave
// more res
//
// Revision 1.11  2003/07/07 22:32:42  dave
// kinstac
//
// Revision 1.10  2003/07/07 21:23:46  dave
// Cleanup
//
// Revision 1.9  2003/07/07 21:10:27  dave
// fin techs errors
//
// Revision 1.8  2003/07/07 20:39:06  dave
// translation for 1.2 retrofit I
//
// Revision 1.7  2001/08/22 16:53:54  roger
// Removed author RM
//
// Revision 1.6  2001/03/26 16:35:46  roger
// Misc formatting clean up
//
// Revision 1.5  2001/03/21 00:01:17  roger
// Implement java class file branding in getVersion method
//
// Revision 1.4  2001/03/16 15:52:27  roger
// Added Log keyword
//

package COM.ibm.opicmpdh.translation;

import java.io.Serializable;
import java.util.Hashtable;

/**
* A class which defines the PackageID for Translation
* @version @date
*/
public final class PackageID implements Serializable {

	static final long serialVersionUID = 1L;

	/**
	 * c_hshLanguageMap
	 */
	public static Hashtable c_hshLanguageMap = new Hashtable();

	static {
		c_hshLanguageMap.put("1", "English");
		c_hshLanguageMap.put("2", "German");
		c_hshLanguageMap.put("3", "Italian");
		c_hshLanguageMap.put("4", "Japaneese");
		c_hshLanguageMap.put("5", "French");
		c_hshLanguageMap.put("6", "Spanish");
		c_hshLanguageMap.put("7", "UK English");
		c_hshLanguageMap.put("8", "Korean");
		c_hshLanguageMap.put("9", "Chinese");
		c_hshLanguageMap.put("10", "French Canadian");
		c_hshLanguageMap.put("11", "Chinese Simplified");
		c_hshLanguageMap.put("12", "Spanish Latin American");
		c_hshLanguageMap.put("13", "Portuguese (Braziliian)");

	}

	/**
	 * m_strEntityType
	 */
	protected String m_strEntityType = null;
	/**
	 * m_strTransDate
	 */
	protected String m_strTransDate = null;
	// The date it was placed on the Waiting Queue
	/**
	 * m_strGroupQueue
	 */
	protected String m_strGroupQueue = null;
	/**
	 * m_iEntityID
	 */
	protected int m_iEntityID = -1;
	/**
	 * m_iNLSID
	 */
	protected int m_iNLSID = 1;
	/**
	 * m_strStatusAttCode
	 */
	protected String m_strStatusAttCode = null;
	/**
	 * Billing Code (Defunct?)
	 */
	protected String m_strBillingCode = null;

	/**
	 * mainline
	 * @param _args
	 * @author Dave
	 */
	public static void main(String _args[]) {
	}

	/**
	 * make a new package ID
	 * @param _strEntityType
	 * @param _iEntityID
	 * @param _iNLSID
	 * @param _strQueue
	 * @param _strTransDate
	 * @param _strBillingCode
	 * @author Dave
	 */
	public PackageID(String _strEntityType, int _iEntityID, int _iNLSID, String _strQueue, String _strTransDate, String _strBillingCode) {
		m_strEntityType = _strEntityType;
		m_iEntityID = _iEntityID;
		m_iNLSID = _iNLSID;
		m_strTransDate = _strTransDate;
		m_strGroupQueue = _strQueue;
		m_strBillingCode = _strBillingCode;
		m_strStatusAttCode = (isMetaType() ? "META" : "") + "XLSTATUS" + _iNLSID;
	}

	/**
	 * isMetaType?
	 * @return
	 * @author Dave
	 */
	public final boolean isMetaType() {
		return m_strEntityType.equals(Translation.ENTITY_TYPE_FOR_META);
	}

	/**
	 * isDataType?
	 * @return
	 * @author Dave
	 */
	public final boolean isDataType() {
		return m_strEntityType.equals(Translation.ENTITY_TYPE_FOR_DATA);
	}

	/**
	 * getNLSID
	 * @return
	 * @author Dave
	 */
	public final int getNLSID() {
		return m_iNLSID;
	}

	/**
	 * getEntityID
	 *
	 * @return
	 * @author Dave
	 */
	public final int getEntityID() {
		return m_iEntityID;
	}

	/**
	 * getEntityType
	 *
	 * @return
	 * @author Dave
	 */
	public final String getEntityType() {
		return m_strEntityType;
	}

	/**
	 * getTransDate
	 *
	 * @return
	 * @author Dave
	 */
	public final String getTransDate() {
		return m_strTransDate;
	}

	/**
	 * getLanguage
	 *
	 * @return
	 * @author Dave
	 */
	public final String getLanguage() {
		return (String) c_hshLanguageMap.get(m_iNLSID + "");
	}

	/**
	 * getGroupQueue
	 *
	 * @return
	 * @author Dave
	 */
	public final String getGroupQueue() {
		return m_strGroupQueue;
	}

	/**
	 * getMetaStatusCode
	 *
	 * @return
	 * @author Dave
	 */
	public final String getMetaStatusCode() {
		return m_strStatusAttCode;
	}

	/**
	 * getBillingCode
	 * @return
	 * @author Dave
	 */
	public final String getBillingCode() {
		return m_strBillingCode;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @author Dave
	 */
	public boolean equals(Object _obj) {
		if ((_obj != null) && (_obj instanceof PackageID)) {
			return (m_strEntityType.equals(((PackageID) _obj).m_strEntityType))
				&& (m_iEntityID == ((PackageID) _obj).m_iEntityID)
				&& (m_iNLSID == ((PackageID) _obj).m_iNLSID)
				&& m_strTransDate.equals(((PackageID) _obj).getTransDate());
		}
		return false;
	}

	/**
	 * Return the <code>PackageID</code> as a <code>String</code>
	 *
	 * @return String
	 */
	public String toString() {
		return m_strEntityType + m_strBillingCode + "-" + m_iNLSID + "_" + m_iEntityID;
	}

	/**
	* Return the date/time this class was generated
	* @return the date/time this class was generated
	*/
	public final static String getVersion() {
		return "$Id: PackageID.java,v 1.27 2005/03/11 22:42:54 dave Exp $";
	}
}
