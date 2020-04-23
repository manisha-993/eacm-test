package com.ibm.eannounce.wwprt.model.price;

import com.ibm.commons.db.mapping.source.ConstantStringSource;
import com.ibm.commons.db.mapping.source.LeftStringSource;
import com.ibm.commons.db.mapping.source.RightStringSource;
import com.ibm.commons.db.mapping.source.ValueSource;
import com.ibm.commons.db.mapping.source.XMLContentSource;
import com.ibm.eannounce.wwprt.model.PriceTable;
import com.ibm.eannounce.wwprt.model.Prices;


public class MUPPrice extends PriceTable {
	public MUPPrice(){		
	}
	
	public MUPPrice(String Version) {
		super(Version);
	}
	
	@Override
	protected ValueSource<String> getOfferingType() {
		return new ConstantStringSource("MODELCONVERT");
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
		if(_version.equals(Prices.VERSION_V1)){
			return new RightStringSource(new XMLContentSource(pricepointvalue), 3);
		}else{
			return new RightStringSource(new XMLContentSource(variantname), 3);
		}
	}
	
	@Override
	protected ValueSource<String> getFeatureCode() {
		return null;
	}

	@Override
	protected ValueSource<String> getPartNum() {
		return null;
	}
	
	@Override
	protected ValueSource<String> getFromMachineTypeAttr() {
		if(_version.equals(Prices.VERSION_V1)){
			return new XMLContentSource(offering);
		}else{
			return new XMLContentSource(offeringname);
		}
	}
	
	@Override
	protected ValueSource<String> getFromModelAttr() {
		if(_version.equals(Prices.VERSION_V1)){
			return new LeftStringSource(new XMLContentSource(pricepointvalue), 3);
		}else{
			return new LeftStringSource(new XMLContentSource(variantname), 3);
		}
	}

	@Override
	protected ValueSource<String> getFromFeatureCode() {
		return null;
	}

}
