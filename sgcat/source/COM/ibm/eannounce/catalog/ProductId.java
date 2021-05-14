//
// $Log: ProductId.java,v $
// Revision 1.2  2011/05/05 11:21:34  wendy
// src from IBMCHINA
//
// Revision 1.1.1.1  2007/06/05 02:09:28  jingb
// no message
//
// Revision 1.1.1.1  2006/03/30 17:36:31  gregg
// Moving catalog module from middleware to
// its own module.
//
// Revision 1.23  2005/09/14 17:49:39  gregg
// fixes
//
// Revision 1.22  2005/09/14 17:42:58  gregg
// map MODEL<->LCTO
// general setAttribtue cleanup
//
// Revision 1.21  2005/09/14 16:44:54  gregg
// LCTO ~really~ is MODEL per Wayne
//
// Revision 1.20  2005/09/14 16:38:23  gregg
// etype MODEL = LCTO
//
// Revision 1.19  2005/06/22 21:17:16  gregg
// change signature for processSyncMap
//
// Revision 1.18  2005/06/22 20:08:56  gregg
// adding in MODEL VE logic
//
// Revision 1.17  2005/06/14 20:37:54  gregg
// filling out data from PDH and such
//
// Revision 1.16  2005/06/02 20:34:09  gregg
// fixing the parent/child collection reference mayhem
//
// Revision 1.15  2005/05/26 00:06:06  dave
// adding put to design by contract
//
// Revision 1.14  2005/05/25 23:43:58  gregg
// updates
//
// Revision 1.13  2005/05/25 17:36:46  gregg
// going for ProdStruct objects
//
//

package COM.ibm.eannounce.catalog;

public class ProductId extends CatId {

    private String m_strEntityType = null;
	private int m_iEntityId = -1;


    public ProductId(String _strLocEntityType, int _iLocEntityId, GeneralAreaMapItem _gami, ProductCollectionId _pcid) {
		super(_strLocEntityType + ":" + _iLocEntityId + ":" + _gami.getCountryList(), _gami);
		setEntityType(_strLocEntityType);
		setEntityId(_iLocEntityId);
		setCollectionId(_pcid);
    }

    public String dump(boolean _b) {
    	return "TBD";
    }

  //
  // getters
  //

	public final String getEntityType() {
		return m_strEntityType;
	}

	public final int getEntityId() {
		return m_iEntityId;
	}

  //
  // setters
  //

	public final void setEntityType(String _s) {
        // LCTO ~really~ is MODEL per Wayne
		if(_s != null && _s.equals(Product.MODEL_ENTITYTYPE)) {
			m_strEntityType = Product.LCTO_ENTITYTYPE;
		} else {
		    m_strEntityType = _s;
		}
	}

	public final void setEntityId(int _i) {
		m_iEntityId = _i;
	}

	public ProductCollectionId getProductCollectionId() {
		return (ProductCollectionId)getCollectionId();
	}

	public final boolean isLSEO() {
		return getEntityType().equals(Product.LSEO_ENTITYTYPE);
	}

	public final boolean isLSEOBUNDLE() {
		return getEntityType().equals(Product.LSEOBUNDLE_ENTITYTYPE);
	}

	public final boolean isMODEL() {
		// MODEL == LCTO pre Wayne
		return getEntityType().equals(Product.LCTO_ENTITYTYPE);
		//return getEntityType().equals(Product.MODEL_ENTITYTYPE);
	}

}

