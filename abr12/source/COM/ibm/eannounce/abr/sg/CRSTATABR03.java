//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
//CRSTATABR03.java,v
//Revision 1.15  2008/01/30 20:02:00  wendy
//Cleanup RSA warnings
//
//Revision 1.14  2006/03/13 19:42:03  couto
//Fixed copyright info.
//
//Revision 1.13  2006/03/13 13:32:35  couto
//Changed layout, using EACustom methods. Changes for Jtest.
//Fixed br tags. Chaged font tags. Fixed table header.
//buildReportHeader and printNavigateAttributes are now local methods.
//
//Revision 1.12  2006/03/03 19:23:28  bala
//remove reference to Constants.CSS
//
//Revision 1.11  2006/01/24 17:08:14  yang
//Jtest Changes
//
//Revision 1.10  2003/11/07 01:21:12  yang
//Adding setDGRptClass
//
//Revision 1.9  2003/09/18 19:51:44  yang
//adding bala's stuff to finally {
//
//Revision 1.8  2003/06/09 18:37:32  minhthy
//fixed minor error for report
//
//Revision 1.4  2003/06/04 03:53:09  dave
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
//Revision 1.13  2002/12/12 21:52:14  minhthy
//printNavigateAttributes()
//
//Revision 1.12  2002/11/06 22:07:21  naomi
//remove setReturnCode(PASS) in the finally clause
//
//Revision 1.11  2002/10/31 18:35:28  naomi
//added setDGName
//
//Revision 1.10  2002/10/22 21:52:56  minhthy
//added strANNDesc
//
//Revision 1.9  2002/10/22 20:00:22  naomi
//add entityID for setDGTitle
//
//Revision 1.8  2002/10/14 22:04:47  minhthy
//added ANNDESC
//
//Revision 1.7  2002/10/11 23:09:09  minhthy
//added "changeRequest's Details"
//
//Revision 1.6  2002/10/10 22:32:19  minhthy
//*** empty log message ***
//
//Revision 1.5  2002/09/19 21:21:48  naomi
//Added buildReportHeader(),setDGTitle() and replaced getEntityGroup() with getParentEntityGroup()
//
//Revision 1.4  2002/09/17 20:39:58  naomi
//added the Class Description
//
//Revision 1.3  2002/09/11 21:15:17  bala
//remove getTable function as its not used anymore...also getAllChildren
//
//Revision 1.2  2002/09/06 16:45:15  bala
//removing DG update portion...will be done by taskmaster from now on
//
//Revision 1.1  2002/09/04 22:16:01  bala
//check in
//
//Revision 1.1  2002/09/04 22:10:58  bala
//check in
//

package COM.ibm.eannounce.abr.sg;

//import COM.ibm.opicmpdh.middleware.*;
//import COM.ibm.opicmpdh.objects.*;
//import COM.ibm.opicmpdh.transactions.*;
//import COM.ibm.opicmpdh.taskmaster.*;
import java.sql.SQLException;

import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
//import java.util.*;
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
public class CRSTATABR03 extends PokBaseABR {
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.";
	
  /**
   *  Execute ABR.
   *
   */
  public final static String ABR = "CRSTATABR03";

  private EntityGroup m_egParent = null;
  private EntityItem m_ei = null;

  /**
     * execute_run
     *
     * @author Owner
     */
    public void execute_run() {
    //String strStatus = null;
    //String strName = null;
    String strAnnNumber = null;
    String strAnnDate = null;
    String strAnnType = null;
    String strAnnTitle = null;
    String strANNDesc = null;
    EntityGroup annGroup = null;

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
      println("<table summary=\"Announcement\" width=\"100%\">" + NEW_LINE + "<tr><th id=\"number\" class=\"PsgLabel\">Announcement Number</th><th id=\"date\" class=\"PsgLabel\">Announcement Date</th><th id=\"type\" class=\"PsgLabel\">Announcement Type</th><th id=\"title\" class=\"PsgLabel\">Announcement Title</th><th id=\"description\" class=\"PsgLabel\">Announcement Description</th></tr>");
      //EntityGroup annGroup = m_elist.getParentEntityGroup();
      annGroup = m_elist.getEntityGroup("ANNOUNCEMENT");
      logMessage(
        "************Root Entity Type and id "
          + getEntityType()
          + ":"
          + getEntityID());

      if (annGroup == null) {
        logMessage("****************Announcement Not found ");
        setReturnCode(FAIL);

      }

      for (int iALL = 0; iALL < annGroup.getEntityItemCount(); iALL++) {
        EntityItem entity = annGroup.getEntityItem(iALL);

        logMessage(
          "************Entity Type returned is "
            + entity.getEntityType());
        //Print the attributes for Announcement entity
        if (entity.getEntityType().equals("ANNOUNCEMENT")) {
          logMessage("******************Entitytype is Announcement");
          // (for debugging) printAttributes(entity.getEntityType(), entity.getEntityID(), false,false);

          /*strStatus =
            getAttributeValue(
              entity.getEntityType(),
              entity.getEntityID(),
              "ANCYCLESTATUS",
              "<em>** Not Populated ** </em>");*/

         /* strName =
            getAttributeValue(
              entity.getEntityType(),
              entity.getEntityID(),
              "NAME",
              "<em>** Not Populated ** </em>");*/

          strAnnNumber =
            getAttributeValue(
              entity.getEntityType(),
              entity.getEntityID(),
              "ANNNUMBER",
              "<em>** Not Populated ** </em>");
          strAnnDate =
            getAttributeValue(
              entity.getEntityType(),
              entity.getEntityID(),
              "ANNDATE",
              "<em>** Not Populated ** </em>");
          strAnnType =
            getAttributeValue(
              entity.getEntityType(),
              entity.getEntityID(),
              "ANNTYPE",
              "<em>** Not Populated ** </em>");
          strAnnTitle =
            getAttributeValue(
              entity.getEntityType(),
              entity.getEntityID(),
              "ANNTITLE",
              "<em>** Not Populated ** </em>");
          strANNDesc =
            getAttributeValue(
              entity.getEntityType(),
              entity.getEntityID(),
              "ANNDESC",
              "<em>** Not Populated ** </em>");

          // Perform Checks

          logMessage("******************After getting all the Announcement Attributes");
          println(
            "<tr><td headers=\"number\" class=\"PsgText\">"
              + strAnnNumber
              + "</td><td headers=\"date\" class=\"PsgText\">"
              + strAnnDate
              + "</td><td headers=\"type\" class=\"PsgText\">"
              + strAnnType
              + "</td><td headers=\"title\" class=\"PsgText\">"
              + strAnnTitle
              + "</td><td headers=\"description\" class=\"PsgText\">"
              + strANNDesc
              + "</td></tr>");
          println("</table>" + NEW_LINE + "<br />");

          //strStatus = null;
          //strName = null;
          strAnnNumber = null;
          strAnnDate = null;
          strAnnType = null;
          strAnnTitle = null;
          strANNDesc = null;
        } //end of if clause
      } //end of for loop

      //println("<br /><br /><table width=\"100%\">\n<tr><td class=\"PsgLabel\">Attribute Description</td><td class=\"PsgLabel\">Attribute Value</td></tr>");
      //EntityGroup egParent = m_elist.getParentEntityGroup();

      if (m_egParent == null) {
        logMessage("**************** CHANGEREQUEST Not found ");
        setReturnCode(FAIL);

      } else {
        String strCRSTATUS =
          getAttributeValue(
            m_ei.getEntityType(),
            m_ei.getEntityID(),
            "CRSTATUS",
            "<em>** Not Populated ** </em>");
        if (strCRSTATUS.equals("Requires PDT Review")) {
          println("<br /><b>This Change Request was Rejected</b>");

        } else if (
          strCRSTATUS.equals("Approved")
                    || strCRSTATUS.equals("Ready for Review")) {
          println("<br /><b>This Change Request was Cancelled</b>");
          ;
        }

        println(
          "<br /><br /><b>" + m_egParent.getLongDescription() + "</b>");
        //printNavigateAttributes(m_ei, m_egParent, false);
		printNavAttributes(m_ei, m_egParent, false, 0);

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
      setDGRptName("CRSTATABR03"); //Set the report name
      setDGRptClass("CRSTATABR03"); //Set the report class
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
     * getStyle
     *
     * @return
     * @author Owner
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
    return "1.15";
  }

  /**
     * getVersion
     *
     * @return
     * @author Owner
     */
    public static String getVersion() {
    return ("CRSTATABR03.java,v 1.15 2008/01/30 20:02:00 wendy Exp");
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
	  println(EACustom.getMetaTags("CRSTATABR03.java"));
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
