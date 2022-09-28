package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.CharacterIterator;
import java.text.MessageFormat;
import java.text.StringCharacterIterator;
import java.util.Hashtable;


import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.abr.sg.rfc.RdhClassificationMaint;
import COM.ibm.eannounce.abr.sg.rfc.RdhMatmCreate;
import COM.ibm.eannounce.abr.sg.rfc.RdhTssFcProd;
import COM.ibm.eannounce.abr.sg.rfc.RdhTssMatChar;
import COM.ibm.eannounce.abr.sg.rfc.SVCMOD;
import COM.ibm.eannounce.abr.sg.rfc.UpdateParkStatus;
import COM.ibm.eannounce.abr.sg.rfc.XMLParse;
import COM.ibm.eannounce.abr.util.EACustom;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareException;

public class SVCMODIERPABRSTATUS extends PokBaseABR {
	private StringBuffer rptSb = new StringBuffer();
	private static final char[] FOOL_JTEST = { '\n' };
	static final String NEWLINE = new String(FOOL_JTEST);
	private int abr_debuglvl = D.EBUG_ERR;
	private String navName = "";
	private Hashtable metaTbl = new Hashtable();
	private String CACEHSQL = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'SVCMOD' and XMLENTITYID = ?  and XMLCACHEVALIDTO > current timestamp with ur";
	String xml = null;

	

	public String getDescription() {
		// TODO Auto-generated method stub
		return "PIABRSTATUS";
	}

	public String getABRVersion() {
		// TODO Auto-generated method stub

		return "1.0";
	}

	public void execute_run() {
		String HEADER = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE
				+ EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">"
				+ EACustom.getMastheadDiv() + NEWLINE
				+ "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
		String HEADER2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE
				+ "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>"
				+ NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE
				+ "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->"
				+ NEWLINE;

		String header1 = "";
	

		MessageFormat msgf;
		String abrversion = "";
		String rootDesc = "";

		Object[] args = new String[10];

		try {
			msgf = new MessageFormat(HEADER);
			args[0] = getShortClassName(getClass());
			args[1] = "ABR";
			header1 = msgf.format(args);
			setDGTitle("SVCMODIERPABRSTATUS report");
			setDGString(getABRReturnCode());
			setDGRptName("SVCMODIERPABRSTATUS"); // Set the report name
			setDGRptClass("SVCMODIERPABRSTATUS"); // Set the report class
			// Default set to pass
			setReturnCode(PASS);

			start_ABRBuild(false); // pull the VE

			abr_debuglvl = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties
					.getABRDebugLevel(m_abri.getABRCode());

			// get the root entity using current timestamp, need this to get the
			// timestamps or info for VE pulls
			  m_elist = m_db.getEntityList(m_prof,
                    new ExtractActionItem(null, m_db, m_prof,"dummy"),
                    new EntityItem[] { new EntityItem(null, m_prof, getEntityType(), getEntityID()) });
			/*
			 * m_db.getEntityList(m_prof, new ExtractActionItem(null, m_db,
			 * m_prof,"dummy"), new EntityItem[] { new EntityItem(null, m_prof,
			 * getEntityType(), getEntityID()) });
			 */

			EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
			addDebug("*****mlm rootEntity = " + rootEntity.getEntityType() + rootEntity.getEntityID());
			// NAME is navigate attributes - only used if error rpt is generated
			navName = getNavigationName();
			rootDesc = m_elist.getParentEntityGroup().getLongDescription();
			addDebug("navName=" + navName);
			addDebug("rootDesc" + rootDesc);
			// build the text file

			Connection connection = m_db.getODSConnection();
			PreparedStatement statement = connection.prepareStatement(CACEHSQL);
			statement.setInt(1, rootEntity.getEntityID());
			ResultSet resultSet = statement.executeQuery();
		
			while (resultSet.next()) {
				xml = resultSet.getString("XMLMESSAGE");
			}
			if (xml != null) {
			
					SVCMOD svcmod = XMLParse.getSvcmodFromXml(xml);
					RdhMatmCreate create = new RdhMatmCreate(svcmod);
					this.addDebug("Calling " + create.getRFCName());
					create.execute();
					this.addDebug(create.createLogEntry());
					if (create.getRfcrc() == 0) {
						this.addOutput(create.getRFCName() + " called successfully!");
					} else {
						this.addOutput(create.getRFCName() + " called  faild!");
						this.addOutput(create.getError_text());
					}

					String obj_id = svcmod.getMACHTYPE() + svcmod.getMODEL();
					String class_name = "MG_COMMON";
					String class_type = "001";
					RdhClassificationMaint cMaint = new RdhClassificationMaint(obj_id, class_name, class_type, obj_id);

					this.addDebug("Calling " + cMaint.getRFCName()+" ID="+obj_id+" NAME="+class_name+" type="+class_type);

					String type = "MG_PRODUCTTYPE";
					String tableData = getTableMapingDate(type, svcmod);
					this.addDebug("addCharacteristic for type="+type+" value=" +tableData );
					if (tableData != null && !"No characteristic".equals(tableData)) {	
						this.addDebug("addCharacteristic for type="+type+" value=" +tableData );
						cMaint.addCharacteristic(type, tableData);
					}else {
						this.addDebug("addCharacteristic for type="+type+" value=" +"" );
						cMaint.addCharacteristic(type, "");
					} // No characteristic
					cMaint.execute();
					this.addDebug(cMaint.createLogEntry());
					if (cMaint.getRfcrc() == 0) {
						this.addOutput(cMaint.getRFCName() + " called successfully!");
					} else {
						this.addOutput(cMaint.getRFCName() + " called  faild!");
						this.addOutput(cMaint.getError_text());
					}
					class_name = "MM_CUSTOM_SERVICES";
					cMaint = new RdhClassificationMaint(obj_id, class_name, class_type, obj_id);
					type = "MM_CUSTOM_TYPE";
					tableData = getTableMapingDate(type, svcmod);
					if (tableData != null && !"No characteristic".equals(tableData)) {
						this.addDebug("addCharacteristic for type="+type+" value=" +tableData );
						cMaint.addCharacteristic(type, tableData);
					}else {
						this.addDebug("addCharacteristic for type="+type+" value=" +"" );
						cMaint.addCharacteristic(type, "");
					}
					type = "MM_CUSTOM_COSTING";
					tableData = getTableMapingDate(type, svcmod);
					
					if (tableData != null && !"No characteristic".equals(tableData)) {
						this.addDebug("addCharacteristic for type="+type+" value=" +tableData );
						cMaint.addCharacteristic(type, tableData);
					}else {
						this.addDebug("addCharacteristic for type="+type+" value=" +"" );
						cMaint.addCharacteristic(type, "");
					}
					type = "MM_PROFIT_CENTER";
					tableData = getTableMapingDate(type, svcmod);
					if (tableData != null && !"No characteristic".equals(tableData)) {
						this.addDebug("addCharacteristic for type="+type+" value=" +tableData );
						cMaint.addCharacteristic(type, tableData);
					}else {
						this.addDebug("addCharacteristic for type="+type+" value=" +"" );
						cMaint.addCharacteristic(type, "");
					}
					type = "MM_TAX_CATEGORY";
					tableData = getTableMapingDate(type, svcmod);
					
					if (tableData != null && !"No characteristic".equals(tableData)) {
						this.addDebug("addCharacteristic for type="+type+" value=" +tableData );
						cMaint.addCharacteristic(type, tableData);
					}else {
						this.addDebug("addCharacteristic for type="+type+" value=" +"" );
						cMaint.addCharacteristic(type, "");
					}
					cMaint.execute();
					this.addDebug(cMaint.createLogEntry());
					if (cMaint.getRfcrc() == 0) {
						this.addOutput(cMaint.getRFCName() + " called successfully!");
					} else {
						this.addOutput(cMaint.getRFCName() + " called  faild!");
						this.addOutput(cMaint.getError_text());
					}

					/*
					 * class_name = "MM_CUSTOM_COSTING"; cMaint = new RdhClassificationMaint(obj_id,
					 * class_name, class_type);
					 */
					
					/*
					 * cMaint.execute(); this.addDebug(cMaint.createLogEntry()); if
					 * (cMaint.getRfcrc() == 0) { this.addOutput(cMaint.getRFCName() +
					 * " called successfully!"); } else { this.addOutput(cMaint.getRFCName() +
					 * " called  faild!"); this.addOutput(cMaint.getError_text()); }
					 */
					class_name = "MM_FIELDS";
					cMaint = new RdhClassificationMaint(obj_id, class_name, class_type, obj_id);
					
					if ("Yes".equals(svcmod.getSOPRELEVANT())) {
						type = "MM_SOP_IND";
						this.addDebug("addCharacteristic for type="+type+" value=" +"X" );
						cMaint.addCharacteristic(type, "X");
					}
					type = "MM_TASK_TYPE";
					this.addDebug("addCharacteristic for type="+type+" value=" +svcmod.getSOPTASKTYPE() );
					cMaint.addCharacteristic(type, svcmod.getSOPTASKTYPE());
					type = "MM_OPPORTUNITY_CODE";
					this.addDebug("addCharacteristic for type="+type+" value=" +svcmod.getWWOCCODE() );
					cMaint.addCharacteristic(type, svcmod.getWWOCCODE());
					cMaint.execute();
					this.addDebug(cMaint.createLogEntry());

					if (cMaint.getRfcrc() == 0) {
						this.addOutput(cMaint.getRFCName() + " called successfully!");
						this.addOutput(cMaint.getError_text());
					} else {
						this.addOutput(cMaint.getRFCName() + " called  faild!");
						this.addOutput(cMaint.getError_text());
					}
					/*
					 * cMaint.execute(); this.addDebug(cMaint.createLogEntry()); if
					 * (cMaint.getRfcrc() == 0) { this.addOutput(cMaint.getRFCName() +
					 * " called successfully!"); this.addOutput(cMaint.getError_text()); } else {
					 * this.addOutput(cMaint.getRFCName() + " called  faild!");
					 * this.addOutput(cMaint.getError_text()); }
					 */
					/*if (svcmod.hasProds()) {
						RdhTssMatChar chart = new RdhTssMatChar(svcmod);
						this.addDebug("Calling " + chart.getRFCName());
						chart.execute();
						this.addDebug(chart.createLogEntry());
						if (chart.getRfcrc() == 0) {
							this.addOutput(chart.getRFCName() + " called successfully!");
							this.addOutput(chart.getError_text());
						} else {
							this.addOutput(chart.getRFCName() + " called  faild!");
							this.addOutput(chart.getError_text());
						}
					}
			*/
				
				UpdateParkStatus updateParkStatus = new UpdateParkStatus("MD_TSS_IERP", svcmod.getMACHTYPE() + svcmod.getMODEL());
				this.addDebug("Calling "+updateParkStatus.getRFCName());
				updateParkStatus.execute();
				this.addDebug(updateParkStatus.createLogEntry());

				if (updateParkStatus.getRfcrc() == 0) {
					this.addOutput(updateParkStatus.getRFCName() + " called successfully!");
					this.addOutput(updateParkStatus.getError_text());
				} else {
					this.addOutput(updateParkStatus.getRFCName() + " called  faild!");
					this.addOutput(updateParkStatus.getError_text());
				}
				this.addDebug("Check if RdhTssFcProd can run:");
				
				
			/*	RdhTssFcProd rdhTssFcProd = new RdhTssFcProd(svcmod);
				this.addDebug("Can run:"+rdhTssFcProd.canRun());
				if(rdhTssFcProd.canRun()) {
				this.addDebug("Calling "+rdhTssFcProd.getRFCName());
				rdhTssFcProd.execute();
				this.addDebug(rdhTssFcProd.createLogEntry());

				if (rdhTssFcProd.getRfcrc() == 0) {
					this.addOutput(rdhTssFcProd.getRFCName() + " called successfully!");
					this.addOutput(rdhTssFcProd.getError_text());
				} else {
					this.addOutput(rdhTssFcProd.getRFCName() + " called  faild!");
					this.addOutput(rdhTssFcProd.getError_text());
				}
				}else {
					this.addDebug("skip "+rdhTssFcProd.getRFCName());
					
				}*/
			} else {
				this.addOutput("XML file not exist in cache,RFC caller not called!");
				//return;
			}
			
			
			
			// exeFtpShell(ffPathName);
			// ftpFile();
			/*
			 * } catch (Exception e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); this.addError(e.getMessage()); setReturnCode(FAIL); }
			 */
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// println(e.toString());
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
			
			// sentFile=exeFtpShell(ffPathName);
		} finally {
			StringBuffer sb = new StringBuffer();
			msgf = new MessageFormat(HEADER2);
			args[0] = m_prof.getOPName();
			args[1] = m_prof.getRoleDescription();
			args[2] = m_prof.getWGName();
			args[3] = getNow();
			args[4] = sb.toString();
			args[5] = abrversion + " " + getABRVersion();
			rptSb.insert(0, convertToHTML(xml)+NEW_LINE);
			rptSb.insert(0, header1 + msgf.format(args) + NEWLINE);

			println(EACustom.getDocTypeHtml()); // Output the doctype and html
			println(rptSb.toString()); // Output the Report
			printDGSubmitString();
			 if(!isReadOnly()) {
	                clearSoftLock();
	            }
			println(EACustom.getTOUDiv());
			buildReportFooter(); // Print </html>
		}
	}

	


	

	/*
	 * Get Name based on navigation attributes for root entity
	 *
	 * @return java.lang.String
	 */
	private String getNavigationName() throws java.sql.SQLException, MiddlewareException {
		return getNavigationName(m_elist.getParentEntityGroup().getEntityItem(0));
	}

	/**********************************************************************************
	 * Get Name based on navigation attributes for specified entity
	 *
	 * @return java.lang.String
	 */
	private String getNavigationName(EntityItem theItem) throws java.sql.SQLException, MiddlewareException {
		StringBuffer navName = new StringBuffer();
		// NAME is navigate attributes
		// check hashtable to see if we already got this meta
		EANList metaList = (EANList) metaTbl.get(theItem.getEntityType());
		if (metaList == null) {
			EntityGroup eg = new EntityGroup(null, m_db, m_prof, theItem.getEntityType(), "Navigate");
			metaList = eg.getMetaAttribute(); // iterator does not maintain
												// navigate order
			metaTbl.put(theItem.getEntityType(), metaList);
		}
		for (int ii = 0; ii < metaList.size(); ii++) {
			EANMetaAttribute ma = (EANMetaAttribute) metaList.getAt(ii);
			navName.append(PokUtils.getAttributeValue(theItem, ma.getAttributeCode(), ", ", "", false));
			if (ii + 1 < metaList.size()) {
				navName.append(" ");
			}
		}
		return navName.toString();
	}
	private String getTableMapingDate(String key, SVCMOD svcmod) {
		String value = null;
		if ("MG_PRODUCTTYPE".equals(key)) {
			if ("Service".equals(svcmod.getCATEGORY())) {
				if ("Custom".equals(svcmod.getSUBCATEGORY())) {
					if ("Project Based".equals(svcmod.getGROUP()) || "Operation Based".equals(svcmod.getGROUP())) {
						value = "S3";
					}
				} else if ("Facility".equals(svcmod.getSUBCATEGORY())) {
					if ("Penalty".equals(svcmod.getGROUP())) {
						value = "S6";
					} else if ("Incident".equals(svcmod.getGROUP())) {
						value = "S7";
					} else if ("Travel".equals(svcmod.getGROUP())) {
						value = "S5";
					} else if ("Activity".equals(svcmod.getGROUP())) {
						value = "S4";
					} else if ("OEM".equals(svcmod.getGROUP())) {
						value = "S4";
					}
					else if ("ICA/NEC".equals(svcmod.getGROUP())) {
						value = "S2";
					}

				} else if ("Productized Services".equals(svcmod.getSUBCATEGORY())) {
					if ("Non-Federated".equals(svcmod.getGROUP())) {
						value = "S2";
					}
				}
			} else if ("IP".equals(svcmod.getCATEGORY())) {
				if ("SC".equals(svcmod.getSUBCATEGORY())) {
					value = "S8";
				}
			}
		} else if ("MM_CUSTOM_TYPE".equals(key)) {

			if ("Service".equals(svcmod.getCATEGORY())) {
				if ("Custom".equals(svcmod.getSUBCATEGORY())) {
					if ("Project Based".equals(svcmod.getGROUP()) || "Operation Based".equals(svcmod.getGROUP())) {
						value = "OCI";
					}
				} else if ("Facility".equals(svcmod.getSUBCATEGORY())) {
					if ("Penalty".equals(svcmod.getGROUP())) {
						value = "PC";
					} else if ("Incident".equals(svcmod.getGROUP())) {
						value = "IC";
					} else if ("Travel".equals(svcmod.getGROUP())) {
						value = "TE";
					} else if ("Activity".equals(svcmod.getGROUP())) {
						value = "SA";
					}else if ("OEM".equals(svcmod.getGROUP())) {
						value = "SA";
					} else if ("ICA/NEC".equals(svcmod.getGROUP())) {
						value = "No characteristic";
					}

				} else if ("Productized Services".equals(svcmod.getSUBCATEGORY())) {
					if ("Non-Federated".equals(svcmod.getGROUP())) {
						value = "SPI";
					}
				}
			} else if ("IP".equals(svcmod.getCATEGORY())) {
				if ("SC".equals(svcmod.getSUBCATEGORY())) {
					value = "IPSC";
				}
			}

		} else if ("MM_CUSTOM_COSTING".equals(key)) {

			if ("Service".equals(svcmod.getCATEGORY())) {
				if ("Custom".equals(svcmod.getSUBCATEGORY())) {
					if ("Project Based".equals(svcmod.getGROUP()) || "Operation Based".equals(svcmod.getGROUP())) {
						value = "WBS";
					}
				} else if ("Facility".equals(svcmod.getSUBCATEGORY())) {
					if ("Penalty".equals(svcmod.getGROUP())) {
						value = "WBS";
					} else if ("Incident".equals(svcmod.getGROUP())) {
						value = "WBS";
					} else if ("Travel".equals(svcmod.getGROUP())) {
						value = "WBS";
					} else if ("Activity".equals(svcmod.getGROUP())) {
						value = "WBS";
					}else if ("OEM".equals(svcmod.getGROUP())) {
						value = "WBS";
					} else if ("ICA/NEC".equals(svcmod.getGROUP())) {
						value = "No characteristic";
					}

				} else if ("Productized Services".equals(svcmod.getSUBCATEGORY())) {
					if ("Non-Federated".equals(svcmod.getGROUP())) {
						value = "WBS";
					}
				}
			} else if ("IP".equals(svcmod.getCATEGORY())) {
				if ("SC".equals(svcmod.getSUBCATEGORY())) {
					value = "No characteristic";
				}
			}

		} else if ("MM_PROFIT_CENTER".equals(key)) {

			if ("Service".equals(svcmod.getCATEGORY())) {
				if ("Custom".equals(svcmod.getSUBCATEGORY())) {
					if ("Project Based".equals(svcmod.getGROUP()) || "Operation Based".equals(svcmod.getGROUP())) {
						value = "D";
					}
				} else if ("Facility".equals(svcmod.getSUBCATEGORY())) {
					if ("Penalty".equals(svcmod.getGROUP())) {
						value = "C";
					} else if ("Incident".equals(svcmod.getGROUP())) {
						value = "C";
					} else if ("Travel".equals(svcmod.getGROUP())) {
						value = "C";
					} else if ("Activity".equals(svcmod.getGROUP())) {
						value = "C";
					}else if ("OEM".equals(svcmod.getGROUP())) {
						value = "C";
					} else if ("ICA/NEC".equals(svcmod.getGROUP())) {
						value = "No characteristic";
					}

				} else if ("Productized Services".equals(svcmod.getSUBCATEGORY())) {
					if ("Non-Federated".equals(svcmod.getGROUP())) {
						value = "C";
					}
				}
			} else if ("IP".equals(svcmod.getCATEGORY())) {
				if ("SC".equals(svcmod.getSUBCATEGORY())) {
					value = "No characteristic";
				}
			}

		} else if ("MM_TAX_CATEGORY".equals(key)) {

			if ("Service".equals(svcmod.getCATEGORY())) {
				if ("Custom".equals(svcmod.getSUBCATEGORY())) {
					if ("Project Based".equals(svcmod.getGROUP()) || "Operation Based".equals(svcmod.getGROUP())) {
						value = "D";
					}
				} else if ("Facility".equals(svcmod.getSUBCATEGORY())) {
					if ("Penalty".equals(svcmod.getGROUP())) {
						value = "C";
					} else if ("Incident".equals(svcmod.getGROUP())) {
						value = "C";
					} else if ("Travel".equals(svcmod.getGROUP())) {
						value = "C";
					} else if ("Activity".equals(svcmod.getGROUP())) {
						value = "C";
					}else if ("OEM".equals(svcmod.getGROUP())) {
						value = "C";
					} else if ("ICA/NEC".equals(svcmod.getGROUP())) {
						value = "No characteristic";
					}

				} else if ("Productized Services".equals(svcmod.getSUBCATEGORY())) {
					if ("Non-Federated".equals(svcmod.getGROUP())) {
						value = "C";
					}
				}
			} else if ("IP".equals(svcmod.getCATEGORY())) {
				if ("SC".equals(svcmod.getSUBCATEGORY())) {
					value = "No characteristic";
				}
			}

		}
		// MM_PROFIT_CENTER
		return value;
	}
	
	 /********************************************************************************
     * Convert string into valid html.  Special HTML characters are converted.
     *
     * @param txt    String to convert
     * @return String
     */
    protected static String convertToHTML(String txt)
    {
        String retVal="";
        StringBuffer htmlSB = new StringBuffer();
        StringCharacterIterator sci = null;
        char ch = ' ';
        if (txt != null) {
            sci = new StringCharacterIterator(txt);
            ch = sci.first();
            while(ch != CharacterIterator.DONE)
            {
                switch(ch)
                {
                case '<':
                    htmlSB.append("&lt;");
                break;
                case '>':
                    htmlSB.append("&gt;");
                    break;
                case '"':
                    // double quotation marks could be saved as &quot; also. this will be &#34;
                    // this should be included too, but left out to be consistent with west coast
                    htmlSB.append("&quot;");
                    break;
                case '\'':
                    //IE6 doesn't support &apos; to convert single quotation marks,we can use &#39; instead
                    htmlSB.append("&#"+((int)ch)+";");
                    break;
                    //case '&': 
                    // ignore entity references such as &lt; if user typed it, user will see it
                    // could be saved as &amp; also. this will be &#38;
                    //htmlSB.append("&#"+((int)ch)+";");
                    //  htmlSB.append("&amp;");
                    //    break;
                default:
                    htmlSB.append(ch);
                break;
                }
                ch = sci.next();
            }
            retVal = htmlSB.toString();
        }

        return retVal;
    }
    
	 protected void addOutput(String msg) { rptSb.append("<p>"+msg+"</p>"+NEWLINE);}


		/**********************************
	     * add debug info as html comment
	     *    EBUG_ERR = 0;
	          EBUG_WARN = 1;
	          EBUG_INFO = 2;
	          EBUG_DETAIL = 3;
	          EBUG_SPEW = 4
	     */
	   
	protected void addDebug(String msg) {
		if (D.EBUG_DETAIL <= abr_debuglvl) {
		rptSb.append("<!-- " + msg + " -->" + NEWLINE);
		}
	}
	 /**********************************
     * add error info and fail abr
     */
    protected void addError(String msg) {
        addOutput(msg);
        setReturnCode(FAIL);
    }

	
}
