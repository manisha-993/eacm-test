/*
 * Created on May 13, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package COM.ibm.eannounce.catalog;

/**
 *
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class WWProdCompatId extends CatId {

	private String ProdEntityTypeFrom = null;
	private int ProdEntityIDFrom = -1;
	private String ProdEntityTypeTo = null;
	private int ProdEntityIDTo = -1;
	private String OS_FC = null; 


   /**
     * WWProdCompatId
     * @param _str
     */
    public WWProdCompatId( String _strProdEntityTypeFrom, int _iProdEntityIDFrom, String _strProdEntityTypeTo, int _iProdEntityIDTo, String _OS_FC, GeneralAreaMapItem _gami, WWProdCompatCollectionId _collID) {
        super(_strProdEntityTypeFrom + ":" + _iProdEntityIDFrom + ":" + _strProdEntityTypeTo + ":" + _iProdEntityIDTo + ":" + _OS_FC, _gami);
        setProdEntityTypeFrom(_strProdEntityTypeFrom);
        setProdEntityTypeTo (_strProdEntityTypeTo );
        setProdEntityIDFrom(_iProdEntityIDFrom);
        setProdEntityIDTo(_iProdEntityIDTo);
	setOS_FC(_OS_FC);
        setWWProdCompatCollectionId(_collID);
    }


	public String getProdEntityTypeFrom() {
		return ProdEntityTypeFrom;
	}

	public String getProdEntityTypeTo () {
		return ProdEntityTypeTo ;
	}

	public String getOS_FC () {
		return OS_FC;
	}

	public int getProdEntityIDFrom() {
		return ProdEntityIDFrom;
	}

	public int getProdEntityIDTo () {
		return ProdEntityIDTo ;
	}

	public void setProdEntityTypeFrom(String string) {
		ProdEntityTypeFrom = string;
	}

	public void setProdEntityTypeTo (String string) {
		ProdEntityTypeTo  = string;
	}

	public void setOS_FC (String string) {
		OS_FC  = string;
	}


	public void setProdEntityIDFrom(int _i) {
		ProdEntityIDFrom = _i;
	}

	public void setProdEntityIDTo (int _i) {
		ProdEntityIDTo  = _i;
	}

	public final String dump(boolean _b) {
		if (_b) {
			return toString();
		} else {
			return toString();
		}
	}

	public WWProdCompatCollectionId getWWProdCompatCollectionId() {
		return (WWProdCompatCollectionId)getCollectionId();
	}

	public void setWWProdCompatCollectionId(WWProdCompatCollectionId id) {
		setCollectionId(id);
	}
}

/*
 * $Log: WWProdCompatId.java,v $
 * Revision 1.3  2011/05/05 11:21:34  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:37  jingb
 * no message
 *
 * Revision 1.2  2007/01/31 02:12:15  rick
 * change for os_fc to add to primary key
 *
 * Revision 1.1.1.1  2006/03/30 17:36:32  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.3  2005/09/27 18:09:49  joan
 * fixes
 *
 * Revision 1.2  2005/09/26 17:41:21  joan
 * fix compile
 *
 * Revision 1.1  2005/09/26 16:21:49  joan
 * initial load
 *
 *
 */
