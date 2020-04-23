//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2010  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.sg;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.Vector;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;

/*************************
 * SVCPACPRESELBase Base class for SERVPACKPRESELECTABR and SVCPACPRESELREQABR
 * 
 * from BH FS ABR CatalogRole ABR_PDG 20091222.doc
 *  
 * B.	ServicePacs
 * A MODEL is a Service if MODEL.COFCAT = “Service” (102) and the ServicePac type of the service is 
 * MODEL.COFSUBCAT (e.g. Remote Technical Support). A LSEO is a ServicePac if its parent WWSEO has a parent
 * MODEL that is a ServicePac. A LSEOBUNDLE is a service pac if BUNDLETYPE is only “Service” (102) and 
 * ServicePac Bundle Type (SVCPACBNDLTYPE) is the type of Service.
 * 
 * C.	Preselect
 * This is used to specify by type of service a single ServicePac as the default selection when ordering a system. 
 * Since this is for the Catalog which is by country, the ServicePac will be a LSEO or a LSEOBUNDLE.
 * 
 */
public abstract class SVCPACPRESELBase extends PokBaseABR {
	// Class constants
	protected final static String ATT_CATMACHTYPE = "CATMACHTYPE";
	protected final static String ATT_CATSEOID ="CATSEOID";
	protected final static String ATT_SVCPACTYPE ="SVCPACTYPE";
	protected final static String ATT_OFFCOUNTRY = "OFFCOUNTRY";
	protected final static String ATT_COFSUBCAT = "COFSUBCAT";
	protected final static String ATT_PRESELINDC = "PRESELINDC";
	protected static final String PRESELINDC_Yes = "Yes";
	protected final static String ATT_PRESELSEOTYPE = "PRESELSEOTYPE";
	//PRESELSEOTYPE	PRE1	LSEO
	//PRESELSEOTYPE	PRE2	LSEOBUNDLE

	protected static final String PRESELSEOTYPE_LSEO = "PRE1";
	protected static final String PRESELSEOTYPE_LSEOBDL = "PRE2";
	protected final static String ATT_COFCAT = "COFCAT";
	protected final static String ATT_BUNDLETYPE = "BUNDLETYPE";
	protected final static String ATT_SVCPACBNDLTYPE = "SVCPACBNDLTYPE";
	protected static final String COFCAT_Service = "102";//BUNDLETYPE	102	Service matches COFCAT	
	
    private StringBuffer rptSb = new StringBuffer();
    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);
    private String navName = "";
    private Hashtable metaTbl = new Hashtable();
    private ResourceBundle rsBundle = null;
    protected Object[] args = new String[10];
 
    private String strCurrentDate = null;

    protected String offCountryFlag = null;
    protected String offCountry = null;
    
	/**********************************
	 * get the resource bundle
	 */
	protected ResourceBundle getBundle() {
		return rsBundle;
	}

	/**
	 * execute_run
	 *
	 * @author Owner
	 */
	public void execute_run() {
        // must split because too many arguments for messageformat, max of 10.. this was 11
        String HEADER = "<head>"+
             EACustom.getMetaTags(getDescription()) + NEWLINE +
             EACustom.getCSS() + NEWLINE +
             EACustom.getTitle("{0} {1}") + NEWLINE +
            "</head>" + NEWLINE + "<body id=\"ibm-com\">" +
             EACustom.getMastheadDiv() + NEWLINE +
            "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
        String HEADER2 = "<table>"+NEWLINE +
             "<tr><th>Userid: </th><td>{0}</td></tr>"+NEWLINE +
             "<tr><th>Role: </th><td>{1}</td></tr>"+NEWLINE +
             "<tr><th>Workgroup: </th><td>{2}</td></tr>"+NEWLINE +
             "<tr><th>Date: </th><td>{3}</td></tr>"+NEWLINE +
             "<tr><th>Description: </th><td>{4}</td></tr>"+NEWLINE +
             "</table>"+NEWLINE+
            "<!-- {5} --><br /><br />" + NEWLINE;

        MessageFormat msgf;     
        String parentGrpDesc = "";
		try { 
			start_ABRBuild();
			setReturnCode(PASS);
			// get properties file for the base class
            rsBundle = ResourceBundle.getBundle(SERVPACKPRESELECTABR.class.getName(), ABRUtil.getLocale(m_prof.getReadLanguage().getNLSID()));

			strCurrentDate = m_db.getDates().getNow().substring(0, 10);
			
			parentGrpDesc = m_elist.getParentEntityGroup().getLongDescription();

            //NAME is navigate attributes
            navName = getNavigationName(m_elist.getParentEntityGroup().getEntityItem(0));
            
        	verifyRequest();

        	if(getReturnCode()== PokBaseABR.PASS){ // valid request	
        		executeThis();
        	}
		}
        catch(Throwable exc)  {
            java.io.StringWriter exBuf = new java.io.StringWriter();
            String Error_EXCEPTION="<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
            String Error_STACKTRACE="<pre>{0}</pre>";
            msgf = new MessageFormat(Error_EXCEPTION);
            setReturnCode(INTERNAL_ERROR);
            exc.printStackTrace(new java.io.PrintWriter(exBuf));
            // Put exception into document
            args[0] = exc.getMessage();
            rptSb.append(msgf.format(args) + NEWLINE);
            msgf = new MessageFormat(Error_STACKTRACE);
            args[0] = exBuf.getBuffer().toString();
            rptSb.append(msgf.format(args) + NEWLINE);
            logError("Exception: "+exc.getMessage());
            logError(exBuf.getBuffer().toString());
        }
        finally {
            setDGTitle(navName);
            setDGRptName(getShortClassName(getClass()));
            setDGRptClass(getABRCode());
			
            // make sure the lock is released
            if(!isReadOnly()) {
                clearSoftLock();
            }
        }
  
        //Print everything up to </html>
        //Insert Header into beginning of report
        msgf = new MessageFormat(HEADER);
   
        args[0] = getShortClassName(getClass());
        args[1] = navName;
        String header1 = msgf.format(args);
        msgf = new MessageFormat(HEADER2);
        args[0] = m_prof.getOPName();
        args[1] = m_prof.getRoleDescription();
        args[2] = m_prof.getWGName();
        args[3] = getNow();
        args[4] = parentGrpDesc;
        args[5] = getABRVersion();

        rptSb.insert(0, header1+msgf.format(args) + NEWLINE);

        println(EACustom.getDocTypeHtml()); //Output the doctype and html        
		println(rptSb.toString()); // Output the Report
		printDGSubmitString();        
        
        println(EACustom.getTOUDiv());
        buildReportFooter(); // Print </html>
	}
	
	/********************
	 * execute the derived class
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	protected abstract void executeThis() throws MiddlewareRequestException, 
    SQLException, MiddlewareException, MiddlewareShutdownInProgressException, RemoteException, 
    EANBusinessRuleException;
	
	/**
	 * derived classes must implement this to validate the abrs attributes
	 */
	protected abstract void verifyRequest();
	
	/********************
	 * find all valid LSEO for this seoid, OFFCOUNTRY must be in COUNTRYLIST and date must be in range
	 * 
	 * 1.	A LSEO exists with LSEO.SEOID = CATSEOID and COUNTRYLIST having OFFCOUNTRY and 
	 * LSEOPUBDATEMTRGT <= NOW() <= LSEOUNPUBDATEMTRGT (only check LSEOUNPUBDATEMTRGT if it exists or
	 * consider it to be 9999-12-31 if it doesn’t exist).
	 * 
	 * I.	Verify that the SEO’s a ServicePac available in OFFCOUNTRY:
	 * 	1.	LSEO
	 * 	b)	If OFFCOUNTRY NOT IN LSEO.COUNTRYLIST, then report an error stating that this LSEO is not available in OFFCOUNTRY.
	 * 
	 *  III.	Validate that the SEO is still available:
	 *  1.	LSEO
	 *  	LSEOPUBDATEMTRGT <= NOW() <= LSEOUNPUBDATEMTRGT	If LSEOUNPUBDATEMTRGT is empty, assume 9999-12-31
	 *   
	 * @param seoid
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	protected Vector findLSEOForCheck1(String seoid) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		return findSEOForCheck1(seoid, "LSEO", "SRDLSEO1", "LSEOUNPUBDATEMTRGT", "LSEOPUBDATEMTRGT");
	}
	
	private Vector findSEOForCheck1(String seoid, String etype, String actionName, 
			String unpubAttr, String pubAttr) 
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		Vector seoVct = new Vector(1);
		//1.	A LSEO exists with LSEO.SEOID = CATSEOID and COUNTRYLIST having OFFCOUNTRY and 
		//LSEOPUBDATEMTRGT <= NOW() <= LSEOUNPUBDATEMTRGT (only check LSEOUNPUBDATEMTRGT if it exists or 
		//consider it to be 9999-12-31 if it doesn’t exist).
		//or  1.A LSEOBUNDLE exists with LSEOBUNDLE.SEOID = CATSEOID and COUNTRYLIST having OFFOUNTRY and 
		//BUNDLPUBDATEMTRGT<= NOW() <= BUNDLUNPUBDATEMTRGT (only check BUNDLUNPUBDATEMTRGT if it exists or 
		//consider it to be 9999-12-31 if it doesn’t exist).
		EntityItem[] aeiSEO = searchForSEO(seoid, etype, actionName);
		if (aeiSEO == null || aeiSEO.length == 0){
			//SEO_ERR = There are no {0} for SEOID: {1}
			args[0] = etype+"s";
			args[1] = seoid;
			addError("SEO_ERR",args);
		}else{
			for (int j = 0; j < aeiSEO.length; j++) {
				EntityItem eiSEO = aeiSEO[j];
				//check offcountry is in countrylist
				EANFlagAttribute fAtt = (EANFlagAttribute)eiSEO.getAttribute("COUNTRYLIST");
				if (fAtt== null || !fAtt.isSelected(offCountryFlag)){	
					//CTRY_ERR = {0} is not available in {1}
					args[0] = getLD_NDN(eiSEO);
					args[1] = offCountry;
					addError("CTRY_ERR",args);
					continue;
				}
				//LSEOPUBDATEMTRGT <= NOW() <= LSEOUNPUBDATEMTRGT
				//BUNDLPUBDATEMTRGT <= NOW() <= BUNDLUNPUBDATEMTRGT
				if (checkDates(eiSEO, unpubAttr, pubAttr)){
					seoVct.add(eiSEO);
				}
			}
		}
		
		return seoVct;
	}
	/******************
	 * find LSEOBUNDLEs complying with check 1
	 * 1.	A LSEOBUNDLE exists with LSEOBUNDLE.SEOID = CATSEOID and COUNTRYLIST having OFFOUNTRY and 
	 *	BUNDLPUBDATEMTRGT<= NOW() <= BUNDLUNPUBDATEMTRGT (only check BUNDLUNPUBDATEMTRGT if it exists or 
	 *	consider it to be 9999-12-31 if it doesn’t exist).
	 * I.	Verify that the SEO’s a ServicePac available in OFFCOUNTRY:
	 *  2.	LSEOBUNDLE
	 *  	b)	If OFFCOUNTRY NOT IN LSEOBUNDLE.COUNTRYLIST, then report an error stating that this LSEOBUNDLE is not
	 *  	available in OFFCOUNTRY.
	 * III.	Validate that the SEO is still available:
	 *  2.	LSEOBUNDLE
	 *  	BUNDLPUBDATEMTRGT <= NOW() <= BUNDLUNPUBDATEMTRGT If BUNDLUNPUBDATEMTRGT is empty, assume 9999-12-31
	 * @param strSEOID
	 * @return 
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	protected Vector findLSEOBDLForCheck1(String seoid) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		return findSEOForCheck1(seoid, "LSEOBUNDLE", "SRDLSEOBUNDLE1", "BUNDLUNPUBDATEMTRGT", "BUNDLPUBDATEMTRGT");
	}

	/***********************
	 * 3.	Verify that the LSEO (SEOID) is compatible with the Machine Type (MACHTYPE.MACHTYPEATR=CATMACHTYPE) as 
	 *		described in the prior section for the Generating ABR.
	 *
	 * II.	Verify that the SEO has a “technical compatibility”:
	 * 1.	Must be in a SEOCGOS
	 * LSEO via WWSEOLSEO-u: SEOCGOSSEO-u
	 * or
	 * LSEOBUNDLE via SEOCGOSBDL-u
	 * 
	 * 2.	Which will be in a SEOCG
	 * SEOCGOS via SEOCGSEOCGOS-u
	 * 
	 * 3.	MACHTYPEATR must be in SEOCG
	 * from SEOCG via SEOCGMDL-d to MODEL.
	 * CATMACHTYPE must match a MODEL.MACHTYPEATR. Note that there may be several MODELs and they could have
	 * different MACHTYPEATR. CATMACHTYPE only has to match one of them to be legal.
	 * if a match is not found, then report an error stating that {LSEO | LSEOBUNDLE} SEOID is not compatible
	 * with this Machine Type (CATMACHTYPE)
	 * 	
	 * @param seoArray - WWSEO when checking for LSEO, or LSEOBUNDLE
	 * @param strMT
	 * @param xai
	 * @return
	 * @throws MiddlewareRequestException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws SQLException
	 */
	protected boolean verifySEOtechCompat(EntityItem[] seoArray, String strMT,ExtractActionItem xai) 
			throws MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException
	{
		boolean iscompat = false;
		addDebug("verifySEOtechCompat match MACHTYPE: "+strMT);	
		// pull all at once, only care about a single match
		EntityList list = EntityList.getEntityList(m_db,m_prof, xai,seoArray);
		addDebug("verifySEOtechCompat Extract "+xai.getActionItemKey()+" "+PokUtils.outputList(list));
		
		EntityGroup egMODEL2 =  list.getEntityGroup("MODEL");
		for (int m = 0; m < egMODEL2.getEntityItemCount(); m++) {
			EntityItem eiMODEL2 = egMODEL2.getEntityItem(m);
			String strMT2 = getAttributeFlagEnabledValue(eiMODEL2,"MACHTYPEATR");
			addDebug("verifySEOtechCompat checking " + eiMODEL2.getKey()+" MACHTYPEATR:"+strMT2);
			if (strMT.equals(strMT2)) {
				iscompat = true;
				break;
			}
		}
		list.dereference();
		
		return iscompat;
	}
	/*******************************
	 * 1.an LSEO exists with LSEO.SEOID = CATSEOID and COUNTRYLIST having OFFCOUNTRY and 
	 * LSEOPUBDATEMTRGT <= NOW() <= LSEOUNPUBDATEMTRGT (only check LSEOUNPUBDATEMTRGT if it exists or 
	 * consider it to be 9999-12-31 if it doesn’t exist).
	 * use 9999-12-31 for pub date too, it will not be eligible
	 * or
	 * 	1.	A LSEOBUNDLE exists with LSEOBUNDLE.SEOID = CATSEOID and COUNTRYLIST having OFFOUNTRY and 
	 *	BUNDLPUBDATEMTRGT<= NOW() <= BUNDLUNPUBDATEMTRGT (only check BUNDLUNPUBDATEMTRGT if it exists or 
	 *	consider it to be 9999-12-31 if it doesn’t exist).
	 * @param eiLSEO
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private boolean checkDates(EntityItem ei, String unpubAttr, String pubAttr) 
	throws SQLException, MiddlewareException
	{
		boolean isok = true;

		String pubDate = PokUtils.getAttributeValue(ei, pubAttr, "", "9999-12-31", false);
		String unpubDate = PokUtils.getAttributeValue(ei, unpubAttr, "", "9999-12-31", false);
		
		addDebug("checkDates " + ei.getKey()+" strCurrentDate "+strCurrentDate+
				" "+pubAttr+":"+pubDate+" "+unpubAttr+":"+unpubDate);
		
		if (pubDate.compareTo(strCurrentDate)>0 ||
				strCurrentDate.compareTo(unpubDate)>0){
			isok = false;
			//DATE_ERR = {0} SEOID is not available. 
			args[0] = getLD_NDN(ei);
			addError("DATE_ERR",args);
		}

		return isok;
	}
	
	/************************************
	 * Find LSEO/LSEOBUNDLE matching SEOID
	 * @param strSEOID
	 * @param etype
	 * @param actionName
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 */
	private EntityItem[] searchForSEO(String strSEOID, String etype, String actionName)
	throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		addDebug("searchForSEO "+etype+" SEOID:"+strSEOID);
		EntityItem eia[]= null;
		Vector attrVct = new Vector(1);
		attrVct.addElement("SEOID");
	
		Vector valVct = new Vector(1);
		valVct.addElement(strSEOID);
	
		try{
			StringBuffer debugSb = new StringBuffer();
			eia= ABRUtil.doSearch(getDatabase(), m_prof, 
					actionName, etype, false, attrVct, valVct, debugSb);
			if (debugSb.length()>0){
				addDebug(debugSb.toString());
			}
		}catch(SBRException exc){
			// these exceptions are for missing flagcodes or failed business rules, dont pass back
			java.io.StringWriter exBuf = new java.io.StringWriter();
			exc.printStackTrace(new java.io.PrintWriter(exBuf));
			addDebug("searchForSEO SBRException: "+exBuf.getBuffer().toString());
		}
		if (eia!=null && eia.length > 0){			
			for (int i=0; i<eia.length; i++){
				addDebug("searchForSEO found "+eia[i].getKey());
			}
		}
		
		attrVct.clear();
		valVct.clear();
		return eia;
	}

    /**************************************
     * Get long description and navigation name for specified entity
     * @param item
     * @return
     * @throws SQLException
     * @throws MiddlewareException
     */
	protected String getLD_NDN(EntityItem item) throws SQLException, MiddlewareException    {
    	return item.getEntityGroup().getLongDescription()+" &quot;"+getNavigationName(item)+"&quot;";
    }
    /************************************
     * @param item
     * @param attrCode
     * @return
     */
    protected String getLD_Value(EntityItem item, String attrCode)   {
    	return PokUtils.getAttributeDescription(item.getEntityGroup(), attrCode, attrCode)+": "+
    		PokUtils.getAttributeValue(item, attrCode, ",", PokUtils.DEFNOTPOPULATED, false);
    }

    /**********************************
    * used for error output
    * Prefix with LD(EntityType) NDN(EntityType) of the EntityType that the ABR is checking
    * (root EntityType)
    *
    * The entire message should be prefixed with 'Error: '
    *
    */
    protected void addError(String errCode, Object args[])
    {
		EntityGroup eGrp = m_elist.getParentEntityGroup();
		setReturnCode(FAIL);

		//ERROR_PREFIX = Error: &quot;{0} {1}&quot;
		MessageFormat msgf = new MessageFormat(getBundle().getString("ERROR_PREFIX"));
		Object args2[] = new Object[2];
		args2[0] = eGrp.getLongDescription();
		args2[1] = navName;

		addMessage(msgf.format(args2), errCode, args);

	}
    /**********************************
    * used for warning or error output
    *
    */
    protected void addMessage(String msgPrefix, String errCode, Object args[])
    {
		String msg = rsBundle.getString(errCode);
		// get message to output
		if (args!=null){
			MessageFormat msgf = new MessageFormat(msg);
			msg = msgf.format(args);
		}

		addOutput(msgPrefix+" "+msg);
	}
	/**********************************
	 * add msg to report output
	 * @param msg
	 */
    protected void addOutput(String msg) { rptSb.append("<p>"+msg+"</p>"+NEWLINE);}

    /**
     * @param level
     * @param msg
     */
    protected void addHeading(int level, String msg) { rptSb.append("<h"+level+">"+msg+"</h"+level+">"+NEWLINE);}
    
	/**********************************
	 * add debug info as html comment
	 * @param msg
	 */
	protected void addDebug(String msg) { rptSb.append("<!-- "+msg+" -->"+NEWLINE);}
	
	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#dereference()
	 */
	public void dereference(){ 
        metaTbl.clear();
        metaTbl = null;
        navName = null;
        rptSb = null;
        rsBundle = null;
        args = null;

        strCurrentDate = null;
        offCountry = null;
    	offCountryFlag = null;
    	
        super.dereference();
	}

	/**********************************************************************************
	 *  Get Name based on navigation attributes for specified entity
	 *
	 *@return java.lang.String
	 */
	protected String getNavigationName(EntityItem theItem) throws java.sql.SQLException, MiddlewareException
	{
		StringBuffer navName = new StringBuffer();
		// NAME is navigate attributes
		// check hashtable to see if we already got this meta
		EANList metaList = (EANList)metaTbl.get(theItem.getEntityType());
		if (metaList==null)
		{
			EntityGroup eg = new EntityGroup(null, m_db, m_prof, theItem.getEntityType(), "Navigate");
			metaList = eg.getMetaAttribute();  // iterator does not maintain navigate order
			metaTbl.put(theItem.getEntityType(), metaList);
		}
		for (int ii=0; ii<metaList.size(); ii++)
		{
			EANMetaAttribute ma = (EANMetaAttribute)metaList.getAt(ii);
			navName.append(PokUtils.getAttributeValue(theItem, ma.getAttributeCode(),", ", "", false));
			if (ii+1<metaList.size()){
				navName.append(" ");
			}
		}

		return navName.toString();
	}

	/**
	 * getRevision
	 *
	 * @return
	 * @author Owner
	 */
	public String getRevision() {
		return "$Revision: 1.2 $";
	}

}
