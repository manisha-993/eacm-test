package com.ibm.eannounce.wwprt.model.price;

import com.ibm.commons.db.mapping.source.ConstantStringSource;
import com.ibm.commons.db.mapping.source.ValueSource;
import com.ibm.commons.db.mapping.source.XMLContentSource;
import com.ibm.eannounce.wwprt.model.PriceTable;
import com.ibm.eannounce.wwprt.model.Prices;

public class NoTypePrice extends PriceTable {
	
	public NoTypePrice() {
	}
	
	public NoTypePrice(String Version) {
		super(Version);
	}
			

	protected ValueSource<String> getPricePointType() {
		return new ConstantStringSource("");
	}
	
	protected ValueSource<String> getVariantType() {
		return new ConstantStringSource("");
	}

	protected ValueSource<String> getPricePointValue() {
		return new ConstantStringSource("");
	}
	
	protected ValueSource<String> getVariantName() {
		return new ConstantStringSource("");
	}

	@Override
	protected ValueSource<String> getOfferingType() {
		if(_version.equals(Prices.VERSION_V1)){
			return new ConstantStringSource("");
		}else{
			return new XMLContentSource("offeringtype");
		}
		
	}

	@Override
	protected ValueSource<String> getMachineTypeAttr() {
		return null;
	}

	@Override
	protected ValueSource<String> getModelAttr() {
		return null;
	}

	@Override
	protected ValueSource<String> getFeatureCode() {
		return null;
	}

	@Override
	protected ValueSource<String> getPartNum() {
		if(_version.equals(Prices.VERSION_V1)){
			return new XMLContentSource(offering);
		}else{
			return new XMLContentSource(offeringname);
		}
		
	}

	@Override
	protected ValueSource<String> getFromMachineTypeAttr() {
		return null;
	}

	@Override
	protected ValueSource<String> getFromModelAttr() {
		return null;
	}

	@Override
	protected ValueSource<String> getFromFeatureCode() {
		return null;
	}

}
