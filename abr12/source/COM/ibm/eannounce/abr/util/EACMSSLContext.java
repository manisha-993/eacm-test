/* Copyright IBM Corp. 2016 */
package COM.ibm.eannounce.abr.util;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

/**
 * A utility class to load KeyStore from the given location.
 * 
 * @author Li Chao Ji (jilichao@cn.ibm.com)
 * @since 08/11/2016
 */
public class EACMSSLContext
{
    private String algorithm;
    private EACMKeyManager keyManager;
    private EACMTrustManager trustManager;

    /**
     * @param protocol The Algorithm parameter passed to the SSLContext for use
     * on the connection
     * @param keyManager SdpiKeyManager
     * @param trustManager SdpiTrustManager
     */
    public EACMSSLContext(String algorithm, EACMKeyManager keyManager, EACMTrustManager trustManager)
    {
        this.algorithm = algorithm;
        this.keyManager = keyManager;
        this.trustManager = trustManager;
    }

    /**
     * Generate an instance of SSLContext according to the given Algorithms
     * 
     * @return
     * @throws Exception
     */
    public SSLContext getSSLContext() throws Exception
    {
        SSLContext context = SSLContext.getInstance(algorithm);
        context.init(getKeyManagers(), getTrustManagers(), null);
        return context;
    }

    /**
     * @return KeyManagers
     * @throws Exception
     */
    private KeyManager[] getKeyManagers() throws Exception
    {
        if (keyManager == null)
        {
            return null;
        }
        return keyManager.getKeyManagers();
    }

    /**
     * @return TrustManagers
     * @throws Exception
     */
    private TrustManager[] getTrustManagers() throws Exception
    {
        if (trustManager == null)
        {
            return null;
        }
        return trustManager.getTrustManagers();
    }
}
