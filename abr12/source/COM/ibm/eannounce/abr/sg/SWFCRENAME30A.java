//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//SWFCRENAME30A.java,v
//Revision 1.4  2008/01/30 19:39:16  wendy
//Cleanup RSA warnings
//
//Revision 1.3  2006/03/03 19:23:30  bala
//remove reference to Constants.CSS
//
//Revision 1.2  2006/01/24 17:12:05  yang
//Jtest Changes
//
//Revision 1.1  2005/01/19 00:04:52  bala
//check in
//

package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.*;
//import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
import java.util.*;
import java.io.*;
import java.sql.*;

/**
 * SWFCRENAME30A
 *
 * @author Owner
 */
public class SWFCRENAME30A extends PokBaseABR {
  /**
  *  Execute ABR.
  */
  //ABR
  public final static String ABR = "SWFCRENAME30A";

  /**
     * getVersion
     *
     * @return
     * @author Owner
     */
    public static String getVersion() {
    return ("SWFCRENAME30A.java,v 1.4 2008/01/30 19:39:16 wendy Exp");
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

  private EntityGroup m_egSWFeature = null;
  private EntityItem m_eiSWfeature = null;

  private EntityGroup m_egSWProdstruct = null;
  private EntityItem m_eiSWProdStruct = null;
  private StringBuffer errMessage = new StringBuffer();

  /**
     * execute_run
     *
     * @author Owner
     */
    public void execute_run() {
    String SWFEATURE_FEATURECODE = null;
    String SWPRODSTRUCT_COMNAME = null;
    StringWriter writer;
    String x = null;
    try {

      start_ABRBuild();
      //logMessage(m_elist.dump(false));
      //Get Some Vital information
      m_egSWFeature = m_elist.getParentEntityGroup();
      m_eiSWfeature =
        (m_egSWFeature == null ? null : m_egSWFeature.getEntityItem(0));

      m_egSWProdstruct = m_elist.getEntityGroup("SWPRODSTRUCT");

      if (m_egSWFeature == null) {
        logMessage(
          ABR
            + ":"
            + getVersion()
            + ":ERROR:1: m_egSWFeature cannot be established.");
        setReturnCode(FAIL);
        return;
      }
      if (m_eiSWfeature == null) {
        logMessage(
          ABR
            + ":"
            + getVersion()
            + ":ERROR:2: m_eiSWfeature cannot be established.");
        setReturnCode(FAIL);
        return;
      }
      logMessage(
        ABR
          + ":"
          + getVersion()
          + ":Request to Work on Entity:"
          + m_eiSWfeature.getEntityType()
          + ":"
          + m_eiSWfeature.getEntityID());

      setControlBlock();

      logMessage(
        ABR
          + ":"
          + getVersion()
          + ":Setup Complete:"
          + m_eiSWfeature.getEntityType()
          + ":"
          + m_eiSWfeature.getEntityID());

      setDGTitle(setDGName(m_eiSWfeature, ABR));
      buildReportHeader();

      // Default the thing to pass...
      setReturnCode(PASS);

      SWFEATURE_FEATURECODE =
        getAttributeValue(m_eiSWfeature, "FEATURECODE").length() > 0
            ? getAttributeValue(m_eiSWfeature, "FEATURECODE")
            : "";
      //Check for a dummy feature code in the featurecode attribute
      if (SWFEATURE_FEATURECODE.indexOf("#") >= 0) {
        logMessage(
          ABR
            + ":"
            + getVersion()
            + ":ERROR:3:dummy featurecode EXISTS and not changed to real FC");
        setReturnCode(FAIL);
        return;
      }

      // when it passes , set attribute value and do not print to report

      if (getReturnCode() == PASS) {

        String SWFEATURE_COMNAME =
          getAttributeValue(m_eiSWfeature, "COMNAME").length() > 0
                ? getAttributeValue(m_eiSWfeature, "COMNAME")
                : "";
        SWFEATURE_COMNAME =
          replaceFCDummy(SWFEATURE_FEATURECODE, SWFEATURE_COMNAME);

        setAttrValue(m_eiSWfeature, "COMNAME", SWFEATURE_COMNAME);

        //Now set the values in SWPRODSTRUCT
        logMessage(
          "Checking SWPRODStruct: found "
            + m_egSWProdstruct.getEntityItemCount());
        for (int i = 0;
          i < m_egSWProdstruct.getEntityItemCount();
          i++) {
          m_eiSWProdStruct = m_egSWProdstruct.getEntityItem(i);
          SWPRODSTRUCT_COMNAME =
            getAttributeValue(m_eiSWProdStruct, "COMNAME").length()
                    > 0
                    ? getAttributeValue(m_eiSWProdStruct, "COMNAME")
                    : "";
          SWPRODSTRUCT_COMNAME =
            replaceFCDummy(
              SWFEATURE_FEATURECODE,
              SWPRODSTRUCT_COMNAME);
          setAttrValue(
            m_eiSWProdStruct,
            "COMNAME",
            SWPRODSTRUCT_COMNAME);
        }

        println(
          "<br><br><FONT SIZE=4>"
            + getABRDescription()
            + " has Passed, no report provided");

      }

      println("<br><br>");

      // Print to the report when it fails
      // Then print to the log file
      //

      if (getReturnCode() == FAIL) {
        displayHeader(m_egSWFeature, m_eiSWfeature);
        if (m_eiSWProdStruct != null) {
          println(
            displayStatuses(
              m_eiSWProdStruct,
              m_eiSWProdStruct.getEntityGroup()));
          println(
            displayNavAttributes(
              m_eiSWProdStruct,
              m_eiSWProdStruct.getEntityGroup()));
        }
        println(errMessage.toString());

        println(
          "<BR><br /><b>"
            + buildMessage(
              MSG_IAB2016I,
              new String[] {
                getABRDescription(),
                (getReturnCode() == PASS
                  ? "Passed"
                  : "Failed")})
            + "</b>");
        log(
          buildLogMessage(
            MSG_IAB2016I,
            new String[] {
              getABRDescription(),
              (getReturnCode() == PASS ? "Passed" : "Failed")}));
      }

    } catch (LockPDHEntityException le) {
      setReturnCode(UPDATE_ERROR);
      println(
        "<h3><font color=red>"
          + ERR_IAB1007E
          + "<br />"
          + le.getMessage()
          + "</font></h3>");
      logError(le.getMessage());
      le.printStackTrace();
    } catch (UpdatePDHEntityException le) {
      setReturnCode(UPDATE_ERROR);
      println(
        "<h3><font color=red>UpdatePDH error: "
          + le.getMessage()
          + "</font></h3>");
      logError(le.getMessage());
      le.printStackTrace();
    } catch (Exception exc) {
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
      // set DG submit string
      setDGString(getABRReturnCode());
      setDGRptName("SWFCRENAME30A"); //Set the report name
      setDGRptClass("SWFCRENAME30A"); //Set the report class
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

  private String replaceFCDummy(
    String _strFCString,
    String _strReplaceString) {
    String strReturn = _strReplaceString;
    if (_strReplaceString.indexOf("#") > -1) {
      //           System.out.println("Found dummy");
      String strTemp1 =
        _strReplaceString
        .substring(0, _strReplaceString.indexOf("#"))
        .trim();
      //           System.out.println(strTemp1);
      String strTemp2 =
        _strReplaceString
        .substring(_strReplaceString.indexOf("#"))
        .trim();
      //           System.out.println(strTemp2);
      String strTemp3 = strTemp2.substring(strTemp2.indexOf(" ")).trim();
      //           System.out.println(strTemp3);
      strReturn = strTemp1 + " " + _strFCString + " " + strTemp3;
      //           System.out.println(strReturn);
    } else {
      logMessage(
        m_abri.getABRCode()
          + " Dummy FC not found to replace in "
          + _strReplaceString);
    }
    return strReturn;
  }

  /**
     * setAttrValue
     *
     * @param _ei
     * @param _attributeCode
     * @param _attributeValue
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @author Owner
     */
    public void setAttrValue(
    EntityItem _ei,
    String _attributeCode,
    String _attributeValue)
    throws SQLException, MiddlewareException {
    try {
      ControlBlock cbOn;
      //OPICMList ol;
      ReturnEntityKey rek =
        new ReturnEntityKey(
          _ei.getEntityType(),
          _ei.getEntityID(),
          true);

      Vector vctAtts = new Vector();
      Vector vctReturnsEntityKeys = new Vector();
      Text txtAtt = null;

      EntityGroup eg = _ei.getEntityGroup();
      EANMetaAttribute ma = eg.getMetaAttribute(_attributeCode);

      switch (ma.getAttributeType().charAt(0)) {
            case 'T' :
                if (_attributeValue.length() == 0) {
                    EANAttribute ea = _ei.getAttribute(_attributeCode);
                    if (ea.toString() != null
                        && ea.toString().length() > 0) {
                        ControlBlock cbOff =
                            new ControlBlock(
                                m_strNow,
                                m_strNow,
                m_strNow,
                m_strNow,
                m_prof.getOPWGID(),
                m_prof.getTranID());
                        txtAtt =
                        new Text(
                            m_prof.getEnterprise(),
                            rek.getEntityType(),
                            rek.getEntityID(),
                            _attributeCode,
                            ea.toString(),
              1,
              cbOff);
                    }
                } else {
                    if (_attributeValue.lastIndexOf('-') > 0)
                        _attributeValue =
                        _attributeValue.substring(
                                0,
                                _attributeValue.length() - 1);

                    cbOn =
                    new ControlBlock(
                        m_strNow,
            m_strForever,
            m_strNow,
            m_strForever,
            m_prof.getOPWGID(),
            m_prof.getTranID());
                    txtAtt =
          new Text(
            m_prof.getEnterprise(),
            rek.getEntityType(),
            rek.getEntityID(),
            _attributeCode,
            _attributeValue,
            1,
            cbOn);
                }
                vctAtts.addElement(txtAtt);
      }

      rek.m_vctAttributes = vctAtts;
      vctReturnsEntityKeys.addElement(rek);
     // ol = 
    	  m_db.update(m_prof, vctReturnsEntityKeys);
      m_db.commit();
      m_db.freeStatement();
      m_db.isPending();

    } catch (COM.ibm.opicmpdh.middleware.MiddlewareException e) {
      logMessage("setAttrValue: " + e.getMessage());
    } catch (Exception e) {
      logMessage("setAttrValue: " + e.getMessage());
    }

  }

  /**
     * getABREntityDesc
     *
     * @param entityType
     * @param entityId
     * @return
     * @author Owner
     */
    protected String getABREntityDesc(String entityType, int entityId) {
    return null;
  }

  /**
     * getDescription
     *
     * @return
     * @author Owner
     */
    public String getDescription() {
    return "This ABR is used to search/copy the FEATURECODE value from the selected SWFEATURE  to COMNAME on SWFEATURE and SWPRODSTRUCT respectively";
  }
  /**
     * getStyle
     *
     * @return
     * @author Owner
     */
    protected String getStyle() {
    return "";
  }

  /**
     * getRevision
     *
     * @return
     * @author Owner
     */
    public String getRevision() {
    return new String("1.4");
  }
}

//insert into opicm.metalinkattr values ('SG13','Action/Entity','EXTSWFCRENAME','SWPRODSTRUCT','U','0','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000','1980-01-01-00.00.00.000000','9999-12-31-00.00.00.000000',0812,7342);
