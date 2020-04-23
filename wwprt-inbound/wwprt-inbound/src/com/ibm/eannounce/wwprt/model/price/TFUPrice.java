package com.ibm.eannounce.wwprt.model.price;

import com.ibm.commons.db.mapping.source.ConstantStringSource;
import com.ibm.commons.db.mapping.source.LeftStringSource;
import com.ibm.commons.db.mapping.source.MidStringSource;
import com.ibm.commons.db.mapping.source.RightStringSource;
import com.ibm.commons.db.mapping.source.ValueSource;
import com.ibm.commons.db.mapping.source.XMLContentSource;
import com.ibm.eannounce.wwprt.model.PriceTable;
import com.ibm.eannounce.wwprt.model.Prices;


public class TFUPrice extends PriceTable {
	public TFUPrice(){		
	}
	
	public TFUPrice(String Version) {
		super(Version);
	}
	
	@Override
	protected ValueSource<String> getOfferingType() {
		return new ConstantStringSource("FCTRANSACTION");
	}

	@Override
	protected ValueSource<String> getMachineTypeAttr() {
		if(_version.equals(Prices.VERSION_V1)){
			return new MidStringSource(new XMLContentSource(pricepointvalue), 5,4);
		}else{
			return new MidStringSource(new XMLContentSource(variantname), 5,4);
		}
	}

	@Override
	protected ValueSource<String> getModelAttr() {
		return null;
	}
	
	@Override
	protected ValueSource<String> getFeatureCode() {
		if(_version.equals(Prices.VERSION_V1)){
			return new RightStringSource(new XMLContentSource(pricepointvalue), 4);
		}else{
			return new RightStringSource(new XMLContentSource(variantname),4);
		}
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
		return null;
	}

	@Override
	protected ValueSource<String> getFromFeatureCode() {
		if(_version.equals(Prices.VERSION_V1)){
			return new LeftStringSource(new XMLContentSource(pricepointvalue), 4);
		}else{
			return new LeftStringSource(new XMLContentSource(variantname),4);
		}
	}

}
