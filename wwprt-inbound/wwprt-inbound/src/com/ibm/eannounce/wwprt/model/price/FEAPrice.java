package com.ibm.eannounce.wwprt.model.price;

import com.ibm.commons.db.mapping.source.ConstantStringSource;
import com.ibm.commons.db.mapping.source.ValueSource;
import com.ibm.commons.db.mapping.source.XMLContentSource;
import com.ibm.eannounce.wwprt.model.PriceTable;
import com.ibm.eannounce.wwprt.model.Prices;


public class FEAPrice extends PriceTable {
	public FEAPrice() {
	}
	
	public FEAPrice(String Version) {
		super(Version);
	}
	
	@Override
	protected ValueSource<String> getOfferingType() {
		return new ConstantStringSource("FEATURE");
	}

	@Override
	protected ValueSource<String> getMachineTypeAttr() {
		if(_version.equals(Prices.VERSION_V1)){
			return new XMLContentSource(offering);
		}else{
			return new XMLContentSource(offeringname);
		}
	}

	@Override
	protected ValueSource<String> getModelAttr() {
		return null;
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
