//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2011  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.darule;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.MetaFlag;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;

import COM.ibm.opicmpdh.middleware.Profile;

/*********************************
 *  
 * One for each DA rule
 * 
 
DARULE  Entity  Attribute Derivation Rule

DARULE  ATTRDERIVEABRSTATUS A   Attribute Derivation ABR Status
DARULE  COMMENTS    L   Comments
DARULE  DAATTRIBUTECODE U   Attribute
DARULE  DAENTITYTYPE    U   Entity Type
DARULE  DALIFECYCLE S   Life Cycle
DARULE  DARULETYPE  U   Rule Type
DARULE  PDHDOMAIN   F   Domains
DARULE  RULEFAIL    L   Fail Results
DARULE  RULEMULTIPLE    T   Rule Concatenation String
DARULE  RULEPASS    L   Pass Results
DARULE  RULESEQ T   Sequence
DARULE  RULETEST    T   Test
 *
 *
DARULETYPE  10  As Is
DARULETYPE  20  Substitution
DARULETYPE  30  ScanReplace
DARULETYPE  40  Equation
 *
 */
// $Log: DARuleItem.java,v $
// Revision 1.2  2011/04/07 18:39:49  wendy
// output all attrs in tostring
//
// Revision 1.1  2011/03/15 21:12:11  wendy
// Init for BH FS ABR Catalog Attr Derivation 20110221b.doc
//
public abstract class DARuleItem implements Comparable, Serializable {
	private EntityItem daRuleItem = null;
	private Set validDomainSet = new HashSet();
    public static final String VERSION = "$Revision: 1.2 $";
	
	/**
	 * constructor
	 * @param ei
	 */
	protected DARuleItem(EntityItem ei){
		daRuleItem = ei;

		// Get the selected Flag codes.
		EANAttribute att = (EANFlagAttribute)daRuleItem.getAttribute("PDHDOMAIN");
		MetaFlag[] mfArray = (MetaFlag[]) att.get();
		for (int im = 0; im < mfArray.length; im++) {
			// get selection
			if (mfArray[im].isSelected()&& !validDomainSet.contains(mfArray[im].getFlagCode())) {
				validDomainSet.add(mfArray[im].getFlagCode());
			}
		} //end for
	}
	
	/**
	 * get entityitem key
	 * @return
	 */
	protected String getKey(){
		return daRuleItem.getKey();
	}
	/**
	 * get the DARULE EntityItem
	 * @return
	 */
	protected EntityItem getDARULEEntity() { return daRuleItem;}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		// order by rule sequence
		String ruleSeq = PokUtils.getAttributeValue(daRuleItem, "RULESEQ", "", "", false);
		String otherRuleSeq = PokUtils.getAttributeValue(((DARuleItem)o).daRuleItem, "RULESEQ", "", "", false);
		return ruleSeq.compareTo(otherRuleSeq);
	}
	
	/**
	 * derive the value for this rule
	 * used for offering DQ and Workflow 
	 * @param db
	 * @param prof
	 * @param interimValue - any derived value to this point
	 * @param debugSb
	 * @return
	 * @throws Exception
	 */
	protected abstract String getDerivedValue(Database db, Profile prof, EntityItem rootItem, String interimValue, 
			StringBuffer debugSb) 
	throws Exception;
	
	/**
	 * used for IDL to improve performance- any extract action can be performed on the set of applicable roots at one time
	 * @param db
	 * @param prof
	 * @param rootItemArray
	 * @param interimValues
	 * @param debugSb
	 * @return
	 * @throws Exception
	 */
	protected abstract String[] getDerivedValues(Database db, Profile prof, EntityItem[] rootItemArray, String[] interimValues, 
			StringBuffer debugSb) 
	throws Exception;

	/**
	 * apply this rule if:
	 * 	The root entity type PDHDOMAIN is in DARULE.PDHDOMAIN
	 * @param rootItem
	 * @param debugSb
	 * @return
	 */
	protected boolean isApplicable(EntityItem rootItem, StringBuffer debugSb){
		boolean useit = false;
		if(PokUtils.contains(rootItem, "PDHDOMAIN", validDomainSet)){
			useit = true;
		}else{
			DARuleEngineMgr.addDebugComment(D.EBUG_INFO,debugSb,"DARULE PDHDOMAIN: "+validDomainSet+" did not have "+
					rootItem.getKey()+" pdhdomain: "+PokUtils.getAttributeFlagValue(rootItem, "PDHDOMAIN"));
		}
		return useit;
	}
	
	/**
	 * release memory
	 */
	protected void dereference(){
		daRuleItem = null;
		validDomainSet.clear();
		validDomainSet = null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer(daRuleItem.getKey()+" DOMAIN: "+validDomainSet);
		for (int i=0; i<daRuleItem.getAttributeCount(); i++){
			EANAttribute attr = daRuleItem.getAttribute(i);
			sb.append(" "+attr.getAttributeCode()+": "+
					PokUtils.getAttributeValue(daRuleItem, attr.getAttributeCode(), ",", "", false));
		}

		return sb.toString();
	}

}
