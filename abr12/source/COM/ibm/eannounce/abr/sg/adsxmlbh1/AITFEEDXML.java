// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Vector;

import javax.xml.transform.TransformerConfigurationException;

import org.xml.sax.SAXException;

import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.MetaFlag;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;

import com.ibm.transform.oim.eacm.util.PokUtils;

// $Log: AITFEEDXML.java,v $
// Revision 1.6  2017/09/06 12:27:26  wangyul
// Story 1749137 - AIT HWW - Autogen - logic change to derive FCTRANSACTIONs for AIT
//
// Revision 1.5  2017/08/07 06:48:52  wangyul
// [Work Item 1726324] AIT HWW - Autogen files issue - Add new install filed in AIT Model XML
//
// Revision 1.3  2017/06/15 14:06:18  wangyul
// Defect 1709867 - AIT HWW - Autogen files issue
//
// Revision 1.2  2017/05/24 07:57:54  wangyul
// story 1703408 - HWW - Autogen files - AIT XML issue fix
//
// Revision 1.1  2015/08/05 09:27:43  wangyul
// EACM to AIT feed
//
public abstract class AITFEEDXML {
	protected static final String ANNTYPE_NEW = "19"; // New
	protected static final String ANNTYPE_EOL = "14"; // End Of Life - Withdrawal from mktg
	protected static final int FLAGVAL = 0;		// flag code
	protected static final int SHORTDESC = 1;	// short description
	
	protected static final Vector AVAILTYPE_FILTER;
	static {
		AVAILTYPE_FILTER = new Vector(2);
		AVAILTYPE_FILTER.add("146");	// Planned Availability
		AVAILTYPE_FILTER.add("149");	// Last Order
	}
	
	protected static final Vector INVALID_COUNTRYCODE;
	static {
		INVALID_COUNTRYCODE = new Vector(2);
		INVALID_COUNTRYCODE.add("1667");	// China - offshore
		INVALID_COUNTRYCODE.add("2001");	// N/A
	}
	
	public abstract void processThis(AITFEEDABRSTATUS abr, Profile profileT2, EntityItem rootEntity) throws FileNotFoundException, TransformerConfigurationException, SAXException, MiddlewareRequestException, SQLException, MiddlewareException;
	
	public abstract String getVeName();
	
	public abstract String getVersion();
	
	public abstract String getXMLFileName();
	
	protected void createCountryListElement(AITFEEDSimpleSaxXML xml, EntityItem availItem, AITFEEDABRSTATUS abr) throws SAXException {
		xml.startElement("COUNTRYLIST");
		Vector countryList = getMultiFlagAttributeValues(availItem, "COUNTRYLIST", AITFEEDXML.FLAGVAL);
		if(countryList != null && countryList.size() > 0) {
			for(int j = 0; j < countryList.size(); j++) {
				String countryCode = (String)countryList.get(j);
				if (INVALID_COUNTRYCODE.contains(countryCode)) {
					abr.addDebug("Ignore invalid country code:" + countryCode + " for " + availItem.getKey());
					continue;
				} else {
					String countryGenareName = abr.getGenareaName(countryCode);
					if (countryGenareName == null) {
						abr.addDebug("Can't get GenareName for country code:" + (String)countryList.get(j) + " for AVAIL:" + availItem.getKey());
					}
					xml.addElement("COUNTRY_FC", countryGenareName != null ? countryGenareName : "");
				}
			}
			countryList.clear();
			countryList = null;
		}
		xml.endElement("COUNTRYLIST");
	}
	
	protected Vector getMultiFlagAttributeValues(EntityItem item, String attrCode, int attrSrc) {
		Vector selectedVct = null;
		EANMetaAttribute metaAttr = item.getEntityGroup().getMetaAttribute(attrCode);
		if (metaAttr.getAttributeType().equals("F")) { // MultiFlagAttribute
			// get attr, it is F
			EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute(attrCode);
			if (fAtt != null && fAtt.toString().length() > 0) {
				selectedVct = new Vector(1);
				// Get the selected Flag codes.
				MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
				for (int i = 0; i < mfArray.length; i++) {
					// get selection
					if (mfArray[i].isSelected()) {
						// may need to get flagcode instead of flag value here
						if (attrSrc == FLAGVAL) {
							selectedVct.addElement(mfArray[i].getFlagCode());
						} else if (attrSrc == SHORTDESC) {
							selectedVct.addElement(mfArray[i].getShortDescription());
						} else {
							selectedVct.addElement(mfArray[i].toString());
						}
					} // metaflag is selected
				}
			}
		}
		return selectedVct;
	}
	
	/**
	 * Get the current Flag code Value for the specified attribute, null if not set
	 *
	 * @param item
	 *            EntityItem
	 * @param attrCode
	 *            String attribute code to get value for
	 * @return String attribute flag code
	 */
	protected String getAttributeFlagValue(EntityItem item, String attrCode) {
		return PokUtils.getAttributeFlagValue(item, attrCode);
	}
		 
	protected String getAttributeValue(EntityItem item, String attrCode) {
		return PokUtils.getAttributeValue(item, attrCode, ",", "", false);
	}
	
}

class AITFEEDXMLRelator {
	private String entit1Type;
	private String entity1ID;
	private String entity2ID;
	private String entity2Type;
	
	public AITFEEDXMLRelator() {}

	public AITFEEDXMLRelator(String entit1Type, String entity1id,
			String entity2Type, String entity2id) {
		this.entit1Type = entit1Type;
		entity1ID = entity1id;
		entity2ID = entity2id;
		this.entity2Type = entity2Type;
	}

	public String getEntity1ID() {
		return entity1ID;
	}

	public void setEntity1ID(String entity1id) {
		entity1ID = entity1id;
	}

	public String getEntity2ID() {
		return entity2ID;
	}

	public void setEntity2ID(String entity2id) {
		entity2ID = entity2id;
	}

	public String getEntit1Type() {
		return entit1Type;
	}

	public void setEntit1Type(String entit1Type) {
		this.entit1Type = entit1Type;
	}

	public String getEntity2Type() {
		return entity2Type;
	}

	public void setEntity2Type(String entity2Type) {
		this.entity2Type = entity2Type;
	}

}
