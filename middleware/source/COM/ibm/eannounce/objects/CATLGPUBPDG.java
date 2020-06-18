// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: CATLGPUBPDG.java,v $
// Revision 1.125  2008/03/06 19:47:41  wendy
// Undo some RSA changes
//
// Revision 1.124  2008/03/04 15:29:53  wendy
// Added more debug output
//
// Revision 1.123  2008/02/01 22:10:06  wendy
// Cleanup RSA warnings
//
// Revision 1.122  2008/01/07 13:53:41  wendy
// move inactive check after search, before extract to improve performance in removecatlgpub()
//
// Revision 1.121  2008/01/04 19:54:38  wendy
// Added more debug info
//
// Revision 1.120  2008/01/04 18:09:44  wendy
// MN33416775 handling of salesstatus and split into derived classes
//
// Revision 1.119  2007/08/15 19:01:19  wendy
// Added more debug output
//
// Revision 1.118  2007/05/04 13:31:22  wendy
// RQ022507373 and MN31580435 updates
//
// Revision 1.117  2006/11/28 23:29:04  joan
// fixes
//
// Revision 1.116  2006/11/28 20:34:29  joan
// fixes

package COM.ibm.eannounce.objects;

import java.sql.SQLException;
import java.util.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.OPICMList;

/**
 * CATLGPUBPDG
 *
 */
public abstract class CATLGPUBPDG extends PDGActionItem {
    static final long serialVersionUID = 20011106L;

    protected static final String ATT_MACHTYPE = "MACHTYPEATR";
    protected static final String ATT_MODEL = "MODELATR";
    protected static final String ATT_SEOID = "SEOID";

    protected static final String ATT_CATMACHTYPE = "CATMACHTYPE";
    protected static final String ATT_CATMODEL = "CATMODEL";
    protected static final String ATT_CATSEOID = "CATSEOID";
    protected static final String ATT_CATAUDIENCE = "CATAUDIENCE";
    protected static final String ATT_CATACTIVE = "CATACTIVE";

    protected static final String ATT_STATUS = "STATUS";
    protected static final String ATT_AUDIEN = "AUDIEN";
    protected static final String ATT_ANNDATE = "ANNDATE";
    protected static final String ATT_WITHDRAWDATE = "WITHDRAWDATE";
    protected static final String ATT_COFCAT = "COFCAT";
    protected static final String ATT_COFGRP = "COFGRP";

    protected static final String ATT_SYSIDUNIT = "SYSIDUNIT";
    protected static final String ATT_CATNEWOFF = "CATNEWOFF";
    protected static final String ATT_CATLGMKTGDESC = "CATLGMKTGDESC";
    protected static final String ATT_PRCINDC = "PRCINDC"; //RQ022507373 and MN31580435

    protected static final String ATT_CATDEFAULTSORT = "CATDEFAULTSORT";
    protected static final String ATT_CATOFFMKTDESC = "CATOFFMKTDESC";
    protected static final String ATT_PF = "PUBFROM";
    protected static final String ATT_PT = "PUBTO";
    protected static final String ATT_CATOFFSEQNUM = "CATOFFSEQNUM";
    protected static final String ATT_CATWORKFLOW = "CATWORKFLOW";


    protected static final String ATT_OFFCOUNTRY  = "OFFCOUNTRY";
    protected static final String ATT_COUNTRYLIST = "COUNTRYLIST";
    protected static final String ATT_GENAREACODE = "GENAREACODE";
    protected static final String ATT_GENAREANAME = "GENAREANAME";
    protected static final String ATT_PROJCDNAM = "PROJCDNAM";
    protected static final String ATT_CATOFFTYPE = "CATOFFTYPE";
    protected static final String ATT_EFFDATE = "EFFECTIVEDATE";

    private Hashtable m_hshGENAREACODE = new Hashtable();
    private Hashtable m_hshMtrnCodes = new Hashtable();

    private OPICMList m_audList = new OPICMList();
    private OPICMList m_cataudList = new OPICMList();
    protected Hashtable m_processedList = new Hashtable();

    public String m_strEntityType = null;
    protected boolean m_bUseDateRange = false;
    public void setUseDateRange(boolean b) { m_bUseDateRange = b; }

    protected String m_strStartTime = null;
    public void setStartTime(String t) { m_strStartTime = t; }
    protected String m_strEndTime = null;
    public void setEndTime(String t) { m_strEndTime = t; }
    protected int m_iChunk = 100;
    public void setChunkSize(int t) { m_iChunk = t; }

    public void setScanAll(boolean b) { m_bScanAll = b; }
    private boolean m_bScanAll = true;

    protected String m_strUpdatedBy = "CATLGPUBPDG" + getRevision();
    private EntityItem[] m_aStartEI = null;
    private String m_domains ="";
    protected String strNow = "";
    protected String strCurrentDate = "";
    private boolean doSearches = false; // if true then if no catlgpub are found in extract then do extra search for them

    public void setDoSearch(boolean b) {doSearches = b;} // used in abr
    public boolean doSearch() { return doSearches;}

    /**
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: CATLGPUBPDG.java,v 1.125 2008/03/06 19:47:41 wendy Exp $";
    }

    /**
     * CATLGPUBPDG
     *
     * @param _mf
     * @param _ai
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     *  @author David Bigelow
     */
    protected CATLGPUBPDG(EANMetaFoundation _mf, CATLGPUBPDG _ai) throws MiddlewareRequestException {
        super(_mf, _ai);
    }

    /**
     * constructor from abr
     * @param _emf
     * @param _db
     * @param _prof
     * @param _domains
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     */
    protected CATLGPUBPDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _domains) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db, _prof, "CATLGPUBPDG");
        m_domains=_domains;
    }

    /**
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("CATLGPUBPDG:" + getKey() + ":desc:" + getLongDescription());
        strbResult.append(":purpose:" + getPurpose());
        strbResult.append(":entitytype:" + getEntityType() + "\n");
        return strbResult.toString();
    }

    /**
     * getPurpose
     *
     * @see COM.ibm.eannounce.objects.EANActionItem#getPurpose()
     */
    public String getPurpose() {
        return "CATLGPUBPDG";
    }

   /*************************************************************************************
    * Check the PDHDOMAIN
    * The ABR will consider data based on PDHDOMAIN.
    * Consider	Code	LongDescription
    * No	0010	iSeries
    * No	0020	pSeries
    * No	0030	zSeries
    * No	0040	eServer
    * Yes	0050	xSeries
    * No	0060	Automated Tape Library
    * No	0070	Disk
    * No	0080	Virtualization Family
    * Yes	0090	SAN
    * No	0100	NAS
    * No	0110	Storage Media
    * No	0120	Tape
    * No	0130	Optical
    * No	0140	CCIN
    * Yes	0150	CSP
    * No	0160	High End Disk
    * No	0170	Disk Products
    * No	0180	Function Authorizations
    * Yes	0190	Midrange Disk
    * No	0200	Network Attached Storage
    * Yes	0210	SANB
    * No	0220	High End Tape
    * Yes	0230	Mid Range Tape
    * Yes	0240	Complementary Stor Products
    * No	0250	Other Storage
    * No	0260	Optics
    * No	0270	Tape Products
    * No	0280	Tape Media Midrange
    * No	0290	Tape Media High End
    * No	0300	SANC
    * Yes	0310	SANM
    * No	0320	SANN
    * Yes	0330	Mid Range Tape 2
    * Yes	0340	Midrange Disk 2
    * No	0350	Disk Products 2
    * Yes	0360	Complementary Stor Products 2
    * No	0370	Other
    * No	0380	Global Services
    * Yes	0390	Converged Products
    * No	SAPL	SAPL
    * No	SG	SG
    *@param item    EntityItem
    *@return boolean set to true if matches one of these domains
    */
    protected boolean domainNeedsCatlgpub(EntityItem item)
    {
		boolean bdomainInList = true;
		if (m_domains.length()==0 || m_domains.equals("all")){
			bdomainInList = true;
		}else{
	        Set testSet = new HashSet();
			StringTokenizer st1 = new StringTokenizer(m_domains,",");
			while (st1.hasMoreTokens()) {
		        testSet.add(st1.nextToken());
			}
	        bdomainInList = contains(item, "PDHDOMAIN", testSet);
	        testSet.clear();
		}

        if (!bdomainInList){
            addDebug(item.getKey()+" PDHDOMAIN did not include "+m_domains+", CATLGPUB will not be generated ["+
                m_utility.getAttrValue(item, "PDHDOMAIN")+"]");
        }
        return bdomainInList;
    }

    /**
    * get all catlgpub for this entity with this offcountry
    *@param rootItem  EntityItem parent for CATLGPUB
    *@param relator  String relator name from parent to CATLGPUB
    *@param offctry  String offering country on abr root CATLGCNTRY item
    *@return Vector of EntityItems
    */
    protected Vector getCatlgPubForCtry(EntityItem rootItem, String relator, String offctry)
    {
		Vector ctryVct = new Vector();

		Vector catlgpubVct = getAllLinkedEntities(rootItem, relator, "CATLGPUB");
		for (int j=0; j < catlgpubVct.size(); j++) {
			EntityItem catlgpubitem = (EntityItem)catlgpubVct.elementAt(j);
			String strOFFCOUNTRY = m_utility.getAttrValue(catlgpubitem, ATT_OFFCOUNTRY);
			if (offctry.equals(strOFFCOUNTRY)){
				ctryVct.add(catlgpubitem);
			}
		}
		catlgpubVct.clear();

		return ctryVct;
	}

    /**
    * get all catlgpub for this entity, this country and by audience
    * deactivate those with duplicate audience values or a cataudience with more than one flag set
    *@param _db    Database
    *@param _prof  Profile
    *@param catlgpubVct  Vector  CATLGPUB EntityItems with the CATLGCNTRY.OFFCOUNTRY
    *@return Hashtable with audience as key, catlgpub item as value
    */
    protected Hashtable getCatlgPubForCtryByAudience(Database _db, Profile _prof,
    	Vector catlgpubVct)
    throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException
    {
		Hashtable audTbl = new Hashtable();
		for (int j=0; j < catlgpubVct.size(); j++) {
			EntityItem catlgpubitem = (EntityItem)catlgpubVct.elementAt(j);
			verifyAudience(_db, _prof, catlgpubitem, audTbl);
		}

		return audTbl;
	}

    /**
    * check catlgpub audience for this entity
    * deactivate those with duplicate audience values or a cataudience with more than one flag set
    *@param _db    Database
    *@param _prof  Profile
    *@param catlgpubitem  EntityItem CATLGPUB
    *@param audTbl Hashtable with audience as key, catlgpub item as value
    */
    protected void verifyAudience(Database _db, Profile _prof,
    	 EntityItem catlgpubitem, Hashtable audTbl)
    throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException
	{
		String strTraceBase = "verifyAudience ";
		String strCATAUDIENCE = m_utility.getAttrValue(catlgpubitem, ATT_CATAUDIENCE);
		// check whether the catlgpub has multiple cataudience values
		// if so, this catlgpub is not good, need to remove it.
		if (strCATAUDIENCE.indexOf(",") >=0) {
			addDebug(_db,D.EBUG_SPEW,strTraceBase," Deactivating "+catlgpubitem.getKey()+" because it has multiple audience values "+strCATAUDIENCE);
			deactivateCatlgpub(_db, _prof, catlgpubitem);
			return;
		}
		EntityItem previtem = (EntityItem)audTbl.get(strCATAUDIENCE);
		if (previtem ==null){
			audTbl.put(strCATAUDIENCE,catlgpubitem); // should only be one, but look for errors
		}else{ //dupe catlgpub found for this audience
			addDebug(_db,D.EBUG_SPEW,strTraceBase," Deactivating "+catlgpubitem.getKey()+" because it was a dupe for audience "+
				strCATAUDIENCE+" leaving "+previtem.getKey());
			deactivateCatlgpub(_db, _prof, catlgpubitem);
		}
	}

	/****
	* Use the appropriate association {LSEOGAA from LSEO.COUNTRYLIST,
	* 	LSEOBUNDLEGAA from LSEOBUNDLE.COUNTRYLIST,
	*	for MODEL - use CATLGCNTRYGAA from CATLGCNTRY.OFFCOUNTRY) to
	* GENERALAREA.GENAREANAME which gives you GENERALAREA.GENAREACODE which is really 'country code'.
	*
	* Use 'country code' to find SALES_STATUS.SALESORG via SALESORG_COUNTRY.COUNTRYCODE.
	* Not sure what DISTRIBUTIONCHANNEL is yet. Assume that SALESORG does not vary by DISTRIBUTIONCHANNEL
	* for a COUNTRY.
	* Default MATERIALSTATUS when one can not be found in SALES_STATUS
	*	a.	Test Cycle
	*	During the early test cycle, the default MATERIALSTATUS is 'Z0'
	*	b.	Final Test and Production
	*	The default MATERIALSTATUS is 'YA'.
	*
    *@param _db    Database
    *@param _prof  Profile
    *@param offctry  String offering country on abr root CATLGCNTRY item
    *@param ctryAssocItem  EntityItem to use for association to GENERALAREA
    *@param xai  ExtractActionItem to use to pull association to GENERALAREA
    *@param theItem  EntityItem to use for finding salesstatus
    *@param matnr  String matnr value to eacm.sales_status
    *@return String with salestatus
	*/
	protected String getSalesStatus(Database _db, Profile _prof, String offctry, ExtractActionItem xai,
		EntityItem ctryAssocItem, EntityItem theItem, String matnr)
		throws java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
	{
        String strTraceBase = "CATLGPUBPDG getSalesStatus method ";
		String strGENAREACODE = (String)m_hshGENAREACODE.get(offctry);
		if (strGENAREACODE == null || strGENAREACODE.length() <= 0) {
			strGENAREACODE = m_utility.getGENAREACODE(_db, _prof, xai, ctryAssocItem, offctry);
			m_hshGENAREACODE.put(offctry, strGENAREACODE);
		}

		String strGENAREACODEDESC = "";
		if (strGENAREACODE.indexOf(":") > 0) {
			int iColon = strGENAREACODE.indexOf(":");
			strGENAREACODEDESC = strGENAREACODE.substring(iColon+1);
			strGENAREACODE = strGENAREACODE.substring(0,iColon);
		}

		String strKeyForMtrnCodes = strGENAREACODEDESC + matnr + theItem.getEntityType();
		String strMtrnCodes = (String)m_hshMtrnCodes.get(strKeyForMtrnCodes);
		if (strMtrnCodes == null || strMtrnCodes.length() == 0) {
			strMtrnCodes = m_utility.getMtrnCodes(_db, _prof, strGENAREACODEDESC, matnr, theItem.getEntityType());
			m_hshMtrnCodes.put(strKeyForMtrnCodes, strMtrnCodes);
		}

		addDebug(_db,D.EBUG_SPEW,strTraceBase,theItem.getKey()+" strGENAREACODE: "
			+strGENAREACODE+" strMtrnCodes: " + strMtrnCodes+" for matnr "+matnr+" offctry "+offctry);

		return strMtrnCodes;
	}

	/****
	* get CATAUDIENCE based on the flag desc for AUDIEN
	* MODEL.AUDIEN and CATLGPUB.CATAUDIENCE have different values, match them up
	*AUDIEN  100
	*AUDIEN  10046   Catalog - Business Partner
	*AUDIEN  10048   Catalog - Indirect/Reseller
	*AUDIEN  10054   Public
	*AUDIEN  10055   None
	*AUDIEN  10062   LE Direct
	*AUDIEN  10067   DAC/MAX
	*
	*CATAUDIENCE CBP     Catalog - Business Partner
	*CATAUDIENCE CIR     Catalog - Indirect/Reseller
	*CATAUDIENCE LE      LE Direct
	*CATAUDIENCE None    None
	*CATAUDIENCE Shop    Public
    *@param _db    Database
    *@param _prof  Profile
    *@param _strAudience  String with parent.AUDIEN value
    *@return String with corresponding CATAUDIENCE values
    */
    protected String getCatAudValue(Database _db, Profile _prof, String _strAudience) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
        String strTraceBase = "CATLGPUBPDG getCatAudValue method ";

        _db.debug(D.EBUG_SPEW,strTraceBase + " _strAudience: " + _strAudience);
        StringBuffer sbReturn = new StringBuffer();
        StringTokenizer stComma = new StringTokenizer(_strAudience, ",");
        boolean bFirst = true;

        while (stComma.hasMoreTokens()) {
            String strAud = stComma.nextToken();

            String strCatAud = (String)m_audList.get(strAud);
            if (strCatAud == null) {
                String[] aCatAud = m_utility.getFlagCodeForLikedDesc(_db, _prof, ATT_CATAUDIENCE, strAud);
                if (aCatAud == null || aCatAud.length <= 0) {
                    strCatAud = "";
                } else {
                    strCatAud = aCatAud[0];
                }
                m_audList.put(strAud, strCatAud);
            }

            if (strCatAud != null && strCatAud.length() > 0){
                if (!bFirst) {
                    sbReturn.append(",");
                }
                sbReturn.append(strCatAud);
                bFirst = false;

            } else {
                _db.debug(D.EBUG_SPEW,strTraceBase + " unable to find matching CATAUDIENCE for "+ATT_AUDIEN+" desc: " + strAud);
            }
        }
        return sbReturn.toString();
    }

	/****
	* get AUDIEN based on the flag desc for CATAUDIENCE
    *@param _db    Database
    *@param _prof  Profile
    *@param _strCatAud  String with catlgpub.CATAUDIENCE value
    *@return String with corresponding AUDIEN values
    */
    protected String getAudienValue(Database _db, Profile _prof, String _strCatAud) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
        String strTraceBase = "CATLGPUBPDG getAudienValue method ";

        _db.debug(D.EBUG_SPEW,strTraceBase + " _strCatAud: " + _strCatAud);
        StringBuffer sbReturn = new StringBuffer();
        StringTokenizer stComma = new StringTokenizer(_strCatAud, ",");
        boolean bFirst = true;

        while (stComma.hasMoreTokens()) {
            String strCatAud = stComma.nextToken();

            String strAudien = (String)m_cataudList.get(strCatAud);
            if (strAudien == null) {
                String[] aAudien = m_utility.getFlagCodeForLikedDesc(_db, _prof, ATT_AUDIEN, strCatAud);
                if (aAudien == null || aAudien.length <= 0) {
                    strAudien = "";
                } else {
                    strAudien = aAudien[0];
                }
                m_cataudList.put(strCatAud, strAudien);
            }

            if (strAudien != null && strAudien.length() > 0){
                if (!bFirst) {
                    sbReturn.append(",");
                }
                sbReturn.append(strAudien);
                bFirst = false;

            } else {
                _db.debug(D.EBUG_SPEW,strTraceBase + " unable to find matching AUDIEN for "+ATT_CATAUDIENCE+" desc: " + strCatAud);

            }

        }
        return sbReturn.toString();
    }

    private StringBuffer debugSb = new StringBuffer();
    protected void addDebug(String msg) { debugSb.append(msg+"\n");}
    protected void addDebug(Database _db, int level, String strTraceBase, String msg) {
        addDebug(msg);
        if (_db!=null){
            _db.debug(level,strTraceBase + msg);
        }else{
            D.ebug(level,strTraceBase + msg);
        }
    }
    public String getDebugInfo() { return debugSb.toString();}

    /**
     * must be defined by derived classes
     * checkCatlgpub does the work for the abr/pdg
     *
     *@param _db    Database
     *@param _prof  Profile
     */
    protected abstract void checkCatlgpub(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException;

    /**
     * must be defined by derived classes
     * countryStillValid checks to see if the parent model, lseo or lseobundle still has the
     * catlgpub.offcountry in its countrylist.  each parent checks different places
     * to find the countrylist.
     *@param parentItem  EntityItem
     *@param offctry  String with catlgpub.OFFCOUNTRY value
     *@return boolean
     */
    protected abstract boolean countryStillValid(EntityItem parentItem, String offctry);

    /**
     * used in pdgaction interface
     *@param _db    Database
     *@param _prof  Profile
     *@param _bGenData  boolean
     *@return StringBuffer
     */
    protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData)
    throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException
    {
        //String strTraceBase =  "CATLGPUBPDG checkMissingData method ";
        StringBuffer sbReturn = new StringBuffer();
        debugSb = new StringBuffer();  // reset it
        checkCatlgpub(_db, _prof);

        if (m_bScanAll) {
            scanAll(_db, _prof);
            removeCATLGPUB(_db, _prof);
        }

        return sbReturn;
    }

    /**
     * Scan all instance of CATLGPUB where CATNEWOFF = 'Yes' and PUBFROM + 30 days < NOW().
     * Set CATNEWOFF = 'No' and set CATWORKFLOW = 'Change'.
     *
     *@param _db    Database
     *@param _prof  Profile
     */
    private void scanAll(Database _db, Profile _prof)
    throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException
    {
        String strTraceBase = "CATLGPUBPDG scanAll method ";

        StringBuffer sb = new StringBuffer();
        sb.append("map_CATNEWOFF=Yes;");
        sb.append("map_OFFCOUNTRY=" +  m_utility.getAttrValue(m_eiPDG, ATT_OFFCOUNTRY));
        if (m_strEntityType.equals("MODEL")) {
            sb.append(";map_CATOFFTYPE=MODEL");
        } else if (m_strEntityType.equals("LSEO")) {
            sb.append(";map_CATOFFTYPE=LSEO");
        } else if (m_strEntityType.equals("LSEOBUNDLE")) {
            sb.append(";map_CATOFFTYPE=BUNDLE");
        }

        _db.debug(D.EBUG_SPEW,strTraceBase + " searching for CATLGPUB: " + sb.toString());
        String strSai =  (String) m_saiList.get("CATLGPUB");
        EntityItem[] aeiCATLGPUB = m_utility.dynaSearch(_db, _prof, null, strSai, "CATLGPUB", sb.toString());
        if (aeiCATLGPUB != null) {
            _db.debug(D.EBUG_INFO,strTraceBase + " number of CATLGPUB: " + aeiCATLGPUB.length + ", for entitytype: " + m_strEntityType);
            for (int i=0; i < aeiCATLGPUB.length; i++) {
                EntityItem eiCATLGPUB = aeiCATLGPUB[i];
                String strPF = m_utility.getAttrValue(eiCATLGPUB, ATT_PF);
                if (strPF.length() > 0) {
                    int iDCPF = m_utility.dateCompare(m_utility.getDate(strPF, 30), strCurrentDate);
                    if (iDCPF == PDGUtility.EARLIER) {
                        OPICMList attL = new OPICMList();
                        attL.put(ATT_CATNEWOFF, ATT_CATNEWOFF + "=No");
                        String strWorkFlow = m_utility.getAttrValue(eiCATLGPUB, ATT_CATWORKFLOW);

                        if (strWorkFlow.equals("Override")) {
                            attL.put(ATT_CATWORKFLOW, "CATWORKFLOW=SalesStatusOverride");
                        } else if (strWorkFlow.equals("Accept")) {
                            attL.put(ATT_CATWORKFLOW, "CATWORKFLOW=Change");
                        } else if (strWorkFlow.equals("New")) {
                            attL.put(ATT_CATWORKFLOW, "CATWORKFLOW=Change");
                        }

                        updateCatlgpub(_db, _prof, eiCATLGPUB, attL);
                        m_processedList.put(eiCATLGPUB.getKey(), eiCATLGPUB.getKey());

                        attL = null;
                    }else{
						// check if this should still be active - try to improve performance of the remove check
						if (checkForInactive(_db, _prof, eiCATLGPUB)){
							// if true, other checks are not needed
							m_processedList.put(eiCATLGPUB.getKey(), eiCATLGPUB.getKey());
						}
					}
                }else{
					// check if this should still be active - try to improve performance of the remove check
					if (checkForInactive(_db, _prof, eiCATLGPUB)){
						// if true, other checks are not needed
                        m_processedList.put(eiCATLGPUB.getKey(), eiCATLGPUB.getKey());
					}
				}
                m_utility.memory(false);
            }
        }
        aeiCATLGPUB = null;
        _db.debug(D.EBUG_SPEW,strTraceBase + " exiting");
    }

    /**
     * CATLGPUB has an attribute 'Offering Status' (CATACTIVE) which has two values:
     * 'Active' and 'Inactive'. Setting CATLGPUB to 'Inactive' has the effect of removing the
     * offering from the Catalog Database.
	 *
	 * Instances of CATLGPUB should have CATLGPUB.CATACTIVE set to 'Inactive' whenever the instance has:
	 * - not been updated for the past 30 days and (logical AND) CATLGPUB.PUBTO + 30 days < NOW()
	 *Note:  if CATLGPUB.PUBTO is null (i.e. not specified, then treat as if it is '9999-12-30' (i.e. 'forever')
     *
     *@param _db    Database
     *@param _prof  Profile
     */
    private void removeCATLGPUB(Database _db, Profile _prof)
    throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException
    {
        String strTraceBase = "CATLGPUBPDG.removeCATLGPUB method ";
        StringBuffer sb = new StringBuffer();
        sb.append("map_OFFCOUNTRY=" +  m_utility.getAttrValue(m_eiPDG, ATT_OFFCOUNTRY));
        if (m_strEntityType.equals("MODEL")) {
            sb.append(";map_CATOFFTYPE=MODEL");
        } else if (m_strEntityType.equals("LSEO")) {
            sb.append(";map_CATOFFTYPE=LSEO");
        } else if (m_strEntityType.equals("LSEOBUNDLE")) {
            sb.append(";map_CATOFFTYPE=BUNDLE");
        }

        sb.append(";map_CATACTIVE=Active");

        _db.debug(D.EBUG_SPEW,strTraceBase + " searching for CATLGPUB: " + sb.toString());

        String strSai =  (String) m_saiList.get("CATLGPUB");
        // find all active catlgpub for the current entitytype
        EntityItem[] aeiCATLGPUBSearch = m_utility.dynaSearch(_db, _prof, null, strSai, "CATLGPUB", sb.toString());
        if (aeiCATLGPUBSearch != null) {
            _db.debug(D.EBUG_INFO,strTraceBase + " number of CATLGPUB: " + aeiCATLGPUBSearch.length + ", for entitytype: " + m_strEntityType);

			Vector tmp = new Vector(1);
			// do the PUBTO check before the extract, PUBTO is a navigate attribute so extract isnt needed yet
			for (int i=0; i<aeiCATLGPUBSearch.length; i++){
                EntityItem eiCATLGPUB = aeiCATLGPUBSearch[i];
				/*
				check for not been updated for the past 30 days and (logical AND)
				CATLGPUB.PUBTO + 30 days < NOW()
				*/
				if (checkForInactive(_db, _prof, eiCATLGPUB)){
					// if true, all other checks are not needed
					continue;
				}
				// remove those already processed before pulling extract
				if (m_processedList.get(eiCATLGPUB.getKey()) == null) {
					// not processed yet
					tmp.add(eiCATLGPUB);
				}
			}
            _db.debug(D.EBUG_INFO,strTraceBase + " number of entity not in processedList: " + tmp.size() + ", for entitytype: " + m_strEntityType);

			if (tmp.size()>0){
		        String strXai = "EXTCATLGPUB01";
		        ExtractActionItem xai = new ExtractActionItem(null, _db, _prof, strXai);

				aeiCATLGPUBSearch = new EntityItem[tmp.size()];
				tmp.copyInto(aeiCATLGPUBSearch);
				tmp.clear();

				Vector vaCATLGPUB = getChunksOfEntities(aeiCATLGPUBSearch, m_iChunk);
				for (int v=0; v < vaCATLGPUB.size(); v++) {
					_db.debug(D.EBUG_INFO,strTraceBase + " at chunk #: " + v);

					EntityItem[] aeiCATLGPUB = (EntityItem[]) vaCATLGPUB.elementAt(v);
					EntityList elCATLGPUB = EntityList.getEntityList(_db, _prof, xai, aeiCATLGPUB);
					EntityGroup egCATLGPUB = elCATLGPUB.getParentEntityGroup();
					for (int i=0; i < egCATLGPUB.getEntityItemCount(); i++) {
						EntityItem eiCATLGPUB = egCATLGPUB.getEntityItem(i);
						String relator = "";

						if (m_strEntityType.equals("MODEL")) {
							relator = "MODELCATLGPUB";
						} else if (m_strEntityType.equals("LSEO")) {
							relator = "LSEOCATLGPUB";
						} else if (m_strEntityType.equals("LSEOBUNDLE")) {
							relator = "LSEOBUNDLECATLGPUB";
						}
						checkForInactiveCATLGPUB(_db, _prof, eiCATLGPUB, relator,	m_strEntityType);
					}

					elCATLGPUB.dereference();
					elCATLGPUB = null;
					m_utility.memory(false);
				} // end subset of catlgpub items
			} // items exist that have not been processed
        } // end search for catlgpub

        _db.debug(D.EBUG_SPEW,strTraceBase + " exiting");
    }

    /**
     * setInactiveCATLGPUB -
     * CATLGPUB has an attribute 'Offering Status' (CATACTIVE) which has two values:
     * 'Active' and 'Inactive'. Setting CATLGPUB to 'Inactive' has the effect of removing the
     * offering from the Catalog Database.
	 *
     *@param _db    Database
     *@param _prof  Profile
     *@param eiCATLGPUB  EntityItem
     */
    protected void setInactiveCATLGPUB(Database _db, Profile _prof, EntityItem eiCATLGPUB)
    	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException
    {
        String strTraceBase = "CATLGPUBPDG.setInactiveCATLGPUB method ";

		if (m_processedList.get(eiCATLGPUB.getKey()) != null) {
			_db.debug(D.EBUG_SPEW,strTraceBase + " already processed " + eiCATLGPUB.getKey());
			return;
		}

		//check that catlgpub is still active, if already inactive then nothing to do
		String strActive = m_utility.getAttrValue(eiCATLGPUB, ATT_CATACTIVE);
		if (strActive.equals("Inactive")) {
			_db.debug(D.EBUG_SPEW,strTraceBase + " already inactive " + eiCATLGPUB.getKey());
			return;
		}

		_db.debug(D.EBUG_SPEW,strTraceBase + " setting to inactive: " + eiCATLGPUB.getKey());
		String strWorkFlow = m_utility.getAttrValue(eiCATLGPUB, ATT_CATWORKFLOW);
		OPICMList dList = new OPICMList();
		dList.put(ATT_CATACTIVE, "CATACTIVE=Inactive");

		if (strWorkFlow.equals("Override")) {
			dList.put(ATT_CATWORKFLOW, "CATWORKFLOW=SalesStatusOverride");
		} else if (strWorkFlow.equals("Accept")) {
			dList.put(ATT_CATWORKFLOW, "CATWORKFLOW=Change");
		} else if (strWorkFlow.equals("New")) {
			dList.put(ATT_CATWORKFLOW, "CATWORKFLOW=Change");
		}

		updateCatlgpub(_db, _prof, eiCATLGPUB, dList);
		dList = null;
	}

    /**
     * checkForInactive - this may be called after a search for all active when looking for items that
	 * need to be set to inactive or it could be called when an audience wasnt found for
	 * a catlgpub during regular processing
     *
     * CATLGPUB has an attribute 'Offering Status' (CATACTIVE) which has two values:
     * 'Active' and 'Inactive'. Setting CATLGPUB to 'Inactive' has the effect of removing the
     * offering from the Catalog Database.
	 *
	 * Instances of CATLGPUB should have CATLGPUB.CATACTIVE set to 'Inactive' whenever the instance has:
	 * - not been updated for the past 30 days and (logical AND) CATLGPUB.PUBTO + 30 days < NOW()
	 *
     *@param _db    Database
     *@param _prof  Profile
     *@param eiCATLGPUB  EntityItem
     *@return boolean true if all checks are done
     */
    protected boolean checkForInactive(Database _db, Profile _prof, EntityItem eiCATLGPUB)
    	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException
    {
        String strTraceBase = "CATLGPUBPDG.checkForInactive method ";

		if (m_processedList.get(eiCATLGPUB.getKey()) != null) {
			_db.debug(D.EBUG_SPEW,strTraceBase + " already processed " + eiCATLGPUB.getKey());
			return true;
		}

		//check that catlgpub is still active, if already inactive then nothing to do
		// when called after search, CATACTIVE will not be there, it is not a navigate
		// attribute but search already found the active entities
		String strActive = m_utility.getAttrValue(eiCATLGPUB, ATT_CATACTIVE);
		if (strActive.equals("Inactive")) {
			_db.debug(D.EBUG_SPEW,strTraceBase + " already inactive " + eiCATLGPUB.getKey());
			return true;
		}

		_db.debug(D.EBUG_SPEW,strTraceBase + " checking: " + eiCATLGPUB.getKey());

		/*
		check for not been updated for the past 30 days and (logical AND)
		CATLGPUB.PUBTO + 30 days < NOW()
		*/
		String strPT = m_utility.getAttrValue(eiCATLGPUB, ATT_PT);
		if (strPT.length() > 0) {
			int iDCPT = m_utility.dateCompare(m_utility.getDate(strPT, 30), strCurrentDate);
			if (iDCPT == PDGUtility.EARLIER) {
				_prof = m_utility.setProfValOnEffOn(_db, _prof);
				// check for last update
				EntityChangeHistoryGroup echg = new EntityChangeHistoryGroup(_db,_prof, eiCATLGPUB);
				EntityChangeHistoryItem echi = (EntityChangeHistoryItem)getCurrentChangeItem(echg);
				String strChangeDate = echi.getChangeDate();

				int iDate1 = m_utility.dateCompare(m_utility.getDate(strChangeDate.substring(0,10), 30), strCurrentDate);
				if (iDate1 == PDGUtility.EARLIER) {
					addDebug(_db,D.EBUG_INFO,strTraceBase,"Setting "+eiCATLGPUB.getKey()+" to Inactive because it has not been updated in past 30 days");
					setInactiveCATLGPUB(_db, _prof, eiCATLGPUB);
					return true;
				}
			}
		}

		return false;
	}
    /**
     * checkForInactiveCATLGPUB - this may be called after a search for all active when looking for items that
	 * need to be set to inactive or it could be called when an audience wasnt found for
	 * a catlgpub during regular processing
     *
     * CATLGPUB has an attribute 'Offering Status' (CATACTIVE) which has two values:
     * 'Active' and 'Inactive'. Setting CATLGPUB to 'Inactive' has the effect of removing the
     * offering from the Catalog Database.
	 *
	 * Instances of CATLGPUB should have CATLGPUB.CATACTIVE set to 'Inactive' whenever the instance has:
	 * - not been updated for the past 30 days and (logical AND) CATLGPUB.PUBTO + 30 days < NOW()
	 *
     *@param _db    Database
     *@param _prof  Profile
     *@param eiCATLGPUB  EntityItem
     *@param relator  String relator name from parent to catlgpub
     *@param parentType  String
     */
    private void checkForInactiveCATLGPUB(Database _db, Profile _prof, EntityItem eiCATLGPUB, String relator,
    	String parentType)
    	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException
    {
        String strTraceBase = "CATLGPUBPDG.checkForInactiveCATLGPUB method ";

 		_db.debug(D.EBUG_SPEW,strTraceBase + " checking: " + eiCATLGPUB.getKey());

		/*
		Instances of CATLGPUB should be removed whenever the entity (MODEL, LSEO, LSEOBUNDLE)
		that caused the derivation of CATLGPUB has been deleted.

		Instances of CATLGPUB should be removed whenever an update of the entity (MODEL, LSEO, LSEOBUNDLE)
		that caused the derivation of CATLGPUB has been deleted. This would happen if the update deleted
		a country from COUNTRYLIST and/or an audience from AUDIEN.
		*/

		// find parent of this CATLGPUB
		Vector parentVct = getAllLinkedEntities(eiCATLGPUB, relator, parentType);
		if (parentVct.size() == 0) {
			_db.debug(D.EBUG_INFO,strTraceBase + eiCATLGPUB.getKey() + " doesn't have a parent " + m_strEntityType + " link to it");
			orphanCATLGPUB(_db, _prof, eiCATLGPUB);
		} else {
			// check COUNTRYLIST of parent, must still have catlgpub.OFFCOUNTRY
			if (parentVct.size() == 1) {
				//String strCATOFFTYPE = m_utility.getAttrValue(eiCATLGPUB, ATT_CATOFFTYPE);
				String strOFFCOUNTRY =  m_utility.getAttrValue(eiCATLGPUB, ATT_OFFCOUNTRY);
				String strCatAud = m_utility.getAttrValue(eiCATLGPUB, ATT_CATAUDIENCE);
				String strCatlgpubUpdated = m_utility.getAttrValue(eiCATLGPUB, "CATUPDATEDBY");

				// should have one parent only
				EntityItem eiFound = (EntityItem)parentVct.elementAt(0);

				if (!eiFound.getEntityType().equals(m_strEntityType)) {
					// checking whether a parent type is good
					_db.debug(D.EBUG_ERR,strTraceBase + " parent type doesn't match with m_strEntityType " + eiFound.getEntityType() + ":" + m_strEntityType);
					return;
				}

				boolean bFoundCountry = countryStillValid(eiFound, strOFFCOUNTRY);

				if (!bFoundCountry) {
					addDebug(_db,D.EBUG_INFO,strTraceBase,"Setting "+eiCATLGPUB.getKey()+
						" to Inactive because no OffCtry["+strOFFCOUNTRY+"] match found in parent "+eiFound.getKey());
					setInactiveCATLGPUB(_db, _prof, eiCATLGPUB);
				} else {
					// found country, check on AUDIEN value
					String strEICatAud = getCatAudValue(_db, _prof, m_utility.getAttrValueDesc(eiFound, ATT_AUDIEN));
					if (strCatAud.indexOf(",") >= 0) {
						if (strCatlgpubUpdated.length() <= 0) {
							_db.debug(D.EBUG_ERR,strTraceBase + eiCATLGPUB.getKey()+" has multiple flag values for audience and blank updatedby");
							recreateThisCatlgpub(_db, _prof, eiCATLGPUB, eiFound);
						}
					} else {
						if (strEICatAud.indexOf(strCatAud) < 0) {
							addDebug(_db,D.EBUG_INFO,strTraceBase,"Setting "+eiCATLGPUB.getKey()+
								" to Inactive because no Audience match found for "+strCatAud+" in "+eiFound.getKey());
							setInactiveCATLGPUB(_db, _prof, eiCATLGPUB);
						} // no audience match
					} // end valid cataudience found
				}// end country match found
			} else {
				_db.debug(D.EBUG_ERR,strTraceBase + eiCATLGPUB.getKey() + " has multiple parents " );
				for (int e =0; e < parentVct.size(); e++) {
					EntityItem ei = (EntityItem)parentVct.elementAt(e);
					_db.debug(D.EBUG_ERR,strTraceBase + " eiParent: " + ei.getKey());
				}
				// deactivate it, something wrong
				addDebug(_db,D.EBUG_SPEW,strTraceBase," Deactivating "+eiCATLGPUB.getKey()+" because it has multiple parents");
				deactivateCatlgpub(_db, _prof, eiCATLGPUB);
			}
		} // some parents found
    }

    /**
     * resetVariables
     *
     * @see COM.ibm.eannounce.objects.PDGActionItem#resetVariables()
     */
    protected void resetVariables() {
        m_eiList = new EANList();
        m_audList = new OPICMList();
        m_cataudList = new OPICMList();
        m_processedList.clear();
    }

    /**
     * checkPDGAttribute - not used but is abstract in pdgaction base class
     *
     */
    protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _pdgEI) throws SQLException, MiddlewareRequestException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
    }

    /**
     * viewMissing - not used but is abstract in pdgaction base class
     *
     */
    public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        return null;
    }

    private static ChangeHistoryItem getCurrentChangeItem(ChangeHistoryGroup _chg) {
        for (int i = _chg.getChangeHistoryItemCount()-1; i >=0; i--) {
        //for (int i = 0; i < _chg.getChangeHistoryItemCount(); i++) {
            ChangeHistoryItem chi = (ChangeHistoryItem) _chg.getChangeHistoryItem(i);

            if (chi.isValid()) {
                return chi;
            }
        }

        return null;
    }

    /**
     * executeAction
     *
     * @see COM.ibm.eannounce.objects.PDGTemplateActionItem#executeAction(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile)
     */
    public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {

        //ExtractActionItem eaItem = null;
        //EntityItem[] eiParm = new EntityItem[1];
        //EntityList el = null;
        //EntityGroup eg = null;

        String strTraceBase = " CATLGPUBPDG executeAction method ";
        m_SBREx = new SBRException();
        String strData = "";
        resetVariables();


        DatePackage dp = _db.getDates();
        strNow = dp.getNow();
        strCurrentDate = strNow.substring(0, 10);


        try {
            _db.debug(D.EBUG_DETAIL, strTraceBase);
            if (m_eiPDG == null) {
                _db.debug(D.EBUG_ERR,"PDG entity is null");
                return;
            }

            _prof = m_utility.setProfValOnEffOn(_db, _prof);
            // get WG
            EntityGroup egWG = new EntityGroup(null, _db, _prof, "WG", "Edit", false);
            m_eiRoot = new EntityItem(egWG, _prof, _db, _prof.getWGName(), _prof.getWGID());

            EntityGroup egPDG = new EntityGroup(null, _db, _prof, m_eiPDG.getEntityType(), "Edit", false);
            m_eiPDG = new EntityItem(egPDG, _prof, _db, m_eiPDG.getEntityType(), m_eiPDG.getEntityID());

            // validate data
            if (m_SBREx.getErrorCount() > 0) {
                throw m_SBREx;
            }

            m_utility.resetActivities();
            m_sbActivities = new StringBuffer();
            m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");
            strData = checkMissingData(_db, _prof, true).toString();
            m_sbActivities.append(strData);

        } catch (SBRException ex) {
            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, ex.toString(), getLongDescription());
            throw ex;
        }
    }

    /**
     * checkDataAvailability - not used
     *
     */
    protected void checkDataAvailability(Database _db, Profile _prof, EntityItem _pdgEI) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
    }

    public String getRevision() {
        return "$Revision: 1.125 $";
    }

    private void recreateThisCatlgpub(Database _db, Profile _prof, EntityItem _eiCATLGPUB, EntityItem _eiRoot) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = "CATLGPUBPDG recreateThisCatlgpub method ";
        deactivateCatlgpub(_db, _prof, _eiCATLGPUB);

       // String strRootEntityType = _eiRoot.getEntityType();
        m_aStartEI = new EntityItem[] {_eiRoot};
        _db.debug(D.EBUG_SPEW, strTraceBase + " calling checkCatlgpub for " + _eiRoot.getKey());

        checkCatlgpub(_db, _prof);
    }

    protected void deactivateCatlgpub(Database _db, Profile _prof, EntityItem _eiCATLGPUB) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = "CATLGPUBPDG deactivateCatlgpub method ";
        OPICMList attL = new OPICMList();
        _prof = m_utility.setProfValOnEffOn(_db, _prof);
        attL.put("CATUPDATEDBY", "CATUPDATEDBY=deactivated by " + m_strUpdatedBy);
        m_utility.updateAttribute(_db, _prof, _eiCATLGPUB, attL);

        _db.debug(D.EBUG_ERR, strTraceBase + " deactivate " + _eiCATLGPUB.getKey());
        EANUtility.deactivateEntity(_db, _prof, _eiCATLGPUB);
    }

    protected void updateCatlgpub(Database _db, Profile _prof, EntityItem _eiCATLGPUB, OPICMList _attL) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        //String strTraceBase = "CATLGPUBPDG updateCatlgpub method ";

        _attL.put("CATUPDATEDBY", "CATUPDATEDBY=" + m_strUpdatedBy);
        _prof = m_utility.setProfValOnEffOn(_db, _prof);
        m_utility.updateAttribute(_db, _prof, _eiCATLGPUB, _attL);
    }

    public void setUpdatedBy(String _str) {
        m_strUpdatedBy = m_strUpdatedBy + _str;
    }

    protected Vector getChunksOfEntities(EntityItem[] _aei, int _iChunk) {
        Vector vReturn = new Vector();
        if (_aei != null) {
            int iCount = 0;
            Vector v = new Vector();
            for (int i=0; i < _aei.length; i++) {
                EntityItem ei = _aei[i];
                v.addElement(ei);
                iCount ++;
                if (iCount == _iChunk) {
                    EntityItem[] aeiChunked = new EntityItem[v.size()];
                    v.toArray(aeiChunked);
                    vReturn.addElement(aeiChunked);
                    iCount = 0;
                    v = new Vector();
                }
            }
            if (v.size() > 0) {
                EntityItem[] aeiChunked = new EntityItem[v.size()];
                v.toArray(aeiChunked);
                vReturn.addElement(aeiChunked);
            }
        }
        return vReturn;
    }

    /**
     * Parent for this CATLGPUB was not found in extract, do a search using values from the CATLGPUB entity
     * if found, relink and set to inactive if criteria are met
     * if no parent found, just set to inactive
	 *
     *@param _db    Database
     *@param _prof  Profile
     *@param _eiCATLGPUB  EntityItem
     */
    private void orphanCATLGPUB(Database _db, Profile _prof, EntityItem _eiCATLGPUB) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = "CATLGPUBPDG orphanCATLGPUB method ";
        String strXai = "";

        if (m_strEntityType.equals("MODEL")) {
            strXai = "EXTCATLGPUBPDGMODEL1";
        } else if (m_strEntityType.equals("LSEO")) {
            strXai = "EXTCATLGPUBPDGLSEO2";
        } else if (m_strEntityType.equals("LSEOBUNDLE")) {
            strXai = "EXTCATLGPUBPDGBDLE2";
        }

        ExtractActionItem xai = new ExtractActionItem(null, _db, _prof, strXai);

        // find parent for this CATLGPUB
        String strCATOFFTYPE = m_utility.getAttrValue(_eiCATLGPUB, ATT_CATOFFTYPE);
        String strOFFCOUNTRY =  m_utility.getAttrValue(_eiCATLGPUB, ATT_OFFCOUNTRY);
        String strCatAud = m_utility.getAttrValue(_eiCATLGPUB, ATT_CATAUDIENCE);
        String strCatlgpubUpdated = m_utility.getAttrValue(_eiCATLGPUB, "CATUPDATEDBY");

        EntityItem[] aeiFound = null;
        StringBuffer sbSearchParent = new StringBuffer();
        String strSai = (String) m_saiList.get(m_strEntityType);
        if (m_strEntityType.equals("MODEL")) {
            String strMachType = m_utility.getAttrValue(_eiCATLGPUB, ATT_CATMACHTYPE);
            String[] aMachType = m_utility.getFlagCodeForLikedDesc(_db, _prof, ATT_MACHTYPE, strMachType);
            if (aMachType == null || aMachType.length <= 0) {
                _db.debug(D.EBUG_SPEW,strTraceBase + " unable to find MACHTYPEATR for desc: " + strMachType);
            } else {
                strMachType = aMachType[0];
			}

			sbSearchParent.append("map_MACHTYPEATR=" +  strMachType + ";");
			sbSearchParent.append("map_MODELATR=" +  m_utility.getAttrValue(_eiCATLGPUB, ATT_CATMODEL));
        } else if (m_strEntityType.equals("LSEO")) {
            sbSearchParent.append("map_SEOID=" +  m_utility.getAttrValue(_eiCATLGPUB, ATT_CATSEOID) + ";");
        } else if (m_strEntityType.equals("LSEOBUNDLE")) {
            sbSearchParent.append("map_SEOID=" +  m_utility.getAttrValue(_eiCATLGPUB, ATT_CATSEOID));
        }

        aeiFound = m_utility.dynaSearch(_db, _prof, null, strSai, m_strEntityType, sbSearchParent.toString());

        if (aeiFound == null || aeiFound.length <= 0) {
            // MODEL, LSEO, LSEOBUNDLE no longer exist
            _db.debug(D.EBUG_INFO,strTraceBase + " search string : " + sbSearchParent.toString());
            _db.debug(D.EBUG_INFO,strTraceBase + " entity not found, setting CATACTIVE to Inactive for " + _eiCATLGPUB.getKey());
			addDebug(strTraceBase+"Setting "+_eiCATLGPUB.getKey()+" to Inactive because no parent "+m_strEntityType+" found");
            setInactiveCATLGPUB(_db, _prof, _eiCATLGPUB);
        } else { // must have been unlinked, relink it
            if (aeiFound.length == 1) {
                EntityItem eiFound = aeiFound[0];

                if (!eiFound.getEntityType().equals(m_strEntityType)) {
                    // checking whether a parent type is good
                    _db.debug(D.EBUG_ERR,strTraceBase + " parent type doesn't match with m_strEntityType " + eiFound.getEntityType() + ":" + m_strEntityType);
                    return;
                }

                EntityItem[] aeiChild = {_eiCATLGPUB};
                if (m_strEntityType.equals("MODEL") && strCATOFFTYPE.equals("MODEL")) {
                    m_utility.linkEntities(_db, _prof, eiFound, aeiChild, "MODELCATLGPUB");
                } else if (m_strEntityType.equals("LSEO") && strCATOFFTYPE.equals("LSEO")) {
                    m_utility.linkEntities(_db, _prof, eiFound, aeiChild, "LSEOCATLGPUB");
                } else if (m_strEntityType.equals("LSEOBUNDLE") && strCATOFFTYPE.equals("BUNDLE")) {
                    m_utility.linkEntities(_db, _prof, eiFound, aeiChild, "LSEOBUNDLECATLGPUB");
                } else {
                    _db.debug(D.EBUG_ERR,strTraceBase + " not match " + m_strEntityType + ":" + strCATOFFTYPE);
                }

				// pull extract from parent down
                EntityList el = EntityList.getEntityList(_db, _prof, xai, aeiFound);
                EntityGroup egParent = el.getParentEntityGroup();
                eiFound = egParent.getEntityItem(eiFound.getKey());

   				boolean bFoundCountry = countryStillValid(eiFound, strOFFCOUNTRY);

                if (!bFoundCountry) {
					addDebug(_db,D.EBUG_INFO,strTraceBase,"Setting "+_eiCATLGPUB.getKey()+
						" to Inactive because no OffCtry match found in parent "+eiFound.getKey());
		            setInactiveCATLGPUB(_db, _prof, _eiCATLGPUB);
                } else {
                    // found country, check on AUDIEN value
                    String strEICatAud = getCatAudValue(_db, _prof, m_utility.getAttrValueDesc(eiFound, ATT_AUDIEN));

                    //boolean bMatch = true;
                    if (strCatAud.indexOf(",") >= 0) {
                        if (strCatlgpubUpdated.length() <= 0) {
                            _db.debug(D.EBUG_INFO,strTraceBase + " this catlgpub has multiple flag values for audience and blank updatedby: " + _eiCATLGPUB.getKey());
                            recreateThisCatlgpub(_db, _prof, _eiCATLGPUB, eiFound);
                        }
                    } else {
                        if (strEICatAud.indexOf(strCatAud) < 0) {
							addDebug(_db,D.EBUG_INFO,strTraceBase,"Setting "+_eiCATLGPUB.getKey()+
								" to Inactive because no Audience match found for "+strCatAud+" in "+eiFound.getKey());
            				setInactiveCATLGPUB(_db, _prof, _eiCATLGPUB);
                        }
                    }
                }
				el.dereference();
                el = null;
            } else if (aeiFound.length > 1) {
                _db.debug(D.EBUG_ERR,strTraceBase + " search for parent return " + aeiFound.length + " records");
                _db.debug(D.EBUG_ERR,strTraceBase + " should be unique. search string: " + sbSearchParent.toString());
				deactivateCatlgpub(_db, _prof, _eiCATLGPUB);
            }
        }
    }

    /**
     * Find the changed roots in the time range
	 *
     *@param _db    Database
     *@param _prof  Profile
     *@param rootType  String parent type to find
     *@param extract  String extract name to use
     *@param attrcode  String with last ran timestamp
     *@param parentType  String
     *@return EntityItem[]
     */
    protected EntityItem[] getChangedRoots(Database _db, Profile _prof,String rootType, String extract,
    	String attrcode) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = " CATLGPUBPDG getChangedRoots method ";

        EntityItem[] aeiReturn = null;

        EANList list = new EANList();

        String strStartDate = m_utility.getAttrValue(m_eiPDG, attrcode);
        String strEndDate = strNow;

        // added for running outside the abr, not using lastrun timestamp and current
        // to avoid memory
        if (m_bUseDateRange && (m_strStartTime != null) && (m_strEndTime != null)) {
            strStartDate = m_strStartTime;
            strEndDate = m_strEndTime;
        }

        addDebug(_db,D.EBUG_SPEW,strTraceBase,"using rootType "+rootType+" extract "+extract+
        	" strStartDate "+strStartDate+" strEndDate "+strEndDate);
        if (m_aStartEI == null || m_aStartEI.length <= 0) {
            Vector v = m_utility.getChangedEntities( _db,  _prof, rootType,
            		extract, strStartDate, strEndDate, 2, 0, "", -1);
        	addDebug(_db,D.EBUG_SPEW,strTraceBase,"found "+v.size()+" changes in the VE ");
            for (int x=0; x < v.size(); x++) {
                String strChanged = (String) v.elementAt(x);
                StringTokenizer st = new StringTokenizer(strChanged, ":");
				//gbl8104:answer:DEPRECATED:MODEL:1267949:E:MODEL:1267949:OFF:0:E::XX:-9:XX:-9:DEPRECATED
                String strGenArea = st.nextToken();
                String strRootType = st.nextToken().trim();
                int iRootId = Integer.parseInt(st.nextToken().trim());
                String strType = st.nextToken(); // E is entity, R is relator
                String strChildType = st.nextToken().trim();
                String strChildId = st.nextToken().trim();
                String strAction = st.nextToken().trim(); //OFF is deleted
                if (strRootType.equals(rootType)) {
					if (strChildType.equals(rootType) && strAction.equals("OFF")){
                	    _db.debug(D.EBUG_SPEW, strTraceBase + " skip Deleted: " + strRootType+iRootId);
                	    // deleted entities will have relators deleted too so dont need to check catlgpubs for this
					}else{
                	    EntityItem ei = new EntityItem( null, _prof, strRootType, iRootId);
                	    //_db.debug(D.EBUG_SPEW, strTraceBase + " found changed: " + ei.getKey());
                	    list.put(ei);
					}
                }
            }
        } else {
            for (int x=0; x < m_aStartEI.length; x++) {
                EntityItem ei = m_aStartEI[x];
                String strRootType = ei.getEntityType();
                //int iRootId = ei.getEntityID();
                if (strRootType.equals(rootType)) {
                    list.put(ei);
                }
            }
        }

        aeiReturn = new EntityItem[list.size()];
        list.copyTo(aeiReturn);

        return aeiReturn;
    }

//////////////////////////////////////////////
//copied from pokutils.

    /********************************************************************************
    * Find entities of the destination type linked to the EntityItems in the EntityGroup
    * through the specified link type.  Both uplinks and downlinks are checked
    * though only one will contain a match.
    * All objects in the source Vector must be EntityItems of the same entity type
    *
    * @param srcGrp     EntityGroup
    * @param linkType   String Association or Relator type linking the entities
    * @param destType   String EntityType to match
    * @return Vector of EntityItems
    */
    public static Vector getAllLinkedEntities(EntityGroup srcGrp, String linkType, String destType)
    {
        // find entities thru 'linkType' relators
        Vector destVct = new Vector(1);
        if (srcGrp != null) {
            for(int ie=0; ie<srcGrp.getEntityItemCount();ie++)
            {
                EntityItem entityItem = srcGrp.getEntityItem(ie);
                getLinkedEntities(entityItem, linkType, destType, destVct);
            }
        }

        return destVct;
    }

    /********************************************************************************
    * Find entities of the destination type linked to the EntityItems in the source
    * vector through the specified link type.  Both uplinks and downlinks are checked
    * though only one will contain a match.
    * All objects in the source Vector must be EntityItems of the same entity type
    *
    * @param srcVct     Vector of EntityItems
    * @param linkType   String Association or Relator type linking the entities
    * @param destType   String EntityType to match
    * @returns Vector of EntityItems
    */
    public static Vector getAllLinkedEntities(Vector srcVct, String linkType, String destType)
    {
        // find entities thru 'linkType' relators
        Vector destVct = new Vector(1);

        Iterator srcItr = srcVct.iterator();
        while (srcItr.hasNext())
        {
            EntityItem entityItem = (EntityItem)srcItr.next();
            getLinkedEntities(entityItem, linkType, destType, destVct);
        }

        return destVct;
    }

    /********************************************************************************
    * Find entities of the destination type linked to the EntityItems in the source
    * vector through the specified link type.  Both uplinks and downlinks are checked
    * though only one will contain a match.
    * All objects in the source Vector must be EntityItems of the same entity type
    *
    * @param entityItem EntityItem
    * @param linkType   String Association or Relator type linking the entities
    * @param destType   String EntityType to match
    * @returns Vector of EntityItems
    */
    public static Vector getAllLinkedEntities(EntityItem entityItem, String linkType, String destType)
    {
        // find entities thru 'linkType' relators
        Vector destVct = new Vector(1);

        getLinkedEntities(entityItem, linkType, destType, destVct);

        return destVct;
    }

    /********************************************************************************
    * Find entities of the destination type linked to the specified EntityItem through
    * the specified link type.  Both uplinks and downlinks are checked though only
    * one will contain a match.
    *
    * @param entityItem EntityItem
    * @param linkType   String Association or Relator type linking the entities
    * @param destType   String EntityType to match
    * @param destVct    Vector EntityItems found are returned in this vector
    */
    private static void getLinkedEntities(EntityItem entityItem, String linkType, String destType,
        Vector destVct)
    {
        if (entityItem==null) {
            return; }

        // see if this relator is used as an uplink
        for (int ui=0; ui<entityItem.getUpLinkCount(); ui++)
        {
            EANEntity entityLink = entityItem.getUpLink(ui);
            if (entityLink.getEntityType().equals(linkType))
            {
                // check for destination entity as an uplink
                for (int i=0; i<entityLink.getUpLinkCount(); i++)
                {
                    EANEntity entity = entityLink.getUpLink(i);
                    if (entity.getEntityType().equals(destType) && !destVct.contains(entity)) {
                        destVct.addElement(entity); }
                }
                // check for destination entity as a downlink
                /*for (int i=0; i<entityLink.getDownLinkCount(); i++)
                {
                    EANEntity entity = entityLink.getDownLink(i);
                    if (entity.getEntityType().equals(destType) && !destVct.contains(entity))
                        destVct.addElement(entity);
                }*/
            }
        }

        // see if this relator is used as a downlink
        for (int ui=0; ui<entityItem.getDownLinkCount(); ui++)
        {
            EANEntity entityLink = entityItem.getDownLink(ui);
            if (entityLink.getEntityType().equals(linkType))
            {
                // check for destination entity as an uplink
                /*for (int i=0; i<entityLink.getUpLinkCount(); i++)
                {
                    EANEntity entity = entityLink.getUpLink(i);
                    if (entity.getEntityType().equals(destType) && !destVct.contains(entity))
                        destVct.addElement(entity);
                }*/
                // check for destination entity as a downlink
                for (int i=0; i<entityLink.getDownLinkCount(); i++)
                {
                    EANEntity entity = entityLink.getDownLink(i);
                    if (entity.getEntityType().equals(destType) && !destVct.contains(entity)) {
                        destVct.addElement(entity); }
                }
            }
        }
    }

    /**
     * Returns true if the flag is selected. Returns false if any parameter is null,
     * the attribute code is not a flag attribute, or the flag code is not valid for the attribute code.
     *@param item EntityItem to check
     *@param attCode String the attribute code to check
     *@param set Set
     *@return boolean
     */
    public static boolean contains(EntityItem item, String attCode, Set set) {
        boolean found=false;
        // Get the attribute
        EANAttribute att = item.getAttribute(attCode);
        // If it is a Flag
        if (att instanceof EANFlagAttribute) {
            EANFlagAttribute fAtt = (EANFlagAttribute) att;
            // Get all the Flag values.
            MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
            // Go through all the flag values
            for (int i = 0; i < mfArray.length; i++) {
                // If this flag is in the set
                if (set.contains(mfArray[i].getFlagCode())) {
                    // Check that it is selected
                    if (mfArray[i].isSelected()) {
                        found=true;
                        break;
                    }
                }
            }
        }
        return found;
    }

}
