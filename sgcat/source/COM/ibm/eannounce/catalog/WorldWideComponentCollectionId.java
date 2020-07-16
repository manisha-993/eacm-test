/*

 */
package COM.ibm.eannounce.catalog;

import java.util.Iterator;

/**
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class WorldWideComponentCollectionId extends CollectionId {
	private WorldWideProductId m_wpid = null;
	private ProductId m_pid = null;
	private ComponentGroupId m_cgid = null;

	public WorldWideComponentCollectionId(CatalogInterval _cati, GeneralAreaMapItem _gami, int _iSource, int _iType) {
		super(_cati, _gami, _iSource, _iType);
	}

	/**
	 * @param _str
	 * @param _gami
	 * @param int
	 */
	public WorldWideComponentCollectionId(ComponentGroupId _cgid, int _iSource, int _iType, CatalogInterval _cati) {
		super(_cgid.toString(), _cgid.getGami(), _iSource, _iType);
		this.setInterval(_cati);
		m_cgid = _cgid;
	}

	/**
	 * @param _str
	 * @param _gami
	 * @param int
	 */
	public WorldWideComponentCollectionId(WorldWideProductId _pid, int _iSource, int _iType, CatalogInterval _cati) {
		super(_pid.toString(), _pid.getGami(), _iSource, _iType);
		this.setInterval(_cati);
		m_wpid = _pid;
	}

	public WorldWideComponentCollectionId(ProductId _pid, int _iSource, int _iType, CatalogInterval _cati) {
		super(_pid.toString(), _pid.getGami(), _iSource, _iType);
		this.setInterval(_cati);
		m_pid = _pid;
	}

	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.catalog.CatObject#dump(boolean)
	 */
	public String dump(boolean _breif) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append(toString());
		sb.append("\n:source:" + this.getSource());
		sb.append("\n:type" + this.getType());
		sb.append("\n:isfromcat" + this.isFromCAT());
		sb.append("\n:isfrompdh" + this.isFromPDH());
		sb.append("\n:isFullImages" + this.isFullImages());
		sb.append("\n:isNetChanges" + this.isNetChanges());
		sb.append("\n:isbyInterval" + this.isByInterval());

		return sb.toString();

	}

	public static void main(String[] args) {
	}

	public final WorldWideProductId getWorldWideProductId() {
		return m_wpid;
	}

	public final boolean hasWorldWideProductId() {
		return m_wpid != null;
	}

	public final ProductId getProductId() {
		return m_pid;
	}

	public final boolean hasProductId() {
		return m_pid != null;
	}

	public final ComponentGroupId getComponentGroupId() {
		return m_cgid;
	}

	public final boolean hasComponentGroupId() {
		return m_cgid != null;
	}
}
/*
$Log&
 */
