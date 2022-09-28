/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import com.ibm.eacm.AES256Utils;

import COM.ibm.opicmpdh.middleware.D;



/**
 * A Utility class to invoke RDH RFC RESTful Web Service
 * 
 */
public class EACMWebServiceUtil
{
    
    // private static Logger log = Logger.getInstance(RdhWebServiceUtil.class);
    private static SSLSocketFactory sslSocketFactory = null;

    /***
     * Invoke RDH RESTful Web Service
     * 
     * @param input Json format data
     * @param serviceName RFC Web Service name
     * @return result string with Json format
     * @throws IOException
     * @throws Exception
     */
    public static String callService(String input, String serviceName) throws Exception
    {
        HttpURLConnection connection = null;
        OutputStream os = null;
        StringBuffer output = new StringBuffer();

        String uri = RfcConfigProperties.getServiceURI();
        String trustStore = RfcConfigProperties.getServiceTruststore();
        String trustStorepw =AES256Utils.decrypt(RfcConfigProperties.getServiceTruststorePassword());
        String keyStore = RfcConfigProperties.getServiceKeystore();
        String keyStorepw = AES256Utils.decrypt(RfcConfigProperties.getServiceKeystorePassword());

        String keyStoreType = RfcConfigProperties.getServiceKeystoreType();
        String algorithm = RfcConfigProperties.getSSLAlgorithm();
        
        D.ebug("service.uri = " + uri);
       // lo(debugSb, "service.uri = " + uri);
        D.ebug( "service.truststore = " + trustStore);
        D.ebug("service.keystore = " + keyStore);
        

        if (sslSocketFactory == null)
        {
            EACMKeyManager keyManager = null;
            EACMTrustManager trustManager = null;
            EACMSSLContext context = null;
            try
            {
                keyManager = new EACMKeyManager(keyStoreType.toUpperCase(), keyStore, keyStorepw);
                trustManager = new EACMTrustManager(keyStoreType.toUpperCase(), trustStore, trustStorepw);
                context = new EACMSSLContext(algorithm.toUpperCase(), keyManager, trustManager);
                sslSocketFactory = context.getSSLContext().getSocketFactory();
            } catch (Exception e)
            {
            	  
              //  log.error("Failed to load certification information: ", e);
                throw e;
            }
        }

        long start = System.currentTimeMillis();
        try
        {
           // log.debug("WebService = " + uri + serviceName);

            URL url = new URL(uri + serviceName);
            connection = (HttpURLConnection) url.openConnection();

            if (connection instanceof HttpsURLConnection)
            {
                ((HttpsURLConnection) connection).setSSLSocketFactory(sslSocketFactory);
            }

            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            os = connection.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
            {
            	D.ebug(D.EBUG_ERR,"Invoke WebService " + serviceName + " Failed: HTTP error code = "
                        + connection.getResponseCode() + " Http response message = "
                        + connection.getResponseMessage());
                throw new RuntimeException("Invoke WebService " + serviceName + " Failed: HTTP error code = "
                        + connection.getResponseCode() + " Http response message = "
                        + connection.getResponseMessage());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

            String data;
            while ((data = br.readLine()) != null)
            {
                output.append(data);
            }

           D.ebug("WebService: " + serviceName + " Input data: " + input + " Output data: "
                    + output.toString());
        } catch (IllegalArgumentException e)
        {
        	D.ebug(D.EBUG_ERR,"Failed to load certification information: "+e.getStackTrace());
            throw e;
        } finally
        {
            if (os != null)
            {
                os.close();
            }
            if (connection != null)
            {
                connection.disconnect();
            }
        }
        D.ebug(serviceName + " return : " + output.toString());
        
        D.ebug(serviceName + " take time: " + (System.currentTimeMillis() - start));

        return output.toString();
    }
}
