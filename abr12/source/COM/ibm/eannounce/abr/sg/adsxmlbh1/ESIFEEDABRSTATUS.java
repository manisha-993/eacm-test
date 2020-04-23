package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.abr.util.EACustom;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Stopwatch;
import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;

public class ESIFEEDABRSTATUS extends PokBaseABR{

	private StringBuffer rptSb = new StringBuffer();
	private StringBuffer xmlgenSb = new StringBuffer();
	private StringBuffer userxmlSb= new StringBuffer();
	private String t2DTS = "&nbsp;";  // T2
	private Object[] args = new String[10];
	private String uniqueKey = String.valueOf(System.currentTimeMillis());
	
	private static int DEBUG_LVL = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getABRDebugLevel("ESIFEEDABRSTATUS");
	private static final char[] FOOL_JTEST = {'\n'};
    protected static final String NEWLINE = new String(FOOL_JTEST);
    protected static final String OLDEFFECTDATE = "2010-03-01";
    protected static final String STATUS_FINAL = "Final";
    protected static final String CHEAT = "@@";
    public final static String RPTPATH = "_rptpath";
    
    private HashMap allRecords = new HashMap();
    private String lineStr = "";
    private static final String INIPATH = "_inipath";
   	private static final String FTPSCRPATH = "_script";
   	private static final String TARGETFILENAME = "_targetfilename";
   	private static final String LOGPATH = "_logpath";
   	private static final String BACKUPPATH = "_backuppath";
   	//private static final String MAXSIZE = "_maxsize";
   	private static final int MAXSIZE = 100000;
	protected static final Set FCTYPE_SET;
	static {
		FCTYPE_SET = new HashSet();
		FCTYPE_SET.add("RPQ-ILISTED");
		FCTYPE_SET.add("RPQ-PLISTED");
		FCTYPE_SET.add("RPQ-RLISTED");
	}
	
	protected static final String[][] country_convert_Array = {
		{"1501", "706"},//France
		{"1447", "624"},//Belgium
		{"1626", "838"},//Spain
		{"1600", "822"},//Portugal
		{"1632", "846"},//Sweden
		{"1484", "678"},//Denmark
		{"1588", "806"},//Norway
		{"1500", "702"},//Finland
		{"1532", "756"},//Israel
		{"1533", "758"},//Italy
		{"1651", "866"},//UK
		{"1531", "754"},//Ireland
		{"1578", "788"},//Netherland
		{"1624", "864"},//South Africa"
	};
	
	public void execute_run() {
		String navName = "";
		MessageFormat msgf;
		
        try {
        	long startTime = System.currentTimeMillis();
        	
			start_ABRBuild(false); // don't pull VE yet

			// Default set to pass
            setReturnCode(PASS);
             
            
			// get the root entity using current timestamp, need this to get the timestamps or info for VE pulls
			m_elist = m_db.getEntityList(m_prof, new ExtractActionItem(null, m_db, m_prof, "dummy"), 
					new EntityItem[] { new EntityItem(null, m_prof, getEntityType(), getEntityID()) });
			
			// get root MACHTYPE from VE
			EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
			
			if(isCanRun()){
				List allmachtype = getMachtype();
				for(int i = 0;i < allmachtype.size(); i++){
					addDebug("Process MACHTYPE: " + allmachtype.get(i)); 
//					EntityItem mtEntity = new EntityItem(null, m_prof, "MACHTYPE" , (int)machtypeids.get(i));
					
				    if (getReturnCode() == PASS) {          	
//						processThis(mtEntity);
						processThis((String)allmachtype.get(i));
				    }
				    
				    Iterator entrie1 = allRecords.keySet().iterator();
					while(entrie1.hasNext()){
						String ctryCode = (String) entrie1.next();
						StringBuffer records = (StringBuffer) allRecords.get(ctryCode);
				    	
						if(records.length() >= MAXSIZE){
							String dir = ABRServerProperties.getValue(m_abri.getABRCode(), RPTPATH, "/Dgq/ESI/");
							if (!dir.endsWith("/")) {
								dir = dir + "/";
							}
							String dts = getNow();
							dts = dts.replace(' ', '_');
							String filename = dir + "MSM" + ctryCode + dts + ".FULL";
							try {
								generateFile(filename, records);
								addDebug("Write to file "+filename);
								records.setLength(0);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
				    }
				    
					addDebug("Total Time: " + Stopwatch.format(System.currentTimeMillis() - startTime) + " for abr:" + getDescription());
				}
	        			
				Vector files = genOutputFile();				
				
				if(files!=null){
					for(int i=0;i<files.size();i++){
						String fileName = (String) files.get(i);
						exeFtpShell(fileName);
					}
				}
				
			}else{
				userxmlSb.append("Can not run at same time since one already run");
			}
											
            //NAME is navigate attributes
            navName = getNavigationName(rootEntity);
            addDebug("Total Time: " + Stopwatch.format(System.currentTimeMillis() - startTime));
		} catch (Throwable exc) {
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
		} finally {
			if (t2DTS.equals("&nbsp;")){
            	t2DTS= getNow();
            }
			setDGTitle(navName);
            setDGRptName(getShortClassName(getClass()));
            setDGRptClass(getABRCode());
            // make sure the lock is released
            if (!isReadOnly()) {
                clearSoftLock();
            }
		}
        
		// Print everything up to </html>
		// Insert Header into beginning of report
        println(EACustom.getDocTypeHtml());
		// must split because too many arguments for messageformat, max of 10..
		// this was 11
		String HEADER = "<head>"
				+ EACustom.getMetaTags(getDescription())
				+ NEWLINE
				+ EACustom.getCSS()
				+ NEWLINE
				+ EACustom.getTitle("{0} {1}")
				+ NEWLINE
				+ "</head>"
				+ NEWLINE
				+ "<body id=\"ibm-com\">"
				+ EACustom.getMastheadDiv()
				+ NEWLINE
				+ "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>"
				+ NEWLINE;
		msgf = new MessageFormat(HEADER);
		args[0] = getShortClassName(getClass());
		args[1] = navName;
		String header1 = msgf.format(args);
		String header2 = buildAitAbrHeader();
		String info = header1 + header2 + "<pre>"
				+ "<br />" + userxmlSb.toString() + "</pre>" + NEWLINE;
		rptSb.insert(0, info);
		println(rptSb.toString());
		printDGSubmitString();
		println(EACustom.getTOUDiv());
        buildReportFooter();
		
		// release memory
		m_elist.dereference();
		m_elist = null;
		args = null;
		msgf = null;
		userxmlSb = null;
		rptSb = null;
		xmlgenSb = null;
	}

	private boolean isCanRun() throws SQLException, MiddlewareException {
		ResultSet result = null;
		PreparedStatement statement = null;
		boolean isRun = false;
		  
		try {
			Connection conn = m_db.getPDHConnection();
				
			String sql = "select count(*) from opicm.flag where entitytype='ESIREPORT' and attributecode='ESIFEEDABRSTATUS' and attributevalue='0050' and valto>current timestamp and effto>current timestamp with ur";
			
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();		
											
			while(result.next()){
							
				int count = result.getInt(1);	
				
				if(count==1){
					isRun = true;
				}
			}
		}finally {
			if (statement != null){
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return isRun;
	}

	public double showMemory() {
		System.gc();
		Runtime runtime = Runtime.getRuntime();
//		addDebug("TotMem:" + runtime.totalMemory() + ", FreeMem:" + runtime.freeMemory() +", UsedMem:" + ( runtime.totalMemory() - runtime.freeMemory())/1000/1000 +"M" );
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(0);
		double percent = runtime.freeMemory()*1.0/runtime.totalMemory();
		addDebug("Free percent: "+nf.format(percent));
		return percent;
	}
	
	public void processThis(String machtype) throws MiddlewareException, SQLException, InterruptedException{
		
		if(machtype != null && !machtype.equals(null)){
			Iterator iter = getDataList(machtype).entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String entityid = (String) entry.getKey();
				String entitytype = (String) entry.getValue();
				
				if(entitytype.equals("M")){
					Vector ctrylist = extractMODCtry(entityid);
					for(int ii=0;ii<country_convert_Array.length;ii++){
			        	if(ctrylist.contains(country_convert_Array[ii][0])){
			        		genRecords(entityid, country_convert_Array[ii][1], "M");
			        	}
			        }
				}else if(entitytype.equals("SF")){
					Vector ctrylist = extractSTMFCtry(entityid);
					for(int ii=0;ii<country_convert_Array.length;ii++){
			        	if(ctrylist.contains(country_convert_Array[ii][0])){
			        		genRecords(entityid, country_convert_Array[ii][1], "SF");
			        	}
			        }
				}else if(entitytype.equals("R")){
					Vector ctrylist = extractRPQCtry(entityid);
					for(int ii=0;ii<country_convert_Array.length;ii++){
			        	if(ctrylist.contains(country_convert_Array[ii][0])){
			        		genRecords(entityid, country_convert_Array[ii][1], "R");
			        	}
			        }
				}else if(entitytype.equals("HF")){
					Vector ctrylist = extractTMFCtry(entityid);
					for(int ii=0;ii<country_convert_Array.length;ii++){
			        	if(ctrylist.contains(country_convert_Array[ii][0])){
			        		genRecords(entityid, country_convert_Array[ii][1], "F");
			        	}
			        }
				}
			}
		addDebug("Processing machtype: "+machtype);
		}
	}
	
	private Vector extractTMFCtry(String entityid) throws MiddlewareException, SQLException {
		
		Vector availVct = getTMFAvailCty(entityid);
		if(availVct.size() == 0){
			return extractRPQCtry(entityid);
		}else{
			//The aggregated (UNION) of the countries found for TMF linked Availability(AVAIL)Country List(COUNTRYLIST)for all Availability Type(AVAILTYPE)
			//that matches the STATUS Final filtering criteria and type equals Plan Avail.
			return availVct;
		}		
	}

	private Vector extractRPQCtry(String entityid) throws MiddlewareException, SQLException {
		
		Vector countryList = new Vector();		
		//case for the PRODSTRUCT that get the avail from the MODEL
		Vector plnAvlVct = getTMFLMODAvailCty(entityid);
		if(plnAvlVct.size() != 0){
			//generate COUNTRYLIST for each country in the intersection of 
			//1. MODEL: MODELAVAIL-d: AVAIL.COUNTRYLIST where AVAILTYPE = “Planned Availability”
			//2. FEATURE.COUNTRYLIST.
			Vector feaCtry = getTMFLFEACty(entityid);
			countryList = genCtryList(feaCtry, plnAvlVct);

			return countryList;		
		}else{
			// If the MODEL does not have an AVAIL of type “Planned Availability”, then use FEATURE.COUNTRYLIST.
			return getTMFLFEACty(entityid);
		}
	}

	private Vector genCtryList(Vector feaCtry, Vector plnAvlVct) {
		
		Vector ctry = new Vector();
		for (int i = 0; i < plnAvlVct.size(); i++) {
			String availCountry = (String) plnAvlVct.get(i);
			if (availCountry != null && !availCountry.equals(null)) {
				if (feaCtry.contains(availCountry)) {
					ctry.add(availCountry);
				} 
			}
		}
		return ctry;
	}


	private Vector extractSTMFCtry(String entityid) throws MiddlewareException, SQLException {
		
		Vector availVct = getSTMFAvailCty(entityid);
		if(availVct.size() == 0){
			//case for the SWPRODSTRUCT that get the avail from the MODEL
			Vector plnAvlVct = getSTMFLMODAvailCty(entityid);
			if(plnAvlVct.size() != 0){
				//SWPRODSTRUCT don't nedd to consider FEATURE.COUNTRYLIST.
				return plnAvlVct;
			}else{
				// If the MODEL does not have an AVAIL of type “Planned Availability”, then use WorldWide COUNTRYLIST.
				return genWorldWideCtryList();
			}
		}else{
			//The aggregated (UNION) of the countries found for TMF linked Availability(AVAIL)Country List(COUNTRYLIST)for all Availability Type(AVAILTYPE)
			//that matches the STATUS Final filtering criteria and type equals Plan Avail.
			return availVct;
		}	
	}


	private Vector extractMODCtry(String entityid) throws MiddlewareException, SQLException {
		
		Vector availVct = getMODAvailCty(entityid);
		if(availVct.size() == 0){
			// If the MODEL does not have an AVAIL of type “Planned Availability”, then use WorldWide COUNTRYLIST.
			return genWorldWideCtryList();
		}else{
			//The aggregated (UNION) of the countries found for MODEL linked Availability(AVAIL)Country List(COUNTRYLIST)for all Availability Type(AVAILTYPE)
			//that matches the STATUS Final filtering criteria and type equals Plan Avail.
			return availVct;
		}		
	}


	private HashMap getDataList(String machtype) throws MiddlewareException, SQLException {
		
		ResultSet result = null;
		PreparedStatement statement = null;
		HashMap entity = new HashMap();
		  
		try {
			Connection conn = m_db.getODS2Connection();
				
			String sql = "select distinct M.entityid as MODID, "
					+" case when M.COFCAT='Hardware' then P.entityid end as TMFID, F.FCTYPE, "
					+" case when M.COFCAT='Software' then S.entityid end as STMFID "
					+" from opicm.machtype T "
					+" join opicm.model M on M.machtypeatr=T.machtypeatr and M.nlsid=1 and M.STATUS='Final' and M.COFCAT in ('Hardware','Software') "
					+" left join opicm.relator R1 on R1.entity2id=M.entityid and R1.entitytype='PRODSTRUCT' "
					+" left join opicm.relator R2 on R2.entity2id=M.entityid and R2.entitytype='SWPRODSTRUCT' " 
					+" left join opicm.prodstruct P on P.entityid=R1.entityid and P.nlsid=1 and P.STATUS='Final' " 
					+" left join opicm.feature F on F.entityid=R1.entity1id and F.nlsid=1 and F.STATUS='Final' "
					+" left join opicm.swprodstruct S on S.entityid=R2.entityid and S.nlsid=1 and S.STATUS='Final' " 
					+" where T.machtypeatr='"+ machtype +"' with ur";
			
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();		
			
//			addDebug("getDataList:"+sql);

			while(result.next()){				
				
				if(result.getString(1) != null && !result.getString(1).equals(null)){
					String modelid = result.getString(1);
					entity.put(modelid, "M");
				}
				if(result.getString(2) != null && !result.getString(2).equals(null) && result.getString(3) != null && !result.getString(3).equals(null)){
					String tmfid = result.getString(2);
					String fcType = result.getString(3);
					if(FCTYPE_SET.contains(fcType)){
						entity.put(tmfid, "R");
					}else{
						entity.put(tmfid, "HF");
					}
				}else if(result.getString(4) !=null && !result.getString(4).equals(null)){
					String tmfid = result.getString(4);
					entity.put(tmfid, "SF");
				}						
			}
		}finally {
			if (statement != null){
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}	
		return entity;	
	}
	
	private Vector getTMFAvailCty(String tmfid) throws MiddlewareException, SQLException {
		
		ResultSet result = null;
		PreparedStatement statement = null;
		Vector ctry = new Vector();	
		
		try {
			Connection conn = m_db.getODS2Connection();
			
			String sql = "select  A1.entityid,dbms_lob.substr(replace(replace(xml2clob(xmlagg(xmlelement(NAME A, trim(F1.attributevalue)||','))),'<A>',''),'</A>','')) as country"
					+" from opicm.prodstruct P join opicm.relator R1 on R1.entity1type = P.entitytype and R1.entity1id = P.entityid"
					+" join opicm.AVAIL A1 on R1.entity2type = A1.entitytype and R1.entity2id = A1.entityid"
					+" join opicm.flag F1 on F1.entitytype = A1.entitytype and F1.entityid = A1.entityid"
					+" where P.entityid = " + tmfid + " and P.nlsid = 1 and P.STATUS = 'Final' and R1.entitytype = 'OOFAVAIL' and A1.nlsid = 1 and A1.AVAILTYPE = 'Planned Availability' "
					+" and A1.STATUS in  ('Final','Ready for Review') and F1.attributecode = 'COUNTRYLIST'"
//					+" AND 1=(case when exists(select  A.entityid, F.attributevalue "
//					+" from opicm.relator R  join opicm.AVAIL A on R.entity2type = A.entitytype and R.entity2id = A.entityid"
//					+" join opicm.flag F on F.entitytype = A.entitytype and F.entityid = A.entityid"
//					+" where R.entity1type = P.entitytype and R.entity1id = P.entityid and F.attributecode = 'COUNTRYLIST'  and  R.entitytype = 'OOFAVAIL' and A.nlsid = 1 and A.AVAILTYPE = 'Planned Availability'" 
//					+" and A.STATUS in  ('Final','Ready for Review')) then '1' else '0' end) "
					+" group by a1.entityid with ur";
			
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();		
			
//			addDebug("getTMFAvailCty:"+sql);

			while(result.next()){			
				String availCountry = result.getString(2);
				if (availCountry != null && !availCountry.equals(null)) {
					availCountry.replaceAll("<A>","").replaceAll("</A>", "");
					String[] availCtry = splitStr(availCountry, ",");
					for(int i=0;i<availCtry.length;i++){
						if (!ctry.contains(availCtry[i])) {
							ctry.add(availCtry[i]);
						} 
					}
				}
			}
			
		}finally {
			if (statement != null){
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		addDebug("TMF linked avail country: " + ctry);

		return ctry;
	}

	private Vector getTMFLMODAvailCty(String tmfid) throws MiddlewareException, SQLException {
		
		ResultSet result = null;
		PreparedStatement statement = null;
		Vector ctry = new Vector();	
		
		try {
			Connection conn = m_db.getODS2Connection();
			
			String sql = "select A1.entityid,dbms_lob.substr(replace(replace(xml2clob(xmlagg(xmlelement(NAME A, trim(F1.attributevalue)||','))),'<A>',''),'</A>','')) as country"
					+" from opicm.prodstruct P"
					+" join opicm.relator R1 on R1.entitytype=P.entitytype and R1.entityid=P.entityid"
					+" join opicm.model m on m.entitytype=R1.entity2type and m.entityid=R1.entity2id"
					+" join opicm.relator R2 on R2.entitytype='MODELAVAIL' and R2.entity1id=m.entityid"
					+" join opicm.AVAIL A1 on R2.entity2type = A1.entitytype and R2.entity2id = A1.entityid"
					+" join opicm.flag F1 on F1.entitytype = A1.entitytype and F1.entityid = A1.entityid"
					+" where p.entityid= " + tmfid + " and P.nlsid = 1 and P.STATUS = 'Final' and m.nlsid = 1 and m.STATUS = 'Final'"
					+" and A1.nlsid = 1 and A1.AVAILTYPE = 'Planned Availability' and A1.STATUS in ('Final','Ready for Review') and F1.attributecode = 'COUNTRYLIST'"
//					+" AND 1=(case when exists(select  A.entityid, F.attributevalue" 
//					+" from opicm.relator R  join opicm.AVAIL A on R.entity2type = A.entitytype and R.entity2id = A.entityid"
//					+" join opicm.flag F on F.entitytype = A.entitytype and F.entityid = A.entityid"
//					+" where R.entity1type = m.entitytype and R.entity1id = m.entityid and F.attributecode = 'COUNTRYLIST'  and  R.entitytype = 'MODELAVAIL' and A.nlsid = 1 and A.AVAILTYPE = 'Planned Availability'" 
//					+" and A.STATUS in  ('Final','Ready for Review')) then '1' else '0' end)" 
					+" group by a1.entityid with ur";
			
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();		
				
//			addDebug("getTMFLinkMODAvailCty:"+sql);

			while(result.next()){			
				String availCountry = result.getString(2);
				if (availCountry != null && !availCountry.equals(null)) {
					availCountry.replaceAll("<A>","").replaceAll("</A>", "");
					String[] availCtry = splitStr(availCountry, ",");
					for(int i=0;i<availCtry.length;i++){
						if (!ctry.contains(availCtry[i])) {
							ctry.add(availCtry[i]);
						} 
					}
				}
			}
			
		}finally {
			if (statement != null){
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		addDebug("TMF linked Model's avail country: " + ctry);

		return ctry;
	}
	
	private Vector getTMFLFEACty(String tmfid) throws MiddlewareException, SQLException {
		
		ResultSet result = null;
		PreparedStatement statement = null;
		Vector feaCtry = new Vector();	
		
		try {
			Connection conn = m_db.getODS2Connection();
			
			String sql = "select F1.entityid,dbms_lob.substr(replace(replace(xml2clob(xmlagg(xmlelement(NAME A, trim(F1.attributevalue)||','))),'<A>',''),'</A>','')) as country"
					+" from opicm.prodstruct P"
					+" join opicm.relator R1 on R1.entitytype=P.entitytype and R1.entityid=P.entityid"
					+" join opicm.feature F on F.entitytype=R1.entity1type and F.entityid=R1.entity1id and F.nlsid=1"
					+" join opicm.flag F1 on  F1.entitytype = F.entitytype and F1.entityid = F.entityid and F1.attributecode = 'COUNTRYLIST' "
					+" where p.entityid= " + tmfid + " and P.nlsid = 1 and P.STATUS = 'Final' and F.nlsid=1 and F.STATUS='Final' group by F1.entityid with ur";
			
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();		
			
//			addDebug("getTMFLinkFeatureCty:"+sql);

			while(result.next()){			
				String availCountry = result.getString(2);
				if (availCountry != null && !availCountry.equals(null)) {
					availCountry.replaceAll("<A>","").replaceAll("</A>", "");
					String[] availCtry = splitStr(availCountry, ",");
					for(int i=0;i<availCtry.length;i++){
						if (!feaCtry.contains(availCtry[i])) {
							feaCtry.add(availCtry[i]);
						} 
					}
				}
			}
			
		}finally {
			if (statement != null){
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		addDebug("TMF linked Feature's country: " + feaCtry);

		return feaCtry;
	}

	private Vector getSTMFAvailCty(String tmfid) throws MiddlewareException, SQLException {
		
		ResultSet result = null;
		PreparedStatement statement = null;
		Vector ctry = new Vector();	
		
		try {
			Connection conn = m_db.getODS2Connection();
			
			String sql = "select A1.entityid,dbms_lob.substr(replace(replace(xml2clob(xmlagg(xmlelement(NAME A, trim(F1.attributevalue)||','))),'<A>',''),'</A>','')) as country"
					+" from opicm.swprodstruct P join opicm.relator R1 on R1.entity1type = P.entitytype and R1.entity1id = P.entityid"
					+" join opicm.AVAIL A1 on R1.entity2type = A1.entitytype and R1.entity2id = A1.entityid"
					+" join opicm.flag F1 on F1.entitytype = A1.entitytype and F1.entityid = A1.entityid"
					+" where P.entityid = " + tmfid + " and P.nlsid = 1 and P.STATUS = 'Final' and R1.entitytype = 'SWPRODSTRUCTAVAIL' and A1.nlsid = 1 and A1.AVAILTYPE = 'Planned Availability'" 
					+" and A1.STATUS in ('Final','Ready for Review') and F1.attributecode = 'COUNTRYLIST'"
//					+" AND 1=(case when exists(select  A.entityid, F.attributevalue" 
//					+" from opicm.relator R  join opicm.AVAIL A on R.entity2type = A.entitytype and R.entity2id = A.entityid"
//					+" join opicm.flag F on F.entitytype = A.entitytype and F.entityid = A.entityid"
//					+" where R.entity1type = P.entitytype and R.entity1id = P.entityid and F.attributecode = 'COUNTRYLIST'  and  R.entitytype = 'SWPRODSTRUCTAVAIL' and A.nlsid = 1 and A.AVAILTYPE = 'Planned Availability'" 
//					+" and A.STATUS in ('Final','Ready for Review')) then '1' else '0' end)"
					+" group by a1.entityid with ur";
			
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();		
				
//			addDebug("getSTMFAvailCty:"+sql);

			while(result.next()){			
				String availCountry = result.getString(2);
				if (availCountry != null && !availCountry.equals(null)) {
					availCountry.replaceAll("<A>","").replaceAll("</A>", "");
					String[] availCtry = splitStr(availCountry, ",");
					for(int i=0;i<availCtry.length;i++){
						if (!ctry.contains(availCtry[i])) {
							ctry.add(availCtry[i]);
						} 
					}
				}
			}
			
		}finally {
			if (statement != null){
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		addDebug("SWTMF linked avail country: " + ctry);
		return ctry;
	}

	private Vector getSTMFLMODAvailCty(String tmfid) throws MiddlewareException, SQLException {
		
		ResultSet result = null;
		PreparedStatement statement = null;
		Vector ctry = new Vector();	
		
		try {
			Connection conn = m_db.getODS2Connection();
			
			String sql = "select A1.entityid,dbms_lob.substr(replace(replace(xml2clob(xmlagg(xmlelement(NAME A, trim(F1.attributevalue)||','))),'<A>',''),'</A>','')) as country"
					+" from opicm.swprodstruct P"
					+" join opicm.relator R1 on R1.entitytype=P.entitytype and R1.entityid=P.entityid"
					+" join opicm.model m on m.entitytype=R1.entity2type and m.entityid=R1.entity2id"
					+" join opicm.relator R2 on R2.entitytype='MODELAVAIL' and R2.entity1id=m.entityid"
					+" join opicm.AVAIL A1 on R2.entity2type = A1.entitytype and R2.entity2id = A1.entityid"
					+" join opicm.flag F1 on F1.entitytype = A1.entitytype and F1.entityid = A1.entityid"
					+" where p.entityid= " + tmfid + " and P.nlsid = 1 and P.STATUS = 'Final' and m.nlsid = 1 and m.STATUS = 'Final'"
					+" and A1.nlsid = 1 and A1.AVAILTYPE = 'Planned Availability' and A1.STATUS in ('Final','Ready for Review') and F1.attributecode = 'COUNTRYLIST'"
//					+" AND 1=(case when exists(select  A.entityid, F.attributevalue "
//					+" from opicm.relator R  join opicm.AVAIL A on R.entity2type = A.entitytype and R.entity2id = A.entityid"
//					+" join opicm.flag F on F.entitytype = A.entitytype and F.entityid = A.entityid"
//					+" where R.entity1type = m.entitytype and R.entity1id = m.entityid and F.attributecode = 'COUNTRYLIST'  and  R.entitytype = 'MODELAVAIL' and A.nlsid = 1 and A.AVAILTYPE = 'Planned Availability'" 
//					+" and A.STATUS in ('Final','Ready for Review')) then '1' else '0' end) "
					+" group by a1.entityid with ur";
			
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();		
				
//			addDebug("getSTMFLinkMODAvailCty:"+sql);
			while(result.next()){			
				String availCountry = result.getString(2);
				if (availCountry != null && !availCountry.equals(null)) {
					availCountry.replaceAll("<A>","").replaceAll("</A>", "");
					String[] availCtry = splitStr(availCountry, ",");
					for(int i=0;i<availCtry.length;i++){
						if (!ctry.contains(availCtry[i])) {
							ctry.add(availCtry[i]);
						} 
					}
				}
			}
			
		}finally {
			if (statement != null){
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		addDebug("SWTMF linked Model's avail country: " + ctry);
		return ctry;
	}
	
	private Vector getMODAvailCty(String modelid) throws MiddlewareException, SQLException {
		
		ResultSet result = null;
		PreparedStatement statement = null;
		Vector ctry = new Vector();	
		
		try {
			Connection conn = m_db.getODS2Connection();
			
			String sql = "select A1.entityid,dbms_lob.substr(replace(replace(xml2clob(xmlagg(xmlelement(NAME A, trim(F1.attributevalue)||','))),'<A>',''),'</A>','')) as country"
					+" from opicm.model m" 
					+" join opicm.relator R2 on R2.entitytype='MODELAVAIL' and R2.entity1id=m.entityid"
					+" join opicm.AVAIL A1 on R2.entity2type = A1.entitytype and R2.entity2id = A1.entityid"
					+" join opicm.flag F1 on F1.entitytype = A1.entitytype and F1.entityid = A1.entityid"
					+" where m.entityid= " + modelid + " and m.nlsid = 1 and m.STATUS = 'Final'"
					+" and A1.nlsid = 1 and A1.AVAILTYPE = 'Planned Availability' and A1.STATUS in ('Final','Ready for Review') and F1.attributecode = 'COUNTRYLIST'"
					+" group by a1.entityid with ur";
			
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();		
				
//			addDebug("getMODAvailCty:"+sql);
			while(result.next()){			
				String availCountry = result.getString(2);
				if (availCountry != null && !availCountry.equals(null)) {
					availCountry.replaceAll("<A>","").replaceAll("</A>", "");
					String[] availCtry = splitStr(availCountry, ",");
					for(int i=0;i<availCtry.length;i++){
						if (!ctry.contains(availCtry[i])) {
							ctry.add(availCtry[i]);
						} 
					}
				}
			}
			
			
		}finally {
			if (statement != null){
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		addDebug("Model's avail country: " + ctry);
		return ctry;
	}

	private List getMachtype() throws MiddlewareException, SQLException {
	
		ResultSet result = null;
		PreparedStatement statement = null;
		List machtypeInfo = new ArrayList();
		  
		try {
			Connection conn = m_db.getPDHConnection();
				
			String sql = "select attributevalue from opicm.flag where entitytype='MACHTYPE' and attributecode='MACHTYPEATR' and valto>current timestamp and effto>current timestamp order by attributevalue with ur";
//			String sql = "select attributevalue from opicm.flag where entitytype='MACHTYPE' and attributecode='MACHTYPEATR' and valto>current timestamp and effto>current timestamp and attributevalue in ('5639','5641','5663','5664','5722','5771','5799') with ur";
			
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();		
											
			while(result.next()){
							
				String machtype = result.getString(1);										
				machtypeInfo.add(machtype.trim());
			}
		}finally {
			if (statement != null){
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		addDebug("all machtype: " + machtypeInfo.toString());
		return machtypeInfo;
	
	}
	
	private Vector genOutputFile(){
		if(allRecords.size()==0){
			userxmlSb.append("File generate failed, for there is no qualified data related");
			return null;
		}
		else{	
			try{
				Iterator entrie1 = allRecords.entrySet().iterator();
				Vector files = new Vector(1);
				while(entrie1.hasNext()){
					Map.Entry entry1 = (Map.Entry) entrie1.next();
					StringBuffer records = (StringBuffer) entry1.getValue();					
					//file name need change
					String dir = ABRServerProperties.getValue(m_abri.getABRCode(), RPTPATH, "/Dgq/ESI/");
					if (!dir.endsWith("/")) {
						dir = dir + "/" ;
					}
					String dts = getNow();
					dts = dts.replace(' ', '_');
					String fileName = dir + "MSM" + entry1.getKey() + dts + ".FULL";					
					addDebug("filename:"+fileName);
					boolean createfile = generateFile(fileName, records);
					boolean addDummy = generatedummyRecords(fileName, entry1.getKey().toString());
					if (createfile && addDummy){
						files.add(fileName);
					}
				}
				
				return files;
				
			}catch(Exception e){			
				e.printStackTrace();
				return null;
			}
		}	
	}		

	private boolean generatedummyRecords(String fileName, String ctry) throws Exception {
		File file = new File(fileName);
		if(file.exists()){
			FileWriter writer = new FileWriter(file, true);
			writer.write(ctry + "SOFTADV   MSOFTWARE CONTRACT             2        ");
			writer.close();
			return true;
		}else{
			return false;
		}
	}
	
	private boolean generateFile(String fileName, StringBuffer sb) throws Exception {
		File file = new File(fileName);
		if(file.exists()){
			FileWriter writer = new FileWriter(file, true);
			writer.write(sb.toString());
			writer.close();
			return true;
		}else{
			file.createNewFile();
			FileOutputStream wOut=new FileOutputStream(file,true);
			try{
				wOut.write(sb.toString().getBytes("UTF-8"));
				userxmlSb.append(fileName +" generate success \n");
				return true;
			}catch(IOException e){
				userxmlSb.append(e);
				throw new Exception("File create failed " + fileName);
			}finally {
				wOut.flush();
				wOut.close();
				try {
					if (wOut != null)
						wOut.close();					
				} catch (Exception e) {
					throw new Exception("File create failed " + fileName);
				}	
			}
		}
	}
	
	private void genRecords(String entityid, String ctryCode, String CHSIUTC) throws MiddlewareException, SQLException {
		
		String sql = null;
		StringBuffer sb = new StringBuffer();
		String IOPUCTY = ctryCode;
		String CSLMTYP = null;
		String IHSIKEY = null;
		String TSLMDES = null;
		String CMKTDEV = null;
		String TSLMFLG = " ";
		String FSLMSWI = " ";
		String FILLER = "      ";
				
		ResultSet result = null;
		PreparedStatement statement = null;
		  
		try {
			Connection conn = m_db.getODS2Connection();
				
			if("F".equals(CHSIUTC)||"R".equals(CHSIUTC)){
				
				sql = "select M.machtypeatr, F.featurecode, F.invname"
					+" from opicm.relator R"
					+" join opicm.feature F on F.entitytype=R.entity1type and F.entityid=R.entity1id and F.nlsid=1"
					+" join opicm.model M on  M.entitytype = R.entity2type and M.entityid = R.entity2id and M.nlsid=1"
					+" where R.entityid=" + entityid + " and R.entitytype='PRODSTRUCT' and M.STATUS = 'Final' and F.STATUS='Final' with ur";
				
				statement = conn.prepareStatement(sql);
				result = statement.executeQuery();
				while(result.next()){
					
					CSLMTYP = fixLength(result.getString(1), 4);
					IHSIKEY = fixLength(result.getString(2), 6);
					String temp = result.getString(3);
					if(temp!=null && !temp.equals(null))
						temp = temp.replaceAll("\r|\n|\t", "");	
					TSLMDES = fixLength(temp, 30);
					
				}
				CMKTDEV = " ";
				
			}else if("M".equals(CHSIUTC)){
				
				sql = "select m.machtypeatr, m.modelatr,m.invname,m.cofcat from opicm.model m where m.entityid= "
					+ entityid +" and m.nlsid=1 with ur";
				
				statement = conn.prepareStatement(sql);
				result = statement.executeQuery();
				while(result.next()){
					
					CSLMTYP = fixLength(result.getString(1), 4);
					IHSIKEY = fixLength(result.getString(2), 6);
					String temp = result.getString(3);
					if(temp!= null && !temp.equals(null))
						temp = temp.replaceAll("\r|\n|\t", "");	
					TSLMDES = fixLength(temp, 30);
					String modelType = result.getString(4);
					if("Software".equals(modelType)){//software
						CMKTDEV = "2";
					}else if("Hardware".equals(modelType)){//hardware
						CMKTDEV = "5";
					}
				}
				
			}else if("SF".equals(CHSIUTC)){
				sql = "select M.machtypeatr, F.featurecode, F.invname"
					+" from opicm.relator R"
					+" join opicm.swfeature F on F.entitytype=R.entity1type and F.entityid=R.entity1id and F.nlsid=1"
					+" join opicm.model M on  M.entitytype = R.entity2type and M.entityid = R.entity2id and M.nlsid=1"
					+" where R.entityid= " + entityid + " and R.entitytype='SWPRODSTRUCT' and M.STATUS = 'Final' and F.STATUS='Final' with ur";
			
				statement = conn.prepareStatement(sql);
				result = statement.executeQuery();		
											
				while(result.next()){
					
					CSLMTYP = fixLength(result.getString(1), 4);
					IHSIKEY = fixLength(result.getString(2), 6);
					String temp = result.getString(3);
					if(temp!= null && !temp.equals(null))
						temp = temp.replaceAll("\r|\n|\t", "");	
					TSLMDES = fixLength(temp, 30);					
				}
				CMKTDEV = " ";
				CHSIUTC = "F";
			}
		}finally {
			if (statement != null){
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
			
		if(CSLMTYP!=null && !CSLMTYP.equals(null)) {
			sb.append(IOPUCTY);
			sb.append(CSLMTYP);
			sb.append(IHSIKEY);
			sb.append(CHSIUTC);
			sb.append(TSLMDES);
			sb.append(CMKTDEV);
			sb.append(TSLMFLG);
			sb.append(FSLMSWI);
			sb.append(FILLER);
			sb.append(NEWLINE);
		}
		
		
//		addDebug("records: " + sb.toString());
		
		if(allRecords.get(ctryCode)!=null){
			StringBuffer ar = (StringBuffer)allRecords.get(ctryCode);						
			//if(ar.length()>=Integer.parseInt(MAXSIZE.trim())){		
			if(ar.toString().indexOf(sb.toString())<=-1){
				ar.append(sb);
				//addDebug("generate records");
			}
			
		}else{
			allRecords.put(ctryCode, sb);
		}
		
	}

	private String fixLength(String text, int width) {
		if(text == null){
			text = "";
		}
		if(text.length() >= width){
			return text.substring(0, width);
		}else{
			String term = "";
			for(int i=0;i<width-text.length();i++){
				term += " ";
			}
			text = text + term;
		}		
		return text;
	}
		
	private Vector genWorldWideCtryList() {
		//only need include EMEA country in this abr
		Vector countrylist = new Vector();
		for(int i=0;i<country_convert_Array.length;i++){
			countrylist.add(country_convert_Array[i][0]);
		}
		return countrylist;
	}

	public String[] splitStr(String str, String delim) {
        StringTokenizer stringTokenizer = new StringTokenizer( str, delim );
        String[] strArr = new String[stringTokenizer.countTokens()];
        int i = 0;
        while( stringTokenizer.hasMoreTokens() ) {
            strArr[i] = stringTokenizer.nextToken();
            i++ ;
        }
        return strArr;
    } 
	
	public String getDescription() {
		return "ESIFEEDABRSTATUS";
	}

	public String getABRVersion() {
		return "1.0";
	}

	/**
     * add error info and fail abr
     */
    protected void addError(String msg) {
        addOutput(msg);
        setReturnCode(FAIL);
    }
    
	protected void addDebug(String msg) {
		if (D.EBUG_DETAIL <= DEBUG_LVL) {
			rptSb.append("<!-- " + msg + " -->" + NEWLINE);
		}
	}
    
	/**
	 * add msg as an html comment if meets debuglevel set in abr.server.properties
	 * @param debuglvl
	 * @param msg
	 */
	protected void addDebugComment(int debuglvl, String msg){
		if (debuglvl <= DEBUG_LVL) {
			rptSb.append("<!-- "+msg+" -->"+NEWLINE);
		}
	}
    
    protected EntityList getEntityList(Profile prof, String veName, EntityItem item) throws MiddlewareRequestException, SQLException, MiddlewareException {
		EntityList list = m_db.getEntityList(prof, new ExtractActionItem(null, m_db, prof, veName), 
				new EntityItem[] { new EntityItem(null, prof, item.getEntityType(), item.getEntityID()) });
		return list;
    }
    
	/**
	 * get database
	 */
	protected Database getDB() {
		return m_db;
	}
	
	protected String getABRTime() {
		return uniqueKey;
	}
	
    /**********************************
     * add error info and fail abr
     * @throws MiddlewareException 
     * @throws SQLException 
     */
    protected void addError(String msg, EntityItem item) throws SQLException, MiddlewareException {
    	String headmsg = getLD_NDN(item); 
        addOutput(headmsg + " " + msg);
        setReturnCode(FAIL);
    }
    
    protected String getLD_NDN(EntityItem item) throws SQLException, MiddlewareException    {
		return item.getEntityGroup().getLongDescription()+" &quot;"+getNavigationName(item)+"&quot;";
	}
	/**
	 * add msg to report output
	 */
	private void addOutput(String msg) {
		rptSb.append("<p>" + msg + "</p>" + NEWLINE);
	}

	
	/**
	 * EACM to AIT Triggered ABRs
	 *
	 * The Report should identify: 
	 * - USERID (USERTOKEN) 
	 * - Role 
	 * - Workgroup 
	 * - Date/Time
	 * - Action Taken
	 */
	private String buildAitAbrHeader() {
		String HEADER2 = "<table>"+NEWLINE +
		"<tr><th>Userid: </th><td>{0}</td></tr>"+NEWLINE +
		"<tr><th>Role: </th><td>{1}</td></tr>"+NEWLINE +
		"<tr><th>Workgroup: </th><td>{2}</td></tr>"+NEWLINE +
		"<tr><th>Date/Time: </th><td>{3}</td></tr>"+NEWLINE +
        "<tr><th>Action Taken: </th><td>{4}</td></tr>"+NEWLINE+
        "</table>"+NEWLINE+
        "<!-- {5} -->" + NEWLINE;
        MessageFormat msgf = new MessageFormat(HEADER2);
        args[0] = m_prof.getOPName();
        args[1] = m_prof.getRoleDescription();
        args[2] = m_prof.getWGName();
        args[3] = t2DTS;
        args[4] = "SOF feed trigger<br/>" + xmlgenSb.toString();
        args[5] = getABRVersion();
        return msgf.format(args);
	}
	
	/**
	 * Get Name based on navigation attributes
	 *
	 * @return java.lang.String
	 */
	private String getNavigationName(EntityItem theItem) throws java.sql.SQLException, MiddlewareException {
		StringBuffer navName = new StringBuffer();
		// NAME is navigate attributes
		EntityGroup eg = new EntityGroup(null, m_db, m_prof, theItem.getEntityType(), "Navigate");
		EANList metaList = eg.getMetaAttribute(); // iterator does not maintain navigate order
		for (int ii = 0; ii < metaList.size(); ii++) {
			EANMetaAttribute ma = (EANMetaAttribute) metaList.getAt(ii);
			navName.append(PokUtils.getAttributeValue(theItem, ma.getAttributeCode(), ", ", "", false));
			navName.append(" ");
		}
		return navName.toString();
	}
	
	 public boolean exeFtpShell(String fileName) {
			// String cmd =
			// "/usr/bin/rsync -av /var/log/www.solive.kv/access_log
			// testuser@10.0.1.219::store --password-file=/etc/client/rsync.pwd";

		String dir = ABRServerProperties.getValue(m_abri.getABRCode(), "/Dgq");
		String fileprefix = ABRServerProperties.getFilePrefix(m_abri.getABRCode());
		String cmd = ABRServerProperties.getValue(m_abri.getABRCode(), FTPSCRPATH, null) + " -f " + fileName;
		String inipath = ABRServerProperties.getValue(m_abri.getABRCode(), INIPATH, null);
		
		if (inipath != null)
			cmd += " -i " + inipath;
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
				if (lineStr.indexOf("FAILD") > -1) {
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

