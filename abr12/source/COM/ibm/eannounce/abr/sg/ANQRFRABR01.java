//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//ANQRFRABR01.java,v
//Revision 1.28  2008/01/30 19:39:14  wendy
//Cleanup RSA warnings
//
//Revision 1.27  2006/03/03 19:23:26  bala
//remove reference to Constants.CSS
//
//Revision 1.26  2006/01/24 17:03:28  yang
//Jtest Changes
//
//Revision 1.25  2005/02/08 18:29:14  joan
//changes for Jtest
//
//Revision 1.24  2003/11/07 18:43:41  yang
//Add SetFlagValue()
//
//Revision 1.22  2003/09/18 19:50:44  yang
//adding bala's stuff to finally {
//
//Revision 1.21  2003/06/12 20:55:23  minhthy
//remove PDSQUESTIONS from hashtable
//
//Revision 1.20  2003/06/12 20:52:01  minhthy
//fixed checkANNStatus()
//
//Revision 1.19  2003/06/12 20:03:58  minhthy
//updated checkFinalStatus()
//
//Revision 1.18  2003/06/12 18:26:32  minhthy
//remove 'OPSYS' from c_hshEntities
//
//Revision 1.17  2003/06/12 17:28:35  minhthy
//*** empty log message ***
//
//Revision 1.16  2003/06/12 17:19:49  minhthy
//minor changes
//
//Revision 1.15  2003/06/12 16:08:14  minhthy
//minor error
//
//Revision 1.14  2003/06/12 15:53:50  minhthy
//updated checkANNstatus()
//
//Revision 1.13  2003/06/12 15:06:53  minhthy
//syntax
//
//Revision 1.12  2003/06/11 21:12:48  minhthy
//fixed checkFinalStatus()
//
//Revision 1.11  2003/06/11 21:02:35  minhthy
//minor error
//
//Revision 1.10  2003/06/11 19:50:38  minhthy
//syntax error
//
//Revision 1.9  2003/06/11 19:31:40  minhthy
//put more entities to c_hshEntities
//
//Revision 1.8  2003/06/11 17:35:41  minhthy
//updated new version
//
//Revision 1.7  2003/06/04 03:53:08  dave
//un Staticing getABRVersion
//
//Revision 1.6  2003/06/04 03:44:24  dave
//minor syntax
//
//Revision 1.5  2003/06/04 03:41:43  dave
//adding getABRVersion
//
//Revision 1.4  2003/06/04 01:45:47  dave
//more commonizing on routines
//
//Revision 1.3  2003/06/03 23:28:33  dave
//commonizing setControlBlock
//
//Revision 1.2  2003/06/03 19:33:58  dave
//more consolidation
//
//Revision 1.1.1.1  2003/06/03 19:02:24  dave
//new 1.1.1 abr
//
//Revision 1.29  2003/06/03 18:17:36  dave
//preping for common T1 and T2 processing
//
//Revision 1.28  2003/06/02 19:44:08  dave
//cleared up the message more
//
//Revision 1.27  2003/06/02 19:43:09  dave
//syntax fix
//
//Revision 1.26  2003/06/02 19:39:36  dave
//clearer messages on warning
//
//Revision 1.25  2003/06/02 19:38:29  dave
//minor adjustments
//
//Revision 1.24  2003/05/23 18:20:11  naomi
//take out PDSQUESTIONS cheking
//
//Revision 1.23  2003/05/22 19:59:51  naomi
//fixed output message
//
//Revision 1.22  2003/05/22 16:19:24  naomi
//check in
//

package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.*;
//import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
import java.util.*;
//import java.io.*;
//import java.sql.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Administrator
 */
public class ANQRFRABR01 extends PokBaseABR {
  /**
  *  Execute ABR.
  */

  //ABR
  public final static String ABR = new String("ANQRFRABR01");
  /**
     * CHANGEREQUEST_STATUS
     *
     */
  public final static String CHANGEREQUEST_STATUS = new String("CRSTATUS");
  /**
     * ANNOUNCEMENT
     *
     */
  public final static String ANNOUNCEMNET = new String("ANNOUNCEMNET");
  /**
     * ANCYCLESTATUS
     *
     */
  public final static String ANCYCLESTATUS = new String("ANCYCLESTATUS");
  /**
     * RFAHWABR01_STATUS
     *
     */
  public final static String RFAHWABR01_STATUS = new String("RFAHWABR01");

  /**
     * c_hshEntities
     *
     */
  public static final Hashtable c_hshEntities = new Hashtable();
  /**
     * c_hshANNtoANN
     *
     */
  public static final Hashtable c_hshANNtoANN = new Hashtable();

  static {

    c_hshEntities.put("ANNAREAMKTINFO", "HI");
    c_hshEntities.put("ANNDELIVERABLE", "HI");
    c_hshEntities.put("CONFIGURATOR", "HI");
    c_hshEntities.put("EDUCATION", "HI");
    c_hshEntities.put("OP", "HI");
    c_hshEntities.put("ORGANUNIT", "HI");
    //c_hshEntities.put("PDSQUESTIONS", "HI");
    c_hshEntities.put("SALESMANCHG", "HI");
    c_hshEntities.put("AVAIL", "HI");

    c_hshEntities.put("BPEXHIBIT", "HI");
    c_hshEntities.put("COFOOFMGMTGRP", "HI");
    c_hshEntities.put("CRYPTO", "HI");
    c_hshEntities.put("ENVIRINFO", "HI");
    c_hshEntities.put("FUP", "HI");
    c_hshEntities.put("OOFOOFMGMTGRP", "HI");
    c_hshEntities.put("ORDERINFO", "HI");
    c_hshEntities.put("STANDAMENDTEXT", "HI");
    c_hshEntities.put("ORDEROF", "HI");
    c_hshEntities.put("PACKAGING", "HI");
    c_hshEntities.put("RULES", "HI");
    c_hshEntities.put("SHIPINFO", "HI");
    c_hshEntities.put("PRICEFININFO", "HI");
    c_hshEntities.put("CATINCL", "HI");
    c_hshEntities.put("CHANNEL", "HI");
    c_hshEntities.put("COFCOFMGMTGRP", "HI");
    c_hshEntities.put("COMMERCIALOF", "HI");
    c_hshEntities.put("IVOCAT", "HI");
    c_hshEntities.put("ORDERINFO", "HI");
    c_hshEntities.put("PUBLICATION", "HI");
    c_hshEntities.put("PUBTABLE", "PUBTABLE");
    c_hshEntities.put("TECHCAPABILITY", "HI");
    c_hshEntities.put("ORDEROF", "HI");
    //c_hshEntities.put("OPSYS", "HI");
    c_hshEntities.put("FUPFUPMGMTGRP", "HI");
    c_hshEntities.put("FUPPOFMGMTGRP", "HI");
    c_hshEntities.put("PHYSICALOF", "HI");
    c_hshEntities.put("POFPOFMGMTGRP", "HI");
    c_hshEntities.put("ENGCHANGE", "HI");

    c_hshANNtoANN.put("ANNOUNCEMENT", "ANNOUNCEMENT");
    //ROW4 , //ROW5, //ROW6

  }

  private EntityGroup m_egParent = null; //root POFPOFMGMTGRP
  private EntityItem m_eiParent = null; //root POFPOFMGMTGRP

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
    LockActionItem lai = null;
    String m_strParentAnnCodeNameValue = null;
    String strFlagValue = null;
    try {

      start_ABRBuild();
      //Get the root POFPOFMGMTGRP
      m_egParent = m_elist.getParentEntityGroup();
      m_eiParent = m_egParent.getEntityItem(0);

      if (m_egParent == null) {
        println(
          ABR
            + ":"
            + getVersion()
            + ":ERROR:1: m_egParent cannot be established.");
        setReturnCode(FAIL);
        return;
      }
      if (m_eiParent == null) {
        println(
          ABR
            + ":"
            + getVersion()
            + ":ERROR:2: m_eiParent cannot be established.");
        setReturnCode(FAIL);
        return;
      }

      logMessage(
        ABR
          + ":"
          + getVersion()
          + ":Request to Work on Entity:"
          + m_eiParent.getEntityType()
          + ":"
          + m_eiParent.getEntityID());
      buildReportHeader();
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

      displayHeader(m_egParent, m_eiParent);

      checkFinalStatus();
      checkANNStatus();
      checkCRstatus();

      if (getReturnCode() == PASS) {

        String strFlagCode =
          getFlagCode(m_eiParent, getStatusAttributeCode(m_eiParent));
        logMessage(
          "****strFlagCode****"
            + strFlagCode
            + m_eiParent
            + getStatusAttributeCode(m_eiParent));
        if (strFlagCode.equals("111")) {
          setFlagValue(ANNOUNCEMNET, getEntityID(), ANCYCLESTATUS);
          //Draft
        } else if (strFlagCode.equals("113")) {
          setFlagValue(ANNOUNCEMNET, getEntityID(), ANCYCLESTATUS);
          //Change (Final Review)
        } else if (strFlagCode.equals("118")) {
          setFlagValue1(ANNOUNCEMNET, getEntityID(), ANCYCLESTATUS);
          //Change (Approved)
        } else if (strFlagCode.equals("119")) {
          setFlagValue2(ANNOUNCEMNET, getEntityID(), ANCYCLESTATUS);
          //Change (Approved w/Risk)
        } else if (strFlagCode.equals("120")) {
          setFlagValue3(
            ANNOUNCEMNET,
            getEntityID(),
            RFAHWABR01_STATUS);
          //Change (Released)
        } else if (strFlagCode.equals("121")) {
          setFlagValue3(
            ANNOUNCEMNET,
            getEntityID(),
            RFAHWABR01_STATUS);
          //Change (Announced)
        }

        //setVERock
        lai =
          new LockActionItem(null, m_db, m_prof, "LOCKEXTANN01LOCK1");
        EntityItem[] aei = { m_eiParent };
        lai.setEntityItems(aei);
        lai.executeAction(m_db, m_prof);

        //take the value of ANNCODENAME and set the value to out of circulation
        //if the value of ANNCODENAME is already set to out of circulation, do nothing

        EANAttribute att = m_eiParent.getAttribute("ANNCODENAME");
        m_strParentAnnCodeNameValue =
          (att != null ? att.toString() : DEF_NOT_POPULATED_HTML);
        logMessage(
          "***** m_strParentAnnCodeNameValue is "
            + m_strParentAnnCodeNameValue);

        MetaFlag[] amf = (MetaFlag[]) att.get();
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

    } catch (LockPDHEntityException le) {
      setReturnCode(UPDATE_ERROR);
      println(
        "<h3><font color=red>"
          + ERR_IAB1007E
          + "<br />"
          + le.getMessage()
          + "</font></h3>");
      logError(le.getMessage());
    } catch (UpdatePDHEntityException le) {
      setReturnCode(UPDATE_ERROR);
      println(
        "<h3><font color=red>UpdatePDH error: "
          + le.getMessage()
          + "</font></h3>");
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
      if (getReturnCode() == PASS) {
        setReturnCode(PASS);
      }
      // set DG submit string
      setDGString(getABRReturnCode());
      setDGRptName("ANQRFRABR01"); //Set the report name
      setDGRptClass("ANQRFRABR01"); //Set the report class
      printDGSubmitString();
      //Stuff into report for subscription and notification
      // Tack on the DGString
      buildReportFooter();
      // make sure the lock is released
      if (!isReadOnly()) {
        clearSoftLock();
      }
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
      "<br><br><b>" + _eg.getLongDescription() + " does not exist</b>");
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
      "<br><br><b>"
        + _eg.getLongDescription()
        + " does not exist (Warning - Pass)</b>");

  }

  /**
     * printeFAILmessage2
     *
     * @param _eg
     * @author Administrator
     */
  public void printFAILmessage_2(EntityGroup _eg) {
    println(
      "<br><br><b>"
        + _eg.getLongDescription()
        + " needs to be 'Final'status</b>");
    setReturnCode(FAIL);

  }
  /**
     * printWARNINGmessage
     *
     * @param _eg
     * @author Administrator
     */
  public void printWARNINGmessage_2(EntityGroup _eg) {
    println(
      "<br><br><b>"
        + _eg.getLongDescription()
        + " needs to be 'Final'status (Warning - Pass)</b>");

  }

  /**
     * printFAILmessage3
     *
     * @param _eg
     * @author Administrator
     */
  public void printFAILmessage_3(EntityGroup _eg) {
    println(
      "<br><br><b>"
        + _eg.getLongDescription()
        + " needs to be at least 'Ready for Final Review'</b>");
    setReturnCode(FAIL);

  }
  /**
     * printWARNINGmessage3
     *
     * @param _eg
     * @author Administrator
     */
  public void printWARNINGmessage_3(EntityGroup _eg) {
    println(
      "<br><br><b>"
        + _eg.getLongDescription()
        + " needs to be at least 'Ready for Final Review' (Warning - Pass)</b>");

  }

  /**
     * checkANNStatus
     *
     * @author Administrator
     */
  public void checkANNStatus() {
    boolean bANNWITHDRAWSANN = false;
    boolean bANNDERIVESFROMANN = false;
    boolean bRELATEDANN = false;
    EntityItem eiChild = null;

    for (int iD = 0; iD < m_eiParent.getDownLinkCount(); iD++) {
      EntityItem eiRelator = (EntityItem) m_eiParent.getDownLink(iD);
      EntityGroup egRelator = eiRelator.getEntityGroup();

      if (eiRelator == null) {
        println(
          "<br><br><b>We have downlink to an non-existant relator... from "
            + m_eiParent.getKey()
            + "</b>");
      } else {

        // if dont have these relator exist , will print FAIL/WARINING message
        if (egRelator.getEntityType().equals("ANNWITHDRAWSANN")) {
          bANNWITHDRAWSANN = true;
        } else if (
          egRelator.getEntityType().equals("ANNDERIVESFROMANN")) {
          bANNDERIVESFROMANN = true;
        } else if (egRelator.getEntityType().equals("RELATEDANN")) {
          bRELATEDANN = true;
        }

        eiChild = (EntityItem) eiRelator.getDownLink(0);
        if (eiChild == null) {
          println(
            "<br><br><b>We have a Relator that points to nowhere..."
              + eiRelator.getKey()
              + "</b>");
        } else {

          if (c_hshANNtoANN.containsKey(eiChild.getEntityType())) {
            if (isStatusableEntity(eiChild)) {
              EANAttribute att =
                eiChild.getAttribute(
                  getStatusAttributeCode(eiChild));
              String strStatusValue =
                (att != null
                  ? att.toString()
                  : DEF_NOT_POPULATED_HTML);

              if (!isANNStatusOK(strStatusValue)) {
                EntityGroup egChild = eiChild.getEntityGroup();

                if (eiRelator
                  .getEntityType()
                  .equals("ANNWITHDRAWSANN")) {
                  printFAILmessage_3(egRelator);

                } else {
                  printWARNINGmessage_3(egRelator);
                }
                println(displayNavAttributes(eiChild, egChild));
                println(displayStatuses(eiChild, egChild));
              }

            }

          }
        }

      }

    }
    if (!bANNWITHDRAWSANN) {
      EntityGroup _eg = m_elist.getEntityGroup("ANNWITHDRAWSANN");
      logMessage("************we are getting here ANNWITHDRAWSANN" + _eg);
      printWARNINGmessage(_eg);
    }
    if (!bANNDERIVESFROMANN) {
      EntityGroup _eg = m_elist.getEntityGroup("ANNDERIVESFROMANN");
      printWARNINGmessage(_eg);
    }
    if (!bRELATEDANN) {
      EntityGroup _eg = m_elist.getEntityGroup("RELATEDANN");
      printWARNINGmessage(_eg);
    }

  }

  /**
     * isANNStatusOK
     *
     * @param _sStatus
     * @return
     * @author Administrator
     */
  public boolean isANNStatusOK(String _sStatus) {
    boolean bResult = false;
    logMessage("isANNStatusOK _sStatus " + _sStatus);
    if (_sStatus != null) {
      if (_sStatus.equals("Ready for Final Review")
        || _sStatus.equals("Approved")
        || _sStatus.equals("Approved with Risk")
        || _sStatus.equals("Released to Production Management")
        || _sStatus.equals("Announced")) {
        bResult = true;
      }
    }
    return bResult;
  }

  /**
     * checkFinalStatus
     *
     * @author Administrator
     */
  public void checkFinalStatus() {

    for (int ii = 0; ii < m_elist.getEntityGroupCount(); ii++) {

      EntityGroup _eg = m_elist.getEntityGroup(ii);

      if (c_hshEntities.containsKey(_eg.getEntityType())) {
        Vector vEI = new Vector();

        //IF AN ENTITY EXISTS THEN CHECK STATUS VALUE. Notice: DO NOT check PUBTABLE and ENGCHANGE (dont have status attributecode)
        if (_eg.getEntityItemCount() > 0) {

          for (int i = 0; i < _eg.getEntityItemCount(); i++) {
            EntityItem ei = _eg.getEntityItem(i);
            if (ei != null) {
              if (getStatusAttributeCode(ei) != null) {
                EANAttribute att =
                  ei.getAttribute(getStatusAttributeCode(ei));
                String strStatusValue =
                  (att != null
                    ? att.toString()
                    : DEF_NOT_POPULATED_HTML);
                if (!strStatusValue.equals("Final")) {
                  vEI.addElement(ei);
                }

              }

            }
          }

          if (vEI != null && vEI.size() > 0) {
            EntityItem ei = (EntityItem) vEI.elementAt(0);
            EntityGroup eg = ei.getEntityGroup();

            if (eg.getEntityType().equals("EDUCATION")
              || eg.getEntityType().equals("OP")
              || eg.getEntityType().equals("ORGANUNIT")
              || eg.getEntityType().equals("STANDAMENDTEXT")) {

              printWARNINGmessage_2(eg);

            } else {
              printFAILmessage_2(eg);
            }

            for (int i = 0; i < vEI.size(); i++) {
              EntityItem ei_Result =
                (EntityItem) vEI.elementAt(i);
              if (ei_Result != null) {
                println(displayNavAttributes(ei_Result, eg));
                println(displayStatuses(ei_Result, eg));
              }
            }

          }

        }

        // IF AN ENTITY DOES NOT EXIST , PRINT MESSAGE
        else {
          if (_eg.getEntityType().equals("EDUCATION")
            || _eg.getEntityType().equals("OP")
            || _eg.getEntityType().equals("ORGANUNIT")
            || _eg.getEntityType().equals("STANDAMENDTEXT")) {

            printWARNINGmessage(_eg);

          } else {
            logMessage("*****We are hitting here aswell" + _eg);
            printWARNINGmessage(_eg);

            //if dont have any AVAIL , print more message
            if (_eg.getEntityType().equals("AVAIL")) {
              println("<br><br><b>There needs to be at least one Availability</b>");
            }

          }
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
            logMessage("checkCR " + ei.dump(true));
          }
        }
      }

      if (vEI != null && vEI.size() > 0) {
        EntityItem ei = (EntityItem) vEI.elementAt(0);
        if (ei != null) {
          EntityGroup eg = ei.getEntityGroup();
          if (eg != null) {
            println(
              "<br><br><b>The following "
                + eg.getLongDescription()
                + " are still in the Approved state</b>");
            println(
              "<br><br><b>" + eg.getLongDescription() + "</b>");
            println(displayAttributes(ei, eg, true));
          }
        }
        setReturnCode(FAIL);
      }
    }
  } //checkCR

  /**
   *  Sets the flagValue attribute of the ANQRFRABR01 object
   *
   *@param  _sEntityType     The new flagValue value
   *@param  _iEntityID       The new flagValue value
   *@param  _sAttributeCode  The new flagValue value
   */
  public void setFlagValue(
    String _sEntityType,
    int _iEntityID,
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
     * @param _sEntityType
     * @param _iEntityID
     * @param _sAttributeCode
     * @author Administrator
     */
  public void setFlagValue1(
    String _sEntityType,
    int _iEntityID,
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
     * @param _sEntityType
     * @param _iEntityID
     * @param _sAttributeCode
     * @author Administrator
     */
  public void setFlagValue2(
    String _sEntityType,
    int _iEntityID,
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
     * setFlagValue3
     *
     * @param _sEntityType
     * @param _iEntityID
     * @param _sAttributeCode
     * @author Administrator
     */
  public void setFlagValue3(
    String _sEntityType,
    int _iEntityID,
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
   * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABREntityDesc(java.lang.String, int)
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
    return new String("1.28");
  }

  /**
     * getVersion
     *
     * @return
     * @author Administrator
     */
  public static String getVersion() {
    return ("ANQRFRABR01.java,v 1.28 2008/01/30 19:39:14 wendy Exp");
  }

  /**
   * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABRVersion()
   * @author Administrator
   */
  public String getABRVersion() {
    return getVersion();
  }

}
