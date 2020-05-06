package com.ibm.transform.oim.eacm.xalan.xslabr;

import com.ibm.etools.xmlschema.beans.*;

/**
* This class represents the complex type <DynamicSQL>
*/
public class DynamicSQL extends ComplexType
{
	private static final long serialVersionUID = 1L;
  public DynamicSQL()
  {
  }
  
  public void setBaseSQL(String BaseSQL)
  {
    setElementValue("BaseSQL", BaseSQL);
  }
  
  public String getBaseSQL()
  {
    return getElementValue("BaseSQL");
  }
  
  public boolean removeBaseSQL()
  {
    return removeElement("BaseSQL");
  }
  
  public void setInsertAttribute(int index, InsertAttribute InsertAttribute)
  {
    setElementValue(index, "InsertAttribute", InsertAttribute);
  }
  
  public InsertAttribute getInsertAttribute(int index)
  {
    return (InsertAttribute) getElementValue("InsertAttribute", "InsertAttribute", index);
  }
  
  public int getInsertAttributeCount()
  {
    return sizeOfElement("InsertAttribute");
  }
  
  public boolean removeInsertAttribute(int index)
  {
    return removeElement(index, "InsertAttribute");
  }
  
}

