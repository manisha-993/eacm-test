package com.ibm.pprds.epimshw.util;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Vector;


import com.ibm.pprds.epimshw.HWPIMSAbnormalException;
import com.ibm.pprds.epimshw.PropertyKeys;
import java.sql.SQLException;
import com.ibm.pprds.epimshw.HWPIMSLog;
import com.ibm.pprds.epimshw.ExceptionUtility;

import java.util.Enumeration;
import java.util.StringTokenizer;
/*
 * Created on May 9, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author laxmi
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ProfitCenterPlantSelector {
	static ProfitCenterPlantSelector instance;
	/**
	 * Vector of profit center plants
	 * 	 */
	Vector profitCenterPlants;
	
	public static synchronized ProfitCenterPlantSelector instanceOf() throws HWPIMSAbnormalException {
			
			if (instance == null) {
				instance = new ProfitCenterPlantSelector();
 			}

			return instance;
		}
		
	public static boolean checkProfitCenterPlants(String sapPlt) throws HWPIMSAbnormalException {
			return ProfitCenterPlantSelector.instanceOf().isProfitCenterPlant(sapPlt);
	}
	public static Vector getProfitCenterPlants() throws HWPIMSAbnormalException {
				return ProfitCenterPlantSelector.instanceOf().getProfitCenterPlants_internal();
	}
	
	public ProfitCenterPlantSelector() throws HWPIMSAbnormalException
		{
			super();
			profitCenterPlants = new Vector();
			retrieveData();
		}	
	private void retrieveData() throws HWPIMSAbnormalException {
		
		String profitCenterPlant = ConfigManager.getConfigManager().getString(PropertyKeys.KEY_PROFIT_CENTER_PLANT, true);
		StringTokenizer stoken = new StringTokenizer(profitCenterPlant,",");
		while(stoken.hasMoreElements()) {
			String pPlant = stoken.nextToken();
			profitCenterPlants.addElement(pPlant);
		}	
		//return profitCenterPlants;
	}
	/**
	 * @return Returns the profit Center Plants.
	 */
	private Vector getProfitCenterPlants_internal() {
		//Enumeration e;
		//FlFilPipeSalesOrg gso;
		Vector salesOrgV = new Vector();
		
		return profitCenterPlants;
		
	}
	private boolean isProfitCenterPlant(String plt) throws HWPIMSAbnormalException{
	
	 Enumeration e;
	 String strPlant;
	 boolean ans = false;
	 Vector plantsV = new Vector();
	  
	 e = profitCenterPlants.elements();
	 while (e.hasMoreElements()) {
		strPlant = (String) e.nextElement();
	  if (plt.equalsIgnoreCase(strPlant))  {
	   ans = true;
	   break;
	  }
	 }
	 return ans;

   }	

}
