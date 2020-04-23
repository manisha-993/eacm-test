//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//FCRENAME.java,v
//Revision 1.42  2008/01/30 19:39:15  wendy
//Cleanup RSA warnings
//
//Revision 1.41  2006/03/03 19:23:28  bala
//remove reference to Constants.CSS
//
//Revision 1.40  2006/01/24 16:54:25  yang
//Jtest Changes
//
//Revision 1.39  2004/03/26 16:32:00  joan
//synchronize 1.2
//
//Revision 1.38  2004/03/23 18:48:39  joan
//fix bug
//
//Revision 1.37  2004/03/23 18:19:13  joan
//fix for CR4840
//
//Revision 1.36  2004/02/24 22:58:39  yang
//*** empty log message ***
//
//Revision 1.35  2004/02/24 22:51:22  yang
//fixed FB53678 - removed extra spaces from COMNAME
//
//Revision 1.34  2004/02/24 22:45:23  yang
//*** empty log message ***
//
//Revision 1.33  2004/02/24 22:37:35  yang
//*** empty log message ***
//
//Revision 1.32  2004/02/24 22:29:30  yang
//*** empty log message ***
//
//Revision 1.31  2004/02/24 22:05:18  yang
//more logs
//
//Revision 1.30  2004/02/24 21:58:14  yang
//syntax
//
//Revision 1.29  2004/02/24 20:23:17  yang
//adding log
//
//Revision 1.28  2003/11/07 01:21:31  yang
//Adding setDGRptClass
//
//Revision 1.27  2003/09/18 19:51:55  yang
//adding bala's stuff to finally {
//
//Revision 1.26  2003/08/27 20:29:29  minhthy
//format report
//
//Revision 1.25  2003/08/27 20:27:56  minhthy
//format report
//
//Revision 1.24  2003/08/27 20:00:10  minhthy
//change FUPSTATUS
//
//Revision 1.23  2003/08/27 19:49:00  minhthy
//syntax
//
//Revision 1.22  2003/08/27 19:36:40  minhthy
//clean up mess
//
//Revision 1.21  2003/08/27 19:09:03  minhthy
//*** empty log message ***
//
//Revision 1.20  2003/08/27 18:21:44  minhthy
//*** empty log message ***
//
//Revision 1.19  2003/08/27 18:19:56  minhthy
//*** empty log message ***
//
//Revision 1.18  2003/08/27 17:37:16  minhthy
//*** empty log message ***
//
//Revision 1.17  2003/08/27 17:11:14  minhthy
//*** empty log message ***
//
//Revision 1.16  2003/08/27 16:29:34  minhthy
//*** empty log message ***
//
//Revision 1.15  2003/08/27 15:40:40  minhthy
//*** empty log message ***
//
//Revision 1.14  2003/08/27 14:54:50  minhthy
//testing setAttrValue()
//
//Revision 1.13  2003/08/26 22:30:01  minhthy
//print reports
//
//Revision 1.12  2003/08/26 22:22:06  minhthy
//fixing errors
//
//Revision 1.11  2003/08/26 21:46:15  minhthy
//fixed setAttrValue()
//
//Revision 1.10  2003/08/26 21:26:40  minhthy
//*** empty log message ***
//
//Revision 1.9  2003/08/26 21:10:41  minhthy
//*** empty log message ***
//
//Revision 1.8  2003/08/26 20:59:02  minhthy
//*** empty log message ***
//
//Revision 1.7  2003/08/26 20:21:21  minhthy
//*** empty log message ***
//
//Revision 1.6  2003/08/26 19:42:43  minhthy
//syntax
//
//Revision 1.5  2003/08/26 18:37:07  minhthy
//minor error
//
//Revision 1.4  2003/08/26 17:33:51  minhthy
//syntax
//
//Revision 1.3  2003/08/26 17:31:02  minhthy
//fixed minor error
//
//Revision 1.2  2003/08/26 15:53:06  minhthy
//add setAttrValue()
//
//Revision 1.1  2003/08/25 22:07:40  minhthy
//initial load
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
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Owner
 */
public class FCRENAME extends PokBaseABR {
  /**
  *  Execute ABR.
  */

  //ABR
  public final static String ABR = "FCRENAME";

  /**
     * getVersion
     *
     * @return
     * @author Owner
     */
    public static String getVersion() {
    return ("FCRENAME.java,v 1.42 2008/01/30 19:39:15 wendy Exp");
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

  private EntityGroup m_egParent = null;
  private EntityItem m_eiParent = null;

  private EntityGroup m_egChild = null;
  private EntityItem m_eiChild = null;
  private StringBuffer errMessage = new StringBuffer();

  /**
     * execute_run
     *
     * @author Owner
     */
    public void execute_run() {
    String OOF_COMNAME = null;
    String FUP_FEATURECODE = null;
    String OOF_MACHTYPE = null;
    String OOF_MODEL = null;
    String OOF_FEATURECODE = null;
    String OOF_INVNAME = null;
    String FUP_COMNAME = null;
    String strSubCatOOF = null;
    String x = null;
    StringWriter writer;
    try {

      start_ABRBuild();

      //Get Some Vital information
      m_egParent = m_elist.getParentEntityGroup();
      m_eiParent =
        (m_egParent == null ? null : m_egParent.getEntityItem(0));

      m_egChild = m_elist.getEntityGroup("FUP");

      if (m_egParent == null) {
        logMessage(
          ABR
            + ":"
            + getVersion()
            + ":ERROR:1: m_egParent cannot be established.");
        setReturnCode(FAIL);
        return;
      }
      if (m_eiParent == null) {
        logMessage(
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

      setControlBlock();

      logMessage(
        ABR
          + ":"
          + getVersion()
          + ":Setup Complete:"
          + m_eiParent.getEntityType()
          + ":"
          + m_eiParent.getEntityID());

      setDGTitle(setDGName(m_eiParent, ABR));
      buildReportHeader();

      // Default the thing to pass...
      setReturnCode(PASS);

      if (m_egChild.getEntityItemCount() == 1) {
        m_eiChild = (EntityItem) m_egChild.getEntityItem(0);

        // checking final status

        if (isFinal(m_eiParent) || isFinal(m_eiChild)) {
          setReturnCode(FAIL);
          if (isFinal(m_eiParent))
            errMessage.append(
              "<br><br><b>ORDEROF COMNAME is in Status OFSTATUS cannot be updated in Final");
          if (isFinal(m_eiChild))
            errMessage.append(
              "<br><br><b>FUP COMNAME is in Status FUPSTATUS cannot be updated in Final");

        }

        // checking subcategory
        strSubCatOOF =
          getAttributeValue(m_eiParent, "OOFSUBCAT");
        if (!strSubCatOOF.equals("FeatureCode")) {
          setReturnCode(FAIL);
          errMessage.append(
            "<br><br><b>ORDEROF SUBCATEGORY is not 'FeatureCode'");
        }

      } else {
        setReturnCode(FAIL);
        errMessage.append(
          "<br><br><b>ORDEROF COMNAME has "
            + (m_egChild.getEntityItemCount() == 0
              ? "(zero) "
              : "(more than 1) ")
            + m_egChild.getLongDescription());
      }

      // when it passes , set attribute value and do not print to report

      if (getReturnCode() == PASS) {
        println(
          "<br><br><FONT SIZE=4>"
            + getABRDescription()
            + " has Passed, no report provided");

        // Change the status to Change Ready for Review
        if (isReadyForReview(m_eiParent))
          setFlagValue(
            m_eiParent,
            getStatusAttributeCode(m_eiParent),
            "116");

        if (isReadyForReview(m_eiChild))
          setFlagValue(
            m_eiChild,
            getStatusAttributeCode(m_eiChild),
            "115");

        OOF_MACHTYPE =
          getAttributeValue(m_eiParent, "MACHTYPE").length() > 0
                ? getAttributeValue(m_eiParent, "MACHTYPE") + "-"
                : "";
        OOF_MODEL =
          getAttributeValue(m_eiParent, "MODEL").length() > 0
                ? getAttributeValue(m_eiParent, "MODEL") + " "
                : "";
        OOF_FEATURECODE =
          getAttributeValue(m_eiParent, "FEATURECODE").length() > 0
                ? getAttributeValue(m_eiParent, "FEATURECODE") + " "
                : "";
        OOF_INVNAME =
          getAttributeValue(m_eiParent, "INVNAME").length() > 0
                ? getAttributeValue(m_eiParent, "INVNAME") + " "
                : "";

        OOF_COMNAME =
          OOF_MACHTYPE + OOF_MODEL + OOF_FEATURECODE + OOF_INVNAME;
        setAttrValue(m_eiParent, "COMNAME", OOF_COMNAME);

        FUP_COMNAME = OOF_FEATURECODE + OOF_INVNAME;
        if (requireOOFMTM(m_eiChild)) {
          FUP_COMNAME = OOF_MACHTYPE + OOF_MODEL + FUP_COMNAME;
        }
        setAttrValue(m_eiChild, "COMNAME", FUP_COMNAME);

        FUP_FEATURECODE = OOF_FEATURECODE;
        setAttrValue(m_eiChild, "FEATURECODE", FUP_FEATURECODE);

      }

      println("<br><br>");

      // Print to the report when it fails
      // Then print to the log file
      //

      if (getReturnCode() == FAIL) {
        displayHeader(m_egParent, m_eiParent);
        if (m_eiChild != null) {
          println(
            displayStatuses(m_eiChild, m_eiChild.getEntityGroup()));
          println(
            displayNavAttributes(
              m_eiChild,
              m_eiChild.getEntityGroup()));
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
      setDGRptName("FCRENAME"); //Set the report name
      setDGRptClass("FCRENAME"); //Set the report class
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

  private boolean requireOOFMTM(EntityItem _ei) {
    String strCat = null;
    String strSubCat = null;
    if (!_ei.getEntityType().equals("FUP")) {
      return false;
    }
    strCat = getFlagCode(_ei, "FUPCAT");
    if (!strCat.equals("101")) { //Software
      return false;
    }
    strSubCat = getFlagCode(_ei, "FUPSUBCAT");
    //AssetReg, Key, License, PackageID, UseCaps, ValueMetric, EntitleReg
    if (strSubCat.equals("376")
      || strSubCat.equals("380")
      || strSubCat.equals("381")
      || strSubCat.equals("384")
      || strSubCat.equals("392")
      || strSubCat.equals("393")
      || strSubCat.equals("EntitleReg")) {
      return true;
    }

    return false;
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

                    ControlBlock cbOn =
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
      //OPICMList ol = 
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
    return "This ABR is used to copy the FEATURECODE value from the selected OOF to 3 other attributes, COMNAME on both ORDEROF and FUP, and FEATURECODE on FUP";
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
    return new String("1.42");
  }
}
