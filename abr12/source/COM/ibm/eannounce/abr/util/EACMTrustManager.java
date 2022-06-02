/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.util;

import java.security.KeyStore;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * A class to load KeyStore and then generated KeyManagers
 * 
 * @author Li Chao Ji (jilichao@cn.ibm.com)
 * @since 08/11/2016
 */
public class EACMTrustManager
{
    private KeyStore ks;

    /**
     * 
     * @param keyStore The keystore file location
     * @param password The keystore file password
     * @throws Exception
     */
    public EACMTrustManager(String keyStore, String password) throws Exception
    {
        this("JKS", keyStore, password);
    }

    /**
     * 
     * @param keyStoreType The KeyStore type
     * @param keyStore The keystore file location
     * @param password The keystore file password
     * @throws Exception
     */
    public EACMTrustManager(String keyStoreType, String keyStore, String password) throws Exception
    {
        this.ks = KeyStoreLoader.loadKeyStore(keyStoreType, keyStore, password);
    }

    /**
     * Initial TrustManagerFactory and then return TrustManagers
     * 
     * @return KeyManagers
     * @throws Exception
     */
    public TrustManager[] getTrustManagers() throws Exception
    {
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);
        return tmf.getTrustManagers();
    }
}
