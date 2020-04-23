//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
//CRSTATABR05.java,v
//Revision 1.13  2008/01/30 20:02:00  wendy
//Cleanup RSA warnings
//
//Revision 1.12  2006/03/13 19:42:03  couto
//Fixed copyright info.
//
//Revision 1.11  2006/03/13 13:34:24  couto
//Changed layout, using EACustom methods. Changes for Jtest.
//Fixed br tags. Chaged font tags. Fixed table header.
//buildReportHeader and printNavigateAttributes are now local methods.
//
//Revision 1.10  2006/03/03 19:23:28  bala
//remove reference to Constants.CSS
//
//Revision 1.9  2006/01/24 17:08:38  yang
//Jtest Changes
//
//Revision 1.8  2003/11/07 01:21:25  yang
//Adding setDGRptClass
//
//Revision 1.7  2003/09/18 23:26:49  yang
//more log message
//
//Revision 1.6  2003/09/18 23:20:35  yang
//added setFlagValue
//
//Revision 1.5  2003/09/18 19:51:15  yang
//adding bala's stuff to finally {
//
//Revision 1.4  2003/06/04 03:53:09  dave
//un Staticing getABRVersion
//
//Revision 1.3  2003/06/04 03:44:26  dave
//minor syntax
//
//Revision 1.2  2003/06/04 03:41:44  dave
//adding getABRVersion
//
//Revision 1.1.1.1  2003/06/03 19:02:24  dave
//new 1.1.1 abr
//
//Revision 1.10  2002/12/12 21:52:42  minhthy
//printNavigateAttributes()
//
//Revision 1.9  2002/11/06 22:08:02  naomi
//remove setReturnCode(PASS) in the finally clause
//
//Revision 1.8  2002/10/31 18:35:43  naomi
//added setDGName
//
//Revision 1.7  2002/10/22 20:00:36  naomi
//add entityID for setDGTitle
//
//Revision 1.6  2002/10/15 21:56:46  minhthy
//modified changeRequest's formatted
//
//Revision 1.5  2002/10/14 22:05:06  minhthy
//added ANNDESC
//
//Revision 1.4  2002/10/11 23:09:36  minhthy
//added changeRequest's Details
//
//Revision 1.3  2002/09/19 22:17:44  naomi
//Added buildReportHeader(),setDGTitle() and replaced getEntityGroup() with getParentEntityGroup()
//
//Revision 1.2  2002/09/17 20:44:09  naomi
//added the Class Description
//
//Revision 1.1  2002/09/11 22:04:20  bala
//check in
//
package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.*;
//import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;

import java.sql.SQLException;
import java.util.*;
//import java.io.*;

/**
 *  Description of the Class
 *  This ABR handles the case where CRSTATUS is set to Complete by the ODM
 *  and produces a report for the Announcement entity.
 *  Return Code
 *  Pass
 *
 *@author Bala
 *@created    September 11, 2002
 */
public class CRSTATABR05 extends PokBaseABR {
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.";
	
  /**
   *  Execute ABR.
   *
   */
  public final static String ABR = "CRSTATABR05";
  /**
     * CHANGEREQUEST
     *
     */
    public final static String CHANGEREQUEST = "CHANGEREQUEST";
  /**
     * CRSTATUS
     *
     */
    public final static String CRSTATUS = "CRSTATUS";

  private EntityGroup m_egParent = null;
  private EntityItem m_ei = null;

  private final String m_strRelProMgmtValue = "116";
  //XLSTATUS10  Ststus Attribute (Awaiting Translation)

  /**
     * execute_run
     *
     * @author Owner
     */
    public void execute_run() {
    try {
      //String strStatus = null;
      String strANNDesc = null;
      EntityItem entity = null;
      EntityGroup annGroup = null;
      EntityGroup egParent = null;

      setReturnCode(PASS);

      start_ABRBuild();

      // Build the report header
      //buildReportHeader();
      buildRptHeader();
      setControlBlock();

      m_egParent = m_elist.getParentEntityGroup();
      m_ei = m_egParent.getEntityItem(0);

      setDGTitle(setDGName(m_ei, ABR));

      annGroup = m_elist.getEntityGroup("ANNOUNCEMENT");

      if (annGroup == null) {
        logMessage("****************Announcement Not found ");
        setReturnCode(FAIL);

      }

      for (int iALL = 0; iALL < annGroup.getEntityItemCount(); iALL++) {
        entity = annGroup.getEntityItem(iALL);

        println("<br /><b>" + annGroup.getLongDescription() + "</b>");
        //printNavigateAttributes(entity, annGroup, true);
		printNavAttributes(entity, annGroup, true, iALL);

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

        println("<br /><b>The following Change Request is now complete.</b>");

        //strStatus = null;
        strANNDesc = null;

      }

      egParent = m_elist.getParentEntityGroup();
      logMessage(
        "************2 Root Entity Type and id "
          + getEntityType()
          + ":"
          + getEntityID());
      if (egParent == null) {
        logMessage("**************** CHANGEREQUEST Not found ");
        setReturnCode(FAIL);

      } else {
        println("<br /><br /><table summary=\"Chagen Request\" width=\"100%\">");        println("<tr><th id=\"attrDesc\" class=\"PsgLabel\">Attribute Description</th><th id=\"attrValue\" class=\"PsgLabel\">Attribute Value</th></tr>");
        entity = egParent.getEntityItem(0);
        for (int i = 0; i < egParent.getMetaAttributeCount(); i++) {
          EANMetaAttribute ma = egParent.getMetaAttribute(i);
          EANAttribute att =
            entity.getAttribute(ma.getAttributeCode());
          if (ma.isNavigate()) {
            String attrCode = ma.getAttributeCode();
            String COMMENT =
              attrCode.substring(
                attrCode.length() - 7,
                attrCode.length());
            String REVIEW =
              attrCode.substring(
                attrCode.length() - 6,
                attrCode.length());

            if (!COMMENT.equals("COMMENT")
              && !REVIEW.equals("REVIEW")) {
              println(
                "<tr><td headers=\"attrDesc\" class=\"PsgText\">"
                  + ma.getLongDescription()
                  + "</td><td headers=\"attrValue\" class=\"PsgText\">"
                  + (att == null
                    ? "** Not Populated **"
                    : att.toString())
                  + "</td></tr>");

            }
          }

        }
        println("</table>");

      }

      if (getReturnCode() == PASS) {
        setFlagValue(CRSTATUS);
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
      setDGRptName("CRSTATABR05"); //Set the report name
      setDGRptClass("CRSTATABR05"); //Set the report class
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
   *  Sets the controlBlock attribute of the XLATEABR01 object
   */
  public void setControlBlock() {
    m_strNow = getNow();
    m_strForever = getForever();
    m_cbOn =
      new ControlBlock(
        m_strNow,
        m_strForever,
        m_strNow,
        m_strForever,
        m_prof.getOPWGID(),
        m_prof.getTranID());
  }

  /**
   *  Sets the flagValue attribute of the XLATEABR01 object
   *
   *@param  _sAttributeCode  The new flagValue value
   */
  public void setFlagValue(
    String _sAttributeCode) {

    String strAttributeValue = null;

    if (_sAttributeCode.equals(CRSTATUS)) {
      //println("****** CRSTATUS: " + _sAttributeCode);
      logMessage("****** CRSTATUS set to: " + m_strRelProMgmtValue);
      strAttributeValue = m_strRelProMgmtValue;

    } else {

      strAttributeValue = null;
    }
    //println("****** strAttributeValue set to: " + strAttributeValue);
    //logMessage("****** strAttributeValue set to: " + strAttributeValue);

    if (strAttributeValue != null) {
      try {
        //println("****** m_prof: " + m_prof);
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

        //println("****** m_cbOn: " + m_cbOn);
        SingleFlag sf =
          new SingleFlag(
            m_prof.getEnterprise(),
            rek.getEntityType(),
            rek.getEntityID(),
            _sAttributeCode,
            strAttributeValue,
            1,
            m_cbOn);
        Vector vctAtts = new Vector();
        Vector vctReturnsEntityKeys = new Vector();

        if (sf != null) {
          vctAtts.addElement(sf);
          rek.m_vctAttributes = vctAtts;
          vctReturnsEntityKeys.addElement(rek);
          m_db.update(m_prof, vctReturnsEntityKeys, false, false);
          m_db.commit();
        }
      } catch (COM.ibm.opicmpdh.middleware.MiddlewareException e) {
        logMessage("setFlagValue: " + e.getMessage());
      } catch (Exception e) {
        logMessage("setFlagValue: " + e.getMessage());
      }
    }
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
    return "1.13";
  }

  /**
     * getVersion
     *
     * @return
     * @author Owner
     */
    public static String getVersion() {
    return ("CRSTATABR05.java,v 1.13 2008/01/30 20:02:00 wendy Exp");
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
	  println(EACustom.getMetaTags("CRSTATABR05.java"));
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
