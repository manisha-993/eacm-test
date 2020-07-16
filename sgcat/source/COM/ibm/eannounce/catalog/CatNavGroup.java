/*
 * Created on May 31, 2005
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 */
package COM.ibm.eannounce.catalog;


import java.util.Vector;
import java.sql.*;
import COM.ibm.opicmpdh.middleware.*;

/**
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CatNavGroup {

    private Catalog m_cat = null;
    private String m_strObjKey = null;
    private Vector m_vct = null;

/**
 * Build ourselves an object from the database.
 */
    public CatNavGroup(Catalog _cat, String _strObjKey) {
		m_cat = _cat;
		m_strObjKey = _strObjKey;
		m_vct = new Vector();
		ResultSet rs = null;
		Database db = _cat.getCatalogDatabase();
		try {

		    rs = db.callGBL4019(new ReturnStatus(-1),m_cat.getEnterprise(),m_strObjKey);

	        ReturnDataResultSet rdrs = new ReturnDataResultSet(rs);

            D.ebug(D.EBUG_SPEW, "GBL4019 row count:" + rdrs.getRowCount());

	        for(int i = 0; i < rdrs.getRowCount(); i++) {
                String strColumnKey = rdrs.getColumn(i,0);
			    D.ebug(D.EBUG_SPEW, "GBL4019 Answers[" + i + "]:" + strColumnKey);
			    CatNavItem cni = new CatNavItem(this,strColumnKey);
			    putItem(cni);
			}

        } catch (SQLException _ex) {
		    	_ex.printStackTrace();
		} catch (MiddlewareException e) {
		   	// TODO Auto-generated catch block
		   	e.printStackTrace();
		} finally {
	        try {
		    	rs.close();
			    rs = null;
			    db.commit();
			    db.freeStatement();
			    db.isPending();
		    } catch (SQLException _ex) {
			    _ex.printStackTrace();
		    }
		}
	}

	public String getObjectKey() {
		return m_strObjKey;
	}

	public Catalog getCatalog() {
		return m_cat;
	}

	public int getItemCount() {
		return m_vct.size();
	}

	public CatNavItem getItem(int _i) {
		return (CatNavItem)m_vct.elementAt(_i);
	}

	private void putItem(CatNavItem _cni) {
		m_vct.addElement(_cni);
	}

    public final CatNavItem getItem(String _strColKey) {
		// this is going to be such a short list, that we don't nede to use a hastable ..
	    for(int i = 0; i < getItemCount(); i++) {
		    if(getItem(i).getColumnKey().equals(_strColKey)) {
				return getItem(i);
			}
		}
		return null;
	}

	public boolean containsColumnKey(String _strColKey) {
        return (getItem(_strColKey) != null);
	}

	public boolean containsColumnKey_fc(String _strColKey) {
		if(_strColKey.length() >= 3) {
			_strColKey = _strColKey.substring(0,_strColKey.length()-3);
		}
		//D.ebug(D.EBUG_SPEW,"containsColumnKey_fc a for:" + _strColKey + " is:" + (getItem(_strColKey) != null));
        return (getItem(_strColKey) != null);
	}

	public String toString() {
		return "CatNavGroup count is:" + getItemCount();
	}

}
