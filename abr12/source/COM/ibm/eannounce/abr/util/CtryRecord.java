package COM.ibm.eannounce.abr.util;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class CtryRecord extends XMLElem {

	public CtryRecord(String nname) {
		super(nname);
		// TODO Auto-generated constructor stub
	}

	boolean existfinalT1 = false;
	boolean existfinalT2 = false;
	String compatModel = "V200309";

	String action = CHEAT;

	// public String country;

	String availStatus = CHEAT; // AVAIL

	String pubfrom = CHEAT; // AVAIL/ PubFrom

	String pubto = CHEAT; // AVAIL/ PubTo

	String endofservice = CHEAT; // ENDOFSERVICE

	String anndate = CHEAT;

	String firstorder = CHEAT;

	String plannedavailability = CHEAT;

	String wdanndate = CHEAT;
	//Story 1865979 Withdrawal RFA Number generation-add eomannnum
	String eomannnum = CHEAT;

	String lastorder = CHEAT;
	
	String eodanndate = CHEAT;
	String eodavaildate = CHEAT;
	
	String eosanndate = CHEAT;
	//Story 1865979 Withdrawal RFA Number generation-add eosannnum
	String eosannnum = CHEAT;
	
	String annnumber = CHEAT;

	String rfraction = CHEAT;

	String rfravailStatus = CHEAT; // AVAIL

	String rfrpubfrom = CHEAT; // AVAIL/ PubFrom

	String rfrpubto = CHEAT; // AVAIL/ PubTo

	String rfrendofservice = CHEAT; // ENDOFSERVICE

	String rfranndate = CHEAT;

	String rfrfirstorder = CHEAT;

	String rfrplannedavailability = CHEAT;

	String rfrwdanndate = CHEAT;
	//Story 1865979 Withdrawal RFA Number generation-add rfreomannnum
	String rfreomannnum = CHEAT;
	
	String rfrlastorder = CHEAT;

	String rfreodanndate = CHEAT;
	String rfreodavaildate = CHEAT;
	
	String rfreosanndate = CHEAT;
	//Story 1865979 Withdrawal RFA Number generation-add rfreosannnum
	String rfreosannnum = CHEAT;
	
	String rfrannnumber = CHEAT;

	// 1615427: EACM SPF Feed to PEP - XML Update Activity(TMF mapping)
	String ordersysname = CHEAT;
	String rfrordersysname = CHEAT;

	String CDOCNO = CHEAT;
	String	EDOCNO = CHEAT;
	String ADOCNO = CHEAT;
	String rfrCDOCNO = CHEAT;
	String rfrEDOCNO = CHEAT;
	String rfrADOCNO = CHEAT;

	public String getOrderSysName() {
		return ordersysname;
	}

	public String getRfrOrderSysName() {
		return rfrordersysname;
	}

	public String getRfraction() {
		return rfraction;
	}

	public String getRfravailStatus() {
		return rfravailStatus;
	}

	public String getRfrpubfrom() {
		return rfrpubfrom;
	}

	public String getRfrpubto() {
		return rfrpubto;
	}

	public String getRfrendofservice() {
		return rfrendofservice;
	}

	public String getRfranndate() {
		return rfranndate;
	}

	public String getRfrfirstorder() {
		return rfrfirstorder;
	}

	public String getRfrplannedavailability() {
		return rfrplannedavailability;
	}

	public String getRfrwdanndate() {
		return rfrwdanndate;
	}
	
	public String getRfreomannnum(){
		return rfreomannnum;
	}	
	
	public String getRfrlastorder() {
		return rfrlastorder;
	}

	public String getRfreosanndate() {
		return rfreosanndate;
	}

	public String getRfreosannnum() {
		return rfreosannnum;
	}
		
	public String getRfreodanndate() {
		return rfreodanndate;
	}

	public String getRfreodavaildate() {
		return rfreodavaildate;
	}

	public String getRfrannnumber() {
		return rfrannnumber;
	}

	boolean isrfrDisplayable() {
		return !rfraction.equals(CHEAT);
	} // only display those with filled in actions

	void setrfrAction(String s) {
		rfraction = s;
	}

	String getAction() {
		return action;
	}

	// String getCountry() {
	// return country;
	// }

	// String getShipDate() { return earliestshipdate;}
	String getPubFrom() {
		return pubfrom;
	}

	String getPubTo() {
		return pubto;
	}

	String getEndOfService() {
		return endofservice;
	}

	String getAvailStatus() {
		return availStatus;
	}

	String getAnndate() {
		return anndate;
	}

	String getFirstorder() {
		return firstorder;
	}

	String getPlannedavailability() {
		return plannedavailability;
	}

	String getWdanndate() {
		return wdanndate;
	}
	
	String getEomannnum() {
		return eomannnum;
	}
	
	String getLastorder() {
		return lastorder;
	}

	String getEosanndate() {
		return eosanndate;
	}

	String getEosannnum() {
		return eosannnum;
	}
	
	public String getEodanndate() {
		return eodanndate;
	}

	public String getEodavaildate() {
		return eodavaildate;
	}

	String getAnnnumber() {
		return annnumber;
	}

	public String getCDOCNO() {
		return CDOCNO;
	}

	public void setCDOCNO(String CDOCNO) {
		this.CDOCNO = CDOCNO;
	}

	public String getEDOCNO() {
		return EDOCNO;
	}

	public void setEDOCNO(String EDOCNO) {
		this.EDOCNO = EDOCNO;
	}

	public String getADOCNO() {
		return ADOCNO;
	}

	public void setADOCNO(String ADOCNO) {
		this.ADOCNO = ADOCNO;
	}

	boolean isDeleted() {
		return action.equals(DELETE_ACTIVITY);
	}

	boolean isDisplayable() {
		return !action.equals(CHEAT);
	}

	void setAction(String s) {
		action = s;
	}

	boolean handleResults(String[] anndates, String[] anndatesT1, String[] annnumbers, String[] annnumbersT1,
			String[] firstorders, String[] firstordersT1, String[] plannedavailabilitys,
			String[] plannedavailabilitysT1, String[] pubfroms, String[] pubfromsT1, String[] pubtos, String[] pubtosT1,
			String[] wdanndates, String[] wdanndatesT1, String[] lastorders, String[] lastordersT1,
			String[] endofservices, String[] endofservicesT1, String[] eosanndates, String[] eosanndatesT1,
			String country, Element element, StringBuffer debugSb) {
		// TODO Compare T1 and T2 set action and rfraction
		String anndateT1 = CHEAT;
		String rfranndateT1 = CHEAT;
		String annnumberT1 = CHEAT;
		String rfrannnumberT1 = CHEAT;
		String firstorderT1 = CHEAT;
		String rfrfirstorderT1 = CHEAT;
		String plannedavailabilityT1 = CHEAT;
		String rfrplannedavailabilityT1 = CHEAT;
		String pubfromT1 = CHEAT;
		String rfrpubfromT1 = CHEAT;
		String pubtoT1 = CHEAT;
		String rfrpubtoT1 = CHEAT;
		String wdanndateT1 = CHEAT;
		String rfrwdanndateT1 = CHEAT;
		String lastorderT1 = CHEAT;
		String rfrlastorderT1 = CHEAT;
		String endofserviceT1 = CHEAT;
		String rfrendofserviceT1 = CHEAT;
		String eosanndateT1 = CHEAT;
		String rfreosanndateT1 = CHEAT;

		anndate = anndates[0];
		rfranndate = anndates[1];
		anndateT1 = anndatesT1[0];
		rfranndateT1 = anndatesT1[1];
		annnumber = annnumbers[0];
		rfrannnumber = annnumbers[1];
		annnumberT1 = annnumbersT1[0];
		rfrannnumberT1 = annnumbersT1[1];
		firstorder = firstorders[0];
		rfrfirstorder = firstorders[1];
		firstorderT1 = firstordersT1[0];
		rfrfirstorderT1 = firstordersT1[1];
		plannedavailability = plannedavailabilitys[0];
		rfrplannedavailability = plannedavailabilitys[1];
		plannedavailabilityT1 = plannedavailabilitysT1[0];
		rfrplannedavailabilityT1 = plannedavailabilitysT1[1];
		pubfrom = pubfroms[0];
		rfrpubfrom = pubfroms[1];
		pubfromT1 = pubfromsT1[0];
		rfrpubfromT1 = pubfromsT1[1];
		pubto = pubtos[0];
		rfrpubto = pubtos[1];
		pubtoT1 = pubtosT1[0];
		rfrpubtoT1 = pubtosT1[1];
		wdanndate = wdanndates[0];
		rfrwdanndate = wdanndates[1];
		wdanndateT1 = wdanndatesT1[0];
		rfrwdanndateT1 = wdanndatesT1[1];
		lastorder = lastorders[0];
		rfrlastorder = lastorders[1];
		lastorderT1 = lastordersT1[0];
		rfrlastorderT1 = lastordersT1[1];
		endofservice = endofservices[0];
		rfrendofservice = endofservices[1];
		endofserviceT1 = endofservicesT1[0];
		rfrendofserviceT1 = endofservicesT1[1];
		eosanndate = eosanndates[0];
		rfreosanndate = eosanndates[1];
		eosanndateT1 = eosanndatesT1[0];
		rfreosanndateT1 = eosanndatesT1[1];

		if (DELETE_ACTIVITY.equals(action)) {
			ABRUtil.append(debugSb, "setallfileds: coutry is delete:" + country);

			rfranndateT1 = copyfinaltoRFR(anndateT1, rfranndateT1, true, debugSb);
			rfrannnumberT1 = copyfinaltoRFR(annnumberT1, rfrannnumberT1, true, debugSb);
			rfrfirstorderT1 = copyfinaltoRFR(firstorderT1, rfrfirstorderT1, true, debugSb);
			rfrplannedavailabilityT1 = copyfinaltoRFR(plannedavailabilityT1, rfrplannedavailabilityT1, true, debugSb);
			rfrpubfromT1 = copyfinaltoRFR(pubfromT1, rfrpubfromT1, true, debugSb);
			rfrpubtoT1 = copyfinaltoRFR(pubtoT1, rfrpubtoT1, true, debugSb);
			rfrwdanndateT1 = copyfinaltoRFR(wdanndateT1, rfrwdanndateT1, true, debugSb);
			rfrlastorderT1 = copyfinaltoRFR(lastorderT1, rfrlastorderT1, true, debugSb);
			rfrendofserviceT1 = copyfinaltoRFR(endofserviceT1, rfrendofserviceT1, true, debugSb);
			rfreosanndateT1 = copyfinaltoRFR(eosanndateT1, rfreosanndateT1, true, debugSb);
			if (existfinalT1) {
				ABRUtil.append(debugSb, "setallfileds: coutry is exist final T1:" + country + NEWLINE);
				setAction(DELETE_ACTIVITY);
				setrfrAction(DELETE_ACTIVITY);
				setAllfieldsEmpty();

			} else {
				ABRUtil.append(debugSb, "setallfileds: coutry is not exist final T1:" + country + NEWLINE);
				setAction(CHEAT);
				setrfrAction(DELETE_ACTIVITY);
				setAllfieldsEmpty();

			}

		} else if (UPDATE_ACTIVITY.equals(action)) {
			ABRUtil.append(debugSb, "setallfileds: coutry is new:" + country + NEWLINE);

			rfranndate = copyfinaltoRFR(anndate, rfranndate, false, debugSb);
			rfrannnumber = copyfinaltoRFR(annnumber, rfrannnumber, false, debugSb);
			rfrfirstorder = copyfinaltoRFR(firstorder, rfrfirstorder, false, debugSb);
			rfrplannedavailability = copyfinaltoRFR(plannedavailability, rfrplannedavailability, false, debugSb);
			rfrpubfrom = copyfinaltoRFR(pubfrom, rfrpubfrom, false, debugSb);
			rfrpubto = copyfinaltoRFR(pubto, rfrpubto, false, debugSb);
			rfrwdanndate = copyfinaltoRFR(wdanndate, rfrwdanndate, false, debugSb);
			rfrlastorder = copyfinaltoRFR(lastorder, rfrlastorder, false, debugSb);
			rfrendofservice = copyfinaltoRFR(endofservice, rfrendofservice, false, debugSb);
			rfreosanndate = copyfinaltoRFR(eosanndate, rfreosanndate, false, debugSb);
			if (existfinalT2) {
				ABRUtil.append(debugSb, "setallfileds: coutry is  exist final T2:" + country + NEWLINE);
				setAction(UPDATE_ACTIVITY);
				setrfrAction(UPDATE_ACTIVITY);

			} else {
				ABRUtil.append(debugSb, "setallfileds: coutry is not exist final T2:" + country + NEWLINE);
				setAction(CHEAT);
				setrfrAction(UPDATE_ACTIVITY);

			}

		} else {
			ABRUtil.append(debugSb, "setallfileds: coutry is both exist T1 and T2:" + country + NEWLINE);

			rfranndateT1 = copyfinaltoRFR(anndateT1, rfranndateT1, true, debugSb);
			rfrannnumberT1 = copyfinaltoRFR(annnumberT1, rfrannnumberT1, true, debugSb);
			rfrfirstorderT1 = copyfinaltoRFR(firstorderT1, rfrfirstorderT1, true, debugSb);
			rfrplannedavailabilityT1 = copyfinaltoRFR(plannedavailabilityT1, rfrplannedavailabilityT1, true, debugSb);
			rfrpubfromT1 = copyfinaltoRFR(pubfromT1, rfrpubfromT1, true, debugSb);
			rfrpubtoT1 = copyfinaltoRFR(pubtoT1, rfrpubtoT1, true, debugSb);
			rfrwdanndateT1 = copyfinaltoRFR(wdanndateT1, rfrwdanndateT1, true, debugSb);
			rfrlastorderT1 = copyfinaltoRFR(lastorderT1, rfrlastorderT1, true, debugSb);
			rfrendofserviceT1 = copyfinaltoRFR(endofserviceT1, rfrendofserviceT1, true, debugSb);
			rfreosanndateT1 = copyfinaltoRFR(eosanndateT1, rfreosanndateT1, true, debugSb);

			rfranndate = copyfinaltoRFR(anndate, rfranndate, false, debugSb);
			rfrannnumber = copyfinaltoRFR(annnumber, rfrannnumber, false, debugSb);
			rfrfirstorder = copyfinaltoRFR(firstorder, rfrfirstorder, false, debugSb);
			rfrplannedavailability = copyfinaltoRFR(plannedavailability, rfrplannedavailability, false, debugSb);
			rfrpubfrom = copyfinaltoRFR(pubfrom, rfrpubfrom, false, debugSb);
			rfrpubto = copyfinaltoRFR(pubto, rfrpubto, false, debugSb);
			rfrwdanndate = copyfinaltoRFR(wdanndate, rfrwdanndate, false, debugSb);
			rfrlastorder = copyfinaltoRFR(lastorder, rfrlastorder, false, debugSb);
			rfrendofservice = copyfinaltoRFR(endofservice, rfrendofservice, false, debugSb);
			rfreosanndate = copyfinaltoRFR(eosanndate, rfreosanndate, false, debugSb);

			if (existfinalT1 && !existfinalT2) {
				ABRUtil.append(debugSb, "setallfileds: coutry  exist final T1 but T2:" + country + NEWLINE);
				setAction(DELETE_ACTIVITY);
				setfinalAllfieldsEmpty();

			} else if (existfinalT2 && !existfinalT1) {
				ABRUtil.append(debugSb, "setallfileds: coutry  exist final T2 but T1:" + country + NEWLINE);
				setAction(UPDATE_ACTIVITY);
				setrfrAction(UPDATE_ACTIVITY);

			} else if (existfinalT2 && existfinalT1) {
				ABRUtil.append(debugSb, "setallfileds: coutry  exist final T1 and T2:" + country + NEWLINE);
				compareT1vT2(anndate, anndateT1, false);
				compareT1vT2(annnumber, annnumberT1, false);
				compareT1vT2(firstorder, firstorderT1, false);
				compareT1vT2(plannedavailability, plannedavailabilityT1, false);
				compareT1vT2(pubfrom, pubfromT1, false);
				compareT1vT2(pubto, pubtoT1, false);
				compareT1vT2(wdanndate, wdanndateT1, false);
				compareT1vT2(lastorder, lastorderT1, false);
				compareT1vT2(endofservice, endofserviceT1, false);
				compareT1vT2(eosanndate, eosanndateT1, false);
				ABRUtil.append(debugSb, "setallfileds: after compare action :" + action + NEWLINE);
			} else {
				// not existfinalT1 && not existfinalT2
				ABRUtil.append(debugSb, "setallfileds: coutry  not exist final T1 and T2:" + country + NEWLINE);
				setAction(CHEAT);
			}
			compareT1vT2(rfranndate, rfranndateT1, true);
			compareT1vT2(rfrannnumber, rfrannnumberT1, true);
			compareT1vT2(rfrfirstorder, rfrfirstorderT1, true);
			compareT1vT2(rfrplannedavailability, rfrplannedavailabilityT1, true);
			compareT1vT2(rfrpubfrom, rfrpubfromT1, true);
			compareT1vT2(rfrpubto, rfrpubtoT1, true);
			compareT1vT2(rfrwdanndate, rfrwdanndateT1, true);
			compareT1vT2(rfrlastorder, rfrlastorderT1, true);
			compareT1vT2(rfrendofservice, rfrendofserviceT1, true);
			compareT1vT2(rfreosanndate, rfreosanndateT1, true);
			ABRUtil.append(debugSb, "setallfileds: after compare rfr values action:" + rfraction + NEWLINE);
		}
		// compatbility model V200309
		// Compatibility Mode will send a subset of <AVAILABILITYELEMENT> based on the
		// XML <STATUS> which may be different than the root entityStatus(STATUS).
		// IF <STATUS> = 0040, the use only <AVAILABILITYELEMENT> where the row in the
		// table has column 2 <STATUS> =RFR
		// IF <STATUS> = 0020, the use only <AVAILABILITYELEMENT> where the row in the
		// table has column 2 <STATUS> =Final

		String modelvalue = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS",
				"_" + "compatibility", CHEAT);
		ABRUtil.append(debugSb, "compatModel compatbility mode:" + modelvalue);

		if (!compatModel.equals(modelvalue)) {
			NodeList nodeList = element.getElementsByTagName("STATUS");
			int i = nodeList.getLength();
			// for (int j =0 ; j<i ;j++){
			// String status = nodeList.item(j).getFirstChild().getNodeValue();
			// ABRUtil.append(debugSb,"compatModel status:" + status);
			// }
			// ABRUtil.append(debugSb,"compatModel length :" + i);
			if (i > 0) {
				String rootvalue = nodeList.item(0).getFirstChild().getNodeValue();
				ABRUtil.append(debugSb, "compatModel root status:" + rootvalue);
				if (STATUS_FINAL.equals(rootvalue)) {
					setrfrAction(CHEAT);
				} else {
					setAction(CHEAT);
				}
			} else {
				ABRUtil.append(debugSb, "compatModel there is no status value");
			}

		}

		return existfinalT2;

	}

	boolean handleResults(String[] anndates, String[] anndatesT1, String[] annnumbers, String[] annnumbersT1,
			String[] firstorders, String[] firstordersT1, String[] plannedavailabilitys,
			String[] plannedavailabilitysT1, String[] pubfroms, String[] pubfromsT1, String[] pubtos, String[] pubtosT1,
			String[] wdanndates, String[] wdanndatesT1, String[] lastorders, String[] lastordersT1,
			String[] endofservices, String[] endofservicesT1, String[] eosanndates, String[] eosanndatesT1,
			String country, boolean isExistfinal, boolean isCompatmodel, StringBuffer debugSb) {
		// TODO Compare T1 and T2 set action and rfraction
		String anndateT1 = CHEAT;
		String rfranndateT1 = CHEAT;
		String annnumberT1 = CHEAT;
		String rfrannnumberT1 = CHEAT;
		String firstorderT1 = CHEAT;
		String rfrfirstorderT1 = CHEAT;
		String plannedavailabilityT1 = CHEAT;
		String rfrplannedavailabilityT1 = CHEAT;
		String pubfromT1 = CHEAT;
		String rfrpubfromT1 = CHEAT;
		String pubtoT1 = CHEAT;
		String rfrpubtoT1 = CHEAT;
		String wdanndateT1 = CHEAT;
		String rfrwdanndateT1 = CHEAT;
		String lastorderT1 = CHEAT;
		String rfrlastorderT1 = CHEAT;
		String endofserviceT1 = CHEAT;
		String rfrendofserviceT1 = CHEAT;
		String eosanndateT1 = CHEAT;
		String rfreosanndateT1 = CHEAT;

		anndate = anndates[0];
		rfranndate = anndates[1];
		anndateT1 = anndatesT1[0];
		rfranndateT1 = anndatesT1[1];
		annnumber = annnumbers[0];
		rfrannnumber = annnumbers[1];
		annnumberT1 = annnumbersT1[0];
		rfrannnumberT1 = annnumbersT1[1];
		firstorder = firstorders[0];
		rfrfirstorder = firstorders[1];
		firstorderT1 = firstordersT1[0];
		rfrfirstorderT1 = firstordersT1[1];
		plannedavailability = plannedavailabilitys[0];
		rfrplannedavailability = plannedavailabilitys[1];
		plannedavailabilityT1 = plannedavailabilitysT1[0];
		rfrplannedavailabilityT1 = plannedavailabilitysT1[1];
		pubfrom = pubfroms[0];
		rfrpubfrom = pubfroms[1];
		pubfromT1 = pubfromsT1[0];
		rfrpubfromT1 = pubfromsT1[1];
		pubto = pubtos[0];
		rfrpubto = pubtos[1];
		pubtoT1 = pubtosT1[0];
		rfrpubtoT1 = pubtosT1[1];
		wdanndate = wdanndates[0];
		rfrwdanndate = wdanndates[1];
		wdanndateT1 = wdanndatesT1[0];
		rfrwdanndateT1 = wdanndatesT1[1];
		lastorder = lastorders[0];
		rfrlastorder = lastorders[1];
		lastorderT1 = lastordersT1[0];
		rfrlastorderT1 = lastordersT1[1];
		endofservice = endofservices[0];
		rfrendofservice = endofservices[1];
		endofserviceT1 = endofservicesT1[0];
		rfrendofserviceT1 = endofservicesT1[1];
		eosanndate = eosanndates[0];
		rfreosanndate = eosanndates[1];
		eosanndateT1 = eosanndatesT1[0];
		rfreosanndateT1 = eosanndatesT1[1];

		if (DELETE_ACTIVITY.equals(action)) {
			ABRUtil.append(debugSb, "setallfileds: coutry is delete:" + country);

			rfranndateT1 = copyfinaltoRFR(anndateT1, rfranndateT1, true, debugSb);
			rfrannnumberT1 = copyfinaltoRFR(annnumberT1, rfrannnumberT1, true, debugSb);
			rfrfirstorderT1 = copyfinaltoRFR(firstorderT1, rfrfirstorderT1, true, debugSb);
			rfrplannedavailabilityT1 = copyfinaltoRFR(plannedavailabilityT1, rfrplannedavailabilityT1, true, debugSb);
			rfrpubfromT1 = copyfinaltoRFR(pubfromT1, rfrpubfromT1, true, debugSb);
			rfrpubtoT1 = copyfinaltoRFR(pubtoT1, rfrpubtoT1, true, debugSb);
			rfrwdanndateT1 = copyfinaltoRFR(wdanndateT1, rfrwdanndateT1, true, debugSb);
			rfrlastorderT1 = copyfinaltoRFR(lastorderT1, rfrlastorderT1, true, debugSb);
			rfrendofserviceT1 = copyfinaltoRFR(endofserviceT1, rfrendofserviceT1, true, debugSb);
			rfreosanndateT1 = copyfinaltoRFR(eosanndateT1, rfreosanndateT1, true, debugSb);
			if (existfinalT1) {
				ABRUtil.append(debugSb, "setallfileds: coutry is exist final T1:" + country + NEWLINE);
				setAction(DELETE_ACTIVITY);
				setrfrAction(DELETE_ACTIVITY);
				setAllfieldsEmpty();

			} else {
				ABRUtil.append(debugSb, "setallfileds: coutry is not exist final T1:" + country + NEWLINE);
				setAction(CHEAT);
				setrfrAction(DELETE_ACTIVITY);
				setAllfieldsEmpty();

			}

		} else if (UPDATE_ACTIVITY.equals(action)) {
			ABRUtil.append(debugSb, "setallfileds: coutry is new:" + country + NEWLINE);

			rfranndate = copyfinaltoRFR(anndate, rfranndate, false, debugSb);
			rfrannnumber = copyfinaltoRFR(annnumber, rfrannnumber, false, debugSb);
			rfrfirstorder = copyfinaltoRFR(firstorder, rfrfirstorder, false, debugSb);
			rfrplannedavailability = copyfinaltoRFR(plannedavailability, rfrplannedavailability, false, debugSb);
			rfrpubfrom = copyfinaltoRFR(pubfrom, rfrpubfrom, false, debugSb);
			rfrpubto = copyfinaltoRFR(pubto, rfrpubto, false, debugSb);
			rfrwdanndate = copyfinaltoRFR(wdanndate, rfrwdanndate, false, debugSb);
			rfrlastorder = copyfinaltoRFR(lastorder, rfrlastorder, false, debugSb);
			rfrendofservice = copyfinaltoRFR(endofservice, rfrendofservice, false, debugSb);
			rfreosanndate = copyfinaltoRFR(eosanndate, rfreosanndate, false, debugSb);
			if (existfinalT2) {
				ABRUtil.append(debugSb, "setallfileds: coutry is  exist final T2:" + country + NEWLINE);
				setAction(UPDATE_ACTIVITY);
				setrfrAction(UPDATE_ACTIVITY);

			} else {
				ABRUtil.append(debugSb, "setallfileds: coutry is not exist final T2:" + country + NEWLINE);
				setAction(CHEAT);
				setrfrAction(UPDATE_ACTIVITY);

			}

		} else {
			ABRUtil.append(debugSb, "setallfileds: coutry is both exist T1 and T2:" + country + NEWLINE);

			rfranndateT1 = copyfinaltoRFR(anndateT1, rfranndateT1, true, debugSb);
			rfrannnumberT1 = copyfinaltoRFR(annnumberT1, rfrannnumberT1, true, debugSb);
			rfrfirstorderT1 = copyfinaltoRFR(firstorderT1, rfrfirstorderT1, true, debugSb);
			rfrplannedavailabilityT1 = copyfinaltoRFR(plannedavailabilityT1, rfrplannedavailabilityT1, true, debugSb);
			rfrpubfromT1 = copyfinaltoRFR(pubfromT1, rfrpubfromT1, true, debugSb);
			rfrpubtoT1 = copyfinaltoRFR(pubtoT1, rfrpubtoT1, true, debugSb);
			rfrwdanndateT1 = copyfinaltoRFR(wdanndateT1, rfrwdanndateT1, true, debugSb);
			rfrlastorderT1 = copyfinaltoRFR(lastorderT1, rfrlastorderT1, true, debugSb);
			rfrendofserviceT1 = copyfinaltoRFR(endofserviceT1, rfrendofserviceT1, true, debugSb);
			rfreosanndateT1 = copyfinaltoRFR(eosanndateT1, rfreosanndateT1, true, debugSb);

			rfranndate = copyfinaltoRFR(anndate, rfranndate, false, debugSb);
			rfrannnumber = copyfinaltoRFR(annnumber, rfrannnumber, false, debugSb);
			rfrfirstorder = copyfinaltoRFR(firstorder, rfrfirstorder, false, debugSb);
			rfrplannedavailability = copyfinaltoRFR(plannedavailability, rfrplannedavailability, false, debugSb);
			rfrpubfrom = copyfinaltoRFR(pubfrom, rfrpubfrom, false, debugSb);
			rfrpubto = copyfinaltoRFR(pubto, rfrpubto, false, debugSb);
			rfrwdanndate = copyfinaltoRFR(wdanndate, rfrwdanndate, false, debugSb);
			rfrlastorder = copyfinaltoRFR(lastorder, rfrlastorder, false, debugSb);
			rfrendofservice = copyfinaltoRFR(endofservice, rfrendofservice, false, debugSb);
			rfreosanndate = copyfinaltoRFR(eosanndate, rfreosanndate, false, debugSb);

			if (existfinalT1 && !existfinalT2) {
				ABRUtil.append(debugSb, "setallfileds: coutry  exist final T1 but T2:" + country + NEWLINE);
				setAction(DELETE_ACTIVITY);
				setfinalAllfieldsEmpty();

			} else if (existfinalT2 && !existfinalT1) {
				ABRUtil.append(debugSb, "setallfileds: coutry  exist final T2 but T1:" + country + NEWLINE);
				setAction(UPDATE_ACTIVITY);
				setrfrAction(UPDATE_ACTIVITY);

			} else if (existfinalT2 && existfinalT1) {
				ABRUtil.append(debugSb, "setallfileds: coutry  exist final T1 and T2:" + country + NEWLINE);
				compareT1vT2(anndate, anndateT1, false);
				compareT1vT2(annnumber, annnumberT1, false);
				compareT1vT2(firstorder, firstorderT1, false);
				compareT1vT2(plannedavailability, plannedavailabilityT1, false);
				compareT1vT2(pubfrom, pubfromT1, false);
				compareT1vT2(pubto, pubtoT1, false);
				compareT1vT2(wdanndate, wdanndateT1, false);
				compareT1vT2(lastorder, lastorderT1, false);
				compareT1vT2(endofservice, endofserviceT1, false);
				compareT1vT2(eosanndate, eosanndateT1, false);
				ABRUtil.append(debugSb, "setallfileds: after compare action :" + action + NEWLINE);
			} else {
				// not existfinalT1 && not existfinalT2
				ABRUtil.append(debugSb, "setallfileds: coutry  not exist final T1 and T2:" + country + NEWLINE);
				setAction(CHEAT);
			}
			compareT1vT2(rfranndate, rfranndateT1, true);
			compareT1vT2(rfrannnumber, rfrannnumberT1, true);
			compareT1vT2(rfrfirstorder, rfrfirstorderT1, true);
			compareT1vT2(rfrplannedavailability, rfrplannedavailabilityT1, true);
			compareT1vT2(rfrpubfrom, rfrpubfromT1, true);
			compareT1vT2(rfrpubto, rfrpubtoT1, true);
			compareT1vT2(rfrwdanndate, rfrwdanndateT1, true);
			compareT1vT2(rfrlastorder, rfrlastorderT1, true);
			compareT1vT2(rfrendofservice, rfrendofserviceT1, true);
			compareT1vT2(rfreosanndate, rfreosanndateT1, true);
			ABRUtil.append(debugSb, "setallfileds: after compare rfr values action:" + rfraction + NEWLINE);
		}

		if (!isCompatmodel) {
			if (isExistfinal) {
				setrfrAction(CHEAT);
			} else {
				setAction(CHEAT);
			}
		}

		return existfinalT2;
	}
	/**
     * 
     * Story 1865979 add eomannnum  (SVCMODAVAILElem)
     */
    boolean handleResults(String[] anndates, String[] anndatesT1, String[] annnumbers, String[] annnumbersT1,
			String[] firstorders, String[] firstordersT1, String[] plannedavailabilitys,
			String[] plannedavailabilitysT1, String[] pubfroms, String[] pubfromsT1, String[] pubtos, String[] pubtosT1,
			String[] wdanndates, String[] wdanndatesT1, String[] eomannnums, String[] eomannnumsT1, 
			String[] lastorders, String[] lastordersT1, String[] endofservices, String[] endofservicesT1, 
			String[] eosanndates, String[] eosanndatesT1, String country, boolean isExistfinal,
			boolean isCompatmodel, StringBuffer debugSb) {
		// TODO Compare T1 and T2 set action and rfraction
		String anndateT1 = CHEAT;
		String rfranndateT1 = CHEAT;
		String annnumberT1 = CHEAT;
		String rfrannnumberT1 = CHEAT;
		String firstorderT1 = CHEAT;
		String rfrfirstorderT1 = CHEAT;
		String plannedavailabilityT1 = CHEAT;
		String rfrplannedavailabilityT1 = CHEAT;
		String pubfromT1 = CHEAT;
		String rfrpubfromT1 = CHEAT;
		String pubtoT1 = CHEAT;
		String rfrpubtoT1 = CHEAT;
		String wdanndateT1 = CHEAT;
		String rfrwdanndateT1 = CHEAT;
		String lastorderT1 = CHEAT;
		String rfrlastorderT1 = CHEAT;
		String endofserviceT1 = CHEAT;
		String rfrendofserviceT1 = CHEAT;
		String eosanndateT1 = CHEAT;
		String rfreosanndateT1 = CHEAT;
		String eomannnumT1 = CHEAT;
		String rfreomannnumT1 = CHEAT;

		anndate = anndates[0];
		rfranndate = anndates[1];
		anndateT1 = anndatesT1[0];
		rfranndateT1 = anndatesT1[1];
		annnumber = annnumbers[0];
		rfrannnumber = annnumbers[1];
		annnumberT1 = annnumbersT1[0];
		rfrannnumberT1 = annnumbersT1[1];
		firstorder = firstorders[0];
		rfrfirstorder = firstorders[1];
		firstorderT1 = firstordersT1[0];
		rfrfirstorderT1 = firstordersT1[1];
		plannedavailability = plannedavailabilitys[0];
		rfrplannedavailability = plannedavailabilitys[1];
		plannedavailabilityT1 = plannedavailabilitysT1[0];
		rfrplannedavailabilityT1 = plannedavailabilitysT1[1];
		pubfrom = pubfroms[0];
		rfrpubfrom = pubfroms[1];
		pubfromT1 = pubfromsT1[0];
		rfrpubfromT1 = pubfromsT1[1];
		pubto = pubtos[0];
		rfrpubto = pubtos[1];
		pubtoT1 = pubtosT1[0];
		rfrpubtoT1 = pubtosT1[1];
		wdanndate = wdanndates[0];
		rfrwdanndate = wdanndates[1];
		wdanndateT1 = wdanndatesT1[0];
		rfrwdanndateT1 = wdanndatesT1[1];
		lastorder = lastorders[0];
		rfrlastorder = lastorders[1];
		lastorderT1 = lastordersT1[0];
		rfrlastorderT1 = lastordersT1[1];
		endofservice = endofservices[0];
		rfrendofservice = endofservices[1];
		endofserviceT1 = endofservicesT1[0];
		rfrendofserviceT1 = endofservicesT1[1];
		eosanndate = eosanndates[0];
		rfreosanndate = eosanndates[1];
		eosanndateT1 = eosanndatesT1[0];
		rfreosanndateT1 = eosanndatesT1[1];
		eomannnum = eomannnums[0];
		rfreomannnum = eomannnums[1];
		eomannnumT1 = eomannnumsT1[0];
		rfreomannnumT1 = eomannnumsT1[1];

		if (DELETE_ACTIVITY.equals(action)) {
			ABRUtil.append(debugSb, "setallfileds: coutry is delete:" + country);

			rfranndateT1 = copyfinaltoRFR(anndateT1, rfranndateT1, true, debugSb);
			rfrannnumberT1 = copyfinaltoRFR(annnumberT1, rfrannnumberT1, true, debugSb);
			rfrfirstorderT1 = copyfinaltoRFR(firstorderT1, rfrfirstorderT1, true, debugSb);
			rfrplannedavailabilityT1 = copyfinaltoRFR(plannedavailabilityT1, rfrplannedavailabilityT1, true, debugSb);
			rfrpubfromT1 = copyfinaltoRFR(pubfromT1, rfrpubfromT1, true, debugSb);
			rfrpubtoT1 = copyfinaltoRFR(pubtoT1, rfrpubtoT1, true, debugSb);
			rfrwdanndateT1 = copyfinaltoRFR(wdanndateT1, rfrwdanndateT1, true, debugSb);
			rfrlastorderT1 = copyfinaltoRFR(lastorderT1, rfrlastorderT1, true, debugSb);
			rfrendofserviceT1 = copyfinaltoRFR(endofserviceT1, rfrendofserviceT1, true, debugSb);
			rfreosanndateT1 = copyfinaltoRFR(eosanndateT1, rfreosanndateT1, true, debugSb);
			rfreomannnumT1 = copyfinaltoRFR(eomannnumT1, rfreomannnumT1, true, debugSb);
			if (existfinalT1) {
				ABRUtil.append(debugSb, "setallfileds: coutry is exist final T1:" + country + NEWLINE);
				setAction(DELETE_ACTIVITY);
				setrfrAction(DELETE_ACTIVITY);
				setAllfieldsEmpty();

			} else {
				ABRUtil.append(debugSb, "setallfileds: coutry is not exist final T1:" + country + NEWLINE);
				setAction(CHEAT);
				setrfrAction(DELETE_ACTIVITY);
				setAllfieldsEmpty();

			}

		} else if (UPDATE_ACTIVITY.equals(action)) {
			ABRUtil.append(debugSb, "setallfileds: coutry is new:" + country + NEWLINE);

			rfranndate = copyfinaltoRFR(anndate, rfranndate, false, debugSb);
			rfrannnumber = copyfinaltoRFR(annnumber, rfrannnumber, false, debugSb);
			rfrfirstorder = copyfinaltoRFR(firstorder, rfrfirstorder, false, debugSb);
			rfrplannedavailability = copyfinaltoRFR(plannedavailability, rfrplannedavailability, false, debugSb);
			rfrpubfrom = copyfinaltoRFR(pubfrom, rfrpubfrom, false, debugSb);
			rfrpubto = copyfinaltoRFR(pubto, rfrpubto, false, debugSb);
			rfrwdanndate = copyfinaltoRFR(wdanndate, rfrwdanndate, false, debugSb);
			rfrlastorder = copyfinaltoRFR(lastorder, rfrlastorder, false, debugSb);
			rfrendofservice = copyfinaltoRFR(endofservice, rfrendofservice, false, debugSb);
			rfreosanndate = copyfinaltoRFR(eosanndate, rfreosanndate, false, debugSb);
			rfreomannnum = copyfinaltoRFR(eomannnum, rfreomannnum, false, debugSb);
			if (existfinalT2) {
				ABRUtil.append(debugSb, "setallfileds: coutry is  exist final T2:" + country + NEWLINE);
				setAction(UPDATE_ACTIVITY);
				setrfrAction(UPDATE_ACTIVITY);

			} else {
				ABRUtil.append(debugSb, "setallfileds: coutry is not exist final T2:" + country + NEWLINE);
				setAction(CHEAT);
				setrfrAction(UPDATE_ACTIVITY);

			}

		} else {
			ABRUtil.append(debugSb, "setallfileds: coutry is both exist T1 and T2:" + country + NEWLINE);

			rfranndateT1 = copyfinaltoRFR(anndateT1, rfranndateT1, true, debugSb);
			rfrannnumberT1 = copyfinaltoRFR(annnumberT1, rfrannnumberT1, true, debugSb);
			rfrfirstorderT1 = copyfinaltoRFR(firstorderT1, rfrfirstorderT1, true, debugSb);
			rfrplannedavailabilityT1 = copyfinaltoRFR(plannedavailabilityT1, rfrplannedavailabilityT1, true, debugSb);
			rfrpubfromT1 = copyfinaltoRFR(pubfromT1, rfrpubfromT1, true, debugSb);
			rfrpubtoT1 = copyfinaltoRFR(pubtoT1, rfrpubtoT1, true, debugSb);
			rfrwdanndateT1 = copyfinaltoRFR(wdanndateT1, rfrwdanndateT1, true, debugSb);
			rfrlastorderT1 = copyfinaltoRFR(lastorderT1, rfrlastorderT1, true, debugSb);
			rfrendofserviceT1 = copyfinaltoRFR(endofserviceT1, rfrendofserviceT1, true, debugSb);
			rfreosanndateT1 = copyfinaltoRFR(eosanndateT1, rfreosanndateT1, true, debugSb);
			rfreomannnumT1 = copyfinaltoRFR(eomannnumT1, rfreomannnumT1, true, debugSb);

			rfranndate = copyfinaltoRFR(anndate, rfranndate, false, debugSb);
			rfrannnumber = copyfinaltoRFR(annnumber, rfrannnumber, false, debugSb);
			rfrfirstorder = copyfinaltoRFR(firstorder, rfrfirstorder, false, debugSb);
			rfrplannedavailability = copyfinaltoRFR(plannedavailability, rfrplannedavailability, false, debugSb);
			rfrpubfrom = copyfinaltoRFR(pubfrom, rfrpubfrom, false, debugSb);
			rfrpubto = copyfinaltoRFR(pubto, rfrpubto, false, debugSb);
			rfrwdanndate = copyfinaltoRFR(wdanndate, rfrwdanndate, false, debugSb);
			rfrlastorder = copyfinaltoRFR(lastorder, rfrlastorder, false, debugSb);
			rfrendofservice = copyfinaltoRFR(endofservice, rfrendofservice, false, debugSb);
			rfreosanndate = copyfinaltoRFR(eosanndate, rfreosanndate, false, debugSb);
			rfreomannnum = copyfinaltoRFR(eomannnum, rfreomannnum, false, debugSb);

			if (existfinalT1 && !existfinalT2) {
				ABRUtil.append(debugSb, "setallfileds: coutry  exist final T1 but T2:" + country + NEWLINE);
				setAction(DELETE_ACTIVITY);
				setfinalAllfieldsEmpty();

			} else if (existfinalT2 && !existfinalT1) {
				ABRUtil.append(debugSb, "setallfileds: coutry  exist final T2 but T1:" + country + NEWLINE);
				setAction(UPDATE_ACTIVITY);
				setrfrAction(UPDATE_ACTIVITY);

			} else if (existfinalT2 && existfinalT1) {
				ABRUtil.append(debugSb, "setallfileds: coutry  exist final T1 and T2:" + country + NEWLINE);
				compareT1vT2(anndate, anndateT1, false);
				compareT1vT2(annnumber, annnumberT1, false);
				compareT1vT2(firstorder, firstorderT1, false);
				compareT1vT2(plannedavailability, plannedavailabilityT1, false);
				compareT1vT2(pubfrom, pubfromT1, false);
				compareT1vT2(pubto, pubtoT1, false);
				compareT1vT2(wdanndate, wdanndateT1, false);
				compareT1vT2(lastorder, lastorderT1, false);
				compareT1vT2(endofservice, endofserviceT1, false);
				compareT1vT2(eosanndate, eosanndateT1, false);
				compareT1vT2(eomannnum, eomannnumT1, false);
				ABRUtil.append(debugSb, "setallfileds: after compare action :" + action + NEWLINE);
			} else {
				// not existfinalT1 && not existfinalT2
				ABRUtil.append(debugSb, "setallfileds: coutry  not exist final T1 and T2:" + country + NEWLINE);
				setAction(CHEAT);
			}
			compareT1vT2(rfranndate, rfranndateT1, true);
			compareT1vT2(rfrannnumber, rfrannnumberT1, true);
			compareT1vT2(rfrfirstorder, rfrfirstorderT1, true);
			compareT1vT2(rfrplannedavailability, rfrplannedavailabilityT1, true);
			compareT1vT2(rfrpubfrom, rfrpubfromT1, true);
			compareT1vT2(rfrpubto, rfrpubtoT1, true);
			compareT1vT2(rfrwdanndate, rfrwdanndateT1, true);
			compareT1vT2(rfrlastorder, rfrlastorderT1, true);
			compareT1vT2(rfrendofservice, rfrendofserviceT1, true);
			compareT1vT2(rfreosanndate, rfreosanndateT1, true);
			compareT1vT2(rfreomannnum, rfreomannnumT1, true);
			ABRUtil.append(debugSb, "setallfileds: after compare rfr values action:" + rfraction + NEWLINE);
		}

		if (!isCompatmodel) {
			if (isExistfinal) {
				setrfrAction(CHEAT);
			} else {
				setAction(CHEAT);
			}
		}

		return existfinalT2;
	}

	 /**
     * 
     * Story 1865979 add eomannnum & eosannnum (TMFAVAILElem & MODELCONVERT)
     */
    boolean handleResults(String[] anndates, String[] anndatesT1, String[] annnumbers, String[] annnumbersT1, String[] firstorders,
      		String[] firstordersT1, String[] plannedavailabilitys, String[] plannedavailabilitysT1, String[] pubfroms,
      		String[] pubfromsT1, String[] pubtos, String[] pubtosT1, String[] wdanndates, String[] wdanndatesT1, String[] eomannnums,
      		String[] eomannnumsT1, String[] lastorders, String[] lastordersT1, String[] endofservices, String[] endofservicesT1, 
      		String[] eosanndates, String[] eosanndatesT1, String[] eosannnums, String[] eosannnumsT1, String country, 
      		boolean isExistfinal, boolean isCompatmodel, StringBuffer debugSb) {
      		//TODO Compare T1 and T2 set action and rfraction
      		String anndateT1 = CHEAT;
      		String rfranndateT1 = CHEAT;
      		String annnumberT1 = CHEAT;
      		String rfrannnumberT1 = CHEAT;
      		String firstorderT1 = CHEAT;
      		String rfrfirstorderT1 = CHEAT;
      		String plannedavailabilityT1 = CHEAT;
      		String rfrplannedavailabilityT1 = CHEAT;
      		String pubfromT1 = CHEAT;
      		String rfrpubfromT1 = CHEAT;
      		String pubtoT1 = CHEAT;
      		String rfrpubtoT1 = CHEAT;
      		String wdanndateT1 = CHEAT;
      		String rfrwdanndateT1 = CHEAT;
      		String eomannnumT1 = CHEAT;
      		String rfreomannnumT1 = CHEAT;
      		String lastorderT1 = CHEAT;
      		String rfrlastorderT1 = CHEAT;
      		String endofserviceT1 = CHEAT;
      		String rfrendofserviceT1 = CHEAT;
      		String eosanndateT1 = CHEAT;
      		String rfreosanndateT1 = CHEAT;
      		String eosannnumT1 = CHEAT;
      		String rfreosannnumT1 = CHEAT;

      		anndate = anndates[0];
      		rfranndate = anndates[1];
      		anndateT1 = anndatesT1[0];
      		rfranndateT1 = anndatesT1[1];
      		annnumber = annnumbers[0];
      		rfrannnumber = annnumbers[1];
      		annnumberT1 = annnumbersT1[0];
      		rfrannnumberT1 = annnumbersT1[1];
      		firstorder = firstorders[0];
      		rfrfirstorder = firstorders[1];
      		firstorderT1 = firstordersT1[0];
      		rfrfirstorderT1 = firstordersT1[1];
      		plannedavailability = plannedavailabilitys[0];
      		rfrplannedavailability = plannedavailabilitys[1];
      		plannedavailabilityT1 = plannedavailabilitysT1[0];
      		rfrplannedavailabilityT1 = plannedavailabilitysT1[1];
      		pubfrom = pubfroms[0];
      		rfrpubfrom = pubfroms[1];
      		pubfromT1 = pubfromsT1[0];
      		rfrpubfromT1 = pubfromsT1[1];
      		pubto = pubtos[0];
      		rfrpubto = pubtos[1];
      		pubtoT1 = pubtosT1[0];
      		rfrpubtoT1 = pubtosT1[1];
      		wdanndate = wdanndates[0];
      		rfrwdanndate = wdanndates[1];
      		wdanndateT1 = wdanndatesT1[0];
      		rfrwdanndateT1 = wdanndatesT1[1];
      		eomannnum = eomannnums[0];
      		rfreomannnum = eomannnums[1];
      		eomannnumT1 = eomannnumsT1[0];
      		rfreomannnumT1 = eomannnumsT1[1];
      		lastorder = lastorders[0];
      		rfrlastorder = lastorders[1];
      		lastorderT1 = lastordersT1[0];
      		rfrlastorderT1 = lastordersT1[1];
      		endofservice = endofservices[0];
      		rfrendofservice = endofservices[1];
      		endofserviceT1 = endofservicesT1[0];
      		rfrendofserviceT1 = endofservicesT1[1];
      		eosanndate = eosanndates[0];
      		rfreosanndate = eosanndates[1];
      		eosanndateT1 = eosanndatesT1[0];
      		rfreosanndateT1 = eosanndatesT1[1];
      		eosannnum = eosannnums[0];
      		rfreosannnum = eosannnums[1];
      		eosannnumT1 = eosannnumsT1[0];
      		rfreosannnumT1 = eosannnumsT1[1];

      		if (DELETE_ACTIVITY.equals(action)) {
      			ABRUtil.append(debugSb,"setallfileds: coutry is delete:" + country);

      			rfranndateT1 = copyfinaltoRFR(anndateT1, rfranndateT1, true, debugSb);
      			rfrannnumberT1= copyfinaltoRFR(annnumberT1, rfrannnumberT1, true, debugSb);
      			rfrfirstorderT1 = copyfinaltoRFR(firstorderT1, rfrfirstorderT1, true, debugSb);
      			rfrplannedavailabilityT1 = copyfinaltoRFR(plannedavailabilityT1, rfrplannedavailabilityT1, true, debugSb);
      			rfrpubfromT1 = copyfinaltoRFR(pubfromT1, rfrpubfromT1, true, debugSb);
      			rfrpubtoT1 = copyfinaltoRFR(pubtoT1, rfrpubtoT1, true, debugSb);
      			rfrwdanndateT1 = copyfinaltoRFR(wdanndateT1, rfrwdanndateT1, true, debugSb);
      			rfreomannnumT1 = copyfinaltoRFR(eomannnumT1, rfreomannnumT1, true, debugSb);
      			rfrlastorderT1 = copyfinaltoRFR(lastorderT1, rfrlastorderT1, true, debugSb);
      			rfrendofserviceT1 = copyfinaltoRFR(endofserviceT1, rfrendofserviceT1, true, debugSb);
      			rfreosanndateT1 = copyfinaltoRFR(eosanndateT1, rfreosanndateT1, true, debugSb);
      			rfreosannnumT1 = copyfinaltoRFR(eosannnumT1, rfreosannnumT1, true, debugSb);
      			if (existfinalT1) {
      				ABRUtil.append(debugSb,"setallfileds: coutry is exist final T1:" + country + NEWLINE);
      				setAction(DELETE_ACTIVITY);
      				setrfrAction(DELETE_ACTIVITY);
      				setAllfieldsEmpty();

      			} else {
      				ABRUtil.append(debugSb,"setallfileds: coutry is not exist final T1:" + country + NEWLINE);
      				setAction(CHEAT);
      				setrfrAction(DELETE_ACTIVITY);
      				setAllfieldsEmpty();

      			}

      		} else if (UPDATE_ACTIVITY.equals(action)) {
      			ABRUtil.append(debugSb,"setallfileds: coutry is new:" + country + NEWLINE);

      			rfranndate = copyfinaltoRFR(anndate, rfranndate, false , debugSb);
      			rfrannnumber = copyfinaltoRFR(annnumber, rfrannnumber, false, debugSb);
      			rfrfirstorder = copyfinaltoRFR(firstorder, rfrfirstorder, false, debugSb);
      			rfrplannedavailability = copyfinaltoRFR(plannedavailability, rfrplannedavailability, false, debugSb);
      			rfrpubfrom = copyfinaltoRFR(pubfrom, rfrpubfrom, false, debugSb);
      			rfrpubto = copyfinaltoRFR(pubto, rfrpubto, false, debugSb);
      			rfrwdanndate = copyfinaltoRFR(wdanndate, rfrwdanndate, false, debugSb);
      			rfreomannnum = copyfinaltoRFR(eomannnum, rfreomannnum, false, debugSb);
      			rfrlastorder = copyfinaltoRFR(lastorder, rfrlastorder, false, debugSb);
      			rfrendofservice = copyfinaltoRFR(endofservice, rfrendofservice, false, debugSb);
      			rfreosanndate = copyfinaltoRFR(eosanndate, rfreosanndate, false, debugSb);
      			rfreosannnum = copyfinaltoRFR(eosannnum, rfreosannnum, false, debugSb);
      			if (existfinalT2) {
      				ABRUtil.append(debugSb,"setallfileds: coutry is  exist final T2:" + country + NEWLINE);
      				setAction(UPDATE_ACTIVITY);
      				setrfrAction(UPDATE_ACTIVITY);

      			} else {
      				ABRUtil.append(debugSb,"setallfileds: coutry is not exist final T2:" + country + NEWLINE);
      				setAction(CHEAT);
      				setrfrAction(UPDATE_ACTIVITY);

      			}

      		} else {
      			ABRUtil.append(debugSb,"setallfileds: coutry is both exist T1 and T2:" + country + NEWLINE);
      		
      			rfranndateT1 = copyfinaltoRFR(anndateT1, rfranndateT1, true, debugSb);
      			rfrannnumberT1= copyfinaltoRFR(annnumberT1, rfrannnumberT1, true, debugSb);
      			rfrfirstorderT1 = copyfinaltoRFR(firstorderT1, rfrfirstorderT1, true, debugSb);
      			rfrplannedavailabilityT1 = copyfinaltoRFR(plannedavailabilityT1, rfrplannedavailabilityT1, true, debugSb);
      			rfrpubfromT1 = copyfinaltoRFR(pubfromT1, rfrpubfromT1, true, debugSb);
      			rfrpubtoT1 = copyfinaltoRFR(pubtoT1, rfrpubtoT1, true, debugSb);
      			rfrwdanndateT1 = copyfinaltoRFR(wdanndateT1, rfrwdanndateT1, true, debugSb);
      			rfreomannnumT1 = copyfinaltoRFR(eomannnumT1, rfreomannnumT1, true, debugSb);
      			rfrlastorderT1 = copyfinaltoRFR(lastorderT1, rfrlastorderT1, true, debugSb);
      			rfrendofserviceT1 = copyfinaltoRFR(endofserviceT1, rfrendofserviceT1, true, debugSb);
      			rfreosanndateT1 = copyfinaltoRFR(eosanndateT1, rfreosanndateT1, true, debugSb);
      			rfreosannnumT1 = copyfinaltoRFR(eosannnumT1, rfreosannnumT1, true, debugSb);

      			rfranndate = copyfinaltoRFR(anndate, rfranndate, false , debugSb);
      			rfrannnumber = copyfinaltoRFR(annnumber, rfrannnumber, false, debugSb);
      			rfrfirstorder = copyfinaltoRFR(firstorder, rfrfirstorder, false, debugSb);
      			rfrplannedavailability = copyfinaltoRFR(plannedavailability, rfrplannedavailability, false, debugSb);
      			rfrpubfrom = copyfinaltoRFR(pubfrom, rfrpubfrom, false, debugSb);
      			rfrpubto = copyfinaltoRFR(pubto, rfrpubto, false, debugSb);
      			rfrwdanndate = copyfinaltoRFR(wdanndate, rfrwdanndate, false, debugSb);
      			rfreomannnum = copyfinaltoRFR(eomannnum, rfreomannnum, true, debugSb);
      			rfrlastorder = copyfinaltoRFR(lastorder, rfrlastorder, false, debugSb);
      			rfrendofservice = copyfinaltoRFR(endofservice, rfrendofservice, false, debugSb);
      			rfreosanndate = copyfinaltoRFR(eosanndate, rfreosanndate, false, debugSb);
      			rfreosannnum = copyfinaltoRFR(eosannnum, rfreosannnum, true, debugSb);
      			
      			if (existfinalT1 && !existfinalT2) {
      				ABRUtil.append(debugSb,"setallfileds: coutry  exist final T1 but T2:" + country + NEWLINE);
      				setAction(DELETE_ACTIVITY);
      				setfinalAllfieldsEmpty();

      			} else if (existfinalT2 && !existfinalT1) {
      				ABRUtil.append(debugSb,"setallfileds: coutry  exist final T2 but T1:" + country + NEWLINE);
      				setAction(UPDATE_ACTIVITY);
      				setrfrAction(UPDATE_ACTIVITY);

      			} else if (existfinalT2 && existfinalT1) {
      				ABRUtil.append(debugSb,"setallfileds: coutry  exist final T1 and T2:" + country + NEWLINE);
      				compareT1vT2(anndate, anndateT1, false);
      				compareT1vT2(annnumber, annnumberT1, false);
      				compareT1vT2(firstorder, firstorderT1, false);
      				compareT1vT2(plannedavailability, plannedavailabilityT1, false);
      				compareT1vT2(pubfrom, pubfromT1, false);
      				compareT1vT2(pubto, pubtoT1, false);
      				compareT1vT2(wdanndate, wdanndateT1, false);
      				compareT1vT2(eomannnum, eomannnumT1, false);
      				compareT1vT2(lastorder, lastorderT1, false);
      				compareT1vT2(endofservice, endofserviceT1, false);
      				compareT1vT2(eosanndate, eosanndateT1, false);
      				compareT1vT2(eosannnum, eosannnumT1, false);
      				ABRUtil.append(debugSb,"setallfileds: after compare action :" + action + NEWLINE);
      			} else {
      				//not existfinalT1 && not existfinalT2
      				ABRUtil.append(debugSb,"setallfileds: coutry  not exist final T1 and T2:" + country + NEWLINE);
      				setAction(CHEAT);
      			}
      			compareT1vT2(rfranndate, rfranndateT1, true);
      			compareT1vT2(rfrannnumber, rfrannnumberT1, true);
      			compareT1vT2(rfrfirstorder, rfrfirstorderT1, true);
      			compareT1vT2(rfrplannedavailability, rfrplannedavailabilityT1, true);
      			compareT1vT2(rfrpubfrom, rfrpubfromT1, true);
      			compareT1vT2(rfrpubto, rfrpubtoT1, true);
      			compareT1vT2(rfrwdanndate, rfrwdanndateT1, true);
      			compareT1vT2(rfreomannnum, rfreomannnumT1, true);
      			compareT1vT2(rfrlastorder, rfrlastorderT1, true);
      			compareT1vT2(rfrendofservice, rfrendofserviceT1, true);
      			compareT1vT2(rfreosanndate, rfreosanndateT1, true);
      			compareT1vT2(rfreosannnum, rfreosannnumT1, true);
      			ABRUtil.append(debugSb,"setallfileds: after compare rfr values action:" + rfraction + NEWLINE);
      		}
    		
            if (!isCompatmodel){				 
    			 if (isExistfinal){
    				 setrfrAction(CHEAT);
    			 }else{
    				 setAction(CHEAT);
    			 } 
    		}
            
      		return existfinalT2;
        }
    /**
     * 
     * Story 1865979 add eomannnum & eosannnum (MODELAVAILElem)
     * add eodannnum & eodanndate
     */
    boolean handleResults(String[] anndates, String[] anndatesT1, String[] annnumbers, String[] annnumbersT1,
			String[] firstorders, String[] firstordersT1, String[] plannedavailabilitys,
			String[] plannedavailabilitysT1, String[] pubfroms, String[] pubfromsT1, String[] pubtos,
			String[] pubtosT1, String[] wdanndates, String[] wdanndatesT1, String[] eomannnums, String[] eomannnumsT1,
			String[] lastorders, String[] lastordersT1, String[] endofservices, String[] endofservicesT1, 
			String[] eodanndates, String[] eodanndatesT1, String[] eodavaildates, String[] eodavaildatesT1, 
			String[] eosanndates, String[] eosanndatesT1, String[] eosannnums, String[] eosannnumsT1, String[] ordersysnames, 
			String[] ordersysnamesT1,String[] adocnumbers,String[] adocnumbersT1,String[] cdocnumbers,String[] cdocnumbersT1,String[] edocnumbers,String[] edocnumbersT1, String country, boolean isExistfinal, boolean isCompatmodel, StringBuffer debugSb) {
		// TODO Compare T1 and T2 set action and rfraction
		String anndateT1 = CHEAT;
		String rfranndateT1 = CHEAT;
		String annnumberT1 = CHEAT;
		String rfrannnumberT1 = CHEAT;
		String firstorderT1 = CHEAT;
		String rfrfirstorderT1 = CHEAT;
		String plannedavailabilityT1 = CHEAT;
		String rfrplannedavailabilityT1 = CHEAT;
		String pubfromT1 = CHEAT;
		String rfrpubfromT1 = CHEAT;
		String pubtoT1 = CHEAT;
		String rfrpubtoT1 = CHEAT;
		String wdanndateT1 = CHEAT;
		String rfrwdanndateT1 = CHEAT;
		String eomannnumT1 = CHEAT;
  		String rfreomannnumT1 = CHEAT;
		String lastorderT1 = CHEAT;
		String rfrlastorderT1 = CHEAT;
		String endofserviceT1 = CHEAT;
		String rfrendofserviceT1 = CHEAT;
		String eodanndateT1 = CHEAT;
		String rfreodanndateT1 = CHEAT;
		String eodavaildateT1 = CHEAT;
  		String rfreodavaildateT1 = CHEAT;
		String eosanndateT1 = CHEAT;
		String rfreosanndateT1 = CHEAT;
		String eosannnumT1 = CHEAT;
  		String rfreosannnumT1 = CHEAT;
		String ordersysnameT1 = CHEAT;
		String rfrordersysnameT1 = CHEAT;
		String edocnumberT1 = CHEAT;
		String adocnumberT1 = CHEAT;
		String cdocnumberT1 = CHEAT;
		String rfredocnumberT1 = CHEAT;
		String rfradocnumberT1 = CHEAT;
		String rfrcdocnumberT1 = CHEAT;

		anndate = anndates[0];
		rfranndate = anndates[1];
		anndateT1 = anndatesT1[0];
		rfranndateT1 = anndatesT1[1];
		annnumber = annnumbers[0];
		rfrannnumber = annnumbers[1];
		annnumberT1 = annnumbersT1[0];
		rfrannnumberT1 = annnumbersT1[1];
		firstorder = firstorders[0];
		rfrfirstorder = firstorders[1];
		firstorderT1 = firstordersT1[0];
		rfrfirstorderT1 = firstordersT1[1];
		plannedavailability = plannedavailabilitys[0];
		rfrplannedavailability = plannedavailabilitys[1];
		plannedavailabilityT1 = plannedavailabilitysT1[0];
		rfrplannedavailabilityT1 = plannedavailabilitysT1[1];
		pubfrom = pubfroms[0];
		rfrpubfrom = pubfroms[1];
		pubfromT1 = pubfromsT1[0];
		rfrpubfromT1 = pubfromsT1[1];
		pubto = pubtos[0];
		rfrpubto = pubtos[1];
		pubtoT1 = pubtosT1[0];
		rfrpubtoT1 = pubtosT1[1];
		wdanndate = wdanndates[0];
		rfrwdanndate = wdanndates[1];
		wdanndateT1 = wdanndatesT1[0];
		rfrwdanndateT1 = wdanndatesT1[1];
		eomannnum = eomannnums[0];
  		rfreomannnum = eomannnums[1];
  		eomannnumT1 = eomannnumsT1[0];
  		rfreomannnumT1 = eomannnumsT1[1];
		lastorder = lastorders[0];
		rfrlastorder = lastorders[1];
		lastorderT1 = lastordersT1[0];
		rfrlastorderT1 = lastordersT1[1];
		endofservice = endofservices[0];
		rfrendofservice = endofservices[1];
		endofserviceT1 = endofservicesT1[0];
		rfrendofserviceT1 = endofservicesT1[1];
		
		eodanndate = eodanndates[0];
		rfreodanndate = eodanndates[1];
		eodanndateT1 = eodanndates[0];
		rfreodanndateT1 = eodanndates[1];
		eodavaildate = eodavaildates[0];
		rfreodavaildate = eodavaildates[1];
		eodavaildateT1 = eodavaildatesT1[0];
		rfreodavaildateT1 = eodavaildatesT1[1];
		
		eosanndate = eosanndates[0];
		rfreosanndate = eosanndates[1];
		eosanndateT1 = eosanndatesT1[0];
		rfreosanndateT1 = eosanndatesT1[1];
		eosannnum = eosannnums[0];
  		rfreosannnum = eosannnums[1];
  		eosannnumT1 = eosannnumsT1[0];
  		rfreosannnumT1 = eosannnumsT1[1];
		ordersysname = ordersysnames[0];
		rfrordersysname = ordersysnames[1];
		ordersysnameT1 = ordersysnamesT1[0];
		rfrordersysnameT1 = ordersysnamesT1[1];
		EDOCNO=edocnumbers[0];
		rfrEDOCNO=edocnumbers[1];
		edocnumberT1 = edocnumbersT1[0];
		rfredocnumberT1 = edocnumbersT1[0];
		ADOCNO = adocnumbers[0];
		rfrADOCNO=adocnumbers[1];
		adocnumberT1 = adocnumbersT1[0];
		CDOCNO=cdocnumbers[0];
		rfrCDOCNO=cdocnumbers[1];
		cdocnumberT1=cdocnumbersT1[0];
		rfradocnumberT1=adocnumbersT1[1];
		rfrcdocnumberT1=cdocnumbersT1[1];
		rfredocnumberT1=edocnumbersT1[1];
		if (DELETE_ACTIVITY.equals(action)) {
			ABRUtil.append(debugSb, "setallfileds: coutry is delete:" + country);

			rfranndateT1 = copyfinaltoRFR(anndateT1, rfranndateT1, true, debugSb);
			rfrannnumberT1 = copyfinaltoRFR(annnumberT1, rfrannnumberT1, true, debugSb);
			rfrfirstorderT1 = copyfinaltoRFR(firstorderT1, rfrfirstorderT1, true, debugSb);
			rfrplannedavailabilityT1 = copyfinaltoRFR(plannedavailabilityT1, rfrplannedavailabilityT1, true,
					debugSb);
			rfrpubfromT1 = copyfinaltoRFR(pubfromT1, rfrpubfromT1, true, debugSb);
			rfrpubtoT1 = copyfinaltoRFR(pubtoT1, rfrpubtoT1, true, debugSb);
			rfrwdanndateT1 = copyfinaltoRFR(wdanndateT1, rfrwdanndateT1, true, debugSb);
			
			rfreodanndateT1 = copyfinaltoRFR(eodanndateT1, rfreodanndateT1, true, debugSb);
			rfreodavaildateT1 = copyfinaltoRFR(eodavaildateT1, rfreodavaildateT1, true, debugSb);
			
			//Story 1865979 Withdrawal RFA Number generation
  			rfreomannnumT1 = copyfinaltoRFR(eomannnumT1, rfreomannnumT1, true, debugSb);	  			
			rfrlastorderT1 = copyfinaltoRFR(lastorderT1, rfrlastorderT1, true, debugSb);
			rfrendofserviceT1 = copyfinaltoRFR(endofserviceT1, rfrendofserviceT1, true, debugSb);
			rfreosanndateT1 = copyfinaltoRFR(eosanndateT1, rfreosanndateT1, true, debugSb);
			//Story 1865979 Withdrawal RFA Number generation
  			rfreosannnumT1 = copyfinaltoRFR(eosannnumT1, rfreosannnumT1, true, debugSb);
  			rfrordersysnameT1 = copyfinaltoRFR(ordersysnameT1, rfrordersysnameT1, true, debugSb);
			rfredocnumberT1=copyfinaltoRFR(edocnumberT1,rfredocnumberT1,true,debugSb);
			rfradocnumberT1=copyfinaltoRFR(adocnumberT1,rfradocnumberT1,true,debugSb);
			rfrcdocnumberT1=copyfinaltoRFR(cdocnumberT1,rfrcdocnumberT1,true,debugSb);
			if (existfinalT1) {
				ABRUtil.append(debugSb, "setallfileds: coutry is exist final T1:" + country + NEWLINE);
				setAction(DELETE_ACTIVITY);
				setrfrAction(DELETE_ACTIVITY);
				setAllfieldsEmpty();

			} else {
				ABRUtil.append(debugSb, "setallfileds: coutry is not exist final T1:" + country + NEWLINE);
				setAction(CHEAT);
				setrfrAction(DELETE_ACTIVITY);
				setAllfieldsEmpty();

			}

		} else if (UPDATE_ACTIVITY.equals(action)) {
			ABRUtil.append(debugSb, "setallfileds: coutry is new:" + country + NEWLINE);

			rfranndate = copyfinaltoRFR(anndate, rfranndate, false, debugSb);
			rfrannnumber = copyfinaltoRFR(annnumber, rfrannnumber, false, debugSb);
			rfrfirstorder = copyfinaltoRFR(firstorder, rfrfirstorder, false, debugSb);
			rfrplannedavailability = copyfinaltoRFR(plannedavailability, rfrplannedavailability, false, debugSb);
			rfrpubfrom = copyfinaltoRFR(pubfrom, rfrpubfrom, false, debugSb);
			rfrpubto = copyfinaltoRFR(pubto, rfrpubto, false, debugSb);
			rfrwdanndate = copyfinaltoRFR(wdanndate, rfrwdanndate, false, debugSb);
			
			rfreodanndate = copyfinaltoRFR(eodanndate, rfreodanndate, false, debugSb);
			rfreodavaildate = copyfinaltoRFR(eodavaildate, rfreodavaildate, false, debugSb);
			//Story 1865979 Withdrawal RFA Number generation
  			rfreomannnum = copyfinaltoRFR(eomannnum, rfreomannnum, false, debugSb);
  			rfrlastorder = copyfinaltoRFR(lastorder, rfrlastorder, false, debugSb);
			rfrendofservice = copyfinaltoRFR(endofservice, rfrendofservice, false, debugSb);
			rfreosanndate = copyfinaltoRFR(eosanndate, rfreosanndate, false, debugSb);
			//Story 1865979 Withdrawal RFA Number generation
  			rfreosannnum = copyfinaltoRFR(eosannnum, rfreosannnum, false, debugSb);
  			rfrordersysname = copyfinaltoRFR(ordersysname, rfrordersysname, false, debugSb);

			rfrADOCNO = copyfinaltoRFR(ADOCNO, rfrADOCNO, false, debugSb);
			rfrEDOCNO = copyfinaltoRFR(EDOCNO, rfrEDOCNO, false, debugSb);
			rfrCDOCNO = copyfinaltoRFR(CDOCNO, rfrCDOCNO, false, debugSb);

			if (existfinalT2) {
				ABRUtil.append(debugSb, "setallfileds: coutry is  exist final T2:" + country + NEWLINE);
				setAction(UPDATE_ACTIVITY);
				setrfrAction(UPDATE_ACTIVITY);

			} else {
				ABRUtil.append(debugSb, "setallfileds: coutry is not exist final T2:" + country + NEWLINE);
				setAction(CHEAT);
				setrfrAction(UPDATE_ACTIVITY);

			}

		} else {
			ABRUtil.append(debugSb, "setallfileds: coutry is both exist T1 and T2:" + country + NEWLINE);

			rfranndateT1 = copyfinaltoRFR(anndateT1, rfranndateT1, true, debugSb);
			rfrannnumberT1 = copyfinaltoRFR(annnumberT1, rfrannnumberT1, true, debugSb);
			rfrfirstorderT1 = copyfinaltoRFR(firstorderT1, rfrfirstorderT1, true, debugSb);
			rfrplannedavailabilityT1 = copyfinaltoRFR(plannedavailabilityT1, rfrplannedavailabilityT1, true,
					debugSb);
			rfrpubfromT1 = copyfinaltoRFR(pubfromT1, rfrpubfromT1, true, debugSb);
			rfrpubtoT1 = copyfinaltoRFR(pubtoT1, rfrpubtoT1, true, debugSb);
			rfrwdanndateT1 = copyfinaltoRFR(wdanndateT1, rfrwdanndateT1, true, debugSb);
			
			rfreodanndateT1 = copyfinaltoRFR(eodanndateT1, rfreodanndateT1, true, debugSb);
			rfreodavaildateT1 = copyfinaltoRFR(eodavaildateT1, rfreodavaildateT1, true, debugSb);
			
			//Story 1865979 Withdrawal RFA Number generation
  			rfreomannnumT1 = copyfinaltoRFR(eomannnumT1, rfreomannnumT1, true, debugSb);
  			rfrlastorderT1 = copyfinaltoRFR(lastorderT1, rfrlastorderT1, true, debugSb);
			rfrendofserviceT1 = copyfinaltoRFR(endofserviceT1, rfrendofserviceT1, true, debugSb);
			rfreosanndateT1 = copyfinaltoRFR(eosanndateT1, rfreosanndateT1, true, debugSb);
			//Story 1865979 Withdrawal RFA Number generation
  			rfreosannnumT1 = copyfinaltoRFR(eosannnumT1, rfreosannnumT1, true, debugSb);
  			rfrordersysnameT1 = copyfinaltoRFR(ordersysnameT1, rfrordersysnameT1, true, debugSb);
			rfredocnumberT1=copyfinaltoRFR(edocnumberT1,rfredocnumberT1,true,debugSb);
			rfradocnumberT1=copyfinaltoRFR(adocnumberT1,rfradocnumberT1,true,debugSb);
			rfrcdocnumberT1=copyfinaltoRFR(cdocnumberT1,rfrcdocnumberT1,true,debugSb);


			rfranndate = copyfinaltoRFR(anndate, rfranndate, false, debugSb);
			rfrannnumber = copyfinaltoRFR(annnumber, rfrannnumber, false, debugSb);
			rfrfirstorder = copyfinaltoRFR(firstorder, rfrfirstorder, false, debugSb);
			rfrplannedavailability = copyfinaltoRFR(plannedavailability, rfrplannedavailability, false, debugSb);
			rfrpubfrom = copyfinaltoRFR(pubfrom, rfrpubfrom, false, debugSb);
			rfrpubto = copyfinaltoRFR(pubto, rfrpubto, false, debugSb);
			rfrwdanndate = copyfinaltoRFR(wdanndate, rfrwdanndate, false, debugSb);
			
			rfreodanndate = copyfinaltoRFR(eodanndate, rfreodanndate, false, debugSb);
			rfreodavaildate = copyfinaltoRFR(eodavaildate, rfreodavaildate, false, debugSb);			
			//Story 1865979 Withdrawal RFA Number generation
  			rfreomannnum = copyfinaltoRFR(eomannnum, rfreomannnum, false, debugSb);
  			rfrlastorder = copyfinaltoRFR(lastorder, rfrlastorder, false, debugSb);
			rfrendofservice = copyfinaltoRFR(endofservice, rfrendofservice, false, debugSb);
			rfreosanndate = copyfinaltoRFR(eosanndate, rfreosanndate, false, debugSb);
			//Story 1865979 Withdrawal RFA Number generation
  			rfreosannnum = copyfinaltoRFR(eosannnum, rfreosannnum, false, debugSb);
  			rfrordersysname = copyfinaltoRFR(ordersysname, rfrordersysname, false, debugSb);
			rfrADOCNO = copyfinaltoRFR(ADOCNO, rfrADOCNO, false, debugSb);
			rfrEDOCNO = copyfinaltoRFR(EDOCNO, rfrEDOCNO, false, debugSb);
			rfrCDOCNO = copyfinaltoRFR(CDOCNO, rfrCDOCNO, false, debugSb);

			if (existfinalT1 && !existfinalT2) {
				ABRUtil.append(debugSb, "setallfileds: coutry  exist final T1 but T2:" + country + NEWLINE);
				setAction(DELETE_ACTIVITY);
				setfinalAllfieldsEmpty();

			} else if (existfinalT2 && !existfinalT1) {
				ABRUtil.append(debugSb, "setallfileds: coutry  exist final T2 but T1:" + country + NEWLINE);
				setAction(UPDATE_ACTIVITY);
				setrfrAction(UPDATE_ACTIVITY);

			} else if (existfinalT2 && existfinalT1) {
				ABRUtil.append(debugSb, "setallfileds: coutry  exist final T1 and T2:" + country + NEWLINE);
				compareT1vT2(anndate, anndateT1, false);
				compareT1vT2(annnumber, annnumberT1, false);
				compareT1vT2(firstorder, firstorderT1, false);
				compareT1vT2(plannedavailability, plannedavailabilityT1, false);
				compareT1vT2(pubfrom, pubfromT1, false);
				compareT1vT2(pubto, pubtoT1, false);
				compareT1vT2(wdanndate, wdanndateT1, false);
				
				compareT1vT2(eodanndate, eodanndateT1, false);
				compareT1vT2(eodavaildate, eodavaildateT1, false);
				//Story 1865979 Withdrawal RFA Number generation
  				compareT1vT2(eomannnum, eomannnumT1, false);
  				compareT1vT2(lastorder, lastorderT1, false);
				compareT1vT2(endofservice, endofserviceT1, false);
				compareT1vT2(eosanndate, eosanndateT1, false);
				//Story 1865979 Withdrawal RFA Number generation
  				compareT1vT2(eosannnum, eosannnumT1, false);
  				compareT1vT2(ordersysname, ordersysnameT1, false);

				compareT1vT2(ADOCNO, adocnumberT1, false);
				compareT1vT2(EDOCNO, edocnumberT1, false);
				compareT1vT2(CDOCNO, cdocnumberT1, false);

				
				ABRUtil.append(debugSb, "setallfileds: after compare action :" + action + NEWLINE);
			} else {
				// not existfinalT1 && not existfinalT2
				ABRUtil.append(debugSb, "setallfileds: coutry  not exist final T1 and T2:" + country + NEWLINE);
				setAction(CHEAT);
			}
			compareT1vT2(rfranndate, rfranndateT1, true);
			compareT1vT2(rfrannnumber, rfrannnumberT1, true);
			compareT1vT2(rfrfirstorder, rfrfirstorderT1, true);
			compareT1vT2(rfrplannedavailability, rfrplannedavailabilityT1, true);
			compareT1vT2(rfrpubfrom, rfrpubfromT1, true);
			compareT1vT2(rfrpubto, rfrpubtoT1, true);
			compareT1vT2(rfrwdanndate, rfrwdanndateT1, true);
			
			compareT1vT2(rfreodanndate, rfreodanndateT1, false);
			compareT1vT2(rfreodavaildate, rfreodavaildateT1, false);
			//Story 1865979 Withdrawal RFA Number generation
			compareT1vT2(rfreomannnum, rfreomannnumT1, false);
  			compareT1vT2(rfrlastorder, rfrlastorderT1, true);
			compareT1vT2(rfrendofservice, rfrendofserviceT1, true);
			compareT1vT2(rfreosanndate, rfreosanndateT1, true);
			//Story 1865979 Withdrawal RFA Number generation
  			compareT1vT2(rfreosannnum, rfreosannnumT1, false);
  			compareT1vT2(rfrordersysname, rfrordersysnameT1, true);
			compareT1vT2(rfrADOCNO, rfradocnumberT1, true);
			compareT1vT2(rfrEDOCNO, rfredocnumberT1, true);
			compareT1vT2(rfrCDOCNO, rfrcdocnumberT1, true);


			ABRUtil.append(debugSb, "setallfileds: after compare rfr values action:" + rfraction + NEWLINE);
		}

		if (!isCompatmodel) {
			if (isExistfinal) {
				setrfrAction(CHEAT);
			} else {
				setAction(CHEAT);
			}
		}

		return existfinalT2;
	}
    
	String copyfinaltoRFR(String fromfinal, String torfr, boolean t1, StringBuffer debugSb) {
		if (!CHEAT.equals(fromfinal)) {
			ABRUtil.append(debugSb, "Exist final data:" + fromfinal + " copy final to rfr.");
			if (t1) {
				existfinalT1 = true;
			} else {
				existfinalT2 = true;
			}
			return fromfinal;
		} else {
			return torfr;
		}
	}

	void compareT1vT2(String T2value, String T1value, boolean rfr) {
		if (rfr) {
			if (!isrfrDisplayable()) {
				if (!T2value.equals(T1value)) {
					setrfrAction(UPDATE_ACTIVITY);
				}
			}
		} else {
			if (!isDisplayable()) {
				if (!T2value.equals(T1value)) {
					setAction(UPDATE_ACTIVITY);
				}
			}
		}
	}

	void setAllfieldsEmpty() {
		rfrpubfrom = CHEAT;
		rfrpubto = CHEAT;
		rfrendofservice = CHEAT;
		rfranndate = CHEAT;
		rfrfirstorder = CHEAT;
		rfrplannedavailability = CHEAT;
		rfrwdanndate = CHEAT;
		rfreomannnum = CHEAT;
		rfrlastorder = CHEAT;
		rfreosanndate = CHEAT;
		rfreosannnum = CHEAT;
		rfrannnumber = CHEAT;	
		
		pubfrom = CHEAT;
		pubto = CHEAT;
		endofservice = CHEAT;
		anndate = CHEAT;
		firstorder = CHEAT;
		plannedavailability = CHEAT;
		wdanndate = CHEAT;
		eomannnum = CHEAT;
		lastorder = CHEAT;
		eosanndate = CHEAT;
		eosannnum = CHEAT;
		annnumber = CHEAT;
		
		ordersysname = CHEAT;
		rfrordersysname = CHEAT;
	}

	void setfinalAllfieldsEmpty() {
		// rfrpubfrom = CHEAT;
		// rfrpubto = CHEAT;
		// rfrendofservice = CHEAT;
		// rfranndate = CHEAT;
		// rfrfirstorder = CHEAT;
		// rfrplannedavailability = CHEAT;
		// rfrwdanndate = CHEAT;
		// rfrlastorder = CHEAT;
		// rfreosanndate = CHEAT;
		// rfrannnumber = CHEAT;
		pubfrom = CHEAT;
		pubto = CHEAT;
		endofservice = CHEAT;
		anndate = CHEAT;
		firstorder = CHEAT;
		plannedavailability = CHEAT;
		wdanndate = CHEAT;
		eomannnum = CHEAT;
		lastorder = CHEAT;
		eosanndate = CHEAT;
		eosannnum = CHEAT;
		annnumber = CHEAT;
		ordersysname = CHEAT;
		CDOCNO=CHEAT;
		EDOCNO=CHEAT;
		ADOCNO=CHEAT;
	}

	void dereference() {
		// availDiff = null;
		action = null;
		// country = null;
		availStatus = null;
		pubfrom = null;
		pubto = null;
		endofservice = null;
		anndate = null;
		firstorder = null;
		plannedavailability = null;
		wdanndate = null;
		eomannnum = null;
		lastorder = null;
		eosanndate = null;
		eosannnum = null;
		annnumber = null;
		
		rfrpubfrom = null;
		rfrpubto = null;
		rfrendofservice = null;
		rfranndate = null;
		rfrfirstorder = null;
		rfrplannedavailability = null;
		rfrwdanndate = null;
		rfreomannnum = null;
		rfrlastorder = null;
		rfreosanndate = null;
		rfreosannnum = null;
		rfrannnumber = null;
			
		ordersysname = null;
		rfrordersysname = null;
		CDOCNO=null;
		EDOCNO=null;
		ADOCNO=null;
	}

}
