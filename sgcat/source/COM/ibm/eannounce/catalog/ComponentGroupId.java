/*
 * Created on May 13, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package COM.ibm.eannounce.catalog;

/**
 * 
 * @author Bala
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ComponentGroupId extends CatId {

	private String EntityType = null;
	private int EntityID = 0;

    /**
     * ComponentGroupId
     * @param _str
     */
     
   public ComponentGroupId( String _strEntityType, int _iEntityID, GeneralAreaMapItem _gami) {
        super(_iEntityID + ":" + _strEntityType , _gami);
        setEntityType(_strEntityType);
        setEntityID(_iEntityID);
    }

	  public ComponentGroupId( String _strEntityType, int _iEntityID, GeneralAreaMapItem _gami, ComponentGroupCollectionId _colid) {
		  super(_strEntityType + ":" + _iEntityID, _gami, _colid);
		  this.setEntityType(_strEntityType);
		  this.setEntityID(_iEntityID);
          this.setComponentGroupCollectionId(_colid);
	  }

	/**
	 * @return
	 */
	public int getEntityID() {
		return EntityID;
	}

	/**
	 * @return
	 */
	public String getEntityType() {
		return EntityType;
	}

	/**
	 * @param i
	 */
	public void setEntityID(int i) {
		EntityID = i;
	}

	/**
	 * @param string
	 */
	public void setEntityType(String string) {
		EntityType = string;
	}
    
    public static void main(String[] args) {
    }

	
	 public String dump(boolean _b) {
		 return "TBD";
	 }
     
	public ComponentGroupCollectionId getComponentGroupCollectionId() {
		return (ComponentGroupCollectionId)getCollectionId();
	}

	public void setComponentGroupCollectionId(ComponentGroupCollectionId id) {
		setCollectionId(id);
	}
     
    
}

/* * $Log: ComponentGroupId.java,v $
/* * Revision 1.2  2011/05/05 11:21:34  wendy
/* * src from IBMCHINA
/* *
/* * Revision 1.1.1.1  2007/06/05 02:09:15  jingb
/* * no message
/* *
/* * Revision 1.1.1.1  2006/03/30 17:36:28  gregg
/* * Moving catalog module from middleware to
/* * its own module.
/* *
/* * Revision 1.6  2005/07/11 22:51:16  bala
/* * changes
/* * 
*/
