//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
//ANNPROJREVABR01.java,v
//Revision 1.14  2008/01/30 19:39:14  wendy
//Cleanup RSA warnings
//
//Revision 1.13  2006/03/15 13:29:51  couto
//Changed layout, using EACustom methods. Changes for Jtest.
//Fixed br tags. Chaged font tags. Fixed table header.
//buildReportHeader is now a local method.
//
//Revision 1.12  2006/03/03 19:23:26  bala
//remove reference to Constants.CSS
//
//Revision 1.11  2006/01/24 16:42:16  yang
//Jtest Changes
//
//Revision 1.10  2005/02/08 18:29:14  joan
//changes for Jtest
//
//Revision 1.9  2003/11/07 01:19:53  yang
//Adding setDGRptClass
//
//Revision 1.8  2003/09/18 19:50:18  yang
//adding bala's stuff to finally {
//
//Revision 1.7  2003/09/17 19:18:39  yang
//more log message
//
//Revision 1.6  2003/09/17 18:34:38  yang
//adding logMessage
//
//Revision 1.5  2003/09/17 17:44:16  yang
//adding printStackTrace()
//
//Revision 1.4  2003/06/04 03:53:07  dave
//un Staticing getABRVersion
//
//Revision 1.3  2003/06/04 03:44:24  dave
//minor syntax
//
//Revision 1.2  2003/06/04 03:41:42  dave
//adding getABRVersion
//
//Revision 1.1.1.1  2003/06/03 19:02:24  dave
//new 1.1.1 abr
//
//Revision 1.20  2002/12/11 01:22:48  minhthy
//fixed error
//
//Revision 1.19  2002/12/05 19:28:25  minhthy
//*** empty log message ***
//
//Revision 1.18  2002/12/05 01:17:16  minhthy
//Copy ANNPROJNAME from ANNPROJ to ANNPROJREVNAME in ANNPROJREVIEW
//
//
//Revision 1.16  2002/11/07 18:30:35  minhthy
//copy 'ANNPROJREVNAME' from ANNPROJ to ANNPROJREVIEW
//
//Revision 1.15  2002/11/06 01:05:54  minhthy
//added funtion setText()
//
//Revision 1.14  2002/10/31 18:34:38  naomi
//added setDGName
//
//Revision 1.13  2002/10/25 16:24:25  minhthy
//fixed errors
//
//Revision 1.10  2002/10/22 18:08:39  naomi
//add entityID for setDGTitle
//
//Revision 1.9  2002/10/11 23:48:47  naomi
//modify the way to get attributes
//
//Revision 1.8  2002/09/30 23:29:09  naomi
//Added html tags to print Announcement Project
//
//Revision 1.7  2002/09/19 19:29:59  naomi
//added setDGTitle(), replaced with getParentEntityGroup()
//
//Revision 1.6  2002/09/16 21:01:09  naomi
//modified the Class description
//
//Revision 1.5  2002/09/16 20:53:43  naomi
//modified the Class description
//
//Revision 1.4  2002/09/16 19:58:19  naomi
//added the Class description
//
//Revision 1.3  2002/09/11 21:15:17  bala
//remove getTable function as its not used anymore...also getAllChildren
//
//Revision 1.2  2002/09/06 16:45:45  bala
//removing DG update code...will be done by taskmaster from now on
//
//Revision 1.1  2002/09/04 20:48:25  bala
//checkin
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
import java.io.*;

/**
 *  Description of the Class
 *  This ABR handles the case where ANPRREVIEW is set to Interlock by the ODM.
 *  and produces a report for the Announcement Project Review entity
 *  Return Code
 *  Pass
 *
 *@author     Administrator
 *@created    August 30, 2002
 */
public class ANNPROJREVABR01 extends PokBaseABR {
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.";
	
  /**
   *  Execute ABR.
   *
   */

  // Class constants
  public final static String DEF_NOT_POPULATED_HTML = "";
  /**
     * ABR
     *
     */
  public final static String ABR = "ANNPROJREVABR01";

  private EntityGroup m_egParent = null;
  private EntityItem m_ei = null;
  private final String RETURN = System.getProperty("line.separator");
  /**
   * @see COM.ibm.opicmpdh.middleware.taskmaster.AbstractTask#execute_run()
   * @author Administrator
   */
  public void execute_run() {
    int iALL = 0;
    String strANNPROJNAME = null;
    EntityGroup annPrGroup = null;
    EntityGroup ANNGroup = null;
    EntityGroup egParent = null;
    DatePackage dbNow1 = null;
    String strNow = null;
    EntityGroup eg = null;
    String x = null;
    StringWriter writer = null;
    try {
      start_ABRBuild();

      // Build the report header
      //buildReportHeader();
	  buildRptHeader();

      m_egParent = m_elist.getParentEntityGroup();
      m_ei = m_egParent.getEntityItem(0);

      setDGTitle(setDGName(m_ei, ABR));

      iALL = 0;
      strANNPROJNAME = null;
      setReturnCode(PASS);

      //Get the ANNPROJ
      annPrGroup = m_elist.getEntityGroup("ANNPROJ");
      logMessage(
        "***************  getEntityGroup: " + annPrGroup.dump(false));
      logMessage(
        "***************  getEntityItemCount: "
          + annPrGroup.getEntityItemCount());
      if (annPrGroup.getEntityItemCount() > 0) {
        println("<br /><b>Announcement Project</b>");

        for (iALL = 0;
          iALL < annPrGroup.getEntityItemCount();
          iALL++) {
          EntityItem entity = annPrGroup.getEntityItem(iALL);
          EANAttribute att = entity.getAttribute("ANNPROJNAME");
          if (att.toString().length() > 0) {
            strANNPROJNAME = att.toString();
          }
          println(
            getNavigateAttrAndOtherAttr(entity, annPrGroup, iALL));

        } //end of for loop
      }

      //=============get ANNOUNCEMENT ==========================================
      ANNGroup = m_elist.getEntityGroup("ANNOUNCEMENT");
      if (ANNGroup.getEntityItemCount() > 0) {
        println("<br /><b>Announcement</b>");
        println("<br /><br /><table summary=\"Announcement\" width=\"100%\">" + RETURN);
        for (iALL = 0; iALL < ANNGroup.getEntityItemCount(); iALL++) {
          EntityItem entity = ANNGroup.getEntityItem(iALL);
          println(getAttributes(entity, ANNGroup, true, iALL));
        }
        println("</table>");

      }

      //=============get ANNPROJREVIEW ==========================================

      egParent = m_elist.getParentEntityGroup();

      //Get the ANNPROJREVIEW
      if (egParent != null) {

        println("<br /><b>Announcement Project Interlock</b>");        
        setText(
          "ANNPROJREVNAME",
          strANNPROJNAME);

        dbNow1 = m_db.getDates();
        strNow = dbNow1.getNow();

        m_prof.setValOn(strNow);
        m_prof.setEffOn(strNow);

        //reRun Extract Action
        start_ABRBuild();

        eg = m_elist.getParentEntityGroup();

        if (eg != null) {
		  println("<br /><table summary=\"Announcement Project Interlock\" width=\"100%\">" + RETURN);
          EntityItem e = eg.getEntityItem(0);
          println(getAttributes(e, eg, false, 0));
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
      exc.printStackTrace();

      writer = new StringWriter();
      exc.printStackTrace(new PrintWriter(writer));
      x = writer.toString();
      println(x);

      logMessage(
        "Error in " + m_abri.getABRCode() + ":" + exc.getMessage());
      logMessage("" + exc);

    } finally {
      //Everything is fine...so lets pass
      if (getReturnCode() == PASS) {
        setReturnCode(PASS);
      }
      // set DG submit string
      setDGString(getABRReturnCode());
      setDGRptName("ANNPROJREVABR01"); //Set the report name
      setDGRptClass("ANNPROJREVABR01"); //Set the report class
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

  /*
  **Get  Attributes of an Entity
  **
  */
  private String getNavigateAttrAndOtherAttr(
    EntityItem entity,
    EntityGroup eg,
    int index) {
    StringBuffer strBuffer = new StringBuffer();
    if (index == 0) {
      strBuffer.append(RETURN);
      strBuffer.append(
        "<table summary=\"Announcement Project\" width=\"100%\">" + RETURN + 
        "<tr><th id=\"attrDesc" + index +"\" class=\"PsgLabel\">Attribute Description</th><th id=\"attrValue" + index +"\" class=\"PsgLabel\">Attribute Value</th></tr>");
    }

    for (int i = 0; i < eg.getMetaAttributeCount(); i++) {
      EANMetaAttribute ma = eg.getMetaAttribute(i);
      EANAttribute att = entity.getAttribute(ma.getAttributeCode());
      String strAtt = null;
      if (att != null && att.toString().length() > 0) {
        strAtt = att.toString();
      }
      if (ma.isNavigate()) {
        strBuffer.append(
          "<tr><td headers=\"attrDesc" + index +"\" class=\"PsgText\">"
            + ma.getLongDescription()
            + "</td><td headers=\"attrValue" + index +"\" class=\"PsgText\">"
            + (strAtt == null ? "** Not Populated **" : strAtt)
            + "</td></tr>");
      }
    }
    for (int i = 0; i < eg.getMetaAttributeCount(); i++) {
      EANMetaAttribute ma = eg.getMetaAttribute(i);
      EANAttribute att = entity.getAttribute(ma.getAttributeCode());
      String strAtt = null;
      if (att != null && att.toString().length() > 0) {
        strAtt = att.toString();
      }
      if (!ma.isNavigate()) {
        strBuffer.append(
          "<tr><td headers=\"attrDesc" + index +"\" class=\"PsgText\">"
            + ma.getLongDescription()
            + "</td><td headers=\"attrValue" + index +"\" class=\"PsgText\">"
            + (strAtt == null ? "** Not Populated **" : strAtt)
            + "</td></tr>");
      }
    }

    if (index == 0) {
      strBuffer.append("</table>");
    }
    return strBuffer.toString();

  }

  /*
  **Get  Attributes of an Entity
  **
  */
  private String getAttributes(
    EntityItem entity,
    EntityGroup eg,
    boolean navigate,
    int index) {
    StringBuffer strBuffer = new StringBuffer();
    if (navigate) {      
      if (index == 0) {
		strBuffer.append("<tr>");
        for (int i = 0; i < eg.getMetaAttributeCount(); i++) {
          EANMetaAttribute ma = eg.getMetaAttribute(i);
          if (ma.isNavigate()) {
            strBuffer.append(
              "<th id=\"attribute" + i + "\" class=\"PsgLabel\">"
                + ma.getAttributeCode()
                + "</th>");
          }

        }
		strBuffer.append("</tr>");
      }      
      strBuffer.append("<tr>");
      for (int i = 0; i < eg.getMetaAttributeCount(); i++) {
        EANMetaAttribute ma = eg.getMetaAttribute(i);
        EANAttribute att = entity.getAttribute(ma.getAttributeCode());
        String strAtt = null;
        if (att != null && att.toString().length() > 0) {
          strAtt = att.toString();
        }

        if (ma.isNavigate()) {
          strBuffer.append(
            "<td headers=\"attribute" + i + "\" class=\"PsgText\">"
              + (strAtt == null ? "** Not Populated **" : strAtt)
              + "</td>");
        }

      }
      strBuffer.append("</tr>");
    } else {
      strBuffer.append(RETURN);
      strBuffer.append(
        "<tr><th id=\"attribute" + index + "\" class=\"PsgLabel\">Attribute Description</th><th id=\"value" + index + "\" class=\"PsgLabel\">Attribute Value</th></tr>");

      for (int i = 0; i < eg.getMetaAttributeCount(); i++) {
        EANMetaAttribute ma = eg.getMetaAttribute(i);
        EANAttribute att = entity.getAttribute(ma.getAttributeCode());
        String strAtt = null;
        if (att != null && att.toString().length() > 0) {
          strAtt = att.toString();
        }
        strBuffer.append(
          "<tr><td headers=\"attribute" + index + "\" class=\"PsgText\">"
            + ma.getLongDescription()
            + "</td><td headers=\"value" + index + "\" class=\"PsgText\">"
            + (strAtt == null ? "** Not Populated **" : strAtt)
            + "</td></tr>");

      }
      strBuffer.append("</table>");

    }
    return strBuffer.toString();

  }

  // copy one text attribute value from an entity to another entity
  /**
     * setText
     *
     * @param _sAttributeCode
     * @param _sAttributeValue
     * @author Administrator
     */
  public void setText(
    String _sAttributeCode,
    String _sAttributeValue) {

    try {
      //println("****** " + _sEntityType + ":" + _iEntityID + " " + _sAttributeCode );
      logMessage("****before update: " + m_elist.dump(false));
      if (_sAttributeValue != null) {

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
        Text sf =
          new Text(
            m_prof.getEnterprise(),
            rek.getEntityType(),
            rek.getEntityID(),
            _sAttributeCode,
            _sAttributeValue,
            1,
            cbOn);
        Vector vctAtts = new Vector();
        Vector vctReturnsEntityKeys = new Vector();

        if (sf != null) {
          try {
            vctAtts.addElement(sf);

            rek.m_vctAttributes = vctAtts;
            vctReturnsEntityKeys.addElement(rek);

            m_db.update(m_prof, vctReturnsEntityKeys, false, false);
            m_db.commit();
          } catch (Exception x) {
            logMessage(this +" trouble updating text value " + x);
          } finally {
            m_db.freeStatement();
            m_db.isPending("finally after update in Text value");
          }
        }
        /*DatePackage dbNow1 = m_db.getDates();
        strNow = dbNow1.getNow();
        strForever = dbNow1.getForever();

        m_prof.setValOn(strNow);
        m_prof.setEffOn(strNow);

        //reRun Extract Action
        ExtractActionItem eaItem1 = new ExtractActionItem(null, m_db, m_prof, "EXTANNPROJREV01");
        EntityItem ei = new EntityItem (null,m_prof, _sEntityType, _iEntityID);
        EntityItem[] eiParm1 = {ei};
        EntityList m_elist1  = m_db.getEntityList(m_prof, eaItem1, eiParm1);

        EntityGroup eg = m_elist1.getParentEntityGroup();

        if(eg != null){
          EntityItem entity = eg.getEntityItem(0);
          println(getAttributes(entity, eg, false, 0));
        } */
      }
    } catch (COM.ibm.opicmpdh.middleware.MiddlewareException e) {
      logMessage("set Text Value: " + e.getMessage());
    } catch (Exception e) {
      logMessage("set Text Value:" + e.getMessage());
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
     * getRevison
     *
     * @return
     * @author Administrator
     */
  public String getRevision() {
    return "1.14";
  }

  /**
     * getVersion
     *
     * @return
     * @author Administrator
     */
  public static String getVersion() {
    return ("ANNPROJREVABR01.java,v 1.14 2008/01/30 19:39:14 wendy Exp");
  }

  /**
   * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABRVersion()
   * @author Administrator
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
	  println(EACustom.getMetaTags("ANNPROJREVABR01.java"));
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

}
