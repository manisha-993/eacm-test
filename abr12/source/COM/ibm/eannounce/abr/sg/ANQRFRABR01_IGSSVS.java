//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2001, 2006  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
//ANQRFRABR01_IGSSVS.java,v
//Revision 1.20  2008/03/19 19:40:51  wendy
//Clean up RSA warnings
//
//Revision 1.19  2006/03/13 19:42:03  couto
//Fixed copyright info.
//
//Revision 1.18  2006/03/13 13:17:44  couto
//Changed layout, using EACustom methods. Changes for Jtest.
//Fixed br tags. Chaged font tags. Fixed multiple return statements.
//displayHeader, displayNavAttributes and displayStatuses are now local methods.
//
//Revision 1.17  2006/03/03 19:23:26  bala
//remove reference to Constants.CSS
//
//Revision 1.16  2006/01/24 17:02:29  yang
//Jtest Changes
//
//Revision 1.15  2005/02/08 18:29:14  joan
//changes for Jtest
//
//Revision 1.14  2003/11/10 22:10:44  yang
//removed checkCOUNTRYLISTstatus FB52954
//
//Revision 1.13  2003/11/07 01:05:01  yang
//adding setDGRptClass
//
//Revision 1.12  2003/10/06 16:53:14  yang
//syntax
//
//Revision 1.11  2003/10/02 20:20:12  yang
//update setFlagValue3 to queue RFA_IGSSVS
//
//Revision 1.10  2003/10/02 19:59:01  yang
//added check____Status for different entities
//
//Revision 1.6  2003/09/24 22:33:32  yang
//syntax
//
//Revision 1.5  2003/09/24 22:20:42  yang
//more logs
//
//Revision 1.4  2003/09/24 21:46:43  yang
//syntax
//
//Revision 1.3  2003/09/24 21:16:34  yang
//adding logMessage
//
//Revision 1.2  2003/09/24 21:00:06  yang
//adding ANQRFRABR01_IGSSVS
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
//import java.sql.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Administrator
 */
public class ANQRFRABR01_IGSSVS extends PokBaseABR {
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2001, 2006  All Rights Reserved.";
	
  /**
  *  Execute ABR.
  */

  //ABR
  public final static String ABR = "ANQRFRABR01";
  /**
     * CHANGEREQUEST_STATUS
     *
     */
  public final static String CHANGEREQUEST_STATUS = "CRSTATUS";
  /**
     * ANCYCLESTATUS
     *
     */
  public final static String ANCYCLESTATUS = "ANCYCLESTATUS";
  /**
     * RFAHWABR01_STATUS
     *
     */
  public final static String RFAHWABR01_STATUS = "RFAHWABR01";
  /**
     * ANNOUNCEMENT
     *
     */
  public final static String ANNOUNCEMNET = "ANNOUNCEMNET";

  //AVAIL Attributes must exist:
  /**
     * EFFECTIVEDATE
     *
     */
  public final static String EFFECTIVEDATE = "EFFECTIVEDATE";
  /**
     * AVAILTYPE
     *
     */
  public final static String AVAILTYPE = "AVAILTYPE";
  /**
     * GENAREASELECTION
     *
     */
  public final static String GENAREASELECTION = "GENAREASELECTION";

  //CMPNT Attributes must exist:
  /**
     * MKTGNAME
     *
     */
  public final static String MKTGNAME = "MKTGNAME";
  /**
     * COMPONENTID
     *
     */
  public final static String COMPONENTID = "COMPONENTID";
  /**
     * DESCRIPTION
     *
     */
  public final static String DESCRIPTION = "DESCRIPTION";
  /**
     * OVERVIEWABSTRACT
     *
     */
  public final static String OVERVIEWABSTRACT = "OVERVIEWABSTRACT";
  /**
     * DISTRCODE
     *
     */
  public final static String DISTRCODE = "DISTRCODE";
  /**
     * SVCCAT
     *
     */
  public final static String SVCCAT = "SVCCAT";
  /**
     * VAE
     *
     */
  public final static String VAE = "VAE";

  /**
     * c_hshANNtoANN
     *
     */
  public static final Hashtable c_hshANNtoANN = new Hashtable();
  /**
     * c_hshFinalEntities
     *
     */
  public static final Hashtable c_hshFinalEntities = new Hashtable();

  static {

    c_hshFinalEntities.put("SOF", "HI");
    c_hshFinalEntities.put("CMPNT", "HI");
    c_hshFinalEntities.put("FEATURE", "HI");
    c_hshFinalEntities.put("ANNOUNCEMENT", "HI");
    c_hshFinalEntities.put("AVAIL", "HI");
    c_hshFinalEntities.put("ANNDELIVERABLE", "HI");
    c_hshFinalEntities.put("CATINCL", "HI");
    c_hshFinalEntities.put("CHANNEL", "HI");
    c_hshFinalEntities.put("FINOF", "HI");
    c_hshFinalEntities.put("CONFIGURATOR", "HI");
    c_hshFinalEntities.put("ORGANUNIT", "HI");
    c_hshFinalEntities.put("PRICEFININFO", "HI");
    c_hshFinalEntities.put("STANDAMENDTEXT", "HI");

    c_hshANNtoANN.put("ANNOUNCEMENT", "ANNOUNCEMENT");
    //ROW4 , //ROW5, //ROW6

  }

  private EntityGroup m_egParent = null; //root POFPOFMGMTGRP
  private EntityItem m_eiParent = null; //root POFPOFMGMTGRP

  //private final String m_strParerntStatusValue = null;
  //ANCYCLESTATUS   Value of Parent ANNOUNCEMENT
  // final String m_strParentAnnCodeNameValue = null;
  //ANNCODENAME     Value of Parent ANNOUNCEMENT
  private final String m_strApprovedValue = "114";
  //ANCYCLESTATUS  Ststus Attribute Approved
  private final String m_strReadyFinalReviewValue = "112";
  //ANCYCLESTATUS  Ststus Attribute Ready for Final Review
  private final String m_strApprovedRiskValue = "115";
  //ANCYCLESTATUS  Ststus Attribute Approved with Risk
  private final String m_strQueueRFAValue = "0020";
  //ANCYCLESTATUS  Ststus Attribute Approved with Risk

  /**
   * @see COM.ibm.opicmpdh.middleware.taskmaster.AbstractTask#execute_run()
   * @author Administrator
   */
  public void execute_run() {
    EntityGroup eg = null;
    boolean finish = false;
    try {

      start_ABRBuild();
      m_egParent = m_elist.getParentEntityGroup();
      m_eiParent = m_egParent.getEntityItem(0);

      if (m_egParent == null) {
        println(
          ABR
            + ":"
            + getVersion()
            + ":ERROR:1: m_egParent cannot be established.");
        setReturnCode(FAIL);
        //return;
        finish = true;
      }
      if (!finish) {
	      if (m_eiParent == null) {
	        println(
	          ABR
	            + ":"
	            + getVersion()
	            + ":ERROR:2: m_eiParent cannot be established.");
	        setReturnCode(FAIL);
	        return;
	      }
      }
      
      if (!finish) {
		  EntityGroup eg1;
		  EntityGroup eg2;
		  EntityGroup eg3;

	      logMessage(
	        ABR
	          + ":"
	          + getVersion()
	          + ":Request to Work on Entity:"
	          + m_eiParent.getEntityType()
	          + ":"
	          + m_eiParent.getEntityID());
		  buildRptHeader();
	      setControlBlock();
	      setDGTitle(setDGName(m_eiParent, ABR));
	
	      logMessage(
	        ABR
	          + ":"
	          + getVersion()
	          + ":Setup Complete:"
	          + m_eiParent.getEntityType()
	          + ":"
	          + m_eiParent.getEntityID());
	
	      // Default the thing to pass...
	      setReturnCode(PASS);
	
	      //displayHeader(m_egParent, m_eiParent);
	      dispHeader(m_egParent, m_eiParent);
	      
	
	      //Checking status from Announcement
	
	      checkFinalStatus();
	      checkCRstatus();
	      checkAVAILstatus();
	      checkCMPNTstatus();
	      checkSOFstatus();
	      checkSTANDTEXTstatus();
	      checkFEATUREstatus();
	      checkPRICEINFOstatus();
	      checkANNOUNCEMENTstatus();
	
	      //check for GENERALAREA first then perform other checks
	      eg = m_eiParent.getEntityGroup();
	      logMessage("***GENERALAREA eg.true***" + eg.dump(true));
	      logMessage("***GENERALAREA eg***" + eg);
	      logMessage(
	        "***GENERALAREA getDownLinkCount()***:"
	          + m_eiParent.getDownLinkCount());
	      for (int i = 0; i < m_eiParent.getDownLinkCount(); i++) {
	        EntityItem eiRelator = (EntityItem) m_eiParent.getDownLink(i);
	        //logMessage("***GENERALAREA eiRelator***:" + eiRelator);
	        EntityItem eiChild = (EntityItem) eiRelator.getDownLink(0);
	        //logMessage("***GENERALAREA eiChild***:" + eiChild);
	        if (eiChild.getEntityType().equals("GENERALAREA")) {
	          //logMessage("***GENERALAREA eiChild***" + eiChild);
	          String strStatus = getFlagCode(eiChild, "RFAGEO");
	          logMessage(
	            "****strStatus:"
	              + eiChild.getEntityType()
	              + "-"
	              + eiChild.getEntityID()
	              + ":"
	              + strStatus);
	          if (strStatus.equals("200")) {
	            checkCONFIGAVAILDATEstatus();
	            checkOPstatus();
	            checkCONFIGNAMEstatus();
	            logMessage("***RFAGEO is: 200");
	          } else if (strStatus.equals("201")) {
	            checkCONFIGAVAILDATEstatus();
	            checkOPstatus();
	            checkCONFIGNAMEstatus();
	            logMessage("***RFAGEO is: 201");
	          } else if (strStatus.equals("202")) {
	            checkCONFIGAVAILDATEstatus();
	            checkOPstatus();
	            checkCONFIGNAMEstatus();
	            logMessage("***RFAGEO is: 202");
	          } else if (strStatus.equals("203")) {
	            checkCONFIGAVAILDATEstatus();
	            checkCHANNELstatus();
	            //checkCOUNTRYLISTstatus();
	            checkOPstatus();
	            checkCONFIGNAMEstatus();
	            logMessage("***RFAGEO is: 203");
	          } else if (strStatus.equals("204")) {
	            checkCONFIGAVAILDATEstatus();
	            checkOPstatus();
	            checkCONFIGNAMEstatus();
	            logMessage("***RFAGEO is: 204");
	          } else if (strStatus.equals("205")) {
	            checkCONFIGAVAILDATEstatus();
	            checkOPstatus();
	            checkCONFIGNAMEstatus();
	            logMessage("***RFAGEO is: 205");
	          }
	        }
	      }
	
	      //check for ANNOP first then perform other checks
	      eg1 = m_eiParent.getEntityGroup();
	      logMessage("***ANNOP eg1.true***" + eg1.dump(true));
	      logMessage("***ANNOP eg1***" + eg1);
	      logMessage(
	        "***ANNOP getDownLinkCount()***:"
	          + m_eiParent.getDownLinkCount());
	      for (int i = 0; i < m_eiParent.getDownLinkCount(); i++) {
	        EntityItem eiRelator = (EntityItem) m_eiParent.getDownLink(i);
			EntityItem eiChild = (EntityItem) eiRelator.getDownLink(0);
	        logMessage("***ANNOP eiRelator***:" + eiRelator);	        
	        logMessage("***ANNOP eiChild***:" + eiChild);
	        if (eiChild.getEntityType().equals("ANNOP")) {
			  String strStatus = getFlagCode(eiChild, "ANNROLETYPE");
	          logMessage("***ANNOP eiChild***" + eiChild);	          
	          logMessage(
	            "****strStatus:"
	              + eiChild.getEntityType()
	              + "-"
	              + eiChild.getEntityID()
	              + ":"
	              + strStatus);
	          if (strStatus.equals("3")) {
	            checkOPstatus();
	            logMessage("***ANNROLETYPE is: 3");
	          } else if (strStatus.equals("5")) {
	            checkOPstatus();
	            logMessage("***ANNROLETYPE is: 5");
	          } else if (strStatus.equals("6")) {
	            checkOPstatus();
	            logMessage("***ANNROLETYPE is: 6");
	          } else if (strStatus.equals("7")) {
	            checkOPstatus();
	            logMessage("***ANNROLETYPE is: 7");
	          } else if (strStatus.equals("9")) {
	            checkOPstatus();
	            logMessage("***ANNROLETYPE is: 9");
	          } else if (strStatus.equals("15")) {
	            checkOPstatus();
	            logMessage("***ANNROLETYPE is: 15");
	          }
	        }
	      }
	
	      //check for ORGANUNIT first then perform other checks
	      eg2 = m_eiParent.getEntityGroup();
	      logMessage("***ORGANUNIT eg2.true***" + eg2.dump(true));
	      logMessage("***ORGANUNIT eg2***" + eg2);
	      logMessage(
	        "***ORGANUNIT getDownLinkCount()***:"
	          + m_eiParent.getDownLinkCount());
	      for (int i = 0; i < m_eiParent.getDownLinkCount(); i++) {
	        EntityItem eiRelator = (EntityItem) m_eiParent.getDownLink(i);
			EntityItem eiChild = (EntityItem) eiRelator.getDownLink(0);
	        logMessage("***ORGANUNIT eiRelator***:" + eiRelator);	        
	        logMessage("***ORGANUNIT eiChild***:" + eiChild);
	        if (eiChild.getEntityType().equals("ORGANUNIT")) {
			  String strStatus = getFlagCode(eiChild, "ORGANUNITTYPE");
	          logMessage("***ORGANUNIT eiChild***" + eiChild);	          
	          logMessage(
	            "****strStatus:"
	              + eiChild.getEntityType()
	              + "-"
	              + eiChild.getEntityID()
	              + ":"
	              + strStatus);
	          if (strStatus.equals("4156")) {
	            checkORGANUNITstatus();
	            logMessage("***ORGANUNITTYPE is: 4156");
	          }
	        }
	      }
	
	      //check for ANNDELIVERABLE first then perform other checks
	      eg3 = m_eiParent.getEntityGroup();
	      logMessage("***ANNDELIVERABLE eg3.true***" + eg3.dump(true));
	      logMessage("***ANNDELIVERABLE eg3***" + eg3);
	      logMessage(
	        "***ANNDELIVERABLE getDownLinkCount()***:"
	          + m_eiParent.getDownLinkCount());
	      for (int i = 0; i < m_eiParent.getDownLinkCount(); i++) {
	        EntityItem eiRelator = (EntityItem) m_eiParent.getDownLink(i);
			EntityItem eiChild = (EntityItem) eiRelator.getDownLink(0);
	        logMessage("***ANNDELIVERABLE eiRelator***:" + eiRelator);	        
	        logMessage("***ANNDELIVERABLE eiChild***:" + eiChild);
	        if (eiChild.getEntityType().equals("ORGANUNIT")) {
			  String strStatus = getFlagCode(eiChild, "ORGANUNITTYPE");
	          logMessage("***ANNDELIVERABLE eiChild***" + eiChild);	          
	          logMessage(
	            "****strStatus:"
	              + eiChild.getEntityType()
	              + "-"
	              + eiChild.getEntityID()
	              + ":"
	              + strStatus);
	          if (strStatus.equals("860")) {
	            checkSUBJECTLINE_1status();
	            logMessage("***ORGANUNITTYPE is: 860");
	          }
	        }
	      }
	
	      logMessage("***getReturnCode()***" + getReturnCode());
	      if (getReturnCode() == PASS) {
	
	        String strFlagCode = getFlagCode(m_eiParent, getStatusAttributeCode(m_eiParent));
			LockActionItem lai;
			EntityItem[] aei = { m_eiParent };
			EANAttribute att;
			String m_strParentAnnCodeNameValue;
			MetaFlag[] amf;
			String strFlagValue;
	        if (strFlagCode.equals("111")) {
	          setFlagValue(ANCYCLESTATUS);
	          //Draft
	        } else if (strFlagCode.equals("113")) {
	          setFlagValue(ANCYCLESTATUS);
	          //Change (Final Review)
	        } else if (strFlagCode.equals("118")) {
	          setFlagValue1(ANCYCLESTATUS);
	          //Change (Approved)
	        } else if (strFlagCode.equals("119")) {
	          setFlagValue2(ANCYCLESTATUS);
	          //Change (Approved w/Risk)
	        } else if (strFlagCode.equals("120")) {
	          setFlagValue3(
	            RFAHWABR01_STATUS);
	          //Change (Released)
	        } else if (strFlagCode.equals("121")) {
	          setFlagValue3(
	            RFAHWABR01_STATUS);
	          //Change (Announced)
	        }
	        //setVELock
	        lai = new LockActionItem(null, m_db, m_prof, "LOCKEXTANN01LOCK1");	        
	        lai.setEntityItems(aei);
	        lai.executeAction(m_db, m_prof);
	
	        //take the value of ANNCODENAME and set the value to out of circulation
	        //if the value of ANNCODENAME is already set to out of circulation, do nothing
	
	        att = m_eiParent.getAttribute("ANNCODENAMEU");
	        //logMessage("***** m_eiParent dump is " + m_eiParent.dump(false));
	        logMessage("***** att for ANNCODENAME is " + att);
	        m_strParentAnnCodeNameValue = (att.toString());
	
	        
	        amf = (MetaFlag[]) att.get();
	        strFlagValue = null;
	
	        if (amf != null) {
	          for (int j = 0; j < amf.length; j++) {
	            if (amf[j]
	              .getLongDescription()
	              .equals(m_strParentAnnCodeNameValue)) {
	
	              ///println("***** j is " + j + " parentValue " + m_strParentAnnCodeNameValue);
	              ///println("***** j is " + j + " getFlagCode " + amf[j].getFlagCode());
	              ///println("***** j is " + j + " getLongDescription " + amf[j].getLongDescription());
	
	              logMessage(
	                "***** j is "
	                  + j
	                  + " parentValue "
	                  + m_strParentAnnCodeNameValue);
	              logMessage(
	                "***** j is "
	                  + j
	                  + " getFlagCode "
	                  + amf[j].getFlagCode());
	              logMessage(
	                "***** j is "
	                  + j
	                  + " getLongDescription "
	                  + amf[j].getLongDescription());
	
	              //set out of circulation
	              strFlagValue = amf[j].getFlagCode();
	              m_db.setOutOfCirculation(
	                m_prof,
	                m_eiParent.getEntityType(),
	                "ANNCODENAME",
	                strFlagValue,
	                false);
	            }
	          }
	        }
	
	        //reRun Extract Action after DB updated
	        m_prof.setValOn(getValOn());
	        m_prof.setEffOn(getEffOn());
	        //start_ABRBuild();
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
	            
      } //else !finish
	
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
	  StringWriter writer = new StringWriter();
	  String x;
      // Report this error to both the datbase log and the PrintWriter
      println("Error in " + m_abri.getABRCode() + ":" + exc.getMessage());
      println("" + exc);

      // don't overwrite an update exception
      if (getABRReturnCode() != UPDATE_ERROR) {
        setReturnCode(INTERNAL_ERROR);
      }

      exc.printStackTrace();
      
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
      setDGRptName("ANQRFRABR01_IGSSVS"); //Set the report name
      setDGRptClass("ANQRFRABR01_IGSSVS"); //Set the report class      
      // make sure the lock is released
      if (!isReadOnly()) {
        clearSoftLock();
      }
    }
    
	if (!finish) {
		//Print everything up to </html>
		printDGSubmitString();
		println(EACustom.getTOUDiv());
		buildReportFooter();    // Print </html>
	}
  }

  /**
     * printFAILmessage
     *
     * @param _eg
     * @author Administrator
     */
  public void printFAILmessage(EntityGroup _eg) {
    println(
      "<br /><br /><b>"
        + _eg.getLongDescription()
        + " is not at final status.</b>");
    setReturnCode(FAIL);

  }
  /**
     * printWARNINGmessage
     *
     * @param _eg
     * @author Administrator
     */
  public void printWARNINGmessage(EntityGroup _eg) {
    println(
      "<br /><br /><b>"
        + _eg.getLongDescription()
        + " does not exist (Warning - Pass)</b>");

  }

  /**
     * printFAILmessage 2
     *
     * @param _eg
     * @author Administrator
     */
  public void printFAILmessage_2(EntityGroup _eg) {
    println(
      "<br /><br /><b>"
        + _eg.getLongDescription()
        + " needs to be 'Final'status</b>");
    setReturnCode(FAIL);

  }
  /**
     * printWARNINGmessage 2
     *
     * @param _eg
     * @author Administrator
     */
  public void printWARNINGmessage_2(EntityGroup _eg) {
    println(
      "<br /><br /><b>"
        + _eg.getLongDescription()
        + " needs to be 'Final'status (Warning - Pass)</b>");

  }

  /**
     * printFAILmessage 3
     *
     * @param _eg
     * @author Administrator
     */
  public void printFAILmessage_3(EntityGroup _eg) {
    println(
      "<br /><br /><b>"
        + _eg.getLongDescription()
        + " needs to be at least 'Ready for Final Review'</b>");
    setReturnCode(FAIL);

  }
  /**
     * printWARNINGmessage 3
     *
     * @param _eg
     * @author Administrator
     */
  public void printWARNINGmessage_3(EntityGroup _eg) {
    println(
      "<br /><br /><b>"
        + _eg.getLongDescription()
        + " needs to be at least 'Ready for Final Review' (Warning - Pass)</b>");

  }

  /**
     * check final status
     *
     * @author Administrator
     */
  public void checkFinalStatus() {
    String strStatus = null;

    for (int ii = 0; ii < m_elist.getEntityGroupCount(); ii++) {

      EntityGroup _eg = m_elist.getEntityGroup(ii);

      if (c_hshFinalEntities.containsKey(_eg.getEntityType())) {		
        logMessage(
          "***** _eg.getEntityType() is " + _eg.getEntityType());
        //logMessage("***** c_hshFinalEntities is " + c_hshFinalEntities.containsKey(_eg.getEntityType()));        

        //IF AN ENTITY EXISTS THEN CHECK STATUS VALUE.
        if (_eg.getEntityItemCount() > 0) {
          //logMessage("***_eg.()***" + _eg.dump(true));
          logMessage(
            "***_eg.getEntityItemCount()***"
              + _eg.getEntityItemCount());

          for (int i = 0; i < _eg.getEntityItemCount(); i++) {
            EntityItem ei = _eg.getEntityItem(i);

            if (getStatusAttributeCode(ei) != null) {
			  String strStatusValue;
              EANAttribute att =
                ei.getAttribute(getStatusAttributeCode(ei));
              logMessage(
                "getStatusAttributeCode***: "
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + ":"
                  + att);
              strStatusValue =
                (att != null
                  ? att.toString()
                  : DEF_NOT_POPULATED_HTML);
              strStatus =
                getFlagCode(ei, getStatusAttributeCode(ei));
              logMessage(
                "***strStatus getFlagCode "
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + ":"
                  + strStatus);
              logMessage(
                "***Entity Attribute values: "
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + ":"
                  + strStatusValue);
              if (strStatus.equals("111")
                || strStatus.equals("114")
                || strStatus.equals("115")
                || strStatus.equals("116")) {
                logMessage(
                  "strStatusValue not to final***: "
                    + ei.getEntityType()
                    + ":"
                    + ei.getEntityID()
                    + ":"
                    + strStatusValue);
                println(
                  "<br /><br /><b>"
                    + _eg.getLongDescription()
                    + ":"
                    + ei.getEntityID()
                    + "  is not at final status.</b>");
                println(dispNavAttributes(ei, _eg));
                println(displayStatus(ei, _eg));
                setReturnCode(FAIL);

              }
            }
          }

        }
        // IF AN ENTITY DOES NOT EXIST , PRINT MESSAGE
        else {
          logMessage("***ENTITY DOES NOT EXIST***" + _eg.dump(true));
          printWARNINGmessage(_eg);
        }
      }
    }
  } //checkStatus

  /**
     * checkCRstatus
     *
     * @author Administrator
     */
  public void checkCRstatus() {
    EntityGroup _eg = m_elist.getEntityGroup("CHANGEREQUEST");
    if (_eg != null) {
      Vector vEI = new Vector();

      for (int i = 0; i < _eg.getEntityItemCount(); i++) {
        EntityItem ei = _eg.getEntityItem(i);
        if (ei != null) {
          EANAttribute att = ei.getAttribute(CHANGEREQUEST_STATUS);
          String strStatus =
            (att != null ? att.toString() : DEF_NOT_POPULATED_HTML);
          if (strStatus.equals("Approved")) {
            vEI.addElement(ei);
            //logMessage("checkCR " + ei.dump(true));
          }
        }
      }

      if (vEI != null && vEI.size() > 0) {
        EntityItem ei = (EntityItem) vEI.elementAt(0);
        if (ei != null) {
          EntityGroup eg = ei.getEntityGroup();
          if (eg != null) {
            println(
              "<br /><br /><b>The following "
                + eg.getLongDescription()
                + " are still in the Approved state</b>");
            println(
              "<br /><br /><b>" + eg.getLongDescription() + "</b>");
            println(dispNavAttributes(ei, eg));
          }
        }
        setReturnCode(FAIL);
      }
    }
  } //checkCR

  /**
     * checkAVAILstatus
     *
     * @author Administrator
     */
  public void checkAVAILstatus() {
    EntityGroup _eg = m_elist.getEntityGroup("AVAIL");
    String strStatus = null;
    String strStatus1 = null;
    //String strStatus2 = null;
    if (_eg != null) {

      if (_eg.getEntityItemCount() == 0) {
        println(
          "<br /><br /><b>Entity: " + _eg.getLongDescription() + "</b>");
        println("<br /><br /><b>There is no value for: EFFECTIVEDATE</b>");
        println("<br /><br /><b>There is no value for: AVAILTYPE</b>");
        println("<br /><br /><b>There is no value for: GENAREASELECTION</b>");
        setReturnCode(FAIL);
      } else {

        for (int i = 0; i < _eg.getEntityItemCount(); i++) {
          EntityItem ei = _eg.getEntityItem(i);
          if (ei != null) {
            EANAttribute[] atts = new EANAttribute[3];
            atts[0] = ei.getAttribute(EFFECTIVEDATE);
            atts[1] = ei.getAttribute(AVAILTYPE);
            atts[2] = ei.getAttribute(GENAREASELECTION);
            //logMessage("atts[0]:" + atts[0]);
            //logMessage("atts[1]:" + atts[1]);
            //logMessage("atts[2]:" + atts[2]);
            strStatus = (atts[0].toString());
            strStatus1 = (atts[1].toString());
           // strStatus2 = (atts[2].toString());
            //logMessage("strStatus for AVAIL:" + strStatus);
            //logMessage("strStatus1 for AVAIL:" + strStatus1);
            //logMessage("strStatus2 for AVAIL:" + strStatus2);
            if (strStatus.equals("")) {
              println("<br /><br /><b>There is no value for: EFFECTIVEDATE</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            } else if (strStatus1.equals("")) {
              println("<br /><br /><b>There is no value for: AVAILTYPE</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              
              setReturnCode(FAIL);
            } else if (strStatus1.equals("")) {
              println("<br /><br /><b>There is no value for: GENAREASELECTION</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
          }
        }
      }
    }
  } //checkAVAILstatus

  /**
     * checkCMPNTstatus
     *
     * @author Administrator
     */
  public void checkCMPNTstatus() {
    EntityGroup _eg = m_elist.getEntityGroup("CMPNT");

    if (_eg != null) {

      if (_eg.getEntityItemCount() == 0) {
        println(
          "<br /><br /><b>Entity: " + _eg.getLongDescription() + "</b>");
        println("<br /><br /><b>There is no value for: MKTGNAME</b>");
        println("<br /><br /><b>There is no value for: COMPONENTID</b>");
        println("<br /><br /><b>There is no value for: DESCRIPTION</b>");
        println("<br /><br /><b>There is no value for: OVERVIEWABSTRACT</b>");
        println("<br /><br /><b>There is no value for: DISTRCODE</b>");
        println("<br /><br /><b>There is no value for: SVCCAT</b>");
        println("<br /><br /><b>There is no value for: VAE</b>");
        setReturnCode(FAIL);
      } else {
        for (int i = 0; i < _eg.getEntityItemCount(); i++) {
          //logMessage("_eg.getEntityItem(i):" + _eg.getEntityItem(i));
          EntityItem ei = _eg.getEntityItem(i);

          logMessage(
            "_eg.getEntityItem(i) for CMPNT:"
              + _eg.getEntityItem(i));
          logMessage("ei for CMPNT:" + ei.dump(false));

          if (ei != null) {
            EANAttribute[] atts = new EANAttribute[7];
            atts[0] = ei.getAttribute(MKTGNAME);
            atts[1] = ei.getAttribute(COMPONENTID);
            atts[2] = ei.getAttribute(DESCRIPTION);
            atts[3] = ei.getAttribute(OVERVIEWABSTRACT);
            atts[4] = ei.getAttribute(DISTRCODE);
            atts[5] = ei.getAttribute(SVCCAT);
            atts[6] = ei.getAttribute(VAE);
            //logMessage("atts[0]:" + atts[0]);
            //logMessage("atts[1]:" + atts[1]);
            //logMessage("atts[2]:" + atts[2]);
            //logMessage("atts[3]:" + atts[3]);
            //logMessage("atts[4]:" + atts[4]);
            //logMessage("atts[5]:" + atts[5]);
            //logMessage("atts[6]:" + atts[6]);
            if (atts[0] == null) {
              println("<br /><br /><b>There is no value for: MKTGNAME</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[1] == null) {
              println("<br /><br /><b>There is no value for: COMPONENTID</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[2] == null) {
              println("<br /><br /><b>There is no value for: DESCRIPTION</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[3] == null) {
              println("<br /><br /><b>There is no value for: OVERVIEWABSTRACT</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[4] == null) {
              println("<br /><br /><b>There is no value for: DISTRCODE</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[5] == null) {
              println("<br /><br /><b>There is no value for: SVCCAT</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[6] == null) {
              println("<br /><br /><b>There is no value for: VAE</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
          }
        }
      }
    }
  } //checkCMPNTstatus

  /**
     * checkSOFstatus
     *
     * @author Administrator
     */
  public void checkSOFstatus() {
    EntityGroup _eg = m_elist.getEntityGroup("SOF");

    if (_eg != null) {

      if (_eg.getEntityItemCount() == 0) {
        println(
          "<br /><br /><b>Entity: " + _eg.getLongDescription() + "</b>");
        println("<br /><br /><b>There is no value for: MKTGNAME</b>");
        println("<br /><br /><b>There is no value for: OFIDNUMBER</b>");
        println("<br /><br /><b>There is no value for: ASSORTMODULE</b>");
        println("<br /><br /><b>There is no value for: MATACCGRP</b>");
        println("<br /><br /><b>There is no value for: OVERVIEWABSTRACT</b>");
        println("<br /><br /><b>There is no value for: DESCRIPTION</b>");
        println("<br /><br /><b>There is no value for: GBNAME</b>");
        setReturnCode(FAIL);
        logMessage("SOF getReturnCode" + getReturnCode());
      } else {
        for (int i = 0; i < _eg.getEntityItemCount(); i++) {
          //logMessage("_eg.getEntityItem(i):" + _eg.getEntityItem(i));
          EntityItem ei = _eg.getEntityItem(i);

          logMessage(
            "_eg.getEntityItem(i) for SOF:" + _eg.getEntityItem(i));
          logMessage("ei for SOF:" + ei.dump(false));

          if (ei != null) {
            EANAttribute[] atts = new EANAttribute[7];
            atts[0] = ei.getAttribute("MKTGNAME");
            atts[1] = ei.getAttribute("OFIDNUMBER");
            atts[2] = ei.getAttribute("ASSORTMODULE");
            atts[3] = ei.getAttribute("MATACCGRP");
            atts[4] = ei.getAttribute("OVERVIEWABSTRACT");
            atts[5] = ei.getAttribute("DESCRIPTION");
            atts[6] = ei.getAttribute("GBNAME");
            logMessage("atts[0]:" + atts[0]);
            logMessage("atts[1]:" + atts[1]);
            logMessage("atts[2]:" + atts[2]);
            logMessage("atts[3]:" + atts[3]);
            logMessage("atts[4]:" + atts[4]);
            logMessage("atts[5]:" + atts[5]);
            logMessage("atts[6]:" + atts[6]);
            if (atts[0] == null) {
              println("<br /><br /><b>There is no value for: MKTGNAME</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[1] == null) {
              println("<br /><br /><b>There is no value for: OFIDNUMBER</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[2] == null) {
              println("<br /><br /><b>There is no value for: ASSORTMODULE</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[3] == null) {
              println("<br /><br /><b>There is no value for: MATACCGRP</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[4] == null) {
              println("<br /><br /><b>There is no value for: OVERVIEWABSTRACT</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[5] == null) {
              println("<br /><br /><b>There is no value for: DESCRIPTION</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[6] == null) {
              println("<br /><br /><b>There is no value for: GBNAME</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
          }
        }
      }
    }
  } //checkSOFstatus

  /**
     * checkSTANDTEXTstatus
     *
     * @author Administrator
     */
  public void checkSTANDTEXTstatus() {
    EntityGroup _eg = m_elist.getEntityGroup("STANDAMENDTEXT");
    if (_eg != null) {

      if (_eg.getEntityItemCount() == 0) {
        println(
          "<br /><br /><b>Entity: " + _eg.getLongDescription() + "</b>");
        println("<br /><br /><b>There is no value for: STANDARDAMENDTEXT_TYPE</b>");
        setReturnCode(FAIL);
      } else {

        logMessage(
          "****_eg.getEntityItemCount() for STANDAMENDTEXT:"
            + _eg.getEntityItemCount());
        for (int i = 0; i < _eg.getEntityItemCount(); i++) {
          EntityItem ei = _eg.getEntityItem(i);
          if (ei != null) {
            EANAttribute att =
              ei.getAttribute("STANDARDAMENDTEXT_TYPE");
            String strStatus =
              getFlagCode(ei, "STANDARDAMENDTEXT_TYPE");
            logMessage("****att" + att);
            logMessage(
              "****strStatus:"
                + ei.getEntityType()
                + "-"
                + ei.getEntityID()
                + ":"
                + strStatus);
            if (strStatus.equals("NOT FOUND")
              || strStatus.equals("100")
              || strStatus.equals("110")
              || strStatus.equals("130")) {
              println("<br /><br /><b>The value for the following attribute is incorrect: STANDARDAMENDTEXT_TYPE</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
          }
        } //checkSTANDTEXTstatus()
      }
    }
  }

  /**
     * checkFEATUREstatus
     *
     * @author Administrator
     */
  public void checkFEATUREstatus() {
    EntityGroup _eg = m_elist.getEntityGroup("FEATURE");

    if (_eg != null) {

      if (_eg.getEntityItemCount() == 0) {
        println(
          "<br /><br /><b>Entity: " + _eg.getLongDescription() + "</b>");
        println("<br /><br /><b>There is no value for: MKTGNAME</b>");
        println("<br /><br /><b>There is no value for: OVERVIEWABSTRACT</b>");
        println("<br /><br /><b>There is no value for: DESCRIPTION</b>");
        setReturnCode(FAIL);
      } else {
        for (int i = 0; i < _eg.getEntityItemCount(); i++) {
          //logMessage("_eg.getEntityItem(i):" + _eg.getEntityItem(i));
          EntityItem ei = _eg.getEntityItem(i);

          logMessage(
            "_eg.getEntityItem(i) for FEATURE:"
              + _eg.getEntityItem(i));
          logMessage("ei for FEATURE:" + ei.dump(true));

          if (ei != null) {
            EANAttribute[] atts = new EANAttribute[3];
            atts[0] = ei.getAttribute("MKTGNAME");
            atts[1] = ei.getAttribute("OVERVIEWABSTRACT");
            atts[2] = ei.getAttribute("DESCRIPTION");
            logMessage("atts[0]:" + atts[0]);
            logMessage("atts[1]:" + atts[1]);
            logMessage("atts[2]:" + atts[2]);
            if (atts[0] == null) {
              println("<br /><br /><b>There is no value for: MKTGNAME</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[1] == null) {
              println("<br /><br /><b>There is no value for: OVERVIEWABSTRACT</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[2] == null) {
              println("<br /><br /><b>There is no value for: DESCRIPTION</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
          }
        }
      }
    }
  } //checkFEATUREstatus

  /**
     * checkPRICEINFOstatus
     *
     * @author Administrator
     */
  public void checkPRICEINFOstatus() {
    EntityGroup _eg = m_elist.getEntityGroup("PRICEFININFO");

    if (_eg != null) {

      logMessage(
        "_eg.getEntityItemCount() for PRICEFININFO:"
          + _eg.getEntityItemCount());
      if (_eg.getEntityItemCount() == 0) {
        println(
          "<br /><br /><b>Entity: " + _eg.getLongDescription() + "</b>");
        println("<br /><br /><b>There is no value for: LPFEE</b>");
        println("<br /><br /><b>There is no value for: CONTRACTCLOSEFEE</b>");
        println("<br /><br /><b>There is no value for: REMKTGDISCOUNT</b>");
        println("<br /><br /><b>There is no value for: CHARGES</b>");
        setReturnCode(FAIL);
      } else {
        for (int i = 0; i < _eg.getEntityItemCount(); i++) {
          //logMessage("_eg.getEntityItem(i):" + _eg.getEntityItem(i));
          EntityItem ei = _eg.getEntityItem(i);

          logMessage(
            "_eg.getEntityItem(i) for PRICEFININFO:"
              + _eg.getEntityItem(i));
          logMessage("ei for PRICEFININFO:" + ei.dump(true));

          if (ei != null) {
            EANAttribute[] atts = new EANAttribute[4];
            atts[0] = ei.getAttribute("LPFEE");
            atts[1] = ei.getAttribute("CONTRACTCLOSEFEE");
            atts[2] = ei.getAttribute("REMKTGDISCOUNT");
            atts[3] = ei.getAttribute("CHARGES");
            logMessage("atts[0]:" + atts[0]);
            logMessage("atts[1]:" + atts[1]);
            logMessage("atts[2]:" + atts[2]);
            logMessage("atts[3]:" + atts[3]);
            if (atts[0] == null) {
              println("<br /><br /><b>There is no value for: LPFEE</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[1] == null) {
              println("<br /><br /><b>There is no value for: CONTRACTCLOSEFEE</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[2] == null) {
              println("<br /><br /><b>There is no value for: REMKTGDISCOUNT</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[3] == null) {
              println("<br /><br /><b>There is no value for: CHARGES</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
          }
        }
      }
    }
  } //checkPRICEINFOstatus

  /**
     * checkANNOUNCEMENTstatus
     *
     * @author Administrator
     */
  public void checkANNOUNCEMENTstatus() {
    EntityGroup _eg = m_elist.getEntityGroup("ANNOUNCEMENT");
    if (_eg != null) {

      logMessage(
        "_eg.getEntityItemCount() for ANNOUNCEMENT:"
          + _eg.getEntityItemCount());
      if (_eg.getEntityItemCount() == 0) {
        println(
          "<br /><br /><b>Entity: " + _eg.getLongDescription() + "</b>");
        println("<br /><br /><b>There is no value for: ANNIMAGES</b>");
        println("<br /><br /><b>There is no value for: ANNTITLE</b>");
        println("<br /><br /><b>There is no value for: USSEC508</b>");
        println("<br /><br /><b>There is no value for: USSEC508LOGO</b>");
        println("<br /><br /><b>There is no value for: OVERVIEWABSTRACT</b>");
        println("<br /><br /><b>There is no value for: ATAGLANCE</b>");
        println("<br /><br /><b>There is no value for: DESCRIPTION</b>");
        println("<br /><br /><b>There is no value for: AMCALLCENTER</b>");
        setReturnCode(FAIL);
      } else {
        for (int i = 0; i < _eg.getEntityItemCount(); i++) {
          //logMessage("_eg.getEntityItem(i):" + _eg.getEntityItem(i));
          EntityItem ei = _eg.getEntityItem(i);

          logMessage(
            "_eg.getEntityItem(i) for ANNOUNCEMENT:"
              + _eg.getEntityItem(i));
          logMessage("ei for ANNOUNCEMENT:" + ei.dump(true));

          if (ei != null) {
            EANAttribute[] atts = new EANAttribute[9];
            atts[0] = ei.getAttribute("ANNIMAGES");
            atts[1] = ei.getAttribute("ANNTITLE");
            atts[2] = ei.getAttribute("USSEC508");
            atts[3] = ei.getAttribute("USSEC508LOGO");
            atts[4] = ei.getAttribute("OVERVIEWABSTRACT");
            atts[5] = ei.getAttribute("ATAGLANCE");
            atts[6] = ei.getAttribute("DESCRIPTION");
            atts[7] = ei.getAttribute("AMCALLCENTER");
            atts[8] = ei.getAttribute("PARAMETERCODENAME");

            logMessage("atts[0]:" + atts[0]);
            logMessage("atts[1]:" + atts[1]);
            logMessage("atts[2]:" + atts[2]);
            logMessage("atts[3]:" + atts[3]);
            logMessage("atts[4]:" + atts[4]);
            logMessage("atts[5]:" + atts[5]);
            logMessage("atts[6]:" + atts[6]);
            logMessage("atts[7]:" + atts[7]);
            logMessage("atts[8]:" + atts[8]);
            if (atts[0] == null) {
              println("<br /><br /><b>There is no value for: ANNIMAGES</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[1] == null) {
              println("<br /><br /><b>There is no value for: ANNTITLE</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[2] == null) {
              println("<br /><br /><b>There is no value for: USSEC508</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[3] == null) {
              println("<br /><br /><b>There is no value for: USSEC508LOGO</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[4] == null) {
              println("<br /><br /><b>There is no value for: OVERVIEWABSTRACT</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[5] == null) {
              println("<br /><br /><b>There is no value for: ATAGLANCE</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[6] == null) {
              println("<br /><br /><b>There is no value for: DESCRIPTION</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[7] == null) {
              println("<br /><br /><b>There is no value for: AMCALLCENTER</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
          }
        }
      }
    }
  } //checkANNOUNCEMENTstatus

  /**
     * checkCONFIGAVAILDATEstatus
     *
     * @author Administrator
     */
  public void checkCONFIGAVAILDATEstatus() {
    EntityGroup _eg = m_elist.getEntityGroup("ANNTOCONFIG");
    if (_eg != null) {

      if (_eg.getEntityItemCount() == 0) {
        println(
          "<br /><br /><b>Entity: " + _eg.getLongDescription() + "</b>");
        println("<br /><br /><b>There is no value for: ANNTOCONFIG</b>");
        setReturnCode(FAIL);
      } else {

        logMessage(
          "****_eg.getEntityItemCount() for ANNTOCONFIG:"
            + _eg.getEntityItemCount());
        for (int i = 0; i < _eg.getEntityItemCount(); i++) {
          EntityItem ei = _eg.getEntityItem(i);
          if (ei != null) {
            EANAttribute att = ei.getAttribute("CONFIGAVAILDATE");
            logMessage("****att for ANNTOCONFIG:" + att);
            if (att == null) {
              println("<br /><br /><b>There is no value for: CONFIGAVAILDATE</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
          }
        }
      }
    }
  } //checkCONFIGAVAILDATEstatus

  /**
     * checkCHANNELstatus
     *
     * @author Administrator
     */
  public void checkCHANNELstatus() {
    EntityGroup _eg = m_elist.getEntityGroup("CHANNEL");
    if (_eg != null) {

      logMessage(
        "_eg.getEntityItemCount() for CHANNEL:"
          + _eg.getEntityItemCount());
      if (_eg.getEntityItemCount() == 0) {
        println(
          "<br /><br /><b>Entity: " + _eg.getLongDescription() + "</b>");
        println("<br /><br /><b>There is no value for: GENAREASELECTION</b>");
        println("<br /><br /><b>There is no value for: COUNTRYLIST</b>");
        println("<br /><br /><b>There is no value for: CHANNELNAME</b>");
        setReturnCode(FAIL);
      } else {
        for (int i = 0; i < _eg.getEntityItemCount(); i++) {

          EntityItem ei = _eg.getEntityItem(i);

          logMessage("ei for CHANNEL:" + ei.dump(true));

          if (ei != null) {
            EANAttribute[] atts = new EANAttribute[3];
            atts[0] = ei.getAttribute("GENAREASELECTION");
            atts[1] = ei.getAttribute("COUNTRYLIST");
            atts[2] = ei.getAttribute("CHANNELNAME");

            logMessage("atts[0] for CHANNEL:" + atts[0]);
            logMessage("atts[1] for CHANNEL:" + atts[1]);
            logMessage("atts[2] for CHANNEL:" + atts[2]);
            if (atts[0] == null) {
              println("<br /><br /><b>There is no value for: GENAREASELECTION</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[1] == null) {
              println("<br /><br /><b>There is no value for: COUNTRYLIST</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[2] == null) {
              println("<br /><br /><b>There is no value for: CHANNELNAME</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
          }
        }
      }
    }
  } //checkCHANNELstatus

  /**
     * checkCOUNTRYLISTstatus
     *
     * @author Administrator
     */
  public void checkCOUNTRYLISTstatus() {
    EntityGroup _eg = m_elist.getEntityGroup("ANNOUNCEMENT");
    if (_eg != null) {

      if (_eg.getEntityItemCount() == 0) {
        println(
          "<br /><br /><b>Entity: " + _eg.getLongDescription() + "</b>");
        println("<br /><br /><b>There is no value for: ANNOUNCEMENT</b>");
        setReturnCode(FAIL);
      } else {

        logMessage(
          "****_eg.getEntityItemCount() for ANNOUNCEMENT:"
            + _eg.getEntityItemCount());
        for (int i = 0; i < _eg.getEntityItemCount(); i++) {
          EntityItem ei = _eg.getEntityItem(i);
          if (ei != null) {
            EANAttribute att = ei.getAttribute("COUNTRYLIST");
            logMessage("****att for ANNOUNCEMENT:" + att);
            if (att == null) {
              println("<br /><br /><b>There is no value for: COUNTRYLIST</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
          }
        }
      }
    }
  } //checkCOUNTRYLISTstatus

  /**
     * checkOPstatus
     *
     * @author Administrator
     */
  public void checkOPstatus() {
    EntityGroup _eg = m_elist.getEntityGroup("OP");
    if (_eg != null) {

      logMessage(
        "_eg.getEntityItemCount() for OP:" + _eg.getEntityItemCount());
      if (_eg.getEntityItemCount() == 0) {
        println(
          "<br /><br /><b>Entity: " + _eg.getLongDescription() + "</b>");
        println("<br /><br /><b>There is no value for: FIRSTNAME</b>");
        println("<br /><br /><b>There is no value for: LASTNAME</b>");
        println("<br /><br /><b>There is no value for: VNETNODE</b>");
        println("<br /><br /><b>There is no value for: VNETUID</b>");
        println("<br /><br /><b>There is no value for: JOBTITLE</b>");
        println("<br /><br /><b>There is no value for: USERNAME</b>");
        println("<br /><br /><b>There is no value for: SITE</b>");
        setReturnCode(FAIL);
      } else {
        for (int i = 0; i < _eg.getEntityItemCount(); i++) {

          EntityItem ei = _eg.getEntityItem(i);

          logMessage("ei for OP:" + ei.dump(true));

          if (ei != null) {
            EANAttribute[] atts = new EANAttribute[7];
            atts[0] = ei.getAttribute("FIRSTNAME");
            atts[1] = ei.getAttribute("LASTNAME");
            atts[2] = ei.getAttribute("VNETNODE");
            atts[3] = ei.getAttribute("VNETUID");
            atts[4] = ei.getAttribute("JOBTITLE");
            atts[5] = ei.getAttribute("USERNAME");
            atts[6] = ei.getAttribute("SITE");

            logMessage("atts[0] for OP:" + atts[0]);
            logMessage("atts[1] for OP:" + atts[1]);
            logMessage("atts[2] for OP:" + atts[2]);
            logMessage("atts[3] for OP:" + atts[3]);
            logMessage("atts[4] for OP:" + atts[4]);
            logMessage("atts[5] for OP:" + atts[5]);
            logMessage("atts[6] for OP:" + atts[6]);
            if (atts[0] == null) {
              println("<br /><br /><b>There is no value for: FIRSTNAME</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[1] == null) {
              println("<br /><br /><b>There is no value for: LASTNAME</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[2] == null) {
              println("<br /><br /><b>There is no value for: VNETNODE</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[3] == null) {
              println("<br /><br /><b>There is no value for: VNETUID</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[4] == null) {
              println("<br /><br /><b>There is no value for: JOBTITLE</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[5] == null) {
              println("<br /><br /><b>There is no value for: SITE</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[6] == null) {
              println("<br /><br /><b>There is no value for: JOBTITLE</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
          }
        }
      }
    }
  } //checkOPstatus

  /**
     * checkCONFIGNAMEstatus
     *
     * @author Administrator
     */
  public void checkCONFIGNAMEstatus() {
    EntityGroup _eg = m_elist.getEntityGroup("CONFIGURATOR");
    if (_eg != null) {

      if (_eg.getEntityItemCount() == 0) {
        println(
          "<br /><br /><b>Entity: " + _eg.getLongDescription() + "</b>");
        println("<br /><br /><b>There is no value for: CONFIGNAME</b>");
        setReturnCode(FAIL);
      } else {

        logMessage(
          "****_eg.getEntityItemCount() for CONFIGNAME:"
            + _eg.getEntityItemCount());
        for (int i = 0; i < _eg.getEntityItemCount(); i++) {
          EntityItem ei = _eg.getEntityItem(i);
          if (ei != null) {
            EANAttribute att = ei.getAttribute("COUNTRYLIST");
            logMessage("****att for CONFIGNAME:" + att);
            if (att == null) {
              println("<br /><br /><b>There is no value for: COUNTRYLIST</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
          }
        }
      }
    }
  } //checkCONFIGNAMEstatus

  /**
     * CHECKORGANUNITSTATUS
     *
     * @author Administrator
     */
  public void checkORGANUNITstatus() {
    EntityGroup _eg = m_elist.getEntityGroup("ORGANUNIT");
    if (_eg != null) {

      logMessage(
        "_eg.getEntityItemCount() for OP:" + _eg.getEntityItemCount());
      if (_eg.getEntityItemCount() == 0) {
        println(
          "<br /><br /><b>Entity: " + _eg.getLongDescription() + "</b>");
        println("<br /><br /><b>There is no value for: NAME</b>");
        println("<br /><br /><b>There is no value for: INITIALS</b>");
        println("<br /><br /><b>There is no value for: STREETADDRESS</b>");
        println("<br /><br /><b>There is no value for: CITY</b>");
        println("<br /><br /><b>There is no value for: STATE</b>");
        println("<br /><br /><b>There is no value for: COUNTRY</b>");
        println("<br /><br /><b>There is no value for: ZIPCODE</b>");
        setReturnCode(FAIL);
      } else {
        for (int i = 0; i < _eg.getEntityItemCount(); i++) {

          EntityItem ei = _eg.getEntityItem(i);

          logMessage("ei for OP:" + ei.dump(true));

          if (ei != null) {
            EANAttribute[] atts = new EANAttribute[7];
            atts[0] = ei.getAttribute("NAME");
            atts[1] = ei.getAttribute("INITIALS");
            atts[2] = ei.getAttribute("STREETADDRESS");
            atts[3] = ei.getAttribute("CITY");
            atts[4] = ei.getAttribute("STATE");
            atts[5] = ei.getAttribute("COUNTRY");
            atts[6] = ei.getAttribute("ZIPCODE");

            logMessage("atts[0] for ORGANUNIT:" + atts[0]);
            logMessage("atts[1] for ORGANUNIT:" + atts[1]);
            logMessage("atts[2] for ORGANUNIT:" + atts[2]);
            logMessage("atts[3] for ORGANUNIT:" + atts[3]);
            logMessage("atts[4] for ORGANUNIT:" + atts[4]);
            logMessage("atts[5] for ORGANUNIT:" + atts[5]);
            logMessage("atts[6] for ORGANUNIT:" + atts[6]);
            if (atts[0] == null) {
              println("<br /><br /><b>There is no value for: NAME</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[1] == null) {
              println("<br /><br /><b>There is no value for: INITIALS</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[2] == null) {
              println("<br /><br /><b>There is no value for: STREETADDRESS</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[3] == null) {
              println("<br /><br /><b>There is no value for: CITY</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[4] == null) {
              println("<br /><br /><b>There is no value for: STATE</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[5] == null) {
              println("<br /><br /><b>There is no value for: COUNTRY</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
            if (atts[6] == null) {
              println("<br /><br /><b>There is no value for: ZIPCODE</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ":"
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
          }
        }
      }
    }
  } //checkORGANUNITstatus

  /**
     * CheckSUBJECTLINE
     *
     * @author Administrator
     */
  public void checkSUBJECTLINE_1status() {
    EntityGroup _eg = m_elist.getEntityGroup("ANNDELIVERABLE");
    if (_eg != null) {

      if (_eg.getEntityItemCount() == 0) {
        println(
          "<br /><br /><b>Entity: " + _eg.getLongDescription() + "</b>");
        println("<br /><br /><b>There is no value for: SUBJECTLINE_1</b>");
        setReturnCode(FAIL);
      } else {

        logMessage(
          "****_eg.getEntityItemCount() for ANNDELIVERABLE:"
            + _eg.getEntityItemCount());
        for (int i = 0; i < _eg.getEntityItemCount(); i++) {
          EntityItem ei = _eg.getEntityItem(i);
          if (ei != null) {
            EANAttribute att = ei.getAttribute("SUBJECTLINE_1");
            logMessage("****att for SUBJECTLINE_1:" + att);
            if (att == null) {
              println("<br /><br /><b>There is no value for: SUBJECTLINE_1</b>");
              println(
                "<br /><br /><b>"
                  + ei.getEntityType()
                  + ei.getEntityID()
                  + "</b>");
              //println(displayAttributes(ei, _eg, true));
			  println(dispNavAttributes(ei, _eg));
              setReturnCode(FAIL);
            }
          }
        }
      }
    }
  } //checkSUBJECTLINE_1status

  /**
   *  Sets the controlBlock attribute of the ANQRFRABR01_IGSSVS object
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
   *  Sets the flagValue attribute of the ANQRFRABR01_IGSSVS object
   *   
   *@param  _sAttributeCode  The new flagValue value
   */
  public void setFlagValue(    
    String _sAttributeCode) {

    String strAttributeValue = null;

    if (_sAttributeCode.equals(ANCYCLESTATUS)) {
      logMessage(
        "****** Announcement Lifecycle Status set to: "
          + m_strReadyFinalReviewValue);
      strAttributeValue = m_strReadyFinalReviewValue;

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
     * setFlagValue1
     *
     * @param _sAttributeCode
     * @author Administrator
     */
  public void setFlagValue1(    
    String _sAttributeCode) {

    String strAttributeValue1 = null;

    if (_sAttributeCode.equals(ANCYCLESTATUS)) {
      logMessage(
        "****** Announcement Lifecycle Status set to: "
          + m_strApprovedValue);
      strAttributeValue1 = m_strApprovedValue;

    } else {

      strAttributeValue1 = null;
    }

    if (strAttributeValue1 != null) {
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
            strAttributeValue1,
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
        logMessage("setFlagValue1: " + e.getMessage());
      } catch (Exception e) {
        logMessage("setFlagValue1: " + e.getMessage());
      }
    }
  }

  /**
     * setFlagValue2
     *
     * @param _sAttributeCode
     * @author Administrator
     */
  public void setFlagValue2(
    String _sAttributeCode) {

    String strAttributeValue2 = null;

    if (_sAttributeCode.equals(ANCYCLESTATUS)) {
      logMessage(
        "****** Announcement Lifecycle Status set to: "
          + m_strApprovedRiskValue);
      strAttributeValue2 = m_strApprovedRiskValue;

    } else {

      strAttributeValue2 = null;
    }

    if (strAttributeValue2 != null) {
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
            strAttributeValue2,
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
        logMessage("setFlagValue2: " + e.getMessage());
      } catch (Exception e) {
        logMessage("setFlagValue2: " + e.getMessage());
      }
    }
  }

  /**
     * setFlagValue2
     *
     * @param _sAttributeCode
     * @author Administrator
     */
  public void setFlagValue3(
    String _sAttributeCode) {

    String strAttributeValue3 = null;

    if (_sAttributeCode.equals(RFAHWABR01_STATUS)) {
      logMessage(
        "****** Announcement Lifecycle Status set to: "
          + m_strQueueRFAValue);
      strAttributeValue3 = m_strQueueRFAValue;

    } else {

      strAttributeValue3 = null;
    }

    if (strAttributeValue3 != null) {
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
            strAttributeValue3,
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
        logMessage("setFlagValue3: " + e.getMessage());
      } catch (Exception e) {
        logMessage("setFlagValue3: " + e.getMessage());
      }
    }
  }

  /**
   * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABREntityDesc(String, int)
   * @author Administrator
   */
  protected String getABREntityDesc(String entityType, int entityId) {
    return null;
  }

  /**
   * @see COM.ibm.eannounce.abr.util.PokBaseABR#getDescription()
   * @author Administrator
   */
  public String getDescription() {
    //return Constants.IAB3053I + "<br /><br />" + Constants.IAB3050I;
    return "<br />This needs to be defined <br />";
  }

  /**
   * @see COM.ibm.eannounce.abr.util.PokBaseABR#getStyle()
   * @author Administrator
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
    return "1.20";
  }

  /**
     * getVersion
     *
     * @return
     * @author Administrator
     */
  public static String getVersion() {
    return ("ANQRFRABR01_IGSSVS.java,v 1.20 2008/03/19 19:40:51 wendy Exp");
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
	  	println(EACustom.getMetaTags("ANQRFRABR01_IGSSVS.java"));
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
	
	/**
	* This is a generic display header - This will now include status info as well.
	*/
	private void dispHeader(EntityGroup _eg, EntityItem _ei) {
	   if(_eg != null && _eg != null) {
		  println("<h1><b>" + _eg.getLongDescription() + "</b></h1><br />");
		  println(displayStatus(_ei, _eg));
	  	  println(dispNavAttributes(_ei, _eg));
	   }
	}
	
	private String dispNavAttributes(EntityItem _ei, EntityGroup _eg){
	   StringBuffer strBuffer = new StringBuffer();
	   strBuffer.append("<br /><br /><table summary=\"layout\" width=\"100%\">" + NEW_LINE);
	   strBuffer.append("<tr><td class=\"PsgLabel\" width=\"35%\">Navigation Attribute</td><td class=\"PsgLabel\" width=\"65%\">Value</td></tr>");
	   for(int i=0;i < _eg.getMetaAttributeCount();i++){
		  EANMetaAttribute ma = _eg.getMetaAttribute(i);
		  EANAttribute att = _ei.getAttribute(ma.getAttributeCode());
		  if(ma.isNavigate()) {
			 strBuffer.append("<tr><td class=\"PsgText\" width=\"35%\">"+ ma.getLongDescription()+"</td><td class=\"PsgText\" width=\"65%\">" +(att == null || att.toString().length() ==0 ? DEF_NOT_POPULATED_HTML : att.toString()) + "</td></tr>");
		  }
	   }
	 strBuffer.append("</table>");
	   return strBuffer.toString();
   }
   
   private String displayStatus(EntityItem _ei, EntityGroup _eg) {
	  StringBuffer strBuffer = new StringBuffer();
	  strBuffer.append("<br /><br /><table summary=\"layout\" width=\"100%\">" + NEW_LINE);
	  strBuffer.append("<tr><td class=\"PsgLabel\" width=\"35%\">Status Attribute</td><td class=\"PsgLabel\" width=\"65%\">Value</td></tr>");
	  for(int i=0;i < _eg.getMetaAttributeCount();i++){
		 EANMetaAttribute ma = _eg.getMetaAttribute(i);
		 EANAttribute att = _ei.getAttribute(ma.getAttributeCode());
		 if(ma instanceof MetaStatusAttribute) {
			strBuffer.append("<tr><td class=\"PsgText\" width=\"35%\">"+ ma.getLongDescription()+"</td><td class=\"PsgText\" width=\"65%\">" +(att == null || att.toString().length() ==0 ? DEF_NOT_POPULATED_HTML : att.toString()) + "</td></tr>");
		 }
	  }
	strBuffer.append("</table>");
	  return strBuffer.toString();
  }

}
