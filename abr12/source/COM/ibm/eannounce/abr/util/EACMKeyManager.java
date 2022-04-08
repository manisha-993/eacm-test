/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.util;

import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;

/**
 * A class to load KeyStore and then generated KeyManagers
 * 
 * @author Li Chao Ji (jilichao@cn.ibm.com)
 * @since 08/11/2016
 */
public class EACMKeyManager
{
    private KeyStore ks;
    private String password;

    /**
     * 
     * @param keyStore The keystore file location
     * @param password The keystore file password
     * @throws Exception
     */
    public EACMKeyManager(String keyStore, String password) throws Exception
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
    public EACMKeyManager(String keyStoreType, String keyStore, String password) throws Exception
    {
        this.password = password;
        this.ks = KeyStoreLoader.loadKeyStore(keyStoreType, keyStore, password);
    }

    /**
     * Initial KeyManagerFactory and then return KeyManagers
     * 
     * @return KeyManagers
     * @throws Exception
     */
    public KeyManager[] getKeyManagers() throws Exception
    {
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, password.toCharArray());
        return kmf.getKeyManagers();
    }
}