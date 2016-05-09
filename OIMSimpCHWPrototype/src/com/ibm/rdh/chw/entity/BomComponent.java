package com.ibm.rdh.chw.entity;

import java.math.BigInteger;

public class BomComponent {

	// Since Reading SAP returns emptyString instead of null set all string to
	// empty String
	String ITEM_CATEG = "";
	String ITEM_NO = "";
	String COMPONENT = "";
	BigInteger ITEM_NODE = null;
	BigInteger ITEM_COUNT = null;
	String SORTSTRING = "";
	String FIXED_QTY = "";
	String COMP_QTY = "";
	String REL_SALES = "";
	String COMP_UNIT = "";

	public BomComponent() {
		super();
	}

	/**
	 * @return Returns the FIXED_QTY.
	 */
	public String getFixed_Qty() {
		return FIXED_QTY;
	}

	/**
	 * @param fixed_qty
	 *            The FIXED_QTY to set.
	 */
	public void setFixed_Qty(String fixed_qty) {
		FIXED_QTY = fixed_qty;
	}

	/**
	 * @return Returns the SORTSTRING.
	 */
	public String getSortstring() {
		return SORTSTRING;
	}

	/**
	 * @param sortstring
	 *            The SORTSTRING to set.
	 */
	public void setSortstring(String sortstring) {
		SORTSTRING = sortstring;
	}

	/**
	 * @return Returns the COMP_QTY.
	 */
	public String getComp_Qty() {
		return COMP_QTY;
	}

	/**
	 * @param comp_qty
	 *            The COMP_QTY to set.
	 */
	public void setComp_Qty(String comp_qty) {
		COMP_QTY = comp_qty;
	}

	/**
	 * @return Returns the REL_SALES.
	 */
	public String getRel_Sales() {
		return REL_SALES;
	}

	/**
	 * @param rel_sales
	 *            The REL_SALES to set.
	 */
	public void setRel_Sales(String rel_sales) {
		REL_SALES = rel_sales;
	}

	/**
	 * @return Returns the COMP_UNIT.
	 */
	public String getComp_Unit() {
		return COMP_UNIT;
	}

	/**
	 * @param comp_unit
	 *            The COMP_UNIT to set.
	 */
	public void setComp_Unit(String comp_unit) {
		COMP_UNIT = comp_unit;
	}

	public java.lang.String getItem_Categ() {
		return ITEM_CATEG;
	}

	public java.lang.String getItem_No() {
		return ITEM_NO;
	}

	public java.lang.String getComponent() {
		return COMPONENT;
	}

	public BigInteger getItem_Node() {
		return ITEM_NODE;
	}

	public BigInteger getItem_Count() {
		return ITEM_COUNT;
	}

	public void setItem_Categ(String Icateg) {
		ITEM_CATEG = Icateg;
	}

	public void setItem_No(String Ino) {
		ITEM_NO = Ino;
	}

	public void setComponent(String Compo) {
		COMPONENT = Compo;
	}

	public void setItem_Node(BigInteger Inode) {
		ITEM_NODE = Inode;
	}

	public void setItem_Count(BigInteger Icount) {
		ITEM_COUNT = Icount;
	}

	public boolean equals(BomComponent bc) {
		boolean result = true;

		result = result
				&& this.equalObjectsInternal(bc.getComp_Qty(),
						this.getComp_Qty());
		result = result
				&& this.equalObjectsInternal(bc.getComp_Unit(),
						this.getComp_Unit());
		result = result
				&& this.equalObjectsInternal(bc.getComponent(),
						this.getComponent());
		result = result
				&& this.equalObjectsInternal(bc.getFixed_Qty(),
						this.getFixed_Qty());
		result = result
				&& this.equalObjectsInternal(bc.getItem_Categ(),
						this.getItem_Categ());
		result = result
				&& this.equalObjectsInternal(bc.getItem_No(), this.getItem_No());
		result = result
				&& this.equalObjectsInternal(bc.getRel_Sales(),
						this.getRel_Sales());
		result = result
				&& this.equalObjectsInternal(bc.getSortstring(),
						this.getSortstring());

		// Not compared since SAP set
		// result = result &&
		// this.equalObjectsInternal(bc.getItem_Count(),this.getItem_Count());
		// result = result &&
		// this.equalObjectsInternal(bc.getItem_Node(),this.getItem_Node());

		return result;
	}

	/**
	 * Compares contents of 2 objects.
	 * 
	 * @param a
	 * @param b
	 * @param nullFlag
	 *            - consider null and empty String the same
	 * @return
	 */
	private boolean equalObjectsInternal(Object a, Object b) {
		boolean result = true;

		if (a != null && b != null) {
			if (!(a.equals(b))) {
				result = false;
			}
		} else if (a != null || b != null) {
			// Since both are not null (already check) but one is then no match
			result = false;
		}
		return result;
	}

}
