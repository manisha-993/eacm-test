/*
 * Created on May 22, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package COM.ibm.eannounce.catalog;

/**
 * @author Bala
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ComponentGroupCollectionId extends CollectionId {

	private WorldWideComponentId m_WorldWideComponentId = null;

//	private boolean ByPartNumber = false;
//	private boolean ByEntityIDRange = false;
//	private String WWPartNumber = null;            //We may not need this, but keeping it anyway
//	private int StartingEntityID = 0;
//	private int EndingEntityID = 0;

	/**
	 * @param _str
	 * @param _gami
	 */
//	public ComponentGroupCollectionId(String _strWWPartNumber, GeneralAreaMapItem _gami, int _iSource, int _iType) {
//		super(_strWWPartNumber, _gami, _iSource, _iType);
//		this.setWWPartNumber(_strWWPartNumber);
//		this.setByPartNumber(true);
//	}

//	public ComponentGroupCollectionId(
//		int _iStartEntityID,
//		int _iEndEntityID,
//		GeneralAreaMapItem _gami,
//		int _iSource,
//		int _iType) {
//		super(_iStartEntityID + ":" + _iEndEntityID, _gami, _iSource, _iType);
//		this.setStartingEntityID(_iStartEntityID);
//		this.setEndingEntityID(_iEndEntityID);
//		this.setByEntityIDRange(true);
//	}

	public ComponentGroupCollectionId(CatalogInterval _cati,
		GeneralAreaMapItem _gami, int _iSource, int _iType) {
		super(_cati, _gami, _iSource, _iType);
	}

	public ComponentGroupCollectionId(WorldWideComponentId _colid, CatalogInterval _cati,
		 int _iSource, int _iType) {
		super(_cati, _colid.getGami(), _iSource, _iType);
		this.setWorldWideComponentId(_colid);
	}

	public String dump(boolean _breif) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();

		sb.append("\n:source:" + this.getSource());
		sb.append("\n:type" + this.getType());
		sb.append("\n:isfromcat" + this.isFromCAT());
		sb.append("\n:isfrompdh" + this.isFromPDH());
		sb.append("\n:isFullImages" + this.isFullImages());
		sb.append("\n:isNetChanges" + this.isNetChanges());
		sb.append("\n:isbyInterval" + this.isByInterval());

		return sb.toString();

	}

    public String toString()    {
        return this.dump(true);
    }

	public static void main(String[] args) {
	}
	/**
	 * @return
	 */
//	public boolean isByPartNumber() {
//		return ByPartNumber;
//	}

	/**
	 * @return
	 */
//	public String getWWPartNumber() {
//		return WWPartNumber;
//	}

	/**
	 * @param b
	 */
//	public void setByPartNumber(boolean b) {
//		ByPartNumber = b;
//	}

	/**
	 * @param string
	 */
//	public void setWWPartNumber(String string) {
//		WWPartNumber = string;
//	}

	/**
	 * @return
	 */
//	public boolean isByEntityIDRange() {
//		return ByEntityIDRange;
//	}

	/**
	 * @return
	 */
//	public int getEndingEntityID() {
//		return EndingEntityID;
//	}

	/**
	 * @return
	 */
//	public int getStartingEntityID() {
//		return StartingEntityID;
//	}

	/**
	 * @param b
	 */
//	public void setByEntityIDRange(boolean b) {
//		ByEntityIDRange = b;
//	}

	/**
	 * @param i
	 */
//	public void setEndingEntityID(int i) {
//		EndingEntityID = i;
//	}

	/**
	 * @param i
	 */
//	public void setStartingEntityID(int i) {
//		StartingEntityID = i;
//	}

	/**
	 * @return
	 */
	public WorldWideComponentId getWorldWideComponentId() {
		return m_WorldWideComponentId;
	}

	/**
	 * @param id
	 */
	public void setWorldWideComponentId(WorldWideComponentId id) {
		m_WorldWideComponentId = id;
	}

	public final boolean hasWorldWideComponentId() {
		return m_WorldWideComponentId != null;
	}

}

/*
$Log: ComponentGroupCollectionId.java,v $
Revision 1.2  2011/05/05 11:21:34  wendy
src from IBMCHINA

Revision 1.1.1.1  2007/06/05 02:09:15  jingb
no message

Revision 1.1.1.1  2006/03/30 17:36:28  gregg
Moving catalog module from middleware to
its own module.

Revision 1.5  2005/10/05 20:59:29  joan
fixes

Revision 1.4  2005/10/04 20:35:30  joan
fixes

Revision 1.3  2005/10/03 22:08:33  joan
work on component

Revision 1.2  2005/07/13 21:37:07  bala
fixes

Revision 1.1  2005/07/11 22:48:58  bala
check in

 */
