package com.ibm.transform.oim.eacm.xalan.xslabr;

import com.ibm.etools.xmlschema.beans.*;

/**
* This class represents the complex type <Obj>
*/
public class Obj extends ComplexType
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  public Obj()
  {
  }
  
  public void setDynamicSQL(DynamicSQL DynamicSQL)
  {
    setElementValue("DynamicSQL", DynamicSQL);
  }
  
  public DynamicSQL getDynamicSQL()
  {
    return (DynamicSQL) getElementValue("DynamicSQL", "DynamicSQL");
  }
  
  public boolean removeDynamicSQL()
  {
    return removeElement("DynamicSQL");
  }
  
  public void setInvoke(int index, Invoke Invoke)
  {
    setElementValue(index, "Invoke", Invoke);
  }
  
  public Invoke getInvoke(int index)
  {
    return (Invoke) getElementValue("Invoke", "Invoke", index);
  }
  
  public int getInvokeCount()
  {
    return sizeOfElement("Invoke");
  }
  
  public boolean removeInvoke(int index)
  {
    return removeElement(index, "Invoke");
  }
  
  public void setExcludeReturnCode(boolean excludeReturnCode)
  {
    setAttributeValue("excludeReturnCode", excludeReturnCode);
  }
  
  public boolean getExcludeReturnCode()
  {
    return getAttributeBooleanValue("excludeReturnCode");
  }
  
  public boolean removeExcludeReturnCode()
  {
    return removeAttribute("excludeReturnCode");
  }
  
  public void setType(String type)
  {
    setAttributeValue("type", type);
  }
  
  public String getType()
  {
    return getAttributeValue("type");
  }
  
  public boolean removeType()
  {
    return removeAttribute("type");
  }
  
}

