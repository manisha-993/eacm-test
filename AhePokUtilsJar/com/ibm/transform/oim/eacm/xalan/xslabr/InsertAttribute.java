package com.ibm.transform.oim.eacm.xalan.xslabr;

import com.ibm.etools.xmlschema.beans.*;

/**
* This class represents the complex type <InsertAttribute>
*  Revision 1.5  2015/06/01 21:46:15  Luis
 * RCQ00276438-WI /RCQ00188990-RQ Storage SS - add isNumber attribute get and set
*/
public class InsertAttribute extends ComplexType
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  public InsertAttribute()
  {
  }
  
  public void setCode(String code)
  {
    setAttributeValue("code", code);
  }
  
  public String getCode()
  {
    return getAttributeValue("code");
  }
  
  public boolean removeCode()
  {
    return removeAttribute("code");
  }
  
  public void setUseFlagCode(boolean useFlagCode)
  {
    setAttributeValue("useFlagCode", useFlagCode);
  }
  
  public boolean getUseFlagCode()
  {
    return getAttributeBooleanValue("useFlagCode");
  }
  
  public boolean removeUseFlagCode()
  {
    return removeAttribute("useFlagCode");
  }
  
  public void setIsList(boolean isList)
  {
    setAttributeValue("isList", isList);
  }
  
  public boolean getIsList()
  {
    return getAttributeBooleanValue("isList");
  }
  
  public boolean removeIsList()
  {
    return removeAttribute("isList");
  }  

  public void setMaxList(String maxList)
  {
    setAttributeValue("maxList", maxList);
  }
  
  public String getMaxList()
  {
    return getAttributeValue("maxList");
  }
  
  public boolean removeMaxList()
  {
    return removeAttribute("maxList");
  }  
  
  public void setIsNumber(boolean isNumber)
  {
    setAttributeValue("isNumber", isNumber);
  }
  
  public boolean getIsNumber()
  {
    return getAttributeBooleanValue("isNumber");
  }
  
  public boolean removeIsNumber()
  {
    return removeAttribute("isNumber");
  }
}

