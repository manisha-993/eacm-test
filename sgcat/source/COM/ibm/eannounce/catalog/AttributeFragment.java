/*
 * Created on May 18, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package COM.ibm.eannounce.catalog;

import java.sql.SQLException;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.D;

/**
 * This holds a small piece of the attribute
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AttributeFragment extends CatItem {

    private String m_strFlagCode = null;
    private String m_strAttributeType = null;
    private String m_strAttributeValue = null;
    private String m_strUnitOfMeasure = null;
    private String m_strValFrom = null;

    /**
     * AttributeFragment
     *
     * @param _cid
     */
    public AttributeFragment(AttributeFragmentId _cid) {
        super(_cid);
        // TODO Auto-generated constructor stub
    }

    /**
     *  (non-Javadoc) - This guy should never implement this class fully.
     * it is too inefficient to go get a single text string from the database
     * we must use this with caution.
     *
     */
    public void get(Catalog _cat) {
        // TODO Auto-generated method stub

    }

    /**
     *  (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        // Good Enough For Now..
        return super.getId()
        + ":"
        + getAttributeType()
        + ":"
        + getFlagCode()
        + ":"
        + getAttributeValue()
        + ":"
        + getValFrom()
        + ":"
        + isActive();
    }

    /**
     *  (non-Javadoc)
     *
     * @see COM.ibm.eannounce.catalog.CatObject#dump(boolean)
     */
    public String dump(boolean _breif) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     *  (non-Javadoc)
     *
     */
    public void getReferences(Catalog _cat, int _icase) {
        // TODO Auto-generated method stub

    }

    /**
     * main
     *
     * @param args
     */
    public static void main(String[] args) {
    }

    /**
     * getAttributeType
     *
     * @return
     */
    public String getAttributeType() {
        return m_strAttributeType;
    }

    /**
     * getAttributeValue
     *
     * @return
     */
    public String getAttributeValue() {
        return m_strAttributeValue;
    }

    /**
     * getFlagCode
     *
     * @return
     */
    public String getFlagCode() {
        return m_strFlagCode;
    }

    /**
     * setAttributeType
     *
     * @param string
     */
    public void setAttributeType(String string) {
        if (string != null && m_strAttributeValue != null) {
            setChanged(!m_strAttributeType.equals(string.trim()));
        } else {
            setChanged(string != m_strAttributeType);
        }
        m_strAttributeType = string;
    }

    /**
     * setAttributeValue
     *
     * @param string
     */
    public void setAttributeValue(String string) {
        if (string != null && m_strAttributeValue != null) {
            setChanged(!m_strAttributeValue.equals(string.trim()));
        } else {
            setChanged(string != m_strAttributeValue);
        }
        m_strAttributeValue = string;
    }

    /**
     * setFlagCode
     *
     * @param string
     */
    public void setFlagCode(String string) {
        if (string != null && m_strAttributeValue != null) {
            setChanged(!m_strFlagCode.equals(string.trim()));
        } else {
            setChanged(string != m_strFlagCode);
        }
        m_strFlagCode = string;
    }

    /**
     * getValFrom
     *
     * @return
     */
    public String getValFrom() {
        return m_strValFrom;
    }

    /**
     * setValFrom
     *
     * @param string
     */
    public void setValFrom(String string) {

        if (string != null && m_strAttributeValue != null) {
          if (m_strValFrom==null) {
            setChanged(true);
          } else {
            setChanged(!m_strValFrom.equals(string.trim()));

          }
        } else {
            setChanged(string != m_strAttributeValue);
        }
        m_strValFrom = string;
    }

    /**
     *  (non-Javadoc)
     *
     * @see COM.ibm.eannounce.catalog.Databaseable#put(COM.ibm.eannounce.catalog.Catalog)
     */
    public void put(Catalog _cat, boolean _bcommit) {
		if (Catalog.isDryRun()) {
			 return;
		}

		Database db = _cat.getCatalogDatabase();

		ReturnStatus rets = new ReturnStatus(-1);

		AttributeFragmentId afid = (AttributeFragmentId)getId();
		WorldWideAttributeId wwaid = afid.getWorldWideAttributeId();
		GeneralAreaMapItem gami = afid.getGami();
		String strEnterprise = gami.getEnterprise();
		String strCountryCode = gami.getCountry();
		String strLanguageCode = gami.getLanguage();
		int iNLSID = gami.getNLSID();
		String strCountryList = gami.getCountryList();
		String strWWEntityType = wwaid.getWWEntityType();
		int iWWEntityID = wwaid.getWWEntityID();
		String strAttEntityType = wwaid.getAttEntityType();
		int iAttEntityID = wwaid.getAttEntityID();
		String strAttributeCode = afid.getAttributeCode();

		try {
			db.callGBL8975(
				rets,
				strEnterprise,
				strCountryCode,
				strLanguageCode,
				iNLSID,
				strCountryList,
				strWWEntityType,
				iWWEntityID,
				strAttEntityType,
				iAttEntityID,
				(this.getAttributeCode() == null ? "": this.getAttributeCode()),
				(this.getAttributeType() == null ? "" : this.getAttributeType()),
				(this.getFlagCode() == null ? "" : this.getFlagCode()),
				(this.getAttributeValue() == null ? "" : this.getAttributeValue()),
				(this.getUnitOfMeasure() == null ? "" : this.getUnitOfMeasure()),
				(this.isActive() ? 1 : 0));

			if (_bcommit) {
				db.commit();
			}
			db.freeStatement();
			db.isPending();

		} catch (MiddlewareException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			db.freeStatement();
			db.isPending();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			db.freeStatement();
			db.isPending();
		}
    }

    /**
     *  (non-Javadoc)
     *
     * @see COM.ibm.eannounce.catalog.CatItem#merge(COM.ibm.eannounce.catalog.CatItem)
     */
    public final void merge(CatItem _cat) {
        if (_cat instanceof AttributeFragment) {
            // Lets do
        }
    }

    public void setAttribute(String _strTag, Object _oAtt) {
		return;
	}


    public Object getAttribute(String _strTag) {
		return null;
	}

	public String[] getAttributeKeys() {
		return null;
	}
    /**
     * getUnitOfMeasure
     * @return
     */
    public String getUnitOfMeasure() {
        return m_strUnitOfMeasure;
    }

    /**
     * setUnitOfMeasure
     * @param string
     */
    public void setUnitOfMeasure(String string) {
        m_strUnitOfMeasure = string;
    }

    public final String getAttributeCode() {
    	return ((AttributeFragmentId)this.getId()).getAttributeCode();
    }

}

/**
 * $Log: AttributeFragment.java,v $
 * Revision 1.2  2011/05/05 11:21:32  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:02  jingb
 * no message
 *
 * Revision 1.1.1.1  2006/03/30 17:36:27  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.17  2005/11/28 15:41:32  bala
 * change setValfrom check for null m_strValfrom
 *
 * Revision 1.16  2005/08/08 18:54:25  tony
 * get attribute keys
 * 20050808
 *
 * Revision 1.15  2005/06/23 04:08:48  dave
 * bitten by the dryrun bug
 *
 * Revision 1.14  2005/06/23 02:06:17  dave
 * syyyntechs air or
 *
 * Revision 1.13  2005/06/23 01:56:18  dave
 * lets hook up the update of the wwattributes
 *
 * Revision 1.12  2005/06/13 04:02:05  dave
 * new dryrun feature to keep things from being updated
 *
 * Revision 1.11  2005/06/07 04:34:50  dave
 * working on commit control
 *
 * Revision 1.10  2005/06/02 04:49:55  dave
 * more clean up
 *
 * Revision 1.9  2005/06/01 20:36:41  gregg
 * get/setAttribute
 *
 * Revision 1.8  2005/06/01 03:32:14  dave
 * JTest clean up
 *
 * Revision 1.7  2005/05/27 00:55:17  dave
 * adding the merge method.
 *
 * Revision 1.6  2005/05/26 07:38:06  dave
 * comments and formating
 *
 * Revision 1.5  2005/05/26 07:20:10  dave
 * new SP and introduction of the Catalog Object
 *
 * Revision 1.4  2005/05/22 23:04:36  dave
 * Added CollectionId
 * addind Catalog Interval
 * Placed enterprise in the Gami
 *
 */
