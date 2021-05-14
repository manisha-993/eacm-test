/*
 * Created on May 18, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package COM.ibm.eannounce.catalog;

/**
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class WorldWideAttributeId extends CatId {

	private String WWEntityType = null;
	private String AttEntityType = null;
	private int WWEntityID = 0;
	private int AttEntityID = 0;
	
	public WorldWideAttributeId(WorldWideProductId _wwpid, String _strAttEntityType, int _iAttEntityID, GeneralAreaMapItem _gami)  {
		super(  _iAttEntityID + ":"  + _strAttEntityType + ":" + _wwpid.getEntityID() + ":" + _wwpid.getEntityType(), _gami);
		setWWEntityType(_wwpid.getEntityType());
		setAttEntityType(_strAttEntityType);
		setWWEntityID(_wwpid.getEntityID());
		setAttEntityID(_iAttEntityID);
	}

	public WorldWideAttributeId(WorldWideProductId _wwpid,  String _strAttEntityType, int _iAttEntityID, WorldWideAttributeCollectionId _wwacid)  {
		super(  _iAttEntityID + ":"  + _strAttEntityType + ":" + _wwpid.getEntityID() + ":" + _wwpid.getEntityType(), _wwacid.getGami());
		setWWEntityType(_wwpid.getEntityType());
		setAttEntityType(_strAttEntityType);
		setWWEntityID(_wwpid.getEntityID());
		setAttEntityID(_iAttEntityID);
		this.setCollectionId(_wwacid);
	}

	public static void main(String[] args) {
	}
	
	public final String dump(boolean _b) {
		if (_b) {
			return toString();
		} else {
			return toString();
		}
	}
	/**
	 * @return
	 */
	public int getAttEntityID() {
		return AttEntityID;
	}

	/**
	 * @return
	 */
	public String getAttEntityType() {
		return AttEntityType;
	}

	/**
	 * @return
	 */
	public int getWWEntityID() {
		return WWEntityID;
	}

	/**
	 * @return
	 */
	public String getWWEntityType() {
		return WWEntityType;
	}

	/**
	 * @param i
	 */
	public void setAttEntityID(int i) {
		AttEntityID = i;
	}

	/**
	 * @param string
	 */
	public void setAttEntityType(String string) {
		AttEntityType = string;
	}

	/**
	 * @param i
	 */
	public void setWWEntityID(int i) {
		WWEntityID = i;
	}

	/**
	 * @param string
	 */
	public void setWWEntityType(String string) {
		WWEntityType = string;
	}
}
