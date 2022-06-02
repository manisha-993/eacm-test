/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * A utility class to load KeyStore from the given location.
 * 
 * @author Li Chao Ji (jilichao@cn.ibm.com)
 * @since 08/11/2016
 */
public class KeyStoreLoader
{
    /***
     * 
     * @param keyStoreType The types can be jceks, jks, pkcs12
     * @param keyStore The keystore file location
     * @param password A password is used to unlock the keystore
     * @return
     * @throws Exception
     */
    public static KeyStore loadKeyStore(String keyStoreType, String keyStore, String password) throws Exception
    {
        if (keyStoreType == null || keyStoreType.trim().isEmpty())
        {
            keyStoreType = "JKS";
        }

        InputStream in = null;
        try
        {
            KeyStore ks = KeyStore.getInstance(keyStoreType);
            in = new FileInputStream(keyStore);
            ks.load(in, password.toCharArray());
            return ks;
        } finally
        {
            if (in != null)
            {
                in.close();
            }
        }
    }
}
