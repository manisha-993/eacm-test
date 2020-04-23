//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2011  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.darule;


import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.Database;

import COM.ibm.opicmpdh.middleware.Profile;

/*********************************
 *DARULETYPE  40  Equation
 *4.	Equation
 *This is not defined nor implemented yet.
 *
 */
// $Log: DARuleEquation.java,v $
// Revision 1.1  2011/03/15 21:12:10  wendy
// Init for BH FS ABR Catalog Attr Derivation 20110221b.doc
//
public class DARuleEquation extends DARuleItem {
	private static final long serialVersionUID = 1L;
	
	/**
	 * constructor
	 * @param ei
	 */
	protected DARuleEquation(EntityItem ei){
		super(ei);
	}
	
	/* (non-Javadoc)
	 *  used for offering DQ and Workflow 
	 * @see COM.ibm.eannounce.darule.DARuleItem#getDerivedValue(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, java.lang.String, java.lang.StringBuffer)
	 */
	protected String getDerivedValue(Database db, Profile prof, EntityItem rootItem,String interimValue, StringBuffer debugSb) 
	throws Exception{
		String results=interimValue;
		//The root entity type PDHDOMAIN is in DARULE.PDHDOMAIN
		if(isApplicable(rootItem, debugSb)){
			// process this rule
			//results=
		}
		return results;
	}
	/* (non-Javadoc)
	 * used for IDL to improve performance
	 * @see COM.ibm.eannounce.darule.DARuleItem#getDerivedValues(COM.ibm.opicmpdh.middleware.Database, COM.ibm.opicmpdh.middleware.Profile, COM.ibm.eannounce.objects.EntityItem[], java.lang.String[], java.lang.StringBuffer)
	 */
	protected String[] getDerivedValues(Database db, Profile prof, EntityItem[] rootItemArray, String[] interimValues, 
			StringBuffer debugSb) 
	throws Exception{
		String results[] = new String[rootItemArray.length];
		for(int i=0; i<rootItemArray.length; i++){
			EntityItem rootItem = rootItemArray[i];
			//The root entity type PDHDOMAIN is in DARULE.PDHDOMAIN
			if(isApplicable(rootItem, debugSb)){
				// process this rule
				//results[i]=
			}else{
				if(interimValues!=null){
					results[i]=interimValues[i];
				}else{
					results[i]=null;
				}
			}
		}
		return results;
	}
	
    /***********************************************
     *  Get the version
     *
     *@return java.lang.String
     */
     public static String getVersion()
     {
     	return "$Revision: 1.1 $";
     }
}
