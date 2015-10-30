/*
 * Created on Jun 3, 2003
 */
package com.ibm.rdh.rfc.proxy;


import com.ibm.rdh.chw.entity.CHWAnnouncement;
import com.ibm.rdh.chw.entity.CHWGeoAnn;
import com.ibm.rdh.chw.entity.TypeModel;
import com.ibm.rdh.chw.entity.TypeModelUPGGeo;
import com.ibm.rdh.chw.caller.R100createTypeMaterialBasicView;

/**
 * @author bobc
 *
 * rjc - this factory will only create the rfc class, 
 * but NOT execute it. 
 */
public class RfcFactory extends Object {

	/* @see com.ibm.pprds.swpims.AbstractrRfcFactory#getR001(com.ibm.pprds.swpims.domain.SWO)
	 */
	public R100createTypeMaterialBasicView getr100(CHWAnnouncement chwA,
			TypeModel typeModel, CHWGeoAnn chwAg, String newFlag,
			TypeModelUPGGeo tmUPGObj, String FromToType, String pimsIdentity) throws Exception {
		return new R100createTypeMaterialBasicView(chwA, typeModel,  chwAg,  newFlag,
				 tmUPGObj,  FromToType,  pimsIdentity);
	}
	

}
 