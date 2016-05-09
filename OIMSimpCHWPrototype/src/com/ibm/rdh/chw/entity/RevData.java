package com.ibm.rdh.chw.entity;

import java.math.BigInteger;

public class RevData {
	String _itemCatelog = null;
	String _itemNumber = null;
	String _component = null;
	String _sortString = null;
	BigInteger _itemNode = null;
	BigInteger _itemCount = null;
	String _bundleid = null;
	int _countHW;
	int _countM;
	int _countI;

	public RevData(String itemCatalog, String itemNo, String component,
			String sortString, BigInteger itemNode, BigInteger itemCount) {
		_itemCatelog = itemCatalog;
		_itemNumber = itemNo;
		_component = component;
		_sortString = sortString;
		_itemNode = itemNode;
		_itemCount = itemCount;
	}

	public java.lang.String getItem_Categ() {
		return _itemCatelog;
	}

	public java.lang.String getItem_No() {
		return _itemNumber;
	}

	public java.lang.String getComponent() {
		return _component;
	}

	public java.lang.String getSortString() {
		return _sortString;
	}

	public BigInteger getItem_Node() {
		return _itemNode;
	}

	public BigInteger getItem_Count() {
		return _itemCount;
	}

	public void setItem_Categ(String Icateg) {
		_itemCatelog = Icateg;
	}

	public void setItem_No(String Ino) {
		_itemNumber = Ino;
	}

	public void setComponent(String Compo) {
		_component = Compo;
	}

	public void setSortString(String sortString) {
		_sortString = sortString;
	}

	public void setItem_Node(BigInteger Inode) {
		_itemNode = Inode;
	}

	public void setItem_Count(BigInteger Icount) {
		_itemCount = Icount;
	}

	public RevData(String bundleid, int countHW, int countM, int countI) {
		_bundleid = bundleid;
		_countHW = countHW;
		_countM = countM;
		_countI = countI;

	}

	public String getBundleID() {
		return _bundleid;
	}

	public int getCountHW() {
		return _countHW;
	}

	public int getCountM() {
		return _countM;
	}

	public int getCountI() {
		return _countI;
	}

	public void setBundleID(String bundleid) {
		_bundleid = bundleid;
	}

	public void setCountHW(int countHW) {
		_countHW = countHW;
	}

	public void setCountM(int countM) {
		_countM = countM;
	}

	public void setCountI(int countI) {
		_countI = countI;
	}

}
