package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.CharacterIterator;
import java.text.MessageFormat;
import java.text.StringCharacterIterator;
import java.util.Hashtable;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.abr.sg.rfc.Chw001ClfCreate;
import COM.ibm.eannounce.abr.sg.rfc.ChwCharMaintain;
import COM.ibm.eannounce.abr.sg.rfc.ChwClassMaintain;
import COM.ibm.eannounce.abr.sg.rfc.ChwConpMaintain;
import COM.ibm.eannounce.abr.sg.rfc.ChwDepdMaintain;
import COM.ibm.eannounce.abr.sg.rfc.ChwMachTypeMtc;
import COM.ibm.eannounce.abr.sg.rfc.ChwMachTypeUpg;
import COM.ibm.eannounce.abr.sg.rfc.ChwMatmCreate;
import COM.ibm.eannounce.abr.sg.rfc.MODEL;
import COM.ibm.eannounce.abr.sg.rfc.RdhChwFcProd;
import COM.ibm.eannounce.abr.sg.rfc.RdhClassificationMaint;
import COM.ibm.eannounce.abr.sg.rfc.RdhSvcMatmCreate;
import COM.ibm.eannounce.abr.sg.rfc.RdhTssFcProd;
import COM.ibm.eannounce.abr.sg.rfc.SVCMOD;
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

public class MODELIERPABRSTATUS extends PokBaseABR {
	private StringBuffer rptSb = new StringBuffer();
	private static final char[] FOOL_JTEST = { '\n' };
	static final String NEWLINE = new String(FOOL_JTEST);
	private int abr_debuglvl = D.EBUG_ERR;
	private String navName = "";
	private Hashtable metaTbl = new Hashtable();
	private String CACEHSQL = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'MODEL' and XMLENTITYID = ?  and XMLCACHEVALIDTO > current timestamp with ur";
	private String COVEQUALSQL="SELECT count(*) FROM OPICM.flag F\n"
			+ "INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.ATTRIBUTEVALUE ='?' AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP "
			+ "INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP "
			+ "WHERE f.ENTITYTYPE ='MODELCONVERT' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELCONVERTIERPABRSTATUS')  AND T1.ATTRIBUTEVALUE =T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' WITH UR"
			;
	private String COVNOTEQUALSQL="SELECT count(*) FROM OPICM.flag F\n"
			+ "INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.ATTRIBUTEVALUE ='?' AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP "
			+ "INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP "
			+ "WHERE f.ENTITYTYPE ='MODELCONVERT' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELCONVERTIERPABRSTATUS')  AND T1.ATTRIBUTEVALUE !=T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' WITH UR"
			;
	private String FCTEQUALSQL="SELECT count(*) FROM OPICM.flag F\n"
			+ "INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.ATTRIBUTEVALUE ='?' AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP "
			+ "INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP "
			+ "WHERE f.ENTITYTYPE ='FCTRANSACTION' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'FCTRANSACTIONIERPABRSTATUS')  AND T1.ATTRIBUTEVALUE =T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' WITH UR"
			;
	String xml = null;

	

	public String getDescription() {
		// TODO Auto-generated method stub
		return "MODELIERPABRSTATUS";
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
			setDGTitle("MODELIERPABRSTATUS report");
			setDGString(getABRReturnCode());
			setDGRptName("MODELIERPABRSTATUS"); // Set the report name
			setDGRptClass("MODELIERPABRSTATUS"); // Set the report class
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
			
				MODEL model = XMLParse.getObjectFromXml(xml,MODEL.class);
				
				if("Hardware".equals(model.getCATEGORY())) {
					processMachTypeNew(model, connection);
					processMachTypeMODEL(model, connection);
					
					
					/**
					 * 
            If there is a MODELCONVERT which meets all of conditions below,
                tomachtype = chwProduct.machineType
                frommachtype !=tomachtype
                past passed ADSABRSTATUS or MODELCONVERTIERPABRSTATUS
            then execute the steps described in the document MachTypeMTC RDH Feed to iERP to populate data elements for MachineTypeMTC material.
					 */
			
					if(exist(COVNOTEQUALSQL, model.getMACHTYPE())) {
						ChwMachTypeMtc chwMachTypeMtc =new ChwMachTypeMtc(model, m_db.getPDHConnection(), connection);
						this.addDebug("Calling " + "ChwMachTypeMtc");
						try{
							chwMachTypeMtc.execute();
							this.addMsg(chwMachTypeMtc.getRptSb());
						}catch (Exception e) {
							this.addMsg(chwMachTypeMtc.getRptSb());
							throw e;
						}
					}
					if(!"Maintenance,MaintFeature".contains(model.getSUBCATEGORY())) {
						
						if(exist(COVEQUALSQL, model.getMACHTYPE())||exist(FCTEQUALSQL, model.getMACHTYPE())) {
							ChwMachTypeUpg chwMachTypeUpg = new ChwMachTypeUpg(model, m_db.getPDHConnection(), connection);
							this.addDebug("Calling " + "ChwMachTypeMtc");
							try{
								chwMachTypeUpg.execute();
								this.addMsg(chwMachTypeUpg.getRptSb());
							}catch (Exception e) {
								this.addMsg(chwMachTypeUpg.getRptSb());
								throw e;
							}
						}
					}else if("M,B".contains(model.getORDERCODE())) {
						processMachTypeUpg(model, connection);	
					}
					
					
					
				}
				else if("Service".equals(model.getCATEGORY())) {
					processMachTypeMODEL_Svc(model, connection);
				}
				else if ("SoftdWare".equals(model.getCATEGORY())) {
					throw new Exception("Not support SoftWare");
				}
			RdhChwFcProd prod = new RdhChwFcProd(model);
			this.addDebug("Calling " + prod.getRFCName());
			prod.execute();
			this.addDebug(prod.createLogEntry());
			if (prod.getRfcrc() == 0) {
				this.addOutput(prod.getRFCName() + " called successfully!");
			} else {
				this.addOutput(prod.getRFCName() + " called  faild!");
				this.addOutput(prod.getError_text());
			}
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
			rptSb.append(convertToTag(msgf.format(args)) + NEWLINE);
			msgf = new MessageFormat(Error_STACKTRACE);
			args[0] = exBuf.getBuffer().toString();
			rptSb.append(convertToTag(msgf.format(args)) + NEWLINE);
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
    
    /********************************************************************************
     * Convert string into valid html.  Special HTML characters are converted.
     *
     * @param txt    String to convert
     * @return String
     */
    protected static String convertToTag(String txt)
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
	 
	 protected void addMsg(StringBuffer msg) { rptSb.append(msg.toString()+NEWLINE);}


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

    public void processMachTypeMODEL (MODEL model,Connection odsConnection) throws Exception {
    	String materialType="ZPRT";
    	String  materialID =model.getMACHTYPE()+model.getMODEL();
    	
    	ChwMatmCreate caller = new ChwMatmCreate(model, materialType, materialID);
    	this.addDebug("Calling " + caller.getRFCName());
    	caller.execute();
		this.addDebug(caller.createLogEntry());
		if (caller.getRfcrc() == 0) {
			this.addOutput(caller.getRFCName() + " called successfully!");
		} else {
			this.addOutput(caller.getRFCName() + " called  faild!");
			this.addOutput(caller.getError_text());
		}
		//Chw001ClfCreate 
		
		Chw001ClfCreate chw001ClfCreate = new Chw001ClfCreate(model, materialType,materialID, odsConnection); 
		this.addDebug("Calling " + "Chw001ClfCreate");
		try{
			chw001ClfCreate.execute();
			this.addMsg(chw001ClfCreate.getRptSb());
		}catch (Exception e) {
			this.addMsg(chw001ClfCreate.getRptSb());
			throw e;
		}
		 
		
		String obj_id=model.getMACHTYPE()+model.getMODEL();
		String dep_extern="PR_"+model.getMACHTYPE()+"_SET_MODEL";
		String dep_type="7"; 
		String descript=model.getMACHTYPE()+" Set Model";
		String sourceLine = "$self.mk_model2 =$self.mk_t_"+model.getMACHTYPE()+"_mod";
		ChwDepdMaintain chwDepdCaller	=new ChwDepdMaintain(obj_id, dep_extern, dep_type, descript)	;
		chwDepdCaller.addSourceLineCondition(sourceLine);
		this.addDebug("Calling " + chwDepdCaller.getRFCName());
		chwDepdCaller.execute();
		this.addDebug(chwDepdCaller.createLogEntry());
		if (chwDepdCaller.getRfcrc() == 0) {
			this.addOutput(chwDepdCaller.getRFCName() + " called successfully!");
		} else {
			this.addOutput(chwDepdCaller.getRFCName() + " called  faild!");
			this.addOutput(chwDepdCaller.getError_text());
		}
		dep_extern = "SC_"+model.getMACHTYPE()+"_MOD_"+model.getMODEL();
		dep_type="5"; 
		descript="SC_"+model.getMACHTYPE()+"_MOD_"+model.getMODEL();
		sourceLine = "$PARENT.MK_T_"+model.getMACHTYPE()+"_MOD='"+model.getMODEL()+"'";
		 chwDepdCaller	=new ChwDepdMaintain(xml, dep_extern, dep_type, descript)	;
		chwDepdCaller.addSourceLineCondition(sourceLine);
		this.addDebug("Calling " + chwDepdCaller.getRFCName());
		chwDepdCaller.execute();
		this.addDebug(chwDepdCaller.createLogEntry());
		if (chwDepdCaller.getRfcrc() == 0) {
			this.addOutput(chwDepdCaller.getRFCName() + " called successfully!");
		} else {
			this.addOutput(chwDepdCaller.getRFCName() + " called  faild!");
			this.addOutput(chwDepdCaller.getError_text());
		}
		 //ChwDepdMaintain 
    	
    }
    public void processMachTypeNew(MODEL model,Connection odsConnection) throws Exception {
    	String materialType="ZMAT";
    	String  materialID =model.getMACHTYPE()+"NEW";
    	ChwMatmCreate chwCreateCaller = new ChwMatmCreate(model, materialType, materialID);
    	this.addDebug("Calling " + chwCreateCaller.getRFCName());
    	chwCreateCaller.execute();
		this.addDebug(chwCreateCaller.createLogEntry());
		if (chwCreateCaller.getRfcrc() == 0) {
			this.addOutput(chwCreateCaller.getRFCName() + " called successfully!");
		} else {
			this.addOutput(chwCreateCaller.getRFCName() + " called  faild!");
			this.addOutput(chwCreateCaller.getError_text());
		}
		
		Chw001ClfCreate chw001ClfCreate = new Chw001ClfCreate(model, materialType,materialID, odsConnection); 
		this.addDebug("Calling " + "Chw001ClfCreate");
		try{
			chw001ClfCreate.execute();
			this.addMsg(chw001ClfCreate.getRptSb());
		}catch (Exception e) {
			this.addMsg(chw001ClfCreate.getRptSb());
			throw e;
		}
		/*
		 * Chw001ClfCreate createCaller = new Chw001ClfCreate(xml, materialType,
		 * materialID, "MODEL"); createCaller.execute();
		 */
		 
    	String obj_id = materialID;
		String class_name="MK_REFERENCE";
		String class_type="300";
		RdhClassificationMaint cMaintCaller = new RdhClassificationMaint(obj_id, class_name, class_type,"H");
		this.addDebug("Calling " + cMaintCaller.getRFCName());
		cMaintCaller.execute();
		this.addDebug(cMaintCaller.createLogEntry());
		if (chwCreateCaller.getRfcrc() == 0) {
			this.addOutput(cMaintCaller.getRFCName() + " called successfully!");
		} else {
			this.addOutput(cMaintCaller.getRFCName() + " called  faild!");
			this.addOutput(cMaintCaller.getError_text());
		}
		class_name = "MK_T_VAO_NEW";
		cMaintCaller = new RdhClassificationMaint(obj_id, class_name, class_type,"H");
		this.addDebug("Calling " + cMaintCaller.getRFCName());
		cMaintCaller.execute();
		this.addDebug(cMaintCaller.createLogEntry());
		if (chwCreateCaller.getRfcrc() == 0) {
			this.addOutput(cMaintCaller.getRFCName() + " called successfully!");
		} else {
			this.addOutput(cMaintCaller.getRFCName() + " called  faild!");
			this.addOutput(cMaintCaller.getError_text());
		}
    }
    
    public void processMachTypeUpg(MODEL model,Connection odsConnection) throws Exception {
    	String materialType="ZMAT";
    	String  materialID =model.getMACHTYPE()+"UPG";
    	ChwMatmCreate chwCreateCaller = new ChwMatmCreate(model, materialType, materialID);
    	this.addDebug("Calling " + chwCreateCaller.getRFCName());
    	chwCreateCaller.execute();
		this.addDebug(chwCreateCaller.createLogEntry());
		if (chwCreateCaller.getRfcrc() == 0) {
			this.addOutput(chwCreateCaller.getRFCName() + " called successfully!");
		} else {
			this.addOutput(chwCreateCaller.getRFCName() + " called  faild!");
			this.addOutput(chwCreateCaller.getError_text());
		}
		Chw001ClfCreate chw001ClfCreate = new Chw001ClfCreate(model, materialType,materialID, odsConnection); 
		this.addDebug("Calling " + "Chw001ClfCreate");
		try{
			chw001ClfCreate.execute();
			this.addMsg(chw001ClfCreate.getRptSb());
		}catch (Exception e) {
			this.addMsg(chw001ClfCreate.getRptSb());
			throw e;
		}
    	String obj_id = materialID;
		String class_name="MK_REFERENCE";
		String class_type="300";
		RdhClassificationMaint cMaintCaller = new RdhClassificationMaint(obj_id, class_name, class_type,"H");
		this.addDebug("Calling " + cMaintCaller.getRFCName());
		cMaintCaller.execute();
		this.addDebug(cMaintCaller.createLogEntry());
		if (chwCreateCaller.getRfcrc() == 0) {
			this.addOutput(cMaintCaller.getRFCName() + " called successfully!");
		} else {
			this.addOutput(cMaintCaller.getRFCName() + " called  faild!");
			this.addOutput(cMaintCaller.getError_text());
		}
		class_name = "MK_T_VAO_NEW";
		cMaintCaller = new RdhClassificationMaint(obj_id, class_name, class_type,"H");
		this.addDebug("Calling " + cMaintCaller.getRFCName());
		cMaintCaller.execute();
		this.addDebug(cMaintCaller.createLogEntry());
		if (chwCreateCaller.getRfcrc() == 0) {
			this.addOutput(cMaintCaller.getRFCName() + " called successfully!");
		} else {
			this.addOutput(cMaintCaller.getRFCName() + " called  faild!");
			this.addOutput(cMaintCaller.getError_text());
		}
		class_name = "MK_D_VAO_NEW";
		cMaintCaller = new RdhClassificationMaint(obj_id, class_name, class_type,"H");
		this.addDebug("Calling " + cMaintCaller.getRFCName());
		cMaintCaller.execute();
		this.addDebug(cMaintCaller.createLogEntry());
		if (chwCreateCaller.getRfcrc() == 0) {
			this.addOutput(cMaintCaller.getRFCName() + " called successfully!");
		} else {
			this.addOutput(cMaintCaller.getRFCName() + " called  faild!");
			this.addOutput(cMaintCaller.getError_text());
		}
		class_name = "MK_FC_EXCH";
		cMaintCaller = new RdhClassificationMaint(obj_id, class_name, class_type,"H");
		this.addDebug("Calling " + cMaintCaller.getRFCName());
		cMaintCaller.execute();
		this.addDebug(cMaintCaller.createLogEntry());
		if (chwCreateCaller.getRfcrc() == 0) {
			this.addOutput(cMaintCaller.getRFCName() + " called successfully!");
		} else {
			this.addOutput(cMaintCaller.getRFCName() + " called  faild!");
			this.addOutput(cMaintCaller.getError_text());
		}
		class_name = "MK_FC_CONV";
		cMaintCaller = new RdhClassificationMaint(obj_id, class_name, class_type,"H");
		this.addDebug("Calling " + cMaintCaller.getRFCName());
		cMaintCaller.execute();
		this.addDebug(cMaintCaller.createLogEntry());
		if (chwCreateCaller.getRfcrc() == 0) {
			this.addOutput(cMaintCaller.getRFCName() + " called successfully!");
		} else {
			this.addOutput(cMaintCaller.getRFCName() + " called  faild!");
			this.addOutput(cMaintCaller.getError_text());
		}
		
		//Set to  "MK_T_<machine_type>_MOD" where <machine_type> is chwProduct.machineType.
		String charact = "MK_T_"+model.getMACHTYPE()+"_MOD";
		String datatype="CHAR";
		int charnumber=6;
		String decplaces=""; 
		String casesens="";
		String neg_vals="";
		String group="";
		String valassignm="-";
		String no_entry="";
		String no_display="";
		String addit_vals="X";
		String chdescr=model.getMACHTYPE()+" Model Characteristic";
		ChwCharMaintain charMaintain = new ChwCharMaintain(obj_id, charact, datatype, charnumber, decplaces, casesens, neg_vals, group, valassignm, no_entry, no_display, addit_vals, chdescr);
    
		charMaintain.addValue(model.getMODEL(), model.getINVNAME().substring(0, 24)+" "+model.getMODEL());
		this.addDebug("Calling " + charMaintain.getRFCName());
		charMaintain.execute();
		this.addDebug(charMaintain.createLogEntry());
		if (charMaintain.getRfcrc() == 0) {
			this.addOutput(charMaintain.getRFCName() + " called successfully!");
		} else {
			this.addOutput(charMaintain.getRFCName() + " called  faild!");
			this.addOutput(charMaintain.getError_text());
		}
		class_name = "MK_"+model.getMACHTYPE()+"_MOD";
		class_type = class_name;
		ChwClassMaintain chwClassMaintain = new ChwClassMaintain(obj_id, class_name, class_type);
		this.addDebug("Calling " + chwClassMaintain.getRFCName());
		chwClassMaintain.addCharacteristic("MK_T_"+model.getMODEL()+"_MOD");
		chwClassMaintain.execute();
		this.addDebug(chwClassMaintain.createLogEntry());
		if (chwClassMaintain.getRfcrc() == 0) {
			this.addOutput(chwClassMaintain.getRFCName() + " called successfully!");
		} else {
			this.addOutput(chwClassMaintain.getRFCName() + " called  faild!");
			this.addOutput(chwClassMaintain.getError_text());
		}
		class_type="300";
		RdhClassificationMaint classificationMaint = new RdhClassificationMaint(obj_id, class_name, class_type,"H");
		this.addDebug("Calling " + classificationMaint.getRFCName());
		classificationMaint.execute();
		this.addDebug(classificationMaint.createLogEntry());
		if (classificationMaint.getRfcrc() == 0) {
			this.addOutput(classificationMaint.getRFCName() + " called successfully!");
		} else {
			this.addOutput(classificationMaint.getRFCName() + " called  faild!");
			this.addOutput(classificationMaint.getError_text());
		}
		String c_profile = "INITIAL"; 
		String bomappl="SD01";
		String bomexpl="2";
		String design=model.getMACHTYPE()+"UPGUI";
		ChwConpMaintain chwConpMaintain = new ChwConpMaintain(obj_id, c_profile, bomappl, bomexpl, design);
		chwConpMaintain.addConfigDependency("E2E", "");
		chwConpMaintain.addConfigDependency("PR_"+model.getMACHTYPE()+"_SET_MODEL", "");
		chwConpMaintain.addConfigDependency("PR_E2E_SET_MTM", "");
		chwConpMaintain.addConfigDependency("PR_E2E_PRICING_HW", "");
		chwConpMaintain.addConfigDependency("PR_E2E_CSTIC_HIDING_HW", "");
		
    }
    public void processMachTypeMODEL_Svc(MODEL model,Connection odsConnection) throws Exception {
    	String materialType = "ZPRT";
    	String materialID = model.getMACHTYPE()+model.getMODEL();

    	RdhSvcMatmCreate svcMatmCreate = new RdhSvcMatmCreate(model);
    	this.addDebug("Calling " + svcMatmCreate.getRFCName());
    	svcMatmCreate.execute();
		this.addDebug(svcMatmCreate.createLogEntry());
		if (svcMatmCreate.getRfcrc() == 0) {
			this.addOutput(svcMatmCreate.getRFCName() + " called successfully!");
		} else {
			this.addOutput(svcMatmCreate.getRFCName() + " called  faild!");
			this.addOutput(svcMatmCreate.getError_text());
		}
		/*
		 * Chw001ClfCreate chw001ClfCreate = new Chw001ClfCreate(xml, materialType,
		 * materialID, materialID); chw001ClfCreate.execute();
		 */
		Chw001ClfCreate chw001ClfCreate = new Chw001ClfCreate(model, materialType,materialID, odsConnection); 
		this.addDebug("Calling " + "Chw001ClfCreate");
		try{
			chw001ClfCreate.execute();
			this.addMsg(chw001ClfCreate.getRptSb());
		}catch (Exception e) {
			this.addMsg(chw001ClfCreate.getRptSb());
			throw e;
		}
    	
    	
    }
    
    public boolean exist(String sql,String type) {
    	boolean flag = false;
    	try {
		Connection connection = m_db.getPDHConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, type);
		
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
			flag = true;
			
		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MiddlewareException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
    	
    	return flag;
    	
    }
	
}
