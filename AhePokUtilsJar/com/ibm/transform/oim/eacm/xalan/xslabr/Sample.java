package com.ibm.transform.oim.eacm.xalan.xslabr;

/**
* A sample program to show how to use the generated Java beans to:
*  - create and save an XML document
*  - load the XML document and print its content
*/
public class Sample
{
  ConfigFactory iConfigFactory;
  Config iConfig;
  
  public Sample()
  {
  }
  
  /**
  * Create the Java bean Option
  *   @param name The element tag name
  *   @return Option The Java bean for this element
  */
  Option createOption(String name)
  {
    Option iOption = iConfigFactory.createOption(name);
    
    return initOption(iOption);
  }
  
  /**
  * Set the children (element or attribute) values in the element that is 
  * represented by the Java bean
  *   @param Option The Java bean
  *   @return Option The Java bean
  */
  Option initOption(Option iOption)
  {
    iOption.setStyle(createStyle("Style"));
    iOption.setCode("code");
    
    return iOption;
  }
  
  /**
  * Print the element represented by the Java bean Option
  */
  void printOption(Option iOption)
  {
    printStyle(iOption.getStyle());
    System.out.println(iOption.getCode());
  }
  
  /**
  * Create the Java bean SelectionAttribute
  *   @param name The element tag name
  *   @return SelectionAttribute The Java bean for this element
  */
  SelectionAttribute createSelectionAttribute(String name)
  {
    SelectionAttribute iSelectionAttribute = iConfigFactory.createSelectionAttribute(name);
    
    return initSelectionAttribute(iSelectionAttribute);
  }
  
  /**
  * Set the children (element or attribute) values in the element that is 
  * represented by the Java bean
  *   @param SelectionAttribute The Java bean
  *   @return SelectionAttribute The Java bean
  */
  SelectionAttribute initSelectionAttribute(SelectionAttribute iSelectionAttribute)
  {
    iSelectionAttribute.setOption(0,createOption("Option"));
    iSelectionAttribute.setCode("code");
    
    return iSelectionAttribute;
  }
  
  /**
  * Print the element represented by the Java bean SelectionAttribute
  */
  void printSelectionAttribute(SelectionAttribute iSelectionAttribute)
  {
    printOption(iSelectionAttribute.getOption(0));
    System.out.println(iSelectionAttribute.getCode());
  }
  
  /**
  * Create the Java bean Attribute
  *   @param name The element tag name
  *   @return Attribute The Java bean for this element
  */
  Attribute createAttribute(String name)
  {
    Attribute iAttribute = iConfigFactory.createAttribute(name);
    
    return initAttribute(iAttribute);
  }
  
  /**
  * Set the children (element or attribute) values in the element that is 
  * represented by the Java bean
  *   @param Attribute The Java bean
  *   @return Attribute The Java bean
  */
  Attribute initAttribute(Attribute iAttribute)
  {
    iAttribute.setStyle(createStyle("Style"));
    iAttribute.setSelectionAttribute(createSelectionAttribute("SelectionAttribute"));
    iAttribute.setCode("code");
    
    return iAttribute;
  }
  
  /**
  * Print the element represented by the Java bean Attribute
  */
  void printAttribute(Attribute iAttribute)
  {
    printStyle(iAttribute.getStyle());
    printSelectionAttribute(iAttribute.getSelectionAttribute());
    System.out.println(iAttribute.getCode());
  }
  
  /**
  * Create the root element in the document
  */
  void createConfig()
  {
    iConfig.setEnterprise(0,createEnterprise("Enterprise"));
  }
  
  /**
  * Print the content of the root element
  */
  void printConfig()
  {
    printEnterprise(iConfig.getEnterprise(0));
  }
  
  /**
  * Create the Java bean Connection
  *   @param name The element tag name
  *   @return Connection The Java bean for this element
  */
  Connection createConnection(String name)
  {
    Connection iConnection = iConfigFactory.createConnection(name);
    
    return initConnection(iConnection);
  }
  
  /**
  * Set the children (element or attribute) values in the element that is 
  * represented by the Java bean
  *   @param Connection The Java bean
  *   @return Connection The Java bean
  */
  Connection initConnection(Connection iConnection)
  {
    iConnection.setUserID("UserID");
    iConnection.setPassword("Password");
    iConnection.setDatabase("Database");
    iConnection.setDatabaseURL("DatabaseURL");
    iConnection.setDatabaseDriver("DatabaseDriver");
    iConnection.setId("id");
    
    return iConnection;
  }
  
  /**
  * Print the element represented by the Java bean Connection
  */
  void printConnection(Connection iConnection)
  {
    System.out.println(iConnection.getUserID());
    System.out.println(iConnection.getPassword());
    System.out.println(iConnection.getDatabase());
    System.out.println(iConnection.getDatabaseURL());
    System.out.println(iConnection.getDatabaseDriver());
    System.out.println(iConnection.getId());
  }
  
  /**
  * Set the element DGTitle to the specified value
  *   @param iDGTitle The Java bean for this element
  *   @return DGTitle The Java bean
  */
  DGTitle initDGTitle(DGTitle iDGTitle)
  {
    iDGTitle.updateElementValue("DGTitle");
    return iDGTitle;
  }
  
  /**
  * Print the element value represented by the Java bean DGTitle
  *   @param iDGTitle The Java bean for this element
  */
  void printDGTitle(DGTitle iDGTitle)
  {
    System.out.println(iDGTitle.getElementValue());
  }
  
  /**
  * Set the element DGClass to the specified value
  *   @param iDGClass The Java bean for this element
  *   @return DGClass The Java bean
  */
  DGClass initDGClass(DGClass iDGClass)
  {
    iDGClass.updateElementValue("DGClass");
    return iDGClass;
  }
  
  /**
  * Print the element value represented by the Java bean DGClass
  *   @param iDGClass The Java bean for this element
  */
  void printDGClass(DGClass iDGClass)
  {
    System.out.println(iDGClass.getElementValue());
  }
  
  /**
  * Set the element Database to the specified value
  *   @param iDatabase The Java bean for this element
  *   @return Database The Java bean
  */
  Database initDatabase(Database iDatabase)
  {
    iDatabase.updateElementValue("Database");
    return iDatabase;
  }
  
  /**
  * Print the element value represented by the Java bean Database
  *   @param iDatabase The Java bean for this element
  */
  void printDatabase(Database iDatabase)
  {
    System.out.println(iDatabase.getElementValue());
  }
  
  /**
  * Set the element DatabaseDriver to the specified value
  *   @param iDatabaseDriver The Java bean for this element
  *   @return DatabaseDriver The Java bean
  */
  DatabaseDriver initDatabaseDriver(DatabaseDriver iDatabaseDriver)
  {
    iDatabaseDriver.updateElementValue("DatabaseDriver");
    return iDatabaseDriver;
  }
  
  /**
  * Print the element value represented by the Java bean DatabaseDriver
  *   @param iDatabaseDriver The Java bean for this element
  */
  void printDatabaseDriver(DatabaseDriver iDatabaseDriver)
  {
    System.out.println(iDatabaseDriver.getElementValue());
  }
  
  /**
  * Set the element DatabaseURL to the specified value
  *   @param iDatabaseURL The Java bean for this element
  *   @return DatabaseURL The Java bean
  */
  DatabaseURL initDatabaseURL(DatabaseURL iDatabaseURL)
  {
    iDatabaseURL.updateElementValue("DatabaseURL");
    return iDatabaseURL;
  }
  
  /**
  * Print the element value represented by the Java bean DatabaseURL
  *   @param iDatabaseURL The Java bean for this element
  */
  void printDatabaseURL(DatabaseURL iDatabaseURL)
  {
    System.out.println(iDatabaseURL.getElementValue());
  }
  
  /**
  * Create the Java bean Enterprise
  *   @param name The element tag name
  *   @return Enterprise The Java bean for this element
  */
  Enterprise createEnterprise(String name)
  {
    Enterprise iEnterprise = iConfigFactory.createEnterprise(name);
    
    return initEnterprise(iEnterprise);
  }
  
  /**
  * Set the children (element or attribute) values in the element that is 
  * represented by the Java bean
  *   @param Enterprise The Java bean
  *   @return Enterprise The Java bean
  */
  Enterprise initEnterprise(Enterprise iEnterprise)
  {
    iEnterprise.setCode("code");
    
    return iEnterprise;
  }
  
  /**
  * Print the element represented by the Java bean Enterprise
  */
  void printEnterprise(Enterprise iEnterprise)
  {
    System.out.println(iEnterprise.getCode());
  }
  
  /**
  * Create the Java bean Entity
  *   @param name The element tag name
  *   @return Entity The Java bean for this element
  */
  Entity createEntity(String name)
  {
    Entity iEntity = iConfigFactory.createEntity(name);
    
    return initEntity(iEntity);
  }
  
  /**
  * Set the children (element or attribute) values in the element that is 
  * represented by the Java bean
  *   @param Entity The Java bean
  *   @return Entity The Java bean
  */
  Entity initEntity(Entity iEntity)
  {
    iEntity.setAttribute(0,createAttribute("Attribute"));
    iEntity.setType("type");
    
    return iEntity;
  }
  
  /**
  * Print the element represented by the Java bean Entity
  */
  void printEntity(Entity iEntity)
  {
    printAttribute(iEntity.getAttribute(0));
    System.out.println(iEntity.getType());
  }
  
  /**
  * Create the Java bean Invoke
  *   @param name The element tag name
  *   @return Invoke The Java bean for this element
  */
  Invoke createInvoke(String name)
  {
    Invoke iInvoke = iConfigFactory.createInvoke(name);
    
    return initInvoke(iInvoke);
  }
  
  /**
  * Set the children (element or attribute) values in the element that is 
  * represented by the Java bean
  *   @param Invoke The Java bean
  *   @return Invoke The Java bean
  */
  Invoke initInvoke(Invoke iInvoke)
  {
    iInvoke.setObj(0,createObj("Obj"));
    iInvoke.setMethod("method");
    
    return iInvoke;
  }
  
  /**
  * Print the element represented by the Java bean Invoke
  */
  void printInvoke(Invoke iInvoke)
  {
    printObj(iInvoke.getObj(0));
    System.out.println(iInvoke.getMethod());
  }
  
  /**
  * Create the Java bean Obj
  *   @param name The element tag name
  *   @return Obj The Java bean for this element
  */
  Obj createObj(String name)
  {
    Obj iObj = iConfigFactory.createObj(name);
    
    return initObj(iObj);
  }
  
  /**
  * Set the children (element or attribute) values in the element that is 
  * represented by the Java bean
  *   @param Obj The Java bean
  *   @return Obj The Java bean
  */
  Obj initObj(Obj iObj)
  {
    iObj.setDynamicSQL(createDynamicSQL("DynamicSQL"));
    iObj.setInvoke(0,createInvoke("Invoke"));
    iObj.setExcludeReturnCode(true);
    iObj.setType("java.lang.String");
    
    return iObj;
  }
  
  /**
  * Print the element represented by the Java bean Obj
  */
  void printObj(Obj iObj)
  {
    printDynamicSQL(iObj.getDynamicSQL());
    printInvoke(iObj.getInvoke(0));
    System.out.println(iObj.getExcludeReturnCode());
    System.out.println(iObj.getType());
  }
  
  /**
  * Set the element Password to the specified value
  *   @param iPassword The Java bean for this element
  *   @return Password The Java bean
  */
  Password initPassword(Password iPassword)
  {
    iPassword.updateElementValue("Password");
    return iPassword;
  }
  
  /**
  * Print the element value represented by the Java bean Password
  *   @param iPassword The Java bean for this element
  */
  void printPassword(Password iPassword)
  {
    System.out.println(iPassword.getElementValue());
  }
  
  /**
  * Create the Java bean Style
  *   @param name The element tag name
  *   @return Style The Java bean for this element
  */
  Style createStyle(String name)
  {
    Style iStyle = iConfigFactory.createStyle(name);
    
    return initStyle(iStyle);
  }
  
  /**
  * Set the children (element or attribute) values in the element that is 
  * represented by the Java bean
  *   @param Style The Java bean
  *   @return Style The Java bean
  */
  Style initStyle(Style iStyle)
  {
    iStyle.setXSLParam(0,createXSLParam("XSLParam"));
    iStyle.setXsl("xsl");
    iStyle.setZip(false);
    
    return iStyle;
  }
  
  /**
  * Print the element represented by the Java bean Style
  */
  void printStyle(Style iStyle)
  {
    printXSLParam(iStyle.getXSLParam(0));
    System.out.println(iStyle.getXsl());
    System.out.println(iStyle.getZip());
  }
  
  /**
  * Set the element UserID to the specified value
  *   @param iUserID The Java bean for this element
  *   @return UserID The Java bean
  */
  UserID initUserID(UserID iUserID)
  {
    iUserID.updateElementValue("UserID");
    return iUserID;
  }
  
  /**
  * Print the element value represented by the Java bean UserID
  *   @param iUserID The Java bean for this element
  */
  void printUserID(UserID iUserID)
  {
    System.out.println(iUserID.getElementValue());
  }
  
  /**
  * Set the element UsesConnection to the specified value
  *   @param iUsesConnection The Java bean for this element
  *   @return UsesConnection The Java bean
  */
  UsesConnection initUsesConnection(UsesConnection iUsesConnection)
  {
    iUsesConnection.updateElementValue("UsesConnection");
    return iUsesConnection;
  }
  
  /**
  * Print the element value represented by the Java bean UsesConnection
  *   @param iUsesConnection The Java bean for this element
  */
  void printUsesConnection(UsesConnection iUsesConnection)
  {
    System.out.println(iUsesConnection.getElementValue());
  }
  
  /**
  * Create the Java bean XSLParam
  *   @param name The element tag name
  *   @return XSLParam The Java bean for this element
  */
  XSLParam createXSLParam(String name)
  {
    XSLParam iXSLParam = iConfigFactory.createXSLParam(name);
    
    return initXSLParam(iXSLParam);
  }
  
  /**
  * Set the children (element or attribute) values in the element that is 
  * represented by the Java bean
  *   @param XSLParam The Java bean
  *   @return XSLParam The Java bean
  */
  XSLParam initXSLParam(XSLParam iXSLParam)
  {
    iXSLParam.setObj(0,createObj("Obj"));
    iXSLParam.setName("name");
    
    return iXSLParam;
  }
  
  /**
  * Print the element represented by the Java bean XSLParam
  */
  void printXSLParam(XSLParam iXSLParam)
  {
    printObj(iXSLParam.getObj(0));
    System.out.println(iXSLParam.getName());
  }
  
  /**
  * Create the Java bean InsertAttribute
  *   @param name The element tag name
  *   @return InsertAttribute The Java bean for this element
  */
  InsertAttribute createInsertAttribute(String name)
  {
    InsertAttribute iInsertAttribute = iConfigFactory.createInsertAttribute(name);
    
    return initInsertAttribute(iInsertAttribute);
  }
  
  /**
  * Set the children (element or attribute) values in the element that is 
  * represented by the Java bean
  *   @param InsertAttribute The Java bean
  *   @return InsertAttribute The Java bean
  */
  InsertAttribute initInsertAttribute(InsertAttribute iInsertAttribute)
  {
    iInsertAttribute.setCode("code");
    iInsertAttribute.setUseFlagCode(false);
    
    return iInsertAttribute;
  }
  
  /**
  * Print the element represented by the Java bean InsertAttribute
  */
  void printInsertAttribute(InsertAttribute iInsertAttribute)
  {
    System.out.println(iInsertAttribute.getCode());
    System.out.println(iInsertAttribute.getUseFlagCode());
  }
  
  /**
  * Set the element BaseSQL to the specified value
  *   @param iBaseSQL The Java bean for this element
  *   @return BaseSQL The Java bean
  */
  BaseSQL initBaseSQL(BaseSQL iBaseSQL)
  {
    iBaseSQL.updateElementValue("BaseSQL");
    return iBaseSQL;
  }
  
  /**
  * Print the element value represented by the Java bean BaseSQL
  *   @param iBaseSQL The Java bean for this element
  */
  void printBaseSQL(BaseSQL iBaseSQL)
  {
    System.out.println(iBaseSQL.getElementValue());
  }
  
  /**
  * Create the Java bean DynamicSQL
  *   @param name The element tag name
  *   @return DynamicSQL The Java bean for this element
  */
  DynamicSQL createDynamicSQL(String name)
  {
    DynamicSQL iDynamicSQL = iConfigFactory.createDynamicSQL(name);
    
    return initDynamicSQL(iDynamicSQL);
  }
  
  /**
  * Set the children (element or attribute) values in the element that is 
  * represented by the Java bean
  *   @param DynamicSQL The Java bean
  *   @return DynamicSQL The Java bean
  */
  DynamicSQL initDynamicSQL(DynamicSQL iDynamicSQL)
  {
    iDynamicSQL.setBaseSQL("BaseSQL");
    iDynamicSQL.setInsertAttribute(0,createInsertAttribute("InsertAttribute"));
    
    return iDynamicSQL;
  }
  
  /**
  * Print the element represented by the Java bean DynamicSQL
  */
  void printDynamicSQL(DynamicSQL iDynamicSQL)
  {
    System.out.println(iDynamicSQL.getBaseSQL());
    printInsertAttribute(iDynamicSQL.getInsertAttribute(0));
  }
  
  /**
  * Create a new XML document using the generated ConfigFactory class
  *   @param filename The XML file name
  */
  void createNewInstance(String filename)
  {
    iConfigFactory = new ConfigFactory();
    iConfigFactory.setPackageName("com.ibm.transform.oim.eacm.xalan.xslabr");
    
    // include schemaLocation hint for validation
    iConfigFactory.setXSDFileName("XSLReportABRConfiguration.xsd");
    iConfigFactory.setNamespaceURI("http://w3.ibm.com/eacm/XSLReportABRConfiguration");
    
    // encoding for output document
    iConfigFactory.setEncoding("UTF8");
    
    // encoding tag for xml declaration
    iConfigFactory.setEncodingTag("UTF-8");
    
    // Create the root element in the document using the specified root element name
    iConfig = (Config) iConfigFactory.createRoot("x:Config");
    createConfig();
    
    iConfigFactory.save(filename);
  }
  
  /**
  * Load an XML document using the generated ConfigFactory class
  *   @param filename An existing XML file name
  */
  void loadExistingInstance(String filename)
  {
    iConfigFactory = new ConfigFactory();
    iConfigFactory.setPackageName("com.ibm.transform.oim.eacm.xalan.xslabr");
    
    // Load the document
    iConfig = (Config) iConfigFactory.loadDocument(filename);
    printConfig();
  }
  
  /**
  * The main program.
  * Creates an example XML document and then loads it
  */
  public static void main(String args[])
  {
    Sample sample = new Sample();
    sample.createNewInstance("C:/Documents and Settings/Administrator/My Documents/IBM/wsappdevie51/workspace/AhePokUtilsJar/XSLReportABRConfigurationSample.xml");
    sample.loadExistingInstance("C:/Documents and Settings/Administrator/My Documents/IBM/wsappdevie51/workspace/AhePokUtilsJar/XSLReportABRConfigurationSample.xml");
  }
}

