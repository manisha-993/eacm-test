/**
 * Copyright (c) 2005, International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * VEEdit CR0815056514
 *
 * provides functionality to parse
 * the heritage class.  This is for convenience
 */
package COM.ibm.eannounce.objects;
import java.io.Serializable;
import java.util.StringTokenizer;

public class KeyPath implements Serializable {
	private static final long serialVersionUID = 1L;
	public static String H_KEY_ONLY = "HeritageKey";
	public static String H_KEY = H_KEY_ONLY + ":";
	private String m_sEntityType = null;
	private String m_sAttCode = null;
	private String m_sRelTag = null;

	/**
	 * the Constructor
	 * @param the key
	 * @author tony
	 */
	public KeyPath() {
	}

	/**
	 * parse the passed in key
	 * @param the key
	 * @author tony
	 */
	public void parse(String _s) {
		if (_s != null) {
//			System.out.println("parse(" + _s + ")");
			parseAttribute(_s,":");
		} else {
			m_sEntityType = null;
			m_sAttCode = null;
			m_sRelTag = null;
		}
	}

	private void parseAttribute(String _s, String _delimit) {
//		System.out.println("parseAttribute(" + _s + ")");
		boolean out = false;
		StringTokenizer st = new StringTokenizer(_s,_delimit);
		if (st.hasMoreTokens()) {
			m_sEntityType = st.nextToken();
//			System.out.println("    eType: " + m_sEntityType);
		}
		if (st.hasMoreTokens()) {
			out = true;
			m_sAttCode = st.nextToken();
//			System.out.println("    aCode: " + m_sAttCode);
		}
		if (st.hasMoreTokens()) {
			m_sRelTag = st.nextToken();
//			System.out.println("    rTag : " + m_sRelTag);
		} else if (out) {
//			System.out.println("parsing aCode: " + m_sAttCode);
			StringTokenizer st2 = new StringTokenizer(m_sAttCode,":");
			if (st2.hasMoreTokens()) {
				m_sAttCode = st2.nextToken();
			}
			if (st2.hasMoreTokens()) {
				m_sRelTag = st2.nextToken();
			}
		}
	}
	/**
	 * get the entitytype for the heritage key
	 * @return entitytype
	 * @author tony
	 */
	public String getEntityType() {
		return m_sEntityType;
	}

	/**
	 * get the AttCode for the heritage key
	 * @return attCode
	 * @author tony
	 */
	public String getAttributeCode() {
		return m_sAttCode;
	}
	/**
	 * get the Relator Tag for the heritage key
	 * @return attCode
	 * @author tony
	 */
	public String getRelTag() {
		return m_sRelTag;
	}

	/**
	 * informs if key is a heritage key
	 * @param key
	 * @return isValid
	 * @author tony
	 */
	public static boolean isHeritageKey(String _s) {
		return (_s.indexOf(H_KEY)>=0);
	}
}
/**
 * Copyright (c) 2005, International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * $Log: KeyPath.java,v $
 * Revision 1.6  2009/04/15 20:15:39  wendy
 * Maintain a list for lookup to prevent returning the wrong EANObject
 *
 * Revision 1.5  2005/11/04 14:52:11  tony
 * VEEdit_Iteration2
 * updated VEEdit logic to meet new requirements.
 *
 * Revision 1.4  2005/09/28 19:11:14  tony
 * TIR USRO-R-TMAY-6GLQLW
 *
 * Revision 1.3  2005/09/08 17:57:26  tony
 * Updated VEEdit Logic to Improve functionality
 *
 * Revision 1.2  2005/09/02 15:32:59  tony
 * VEEdit functionality addition for CR 0815056514
 *
 * Revision 1.1  2005/08/25 16:46:48  tony
 * initLoad
 *
 */
