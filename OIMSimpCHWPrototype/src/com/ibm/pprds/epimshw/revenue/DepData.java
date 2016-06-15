package com.ibm.pprds.epimshw.revenue;

import java.math.BigInteger;

/**
 * @author laxmi
 *
 */
public class DepData {
	
					
	String _Dep_Intern = null;
	String _Status = null;
	BigInteger _Item_Node = null;
	BigInteger _Item_Count = null;
	String _itemCatelog = null;
	String _itemNumber = null;
	String _component = null;
	String _sortString = null ;
	BigInteger _Item_Node1 = null;
	BigInteger _Item_Count1 = null;
	
	
	public DepData(String itemCatalog, String itemNo, String component, String sortString,String depIntern, String status, BigInteger itemNode, BigInteger itemCount)
	{
		_itemCatelog = itemCatalog;
		_itemNumber = itemNo;
		_component = component;
		_sortString = sortString;
		_Dep_Intern = depIntern;
		_Status = status;
		_Item_Node = itemNode;
		_Item_Count = itemCount;
		
	}
	
	public DepData () {
	
	}
		
	
	public java.lang.String getDep_Intern() {
		return _Dep_Intern;
	}	
	
	public java.lang.String getStatus() {
		return _Status;
	}	
		
	public BigInteger getItem_Node() {
		return _Item_Node;
	}	
	
	public BigInteger getItem_Count() {
		return _Item_Count;
	}	
	
	public BigInteger getItem_Node1() {
		return _Item_Node1;
	}	
	
	public BigInteger getItem_Count1() {
		return _Item_Count1;
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
	
	public void setDep_Intern(String dep) {
		_Dep_Intern = dep;
	}
	public void setStatus(String sat) {
		_Status = sat;
	}	
	
	public void setItem_Node(BigInteger Ino) {
		_Item_Node = Ino;
	}
	
	public void setItem_Count(BigInteger Compo) {
		_Item_Count = Compo;
	}
	
	public void setItem_Node1(BigInteger Ino1) {
		_Item_Node1 = Ino1;
	}
	
	public void setItem_Count1(BigInteger Compo1) {
		_Item_Count1 = Compo1;
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
	
	

}
