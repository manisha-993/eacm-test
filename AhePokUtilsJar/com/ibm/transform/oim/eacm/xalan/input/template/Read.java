package com.ibm.transform.oim.eacm.xalan.input.template;

import com.ibm.etools.xmlschema.beans.*;

/**
* This class represents the complex type <Read>
*/
public class Read extends ComplexType
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  public Read()
  {
  }
  
  public void setNlsID(String nlsID)
  {
    setAttributeValue("nlsID", nlsID);
  }
  
  public String getNlsID()
  {
    return getAttributeValue("nlsID");
  }
  
  public boolean removeNlsID()
  {
    return removeAttribute("nlsID");
  }
  
}

