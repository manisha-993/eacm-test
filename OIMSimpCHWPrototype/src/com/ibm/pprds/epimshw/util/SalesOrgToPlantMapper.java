package com.ibm.pprds.epimshw.util;

import java.util.HashMap;
import java.util.Map;

public class SalesOrgToPlantMapper {
	
	private static Map salesOrgToPlantMap = new HashMap();
	private static final String PLANT_1999 = "1999";
	private static final String PLANT_1222 = "1222";
	
	private static final String SO_MAXICO = "0391";
	private static final String SO_CANADA = "0026";
	private static final String SO_US = "0147";
	private static final String SO_ARG = "0007";
	private static final String SO_BOL = "0019";
	private static final String SO_BRAZ = "0022";
	private static final String SO_CHILE = "0029";
	private static final String SO_COL = "0032";
	private static final String SO_EQU = "0039";
	//private static final String SO_PAR1 = "0090";
	private static final String SO_PAR2 = "0106";
	private static final String SO_PERU = "0107";
	private static final String SO_VEN = "0148";
	private static final String SO_URU = "0146"; 
	private static final String GEO_US = "US";
	private static final String GEO_CCN = "CCN";
	private static final String GEO_LA = "LA"; 
	
	private static final String[] LA_PLANTS = { PLANT_1222, PLANT_1999}; 
	private static final String[] US_PLANTS = { PLANT_1222}; 
	
	
	
	static{
		salesOrgToPlantMap.put(SO_MAXICO, LA_PLANTS);
		salesOrgToPlantMap.put(SO_CANADA, US_PLANTS);
		salesOrgToPlantMap.put(SO_US, US_PLANTS);
		salesOrgToPlantMap.put(SO_ARG, LA_PLANTS);
		salesOrgToPlantMap.put(SO_BOL, US_PLANTS);
		salesOrgToPlantMap.put(SO_BRAZ, US_PLANTS);
		salesOrgToPlantMap.put(SO_CHILE, LA_PLANTS);
		salesOrgToPlantMap.put(SO_COL, LA_PLANTS);
		salesOrgToPlantMap.put(SO_EQU, LA_PLANTS);
		salesOrgToPlantMap.put(SO_PAR2, US_PLANTS);
		//salesOrgToPlantMap.put(SO_PAR1, LA_PLANTS);
		salesOrgToPlantMap.put(SO_PERU, LA_PLANTS);
		salesOrgToPlantMap.put(SO_VEN, LA_PLANTS);
		salesOrgToPlantMap.put(SO_URU, LA_PLANTS);
		salesOrgToPlantMap.put(GEO_LA, LA_PLANTS);
		salesOrgToPlantMap.put(GEO_US, US_PLANTS);
		salesOrgToPlantMap.put(GEO_CCN, US_PLANTS);
	}
	
	public static String[] getPlantBySalesOrg(String salesOrg){
		Object plant = salesOrgToPlantMap.get(salesOrg);
		return plant != null ? (String[])plant : new String[0];
	}

}
