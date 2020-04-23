//javac -classpath $HOME/abrgenerator/script/ibmwebas.jar:.:./db2java.zip:/usr/java_dev2/jre/lib/rt.jar:$HOME/generator/source/middleware.jar PokBaseABR.java > errors
//
// (c) Copyright International Business Machines Corporation, 2001
// All Rights Reserved.

// $Log: XLABR7.java,v $
// Revision 1.25  2010/04/23 15:40:47  lucasrg
// Fixed Queue Post Process ABR method (added Database m_db parameter)
//
// Revision 1.24  2010/04/23 14:23:58  lucasrg
// Added setControlBlock (was causing an error when trying to queue the Post Process ABR)
//
// Revision 1.23  2010/04/22 17:34:10  lucasrg
// Fixed Queue Post Proceess ABR method for XLABRs
//
// Revision 1.22  2010/03/26 19:31:20  lucasrg
// Added XLABRQueueUtil to trigger the Translation post process ABR (XLPOSTPROCABR)
//
// Revision 1.21  2010/03/26 18:09:56  lucasrg
// XLABR now using EnterpriseUtil to verify enterprise related rules
//
// Revision 1.20  2010/03/25 18:46:26  lucasrg
// Handle translation's max length depending on the enterprise
//
// Revision 1.19  2010/03/25 14:47:09  lucasrg
// XLABRs now handling max length translation exception
//
// Revision 1.18  2008/01/30 19:27:20  wendy
// Cleanup RSA warnings
//
// Revision 1.17  2006/03/03 19:24:12  bala
// remove reference to Constants.CSS
//
// Revision 1.16  2006/01/25 17:47:38  yang
// Jtest changes
//
// Revision 1.15  2005/01/27 16:39:54  joan
// changes for Jtest
//
// Revision 1.14  2004/11/05 17:15:08  yang
// adjust for package id
//
// Revision 1.13  2004/11/05 00:08:58  joan
// adjust code for PackageID constructor change
//
// Revision 1.12  2003/09/18 19:34:21  yang
// adding bala's stuff to finally {
//
// Revision 1.11  2003/09/15 18:54:42  yang
// syntax
//
// Revision 1.10  2003/09/15 18:18:41  yang
// testing postETSPackage by setting abr to always pass
//
// Revision 1.9  2003/09/09 21:24:19  dave
// syntax and import
//
// Revision 1.8  2003/09/09 21:10:17  dave
// syntax
//
// Revision 1.7  2003/09/09 21:06:20  dave
// syntax
//
// Revision 1.6  2003/09/09 20:56:15  dave
// syntax
//
// Revision 1.5  2003/09/09 20:54:05  dave
// simplification and cleanup
//
// Revision 1.4  2003/08/28 20:25:33  yang
// updated getETSPackage
//
// Revision 1.3  2003/08/26 22:04:42  yang
// syntax
//
// Revision 1.2  2003/08/26 21:19:16  yang
// fix null pointer
//
// Revision 1.1  2003/08/26 19:44:15  yang
// adding XLABR7
//

package COM.ibm.eannounce.abr.pcd;

import java.io.PrintWriter;
import java.io.StringWriter;

import COM.ibm.eannounce.abr.util.XLABRQueueUtil;
import COM.ibm.eannounce.abr.util.EnterpriseUtil;
import COM.ibm.eannounce.abr.util.LockPDHEntityException;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.translation.MaxTranslationException;
import COM.ibm.opicmpdh.translation.PackageID;
import COM.ibm.opicmpdh.translation.Translation;
import COM.ibm.opicmpdh.translation.TranslationPackage;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Administrator
 */
public class XLABR7 extends PokBaseABR {

  /**
     * TARGET_NLSID
     *
     */
    public static int TARGET_NLSID = 7;

  /**
   * @see COM.ibm.opicmpdh.middleware.taskmaster.AbstractTask#execute_run()
   * @author Administrator
   */
  public void execute_run() {
    EntityGroup eg;
    EntityItem ei;
    EANFlagAttribute fa;
    String strBillingCode;
    PackageID pkID;
    TranslationPackage etspk;
    StringWriter writer;
    String x;

    try {

      start_ABRBuild(false);
      buildReportHeader();
      setNow();

      eg =
        m_db.getEntityGroup(m_prof, m_abri.getEntityType(), "Edit");
      ei =
        new EntityItem(
          eg,
          m_prof,
          m_db,
          m_abri.getEntityType(),
          m_abri.getEntityID());
      fa =
        (EANFlagAttribute) ei.getAttribute("BILLINGCODE");
      strBillingCode = "";
      if (fa != null) {
        strBillingCode = fa.getFirstActiveFlagCode();
      }
      if (strBillingCode.equals("PCD")) {
        strBillingCode = "";
      }

      println(
        "<b><tr><td class=\"PsgText\" width=\"100%\" >"
          + getEntityType()
          + ":</b></td><b><td class=\"PsgText\" width=\"100%\">"
          + getEntityID()
          + "</b></td>");
      printNavigateAttributes(ei, eg, true);

      pkID =
        new PackageID(
          getEntityType(),
          getEntityID(),
          TARGET_NLSID,
          "N/A",
          m_strNow,
          strBillingCode);
      etspk =
        Translation.getETSPackage(m_db, m_prof, pkID);
      if (etspk == null) {
        println("<br><br><b>Failed. ETSPackage is empty");
      } else {
        Translation.postETSPackage(m_db, m_prof, pkID, EnterpriseUtil.isLastEnterpriseVersion(m_prof));
        setReturnCode(PASS);
        setControlBlock();
        XLABRQueueUtil.queueTranslationPostProcessABR(m_db, m_prof, ei, m_cbOn);
      }

      println(
        "<br /><b>"
          + buildMessage(
            MSG_IAB2016I,
            new String[] {
              getABRDescription(),
              (getReturnCode() == PASS ? "Passed" : "Failed")})
          + "</b>");

    } catch (MaxTranslationException e) {
      setReturnCode(FAIL);
  	  println(e.toHtml());
    } catch (LockPDHEntityException le) {
      setReturnCode(UPDATE_ERROR);
      println(
        "<h3><font color=red>"
          + ERR_IAB1007E
          + "<br />"
          + le.getMessage()
          + "</font></h3>");
    } catch (UpdatePDHEntityException le) {
      setReturnCode(UPDATE_ERROR);
      println(
        "<h3><font color=red>UpdatePDH error: "
          + le.getMessage()
          + "</font></h3>");
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

    } finally {

      setDGString(getABRReturnCode());
      setDGRptName("XLABR7"); //Set the report name
      printDGSubmitString();
      //Stuff into report for subscription and notification
      buildReportFooter();
      if (!isReadOnly()) {
        clearSoftLock();
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
    return "<br /><br />";
  }

  /**
   * @see COM.ibm.eannounce.abr.util.PokBaseABR#getStyle()
   * @author Administrator
   */
  protected String getStyle() {
    return "";
  }

  /**
     * getRevsion
     *
     * @return
     * @author Administrator
     */
  public String getRevision() {
    return new String("$Revision: 1.25 $");
  }

  /**
     * printPassMessage
     *
     * @author Administrator
     */
  public void printPassMessage() {
    println("<br /><b>send to Production Management</b><br /><br />");
  }

  /**
     * getVersion
     *
     * @return
     * @author Administrator
     */
  public static String getVersion() {
    return ("$Id: XLABR7.java,v 1.25 2010/04/23 15:40:47 lucasrg Exp $");
  }
  /**
   * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABRVersion()
   * @author Administrator
   */
  public String getABRVersion() {
    return getVersion();
  }

}
