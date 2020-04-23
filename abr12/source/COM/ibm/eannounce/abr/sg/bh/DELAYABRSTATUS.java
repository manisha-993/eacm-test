//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2010  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.bh;


import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.*;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.ControlBlock;

import COM.ibm.opicmpdh.objects.SingleFlag;


/**************************************
 *BH FS ABR DelayedQueue 20100315.doc
 * needs new sp gbl8992
 * needs meta and actions

ABRDELAYEDQUEUE.ABRLASTRAN is editable and should not be- it is only for abr use
does ABRDELAYEDQUEUE.ABRVALUE have any rules?  abr requires it to have a value
ABRMINDELAY must be hours and minutes.. like 00:00, so it can only have a max of 5 chars and can't have letters.
this works
SG	Attribute/Test	ABRMINDELAY	TIME	Value	0	1980-01-01 00:00:00.0	9999-12-31 00:00:00.0	1980-01-01 00:00:00.0	9999-12-31 00:00:00.0	-1	-1	

 * Need meta 
 *    - missing attribute ABRDELAYEDQUEUE.ABRMINDELAY 
      - ABRDELAYEDQUEUE.ABRVALUE needs to be searchable
      - missing ABRVALUE[ABRXMLFEED]
      - missing all ABRDELAYEDQUEUE.DELAYABRSTATUS flags
      - All 'A' type attributes that must match ABRVALUE will need those flag(s) added to the 'A' attribute.
      for example ADSABRSTATUS

   Need search action for the cron  - SRDDQUEUE
 * 
 * The ABR is queued for a specific instance of ABRDELAYEDQUEUE and runs under TaskMaster.
 * 
 * The first thing the ABR does is to remember the DTS from Get NOW().
 * The ABR queries the PDH for all attributes of TYPE = A with a current value matching the ABRVALUE.
 * 
 * If ABRMINDELAY is not empty, then the ABR first checks the VALFROM for the matching ABRVALUE. 
 * If the VALFROM + ABRMINDELAY <= the remembered DTS from Get NOW(), then update the instance by setting 
 * that attributecode = 0020 (Queued).
 * 
 * If ABRMINDELAY is empty, then the ABR updates each instance by setting that attributecode = 0020 (Queued).
 * 
 * The ABR does not produce a Report.
 * 
 * Once the ABR has finished, it sets ABRLASTRAN equal to the DTS that it started and returns to TaskMaster as Passed.
 * 
 * There will be five instances of ABRDELAYEDQUEUE setup with the following values for ABRVALUE that 
 * must match the code that will be looked for:
 * ABRWAITODS
 * ABRENDOFDAY
 * ABRENDOFWEEK
 * ABRWAITODS2
 * ABRXMLFEED
 * 
 * DELAYABRSTATUS_class=COM.ibm.eannounce.abr.sg.bh.DELAYABRSTATUS
 * DELAYABRSTATUS_enabled=true
 * DELAYABRSTATUS_idler_class=A
 * DELAYABRSTATUS_keepfile=true
 * DELAYABRSTATUS_read_only=false
 */
//$Log: DELAYABRSTATUS.java,v $
//Revision 1.2  2014/01/13 13:53:28  wendy
//migration to V17
//
//Revision 1.1  2010/06/10 22:32:36  wendy
//Initial code
//
public class DELAYABRSTATUS extends PokBaseABR
{
    private StringBuffer rptSb = new StringBuffer();
    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);
    private Object[] args = new String[10];

    private Hashtable metaTbl = new Hashtable();
    private String navName = ""; 
	 
    private Vector vctReturnsEntityKeys = new Vector();
    private static final String ABR_QUEUED = "0020";

    /*
ABRDELAYEDQUEUE	ABRLASTRAN	T	Date Timestamp Last Run
ABRDELAYEDQUEUE	ABRVALUE	U	Value to Consider
ABRDELAYEDQUEUE	DELAYABRSTATUS	A	Delayed Queue ABR

ABRVALUE	ABRENDOFDAY	Wait until End of the Day	
ABRVALUE	ABRENDOFWEEK	Wait until End of the Week	
ABRVALUE	ABRWAITODS	Wait for Approved Data ODS	
ABRVALUE	ABRWAITODS2	Wait for Data ODS
     */

    /**********************************
     *  Execute ABR.
     */
    public void execute_run()
    {
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
             "<tr><th>Return code: </th><td>{5}</td></tr>"+NEWLINE +
             "</table>"+NEWLINE+
            "<!-- {6} -->" + NEWLINE;

        MessageFormat msgf;
        String rootDesc="";
        String abrversion="";

        println(EACustom.getDocTypeHtml()); //Output the doctype and html

        try
        {
        	long startTime = System.currentTimeMillis();
            //Default set to pass
            setReturnCode(PASS);
            
            start_ABRBuild(false); // pull VE
           
			//  The ABR does not produce a Report.
			setCreateDGEntity(false);
     
            //The first thing the ABR does is to remember the DTS from Get NOW().
            String strNow = m_db.getDates().getNow();       	
           
        	EntityGroup eg = m_db.getEntityGroup(m_prof, getEntityType(), "Edit");
			EntityItem rootItem = new EntityItem(eg, m_prof, m_db,getEntityType(), getEntityID());

			String abrValue = PokUtils.getAttributeFlagValue(rootItem, "ABRVALUE");
			//If ABRMINDELAY is empty, then assume a value of zero.
			String abrMinDelay = PokUtils.getAttributeValue(rootItem, "ABRMINDELAY","", "00:00", false);

			addDebug(getShortClassName(getClass())+" entered for " +rootItem.getKey()+
					" abrValue: "+abrValue+" abrMinDelay: "+abrMinDelay+" strNow "+strNow);
			if (abrMinDelay.length()>5){
				addError("ABRMINDELAY is invalid "+abrMinDelay);
			}
			if (abrValue == null){
				addError("ABRVALUE is invalid "+abrValue);
			}
			
			if(getReturnCode()==PASS) {
				ISOCalendar isoCalendar = new ISOCalendar(strNow);

				String updateMinTime = isoCalendar.getAdjustedDate(abrMinDelay); 
				addDebug(" updateMinTime "+updateMinTime);

				// run the query and create returnentitykeys for each abr that needs to be queued
				getDelayedEntities(abrValue, updateMinTime);

				addDebug("Time to do getDelayedEntities: "+Stopwatch.format(System.currentTimeMillis()-startTime));

				//NAME is navigate attributes
				navName = getNavigationName(rootItem);
				rootDesc = eg.getLongDescription()+" &quot;"+navName+"&quot;";

				//Once the ABR has finished, it sets ABRLASTRAN equal to the DTS that it started and returns to 
				//TaskMaster as Passed.
				setTextValue("ABRLASTRAN", strNow, rootItem);

				// update the pdh
				updatePDH();

				isoCalendar.dereference();
			}

            addDebug("Total Time: "+Stopwatch.format(System.currentTimeMillis()-startTime));
        }
        catch(Throwable exc) {
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
        finally
        {
            setDGTitle(navName);
            setDGRptName(getShortClassName(getClass()));
            setDGRptClass(getABRCode());
            // make sure the lock is released
            if(!isReadOnly())
            {
                clearSoftLock();
            }
        }
        
        //Print everything up to </html>
        //Insert Header into beginning of report
        msgf = new MessageFormat(HEADER);
        args[0] = getDescription();
        args[1] = navName;
        String header1 = msgf.format(args);
        msgf = new MessageFormat(HEADER2);
        args[0] = m_prof.getOPName();
        args[1] = m_prof.getRoleDescription();
        args[2] = m_prof.getWGName();
        args[3] = getNow();
        args[4] = rootDesc;
        args[5] = (this.getReturnCode()==PokBaseABR.PASS?"Passed":"Failed");
        args[6] = abrversion+" "+getABRVersion();

        rptSb.insert(0, header1+msgf.format(args) + NEWLINE);

        println(rptSb.toString()); // Output the Report
        printDGSubmitString();
        println(EACustom.getTOUDiv());
        buildReportFooter(); // Print </html>

        metaTbl.clear();
    }
 
    /******
     * @see COM.ibm.eannounce.abr.util.PokBaseABR#dereference()
     */
    public void dereference(){
    	super.dereference();

    	rptSb = null;
    	args = null;

        metaTbl = null;
        navName = null;
        if (vctReturnsEntityKeys!=null){
        	vctReturnsEntityKeys.clear();
            vctReturnsEntityKeys = null;
        }
    }
    /***********************************************
     *  Sets the specified Flag Attribute on the specified Entity
     *
     * @param strAttributeCode
     * @param entityType
     * @param entityid
     */
    private void queueABR(String strAttributeCode, String entityType, int entityid)
    {
    	addDebug("queueABR entered "+entityType+entityid+" for "+strAttributeCode);

    	addOutput("Set "+strAttributeCode+" on "+entityType+entityid+" to Queued.");
    	if (m_cbOn==null){
    		setControlBlock(); // needed for attribute updates
    	}
    	Vector vctAtts = null;
    	// look at each key to see if root is there yet
    	for (int i=0; i<vctReturnsEntityKeys.size(); i++){
    		ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
    		if (rek.getEntityID() == entityid &&
    				rek.getEntityType().equals(entityType)){
    			vctAtts = rek.m_vctAttributes;
    			break;
    		}
    	}
    	if (vctAtts ==null){
    		ReturnEntityKey rek = new ReturnEntityKey(entityType,entityid, true);
    		vctAtts = new Vector();
    		rek.m_vctAttributes = vctAtts;
    		vctReturnsEntityKeys.addElement(rek);
    	}

    	SingleFlag sf = new SingleFlag (m_prof.getEnterprise(), entityType, entityid,
    			strAttributeCode, ABR_QUEUED, 1, m_cbOn);

    	vctAtts.addElement(sf); 
    }
    /***********************************************
     * Sets the specified Text Attribute on the specified entity
     *
     *@param _sAttributeCode
     *@param _sAttributeValue
     *@param eitem
     */
    private void setTextValue(String _sAttributeCode, String _sAttributeValue,EntityItem eitem)
    {
    	addDebug("setTextValue entered for "+eitem.getKey()+" "+_sAttributeCode+" set to: " + _sAttributeValue);

    	// if meta does not have this attribute, there is nothing to do
    	EANMetaAttribute metaAttr = eitem.getEntityGroup().getMetaAttribute(_sAttributeCode);
    	if (metaAttr==null) {
    		addDebug("setTextValue: "+_sAttributeCode+" was not in meta for "+eitem.getEntityType()+", nothing to do");
    		logMessage(getDescription()+" ***** "+_sAttributeCode+" was not in meta for "+
    				eitem.getEntityType()+", nothing to do");
    		return;
    	}

    	if( _sAttributeValue != null ) {
    		if (m_cbOn==null){
    			setControlBlock(); // needed for attribute updates
    		}
    		ControlBlock cb = m_cbOn;
    		if (_sAttributeValue.length()==0){ // deactivation is now needed
    			EANAttribute att = eitem.getAttribute(_sAttributeCode);
    			String efffrom = att.getEffFrom();
    			cb = new ControlBlock(efffrom, efffrom, efffrom, efffrom, m_prof.getOPWGID());
    			_sAttributeValue = att.toString();
    			addDebug("setTextValue deactivating "+_sAttributeCode);
    		}
    		Vector vctAtts = null;
    		// look at each key to see if this item is there yet
    		for (int i=0; i<vctReturnsEntityKeys.size(); i++){
    			ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
    			if (rek.getEntityID() == eitem.getEntityID() &&
    					rek.getEntityType().equals(eitem.getEntityType())){
    				vctAtts = rek.m_vctAttributes;
    				break;
    			}
    		}
    		if (vctAtts ==null){
    			ReturnEntityKey rek = new ReturnEntityKey(eitem.getEntityType(),
    					eitem.getEntityID(), true);
    			vctAtts = new Vector();
    			rek.m_vctAttributes = vctAtts;
    			vctReturnsEntityKeys.addElement(rek);
    		}
    		COM.ibm.opicmpdh.objects.Text sf = new COM.ibm.opicmpdh.objects.Text(m_prof.getEnterprise(),
    				eitem.getEntityType(), eitem.getEntityID(), _sAttributeCode, _sAttributeValue, 1, cb);
    		vctAtts.addElement(sf);
    	}
    }
    /***********************************************
     * Update the PDH with the values in the vector, do all at once
     *
     */
    private void updatePDH()
    throws java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    java.rmi.RemoteException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    COM.ibm.eannounce.objects.EANBusinessRuleException
    {
    	logMessage(getDescription()+" updating PDH");
    	addDebug("updatePDH entered for vctReturnsEntityKeys: "+vctReturnsEntityKeys);

    	if(vctReturnsEntityKeys.size()>0) {
    		try {
    			m_db.update(m_prof, vctReturnsEntityKeys, false, false);
    		}
    		finally {
    			vctReturnsEntityKeys.clear();
    			m_db.commit();
    			m_db.freeStatement();
    			m_db.isPending("finally after updatePDH");
    		}
    	}
    }

	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABRVersion()
	 */
	public String getABRVersion() {
		return "$Revision: 1.2 $";
	}

	/* (non-Javadoc)
	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getDescription()
	 */
	public String getDescription() {
		return "DELAYABRSTATUS";
	}
	/**********************************
	 * add msg to report output
	 * @param msg
	 */
	private void addOutput(String msg) { rptSb.append("<p>"+msg+"</p>"+NEWLINE);}
	private void addError(String msg)
	{
		setReturnCode(FAIL);
		addOutput(msg);
	}

	/**********************************
	 * add debug info as html comment
	 * @param msg
	 */
	private void addDebug(String msg) { rptSb.append("<!-- "+msg+" -->"+NEWLINE);}

	/**********************************************************************************
	 *  Get Name based on navigation attributes for specified entity
	 *
	 *@return java.lang.String
	 */
    private String getNavigationName(EntityItem theItem) throws java.sql.SQLException, MiddlewareException
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
     * The ABR queries the PDH for all attributes of TYPE = A with a current value matching the ABRVALUE.
     * 
     * If ABRMINDELAY is not empty, then the ABR first checks the VALFROM for the matching ABRVALUE. 
     * If the VALFROM + ABRMINDELAY <= the remembered DTS from Get NOW(), then update the instance by 
     * setting that attributecode = 0020 (Queued).
     * 
     * If ABRMINDELAY is empty, then the ABR updates each instance by setting that attributecode = 0020 (Queued).
     * 
     * @param attrValue
     * @param updateMinTime
     * @throws MiddlewareException
     * @throws SQLException
     */
    private void getDelayedEntities(String attrValue, String updateMinTime) throws MiddlewareException, SQLException
    {
    	java.sql.ResultSet rs = null;
    	try {
    		ReturnStatus returnStatus = new ReturnStatus( -1);
    		rs = m_db.callGBL8992(returnStatus, m_prof.getEnterprise(), attrValue,
    				m_prof.getValOn(), m_prof.getEffOn());
  
    		while(rs.next()) {
				int entityid = rs.getInt(1);
				String entityType = rs.getString(2).trim();
				String attrCode = rs.getString(3).trim();
				String valfrom = rs.getString(4).trim();
				valfrom = valfrom.replace(' ', '-'); // convert from 2010-06-10 17:41:16.451848
				valfrom = valfrom.replace(':', '.'); //           to 2010-06-10-17.27.11.739476
				addDebug("getDelayedEntities callGBL8992 answer: " + entityid  + ":" +entityType  + ":"+attrCode+":"+valfrom);
				if (valfrom.compareTo(updateMinTime)<=0){
					queueABR(attrCode, entityType, entityid);
				}else{
					addDebug("getDelayedEntities " + entityid  + ":" +entityType  + " has not aged long enough");
					
				}
			}
    	}
    	finally {
    		if (rs !=null){
    			rs.close();
    			rs = null;
    		}
    		m_db.commit();
    		m_db.freeStatement();
    		m_db.isPending();
    	}
	}
 
	// used to modify entity deletion date
	private class ISOCalendar
	{
		private GregorianCalendar calendar = new GregorianCalendar(); // doesnt' seem like any DateFormat will parse an ISO date
		private String microSecStr;
		ISOCalendar(String isoDate)
		{
			calendar.set(Calendar.YEAR,Integer.parseInt(isoDate.substring(0,4)));
			calendar.set(Calendar.MONTH,Integer.parseInt(isoDate.substring(5,7))-1); // months are counted from 0
			calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(isoDate.substring(8,10)));
			calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(isoDate.substring(11,13)));
			calendar.set(Calendar.MINUTE,Integer.parseInt(isoDate.substring(14,16)));
			calendar.set(Calendar.SECOND,Integer.parseInt(isoDate.substring(17,19)));
			calendar.set(Calendar.MILLISECOND,0); // really want to set MICROSECOND but it doesn't support it
			microSecStr = isoDate.substring(20);	
		}
		void dereference(){
			microSecStr = null;
			calendar = null;
		}
	   // long getTimeInMillis()  {
	    	 //return calendar.getTimeInMillis();  // java 1.4 only!!
	     //   return calendar.getTime().getTime();
	   // }
	    
		/*
		 * ABRMINDELAY is the minimum delay (TIME = HH:MM) since the value was set (VALFROM) 
		 * before changing the value to Queued (0020).
		 */
		String getAdjustedDate(String minDelay)
		{
			int mins =0;
			int colon = minDelay.indexOf(':');
			if(colon==-1){
				mins = Integer.parseInt(minDelay);
				addDebug("getAdjustedDate minDelay "+minDelay+" converted mins "+mins);
			}else{
				int hrs = Integer.parseInt(minDelay.substring(0,colon));
				mins = Integer.parseInt(minDelay.substring(colon+1));
				addDebug("getAdjustedDate minDelay "+minDelay+" converted hrs "+hrs+" mins "+mins);
				mins += (hrs*60);
			}
			
			// must manipulate date, subtract X minutes
			calendar.add(Calendar.MINUTE, -mins);
			
			// build new date string
			StringBuffer datesb = new StringBuffer(calendar.get(Calendar.YEAR)+"-");
			int dts = calendar.get(Calendar.MONTH)+1; // months are counted from 0
			if (dts<10) datesb.append("0");
			datesb.append(dts+"-");
			dts = calendar.get(Calendar.DAY_OF_MONTH);
			if (dts<10) datesb.append("0");
			datesb.append(dts+"-");
			dts = calendar.get(Calendar.HOUR_OF_DAY);
			if (dts<10) datesb.append("0");
			datesb.append(dts+".");
			dts = calendar.get(Calendar.MINUTE);
			if (dts<10) datesb.append("0");
			datesb.append(dts+".");
			dts = calendar.get(Calendar.SECOND);
			if (dts<10) datesb.append("0");
			datesb.append(dts+"."+microSecStr);

			return datesb.toString();
		}
	}
}
