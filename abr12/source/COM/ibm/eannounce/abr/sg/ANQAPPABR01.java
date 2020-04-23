//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
//ANQAPPABR01.java,v
//Revision 1.14  2006/03/13 19:42:03  couto
//Fixed copyright info.
//
//Revision 1.13  2006/03/13 16:14:16  couto
//Changed layout, using EACustom methods. Changes for Jtest.
//Fixed br tags. Chaged font tags. Fixed table header.
//buildReportHeader and printNavigateAttributes are now local methods.
//
//Revision 1.12  2006/03/03 19:23:26  bala
//remove reference to Constants.CSS
//
//Revision 1.11  2006/01/24 16:44:08  yang
//Jtest Changes
//
//Revision 1.10  2005/02/08 18:29:14  joan
//changes for Jtest
//
//Revision 1.9  2003/11/07 01:20:14  yang
//Adding setDGRptClass
//
//Revision 1.8  2003/09/23 19:24:24  yang
//syntax
//
//Revision 1.7  2003/09/23 19:13:25  yang
//adding logmessage
//
//Revision 1.6  2003/09/23 18:15:10  yang
//changed attribute from ANNREVIEW to ANREVIEW
//
//Revision 1.5  2003/09/18 19:50:38  yang
//adding bala's stuff to finally {
//
//Revision 1.4  2003/06/04 03:53:08  dave
//un Staticing getABRVersion
//
//Revision 1.3  2003/06/04 03:44:24  dave
//minor syntax
//
//Revision 1.2  2003/06/04 03:41:43  dave
//adding getABRVersion
//
//Revision 1.1.1.1  2003/06/03 19:02:24  dave
//new 1.1.1 abr
//
//Revision 1.6  2003/05/14 19:38:28  naomi
//fixed output message
//
//Revision 1.5  2002/12/12 23:14:03  minhthy
//rerun extract action after DB updated
//
//Revision 1.4  2002/11/06 22:04:46  naomi
//remove setReturnCode(PASS) in the finally clause
//
//Revision 1.3  2002/10/31 18:34:56  naomi
//added setDGName
//
//Revision 1.2  2002/10/29 21:53:34  minhthy
//added file
//

package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;

import java.sql.SQLException;
import java.util.*;

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
public class ANQAPPABR01 extends PokBaseABR {
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.";
	
  /**
   *  Execute ABR.
   *
   */
  public final static String ABR = "ANQAPPABR01";

  private EntityGroup m_egParent = null;
  private EntityItem m_ei = null;

  /**
   * @see COM.ibm.opicmpdh.middleware.taskmaster.AbstractTask#execute_run()
   * @author Administrator
   */
  public void execute_run() {
    int iALL = 0;
    boolean fUpdate;
    boolean fNoUpdate;
    EntityGroup egANNREVIEW = null;
    String strANCYCLESTATUS = null;
    EntityGroup egParent = null;
    String strANNDesc = null;
    try {
	  EntityItem entity;
      start_ABRBuild();

      // Build the report header
      //buildReportHeader();
	  buildRptHeader();

      m_egParent = m_elist.getParentEntityGroup();
      m_ei = m_egParent.getEntityItem(0);

      setDGTitle(setDGName(m_ei, ABR));

      iALL = 0;
      fUpdate = false;
      fNoUpdate = false;
      setReturnCode(PASS);

      //Get ANNREVIEW
      egANNREVIEW = m_elist.getEntityGroup("ANNREVIEW");
      strANCYCLESTATUS = null;
      logMessage("**********EntityItem: " + m_ei.dump(false));
      logMessage("**********egANNREVIEW: " + egANNREVIEW);

      if (egANNREVIEW == null) {
        println("****************ANREVIEW Not found ");
        setReturnCode(FAIL);
      } else {
        logMessage("****************ANREVIEW Found ");
        for (iALL = 0;
          iALL < egANNREVIEW.getEntityItemCount();
          iALL++) {
          EntityItem entityItem = egANNREVIEW.getEntityItem(iALL);
          String strANNREVIEW =
            getAttributeValue(
			  entityItem.getEntityType(),
			  entityItem.getEntityID(),
              "ANREVIEW",
              "<em>** Not Populated ** </em>");
          String strANNREVIEWDEF =
            getAttributeValue(
			  entityItem.getEntityType(),
			  entityItem.getEntityID(),
              "ANNREVIEWDEF",
              "<em>** Not Populated ** </em>");

          logMessage(
            "strANNREVIEW: "
              + strANNREVIEW
              + ", strANNREVIEWDEF: "
              + strANNREVIEWDEF);

          if ((strANNREVIEW.equals("Approved")
                            || strANNREVIEW.equals("Proceed with Risk"))
            && strANNREVIEWDEF.equals("Final Review")) {

            if (strANNREVIEW.equals("Approved")) {
              strANCYCLESTATUS = "114";

            } else if (strANNREVIEW.equals("Proceed with Risk")) {
              strANCYCLESTATUS = "115";
            }
          }

          logMessage("strANCYCLESTATUS " + strANCYCLESTATUS);
          if (strANCYCLESTATUS != null) {
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
                "ANCYCLESTATUS",
                strANCYCLESTATUS,
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
            fUpdate = true;
            logMessage("After update of ANNOUNCEMENT COLUMN");
          } else {
            fNoUpdate = true;
          }
        } //end of for
      }

      //reRun Extract Action after DB updated

      m_prof.setValOn(getValOn());
      m_prof.setEffOn(getEffOn());
      start_ABRBuild();

      //get ANNOUNCEMENT
      egParent = m_elist.getParentEntityGroup();
      entity = egParent.getEntityItem(0);
      if (egParent == null) {
        logMessage("****************ANNOUNCEMENT Not found ");
        setReturnCode(FAIL);
      } else {
		EANAttribute att = entity.getAttribute("ANNDESC");
        println("<br /><b>" + egParent.getLongDescription() + "</b>");
        //printNavigateAttributes(entity, egParent, true);
		printNavAttributes(entity, egParent, true);
        strANNDesc =
          (att != null
            ? att.toString()
            : "<em>** Not Populated ** </em>");
        println(
          "<br /><table summary=\"Announcement\" width=\"100%\"><tr><td class=\"PsgLabel\">Announcement Description</td></tr><tr><td class=\"PsgText\">"
            + strANNDesc
            + "</td></tr></table>");
      }

      if (fUpdate && strANCYCLESTATUS != null) {

        EANAttribute att = entity.getAttribute("ANCYCLESTATUS");
        if (att != null) {
          EANMetaAttribute ma = att.getMetaAttribute();
          if (ma != null) {
            if (att instanceof EANFlagAttribute) {
              MetaFlag[] amf = (MetaFlag[]) att.get();
              for (int f = 0; f < amf.length; f++) {
                String strFlagCode = amf[f].getFlagCode();
                if (amf[f].isSelected()
                  && strFlagCode.equals(strANCYCLESTATUS)) {
                  logMessage(
                    "****************ANNOUNCEMENT strFlagCode: "
                      + strFlagCode);
                  println(
                    "<br /><b>Announcement set to '"
                      + amf[f].getLongDescription()
                      + "' </b>");

                }
              }
            }
          }
        }
      }

      if (!fUpdate && fNoUpdate) {
        println("<br /><b>Completed Final Review not found </b>");
        setReturnCode(FAIL);
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
      setDGRptName("ANQAPPABR01"); //Set the report name
      setDGRptClass("ANQAPPABR01"); //Set the report class
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
    return ("ANQAPPABR01.java,v 1.14 2006/03/13 19:42:03 couto Exp");
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
		println(EACustom.getMetaTags("ANQAPPABR01.java"));
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
	private void printNavAttributes(EntityItem entity, EntityGroup eg, boolean navigate){
	  
		println("<br /><br /><table summary=\"" + eg.getLongDescription() + "\" width=\"100%\">" + NEW_LINE);

		for(int i=0;i<eg.getMetaAttributeCount();i++){
			EANMetaAttribute ma = eg.getMetaAttribute(i);
			EANAttribute att = entity.getAttribute(ma.getAttributeCode());
			if(navigate){
				if(i ==0) {
					println("<tr><th class=\"PsgLabel\" width=\"50%\" id=\"attribute\">Navigation Attribute</th>");
					println("<th class=\"PsgLabel\" width=\"50%\" id=\"value\">Attribute Value</th></tr>");				  				 
				}
				if(ma.isNavigate()) {
					println("<tr><td headers=\"attribute\" class=\"PsgText\" width=\"50%\">"+ ma.getLongDescription()+"</td>");
					println("<td headers=\"value\" class=\"PsgText\" width=\"50%\">" +(att == null || att.toString().length() ==0 ? "** Not Populated **" : att.toString()) + "</td></tr>");
				}
			}
			else {
				if(i ==0) {
					println("<tr><th class=\"PsgLabel\" width=\"50%\" id=\"attribute\">Attribute Description</th>");
					println("<th class=\"PsgLabel\" width=\"50%\" id=\"value\">Attribute Value</th></tr>");				  				  
				}
				println("<tr><td headers=\"attribute\" class=\"PsgText\" width=\"50%\">"+ ma.getLongDescription()+"</td>");
				println("<td headers=\"value\" class=\"PsgText\" width=\"50%\">" +(att == null || att.toString().length() ==0 ? "** Not Populated **" : att.toString()) + "</td></tr>");			  
			}

		}
		println("</table>");

   }	

}
