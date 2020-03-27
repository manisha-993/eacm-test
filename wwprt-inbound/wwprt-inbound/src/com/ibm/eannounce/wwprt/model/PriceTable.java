package com.ibm.eannounce.wwprt.model;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.ibm.commons.db.mapping.Column;
import com.ibm.commons.db.mapping.InvalidColumnException;
import com.ibm.commons.db.mapping.Table;
import com.ibm.commons.db.mapping.column.ColumnTypes;
import com.ibm.commons.db.mapping.source.NowTimestampSource;
import com.ibm.commons.db.mapping.source.ValueSource;
import com.ibm.commons.db.mapping.source.ValueSourceVisitor;
import com.ibm.commons.db.mapping.source.XMLAttributeSource;
import com.ibm.commons.db.mapping.source.XMLContentSource;
import com.ibm.commons.db.mapping.source.XMLDateSource;
import com.ibm.commons.db.mapping.source.XMLDecimalSource;
import com.ibm.commons.db.mapping.source.XMLElementAsStringSource;
import com.ibm.commons.db.mapping.source.XMLTimestampSource;
import com.ibm.commons.db.mapping.source.XMLValueSource;
import com.ibm.eannounce.wwprt.Context;

public abstract class PriceTable extends Table implements ValueSourceVisitor {

	private XMLAttributeSource pricesIdSource = new XMLAttributeSource("id");
	private String offerings;
	private Element element;
	protected String _version = Prices.VERSION_V1;
	protected static String pricepointtype  = "pricepointtype";
	protected static String pricepointvalue = "pricepointvalue";
	protected static String varianttype     = "varianttype";
	protected static String variantname     = "variantname";
	protected static String offering        = "offering";
	protected static String offeringname    = "offeringname";
	
	
	
	
	public String get_version() {
		return _version;
	}

	public void set_version(String version) {
		_version = version;
	}

	public PriceTable(String version){
		super(Context.get().getTempPriceTable());
		set_version(version);
		//Log.i("Start process PriceTable version : " + _version);
		

		add("ID", ColumnTypes.STRING, pricesIdSource, true);
		add("INSERT_TS", ColumnTypes.TIMESTAMP, new NowTimestampSource(), true);

		// PRIMARY KEYS
		add("OFFERING", ColumnTypes.STRING, new XMLContentSource(offeringname), true, true);
		add("PRICE_POINT_TYPE", ColumnTypes.STRING, getVariantType(), true, true);
		add("PRICE_POINT_VALUE", ColumnTypes.STRING, getVariantName(), true, true);
		add("PRICE_TYPE", ColumnTypes.STRING, new XMLContentSource("pricetype"), true, true);
		add("COUNTRY", ColumnTypes.STRING, new XMLContentSource("country"), true, true);
		add("ONSHORE", ColumnTypes.STRING, new XMLContentSource("onshore"), true, true);
		add("END_DATE", ColumnTypes.DATE, new XMLDateSource(new XMLContentSource("enddate")), true, true);
		add("ACTION", ColumnTypes.STRING, new XMLAttributeSource("type"), true, true);

		//COPIED ATTRIBUTES
		add("PRICEXML", ColumnTypes.CLOB, new XMLElementAsStringSource(), true);
		add("START_DATE", ColumnTypes.DATE, new XMLDateSource(new XMLContentSource("startdate")), true);
		add("CURRENCY", ColumnTypes.STRING, new XMLContentSource("currency"), false);
		add("PRICE_VALUE", ColumnTypes.STRING, new XMLDecimalSource(new XMLContentSource("pricevalue")), false);
		add("PRICE_VALUE_USD", ColumnTypes.STRING, new XMLDecimalSource(new XMLContentSource("pricevalueusd")), false);
		add("FACTOR", ColumnTypes.STRING, new XMLDecimalSource(new XMLContentSource("factor")),false);
		add("RELEASE_TS", ColumnTypes.TIMESTAMP, new XMLTimestampSource(new XMLContentSource("releasets")), true);
		add("CABLETYPE", ColumnTypes.STRING, new XMLContentSource("cabletype"), false);
		add("CABLEID", ColumnTypes.STRING, new XMLContentSource("cableid"), false);

		//DERIVED ATTRIBUTES
		addDerived("OFFERING_TYPE", getOfferingType());
		addDerived("MACHTYPEATR", getMachineTypeAttr());
		addDerived("MODELATR", getModelAttr());
		addDerived("FEATURECODE", getFeatureCode());
		addDerived("PARTNUM", getPartNum());
		addDerived("FROM_MACHTYPEATR", getFromMachineTypeAttr());
		addDerived("FROM_MODELATR", getFromModelAttr());
		addDerived("FROM_FEATURECODE", getFromFeatureCode());
		//Log.i("End process PriceTable version :"+ version);
		
	}

	public PriceTable() {
		
		super(Context.get().getTempPriceTable());
		
		//Log.i("Start process PriceTable......v1="+_version);
		

		add("ID", ColumnTypes.STRING, pricesIdSource, true);
		add("INSERT_TS", ColumnTypes.TIMESTAMP, new NowTimestampSource(), true);

		// PRIMARY KEYS
		add("OFFERING", ColumnTypes.STRING, new XMLContentSource(offering), true, true);
		add("PRICE_POINT_TYPE", ColumnTypes.STRING, getPricePointType(), true, true);
		add("PRICE_POINT_VALUE", ColumnTypes.STRING, getPricePointValue(), true, true);
		add("PRICE_TYPE", ColumnTypes.STRING, new XMLContentSource("pricetype"), true, true);
		add("COUNTRY", ColumnTypes.STRING, new XMLContentSource("country"), true, true);
		add("ONSHORE", ColumnTypes.STRING, new XMLContentSource("onshore"), true, true);
		add("END_DATE", ColumnTypes.DATE, new XMLDateSource(new XMLContentSource("enddate")), true, true);
		add("ACTION", ColumnTypes.STRING, new XMLAttributeSource("type"), true, true);

		//COPIED ATTRIBUTES
		add("PRICEXML", ColumnTypes.CLOB, new XMLElementAsStringSource(), true);
		add("START_DATE", ColumnTypes.DATE, new XMLDateSource(new XMLContentSource("startdate")), true);
		add("CURRENCY", ColumnTypes.STRING, new XMLContentSource("currency"), false);
		add("PRICE_VALUE", ColumnTypes.STRING, new XMLDecimalSource(new XMLContentSource("pricevalue")), false);
		add("PRICE_VALUE_USD", ColumnTypes.STRING, new XMLDecimalSource(new XMLContentSource("pricevalueusd")), false);
		add("FACTOR", ColumnTypes.STRING, new XMLDecimalSource(new XMLContentSource("factor")),	false);
		add("RELEASE_TS", ColumnTypes.TIMESTAMP, new XMLTimestampSource(new XMLContentSource("releasets")), true);
		add("CABLETYPE", ColumnTypes.STRING, new XMLContentSource("cabletype"), false);
		add("CABLEID", ColumnTypes.STRING, new XMLContentSource("cableid"), false);

		//DERIVED ATTRIBUTES
		addDerived("OFFERING_TYPE", getOfferingType());
		addDerived("MACHTYPEATR", getMachineTypeAttr());
		addDerived("MODELATR", getModelAttr());
		addDerived("FEATURECODE", getFeatureCode());
		addDerived("PARTNUM", getPartNum());
		addDerived("FROM_MACHTYPEATR", getFromMachineTypeAttr());
		addDerived("FROM_MODELATR", getFromModelAttr());
		addDerived("FROM_FEATURECODE", getFromFeatureCode());
		//Log.i("End process PriceTable......");
	}

	protected void addDerived(String column, ValueSource<String> source) {
		add(column, ColumnTypes.STRING, source, false);
	}

	protected ValueSource<String> getPricePointType() {
		return new XMLContentSource(pricepointtype);
	}
	
	protected ValueSource<String> getVariantType() {
		return new XMLContentSource(varianttype);
	}

	protected ValueSource<String> getPricePointValue() {
		return new XMLContentSource(pricepointvalue);
	}
	
	protected ValueSource<String> getVariantName() {
		return new XMLContentSource(variantname);
	}

	protected abstract ValueSource<String> getOfferingType();

	protected abstract ValueSource<String> getMachineTypeAttr();

	protected abstract ValueSource<String> getModelAttr();

	protected abstract ValueSource<String> getFeatureCode();

	protected abstract ValueSource<String> getPartNum();

	protected abstract ValueSource<String> getFromMachineTypeAttr();

	protected abstract ValueSource<String> getFromModelAttr();

	protected abstract ValueSource<String> getFromFeatureCode();

	public void setValues(Prices prices, Element element) {
		this.element = element;
		NodeList nodeList = null;
		if(_version.equals(Prices.VERSION_V1)){
			nodeList = element.getElementsByTagName("offering");
		}else{
			nodeList = element.getElementsByTagName("offeringname");
		}
		if (nodeList.getLength() == 1) {
			this.offerings = nodeList.item(0).getTextContent();
		} else {
		}

		acceptVisitor(this);
		pricesIdSource.setElement(element.getOwnerDocument().getDocumentElement());
	}

	public PriceErrors populateAndValidate() {
		PriceErrors errors = new PriceErrors(offerings);
		for (Column<?> column : getColumns()) {
			try {
				column.validate();
			} catch (InvalidColumnException e) {
				errors.addError(e.getId(), e.getMessage());
			}
		}
		return errors;
	}

	public void visit(ValueSource<?> valueSource) {
		if (valueSource != null) {
			if (valueSource instanceof XMLValueSource<?>) {
				((XMLValueSource<?>) valueSource).setElement(element);
			}
		}
	}

	public String dumpValues() {
		StringBuilder sb = new StringBuilder();
		for (Column<?> column : getColumns()) {
			sb.append(column.getValue());
			sb.append(", ");
		}
		return sb.toString();
	}
	
	public String getOffering() {
		return offerings;
	}
}
