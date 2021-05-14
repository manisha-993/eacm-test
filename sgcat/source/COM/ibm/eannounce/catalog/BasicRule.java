//
// Copyright (c) 2005, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: BasicRule.java,v $
// Revision 1.32  2011/08/11 14:21:55  praveen
// Add parans to WWSEOWARR if clauses
//
// Revision 1.31  2011/05/05 11:21:34  wendy
// src from IBMCHINA
//
// Revision 1.3  2011/04/19 00:10:36  rick
// changes to support finding warranty for SEO intial
// special bid on the PRODSTRUCTWARR for an RPQ feature
//
// Revision 1.2  2010/12/12 17:29:03  rick
// checking in changes for BH WARR data model
// change
//
// Revision 1.1.1.1  2007/06/05 02:09:03  jingb
// no message
//
// Revision 1.30  2007/05/15 18:42:32  rick
// changed to return true setting of catbrlifecycle_fc
//
// Revision 1.29  2007/03/05 13:38:37  rick
// 0308 deploy of fixes for WARR, processor and various other
// attributes not flowing correctly.
//
// Revision 1.28  2006/09/21 21:02:11  gregg
// more work for BasicRuleLoad
//
// Revision 1.27  2006/09/14 20:50:23  gregg
// null ptr check
//
// Revision 1.26  2006/08/02 20:22:12  gregg
// replaceAll last time.
//
// Revision 1.25  2006/08/02 19:59:58  gregg
// back to replaceFirst
//
// Revision 1.24  2006/08/02 19:44:15  gregg
// back to replaceAll in scanReplace.
// Today's java lesson learned: never expect Strings to be modified by referemce.
//
// Revision 1.23  2006/08/02 19:24:48  gregg
// scanReplace work
//
// Revision 1.22  2006/08/02 18:16:21  gregg
// using replcaeFirst for scanReplace for quick fix
//
// Revision 1.21  2006/07/31 22:49:54  gregg
// adding in actual replace logic for scan replace
//
// Revision 1.20  2006/07/31 22:31:19  gregg
// some debugs
//
// Revision 1.19  2006/07/31 22:09:28  gregg
// ScanReplace work
//
// Revision 1.18  2006/07/26 20:12:08  gregg
// reset m_bSubFailed = false; in building features
//
// Revision 1.17  2006/07/26 19:50:32  gregg
// using smc to derive featuredetails in getSubstitutionValue
//
// Revision 1.16  2006/07/17 19:46:57  gregg
// getSubstitutionValue:add path into the mix.
//
// Revision 1.15  2006/07/14 22:26:10  gregg
// let's try settng subFailed to false prior to check
//
// Revision 1.14  2006/07/14 22:10:42  gregg
// more debug ging
//
// Revision 1.13  2006/07/14 21:29:57  gregg
// removing some debugs
//
// Revision 1.12  2006/07/14 21:11:12  gregg
// more dumps
//
// Revision 1.11  2006/07/14 19:50:56  gregg
// debug stmts
//
// Revision 1.10  2006/07/13 21:50:17  gregg
// in getSubstitutionValue: for WWProducts we will try to use smc rather then wwattributecollection
// object to fish out entities/attributes.
//
// Revision 1.9  2006/05/09 21:23:35  gregg
// more debug
//
// Revision 1.8  2006/05/09 21:11:52  gregg
// debugging
//
// Revision 1.7  2006/05/09 20:40:18  gregg
// debuggg
//
// Revision 1.6  2006/05/09 20:33:28  gregg
// more substitution failed work
//
// Revision 1.5  2006/05/09 19:52:01  gregg
// more work on substitution failed methods
//
// Revision 1.4  2006/05/09 18:21:03  gregg
// fix for real this time.
//
// Revision 1.3  2006/05/09 18:08:32  gregg
// debug + npe fix
//
// Revision 1.2  2006/04/28 17:12:10  gregg
// check for substitutionFailed
//
// Revision 1.1.1.1  2006/03/30 17:36:27  gregg
// Moving catalog module from middleware to
// its own module.
//
// Revision 1.12  2005/12/15 22:09:47  gregg
// BasicRule building logic for WorldWideAttributes!!
//
// Revision 1.11  2005/11/29 18:31:40  gregg
// basic rule fix to include proper item entity type
//
// Revision 1.10  2005/11/23 18:17:30  gregg
// haha let's return the right value please
//
// Revision 1.9  2005/11/23 17:58:05  gregg
// more debug
//
// Revision 1.8  2005/11/23 17:24:13  gregg
// debugs stmts
//
// Revision 1.7  2005/11/22 22:53:43  gregg
// prepping for wayne spec cases
//
// Revision 1.6  2005/11/22 22:07:51  gregg
// attempt to fish values out of fdc for basicrule substitutions
//
// Revision 1.5  2005/11/22 21:41:22  gregg
// furthering basicrule substitutions
//
// Revision 1.4  2005/11/22 21:03:51  gregg
// update
//
// Revision 1.3  2005/11/21 18:25:59  gregg
// basicrule.featuredetail building step 1
//
// Revision 1.2  2005/11/16 22:31:01  gregg
// BasicRules
//
// Revision 1.1  2005/11/16 20:57:52  gregg
// initial load
//
//

package COM.ibm.eannounce.catalog;

import COM.ibm.opicmpdh.middleware.*;
import java.util.*;
import COM.ibm.eannounce.objects.*;



public class BasicRule {

    public static final String SUB_DELIM = "~";

    public static final String RULE_TYPE_ASIS = "ASIS";
    public static final String RULE_TYPE_SUBSTITUTION = "SUBSTITUTION";
    public static final String RULE_TYPE_SCANREPLACE = "SCANREPLACE";

    private String m_strEntityType = null;
    private String m_strItemType = null;
    private String m_strAttributeCode = null;
    private String m_strType = null;
    private String m_strTest = null;
    private String m_strFail = null;
    private String m_strPass = null;
    private String m_brEntityid = null;
    private String m_brLifeCycle_fc = null;
    private GeneralAreaMapItem m_gami = null;
    private boolean m_bSubFailed = false;
    private Hashtable m_hashScanReplace = null;
    private Vector m_vctScanReplace = null;

    public BasicRule(GeneralAreaMapItem _gami, String _strEntityType, String _strItemType, String _strAttributeCode/*,String _strType, String _strTest, String _strFail, String _strPass*/) {
		D.ebug(D.EBUG_SPEW,"BasicRule Constructer:new BasicRule");
		m_strEntityType = _strEntityType;
		m_strItemType = _strItemType;
		m_strAttributeCode = _strAttributeCode;
		//m_strType = _strType;
		//m_strTest = _strTest;
		//m_strFail = _strFail;
		//m_strPass = _strPass;
		m_gami = _gami;

	}

    public boolean isSubstitution() {
		if(getType() == null) {
			D.ebug(D.EBUG_WARN,"BasicRule.isSubstitution WARNING:getType is null for:" + getKey());
			return false;
		}
		return getType().toUpperCase().equals(RULE_TYPE_SUBSTITUTION);
	}

	public String getEntityType() {
		return m_strEntityType;
	}

	public String getItemType() {
		return m_strItemType;
	}

	public String getAttributeCode() {
		return m_strAttributeCode;
	}

	public String getType() {
		return m_strType;
	}

	public String getTest() {
		return m_strTest;
	}

	public String getFail() {
		return m_strFail;
	}

	public String getPass() {
		return m_strPass;
	}

        public String getBrEntityID() {
		return m_brEntityid;
	}

        public String getCatBrLifeCycle_fc() {
		return m_brLifeCycle_fc;
	}

	public void setType(String _s) {
	    m_strType = _s;
	}

	public void setTest(String _s) {
		m_strTest = _s;
	}

	public void setFail(String _s) {
		m_strFail = _s;
	}

	public void setPass(String _s) {
		m_strPass = _s;
	}

        public void setBrentityID(String _s) {
		m_brEntityid = _s;
	}

        public void setCatBrLifeCycle_fc(String _s) {
		m_brLifeCycle_fc = _s;
	}

	public String getKey() {
        return buildKey(m_gami,getEntityType(),getItemType(),getAttributeCode());
	}

/**
 * Remember strFail is actually the replace text for these guys.
 */
    protected final void putScanReplace(String _strPass, String _strFail) {
		if(m_vctScanReplace == null) {
			m_vctScanReplace = new Vector();
			m_hashScanReplace = new Hashtable();
		}
		D.ebug(D.EBUG_SPEW,"putScanReplace:\"" + _strPass + "\", \"" + _strFail + "\"");
	    m_vctScanReplace.addElement(_strPass);
	    m_hashScanReplace.put(_strPass,_strFail);
	}

    public final boolean hasScanReplace() {
		return (m_vctScanReplace != null);
	}

	protected boolean passTest(CatItem _obj) {
		// true for now, but this is where we will check
		return true;
	}

/**
 * Did our rule successfully yield a value?
 */
	protected boolean substitutionFailed(CatItem _obj) {
		D.ebug(D.EBUG_WARN,"Getting substitutionFailed for BasicRule [" + getKey() + "]:" + _obj.toString() + " is:" + m_bSubFailed);
		return m_bSubFailed;
	}

/**
 * _obj only needed for logging
 */
	private void setSubstitutionFailed(CatItem _obj) {
		D.ebug(D.EBUG_WARN,"Setting substitutionFailed for BasicRule [" + getKey() + "]:" + _obj.toString());
		m_bSubFailed = true;;
	}

//
// substitution processing methods...
//
	private String getPassVal(Catalog _cat, CatItem _obj) {
		D.ebug(D.EBUG_SPEW,"BasicRule.getPassVal  start for:" + getPass() + ".....");
		if(!isSubstitution()) {
			D.ebug(D.EBUG_SPEW, "BasicRule.getPassVal  !isSubstitution -> returning getPassVal() as is.");
			setSubstitutionFailed(_obj);
			return getPass();
		}
		if(!verifyPassVal(getPass())) {
			D.ebug(D.EBUG_WARN,"BasicRule.getPassVal  WARNING: could not parse pass val for:" + getKey());
			setSubstitutionFailed(_obj);
			return getPass();
		}
        String strFinal = new String(getPass());
        int iNumElements = StringUtil.numSubstitutionElements(SUB_DELIM,getPass());
        for(int i = 1; i <= iNumElements; i++) {
			String strSubElement = StringUtil.getSubstitutionElement(getPass(),SUB_DELIM,i);
            D.ebug(D.EBUG_SPEW,"BasicRule.getPassVal strSubElement [" + i + "/" + iNumElements + "] is \"" + strSubElement	+ "\"");
			String strReplacedVal = getSubstitutionValue(_cat,_obj,strSubElement);	// this'll be same thing as element w/out delims for now..
			String strReplace = SUB_DELIM + strSubElement + SUB_DELIM;
			D.ebug(D.EBUG_SPEW, "BasicRule.getPassVal strReplace:" + strReplace );
			D.ebug(D.EBUG_SPEW, "BasicRule.getPassVal strReplacedVal:" + strReplacedVal);
			D.ebug(D.EBUG_SPEW, "BasicRule.getPassVal strFinal (before):" + strFinal);
			strFinal = StringUtil.replaceSubstitutionElement(strFinal,strReplace,strReplacedVal);
		    D.ebug(D.EBUG_SPEW, "BasicRule.getPassVal strFinal (after):" + strFinal);
		}
        D.ebug(D.EBUG_SPEW,"BasicRule.getPassVal ....End");
		return strFinal;
	}

    private String getSubstitutionValue(Catalog _cat, CatItem _obj, String _s) {
		if(_s.indexOf(".") < 0) {
			D.ebug(D.EBUG_SPEW, "BasicRule getSubstitutionValue, invalid token (no \".\"). returning o.g. string");
			setSubstitutionFailed(_obj);
			return _s;
		}
		StringTokenizer st = new StringTokenizer(_s,".");
		String strEntityType = st.nextToken();
		String strAttributeCode = st.nextToken();
		String strPath = null;
		if(strEntityType.indexOf(":") > -1) {
        	strPath = strEntityType.substring(0,strEntityType.lastIndexOf(":"));
        	strEntityType = strEntityType.substring(strEntityType.lastIndexOf(":")+1,strEntityType.length());
		}

        D.ebug(D.EBUG_SPEW, "BasicRule.getSubstitutionValue- _s:\"" + _s + "\"");
        D.ebug(D.EBUG_SPEW, "BasicRule.getSubstitutionValue- strEntityType:\"" + strEntityType + "\"");
        D.ebug(D.EBUG_SPEW, "BasicRule.getSubstitutionValue- strAttributeCode:\"" + strAttributeCode + "\"");
        D.ebug(D.EBUG_SPEW, "BasicRule.getSubstitutionValue- strPath:\"" + strPath + "\"");

		if(_obj instanceof Feature) {
			Feature f = (Feature)_obj;

			/*
			// let's look through details and find a matching entityitem.
			FeatureDetailCollection fdc = f.getFeatureDetailCollection();
			Iterator it = fdc.values().iterator();
        	while (it.hasNext()) {
        	    FeatureDetail fd = (FeatureDetail) it.next();
        	    if(fd.getItemEntityType().equals(strEntityType)) {
					D.ebug(D.EBUG_SPEW, "BasicRule (Feature) getSubstitutionValue: found entity -> fishing out att val for " + strEntityType + "." + strAttributeCode + "...");
					EntityItem ei = Catalog.getEntityItem(_cat, strEntityType, fd.getItemEntityID());
					String[] saVals = CatDBTableRecord.getAttributeValue(ei,strAttributeCode);
					if(saVals[0] != null) {
						D.ebug(D.EBUG_SPEW, "BasicRule (Feature) getSubstitutionValue -> value found for " + strEntityType + "." + strAttributeCode + ":" + saVals[0]);
						return saVals[0];
					} else {
						D.ebug(D.EBUG_SPEW, "BasicRule (Feature) getSubstitutionValue -> no attributevalue found for " + strEntityType + "." + strAttributeCode);
					}
				}
			}
			*/
            for (int i = 0; i < f.getSmc().getCount(); i++) {
                SyncMapItem smi = f.getSmc().get(i);

                //D.ebug(D.EBUG_SPEW,"BasicRule.getSubstitutionValue smi.childtype="+smi.getChildType() + ",strEntityType=" + strEntityType);
                //D.ebug(D.EBUG_SPEW,"BasicRule.getSubstitutionValue smi dump=" + smi.toString());


        	    if(smi.getChildType().equals(strEntityType) && smi.getChildTran().equals("ON")) {
					if(strPath != null && !smi.getChildPath().equals(strPath)) {
						D.ebug(D.EBUG_DETAIL, "BasicRule.getSubstitutionValue:path not matching. getChildPath:" + smi.getChildPath() + ",strPath:" + strPath);
					    continue;
					}
					EntityItem ei = Catalog.getEntityItem(_cat, strEntityType, smi.getChildID());
					D.ebug(D.EBUG_DETAIL, "BasicRule.getSubstitutionValue: " + ei.getKey() + " found entity -> fishing out att val for " + strEntityType + "." + strAttributeCode + "...");
					String[] saVals = CatDBTableRecord.getAttributeValue(ei,strAttributeCode);
					if(saVals[0] != null) {
						D.ebug(D.EBUG_DETAIL, "BasicRule (WorldWideAttribute) getSubstitutionValue -> value found for " + strEntityType + "." + strAttributeCode + ":" + saVals[0]);
						return saVals[0];
					} else {
						D.ebug(D.EBUG_DETAIL, "BasicRule (WorldWideAttribute) getSubstitutionValue -> no attributevalue found for " + strEntityType + "." + strAttributeCode);
					}
				}
			}


        } else if(_obj instanceof WorldWideProduct) {
			WorldWideProduct wwp = (WorldWideProduct)_obj;
			WorldWideProductId wwpid = wwp.getWorldWideProductId();
			String strWWPentitytype = wwpid.getEntityType();
			int strWWPentityid = wwpid.getEntityID();
			boolean WWSEOWARR = false;
			boolean WWSEOoption = false;
			boolean WWSEOInitial = false;
			boolean WWSEOSpecbid = false;
			// The WARR attributes have special processing for a WWSEO.
			// Set some booleans to see later if we have an WWSEO and whether it is an
			// option or not and whether it is a special bid or not.
			D.ebug(D.EBUG_SPEW, "RQK - basicrule :" + strEntityType + " " + strWWPentitytype + ":" + strWWPentityid);
			if (strEntityType.equals("WARR") && strWWPentitytype.equals("WWSEO")) {
				WWSEOWARR = true;
				EntityItem wwpei = Catalog.getEntityItem(_cat, strWWPentitytype, strWWPentityid);
				String[] SEOOrdercodeVals = CatDBTableRecord.getAttributeValue(wwpei,"SEOORDERCODE");
				if (SEOOrdercodeVals != null && SEOOrdercodeVals.length > 1) {
					if (SEOOrdercodeVals[1].equals("20")) WWSEOoption = true;
					if (SEOOrdercodeVals[1].equals("10")) WWSEOInitial = true;
				}
				String[] SEOSpecbidVals = CatDBTableRecord.getAttributeValue(wwpei,"SPECBID");
				if (SEOSpecbidVals != null && SEOSpecbidVals.length > 1) {
					if (SEOSpecbidVals[1].equals("11458")) WWSEOSpecbid = true;
				}
				D.ebug(D.EBUG_SPEW, "RQK - we have a WARR and WWSEO with WWSEOoption:" + WWSEOoption +
						" WWSEOInitial:" + WWSEOInitial + " WWSEOSpecbid:" + WWSEOSpecbid);
			}


			//WorldWideAttributeCollection wwac = wwp.getWorldWideAttributeCollection();
			//Iterator it1 = wwac.values().iterator();

        	//
        	// GAB 071306 - WWAttributes doesn't necessarily include all the attributes.
        	//              We're gonna need in our basicrules. Where to get this? Smi?
        	/*
        	while (it1.hasNext()) {
        	    WorldWideAttribute wwa = (WorldWideAttribute)it1.next();
                WorldWideAttributeId wwaid = wwa.getWorldWideAttributeId();
                String strAttEntityType = wwaid.getAttEntityType();
        	    if(strAttEntityType.equals(strEntityType)) {
					D.ebug(D.EBUG_DETAIL, "BasicRule (WorldWideAttribute) getSubstitutionValue: found entity -> fishing out att val for " + strEntityType + "." + strAttributeCode + "...");
					EntityItem ei = Catalog.getEntityItem(_cat, strEntityType, wwaid.getAttEntityID());
					String[] saVals = CatDBTableRecord.getAttributeValue(ei,strAttributeCode);
					if(saVals[0] != null) {
						D.ebug(D.EBUG_DETAIL, "BasicRule (WorldWideAttribute) getSubstitutionValue -> value found for " + strEntityType + "." + strAttributeCode + ":" + saVals[0]);
						return saVals[0];
					} else {
						D.ebug(D.EBUG_DETAIL, "BasicRule (WorldWideAttribute) getSubstitutionValue -> no attributevalue found for " + strEntityType + "." + strAttributeCode);
					}
				}
			}
			*/
			// If we have a WWSEO product and are looking for a WARR entity then we will
			// override the path.
			// If the WWSEO is not an option and not a special bid
			// then we only need to look for a WARR on the MODEL.
			// If the WWSEO is an option, then we need to first look for the WARR on a
			// WWSEOPRODSTRUCT. If it is not there then we need to look for one on the MODEL.
			//
			// Here is the logic which is used -
			//
			// if WWSEO.SEOORDERCODE = "Initial" (10)
			// then
			// if WWSEO.SPECBID = "Yes"
			// then
			// if FEATURE for the PRODSTRUCT has
			// HWFCCAT = "RPQ" (227) and
			// HWFCSUBCAT in  { "Warranty" (358) , "Warranty Extension" (377) }
			// then use WWSEOPRODSTRUCT-d: PRODSTRUCTWARR-d: WARR if it exists or
			//  use the MODELWWSEO-u: MODELWARR-d: WARR
			// else
			// use the MODELWWSEO-u: MODELWARR-d: WARR
			// end if
			// end if
            //
            //
			// if WWSEO.SEOORDERCODE = "MES" (20)
			// then
			// use WWSEOPRODSTRUCT-d: PRODSTRUCTWARR-d: WARR if it exists or
			// use the MODELWWSEO-u: MODELWARR-d: WARR
			// end if

			// breaking out looking for WARR for an Initial special bid WWSEO
			if (!(WWSEOWARR && WWSEOInitial && WWSEOSpecbid)) {
			D.ebug(D.EBUG_SPEW, "RQK - basicrule : not WARR and Initial and specbid");
			int loopcount = 1;
			if (WWSEOWARR && !WWSEOoption) {
				D.ebug(D.EBUG_SPEW, "RQK - basicrule : WWSEO WARR but not option so getting WARR from model with path MODELWWSEO:MODELWARR");
				strPath = "MODELWWSEO:MODELWARR";
			} // end if
			if (WWSEOWARR && WWSEOoption) {
				D.ebug(D.EBUG_SPEW, "RQK - basicrule : WWSEO WARR and option");
				loopcount = 2;
			} // end if

			for (int icycle = 0; icycle<loopcount; icycle++) {
				if (WWSEOWARR && WWSEOoption && icycle==0) {
					D.ebug(D.EBUG_SPEW, "RQK - basicrule : WWSEO WARR and option so looking for WARR on PRODSTRUCT with path WWSEOPRODSTRUCT:PRODSTRUCTWARR first");
					strPath = "WWSEOPRODSTRUCT:PRODSTRUCTWARR";
				}
				if (WWSEOWARR && WWSEOoption && icycle==1) {
					D.ebug(D.EBUG_SPEW, "RQK - basicrule : WWSEO WARR and option, now going to get WARR from model with path MODELWWSEO:MODELWARR");
					strPath = "MODELWWSEO:MODELWARR";
				}

            for (int i = 0; i < wwp.getSmc().getCount(); i++) {
                SyncMapItem smi = wwp.getSmc().get(i);

        	    if(smi.getChildType().equals(strEntityType) && smi.getChildTran().equals("ON")) {
					if(strPath != null && !smi.getChildPath().equals(strPath)) {
						D.ebug(D.EBUG_DETAIL, "BasicRule.getSubstitutionValue:path not matching. getChildPath:" + smi.getChildPath() + ",strPath:" + strPath);
					    continue;
					} // end if
                    String strChildPath = smi.getChildPath();
                    if (strChildPath.length() > 21) {
                    	String strChildPathsub = strChildPath.substring(0,22);
                    	if (strChildPathsub.equals("MODELWWSEO:PRODSTRUCT:")) {
                    		D.ebug(D.EBUG_SPEW, "Skipping MODEL PRODSTRUCT ");
                    		continue;
                    	} // end if
					} // end if

					EntityItem ei = Catalog.getEntityItem(_cat, strEntityType, smi.getChildID());
					D.ebug(D.EBUG_DETAIL, "BasicRule.getSubstitutionValue: " + ei.getKey() + " found entity -> fishing out att val for " + strEntityType + "." + strAttributeCode + "...");
					String[] saVals = CatDBTableRecord.getAttributeValue(ei,strAttributeCode);
					if(saVals[0] != null) {
						D.ebug(D.EBUG_DETAIL, "BasicRule (WorldWideAttribute) getSubstitutionValue -> value found for " + strEntityType + "." + strAttributeCode + ":" + saVals[0]);
						return saVals[0];
					} else {
						D.ebug(D.EBUG_DETAIL, "BasicRule (WorldWideAttribute) getSubstitutionValue -> no attributevalue found for " + strEntityType + "." + strAttributeCode);
					} // end else
				} // end if
			} // end for loop through syncmap
			} // end for icycle
			} // end if not WARR and initial and special bid
			else {
				D.ebug(D.EBUG_SPEW, "RQK-basicrule : WWSEO WARR, initial and specbid");
				D.ebug(D.EBUG_SPEW, "RQK-basicrule : starting loop to look for entity1type of feature and childpath of WWSEOPRODSTRUCT:PRODSTUCT");
				for (int i = 0; i < wwp.getSmc().getCount(); i++) {
	                SyncMapItem smi = wwp.getSmc().get(i);
	                // We need to check the FEATUREs which are linked to this WWSEO through WWSEOPRODSTRUCT
	                // the child is the prodstruct
	                if (!(smi.getEntity1Type().equals("FEATURE") && smi.getChildTran().equals("ON")
	        	    && smi.getChildPath().equals("WWSEOPRODSTRUCT:PRODSTRUCT"))) {
					continue;
						} // end if
	        	    // Once we have found a feature then we need to check the HWFCCAT and HWFCSUBCAT values
	        	    D.ebug(D.EBUG_SPEW, "RQK - basicrule : found feature " + smi.getEntity1ID());
	        	    EntityItem feat = Catalog.getEntityItem(_cat, "FEATURE", smi.getEntity1ID());
					String[] HWFCCATVals = CatDBTableRecord.getAttributeValue(feat,"HWFCCAT");
					String[] HWFCSUBCATVals = CatDBTableRecord.getAttributeValue(feat,"HWFCSUBCAT");
					D.ebug(D.EBUG_SPEW, "RQK-basicrule : check feature hwfccat and hwfcsubcat values");
					if (HWFCCATVals != null && HWFCCATVals.length > 1 &&
						HWFCCATVals[1].equals("227") &&
						HWFCSUBCATVals != null && HWFCSUBCATVals.length > 1 &&
						(HWFCSUBCATVals[1].equals("358") || HWFCSUBCATVals[1].equals("377")) ) {
					// Now a matching FEATURE has been found, so we need to find the WARR related
					// to the PRODSTRUCT
					D.ebug(D.EBUG_SPEW, "RQK-basicrule : feature with hwfccat: " +HWFCCATVals[1] + " hwfcsubcat " + HWFCSUBCATVals[1]);
					D.ebug(D.EBUG_SPEW, "RQK-basicrule : start loop to find entity1type of PRODSTRUCT " + smi.getChildType() + ":" + smi.getChildID() + " and entity2type of WARR");
					for (int j = 0; j < wwp.getSmc().getCount(); j++) {
						SyncMapItem smi2 = wwp.getSmc().get(j);
						if (smi2.getEntity1Type().equals("PRODSTRUCT") &&
							smi2.getEntity1ID() == smi.getChildID() &&
							smi2.getEntity2Type().equals("WARR") &&
							smi2.getChildTran().equals("ON")) {
							D.ebug(D.EBUG_SPEW, "RQK-basicrule : found smi with entity1type of PRODSTRUCT:" + smi2.getEntity1ID() + " entity2type of WARR:" + smi2.getEntity2ID());
							EntityItem ei = Catalog.getEntityItem(_cat, "WARR", smi2.getEntity2ID());
							D.ebug(D.EBUG_SPEW, "BasicRule.getSubstitutionValue: " + ei.getKey() + " found entity -> fishing out att val for " + strEntityType + "." + strAttributeCode + "...");
							String[] saVals = CatDBTableRecord.getAttributeValue(ei,strAttributeCode);
							if(saVals[0] != null) {
								D.ebug(D.EBUG_SPEW, "BasicRule (WorldWideAttribute) getSubstitutionValue -> value found for " + strEntityType + "." + strAttributeCode + ":" + saVals[0]);
								return saVals[0];
							} else {
								D.ebug(D.EBUG_SPEW, "BasicRule (WorldWideAttribute) getSubstitutionValue -> no attributevalue found for " + strEntityType + "." + strAttributeCode);
							} // end else
						} // end if
						else continue;
					} // end for
					} // end if HWFCCAT ....


					else continue;


					} // end for
				// if we are still here, then we need to fallback to MODELWARR
				D.ebug(D.EBUG_SPEW, "RQK-basicrule : we have not found a warr for the WWSEO initial specialbid, so now we will get from model with path MODELWWSEO:MODELWARR");
				for (int k = 0; k < wwp.getSmc().getCount(); k++) {
	                SyncMapItem smi3 = wwp.getSmc().get(k);

	                //D.ebug(D.EBUG_SPEW,"BasicRule.getSubstitutionValue smi.childtype="+smi.getChildType() + ",strEntityType=" + strEntityType);
	                //D.ebug(D.EBUG_SPEW,"BasicRule.getSubstitutionValue smi dump=" + smi.toString());


	        	    if (smi3.getChildType().equals("WARR") && smi3.getChildTran().equals("ON") &&
	        	    	smi3.getChildPath().equals("MODELWWSEO:MODELWARR")) {

						EntityItem ei = Catalog.getEntityItem(_cat, strEntityType, smi3.getChildID());
						D.ebug(D.EBUG_SPEW, "BasicRule.getSubstitutionValue: " + ei.getKey() + " found entity -> fishing out att val for " + strEntityType + "." + strAttributeCode + "...");
						String[] saVals = CatDBTableRecord.getAttributeValue(ei,strAttributeCode);
						if(saVals[0] != null) {
							D.ebug(D.EBUG_SPEW, "BasicRule (WorldWideAttribute) getSubstitutionValue -> value found for " + strEntityType + "." + strAttributeCode + ":" + saVals[0]);
							return saVals[0];
						} else {
							D.ebug(D.EBUG_SPEW, "BasicRule (WorldWideAttribute) getSubstitutionValue -> no attributevalue found for " + strEntityType + "." + strAttributeCode);
						} // end else
					} // end if
				} // end for
				} // end else

		} // end else if instance of WWProduct
		setSubstitutionFailed(_obj);
        return _s;
	}

    private boolean verifyPassVal(String _s) {
		if(_s == null) {
		    D.ebug(this, D.EBUG_WARN,"BasicRule.getPassVal: null value passed for:" + getKey());
		    return false;
		}
        return (StringUtil.numSubstitutionElements("~",_s) > 0);
	}
//
// ... end substitution processing methods
//


/**
 * using gami's in the key might be overkill because its limited by area already in collection, but at least we're covered.
 */
	protected static String buildKey(GeneralAreaMapItem _gami, String _strEntityType, String _strItemType, String _strAttributeCode) {
		String s1 = _gami.getCountry();
		String s2 = _gami.getLanguage();
        String s3 = String.valueOf(_gami.getNLSID());
        String s4 = _strEntityType;
        String s5 = _strItemType;
        String s6 = _strAttributeCode;
		return s1 +":"+ s2 +":"+ s3 +":"+ s4 +":"+ s5 + ":" + s6;
	}

	protected FeatureDetail buildFeatureDetail(Catalog _cat, Feature _f) {
		String strFeatEntityType = getEntityType();
		D.ebug(D.EBUG_SPEW, "getentitytype :" + getEntityType());
		int iFeatEntityID = _f.getFeatEntityID();
		D.ebug(D.EBUG_SPEW, "getfeatentityid :" + _f.getFeatEntityID());
		String strItemEntityType = getItemType();
		D.ebug(D.EBUG_SPEW, "getitemtype :" + getItemType());
		int iItemEntityID = -9; // ??? - probably get this from the featuredetail itementityid
		String strAttCode = getAttributeCode();
		D.ebug(D.EBUG_SPEW, "getattributecode :" + getAttributeCode());
		FeatureDetailCollectionId fdcid = (FeatureDetailCollectionId)_f.getFeatureDetailCollection().getId();
	    D.ebug(D.EBUG_SPEW, "BasicRule build FeatureDetailId:" + strFeatEntityType + ":" + iFeatEntityID + ":" + strItemEntityType + ":" + iItemEntityID + ":" + strAttCode);
		FeatureDetailId fdid = new FeatureDetailId(strFeatEntityType, iFeatEntityID, strItemEntityType, iItemEntityID, _f.getId().getGami(), fdcid);
		FeatureDetail fd = new FeatureDetail(fdid);
//
        DetailFragmentId dfid = new DetailFragmentId(getAttributeCode(), m_gami);
        DetailFragment df = new DetailFragment(dfid);
        df.setExtAttributeCode(getAttributeCode());

        m_bSubFailed = false;

        String strPass = getPassVal(_cat,_f);
        if(hasScanReplace()) {
			strPass = doScanReplace(strPass);
		}
        df.setAttributeValue(strPass);
        String strNow = null;
        try {
        	strNow = _cat.getCatalogDatabase().getDates().getNow();
		} catch(Exception exc) {}
        df.setValFrom(strNow);
        df.setActive(true);
        df.setDerived(true);
        fd.put(df);
//
		return fd;
	}

	protected WorldWideAttribute buildWorldWideAttribute(Catalog _cat, WorldWideProduct _wwp) {
		String strProdEntityType = getEntityType();
		int iProdEntityID = _wwp.getIntVal(WorldWideProduct.WWENTITYID);
		String strItemEntityType = getItemType();
		int iItemEntityID = -9; // ??? - probably get this from the featuredetail itementityid
		String strAttCode = getAttributeCode();
		WorldWideAttributeCollectionId wwacid = (WorldWideAttributeCollectionId)_wwp.getWorldWideAttributeCollection().getId();
	    D.ebug(D.EBUG_SPEW, "BasicRule build WorldWideAttributeId:" + strProdEntityType + ":" + iProdEntityID + ":" + strItemEntityType + ":" + iItemEntityID + ":" + strAttCode);
		WorldWideAttributeId wwaid = new WorldWideAttributeId(_wwp.getWorldWideProductId(), strItemEntityType, iItemEntityID, m_gami);
        WorldWideAttribute wwa = new WorldWideAttribute(wwaid);
//
        AttributeFragmentId afid = new AttributeFragmentId(wwaid, strAttCode, m_gami);
        AttributeFragment af = new AttributeFragment(afid);

        m_bSubFailed = false;

        String strPass = getPassVal(_cat,_wwp);
        if(hasScanReplace()) {
		    strPass = doScanReplace(strPass);
		}
        //if (strPriorAttType.equals("M")) {
        //    af.setFlagCode("Multi");
        //} else {
        //    af.setFlagCode(strPriorFlagCode);
        //}
        //af.setAttributeType(strPriorAttType);
        af.setAttributeValue(strPass);
        String strNow = null;
        try {
        	strNow = _cat.getCatalogDatabase().getDates().getNow();
		} catch(Exception exc) {}
        af.setValFrom(strNow);
        af.setActive(true);
        wwa.put(af);
//
		return wwa;
	}

	private final String doScanReplace(String _s) {
		for(int i = 0; i < m_vctScanReplace.size(); i++) {
			String strKey = (String)m_vctScanReplace.elementAt(i);
			String strReplace = (String)m_hashScanReplace.get(strKey);
			D.ebug(D.EBUG_SPEW,"doScanReplace:looking at \"" + strKey + "\" in \"" + _s + "\", to replace with \"" + strReplace + "\"");
		    if(_s.indexOf(strKey) > -1) {
				D.ebug(D.EBUG_SPEW,"doScanReplace:found key, now replacing...");
				_s = StringUtil.replaceAll(_s,strKey,strReplace);
				D.ebug(D.EBUG_SPEW,"doScanReplace:new string is:\"" + _s + "\"");
			}
		}
		return _s;
	}

/**
 * Here is where we report the state in terms of BasicRule Load.
 */
	protected final boolean isQueued() {
                if (m_brLifeCycle_fc != null && m_brLifeCycle_fc.equals("110"))
                 {
                  D.ebug(D.EBUG_WARN,"isQueued returning true" + m_brLifeCycle_fc + "\"");
                  return true;
                 }
                else
                 {
                  D.ebug(D.EBUG_WARN,"isQueued returning false" + m_brLifeCycle_fc + "\"");
                  return false;
                 }
	}

}
