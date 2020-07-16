/*
 * Created on May 22, 2005
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
public abstract class CollectionId extends CatId {

	public static final int FULL_IMAGES = 0;
	public static final int NET_CHANGES = 1;
	public static final int PDH_SOURCE = 2;
	public static final int CAT_SOURCE = 3;

	private boolean FromPDH = false;
	private boolean FromCAT = false;
	private boolean FullImages = false;
	private boolean NetChanges = false;
	private CatalogInterval Interval = null;
	private int Source = -1;
	private int Type = -1;

  	/**
	 * @param _str
	 * @param _gami
	 */
	public CollectionId(String _str, GeneralAreaMapItem _gami, int _iSource, int _iType, CatalogInterval _int) {
		super(_str, _gami);
		setSource(_iSource);
		setType(_iType);
		setInterval(_int);
	}

	/**
	 * @param _str
	 * @param _gami
	 */
	public CollectionId(String _str, GeneralAreaMapItem _gami, int _iSource, int _iType) {
		super(_str, _gami);
		setSource(_iSource);
		setType(_iType);
		setInterval(null);
	}

	/**
	 * @param _str
	 * @param _gami
	 */
	public CollectionId(String _str, GeneralAreaMapItem _gami) {
		super(_str, _gami);
		setSource(this.CAT_SOURCE);
		setType(this.FULL_IMAGES);
		setInterval(null);
	}

	/**
	 * @param _str
	 * @param _gami
	 */
	public CollectionId(CatalogInterval _cati, GeneralAreaMapItem _gami, int _iSource, int _iType) {
		super(_cati.toString(), _gami);
		setInterval(_cati);
		setSource(_iSource);
		setType(_iType);
	}

	/**
	 * @param _str
	 * @param _gami
	 */
	public CollectionId(String _str, CatalogInterval _cati, GeneralAreaMapItem _gami, int _iSource, int _iType) {
		super(_str, _gami);
		setInterval(_cati);
		setSource(_iSource);
		setType(_iType);
	}
	/**
		 * @return
		 */
	public boolean isFromCAT() {
		return getSource() == CollectionId.CAT_SOURCE;
	}

	/**
	 * @return
	 */
	public boolean isFromPDH() {
		return getSource() == CollectionId.PDH_SOURCE;
	}

	/**
	 * @return
	 */
	public boolean isFullImages() {
		return getType() == CollectionId.FULL_IMAGES;
	}

	/**
	 * @return
	 */
	public CatalogInterval getInterval() {
		return Interval;
	}

	/**
	 * @return
	 */
	public boolean isNetChanges() {
		return getType() == CollectionId.NET_CHANGES;
	}

	/**
	 * @param interval
	 */
	public void setInterval(CatalogInterval interval) {
		Interval = interval;
	}

	public final boolean isByInterval() {
		return (this.getInterval() != null);
	}

	//  clone()
	// boolean sameObject() is on CatObject

	/**
	 * @return
	 */
	public int getSource() {
		return Source;
	}

	/**
	 * @return
	 */
	public int getType() {
		return Type;
	}

	/**
	 * @param i
	 */
	public void setSource(int i) {
		Source = i;
	}

	/**
	 * @param i
	 */
	public void setType(int i) {
		Type = i;
	}

	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.catalog.CatObject#dump(boolean)
	 */
	public String dump(boolean _breif) {
		// TODO Auto-generated method stub
		return null;
	}

	public String toString() {
		return super.toString() + ":" + (Interval != null? Interval.toString(): "");
	}
	public static void main(String[] args) {
	}

}
