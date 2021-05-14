/*
 * Created on Mar 16, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * $Log: Category.java,v $
 * Revision 1.5  2011/05/05 11:21:35  wendy
 * src from IBMCHINA
 *
 * Revision 1.1.1.1  2007/06/05 02:09:09  jingb
 * no message
 *
 * Revision 1.4  2006/07/25 16:56:03  gregg
 * setting isActive + processing OFF/ON childtrans
 *
 * Revision 1.3  2006/05/04 16:57:43  gregg
 * debug
 *
 * Revision 1.2  2006/05/04 15:37:14  gregg
 * extending some col widths (cceidentifier related)
 *
 * Revision 1.1.1.1  2006/03/30 17:36:27  gregg
 * Moving catalog module from middleware to
 * its own module.
 *
 * Revision 1.50  2006/03/08 18:07:00  gregg
 * pricedisclaimer = longvarchar
 *
 * Revision 1.49  2006/03/03 18:03:49  gregg
 * long varchar catbulletpoints = 32671
 *
 * Revision 1.48  2006/03/03 17:50:44  gregg
 * catbulletpoints = long varchar
 *
 * Revision 1.47  2005/12/22 18:04:15  gregg
 * removing debugs
 *
 * Revision 1.46  2005/12/21 22:48:23  gregg
 * debug
 *
 * Revision 1.45  2005/12/20 22:14:42  gregg
 * debug
 *
 * Revision 1.44  2005/12/16 21:18:29  gregg
 * prepping setAttributes for LEVELXVALFROM populations based on att valfrom dates
 *
 * Revision 1.43  2005/11/22 21:52:28  gregg
 * oh yeah move replaceFirst to StringUtil and use this method per jre 1.3.1
 *
 * Revision 1.42  2005/11/03 23:08:02  gregg
 * null ptr fix (?) for 8985
 *
 * Revision 1.41  2005/11/03 21:45:50  gregg
 * CCEIDENTIFIER goodness
 *
 * Revision 1.40  2005/11/02 21:44:37  gregg
 * trace
 *
 * Revision 1.39  2005/11/02 21:18:27  gregg
 * debug
 *
 * Revision 1.38  2005/11/02 20:53:31  gregg
 * some CCEIDENTIFIER stuff
 *
 * Revision 1.37  2005/11/01 18:18:04  gregg
 * PUBLISHFLAG int logic
 *
 * Revision 1.36  2005/10/31 21:19:41  gregg
 * replaceAll algorithm
 *
 * Revision 1.35  2005/10/31 19:47:00  gregg
 * temp. rem. replace for compile
 *
 * Revision 1.34  2005/10/31 19:10:39  gregg
 * no replaceAll? weird.
 *
 * Revision 1.33  2005/10/31 18:43:12  gregg
 * replcaing [NO VAlue] in some fields
 *
 * Revision 1.32  2005/10/28 17:03:04  gregg
 * columns object allowNull property to return null or ""
 *
 * Revision 1.31  2005/10/27 23:57:37  gregg
 * default in props + some fixes
 *
 * Revision 1.30  2005/10/26 18:05:52  gregg
 * remove some traces
 *
 * Revision 1.29  2005/10/25 22:52:24  gregg
 * *** empty log message ***
 *
 * Revision 1.25  2005/10/13 19:48:24  gregg
 * getValueCombinationCount() fix
 *
 * Revision 1.24  2005/10/13 18:25:43  gregg
 * fix
 *
 * Revision 1.23  2005/10/13 00:12:43  gregg
 * more getValueCombo
 *
 * Revision 1.22  2005/10/12 21:17:07  gregg
 * CatNavGroup/Item --> MultiRowAttrGroup/Item
 *
 * Revision 1.21  2005/10/12 21:11:30  bala
 * Change class CatNavGroup/Item to MultiRowAttrGroup/Item
 *
 * Revision 1.20  2005/10/12 19:44:04  gregg
 * cleaning up CatNav funtionality
 *
 * Revision 1.19  2005/10/12 19:21:28  gregg
 * temp fix for compile
 *
 * Revision 1.18  2005/10/12 18:26:37  gregg
 * some more CatNav work
 *
 * Revision 1.17  2005/10/12 16:41:17  gregg
 * fix
 *
 * Revision 1.16  2005/10/12 16:34:48  gregg
 * more CatNavGroup up into CatDBTableRecord.
 *
 * Revision 1.15  2005/10/10 21:22:27  gregg
 * CatNavItem fix
 *
 * Revision 1.14  2005/10/10 21:19:35  gregg
 * some multi flag stuff
 *
 * Revision 1.13  2005/10/10 17:43:16  gregg
 * catnav logic
 *
 * Revision 1.12  2005/10/07 21:15:19  gregg
 * three new cols
 *
 * Revision 1.11  2005/10/07 21:08:26  gregg
 * more cat cols
 *
 * Revision 1.10  2005/10/07 17:55:07  gregg
 * adding CATEGORY.CATAUDIENCE
 *
 * Revision 1.9  2005/10/06 18:23:12  gregg
 * more props functionality (literal "val")
 *
 * Revision 1.8  2005/10/06 16:52:00  gregg
 * get correct props vals
 *
 * Revision 1.7  2005/10/06 16:25:59  gregg
 * get CATNAV atts populated
 *
 * Revision 1.6  2005/10/05 22:54:15  gregg
 * NO_TIMESTAMP_VAL
 *
 * Revision 1.5  2005/10/04 22:18:04  gregg
 * compile
 *
 * Revision 1.4  2005/10/04 22:10:10  gregg
 * compile
 *
 * Revision 1.3  2005/10/04 22:05:14  gregg
 * put
 *
 * Revision 1.2  2005/10/04 22:01:55  gregg
 * pounding into workingness
 *
 */
package COM.ibm.eannounce.catalog;

import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EANFoundation;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EANMetaFoundation;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EANTextAttribute;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.MetaFlag;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.D;
import java.util.Vector;
import java.util.StringTokenizer;
import java.util.Set;
import java.util.HashMap;
import java.util.Enumeration;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import java.lang.reflect.Field;
import java.lang.Package;
import java.lang.reflect.InvocationTargetException;




/**
 * This represent a generic product for IBM product internal Catalogues
 *
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Category extends CatDBTableRecord {

    public static final String CATNAV_ENTITYTYPE = "CATNAV";
    public static final String FMLY_ENTITYTYPE = "FMLY";
    public static final String SER_ENTITYTYPE = "SER";

    public static final String TABLE_NAME = "CATEGORY";

    public static final String ENTERPRISE = "ENTERPRISE";
    public static final String ENTITYTYPE = "ENTITYTYPE";
    public static final String ENTITYID = "ENTITYID";
    public static final String COUNTRYCODE = "COUNTRYCODE";
    public static final String LANGUAGECODE = "LANGUAGECODE";
    public static final String NLSID = "NLSID";
    public static final String COUNTRYLIST = "COUNTRYLIST";
    public static final String CATNAVTYPE = "CATNAVTYPE";
    public static final String CATNAVTYPE_FC = "CATNAVTYPE_FC";
    public static final String CATNAME = "CATNAME";
    public static final String CCEIDENTIFIER = "CCEIDENTIFIER";
    public static final String CATBR = "CATBR";
    public static final String CATFMLY = "CATFMLY";
    public static final String CATSER = "CATSER";
    public static final String CATOPTGRPNAM = "CATOPTGRPNAM";
    public static final String CATFAMDESC = "CATFAMDESC";
    public static final String CATSERDESC = "CATSERDESC";
    public static final String CATSERHEAD = "CATSERHEAD";
    public static final String CATAUDIENCE = "CATAUDIENCE";
    public static final String CATAUDIENCE_FC = "CATAUDIENCE_FC";
    public static final String CATPARTNUMBER_E = "CATPARTNUMBER_E";
    public static final String CATPARTNUMBER_V = "CATPARTNUMBER_V";
    public static final String CATPARTNUMBER_P = "CATPARTNUMBER_P";
    public static final String CCEIDENTIFIER1 = "CCEIDENTIFIER1";
    public static final String CCEIDENTIFIER2 = "CCEIDENTIFIER2";
    public static final String CCEIDENTIFIER3 = "CCEIDENTIFIER3";
    public static final String CCEIDENTIFIER4 = "CCEIDENTIFIER4";
    public static final String PROJCDNAM = "PROJCDNAM";
    public static final String PROJCDNAM_FC = "PROJCDNAM_FC";
    public static final String FMLYID = "FMLYID";
    public static final String SERID = "SERID";
    public static final String CATBULLETPOINTS = "CATBULLETPOINTS";
    public static final String IMGDISCLAIMER = "IMGDISCLAIMER";
    public static final String PRICEDISCLAIMER = "PRICEDISCLAIMER";
    public static final String SRCHTITLE = "SRCHTITLE";
    public static final String SRCHKEYWORD = "SRCHKEYWORD";
    public static final String SRCHABSTRACT = "SRCHABSTRACT";
    public static final String SRCHDESC = "SRCHDESC";
    public static final String SRCHCATG = "SRCHCATG";
    public static final String SRCHDOCTYPE = "SRCHDOCTYPE";
    public static final String PUBLISHFLAG = "PUBLISHFLAG";
    public static final String LEVEL1VALFROM = "LEVEL1VALFROM";
    public static final String LEVEL2VALFROM = "LEVEL2VALFROM";
    public static final String LEVEL3VALFROM = "LEVEL3VALFROM";
    public static final String LEVEL4VALFROM = "LEVEL4VALFROM";
    public static final String VALFROM = "VALFROM";
    public static final String VALTO = "VALTO";
    public static final String ISACTIVE = "ISACTIVE";

    static {
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(ENTERPRISE,8,false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(ENTITYTYPE,32,false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeIntColumn(ENTITYID,false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(COUNTRYCODE,2,false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(LANGUAGECODE,2,false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeIntColumn(NLSID,false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(COUNTRYLIST,8,false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(CATNAVTYPE,15,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(CATNAVTYPE_FC,4,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(CATNAME,25,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(CCEIDENTIFIER,254,false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(CATBR,50,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(CATFMLY,50,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(CATSER,50,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(CATOPTGRPNAM,50,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(CATFAMDESC,128,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(CATSERDESC,128,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(CATSERHEAD,128,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(CATAUDIENCE,128,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(CATAUDIENCE_FC,8,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(CATPARTNUMBER_E,128,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(CATPARTNUMBER_V,128,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(CATPARTNUMBER_P,128,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(CCEIDENTIFIER1,254,false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(CCEIDENTIFIER2,254,false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(CCEIDENTIFIER3,254,false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(CCEIDENTIFIER4,254,false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(PROJCDNAM,254,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(PROJCDNAM_FC,128,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeIntColumn(FMLYID,false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeIntColumn(SERID,false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(CATBULLETPOINTS,32671,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(IMGDISCLAIMER,128,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(PRICEDISCLAIMER,32671,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(SRCHTITLE,128,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(SRCHKEYWORD,128,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(SRCHABSTRACT,128,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(SRCHDESC,128,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(SRCHCATG,128,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(SRCHDOCTYPE,128,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeIntColumn(PUBLISHFLAG,true);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(LEVEL1VALFROM,26,false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(LEVEL2VALFROM,26,false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(LEVEL3VALFROM,26,false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(LEVEL4VALFROM,26,false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(VALFROM,26,false);
        CatDBTable.getCatDBTable(TABLE_NAME).makeStringColumn(VALTO,26,false);
    }

    private SyncMapCollection m_smc = null;

	private CategoryCollection m_psc = null; // children container

	/**
	 * Category lets get the Light
	 * @param _cid
	 */
	public Category(CategoryId _cid) {
		super(TABLE_NAME,_cid);
		putStringVal(ENTITYTYPE,_cid.getEntityType());
		putIntVal(ENTITYID,_cid.getEntityID());
		// TODO Auto-generated constructor stub
	}

	/**
	 * Product - lets get the heavy
	 * Version
	 * @param _cid
	 */
	public Category(CategoryId _cid, Catalog _cat) {
		this(_cid);
		get(_cat);
	}

    /**
     * main
     *
     * @param args
     */
    public static void main(String[] args) {


    }


	/**
	 * get
	 *
	 * When ever we pull from the PDH, we need the SMC as a quide
	 *
	 */
	public void get(Catalog _cat) {
		//
		// When we are getting something out of the CatDB
		// We need to pull enterprise out of the ProductID.

		ResultSet rs = null;
		Database db = _cat.getCatalogDatabase();
		Profile prof = _cat.getCatalogProfile();

		CategoryId cid = this.getCategoryId();
		CategoryCollectionId ccid = cid.getCategoryCollectionId();

		GeneralAreaMapItem gami = cid.getGami();

		CatalogInterval cati = ccid.getInterval();

		String strEnterprise = gami.getEnterprise();
		String strEntityType = cid.getEntityType();
		int iEntityID = cid.getEntityID();
		int iNLSID = gami.getNLSID();
		String strCountryList = gami.getCountryList();
		String strLanguageCode = gami.getLanguage();
		String strCountryCode = gami.getCountry();

        MultiRowAttrGroup cng = new MultiRowAttrGroup(_cat,this.getClass().getName());
        setMultiRowAttrGroup(cng);

		if (ccid.isByInterval() && ccid.isFromPDH()) {

			// Right now .. we are gong to have to rebuild this guy.. and
			// any other thing it may be resonsible for
			// at this point.. we will always need to

			if (this.getSmc() == null) {
				System.out.println("Cannot pull out of the PDH since there is no SycnMap for me.");
				;
			}

			//
			// Lets set the valon's in the profile for the Catalog to
			// I am not sure i like setting them here.
			//

			prof.setEffOn(cati.getEndDate());
			prof.setValOn(cati.getEndDate());

			try {

				EntityItem eiRoot = Catalog.getEntityItem(_cat, cid.getEntityType(), cid.getEntityID());
				this.setAttributes(eiRoot);
				db.debug(D.EBUG_SPEW, " @@@ EntityItem is:" + cid.getEntityType() + ":" + cid.getEntityID());

                //
                //EntityGroup eg = eiRoot.getEntityGroup();
                //db.debug(D.EBUG_SPEW,"debugging metaattributes metaattributecount:" + eg.getMetaAttributeCount());
                //for(int i = 0; i < eg.getMetaAttributeCount(); i++) {
                //    EANMetaAttribute ema = eg.getMetaAttribute(i);
                //    db.debug(D.EBUG_SPEW,"debugging metaattributes:" + eg.getEntityType() + " att [" + i + "]:" + ema.getAttributeCode());
				//}
				//

				//
				// O.K.  here is where I have to go get other things If I have to find kiddies.
				// If we have a sync map collection.. lets get it and search for other things
				//
				if (this.hasSyncMapCollection()) {

					//db.debug(D.EBUG_SPEW, " @@@SyncMapCollection (count=" + this.getSmc().getCount() + "): " + this.getSmc().toString());

					//
					// Okay, now any "children" entities...
					//
					// We know of only two children.  They are Family and Series
					//
					for (int i = 0; i < this.getSmc().getCount(); i++) {
						SyncMapItem smi = getSmc().get(i);

            			if(!smi.getChildTran().equals("ON")) {
							continue;
						}

						//D.ebug(this, D.EBUG_SPEW, "@@@ Child Type is " + smi.getChildType());
						//if (smi.getChildType().equals(FMLY_ENTITYTYPE) || smi.getChildType().equals(SER_ENTITYTYPE)) {
							this.setAttributes(Catalog.getEntityItem(_cat, smi.getChildType(), smi.getChildID()));
						//}
					}
				}
			} catch (Exception ex) {
				D.ebug(D.EBUG_ERR,ex.toString());
				ex.printStackTrace(System.err);
				//TODO
			}
		} else if (ccid.isFromCAT()) {

			//
			// pl we want to get it from the CatDb.
			try {
				rs = db.callGBL4021(new ReturnStatus(-1), strEnterprise, strEntityType, iEntityID, strCountryCode, strLanguageCode, iNLSID);
				ReturnDataResultSet rdrs = new ReturnDataResultSet(rs);

				for (int i = 0; i < rdrs.getRowCount(); i++) {
                    putStringVal(ENTERPRISE,rdrs.getColumn(i,1));
                    putStringVal(ENTITYTYPE,rdrs.getColumn(i,2));
                    putIntVal(ENTITYID,rdrs.getColumnInt(i,3));
                    putStringVal(COUNTRYCODE,rdrs.getColumn(i,4));
                    putStringVal(LANGUAGECODE,rdrs.getColumn(i,5));
                    putIntVal(NLSID,rdrs.getColumnInt(i,6));
                    putStringVal(COUNTRYLIST,rdrs.getColumn(i,7));
                    putStringVal(CATNAVTYPE,rdrs.getColumn(i,8));
                    putStringVal(CATNAVTYPE_FC,rdrs.getColumn(i,9));
                    putStringVal(CATNAME,rdrs.getColumn(i,10));
                    putStringVal(CCEIDENTIFIER,rdrs.getColumn(i,11));
                    putStringVal(CATBR,rdrs.getColumn(i,12));
                    putStringVal(CATFMLY,rdrs.getColumn(i,13));
                    putStringVal(CATSER,rdrs.getColumn(i,14));
                    putStringVal(CATOPTGRPNAM,rdrs.getColumn(i,15));
                    putStringVal(CATFAMDESC,rdrs.getColumn(i,16));
                    putStringVal(CATSERDESC,rdrs.getColumn(i,17));
                    putStringVal(CATSERHEAD,rdrs.getColumn(i,18));
                    putStringVal(CATAUDIENCE,rdrs.getColumn(i,19));
                    putStringVal(CATAUDIENCE_FC,rdrs.getColumn(i,20));
                    putStringVal(CATPARTNUMBER_E,rdrs.getColumn(i,21));
                    putStringVal(CATPARTNUMBER_V,rdrs.getColumn(i,22));
                    putStringVal(CATPARTNUMBER_P,rdrs.getColumn(i,23));
                    putStringVal(CCEIDENTIFIER1,rdrs.getColumn(i,24));
                    putStringVal(CCEIDENTIFIER2,rdrs.getColumn(i,25));
                    putStringVal(CCEIDENTIFIER3,rdrs.getColumn(i,26));
                    putStringVal(CCEIDENTIFIER4,rdrs.getColumn(i,27));
                    putStringVal(PROJCDNAM,rdrs.getColumn(i,28));
                    putStringVal(PROJCDNAM_FC,rdrs.getColumn(i,29));
                    putIntVal(FMLYID,rdrs.getColumnInt(i,30));
                    putIntVal(SERID,rdrs.getColumnInt(i,31));
                    putStringVal(CATBULLETPOINTS,rdrs.getColumn(i,32));
                    putStringVal(IMGDISCLAIMER,rdrs.getColumn(i,33));
                    putStringVal(PRICEDISCLAIMER,rdrs.getColumn(i,34));
                    putStringVal(SRCHTITLE,rdrs.getColumn(i,35));
                    putStringVal(SRCHKEYWORD,rdrs.getColumn(i,36));
                    putStringVal(SRCHABSTRACT,rdrs.getColumn(i,37));
                    putStringVal(SRCHDESC,rdrs.getColumn(i,38));
                    putStringVal(SRCHCATG,rdrs.getColumn(i,39));
                    putStringVal(SRCHDOCTYPE,rdrs.getColumn(i,40));
                    putIntVal(PUBLISHFLAG,rdrs.getColumnInt(i,41));
                    putStringVal(LEVEL1VALFROM,rdrs.getColumn(i,42));
                    putStringVal(LEVEL2VALFROM,rdrs.getColumn(i,43));
                    putStringVal(LEVEL3VALFROM,rdrs.getColumn(i,44));
                    putStringVal(LEVEL4VALFROM,rdrs.getColumn(i,45));
                    putStringVal(VALFROM,rdrs.getColumn(i,46));
                    putStringVal(VALTO,rdrs.getColumn(i,47));
                    putIntVal(ISACTIVE,rdrs.getColumnInt(i,48));
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
	}


	/**
	 *  Here, its the same as a standard get.  If we are sourced from the PDH and are in build mode
	 *  This is a stand alone entity!
	 *
	 *  (non-Javadoc)
	 * @see COM.ibm.eannounce.catalog.Databaseable#getReferences(COM.ibm.eannounce.catalog.Catalog, int)
	 */
	public void getReferences(Catalog _cat, int _iCase) {
	}

	/**
	 * This is going to fill out ALL Data (fat) and ALL references (deep) _iLvl's deep.
	 */
	public void getFatAndDeep(Catalog _cat, int _iLvl) {
	}

	/**
	 * For debugging XML
	 */
	public void dumpXML(XMLWriter _xml, boolean _bDeep) {
		generateXMLFragment(_xml);
	}

	/**
	 * getCategoryId
	 *
	 * 	 * O.K. We are going to get the category Id
	 *
	 * @
	 */
	public CategoryId getCategoryId() {
		return (CategoryId) super.getId();
	}

	//public void setProductCollectionId(ProductCollectionId _pcid) {
	//    m_pcid = _pcid;
	//}

	public String dump(boolean _bBrief) {
		StringBuffer sb = new StringBuffer("[CATEGORY]:");
		/*		if (_bBrief) {
					sb.append(getENTERPRISE() + ",");
					sb.append(getLOCENTITYTYPE() + ",");
					sb.append(getLOCENTITYID() + ",");
					sb.append(getNLSID() + ",");
				} else {
					sb.append(getENTERPRISE() + ",");
					sb.append(getCOUNTRYCODE() + ",");
					sb.append(getLANGUAGECODE() + ",");
					sb.append(getNLSID() + ",");
					sb.append(getWWENTITYTYPE() + ",");
					sb.append(getWWENTITYID() + ",");
					sb.append(getLOCENTITYTYPE() + ",");
					sb.append(getLOCENTITYID() + ",");
					sb.append(getMODELNAME() + ",");
					sb.append(getWWPARTNUMBER() + ",");
					sb.append(getPARTNUMBER() + ",");
					sb.append(getMARKETINGDESC() + ",");
					sb.append(getANNDATE() + ",");
					sb.append(getWITHDRAWLDATE() + ",");
					sb.append(getFOTDATE() + ",");
					sb.append(getFLFILSYSINDC() + ",");
					sb.append(getFLFILSYSINDC_FC() + ",");
					sb.append(getPUBLISHFLAG() + ",");
					sb.append(getSTATUS() + ",");
					sb.append(getSTATUS_FC() + ",");
					sb.append(getVALFROM() + ",");
					sb.append(getVALTO() + ",");
					sb.append(isActive());
					if (isProdStructCollectionLoaded()) {
						sb.append("\n" + getProdStructCollection().dump(_bBrief));
					}
				}
			*/
		return sb.toString();

	}

	public String toString() {
		return dump(true);
	}

	///////////////
	///////////////

	public void put(Catalog _cat, boolean _bcommit) {
		if (Catalog.isDryRun()) {
			D.ebug(D.EBUG_SPEW, this.getClass().getName() + " >>> Catalog.isDryRun() == true!!! Skipping put()! <<<");
		}
		if (!hasMultiRowAttrGroup() || getMultiRowAttrGroup().getItemCount() == 0) {
			D.ebug(D.EBUG_SPEW, this.getClass().getName() + " >>> MultiRowAttrGroup id null or Empty...cannot update!!  Skipping put()! <<<");
		}
		Database db = _cat.getCatalogDatabase();
		ReturnStatus rets = new ReturnStatus(-1);
		CategoryId cid = this.getCategoryId();
		GeneralAreaMapItem gami = cid.getGami();

		String strEnterprise = gami.getEnterprise();
		String strCountryCode = gami.getCountry();
		String strLanguageCode = gami.getLanguage();
		String strCountryList = gami.getCountryList();
		int iNLSID = gami.getNLSID();
		String strEntityType = cid.getEntityType();
		int iEntityID = cid.getEntityID();

        //D.ebug(D.EBUG_SPEW,"getMultiRowAttrGroup().getValueCombinationCount():" + getMultiRowAttrGroup().getValueCombinationCount());

        for(int i =  0; i < getMultiRowAttrGroup().getValueCombinationCount(); i++) {

				try {

					db.callGBL8985(rets, strEnterprise, strEntityType, iEntityID, strCountryCode, strLanguageCode, iNLSID, strCountryList,
									this.getStringVal(CATNAVTYPE),
        		                    this.getStringVal(CATNAVTYPE_FC),
        		                    this.getStringVal(CATNAME),
        		                    this.getStringVal(CCEIDENTIFIER),
        		                    this.getStringVal(CATBR),
        		                    this.getStringVal(CATFMLY),
        		                    this.getStringVal(CATSER),
        		                    this.getStringVal(CATOPTGRPNAM),
        		                    this.getStringVal(CATFAMDESC),
        		                    this.getStringVal(CATSERDESC),
        		                    this.getStringVal(CATSERHEAD),
        		                    this.getStringVal(CATAUDIENCE,i),
        		                    this.getStringVal(CATAUDIENCE_FC,i),
        		                    this.getStringVal(CATPARTNUMBER_E),
        		                    this.getStringVal(CATPARTNUMBER_V),
        		                    this.getStringVal(CATPARTNUMBER_P),
        		                    this.getStringVal(CCEIDENTIFIER1),
        		                    this.getStringVal(CCEIDENTIFIER2),
        		                    this.getStringVal(CCEIDENTIFIER3),
        		                    this.getStringVal(CCEIDENTIFIER4),
        		                    this.getStringVal(PROJCDNAM,i),
        		                    this.getStringVal(PROJCDNAM_FC,i),
        		                    this.getIntVal(FMLYID),
        		                    this.getIntVal(SERID),
        		                    this.getStringVal(CATBULLETPOINTS),
        		                    this.getStringVal(IMGDISCLAIMER),
        		                    this.getStringVal(PRICEDISCLAIMER),
        		                    this.getStringVal(SRCHTITLE),
        		                    this.getStringVal(SRCHKEYWORD),
        		                    this.getStringVal(SRCHABSTRACT),
        		                    this.getStringVal(SRCHDESC),
        		                    this.getStringVal(SRCHCATG),
        		                    this.getStringVal(SRCHDOCTYPE),
        		                    this.getIntVal(PUBLISHFLAG),
        		                    this.getStringVal(LEVEL1VALFROM),
        		                    this.getStringVal(LEVEL2VALFROM),
        		                    this.getStringVal(LEVEL3VALFROM),
        		                    this.getStringVal(LEVEL4VALFROM),
					                (this.isActive() ? 1 : 0));


					if (_bcommit) {
						db.commit();
					}
					db.freeStatement();
					db.isPending();

				} catch (MiddlewareException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
	}

	public void merge(CatItem _ci) {
		// TODO Auto-generated method stub

	}


	/**
	 * If I have an Smc Collection.  This means I have work to do to sync up to the CatDb
	 *
	 * @
	 */
	public SyncMapCollection getSmc() {
		return m_smc;
	}

	/**
	 * If I have an Smc Collection.  This means I have work to do to sync up to the CatDb
	 * @param collection
	 */
	public void setSmc(SyncMapCollection collection) {
		m_smc = collection;
	}

	/**
	 * hasSyncMapCollection
	 *
	 * @
	 */
	public final boolean hasSyncMapCollection() {
		return m_smc != null;
	}
    /**
     * setAttributes
     * sets all the attributes it can given the EntityItem that was passed
     * This handles all EntityTypes that have to run through this object
     * Here is where we flatten things out and apply any concatenation rules
     *
     * @param _ei
     */
    public void setAttributes(EntityItem _ei) {

        String strVE = "CATNAV";//CategoryCollection.CATNAV_VE;

        D.ebug(D.EBUG_SPEW,"Category.setAttributes EntityItem is:" + _ei.getKey());

        setAttributesFromProps(_ei,strVE);

        if(_ei.getEntityType().equals(CATNAV_ENTITYTYPE)) {


           	// FLFILSYSINDC for Catalog Override
			String[] sa = getAttributeValue(_ei,"CATPUBLISH");
			D.ebug(D.EBUG_DETAIL,"sa[0] for CATPUBLISH:" + sa[0]);
			D.ebug(D.EBUG_DETAIL,"sa[1] for CATPUBLISH:" + sa[1]);
			int iVal = 0;
			if(sa[1] != null && sa[1].equalsIgnoreCase("Yes")) {
				iVal = 1;
			}
			putIntVal(PUBLISHFLAG,iVal);

			// Ok, here is where we insert the logic for LEVELXVALFROMS being set based on a ~certain???~ attribute's valfrom date..
			// For kicks, let us see if it's possible!
			//
			EANAttribute att = _ei.getAttribute("CATPUBLISH");
			if(att != null) {
				String strValFrom = att.getValFrom();
				D.ebug(D.EBUG_SPEW,"Category.setAttributes, checking valfrom date on CATPUBLISH attribute:" + strValFrom);
			} else {
			    D.ebug(D.EBUG_SPEW,"Category.setAttributes, checking valfrom date on CATPUBLISH - att is null!");
			}
		}

    }

	///////////////////////
	// END PRUNING CODE //
	//////////////////////

	/**
	 * This generates an XMLFragment per the XML Interface
	 *
	 * Over the time, we can make new getXXXXX's and pass the
	 * xml object to them and they will generate their own fragements
	 * @param _xml
	 */
	public void generateXMLFragment(XMLWriter _xml) {

		CategoryId cid = this.getCategoryId();

		try {
			_xml.writeEntity(cid.getEntityType());
			_xml.endEntity();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

///////
//
///////
        public void putStringVal(String _strColKey, String _strVal) {
			try {
				D.ebug(D.EBUG_SPEW,"putStringVal (" + getCategoryId().getEntityID() + "): [len=" + _strVal.length() + "] " + _strColKey + "=" + _strVal);
				super.putStringVal(_strColKey,_strVal);
			} catch(NullPointerException exc) {
				D.ebug(D.EBUG_ERR,"NPE - figure this out later");
			}
		}
        public void putIntVal(String _strColKey, int _iVal) {
			//D.ebug(D.EBUG_SPEW,"putIntVal:" + _strColKey + "=" + _iVal);
			super.putIntVal(_strColKey,_iVal);
		}

        public String getStringVal(String _strColKey) {
			String s = super.getStringVal(_strColKey);
			if(s == null &&
			    (_strColKey.equals(LEVEL1VALFROM) ||
			     _strColKey.equals(LEVEL2VALFROM) ||
			     _strColKey.equals(LEVEL3VALFROM) ||
			     _strColKey.equals(LEVEL4VALFROM))) {
				s = NO_TIMESTAMP_VAL;
			}
			if(_strColKey.equals(CCEIDENTIFIER1) ||
			   _strColKey.equals(CCEIDENTIFIER2) ||
			   _strColKey.equals(CCEIDENTIFIER3) ||
			   //_strColKey.equals(CCEIDENTIFIER4) ||
			   _strColKey.equals(CCEIDENTIFIER) ||
			   _strColKey.equals(CATBR) ||
			   _strColKey.equals(CATFMLY) ||
			   _strColKey.equals(CATSER)) {
				   //D.ebug(D.EBUG_SPEW,"replacing noval in " + _strColKey + "(" + s + ")");
                   if(s != null && s.indexOf(NO_STRING_VAL) > -1) {
					   s = StringUtil.replaceAll(s,NO_STRING_VAL+"@","");
					   s = StringUtil.replaceAll(s,"@"+NO_STRING_VAL,"");
				   }
			}
			if(_strColKey.equals(CCEIDENTIFIER4)) {
				   //D.ebug(D.EBUG_SPEW,"replacing noval in " + _strColKey + "(" + s + ") with EMTPYSTRING");
                   if(s != null && s.indexOf(NO_STRING_VAL) > -1) {
					   s = null;
				   }
			}
			if(s == null) {
				s = "";
			}
			return s;
		}
        public int getIntVal(String _iColKey) {
			return super.getIntVal(_iColKey);
		}

}
