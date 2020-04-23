
//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2002, 2008  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
//$Log: LSCCCONVERT01ABR.java,v $
//Revision 1.19  2008/09/04 14:22:59  wendy
//MN36762199
//
//Revision 1.18  2006/10/04 20:49:45  joan
//remove System.out
//
//Revision 1.17  2006/10/04 18:27:31  joan
//search before deactivate
//
//Revision 1.16  2006/10/03 18:07:08  joan
//add debug statement
//
//Revision 1.15  2006/09/25 18:43:16  joan
//changes
//
//Revision 1.14  2006/09/25 18:13:44  joan
//add search action
//
//Revision 1.13  2006/06/26 12:42:34  chris
//moved call to EACustom.getTOUDiv()
//
//Revision 1.12  2006/03/13 19:05:45  couto
//Fixed copyright info.
//
//Revision 1.11  2006/03/03 19:53:39  couto
//Changed layout, using EACustom methods. Using local methods to show header and attributes.
//Fixed br tags. Chaged font tags. Fixed multiple return statements.
//
//Revision 1.10  2006/03/03 19:24:17  bala
//remove reference to Constants.CSS
//
//Revision 1.9  2006/01/22 18:28:29  joan
//changes for Jtest
//
//Revision 1.8  2005/06/02 19:28:08  joan
//fixes
//
//Revision 1.7  2005/05/27 15:23:24  joan
//fixes
//
//Revision 1.6  2005/05/10 17:42:33  joan
//fixes
//
//Revision 1.5  2005/01/26 23:29:56  joan
//using method
//
//Revision 1.4  2005/01/26 23:23:46  joan
//changes for Jtest
//
//Revision 1.3  2003/11/10 21:29:38  joan
//fb fixes
//
//Revision 1.2  2003/10/08 21:29:25  joan
//put more work
//
//Revision 1.1  2003/10/07 23:26:46  joan
//initial load
//

package COM.ibm.eannounce.abr.ls;

import COM.ibm.opicmpdh.middleware.*;
//import COM.ibm.opicmpdh.objects.*;
//import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;

import java.sql.SQLException;
import java.util.*;
//import java.io.*;

/**
 * Javadoc header
 *
 *@author     Administrator
 *@created    August 30, 2002
 */
public class LSCCCONVERT01ABR extends PokBaseABR {
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2002, 2008  All Rights Reserved.";

    // Class constants
  	/**
   	 * static variable
     */
  	public final static String ABR = "LSCCCONVERT01ABR";
    /**
     * static variable
     */
    public final static String COURSE_CODE = "LSCRSID";
    /**
     * static variable
     */
    public final static String COURSE_TITLE = "LSCRSTITLE";
    /**
     * static variable
     */
    public final static String COURSE_LIFECYCLE = "LSCCLIFECYCLE";
    /**
     * static variable
     */
    public final static String WW_CODE = "LSWWID";
    /**
     * static variable
     */
    public final static String WW_TITLE = "LSWWTITLE";
    /**
     * static variable
     */
    public final static String CUR_CODE = "LSCURID";
    /**
     * static variable
     */
    public final static String CUR_TITLE = "LSCURTITLE";

	private final static int MAX_DG_LEN = 64; //Max DG name lenght

    private EntityGroup m_egParent = null;
  	private EntityItem m_ei = null;
  	private EntityItem m_eiWW = null;
  	private EntityItem m_eiCUR = null;
    private PDGUtility m_utility = new PDGUtility();

  /**
  *  Execute ABR.
  *
  */
    public void execute_run() {
        try {
      		String strCC = null;
      		String strCT = null;
      		String s = null;
      		Vector vLSWW = new Vector();

            start_ABRBuild();

            // Build the report header
			//buildReportHeaderII();
			buildHeader();

            m_egParent = m_elist.getParentEntityGroup();
            m_ei = m_egParent.getEntityItem(0);
            println("<br /><b>In-Country Course: " + m_ei.getKey() + "</b>");

			printNavAttributes(m_ei, m_egParent, true);

            setReturnCode(PASS);

            strCC = getAttributeValue(m_elist, m_ei.getEntityType(), m_ei.getEntityID(), COURSE_CODE);
            strCT = getAttributeValue(m_elist, m_ei.getEntityType(), m_ei.getEntityID(), COURSE_TITLE);

            if (strCC == null || strCC.length() <= 0) {
                strCC = "** Not Populated **";
            }

            if (strCT == null || strCT.length() <= 0) {
                strCT = "** Not Populated **";
            }

            //==== check LSCC's LSCCLIFECYCLE="Change Request"
            s = getAttributeValue(m_elist, m_ei.getEntityType(), m_ei.getEntityID(), COURSE_LIFECYCLE);
            if (s == null || s.length() <=0 || s.indexOf("Change Request") < 0) {
                println( "<br />" + "The Conversion of In-Country Course (" + strCC + " and " + strCT + ") was attempted but failed because the Course was not in Change Request.");
                setReturnCode(FAIL);
            }

            //==== check LSWW entities =================
            D.ebug(D.EBUG_SPEW, ABR + "LSCCCONVERT01ABR checking LSWW");
            vLSWW = getChildrenEntityIds(m_ei.getEntityType(), m_ei.getEntityID(), "LSWW", "LSCCWW");
            if (vLSWW.size() <= 0 || vLSWW.size() > 1) {
                println( "<br />" + "The Conversion of In-Country Course (" + strCC + " and " + strCT + ") was attempted but failed because a single correct parent Worldwide Course was not identified.");
                setReturnCode(FAIL);
            } else {
        		Vector vCUR = new Vector();
                EntityGroup eg = m_elist.getEntityGroup("LSWW");
                m_eiWW = eg.getEntityItem("LSWW" + vLSWW.elementAt(0));
                vCUR = getParentEntityIds(m_eiWW.getEntityType(), m_eiWW.getEntityID(), "LSCUR", "LSCURWW");
                if (vCUR.size() > 0) {
                    eg = m_elist.getEntityGroup("LSCUR");
                    m_eiCUR = eg.getEntityItem("LSCUR" + vCUR.elementAt(0));
                }
            }

            //============= run the PDG to generate data ==========================================
            if(getReturnCode() == PASS) {
        		LSCONVERT01PDG pdg = null;
        		String strWWCC = null;
        		String strWWCT = null;
                D.ebug(D.EBUG_SPEW, ABR + " generating data");
                pdg = new LSCONVERT01PDG(null, m_db, m_prof, "LSCONVERT01PDG");
                pdg.setEntityItem(m_ei);
                pdg.setABReList(m_elist);
                pdg.executeAction(m_db, m_prof);
                D.ebug(D.EBUG_SPEW, ABR + " finish generating data");

      			StringBuffer sbSearch = new StringBuffer();
      			sbSearch.append("map_LSCRSID=" + strCC);
      			String strSai = "SRDLSWWCC";// not defined ->"SRDLSWWCC01";

     			EntityItem[] aeiLSWWCC = m_utility.dynaSearch(m_db, m_prof, null, strSai, "LSWWCC", sbSearch.toString());

				if (aeiLSWWCC != null && aeiLSWWCC.length > 0) {
					strWWCC = getAttributeValue(m_elist, m_eiWW.getEntityType(), m_eiWW.getEntityID(), WW_CODE);
                	strWWCT = getAttributeValue(m_elist, m_eiWW.getEntityType(), m_eiWW.getEntityID(), WW_TITLE);

                	if (m_eiCUR != null) {
                	    String strCURCC = getAttributeValue(m_elist, m_eiCUR.getEntityType(), m_eiCUR.getEntityID(), CUR_CODE);
                	    String strCURCT = getAttributeValue(m_elist, m_eiCUR.getEntityType(), m_eiCUR.getEntityID(), CUR_TITLE);
                	    println("<br /> Course (" + strCC + " and " + strCT + ") is successfully converted to a Worldwide In-Country Course for parent WorldwideCourse (" +
                	        strWWCC + " and " + strWWCT + ") and parent Curriculumn (" + strCURCC + " and " + strCURCT + ")");
                	} else {
                	    println("<br /> Course (" + strCC + " and " + strCT + ") is successfully converted to a Worldwide In-Country Course for parent WorldwideCourse (" +
                	        strWWCC + " and " + strWWCT + "). There's no parent Curriculumn.)");
                	}

	                setDeactivateABREntity(true);
				} else {
					D.ebug(D.EBUG_ERR, ABR + " Unable to convert the course: " + m_ei.getKey());
					setReturnCode(FAIL);
					println("<br />Unable to convert the course.");

				}
            }

            println("<br /><b>" + buildMessage(MSG_IAB2016I, new String[]{getABRDescription(), (getReturnCode() == PASS ? "Passed" : "Failed")}) + "</b>");

            log(buildLogMessage(MSG_IAB2016I, new String[]{getABRDescription(), (getReturnCode() == PASS ? "Passed" : "Failed")}));
        } catch (LockPDHEntityException le) {
            setReturnCode(UPDATE_ERROR);
            println("<h3 style=\"color:#c00; font-weight:bold;\">" + ERR_IAB1007E + "<br />" + le.getMessage() + "</h3>");
            logError(le.getMessage());
        } catch (UpdatePDHEntityException le) {
            setReturnCode(UPDATE_ERROR);
            println("<h3 style=\"color:#c00; font-weight:bold;\">UpdatePDH error: " + le.getMessage() + "</h3>");
            logError(le.getMessage());
        } catch (SBRException _sbrex) {
            setReturnCode(UPDATE_ERROR);
            println("<h3 style=\"color:#c00; font-weight:bold;\">Generate Data error: " + _sbrex.toString() + "</h3>");
            logError(_sbrex.toString());
        } catch (MiddlewareException _sbrex) {
            String strError = _sbrex.toString();
            int i = strError.indexOf("(ok)");
      		setReturnCode(UPDATE_ERROR);
            if (i > 0) {
                strError = strError.substring(0, i);
            }
            println("<h3 style=\"color:#c00; font-weight:bold;\">" + strError + "</h3>");
            logError(_sbrex.toString());
            _sbrex.printStackTrace();
        } catch (Exception exc) {
            // Report this error to both the datbase log and the PrintWriter
            println("Error in " + m_abri.getABRCode() + ":" + exc.getMessage());
            println("" + exc);
            exc.printStackTrace();
            // don't overwrite an update exception
            if (getABRReturnCode() != UPDATE_ERROR) {
                setReturnCode(INTERNAL_ERROR);
            }
        } finally {
            // set DG title
            String strDgName = m_ei.toString();
            if (strDgName.length() > MAX_DG_LEN) {
                strDgName = strDgName.substring(0,MAX_DG_LEN);
            }
            setDGTitle(strDgName);

            // set DG submit string
            setDGString(getABRReturnCode());
            setDGRptName(ABR);
            setDGRptClass(getABRCode());
        }

		//Print everything up to </html>
		println(EACustom.getTOUDiv());
		printDGSubmitString();  //Stuff into report for subscription and notification
		buildReportFooter();
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
        return "Convert In-Country Course to Worldwide In-Country Course.";
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
     * @return
     * @author Administrator
     */
    public String getRevision() {
        return "$Revision: 1.19 $";
    }

    /**
     * getVersion
     * @return
     * @author Administrator
     */
    public static String getVersion() {
        return ("$Id: LSCCCONVERT01ABR.java,v 1.19 2008/09/04 14:22:59 wendy Exp $");
    }

    /**
     * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABRVersion()
     * @author Administrator
     */
    public String getABRVersion() {
        return ("LSCCCONVERT01ABR.java "+getRevision());
    }

	/*
	 *  builds the standard header and submits it to the passed PrintWriter
	 */
	private void buildHeader() throws SQLException, MiddlewareException{

		// output html header
		//String abrName = getShortClassName(getClass());
		//println("<html>\n<head>\n<title>" + abrName + "</title>\n" +
		//	getStyle() + "</head>\n<body>\n");
		//String strMessage1 = buildMessage(MSG_IAB0001I, _strMessage);
		//println("<p>" + strMessage1 + "</p>");

//		println(EACustom.getDocTypeHtml());
//		println("<head>");
//		println(EACustom.getMetaTags(getDescription()));
//		println(EACustom.getCSS());
//		println(EACustom.getTitle(getShortClassName(getClass())));
//		println("</head>");
//		println("<body id=\"w3-ibm-com\">");
//		println(EACustom.getMastheadDiv());

		println("<table summary=\"layout\" width=\"750\" cellpadding=\"0\" cellspacing=\"0\">" + NEW_LINE +
			"<tr><td width=\"25%\"><b>ABRName: </b></td><td>" +
			getABRCode() + "</td></tr>" + NEW_LINE +
			"<tr><td width=\"25%\"><b>Abr Version: </b></td><td>" +
			getABRVersion() + "</td></tr>" + NEW_LINE +
			"<tr><td width=\"25%\"><b>Enterprise: </b></td><td>" +
			getEnterprise() + "</td></tr>" + NEW_LINE +
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

		println("<br /><br /><table summary=\"layout\" width=\"100%\">" + NEW_LINE);

		for(int i=0;i<eg.getMetaAttributeCount();i++){
			EANMetaAttribute ma = eg.getMetaAttribute(i);
			EANAttribute att = entity.getAttribute(ma.getAttributeCode());
			if(navigate){
				if(i ==0) {
					println("<tr><th width=\"50%\">Navigation Attribute</th><th width=\"50%\">Attribute Value</th></tr>");
				}
				if(ma.isNavigate()) {
					println("<tr><td width=\"50%\">"+ ma.getLongDescription()+"</td><td width=\"50%\">" +(att == null || att.toString().length() ==0 ? "** Not Populated **" : att.toString()) + "</td></tr>");
				}
			}
			else {
				if(i ==0) {
					println("<tr><th width=\"50%\" >Attribute Description</th><th width=\"50%\">Attribute Value</th></tr>");
				}
				println("<tr><td width=\"50%\" >"+ ma.getLongDescription()+"</td><td width=\"50%\">" +(att == null || att.toString().length() ==0 ? "** Not Populated **" : att.toString()) + "</td></tr>");
			}

		}
		println("</table>");

   }
}
