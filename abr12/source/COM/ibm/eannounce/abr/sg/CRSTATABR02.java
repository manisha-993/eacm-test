//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
//CRSTATABR02.java,v
//Revision 1.14  2008/01/30 20:02:00  wendy
//Cleanup RSA warnings
//
//Revision 1.13  2006/03/13 19:42:03  couto
//Fixed copyright info.
//
//Revision 1.12  2006/03/13 13:31:28  couto
//Changed layout, using EACustom methods. Changes for Jtest.
//Fixed br tags. Chaged font tags. Fixed table header.
//buildReportHeader and printNavigateAttributes are now local methods.
//
//Revision 1.11  2006/03/03 19:23:27  bala
//remove reference to Constants.CSS
//
//Revision 1.10  2006/01/24 17:08:03  yang
//Jtest Changes
//
//Revision 1.9  2003/11/07 01:21:06  yang
//Adding setDGRptClass
//
//Revision 1.8  2003/09/18 20:05:25  yang
//syntax
//
//Revision 1.7  2003/09/18 19:51:38  yang
//adding bala's stuff to finally {
//
//Revision 1.6  2003/09/18 18:48:04  yang
//more fixes
//
//Revision 1.5  2003/09/18 18:08:49  yang
//test
//
//Revision 1.4  2003/06/04 03:53:08  dave
//un Staticing getABRVersion
//
//Revision 1.3  2003/06/04 03:44:25  dave
//minor syntax
//
//Revision 1.2  2003/06/04 03:41:44  dave
//adding getABRVersion
//
//Revision 1.1.1.1  2003/06/03 19:02:24  dave
//new 1.1.1 abr
//
//Revision 1.16  2002/12/12 21:52:02  minhthy
//printNavigateAttributes()
//
//Revision 1.15  2002/11/06 22:06:44  naomi
//remove setReturnCode(PASS) in the finally clause
//
//Revision 1.14  2002/10/31 18:35:19  naomi
//added setDGName
//
//Revision 1.13  2002/10/22 20:00:15  naomi
//add entityID for setDGTitle
//
//Revision 1.12  2002/10/17 00:36:00  minhthy
//changed 'ANCYCLESTATUS' description
//
//Revision 1.11  2002/10/14 22:04:28  minhthy
//added ANNDESC
//
//Revision 1.10  2002/10/11 23:08:44  minhthy
//added "ChangeRequest's Details'
//
//Revision 1.9  2002/10/08 00:47:41  minhthy
//modified perform checks
//
//Revision 1.8  2002/09/19 21:18:25  naomi
//Added buildReportHeader(),setDGTitle() and replaced getEntityGroup() with getParentEntityGroup()
//
//Revision 1.7  2002/09/17 20:38:04  naomi
//added the Class Description
//
//Revision 1.6  2002/09/11 21:15:17  bala
//remove getTable function as its not used anymore...also getAllChildren
//
//Revision 1.5  2002/09/06 16:45:45  bala
//removing DG update code...will be done by taskmaster from now on
//
//Revision 1.4  2002/09/04 22:01:28  bala
//1 more message
//
//Revision 1.3  2002/09/04 21:59:28  bala
//added message
//
//Revision 1.2  2002/09/04 21:49:51  bala
//strStatus defined twice. correcting
//
//Revision 1.1  2002/09/04 21:47:38  bala
//check in
//

package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.*;
//import COM.ibm.opicmpdh.transactions.*;
//import COM.ibm.opicmpdh.taskmaster.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;

import java.sql.SQLException;
import java.util.*;
//import java.io.*;

/**
 *  Description of the Class
 *  This ABR handles the case where CRSTATUS is set to Requires PDT Review by the ODM
 *  and produces a report for the Announcement entity
 *  Return Code
 *  Pass
 *
 *@author
 *@created
 */
public class CRSTATABR02 extends PokBaseABR {
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.";
	
  /**
   *  Execute ABR.
   *
   */
  public final static String ABR = "CRSTATABR02";

  private EntityGroup m_egParent = null;
  private EntityItem m_ei = null;

  //ANCYCLESTATUS
  //private final String strANCYCLEValue1 = "111"; //Draft
  //private final String strANCYCLEValue2 = "112"; //Ready for Final Review
  //private final String strANCYCLEValue3 = "113"; //Change(Final Review)

  /**
     * execute_run
     *
     * @author Owner
     */
    public void execute_run() {
    String strANNDesc = null;
    //String strStatus = null;
    String strCR = null;
    String strStatus1 = null;
    String strEntityType = null;
    EntityGroup annGroup = null;
    EntityGroup egParent = null;
    try {
      start_ABRBuild();

      // Build the report header
      //buildReportHeader();
	  buildRptHeader();

      m_egParent = m_elist.getParentEntityGroup();
      m_ei = m_egParent.getEntityItem(0);

      setDGTitle(setDGName(m_ei, ABR));

      /*
       *  ANNOUNCEMENT
       *  |
       *  CHANGEREQUEST ** We are here at this point
       *
       *  Get the announcement columns
       *  Set the CHANGEREQUEST to value based on ANCYCLESTATUS
       */

      setReturnCode(PASS);
      // Print parents
      annGroup = m_elist.getEntityGroup("ANNOUNCEMENT");

      if (getEntityType() == null) {
        logMessage("****************Announcement Not found ");
        setReturnCode(FAIL);

      }

      for (int iALL = 0; iALL < annGroup.getEntityItemCount(); iALL++) {
        EntityItem entity = annGroup.getEntityItem(iALL);

        logMessage(
          "************Entity Type returned is "
            + entity.getEntityType()
            + ", "
            + entity.getEntityID());

        println("<br /><b>" + annGroup.getLongDescription() + "</b>");
        //printNavigateAttributes(entity, annGroup, true);
		printNavAttributes(entity, annGroup, true, iALL + 1);

        strANNDesc =
          getAttributeValue(
            entity.getEntityType(),
            entity.getEntityID(),
            "ANNDESC",
            "<em>** Not Populated ** </em>");
        println(
          "<br /><table summary=\"Announcement Description\" width=\"100%\"><tr><td class=\"PsgLabel\">Announcement Description</td></tr><tr><td class=\"PsgText\">"
            + strANNDesc
            + "</td></tr></table>");

        /*strStatus =
          getAttributeValue(
            entity.getEntityType(),
            entity.getEntityID(),
            "ANCYCLESTATUS",
            "<em>** Not Populated ** </em>");*/

        strEntityType = entity.getEntityType();
        strStatus1 = getFlagCode(entity, "ANCYCLESTATUS");
        logMessage("*************** strStatus1: " + strStatus1);

        /*if (strStatus != null) {
          EANFlagAttribute ema = (EANFlagAttribute) strStatus;
          MetaFlag[] amf = (MetaFlag[]) ema.get();
          for (int f = 0; f < amf.length; f++) {
            if (amf[f].isSelected()) {
              String sFlagClass = amf[f].getFlagCode().trim();
              logMessage("*************** sFlagClass: " + sFlagClass);*/

        if (strEntityType.equals("ANNOUNCEMENT")
          && strStatus1.equals("111")) {
          strCR = "117";
        } else if (
          strEntityType.equals("ANNOUNCEMENT")
          && strStatus1.equals("112")) {
          strCR = "117";
        } else if (
          strEntityType.equals("ANNOUNCEMENT")
          && strStatus1.equals("113")) {
          strCR = "117";
        }
      }
      logMessage("******************AFter checking Status of announcement");

      /*
       *  Now Update the CHANGEREQUEST column in CHANGEREQUEST Entity
       */
      /*try {
        EntityGroup rstAnnReview = m_elist.getEntityGroup("CHANGEREQUEST");
        RowSelectableTable rstAnnouncement = rstAnnReview.getEntityGroupTable();

        int iTextCol = rstAnnouncement.getColumnIndex("CHANGEREQUEST:CHANGEREQUEST");
        logMessage("**********************Text column returned " + iTextCol);
        rstAnnouncement.put(0, iTextCol, strCR);
        rstAnnouncement.commit(getDatabase());
        setReturnCode(PASS);
      } catch (EANBusinessRuleException ebx) {
        D.ebug(D.EBUG_ERR, " trouble getting ABR update for ANNReview " + ebx.getMessage());
      }*/

      /*
      *  Now Update the CHANGEREQUEST column in CHANGEREQUEST Entity
      */
      if (strCR != null) {
        EntityItem eiParm =
          new EntityItem(
            null,
            m_prof,
            getEntityType(),
            getEntityID());
        ReturnEntityKey rek =
          new ReturnEntityKey(
            eiParm.getEntityType(),
            eiParm.getEntityID(),
            true);
        DatePackage dbNow = m_db.getDates();
        String strNow = dbNow.getNow();
        String strForever = dbNow.getForever();
        ControlBlock cbOn =
          new ControlBlock(
            strNow,
            strForever,
            strNow,
            strForever,
            m_prof.getOPWGID(),
            m_prof.getTranID());
        SingleFlag sf =
          new SingleFlag(
            m_prof.getEnterprise(),
            rek.getEntityType(),
            rek.getEntityID(),
            "CRSTATUS",
            strCR,
            1,
            cbOn);
        Vector vctAtts = new Vector();
        Vector vctReturnsEntityKeys = new Vector();

        if (sf != null) {
          vctAtts.addElement(sf);
        }

        rek.m_vctAttributes = vctAtts;
        vctReturnsEntityKeys.addElement(rek);

        m_db.update(m_prof, vctReturnsEntityKeys, false, false);
        m_db.commit();
        println("<br /><b>This Change Request was cancelled because the Announcement is not eligible for Change Requests.</b>");

      } else {
        println("<br /><b>Please review and approve as required.</b>");
      }
      
      strANNDesc = null;
      strCR = null;

      //} //end of for loop

      //reRun Extract Action after DB updated
      m_prof.setValOn(getValOn());
      m_prof.setEffOn(getEffOn());
      start_ABRBuild();

      egParent = m_elist.getParentEntityGroup();
      if (egParent == null) {
        logMessage("**************** CHANGEREQUEST Not found ");
        setReturnCode(FAIL);

      } else {
        EntityItem entity = egParent.getEntityItem(0);
        println("<br /><br /><b>" + egParent.getLongDescription() + "</b>");
        //printNavigateAttributes(entity, annGroup, false);
		printNavAttributes(entity, annGroup, false, 0);
      }

      println(
        "<br /><b>"
          + buildMessage(
            MSG_IAB2016I,
            new String[] {
              getABRDescription(),
              (getReturnCode() == PASS ? "Passed" : "Failed")})
          + "</b>");

      log(
        buildLogMessage(
          MSG_IAB2016I,
          new String[] {
            getABRDescription(),
            (getReturnCode() == PASS ? "Passed" : "Failed")}));
    } catch (LockPDHEntityException le) {
      setReturnCode(UPDATE_ERROR);
      println(
        "<h3 style=\"color:#c00; font-weight:bold;\">"
          + ERR_IAB1007E
          + "<br />"
          + le.getMessage()
          + "</h3>");
      logError(le.getMessage());
    } catch (UpdatePDHEntityException le) {
      setReturnCode(UPDATE_ERROR);
      println(
        "<h3 style=\"color:#c00; font-weight:bold;\">UpdatePDH error: "
          + le.getMessage()
          + "</h3>");
      logError(le.getMessage());
    } catch (Exception exc) {
      // Report this error to both the datbase log and the PrintWriter
      println("Error in " + m_abri.getABRCode() + ":" + exc.getMessage());
      println("" + exc);

      // don't overwrite an update exception
      if (getABRReturnCode() != UPDATE_ERROR) {
        setReturnCode(INTERNAL_ERROR);
      }
    } finally {
      //Everything is fine...so lets pass
      //setReturnCode(PASS);
      // set DG submit string
      setDGString(getABRReturnCode());
      setDGRptName("CRSTATABR02"); //Set the report name
      setDGRptClass("CRSTATABR02"); //Set the report class
      // make sure the lock is released
      if (!isReadOnly()) {
        clearSoftLock();
      }
    }
    
	//Print everything up to </html>
	printDGSubmitString();
	println(EACustom.getTOUDiv());
	buildReportFooter();    // Print </html>
  }

  /**
   *  Get the entity description to use in error messages
   *
   *@param  entityType  Description of the Parameter
   *@param  entityId    Description of the Parameter
   *@return             String
   */
  protected String getABREntityDesc(String entityType, int entityId) {
    return null;
  }

  /**
   *  Get ABR description
   *
   *@return    java.lang.String
   */
  public String getDescription() {
    //return Constants.IAB3053I + "<br /><br />" + Constants.IAB3050I;
    return "<br /><br />";
  }

  /**
   *  Get any style that should be used for this page. Derived classes can
   *  override this to set styles They must include the <style>...</style> tags
   *
   *@return    String
   */
  protected String getStyle() {
    // Print out the PSG stylesheet
    return "";
  }

  /**
     * getRevision
     *
     * @return
     * @author Owner
     */
    public String getRevision() {
    return "1.14";
  }
  /**
     * getVersion
     *
     * @return
     * @author Owner
     */
    public static String getVersion() {
    return ("CRSTATABR02.java,v 1.14 2008/01/30 20:02:00 wendy Exp");
  }

  /**
     * getABRVersion
     *
     * @return
     * @author Owner
     */
    public String getABRVersion() {
    return getVersion();
  }

  /*
   *  builds the standard header and submits it to the passed PrintWriter
   */
  private void buildRptHeader() throws SQLException, MiddlewareException{

	  String strVersion = getVersion();
	  String astr1[] = new String[]{getABRDescription(), strVersion, getEnterprise()};
	  String strMessage1 = buildMessage(MSG_IAB0001I, astr1);
	
	  // output html header
	  //String abrName = getShortClassName(getClass());
	  //println("<html>\n<head>\n<title>" + abrName + "</title>\n" +
	  //	getStyle() + "</head>\n<body>\n");
		
	  println(EACustom.getDocTypeHtml());
	  println("<head>");
	  println(EACustom.getMetaTags("CRSTATABR02.java"));
	  println(EACustom.getCSS());
	  println(EACustom.getTitle(getShortClassName(getClass())));
	  println("</head>");
	  println("<body id=\"ibm-com\">");
	  println(EACustom.getMastheadDiv());
	  
	  println("<p class=\"ibm-intro ibm-alternate-three\"><em>" + strMessage1 + "</em></p>");

	  println("<table summary=\"layout\" width=\"750\" cellpadding=\"0\" cellspacing=\"0\">" + NEW_LINE +
		  "<tr><td width=\"25%\"><b>Abr Version: </b></td><td>" +
		  getABRVersion() + "</td></tr>" + NEW_LINE +
		  "<tr><td width=\"25%\"><b>Valid Date: </b></td><td>" +
		  getValOn() + "</td></tr>" + NEW_LINE +
		  "<tr><td width=\"25%\"><b>Effective Date: </b></td><td>" +
		  getEffOn() + "</td></tr>" + NEW_LINE +
		  "<tr><td width=\"25%\"><b>Date Generated: </b></td><td>" +
		  getNow() + "</td></tr>" + NEW_LINE +
		  "</table>");
	  println("<h3>Description: </h3>" + getDescription() + NEW_LINE + "<hr />" + NEW_LINE);

  }
  
  /*
   *Get  Attributes of an Entity
   *
   */
  private void printNavAttributes(EntityItem entity, EntityGroup eg, boolean navigate, int counter){
	  
	  println("<br /><br /><table summary=\"" + eg.getLongDescription() + "\" width=\"100%\">" + NEW_LINE);

	  for(int i=0;i<eg.getMetaAttributeCount();i++){
		  EANMetaAttribute ma = eg.getMetaAttribute(i);
		  EANAttribute att = entity.getAttribute(ma.getAttributeCode());
		  if(navigate){
			  if(i ==0) {
				  println("<tr><th class=\"PsgLabel\" width=\"50%\" id=\"attribute" + counter + "\">Navigation Attribute</th>");
				  println("<th class=\"PsgLabel\" width=\"50%\" id=\"value" + counter + "\">Attribute Value</th></tr>");				  				 
			  }
			  if(ma.isNavigate()) {
				  println("<tr><td headers=\"attribute" + counter + "\" class=\"PsgText\" width=\"50%\">"+ ma.getLongDescription()+"</td>");
				  println("<td headers=\"value" + counter + "\" class=\"PsgText\" width=\"50%\">" +(att == null || att.toString().length() ==0 ? "** Not Populated **" : att.toString()) + "</td></tr>");
			  }
		  }
		  else {
			  if(i ==0) {
				  println("<tr><th class=\"PsgLabel\" width=\"50%\" id=\"attribute" + counter + "\">Attribute Description</th>");
				  println("<th class=\"PsgLabel\" width=\"50%\" id=\"value" + counter + "\">Attribute Value</th></tr>");				  				  
			  }
			  println("<tr><td headers=\"attribute" + counter + "\" class=\"PsgText\" width=\"50%\">"+ ma.getLongDescription()+"</td>");
			  println("<td headers=\"value" + counter + "\" class=\"PsgText\" width=\"50%\">" +(att == null || att.toString().length() ==0 ? "** Not Populated **" : att.toString()) + "</td></tr>");			  
		  }

	  }
	  println("</table>");

 }

}
