package com.ibm.eannounce.wwprt.model.price;

import com.ibm.commons.db.mapping.source.ValueSource;
import com.ibm.eannounce.wwprt.model.PriceTable;

public class MergePrice extends PriceTable {
	public MergePrice() {
	}
	
	public MergePrice(String Version) {
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
		return null;
	}

	@Override
	protected ValueSource<String> getPartNum() {
		return null;
	}

}
