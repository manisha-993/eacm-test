//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2003, 2006  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
//FEATUREABR01.java,v
//Revision 1.5  2006/03/13 20:26:30  couto
//Changed layout, using EACustom methods. Changes for Jtest.
//Fixed br tags. Chaged font tags. Fixed table header.
//buildReportHeader and displayAttributes are now local methods.
//
//Revision 1.4  2006/03/03 19:23:28  bala
//remove reference to Constants.CSS
//
//Revision 1.3  2006/01/24 16:51:10  yang
//Jtest Changes
//
//Revision 1.2  2003/11/07 01:21:37  yang
//Adding setDGRptClass
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
//import java.sql.*;

/**
 *  Description of the Class
 *  This ABR sets FEATSTATUS to ‘Ready for Review’ or 'Final'.
 *  Return Code
 *  Pass
 *
 *@author     Yang
 *@created    Oct 13,2003
 */
public class FEATUREABR01 extends PokBaseABR {
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2003, 2006  All Rights Reserved.";
	
  /**
   *  Execute ABR.
   */

  //ABR
  public final static String ABR = "FEATUREABR01";

  //ANNOUNCEMENT Entity
  /**
     * SOF
     *
     */
    public final static String SOF = "SOF"; //Announcement

  //ANNOUNCEMENT Entity -- Attributes
  /**
     * FEATSTATUS
     *
     */
    public final static String FEATSTATUS = "FEATSTATUS";
  //Offering Life Cycle Status
  /**
     * OFDISTATUS
     *
     */
    public final static String OFDISTATUS = "OFDISTATUS";
  //Offering DI Status
  /**
     * OFWWSDSTATUS
     *
     */
    public final static String OFWWSDSTATUS = "OFWWSDSTATUS";
  //Offering WWSD Status
  /**
     * OFMKTGSTATUS
     *
     */
    public final static String OFMKTGSTATUS = "OFMKTGSTATUS";
  //Offering MKTG Status
  /**
     * ABRSET
     *
     */
    public final static String ABRSET = "ABRSET";
  //Attribute to check if setting to RR or Final

  // Class constants
  /**
     * DEF_NOT_POPULATED_HTML
     *
     */
    public final static String DEF_NOT_POPULATED_HTML =
    "** Not Populated **";
  private final String m_strReadyForReviewValue = "114";
  //FEATSTATUS  Ststus Attribute Ready for Final Review
  private final String m_strFinalValue = "112";
  //FEATSTATUS  Ststus Attribute Ready for Final Review

  private EntityGroup m_egParent = null; //FEATURE

  private EntityItem m_eiParent = null; //FEATURE

  private ControlBlock m_cbOn = null;
  private String m_strNow = null;
  private String m_strForever = null;

  /**
     * execute_run
     *
     * @author Owner
     */
    public void execute_run() {
    EANAttribute attabrset;
    EANAttribute att;
    EANAttribute att1;
    EANAttribute att2;
    EANAttribute att4;
    String abrsetStatusvalue = null;
    String abrsetStatus = null;
    String strStatusvalue = null;
    String strStatusvalue1 = null;
    String strStatusvalue2 = null;
    String strStatusvalue4 = null;
    String strStatus = null;
    String strStatus1 = null;
    String strStatus2 = null;
    String strStatus4 = null;

    try {
      start_ABRBuild();

      // Build the report header
      //buildReportHeader();
	  buildRptHeader();

      setControlBlock();
      setReturnCode(PASS);

      //Get the root SOF
      m_egParent = m_elist.getParentEntityGroup();
      m_eiParent = m_egParent.getEntityItem(0);

      setDGTitle(setDGName(m_eiParent, ABR));

      logMessage(
        "************ Root Entity Type and id "
          + getEntityType()
          + ":"
          + getEntityID());

      if (m_egParent == null) {
        logMessage("**********Service Offering Not found");
        setReturnCode(FAIL);

      } else {

        EntityGroup eg = m_eiParent.getEntityGroup();
        logMessage("***SOF true***" + eg.dump(true));

        attabrset = m_eiParent.getAttribute(ABRSET);
        abrsetStatusvalue =
          (attabrset != null
            ? attabrset.toString()
            : DEF_NOT_POPULATED_HTML);
        logMessage(
          "***abrsetStatusvalue***: "
            + m_eiParent.getEntityType()
            + ":"
            + m_eiParent.getEntityID()
            + ":"
            + abrsetStatusvalue);

        abrsetStatus = getFlagCode(m_eiParent, ABRSET);
        logMessage(
          "****abrsetStatus:"
            + m_eiParent.getEntityType()
            + "-"
            + m_eiParent.getEntityID()
            + ":"
            + abrsetStatus);

        att = m_eiParent.getAttribute(FEATSTATUS);
        strStatusvalue =
          (att != null ? att.toString() : DEF_NOT_POPULATED_HTML);
        logMessage(
          "***strStatusvalue***: "
            + m_eiParent.getEntityType()
            + ":"
            + m_eiParent.getEntityID()
            + ":"
            + strStatusvalue);

        att1 = m_eiParent.getAttribute(OFDISTATUS);
        strStatusvalue1 =
          (att1 != null ? att1.toString() : DEF_NOT_POPULATED_HTML);
        logMessage(
          "***strStatusvalue1***: "
            + m_eiParent.getEntityType()
            + ":"
            + m_eiParent.getEntityID()
            + ":"
            + strStatusvalue1);

        att2 = m_eiParent.getAttribute(OFWWSDSTATUS);
        strStatusvalue2 =
          (att2 != null ? att2.toString() : DEF_NOT_POPULATED_HTML);
        logMessage(
          "***strStatusvalue2***: "
            + m_eiParent.getEntityType()
            + ":"
            + m_eiParent.getEntityID()
            + ":"
            + strStatusvalue2);

        att4 = m_eiParent.getAttribute(OFMKTGSTATUS);
        strStatusvalue4 =
          (att4 != null ? att4.toString() : DEF_NOT_POPULATED_HTML);
        logMessage(
          "***strStatusvalue4***: "
            + m_eiParent.getEntityType()
            + ":"
            + m_eiParent.getEntityID()
            + ":"
            + strStatusvalue4);

        strStatus = getFlagCode(m_eiParent, FEATSTATUS);
        logMessage(
          "****strStatus:"
            + m_eiParent.getEntityType()
            + "-"
            + m_eiParent.getEntityID()
            + ":"
            + strStatus);
        strStatus1 = getFlagCode(m_eiParent, OFDISTATUS);
        logMessage(
          "****strStatus1:"
            + m_eiParent.getEntityType()
            + "-"
            + m_eiParent.getEntityID()
            + ":"
            + strStatus1);
        strStatus2 = getFlagCode(m_eiParent, OFWWSDSTATUS);
        logMessage(
          "****strStatus2:"
            + m_eiParent.getEntityType()
            + "-"
            + m_eiParent.getEntityID()
            + ":"
            + strStatus2);
        strStatus4 = getFlagCode(m_eiParent, OFMKTGSTATUS);
        logMessage(
          "****strStatus4:"
            + m_eiParent.getEntityType()
            + "-"
            + m_eiParent.getEntityID()
            + ":"
            + strStatus4);

        //check to move FEATSTATUS to Final
        if (abrsetStatus.equals("0020")) {
          logMessage(
            "We are gettin here abrset 0020:"
              + attabrset
              + ": "
              + abrsetStatus);
          if ((strStatus.equals("111")
                            || strStatus.equals("114")
                            || strStatus.equals("116"))
            && strStatus1.equals("112")
            && strStatus2.equals("112")
            && strStatus4.equals("112")) {
            println(
              "<br /><br /><b>"
                + "Entity: "
                + m_eiParent.getEntityType()
                + "   EntityId: "
                + m_eiParent.getEntityID()
                + "</b>");
            //println(displayAttributes(m_eiParent, eg, true));
            println(dispNavAttributes(m_eiParent, eg));
            println(
              "<br /><br /><b>Offering Life Cycle Status"
                + ": "
                + strStatusvalue
                + "</b>"
                + strStatus);
            println(
              "<br /><br /><b>Offering DI Status"
                + ": "
                + strStatusvalue1
                + "</b>"
                + strStatus1);
            println(
              "<br /><br /><b>Offering WWSD Status"
                + ": "
                + strStatusvalue2
                + "</b>"
                + strStatus2);
            println(
              "<br /><br /><b>Offering MKTG Status"
                + ": "
                + strStatusvalue4
                + "</b>"
                + strStatus4);
            setFinalValue(FEATSTATUS);
            setReturnCode(PASS);

          } else {
            println(
              "<br /><br /><b>"
                + "Entity: "
                + m_eiParent.getEntityType()
                + "   EntityId: "
                + m_eiParent.getEntityID()
                + "</b>");
            //println(displayAttributes(m_eiParent, eg, true));
			println(dispNavAttributes(m_eiParent, eg));
            println("<br /><br /><b>OFroleSTATUS is not set to the correct value.</b>");
            println(
              "<br /><br /><b>Offering Life Cycle Status"
                + ": "
                + strStatusvalue
                + "</b>");
            println(
              "<br /><br /><b>Offering DI Status"
                + ": "
                + strStatusvalue1
                + "</b>");
            println(
              "<br /><br /><b>Offering WWSD Status"
                + ": "
                + strStatusvalue2
                + "</b>");
            println(
              "<br /><br /><b>Offering MKTG Status"
                + ": "
                + strStatusvalue4
                + "</b>");
            setReturnCode(FAIL);
          }

          //check to move FEATSTATUS to Ready for Review
        } else if (abrsetStatus.equals("0010")) {
          logMessage(
            "We are gettin here abrset 0010:"
              + attabrset
              + ": "
              + abrsetStatus);
          if ((strStatus.equals("111") || strStatus.equals("115"))
            && (strStatus1.equals("114") || strStatus1.equals("112"))
            && (strStatus2.equals("114") || strStatus2.equals("112"))
            && (strStatus4.equals("114")
              || strStatus4.equals("112"))) {
            println(
              "<br /><br /><b>"
                + "Entity: "
                + m_eiParent.getEntityType()
                + "   EntityId: "
                + m_eiParent.getEntityID()
                + "</b>");
            //println(displayAttributes(m_eiParent, eg, true));
			println(dispNavAttributes(m_eiParent, eg));
            println(
              "<br /><br /><b>Offering Life Cycle Status"
                + ": "
                + strStatusvalue
                + "</b>"
                + strStatus);
            println(
              "<br /><br /><b>Offering DI Status"
                + ": "
                + strStatusvalue1
                + "</b>"
                + strStatus1);
            println(
              "<br /><br /><b>Offering WWSD Status"
                + ": "
                + strStatusvalue2
                + "</b>"
                + strStatus2);
            println(
              "<br /><br /><b>Offering MKTG Status"
                + ": "
                + strStatusvalue4
                + "</b>"
                + strStatus4);
            setReadyForReviewValue(FEATSTATUS);
            setReturnCode(PASS);

          } else {
            println(
              "<br /><br /><b>"
                + "Entity: "
                + m_eiParent.getEntityType()
                + "   EntityId: "
                + m_eiParent.getEntityID()
                + "</b>");
            //println(displayAttributes(m_eiParent, eg, true));
			println(dispNavAttributes(m_eiParent, eg));
            println("<br /><br /><b>OFroleSTATUS is not set to the correct value.</b>");
            println(
              "<br /><br /><b>Offering Life Cycle Status"
                + ": "
                + strStatusvalue
                + "</b>");
            println(
              "<br /><br /><b>Offering DI Status"
                + ": "
                + strStatusvalue1
                + "</b>");
            println(
              "<br /><br /><b>Offering WWSD Status"
                + ": "
                + strStatusvalue2
                + "</b>");
            println(
              "<br /><br /><b>Offering MKTG Status"
                + ": "
                + strStatusvalue4
                + "</b>");
            setReturnCode(FAIL);
          }
        }
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
      // set DG submit string
      setDGString(getABRReturnCode());
      setDGRptName("FEATUREABR01"); //Set the report name
      setDGRptClass("FEATUREABR01"); //Set the report class
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
   *  Get the getRevision
   *
   *@return    java.lang.String
   */
  public String getRevision() {
    return "1.5";
  }

  /**
     * setControlBlock
     *
     * @author Owner
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
   *  Sets the flagValue attribute of the FEATUREABR01 object
   *
   *@param  _sAttributeCode  The new flagValue value
   */
  public void setReadyForReviewValue(
    String _sAttributeCode) {

    String strAttributeValue = null;

    if (_sAttributeCode.equals(FEATSTATUS)) {
      logMessage(
        "****** FEATURE Status set to: " + m_strReadyForReviewValue);
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
     * setFinalValue
     *
     * @param _sAttributeCode
     * @author Owner
     */
    public void setFinalValue(
    String _sAttributeCode) {

    String strAttributeValue = null;

    if (_sAttributeCode.equals(FEATSTATUS)) {
      logMessage("****** FEATURE Status set to: " + m_strFinalValue);
      strAttributeValue = m_strFinalValue;

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
     * getVersion
     *
     * @return
     * @author Owner
     */
    public static String getVersion() {
    return ("FEATUREABR01.java,v 1.5 2006/03/13 20:26:30 couto Exp");
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
	  println(EACustom.getMetaTags("FEATUREABR01.java"));
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
	
  private String dispNavAttributes(EntityItem _ei, EntityGroup _eg){
	 StringBuffer strBuffer = new StringBuffer();
	 strBuffer.append("<br /><br /><table summary=\"Attributes\" width=\"100%\">" + NEW_LINE);
	 strBuffer.append("<tr><th id=\"navAttr\" class=\"PsgLabel\" width=\"35%\">Navigation Attribute</th><th id=\"value\" class=\"PsgLabel\" width=\"65%\">Value</th></tr>");
	 for(int i=0;i < _eg.getMetaAttributeCount();i++){
		EANMetaAttribute ma = _eg.getMetaAttribute(i);
		EANAttribute att = _ei.getAttribute(ma.getAttributeCode());
		if(ma.isNavigate()) {
		   strBuffer.append("<tr><td headers=\"navAttr\" class=\"PsgText\" width=\"35%\">"+ ma.getLongDescription()+"</td><td headers=\"value\" class=\"PsgText\" width=\"65%\">" +(att == null || att.toString().length() ==0 ? DEF_NOT_POPULATED_HTML : att.toString()) + "</td></tr>");
		}
	 }
   strBuffer.append("</table>");
	 return strBuffer.toString();
 }

}
