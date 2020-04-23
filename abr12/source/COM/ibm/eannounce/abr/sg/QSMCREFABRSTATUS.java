//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.sg;

import java.io.*;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

import com.ibm.transform.oim.eacm.util.PokUtils;
import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;

/**********************************************************************************
* From "SG FS SysFeed QSM Load  20121128.doc" 
**********************************************************************************/
//$Log: QSMCREFABRSTATUS.java,v $
//Revision 1.24  2017/03/16 01:17:00  mmiotto
//Updates to support AVAIL EPIC
//
//Revision 1.23  2016/11/28 21:12:12  mmiotto
//Fix for some attributes
//Added file handling - create in one dir, copy to ftp dir when complete
//
//Revision 1.22  2016/11/11 18:50:01  mmiotto
//Fix output file name
//
//Revision 1.21  2016/11/11 16:03:09  mmiotto
//Update output file name.
//Remove checks on GENAREA and replace by checks on QSMGEO
//
//Revision 1.20  2016/10/08 22:46:40  mmiotto
//Code cleanup
//
//Revision 1.19  2016/10/07 21:24:14  mmiotto
//Added some debug statements
//
//Revision 1.18  2016/10/03 21:12:57  mmiotto
//Fix to CLASSPT field
//
//Revision 1.17  2016/09/30 20:41:37  mmiotto
//Fix for Type Feature
//
//Revision 1.16  2016/09/30 13:09:39  mmiotto
//Add debug for feature section, ISLMPRN field
//
//Revision 1.15  2016/09/27 02:41:01  mmiotto
//Fix VEs
//Remove lock when completed
//
//Revision 1.14  2016/09/26 23:59:16  mmiotto
//Fix geoModVect for loop
//
//Revision 1.13  2016/09/26 23:18:27  mmiotto
//Fix a compile issue
//
//Revision 1.12  2016/09/26 23:08:44  mmiotto
//Add debug PokUtils.outputList to display what is returned by each VE
//
//Revision 1.11  2016/09/26 21:32:15  mmiotto
//Minor changes to the code, analysing the empty report issue
//
//Revision 1.10  2016/09/25 02:15:34  mmiotto
//Fixes to GEOMOD selection
//
//Revision 1.9  2016/09/24 03:13:07  mmiotto
//Fix NullPointerException on a debug statement
//
//Revision 1.8  2016/09/08 19:55:48  mmiotto
//Add cvs log
//
// Revision 1.1  2016-05-27 16:38:00  mmiotto
// OIM Simplification project is to sunset ePIMS HW   and MMLC (SAP) systems. In order to sunset ePIMS HW we need to replace ePIMS HW -> QSM feed to EACM.
//
//
/**
 * @author mmiotto
 *
 */
/**
 * @author mmiotto
 *
 */
public class QSMCREFABRSTATUS extends PokBaseABR
{
    private StringBuffer rptSb = new StringBuffer();
    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);
    
    private ResourceBundle rsBundle = null;
    private Hashtable metaTbl = new Hashtable();
    private String navName = "";
    private String strCREFIF = "";
    
    private String ffFileName = null;
    private String ffPathName = null;
    private String ffFTPPathName = null;
	private String dir = null;
	private String dirDest = null;
	private final String QSMRPTPATH = "_rptpath";
	private final String QSMGENPATH = "_genpath";
	private final String QSMFTPPATH = "_ftppath";
	private int abr_debuglvl=D.EBUG_ERR;
	private String fileprefix = null;
	private String lineStr = "";
	private static final String SUCCESS = "SUCCESS";
	private static final String FAILD = "FAILD";
	private static final String CREFINIPATH = "_inipath";
	private static final String FTPSCRPATH = "_script";
	private static final String TARGETFILENAME = "_targetfilename";
	private static final String LOGPATH = "_logpath";
	private static final String BACKUPPATH = "_backuppath";
    private static final String STATUS_PASSED = "0030";
	public final static String ISLMRFA = "ISLMRFA";
	public final static String ISLMPRN = "ISLMPRN";
	public final static String CSLMPCI = "CSLMPCI";
	public final static String IPRTNUM = "IPRTNUM";
	public final static String FPUNINC = "FPUNINC";
	public final static String DSLMCPA = "DSLMCPA";
	public final static String DSLMGAD = "DSLMGAD";
	public final static String DSLMMES = "DSLMMES";
	public final static String DSLMWDN = "DSLMWDN";
	public final static String CJLBIC1 = "CJLBIC1";
	public final static String CJLBIDS = "CJLBIDS";
	public final static String CJLBIDT = "CJLBIDT";
	public final static String CJLBOEM = "CJLBOEM";
	public final static String CJLBPOF = "CJLBPOF";
	public final static String CJLBSAC = "CJLBSAC";
	public final static String CREFIF = "CREFIF";
	public final static String CLASSPT = "CLASSPT";
	public final static String CSLMFCC = "CSLMFCC";
	public final static String CSLMSYT = "CSLMSYT";
	public final static String CVOAT = "CVOAT";
	public final static String FBRAND = "FBRAND";
	public final static String FSLMCPU = "FSLMCPU";
	public final static String FSLMIOP = "FSLMIOP";
	public final static String FSLMMLC = "FSLMMLC";
	public final static String IDORIG = "IDORIG";
	public final static String POGMES = "POGMES";
	public final static String STSPCFT = "STSPCFT";
	public final static String TSLMDES = "TSLMDES";
	public final static String TSLTDES = "TSLTDES";
	public final static String XMAP = "XMAP";
	public final static String XMCCN = "XMCCN";
	public final static String XMEMEA = "XMEMEA";
	public final static String XMLA = "XMLA";
	public final static String XMUS = "XMUS";
	public final static String TIMSTMP = "TIMSTMP";
	public final static String USERID = "USERID";
	private static final Hashtable COLUMN_LENGTH;

	/**
	 * Record format defintion
	 * Form (record name), (record length), <starting postion>, <type>
	 */ 		
	static {
		COLUMN_LENGTH = new Hashtable();
		COLUMN_LENGTH.put(ISLMRFA, "6");
		COLUMN_LENGTH.put(ISLMPRN, "14");
		COLUMN_LENGTH.put(CSLMPCI, "2");
		COLUMN_LENGTH.put(IPRTNUM, "12");
		COLUMN_LENGTH.put(FPUNINC, "1");
		COLUMN_LENGTH.put(DSLMCPA, "10");
		COLUMN_LENGTH.put(DSLMGAD, "10");
		COLUMN_LENGTH.put(DSLMMES, "10");
		COLUMN_LENGTH.put(DSLMWDN, "10");
		COLUMN_LENGTH.put(CJLBIC1, "2");
		COLUMN_LENGTH.put(CJLBIDS, "1");
		COLUMN_LENGTH.put(CJLBIDT, "1");
		COLUMN_LENGTH.put(CJLBOEM, "1");
		COLUMN_LENGTH.put(CJLBPOF, "1");
		COLUMN_LENGTH.put(CJLBSAC, "3");
		COLUMN_LENGTH.put(CREFIF, "1");
		COLUMN_LENGTH.put(CLASSPT, "3");
		COLUMN_LENGTH.put(CSLMFCC, "4");
		COLUMN_LENGTH.put(CSLMSYT, "4");
		COLUMN_LENGTH.put(CVOAT, "1");
		COLUMN_LENGTH.put(FBRAND, "1");
		COLUMN_LENGTH.put(FSLMCPU, "1");
		COLUMN_LENGTH.put(FSLMIOP, "1");
		COLUMN_LENGTH.put(FSLMMLC, "1");
		COLUMN_LENGTH.put(IDORIG, "3");
		COLUMN_LENGTH.put(POGMES, "10");
		COLUMN_LENGTH.put(STSPCFT, "4");
		COLUMN_LENGTH.put(TSLMDES, "30");
		COLUMN_LENGTH.put(TSLTDES, "56");
		COLUMN_LENGTH.put(XMAP, "1");
		COLUMN_LENGTH.put(XMCCN, "1");
		COLUMN_LENGTH.put(XMEMEA, "1");
		COLUMN_LENGTH.put(XMLA, "1");
		COLUMN_LENGTH.put(XMUS, "1");
		COLUMN_LENGTH.put(TIMSTMP, "26");
		COLUMN_LENGTH.put(USERID, "8");
	}
	
    /**********************************
    * get the resource bundle
    */
    protected ResourceBundle getBundle() {
        return rsBundle;
    }

    /**********************************
     *  Execute ABR.
     *
     */
    public void execute_run()
    {
        String HEADER = "<head>"+
             EACustom.getMetaTags(getDescription()) + NEWLINE +
             EACustom.getCSS() + NEWLINE +
             EACustom.getTitle("{0} {1}") + NEWLINE +
            "</head>" + NEWLINE + "<body id=\"ibm-com\">" +
             EACustom.getMastheadDiv() + NEWLINE +
            "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
        String HEADER2 = "<table>"+NEWLINE +
             "<tr><th>Userid: </th><td>{0}</td></tr>"+NEWLINE +
             "<tr><th>Role: </th><td>{1}</td></tr>"+NEWLINE +
             "<tr><th>Workgroup: </th><td>{2}</td></tr>"+NEWLINE +
             "<tr><th>Date: </th><td>{3}</td></tr>"+NEWLINE +
             "<tr><th>Description: </th><td>{4}</td></tr>"+NEWLINE +
             "</table>"+NEWLINE+
            "<!-- {5} -->" + NEWLINE;

		String header1 = "";
		boolean creatFile = true;
		boolean sentFile = true;

        MessageFormat msgf;
        String abrversion="";
        String rootDesc="";
        
        Object[] args = new String[10];
       
        try {
			msgf = new MessageFormat(HEADER);
			args[0] = getShortClassName(getClass());
			args[1] = "ABR";
			header1 = msgf.format(args);

            //Default set to pass
            setReturnCode(PASS);
            
            start_ABRBuild(false); // pull the VE
            
            abr_debuglvl = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getABRDebugLevel(m_abri.getABRCode());

            //get the root entity using current timestamp, need this to get the timestamps or info for VE pulls
			m_elist = getEntityList(getModelVEName());
			
            EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
            //NAME is navigate attributes - only used if error rpt is generated
            navName = getNavigationName();
            rootDesc = m_elist.getParentEntityGroup().getLongDescription();

			AttributeChangeHistoryGroup achgQsmCrefAbrStatus = getAttributeHistory("QSMCREFABRSTATUS");
	        if (existBefore(achgQsmCrefAbrStatus, STATUS_PASSED)){
				strCREFIF = "1";
	        }else{
	        	strCREFIF = "0";
	        }

        	// build the text file 	
			setDGString(getABRReturnCode());
			setDGRptName("QSMCREFABRSTATUS"); // Set the report name
			setDGTitle(navName);
			setDGRptClass(getABRCode()); // Set the report class
            generateFlatFile(rootEntity);
            exeFtpShell(ffPathName);

        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//println(e.toString());
			setReturnCode(FAIL);
			java.io.StringWriter exBuf = new java.io.StringWriter();
			String Error_EXCEPTION = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
			String Error_STACKTRACE = "<pre>{0}</pre>";
			msgf = new MessageFormat(Error_EXCEPTION);
			setReturnCode(INTERNAL_ERROR);
			e.printStackTrace(new java.io.PrintWriter(exBuf));
			// Put exception into document
			args[0] = e.getMessage();
			rptSb.append(msgf.format(args) + NEWLINE);
			msgf = new MessageFormat(Error_STACKTRACE);
			args[0] = exBuf.getBuffer().toString();
			rptSb.append(msgf.format(args) + NEWLINE);
			logError("Exception: " + e.getMessage());
			logError(exBuf.getBuffer().toString());
			// was an error make sure user gets report
			setCreateDGEntity(true);
			creatFile = false;
			sentFile=exeFtpShell(ffPathName);
        }finally {
            if(!isReadOnly()){
                clearSoftLock();
            }

			StringBuffer sb = new StringBuffer();
			msgf = new MessageFormat(HEADER2);
			args[0] = m_prof.getOPName();
			args[1] = m_prof.getRoleDescription();
			args[2] = m_prof.getWGName();
			args[3] = getNow();
			sb.append(creatFile ? "generated the QSM report file successful " : "generated the QSM report file faild");
			sb.append(",");
			sb.append(sentFile?"send the QSM report file successful " : "sent the QSM report file faild");
			args[4] = sb.toString();
			args[5] = abrversion + " " + getABRVersion();

			rptSb.insert(0, header1 + msgf.format(args) + NEWLINE);

			println(EACustom.getDocTypeHtml()); // Output the doctype and html
			println(rptSb.toString()); // Output the Report
			printDGSubmitString();

			println(EACustom.getTOUDiv());
			buildReportFooter(); // Print </html>
		}
    }
    
    /******************************************** 
     *  CQ00016165
     *  The Files will be named as follows:
1.	'EACMFEEDQSMABR_'
2.	The DB2 DTS for T2 with spaces and special characters replaced with an underscore
3.	'.txt'
     */
    private void setFileName(EntityItem rootEntity){
		// FILE_PREFIX=EACMFEEDQSMABR_
		 fileprefix = ABRServerProperties.getFilePrefix(m_abri
				.getABRCode());
		// ABRServerProperties.getOutputPath()
		// ABRServerProperties.get
		StringBuffer sb = new StringBuffer(fileprefix.trim());
		sb.append(rootEntity.getEntityType() + rootEntity.getEntityID() + "_");
		String dts = getNow();
		// replace special characters
		dts = dts.replace(' ', '_');
		sb.append(dts + ".txt");
    	dir = ABRServerProperties.getValue(m_abri.getABRCode(), QSMGENPATH, "/Dgq");
    	if (!dir.endsWith("/")){
    		dir=dir+"/";
    	}
		ffFileName = sb.toString();
		ffPathName = dir + ffFileName;
    }
    /********************************************
     * Build a text file using all 
	 * The records are data based on the type of data described above.
     * @throws IOException 
     * @throws MiddlewareException 
     * @throws SQLException 
     * @throws ParseException 
     */
    private void generateFlatFile(EntityItem rootEntity) throws IOException, SQLException, MiddlewareException, ParseException
    {
		
    	FileChannel sourceChannel = null;
    	FileChannel destChannel = null;
    	
    	// generate file name
    	setFileName(rootEntity);
    	
    	FileOutputStream fos = new FileOutputStream(ffPathName);
    	// OutputStreamWriter will convert from characters to bytes using
    	// the specified character encoding or the platform default if none
    	// is specified.  Output as unicode
    	OutputStreamWriter wOut = new OutputStreamWriter(fos, "UTF-8");

    	createModelRecords(rootEntity, wOut);
		createFeatureRecords(rootEntity, wOut);
		createModelConvertRecords(rootEntity, wOut);

		 wOut.close();
		 
    	dirDest = ABRServerProperties.getValue(m_abri.getABRCode(), QSMFTPPATH, "/Dgq");
    	if (!dirDest.endsWith("/")){
    		dirDest=dirDest+"/";
    	}
    	
    	ffFTPPathName = dirDest + ffFileName;

    	try {
        	sourceChannel = new FileInputStream(ffPathName).getChannel();
        	destChannel = new FileOutputStream(ffFTPPathName).getChannel();
        	destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
    	} finally {
    		sourceChannel.close();
    		destChannel.close();
    	}

    }
    
    
    /*
     * Create records for ANNOUNCEMENT -> AVAIL -> MODELAVAIL -> MODEL
     * @param rootEntity the ANNOUNCEMENT rootEntity this ABR runs on
     * @param wOut OutputStreamWriter shared by all record types to generate them all on a single file
     * @throws IOException
     * @throws ParseException
     */
	private void createModelRecords(EntityItem rootEntity, OutputStreamWriter wOut) throws IOException, ParseException {
		
		StringBuffer sb;
		String strEmeabrandcd = "";
		String strFSLMCPU = "";
		String strModelSysdUnit = "";
		String strGeoModIntegratedModel = "";
		
		EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");

		String minPlanAvailDate = getEarliestPlanAvailDate(availGrp);
		String minMESAvailDate = getEarliestMESAvailDate(availGrp);
		String strAnnDate = PokUtils.getAttributeValue(rootEntity, "ANNDATE", ",", "", false);
		String strAvailAnnType = "";
		boolean isEpic = false;

		for (int availI = 0; availI < availGrp.getEntityItemCount(); availI++){
			EntityItem availEI = availGrp.getEntityItem(availI);

			strAvailAnnType = PokUtils.getAttributeValue(availEI, "AVAILANNTYPE", "", "");
			if (strAvailAnnType.equals("EPIC")){
				isEpic = true;
			}

			Vector modelVect = PokUtils.getAllLinkedEntities(availEI, "MODELAVAIL", "MODEL");

			for(int i = 0; i < modelVect.size(); i++){
				sb = new StringBuffer();
				sb.append(getValue(ISLMRFA, isEpic ? PokUtils.getAttributeValue(availEI, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(rootEntity, "ANNNUMBER", "", "")));
				EntityItem eiModel = (EntityItem) modelVect.elementAt(i);
				String description = PokUtils.getAttributeValue(eiModel, "MACHTYPEATR", "", "");
				description += PokUtils.getAttributeValue(eiModel, "MODELATR", "", "");
				sb.append(getValue(ISLMPRN, description));
				sb.append(getValue(CSLMPCI, "MM"));
				sb.append(getValue(IPRTNUM, "            "));
				sb.append(getValue(FPUNINC, "2"));
				if(strAnnDate != null && !strAnnDate.equals("")){
					sb.append(getValue(DSLMCPA, strAnnDate));
				} else {
					sb.append(getValue(DSLMCPA, "          "));
				}
				sb.append(getValue(DSLMGAD, minPlanAvailDate));
				sb.append(getValue(DSLMMES, minMESAvailDate));
				sb.append(getValue(DSLMWDN, "2050-12-31"));

				String annIndDefnCatg = PokUtils.getAttributeValue(rootEntity, "INDDEFNCATG", ",", "", false);
				if (annIndDefnCatg.length() >= 2){
					sb.append(getValue(CJLBIC1, annIndDefnCatg.substring(0, 2)));
				} else {
					sb.append(getValue(CJLBIC1, ""));
				}
				if (annIndDefnCatg.length() >= 3){
					sb.append(getValue(CJLBIDS, annIndDefnCatg.substring(2)));
				} else {
					sb.append(getValue(CJLBIDS, ""));
				}
				
				sb.append(getValue(CJLBIDT, " "));
				sb.append(getValue(CJLBOEM, PokUtils.getAttributeValue(eiModel, "SPECMODDESGN", "", "")));
				sb.append(getValue(CJLBPOF, " "));
				
				EntityGroup sgmntAcrnymGrp = m_elist.getEntityGroup("SGMNTACRNYM");
				if(sgmntAcrnymGrp != null && sgmntAcrnymGrp.hasData()){
					EntityItem sgmntAcrnymEI = sgmntAcrnymGrp.getEntityItem(0);
					sb.append(getValue(CJLBSAC, PokUtils.getAttributeValue(sgmntAcrnymEI, "ACRNYM", "", "")));
				} else {
					sb.append(getValue(CJLBSAC, "   "));
				}
				sb.append(getValue(CREFIF, strCREFIF));
				sb.append(getValue(CLASSPT, PokUtils.getAttributeValue(availEI, "AVAILANNTYPE", "", "", false)));
				sb.append(getValue(CSLMFCC, PokUtils.getAttributeValue(eiModel, "FUNCCLS", "", "")));
				sb.append(getValue(CSLMSYT, PokUtils.getAttributeValue(eiModel, "SYSTEMTYPE", "", "")));
				sb.append(getValue(CVOAT, " "));
				
				strEmeabrandcd = "";
				strGeoModIntegratedModel = "";
				String strAvailGenAreaSel = PokUtils.getAttributeValue(availEI, "GENAREASELECTION", "", "");
				String geoModGeo = "";
				EntityItem eiGeomod = null;
				Vector geoModVect = PokUtils.getAllLinkedEntities(eiModel, "MODELGEOMOD", "GEOMOD");
				if (geoModVect.size() > 0){
					for(int igm = 0; igm < geoModVect.size(); igm++){
						eiGeomod = (EntityItem)geoModVect.elementAt(igm);
						geoModGeo = PokUtils.getAttributeValue(eiGeomod, "GENAREASELECTION", "", "");
						if(geoModGeo.equals(strAvailGenAreaSel)) {
							strEmeabrandcd = PokUtils.getAttributeValue(eiGeomod, "EMEABRANDCD", "", "");
							strGeoModIntegratedModel = PokUtils.getAttributeValue(eiGeomod, "INTEGRATEDMODEL", "", "");
							igm = geoModVect.size();
						} else {
							eiGeomod = null;
						}
					}
				}

				sb.append(getValue(FBRAND, strEmeabrandcd));
				
				strFSLMCPU = "";
				strModelSysdUnit = PokUtils.getAttributeValue(eiModel, "SYSIDUNIT", "", "");
				if (strModelSysdUnit.equals("SIU-CPU")){
					strFSLMCPU = "CPU";
				} 
				sb.append(getValue(FSLMCPU, strFSLMCPU));
				sb.append(getValue(FSLMIOP, strGeoModIntegratedModel));
				sb.append(getValue(FSLMMLC, PokUtils.getAttributeValue(eiModel, "MACHLVLCNTRL", "", "")));
				sb.append(getValue(IDORIG, PokUtils.getAttributeValue(eiModel, "SPECMODDESGN", "", "")));
				sb.append(getValue(POGMES, " "));
				sb.append(getValue(STSPCFT, "    "));
				sb.append(getValue(TSLMDES, PokUtils.getAttributeValue(eiModel, "INVNAME", "", "")));
				sb.append(getValue(TSLTDES, " "));
				
				String ap = "N";
				String cn = "N";
				String emea = "N";
				String la = "N";
				String us = "N";
				
				Vector annGenAreaVec = PokUtils.getAllLinkedEntities(rootEntity, "ANNGAA", "GENERALAREA");
				for (int a = 0; a < annGenAreaVec.size(); a++){
					EntityItem annGenAreaEI = (EntityItem) annGenAreaVec.elementAt(a);
					String geo = PokUtils.getAttributeValue(annGenAreaEI, "RFAGEO", "", "");
					if (geo.equals("AP")){
						ap = "Y";
					} else if (geo.equals("CCN")) {
						cn = "Y";
					} else if (geo.equals("EMEA")) {
						emea = "Y";
					} else if (geo.equals("LA")) {
						la = "Y";
					} else if (geo.equals("US")) {
						us = "Y";
					}
				}
				sb.append(getValue(XMAP, ap));
				sb.append(getValue(XMCCN, cn));
				sb.append(getValue(XMEMEA, emea));
				sb.append(getValue(XMLA, la));
				sb.append(getValue(XMUS, us));
				
				sb.append(getValue(TIMSTMP, " "));
				sb.append(getValue(USERID, " "));
				
				sb.append(NEWLINE);
				wOut.write(sb.toString());
				wOut.flush();
				
			}
		}
		
	}

    /*
     * Create records for ANNOUNCEMENT -> AVAIL -> OOFAVAIL -> PRODSTRUCT -> FEATURE
     * @param rootEntity the ANNOUNCEMENT rootEntity this ABR runs on
     * @param wOut OutputStreamWriter shared by all record types to generate them all on a single file
     * @throws IOException
     * @throws SQLException
     * @throws MiddlewareException
     * @throws ParseException
     */
	private void createFeatureRecords(EntityItem rootEntity, OutputStreamWriter wOut) throws IOException, SQLException, MiddlewareException, ParseException {
		
		StringBuffer sb;
		String strDescription = "";
		String strModelSpecModDesgn = "";
		String strAcronym = "";
		String strEmeabrandcd = "";
		String strFSLMCPU = "";
		String strModelSysdUnit = "";
		String strGeoModIntegratedModel = "";
		String strFcType = "";

		m_elist = getEntityList(getFeatureVEName()); 

		EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");

		String minPlanAvailDate = getEarliestPlanAvailDate(availGrp);
		String minMESAvailDate = getEarliestMESAvailDate(availGrp);
		String strAnnDate = PokUtils.getAttributeValue(rootEntity, "ANNDATE", ",", "", false);
		String strAvailAnnType = "";
		boolean isEpic = false;

		for (int availI = 0; availI < availGrp.getEntityItemCount(); availI++){
			EntityItem availEI = availGrp.getEntityItem(availI);

			strAvailAnnType = PokUtils.getAttributeValue(availEI, "AVAILANNTYPE", "", "");
			if (strAvailAnnType.equals("EPIC")){
				isEpic = true;
			}

			Vector prodstructVect = PokUtils.getAllLinkedEntities(availEI, "OOFAVAIL", "PRODSTRUCT");
			
			for(int i = 0; i < prodstructVect.size(); i++){
				sb = new StringBuffer();
				sb.append(getValue(ISLMRFA, isEpic ? PokUtils.getAttributeValue(availEI, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(rootEntity, "ANNNUMBER", "", "")));
				EntityItem eiProdstruct = (EntityItem) prodstructVect.elementAt(i);
				
			    ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, getProdstructVEName());

				EntityList list = m_db.getEntityList(m_prof, eaItem, new EntityItem[] { new EntityItem(null, m_prof, eiProdstruct.getEntityType(), eiProdstruct.getEntityID())});
				  
				EntityGroup featureGrp = list.getEntityGroup("FEATURE");
				EntityGroup modelGrp = list.getEntityGroup("MODEL");
				EntityItem eiFeature = featureGrp.getEntityItem(0);
				EntityItem eiModel = modelGrp.getEntityItem(0);
				strDescription = "";
				strModelSpecModDesgn = "";
				strAcronym = "";
				strModelSpecModDesgn = PokUtils.getAttributeValue(eiModel, "SPECMODDESGN", "", "", false);
				//EntityGroup sgmntAcrnymGrp = list.getEntityGroup("SGMNTACRNYM");
				EntityGroup sgmntAcrnymGrp = m_elist.getEntityGroup("SGMNTACRNYM");
				if(sgmntAcrnymGrp != null && sgmntAcrnymGrp.hasData()){
					EntityItem sgmntAcrnymEI = sgmntAcrnymGrp.getEntityItem(0);
					strAcronym = PokUtils.getAttributeValue(sgmntAcrnymEI, "ACRNYM", "", "", false);
				}

				strDescription = PokUtils.getAttributeValue(eiModel, "MACHTYPEATR", "", "", false);
				strDescription += PokUtils.getAttributeValue(eiFeature, "FEATURECODE", "", "", false);
				sb.append(getValue(ISLMPRN, strDescription));
				
				strFcType = "";
				strFcType = PokUtils.getAttributeValue(eiFeature, "FCTYPE", "", "", false);
				if (strFcType.equals("RPQ-RLISTED") || strFcType.equals("RPQ-ILISTED") || strFcType.equals("RPQ-PLISTED")) {
					sb.append(getValue(CSLMPCI, "MQ"));
				} else {
					sb.append(getValue(CSLMPCI, "MF"));
				}
				sb.append(getValue(IPRTNUM, "            "));
				sb.append(getValue(FPUNINC, "2"));
				if(strAnnDate != null && !strAnnDate.equals("")){
					sb.append(getValue(DSLMCPA, strAnnDate));
				} else {
					sb.append(getValue(DSLMCPA, "          "));
				}
				sb.append(getValue(DSLMGAD, minPlanAvailDate));
				sb.append(getValue(DSLMMES, minMESAvailDate));
				sb.append(getValue(DSLMWDN, "2050-12-31"));
				String annIndDefnCatg = PokUtils.getAttributeValue(rootEntity, "INDDEFNCATG", ",", "", false);
				if (annIndDefnCatg.length() >= 2){
					sb.append(getValue(CJLBIC1, annIndDefnCatg.substring(0, 2)));
				} else {
					sb.append(getValue(CJLBIC1, ""));
				}
				if (annIndDefnCatg.length() >= 3){
					sb.append(getValue(CJLBIDS, annIndDefnCatg.substring(2)));
				} else {
					sb.append(getValue(CJLBIDS, ""));
				}
				sb.append(getValue(CJLBIDT, " "));
				sb.append(getValue(CJLBOEM, strModelSpecModDesgn));
				sb.append(getValue(CJLBPOF, " "));
					
				sb.append(getValue(CJLBSAC, strAcronym));
				
				sb.append(getValue(CREFIF, strCREFIF));
				
				sb.append(getValue(CLASSPT, PokUtils.getAttributeValue(availEI, "AVAILANNTYPE", "", "")));
				sb.append(getValue(CSLMFCC, PokUtils.getAttributeValue(eiModel, "FUNCCLS", "", "")));
				sb.append(getValue(CSLMSYT, PokUtils.getAttributeValue(eiModel, "SYSTEMTYPE", "", "")));
				sb.append(getValue(CVOAT, " "));
				
				strEmeabrandcd = "";
				strGeoModIntegratedModel = "";
				String strAvailGenAreaSel = PokUtils.getAttributeValue(availEI, "GENAREASELECTION", "", "");
				String geoModGeo = "";
				EntityItem eiGeomod = null;
				Vector geoModVect = PokUtils.getAllLinkedEntities(eiModel, "MODELGEOMOD", "GEOMOD");
				if (geoModVect.size() > 0){
					for(int igm = 0; igm < geoModVect.size(); igm++){
						eiGeomod = (EntityItem)geoModVect.elementAt(igm);
						geoModGeo = PokUtils.getAttributeValue(eiGeomod, "GENAREASELECTION", "", "");
						if(geoModGeo.equals(strAvailGenAreaSel)) {
							strEmeabrandcd = PokUtils.getAttributeValue(eiGeomod, "EMEABRANDCD", "", "");
							strGeoModIntegratedModel = PokUtils.getAttributeValue(eiGeomod, "INTEGRATEDMODEL", "", "");
							igm = geoModVect.size();
						} else {
							eiGeomod = null;
						}
					}
				}

				sb.append(getValue(FBRAND, strEmeabrandcd));
					
				strFSLMCPU = "";
				strModelSysdUnit = PokUtils.getAttributeValue(eiModel, "SYSIDUNIT", "", "");
				if (strModelSysdUnit.equals("SIU-CPU")){
					strFSLMCPU = "CPU";
				} 
				sb.append(getValue(FSLMCPU, strFSLMCPU));
				sb.append(getValue(FSLMIOP, strGeoModIntegratedModel));
				sb.append(getValue(FSLMMLC, PokUtils.getAttributeValue(eiModel, "MACHLVLCNTRL", "", "")));
				sb.append(getValue(IDORIG, PokUtils.getAttributeValue(eiModel, "SPECMODDESGN", "", "")));
				sb.append(getValue(POGMES, "0"));
				sb.append(getValue(STSPCFT, "    "));
				sb.append(getValue(TSLMDES, PokUtils.getAttributeValue(eiFeature, "INVNAME", "", "")));
				sb.append(getValue(TSLTDES, " "));
				
				String ap = "N";
				String cn = "N";
				String emea = "N";
				String la = "N";
				String us = "N";
				
				Vector annGenAreaVec = PokUtils.getAllLinkedEntities(rootEntity, "ANNGAA", "GENERALAREA");
				for (int a = 0; a < annGenAreaVec.size(); a++){
					EntityItem annGenAreaEI = (EntityItem) annGenAreaVec.elementAt(a);
					String geo = PokUtils.getAttributeValue(annGenAreaEI, "RFAGEO", "", "");
					if (geo.equals("AP")){
						ap = "Y";
					} else if (geo.equals("CCN")) {
						cn = "Y";
					} else if (geo.equals("EMEA")) {
						emea = "Y";
					} else if (geo.equals("LA")) {
						la = "Y";
					} else if (geo.equals("US")) {
						us = "Y";
					}
				}

				sb.append(getValue(XMAP, ap));
				sb.append(getValue(XMCCN, cn));
				sb.append(getValue(XMEMEA, emea));
				sb.append(getValue(XMLA, la));
				sb.append(getValue(XMUS, us));
				
				sb.append(getValue(TIMSTMP, " "));
				sb.append(getValue(USERID, " "));
				
				sb.append(NEWLINE);
				wOut.write(sb.toString());
				wOut.flush();

				strDescription = "";
				strModelSpecModDesgn = "";
				strAcronym = "";
				strEmeabrandcd = "";
				strFSLMCPU = "";
				strModelSysdUnit = "";
				strGeoModIntegratedModel = "";
				strFcType = "";
			}
		}
	}

    /*
     * 
     * Create records for ANNOUNCEMENT -> AVAIL -> MODELCONVERTAVAIL -> MODELCONVERT
     * @param rootEntity the ANNOUNCEMENT rootEntity this ABR runs on
     * @param wOut OutputStreamWriter shared by all record types to generate them all on a single file
     * @throws IOException
     * @throws SQLException
     * @throws MiddlewareException
     * @throws ParseException
     */
	private void createModelConvertRecords(EntityItem rootEntity, OutputStreamWriter wOut) throws IOException, SQLException, MiddlewareException, ParseException {
		
		StringBuffer sb;
		String strDescription = "";
		String strModelSpecModDesgn = "";
		String strAcronym = "";
		String strEmeabrandcd = "";
		String strFSLMCPU = "";
		String strModelSysdUnit = "";
		String strGeoModIntegratedModel = "";

		m_elist = getEntityList(getModelConvertVEName()); 

		EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");

		String minPlanAvailDate = getEarliestPlanAvailDate(availGrp);
		String minMESAvailDate = getEarliestMESAvailDate(availGrp);
		String strAnnDate = PokUtils.getAttributeValue(rootEntity, "ANNDATE", ",", "", false);
		String strAvailAnnType = "";
		boolean isEpic = false;

		for (int availI = 0; availI < availGrp.getEntityItemCount(); availI++){
			EntityItem availEI = availGrp.getEntityItem(availI);

			strAvailAnnType = PokUtils.getAttributeValue(availEI, "AVAILANNTYPE", "", "");
			if (strAvailAnnType.equals("EPIC")){
				isEpic = true;
			}

			Vector modelConvertVect = PokUtils.getAllLinkedEntities(availEI, "MODELCONVERTAVAIL", "MODELCONVERT");
			Vector modelVect = PokUtils.getAllLinkedEntities(availEI, "MODELAVAIL", "MODEL");
			EntityItem eiModel = null;
			if (modelVect.size() > 0){
				eiModel = (EntityItem) modelVect.elementAt(0);
			}

			for(int i = 0; i < modelConvertVect.size(); i++){
				sb = new StringBuffer();
				sb.append(getValue(ISLMRFA, isEpic ? PokUtils.getAttributeValue(availEI, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(rootEntity, "ANNNUMBER", "", "")));
				EntityItem eiModelConvert = (EntityItem) modelConvertVect.elementAt(i);
				
				strDescription = "";
				strModelSpecModDesgn = "";
				strAcronym = "";
				strDescription = PokUtils.getAttributeValue(eiModelConvert, "FROMMACHTYPE", "", "", false);
				strDescription += PokUtils.getAttributeValue(eiModelConvert, "FROMMODEL", "", "", false);
				strDescription += PokUtils.getAttributeValue(eiModelConvert, "TOMACHTYPE", "", "", false);
				strDescription += PokUtils.getAttributeValue(eiModelConvert, "TOMODEL", "", "", false);
				if(eiModel != null){
					strModelSpecModDesgn = PokUtils.getAttributeValue(eiModel, "SPECMODDESGN", "", "", false);
					Vector sgmntAcrnymVec = PokUtils.getAllLinkedEntities(eiModel, "MODELSGMTACRONYMA", "SGMNTACRNYM");
					if(sgmntAcrnymVec.size() > 0){
						EntityItem sgmntAcrnymEI = (EntityItem) sgmntAcrnymVec.elementAt(0);
						strAcronym = PokUtils.getAttributeValue(sgmntAcrnymEI, "ACRNYM", "", "", false);
					}
				}

				sb.append(getValue(ISLMPRN, strDescription));
				sb.append(getValue(CSLMPCI, "TM"));
				sb.append(getValue(IPRTNUM, "            "));
				sb.append(getValue(FPUNINC, "2"));
				if(strAnnDate != null && !strAnnDate.equals("")){
					sb.append(getValue(DSLMCPA, strAnnDate));
				} else {
					sb.append(getValue(DSLMCPA, "          "));
				}
				sb.append(getValue(DSLMGAD, minPlanAvailDate));
				sb.append(getValue(DSLMMES, minMESAvailDate));
				sb.append(getValue(DSLMWDN, "2050-12-31"));
				sb.append(getValue(CJLBIC1, ""));
				sb.append(getValue(CJLBIDS, ""));
				sb.append(getValue(CJLBIDT, " "));
				sb.append(getValue(CJLBOEM, strModelSpecModDesgn));
				sb.append(getValue(CJLBPOF, " "));
				sb.append(getValue(CJLBSAC, strAcronym));
				sb.append(getValue(CREFIF, strCREFIF));
				sb.append(getValue(CLASSPT, PokUtils.getAttributeValue(availEI, "AVAILANNTYPE", "", "")));

				if(eiModel != null){
					sb.append(getValue(CSLMFCC, PokUtils.getAttributeValue(eiModel, "FUNCCLS", "", "")));
					sb.append(getValue(CSLMSYT, PokUtils.getAttributeValue(eiModel, "SYSTEMTYPE", "", "")));
				} else {
					sb.append(getValue(CSLMFCC, " "));
					sb.append(getValue(CSLMSYT, " "));
				}
				
				sb.append(getValue(CVOAT, " "));
				
				strEmeabrandcd = "";
				strGeoModIntegratedModel = "";
				String strAvailGenAreaSel = PokUtils.getAttributeValue(availEI, "GENAREASELECTION", "", "");
				String geoModGeo = "";
				EntityItem eiGeomod = null;
				Vector geoModVect = PokUtils.getAllLinkedEntities(eiModel, "MODELGEOMOD", "GEOMOD");
				if (geoModVect.size() > 0){
					for(int igm = 0; igm < geoModVect.size(); igm++){
						eiGeomod = (EntityItem)geoModVect.elementAt(igm);
						geoModGeo = PokUtils.getAttributeValue(eiGeomod, "GENAREASELECTION", "", "");
						if(geoModGeo.equals(strAvailGenAreaSel)) {
							strEmeabrandcd = PokUtils.getAttributeValue(eiGeomod, "EMEABRANDCD", "", "");
							strGeoModIntegratedModel = PokUtils.getAttributeValue(eiGeomod, "INTEGRATEDMODEL", "", "");
							igm = geoModVect.size();
						} else {
							eiGeomod = null;
						}
					}
				}

				sb.append(getValue(FBRAND, strEmeabrandcd));
					
				strFSLMCPU = "";
				strModelSysdUnit = "";
				if (eiModel != null){
					strModelSysdUnit = PokUtils.getAttributeValue(eiModel, "SYSIDUNIT", "", "");
				}

				if (strModelSysdUnit.equals("SIU-CPU")){
					strFSLMCPU = "CPU";
				} 
				
				sb.append(getValue(FSLMCPU, strFSLMCPU));
				sb.append(getValue(FSLMIOP, strGeoModIntegratedModel));
				
				if (eiModel != null){
					sb.append(getValue(FSLMMLC, PokUtils.getAttributeValue(eiModel, "MACHLVLCNTRL", "", "")));
					sb.append(getValue(IDORIG, PokUtils.getAttributeValue(eiModel, "SPECMODDESGN", "", "")));
				} else {
					sb.append(getValue(FSLMMLC, ""));
					sb.append(getValue(IDORIG, ""));
				}
				
				sb.append(getValue(POGMES, " "));
				sb.append(getValue(STSPCFT, "    "));
				
				if (eiModel != null) {
					sb.append(getValue(TSLMDES, PokUtils.getAttributeValue(eiModel, "INVNAME", "", "")));
				}
				
				sb.append(getValue(TSLTDES, " "));
				
				String ap = "N";
				String cn = "N";
				String emea = "N";
				String la = "N";
				String us = "N";
				
				Vector annGenAreaVec = PokUtils.getAllLinkedEntities(rootEntity, "ANNGAA", "GENERALAREA");
				for (int a = 0; a < annGenAreaVec.size(); a++){
					EntityItem annGenAreaEI = (EntityItem) annGenAreaVec.elementAt(a);
					String geo = PokUtils.getAttributeValue(annGenAreaEI, "RFAGEO", "", "");
					if (geo.equals("AP")){
						ap = "Y";
					} else if (geo.equals("CCN")) {
						cn = "Y";
					} else if (geo.equals("EMEA")) {
						emea = "Y";
					} else if (geo.equals("LA")) {
						la = "Y";
					} else if (geo.equals("US")) {
						us = "Y";
					}
				}

				sb.append(getValue(XMAP, ap));
				sb.append(getValue(XMCCN, cn));
				sb.append(getValue(XMEMEA, emea));
				sb.append(getValue(XMLA, la));
				sb.append(getValue(XMUS, us));
				sb.append(getValue(TIMSTMP, " "));
				sb.append(getValue(USERID, " "));

				sb.append(NEWLINE);
				wOut.write(sb.toString());
				wOut.flush();

				strDescription = "";
				strModelSpecModDesgn = "";
				strAcronym = "";
				strEmeabrandcd = "";
				strFSLMCPU = "";
				strModelSysdUnit = "";
				strGeoModIntegratedModel = "";
			}
		}
		
	}

	/**
	 * Return the min AVAIL.EFFECTIVEDATE when AVAILTYPE=MES Planned Availability
	 * @param availGrp
	 * @return String
	 * @throws ParseException
	 */
	private String getEarliestMESAvailDate(EntityGroup availGrp) throws ParseException{
		
		EntityGroup mesAvailGrp = availGrp;
		Date earlierDate = null;
		Date tmpDate = null;
		String strDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		for (int i = 0; i < mesAvailGrp.getEntityItemCount(); i++){
			EntityItem eiAvail = availGrp.getEntityItem(i);
			String availType = PokUtils.getAttributeValue(eiAvail, "AVAILTYPE", "", "");
			if(availType.equals("MES Planned Availability")) {
				strDate = PokUtils.getAttributeValue(eiAvail, "EFFECTIVEDATE", ",", "", false);
				tmpDate = sdf.parse(strDate);

				if (earlierDate != null  && (tmpDate != null))
				{
					if (earlierDate.after(tmpDate))
					{
						earlierDate = tmpDate;
					}
				} 
				else if (tmpDate != null)
				{
					earlierDate = tmpDate;
				}
			}
		}

		if (earlierDate != null && !earlierDate.equals("")){
			return sdf.format(earlierDate);
		}
		
		return "2050-12-31";
	}
	
	/**
	 * Return the min AVAIL.EFFECTIVEDATE when AVAILTYPE=Planned Availability
	 * @param availGrp
	 * @return String
	 * @throws ParseException
	 */
	private String getEarliestPlanAvailDate(EntityGroup availGrp) throws ParseException{
		
		EntityGroup mesAvailGrp = availGrp;
		Date earlierDate = null;
		Date tmpDate = null;
		String strDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		for (int i = 0; i < mesAvailGrp.getEntityItemCount(); i++){
			EntityItem eiAvail = availGrp.getEntityItem(i);
			String availType = PokUtils.getAttributeValue(eiAvail, "AVAILTYPE", "", "");
			if(availType.equals("Planned Availability")) {
				strDate = PokUtils.getAttributeValue(eiAvail, "EFFECTIVEDATE", ",", "", false);
				tmpDate = sdf.parse(strDate);

				if (earlierDate != null  && (tmpDate != null))
				{
					if (earlierDate.after(tmpDate))
					{
						earlierDate = tmpDate;
					}
				} 
				else if (tmpDate != null)
				{
					earlierDate = tmpDate;
				}
			}
		}

		if (earlierDate != null && !earlierDate.equals("")){
			return sdf.format(earlierDate);
		}
		
		return "2050-12-31";
	}
	
	protected String getValue(String column, String columnValue) {
		if (columnValue == null)
			columnValue = "";
		int columnValueLength = columnValue == null ? 0 : columnValue.length();
		int columnLength = Integer.parseInt(COLUMN_LENGTH.get(column)
				.toString());
		if (columnValueLength == columnLength)
			return columnValue;
		if (columnValueLength > columnLength)
			return columnValue.substring(0, columnLength);

		return columnValue + getBlank(columnLength - columnValueLength);
	}

	protected String getBlank(int count) {
		StringBuffer sb = new StringBuffer();
		while (count > 0) {
			sb.append(" ");
			count--;
		}
		return sb.toString();
	}
    
    /***************************************************************************
     * Checks if the <b>String value</b> received via parameter already exists for the AttributeChangeHistoryGroup
     * @param com.ibm.eannounce.objects.AttributeChangeHistoryGroup achg
     * @param String value
     * @return boolean
     */
    private boolean existBefore(AttributeChangeHistoryGroup achg, String value) {
        if (achg != null) {
            for (int i = achg.getChangeHistoryItemCount() - 1; i >= 0; i--) {
                AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) achg.getChangeHistoryItem(i);
                if (achi.getFlagCode().equals(value)) {
                    return true;
                }
            }

        }
        return false;
    }
	
    /**********************************
    * add msg to report output
    */
    protected void addOutput(String msg) { rptSb.append("<p>"+msg+"</p>"+NEWLINE);}

    /**********************************
     * used for output
     *
     */
    protected void addOutput(String resrcCode, Object args[])
    {
    	String msg = getBundle().getString(resrcCode);
    	if (args != null){
    		MessageFormat msgf = new MessageFormat(msg);
    		msg = msgf.format(args);
    	}

    	addOutput(msg);
    }
    /**********************************
    * add debug info as html comment
    */
    protected void addDebug(String msg) { rptSb.append("<!-- "+msg+" -->"+NEWLINE);}

    /**********************************
    * add error info and fail abr
    */
    protected void addError(String msg) {
        addOutput(msg);
        setReturnCode(FAIL);
    }

    /**********************************
    * used for error output
    * Prefix with LD(EntityType) NDN(EntityType) of the EntityType that the ABR is checking
    * (root EntityType)
    *
    * The entire message should be prefixed with 'Error: '
    *
    */
    protected void addError(String errCode, Object args[])
    {
		EntityGroup eGrp = m_elist.getParentEntityGroup();
		setReturnCode(FAIL);

		//ERROR_PREFIX = Error: &quot;{0} {1}&quot;
		MessageFormat msgf = new MessageFormat(getBundle().getString("ERROR_PREFIX"));
		Object args2[] = new Object[2];
		args2[0] = eGrp.getLongDescription();
		args2[1] = navName;

		addMessage(msgf.format(args2), errCode, args);
	}

    /**********************************
    * used for warning or error output
    *
    */
    private void addMessage(String msgPrefix, String errCode, Object args[])
    {
		String msg = getBundle().getString(errCode);
		// get message to output
		if (args!=null){
			MessageFormat msgf = new MessageFormat(msg);
			msg = msgf.format(args);
		}

		addOutput(msgPrefix+" "+msg);
	}

    /**********************************************************************************
    *  Get Name based on navigation attributes for root entity
    *
    *@return java.lang.String
    */
    private String getNavigationName() throws java.sql.SQLException, MiddlewareException
    {
        return getNavigationName(m_elist.getParentEntityGroup().getEntityItem(0));
    }

    /**********************************************************************************
    *  Get Name based on navigation attributes for specified entity
    *
    *@return java.lang.String
    */
    private String getNavigationName(EntityItem theItem) throws java.sql.SQLException, MiddlewareException
    {
        StringBuffer navName = new StringBuffer();
        // NAME is navigate attributes
        // check hashtable to see if we already got this meta
        EANList metaList = (EANList)metaTbl.get(theItem.getEntityType());
        if (metaList==null)
        {
            EntityGroup eg = new EntityGroup(null, m_db, m_prof, theItem.getEntityType(), "Navigate");
            metaList = eg.getMetaAttribute();  // iterator does not maintain navigate order
            metaTbl.put(theItem.getEntityType(), metaList);
        }
        for (int ii=0; ii<metaList.size(); ii++)
        {
            EANMetaAttribute ma = (EANMetaAttribute)metaList.getAt(ii);
            navName.append(PokUtils.getAttributeValue(theItem, ma.getAttributeCode(),", ", "", false));
            if (ii+1<metaList.size()){
                navName.append(" ");
            }
        }

        return navName.toString();
    }

	/******************************************
	* get entitylist used for compares
	* @throws SQLException
	* @throws MiddlewareException
	*/
	private EntityList getEntityList(String veName) throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException
	{
	      ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof,veName);

	      EntityList list = m_db.getEntityList(m_prof, eaItem, new EntityItem[] { new EntityItem(null, m_prof, getEntityType(), getEntityID())});

	      // debug display list of groups
		addDebug("EntityList for "+m_prof.getValOn()+" extract "+veName+" contains the following entities: \n"+
				PokUtils.outputList(list));

		return list;
	}

	/**********************************
	 * get the name of the VE to use for MODEL records 
	 * @return java.lang.String
	 */
	public String getModelVEName() { 
		//return "QSMCREF";
		return "QSMFULL";
	}

	/**********************************
	 * get the name of the VE to use for FEATURE records - pulls PRODSTRUCT 
	 * @return java.lang.String
	 */
	public String getFeatureVEName() { 
		//return "QSMCREF1";
		return "QSMFULL";
	}

	/**********************************
	 * get the name of the VE to use for FEATURE records - returns PRODSTRUCT entities with uplink and downlink
	 * @return java.lang.String
	 */
	public String getProdstructVEName() { 
		return "QSMCREF2";
	}

	/**********************************
	 * get the name of the VE to use for MODELCONVERT records 
	 * @return java.lang.String
	 */
	public String getModelConvertVEName() { 
		return "QSMCREF3";
	}

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
        return "1.0";
    }
    /***********************************************
    *  Get ABR description
    *
    *@return java.lang.String
    */
    public String getDescription()
    {
        return "QSMCREFABRSTATUS";
    }

    /***********************************************
     *  Return history for an attribute
     *  @param attCode - AttributeCode to return history for
     *  @return com.ibm.eannounce.objects.AttributeChangeHistoryGroup
     *  @throws MiddlewareException
     */
    private AttributeChangeHistoryGroup getAttributeHistory(String attCode) throws MiddlewareException {
        EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

        EANAttribute att = rootEntity.getAttribute(attCode);
        if (att != null) {
            return new AttributeChangeHistoryGroup(m_db, m_prof, att);
        } else {
            return null;
        }
    }
    
    public boolean exeFtpShell(String fileName) {
		// String cmd =
		// "/usr/bin/rsync -av /var/log/www.solive.kv/access_log
		// testuser@10.0.1.219::store --password-file=/etc/client/rsync.pwd";

		String cmd = ABRServerProperties.getValue(m_abri.getABRCode(), FTPSCRPATH, null) + " -f " + fileName;
		String ibiinipath = ABRServerProperties.getValue(m_abri.getABRCode(), CREFINIPATH, null);
		if (ibiinipath != null)
			cmd += " -i " + ibiinipath;
		if (dir != null)
			cmd += " -d " + dir;
		if (fileprefix != null)
			cmd += " -p " + fileprefix;
		String targetFilePath = ABRServerProperties.getValue(m_abri.getABRCode(), TARGETFILENAME, null);
		if(targetFilePath!=null)
			cmd += " -t " + targetFilePath;
		String logPath = ABRServerProperties.getValue(m_abri.getABRCode(), LOGPATH, null);
		if(logPath!=null)
			cmd += " -l " + logPath;
		String backupPath = ABRServerProperties.getValue(m_abri.getABRCode(), BACKUPPATH, null);
		if(backupPath!=null)
			cmd += " -b " + backupPath;
		Runtime run = Runtime.getRuntime();
		String result = "";
		BufferedReader br = null;
		BufferedInputStream in = null;
		//addDebug("cmd:"+cmd);
		try {
			Process p = run.exec(cmd);
			if (p.waitFor() != 0) {
				return false;
			}
			in = new BufferedInputStream(p.getInputStream());
			br = new BufferedReader(new InputStreamReader(in));
			while ((lineStr = br.readLine()) != null) {
				result += lineStr;
				if (lineStr.indexOf(FAILD) > -1) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (br != null) {
				try {
					br.close();
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result == null ? false : true;
	}
}
