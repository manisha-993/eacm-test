/*
 * Created on May 26, 2005
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
public class SyncMapItem extends CatObject {

	private String GeneralAreaName = null;
	private String ChildGeneralAreaName = null;
	private String RootType = null;
	private int RootID = 0;
	private String RootTran = null;
	private String ChildType = null;
	private int ChildID = 0;
	private String ChildTran = null;
	private int ChildLevel = 0;
	private String ChildClass = null;
	private String ChildPath = null;
	private String Entity2Type = null;
	private int Entity2ID = 0;
	private String Entity1Type = null;
	private int Entity1ID = 0;

	/**
	 *  Basic Constructor
	 *
	 */
	public SyncMapItem() {
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ga:" + this.getGeneralAreaName());
		sb.append(":rty:" + this.getRootType());
		sb.append(":rid:" + this.getRootID());
		sb.append(":rtr:" + this.getRootTran());
		sb.append(":ca:" + this.getChildGeneralAreaName());
		sb.append(":cty:" + this.getChildType());
		sb.append(":cid:" + this.getChildID());
		sb.append(":ctr:" + this.getChildTran());
		sb.append(":clv:" + this.getChildLevel());
		sb.append(":ccl:" + this.getChildClass());
		sb.append(":cpt:" + this.getChildPath());
		sb.append(":e2t:" + this.getEntity2Type());
		sb.append(":e2i:" + this.getEntity2ID());
		sb.append(":e1t:" + this.getEntity1Type());
		sb.append(":e1i:" + this.getEntity1ID());
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.catalog.CatObject#dump(boolean)
	 */
	public String dump(boolean _breif) {
		return toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		SyncMapItem smi = (SyncMapItem)obj;
		return(this.getGeneralAreaName().equals(smi.getGeneralAreaName()) &&
			   this.getRootType().equals(smi.getRootType()) &&
			   this.getRootID() == smi.getRootID() &&
			   this.getRootTran().equals(smi.getRootTran()) &&
			   this.getChildGeneralAreaName().equals(smi.getChildGeneralAreaName()) &&
			   this.getChildType().equals(smi.getChildType()) &&
			   this.getChildID() == smi.getChildID() &&
			   this.getChildTran().equals(smi.getChildTran()) &&
			   this.getChildLevel() == smi.getChildLevel() &&
			   this.getChildClass().equals(smi.getChildClass()) &&
			   this.getChildPath().equals(smi.getChildPath()) &&
			   this.getEntity2Type().equals(smi.getEntity2Type()) &&
			   this.getEntity2ID() == smi.getEntity2ID() &&
			   this.getEntity1Type().equals(smi.getEntity1Type()) &&
			   this.getEntity1ID() == smi.getEntity1ID());
	}

	public static void main(String[] args) {
	}
	/**
	 * @return
	 */
	public String getChildClass() {
		return ChildClass;
	}

	/**
	 * @return
	 */
	public int getChildID() {
		return ChildID;
	}

	/**
	 * @return
	 */
	public int getChildLevel() {
		return ChildLevel;
	}

	/**
	 * @return
	 */
	public String getChildPath() {
		return ChildPath;
	}

	/**
	 * @return
	 */
	public String getChildTran() {
		return ChildTran;
	}

	/**
	 * @return
	 */
	public String getChildType() {
		return ChildType;
	}

	/**
	 * @return
	 */
	public int getEntity1ID() {
		return Entity1ID;
	}

	/**
	 * @return
	 */
	public String getEntity1Type() {
		return Entity1Type;
	}

	/**
	 * @return
	 */
	public int getEntity2ID() {
		return Entity2ID;
	}

	/**
	 * @return
	 */
	public String getEntity2Type() {
		return Entity2Type;
	}

	/**
	 * @return
	 */
	public String getGeneralAreaName() {
		return GeneralAreaName;
	}

	/**
	 * @return
	 */
	public int getRootID() {
		return RootID;
	}

	/**
	 * @return
	 */
	public String getRootTran() {
		return RootTran;
	}

	/**
	 * @return
	 */
	public String getRootType() {
		return RootType;
	}

	/**
	 * @param string
	 */
	public void setChildClass(String string) {
		ChildClass = string;
	}

	/**
	 * @param i
	 */
	public void setChildID(int i) {
		ChildID = i;
	}

	/**
	 * @param i
	 */
	public void setChildLevel(int i) {
		ChildLevel = i;
	}

	/**
	 * @param string
	 */
	public void setChildPath(String string) {
		ChildPath = string;
	}

	/**
	 * @param string
	 */
	public void setChildTran(String string) {
		ChildTran = string;
	}

	/**
	 * @param string
	 */
	public void setChildType(String string) {
		ChildType = string;
	}

	/**
	 * @param i
	 */
	public void setEntity1ID(int i) {
		Entity1ID = i;
	}

	/**
	 * @param string
	 */
	public void setEntity1Type(String string) {
		Entity1Type = string;
	}

	/**
	 * @param i
	 */
	public void setEntity2ID(int i) {
		Entity2ID = i;
	}

	/**
	 * @param string
	 */
	public void setEntity2Type(String string) {
		Entity2Type = string;
	}

	/**
	 * @param string
	 */
	public void setGeneralAreaName(String string) {
		GeneralAreaName = string;
	}

	/**
	 * @param i
	 */
	public void setRootID(int i) {
		RootID = i;
	}

	/**
	 * @param string
	 */
	public void setRootTran(String string) {
		RootTran = string;
	}

	/**
	 * @param string
	 */
	public void setRootType(String string) {
		RootType = string;
	}
/**
 * $Log: SyncMapItem.java,v $
 * Revision 1.3  2011/05/05 11:21:34  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:30  jingb
 * no message
 *
 * Revision 1.2  2006/06/20 02:42:26  dave
 * memory cleanup
 *
 * Revision 1.1.1.1  2006/03/30 17:36:31  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.3  2005/06/23 21:03:53  gregg
 * equals method
 *
 * Revision 1.2  2005/05/27 02:09:29  dave
 * introducing ChildGeneralArea
 *
 * Revision 1.1  2005/05/27 02:03:09  dave
 * ok.. first attempt at focusing on isolating GBL8104 from
 * the world
 *
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
	/**
	 * @return
	 */
	public String getChildGeneralAreaName() {
		return ChildGeneralAreaName;
	}

	/**
	 * @param string
	 */
	public void setChildGeneralAreaName(String string) {
		ChildGeneralAreaName = string;
	}

        public final void clear() {

             GeneralAreaName = null;
             ChildGeneralAreaName = null;
             RootType = null;
             RootID = 0;
             RootTran = null;
             ChildType = null;
             ChildID = 0;
             ChildTran = null;
             ChildLevel = 0;
             ChildClass = null;
             ChildPath = null;
             Entity2Type = null;
             Entity2ID = 0;
             Entity1Type = null;
             Entity1ID = 0;
        }

}
