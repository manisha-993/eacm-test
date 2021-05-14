//
// Copyright (c) 2005, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: BasicRuleCollection.java,v $
// Revision 1.15  2011/05/05 11:21:32  wendy
// src from IBMCHINA
//
// Revision 1.1.1.1  2007/06/05 02:09:03  jingb
// no message
//
// Revision 1.14  2007/05/15 19:00:45  rick
// changed to get catbrlifecycle from SP 4022
// and set it in BasicRule object.
//
// Revision 1.13  2006/09/21 21:02:11  gregg
// more work for BasicRuleLoad
//
// Revision 1.12  2006/07/31 22:42:26  gregg
// null checks on building BasicRules
//
// Revision 1.11  2006/07/31 22:15:20  gregg
// more
//
// Revision 1.10  2006/07/31 22:14:18  gregg
// compile
//
// Revision 1.9  2006/07/31 22:09:28  gregg
// ScanReplace work
//
// Revision 1.8  2006/05/31 23:11:10  gregg
// rem debug stmt
//
// Revision 1.7  2006/05/31 22:51:22  gregg
// Some basicrulecollection work
//
// Revision 1.6  2006/05/10 19:25:56  gregg
// debugging
//
// Revision 1.5  2006/05/09 21:46:09  gregg
// substitution failed fix
//
// Revision 1.4  2006/05/09 20:34:32  gregg
// class cast fix
//
// Revision 1.3  2006/05/09 20:33:28  gregg
// more substitution failed work
//
// Revision 1.2  2006/04/28 17:12:10  gregg
// check for substitutionFailed
//
// Revision 1.1.1.1  2006/03/30 17:36:27  gregg
// Moving catalog module from middleware to
// its own module.
//
// Revision 1.11  2005/12/15 22:09:47  gregg
// BasicRule building logic for WorldWideAttributes!!
//
// Revision 1.10  2005/12/12 20:53:51  gregg
// debug
//
// Revision 1.9  2005/12/09 21:13:04  gregg
// fix for no rs on 4022
//
// Revision 1.8  2005/11/29 18:31:40  gregg
// basic rule fix to include proper item entity type
//
// Revision 1.7  2005/11/22 22:53:43  gregg
// prepping for wayne spec cases
//
// Revision 1.6  2005/11/22 21:03:51  gregg
// update
//
// Revision 1.5  2005/11/21 19:18:39  gregg
// saving some debug stmts for now..
//
// Revision 1.4  2005/11/21 18:25:59  gregg
// basicrule.featuredetail building step 1
//
// Revision 1.3  2005/11/18 18:03:27  gregg
// debug stmt
//
// Revision 1.2  2005/11/17 00:04:47  gregg
// npe
//
// Revision 1.1  2005/11/16 22:31:01  gregg
// BasicRules
//
//

package COM.ibm.eannounce.catalog;

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
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Collection;

/**
 * BasicRuleCollections per gami...for now.
 */
public class BasicRuleCollection {


    private Hashtable m_hashRules = null;
    private GeneralAreaMapItem m_gami = null;
    private String m_strTable = null;

    public BasicRuleCollection(Catalog _cat, String _strTable, GeneralAreaMapItem _gami) {
        m_gami = _gami;
        m_strTable = _strTable;
		ResultSet rs = null;
		Database db = _cat.getCatalogDatabase();
        m_hashRules = new Hashtable();

		try {
			rs =
				db.callGBL4022(
					new ReturnStatus(-1),
					_gami.getCountry(),
					_gami.getLanguage(),
					_gami.getNLSID(),
					_strTable);


	        ReturnDataResultSet rdrs = new ReturnDataResultSet(rs);

            D.ebug(D.EBUG_SPEW,"GBL4022 record count:" + rdrs.size());


	        for(int i = 0; i < rdrs.getRowCount(); i++) {
  				String strCATENTITYTYPE = rdrs.getColumn(i,0);
  				String strCATITEMTYPE = rdrs.getColumn(i,1);
 				String strCATATTRIBUTECODE = rdrs.getColumn(i,2);
 				String strRULETYPE_FC = rdrs.getColumn(i,3);
 				String strRULETEST = rdrs.getColumn(i,4);
				String strRULEFAIL = rdrs.getColumn(i,5);
				String strRULEPASS = rdrs.getColumn(i,6);
                String strBRENTITYID = rdrs.getColumn(i,7);
                String strCATBRLIFECYCLE_FC = rdrs.getColumn(i,8);
				BasicRule br = (BasicRule)m_hashRules.get(BasicRule.buildKey(_gami,strCATENTITYTYPE, strCATITEMTYPE, strCATATTRIBUTECODE));
				if(br == null) {
					br = new BasicRule(m_gami
					                  ,strCATENTITYTYPE
					                  ,strCATITEMTYPE
					                  ,strCATATTRIBUTECODE);
					                  //,strRULETYPE_FC
					                  //,strRULETEST
					                  //,strRULEFAIL
					                  //,strRULEPASS);
					D.ebug(D.EBUG_SPEW,"GBL4022/building one BasicRule:" + br.getKey());
					D.ebug(D.EBUG_SPEW,"GBL4022/CATBRLIFECYCLE:" + strCATBRLIFECYCLE_FC);
					m_hashRules.put(br.getKey(),br);
				}
                br.setBrentityID(strBRENTITYID);
                br.setCatBrLifeCycle_fc(strCATBRLIFECYCLE_FC);
				if(strRULETYPE_FC.toUpperCase().equals(BasicRule.RULE_TYPE_SUBSTITUTION)) {
					br.setType(strRULETYPE_FC);
					br.setTest(strRULETEST);
					br.setFail(strRULEFAIL);
					br.setPass(strRULEPASS);
				} else if(strRULETYPE_FC.toUpperCase().equals(BasicRule.RULE_TYPE_SCANREPLACE)) {
					if(strRULEPASS != null && strRULEFAIL != null) {
					    br.putScanReplace(strRULEPASS,strRULEFAIL);
					} else {
						D.ebug(D.EBUG_WARN,"Null value for BasicRule:" + br.getKey() + ",\"" + strRULEPASS + "\",\"" + strRULEFAIL + "\"");
					}
				}
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

    public int getBasicRuleCount() {
		return m_hashRules.size();
	}

	public BasicRule[] toArray() {
		BasicRule[] abr = new BasicRule[getBasicRuleCount()];
		Collection colVals = m_hashRules.values();
		Object[] aObj = colVals.toArray();
		for(int i = 0; i < aObj.length; i++) {
			abr[i] = (BasicRule)aObj[i];
		}
		return abr;
	}

	//public BasicRule[] getRulesForEntityType(String _strEntityType) {
	//	// how to do this? couple ways - either store on puts by entitytype/gami, or filter out on the fly.
	//	return new BasicRule[0];
	//}

	public FeatureDetail[] buildFeatureDetails(Catalog _cat, Feature _f) {
		Vector vctFD = new Vector();
        FeatureDetailCollection fdc = _f.getFeatureDetailCollection();
        BasicRule[] abr = this.toArray();
        for(int i = 0; i < abr.length; i++) {
			BasicRule br = abr[i];

			// GAB 092106
			if(CatalogRunner.isBasicRuleLoad() && !br.isQueued()) {
                continue;
			}
			//

			D.ebug(D.EBUG_SPEW,"entering loop logic " + br.getItemType());
            String strBasicRuleItemType = br.getItemType();
            //D.ebug(D.EBUG_SPEW,"buildFeatureDetails, fdc.hasItemEntityType for " + strBasicRuleItemType + " = " + fdc.hasItemEntityType(strBasicRuleItemType));
            //if(fdc.hasItemEntityType(strBasicRuleItemType)) {
            if (_cat == null) {
            D.ebug(D.EBUG_SPEW,"cat is null");	
            }
            if (_f == null) {
                D.ebug(D.EBUG_SPEW,"f is null");	
            }
				if(br.passTest(_f)) {
					D.ebug(D.EBUG_SPEW,"calling buildFeatureDetail from brc buildfeaturedetails");
			    	FeatureDetail fd = br.buildFeatureDetail(_cat,_f);
			    	if(!br.substitutionFailed(_f)) {
						D.ebug(D.EBUG_SPEW,"buildFeatureDetails ADDING fd:" + fd.toString());
			    	    vctFD.addElement(fd);
					} else {
						D.ebug(D.EBUG_SPEW,"buildFeatureDetails SKIPPING fd:" + fd.toString());
					}
				}
			//} else {
				// do fail ... what is this???
			//}


		}
		FeatureDetail[] afd = new FeatureDetail[vctFD.size()];
		vctFD.copyInto(afd);
		return afd;
	}

	public WorldWideAttribute[] buildWorldWideAttributes(Catalog _cat, WorldWideProduct _wwp) {
		Vector vctWWA = new Vector();
        WorldWideAttributeCollection wwac = _wwp.getWorldWideAttributeCollection();
        BasicRule[] abr = this.toArray();
        for(int i = 0; i < abr.length; i++) {
			BasicRule br = abr[i];

			// GAB 092106
			D.ebug(D.EBUG_SPEW,"checking isQueued for" + br.getKey() + " brlifecycle:" + br.getCatBrLifeCycle_fc());
			if(CatalogRunner.isBasicRuleLoad() && !br.isQueued()) {
                continue;
			}
			//
			D.ebug(D.EBUG_SPEW,"entering loop logic " + br.getItemType());
            String strBasicRuleItemType = br.getItemType();
            //_cat.getCatalogDatabase().debug(D.EBUG_SPEW,"buildWorldWideAttributes, wwa.hasItemEntityType for " + strBasicRuleItemType + " = " + wwac.hasAttEntityType(strBasicRuleItemType));
            //if(wwac.hasAttEntityType(strBasicRuleItemType)) {
				if(br.passTest(_wwp)) {
			    	WorldWideAttribute wwa = br.buildWorldWideAttribute(_cat,_wwp);
			    	if(!br.substitutionFailed(_wwp)) {
						D.ebug(D.EBUG_SPEW,"buildWorldWideAttributes ADDING wwa:" + wwa.toString());
			    	    vctWWA.addElement(wwa);
					} else {
						D.ebug(D.EBUG_SPEW,"buildWorldWideAttributes SKIPPING wwa:" + wwa.toString());
					}
				}
			//} else {
				// do fail ... what is this???
			//}
		}
		WorldWideAttribute[] awwa = new WorldWideAttribute[vctWWA.size()];
		vctWWA.copyInto(awwa);
		return awwa;
	}

    public String getKey() {
		return buildKey(m_strTable,m_gami);
	}

    public static String buildKey(String _strTable, GeneralAreaMapItem _gami) {
		return _strTable + ":" + _gami.toString();
	}

}
