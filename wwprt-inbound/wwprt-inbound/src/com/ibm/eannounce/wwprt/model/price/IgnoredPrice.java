package com.ibm.eannounce.wwprt.model.price;

import com.ibm.commons.db.mapping.source.ConstantStringSource;
import com.ibm.commons.db.mapping.source.ValueSource;
import com.ibm.eannounce.wwprt.model.PriceTable;

public class IgnoredPrice extends PriceTable {
	public IgnoredPrice(){		
	}
	
	public IgnoredPrice(String Version) {
		super(Version);
	}

	@Override
	protected ValueSource<String> getFeatureCode() {
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

	@Override
	protected ValueSource<String> getMachineTypeAttr() {
		return null;
	}

	@Override
	protected ValueSource<String> getModelAttr() {
		return null;
	}

	@Override
	protected ValueSource<String> getOfferingType() {
		return new ConstantStringSource("");
	}

	@Override
	protected ValueSource<String> getPartNum() {
		return null;
	}

}
