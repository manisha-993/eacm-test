package com.ibm.eannounce.wwprt.model.price;

import com.ibm.commons.db.mapping.source.ConstantStringSource;
import com.ibm.commons.db.mapping.source.LeftStringSource;
import com.ibm.commons.db.mapping.source.RightStringSource;
import com.ibm.commons.db.mapping.source.ValueSource;
import com.ibm.commons.db.mapping.source.XMLContentSource;
import com.ibm.eannounce.wwprt.model.PriceTable;


public class SWSPrice extends PriceTable {
	public SWSPrice() {
	}
	
	public SWSPrice(String Version) {
		super(Version);
	}
	
	@Override
	protected ValueSource<String> getOfferingType() {
		return new ConstantStringSource("SWSFEATURE");
	}

	@Override
	protected ValueSource<String> getMachineTypeAttr() {	
		return new LeftStringSource(new XMLContentSource(offeringname), 4);
	}

	@Override
	protected ValueSource<String> getModelAttr() {
		return new RightStringSource(new XMLContentSource(offeringname), 3);
	}
	
	@Override
	protected ValueSource<String> getFeatureCode() {	
		return new XMLContentSource(variantname);		
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
