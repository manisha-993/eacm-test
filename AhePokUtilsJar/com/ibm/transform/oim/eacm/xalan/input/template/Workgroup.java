package com.ibm.transform.oim.eacm.xalan.input.template;

import com.ibm.etools.xmlschema.beans.*;

/**
* This class represents the complex type <Workgroup>
*/
public class Workgroup extends ComplexType
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  public Workgroup()
  {
  }
  
  public void setWid(int wid)
  {
    setAttributeValue("wid", wid);
  }
  
  public int getWid()
  {
    return getAttributeIntegerValue("wid");
  }
  
  public boolean removeWid()
  {
    return removeAttribute("wid");
  }
  
}

