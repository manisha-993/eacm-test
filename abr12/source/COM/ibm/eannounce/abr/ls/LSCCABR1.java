//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2001, 2006  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
 * <pre>In-Country Course Validation and Work Flow initiation.
 *
 * Change History (most recent at top)
 * $Log: LSCCABR1.java,v $
 * Revision 1.30  2014/01/13 13:50:51  wendy
 * migration to V17
 *
 * Revision 1.29  2006/06/26 12:42:35  chris
 * moved call to EACustom.getTOUDiv()
 *
 * Revision 1.28  2006/03/13 19:05:44  couto
 * Fixed copyright info.
 *
 * Revision 1.27  2006/03/07 14:22:14  couto
 * Fixed wrong if value.
 *
 * Revision 1.26  2006/03/02 13:26:20  couto
 * Changed layout, using EACustom methods. Changed ul position (put it inside the method).
 * Fixed br tags. Chaged font tags. Fixed multiple return statements
 *
 * Revision 1.25  2006/01/23 17:25:15  joan
 * Jtest
 *
 * Revision 1.24  2006/01/22 18:28:29  joan
 * changes for Jtest
 *
 * Revision 1.23  2005/01/26 23:23:45  joan
 * changes for Jtest
 *
 * Revision 1.22  2004/09/15 18:33:09  wendy
 * CR0715044211 to set Catalog Editing status
 *
 * Revision 1.21  2004/06/09 17:55:49  chris
 * Fix for MN19443595 to set LSCRSTAAUD
 *
 * Revision 1.20  2004/06/03 13:39:33  chris
 * Changes for CR0519045436 Must have LSMAT when LSCRSDELIVERY=Concerence
 *
 * Revision 1.19  2004/05/27 22:03:40  bala
 * executeaction to throw WorkflowException (compiler error)
 *
 * Revision 1.18  2004/05/13 19:04:17  chris
 * CR Demo Changes
 *
 * Revision 1.17  2004/03/09 15:58:01  chris
 * Changes for CR0701025842, CR1218022919, CR050902204
 *
 * Revision 1.16  2003/12/02 20:19:45  chris
 * Spec changes related to feedbacks 53243:653AE1 and 53251:75170B
 *
 * Revision 1.15  2003/12/02 12:03:50  chris
 * Fix for FB 53250:74AF86
 *
 * Revision 1.14  2003/11/21 21:04:57  chris
 * Possible fix for 53020, 53021,53023
 *
 * Revision 1.13  2003/11/20 15:43:21  chris
 * Fix for feedback 53061:59B65D
 *
 * Revision 1.12  2003/11/12 20:35:27  chris
 * put Revision back
 *
 * Revision 1.11  2003/11/06 22:01:16  bala
 * EC drop
 *
 * Revision 1.6  2003/10/30 20:31:49  cstolpe
 * Updated search action name and changed setDGTitle
 *
 * Revision 1.5  2003/10/28 20:58:57  cstolpe
 * setRptClass in all and Linking in ABR 1
 *
 * Revision 1.4  2003/10/23 19:23:58  cstolpe
 * Check lifecycle and do VELOCK
 *
 * Revision 1.3  2003/10/20 19:26:53  cstolpe
 * latest
 *
 * Revision 1.2  2003/10/15 19:35:25  cstolpe
 * Latest Updates
 *
 * Revision 1.1  2003/10/09 18:51:54  cstolpe
 * Initial LS drop
 *
 * </pre>
 * @author     Christopher Stolpe
 * @created    October 8, 2001
 */
package COM.ibm.eannounce.abr.ls;

//import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.*;
//import COM.ibm.eannounce.objects.WorkflowException;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;

import java.util.*;
import java.text.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Administrator
 */
public class LSCCABR1 extends PokBaseABR {
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2001, 2006  All Rights Reserved.";
	
    private String strCAEdit = "";
    
    /**
     *  Description of the Method
     */
    public void execute_run() {
        try {
            Object[] mfParms = new String[10];
            MessageFormat mfOut = new MessageFormat("<p class=\"ibm-intro ibm-alternate-three\"><em>{0}</em></p><p><b>Date: </b>{1}<br /><b>User: </b>{2} ({3})<br /><b>Description: </b>{4}</p><!-- {5} -->");
            int iReturnCode = PASS;
            StringBuffer sb = new StringBuffer();
            EntityGroup eg = null;
            DeleteActionItem dai = null;
            EntityItem[] aItems = null;
            start_ABRBuild();
            // NAME is navigate attributes
            setDGTitle(getDGTitle(true));

            mfParms[0] = getShortClassName(getClass());
            mfParms[1] = getNow();
            mfParms[2] = m_prof.getOPName();
            mfParms[3] = m_prof.getRoleDescription();
            mfParms[4] = getDescription();
            mfParms[5] = getABRVersion();
            println(EACustom.getDocTypeHtml());
            println("<head>");
            println(EACustom.getMetaTags(getDescription()));
			println(EACustom.getCSS());
			println(EACustom.getTitle(getShortClassName(getClass())));
			println("</head>");
			println("<body id=\"ibm-com\">");
			println(EACustom.getMastheadDiv());
            
            println(mfOut.format(mfParms));

            sb.append(getEntityDescription(getEntityType()));
            sb.append(" Code: ");
            sb.append(getAttributeValue(getEntityType(), getEntityID(), "LSCRSID", DEF_NOT_POPULATED_HTML));
            sb.append("<br />");
            println(sb.toString());

            sb = new StringBuffer();
            strCAEdit = getAttributeFlagEnabledValue(getRootEntityType(), getRootEntityID(), "LSCCCATEDITSTATUS","");
            adjustCatalogEditStatus(sb); //CR0715044211
            println("<!-- "+sb.toString()+" -->");

            //if (checkFlagValues("LSCCLIFECYCLE", XR_LSCCLIFECYCLE_Available) ||
            //    checkFlagValues("LSCCLIFECYCLE", XR_LSCCLIFECYCLE_Retired))
            //{
            //    println("<br />LSCCABR1 not processed.  Current Lifecycle Status is not appropriate for this action.<br />");
            //    iReturnCode = FAIL;
            //}
            //else {
                // Do we have a relator? to OP?
            eg = m_elist.getEntityGroup("LSCCOP");
            if (eg.getEntityItemCount() > 0) {
                // There is an OP linked to this course
                // Is the OP a different person?
                EntityItem eiCCOP = eg.getEntityItem(0);
                EntityItem eiOP = (EntityItem) eiCCOP.getDownLink(0);
                EANAttribute att = eiOP.getAttribute("USERTOKEN");
                if (att != null && att instanceof EANTextAttribute) {
                    if (m_prof.getEmailAddress().equals(att.get())) {
                        // The submitter is already linked to this course
                        // Don't need to do anything
                        println("<!-- " + att.get() + " is already linked -->");
                    }
                    else {
                        // This is a new submitter
                        // unlink the old submitter and link the new one
                        linkToOP("LINKCCOP", "SRGLOP");
                        dai = new DeleteActionItem(null, m_db, m_prof, "DELCCOP");
                        aItems = new EntityItem[] { eiCCOP };
                        dai.setEntityItems(aItems);
                        m_db.executeAction(m_prof, dai);
                    }
                }
            }
            else {
                // No current OP associated with course
                // need to link to the OP for the submitter
                linkToOP("LINKCCOP", "SRGLOP");
            }
            // check for all necessary relators
            m_bOutputAsList = true;
            println("<b>Relator check:</b>" + NEW_LINE + "<ul>");
            if (!checkRelators()) {
                iReturnCode = FAIL;
            }
            else {
                println("<li>All required relators are linked.</li>");
            }

            // Check Attributes
            println("</ul>" + NEW_LINE + "<b>Attribute check:</b>" + NEW_LINE + "<ul>");
            if (!verifyAttributes()) {
                iReturnCode = FAIL;
            }
            //else {
                //println("<li>All required attributes are populated.</li>");
            //}
            println("</ul>" + NEW_LINE + "<b>Price Check:</b>" + NEW_LINE + "<ul>");
            if (!checkNewPriceEffictiveDates()) {
                iReturnCode = FAIL;
            }
            else {
                println("<li>The New Price Effective date is earlier than the Course Expiration date.</li>");
            }
            println("</ul>" + NEW_LINE + "<b>Volume Price Display Order Check:</b>" + NEW_LINE);
            if (!checkVPCDisplayOrder()) {
                iReturnCode = FAIL;
            }
            m_bOutputAsList = false;

            updateStatus(iReturnCode);
            //}

            if (iReturnCode == PASS) {
                //MSG_IAB2009I = "%1# is now available for use.";
                printMessage(MSG_IAB2009I, new String[]{getABREntityDesc(getEntityType(), getEntityID())});
            }

            sb = new StringBuffer();
            sb.append(getShortClassName(getClass()));
            sb.append(" has ");
            sb.append((iReturnCode == PASS) ? "Passed" : "Failed");
            sb.append(".<br />");
            println(sb.toString());

            setReturnCode(iReturnCode);
            setDGTitle(getDGTitle(false));
        }
        catch (LockPDHEntityException le) {
            setReturnCode(FAIL);
            println("<h3 style=\"color:#c00; font-weight:bold;\">" +
                    ERR_IAB1007E +
                    "<br />" +
                    le.getMessage() +
                    "</h3>");
            logError(le.getMessage());
        }
        catch (UpdatePDHEntityException le) {
            setReturnCode(FAIL);
            println("<h3 style=\"color:#c00; font-weight:bold;\">UpdatePDH error: " +
                    le.getMessage() +
                    "</h3>");
            logError(le.getMessage());
        }
        catch(Throwable exc) {
            // Report this error to both the datbase log and the PrintWriter
			java.io.StringWriter exBuf = new java.io.StringWriter();
            println("Error in " + m_abri.getABRCode() + ":" + exc.getMessage());
            println("" + exc);
            
            exc.printStackTrace(new java.io.PrintWriter(exBuf));
            println("<pre>");
            println(exBuf.getBuffer().toString());
            println("</pre>");
            setReturnCode(FAIL);
        }
        finally {
            setDGRptName(getShortClassName(getClass()));
            setDGRptClass("LSCCABR");
            // make sure the lock is released
            if (!isReadOnly()) {
                clearSoftLock();
            }
        }
        
		//Print everything up to </html>
		println(EACustom.getTOUDiv());
		printDGSubmitString();
		buildReportFooter();    // Print </html>
    }

    /**********************************************
    * CR0715044211
    * There are two ITES business rules concerning Catalog Editing and Advertising Method. The first rule is that if the
    * Course has an Advertising Method of Internet or Hardcopy Catalog then Catalog Editing needs to be performed. The
    * Second rule is that if the existing course has been Edited by a Catalog Editor then its status should remain as
    * "Edited" (until an update to the course description is performed) even if the Advertising Method changes to
    * No Advertising  allowing for a delisting of a course in the Web Catalog.
    *
    * If the Advertising Method which is a Multiple Flag Field does not have the value of  1 Internet or 2 Hardcopy Catalog
    * and the Catalog Editing Status is not "Edited" then set the Catalog Editing Status to "Editing Not Required".
    * A workflow action will need to be created for use solely by ABR1 to set the Catalog Editing Status to "Editing Not Required"
    *
    * LSCRSADVERTISING
    * FlagCode:0010:ShortDesc:1:LongDesc:Internet
    * FlagCode:0020:ShortDesc:2:LongDesc:Hardcopy Catalog
    * FlagCode:0030:ShortDesc:3:LongDesc:Intranet
    * FlagCode:0040:ShortDesc:4:LongDesc:OI
    * FlagCode:0050:ShortDesc:5:LongDesc:CD ROM:
    * FlagCode:0060:ShortDesc:6:LongDesc:No Advertising At This Time
    *
    * LSCCCATEDITSTATUS
    * FlagCode:0010:LongDesc:Not Edited
    * FlagCode:0020:LongDesc:Editing Not Required
    * FlagCode:0030:LongDesc:Requires Catalog  Editing
    * FlagCode:0040:LongDesc:Under Edit
    * FlagCode:0050:LongDesc:Edited
    * FlagCode:0060:LongDesc:Denied
    */
    private void adjustCatalogEditStatus(StringBuffer traceSb)     throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.eannounce.objects.WorkflowException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
		boolean finish = false;
		String advMethod = null;
		String actionName = "WFLSCCEDITNOTREQ";
        String catStatusAttr = getRootEntityType()+"CATEDITSTATUS";
        if (strCAEdit.equals("0020"))
        {
            traceSb.append("cateditstatus flag: "+strCAEdit+" attval: "+
                getAttributeValue(getRootEntityType(), getRootEntityID(),catStatusAttr)+" is already set, no changes needed." + NEW_LINE + "");
            //return;
            finish = true;
        }
        
        if (!finish) {

	        advMethod = getAttributeFlagEnabledValue(getRootEntityType(), getRootEntityID(),"LSCRSADVERTISING", "");
	        traceSb.append("advmethod flag: "+advMethod+" attrval: "+
	            getAttributeValue(getRootEntityType(), getRootEntityID(),"LSCRSADVERTISING")+"" + NEW_LINE + "");
	        traceSb.append("cateditstatus flag: "+strCAEdit+" attval: "+
	            getAttributeValue(getRootEntityType(), getRootEntityID(),catStatusAttr)+"" + NEW_LINE + "");
	
	        if (((advMethod.indexOf("0010")!=-1) ||  // is Internet
	                (advMethod.indexOf("0020")!=-1)))   // or Hardcopy
	        {
	            traceSb.append("LSCRSADVERTISING was Internet or Hardcopy ("+
	                getAttributeValue(getRootEntityType(), getRootEntityID(),"LSCRSADVERTISING")+
	                ") so "+catStatusAttr+" will not be changed!" + NEW_LINE + "");
	            //return;
	            finish = true;
	        }
	
	        if (!finish) {
	        
		        if (strCAEdit.indexOf("0050")!=-1)  // Edited
		        {
		            traceSb.append(catStatusAttr+" was Edited ("+
		                    getAttributeValue(getRootEntityType(), getRootEntityID(),catStatusAttr)+
		                    ") so "+catStatusAttr+" will not be changed!" + NEW_LINE + "");
		            //return;
		            finish = true;
		        }
		        
		        if (!finish) {
		
			        // WF changes the status but it isn't reflected in the current EntityItem!! so must set instance var
			        // - which changes the LSCCCATEDSTATUS to Editing not Required
			        traceSb.append("TRIGGERING WF: "+actionName+"" + NEW_LINE + "");
			        triggerWorkFlow(actionName);
			        strCAEdit = "0020";
		        }
	        }
        }
    }
    /**
     * Get the DG title based on the entity Nav attributes if ignore is false and the ABR is
     * one of the Nav attributes it will use the ABR return code to determine the value otherwise
     * it uses the value of the attribute from the extract
     *
     * @param ignore Wether to ignore the ABR navigate attribute.
     * @exception  java.sql.SQLException  Description of the Exception
     */
    private  String getDGTitle(boolean ignore)
    throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException
    {
        StringBuffer navName = new StringBuffer();
        // NAME is navigate attributes
        EntityGroup eg =  new EntityGroup(null, m_db, m_prof, getRootEntityType(), "Navigate");
        Iterator itMeta = eg.getMetaAttribute().values().iterator();
        while (itMeta.hasNext()) {
            EANMetaAttribute ma = (EANMetaAttribute) itMeta.next();
            if (!ignore && ma.getAttributeCode().equals(getShortClassName(getClass()))) { // It's actual value is in proccess or queued
                navName.append((getReturnCode() == PASS) ? "Passed" : "Failed");
            }
            else {
                navName.append(getAttributeValue(getRootEntityType(), getRootEntityID(), ma.getAttributeCode()));
            }
            if (itMeta.hasNext()) {
                navName.append(" ");
            }
        }
        return navName.toString();
    }

    /**
     *  Links the course to the OP
     *
     * @param linkAction Name of the link action.
     * @param searchAction Name of the search action.
     * @exception  java.sql.SQLException  Description of the Exception
     * @exception  COM.ibm.opicmpdh.middleware.MiddlewareRequestException  Description of the Exception
     * @exception  COM.ibm.opicmpdh.middleware.MiddlewareException  Description of the Exception
     * @exception  COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException  Description of the Exception
     * @exception  COM.ibm.opicmpdh.middleware.LockException  Description of the Exception
     * @exception  COM.ibm.eannounce.objects.EANBusinessRuleException  Description of the Exception
     */
    private void linkToOP(String linkAction, String searchAction)
    throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        COM.ibm.opicmpdh.middleware.LockException,
        COM.ibm.eannounce.objects.EANBusinessRuleException,
    COM.ibm.eannounce.objects.WorkflowException
    {
		LinkActionItem lai = null;
		SearchActionItem sai = null;
		EntityList list = null;
		Iterator items = null;
        // No current OP associated with course
        // need to link to the OP for the submitter
        EntityItem[] aItems = new EntityItem[1];
        EntityGroup eg = m_elist.getParentEntityGroup();
        aItems[0] = eg.getEntityItem(0); // This is the course
        lai = new LinkActionItem(null, m_db, m_prof, linkAction);
        lai.setParentEntityItems(aItems);
        // Find a the OP entity item that matches the profile e-mail address
        sai = new SearchActionItem(null, m_db, m_prof, searchAction);
        sai.setSearchString(m_prof.getEmailAddress());
        sai.setUseSearchRequest(false);
        list = m_db.executeAction(m_prof, sai);
        eg = list.getEntityGroup("OP");
        items = eg.getEntityItem().values().iterator();
        aItems[0] = null;
        while (items.hasNext()) {
            EntityItem ei = (EntityItem) items.next();
            EANAttribute att = ei.getAttribute("USERTOKEN");
            if (att != null && att instanceof EANTextAttribute) {
                if (m_prof.getEmailAddress().equals(att.get())) {
                    aItems[0] = ei; // This is the submitter OP
                }
            }
        }
        if (aItems[0] != null) {
            lai.setChildEntityItems(aItems);
            m_db.executeAction(m_prof, lai);
        }
        else {
            logMessage("LSCCABR1:" + getEntityID() + " No OP found for " + m_prof.getEmailAddress());
        }
    }

    /**
     *  Description of the Method
     *
     * @return                Description of the Return Value
     * @exception  Exception  Description of the Exception
     */
    private boolean verifyAttributes() throws Exception {
        boolean bReturnCode = true;
		EANAttribute eaLSLANGINDICATOR = null;
		List lLSLANGINDICATOR = new ArrayList();
		String[] astrRequiredAttributes;
		String strCATADIND = null;
		EntityItem ei = null;
		
        String strEntityType = getEntityType();
        int iEntityID = getEntityID();
        
		Iterator iMF;

        // Check for all necessary attributes
        // TODO This is an association now shouldn't have to get other entities
        int ipdhCCF = getParentEntityId(strEntityType, iEntityID, "LSCCF", "LSCCFCC");
        int ipdhCT = getParentEntityId("LSCCF", ipdhCCF, "LSCT", "LSCTCCF");
        String strCTID = getAttributeValue("LSCT", ipdhCT, "LSCTID");
        String strCTDESC = getAttributeValue("LSCT", ipdhCT, "LSCTDESC");
        if ("897".equals(strCTID)) {
            String tmp = getAttributeValue(strEntityType, iEntityID, "LSCRSCERIS");
            if (tmp == null) {
                printErrorMessage(
                        ERR_IAB2019E,
                        new String[]{
                        strCTDESC,
                        getEntityDescription(strEntityType),
                        getAttributeDescription(strEntityType, "LSCRSCERIS")});
                bReturnCode = false;
            }
        }

        eaLSLANGINDICATOR = m_elist.getParentEntityGroup().getEntityItem(0).getAttribute("LSLANGINDICATOR");
        
        if (eaLSLANGINDICATOR != null && eaLSLANGINDICATOR instanceof MultiFlagAttribute) {
            MetaFlag[] mfaLSLANGINDICATOR = (MetaFlag[]) ((MultiFlagAttribute) eaLSLANGINDICATOR).get();
            for (int count = 0; count < mfaLSLANGINDICATOR.length; count++) {
                if (mfaLSLANGINDICATOR[count].isSelected()) {
                    lLSLANGINDICATOR.add(mfaLSLANGINDICATOR[count]);
                }
            }
        }
        // Now all selected languages are in lLSLANGINDICATOR
        
        strCATADIND = getAttributeFlagEnabledValue(strEntityType, iEntityID, "LSCATADIND","");
        if ("0010".equals(strCATADIND)) {
            boolean bResult = checkAttributes2(getEntityType(), getEntityID(), new String[]{"LSCRSMKTCHAPPRIM"});
            if (bResult) {
                // LSCRSMKTCHAPPRIM is populated.
                // Get LSCRSMKTCHAPPRIM value.
                String flag = getAttributeFlagEnabledValue(getEntityType(), getEntityID(), "LSCRSMKTCHAPPRIM","");
                MetaFlagAttributeList mfalP = m_db.getMetaFlagAttributeList(getProfile(), "LSCRSMKTCHAPPRIM");

                MetaFlagAttribute mfaP = mfalP.getBaseMetaFlagAttribute();
                // Get the MetaFlag for the LSCRSMKTCHAPPRIM value.
                MetaFlag mfP = mfaP.getMetaFlag(flag);
                // Does it have a dependent choice value?
                if (mfP.getControllerCount() > 0) {
                    // It has a dependent choice value.
                    // Does the LSCRSMKTSUBCHAPPRI have a value?
                    bResult = checkAttributes2(getEntityType(), getEntityID(), new String[]{"LSCRSMKTSUBCHAPPRI"});
                    if (!bResult) {
                        // LSCRSMKTSUBCHAPPRI is not populated.
                        // Give it a default value
                        setFlagValue("LSCRSMKTSUBCHAPPRI", "0010");
                        // Notify user of acion taken.
                        printWarningMessage(
                                MSG_IAB2023I,
                                new String[]{
                                getEntityDescription(getEntityType()),
                                getAttributeValue(getEntityType(), getEntityID(), "LSCRSID"),
                                getAttributeValue(getEntityType(), getEntityID(), "LSCRSTITLE"),
                                getAttributeDescription(getEntityType(), "LSCRSMKTSUBCHAPPRI"),
                                getAttributeMetaFlagDescription(getEntityType(), "LSCRSMKTSUBCHAPPRI", "0010"),
                                getAttributeDescription(getEntityType(), "LSCRSMKTCHAPPRIM"),
                                getAttributeMetaFlagDescription(getEntityType(), "LSCRSMKTCHAPPRIM", flag)});
                    }
                }
            }
            else {
                // LSCRSMKTCHAPPRIM is not populated.
                bReturnCode = false;
            }
            // Advertising Indicator = Yes
            astrRequiredAttributes = new String[] {
                    "LSCRSTITLE",
                    "LSCRSAUDIENCEDESC",
                    "LSCRSTOPICS",
                    "LSCRSOBJECTIVES",
                    "LSCRSCOURSEOVERVIEW",
                    "LSCRSPREREQDESC"
            };
        }
        else {
            // Always Check Title
            astrRequiredAttributes = new String[] {"LSCRSTITLE"};
        }

        iMF = lLSLANGINDICATOR.iterator();
        ei = m_elist.getParentEntityGroup().getEntityItem(0);
        while (iMF.hasNext()) {
			boolean hasAllForThisNLS = true;
            MetaFlag mf = (MetaFlag) iMF.next();
            int nls = Integer.parseInt(mf.getShortDescription());
            println("<li>" + mf.getLongDescription() + ":<br /><ul>");
            
            for (int i = 0; i < astrRequiredAttributes.length; i++) {
                String[] astrMessage = new String[]{
                    getABREntityDesc(strEntityType, iEntityID),
                    getAttributeDescription(strEntityType, astrRequiredAttributes[i])
                };

                EANAttribute attr = ei.getAttribute(astrRequiredAttributes[i]);
                if (attr == null) {
                    printErrorMessage(ERR_IAB1001E, astrMessage);
                    bReturnCode = false;
                    hasAllForThisNLS = false;
                }
                else if (attr instanceof EANTextAttribute) {
                    EANTextAttribute tAttr = (EANTextAttribute) attr;
                    if (!tAttr.containsNLS(nls)) {
                        printErrorMessage(ERR_IAB1001E, astrMessage);
                        bReturnCode = false;
                        hasAllForThisNLS = false;
                    }
                    else {
                        printTestedMessage(MSG_IAB2011I, astrMessage);
                    }
                }
            }
            if (hasAllForThisNLS) {
                println("<li>All Required attributes are populated</li>");
            }
            println("</ul></li>");
        }
        return bReturnCode;
    }


    /**
     * check the volume price display order is unique
     *
     */
    private boolean checkVPCDisplayOrder() {
        Iterator ids = getChildrenEntityIds(getEntityType(), getEntityID(), "LSVPC", "LSCCVPC").iterator();
        boolean hasVPC = ids.hasNext();
        boolean ret = true;
        Set displayOrder = new HashSet();
        while (ids.hasNext()) {
            Integer id = (Integer) ids.next();
            String dispOrd = getAttributeValue("LSVPC", id.intValue(), "LSCRSVPCDISPORD");
            println("<!-- dispOrd:" + dispOrd + " -->");
            if (displayOrder.contains(dispOrd)) {
                printErrorMessage(
                        "%1# Entities do not have unique %2#",
                        new String[]{
                        getEntityDescription("LSVPC"),
                        getAttributeDescription("LSVPC","LSCRSVPCDISPORD")});
                ret = false;
                break;
            }
            else {
                displayOrder.add(dispOrd);
            }
        }
        if (ret) {
        	println("<ul>");
	        if (hasVPC) {
	            println("<li>All Volume Prices have a unique Display Order.</li>");
	        }
	        else {
	            println("<li>There are no Volume Prices linked to this Course.</li>");
	        }
	        println("</ul>");
        }
        return ret;
    }

    /*
     * Checks that the new price effictive dates are not after the course expiration date
     */
    private boolean checkNewPriceEffictiveDates() {
        String[] attCodes = new String[] {
            "LSCRSEXTNPRICEEFFDATE",
            "LSCRSINTNPRICEEFFDATE",
            "LSCRSWTNEWPRICEDATE",
            "LSCRSPRIVNPRICEEFFDATE"
        };
        boolean bReturn = true;
        String expDate = getAttributeValue(getEntityType(), getEntityID(), "LSCRSEXPDATE", "1900-01-01");
        for (int i = 0; i < attCodes.length; i++) {
            String value = getAttributeValue(getEntityType(), getEntityID(), attCodes[i], "1900-01-01");
            if (expDate.compareTo(value) <= 0) {
                printErrorMessage(
                        "The %1# cannot be on or after the %2# of %3#.",
                        new String[]{
                        getAttributeDescription("LSCC",attCodes[i]),
                        getAttributeDescription("LSCC","LSCRSEXPDATE"),
                        expDate});
                bReturn = false;
                break;
            }
        }
        return bReturn;
    }


    /**
     *  checks All relators out to ensure that their count is only one
     *
     * @return    Description of the Return Value
     */
    private boolean checkRelators() {
        boolean bReturnCode = true;
		boolean bRC = false;
		boolean bCUR = false;
		boolean bSC = false;
		boolean bCCF = false;
		String strMethod = null;
		Set setMethods = null;
		
        String strEntityType = getEntityType();
        int iEntityID = getEntityID();
		
		
        // A In-Country Course must be linked to one and only one Person - Course Contact
        if (!checkRelator2("LSPER", "LSCCPERCON", UNIQUE2, true)) {
        //if (!checkUniqueChild("LSPER", "LSCCPERCON")) { // Behavior changed by WC
            bReturnCode = false;
        }
        // A In-Country Course must be linked to one and only one Person - Owner
        if (!checkRelator2("LSPER", "LSCCPEROW", UNIQUE2, true)) {
        //if (!checkUniqueChild("LSPER", "LSCCPEROW")) { // Behavior changed by LF
            bReturnCode = false;
        }
        // A In-Country Course must be linked to one and only one RCODE
        bRC = checkRelator2("LSRC", "LSCCRC", UNIQUE2, true);
        if (!bRC) {
        //if (!checkUniqueChild("LSRC", "LSCCRC")) {
            bReturnCode = false;
        }
        // A In-Country Course must be linked to one and only one Segment
        if (anyRetiredChildren(strEntityType, iEntityID, "LSSEG", "CCSEGA", "LSSEGLIFECYCLE", XR_LSSEGLIFECYCLE_Retired)) {
            bReturnCode = false;
        }

        // A In-Country Course must be linked to one and only one Curriculum
        bCUR = checkRelator2("LSCUR", "LSCCCUR", UNIQUE2, true);
        if (!bCUR) {
        //if (!checkUniqueChild("LSCUR", "LSCCCUR")) { // Behavior changed by LF
            bReturnCode = false;
        }
        else if (anyRetiredChildren(strEntityType, iEntityID, "LSCUR", "LSCCCUR", "LSCURLIFECYCLE", XR_LSCURLIFECYCLE_Retired)) {
            bReturnCode = false;
        }

        // A In-Country Course must be linked to one and only one Subcurriculumn
        bSC = checkRelator2("LSSC", "LSCCSC", UNIQUE2, true);
        if (!bSC) {
        //if (!checkUniqueChild("LSSC", "LSCCSC")) { // Behavior changed by LF
            bReturnCode = false;
        }

        // Have one of each now check that they are linked to each other
        if (bCUR && bSC) {
            int iCUR = getChildEntityId(getEntityType(), getEntityID(), "LSCUR", "LSCCCUR");
            int iSC = getChildEntityId(getEntityType(), getEntityID(), "LSSC", "LSCCSC");
            Vector vCURChild = getChildrenEntityIds("LSCUR", iCUR, "LSSC", "LSCURSC");
            boolean bFoundOne = false;
            Iterator itCURChild = vCURChild.iterator();
            while (itCURChild.hasNext()) {
                Integer iCURChild = (Integer) itCURChild.next();
                if (iSC == iCURChild.intValue()) {
                    bFoundOne = true;
                    println("<!-- SC:" + iCURChild.intValue() + " linked to CUR:" + iCUR + " is linked to this CC -->");
                }
                else {
                    println("<!-- SC:" + iCURChild.intValue() + " linked to CUR:" + iCUR + " is not linked to this CC -->");
                }
            }
            if (!bFoundOne) {
                printErrorMessage(
                        "There is in invalid pairing of %1# %2# and %3# %4# for this %5#.",
                        new String[]{
                        getEntityDescription("LSCUR"),
                        getAttributeValue("LSCUR", iCUR, "LSCURTITLE"),
                        getEntityDescription("LSSC"),
                        getAttributeValue("LSSC", iSC, "LSSCTITLE"),
                        getEntityDescription(getEntityType())});
                bReturnCode = false;
            }
        }
        // A In-Country Course must be linked to one and only one In-Country Course Folder
        bCCF = checkRelator2("LSCCF", "LSCCFCC", UNIQUE2, false);
        if (!bCCF) {
        //if (!checkUniqueParent("LSCCF", "LSCCFCC")) { // Behavior changed by LF
            bReturnCode = false;
        }

        if (bRC && bCCF) {
            int iRC = getChildEntityId(getEntityType(), getEntityID(), "LSRC", "LSCCRC");
            int iRCCT = getChildEntityId("LSRC", iRC, "LSCT", "LSRCCT");
            int iCCF = getParentEntityId(getEntityType(), getEntityID(), "LSCCF", "LSCCFCC");
            int iCCFCT = getParentEntityId("LSCCF", iCCF, "LSCT", "LSCTCCF");
            if (iRCCT == iCCFCT) {
                println("<!-- RCODE country matches -->");
            }
            else {
                printErrorMessage(
                        "There is an invalid pairing of %1# %2# and %3# %4# for this %5#.",
                        new String[]{
                        getEntityDescription("LSRC"),
                        getAttributeValue("LSRC", iRC, "LSRCID"),
                        getEntityDescription("LSCT"),
                        getAttributeValue("LSCT", iCCFCT, "LSCTDESC"),
                        getEntityDescription(getEntityType())});
                bReturnCode = false;
            }
        }

        strMethod = getAttributeFlagEnabledValue(strEntityType, iEntityID, "LSCRSDELIVERY","");

        setMethods = new HashSet();
        setMethods.add(XR_LSCRSDELIVERY_Classroom);
        setMethods.add(XR_LSCRSDELIVERY_OnLineInstructorLed);
        setMethods.add(XR_LSCRSDELIVERY_Satellite);
        setMethods.add(XR_LSCRSDELIVERY_NTU);
        setMethods.add("0030"); // CONFERENCE
        if (setMethods.contains(strMethod) &&
            (!checkRelator2("LSPER", "LSCCPERSTL", UNIQUE2, true) |
            //(!checkUniqueChild("LSPER", "LSCCPERSTL") || // Behavior changed by LF
                !checkMultipleChild("LSMAT", "LSCCMAT")))
        {
            bReturnCode = false;
        }

        return bReturnCode;
    }


    /**
     *  Description of the Method
     *
     * @param  _iReturnCode   Description of the Parameter
     * @exception  Exception  Description of the Exception
     */
    private void updateStatus(int _iReturnCode) throws Exception {
        String strEntityType = getEntityType();
        int iEntityID = getEntityID();

        //based on TA feed indicator
        String strTA = getAttributeFlagEnabledValue(strEntityType, iEntityID, "LSCRSTASERVERINDICATOR","");
        String strPricing = getAttributeFlagEnabledValue(strEntityType, iEntityID, "LSCCPRICINGSTATUS","");
        //String strCAEdit = getAttributeFlagEnabledValue(strEntityType, iEntityID, "LSCCCATEDITSTATUS","");
        //Catalog edit status may have been adjusted earlier but not reflected in current entity, use instance variable

        String[] astrFlags = convertToArray(getAttributeFlagEnabledValue(strEntityType, iEntityID, "LSCRSADVERTISING",""));
        Set hsSelectedFlags = new HashSet();
        for (int i = 0; i < astrFlags.length; i++) {
            hsSelectedFlags.add(astrFlags[i]);
        }

        if (_iReturnCode == FAIL) {
            triggerWorkFlow("WFLSCCFL");
        } else if (strTA.equals("0040")) {
            setFlagValue("LSCRSTAAUD", getAttributeFlagEnabledValue(strEntityType, iEntityID, "LSCRSAUDIENCE","0010"));
            triggerWorkFlow("WFLSCCCSREAVCL");
            triggerLock("EXTLSCCLOCK");
        }
        else if (strPricing.equals("0030") && strCAEdit.equals("0050")) {
            setFlagValue("LSCRSTAAUD", getAttributeFlagEnabledValue(strEntityType, iEntityID, "LSCRSAUDIENCE","0010"));
            triggerWorkFlow("WFLSCCAVCL");
            triggerLock("EXTLSCCLOCK");
        }
        else {
            if (hsSelectedFlags.contains("0010") || hsSelectedFlags.contains("0020")) {
                if (strCAEdit.equals("0030") || // Requires Catalog  Editing
                    strCAEdit.equals("0040") || // Under Edit
                    strCAEdit.equals("0050"))   // Edited
                {
                    // Set Lifecycle Status to Awaiting Approvals
                    triggerWorkFlow("WFLSCCAVA");
                }
                else if (strCAEdit.equals("0010") || // Not Edited
                    strCAEdit.equals("0020") || // Editing Not Required
                    strCAEdit.equals("0060"))   // Denied
                {
                    // Set Status to Requires Catalog Editing
                    triggerWorkFlow("WFLSCCCSREAVA");
                }
            }
            else if (strPricing.equals("0030")) { // Approved
                setFlagValue("LSCRSTAAUD", getAttributeFlagEnabledValue(strEntityType, iEntityID, "LSCRSAUDIENCE","0010"));
                triggerWorkFlow("WFLSCCAVCL");
                triggerLock("EXTLSCCLOCK");
            }

            if (strPricing.equals("0010") || // Not Approved
                strPricing.equals("0040") || // Denied
                strPricing.equals("0020"))   // Needs Pricing Approval
            {
                // Set Status to Needs Pricing Approval
                triggerWorkFlow("WFLSCCPSNAAVA");
            }
        }
    }


    /**
     *  Description of the Method
     *  Overidden to show message
     *  This checks to see if any Children of a specific entitytype is retired.  Error's may need to be handled by caller
     *
     * @param  _strEntityType     Description of the Parameter
     * @param  _iEntityID         Description of the Parameter
     * @param  _strChildType      Description of the Parameter
     * @param  _strRelator        Description of the Parameter
     * @param  _strAttributeCode  Description of the Parameter
     * @param  _strFlagValue      Description of the Parameter
     * @return                    Description of the Return Value
     */
    protected boolean anyRetiredChildren(String _strEntityType, int _iEntityID, String _strChildType, String _strRelator, String _strAttributeCode, String _strFlagValue) {
        boolean bReturnCode = false;
        Iterator ids = getChildrenEntityIds(_strEntityType, _iEntityID, _strChildType, _strRelator).iterator();
        while (ids.hasNext()) {
            int iChildID = ((Integer) ids.next()).intValue();
            if (_strFlagValue.equals(getAttributeFlagEnabledValue(_strChildType, iChildID, _strAttributeCode,""))) {
                printErrorMessage(
                        ERR_IAB2003E,
                        new String[]{
                        getEntityDescription(_strChildType),
                        getEntityDescription(_strEntityType),
                        getEntityDescription(_strChildType),
                        getAttributeMetaFlagDescription(_strChildType, _strAttributeCode, _strFlagValue)});
                bReturnCode = true;
            }
        }
        return bReturnCode;
    }

    /**
     *  Triggers the specified workflow
     *
     * @param actionName Name of the workflow action.
     * @exception  java.sql.SQLException  Description of the Exception
     * @exception  COM.ibm.opicmpdh.middleware.MiddlewareException  Description of the Exception
     * @exception  COM.ibm.eannounce.objects.WorkflowException  Description of the Exception
     */
    private void triggerWorkFlow(String actionName)
    throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.eannounce.objects.WorkflowException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
    	WorkflowActionItem wfai = null;
        EntityGroup eg = m_elist.getParentEntityGroup();
        EntityItem[] aItems = new EntityItem[1];
        aItems[0] = eg.getEntityItem(0);
        wfai = new WorkflowActionItem(null, m_db, m_prof, actionName);
        wfai.setEntityItems(aItems);
        m_db.executeAction(m_prof, wfai);
    }

    /**
     *  Triggers the specified lock action
     *
     * @param actionName Name of the lock action.
     * @exception  java.sql.SQLException  Description of the Exception
     * @exception  COM.ibm.opicmpdh.middleware.MiddlewareException  Description of the Exception
     */
    private void triggerLock(String actionName)
    throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
		LockActionItem lai = null;
        EntityGroup eg = m_elist.getParentEntityGroup();
        EntityItem[] aItems = new EntityItem[1];
        aItems[0] = eg.getEntityItem(0);
        lai = new LockActionItem(null, m_db, m_prof, actionName);
        lai.setEntityItems(aItems);
        m_db.executeAction(m_prof, lai);
    }

    /**
     *  Sets the specified Flag Attribute on the Root Entity
     *
     *@param    strAttributeCode The Flag Attribute Code
     *@param    strAttributeValue The Flag Attribute Value
     */
    private void setFlagValue(String strAttributeCode, String strAttributeValue) {
        setControlBlock();
        logMessage("****** strAttributeValue set to: " + strAttributeValue);

        if(strAttributeValue != null) {
            try {
                EntityItem eiParm = new EntityItem(null,m_prof, getEntityType(), getEntityID());
                ReturnEntityKey rek = new ReturnEntityKey(eiParm.getEntityType(), eiParm.getEntityID(), true);

                SingleFlag sf = new SingleFlag (m_prof.getEnterprise(), rek.getEntityType(), rek.getEntityID(), strAttributeCode, strAttributeValue, 1, m_cbOn);
                Vector vctAtts = new Vector();
                Vector vctReturnsEntityKeys = new Vector();

                if(sf != null) {
                    vctAtts.addElement(sf);

                    rek.m_vctAttributes = vctAtts;
                    vctReturnsEntityKeys.addElement(rek);

                    m_db.update(m_prof, vctReturnsEntityKeys, false, false);
                    m_db.commit();
                }
            }
            catch (COM.ibm.opicmpdh.middleware.MiddlewareException e) {
                logMessage("setFlagValue: " + e.getMessage());
            }
            catch (Exception e) {
                logMessage("setFlagValue: " + e.getMessage());
            }
        }
    }


    /**
     *  Gets the description attribute of the LSCCABR1 object
     *
     * @return    The description value
     */
    public String getDescription() {
        return "In-Country Course Validation and Work Flow Initiation.";
    }


    /**
     *  Get the version
     *
     * @return    java.lang.String
     */
    public String getABRVersion() {
        return "$Revision: 1.30 $";
    }
    /**
    *  Description of the Method
    *
    *@param  _strEntityType     Description of the Parameter
    *@param  _iEntityID         Description of the Parameter
    *@param  _strParentType     Description of the Parameter
    *@param  _strRelator        Description of the Parameter
    *@param  _strAttributeCode  Description of the Parameter
    *@param  _strFlagValue      Description of the Parameter
    *@return                    Description of the Return Value
    */
    protected boolean anyRetiredParents(String _strEntityType, int _iEntityID, String _strParentType, String _strRelator, String _strAttributeCode, String _strFlagValue) {
        boolean bReturn = true;
        Iterator ids = getParentEntityIds(_strEntityType, _iEntityID, _strParentType, _strRelator).iterator();
        while (ids.hasNext()) {
			String flagDesc = "";
            int iParentID = ((Integer) ids.next()).intValue();
            String flagVal = getAttributeFlagEnabledValue(_strParentType, iParentID, _strAttributeCode, "");
            log("anyRetiredParents2:flagVal=" + flagVal);
            if (!"".equals(flagVal) && !_strFlagValue.equals(flagVal)) {
                bReturn = false;
                flagDesc = "<em>** Not Populated **</em>";
                if (flagVal != null) {
                    flagDesc = getAttributeMetaFlagDescription(_strParentType, _strAttributeCode, flagVal);
                }

        //ERR_IAB2001E="The %1# cannot be %2# because %3 has a current Life Cycle status value of %4.";
                printErrorMessage(
                    ERR_IAB2001E,
                    new String[]{
                        getEntityDescription(getEntityType()),
                        "retired",
                        getABREntityDesc(_strParentType, iParentID),
                        flagDesc
                });
            } else {
        //MSG_IAB2015I="%1 has a current Life Cycle status value of %2.";
                String strMetaDesc = getAttributeMetaFlagDescription(_strParentType, _strAttributeCode, _strFlagValue);
                printTestedMessage(
                    MSG_IAB2015I,
                    new String[]{
                        getABREntityDesc(_strParentType, iParentID),
                        ((strMetaDesc == null) ? "Null value returned for "+_strParentType+":"+_strAttributeCode+":"+_strFlagValue : strMetaDesc)
                });
            }
        }
        return bReturn;
    }


  /*
   *  This checks to see if any Children of a specific entitytype is retired.  Error's may need to be handled by caller
   */
    /**
    *  Get the Meta Flag description for the specified attribute and flag value
    *
    *@param  entityType  String specifying the entity type to find
    *@param  attrCode    String attribute code to get value for
    *@param  flagValue   String flag code to get m_hDisplay value for
    *@return             The attributeMetaFlagDescription value
    *@returns            String flag m_hDisplay text
    */
    public String getAttributeMetaFlagDescription(String entityType, String attrCode, String flagValue) {
		EntityGroup entGroup = null;
		String desc = null;
		EANMetaAttribute mAttr = null;
		boolean finish = false; 
		
        if (flagValue == null) {
            logError("null FlagCode supplied! Returning Error");
            //return null;
            finish = true;
        }
        
        if (!finish) {
	        entGroup = m_elist.getEntityGroup(entityType);
	
	        if (entGroup == null) {
	            logError("Did not find EntityGroup: "+entityType+" in entity list to extract MetaFlagDescription");
	            //return null;
	            finish = true;
	        }
	        
	        if (!finish) {
	      
		        mAttr = entGroup.getMetaAttribute(attrCode);
		        if (mAttr != null && mAttr instanceof EANMetaFlagAttribute) {
		            MetaFlag mf = ((EANMetaFlagAttribute) mAttr).getMetaFlag(flagValue);
		            desc = mf.getLongDescription();
		        }
		        else {
		            desc = attrCode + " not found or not a flag";
		        }
		    //EntityItem entItem = entGroup.getEntityItem(_strEtype);
		    //EANAttribute eattr = entItem.getAttribute(_strEtype);
		    //if (eattr instanceof EANFlagAttribute) {
		      //EANFlagAttribute ema = (EANFlagAttribute) eattr;
		      //desc = ema.getFlagLongDescription(_strFlagValue);
		    //}
	        }
        }
        
        //return desc;
        return (finish ? null : desc);
    }
    /**
    *  Check for entities through the specified relator
    *  behavior of getUniqueChild and getUniqueParent has changed. It no longer uses checkRelator
    *  checkRelator has private access in PokBaseABR so I cannot invoke it or override it. I had to copy it to each ABR that needed it.
    *
    *@param  _strEntityType   Description of the Parameter
    *@param  _strRelatorType  Description of the Parameter
    *@param  _iState          Description of the Parameter
    *@param  _bChild          Description of the Parameter
    *@return                  boolean
    */
    private boolean checkRelator2(String _strEntityType, String _strRelatorType, int _iState, boolean _bChild) {

        Vector vct = null;
        boolean ret = true;
		int iCount = -1;
        String strEntityDesc = getEntityDescription(_strRelatorType);
        String[] astrMessage = new String[]{
            getEntityDescription(getEntityType()),
            getABREntityDesc(getEntityType(), getEntityID()),
            strEntityDesc
        };

        if (_bChild) {
            vct = getChildrenEntityIds(getEntityType(), getEntityID(), _strEntityType, _strRelatorType);
        } else {
            vct = getParentEntityIds(getEntityType(), getEntityID(), _strEntityType, _strRelatorType);
        }

        iCount = vct.size();

    // avoid hardcoding description, even if entity id=0, the description
    // is valid so get it if possible

        switch (_iState) {
        case UNIQUE2:                                   // one and only one required
            switch (iCount) {
            case 0:
                printErrorMessage(ERR_IAB1002E, astrMessage);
                //return false;
                ret = false;
                break;
            case 1:
                break;
            default:
                printErrorMessage(ERR_IAB1003E, astrMessage);
                //return false;
                ret = false;
                break;
            }
            break;
        case ONE_OR_MORE2:                              // at least one is required
            if (iCount == 0) {
                printErrorMessage(ERR_IAB1004E, astrMessage);
                //return false;
                ret = false;
                break;
            }
            break;
        case ONE_OR_LESS2:                              // may have only one
            if (iCount > 1) {
                printErrorMessage(ERR_IAB1003E, astrMessage);
                //return false;
                ret = false;
                break;
            }
            break;
        default:
            break;
        }

        if (ret) {
        	printTestedMessage(MSG_IAB2012I, astrMessage);
        }
        return ret;
    }
  // used to control relator search
    private final static int UNIQUE2 = 0;
    private final static int ONE_OR_MORE2 = 1;
    private final static int ONE_OR_LESS2 = 2;
    /**
    *  Copied from PokBaseABR to fix exception.
    *  Check for existance of attributes and generate error msg
    *
    *@param  _strEntityType           Description of the Parameter
    *@param  _iEntityID               Description of the Parameter
    *@param  _astrRequiredAttributes  Description of the Parameter
    *@return                          boolean
    */
    protected final boolean checkAttributes2(String _strEntityType, int _iEntityID, String[] _astrRequiredAttributes) {

        boolean result = true;

        //check if all required attributes exist
        for (int i = 0; i < _astrRequiredAttributes.length; i++) {

            String[] astrMessage = new String[]{
                getABREntityDesc(_strEntityType, _iEntityID),
                getAttributeDescription(_strEntityType, _astrRequiredAttributes[i])
            };
            String strValue = getAttributeValue(_strEntityType, _iEntityID, _astrRequiredAttributes[i], DEF_NOT_POPULATED_HTML);

            if (strValue == null || strValue.equals(DEF_NOT_POPULATED_HTML)) {
                printErrorMessage(ERR_IAB1001E, astrMessage);
                result = false;
            } else {
                printTestedMessage(MSG_IAB2011I, astrMessage);
            }
        }

        return result;
    }

}
