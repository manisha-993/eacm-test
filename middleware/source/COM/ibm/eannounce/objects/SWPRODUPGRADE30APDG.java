// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2004, 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
// $Log: SWPRODUPGRADE30APDG.java,v $
// Revision 1.18  2008/03/31 17:41:02  wendy
// CQ00002041-RQ create second MODELREL relator
//
// Revision 1.17  2007/09/06 12:19:54  couto
// MN32841099 WGMODEL replaced by WGMODELA
//
// Revision 1.1  2004/08/25 19:44:51  joan
// add new pdgs
//

package COM.ibm.eannounce.objects;

import java.sql.SQLException;
import java.util.*;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.OPICMList;
import COM.ibm.opicmpdh.middleware.ReturnRelatorKey;

/********************************************************
* B.	Software Product Upgrade Data Generator:
*
* Input from User Interface: (Fields marked with * are used.  Otherwise, they are ignored)
* 	*AFCOMNAME - Common name = (required field) User defined name for the PDG request form
* 	*AFSWREQTYPE - Request Type = (required field) SW Product
* 	*OPTFEATUREID - Optional Feature ID = (required field) f00
* 	*MACHTYPEATR - Machine Type = (required field) MMMM
* 	*MODELATR - Model = (required field) mmm
* 	*EFFECTIVEDATE - Effective Date - used in template
* 	*AFSWUPGRADEFROM - Upgrade From = x-PPPP-ppp
* 	*ANNCODENAME - Announcement Code Name: (required field) - used in template
* 	*GENAREASELECTION - General Area Selection: (required field) US, AP, CCN, EMEA, LAD
* 	*AFBILLINGTEMPLATE -Billing Template: (required field) (picklist of all valid values)
*
* 1.	Input Validation:
* The SW Product Upgrade Data generator will verify the following input conditions:
* a)	Local Rules:
* b)	PDG Checks:
* -	At least one upgrade path must be specified
* -	Verify the format of any specified upgrade path.  Each upgrade path is separated by a new line and the format is x-mmmm-mmm where x is a digit.
* -	A MODEL classified Software-Application-Base with the input MACHTYPE and MODEL must exist
* -	The Billing template on the input form must match the billing template specified on the Software-Application-Base MODEL found.
* -	The optional feature ID must be in the format f00
* -	General Area selections are non-overlapping ( For example, cannot select both WW and US)
* -	An additional check for ANNCODENAME existence
* -	If a billing template other than NOBILLING, IPLA06, IPLA22 or IPLA25 is entered, provide a warning that billing codes must be added manually.
*
* Note: it is a valid business scenario to run this PDG several times to add additional upgrade paths.
*
* 2.	Create Upgrade MODELs
* IF at least one upgrade path was specified, follow these steps to create and attach one or more Upgrade
* MODELs under the base MODEL.
*
* Create an 'Application-Upgrade' MODEL for each upgrade path specified
* Input from User Interface:
* Machine Type: MMMM
* Model: mmm
* Upgrade From: x-PPPP-ppp
*
* Set Attributes on the new Upgrade MODEL based on the above input from the UI
*
* -	MACHTYPEATR = MMMM
* -	MODELATR = mmm
* -	INTERNALNAME = MMMM-mmm (UPPPP-ppp)
* -	COFCAT = Software
* -	COFSUBCAT = Application
* -	COFGRP = Upgrade
* -	COFSUBGRP = N/A
*
*
* 3.	Create MODELREL
* Attach Upgrade MODEL to the SW-Application-Base MODEL of machinetype model MMMM-mmm thru relator MODELREL
* (i.e. MODELREL is from MMMM-mmm Software Application Base N/A to the newly created MODEL MMMM-mmm Software Application Upgrade N/A)
* Set Attributes on the MODELREL as follows:
* -	COMNAME = Upgrade Model
* -	COFCOFMGCAT = Software
* -	COFCOFMGSUBCAT = Application
* -	COFCOFMGGRP = Upgrade
* -	COFCOFMGSUBGRP = N/A
*
* If MMMMmmm <> PPPPppp then
* (i.e. the upgrade is from a different TypeModel which must exist)
* Create a MODELREL between the following two MODELs with the same attribute values as stated above
* 1.	The 'To' MODEL which is Entity1Type & Entity1Id
* 	MACHTYPEATR = MMMM
* 	MODELATR = mmm
* 	COFGRP = Upgrade
* 2.	The 'From' MODEL which is Entity2type & Entity2Id
* 	MACHTYPEATR = PPPP
* 	MODELATR = ppp
* 	COFCAT = Software
* 	COFSUBCAT = Application
* 	COFGRP = Base
* 	COFSUBGRP = N/A
* END IF
*
* 4.	Create SWFEATUREs
* Skip this section for Billing Template = NOBILLING, and for billing templates other than IPLA06, IPLA22 or IPLA25
*
* PDGTemplates are used for this section:
*
* Follow these steps to create and attach SWFEATUREs to each SW-Application-Base MODELs for the selected billing template.  The x in the Upgradesfrom is used to determine the number of US billing codes to create.  One set of billing codes is created for EMEA/LAD for each upgrade path entered.  One set of billing codes is created for each unique value of x for US/AP/CCN.
*
* -	Create Billing related SWFEATUREs as follows, using the tables from "SWFEATUREs and SWPRODSTRUCTs for SW Billing" for the attribute values.  If SWFEATUREs are reusable, use dynamic search to look for them. If a reusable one does not already exist, create one.
* -	Connect the SWFEATURE to the SW-Application-Base MODEL using an SWPRODSTRUCT relator.
* -	Find and attach AVAILs to the SWPRODSTRUCT as specified in the tables in "SWFEATUREs and SWPRODSTRUCTs for SW Billing".
* -	Create COFOOFMGs per the table below.  Each row in the table is a separate COFOOFMG.
* -	Attach the COFOOFMGs to the Application-Upgrade MODEL created above.
* -	Attach the SWPRODSTRUCTs to the COFOOFMGs as specified below.
*
* a)	IF Billing Template = IPLA06, create the following using PDGTemplates\UpgradeBTIPLA06_30a.txt:
* 	COFOOFMG Attributes	SWFEATURE, SWPRODSTRUCT
* 	COMNAME = Upgrade PBOTC Billing
* 	COFOOFMGCAT = Software
* 	COFOOFMGSUBCAT=FeatureCode
* 	COFOOFMGGRP = Billing
* 	COFOOFMGSUBGRP = N/A	SWPRODSTRUCTs for SWFEATUREs with FEATURECODE = #U0Bx0, #U1Bx0, #U2Bx0, #U3Bx0, #U4Bx0, #U5Bx0, #U6Bx0, #E0By0, #E1By0, #E2By0, #E3By0, #E4By0, #E5By0, #E6By0
* 	Programming Notes:
* 	- x in the FC is for each unique value of x input in UI, for an upgrade path
* 	- y in the FC is unique for each upgrade path input in the UI
*
* b)	IF Billing Template = IPLA22, create the following using PDGTemplates\UpgradeBTIPLA22_30a.txt:
* 	MG Name	SWFEATURE, SWPRODSTRUCT
* 	COMNAME = Discount Per Use Billing
* 	COFOOFMGCAT = Software
* 	COFOOFMGSUBCAT = FeatureCode
* 	COFOOFMGGRP = Billing
* 	COFOOFMGSUBGRP = N/A	SWPRODSTRUCTs for SWFEATUREs with FEATURECODE = #UJ1x0, #EJ1y0
* 	Programming Notes:
* 	- x in the FC is for each unique value of x input in UI, for an upgrade path
* 	- y in the FC is unique for each upgrade path input in the UI
*
* c)	IF Billing Template = IPLA25, create the following using PDGTemplates\UpgradeBTIPLA25_30a.txt:
* 	COFOOFMG Attributes	SWFEATURE, SWPRODSTRUCT
* 	COMNAME = Upgrade Per Use Billing
* 	COFOOFMGCAT = Software
* 	COFOOFMGSUBCAT = FeatureCode
* 	COFOOFMGGRP = Billing
* 	COFOOFMGSUBGRP = N/A	SWPRODSTRUCTs for SWFEATUREs with FEATURECODE = #UFBx0, #EFBy0, #UH1x0,  #EH1y0, #UH2x0, #UJ1x0,  #EJ1y0, #UJ2x0
* 	Programming Notes:
* 	- x in the FC is for each unique value of x input in UI, for an upgrade path
* 	- y in the FC is unique for each upgrade path input in the UI
* 	- block of 250 will always be created as max users is default to 999
*
*/
public class SWPRODUPGRADE30APDG extends PDGActionItem {

    static final long serialVersionUID = 20011106L;

    private String m_strAfOptFeature = "f00";
    private Vector m_afUpgradeVec = null;
    private String m_strAfBillingTemplate = null;
    private String m_strAfMachType = null;
    private String m_strAfModel = null;
    private String m_strAnnCodeName = null;
    private Hashtable upgradeTbl = new Hashtable(); // entity items matching PPPP-ppp

    /****************************************
    * Version info
    */
    public String getVersion() {
        return "$Id: SWPRODUPGRADE30APDG.java,v 1.18 2008/03/31 17:41:02 wendy Exp $";
    }

    /****************************************
    * constructors
    */
    public SWPRODUPGRADE30APDG(EANMetaFoundation  _mf, SWPRODUPGRADE30APDG _ai) throws MiddlewareRequestException {
        super(_mf, _ai);
    }

    public SWPRODUPGRADE30APDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
        super(_emf, _db,  _prof, _strActionItemKey);
    }

    /****************************************
    * dump info
    */
    public String dump(boolean _bBrief) {
        StringBuffer strbResult = new StringBuffer();
        strbResult.append("SWPRODUPGRADE30APDG:" + getKey() + ":desc:" + getLongDescription());
        strbResult.append(":purpose:" + getPurpose());
        strbResult.append(":entitytype:" + getEntityType() + "/n");
        return strbResult.toString();
    }

    public String getPurpose() {
        return "SWPRODUPGRADE30APDG";
    }

    /****************************************
    * go thru the motions but dont create anything
    */
    public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = " SWPRODUPGRADE30APDG viewMissing method";

        _db.debug(D.EBUG_DETAIL, strTraceBase);
        resetVariables();
        if (m_eiPDG == null) {
            String s="PDG entity is null";
            return s.getBytes();
        }

        m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");

        addDebug("viewMissing entered "+m_eiPDG.getKey());
        //_prof = m_utility.setProfValOnEffOn(_db, _prof);
        ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTAFIRMT1");
        EntityItem[] eiParm = {m_eiPDG};
        // get attributes
        EntityList el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
        EntityGroup eg = el.getParentEntityGroup();
        m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());
        eg = el.getEntityGroup("WG");
        if (eg != null) {
            if (eg.getEntityItemCount() > 0) {
                m_eiRoot = eg.getEntityItem(0); // used for setting PDHDOMAIN
            }
        }

        checkPDGAttribute(_db, _prof, m_eiPDG);

        // validate data
        try{
        	checkDataAvailability(_db, _prof, m_eiPDG);
        } catch (SBRException ex) {
			String[] msg = ex.getMessageAsArray();
			for (int x=0; x<msg.length; x++){
				m_SBREx.add(msg[x]);
			}
        }
        if (m_SBREx.getErrorCount() > 0) {
            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
            throw m_SBREx;
        }

        String s = checkMissingData(_db, _prof,false).toString();
        if (s.length() == 0) {
            s = "Generating data is complete";
        }
        m_sbActivities.append(m_utility.getViewXMLString(s));
        m_utility.savePDGViewXML(_db, _prof, m_eiPDG, m_sbActivities.toString());
        m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_PASSED, "", getLongDescription());
        return s.getBytes();
    }

    /****************************************
    * do all checks and generate data too
    */
    public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = " SWPRODUPGRADE30APDG executeAction method";
        String strData = "";
        try {
            _db.debug(D.EBUG_DETAIL, strTraceBase);
            resetVariables();
            if (m_eiPDG == null) {
                System.out.println("PDG entity is null");
                return;
            }
            m_sbActivities.append("<PDGACTTIONITEM>" + getLongDescription() + "</PDGACTTIONITEM>");

        	addDebug("executeAction entered "+m_eiPDG.getKey());

           // _prof = m_utility.setProfValOnEffOn(_db, _prof);
            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_SUBMIT, "", getLongDescription());

            ExtractActionItem eaItem = new ExtractActionItem(null, _db, _prof, "EXTAFIRMT1");
            EntityItem[] eiParm = {m_eiPDG};
            EntityList el = EntityList.getEntityList(_db, _prof, eaItem, eiParm);
            EntityGroup eg = el.getParentEntityGroup();
            m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());
            eg = el.getEntityGroup("WG");
            if (eg != null) {
                if (eg.getEntityItemCount() > 0) {
                    m_eiRoot = eg.getEntityItem(0);
                }
            }

            checkPDGAttribute(_db, _prof, m_eiPDG);

            // validate data
            checkDataAvailability(_db, _prof, m_eiPDG);
            if (m_SBREx.getErrorCount() > 0) {
                m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, m_SBREx.toString(), getLongDescription());
                throw m_SBREx;
            }
            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_RUNNING, "", getLongDescription());
            m_utility.resetActivities();
            strData = checkMissingData(_db, _prof,true).toString();
            m_sbActivities.append(m_utility.getActivities().toString());
            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_COMPLETE, m_sbActivities.toString(), getLongDescription());
        } catch (SBRException ex) {
            ex.printStackTrace();
            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, ex.toString(), getLongDescription());
            throw ex;
        } catch (MiddlewareException mex) {
            mex.printStackTrace();
            m_utility.savePDGStatusXML(_db, _prof, m_eiPDG, PDGUtility.STATUS_ERROR, mex.toString(), getLongDescription());
            throw mex;
        }
        if (!runBySPDG() && strData.length() <= 0) {
            m_SBREx.add("Generating data is complete.  No data created during this run. (ok)");
            throw m_SBREx;
        }

        //-	If a billing template other than NOBILLING, IPLA06, IPLA22 or IPLA25 is entered, provide a warning that billing codes must be added manually.
        if ( !(m_strAfBillingTemplate.equals("IPLA06")
                || m_strAfBillingTemplate.equals("IPLA22")
                || m_strAfBillingTemplate.equals("IPLA25")
                || m_strAfBillingTemplate.equals("No Billing"))) {
            m_SBREx.add("Billing codes must be added manually. (ok)");
            throw m_SBREx;
        }
    }

    /****************************************
    * this will check and create data based on _bGenData value
    */
    protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = " SWPRODUPGRADE30APDG checkMissingData method";
        _db.debug(D.EBUG_SPEW, strTraceBase);
        StringBuffer sbReturn = new StringBuffer();

        EntityItem eiBaseCOF = (EntityItem)m_eiList.get("BASE");
        boolean addedtmpl = false;
        String strFileName2 = "PDGtemplates/UpgradeModelRel_30a.txt";  // second modelrel from new MMMM-mmm model to upgrade PPPP-ppp model

        String strFileName = "PDGtemplates/Upgrade_30a.txt";
        if (m_strAfBillingTemplate != null) {
            if (m_strAfBillingTemplate.equals("IPLA06")) {
                strFileName = "PDGtemplates/UpgradeBTIPLA06_30a.txt";
            } else if (m_strAfBillingTemplate.equals("IPLA22")) {
                strFileName = "PDGtemplates/UpgradeBTIPLA22_30a.txt";
            } else if (m_strAfBillingTemplate.equals("IPLA25")) {
                strFileName = "PDGtemplates/UpgradeBTIPLA25_30a.txt";
            }
        }

        m_sbActivities.append("<TEMPLATEFILE>" + strFileName + "</TEMPLATEFILE>\n");

        String ofid = m_utility.getOptFeatIDAbr(m_strAfOptFeature);

        for(int i=0; i < m_afUpgradeVec.size(); i++) {
            String strUpgrade = (String)m_afUpgradeVec.elementAt(i);
            StringTokenizer st = new StringTokenizer(strUpgrade, "-");
            String strUS = st.nextToken();
            String strMachType = st.nextToken();
            String strModel = st.nextToken();

            OPICMList infoList = new OPICMList();
            infoList.put("WG", m_eiRoot);
            infoList.put("PDG", m_eiPDG);
            infoList.put("STRUPGRADE", strMachType + "-" + strModel); // + (strFeatureCode.length() > 0 ? "-" + strFeatureCode : ""));
            infoList.put("US", strUS);
            infoList.put("EMEA", (i+1)+"");
            infoList.put("OFID", ofid);
            infoList.put("GEOIND", "GENAREASELECTION"); // used in the TestPDGII object
            infoList.put("UPMACHTYPE", strMachType);
            infoList.put("UPMODEL", strModel);
           // _prof = m_utility.setProfValOnEffOn(_db, _prof);

            TestPDGII pdgObject = new TestPDGII(_db, _prof, eiBaseCOF, infoList, m_PDGxai, strFileName);
            StringBuffer sbMissing = pdgObject.getMissingEntities();
            if (_bGenData) {
				//* 2.	Create Upgrade MODELs and their MODELREL
                generateData(_db, _prof, sbMissing);
            }
			EntityList el = pdgObject.getEntityList();
			if (el != null){
				el.dereference();
				el = null;
			}
			pdgObject = null;
			infoList = null;

            sbReturn.append(sbMissing.toString());

            // create second modelrel relator if upgrade PPPP-ppp doesnt match base MMMM-mmm
            // CQ00002041-RQ
            if(!m_strAfMachType.equals(strMachType) || !m_strAfModel.equals(strModel)){
				if (!addedtmpl){
					m_sbActivities.append("<TEMPLATEFILE>" + strFileName2 + "</TEMPLATEFILE>\n");
					addedtmpl = true;
				}

				addDebug("Creating second MODELREL for "+strUpgrade);
				infoList = new OPICMList();
        	    infoList.put("WG", m_eiRoot);
        	    infoList.put("PDG", m_eiPDG);
        	    infoList.put("UPMACHTYPE", strMachType);
        	    infoList.put("UPMODEL", strModel);

				Vector upgradeVct = (Vector)upgradeTbl.get(strUpgrade);
				for (int p=0; p<upgradeVct.size(); p++){
			        EntityItem eiUpgrade = (EntityItem)upgradeVct.elementAt(p);

					pdgObject = new TestPDGII(_db, _prof, eiUpgrade, infoList, strFileName2);
					sbMissing = pdgObject.getMissingEntities();
					addDebug("Second MODELREL for "+strUpgrade+" sbMissing: "+sbMissing.toString());

					if (_bGenData) {
						// * 3.	Create MODELREL (second one)
						generateData(_db, _prof, sbMissing);
					}
					pdgObject = null;
					infoList = null;

					sbReturn.append(sbMissing.toString());
				}
			}

        }

        return sbReturn;
    }

    private void addDebug(String msg){
        m_sbActivities.append("<DEBUG>"+msg+"\n</DEBUG>"+ NEW_LINE);
    }

	/***************************************
	* -	Verify the format of any specified upgrade path.  Each upgrade path is separated by a new line and
	* the format is x-mmmm-mmm where x is a digit.  Put each valid one into a Vector.
	*/
    private void verifyUpgradeStrings(String _s) {
        StringTokenizer st = new StringTokenizer(_s, "\n");
		while (st.hasMoreElements()) {
			boolean bAdd = true;
			String str = st.nextToken().trim();

			StringTokenizer st1 = new StringTokenizer(str, "-");
			int i = st1.countTokens();
			if (i != 3) {
				m_SBREx.add(str + " is not in format x-pppp-ppp");
				bAdd = false;
			}else{
				// check len of each part
				String digit = st1.nextToken();
				String mt = st1.nextToken();
				String mdl = st1.nextToken();

				if (digit.length() !=1){
					bAdd = false;
					m_SBREx.add(str + " first element is not a single character.");
				} else if (!Character.isDigit(digit.charAt(0))) {
					bAdd = false;
					m_SBREx.add(str + " first character is not a digit.");
				}
				if (mt.length() !=4){
					bAdd = false;
					m_SBREx.add(str + " Machine type is not 4 characters.");
				}
				if (mdl.length() !=3){
					bAdd = false;
					m_SBREx.add(str + " Model is not 3 characters.");
				}
			}

			if (bAdd) {
				if (m_afUpgradeVec==null){
					m_afUpgradeVec = new Vector(1);
				}
				m_afUpgradeVec.addElement(str);
			}
		}

        addDebug("verifyUpgradeStrings vct: "+m_afUpgradeVec);
    }

	/***************************************
	* 1.	Input Validation: - some done in other places
	* The SW Product Upgrade Data generator will verify the following input conditions:
	* a)	Local Rules:
	* b)	PDG Checks:
	* -	At least one upgrade path must be specified
	* -	Verify the format of any specified upgrade path.  Each upgrade path is separated by a new line and the format is x-mmmm-mmm where x is a digit.
	* -	The optional feature ID must be in the format f00
	* -	General Area selections are non-overlapping ( For example, cannot select both WW and US)
	* -	An additional check for ANNCODENAME existence
	* -	If a billing template other than NOBILLING, IPLA06, IPLA22 or IPLA25 is entered, provide a warning that billing codes must be added manually.
	* -	A MODEL classified Software-Application-Base with the input MACHTYPE and MODEL must exist
	* -	The Billing template on the input form must match the billing template specified on the
	*		Software-Application-Base MODEL found.
	*/
    protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareRequestException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
        //String strTraceBase = " SWPRODUPGRADE30APDG checkPDGAttribute method";
        boolean GENAREASELECTIONfnd = false;

        for (int i =0; i < _afirmEI.getAttributeCount(); i++) {
            EANAttribute att = _afirmEI.getAttribute(i);
            String textAtt = "";
            String sFlagAtt = "";
            //String sFlagClass = "";
            Vector mFlagAtt = new Vector();

			addDebug("checkPDGAttr attr: "+att.getAttributeCode()+" val "+att);
            //int index = -1;
            if (att instanceof EANTextAttribute) {
                textAtt = ((String)att.get()).trim();
            } else if (att instanceof EANFlagAttribute) {
                if (att instanceof SingleFlagAttribute || att instanceof StatusAttribute) {
                    MetaFlag[] amf = (MetaFlag[])att.get();
                    for (int f=0; f < amf.length; f++) {
                        if (amf[f].isSelected()) {
                            sFlagAtt = amf[f].getLongDescription().trim();
                            //sFlagClass = amf[f].getFlagCode().trim();
                            //index = f;
                            break;
                        }
                    }
                } else if (att instanceof MultiFlagAttribute) {
                    MetaFlag[] amf = (MetaFlag[])att.get();
                    for (int f=0; f < amf.length; f++) {
                        if (amf[f].isSelected()) {
                            mFlagAtt.addElement(amf[f].getLongDescription().trim());
                        }
                    }
                }
            }

            if (att.getKey().equals("AFSWREQTYPE")) {
				if ( ! sFlagAtt.equals("SWProduct")) {
					SBRException ex = new SBRException();
					ex.add("Invalid Request Type:" + sFlagAtt + ". This action item is for SW Product Request.");
					throw ex;
				}
            } else if (att.getKey().equals("OPTFEATUREID")) {
				//-	The optional feature ID must be in the format f00
                m_utility.checkOptFeatureIDFormat(textAtt, PDGUtility.OF_PRODUCT, true, m_SBREx);
                m_strAfOptFeature = textAtt;
            } else if (att.getKey().equals("AFSWUPGRADEFROM")) {
				//-	Verify the format of any specified upgrade path.  Each upgrade path is separated by a new line and the format is x-mmmm-mmm where x is a digit.
                verifyUpgradeStrings(textAtt);
            } else if (att.getKey().equals("AFBILLINGTEMPLATE")) {
                m_strAfBillingTemplate = sFlagAtt;
            } else if (att.getKey().equals("MACHTYPEATR")) {
                m_strAfMachType = sFlagAtt;
            } else if (att.getKey().equals("MODELATR")) {
                m_strAfModel = textAtt;
            } else if (att.getKey().equals("GENAREASELECTION")) {
				//-	General Area selections are non-overlapping ( For example, cannot select both WW and US)
                m_utility.checkGenAreaOverlap(mFlagAtt, m_SBREx);
                GENAREASELECTIONfnd = true;
            } else if (att.getKey().equals("ANNCODENAME")) {
                m_strAnnCodeName = sFlagAtt;
            }
        }

		//-	At least one upgrade path must be specified
        if (m_afUpgradeVec == null) {
            m_SBREx.add("No valid upgrade paths specified.");
        }
        //ANNCODENAME used in some billing templates -	An additional check for ANNCODENAME existence
        if (m_strAnnCodeName == null || m_strAnnCodeName.length() ==0) {
            m_SBREx.add("ANNCODENAME is required.");
        }
        if (!GENAREASELECTIONfnd){ // used in TestPDGII
            m_SBREx.add("GENAREASELECTION is required.");
		}
        if (m_strAfBillingTemplate == null || m_strAfBillingTemplate.length() ==0) {
            m_SBREx.add("BILLINGTEMPLATE is required.");
        }
    }

    protected void resetVariables() {
        m_SBREx = new SBRException();
        m_strAfOptFeature = "f00";
        m_afUpgradeVec = null;
        m_strAfBillingTemplate = null;
        m_strAfMachType = null;
        m_strAfModel = null;
        m_strAnnCodeName = null;
        m_eiList = new EANList();
        upgradeTbl.clear();
        m_sbActivities = new StringBuffer();
    }

	/*******************************************
	* 1.	Input Validation: - some done in other places
	* -	If a billing template other than NOBILLING, IPLA06, IPLA22 or IPLA25 is entered, provide a warning that billing codes must be added manually.
	* -	A MODEL classified Software-Application-Base with the input MACHTYPE and MODEL must exist
	* -	The Billing template on the input form must match the billing template specified on the
	*		Software-Application-Base MODEL found.
	*/
    protected void checkDataAvailability(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        //String strTraceBase = "SWPRODUPGRADE30APDG checkDataAvailability ";
        StringBuffer sb = new StringBuffer();
        // make sure the Base COMMERCIALOF for the MTM already exists
        sb.append("map_COFCAT=101;"); //software
        sb.append("map_COFSUBCAT=127;"); // application
        sb.append("map_COFGRP=150;"); //base=150, upgrade=152
        sb.append("map_COFSUBGRP=010;"); // N/A
        sb.append("map_MACHTYPEATR=" + m_strAfMachType + ";");
        sb.append("map_MODELATR=" + m_strAfModel+ ";");

        String strSai = (String)m_saiList.get("MODEL");
        addDebug("Using "+strSai+" to search for "+sb.toString());
        //_prof = m_utility.setProfValOnEffOn(_db, _prof);

		//-	A MODEL classified Software-Application-Base with the input MACHTYPE and MODEL must exist
        EntityItem[] aeiCOM = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "MODEL", sb.toString());

        if (aeiCOM ==null || aeiCOM.length == 0) {
            if (!runBySPDG()) {
                m_SBREx.add("The MODEL with classifications SW-Application-Base-N/A, MACHTYPE=" + m_strAfMachType + ", MODEL=" + m_strAfModel + " must be created before Upgrade MODEL.");
            }
        } else if (aeiCOM.length > 1) {
            m_SBREx.add("Must only be 1 MODEL. There are " + aeiCOM.length + " existing MODELs with classifications SW-Application-Base-N/A, MACHTYPE=" + m_strAfMachType + ", MODEL=" + m_strAfModel);
        } else {
            m_eiList.put("BASE", aeiCOM[0]);
            m_eiList.put(aeiCOM[0]); // used later in generateData

        	addDebug("Base COMMERCIALOF search found "+aeiCOM[0].getKey());

			//-	The Billing template on the input form must match the billing template specified on the
			//		Software-Application-Base MODEL found.
            EANAttribute att = aeiCOM[0].getAttribute("BILLINGTEMPLATE");
            if (att instanceof EANFlagAttribute) {
                MetaFlag[] amf = (MetaFlag[])att.get();
                for (int f=0; f < amf.length; f++) {
                    if (amf[f].isSelected()) {
                        String sFlagAtt = amf[f].getLongDescription().trim();
                        if (!sFlagAtt.equals(m_strAfBillingTemplate)) {
                            m_SBREx.add("Billing Template on the request: "+m_strAfBillingTemplate+", must match Billing Template: "+ sFlagAtt+", on the Base MODEL: " + aeiCOM[0].getKey());
                        }
                        break;
                    }
                }
            }
        }

		// each model in the upgrade must exist PPPP-ppp
        if (m_afUpgradeVec != null){
			for(int i=0; i < m_afUpgradeVec.size(); i++) {
				String strUpgrade = (String)m_afUpgradeVec.elementAt(i);
				addDebug("Checking PPPP-ppp for existance "+strUpgrade);

				StringTokenizer st = new StringTokenizer(strUpgrade, "-");
				st.nextToken(); // skip first one
				String strMachType = st.nextToken();
				String strModel = st.nextToken();
				sb = new StringBuffer();
				sb.append("map_COFCAT=101;"); //software
				sb.append("map_COFSUBCAT=127;"); // application
        		sb.append("map_COFGRP=150;"); //base=150, upgrade=152
        		sb.append("map_COFSUBGRP=010;"); // N/A

				sb.append("map_MACHTYPEATR=" + strMachType + ";");
        		sb.append("map_MODELATR=" + strModel+ ";");

				addDebug("Using "+strSai+" to search for "+sb.toString());

				//-	A MODEL classified Software-Application-Base with the upgrade MACHTYPE and MODEL must exist
				aeiCOM = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, "MODEL", sb.toString());

				if (aeiCOM ==null || aeiCOM.length == 0) {
				   m_SBREx.add("The upgrade MODEL with classifications SW-Application-Base, MACHTYPE=" + strMachType + ", MODEL=" + strModel + " does not exist.");
				} else {
					Vector vct = new Vector(1);
					upgradeTbl.put(strUpgrade,vct);
					for (int p=0; p<aeiCOM.length; p++){
						vct.add(aeiCOM[p]);
						m_eiList.put(aeiCOM[p]);
						addDebug(strUpgrade+" PPPP-ppp["+p+"] upgrade search found "+aeiCOM[p].getKey());
					}
				}
			}
		}
    }

    private void generateData(Database _db, Profile _prof, StringBuffer _sbMissing) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
        String strTraceBase = "SWPRODUPGRADE30APDG generateData";
        _db.debug(D.EBUG_SPEW, strTraceBase+" sbmissing: "+_sbMissing.toString());

		StringTokenizer st = new StringTokenizer(_sbMissing.toString(),"\n");
		Hashtable ht = new Hashtable();
		while (st.hasMoreTokens()) {
			String s = st.nextToken();
			addDebug("Processing "+s);
			StringTokenizer st1 = new StringTokenizer(s,"|");
			if (st1.hasMoreTokens()) {
				String strParentEntity = st1.nextToken().trim();
				int iLevel = Integer.parseInt(st1.nextToken());
				String strDirection = st1.nextToken().trim();
				addDebug("strParentEntity "+strParentEntity+" iLevel "+iLevel+" strDirection "+strDirection);

				// get parent for later links
				EntityItem eiParent = null;
				if (strParentEntity != null && strParentEntity.length() > 0) {
					StringTokenizer stParent = new StringTokenizer(strParentEntity,"-");
					if (stParent.hasMoreTokens()) {
						String strParentType = stParent.nextToken();
						int iParentID = Integer.parseInt(stParent.nextToken());
						// MN32841099 try to find it in lists
						addDebug("strParentType "+strParentType+" iParentID "+iParentID);
						eiParent = (EntityItem)ht.get((iLevel-1) + "");
						if (eiParent !=null){
							if(!eiParent.getKey().equals(strParentType+iParentID)){
								eiParent = null;  // wrong one
							}
							addDebug("parent from ht "+(eiParent==null?"null":eiParent.getKey()));

						}
						if (eiParent==null){ // MN32841099 try by key
							eiParent = (EntityItem)m_eiList.get(strParentType+iParentID);
							addDebug("parent from m_eilist using key "+(eiParent==null?"null":eiParent.getKey()));
						}
						if (eiParent==null){ // MN32841099 try by entitytype
							eiParent = (EntityItem)m_eiList.get(strParentType);
							if(eiParent!=null && !eiParent.getKey().equals(strParentType+iParentID)){
								eiParent = null;  // wrong one
							}
							addDebug("parent from m_eilist using type "+(eiParent==null?"null":eiParent.getKey()));
						}

						if (eiParent ==null) { // create it
							eiParent = new EntityItem(null, _prof, strParentType, iParentID);
							addDebug("parent create new "+eiParent.getKey());
						}
					}
				} else {
					eiParent = (EntityItem)ht.get((iLevel-1) + "");
					addDebug("parent from ht "+(eiParent==null?"null":eiParent.getKey()));
				}

				// get stuff for Entity
				String strEntity = st1.nextToken();
				int i1 = strEntity.indexOf(":");
				String strEntityType = strEntity;
				String strAttributes = "";
				if (i1 > -1 ){
					strEntityType = strEntity.substring(0, i1);
					strAttributes = strEntity.substring(i1+1);
				}

				String strAction = st1.nextToken();
				String strRelatorInfo = st1.nextToken();

				addDebug("strEntityType "+strEntityType+" strAttributes "+strAttributes+
					" strAction "+strAction+" strRelatorInfo "+strRelatorInfo);

				//find the item if needed
				int iFind = strAction.indexOf("find");
				EntityItem currentEI = null;
				if (iFind > -1) {
					if (strAttributes.indexOf("map") >= 0) {
						int iEqual = strAttributes.indexOf("=");
						String strHead = strAttributes.substring(4, iEqual);
						currentEI = m_utility.findEntityItem(m_eiList, strEntityType, strAttributes);
						addDebug("find currentEI "+(currentEI==null?"null":currentEI.getKey()));
						if (currentEI == null) {
							String strSai = (String)m_saiList.get(strEntityType);
							EntityItem[] aei = null;
							if (strHead.indexOf(":") >= 0) {
								aei = m_utility.dynaSearchII(_db, _prof, m_eiPDG, strSai, strEntityType, strAttributes);
							} else {
								aei = m_utility.dynaSearch(_db, _prof, m_eiPDG, strSai, strEntityType, strAttributes);
							}

							if (aei.length > 0) {
								currentEI = aei[0];
							}
							addDebug("after search currentEI "+(currentEI==null?"null":currentEI.getKey()));
						}
						if (currentEI != null) {
							m_eiList.put(currentEI);
							ht.put(iLevel + "", currentEI);
						}
					}
				}

				// link them if there's command link
				int iLink = strAction.indexOf("linkParent");
				if (iLink > -1 && currentEI != null) {
					// use parent entity, relator,link
					if (eiParent != null) {
						OPICMList ol = null;
						if (strDirection.equals("U")) {
							EntityItem[] aei = {eiParent};
							addDebug("linking eiParent: "+eiParent.getKey()+" to currentEI "+currentEI.getKey());
							ol = m_utility.linkEntities(_db, _prof, currentEI, aei, strRelatorInfo);
						} else {
							EntityItem[] aei = {currentEI};
							addDebug("linking currentEI: "+currentEI.getKey()+" to eiParent "+eiParent.getKey());
							ol = m_utility.linkEntities(_db, _prof, eiParent, aei, strRelatorInfo);
						}

						if (ol !=null){ // MN32841099
							for (int i=0; i < ol.size(); i++) {
								Object obj = ol.getAt(i);
								//System.out.println(strTraceBase + " obj: " + obj.toString());
								if (obj instanceof ReturnRelatorKey) {
									ReturnRelatorKey rrk = (ReturnRelatorKey)obj;
									String strRType = rrk.getEntityType();
									EntityGroup eg = m_utility.getEntityGroup(strRType);
									if (eg ==null) {
										eg = new EntityGroup(null, _db, _prof, strRType, "Edit", false);
									}
									//_prof = m_utility.setProfValOnEffOn(_db, _prof);
									EntityItem ei = new EntityItem(eg, _prof, _db, rrk.getEntityType(), rrk.getReturnID());
									//System.out.println(strTraceBase + " ei: " + ei.dump(false));
									addDebug("adding  ei to m_eilist: "+ei.getKey());

									m_eiList.put(ei);
								}
							}
						}else {
							addDebug("Link error? ol was null");
							_db.debug(D.EBUG_SPEW,strTraceBase + "linkParent OPICMList ol is null from link");
						}
					}
					continue;
				}

				// create the item
				int iCreate = strAction.indexOf("create");
				if (iCreate > -1) {
					String strRelatorType = "";
					int iAttrO = strRelatorInfo.indexOf("[");
					if (iAttrO > -1) {
						strRelatorType = strRelatorInfo.substring(0,iAttrO);
					} else {
						strRelatorType = strRelatorInfo;
					}

					if (eiParent == null) {
						eiParent = m_eiRoot;
					}
					addDebug("create action: eiParent "+(eiParent==null?"null":eiParent.getKey()));

					//prepare the list of attributes
					OPICMList attList = m_utility.getAttributeListForCreate(strEntityType, strAttributes, strRelatorInfo);
					String strCai = (String)m_caiList.get(strRelatorType);
					if (strDirection.equals("U")) {
						// create stand alone entity
						attList = m_utility.getAttributeListForCreate(strEntityType, strAttributes, "");
						strCai = (String)m_caiList.get(strEntityType);
					}

					if (strAction.indexOf("USUPGRADE") > -1) {      // need to check for ORDEROF for the same upgrade group
						currentEI = m_utility.findEntityItem(m_eiList, strEntityType, strAttributes);
						addDebug("create action USUPGRADE: currentEI "+(currentEI==null?"null":currentEI.getKey()));

						if (currentEI != null) {
							EntityItem[] aei = {currentEI};
							addDebug("create action USUPGRADE: linking currentEI "+currentEI.getKey()+" to "+eiParent.getKey());
							m_utility.linkEntities(_db, _prof, eiParent, aei, strRelatorInfo);
						}
					}

					if (currentEI == null) {
						currentEI = m_utility.createEntityByRST(_db, _prof, eiParent, attList, strCai, strRelatorType, strEntityType);
						addDebug("create action byRST: currentEI "+(currentEI==null?"null":currentEI.getKey()));
					}

					_db.test(currentEI != null, strTraceBase + " ei is null for: " + s);
					ht.put(iLevel + "", currentEI);
					m_eiList.put(currentEI);

					if (strDirection.equals("U")) {
						// link to 1 level up
						EntityItem[] aei = {eiParent};
						addDebug("create action: linking eiParent "+eiParent.getKey()+" to "+currentEI.getKey());
						OPICMList ol = m_utility.linkEntities(_db, _prof, currentEI, aei, strRelatorInfo);
						//System.out.println(strTraceBase + " ol size: " + ol.size());

						if (ol !=null){ // MN32841099
							for (int i=0; i < ol.size(); i++) {
								Object obj = ol.getAt(i);
								//System.out.println(strTraceBase + " obj: " + obj.toString());
								if (obj instanceof ReturnRelatorKey) {
									ReturnRelatorKey rrk = (ReturnRelatorKey)obj;
									String strRType = rrk.getEntityType();
									EntityGroup eg = m_utility.getEntityGroup(strRType);
									if (eg ==null) {
										eg = new EntityGroup(null, _db, _prof, strRType, "Edit", false);
									}
									//_prof = m_utility.setProfValOnEffOn(_db, _prof);
									EntityItem ei = new EntityItem(eg, _prof, _db, rrk.getEntityType(), rrk.getReturnID());
									//System.out.println(strTraceBase + " ei: " + ei.dump(false));
									m_eiList.put(ei);
								}
							}
						} else {
							_db.debug(D.EBUG_SPEW,strTraceBase + "create OPICMList ol is null from link");
							addDebug("create action Link error? ol was null");
						}
					}
				}
			}
		}
    }
}
