
package COM.ibm.eannounce.abr.util;
import java.io.FileInputStream;
import java.util.Properties;


import COM.ibm.opicmpdh.middleware.taskmaster.Log;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 */

public final class RfcConfigProperties extends Properties {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Class constants
	private static final String PROPERTIES_FILENAME = "rfc.properties";
	// Class variables
	private static Properties c_prop = null;

	
	/**
	 * Some class level initialization
	 */
	static {
		// Load the properties from file so they are available for each accessor method
		reloadProperties();
	}

	/**
	 * Don't let anyone instantiate this class.
	 */
	private RfcConfigProperties() {
	}
	/**
	 * Load the TaskMaster properties from the taskmaster.properties file
	 */
	private static final void loadProperties() {

		FileInputStream inProperties = null;
		try {
			if (c_prop == null) {
				c_prop = new Properties();

				inProperties =
					new FileInputStream(PROPERTIES_FILENAME);

				c_prop.load(inProperties);
			}
		} catch (Exception x) {
			
			Log.out(
				"Unable to loadProperties for "
					+ PROPERTIES_FILENAME
					+ " "
					+ x);
		} finally {
			if (inProperties != null) {
				try {
					inProperties.close();
				} catch (java.io.IOException ex){
					ex.printStackTrace();
				}
			}
		}
	}
	/**
	 * Reload the properties from the middleware.server.properties file
	 */
	public static final void reloadProperties() {
		loadProperties();
	}
	
	 /* String uri = ConfigUtils.getProperty("rdh.service.uri");
      String trustStore = ConfigUtils.getProperty("rdh.service.truststore");
      String trustStorepw = ConfigUtils.getProperty("rdh.service.truststore.password");
      String keyStore = ConfigUtils.getProperty("rdh.service.keystore");
      String keyStorepw = ConfigUtils.getProperty("rdh.service.keystore.password");

      String keyStoreType = ConfigUtils.getProperty("rdh.keystore.type");
      String algorithm = ConfigUtils.getProperty("rdh.ssl.algorithm");*/
	/**
	 * Return the DB gate delay
	 *
	 * @return long
	 */
	public static final String getServiceURI() {
		reloadProperties();

		return 
			c_prop.getProperty("rdh.service.uri", "");
	}
	/**
	 * Return
	 *
	 * @return int
	 */
	public static final String getServiceTruststore() {
		reloadProperties();
		return 
			c_prop.getProperty("rdh.service.truststore", "");
	}
	public static final String getServiceTruststorePassword() {
		reloadProperties();
		return 
			c_prop.getProperty("rdh.service.truststore.password", "");
	}
	
	public static final String getServiceKeystore() {
		reloadProperties();
		return 
			c_prop.getProperty("rdh.service.keystore", "");
	}
	public static final String getServiceKeystorePassword() {
		reloadProperties();
		return 
			c_prop.getProperty("rdh.service.keystore.password", "");
	}
	public static final String getServiceKeystoreType() {
		reloadProperties();
		return 
			c_prop.getProperty("rdh.keystore.type", "");
	}
	public static final String getSSLAlgorithm() {
		reloadProperties();
		return 
			c_prop.getProperty("rdh.ssl.algorithm", "");
	}
	public static final String getZdmstatus() {
		reloadProperties();
		return 
			c_prop.getProperty("rdh.zdmstatus", "");
	}
	public static  String getPropertys(String key){
		reloadProperties();
		return 
			c_prop.getProperty(key, "");
	}
	public static  String getCountry(String key){
		reloadProperties();
		return 
			c_prop.getProperty("COUNTRY."+key, null);
	}
	public static  String getZsabrtaxPropertys(String key){
		reloadProperties();
		return 
			c_prop.getProperty("ZSABRTAX."+key, null);
	}
	/**
	 * Return the date/time this class was generated
	 * @return the date/time this class was generated
	 */
	public static final String getVersion() {
		return "$Id: TaskMasterProperties.java,v 1.29 2013/09/11 18:40:37 wendy Exp $";
	}
}
