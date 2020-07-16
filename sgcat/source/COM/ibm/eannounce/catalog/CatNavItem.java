/*
 * Created on May 31, 2005
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 */
package COM.ibm.eannounce.catalog;


import java.util.Vector;
import COM.ibm.opicmpdh.middleware.D;

/**
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CatNavItem {

    private CatNavGroup m_cng = null;
    private String m_strColumnKey = null;
    private Vector m_vctVals = null;
    private Vector m_vctVals_fc = null;

    public CatNavItem(CatNavGroup _cng, String _strColumnKey) {
		m_cng = _cng;
		m_strColumnKey = _strColumnKey;
		m_vctVals = new Vector();
		m_vctVals_fc = new Vector();
	}

/**
 * Ok, this is based on the CATDB column -- NOT the PDH ATTRIBUTECODE!!!
 */
	public final String getColumnKey() {
		return m_strColumnKey;
	}

	public final String getObjectKey() {
		return m_cng.getObjectKey();
	}

	public final String getEnterprise() {
		return m_cng.getCatalog().getEnterprise();
	}

    public final int getValueCount() {
		return m_vctVals.size();
	}

	public final String getValue(int _i) {
		return (String)m_vctVals.elementAt(_i);
	}

	protected final void putValue(String _s) {
		D.ebug(D.EBUG_SPEW,"CatNavItem.putValue() for " + toString() + " = " + _s);
		m_vctVals.addElement(_s);
	}

	public final String getValue_fc(int _i) {
		return (String)m_vctVals_fc.elementAt(_i);
	}

	protected final void putValue_fc(String _s) {
		D.ebug(D.EBUG_SPEW,"CatNavItem.putValue_fc() for " + toString() + " = " + _s);
		m_vctVals_fc.addElement(_s);
	}

    public String toString() {
		return getObjectKey() + ":" + getColumnKey();
	}

}
