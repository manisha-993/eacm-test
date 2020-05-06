package com.ibm.transform.oim.eacm.xalan.xslabr;

import com.ibm.etools.xmlschema.beans.*;

/**
* Provides convenience methods for creating Java beans for elements
* in this XML document
*/
public class ConfigFactory extends Factory
{
	private static final long serialVersionUID = 1L;
  public ConfigFactory()
  {
    super();
  }
  
  /**
  * Create the Java bean Config for the root element
  *   @param rootElementname The tag for the root element
  *   @return Config The Java bean representing this element
  */
  public Config createRoot(String rootElementname)
  {
    return (Config) createRootDOMFromComplexType("Config",rootElementname);
  }
  
  /**
  * Create the Java bean Config by loading the XML file
  *   @param filename The XML file name
  *   @return Config The Java bean representing the root element
  */
  public Config loadDocument(String filename)
  {
    return (Config) loadDocument("Config",filename);
  }
  
  /**
  * Create the Java bean Option for the element
  *   @param elementName The tag for the element
  *   @return Option The Java bean representing this element
  */
  public Option createOption(String elementName)
  {
    return (Option) createDOMElementFromComplexType("Option",elementName);
  }
  
  /**
  * Create the Java bean SelectionAttribute for the element
  *   @param elementName The tag for the element
  *   @return SelectionAttribute The Java bean representing this element
  */
  public SelectionAttribute createSelectionAttribute(String elementName)
  {
    return (SelectionAttribute) createDOMElementFromComplexType("SelectionAttribute",elementName);
  }
  
  /**
  * Create the Java bean Attribute for the element
  *   @param elementName The tag for the element
  *   @return Attribute The Java bean representing this element
  */
  public Attribute createAttribute(String elementName)
  {
    return (Attribute) createDOMElementFromComplexType("Attribute",elementName);
  }
  
  /**
  * Create the Java bean Config for the element
  *   @param elementName The tag for the element
  *   @return Config The Java bean representing this element
  */
  public Config createConfig(String elementName)
  {
    return (Config) createDOMElementFromComplexType("Config",elementName);
  }
  
  /**
  * Create the Java bean Connection for the element
  *   @param elementName The tag for the element
  *   @return Connection The Java bean representing this element
  */
  public Connection createConnection(String elementName)
  {
    return (Connection) createDOMElementFromComplexType("Connection",elementName);
  }
  
  /**
  * Create the Java bean DGTitle for the element
  *   @param elementName The tag for the element
  *   @return DGTitle The Java bean representing this element
  */
  public DGTitle createDGTitle(String elementName)
  {
    return (DGTitle) createDOMElementFromSimpleType("DGTitle",elementName);
  }
  
  /**
  * Create the Java bean DGClass for the element
  *   @param elementName The tag for the element
  *   @return DGClass The Java bean representing this element
  */
  public DGClass createDGClass(String elementName)
  {
    return (DGClass) createDOMElementFromSimpleType("DGClass",elementName);
  }
  
  /**
  * Create the Java bean Database for the element
  *   @param elementName The tag for the element
  *   @return Database The Java bean representing this element
  */
  public Database createDatabase(String elementName)
  {
    return (Database) createDOMElementFromSimpleType("Database",elementName);
  }
  
  /**
  * Create the Java bean DatabaseDriver for the element
  *   @param elementName The tag for the element
  *   @return DatabaseDriver The Java bean representing this element
  */
  public DatabaseDriver createDatabaseDriver(String elementName)
  {
    return (DatabaseDriver) createDOMElementFromSimpleType("DatabaseDriver",elementName);
  }
  
  /**
  * Create the Java bean DatabaseURL for the element
  *   @param elementName The tag for the element
  *   @return DatabaseURL The Java bean representing this element
  */
  public DatabaseURL createDatabaseURL(String elementName)
  {
    return (DatabaseURL) createDOMElementFromSimpleType("DatabaseURL",elementName);
  }
  
  /**
  * Create the Java bean Enterprise for the element
  *   @param elementName The tag for the element
  *   @return Enterprise The Java bean representing this element
  */
  public Enterprise createEnterprise(String elementName)
  {
    return (Enterprise) createDOMElementFromComplexType("Enterprise",elementName);
  }
  
  /**
  * Create the Java bean Entity for the element
  *   @param elementName The tag for the element
  *   @return Entity The Java bean representing this element
  */
  public Entity createEntity(String elementName)
  {
    return (Entity) createDOMElementFromComplexType("Entity",elementName);
  }
  
  /**
  * Create the Java bean Invoke for the element
  *   @param elementName The tag for the element
  *   @return Invoke The Java bean representing this element
  */
  public Invoke createInvoke(String elementName)
  {
    return (Invoke) createDOMElementFromComplexType("Invoke",elementName);
  }
  
  /**
  * Create the Java bean Obj for the element
  *   @param elementName The tag for the element
  *   @return Obj The Java bean representing this element
  */
  public Obj createObj(String elementName)
  {
    return (Obj) createDOMElementFromComplexType("Obj",elementName);
  }
  
  /**
  * Create the Java bean Password for the element
  *   @param elementName The tag for the element
  *   @return Password The Java bean representing this element
  */
  public Password createPassword(String elementName)
  {
    return (Password) createDOMElementFromSimpleType("Password",elementName);
  }
  
  /**
  * Create the Java bean Style for the element
  *   @param elementName The tag for the element
  *   @return Style The Java bean representing this element
  */
  public Style createStyle(String elementName)
  {
    return (Style) createDOMElementFromComplexType("Style",elementName);
  }
  
  /**
  * Create the Java bean UserID for the element
  *   @param elementName The tag for the element
  *   @return UserID The Java bean representing this element
  */
  public UserID createUserID(String elementName)
  {
    return (UserID) createDOMElementFromSimpleType("UserID",elementName);
  }
  
  /**
  * Create the Java bean UsesConnection for the element
  *   @param elementName The tag for the element
  *   @return UsesConnection The Java bean representing this element
  */
  public UsesConnection createUsesConnection(String elementName)
  {
    return (UsesConnection) createDOMElementFromSimpleType("UsesConnection",elementName);
  }
  
  /**
  * Create the Java bean XSLParam for the element
  *   @param elementName The tag for the element
  *   @return XSLParam The Java bean representing this element
  */
  public XSLParam createXSLParam(String elementName)
  {
    return (XSLParam) createDOMElementFromComplexType("XSLParam",elementName);
  }
  
  /**
  * Create the Java bean InsertAttribute for the element
  *   @param elementName The tag for the element
  *   @return InsertAttribute The Java bean representing this element
  */
  public InsertAttribute createInsertAttribute(String elementName)
  {
    return (InsertAttribute) createDOMElementFromComplexType("InsertAttribute",elementName);
  }
  
  /**
  * Create the Java bean BaseSQL for the element
  *   @param elementName The tag for the element
  *   @return BaseSQL The Java bean representing this element
  */
  public BaseSQL createBaseSQL(String elementName)
  {
    return (BaseSQL) createDOMElementFromSimpleType("BaseSQL",elementName);
  }
  
  /**
  * Create the Java bean DynamicSQL for the element
  *   @param elementName The tag for the element
  *   @return DynamicSQL The Java bean representing this element
  */
  public DynamicSQL createDynamicSQL(String elementName)
  {
    return (DynamicSQL) createDOMElementFromComplexType("DynamicSQL",elementName);
  }
  
}

