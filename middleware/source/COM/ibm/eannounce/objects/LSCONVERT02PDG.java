//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: LSCONVERT02PDG.java,v $
// Revision 1.8  2008/09/04 14:23:56  wendy
// MN36762199 - always restore lscrsid
//
// Revision 1.7  2008/03/26 20:20:22  wendy
// Clean up RSA warnings
//
// Revision 1.6  2006/10/03 15:49:35  joan
// catch sbrexception
//
// Revision 1.5  2006/02/20 22:35:33  joan
// clean up system.out
//
// Revision 1.4  2005/07/25 18:18:29  joan
// fixes
//
// Revision 1.3  2005/05/10 17:42:05  joan
// fixes
//
// Revision 1.2  2003/11/25 20:34:05  joan
// adjust copy
//
// Revision 1.1  2003/10/14 16:26:15  minhthy
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

public class LSCONVERT02PDG extends PDGActionItem {
	static final long serialVersionUID = 20011106L;

	//private Vector m_vctReturnEntityKeys = new Vector();
	private String[] m_aCopyAttributes = {"LSLANGINDICATOR=LSLANGINDICATOR",
										"LSCRGLOBALREPTITLE=LSCRGLOBALREPTITLE",
										"LSCRSACTIVELANG=LSCRSACTIVELANG",
										"LSCRSPRICERCOMMENT=LSCRSPRICERCOMMENT",
										"LSCRSMKTCHAPPRIM=LSCRSMKTCHAPPRIM",
										"LSCRSPRTCATET=LSCRSPRTCATET",
										"LSCRSPRIVSTUDNEWMAX=LSCRSPRIVSTUDNEWMAX",
										"LSCRSCOURSEOVERVIEW=LSCRSCOURSEOVERVIEW",
										"LSCRSPRIVSTUDNEWADDPRICE=LSCRSPRIVSTUDNEWADDPRICE",
										"LSCRSPRIVSTUDMAX=LSCRSPRIVSTUDMAX",
										"LSCRSPRIVSTUDADDPRICE=LSCRSPRIVSTUDADDPRICE",
										"LSCRSPRIVPRICE=LSCRSPRIVPRICE",
										"LSCRSREMARKS=LSCRSREMARKS",
										"LSCRSPRIVNPRICEEFFDATE=LSCRSPRIVNPRICEEFFDATE",
										"LSCRSPRIVNEWPRICE=LSCRSPRIVNEWPRICE",
										"LSCRSPREREQDESC=LSCRSPREREQDESC",
										"LSCRSOTHERCOMTMS=LSCRSOTHERCOMTMS",
										"LSCRSOTHCOMTMSONWERNAME=LSCRSOTHCOMTMSONWERNAME",
										"LSCRSORD=LSCRSORD",
										"LSCRSOBJECTIVES=LSCRSOBJECTIVES",
										"LSCRSMKTSUBCHAPSEC=LSCRSMKTSUBCHAPSEC",
										"LSCRSMKTSUBCHAPPRI=LSCRSMKTSUBCHAPPRI",
										"LSCRSSECURITY=LSCRSSECURITY",
										"LSCRSMKTCHAPREG=LSCRSMKTCHAPREG",
										"LSCRSTIERCODE=LSCRSTIERCODE",
										"LSCRSMEDIA=LSCRSMEDIA",
										"LSCRSMKTCHAPSEC=LSCRSMKTCHAPSEC",
										"LSCRSMATLSETSTATUS=LSCRSMATLSETSTATUS",
										"PDHDOMAIN=PDHDOMAIN",
										"LSCRSWTPRICE=LSCRSWTPRICE",
										"LSCRSWTNEWPRICEDATE=LSCRSWTNEWPRICEDATE",
										"LSCRSTACREATEDATE=LSCRSTACREATEDATE",
										"LSCRSTOPICS=LSCRSTOPICS",
										"LSCRSSESLENGTH=LSCRSSESLENGTH",
										"LSCRSTASERVERINDICATOR=LSCRSTASERVERINDICATOR",
										"LSCRSTAAUD=LSCRSTAAUD",
										"LSCRSSUBDELIVERY=LSCRSSUBDELIVERY",
										"LSCRSSTUDENTMIN=LSCRSSTUDENTMIN",
										"LSCRSSTUDENTMAX=LSCRSSTUDENTMAX",
										"LSCRSSTUDENTMACHREQTS=LSCRSSTUDENTMACHREQTS",
										"LSCRSSPONSOR=@LSWW:LSCRSSPONSOR",
										"LSCRSSPECNOTES=LSCRSSPECNOTES",
										"LSCRSSESSTARTTIME=LSCRSSESSTARTTIME",
										"LSCRSWTNEWPRICE=LSCRSWTNEWPRICE",
										"LSCRSCATSKILLVDES=LSCRSCATSKILLVDES",
										"LSCRSCATSKILLLEV=LSCRSCATSKILLLEV",
										"LSCRSAVLDATE=LSCRSAVLDATE",
										"LSCRSAUDIENCEDESC=LSCRSAUDIENCEDESC",
										"LSCRSAUDIENCE=LSCRSAUDIENCE",
										"LSCRSADVERTISING=LSCRSADVERTISING",
										"LSCRSABSTRACT=LSCRSABSTRACT",
										"LSCCPRICINGSTATUS=LSCCPRICINGSTATUS",
										"LSCRSMATERIALSLANG=LSCRSMATERIALSLANG",
										"LSCRSCEU=LSCRSCEU",
										"LSCATADIND=LSCATADIND",
										"AUTOPRICER=AUTOPRICER",
										"LSCRSEXTNEWPRICE=LSCRSEXTNEWPRICE",
										"LSCRSLOCK=LSCRSLOCK",
										"LSCRSKEYWORD=LSCRSKEYWORD",
										"LSCRSINTPRICE=LSCRSINTPRICE",
										"LSCRSINTNPRICEEFFDATE=LSCRSINTNPRICEEFFDATE",
										"LSCRSINTNEWPRICE=LSCRSINTNEWPRICE",
										"LSCRSINSTRUCTORCOUNT=LSCRSINSTRUCTORCOUNT",
										"LSCRSINSTPREPHOURS=LSCRSINSTPREPHOURS",
										"LSCRSIBMTRADEMARKS=LSCRSIBMTRADEMARKS",
										"LSCRSCERIS=LSCRSCERIS",
										"LSCRSEXTNPRICEEFFDATE=LSCRSEXTNPRICEEFFDATE",
										"LSCRSCERTPGMDESC=LSCRSCERTPGMDESC",
										"LSCRSEXPDATE=LSCRSEXPDATE",
										"LSCRSDURATIONUNITS=LSCRSDURATIONUNITS",
										"LSCRSDURATIONTEXT=LSCRSDURATIONTEXT",
										"LSCRSDURATION=LSCRSDURATION",
										"LSCRSDRESSCODE=LSCRSDRESSCODE",
										"LSCRSDELIVERY=LSCRSDELIVERY",
										"LSCRSCREDITHOURS=LSCRSCREDITHOURS",
										"LSCRSDELFORMATDESC=LSCRSDELFORMATDESC",
										"LSCRSCOURSEMAT=LSCRSCOURSEMAT",
										"LSCRSEXTPRICE=LSCRSEXTPRICE"};

  	/*
  	* Version info
  	*/
  	public String getVersion() {
  		return "$Id: LSCONVERT02PDG.java,v 1.8 2008/09/04 14:23:56 wendy Exp $";
  	}


	public LSCONVERT02PDG(EANMetaFoundation  _mf, LSCONVERT02PDG _ai) throws MiddlewareRequestException {
		super(_mf, _ai);
	}

	/**
	* This represents a SBR Action Item.  It can only be constructed via a database connection, a Profile, and an Action Item Key
	*/
	public LSCONVERT02PDG(EANMetaFoundation _emf, Database _db, Profile _prof, String _strActionItemKey) throws SQLException, MiddlewareException, MiddlewareRequestException {
	  	super(_emf, _db,  _prof, _strActionItemKey);
	}

  	public String dump(boolean _bBrief) {
   		StringBuffer strbResult = new StringBuffer();
   		strbResult.append("LSCONVERT02PDG:" + getKey() + ":desc:" + getLongDescription());
  		strbResult.append(":purpose:" + getPurpose());
  		strbResult.append(":entitytype:" + getEntityType() + "/n");
   		return strbResult.toString();
  	}

  	public String getPurpose() {
  		return "LSCONVERT02PDG";
  	}

	protected StringBuffer checkMissingData(Database _db, Profile _prof, boolean _bGenData) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " LSCONVERT02PDG executeProduct method";
		StringBuffer sbReturn = new StringBuffer();

		//StringBuffer sb = new StringBuffer();

		String strFileName = "PDGtemplates/LSCONVERT02.txt";

		ExtractActionItem xai = new ExtractActionItem(null, _db, _prof, "EXTLSCONVERT02");

		m_sbActivities.append("<ul> ");

		OPICMList infoList = new OPICMList();
		infoList.put("LSCCF", m_eiRoot);
		infoList.put("LSWWCC", m_eiPDG);

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
						EntityItem eiLSCC = (EntityItem)m_savedEIList.getAt(i);
						if (eiLSCC.getEntityType().equals("LSCC")) {
							D.ebug(D.EBUG_SPEW,strTraceBase + " created "+eiLSCC.getKey());
							OPICMList copyAttList = m_utility.getCopyAttList(m_eiPDG, "LSCC", m_aCopyAttributes);
							m_utility.updateAttribute(_db, _prof, eiLSCC, copyAttList);
							break;
						}
					}
	            } finally {
	            	try{
	            		// turn it back on...
	            		D.ebug(D.EBUG_SPEW,strTraceBase + " call0028 LSCRSID back on for "+m_eiPDG.getKey());
	            		_db.callGBL0028(new ReturnStatus(-1),_prof.getEnterprise(),_prof.getOPWGID(),m_eiPDG.getEntityType(), m_eiPDG.getEntityID(),"LSCRSID",1);
	            		_db.commit();
	            		_db.freeStatement();
	            	}catch(Exception exc){
              			exc.printStackTrace();
              		}
              	}
			} else {
				m_sbActivities.append("<li>No LSCC created.");
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
		//String strTraceBase = " LSCONVERT02PDG viewMissingEntities method";
		return null;
	}

	public void executeAction(Database _db, Profile _prof) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		String strTraceBase = " LSCONVERT02PDG executeAction method ";
		m_SBREx = new SBRException();

		_db.debug(D.EBUG_DETAIL, strTraceBase);
		if (m_eiPDG == null) {
			System.out.println("LSWWCC entity is null");
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
		m_eiList.put("LSWWCC", m_eiPDG);

		// get LSCCF
		eg = m_ABReList.getEntityGroup("LSCCF");
		if (eg != null) {
			if (eg.getEntityItemCount() > 0) {
				m_eiRoot = eg.getEntityItem(0);
			}
		}
		_db.test(m_eiRoot != null, "LSCCF entity is null");
	}
}
