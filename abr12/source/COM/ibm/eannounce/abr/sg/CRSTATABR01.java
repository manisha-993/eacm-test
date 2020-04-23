//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
//CRSTATABR01.java,v
//Revision 1.16  2008/01/30 20:02:00  wendy
//Cleanup RSA warnings
//
//Revision 1.15  2006/03/13 19:42:03  couto
//Fixed copyright info.
//
//Revision 1.14  2006/03/13 13:28:58  couto
//Changed layout, using EACustom methods. Changes for Jtest.
//Fixed br tags. Chaged font tags.
//buildReportHeader and printNavigateAttributes are now local methods.
//
//Revision 1.13  2006/03/03 19:23:27  bala
//remove reference to Constants.CSS
//
//Revision 1.12  2006/01/24 17:03:18  yang
//Jtest Changes
//
//Revision 1.11  2003/11/07 01:21:00  yang
//Adding setDGRptClass
//
//Revision 1.10  2003/10/28 18:57:54  yang
//syntax
//
//Revision 1.9  2003/10/28 18:20:04  yang
//add setFlagValue method
//
//Revision 1.8  2003/09/18 20:39:35  yang
//add getFlagCode instead of getting long description
//
//Revision 1.7  2003/09/18 19:51:32  yang
//adding bala's stuff to finally {
//
//Revision 1.6  2003/09/18 17:22:11  yang
//adding log
//
//Revision 1.5  2003/09/18 00:16:44  yang
//syntax change
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
//Revision 1.17  2002/12/12 21:51:39  minhthy
//printNavigateAttributes
//
//Revision 1.16  2002/12/12 01:13:43  minhthy
//rerun Extract action after DB Updated
//
//Revision 1.15  2002/11/06 22:06:22  naomi
//remove setReturnCode(PASS) in the finally clause
//
//Revision 1.14  2002/10/31 18:35:11  naomi
//added setDGName
//
//Revision 1.13  2002/10/22 20:00:08  naomi
//add entityID for setDGTitle
//
//Revision 1.12  2002/10/22 18:11:47  naomi
//add entityID for setDGTitle
//
//Revision 1.11  2002/10/17 18:11:30  minhthy
//displayed attribute value for navigation attribute
//
//Revision 1.10  2002/10/17 00:35:29  minhthy
//changed 'ANCYCLESTATUS' description
//
//Revision 1.9  2002/10/15 21:56:17  minhthy
//modified changeRequest's formatted
//
//Revision 1.8  2002/10/14 22:03:51  minhthy
//added ANNDESC
//
//Revision 1.7  2002/10/11 23:00:35  minhthy
//added "Change Request's Details"
//
//Revision 1.6  2002/10/10 22:32:32  minhthy
//*** empty log message ***
//
//Revision 1.5  2002/09/19 21:13:10  naomi
//Added buildReportHeader(),setDGTitle() and replaced getEntityGroup() with getParentEntityGroup()
//
//Revision 1.4  2002/09/17 20:35:00  naomi
//Added the Class Description
//
//Revision 1.3  2002/09/11 21:15:17  bala
//remove getTable function as its not used anymore...also getAllChildren
//
//Revision 1.2  2002/09/06 16:45:45  bala
//removing DG update code...will be done by taskmaster from now on
//
//Revision 1.1  2002/09/04 22:10:58  bala
//check in
//

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
 *  This ABR checks to see if the Announcement entity is not at least in status Approved.
 *  If it is not in status Approved, then the Change Request will be cancelled and the ABR will produce a report for the Announcement entity.
 *  The ABR will set CRSTATUS to "Cancelled" based on the attribute ANCYCLESTATUS in the parent ANNOUNCEMENT.
 *  Return Code
 *  Pass
 *
 *@author
 *@created
 */
public class CRSTATABR01 extends PokBaseABR {
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.";
	
  /**
   *  Execute ABR.
   *
   */
  public final static String ABR = "CRSTATABR01";

  private EntityGroup m_egParent = null;
  private EntityItem m_ei = null;

  private final String m_strReadyForReviewValue = "112";
  //ANCYCLESTATUS  Ststus Attribute Approved with Risk

  /**
     * execute_run
     *
     * @author Owner
     */
    public void execute_run() {
    //String strStatus = null;
    String strANNDesc = null;
    String strEntityType = null;
    String strCR = null;
    String strStatus1 = null;
    EntityGroup annGroup = null;
    EntityGroup egParent = null;
    EntityItem entity= null;
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

        strEntityType = entity.getEntityType();
        strStatus1 = getFlagCode(entity, "ANCYCLESTATUS");
        logMessage("*************** strStatus1: " + strStatus1);

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

        logMessage("******************AFter checking Status of announcement");

        /*
         *  Now Update the CHANGEREQUEST column in CHANGEREQUEST Entity
         */
        /*  if (strCR !=null)  {
            try {
            EntityGroup rstAnnReview = m_elist.getEntityGroup("CHANGEREQUEST");
            RowSelectableTable rstAnnouncement = rstAnnReview.getEntityGroupTable();

            int iTextCol = rstAnnouncement.getColumnIndex("CHANGEREQUEST:CHANGEREQUEST");
            logMessage("**********************Text column returned " + iTextCol);
            rstAnnouncement.put(0, iTextCol, strCR);
            rstAnnouncement.commit(getDatabase());
            setReturnCode(PASS);
            } catch (EANBusinessRuleException ebx) {
            D.ebug(D.EBUG_ERR, " trouble getting ABR update for ANNReview " + ebx.getMessage());
            }
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
          logMessage("After update of CHANGEREQUEST COLUMN");
          println("<br /><b>This Change Request was cancelled because the Announcement is not eligible for Change Requests.</b>");

        } else {
          println("<br /><b>Please review and process this Change Request.</b>");
        }

        //strStatus = null;
        strCR = null;
        strANNDesc = null;

      } //end of for loop

      //reRun Extract Action after DB updated

      m_prof.setValOn(getValOn());
      m_prof.setEffOn(getEffOn());
      start_ABRBuild();

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
        println("<br /><br /><table summary=\"Change Request\" width=\"100%\">");
		println("<tr><th class=\"PsgLabel\" id=\"attributeDesc\">Attribute Description</th>");
		println("<th class=\"PsgLabel\" id=\"attrValue\">Attribute Value</th></tr>");
		        
        entity = egParent.getEntityItem(0);
        for (int i = 0; i < egParent.getMetaAttributeCount(); i++) {
          EANMetaAttribute ma = egParent.getMetaAttribute(i);
          EANAttribute att =
            entity.getAttribute(ma.getAttributeCode());

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
              "<tr><td headers=\"attributeDesc\" class=\"PsgText\">"
                + ma.getLongDescription()
                + "</td><td headers=\"attrValue\" class=\"PsgText\">"
                + (att == null
                  ? "** Not Populated **"
                  : att.toString())
                + "</td></tr>");

          }

        }
        println("</table>");

      }

      /*//need to change the value for CRSTATUS when ABR passes
      if( getReturnCode() == PASS ){

        String strFlagCode = getFlagCode(m_ei, getStatusAttributeCode(m_ei));
                if (strFlagCode.equals("111")) {
          setFlagValue("CHANGEREQUEST", getEntityID(), "CRSTATUS");
                }
      }*/

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
      setDGRptName("CRSTATABR01"); //Set the report name
      setDGRptClass("CRSTATABR01"); //Set the report class
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
   *  Sets the controlBlock attribute of the B. CRSTATABR01 object
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
   *  Sets the flagValue attribute of the B.  CRSTATABR01 object
   *
   *@param  _sAttributeCode  The new flagValue value
   */
  public void setFlagValue(
    String _sAttributeCode) {

    String strAttributeValue = null;

    if (_sAttributeCode.equals("CRSTATUS")) {
      logMessage(
        "****** Change Request Status set to: "
          + m_strReadyForReviewValue);
      strAttributeValue = m_strReadyForReviewValue;

    } else {

      strAttributeValue = null;
    }

    if (strAttributeValue != null) {
      try {
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
   *  Get the getRevision
   *
   *@return    java.lang.String
   */
  public String getRevision() {
    return "1.16";
  }

  /**
     * getVersion
     *
     * @return
     * @author Owner
     */
    public static String getVersion() {
    return ("CRSTATABR01.java,v 1.16 2008/01/30 20:02:00 wendy Exp");
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
	  println(EACustom.getMetaTags("CRSTATABR01.java"));
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
