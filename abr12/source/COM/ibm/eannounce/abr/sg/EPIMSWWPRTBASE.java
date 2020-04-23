// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007, 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.util.*;

import java.util.*;
import java.text.*;


/**********************************************************************************
* EPIMSWWPRTBASE class used to handle xml generation for EPIMS or WWPRT by specific entitytype
* from "SG FS ABR ePIMS Notification 20080407.doc"
*
*/
// EPIMSWWPRTBASE.java,v
// Revision 1.9  2010/07/30 11:19:33  wendy
// updated debug msg
//
// Revision 1.8  2008/06/24 19:52:56  wendy
// CQ00006088-WI - LA CTO Support - The requirement is to not feed LA products to ePIMS.
//
// Revision 1.7  2008/04/14 17:58:21  wendy
// Made a few methods public for EPWQGenerator access
//
// Revision 1.6  2008/04/08 12:26:45  wendy
// MN 35084789 - support resend of lost notification messages.
// and MN 35178533 - ePIMS lost some geography data
// "SG FS ABR WWPRT Notification 20080407.doc"
// "SG FS ABR ePIMS Notification 20080407.doc"
//
// Revision 1.5  2008/01/30 19:39:14  wendy
// Cleanup RSA warnings
//
// Revision 1.4  2008/01/20 23:19:40  wendy
// Added check for domains
//
// Revision 1.3  2007/12/03 19:26:18  wendy
// Save key with attribute values
//
// Revision 1.2  2007/11/30 22:30:43  wendy
// Added support for announcement
//
// Revision 1.1  2007/11/28 22:56:27  wendy
// Init for WWPRT ABRs
//
//
public abstract class EPIMSWWPRTBASE extends PokBaseABR
{
    private StringBuffer rptSb = new StringBuffer();
    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);
    protected static final String STATUS_FINAL = "0020";

    private Vector finalDtsVct;
    private boolean bdomainInList = false;

    private ResourceBundle rsBundle = null;
    private Hashtable metaTbl = new Hashtable();
    private String navName = "";

    private String xmlgen = "Not required";  // set to Success or Failed or Not required

    private static final Hashtable NDN_TBL;
    private static final Hashtable STATUSATTR_TBL;
    static{
        NDN_TBL = new Hashtable();
        STATUSATTR_TBL = new Hashtable();
        STATUSATTR_TBL.put("ANNOUNCEMENT","ANNSTATUS");

    /*
The NDN of PRODSTRUCT is:
MODEL.MACHTYPEATR
MODEL.MODELATR
MODEL.COFCAT
MODEL.COFSUBCAT
MODEL.COFGRP
MODEL.COFSUBGRP
FEATURE.FEATURECODE
    */
        NDN ndnMdl = new NDN("MODEL", "(TM)");
        ndnMdl.addAttr("MACHTYPEATR");
        ndnMdl.addAttr("MODELATR");
        ndnMdl.addAttr("COFCAT");
        ndnMdl.addAttr("COFSUBCAT");
        ndnMdl.addAttr("COFGRP");
        ndnMdl.addAttr("COFSUBGRP");
        NDN ndnFc = new NDN("FEATURE", "(FC)");
        ndnFc.addAttr("FEATURECODE");
        ndnMdl.setNext(ndnFc);
        NDN_TBL.put("PRODSTRUCT",ndnMdl);
    /*
The NDN of SWPRODSTRUCT is:
MODEL.MACHTYPEATR
MODEL.MODELATR
MODEL.COFCAT
MODEL.COFSUBCAT
MODEL.COFGRP
MODEL.COFSUBGRP
SWFEATURE.FEATURECODE

    */
        ndnMdl = new NDN("MODEL", "(TM)");
        ndnMdl.addAttr("MACHTYPEATR");
        ndnMdl.addAttr("MODELATR");
        ndnMdl.addAttr("COFCAT");
        ndnMdl.addAttr("COFSUBCAT");
        ndnMdl.addAttr("COFGRP");
        ndnMdl.addAttr("COFSUBGRP");
        ndnFc = new NDN("SWFEATURE", "(FC)");
        ndnFc.addAttr("FEATURECODE");
        ndnMdl.setNext(ndnFc);
        NDN_TBL.put("SWPRODSTRUCT",ndnMdl);
    }

    /**********************************
    * get the resource bundle
    */
    protected ResourceBundle getBundle() {
        return rsBundle;
    }

    /**********************************
     *  Execute ABR.
     *
     */
    public void execute_run()
    {
        /*
        The Report should identify:
            USERID (USERTOKEN)
            Role
            Workgroup
            Date/Time
            EntityType LongDescription
            ABRSTATUS that was set
            Data Quality Errors (if any) as described in other sections
            An indication if XML generation/feed was applicable
            If XML was applicable, an indication of whether it was successfully sent
        */
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
             "<tr><th>XML generation: </th><td>{5}</td></tr>"+NEWLINE+
             "</table>"+NEWLINE+
            "<!-- {6} -->" + NEWLINE;

        MessageFormat msgf;
        String rootDesc="";
        String abrversion="";
        EPIMSABRBase epimsAbr=null;
        Object[] args = new String[10];
        println(EACustom.getDocTypeHtml()); //Output the doctype and html
        try
        {
            long startTime = System.currentTimeMillis();

            start_ABRBuild(false); // dont pull VE yet, have to get name from EPIMSABR class

            // get VE name from it
            String VEname = "dummy";//just need root for timestamps now but prodstruct and swprodstruct
            // need fc and mdl for nav name
            if (getEntityType().equals("PRODSTRUCT")){
				VEname = "EXRPT3FM"; //get VE with just ps
			}else if (getEntityType().equals("SWPRODSTRUCT")){
				VEname = "DQVESWPRODSTRUCT2";// get VE with just swps
			}

            // create VE
            m_elist = m_db.getEntityList(m_prof,
                new ExtractActionItem(null, m_db,m_prof,VEname),
                new EntityItem[] { new EntityItem(null, m_prof, getEntityType(), getEntityID()) });

            addDebug("Time to get VE: "+(System.currentTimeMillis()-startTime)+" (mseconds)");

			String statusattr = (String)STATUSATTR_TBL.get(getEntityType());
			if (statusattr==null){
				statusattr = "STATUS";
			}

            finalDtsVct = getChangeTimes(statusattr, STATUS_FINAL);

            setControlBlock(); // needed for attribute updates

            //get properties file for the base class
            rsBundle = ResourceBundle.getBundle(EPIMSWWPRTBASE.class.getName(), getLocale(m_prof.getReadLanguage().getNLSID()));

            // debug display list of groups
            addDebug("DEBUG: "+getShortClassName(getClass())+" entered for " + getEntityType() + getEntityID()+
                " extract: "+VEname+" using DTS: "+m_prof.getValOn()+NEWLINE + PokUtils.outputList(m_elist));

            //Default set to pass
            setReturnCode(PASS);

            //NAME is navigate attributes
            navName = getNavigationName();

            // get root from VE
            rootDesc = m_elist.getParentEntityGroup().getLongDescription();

// fixme remove this.. avoid msgs to userid for testing
//setCreateDGEntity(false);
        	EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

            // check if pdhdomain is in domain list for this ABR
            domainNeedsChecks(rootEntity);

			if (bdomainInList){
				// find class to instantiate based on entitytype
				// Load the specified ABR class in preparation for execution
				String clsname = getSimpleABRName();
				if (clsname!=null){
					epimsAbr = (EPIMSABRBase) Class.forName(clsname).newInstance();

					// call execute passing ve and rptsb
					epimsAbr.execute_run(this);
					xmlgen = epimsAbr.getXMLGenMsg();
					abrversion = getShortClassName(epimsAbr.getClass())+" "+epimsAbr.getVersion();
				}else{
					addError(getShortClassName(getClass())+" does not support "+getEntityType());
				}

				// no report needed if all is ok
				if (getReturnCode()==PASS){
					setCreateDGEntity(false);
				}
			}else{
				xmlgen = getBundle().getString("DOMAIN_NOT_LISTED"); //  = Domain was not in the list of supported Domains. Execution bypassed.
			}

            finalDtsVct.clear();
        }
        catch(Throwable exc)
        {
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
        if (epimsAbr!=null){
            args[0] = getShortClassName(epimsAbr.getClass());
        }else{
            args[0] = getShortClassName(getClass());
        }
        args[1] = navName;
        String header1 = msgf.format(args);
        msgf = new MessageFormat(HEADER2);
        args[0] = m_prof.getOPName();
        args[1] = m_prof.getRoleDescription();
        args[2] = m_prof.getWGName();
        args[3] = getNow();
        args[4] = rootDesc;
        args[5] = xmlgen;
        args[6] = abrversion+" "+getABRVersion();

        rptSb.insert(0, header1+msgf.format(args) + NEWLINE);

        println(rptSb.toString()); // Output the Report
        printDGSubmitString();
        println(EACustom.getTOUDiv());
        buildReportFooter(); // Print </html>

        metaTbl.clear();
    }

	/**********************************************************************************
	 * get timestamp for queueing this ABR
	 * from "SG FS ABR ePIMS Notification 20080407.doc" and "SG FS ABR WWPRT Notification 20080407.doc"
	 * MN 35178533 - ePIMS lost some geography data. A change to the AVAILability to cause it
	 * to reflow did not work. Although a new notification was sent, the DTS uses was the last DTS to
	 * Final which was missing the data. This fixes the problem with DTS. A work around of editing the
	 * LSEO got past the SEV=1 and this is the functional specification fix which requires a code update.
	 */
	private String getABRQueuedTime()
	throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		String attCode = m_abri.getABRCode();
		EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

		addDebug("getABRQueuedTime entered for "+rootEntity.getKey()+" "+attCode);
		EANAttribute att = rootEntity.getAttribute(attCode);
		if (att != null) {
			AttributeChangeHistoryGroup achg = new AttributeChangeHistoryGroup(m_db, m_prof, att);
			if (achg.getChangeHistoryItemCount()>1){
				// last chghistory is the current one(in process), -2 is queued
				int i=achg.getChangeHistoryItemCount()-2;
				AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem)achg.getChangeHistoryItem(i);
				addDebug("getABRQueuedTime ["+i+"] isActive: "+
						achi.isActive()+" isValid: "+achi.isValid()+" chgdate: "+
						achi.getChangeDate()+" flagcode: "+achi.getFlagCode());

				return achi.getChangeDate();
			} // has history items
			else {
				addDebug("getABRQueuedTime for "+rootEntity.getKey()+" "+attCode+" has no history");
			}
		} else {
			addDebug("getABRQueuedTime for "+rootEntity.getKey()+" "+attCode+" was null");
		}

		return getNow();
	}

    /**********************************
    * derived classes implement this to supply the name of the abr to instantiate
    */
	protected abstract String getSimpleABRName();

    /**********************************
    * generate string representation of attributes in the list for this entity
    */
	protected String generateString(EntityItem theItem, String[] attrlist){
		StringBuffer sb = new StringBuffer(theItem.getKey());
		if (attrlist !=null){
			for (int a=0; a<attrlist.length; a++){
				sb.append(":"+PokUtils.getAttributeValue(theItem, attrlist[a],", ", "", false));
			}
		}else{
           // addDebug("generateString: No list of 'attr of interest' found for "+theItem.getEntityType());
		}
		return sb.toString();
	}

	protected boolean isFirstFinal() { return firstFinal;}
	private boolean firstFinal = true;
    /**********************************************************************************
    * get timestamp(s) for attribute when attribute went to specified value
    * (only works for flag attributes)
    */
    protected Vector getChangeTimes(String attCode, String flagcode)
    throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
        Vector dtsVct = new Vector(1);
        Vector chiVct = new Vector(1);

        EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
        addDebug("getChangeTimes entered for "+rootEntity.getKey()+" "+attCode+" flag: "+flagcode);
        EANAttribute att = rootEntity.getAttribute(attCode);
        if (att != null) {
            AttributeChangeHistoryGroup achg = new AttributeChangeHistoryGroup(m_db,
                m_elist.getProfile(), att);
            if (achg.getChangeHistoryItemCount()>0){
                for (int i=achg.getChangeHistoryItemCount()-1; i>=0; i--)
                {
                    chiVct.add(achg.getChangeHistoryItem(i));
                }

                Collections.sort(chiVct, new ChiComparator()); // Multiflag attr require sort
                for (int i=0; i<chiVct.size(); i++){
                    AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem)chiVct.elementAt(i);
                    addDebug("getChangeTimes "+attCode+"["+i+"] isActive: "+
                        achi.isActive()+" isValid: "+achi.isValid()+" chgdate: "+
                        achi.getChangeDate()+" flagcode: "+achi.getFlagCode());
                    if (flagcode == null){ // just return last change dts
                        dtsVct.add(achi.getChangeDate());
                        break;
                    }
                    if (flagcode.equals(achi.getFlagCode())){
                        dtsVct.add(achi.getChangeDate());
                        if (dtsVct.size()==2){ // only need the last time and the one before that
                        	firstFinal = false; // second final here
                            break;
                        }
                    }
                }

                chiVct.clear();
            } // has history items
            addDebug("getChangeTimes Before using queued dts "+rootEntity.getKey()+" "+dtsVct);
            if (dtsVct.size()>0){
            	dtsVct.remove(0); //remove last final or only final MN 35178533
	            dtsVct.insertElementAt(getABRQueuedTime(), 0);//replace with queued dts MN 35178533
			}else{
				dtsVct.add(getABRQueuedTime());
			}
            addDebug("getChangeTimes using queued dts "+rootEntity.getKey()+" "+dtsVct);
        } else {// status attr !=null
            addDebug("ERROR: getChangeTimes for "+rootEntity.getKey()+" "+attCode+"  was null use queued time");
            dtsVct.add(getABRQueuedTime());
        }

        return dtsVct;
    }

    /**********************************
    * get entitylist
    */
    protected EntityList getEntityList() { return m_elist; }

    /**********************************
    * get last final timestamp - now it is the queued timestmp
    */
    public String getLastFinalDTS() { return (String)finalDtsVct.firstElement(); }

    /**********************************
    * get prior final timestamp
    */
    public String getPriorFinalDTS() {
        String prior = getLastFinalDTS();
        if (finalDtsVct.size()>1){
            prior = (String)finalDtsVct.lastElement();
        }
        return prior;
    }

    /**********************************
    * get database
    */
    public Database getDB() { return m_db; }

    /**********************************
    * add msg to report output
    */
    protected void addOutput(String msg) { rptSb.append("<p>"+msg+"</p>"+NEWLINE);}

    /**********************************
    * add debug info as html comment
    */
    public void addDebug(String msg) { rptSb.append("<!-- "+msg+" -->"+NEWLINE);}

    /**********************************
    * add error info and fail abr
    */
    public void addError(String msg) {
        addOutput(msg);
        setReturnCode(FAIL);
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
    * used for warning output
    * Prefix with LD(EntityType) NDN(EntityType) of the EntityType that the ABR is checking
    * (root EntityType)
    *
    * The entire message should be prefixed with 'Warning: '
    *
    */
    protected void addWarning(String errCode, Object args[])
    {
		EntityGroup eGrp = m_elist.getParentEntityGroup();

		//WARNING_PREFIX = Warning: &quot;{0} {1}&quot;
		MessageFormat msgf = new MessageFormat(getBundle().getString("WARNING_PREFIX"));
		Object args2[] = new Object[2];
		args2[0] = eGrp.getLongDescription();
		args2[1] = navName;

		addMessage(msgf.format(args2), errCode, args);
	}

    /**********************************
    * used for warning or error output
    *
    */
    private void addMessage(String msgPrefix, String errCode, Object args[])
    {
		String msg = getBundle().getString(errCode);
		// get message to output
		if (args!=null){
			MessageFormat msgf = new MessageFormat(msg);
			msg = msgf.format(args);
		}

		addOutput(msgPrefix+" "+msg);
	}

    /**********************************************************************************
    *  Get Name based on navigation attributes for root entity
    *
    *@return java.lang.String
    */
    private String getNavigationName() throws java.sql.SQLException, MiddlewareException
    {
        return getNavigationName(m_elist.getParentEntityGroup().getEntityItem(0));
    }

    /**********************************************************************************
    *  Get Name based on navigation attributes for specified entity
    *
    *@return java.lang.String
    */
    protected String getNavigationName(EntityItem theItem) throws java.sql.SQLException, MiddlewareException
    {
        StringBuffer navName = new StringBuffer();
        NDN ndn = (NDN)NDN_TBL.get(theItem.getEntityType());
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
        if (ndn!=null){ // must get other attr from parent and child entities
            StringBuffer sb = new StringBuffer();
            EntityItem ei = getNDNitem(theItem,ndn.getEntityType());
            if (ei!=null){
                sb.append("("+ndn.getTag());
                for (int y=0; y<ndn.getAttr().size(); y++){
                    String attrcode = ndn.getAttr().elementAt(y).toString();
                    sb.append(PokUtils.getAttributeValue(ei, attrcode,", ", "", false));
                    if (y+1<ndn.getAttr().size()){
                        sb.append(" ");
                    }
                }
                sb.append(") ");
            }else{
                addDebug("NO entity found for ndn.getEntityType(): "+ndn.getEntityType());
            }
            ndn = ndn.getNext();
            if (ndn !=null){
                ei = getNDNitem(theItem,ndn.getEntityType());
                if (ei!=null){
                    sb.append("("+ndn.getTag());
                    for (int y=0; y<ndn.getAttr().size(); y++){
                        String attrcode = ndn.getAttr().elementAt(y).toString();
                        sb.append(PokUtils.getAttributeValue(ei, attrcode,", ", "", false));
                        if (y+1<ndn.getAttr().size()){
                            sb.append(" ");
                        }
                    }
                    sb.append(") ");
                }else{
                    addDebug("NO entity found for next ndn.getEntityType(): "+ndn.getEntityType());
                }
            }
            navName.insert(0,sb.toString());
        } // end getting other entity info

        return navName.toString();
    }

    /**********************************************************************************
    * Find entity item to use for building the navigation display name when more then
    * one entity is needed, like for PRODSTRUCT
    *
    *@return EntityItem
    */
    private EntityItem getNDNitem(EntityItem theItem,String etype){
        for (int i=0; i<theItem.getDownLinkCount(); i++){
            EntityItem ent = (EntityItem)theItem.getDownLink(i);
            if (ent.getEntityType().equals(etype)){
                return ent;
            }
        }
        for (int i=0; i<theItem.getUpLinkCount(); i++){
            EntityItem ent = (EntityItem)theItem.getUpLink(i);
            if (ent.getEntityType().equals(etype)){
                return ent;
            }
        }
        return null;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
        return "1.9";
    }
    /***********************************************
    *  Get ABR description
    *
    *@return java.lang.String
    */
    public String getDescription()
    {
        return "EPIMSWWPRTBASE";
    }

    /***********************************************
    *  Sets the specified Flag Attribute on the Root Entity
    *
    *@param    profile Profile
    *@param    strAttributeCode The Flag Attribute Code
    *@param    strAttributeValue The Flag Attribute Value
    */
    protected void setFlagValue(String strAttributeCode, String strAttributeValue)
    throws java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException
    {
        logMessage(getDescription()+" ***** "+strAttributeCode+" set to: " + strAttributeValue);
        addDebug("setFlagValue entered for "+strAttributeCode+" set to: " + strAttributeValue);
        EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

		// if meta does not have this attribute, there is nothing to do
        EANMetaAttribute metaAttr = rootEntity.getEntityGroup().getMetaAttribute(strAttributeCode);
        if (metaAttr==null) {
			addDebug("setFlagValue: "+strAttributeCode+" was not in meta for "+rootEntity.getEntityType()+", nothing to do");
        	logMessage(getDescription()+" ***** "+strAttributeCode+" was not in meta for "+
        		rootEntity.getEntityType()+", nothing to do");
			return;
		}
        if(strAttributeValue != null)
        {
            if(strAttributeValue.equals(getAttributeFlagEnabledValue(rootEntity,strAttributeCode))){
                addDebug("setFlagValue "+rootEntity.getKey()+" "+strAttributeCode+
                    " already matches: " + strAttributeValue);
            }else{
                try
                {
                    if (m_cbOn==null){
                        setControlBlock(); // needed for attribute updates
                    }
                    ReturnEntityKey rek = new ReturnEntityKey(getEntityType(), getEntityID(), true);

                    SingleFlag sf = new SingleFlag (m_prof.getEnterprise(), rek.getEntityType(), rek.getEntityID(),
                        strAttributeCode, strAttributeValue, 1, m_cbOn);
                    Vector vctAtts = new Vector();
                    Vector vctReturnsEntityKeys = new Vector();
                    vctAtts.addElement(sf);
                    rek.m_vctAttributes = vctAtts;
                    vctReturnsEntityKeys.addElement(rek);

                    m_db.update(m_prof, vctReturnsEntityKeys, false, false);
                    addDebug(rootEntity.getKey()+" had "+strAttributeCode+" set to: " + strAttributeValue);
                }
                finally {
                    m_db.commit();
                    m_db.freeStatement();
                    m_db.isPending("finally after update in setflag value");
                }
            }
        }
    }

    /**********************************************************************************
    *  Get Locale based on NLSID
    *
    *@return java.util.Locale
    */
    public static Locale getLocale(int nlsID)
    {
        Locale locale = null;
        switch (nlsID)
        {
        case 1:
            locale = Locale.US;
            break;
        case 2:
            locale = Locale.GERMAN;
            break;
        case 3:
            locale = Locale.ITALIAN;
            break;
        case 4:
            locale = Locale.JAPANESE;
            break;
        case 5:
            locale = Locale.FRENCH;
            break;
        case 6:
            locale = new Locale("es", "ES");
            break;
        case 7:
            locale = Locale.UK;
            break;
        default:
            locale = Locale.US;
            break;
        }
        return locale;
    }

    /*************************************************************************************
    * Check the PDHDOMAIN
    * xseries and converged prod need DQ checks in the ABRs but the other domains like iseries don't
    * because those Brands do not want any checking, they do not use STATUS, they want no process
    * criteria apply if PDHDOMAIN = (0050) 'xSeries' or (0390) 'Converged Products'
    *@param item    EntityItem
    * domainInList set to true if matches one of these domains
    */
    private void domainNeedsChecks(EntityItem item)
    {
    	String domains = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getDomains(m_abri.getABRCode());
    	addDebug("domainNeedsChecks pdhdomains needing checks: "+domains);
		if (domains.equals("all")){
			bdomainInList = true;
		}else{
	        Set testSet = new HashSet();
			StringTokenizer st1 = new StringTokenizer(domains,",");
			while (st1.hasMoreTokens()) {
		        testSet.add(st1.nextToken());
			}
	        bdomainInList = PokUtils.contains(item, "PDHDOMAIN", testSet);
	        testSet.clear();
		}

        if (!bdomainInList){
            addDebug("PDHDOMAIN did not include "+domains+", execution is bypassed ["+
                PokUtils.getAttributeFlagValue(item, "PDHDOMAIN"));
        }
    }

    /**********************************
     * check for specified country in the abr's countrylist
     * CQ00006088-WI
 	 * 	LA CTO Support - The requirement is to not feed LA products to ePIMS.
 	 * This enhancement is to recognize a list of Countries for which ePIMS should be notified
 	 * and hence eAnnounce will not notify ePIMS about offerings that are only available in LA.
 	 * Since it is possible to have 'offerings' that are available in Countries that ePIMS needs
 	 * data and Countries that ePIMS does not handle, ePIMS will need to handle this case.
 	 *
     *@param item    EntityItem
     * @return boolean true if matches one of these countries
     */
    protected boolean checkABRCountryList(EntityItem item)
    {
    	boolean inlist = false;
     	String ctrylist = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getCountryList(m_abri.getABRCode());
     	addDebug("checkABRCountryList countrylist: "+ctrylist);
 		if (ctrylist.equals("all")){
 			inlist = true;
 		}else{
 	        Set testSet = new HashSet();
 			StringTokenizer st1 = new StringTokenizer(ctrylist,",");
 			while (st1.hasMoreTokens()) {
 		        testSet.add(st1.nextToken());
 			}
 			inlist = PokUtils.contains(item, "COUNTRYLIST", testSet);
 	        testSet.clear();
 		}

         if (!inlist){
             addDebug(item.getKey()+".COUNTRYLIST ["+
                 PokUtils.getAttributeValue(item, "COUNTRYLIST",", ", "", false)+
                 "] did not include "+ctrylist+", notification will not be sent ");
         }
         return inlist;
     }
    /**********************************************************************************
     * This class is used to sort ChangeHistoryItem based on timestamp
     */
    private class ChiComparator implements java.util.Comparator
    {
        public int compare(Object o1, Object o2) {
            ChangeHistoryItem chi1 = (ChangeHistoryItem)o1;
            ChangeHistoryItem chi2 = (ChangeHistoryItem)o2;
            return chi2.getChangeDate().compareTo(chi1.getChangeDate()); // in descending order
        }
    }

    // used to support getting navigation display name when other entities are needed
    private static class NDN {
        private String etype, tag;
        private NDN next;
        private Vector attrVct = new Vector();
        NDN(String t,String s){
            etype = t;
            tag = s;
        }
        String getTag() { return tag;}
        String getEntityType() { return etype;}
        Vector getAttr(){ return attrVct;}
        void addAttr(String a){
            attrVct.addElement(a);
        }
        void setNext(NDN n) { next = n;}
        NDN getNext() { return next;}
    }
}
