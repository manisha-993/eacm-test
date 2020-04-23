//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2001, 2008  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
 * <pre>
 *
 * In-Country Course Validation and Work Flow initiation.
 *
 *
 * Change History (most recent at top)
 * $Log: LSWWCCABR1.java,v $
 * Revision 1.34  2014/01/13 13:50:51  wendy
 * migration to V17
 *
 * Revision 1.33  2008/08/29 19:39:00  wendy
 * MN36715676 added missing check for expdate
 *
 * Revision 1.32  2006/06/26 12:42:34  chris
 * moved call to EACustom.getTOUDiv()
 *
 * Revision 1.31  2006/03/13 19:05:45  couto
 * Fixed copyright info.
 *
 * Revision 1.30  2006/03/08 12:41:08  couto
 * Changed layout, using EACustom methods. Changed ul position (put it inside the method).
 * Fixed br tags. Chaged font tags. Fixed multiple return statements.
 *
 * Revision 1.29  2006/01/26 00:36:19  joan
 * Jtest
 *
 * Revision 1.28  2006/01/22 18:28:29  joan
 * changes for Jtest
 *
 * Revision 1.27  2005/01/26 23:37:00  joan
 * changes for Jtest
 *
 * Revision 1.26  2004/09/15 18:33:09  wendy
 * CR0715044211 to set Catalog Editing status
 *
 * Revision 1.25  2004/06/09 17:55:49  chris
 * Fix for MN19443595 to set LSCRSTAAUD
 *
 * Revision 1.24  2004/06/03 13:39:33  chris
 * Changes for CR0519045436 Must have LSMAT when LSCRSDELIVERY=Concerence
 *
 * Revision 1.23  2004/05/27 22:03:56  bala
 * executeaction to throw WorkflowException (compiler error)
 *
 * Revision 1.22  2004/05/13 19:04:18  chris
 * CR Demo Changes
 *
 * Revision 1.21  2004/04/15 13:46:24  chris
 * Remove template check for CR0324044656
 *
 * Revision 1.20  2004/03/09 15:58:01  chris
 * Changes for CR0701025842, CR1218022919, CR050902204
 *
 * Revision 1.19  2003/12/18 14:10:46  chris
 *  Fix feedback 53430:5B83C9
 *
 * Revision 1.18  2003/12/08 18:46:30  chris
 * Fix Feedback 53336:60A7A1
 *
 * Revision 1.17  2003/12/02 20:19:46  chris
 * Spec changes related to feedbacks 53243:653AE1 and 53251:75170B
 *
 * Revision 1.16  2003/12/02 12:04:42  chris
 * Fix for FB 53250:74AF86
 *
 * Revision 1.15  2003/11/21 21:04:57  chris
 * Possible fix for 53020, 53021,53023
 *
 * Revision 1.14  2003/11/20 15:43:21  chris
 * Fix for feedback 53061:59B65D
 *
 * Revision 1.13  2003/11/12 20:35:28  chris
 * put Revision back
 *
 * Revision 1.12  2003/11/12 20:28:31  chris
 * Fix incorrect Association Name
 *
 * Revision 1.11  2003/11/06 22:01:18  bala
 * EC drop
 *
 * Revision 1.6  2003/10/30 20:31:49  cstolpe
 * Updated search action name and changed setDGTitle
 *
 * Revision 1.5  2003/10/28 20:58:58  cstolpe
 * setRptClass in all and Linking in ABR 1
 *
 * Revision 1.4  2003/10/23 19:24:00  cstolpe
 * Check lifecycle and do VELOCK
 *
 * Revision 1.3  2003/10/20 19:26:54  cstolpe
 * latest
 *
 * Revision 1.2  2003/10/15 19:35:26  cstolpe
 * Latest Updates
 *
 * Revision 1.1  2003/10/09 18:51:57  cstolpe
 * Initial LS drop
 *
 * </pre>
 * @author     Chris Stolpe
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
 * 3. LSWWCCABR1
a)  Processing Worldwide In-Country Course
ABR is Triggered by
-   Workflow action 'Validate Worldwide In-Country Course'
-   Change Lifecycle Status to Validate and Initiate Workflow

CHECK 2
    If a relator from WWCC to a WWCC Template exists, the course is a template and the ABR fails.

CHECK 3
    This check in 1.3 has been replaced by meta lock groups, as described above for CR0324044656.

CHECK 4
    If the Country linked to Course is United States (country Code 897), then the Ceris Code attribute
    must be populated.

CHECK 5
    Advertising Indicator must have a value other than the default of 0.

CHECK 6
    The LSCRSTITLE must exist in all languages specified in the LSLANGINDICATOR attribute.

CHECK 7
If Advertising Indicator is Yes, then the LSLANGINDICATOR attribute must be checked for which language
versions must be created for the Course entity, and verify that 5 attributes exist for each of the selected languages.
-   Audience Description
-   Topics
-   Objectives
-   Course Overview
-   Prerequisite Description

CHECK 8
If Advertising Indicator is Yes, the following attributes must be populated (English only rule implemented,
cannot have non-English versions).
-   Country Marketing Chapter Primary
-   Country Marketing Sub-Chapter Primary

CHECK 9
    There must one and only one relator from the WWCC to each of the following entities:
-   Person as Course Contact
-   Person as Course Owner
-   RCODE
-   Worldwide Course

CHECK 10
    The Worldwide In-Country Course must have one and only one relator to a Person as STL Contact if the
    Delivery Method is one of the following:
   22 ON-LINE, INSTRUCTOR-LED
   01 CLASSROOM
   07 NATIONAL TECHNOLOGICAL UNIVERSITY (NTU)
   13 SATELLITE

CHECK 11
The Worldwide In-Country Course must have at least one, but possibly more than one, relator to a Material
Set if the Delivery is one of the following:
   22 ON-LINE, INSTRUCTOR-LED
   01 CLASSROOM
   07 NATIONAL TECHNOLOGICAL UNIVERSITY (NTU)
   13 SATELLITE

CHECK 12
    The associated Segment and Worldwide Course parent must have a Status of 'Available'.
    The Worldwide In-Country Course must have an Expiration Date that is less than or equal to the
    Expiration date of the parent Worldwide Course.

CHECK 13
    A relator will be created from the Course to the OP entity that makes the change to Validate and Initiate
    Workflow status.  This will be considered the 'Course Creator', and be used for notification.
    A check should be made to see if this relator exists for a Course.  If so, the existing relator should
    be expired, and replaced with a new relator to the current OP.

CHECK 14
Added per CR050902204
    A check must be done to see if any LSVPC entities are linked to a course.  If there is more than one
    LSVPC linked to a single course, the attribute LSCRSVPCDISPORD for each must be unique.

CHECK 15
Added per CR1218022919
    A New Price Effective Date must not be later than or equal to the Course expiration date.

CHECK 16
Added per CR0701025842
    The RCODE linked to an LSWWCC must be linked to the same LSCT that is linked to the LSWWCC

 */
public class LSWWCCABR1 extends PokBaseABR {
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2001, 2008  All Rights Reserved.";

    private String strCAEdit = "";

    /**
     * Execute LS ABR.
     *
     */
    public void execute_run() {
        try {
			Object[] mfParms = new String[10];
			MessageFormat mfOut = new MessageFormat("<p class=\"ibm-intro ibm-alternate-three\"><em>{0}</em></p><p><b>Date: </b>{1}<br /><b>User: </b>{2} ({3})<br /><b>Description: </b>{4}</p><!-- {5} -->");
			int iRetCode = PASS;
			StringBuffer sb = new StringBuffer();
			EntityGroup eg = null;

            start_ABRBuild();
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
            strCAEdit = getAttributeFlagEnabledValue(getEntityType(), getEntityID(), "LSWWCCCATEDITSTATUS","");
            adjustCatalogEditStatus(sb); //CR0715044211
            println("<!-- "+sb.toString()+" -->");

            println("<!-- "+outputList(m_elist)+" -->");

            //if (checkFlagValues("LSWWCCLIFECYCLE", XR_LSWWCCLIFECYCLE_Available) ||
            //    checkFlagValues("LSWWCCLIFECYCLE", XR_LSWWCCLIFECYCLE_Retired))
            //{
            //    println("<br />LSWWCCABR1 not processed.  Current Lifecycle Status is not appropriate for this action.<br />");
            //    iRetCode = FAIL;
            //}
            //else {
            // Do we have a relator? to OP?
            /*
             * CHECK 13
    A relator will be created from the Course to the OP entity that makes the change to Validate and Initiate
    Workflow status.  This will be considered the 'Course Creator', and be used for notification.
    A check should be made to see if this relator exists for a Course.  If so, the existing relator should
    be expired, and replaced with a new relator to the current OP.
             */
            eg = m_elist.getEntityGroup("LSWWCCOP");
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
						DeleteActionItem dai = null;
						EntityItem[] aItems = null;
                        linkToOP("LINKWWCCOP", "SRGLOP");
                        dai = new DeleteActionItem(null, m_db, m_prof, "DELWWCCOP");
                        aItems = new EntityItem[] { eiCCOP };
                        dai.setEntityItems(aItems);
                        m_db.executeAction(m_prof, dai);
                    }
                }
            }
            else {
                // No current OP associated with course
                // need to link to the OP for the submitter
                linkToOP("LINKWWCCOP", "SRGLOP");
            }
            //CHECK 2 If a relator from WWCC to a WWCC Template exists, the course is a template and the ABR fails.
            if (getParents("LSWW", "LSWWWWCCTEMP").size() > 0) {
                if (getParentEntityIds(getEntityType(), getEntityID(), "LSWW", "LSWWWWCC").size() > 0) {
                    printWarningMessage(
                        MSG_IAB2025I,
                        new String[]{
                            getEntityDescription("LSWWCC"),
                            getAttributeValue(getEntityType(), getEntityID(), "LSCRSTITLE"),
                            getAttributeValue(getEntityType(), getEntityID(), "LSCRSTEMPLATENAME"),
                            getEntityDescription("LSWW"),
                            getAttributeValue(getEntityType(), getEntityID(), "LSCRSID"),
                            getAttributeValue(getEntityType(), getEntityID(), "LSCRSTITLE"),
                            getEntityDescription("LSWWWWCC")});
                }
                iRetCode = FAIL;
            }
            else {
                //if (!checkTemplate()) {
                //    iRetCode = FAIL;
                //}
                // Check Attributes
                m_bOutputAsList = true;
                println(NEW_LINE + "<b>Attribute check:</b>" + NEW_LINE + "<ul>");
                if (!checkAttributes()) {
                    iRetCode = FAIL;
                }
                //else {
                    //println("<li>All required attributes are populated.</li>");
                //}

                // check for all necessary relators
                println("</ul>" + NEW_LINE + "<b>Relator check:</b>" + NEW_LINE + "<ul>");
                if (!checkRelators()) {
                    iRetCode = FAIL;
                }
                else {
                    println("<li>All required relators are linked.</li>");
                }
                println("</ul>" + NEW_LINE + "<b>Price Check:</b>" + NEW_LINE + "<ul>");
                if (!checkNewPriceEffictiveDates()) {
                    iRetCode = FAIL;
                }
                else {
                    println("<li>The New Price Effective date is earlier than the Course Expiration date.</li>");
                }
                println("</ul>" + NEW_LINE + "<b>Volume Price Display Order Check:</b>" + NEW_LINE);
                if (!checkVPCDisplayOrder()) {
                    iRetCode = FAIL;
                }
                m_bOutputAsList = false;
            //}

                updateStatus(iRetCode);
            }
            if (iRetCode == PASS) {
                //MSG_IAB2009I = "%1# is now available for use.";
                printMessage(
                        MSG_IAB2009I,
                        new String[]{getABREntityDesc(getEntityType(), getEntityID())});
            }

            sb = new StringBuffer();
            sb.append(getShortClassName(getClass()));
            sb.append(" has ");
            sb.append((iRetCode == PASS) ? "Passed" : "Failed");
            sb.append(".<br />");
            println(sb.toString());

            setReturnCode(iRetCode);
            setDGTitle(getDGTitle(false));
        }
        catch (LockPDHEntityException le) {
            setReturnCode(FAIL);
            println(
                    "<h3 style=\"color:#c00; font-weight:bold;\">" +
                    ERR_IAB1007E +
                    "<br />" +
                    le.getMessage() +
                    "</h3>");
            logError(le.getMessage());
        }
        catch (UpdatePDHEntityException le) {
            setReturnCode(FAIL);
            println(
                    "<h3 style=\"color:#c00; font-weight:bold;\">UpdatePDH error: " +
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
            setDGRptClass("LSWWCCABR");
            // make sure the lock is released
            if (!isReadOnly()) {
                clearSoftLock();
            }
        }

		//Print everything up to </html>
		println(EACustom.getTOUDiv());
		printDGSubmitString();
		buildReportFooter();
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
    * LSWWCCCATEDITSTATUS
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
		String advMethod = null;
		String actionName = "WFLSWWCCEDITNOTREQ";
        String catStatusAttr = getRootEntityType()+"CATEDITSTATUS";
        boolean finish = false;
        if (strCAEdit.equals("0020"))
        {
            traceSb.append("cateditstatus flag: "+strCAEdit+" attval: "+
                getAttributeValue(getRootEntityType(), getRootEntityID(),catStatusAttr)+" is already set, no changes needed." + NEW_LINE);
            //return;
            finish = true;
        }

        if (!finish) {
	        advMethod = getAttributeFlagEnabledValue(getRootEntityType(), getRootEntityID(),"LSCRSADVERTISING", "");
	        traceSb.append("advmethod flag: "+advMethod+" attrval: "+
	            getAttributeValue(getRootEntityType(), getRootEntityID(),"LSCRSADVERTISING")+ NEW_LINE);
	        traceSb.append("cateditstatus flag: "+strCAEdit+" attval: "+
	            getAttributeValue(getRootEntityType(), getRootEntityID(),catStatusAttr)+ NEW_LINE);

	        if (((advMethod.indexOf("0010")!=-1) ||  // is Internet
	                (advMethod.indexOf("0020")!=-1)))   // or Hardcopy
	        {
	            traceSb.append("LSCRSADVERTISING was Internet or Hardcopy ("+
	                getAttributeValue(getRootEntityType(), getRootEntityID(),"LSCRSADVERTISING")+
	                ") so "+catStatusAttr+" will not be changed!" + NEW_LINE);
	            //return;
	            finish = true;
	        }

			if (!finish) {
		        if (strCAEdit.indexOf("0050")!=-1)  // Edited
		        {
		            traceSb.append(catStatusAttr+" was Edited ("+
		                    getAttributeValue(getRootEntityType(), getRootEntityID(),catStatusAttr)+
		                    ") so "+catStatusAttr+" will not be changed!" + NEW_LINE);
		            //return;
		            finish = true;
		        }

		        if (!finish) {
			        // WF changes the status but it isn't reflected in the current EntityItem!! so must set instance var
			        // - which changes the LSWWCCCATEDITSTATUS to Editing not Required
			        traceSb.append("TRIGGERING WF: "+actionName+ NEW_LINE);
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
     *  Description of the Method
     *
     * @return                          Description of the Return Value
     * @exception  java.lang.Exception  Description of the Exception
     */
    private boolean checkAttributes() throws java.lang.Exception {
        boolean bRetCode = true;
		int ipdhCT = -1;
		String strCTID = null;
		String strCTDESC = null;
		EANAttribute eaLSLANGINDICATOR = null;
		List lLSLANGINDICATOR = new ArrayList();
		String[] astrRequiredAttributes;
		String strCATADIND = null;
		EntityItem ei = null;
		Iterator iMF;

        // Check for all necessary attributes
        String strAudience = getAttributeFlagEnabledValue(getEntityType(), getEntityID(), "LSCRSAUDIENCE","");
        if ("0005".equals(strAudience)) {
            bRetCode = false;
            printErrorMessage(
                    ERR_IAB2027E,
                    new String[]{
                    getEntityDescription(getEntityType()),
                    getAttributeValue(getEntityType(), getEntityID(), "LSCRSID"),
                    getAttributeValue(getEntityType(), getEntityID(), "LSCRSTITLE"),
                    getAttributeDescription(getEntityType(), "LSCRSAUDIENCE")});
        }

        ipdhCT = getChildEntityId(getEntityType(), getEntityID(), "LSCT", "WWCCCTA");
        strCTID = getAttributeValue("LSCT", ipdhCT, "LSCTID");
        strCTDESC = getAttributeValue("LSCT", ipdhCT, "LSCTDESC");
        //CHECK 4  If the Country linked to Course is United States (country Code 897), then the Ceris Code attribute
        // must be populated.
        if ("897".equals(strCTID)) {
            String bResult = getAttributeValue(getEntityType(), getEntityID(), "LSCRSCERIS");
            if (bResult == null) {
                printErrorMessage(
                        ERR_IAB2019E,
                        new String[]{
                        strCTDESC,
                        getEntityDescription(getEntityType()),
                        getAttributeDescription(getEntityType(), "LSCRSCERIS")});
                bRetCode = false;
            }
        }
        eaLSLANGINDICATOR = m_elist.getParentEntityGroup().getEntityItem(0).getAttribute("LSLANGINDICATOR");

        if (eaLSLANGINDICATOR != null && eaLSLANGINDICATOR instanceof MultiFlagAttribute) {
            MetaFlag[] mfaLSLANGINDICATOR = (MetaFlag[]) ((MultiFlagAttribute) eaLSLANGINDICATOR).get();
            for (int ctMF = 0; ctMF < mfaLSLANGINDICATOR.length; ctMF++) {
                if (mfaLSLANGINDICATOR[ctMF].isSelected()) {
                    lLSLANGINDICATOR.add(mfaLSLANGINDICATOR[ctMF]);
                }
            }
        }
        // Now all selected languages are in lLSLANGINDICATOR
        //CHECK 6  The LSCRSTITLE must exist in all languages specified in the LSLANGINDICATOR attribute.
        strCATADIND = getAttributeFlagEnabledValue(getEntityType(), getEntityID(), "LSCATADIND","");
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
                bRetCode = false;
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
            MetaFlag mf = (MetaFlag) iMF.next();
			boolean hasAllForThisNLS = true;
            int nls = Integer.parseInt(mf.getShortDescription());
            println("<li>" + mf.getLongDescription() + ":<br /><ul>");

            for (int i = 0; i < astrRequiredAttributes.length; i++) {
                String[] astrMessage = new String[]{
                    getABREntityDesc(getEntityType(), getEntityID()),
                    getAttributeDescription(getEntityType(), astrRequiredAttributes[i])
                };

                EANAttribute attr = ei.getAttribute(astrRequiredAttributes[i]);
                if (attr == null) {
                    printErrorMessage(ERR_IAB1001E, astrMessage);
                    bRetCode = false;
                    hasAllForThisNLS = false;
                }
                else if (attr instanceof EANTextAttribute) {
                    EANTextAttribute tAttr = (EANTextAttribute) attr;
                    if (!tAttr.containsNLS(nls)) {
                        printErrorMessage(ERR_IAB1001E, astrMessage);
                        bRetCode = false;
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

        return bRetCode;
    }


    /**
     * check the volume price display order is unique
     *CHECK 14
Added per CR050902204
    A check must be done to see if any LSVPC entities are linked to a course.  If there is more than one
    LSVPC linked to a single course, the attribute LSCRSVPCDISPORD for each must be unique.
     */
    private boolean checkVPCDisplayOrder() {
        Iterator ids = getChildrenEntityIds(getEntityType(), getEntityID(), "LSVPC", "LSWWCCVPC").iterator();
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
                //return false;
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
     * CHECK 15
Added per CR1218022919
    A New Price Effective Date must not be later than or equal to the Course expiration date.
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
                        getAttributeDescription("LSWWCC",attCodes[i]),
                        getAttributeDescription("LSWWCC","LSCRSEXPDATE"),
                        expDate});
                bReturn = false;
            }
        }
        return bReturn;
    }


    /**
     * CHECK 9
    There must one and only one relator from the WWCC to each of the following entities:
-   Person as Course Contact
-   Person as Course Owner
-   RCODE
-   Worldwide Course
     *
     * @return                                   Description of the Return Value
     * @exception  java.lang.Exception           The exception description.
     */
    private boolean checkRelators() throws java.lang.Exception {
        boolean bRetCode = true;
		String method = null;
		Set setMethods = new HashSet();

        // A Worldwide In-Country Course must be linked to one and only one Person - Course Contact
        if (!checkRelator2("LSPER", "LSWWCCPERCON", UNIQUE2, true)) {
        //if (!checkUniqueChild("LSPER", "LSWWCCPERCON")) { // Behavior changed by LF
            bRetCode = false;
        }

        // A Worldwide In-Country Course must be linked to one and only one Person - Owner
        if (!checkRelator2("LSPER", "LSWWCCPEROW", UNIQUE2, true)) {
        //if (!checkUniqueChild("LSPER", "LSWWCCPEROW")) { // Behavior changed by LF
            bRetCode = false;
        }

        // A Worldwide In-Country Course must be linked to one and only one RCODE
        if (!checkRelator2("LSRC", "LSWWCCRC", UNIQUE2, true)) {
        //if (!checkUniqueChild("LSRC", "LSWWCCRC")) { // Behavior changed by LF
            bRetCode = false;
        }
        else {
        	//CHECK 16
        	//Added per CR0701025842
            //The RCODE linked to an LSWWCC must be linked to the same LSCT that is linked to the LSWWCC
            int iRC = getChildEntityId(getEntityType(), getEntityID(), "LSRC", "LSWWCCRC");
            int iRCCT = getChildEntityId("LSRC", iRC, "LSCT", "LSRCCT");
            int iCT = getChildEntityId(getEntityType(), getEntityID(), "LSCT", "WWCCCTA");
            if (iRCCT == iCT) {
                println("<!-- RCODE country matches -->");
            }
            else {
                printErrorMessage(
                        "There is an invalid pairing of %1# %2# and %3# %4# for this %5#.",
                        new String[]{
                        getEntityDescription("LSRC"),
                        getAttributeValue("LSRC", iRC, "LSRCID"),
                        getEntityDescription("LSCT"),
                        getAttributeValue("LSCT", iCT, "LSCTDESC"),
                        getEntityDescription(getEntityType())});
                bRetCode = false;
            }
        }

        /*
         * CHECK 12
    The associated Segment and Worldwide Course parent must have a Status of 'Available'.
    The Worldwide In-Country Course must have an Expiration Date that is less than or equal to the
    Expiration date of the parent Worldwide Course
         */
        // A Worldwide In-Country Course can have only one Worldwide Course parent
        if (!checkRelator2("LSWW", "LSWWWWCC", UNIQUE2, false)) {
        //if (!checkUniqueParent("LSWW", "LSWWWWCC")) { // Behavior changed by LF
            bRetCode = false;
        }
        else {
            boolean check = false;
            String lswwccexpDate = getAttributeValue(getEntityType(), getEntityID(), "LSCRSEXPDATE", "1900-01-01");
            println("<!-- "+getEntityType()+getEntityID()+" LSCRSEXPDATE "+lswwccexpDate+" -->");
            // Assume all LSWW parents are Available
            Iterator ids = getParentEntityIds(getEntityType(), getEntityID(), "LSWW", "LSWWWWCC").iterator();
            while (ids.hasNext()) {
                int iParentID = ((Integer) ids.next()).intValue();
                if (!XR_LSWWLIFECYCLE_Available.equals(getAttributeFlagEnabledValue("LSWW", iParentID, "LSWWLIFECYCLE",""))) {
                    printErrorMessage(
                    //"IAB2032E: The %1 %2 %3 is linked to %4 %5 %6 with a %7 that is not %8.";
                            ERR_IAB2032E,
                            new String[]{
                            getEntityDescription(getEntityType()),
                            getAttributeValue(getEntityType(), getEntityID(), "LSCRSID"),
                            getAttributeValue(getEntityType(), getEntityID(), "LSCRSTITLE"),
                            getEntityDescription("LSWW"),
                            getAttributeValue("LSWW", iParentID, "LSWWID"),
                            getAttributeValue("LSWW", iParentID, "LSWWTITLE"),
                            getAttributeDescription("LSWW", "LSWWLIFECYCLE"),
                            getAttributeMetaFlagDescription("LSWW", "LSWWLIFECYCLE", XR_LSWWLIFECYCLE_Available)});
                    check = true;
                }
                // check expiration date 
                String lswwexpdate = getAttributeValue("LSWW", iParentID, "LSWWEXPDATE", "1900-01-01");
                println("<!-- LSWW"+iParentID+" LSWWEXPDATE "+lswwexpdate+" -->");

                if (lswwccexpDate.compareTo(lswwexpdate)> 0) {
                	printErrorMessage(
                			"The %1# cannot be after the %2# of %3#.",
                			new String[]{
                					getAttributeDescription("LSWWCC","LSCRSEXPDATE"),
                					getAttributeDescription("LSWW","LSWWEXPDATE"),
                					lswwexpdate});
                	check = true;
                }
            }

//          Assume all LSSEG parents are Available
            ids = getChildrenEntityIds(getEntityType(), getEntityID(), "LSSEG", "WWCCSEGA").iterator();
            while (ids.hasNext()) {
                int ichildID = ((Integer) ids.next()).intValue();
                if (!XR_LSSEGLIFECYCLE_Available.equals(getAttributeFlagEnabledValue("LSSEG", ichildID, "LSSEGLIFECYCLE",""))) {
                    printErrorMessage(
                    //"IAB2032E: The %1 %2 %3 is linked to %4 %5 %6 with a %7 that is not %8.";
                            ERR_IAB2032E,
                            new String[]{
                            getEntityDescription(getEntityType()),
                            getAttributeValue(getEntityType(), getEntityID(), "LSCRSID"),
                            getAttributeValue(getEntityType(), getEntityID(), "LSCRSTITLE"),
                            getEntityDescription("LSSEG"),
                            getAttributeValue("LSSEG", ichildID, "LSSEGID"),
                            getAttributeValue("LSSEG", ichildID, "LSSEGTITLE"),
                            getAttributeDescription("LSSEG", "LSSEGLIFECYCLE"),
                            getAttributeMetaFlagDescription("LSSEG", "LSSEGLIFECYCLE", XR_LSSEGLIFECYCLE_Available)});
                    check = true;
                }
            }
            if (check) {
                bRetCode = false;
            }
        }

        // A Worldwide In-Country Course must be linked to one and only one Segment
        if (anyRetiredChildren(
                getEntityType(),
                getEntityID(),
                "LSSEG",
                "WWCCSEGA",
                "LSSEGLIFECYCLE",
                XR_LSSEGLIFECYCLE_Retired))
        {
            bRetCode = false;
        }

        /*
         * CHECK 10
    The Worldwide In-Country Course must have one and only one relator to a Person as STL Contact if the
    Delivery Method is one of the following:
   22 ON-LINE, INSTRUCTOR-LED
   01 CLASSROOM
   07 NATIONAL TECHNOLOGICAL UNIVERSITY (NTU)
   13 SATELLITE

CHECK 11
The Worldwide In-Country Course must have at least one, but possibly more than one, relator to a Material
Set if the Delivery is one of the following:
   22 ON-LINE, INSTRUCTOR-LED
   01 CLASSROOM
   07 NATIONAL TECHNOLOGICAL UNIVERSITY (NTU)
   13 SATELLITE
         */
        method = getAttributeFlagEnabledValue(getEntityType(), getEntityID(), "LSCRSDELIVERY","");

        setMethods.add(XR_LSCRSDELIVERY_Classroom);
        setMethods.add(XR_LSCRSDELIVERY_OnLineInstructorLed);
        setMethods.add(XR_LSCRSDELIVERY_Satellite);
        setMethods.add(XR_LSCRSDELIVERY_NTU);
        setMethods.add("0030"); // CONFERENCE
        if (setMethods.contains(method) &&
            (!checkRelator2("LSPER", "LSWWCCPERSTL", UNIQUE2, true) |
                //(!checkUniqueChild("LSPER", "LSWWCCPERSTL") | // Behavior changed by LF
                !checkMultipleChild("LSMAT", "LSWWCCMAT"))) {
            bRetCode = false;
        }
        return bRetCode;
    }


    /**
     *b
     * Insert the method's description here.
     * Creation date: (10/10/2001 2:04:56 PM)
     *
     * @param  _iRetCode                         Description of the Parameter
     * @exception  java.lang.Exception           The exception description.
     */
    private void updateStatus(int _iRetCode) throws java.lang.Exception {
        //based on TA feed indicator
		Set advMethods = new HashSet();
        String strTA = getAttributeFlagEnabledValue(getEntityType(), getEntityID(), "LSCRSTASERVERINDICATOR","");
        String strPricing = getAttributeFlagEnabledValue(getEntityType(), getEntityID(), "LSWWCCPRICINGSTATUS","");
        //String strCAEdit = getAttributeFlagEnabledValue(getEntityType(), getEntityID(), "LSWWCCCATEDITSTATUS","");
        //Catalog edit status may have been adjusted earlier but not reflected in current entity, use instance variable

        // update lifecycle status field based on checks
        // map int to strings
        String[] flags = convertToArray(getAttributeFlagEnabledValue(getEntityType(), getEntityID(), "LSCRSADVERTISING",""));
        Set selectedFlags = new HashSet();
        for (int i = 0; i < flags.length; i++) {
            selectedFlags.add(flags[i]);
        }

        advMethods.add("0010");
        advMethods.add("0020");

        if (_iRetCode == FAIL) {
            triggerWorkFlow("WFLSWWCCFL");
        }
        else if ("0040".equals(strTA)) {
            setFlagValue("LSCRSTAAUD", getAttributeFlagEnabledValue(getEntityType(), getEntityID(), "LSCRSAUDIENCE","0010"));
            triggerWorkFlow("WFLSWWCCCSNRAVCL");
            triggerLock("EXTLSWWCCLOCK");
        }
        else if ("0030".equals(strPricing) && "0050".equals(strCAEdit)) {
            setFlagValue("LSCRSTAAUD", getAttributeFlagEnabledValue(getEntityType(), getEntityID(), "LSCRSAUDIENCE","0010"));
            triggerWorkFlow("WFLSWWCCAVCL");
            triggerLock("EXTLSWWCCLOCK");
        }
        else {
            if (selectedFlags.contains("0010") || selectedFlags.contains("0020")) {
                if ("0030".equals(strCAEdit) || // Requires Catalog Editing
                    "0040".equals(strCAEdit) || // Under Edit
                    "0050".equals(strCAEdit))   // Edited
                {
                    triggerWorkFlow("WFLSWWCCAVA");
                }
                else if ("0010".equals(strCAEdit) || // Not Edited
                    "0020".equals(strCAEdit) || // Editing Not Required
                    "0060".equals(strCAEdit))   // Denied
                {
                    // Requires Catalog Editing
                    triggerWorkFlow("WFLSWWCCCSREAVA");
                }
            }
            else if ("0030".equals(strPricing)) { // Approved
                setFlagValue("LSCRSTAAUD", getAttributeFlagEnabledValue(getEntityType(), getEntityID(), "LSCRSAUDIENCE","0010"));
                triggerWorkFlow("WFLSWWCCAVCL");
                triggerLock("EXTLSWWCCLOCK");
            }
            if ("0010".equals(strPricing) || // Not Approved
                "0020".equals(strPricing) || // Needs Pricing Approval
                "0040".equals(strPricing))   // Denied
            {
                //Needs Pricing Approval
                triggerWorkFlow("WFLSWWCCPSNAAVA");
            }
        }
    }


    /**
     * Insert the method's description here.
     * Creation date: (10/10/2001 10:12:12 AM)
     *
     * @return                                   boolean
     * @exception  java.lang.Exception           The exception description.
     */
/*
    private boolean checkTemplate() throws java.lang.Exception {
        boolean bRetCode = true;
        String strLSCRSTEMPLATENAME = getAttributeValue(getEntityType(), getEntityID(), "LSCRSTEMPLATENAME");
        println("\n<b>Template check:</b>\n<ul>");
        if ("9999".equals(strLSCRSTEMPLATENAME)) {
            println("<li>");
            printMessage(
                    MSG_IAB2024I,
                    new String[]{
                    getAttributeDescription(getEntityType(), "LSCRSTEMPLATENAME"),
                    strLSCRSTEMPLATENAME});
            println("</li>");
        }
        else {
            EntityItem template = null;
            Iterator itWW = getParentEntityIds(getEntityType(), getEntityID(), "LSWW", "LSWWWWCC").iterator();
            while (itWW.hasNext()) {
                Integer pdhWW = (Integer) itWW.next();
                //PokPDHNavEntity navWW = new PokPDHNavEntity(getProfile(),getDatabase(), "LSWW", pdhWW.intValue());
                Iterator itWWCC = getChildren("LSWWCC", "LSWWWWCCTEMP").iterator();
                while (itWWCC.hasNext()) {
                    EntityItem navWWCC = (EntityItem) itWWCC.next();
                    if (strLSCRSTEMPLATENAME.equals(getAttributeValue(navWWCC, "LSCRSTEMPLATENAME", ""))) {
                        template = navWWCC;
                    }
                }
            }
            if (template == null) {
                println("<li>");
                printErrorMessage(
                        ERR_IAB2025E,
                        new String[]{
                        getEntityDescription("LSWWCC"),
                        getAttributeValue(getEntityType(), getEntityID(), "LSCRSID"),
                        getAttributeValue(getEntityType(), getEntityID(), "LSCRSTITLE")});
                println("</li>");
                bRetCode = false;
            }
            else {
                //compare attributes to template

                String[][] checks = new String[][]{
                        {"LSCRSDELIVERYSTATUS", "LSCRSDELIVERY"},
                        {"LSCRSSUBDELIVERYSTATUS", "LSCRSSUBDELIVERY"},
                        {"LSCRSMEDIASTATUS", "LSCRSMEDIA"},
                        {"LSCRSMATLLANGSTATUS", "LSCRSMATERIALSLANG"},
                        {"LSCRSDURATIONSTATUS", "LSCRSDURATION"},
                        {"LSCRSDURATIONSTATUS", "LSCRSDURATIONUNITS"}
                        };
                for (int i = 0; i < checks.length; i++) {
                    if ("0020".equals(getAttributeFlagEnabledValue(template, checks[i][0]))) {
                        String strTemplateValue = getAttributeValue(template, checks[i][1], "");
                        if (!strTemplateValue.equals(getAttributeValue(getEntityType(), getEntityID(), checks[i][1], ""))) {
                            println("<li>");
                            printErrorMessage(
                                    ERR_IAB2022E,
                                    new String[]{
                                    getEntityDescription("LSWWCC"),
                                    getAttributeValue(getEntityType(), getEntityID(), "LSCRSID"),
                                    getAttributeValue(getEntityType(), getEntityID(), "LSCRSTITLE"),
                                    getEntityDescription("LSWWCC"),
                                    getAttributeDescription(getEntityType(), checks[i][1])});
                            println("</li>");
                            bRetCode = false;
                        }
                    }
                }
                if (bRetCode) {
                    println("<li>All mandatory template attributes match.</li>");
                }
            }
        }
        println("</ul>");
        return bRetCode;
    }
*/

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
        COM.ibm.eannounce.objects.WorkflowException,
        COM.ibm.eannounce.objects.EANBusinessRuleException
    {
        // No current OP associated with course
        // need to link to the OP for the submitter
		LinkActionItem lai = null;
		SearchActionItem sai = null;
		EntityList list = null;
		Iterator items = null;
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
            logMessage("LSWWCCABR1:" + getEntityID() + " No OP found for " + m_prof.getEmailAddress());
        }
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
     * Get ABR description
     *
     * @return    java.lang.String
     */
    public String getDescription() {
        return "In-Country Course Validation and Work Flow initiation.";
    }


    /**
     *  Get the version
     *
     * @return    java.lang.String
     */
    public String getABRVersion() {
        return "$Revision: 1.34 $";
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
            int iParentID = ((Integer) ids.next()).intValue();
            String flagVal = getAttributeFlagEnabledValue(_strParentType, iParentID, _strAttributeCode, "");
            log("anyRetiredParents2:flagVal=" + flagVal);
            if (!"".equals(flagVal) && !_strFlagValue.equals(flagVal)) {
				String flagDesc = "<em>** Not Populated **</em>";
                bReturn = false;

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
		int iCount = -1;
        Vector vct = null;
		boolean ret = true;
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
            }
            break;
        case ONE_OR_LESS2:                              // may have only one
            if (iCount > 1) {
                printErrorMessage(ERR_IAB1003E, astrMessage);
                //return false;
                ret = false;
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
    private static final int MAX_LIST_LEN=256;
    private static final String NEWLINE="\n";
    /********************************************************************************
    * display content of entitylist for debug
    *@param  list    EntityList
    *@return String
    */
    public static String outputList(EntityList list) // debug only
    {
        StringBuffer sb = new StringBuffer();
        EntityGroup peg = null;
        if (list==null) {
            sb.append("Null List");
        }else{
            peg =list.getParentEntityGroup();
            if (peg!=null)
            {
                sb.append(peg.getEntityType()+" : "+peg.getEntityItemCount()+" parent items. ");
                if (peg.getEntityItemCount()>0)
                {
                    StringBuffer tmpsb = new StringBuffer();  // prevent more than 2048 chars in a line
                    tmpsb.append("IDs(");
                    for (int e=0; e<peg.getEntityItemCount(); e++)
                    {
                        tmpsb.append(" "+peg.getEntityItem(e).getEntityID());
                        if (tmpsb.length()>MAX_LIST_LEN)
                        {
                            sb.append(tmpsb.toString()+NEWLINE);
                            tmpsb.setLength(0);
                        }
                    }
                    tmpsb.append(")");
                    sb.append(tmpsb.toString());
                }
                sb.append(NEWLINE);
            }

            for (int i=0; i<list.getEntityGroupCount(); i++)
            {
                EntityGroup eg =list.getEntityGroup(i);
                sb.append(eg.getEntityType()+" : "+eg.getEntityItemCount()+" entity items. ");
                if (eg.getEntityItemCount()>0)
                {
                    StringBuffer tmpsb = new StringBuffer();  // prevent more than 2048 chars in a line
                    tmpsb.append("IDs(");
                    for (int e=0; e<eg.getEntityItemCount(); e++)
                    {
                        tmpsb.append(" "+eg.getEntityItem(e).getEntityID());
                        if (tmpsb.length()>MAX_LIST_LEN)
                        {
                            sb.append(tmpsb.toString()+NEWLINE);
                            tmpsb.setLength(0);
                        }
                    }
                    tmpsb.append(")");
                    sb.append(tmpsb.toString());
                }
                sb.append(NEWLINE);
            }
        }
        return sb.toString();
    }
}
