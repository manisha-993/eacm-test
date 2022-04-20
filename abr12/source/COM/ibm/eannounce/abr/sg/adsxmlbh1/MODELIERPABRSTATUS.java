package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.CharacterIterator;
import java.text.MessageFormat;
import java.text.StringCharacterIterator;
import java.util.Hashtable;
import java.util.List;

import COM.ibm.eannounce.abr.sg.rfc.Chw001ClfCreate;
import COM.ibm.eannounce.abr.sg.rfc.ChwCharMaintain;
import COM.ibm.eannounce.abr.sg.rfc.ChwClassMaintain;
import COM.ibm.eannounce.abr.sg.rfc.ChwConpMaintain;
import COM.ibm.eannounce.abr.sg.rfc.ChwDepdMaintain;
import COM.ibm.eannounce.abr.sg.rfc.ChwMachTypeMtc;
import COM.ibm.eannounce.abr.sg.rfc.ChwMachTypeUpg;
import COM.ibm.eannounce.abr.sg.rfc.ChwMatmCreate;
import COM.ibm.eannounce.abr.sg.rfc.CommonUtils;
import COM.ibm.eannounce.abr.sg.rfc.MODEL;
import COM.ibm.eannounce.abr.sg.rfc.RdhBase;
import COM.ibm.eannounce.abr.sg.rfc.RdhChwFcProd;
import COM.ibm.eannounce.abr.sg.rfc.RdhClassificationMaint;
import COM.ibm.eannounce.abr.sg.rfc.RdhSvcMatmCreate;
import COM.ibm.eannounce.abr.sg.rfc.SVCMOD;
import COM.ibm.eannounce.abr.sg.rfc.XMLParse;
import COM.ibm.eannounce.abr.sg.rfc.entity.LANGUAGE;
import COM.ibm.eannounce.abr.util.EACustom;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareException;

import com.ibm.transform.oim.eacm.util.PokUtils;

public class MODELIERPABRSTATUS extends PokBaseABR {
	private StringBuffer rptSb = new StringBuffer();
	private static final char[] FOOL_JTEST = { '\n' };
	static final String NEWLINE = new String(FOOL_JTEST);
	private int abr_debuglvl = D.EBUG_ERR;
	private String navName = "";
	private Hashtable metaTbl = new Hashtable();
	private String CACEHSQL = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'MODEL' and XMLENTITYID = ?  and XMLCACHEVALIDTO > current timestamp with ur";
	
	private String COVNOTEQUALSQL = "SELECT count(*) FROM OPICM.flag F\n"
			+ " INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT  TIMESTAMP "
			+ " INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.ATTRIBUTEVALUE = ? and T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP "
			+ " INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP "
			+ " INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP "
			+ " WHERE f.ENTITYTYPE ='MODELCONVERT' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELCONVERTIERPABRSTATUS') "
			+ " AND T1.ATTRIBUTEVALUE !=T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION= ? WITH UR";
	
	private String COVEQUALSQL = "SELECT count(*) FROM OPICM.flag F\n"
			+ " INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.VALTO > CURRENT TIMESTAMP AND T1.EFFTO > CURRENT TIMESTAMP "
			+ " INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.ATTRIBUTEVALUE = ? and T2.VALTO > CURRENT TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP "
			+ " INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT TIMESTAMP "
			+ " INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT TIMESTAMP AND M.EFFTO > CURRENT TIMESTAMP "
			+ " WHERE f.ENTITYTYPE ='MODELCONVERT' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'MODELCONVERTIERPABRSTATUS') "
			+ " AND T1.ATTRIBUTEVALUE =T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION= ? WITH UR";

	private static String FCTEQUALSQL= "SELECT count(*) FROM OPICM.flag F\n"
			+ " INNER JOIN opicm.text t1 ON f.ENTITYID =t1.ENTITYID AND f.ENTITYTYPE =t1.ENTITYTYPE AND t1.ATTRIBUTECODE ='FROMMACHTYPE' AND T1.VALTO > CURRENT  TIMESTAMP AND T1.EFFTO > CURRENT TIMESTAMP "
			+ " INNER JOIN OPICM.TEXT t2 ON f.ENTITYID =t2.ENTITYID AND f.ENTITYTYPE =t2.ENTITYTYPE AND t2.ATTRIBUTECODE ='TOMACHTYPE' AND T2.ATTRIBUTEVALUE = ? and T2.VALTO > CURRENT  TIMESTAMP AND T2.EFFTO > CURRENT  TIMESTAMP "
			+ " INNER JOIN OPICM.FLAG F1 ON F1.ENTITYID =t2.ENTITYID AND F1.ENTITYTYPE =t2.ENTITYTYPE AND F1.ATTRIBUTECODE ='PDHDOMAIN' and F1.VALTO > CURRENT  TIMESTAMP AND F1.EFFTO > CURRENT  TIMESTAMP "
			+ " INNER JOIN OPICM.METADESCRIPTION M ON M.DESCRIPTIONCLASS=F1.ATTRIBUTEVALUE AND  M.NLSID=1 AND M.VALTO > CURRENT  TIMESTAMP AND M.EFFTO > CURRENT  TIMESTAMP "
			+ " WHERE f.ENTITYTYPE ='FCTRANSACTION' AND F.ATTRIBUTECODE IN ('ADSABRSTATUS' ,'FCTRANSACTIONIERPABRSTATUS') "
			+ " AND T1.ATTRIBUTEVALUE =T2.ATTRIBUTEVALUE AND  F.ATTRIBUTEVALUE ='0030' AND M.LONGDESCRIPTION= ? WITH UR";
	
	String xml = null;

	public static void main(String[] args) {
		System.out.println(FCTEQUALSQL);
	}

	public String getDescription() {
		return "MODELIERPABRSTATUS";
	}

	public String getABRVersion() {
		return "1.1";
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
					 *  step c
			            If there is a MODELCONVERT which meets all of conditions below,
			                tomachtype = chwProduct.machineType
			                frommachtype !=tomachtype
			                pdhdomain = chwProduct.pdhdomain -- new add 
			                past passed ADSABRSTATUS or MODELCONVERTIERPABRSTATUS
			            then execute the steps described in the document MachTypeMTC RDH Feed to iERP to populate data elements for MachineTypeMTC material.
					 */
			
					if(exist(COVNOTEQUALSQL, model.getMACHTYPE(),model.getPDHDOMAIN())) {
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
					//step d
					
					if(!CommonUtils.contains("Maintenance,MaintFeature",model.getSUBCATEGORY())) {
						
						if(exist(COVEQUALSQL, model.getMACHTYPE(),model.getPDHDOMAIN())||exist(FCTEQUALSQL, model.getMACHTYPE(),model.getPDHDOMAIN())) {
							ChwMachTypeUpg chwMachTypeUpg = new ChwMachTypeUpg(model, m_db.getPDHConnection(), connection);
							this.addDebug("Calling " + "ChwMachTypeUpg");
							try{
								chwMachTypeUpg.execute();
								this.addMsg(chwMachTypeUpg.getRptSb());
							}catch (Exception e) {
								this.addMsg(chwMachTypeUpg.getRptSb());
								throw e;
							}
						}else if(model.getORDERCODE()!=null&&model.getORDERCODE().trim().length()>0&&CommonUtils.contains("M,B",model.getORDERCODE())) {
							this.addDebug("Calling " + "processMachTypeUpg");
							processMachTypeUpg(model, connection);	
						}
					}
					
					RdhChwFcProd prod = new RdhChwFcProd(model);
					runRfcCaller(prod);
				}
				else if("Service".equals(model.getCATEGORY())) {
					processMachTypeMODEL_Svc(model, connection);
					RdhChwFcProd prod = new RdhChwFcProd(model);
					runRfcCaller(prod);
				}
				else if ("Software".equals(model.getCATEGORY())) {
					this.addError("It is not supported to feed software Model to iERP");
					//throw new Exception("Not support SoftWare");
				}
				
			}	
			
			// exeFtpShell(ffPathName);
			// ftpFile();
			/*
			 * } catch (Exception e) { 
			 * e.printStackTrace(); this.addError(e.getMessage()); setReturnCode(FAIL); }
			 */
		} catch (Exception e) {
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
    
    protected void runRfcCaller(RdhBase caller) throws Exception {
		this.addDebug("Calling " + caller.getRFCName());
		caller.execute();
		this.addDebug(caller.createLogEntry());
		if (caller.getRfcrc() == 0) {
			this.addOutput(caller.getRFCName() + " called successfully!");
		} else {
			this.addOutput(caller.getRFCName() + " called  faild!");
			this.addOutput(caller.getError_text());
		}
	}

    public void processMachTypeMODEL (MODEL model,Connection odsConnection) throws Exception {
    	String materialType="ZPRT";
    	String  materialID =model.getMACHTYPE()+model.getMODEL();
    	
    	ChwMatmCreate caller = new ChwMatmCreate(model, materialType, materialID);
    	runRfcCaller(caller);
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
		 
		//step 3 Create the SC_machinetype_MOD_model object dependency:
//		String obj_id=model.getMACHTYPE()+model.getMODEL();
//		String dep_extern="PR_"+model.getMACHTYPE()+"_SET_MODEL";
//		String dep_type="7"; 
//		String descript=model.getMACHTYPE()+" Set Model";
//		String sourceLine = "$self.mk_model2 = mk_t_"+model.getMACHTYPE()+"_mod";
//		ChwDepdMaintain chwDepdCaller	=new ChwDepdMaintain(obj_id, dep_extern, dep_type, descript)	;
//		chwDepdCaller.addSourceLineCondition(sourceLine);
//		runRfcCaller(chwDepdCaller);
		
		//step 4 Create the SC_machinetype_MOD_model object dependency:
		String obj_id=model.getMACHTYPE()+model.getMODEL();
		String dep_extern = "SC_"+model.getMACHTYPE()+"_MOD_"+model.getMODEL();
		String dep_type="5"; 
		String descript="SC_"+model.getMACHTYPE()+"_MOD_"+model.getMODEL();
		String sourceLine = "$PARENT.MK_T_"+model.getMACHTYPE()+"_MOD='"+model.getMODEL()+"'";
		ChwDepdMaintain chwDepdCaller	=new ChwDepdMaintain(obj_id, dep_extern, dep_type, descript)	;
		chwDepdCaller.addSourceLineCondition(sourceLine);
		runRfcCaller(chwDepdCaller);
		 //ChwDepdMaintain 
		
		
		
    	
    }
    public void processMachTypeNew(MODEL model,Connection odsConnection) throws Exception {
    	//step 1 Call ChwMatmCreate to create the material master for the product object
    	String materialType="ZMAT";
    	String  materialID =model.getMACHTYPE()+"NEW";
    	ChwMatmCreate chwCreateCaller = new ChwMatmCreate(model, materialType, materialID);
    	runRfcCaller(chwCreateCaller);
		
    	//step2 Call Chw001ClfCreate to create the standard 001 classifications and characteristics which are tied to the offering's material master record.
		Chw001ClfCreate chw001ClfCreate = new Chw001ClfCreate(model, materialType,materialID, odsConnection); 
		this.addDebug("Calling " + "Chw001ClfCreate");
		try{
			chw001ClfCreate.execute();
			this.addMsg(chw001ClfCreate.getRptSb());
		}catch (Exception e) {
			this.addMsg(chw001ClfCreate.getRptSb());
			throw e;
		}
		
		//step3 Call the TssClassificationMaint to associate the MK_REFERENCE class to the product's material master record
    	String obj_id = materialID;
		String class_name="MK_REFERENCE";
		String class_type="300";
		RdhClassificationMaint cMaintCaller = new RdhClassificationMaint(obj_id, class_name, class_type,"H");
		runRfcCaller(cMaintCaller);
		
		//step4 Call the TssClassificationMaint to associate the MK_T_VAO_NEW class to the product's material master record. 
		class_name = "MK_T_VAO_NEW";
		cMaintCaller = new RdhClassificationMaint(obj_id, class_name, class_type,"H");
		runRfcCaller(cMaintCaller);
		
		//step 5 Create the MK_machineType_MOD class and MK_T_machineType_MOD characteristic
		String empty ="";
		ChwCharMaintain chwCharMaintain = 
		new ChwCharMaintain(obj_id  //String obj_id Set to concatenation of chwProduct.machineType + "MTC"
							,"MK_T_"+model.getMACHTYPE()+"_MOD" //String charact  Set to  "MK_T_<machine_type>_MOD"
							, "CHAR" 			//String datatype
							, 6 				//int charnumber
							, empty				//String decplaces
							, empty				//String casesens
							, empty				//String neg_vals
							, empty				//String group
							, "-"				//String valassignm  Set to "-".
							, empty				//String no_entry
							, empty				//String no_display
							, "X" 				//String addit_vals Set to "X".
							, model.getMACHTYPE() +" Model Characteristic" //String chdescr  Set to "<machine_type> Model Characteristic" where <machine_type> is chwProduct.machineType
							);
		//5.b Call the ChwCharMaintain.addValue() method Set to SUBSTRING(chwProduct/INVNAME FROM 1 FOR 25)||' '||chwProduct/MODEL;
		String invname = "";
		List<LANGUAGE> languagesList = model.getLANGUAGELIST();
		for(LANGUAGE language : languagesList){
			String nlsid = language.getNLSID();
			if("1".equals(nlsid)){					//<NLSID>1</NLSID>
				invname = language.getINVNAME();
				break;
			}
		}
		String valdescr = CommonUtils.getFirstSubString(invname,25) + " " + model.getMODEL();
		chwCharMaintain.addValue(model.getMODEL(), valdescr);
		runRfcCaller(chwCharMaintain);
		
		//5.c Call the ChwClassMaintain constructor to create the MK_machineType_MOD class.
		ChwClassMaintain ChwClassMaintain = 
				new ChwClassMaintain(
						obj_id 								//String obj_id Set to concatenation of chwProduct.machineType + "MTC"
						, "MK_"+model.getMACHTYPE()+"_MOD"  //String class_name   Set to  "MK_<machine_type>_MOD" where <machine_type> is chwProduct.machineType
						, "MK_"+model.getMACHTYPE()+"_MOD"  //String class_type   Set to  "MK_<machine_type>_MOD" where <machine_type> is chwProduct.machineType.
						);
		//5.d Call the ChwClassMaintain.addCharacteristic() method to add the MK_T_machineType_MOD characteristic to the MK_machineType_MOD characteristic class
		ChwClassMaintain.addCharacteristic("MK_T_"+model.getMACHTYPE()+"_MOD"); 
		runRfcCaller(ChwClassMaintain);
		
		//5.e Call the TssClassificationMaint constructor to associate the MK_machineType_MOD class to the product's material master record. 
		RdhClassificationMaint TssClassificationMaint = 
				new RdhClassificationMaint(
						obj_id 								//String obj_id Set to concatenation of chwProduct.machineType + "MTC"
						, "MK_"+model.getMACHTYPE()+"_MOD"  //String class_name   Set to  "MK_<machine_type>_MOD" where <machine_type> is chwProduct.machineType
						, "300"  							//String class_type   Set to "300"
						, "H"
						);
		runRfcCaller(TssClassificationMaint);
		//step 6 Create the PR_machinetype_SET_MODEL object dependency:
		//6 a Call the ChwDepdMaintain constructor to create the PR_machinetype_SET_MODEL dependency.
		String obj_id_depd=model.getMACHTYPE()+"NEW";
		String dep_extern="PR_"+model.getMACHTYPE()+"_SET_MODEL";
		String dep_type="7"; 
		String descript=model.getMACHTYPE()+" Set Model";
		String sourceLine = "$self.mk_model2 = $self.mk_t_"+model.getMACHTYPE()+"_mod";
		ChwDepdMaintain chwDepdCaller	=new ChwDepdMaintain(obj_id_depd, dep_extern, dep_type, descript)	;
		chwDepdCaller.addSourceLineCondition(sourceLine);
		runRfcCaller(chwDepdCaller);
		
		//step7 Call the ChwConpMaintain to create a configuration profile for the product's material master record
		ChwConpMaintain ChwConpMaintain  = 
				new ChwConpMaintain(
						obj_id 				//String obj_id  Set to concatenation of chwProduct.machineType + "MTC"
						, "INITIAL"         //String c_profile Set to "INITIAL".
						, "SD01"			//String bomappl Set to "SD01".
						, "2"				//String bomexpl Set to "2".
						, model.getMACHTYPE() +	"NEWUI"		//String design	 Set to Set to concatenation of chwProduct.machineType + "NEWUI" 
						);
		
		//7.b Call the ChwConpMaintain.addConfigDependency() method.
		ChwConpMaintain.addConfigDependency("E2E", empty); //Set to "E2E".
		//7.c 
		ChwConpMaintain.addConfigDependency("PR_"+model.getMACHTYPE()+"_SET_MODEL", empty);  //Set to "PR_<chwProduct.machineType>_SET_MODEL"
		//7.d
		ChwConpMaintain.addConfigDependency("PR_E2E_SET_MTM", empty);  //Set to "PR_E2E_SET_MTM".
		//7.e 
		ChwConpMaintain.addConfigDependency("PR_E2E_PRICING_HW", empty);  //Set to "PR_E2E_PRICING_HW".
		//7.f 
		ChwConpMaintain.addConfigDependency("PR_E2E_CSTIC_HIDING_HW", empty);  //Set to "PR_E2E_PRICING_HW"		
		runRfcCaller(ChwConpMaintain);
		
    }
    
    public void processMachTypeUpg(MODEL model,Connection odsConnection) throws Exception {
    	String materialType="ZMAT";
    	String  materialID =model.getMACHTYPE()+"UPG";
    	ChwMatmCreate chwCreateCaller = new ChwMatmCreate(model, materialType, materialID);
    	runRfcCaller(chwCreateCaller);
    	
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
		runRfcCaller(cMaintCaller);
		
		class_name = "MK_T_VAO_NEW";
		cMaintCaller = new RdhClassificationMaint(obj_id, class_name, class_type,"H");
		runRfcCaller(cMaintCaller);
		
		class_name = "MK_D_VAO_NEW";
		cMaintCaller = new RdhClassificationMaint(obj_id, class_name, class_type,"H");
		runRfcCaller(cMaintCaller);
		
		class_name = "MK_FC_EXCH";
		cMaintCaller = new RdhClassificationMaint(obj_id, class_name, class_type,"H");
		runRfcCaller(cMaintCaller);
		
		class_name = "MK_FC_CONV";
		cMaintCaller = new RdhClassificationMaint(obj_id, class_name, class_type,"H");
		runRfcCaller(cMaintCaller);
		
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
		String valdescr = null;
		List<LANGUAGE> languages =model.getLANGUAGELIST();
		String invname = null;
		for (int i = 0; i < languages.size(); i++) {
			if("1".equals(languages.get(i).getNLSID())){
				invname= languages.get(i).getINVNAME();
			}
		}
		
		valdescr =  invname==null? "":invname.substring(0, Math.min(invname.length(),24))+" "+model.getMODEL();
		charMaintain.addValue(model.getMODEL(),valdescr);
		runRfcCaller(charMaintain);
		
		class_name = "MK_"+model.getMACHTYPE()+"_MOD";
		class_type = class_name;
		ChwClassMaintain chwClassMaintain = new ChwClassMaintain(obj_id, class_name, class_type);
		chwClassMaintain.addCharacteristic("MK_T_"+model.getMACHTYPE()+"_MOD");
		runRfcCaller(chwClassMaintain);
		
		class_type="300";
		RdhClassificationMaint classificationMaint = new RdhClassificationMaint(obj_id, class_name, class_type,"H");
		runRfcCaller(classificationMaint);


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
		runRfcCaller(chwConpMaintain);
		
    }
    public void processMachTypeMODEL_Svc(MODEL model,Connection odsConnection) throws Exception {
    	String materialType = "ZPRT";
    	String materialID = model.getMACHTYPE()+model.getMODEL();

    	RdhSvcMatmCreate svcMatmCreate = new RdhSvcMatmCreate(model);
    	runRfcCaller(svcMatmCreate);
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
    
    public boolean exist(String sql,String type,String pdhdomain) {
    	boolean flag = false;
    	try {
			Connection connection = m_db.getPDHConnection();
			Object[] params = new String[2]; 
			params[0] =type;
			params[1] =pdhdomain;			
			String realSql = CommonUtils.getPreparedSQL(sql, params);
			this.addDebug("querySql=" + realSql);
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, type);
			statement.setString(2, pdhdomain);
			
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				int count = resultSet.getInt(1);
				flag = count>0 ? true: false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (MiddlewareException e) {
			e.printStackTrace();
		}    	
    	return flag;
    	
    }
	
}
