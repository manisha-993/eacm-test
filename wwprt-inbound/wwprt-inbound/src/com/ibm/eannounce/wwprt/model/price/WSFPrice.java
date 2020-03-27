package com.ibm.eannounce.wwprt.model.price;

import com.ibm.commons.db.mapping.source.ConstantStringSource;
import com.ibm.commons.db.mapping.source.LeftStringSource;
import com.ibm.commons.db.mapping.source.RightStringSource;
import com.ibm.commons.db.mapping.source.ValueSource;
import com.ibm.commons.db.mapping.source.XMLContentSource;
import com.ibm.eannounce.wwprt.model.PriceTable;
import com.ibm.eannounce.wwprt.model.Prices;


public class WSFPrice extends PriceTable {
	public WSFPrice(){		
	}
	
	public WSFPrice(String Version) {
		super(Version);
	}
	
	
	@Override
	protected ValueSource<String> getOfferingType() {
		return new ConstantStringSource("SWFEATURE");
	}

	@Override
	protected ValueSource<String> getMachineTypeAttr() {
		if(_version.equals(Prices.VERSION_V1)){
			return new LeftStringSource(new XMLContentSource(offering), 4);
		}else{
			return new LeftStringSource(new XMLContentSource(offeringname), 4);
		}
	}

	@Override
	protected ValueSource<String> getModelAttr() {
		if(_version.equals(Prices.VERSION_V1)){
			return new RightStringSource(new XMLContentSource(offering), 3);
		}else{
			return new RightStringSource(new XMLContentSource(offeringname), 3);
		}
	}
	
	@Override
	protected ValueSource<String> getFeatureCode() {
		if(_version.equals(Prices.VERSION_V1)){
			return new XMLContentSource(pricepointvalue);
		}else{
			return new XMLContentSource(variantname);
		}
	}

	@Override
	protected ValueSource<String> getPartNum() {
		return null;
	}

	@Override
	protected ValueSource<String> getFromFeatureCode() {
		return null;
	}

	@Override
	protected ValueSource<String> getFromMachineTypeAttr() {
		return null;
	}

	@Override
	protected ValueSource<String> getFromModelAttr() {
		return null;
	}



}
