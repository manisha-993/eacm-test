//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
//CRSTATABR04.java,v
//Revision 1.13  2008/01/30 20:02:00  wendy
//Cleanup RSA warnings
//
//Revision 1.12  2006/03/13 19:42:03  couto
//Fixed copyright info.
//
//Revision 1.11  2006/03/13 13:33:18  couto
//Changed layout, using EACustom methods. Changes for Jtest.
//Fixed br tags. Chaged font tags. Fixed table header.
//buildReportHeader and printNavigateAttributes are now local methods.
//
//Revision 1.10  2006/03/03 19:23:28  bala
//remove reference to Constants.CSS
//
//Revision 1.9  2006/01/24 17:08:24  yang
//Jtest Changes
//
//Revision 1.8  2003/11/07 01:21:18  yang
//Adding setDGRptClass
//
//Revision 1.7  2003/09/18 21:30:50  yang
//adding printStackTrace();
//
//Revision 1.6  2003/09/18 21:15:25  yang
//add getFlagCode instead of getting long description
//
//Revision 1.5  2003/09/18 19:51:50  yang
//adding bala's stuff to finally {
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
//Revision 1.12  2003/05/22 18:58:31  naomi
//adjust setOutOfCirculation
//
//Revision 1.11  2002/12/18 01:44:49  minhthy
//created LockGroup
//
//Revision 1.10  2002/12/12 21:52:28  minhthy
//printNavigateAttributes()
//
//Revision 1.9  2002/11/13 19:53:13  minhthy
//updated ANNDES to ANNDESC
//
//Revision 1.8  2002/11/06 22:07:42  naomi
//remove setReturnCode(PASS) in the finally clause
//
//Revision 1.7  2002/10/31 18:35:35  naomi
//added setDGName
//
//Revision 1.6  2002/10/22 20:00:29  naomi
//add entityID for setDGTitle
//
//Revision 1.5  2002/10/17 00:36:58  minhthy
//changed 'ANCYCLESTATUS' description
//
//Revision 1.4  2002/10/15 21:56:32  minhthy
//modified changeRequest's formatted
//
//Revision 1.3  2002/09/19 22:14:00  naomi
//Added buildReportHeader(),setDGTitle() and replaced getEntityGroup() with getParentEntityGroup()
//
//Revision 1.2  2002/09/17 20:41:43  naomi
//added the Class Description
//
//Revision 1.1  2002/09/11 22:00:28  bala
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
import java.io.*;

/**
 *  Description of the Class
 *  This ABR handles the case where CRSTATUS is set to Approved by the ODM
 *  and produces a report for the Announcement entity.
 *  The ABR unlocks the VE and changes the ANCYCLESTATUS to Process Change (1 - 4).
 *  Return Code
 *  Pass
 *
 *@author Bala
 *@created    September 11, 2002
 */
public class CRSTATABR04 extends PokBaseABR {
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.";
	
  /**
   *  Execute ABR.
   *
   */

  public final static String ABR = "CRSTATABR04";

  private EntityGroup m_egParent = null;
  private EntityItem m_ei = null;

  /**
     * execute_run
     *
     * @author Owner
     */
    public void execute_run() {
    //String strStatus = null;
    String strCR = null;
    String strANNDesc = null;
    String strStatus1= null;
    String strEntityType= null;
    String attrCode = null;
    String COMMENT = null;
    String REVIEW = null;
    String x = null;
    StringWriter writer;
    EANAttribute att = null;
    EntityGroup annGroup = null;
    EntityGroup egParent = null;
    EntityItem entity = null;
	LockActionItem lai;
    setReturnCode(PASS);    

    try {
      start_ABRBuild();

      // Build the report header
      //buildReportHeader();
      buildRptHeader();

      m_egParent = m_elist.getParentEntityGroup();
      m_ei = m_egParent.getEntityItem(0);

      setDGTitle(setDGName(m_ei, ABR));

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
        entity = annGroup.getEntityItem(iALL);
		EntityItem[] aei = { entity };

        logMessage(
          "************Entity Type returned is "
            + entity.getEntityType());

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
          && strStatus1.equals("114")) {
          strCR = "118";
        } else if (
          strEntityType.equals("ANNOUNCEMENT")
          && strStatus1.equals("115")) {
          strCR = "119";
        } else if (
          strEntityType.equals("ANNOUNCEMENT")
          && strStatus1.equals("116")) {
          strCR = "120";
        } else if (
          strEntityType.equals("ANNOUNCEMENT")
          && strStatus1.equals("117")) {
          strCR = "121";
        } else if (
          strEntityType.equals("ANNOUNCEMENT")
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
        } else if (
          strEntityType.equals("ANNOUNCEMENT")
          && strStatus1.equals("122")) {
          strCR = "117";
        }

        logMessage("******************AFter checking Status of announcement");
        logMessage("******************strCR " + strCR);

        /*
         *  Now Update the CRSTATUS column in CHANGEREQUEST Entity
         */
        if (strCR != null && strCR.equals("117")) {
          EntityItem eiParm =
            new EntityItem(
              null,
              m_prof,
              getEntityType(),
              getEntityID());
          //String strEntityType = eiParm.getEntityType();
          int iEntityID = eiParm.getEntityID();
          ReturnEntityKey rek =
            new ReturnEntityKey(strEntityType, iEntityID, true);
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
        }
        /*
         *  Now Update the ANCYCLESTATUS column in ANNOUNCEMENT Entity
         */
        else if (strCR != null && !strCR.equals("117")) {
          EntityItem eiParm =
            new EntityItem(
              null,
              m_prof,
              entity.getEntityType(),
              entity.getEntityID());
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
              "ANCYCLESTATUS",
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

          logMessage("After update of ANNOUNCEMENT COLUMN");
        }

        println("<br /><b>Please make the necessary data changes as described in the following Change Request.</b>");

        //strStatus = null;
        strCR = null;
        strANNDesc = null;
        // }

        //RELEASE VE LOCK FOR ANNOUNCEMENT
        lai =
          new LockActionItem(null, m_db, m_prof, "EXTANN01UNLOCK");        
        lai.setEntityItems(aei);
        lai.executeAction(m_db, m_prof);

        //put the flag value back into "Circulation"
        att = entity.getAttribute("ANNCODENAME");
        logMessage("set in of Circulation ");
        if (att != null) {
          MetaFlag[] amf = (MetaFlag[]) att.get();
          for (int f = 0; f < amf.length; f++) {
            if (amf[f].isSelected()) {
              String code = amf[f].getFlagCode();
              m_db.setOutOfCirculation(
                m_prof,
                entity.getEntityType(),
                "ANNCODENAME",
                code,
                true);
            }
          }

        }

      }

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
          att =
            entity.getAttribute(ma.getAttributeCode());

          attrCode = ma.getAttributeCode();
          COMMENT =
            attrCode.substring(
              attrCode.length() - 7,
              attrCode.length());
          REVIEW =
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

      exc.printStackTrace();

      writer = new StringWriter();
      exc.printStackTrace(new PrintWriter(writer));
      x = writer.toString();
      println(x);

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
      setDGRptName("CRSTATABR04"); //Set the report name
      setDGRptClass("CRSTATABR04"); //Set the report Class
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
    return "1.13";
  }

  /**
     * getVersion
     *
     * @return
     * @author Owner
     */
    public static String getVersion() {
    return ("CRSTATABR04.java,v 1.13 2008/01/30 20:02:00 wendy Exp");
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
	  println(EACustom.getMetaTags("CRSTATABR04.java"));
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
