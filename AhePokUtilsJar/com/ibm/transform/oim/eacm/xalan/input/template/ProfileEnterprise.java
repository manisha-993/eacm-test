package com.ibm.transform.oim.eacm.xalan.input.template;

import com.ibm.etools.xmlschema.beans.*;

/**
* This class represents the complex type <ProfileEnterprise>
*/
public class ProfileEnterprise extends ComplexType
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  public ProfileEnterprise()
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
  
}

