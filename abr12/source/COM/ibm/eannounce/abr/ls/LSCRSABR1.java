//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2008  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.ls;

import java.text.MessageFormat;
import java.util.Vector;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
import COM.ibm.opicmpdh.objects.Attribute;
import COM.ibm.opicmpdh.objects.ControlBlock;
import COM.ibm.opicmpdh.objects.SingleFlag;
import COM.ibm.opicmpdh.objects.Text;

/********************************************************
 * CQ1601 - RQ Modify the Catalog New Course Indicator Field to Include Updated Status
 * 
 * From LS FS SysFeed TA 20080725.doc
 * 
 * This ABR will:
 * 	-Set Catalog New Course Indicator (LSCRSPRTCATET) equal to N/A (NC3) which has a 'blank' short description.
 * 	-Set New Course Indicator Valid Until (LSCRNCIVU) equal to empty (aka deactivate or deleted).
 *
 * LSCRSPRTCATET	NC1	N	New
 * LSCRSPRTCATET	NC2	U	Updated
 * LSCRSPRTCATET	NC3		N/A
 * LSCRNCIVU	T	New Ind Until	New Course Indicator Valid Until
 */
// $Log: LSCRSABR1.java,v $
// Revision 1.2  2014/01/13 13:50:50  wendy
// migration to V17
//
// Revision 1.1  2008/07/28 21:35:50  wendy
// Init for CQ1601
//
public class LSCRSABR1 extends PokBaseABR {
    private Object[] args = new String[10];

    private Vector vctReturnsEntityKeys = new Vector();

    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);
    private static final String NA_FLAG = "NC3";
    private static final String NEW_CRS_ATTR = "LSCRSPRTCATET";
    private static final String NEW_CRS_DATE_ATTR = "LSCRNCIVU";

    private StringBuffer rptSb = new StringBuffer();
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2008  All Rights Reserved.";

    private static final String RPT_TEMPLATE = "<table>"+NEWLINE +
        "<tr><th>Date: </th><td>{0}</td></tr>"+NEWLINE +
        "<tr><th>User: </th><td>{1} ({2})</td></tr>"+NEWLINE +
        "<tr><th>Description: </th><td>{3}</td></tr></table>"+NEWLINE +
        "<p>{4} Code: {5}</p>"+NEWLINE;

    /**
     * Execute LS ABR.
     *
     */
    public void execute_run() {
    	String rootDesc = "";
        String navName = "";
        String HEADER = "<head>"+
            EACustom.getMetaTags(getDescription()) + NEWLINE +
            EACustom.getCSS() + NEWLINE +
            EACustom.getTitle("{0} {1}") + NEWLINE +
            "</head>" + NEWLINE + "<body id=\"ibm-com\">" +
            EACustom.getMastheadDiv() + NEWLINE +
            "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE+
            "<!-- {2} -->" + NEWLINE;

        MessageFormat msgf;
        try {
            start_ABRBuild();

            // NAME is navigate attributes
            navName = getNavigationName();
            
            rootDesc = m_elist.getParentEntityGroup().getLongDescription();

            if (m_cbOn==null){
                setControlBlock(); // needed for attribute updates
            }
            // reset the flag
            setFlagValue(NEW_CRS_ATTR,NA_FLAG);
            // deactivate the date
            deactivateTextAttr(NEW_CRS_DATE_ATTR);

            updatePDH();

//          fixme remove
//            setCreateDGEntity(false);

            setReturnCode(PASS);
        }
        catch (Throwable exc) {
            java.io.StringWriter exBuf = new java.io.StringWriter();
            String Error_EXCEPTION="<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
            String Error_STACKTRACE="<pre>{0}</pre>";
            msgf = new MessageFormat(Error_EXCEPTION);
            setReturnCode(INTERNAL_ERROR);
            exc.printStackTrace(new java.io.PrintWriter(exBuf));
            // Put exception into document
            args[0] = exc.getMessage();
            rptSb.append(msgf.format(args) + NEWLINE);
            msgf = new MessageFormat(Error_STACKTRACE);
            args[0] = exBuf.getBuffer().toString();
            rptSb.append(msgf.format(args) + NEWLINE);
            logError("Exception: "+exc.getMessage());
            logError(exBuf.getBuffer().toString());
        }
        finally
        {
            setDGTitle(navName);
            setDGRptName(getShortClassName(getClass()));
            setDGRptClass(getABRCode());
            // make sure the lock is released
            if(!isReadOnly()) {
                clearSoftLock();
            }
        }

        //Print everything up to </html>
        //Insert Header into beginning of report
        msgf = new MessageFormat(HEADER);
        args[0] = getShortClassName(getClass());
        args[1] = navName;
        args[2] = getABRVersion();

        String header = EACustom.getDocTypeHtml()+msgf.format(args);
        msgf = new MessageFormat(RPT_TEMPLATE);
        args[0] = getNow();
        args[1] = m_prof.getOPName();
        args[2] = m_prof.getRoleDescription();
        args[3] = getDescription();
        args[4] = rootDesc;
        args[5] = getAttributeValue(getEntityType(), getEntityID(), "LSCRSID", DEF_NOT_POPULATED_HTML);

        rptSb.insert(0,header+msgf.format(args));

        println(rptSb.toString()); // Output the Report
        printDGSubmitString();
        println(EACustom.getTOUDiv());
        buildReportFooter(); // Print </html>
    }

    /**********************************************************************************
     *  Get Name based on navigation attributes
     *
     *@return java.lang.String
     */
    private String getNavigationName() throws java.sql.SQLException, MiddlewareException
    {
        StringBuffer navName = new StringBuffer();
        // NAME is navigate attributes
        EntityGroup eg =  new EntityGroup(null, m_db, m_prof, getRootEntityType(), "Navigate");
        EANList metaList = eg.getMetaAttribute(); // iterator does not maintain navigate order
        for (int ii=0; ii<metaList.size(); ii++)
        {
            EANMetaAttribute ma = (EANMetaAttribute)metaList.getAt(ii);
            navName.append(getAttributeValue(getRootEntityType(), getRootEntityID(), ma.getAttributeCode()));
            navName.append(" ");
        }

        return navName.toString().trim();
    }

    /***********************************************
     *  Sets the specified Flag Attribute on the Root Entity
     *
     *@param    profile Profile
     *@param    strAttributeCode The Flag Attribute Code
     *@param    strAttributeValue The Flag Attribute Value
     */
    private void setFlagValue(String strAttributeCode, String strAttributeValue)
    throws java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException
    {
        logDebug(" ***** "+strAttributeCode+" set to: " + strAttributeValue);
        EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

        // if meta does not have this attribute, there is nothing to do
        EANMetaAttribute metaAttr = rootEntity.getEntityGroup().getMetaAttribute(strAttributeCode);
        if (metaAttr==null) {
            logDebug("setFlagValue: "+strAttributeCode+" was not in meta for "+rootEntity.getEntityType()+", nothing to do");
            return;
        }

        if(strAttributeValue != null)
        {
            if(strAttributeValue.equals(getAttributeFlagEnabledValue(rootEntity,strAttributeCode))){
                logDebug("setFlagValue "+rootEntity.getKey()+" "+strAttributeCode+
                        " already matches: " + strAttributeValue);
            }else{
                try {
                    ReturnEntityKey rek = new ReturnEntityKey(getEntityType(), getEntityID(), true);

                    SingleFlag sf = new SingleFlag (m_prof.getEnterprise(), rek.getEntityType(), rek.getEntityID(),
                            strAttributeCode, strAttributeValue, 1, m_cbOn);
                    Vector vctAtts = new Vector();
                    vctAtts.addElement(sf);
                    rek.m_vctAttributes = vctAtts;
                    vctReturnsEntityKeys.addElement(rek);

                    m_db.update(m_prof, vctReturnsEntityKeys, false, false);
                    logDebug(rootEntity.getKey()+" had "+strAttributeCode+" set to: " + strAttributeValue);
                    rptSb.append("<p>Set &quot;"+
                    	getAttributeDescription(rootEntity.getEntityGroup(), strAttributeCode, strAttributeCode) +
                    	"&quot; to N/A from "+
                    	getAttributeValue(rootEntity.getEntityType(), rootEntity.getEntityID(), strAttributeCode, DEF_NOT_POPULATED_HTML)+
                    	"</p>");
                }
                finally {
                    m_db.commit();
                    m_db.freeStatement();
                    m_db.isPending("finally after update in setflag value");
                }
            }
        }
    }
    /********************************************************************************
     * Get attribute description - base class doesnt work because doesnt get parentgroup
     *
     * @param entityGroup EntityGroup
     * @param attrCode String attribute code
     * @param defValue String
     * @return String
     */
     private static String getAttributeDescription(EntityGroup entityGroup, String attrCode, String defValue)
     {
         EANMetaAttribute ma = null;
         String desc=null;
         if (entityGroup==null) {
             desc= defValue;
         }
         else {
             ma = entityGroup.getMetaAttribute(attrCode);
             desc = defValue;
             if (ma != null) {
                 desc = ma.getActualLongDescription();
             }
         }

         return desc;
     }
    /***********************************************
     *  Deactivate the specified Text Attribute on the Root Entity
     *
     *@param    strAttributeCode Attribute Code
     */
    private void deactivateTextAttr(String strAttributeCode)
    throws java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException
    {
        logDebug(" deactivateTextAttr "+strAttributeCode);
        EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

        // if meta does not have this attribute, there is nothing to do
        EANMetaAttribute metaAttr = rootEntity.getEntityGroup().getMetaAttribute(strAttributeCode);
        if (metaAttr==null) {
            logDebug("deactivateTextAttr: "+strAttributeCode+" was not in meta for "+rootEntity.getEntityType()+", nothing to do");
            return;
        }

        EANAttribute att = rootEntity.getAttribute(strAttributeCode);
        if (att!=null){ // if null, then was never set
        	String efffrom = att.getEffFrom();
        	ControlBlock cbOff = new ControlBlock(efffrom, efffrom, efffrom, efffrom, m_prof.getOPWGID());

        	Text text1 = new Text(m_prof.getEnterprise(), rootEntity.getEntityType(),
        			rootEntity.getEntityID(), strAttributeCode, att.toString(), 1, cbOff);

        	Vector vctAtts = null;
        	if (vctReturnsEntityKeys.isEmpty()){
        		ReturnEntityKey rek = new ReturnEntityKey(getEntityType(), getEntityID(), true);
        		vctAtts = new Vector();
        		vctReturnsEntityKeys.addElement(rek);
        		rek.m_vctAttributes = vctAtts;
        	}else{
        		ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.firstElement();
        		vctAtts = rek.m_vctAttributes;
        	}

        	vctAtts.addElement(text1);
        	rptSb.append("<p>Deactivate &quot;"+
        			getAttributeDescription(rootEntity.getEntityGroup(), strAttributeCode, strAttributeCode) +
                	"&quot; with value of "+
                	getAttributeValue(rootEntity.getEntityType(), rootEntity.getEntityID(), strAttributeCode, DEF_NOT_POPULATED_HTML)
                	+"</p>");
        }else {
            logDebug("deactivateTextAttr: "+strAttributeCode+" was already null for "+rootEntity.getEntityType()+", nothing to do");
        }
    }

    /***********************************************
     * Update the PDH with the values in the vector, do all at once
     */
    private void updatePDH()
    throws java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    java.rmi.RemoteException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    COM.ibm.eannounce.objects.EANBusinessRuleException
    {
    	logDebug(" updating PDH for vctReturnsEntityKeys: "+vctReturnsEntityKeys);
    	if(vctReturnsEntityKeys.size()>0)
    	{
    		try	{
    			m_db.update(m_prof, vctReturnsEntityKeys, false, false);

    			for (int i=0; i<vctReturnsEntityKeys.size(); i++){
    				ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
    				// must commit text chgs.. not sure why
    				for (int ii=0; ii<rek.m_vctAttributes.size(); ii++){
    					Attribute attr = (Attribute)rek.m_vctAttributes.elementAt(ii);
    					if (attr instanceof Text){
    						EntityGroup egrp = m_elist.getParentEntityGroup();
    						EntityItem item = egrp.getEntityItem(rek.getEntityType()+rek.getEntityID());
    						item.commit(m_db, null);
    					}
    				}
    			}
    		}
    		finally {
    			vctReturnsEntityKeys.clear();
    			m_db.commit();
    			m_db.freeStatement();
    			m_db.isPending("finally after updatePDH");
    		}
    	}
    }

    /**
     * @param _strMessage
     */
    private void logDebug(String _strMessage) {
    	rptSb.append("<!-- "+_strMessage+" -->"+NEWLINE);
    	logMessage(_strMessage);
    }
    /**
     *  Gets the description of LSCRSABR1 
     * @return    The description value
     */
    public String getDescription() {
        return "This ABR will set the 'Catalog New Course Indicator' equal to  N/A (NC3) and "+
        "deletes the 'New Course Indicator Valid Until' attribute.";
    }

    /**
     *  Get the version
     *
     * @return    java.lang.String
     */
    public String getABRVersion() {
        return "$Revision: 1.2 $";
    }
}

