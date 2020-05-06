package com.ibm.transform.oim.eacm.xalan.input.template;

import com.ibm.etools.xmlschema.beans.*;

/**
* Provides convenience methods for creating Java beans for elements
* in this XML document
*/
public class ABRJobFactory extends Factory
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  public ABRJobFactory()
  {
    super();
  }
  
  /**
  * Create the Java bean ABRJob for the root element
  *   @param rootElementname The tag for the root element
  *   @return ABRJob The Java bean representing this element
  */
  public ABRJob createRoot(String rootElementname)
  {
    return (ABRJob) createRootDOMFromComplexType("ABRJob",rootElementname);
  }
  
  /**
  * Create the Java bean ABRJob by loading the XML file
  *   @param filename The XML file name
  *   @return ABRJob The Java bean representing the root element
  */
  public ABRJob loadDocument(String filename)
  {
    return (ABRJob) loadDocument("ABRJob",filename);
  }
  
  /**
  * Create the Java bean ABRCode for the element
  *   @param elementName The tag for the element
  *   @return ABRCode The Java bean representing this element
  */
  public ABRCode createABRCode(String elementName)
  {
    return (ABRCode) createDOMElementFromComplexType("ABRCode",elementName);
  }
  
  /**
  * Create the Java bean ABRJob for the element
  *   @param elementName The tag for the element
  *   @return ABRJob The Java bean representing this element
  */
  public ABRJob createABRJob(String elementName)
  {
    return (ABRJob) createDOMElementFromComplexType("ABRJob",elementName);
  }
  
  /**
  * Create the Java bean AttributeDescription for the element
  *   @param elementName The tag for the element
  *   @return AttributeDescription The Java bean representing this element
  */
  public AttributeDescription createAttributeDescription(String elementName)
  {
    return (AttributeDescription) createDOMElementFromComplexType("AttributeDescription",elementName);
  }
  
  /**
  * Create the Java bean DGSubmit for the element
  *   @param elementName The tag for the element
  *   @return DGSubmit The Java bean representing this element
  */
  public DGSubmit createDGSubmit(String elementName)
  {
    return (DGSubmit) createDOMElementFromSimpleType("DGSubmit",elementName);
  }
  
  /**
  * Create the Java bean ProfileEnterprise for the element
  *   @param elementName The tag for the element
  *   @return ProfileEnterprise The Java bean representing this element
  */
  public ProfileEnterprise createProfileEnterprise(String elementName)
  {
    return (ProfileEnterprise) createDOMElementFromComplexType("ProfileEnterprise",elementName);
  }
  
  /**
  * Create the Java bean EntityDescription for the element
  *   @param elementName The tag for the element
  *   @return EntityDescription The Java bean representing this element
  */
  public EntityDescription createEntityDescription(String elementName)
  {
    return (EntityDescription) createDOMElementFromComplexType("EntityDescription",elementName);
  }
  
  /**
  * Create the Java bean ReadLanguage for the element
  *   @param elementName The tag for the element
  *   @return ReadLanguage The Java bean representing this element
  */
  public ReadLanguage createReadLanguage(String elementName)
  {
    return (ReadLanguage) createDOMElementFromComplexType("ReadLanguage",elementName);
  }
  
  /**
  * Create the Java bean Role for the element
  *   @param elementName The tag for the element
  *   @return Role The Java bean representing this element
  */
  public Role createRole(String elementName)
  {
    return (Role) createDOMElementFromComplexType("Role",elementName);
  }
  
  /**
  * Create the Java bean UserProfile for the element
  *   @param elementName The tag for the element
  *   @return UserProfile The Java bean representing this element
  */
  public UserProfile createUserProfile(String elementName)
  {
    return (UserProfile) createDOMElementFromComplexType("UserProfile",elementName);
  }
  
  /**
  * Create the Java bean Workgroup for the element
  *   @param elementName The tag for the element
  *   @return Workgroup The Java bean representing this element
  */
  public Workgroup createWorkgroup(String elementName)
  {
    return (Workgroup) createDOMElementFromComplexType("Workgroup",elementName);
  }
  
  /**
  * Create the Java bean WriteLanguage for the element
  *   @param elementName The tag for the element
  *   @return WriteLanguage The Java bean representing this element
  */
  public WriteLanguage createWriteLanguage(String elementName)
  {
    return (WriteLanguage) createDOMElementFromComplexType("WriteLanguage",elementName);
  }
  
}

