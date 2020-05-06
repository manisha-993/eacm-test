package com.ibm.transform.oim.eacm.xalan;

import COM.ibm.eannounce.objects.EntityItem;

import com.ibm.transform.oim.eacm.util.PokUtils;

/**
 * When we trigger the Strage Extract Report action, if the MACHTYPE or ANNNUMBER is empty, the query will 
 * extract all the data from view, then will throw out of memory exception and TM will be down. We use this
 * checker to valid if the query extract all data from db to avoid this error.
 * 
 *
 */
public class StorageExtractSetupEntityChecker {
	
	private static final String STORAGE_EXTRACT_SETUP_ENTITYTYPE = "EXTSTORAGE";
	private static final String STORAGE_EXTRACT_ANNNUMBER_ABR = "STORAGEABR";
	private static final String STORAGE_EXTRACT_ANNNUMBER_ABR_REQUIRED_ATTRIBUTE = "ANNNUMBER";
	private static final String STORAGE_EXTRACT_MACHTYPE_ABR = "STORAGEMTABR";
	private static final String STORAGE_EXTRACT_MACHTYPE_ABR_REQUIRED_ATTRIBUTE = "MACHTYPEATR";
	
	public boolean isStorageExtractSetupEntity(String entityType) {
		if(STORAGE_EXTRACT_SETUP_ENTITYTYPE.equals(entityType)) {
			return true;
		}
		return false;
	}
	
	public boolean isRequiredAttributeEmpty(EntityItem item, String abrCode) {
		boolean attributeIsEmpty = false;
		if(STORAGE_EXTRACT_ANNNUMBER_ABR.equals(abrCode)) {				
			String attributeValue = PokUtils.getAttributeValue(item, STORAGE_EXTRACT_ANNNUMBER_ABR_REQUIRED_ATTRIBUTE, ",", "");
			if("".equals(attributeValue)) {
				attributeIsEmpty = true;
			}			
		} else if(STORAGE_EXTRACT_MACHTYPE_ABR.equals(abrCode)) {
			String attributeValue = PokUtils.getAttributeValue(item, STORAGE_EXTRACT_MACHTYPE_ABR_REQUIRED_ATTRIBUTE, ",", "");
			if("".equals(attributeValue)) {
				attributeIsEmpty = true;
			}
		}
		return attributeIsEmpty;
	}

}
