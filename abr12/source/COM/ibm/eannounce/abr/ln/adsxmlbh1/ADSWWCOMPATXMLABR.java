// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2011  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.ln.adsxmlbh1;


import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;

import java.util.*;
import java.sql.*;
import java.io.*;

import javax.xml.parsers.*;
import javax.xml.transform.TransformerException;

import org.w3c.dom.*;

import com.ibm.transform.oim.eacm.util.PokUtils;

/**********************************************************************************
*/
// $Log: ADSWWCOMPATXMLABR.java,v $
// Revision 1.1  2015/02/04 14:55:48  wangyul
// RCQ00337765-RQ change the XML mapping to pull DIV from PROJ for Lenovo
//
// Revision 1.22  2013/12/11 08:11:54  guobin
// xsd validation for generalarea, reconcile, wwcompat and price XML
//
// Revision 1.21  2012/10/22 14:32:17  guobin
// Build request - for defect 820634  update activity ='W' for withdraw offering.- spec BH FS ABR Catalog DB Compatibility Gen 20121011.doc
//
// Revision 1.20  2012/07/17 16:56:25  wangyulo
// add the switch to turn on or turn off to generate the userxml file for the periodic ABR
//
// Revision 1.19  2012/04/10 15:12:03  wangyulo
// the change for the  WWCOMPAT_UPDATE generation
//
// Revision 1.18  2012/04/06 12:52:37  wangyulo
// the changes to WWCOMPAT_UPDATE generation
//
// Revision 1.17  2012/01/18 15:57:24  guobin
// Fix the issue 635138 for WWCOMPAT:
// 1. There should be <STATUS> tag after BRANDCD tag in LSEO xml
//
// Revision 1.16  2011/12/14 02:26:54  guobin
// Update the Version V Mod M for the ADSABR
//
// Revision 1.15  2011/10/05 02:24:53  praveen
// Fix OSENTITYID default value
//
// Revision 1.14  2011/10/05 01:53:30  praveen
// fix addDebugComment stmt
//
// Revision 1.13  2011/10/05 01:40:22  praveen
// Uncommented osentitytype, osentityid references, pointing them to default value
//
// Revision 1.12  2011/10/05 00:55:21  praveen
// Commented OSENTITYTYPE, OSENTITYID as they are not in the xml
//
// Revision 1.11  2011/09/27 08:13:21  guobin
// change SQL to do not select systemos and OS.  default chunking size is 500000
//
// Revision 1.10  2011/09/16 12:45:24  guobin
// add chunking size for WWCOMPAT IDL
//
// Revision 1.9  2011/06/01 02:20:04  guobin
// change the country filter for WWCOMPAT
//
// Revision 1.8  2011/05/20 05:57:19  guobin
// change getMQPropertiesFN method
//
// Revision 1.7  2011/05/05 14:34:48  guobin
// change to send xml 3K records at a time.
//
// Revision 1.6  2011/04/27 21:02:16  rick
// changes to join to price tables to get information for LSEOs
//
//Revision 1.5  2011/02/15 10:59:50  lucasrg
//Applied mapping updates for DM Cycle 2
//
//Revision 1.4  2011/02/01 20:17:22  rick
//fixes
//
//Revision 1.3  2011/01/28 03:09:02  rick
//more fixes
//
// Revision 1.2  2011/01/28 02:27:52  rick
// fixes.
//
// Revision 1.1  2011/01/25 02:43:30  rick
// adding ADSWWCOMPATXMLABR
//
//
public class ADSWWCOMPATXMLABR extends XMLMQAdapter
{
	//private static final String COMPAT_SQL = "select activity,systementitytype,systementityid,"+
    //            "systemos,groupentitytype,groupentityid,oktopub,osentitytype,osentityid,os,optionentitytype,"+
    //            "optionentityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,"+
    //            "brandcd_fc "+
    //            "from gbli.wwtechcompat "+
    //            "where updated BETWEEN ? AND ? with ur";

	private static final int MW_ENTITY_LIMIT;
	private static final int WWCOMPAT_ROW_LIMIT;
	protected static int WWCOMPAT_MESSAGE_COUNT = 0;
	Connection connection = null;
	static {
		String entitylimit = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS", "_entitylimit",
			"3000");
		MW_ENTITY_LIMIT = Integer.parseInt(entitylimit);
		String rowlimit = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS", "_XMLGENCOUNT",
		"500000");
		WWCOMPAT_ROW_LIMIT = Integer.parseInt(rowlimit);		
	}

	private static final String COMPAT_SQL =
		"select activity,'LSEO',left_lseo.entityid,"+
        "groupentitytype,groupentityid,oktopub,'LSEO',"+
        "right_lseo.entityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,"+
        "brandcd_fc,"+
        "left_lseo.seoid,right_lseo.seoid,"+
        "left_model.machtypeatr,left_model.modelatr "+
        "from gbli.wwtechcompat wwtc "+
        "join price.wwseolseo left_wwseolseo on wwtc.systementityid=left_wwseolseo.id1 "+
        "and left_wwseolseo.isactive=1 "+
        "join price.lseo left_lseo on left_wwseolseo.id2=left_lseo.entityid and left_lseo.isactive=1 "+
        "join price.wwseolseo right_wwseolseo on wwtc.optionentityid=right_wwseolseo.id1 "+
        "and right_wwseolseo.isactive=1 "+
        "join price.lseo right_lseo on right_wwseolseo.id2=right_lseo.entityid and right_lseo.isactive=1 "+
        "join price.modelwwseo left_modelwwseo on left_modelwwseo.id2=wwtc.systementityid "+
        "and left_modelwwseo.isactive=1 "+
        "join price.model left_model on left_model.entityid=left_modelwwseo.id1 "+
        "and left_model.isactive=1 "+
        "where systementitytype='WWSEO' and optionentitytype='WWSEO' and "+
        "updated BETWEEN ? AND ? "+
        "and activity <> 'W' "+
        //According to Doc BH FS Catolog DB Compatbility Gen 20121011b.doc Flow to downstream where activity <> 'W'
        "union "+
        "select activity,'MODEL',systementityid,"+
        "groupentitytype,groupentityid,oktopub,'MODEL',"+
        "optionentityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,"+
        "brandcd_fc,cast(null as char),cast(null as char),"+
        "left_model.machtypeatr,left_model.modelatr "+
        "from gbli.wwtechcompat wwtc "+
        "join price.model left_model on left_model.entityid=systementityid and left_model.isactive=1 "+
        "where systementitytype='MODEL' and optionentitytype='MODEL' and "+
        "updated BETWEEN ? AND ? "+
        "and activity <> 'W' "+
        //According to Doc BH FS Catolog DB Compatbility Gen 20121011b.doc Flow to downstream where activity <> 'W'
        "union "+
        "select activity,'MODEL',systementityid,"+
        "groupentitytype,groupentityid,oktopub,'LSEO',"+
        "right_lseo.entityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,"+
        "brandcd_fc,cast(null as char),right_lseo.seoid,"+
        "left_model.machtypeatr,left_model.modelatr "+
        "from gbli.wwtechcompat wwtc "+
        "join price.model left_model on left_model.entityid=systementityid and left_model.isactive=1 "+
        "join price.wwseolseo right_wwseolseo on wwtc.optionentityid=right_wwseolseo.id1 "+
        "and right_wwseolseo.isactive=1 "+
        "join price.lseo right_lseo on right_wwseolseo.id2=right_lseo.entityid and right_lseo.isactive=1 "+
        "where systementitytype='MODEL' and optionentitytype='WWSEO' and "+
        "updated BETWEEN ? AND ? "+
        "and activity <> 'W' "+
        //According to Doc BH FS Catolog DB Compatbility Gen 20121011b.doc Flow to downstream where activity <> 'W'
        "union "+
        "select activity,'MODEL',systementityid,"+
        "groupentitytype,groupentityid,oktopub,'LSEOBUNDLE',"+
        "optionentityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,"+
        "brandcd_fc,cast(null as char),right_lseobundle.seoid,"+
        "left_model.machtypeatr,left_model.modelatr "+
        "from gbli.wwtechcompat wwtc "+
        "join price.model left_model on left_model.entityid=systementityid and left_model.isactive=1 "+
        "join price.lseobundle right_lseobundle on right_lseobundle.entityid=optionentityid and "+
        "right_lseobundle.isactive=1 "+
        "where systementitytype='MODEL' and optionentitytype='LSEOBUNDLE' and "+
        "updated BETWEEN ? AND ? "+
        "and activity <> 'W' "+
        //According to Doc BH FS Catolog DB Compatbility Gen 20121011b.doc Flow to downstream where activity <> 'W'
        "union "+
        "select activity,'LSEO',left_lseo.entityid,"+
        "groupentitytype,groupentityid,oktopub,'LSEOBUNDLE',"+
        "optionentityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,"+
        "brandcd_fc,"+
        "left_lseo.seoid,right_lseobundle.seoid,"+
        "left_model.machtypeatr,left_model.modelatr "+
        "from gbli.wwtechcompat wwtc "+
        "join price.wwseolseo left_wwseolseo on wwtc.systementityid=left_wwseolseo.id1 "+
        "and left_wwseolseo.isactive=1 "+
        "join price.lseo left_lseo on left_wwseolseo.id2=left_lseo.entityid and left_lseo.isactive=1 "+
        "join price.lseobundle right_lseobundle on right_lseobundle.entityid=optionentityid "+
        "and right_lseobundle.isactive=1 "+
        "join price.modelwwseo left_modelwwseo on left_modelwwseo.id2=wwtc.systementityid "+
        "and left_modelwwseo.isactive=1 "+
        "join price.model left_model on left_model.entityid=left_modelwwseo.id1 "+
        "and left_model.isactive=1 "+
        "where systementitytype='WWSEO' and optionentitytype='LSEOBUNDLE' and "+
        "updated BETWEEN ? AND ? "+
        "and activity <> 'W' "+
        //According to Doc BH FS Catolog DB Compatbility Gen 20121011b.doc Flow to downstream where activity <> 'W'
        "union "+
        "select activity,'LSEOBUNDLE',systementityid,"+
        "groupentitytype,groupentityid,oktopub,'LSEO',"+
        "right_lseo.entityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,"+
        "brandcd_fc,"+
        "left_lseobundle.seoid,right_lseo.seoid,"+
        "cast(null as char),cast(null as char) "+
        "from gbli.wwtechcompat wwtc "+
        "join price.lseobundle left_lseobundle on left_lseobundle.entityid=systementityid "+
        "and left_lseobundle.isactive=1 "+
        "join price.wwseolseo right_wwseolseo on wwtc.optionentityid=right_wwseolseo.id1 "+
        "and right_wwseolseo.isactive=1 "+
        "join price.lseo right_lseo on right_wwseolseo.id2=right_lseo.entityid and right_lseo.isactive=1 "+
        "where systementitytype='LSEOBUNDLE' and optionentitytype='WWSEO' and "+
        "updated BETWEEN ? AND ? "+
        "and activity <> 'W' "+
        //According to Doc BH FS Catolog DB Compatbility Gen 20121011b.doc Flow to downstream where activity <> 'W'
        "union "+
        "select activity,'LSEOBUNDLE',systementityid,"+
        "groupentitytype,groupentityid,oktopub,'LSEOBUNDLE',"+
        "optionentityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,"+
        "brandcd_fc,"+
        "left_lseobundle.seoid,right_lseobundle.seoid,"+
        "cast(null as char),cast(null as char) "+
        "from gbli.wwtechcompat wwtc "+
        "join price.lseobundle left_lseobundle on left_lseobundle.entityid=systementityid "+
        "and left_lseobundle.isactive=1 "+
        "join price.lseobundle right_lseobundle on right_lseobundle.entityid=optionentityid "+
        "and right_lseobundle.isactive=1 "+
        "where systementitytype='LSEOBUNDLE' and optionentitytype='LSEOBUNDLE' and "+
        "updated BETWEEN ? AND ? "+
	    "and activity <> 'W' with ur";

//		"select distinct activity,'LSEO',left_lseo.entityid,"+
//        "systemos,groupentitytype,groupentityid,oktopub,osentitytype,osentityid,wwtc.os,'LSEO',"+
//        "right_lseo.entityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,"+
//        "brandcd_fc,"+
//        "left_lseo.seoid,right_lseo.seoid,"+
//        "left_model.machtypeatr,left_model.modelatr "+
//        "from gbli.wwtechcompat wwtc "+
//        "join price.wwseolseo left_wwseolseo on wwtc.systementityid=left_wwseolseo.id1 "+
//        "and left_wwseolseo.isactive=1 "+
//        "join price.lseo left_lseo on left_wwseolseo.id2=left_lseo.entityid and left_lseo.isactive=1 "+
//        "join price.wwseolseo right_wwseolseo on wwtc.optionentityid=right_wwseolseo.id1 "+
//        "and right_wwseolseo.isactive=1 "+
//        "join price.lseo right_lseo on right_wwseolseo.id2=right_lseo.entityid and right_lseo.isactive=1 "+
//        "join price.modelwwseo left_modelwwseo on left_modelwwseo.id2=wwtc.systementityid "+
//        "and left_modelwwseo.isactive=1 "+
//        "join price.model left_model on left_model.entityid=left_modelwwseo.id1 "+
//        "and left_model.isactive=1 "+
//        "where systementitytype='WWSEO' and optionentitytype='WWSEO' and "+
//        "updated BETWEEN ? AND ? "+
//        "union "+
//        "select distinct activity,'MODEL',systementityid,"+
//        "systemos,groupentitytype,groupentityid,oktopub,osentitytype,osentityid,wwtc.os,'MODEL',"+
//        "optionentityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,"+
//        "brandcd_fc,cast(null as char),cast(null as char),"+
//        "left_model.machtypeatr,left_model.modelatr "+
//        "from gbli.wwtechcompat wwtc "+
//        "join price.model left_model on left_model.entityid=systementityid and left_model.isactive=1 "+
//        "where systementitytype='MODEL' and optionentitytype='MODEL' and "+
//        "updated BETWEEN ? AND ? "+
//        "union "+
//        "select distinct activity,'MODEL',systementityid,"+
//        "systemos,groupentitytype,groupentityid,oktopub,osentitytype,osentityid,wwtc.os,'LSEO',"+
//        "right_lseo.entityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,"+
//        "brandcd_fc,cast(null as char),right_lseo.seoid,"+
//        "left_model.machtypeatr,left_model.modelatr "+
//        "from gbli.wwtechcompat wwtc "+
//        "join price.model left_model on left_model.entityid=systementityid and left_model.isactive=1 "+
//        "join price.wwseolseo right_wwseolseo on wwtc.optionentityid=right_wwseolseo.id1 "+
//        "and right_wwseolseo.isactive=1 "+
//        "join price.lseo right_lseo on right_wwseolseo.id2=right_lseo.entityid and right_lseo.isactive=1 "+
//        "where systementitytype='MODEL' and optionentitytype='WWSEO' and "+
//        "updated BETWEEN ? AND ? "+
//        "union "+
//        "select distinct activity,'MODEL',systementityid,"+
//        "systemos,groupentitytype,groupentityid,oktopub,osentitytype,osentityid,wwtc.os,'LSEOBUNDLE',"+
//        "optionentityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,"+
//        "brandcd_fc,cast(null as char),right_lseobundle.seoid,"+
//        "left_model.machtypeatr,left_model.modelatr "+
//        "from gbli.wwtechcompat wwtc "+
//        "join price.model left_model on left_model.entityid=systementityid and left_model.isactive=1 "+
//        "join price.lseobundle right_lseobundle on right_lseobundle.entityid=optionentityid and "+
//        "right_lseobundle.isactive=1 "+
//        "where systementitytype='MODEL' and optionentitytype='LSEOBUNDLE' and "+
//        "updated BETWEEN ? AND ? "+
//        "union "+
//        "select activity,'LSEO',left_lseo.entityid,"+
//        "systemos,groupentitytype,groupentityid,oktopub,osentitytype,osentityid,wwtc.os,'LSEOBUNDLE',"+
//        "optionentityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,"+
//        "brandcd_fc,"+
//        "left_lseo.seoid,right_lseobundle.seoid,"+
//        "left_model.machtypeatr,left_model.modelatr "+
//        "from gbli.wwtechcompat wwtc "+
//        "join price.wwseolseo left_wwseolseo on wwtc.systementityid=left_wwseolseo.id1 "+
//        "and left_wwseolseo.isactive=1 "+
//        "join price.lseo left_lseo on left_wwseolseo.id2=left_lseo.entityid and left_lseo.isactive=1 "+
//        "join price.lseobundle right_lseobundle on right_lseobundle.entityid=optionentityid "+
//        "and right_lseobundle.isactive=1 "+
//        "join price.modelwwseo left_modelwwseo on left_modelwwseo.id2=wwtc.systementityid "+
//        "and left_modelwwseo.isactive=1 "+
//        "join price.model left_model on left_model.entityid=left_modelwwseo.id1 "+
//        "and left_model.isactive=1 "+
//        "where systementitytype='WWSEO' and optionentitytype='LSEOBUNDLE' and "+
//        "updated BETWEEN ? AND ? "+
//        "union "+
//        "select activity,'LSEOBUNDLE',systementityid,"+
//        "systemos,groupentitytype,groupentityid,oktopub,osentitytype,osentityid,wwtc.os,'LSEO',"+
//        "right_lseo.entityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,"+
//        "brandcd_fc,"+
//        "left_lseobundle.seoid,right_lseo.seoid,"+
//        "cast(null as char),cast(null as char) "+
//        "from gbli.wwtechcompat wwtc "+
//        "join price.lseobundle left_lseobundle on left_lseobundle.entityid=systementityid "+
//        "and left_lseobundle.isactive=1 "+
//        "join price.wwseolseo right_wwseolseo on wwtc.optionentityid=right_wwseolseo.id1 "+
//        "and right_wwseolseo.isactive=1 "+
//        "join price.lseo right_lseo on right_wwseolseo.id2=right_lseo.entityid and right_lseo.isactive=1 "+
//        "where systementitytype='LSEOBUNDLE' and optionentitytype='WWSEO' and "+
//        "updated BETWEEN ? AND ? "+
//        "union "+
//        "select activity,'LSEOBUNDLE',systementityid,"+
//        "systemos,groupentitytype,groupentityid,oktopub,osentitytype,osentityid,wwtc.os,'LSEOBUNDLE',"+
//        "optionentityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,"+
//        "brandcd_fc,"+
//        "left_lseobundle.seoid,right_lseobundle.seoid,"+
//        "cast(null as char),cast(null as char) "+
//        "from gbli.wwtechcompat wwtc "+
//        "join price.lseobundle left_lseobundle on left_lseobundle.entityid=systementityid "+
//        "and left_lseobundle.isactive=1 "+
//        "join price.lseobundle right_lseobundle on right_lseobundle.entityid=optionentityid "+
//        "and right_lseobundle.isactive=1 "+
//        "where systementitytype='LSEOBUNDLE' and optionentitytype='LSEOBUNDLE' and "+
//        "updated BETWEEN ? AND ? with ur";
	private static final String WITHDRAWUPDATE_SQL =
		"update gbli.wwtechcompat set activity = 'W' where "+
		"SYSTEMENTITYTYPE = ? "+
		"and SYSTEMENTITYID = ? "+
		"and SYSTEMOS = ? "+
		"and GROUPENTITYTYPE = ? "+
		"and GROUPENTITYID = ? "+
		"and OSENTITYTYPE = ? "+
		"and OSENTITYID = ? "+
		"and OS = ? "+
		"and OPTIONENTITYTYPE = ? "+
		"and OPTIONENTITYID = ?";
	
	
	
	private static final String WITHDRAW_SQL =
		"select SYSTEMENTITYTYPE,SYSTEMENTITYID,SYSTEMOS,GROUPENTITYTYPE,GROUPENTITYID,OSENTITYTYPE,OSENTITYID,wwtc.OS,OPTIONENTITYTYPE,optionentityid " +
		"from gbli.wwtechcompat wwtc "+
		"join price.wwseolseo right_wwseolseo on wwtc.optionentityid=right_wwseolseo.id1 "+
		"and right_wwseolseo.isactive=1 "+
		"join price.lseo right_lseo on right_wwseolseo.id2=right_lseo.entityid and right_lseo.isactive=1 "+
		"and right_lseo.nlsid=1 "+
		"and right_lseo.lseounpubdatemtrgt + 90 day < current date "+
		"where optionentitytype='WWSEO' "+
		"and activity in('A','C') "+
		"and updated BETWEEN ? AND ? "+
        "union "+
	    "select SYSTEMENTITYTYPE,SYSTEMENTITYID,SYSTEMOS,GROUPENTITYTYPE,GROUPENTITYID,OSENTITYTYPE,OSENTITYID,wwtc.OS,OPTIONENTITYTYPE,optionentityid " +
	    "from gbli.wwtechcompat wwtc "+
	    "join price.model right_model on right_model.entityid=wwtc.optionentityid and right_model.isactive=1 "+
	    "and right_model.nlsid=1 "+
	    "and right_model.wthdrweffctvdate + 90 day < current date "+
	    "where optionentitytype='MODEL' "+
	    "and activity in('A','C') "+
	    "and updated BETWEEN ? AND ? "+
	    "union "+
	    "select SYSTEMENTITYTYPE,SYSTEMENTITYID,SYSTEMOS,GROUPENTITYTYPE,GROUPENTITYID,OSENTITYTYPE,OSENTITYID,wwtc.OS,OPTIONENTITYTYPE,optionentityid " +
	    "from gbli.wwtechcompat wwtc "+
	    "join price.lseobundle right_lseobundle on right_lseobundle.entityid=wwtc.optionentityid "+
	    "and right_lseobundle.isactive=1 "+
	    "and right_lseobundle.nlsid=1 "+
	    "and right_lseobundle.bundlunpubdatemtrgt + 90 day < current date "+
	    "where optionentitytype='LSEOBUNDLE' "+
	    "and activity in('A','C') "+
	    "and updated BETWEEN ? AND ?  ";

    /**********************************
    * create xml and write to queue
    */
    public void processThis(ADSABRSTATUS abr, Profile profileT1, Profile profileT2, EntityItem rootEntity)
    throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    ParserConfigurationException,
    java.rmi.RemoteException,
    COM.ibm.eannounce.objects.EANBusinessRuleException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    IOException,
    javax.xml.transform.TransformerException,
	MissingResourceException
    {
		String t1DTS = profileT1.getValOn();
		String t2DTS = profileT2.getValOn();

		abr.addDebug("ADSWWCOMPATXMLABR.processThis checking between "+t1DTS+" and "+t2DTS);
		// find COMPATs
		WWCOMPAT_MESSAGE_COUNT = 0;
		abr.addDebug("ADSWWCOMPATXMLABR.withdraw offering set activity ='W' between "+t1DTS+" and "+t2DTS);
	    withdrawOffering(abr,t1DTS,t2DTS);
		processCompat(abr,t1DTS,t2DTS, profileT2, abr.getDatabase(),rootEntity);
//		Vector compatVct = getCompat(abr,t1DTS,t2DTS, profileT2, abr.getDatabase());
//		if (compatVct.size()==0){
//			//NO_CHANGES_FND=No Changes found for {0}
//			abr.addXMLGenMsg("NO_CHANGES_FND","ADSWWCOMPATXMLABR");
//		}else{
//			abr.addDebug("ADSWWCOMPATXMLABR.processThis found "+compatVct.size()+" WWTECHCOMPAT");
//
//			Vector mqVct =  getMQPropertiesFN(rootEntity,abr);
//
//			// filter wwcompat
//			if(isFilterWWCOMPAT){
//				Iterator it = wwcompMQTable.keySet().iterator();
//		 		while (it.hasNext()){
//		 			Vector filtercompatVct = new Vector();
//		 			String key =(String)it.next();
//		 			abr.addDebug("wwcompMQTable:key=" + key);
//		 			mqVct = (Vector)wwcompMQTable.get(key);
//		 			if(key.equals(CHEAT)){
//		 				processMQ(abr, profileT2, compatVct, mqVct);
//		 			}else{
//		 				for(int i=0;i<compatVct.size();i++){
//		 					CompatInfo ci = (CompatInfo)compatVct.elementAt(i);
//		 					String brandcd_fc = ci.brandcd_fc_xml;
//		 					if(key.equals(brandcd_fc)){
//		 						filtercompatVct.add(compatVct.get(i));
//		 					}
//			 			}
//		 				processMQ(abr, profileT2, filtercompatVct, mqVct);
//		 				//release momery
//		 				filtercompatVct.clear();
//		 			}
//		 		}
//		 		wwcompMQTable.clear();
//			}else{
//				processMQ(abr, profileT2, compatVct, mqVct);
//			}
//
//    		// release memory
//			compatVct.clear();
//		}
    }

	/**
	 * @param abr
	 * @param profileT2
	 * @param compatVct
	 * @param mqVct
	 * @throws ParserConfigurationException
	 * @throws DOMException
	 * @throws TransformerException
	 * @throws MissingResourceException
	 */
	private void processMQ(ADSABRSTATUS abr, Profile profileT2, Vector compatVct, Vector mqVct) throws ParserConfigurationException, DOMException, TransformerException, MissingResourceException {
		if (mqVct==null){
			abr.addDebug("ADSWWCOMPATXMLABR: No MQ properties files, nothing will be generated.");
			//NOT_REQUIRED = Not Required for {0}.
			abr.addXMLGenMsg("NOT_REQUIRED", "ADSWWCOMPATXMLABR");
		}else{
			// create one COMPATELEMENT for each one found
			if (compatVct.size() > MW_ENTITY_LIMIT) {
				int numGrps = 0;
				int numUsed = 0;
				Vector tmpVec = new Vector();
				if (compatVct.size() % MW_ENTITY_LIMIT != 0){
					numGrps = compatVct.size() / MW_ENTITY_LIMIT + 1;
				}else {
				    numGrps = compatVct.size() / MW_ENTITY_LIMIT;
				}
				WWCOMPAT_MESSAGE_COUNT = WWCOMPAT_MESSAGE_COUNT + numGrps;
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				for (int i = 0; i < numGrps; i++) {
					for (int x = 0; x < MW_ENTITY_LIMIT; x++) {
						if (numUsed == compatVct.size()) {
							break;
						}
						tmpVec.add(compatVct.elementAt(numUsed++));
					}
					Document document = builder.newDocument();  // Create
					String nodeName = "WWCOMPAT_UPDATE";
					String xmlns = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + nodeName;

					// Element parent = (Element) document.createElement("WWCOMPAT_UPDATE");
					Element parent = (Element) document.createElementNS(xmlns,nodeName);
					// create the root
					document.appendChild(parent);
					parent.setAttributeNS("http://www.w3.org/2000/xmlns/","xmlns",xmlns);
					parent.appendChild(document.createComment("WWCOMPAT_UPDATE Version "+XMLVERSION10+" Mod "+XMLMOD10));

		            Element elem = (Element) document.createElement("DTSOFMSG");
					elem.appendChild(document.createTextNode(profileT2.getEndOfDay()));
					parent.appendChild(elem);
					for (int j=0; j<tmpVec.size(); j++){
						CompatInfo ci = (CompatInfo)tmpVec.elementAt(j);
						Element compat = (Element) document.createElement("COMPATELEMENT");
						parent.appendChild(compat);

						elem = (Element) document.createElement("ACTIVITY");
						elem.appendChild(document.createTextNode(ci.activity_xml));
						compat.appendChild(elem);

//							Add	Ready	4,50		1	1,0	<BRANDCD>
						elem = (Element) document.createElement("BRANDCD");
						elem.appendChild(document.createTextNode(ci.brandcd_fc_xml));
						compat.appendChild(elem);
						
						//Defect 635138 There should be <STATUS> tag after BRANDCD tag-- always 0020( Final ) 
						elem = (Element) document.createElement("STATUS");
						elem.appendChild(document.createTextNode(ADSABRSTATUS.STATUS_FINAL));
						compat.appendChild(elem);

						elem = (Element) document.createElement("SYSTEMENTITYTYPE");
						elem.appendChild(document.createTextNode(ci.systementitytype_xml));
						compat.appendChild(elem);

						elem = (Element) document.createElement("SYSTEMENTITYID");
						elem.appendChild(document.createTextNode(ci.systementityid_xml));
						compat.appendChild(elem);

						elem = (Element) document.createElement("SYSTEMMACHTYPE");
						elem.appendChild(document.createTextNode(ci.systemmachtype_xml));
						compat.appendChild(elem);

		                elem = (Element) document.createElement("SYSTEMMODEL");
						elem.appendChild(document.createTextNode(ci.systemmodel_xml));
						compat.appendChild(elem);

		                elem = (Element) document.createElement("SYSTEMSEOID");
						elem.appendChild(document.createTextNode(ci.systemseoid_xml));
						compat.appendChild(elem);

		                elem = (Element) document.createElement("GROUPENTITYTYPE");
						elem.appendChild(document.createTextNode(ci.groupentitytype_xml));
						compat.appendChild(elem);

		                elem = (Element) document.createElement("GROUPENTITYID");
						elem.appendChild(document.createTextNode(ci.groupentityid_xml));
						compat.appendChild(elem);

		                elem = (Element) document.createElement("OKTOPUB");
						elem.appendChild(document.createTextNode(ci.oktopub_xml));
						compat.appendChild(elem);

		                elem = (Element) document.createElement("OPTIONENTITYTYPE");
						elem.appendChild(document.createTextNode(ci.optionentitytype_xml));
						compat.appendChild(elem);

		                elem = (Element) document.createElement("OPTIONENTITYID");
						elem.appendChild(document.createTextNode(ci.optionentityid_xml));
						compat.appendChild(elem);

		                elem = (Element) document.createElement("OPTIONSEOID");
						elem.appendChild(document.createTextNode(ci.optionseoid_xml));
						compat.appendChild(elem);

		                elem = (Element) document.createElement("COMPATPUBFLAG");
						elem.appendChild(document.createTextNode(ci.compatibilitypublishingflag_xml));
						compat.appendChild(elem);

		                elem = (Element) document.createElement("RELTYPE");
						elem.appendChild(document.createTextNode(ci.relationshiptype_xml));
						compat.appendChild(elem);

		                elem = (Element) document.createElement("PUBFROM");
						elem.appendChild(document.createTextNode(ci.publishfrom_xml));
						compat.appendChild(elem);

		                elem = (Element) document.createElement("PUBTO");
						elem.appendChild(document.createTextNode(ci.publishto_xml));
						compat.appendChild(elem);

						// release memory
						ci.dereference();
					}
						tmpVec.clear();
						String xml = abr.transformXML(this, document);
						
//						new added
						boolean ifpass = false;
					//	String entitytype = rootEntity.getEntityType();
						String entitytype = "XMLCOMPATSETUP";
						String ifNeed = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS" ,"_"+entitytype+"_XSDNEEDED","NO");
						if ("YES".equals(ifNeed.toUpperCase())) {
						   String xsdfile = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS","_"+entitytype+"_XSDFILE","NONE");
						    if ("NONE".equals(xsdfile)) {
						    	abr.addError("there is no xsdfile for "+entitytype+" defined in the propertyfile ");
						    } else {
						    	long rtm = System.currentTimeMillis();
						    	Class cs = this.getClass();
						    	StringBuffer debugSb = new StringBuffer();
						    	ifpass = ABRUtil.validatexml(cs,debugSb,xsdfile,xml);
						    	if (debugSb.length()>0){
						    		String s = debugSb.toString();
									if (s.indexOf("fail") != -1)
										abr.addError(s);
									abr.addOutput(s);
						    	}
						    	long ltm = System.currentTimeMillis();
								abr.addDebugComment(D.EBUG_DETAIL, "Time for validation: "+Stopwatch.format(ltm-rtm));
						    	if (ifpass) {
						    		abr.addDebug("the xml for "+entitytype+" passed the validation");
						    	}
						    }
						} else {
							abr.addOutput("the xml for "+entitytype+" doesn't need to be validated");
							ifpass = true;
						}

						//new added end

						//add flag(new added)
						if (xml != null && ifpass) {						
						if(ADSABRSTATUS.USERXML_OFF_LOG){
							//abr.addDebug("ADSWWCOMPATXMLABR: Generated MQ xml:"+ADSABRSTATUS.NEWLINE);
						}else{
							abr.addDebug("ADSWWCOMPATXMLABR: Generated MQ xml:"+ADSABRSTATUS.NEWLINE+xml+ADSABRSTATUS.NEWLINE);
						}
						abr.notify(this, "WWCOMPAT", xml, mqVct);
						}
						document = null;
						System.gc();
				}

			}else {
				WWCOMPAT_MESSAGE_COUNT = WWCOMPAT_MESSAGE_COUNT + 1;
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.newDocument();  // Create
				String nodeName = "WWCOMPAT_UPDATE";
				String xmlns = "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/" + nodeName;

				// Element parent = (Element) document.createElement("WWCOMPAT_UPDATE");
				Element parent = (Element) document.createElementNS(xmlns,nodeName);
				// create the root
				document.appendChild(parent);
				parent.setAttributeNS("http://www.w3.org/2000/xmlns/","xmlns",xmlns);
				parent.appendChild(document.createComment("WWCOMPAT_UPDATE Version "+XMLVERSION10+" Mod "+XMLMOD10));

		        Element elem = (Element) document.createElement("DTSOFMSG");
				elem.appendChild(document.createTextNode(profileT2.getEndOfDay()));
				parent.appendChild(elem);
				for (int i=0; i<compatVct.size(); i++){
					CompatInfo ci = (CompatInfo)compatVct.elementAt(i);
					Element compat = (Element) document.createElement("COMPATELEMENT");
					parent.appendChild(compat);

					elem = (Element) document.createElement("ACTIVITY");
					elem.appendChild(document.createTextNode(ci.activity_xml));
					compat.appendChild(elem);

//						Add	Ready	4,50		1	1,0	<BRANDCD>
					elem = (Element) document.createElement("BRANDCD");
					elem.appendChild(document.createTextNode(ci.brandcd_fc_xml));
					compat.appendChild(elem);
					
					//Defect 635138 There should be <STATUS> tag after BRANDCD tag-- always 0020( Final ) 
					elem = (Element) document.createElement("STATUS");
					elem.appendChild(document.createTextNode(ADSABRSTATUS.STATUS_FINAL));
					compat.appendChild(elem);

					elem = (Element) document.createElement("SYSTEMENTITYTYPE");
					elem.appendChild(document.createTextNode(ci.systementitytype_xml));
					compat.appendChild(elem);

					elem = (Element) document.createElement("SYSTEMENTITYID");
					elem.appendChild(document.createTextNode(ci.systementityid_xml));
					compat.appendChild(elem);

					elem = (Element) document.createElement("SYSTEMMACHTYPE");
					elem.appendChild(document.createTextNode(ci.systemmachtype_xml));
					compat.appendChild(elem);

		            elem = (Element) document.createElement("SYSTEMMODEL");
					elem.appendChild(document.createTextNode(ci.systemmodel_xml));
					compat.appendChild(elem);

		            elem = (Element) document.createElement("SYSTEMSEOID");
					elem.appendChild(document.createTextNode(ci.systemseoid_xml));
					compat.appendChild(elem);

		            elem = (Element) document.createElement("GROUPENTITYTYPE");
					elem.appendChild(document.createTextNode(ci.groupentitytype_xml));
					compat.appendChild(elem);

		            elem = (Element) document.createElement("GROUPENTITYID");
					elem.appendChild(document.createTextNode(ci.groupentityid_xml));
					compat.appendChild(elem);

		            elem = (Element) document.createElement("OKTOPUB");
					elem.appendChild(document.createTextNode(ci.oktopub_xml));
					compat.appendChild(elem);

		            elem = (Element) document.createElement("OPTIONENTITYTYPE");
					elem.appendChild(document.createTextNode(ci.optionentitytype_xml));
					compat.appendChild(elem);

		            elem = (Element) document.createElement("OPTIONENTITYID");
					elem.appendChild(document.createTextNode(ci.optionentityid_xml));
					compat.appendChild(elem);

		            elem = (Element) document.createElement("OPTIONSEOID");
					elem.appendChild(document.createTextNode(ci.optionseoid_xml));
					compat.appendChild(elem);

		            elem = (Element) document.createElement("COMPATPUBFLAG");
					elem.appendChild(document.createTextNode(ci.compatibilitypublishingflag_xml));
					compat.appendChild(elem);

		            elem = (Element) document.createElement("RELTYPE");
					elem.appendChild(document.createTextNode(ci.relationshiptype_xml));
					compat.appendChild(elem);

		            elem = (Element) document.createElement("PUBFROM");
					elem.appendChild(document.createTextNode(ci.publishfrom_xml));
					compat.appendChild(elem);

		            elem = (Element) document.createElement("PUBTO");
					elem.appendChild(document.createTextNode(ci.publishto_xml));
					compat.appendChild(elem);

					// release memory
					ci.dereference();
			}

			String xml = abr.transformXML(this, document);
			
			//new added
			boolean ifpass = false;
//			String entitytype = rootEntity.getEntityType();
			String entitytype = "XMLCOMPATSETUP";
			String ifNeed = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS" ,"_"+entitytype+"_XSDNEEDED","NO");
			if ("YES".equals(ifNeed.toUpperCase())) {
			   String xsdfile = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS","_"+entitytype+"_XSDFILE","NONE");
			    if ("NONE".equals(xsdfile)) {
			    	abr.addError("there is no xsdfile for "+entitytype+" defined in the propertyfile ");
			    } else {
			    	long rtm = System.currentTimeMillis();
			    	Class cs = this.getClass();
			    	StringBuffer debugSb = new StringBuffer();
			    	ifpass = ABRUtil.validatexml(cs,debugSb,xsdfile,xml);
			    	if (debugSb.length()>0){
			    		String s = debugSb.toString();
						if (s.indexOf("fail") != -1)
							abr.addError(s);
						abr.addOutput(s);
			    	}
			    	long ltm = System.currentTimeMillis();
					abr.addDebugComment(D.EBUG_DETAIL, "Time for validation: "+Stopwatch.format(ltm-rtm));
			    	if (ifpass) {
			    		abr.addDebug("the xml for "+entitytype+" passed the validation");
			    	}
			    }
			} else {
				abr.addOutput("the xml for "+entitytype+" doesn't need to be validated");
				ifpass = true;
			}

			//new added end
			//add flag(new added)
			if (xml != null && ifpass) {
			if(ADSABRSTATUS.USERXML_OFF_LOG){
				//abr.addDebug("ADSWWCOMPATXMLABR: Generated MQ xml:"+ADSABRSTATUS.NEWLINE);
			}else{
				abr.addDebug("ADSWWCOMPATXMLABR: Generated MQ xml:"+ADSABRSTATUS.NEWLINE+xml+ADSABRSTATUS.NEWLINE);
			}
			abr.notify(this, "WWCOMPAT", xml, mqVct);
			}
			document = null;
		}

}
	}

//	private Vector getCompat(ADSABRSTATUS abr,String t1DTS, String t2DTS, Profile profileT2,Database db)
// 	throws java.sql.SQLException
//	{
//		Vector rootVct = new Vector();
//        ResultSet result=null;
//		Connection connection=null;
//		PreparedStatement statement = null;
//        try {
//            connection = setupConnection();
//			statement = connection.prepareStatement(COMPAT_SQL);
//
//            //statement.clearParameters();
//            statement.setString(1, t1DTS);//"time1"
//            statement.setString(3, t1DTS);//"time1"
//            statement.setString(5, t1DTS);//"time1"
//            statement.setString(7, t1DTS);//"time1"
//            statement.setString(9, t1DTS);//"time1"
//            statement.setString(11, t1DTS);//"time1"
//            statement.setString(13, t1DTS);//"time1"
//            statement.setString(2, t2DTS);//"time2"
//            statement.setString(4, t2DTS);//"time2"
//            statement.setString(6, t2DTS);//"time2"
//            statement.setString(8, t2DTS);//"time2"
//            statement.setString(10, t2DTS);//"time2"
//            statement.setString(12, t2DTS);//"time2"
//            statement.setString(14, t2DTS);//"time2"
//
//            result = statement.executeQuery();
//            while(result.next()) {
//			    String activity = result.getString(1);
//                String systementitytype = result.getString(2);
//                int    systementityid = result.getInt(3);
//                String systemos = result.getString(4);
//                String groupentitytype = result.getString(5);
//                int    groupentityid = result.getInt(6);
//                String oktopub = result.getString(7);
//                String osentitytype = result.getString(8);
//                int    osentityid = result.getInt(9);
//                String os = result.getString(10);
//                String optionentitytype = result.getString(11);
//                int    optionentityid = result.getInt(12);
//                String compatibilitypublishingflag = result.getString(13);
//                String relationshiptype = result.getString(14);
//                String publishfrom = result.getString(15);
//                String publishto = result.getString(16);
//                String brandcd_fc = result.getString(17);
//                String systemseoid = result.getString(18);
//                String optionseoid = result.getString(19);
//                String systemmachtype = result.getString(20);
//                String systemmodel = result.getString(21);
//
//                /*
//
//                if (systementitytype != null && systementityid != 0) {
//                EntityGroup egs = new EntityGroup(null,db,profileT2,systementitytype.trim(),"Navigate");
//                EntityItem eis = new EntityItem(egs, profileT2, db, systementitytype.trim(),systementityid);
//                egs.putEntityItem(eis);
//                abr.addDebug("getCompat:"+systementitytype+systementityid+" entityitem: "+eis.getKey());
//                if (systementitytype.trim().equals("MODEL")) {
//                systemmachtype = PokUtils.getAttributeValue(eis, "MACHTYPEATR",", ", "", false);
//                if (systemmachtype.equals("")) systemmachtype = null;
//                systemmodel = PokUtils.getAttributeValue(eis, "MODELATR",", ", "", false);
//                if (systemmodel.equals("")) systemmodel = null;
//                }
//                else
//                	if (systementitytype.trim().equals("WWSEO") || systementitytype.trim().equals("LSEOBUNDLE")) {
//                	systemseoid = PokUtils.getAttributeValue(eis, "SEOID",", ", "", false);
//                	if (systemseoid.equals("")) systemseoid = null;
//                	}
//                egs.dereference();
//                }
//
//                if (optionentitytype != null && optionentityid != 0 &&
//                	(optionentitytype.trim().equals("WWSEO") || optionentitytype.trim().equals("LSEOBUNDLE")) 	) {
//                EntityGroup ego = new EntityGroup(null,db,profileT2,optionentitytype.trim(),"Navigate");
//                EntityItem eio = new EntityItem(ego, profileT2, db, optionentitytype.trim(),optionentityid);
//                abr.addDebug("getCompat:"+optionentitytype+optionentityid+" entityitem: "+eio.getKey());
//                ego.putEntityItem(eio);
//                optionseoid = PokUtils.getAttributeValue(eio, "SEOID",", ", "", false);
//                if (optionseoid.equals("")) optionseoid = null;
//                ego.dereference();
//
//                }
//                */
//
//                abr.addDebug("getCompat activity:"+activity+" systementitytype:"+systementitytype+
//                                " systementityid:"+systementityid+" systemos:"+systemos+" groupentitytype:"+groupentitytype+
//                                " groupentityid:"+groupentityid+" oktopub:"+oktopub+" osentitytype:"+osentitytype+
//                                " osentityid:"+osentityid+" osentityid:"+osentityid+" os:"+os+
//                                " optionentitytype:"+optionentitytype+" optionentityid:"+optionentityid+" compatibilitypublishingflag:"+compatibilitypublishingflag+
//                                " relationshiptype:"+relationshiptype+" publishfrom:"+publishfrom+" publishto:"+publishto+
//                                " brandcd_fc:"+brandcd_fc+" systemmachtype:"+systemmachtype+" systemmodel:"+systemmodel+
//                                " systemseoid:"+systemseoid+" optionseoid:"+optionseoid);
//
//				rootVct.add(new CompatInfo(activity,systementitytype,systementityid,systemos,
//                                     groupentitytype,groupentityid,oktopub,osentitytype,osentityid,os,optionentitytype,
//                                     optionentityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,
//                                     brandcd_fc,systemmachtype,systemmodel,systemseoid,optionseoid));
//
//            }
//        }
//        //catch(COM.ibm.opicmpdh.middleware.MiddlewareRequestException x){
//        //	abr.addDebug("MiddlewareRequestExeption"+x);
//		//}
//        //catch(COM.ibm.opicmpdh.middleware.MiddlewareException x){
//        //	abr.addDebug("MiddlewareExeption"+x);
//		//}
//        finally{
//			try {
//				if (statement!=null) {
//					statement.close();
//					statement=null;
//				}
//			}catch(Exception e){
//				System.err.println("getCompat(), unable to close statement. "+ e);
//				abr.addDebug("getCompat unable to close statement. "+e);
//			}
//            if (result!=null){
//                result.close();
//            }
//            closeConnection(connection);
//        }
//		return rootVct;
//	}
	/*//TODO
	 * According to Doc BH FS Catolog DB Compatbility Gen 20121011b.doc 
	 *  Withdrawn Offerings
     *  A preprocessing step is required to remove all withdrawn products (options -  right side) from the table in (section VI. ABR)
     *  that is generated by the ABRs. The first step is to set the Activity = W if the right side is withdrawn. 
     *  Queries can be developed to facilitate this processing. 
	 */
	private void withdrawOffering(ADSABRSTATUS abr,String t1DTS, String t2DTS) throws SQLException{
		ResultSet result=null;
		Connection conn=null;
		PreparedStatement statement = null;
		Vector vct = new Vector();
		long lastTime =System.currentTimeMillis();
		long runTime = 0;
        int counter = 0;
        try {
        	abr.addDebug("");
        	conn = setupConnection();
			statement = conn.prepareStatement(WITHDRAW_SQL);
            statement.clearParameters();
            statement.setString(1, t1DTS);//"time1"
            statement.setString(2, t2DTS);//"time1"
            statement.setString(3, t1DTS);//"time1"
            statement.setString(4, t2DTS);//"time1"
            statement.setString(5, t1DTS);//"time1"
            statement.setString(6, t2DTS);//"time1"
            result = statement.executeQuery();
            runTime = System.currentTimeMillis();
			abr.addDebugComment(D.EBUG_DETAIL, "Execute query SQL:" +WITHDRAW_SQL + "Time is : "+Stopwatch.format(runTime-lastTime)); 
            while(result.next()) {
                String systementitytype = result.getString(1);
                int    systementityid = result.getInt(2);
                String systemos = result.getString(3);
                String groupentitytype = result.getString(4);
                int    groupentityid = result.getInt(5);
                String osentitytype = result.getString(6);
                int    osentityid = result.getInt(7);
                String os = result.getString(8);
                String optionentitytype = result.getString(9);
                int    optionentityid = result.getInt(10);
                String[] list = new String[] {systementitytype,Integer.toString(systementityid),systemos,
                	                          groupentitytype,Integer.toString(groupentityid), 
                	                          osentitytype,Integer.toString(osentityid),os,
                	                          optionentitytype, Integer.toString(optionentityid)};
                vct.add(list);
                counter++;
                if (vct.size()>=5000)
                	updateWithdrawAct(abr,vct);
            }

            if (vct.size()>0){
            	updateWithdrawAct(abr,vct);
            }     
            runTime = System.currentTimeMillis();
			abr.addDebugComment(D.EBUG_DETAIL, "" + "Process withdraw offerings options products count:"+ counter + ". Total time is : "+Stopwatch.format(runTime-lastTime));
            
        }finally{
			try {
				if (statement!=null) {
					statement.close();
					statement=null;
				}
			}catch(Exception e){
				System.err.println("getCompat(), unable to close statement. "+ e);
				abr.addDebug("getCompat unable to close statement. "+e);
			}
            if (result!=null){
                result.close();
            }
            if (connection != null){
            	connection.close();
                connection = null;
            }
            
            closeConnection(conn);
        }
		
	}
	/**
	 * udpate activity = 'W' which withdraweffectivedate + 90 < current day
	 * @param abr
	 * @param vct
	 * @throws SQLException
	 */
	private void updateWithdrawAct(ADSABRSTATUS abr, Vector vct) throws SQLException {
		long startTime = System.currentTimeMillis();
		Iterator list = vct.iterator();
		
		PreparedStatement ps = null;
	
		try {
			//m_db.setAutoCommit(false);
			if (connection == null){
			    connection = setupConnection();
			}
		ps = connection.prepareStatement(WITHDRAWUPDATE_SQL);
		while (list.hasNext()) {
			String record[] = (String[]) list.next();
			ps.setString(1, record[0]);
			ps.setInt(2, Integer.parseInt(record[1]));
			ps.setString(3, record[2]);
			ps.setString(4, record[3]);
			ps.setInt(5, Integer.parseInt(record[4]));
			ps.setString(6, record[5]);
			ps.setInt(7, Integer.parseInt(record[6]));
			ps.setString(8, record[7]);
			ps.setString(9, record[8]);
			ps.setInt(10, Integer.parseInt(record[9]));
			ps.addBatch();
		}
		ps.executeBatch();
		if (connection != null){
			connection.commit();
		}
		//m_db.commit();
		abr.addDebug(vct.size() + " records was updated in the table wwtechcompat. Total Time: " + Stopwatch.format(System.currentTimeMillis() - startTime));
		}catch (SQLException rx) {
			abr.addDebug("SQLException on ? " + rx);
		    rx.printStackTrace();
		    throw rx;
		}finally {
			vct.clear();
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}		
	}	
	
    /**
     * processWWCOMPAT
     * @param abr
     * @param t1DTS
     * @param t2DTS
     * @param profileT2
     * @param db
     * @return
     * @throws java.sql.SQLException
     * @throws TransformerException
     * @throws ParserConfigurationException
     * @throws MissingResourceException
     * @throws DOMException
     */
	private void processCompat(ADSABRSTATUS abr,String t1DTS, String t2DTS, Profile profileT2,Database db,EntityItem rootEntity)
 	throws java.sql.SQLException, DOMException, MissingResourceException, ParserConfigurationException, TransformerException
	{
		Vector rootVct = new Vector();
        ResultSet result=null;
		Connection connection=null;
		PreparedStatement statement = null;
        try {
            connection = setupConnection();
			statement = connection.prepareStatement(COMPAT_SQL);

            //statement.clearParameters();
            statement.setString(1, t1DTS);//"time1"
            statement.setString(3, t1DTS);//"time1"
            statement.setString(5, t1DTS);//"time1"
            statement.setString(7, t1DTS);//"time1"
            statement.setString(9, t1DTS);//"time1"
            statement.setString(11, t1DTS);//"time1"
            statement.setString(13, t1DTS);//"time1"
            statement.setString(2, t2DTS);//"time2"
            statement.setString(4, t2DTS);//"time2"
            statement.setString(6, t2DTS);//"time2"
            statement.setString(8, t2DTS);//"time2"
            statement.setString(10, t2DTS);//"time2"
            statement.setString(12, t2DTS);//"time2"
            statement.setString(14, t2DTS);//"time2"

            result = statement.executeQuery();
            int counter = 1;
            int messageCount =0;
            while(result.next()) {
            	String activity = result.getString(1);
                String systementitytype = result.getString(2);
                int    systementityid = result.getInt(3);
                //String systemos = result.getString(4);
                String groupentitytype = result.getString(4);
                int    groupentityid = result.getInt(5);
                String oktopub = result.getString(6);
                //String osentitytype = result.getString(7);
                //int    osentityid = result.getInt(8);
                //String os = result.getString(10);
                String optionentitytype = result.getString(7);
                int    optionentityid = result.getInt(8);
                String compatibilitypublishingflag = result.getString(9);
                String relationshiptype = result.getString(10);
                String publishfrom = result.getString(11);
                String publishto = result.getString(12);
                String brandcd_fc = result.getString(13);
                String systemseoid = result.getString(14);
                String optionseoid = result.getString(15);
                String systemmachtype = result.getString(16);
                String systemmodel = result.getString(17);
//			    String activity = result.getString(1);
//                String systementitytype = result.getString(2);
//                int    systementityid = result.getInt(3);
//                String systemos = result.getString(4);
//                String groupentitytype = result.getString(5);
//                int    groupentityid = result.getInt(6);
//                String oktopub = result.getString(7);
//                String osentitytype = result.getString(8);
//                int    osentityid = result.getInt(9);
//                String os = result.getString(10);
//                String optionentitytype = result.getString(11);
//                int    optionentityid = result.getInt(12);
//                String compatibilitypublishingflag = result.getString(13);
//                String relationshiptype = result.getString(14);
//                String publishfrom = result.getString(15);
//                String publishto = result.getString(16);
//                String brandcd_fc = result.getString(17);
//                String systemseoid = result.getString(18);
//                String optionseoid = result.getString(19);
//                String systemmachtype = result.getString(20);
//                String systemmodel = result.getString(21);

                /*

                if (systementitytype != null && systementityid != 0) {
                EntityGroup egs = new EntityGroup(null,db,profileT2,systementitytype.trim(),"Navigate");
                EntityItem eis = new EntityItem(egs, profileT2, db, systementitytype.trim(),systementityid);
                egs.putEntityItem(eis);
                abr.addDebug("getCompat:"+systementitytype+systementityid+" entityitem: "+eis.getKey());
                if (systementitytype.trim().equals("MODEL")) {
                systemmachtype = PokUtils.getAttributeValue(eis, "MACHTYPEATR",", ", "", false);
                if (systemmachtype.equals("")) systemmachtype = null;
                systemmodel = PokUtils.getAttributeValue(eis, "MODELATR",", ", "", false);
                if (systemmodel.equals("")) systemmodel = null;
                }
                else
                	if (systementitytype.trim().equals("WWSEO") || systementitytype.trim().equals("LSEOBUNDLE")) {
                	systemseoid = PokUtils.getAttributeValue(eis, "SEOID",", ", "", false);
                	if (systemseoid.equals("")) systemseoid = null;
                	}
                egs.dereference();
                }

                if (optionentitytype != null && optionentityid != 0 &&
                	(optionentitytype.trim().equals("WWSEO") || optionentitytype.trim().equals("LSEOBUNDLE")) 	) {
                EntityGroup ego = new EntityGroup(null,db,profileT2,optionentitytype.trim(),"Navigate");
                EntityItem eio = new EntityItem(ego, profileT2, db, optionentitytype.trim(),optionentityid);
                abr.addDebug("getCompat:"+optionentitytype+optionentityid+" entityitem: "+eio.getKey());
                ego.putEntityItem(eio);
                optionseoid = PokUtils.getAttributeValue(eio, "SEOID",", ", "", false);
                if (optionseoid.equals("")) optionseoid = null;
                ego.dereference();

                }
                */

//                abr.addDebugComment(D.EBUG_INFO, "getCompat activity:"+activity+" systementitytype:"+systementitytype+
//                                " systementityid:"+systementityid+" systemos:"+systemos+" groupentitytype:"+groupentitytype+
//                                " groupentityid:"+groupentityid+" oktopub:"+oktopub+" osentitytype:"+osentitytype+
//                                " osentityid:"+osentityid+" osentityid:"+osentityid+" os:"+os+
//                                " optionentitytype:"+optionentitytype+" optionentityid:"+optionentityid+" compatibilitypublishingflag:"+compatibilitypublishingflag+
//                                " relationshiptype:"+relationshiptype+" publishfrom:"+publishfrom+" publishto:"+publishto+
//                                " brandcd_fc:"+brandcd_fc+" systemmachtype:"+systemmachtype+" systemmodel:"+systemmodel+
//                                " systemseoid:"+systemseoid+" optionseoid:"+optionseoid);
                if(!ADSABRSTATUS.USERXML_OFF_LOG){
                	abr.addDebugComment(D.EBUG_INFO, "getCompat activity:"+activity+" systementitytype:"+systementitytype+
                            " systementityid:"+systementityid+" groupentitytype:"+groupentitytype+
                            " groupentityid:"+groupentityid+" oktopub:"+oktopub+
                            " optionentitytype:"+optionentitytype+" optionentityid:"+optionentityid+" compatibilitypublishingflag:"+compatibilitypublishingflag+
                            " relationshiptype:"+relationshiptype+" publishfrom:"+publishfrom+" publishto:"+publishto+
                            " brandcd_fc:"+brandcd_fc+" systemmachtype:"+systemmachtype+" systemmodel:"+systemmodel+
                            " systemseoid:"+systemseoid+" optionseoid:"+optionseoid);                	
                }
                
				rootVct.add(new CompatInfo(activity,systementitytype,systementityid,XMLElem.CHEAT,
                                     groupentitytype,groupentityid,oktopub,XMLElem.CHEAT,1,XMLElem.CHEAT,optionentitytype,
                                     optionentityid,compatibilitypublishingflag,relationshiptype,publishfrom,publishto,
                                     brandcd_fc,systemmachtype,systemmodel,systemseoid,optionseoid));
				if (rootVct.size()>=WWCOMPAT_ROW_LIMIT){
					abr.addDebug("Chunking size is " +  WWCOMPAT_ROW_LIMIT + ". Start to run chunking "  + counter++  + " times.");
					sentToMQ(abr,rootVct,profileT2,rootEntity);
				}
            }

            if (rootVct.size()>0){
            	sentToMQ(abr,rootVct,profileT2,rootEntity);
            }
            abr.addDebug("WWCOMPAT_MESSAGE_COUNT is " +  WWCOMPAT_MESSAGE_COUNT);
            wwcompMQTable.clear();
        }
        //catch(COM.ibm.opicmpdh.middleware.MiddlewareRequestException x){
        //	abr.addDebug("MiddlewareRequestExeption"+x);
		//}
        //catch(COM.ibm.opicmpdh.middleware.MiddlewareException x){
        //	abr.addDebug("MiddlewareExeption"+x);
		//}
        finally{
			try {
				if (statement!=null) {
					statement.close();
					statement=null;
				}
			}catch(Exception e){
				System.err.println("getCompat(), unable to close statement. "+ e);
				abr.addDebug("getCompat unable to close statement. "+e);
			}
            if (result!=null){
                result.close();
            }
            closeConnection(connection);
        }
	}

	/**
	 *  set to MQ
	 * @param abr
	 * @param compatVct
	 * @param profileT2
	 * @param rootEntity
	 * @throws DOMException
	 * @throws MissingResourceException
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	private void sentToMQ(ADSABRSTATUS abr, Vector compatVct,Profile profileT2,EntityItem rootEntity) throws DOMException, MissingResourceException, ParserConfigurationException, TransformerException{
		if (compatVct.size()==0){
			//NO_CHANGES_FND=No Changes found for {0}
			abr.addXMLGenMsg("NO_CHANGES_FND","ADSWWCOMPATXMLABR");
		}else{
			abr.addDebug("ADSWWCOMPATXMLABR.processThis found "+compatVct.size()+" WWTECHCOMPAT");

//			Vector mqVct =  getMQPropertiesFN(rootEntity,abr);
//
//			// filter wwcompat
//			if(isFilterWWCOMPAT){
//				Iterator it = wwcompMQTable.keySet().iterator();
//		 		while (it.hasNext()){
//		 			Vector filtercompatVct = new Vector();
//		 			String key =(String)it.next();
//		 			abr.addDebug("wwcompMQTable:key=" + key);
//		 			mqVct = (Vector)wwcompMQTable.get(key);
//		 			if(key.equals(CHEAT)){
//		 				processMQ(abr, profileT2, compatVct, mqVct);
//		 			}else{
//		 				for(int i=0;i<compatVct.size();i++){
//		 					CompatInfo ci = (CompatInfo)compatVct.elementAt(i);
//		 					String brandcd_fc = ci.brandcd_fc_xml;
//		 					if(key.equals(brandcd_fc)){
//		 						filtercompatVct.add(compatVct.get(i));
//		 					}
//			 			}
//		 				processMQ(abr, profileT2, filtercompatVct, mqVct);
//		 				//release momery
//		 				filtercompatVct.clear();
//		 			}
//		 		}
//			}else{
//				processMQ(abr, profileT2, compatVct, mqVct);
//			}
			
			Vector mqVct =  getPeriodicMQ(rootEntity);
			//filter wwcompat
			Vector filtercompatVct = new Vector();//get the filter comat data which filter by BRANDCD
 			String key =convertValue(PokUtils.getAttributeFlagValue(rootEntity,"BRANDCD"));
 			String keydesc = getDescription(rootEntity, "BRANDCD","long");
 			if(key.equals("")) {
 				key = CHEAT;
 			}else{
 				abr.addXMLGenMsg("FILTER", "WWCOMPAT Periodic ABR is filtered by BRANDCD="+key+"("+keydesc+")");
 			} 			
 			abr.addDebug("wwcompat filter key =" + key);
 			abr.addDebug("wwcompat MQ vector  =" + mqVct);
 			
			if(key.equals(CHEAT)){
 				processMQ(abr, profileT2, compatVct, mqVct);	 				
 			}else{
 				for(int i=0;i<compatVct.size();i++){
 					CompatInfo ci = (CompatInfo)compatVct.elementAt(i);
 					String brandcd_fc = ci.brandcd_fc_xml;
 					if(key.equals(brandcd_fc)){
 						filtercompatVct.add(compatVct.get(i));
 					}
	 			}
 				processMQ(abr, profileT2, filtercompatVct, mqVct);
 				//release momery
 				filtercompatVct.clear();
 			}
    		// release memory
			compatVct.clear();
		}
	}
	
	/**
     * get the description of the item
     * @param item
     * @param code
     * @return
     */
	private String getDescription(EntityItem item, String code,String type) {
		String value=""; 
		EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute(code);
        if (fAtt!=null && fAtt.toString().length()>0){
            // Get the selected Flag codes.
            MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mfArray.length; i++){
                // get selection            	
                if (mfArray[i].isSelected())
                {
                	if (sb.length()>0) {
                        sb.append(","); 
                    }
                	if(type.equals("short")) {
                		sb.append(mfArray[i].getShortDescription());
                	} else if(type.equals("long")) {
                		sb.append(mfArray[i].getLongDescription());                	
                	} else if(type.equals("flag")) {
                		sb.append(mfArray[i].getFlagCode());                	
                	}
                	else{
                		sb.append(mfArray[i].toString());
                	}
                }                
            }//
            value = sb.toString();
        }
        return value;
	}
	
	private String convertValue(String fromValue){
    	return fromValue==null?"":fromValue;
    }
    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion() {
        return "1.6";//"1.4";
    }

    /**********************************
    *
	A.	MQ-Series CID
    */
    public String getMQCID() { return "WWCOMPAT_UPDATE"; }

    /**********************************
    * get the status attribute to use for this ABR
    */
    public String getStatusAttr() { return "ADSABRSTATUS";}

    private static class CompatInfo{

                String activity_xml = XMLElem.CHEAT;
                String systementitytype_xml = XMLElem.CHEAT;
                String systementityid_xml = XMLElem.CHEAT;
                String systemos_xml = XMLElem.CHEAT;
                String groupentitytype_xml = XMLElem.CHEAT;
                String groupentityid_xml = XMLElem.CHEAT;
                String oktopub_xml = XMLElem.CHEAT;
                String osentitytype_xml = XMLElem.CHEAT;
                String osentityid_xml = XMLElem.CHEAT;
                String os_xml = XMLElem.CHEAT;
                String optionentitytype_xml = XMLElem.CHEAT;
                String optionentityid_xml = XMLElem.CHEAT;
                String compatibilitypublishingflag_xml = XMLElem.CHEAT;
                String relationshiptype_xml = XMLElem.CHEAT;
                String publishfrom_xml = XMLElem.CHEAT;
                String publishto_xml = XMLElem.CHEAT;
                String brandcd_fc_xml  = XMLElem.CHEAT;
                String systemmachtype_xml  = XMLElem.CHEAT;
                String systemmodel_xml  = XMLElem.CHEAT;
                String systemseoid_xml  = XMLElem.CHEAT;
                String optionseoid_xml  = XMLElem.CHEAT;

		CompatInfo(String activity,
                            String systementitytype,
                            int    systementityid,
                            String systemos,
                            String groupentitytype,
                            int    groupentityid,
                            String oktopub,
                            String osentitytype,
                            int    osentityid,
                            String os,
                            String optionentitytype,
                            int    optionentityid,
                            String compatibilitypublishingflag,
                            String relationshiptype,
                            String publishfrom,
                            String publishto,
                            String brandcd_fc,
                            String systemmachtype,
                            String systemmodel,
                            String systemseoid,
                            String optionseoid)

                        {
			if (activity.equals("A") || activity.equals("C")){
				activity_xml = XMLElem.UPDATE_ACTIVITY;
			}
            else    activity_xml = XMLElem.DELETE_ACTIVITY;

			if (systementitytype != null){
				systementitytype_xml = systementitytype.trim();
			}

			if (systementityid != 0){
				systementityid_xml = Integer.toString(systementityid);
			}

			if (systemos != null){
				systemos_xml = systemos.trim();
			}

            if (groupentitytype != null){
				groupentitytype_xml = groupentitytype.trim();
			}

            if (groupentityid != 0){
				groupentityid_xml = Integer.toString(groupentityid);
			}

            if (oktopub != null){
				oktopub_xml = oktopub.trim();
            }
            if (osentitytype != null){
				osentitytype_xml = osentitytype.trim();
            }

            if (osentityid != 0){
				osentityid_xml = Integer.toString(osentityid);
			}

            if (os != null){
				os_xml = os.trim();
            }

            if (optionentitytype != null){
				optionentitytype_xml = optionentitytype.trim();
            }

            if (optionentityid != 0){
				optionentityid_xml = Integer.toString(optionentityid);
            }

            if (compatibilitypublishingflag != null){
				compatibilitypublishingflag_xml = compatibilitypublishingflag.trim();
            }

            if (relationshiptype != null){
				relationshiptype_xml = relationshiptype.trim();
            }

            if (publishfrom != null){
				publishfrom_xml = publishfrom.trim();
            }

            if (publishto != null){
				publishto_xml = publishto.trim();
            }

            if (brandcd_fc != null){
				brandcd_fc_xml = brandcd_fc.trim();
            }

            if (systemmachtype != null){
				systemmachtype_xml = systemmachtype.trim();
            }

            if (systemmodel != null){
				systemmodel_xml = systemmodel.trim();
            }

            if (systemseoid != null){
				systemseoid_xml = systemseoid.trim();
            }

            if (optionseoid != null){
				optionseoid_xml = optionseoid.trim();
            }


		}
		void dereference(){
		             activity_xml = null;
                     systementitytype_xml = null;
                     systementityid_xml = null;
                     systemos_xml = null;
                     groupentitytype_xml = null;
                     groupentityid_xml = null;
                     oktopub_xml = null;
                     osentitytype_xml = null;
                     osentityid_xml = null;
                     os_xml = null;
                     optionentitytype_xml = null;
                     optionentityid_xml = null;
                     compatibilitypublishingflag_xml = null;
                     relationshiptype_xml = null;
                     publishfrom_xml = null;
                     publishto_xml = null;
                     brandcd_fc_xml  = null;
                     systemmachtype_xml = null;
                     systemmodel_xml = null;
                     systemseoid_xml = null;
                     optionseoid_xml = null;

		}
	}
}
