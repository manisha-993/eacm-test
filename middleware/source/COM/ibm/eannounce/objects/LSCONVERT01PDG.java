//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: LSCONVERT01PDG.java,v $
// Revision 1.11  2008/09/04 14:23:56  wendy
// MN36762199 - always restore lscrsid
//
// Revision 1.10  2008/03/26 20:18:22  wendy
// Clean up RSA warnings
//
// Revision 1.9  2006/10/03 15:49:35  joan
// catch sbrexception
//
// Revision 1.8  2006/02/20 21:39:47  joan
// clean up System.out.println
//
// Revision 1.7  2005/07/25 18:18:29  joan
// fixes
//
// Revision 1.6  2005/05/10 17:42:04  joan
// fixes
//
// Revision 1.5  2003/11/25 20:34:05  joan
// adjust copy
//
// Revision 1.4  2003/10/13 18:17:18  joan
// some adjustment
//
// Revision 1.3  2003/10/07 18:24:48  joan
// fix bug
//
// Revision 1.2  2003/10/06 23:34:21  joan
// work on LS ABR
//
// Revision 1.1  2003/10/03 20:16:54  joan
// initial load
//


package COM.ibm.eannounce.objects;

import java.sql.SQLException;

import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.transactions.OPICMList;

public class LSCONVERT01PDG extends PDGActionItem {
	static final long serialVersionUID = 20011106L;

	//private Vector m_vctReturnEntityKeys = new Vector();

	private String[] m_aCopyAttributes = {"LSLANGINDICATOR=LSLANGINDICATOR",
										"LSCRGLOBALREPTITLE=LSCRGLOBALREPTITLE",
										"LSCRSACTIVELANG=LSCRSACTIVELANG",
										"LSCRSPRICERCOMMENT=LSCRSPRICERCOMMENT",
										"LSCRSSESLENGTH=LSCRSSESLENGTH",
										"LSCRSSESSTARTTIME=LSCRSSESSTARTTIME",
										"LSCRSSPECNOTES=LSCRSSPECNOTES",
										"LSCRSSTUDENTMACHREQTS=LSCRSSTUDENTMACHREQTS",
										"LSCRSSTUDENTMIN=LSCRSSTUDENTMIN",
										"LSCRSSUBDELIVERY=LSCRSSUBDELIVERY",
										"LSCRSSECURITY=LSCRSSECURITY",
										"LSCRSPRIVNPRICEEFFDATE=LSCRSPRIVNPRICEEFFDATE",
										"LSCRSSTUDENTMAX=LSCRSSTUDENTMAX",
										"LSCRSREMARKS=LSCRSREMARKS",
										"LSCRSPRTCATET=LSCRSPRTCATET",
										"LSCRSPRIVSTUDNEWMAX=LSCRSPRIVSTUDNEWMAX",
										"LSCRSPRIVSTUDNEWADDPRICE=LSCRSPRIVSTUDNEWADDPRICE",
										"LSCRSPRIVSTUDMAX=LSCRSPRIVSTUDMAX",
										"LSCRSPRIVSTUDADDPRICE=LSCRSPRIVSTUDADDPRICE",
										"LSCRSTAAUD=LSCRSTAAUD",
										"PDHDOMAIN=PDHDOMAIN",
										"LSCRSPRIVNEWPRICE=LSCRSPRIVNEWPRICE",
										"LSCRSPREREQDESC=LSCRSPREREQDESC",
										"LSCRSOTHERCOMTMS=LSCRSOTHERCOMTMS",
										"LSCRSPRIVPRICE=LSCRSPRIVPRICE",
										"LSCRSMEDIA=LSCRSMEDIA",
										"LSCRSOTHCOMTMSONWERNAME=LSCRSOTHCOMTMSONWERNAME",
										"LSCRSWTNEWPRICEDATE=LSCRSWTNEWPRICEDATE",
										"LSCRSTACREATEDATE=LSCRSTACREATEDATE",
										"LSCRSWTPRICE=LSCRSWTPRICE",
										"LSCRSWTNEWPRICE=LSCRSWTNEWPRICE",
										"LSCRSTOPICS=LSCRSTOPICS",
										"LSCRSTIERCODE=LSCRSTIERCODE",
										"LSCRSTASERVERINDICATOR=LSCRSTASERVERINDICATOR",
										"LSCRSCATSKILLLEV=LSCRSCATSKILLLEV",
										"LSCRSDURATION=LSCRSDURATION",
										"LSCRSDRESSCODE=LSCRSDRESSCODE",
										"LSCRSDELIVERY=LSCRSDELIVERY",
										"LSCRSCREDITHOURS=LSCRSCREDITHOURS",
										"LSCRSCOURSEOVERVIEW=LSCRSCOURSEOVERVIEW",
										"LSCRSCOURSEMAT=LSCRSCOURSEMAT",
										"LSCRSCEU=LSCRSCEU",
										"LSCRSCERTPGMDESC=LSCRSCERTPGMDESC",
										"LSCRSDURATIONTEXT=LSCRSDURATIONTEXT",
										"LSCRSCATSKILLVDES=LSCRSCATSKILLVDES",
										"LSCRSAVLDATE=LSCRSAVLDATE",
										"LSCRSAUDIENCEDESC=LSCRSAUDIENCEDESC",
										"LSCRSAUDIENCE=LSCRSAUDIENCE",
										"LSCRSADVERTISING=LSCRSADVERTISING",
										"LSCRSABSTRACT=LSCRSABSTRACT",
										"LSCATADIND=LSCATADIND",
										"AUTOPRICER=AUTOPRICER",
										"LSCRSORD=LSCRSORD",
										"LSCRSMKTCHAPPRIM=LSCRSMKTCHAPPRIM",
										"LSCRSCERIS=LSCRSCERIS",
										"LSCRSOBJECTIVES=LSCRSOBJECTIVES",
										"LSCRSMKTSUBCHAPSEC=LSCRSMKTSUBCHAPSEC",
										"LSCRSMKTSUBCHAPPRI=LSCRSMKTSUBCHAPPRI",
										"LSCRSMKTCHAPSEC=LSCRSMKTCHAPSEC",
										"LSCRSMKTCHAPREG=LSCRSMKTCHAPREG",
										"LSCRSDELFORMATDESC=LSCRSDELFORMATDESC",
										"LSCRSMATLSETSTATUS=LSCRSMATLSETSTATUS",
										"LSCRSDURATIONUNITS=LSCRSDURATIONUNITS",
										"LSCRSMATERIALSLANG=LSCRSMATERIALSLANG",
										"LSCRSLOCK=LSCRSLOCK",
										"LSCRSKEYWORD=LSCRSKEYWORD",
										"LSCRSINTPRICE=LSCRSINTPRICE",
										"LSCRSINTNEWPRICE=LSCRSINTNEWPRICE",
										"LSCRSINSTRUCTORCOUNT=LSCRSINSTRUCTORCOUNT",
										"LSCRSINSTPREPHOURS=LSCRSINSTPREPHOURS",
										"LSCRSEXPDATE=LSCRSEXPDATE",
										"LSCRSIBMTRADEMARKS=LSCRSIBMTRADEMARKS",
										"LSCRSEXTNEWPRICE=LSCRSEXTNEWPRICE",
										"LSCRSEXTPRICE=LSCRSEXTPRICE",
										"LSCRSEXTNPRICEEFFDATE=LSCRSEXTNPRICEEFDATE",
										"LSCRSINTNPRICEEFFDATE=LSCRSINTNPRICEEFFDATE"
								};

	/*
  	* Version info
  	*/
  	public String getVersion() {
  		return "$Id: LSCONVERT01PDG.java,v 1.11 2008/09/04 14:23:56 wendy Exp $";
  	}


	public LSCONVERT01PDG(EANMetaFoundation  _mf, LSCONVERT01PDG _ai) throws MiddlewareRequestException {
		super(_mf, _ai);
	}

	/**
	* This represents a SBR Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
	*/
	public LSCONVERT01PDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
	  	super(_emf, _db,  _prof, _strActionItemKey);
	}

  	public String dump(boolean _bBrief) {
   		StringBuffer strbResult = new StringBuffer();
   		strbResult.append("LSCONVERT01PDG:" + getKey() + ":desc:" + getLongDescription());
  		strbResult.append(":purpose:" + getPurpose());
  		strbResult.append(":entitytype:" + getEntityType() + "/n");
   		return strbResult.toString();
  	}

  	public String getPurpose() {
  		return "LSCONVERT01PDG";
  	}

	protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " LSCONVERT01PDG executeProduct method";
		StringBuffer sbReturn = new StringBuffer();

		//StringBuffer sb = new StringBuffer();

		String strFileName = "PDGtemplates/LSCONVERT01.txt";

		ExtractActionItem xai = new ExtractActionItem(null, _db, _prof, "EXTLSCONVERT01");

		m_sbActivities.append("<ul> ");

		OPICMList infoList = new OPICMList();
		infoList.put("LSWW", m_eiRoot);
		infoList.put("LSCC", m_eiPDG);

		// get LSCT
		EntityGroup eg = m_ABReList.getEntityGroup("LSCT");
		if (eg != null) {
			if (eg.getEntityItemCount() > 0) {
				infoList.put("LSCT", eg.getEntityItem(0));
			}
		}

		_prof = m_utility.setProfValOnEffOn(_db, _prof);
		TestPDG pdgObject = new TestPDG(_db, _prof, m_eiRoot, infoList, xai, strFileName);
		StringBuffer sbMissing = pdgObject.getMissingEntities();
		D.ebug(D.EBUG_SPEW,strTraceBase +" "+m_eiPDG.getKey()+" root "+m_eiRoot.getKey()+" sbmissing: "+sbMissing);
		if (_bGenData) {
			if (sbMissing.toString().length() > 0) {
              	try {
                 	D.ebug(D.EBUG_SPEW,strTraceBase + " call0028 LSCRSID off for "+m_eiPDG.getKey());
                 	_db.callGBL0028(new ReturnStatus(-1),_prof.getEnterprise(),_prof.getOPWGID(),m_eiPDG.getEntityType(), m_eiPDG.getEntityID(),"LSCRSID",0);
                 	_db.commit();
                 	_db.freeStatement();
                 	_prof = m_utility.setProfValOnEffOn(_db, _prof);
					generateData(_db, _prof, sbMissing,"");

					for (int i=0; i < m_savedEIList.size(); i++) {
						EntityItem eiLSWWCC = (EntityItem)m_savedEIList.getAt(i);
						if (eiLSWWCC.getEntityType().equals("LSWWCC")) {
							D.ebug(D.EBUG_SPEW,strTraceBase + " created "+eiLSWWCC.getKey());
							OPICMList copyAttList = m_utility.getCopyAttList(m_eiPDG, "LSWWCC", m_aCopyAttributes);
							m_utility.updateAttribute(_db, _prof, eiLSWWCC, copyAttList);
							break;
						}
					}
	            } finally{
              		try{
//              		 turn it back on...
                      	D.ebug(D.EBUG_SPEW,strTraceBase + " call0028 LSCRSID back on for "+m_eiPDG.getKey());
                      	_db.callGBL0028(new ReturnStatus(-1),_prof.getEnterprise(),_prof.getOPWGID(),m_eiPDG.getEntityType(), m_eiPDG.getEntityID(),"LSCRSID",1);
                      	_db.commit();
                      	_db.freeStatement();
              		}catch(Exception exc){
              			exc.printStackTrace();
              		}
              	}
			} else {
				m_sbActivities.append("<li>No LSWWCC created.");
			}
		}
		m_sbActivities.append("</ul>");
		return sbReturn;
	}

	protected void checkPDGAttribute(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareRequestException, MiddlewareException, SBRException, MiddlewareShutdownInProgressException {
	}

	protected void resetVariables() {
		//m_vctReturnEntityKeys = new Vector();
		m_eiRoot = null;
	}

	public byte[] viewMissing(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		//String strTraceBase = " LSCONVERT01PDG viewMissingEntities method";
		return null;
	}

	public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " LSCONVERT01PDG executeAction method ";
		m_SBREx = new SBRException();

		_db.debug(D.EBUG_DETAIL, strTraceBase);
		if (m_eiPDG == null) {
			D.ebug(D.EBUG_SPEW,"LSCC entity is null");
			return;
		}

		// validate data
		checkDataAvailability(_db, _prof, m_eiPDG);
		if (m_SBREx.getErrorCount() > 0) {
			throw m_SBREx;
		}

		checkMissingData(_db, _prof, true).toString();
	}

	protected void checkDataAvailability(Database _db, Profile _prof, EntityItem _afirmEI) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		_prof = m_utility.setProfValOnEffOn(_db, _prof);

		EntityGroup eg = m_ABReList.getParentEntityGroup();
		m_eiPDG = eg.getEntityItem(m_eiPDG.getKey());
		m_eiList.put("LSCC", m_eiPDG);
		// get LSWW
		eg = m_ABReList.getEntityGroup("LSWW");
		if (eg != null) {
			if (eg.getEntityItemCount() > 0) {
				m_eiRoot = eg.getEntityItem(0);
			}
		}
		_db.test(m_eiRoot != null, "LSWW entity is null");
	}
}
