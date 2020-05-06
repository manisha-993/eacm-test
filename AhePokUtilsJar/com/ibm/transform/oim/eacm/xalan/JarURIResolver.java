/*
 * Created on Jan 13, 2005
 *
 * Licensed Materials -- Property of IBM
 *
 * (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
 * The source code for this program is not published or otherwise divested of
 * its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
 */
package com.ibm.transform.oim.eacm.xalan;

import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

/**
 * An implementation of URIResolver that can find files in jar files on the classpath.
 *
 * <pre>
 * $Log: JarURIResolver.java,v $
 * Revision 1.3  2006/10/19 21:29:42  chris
 * Interface changes
 *
 * Revision 1.2  2006/01/26 15:28:14  wendy
 * AHE copyright
 *
 * Revision 1.1  2005/09/08 19:09:29  wendy
 * New pkg
 *
 * Revision 1.1  2005/02/23 21:13:03  chris
 * Initial XSL Report ABR Code
 *
 * </pre>
 *
 * @author cstolpe
 */
public class JarURIResolver implements URIResolver {

    /**
     * Looks for href in jar files on the class path. uriBase is ignored
     * e.g. /com/ibm/transform/oim/eacm/xalan/style/eacustom.xsl
     *
     * @see javax.xml.transform.URIResolver#resolve(java.lang.String, java.lang.String)
     */
    public Source resolve(String href, String uriBase)
        throws TransformerException
    {
    	Source result = null;
        InputStream inputStream = getInputStream(href);
        if (inputStream != null) {
        	result = new StreamSource(inputStream);
        }
        else {
			System.err.println("JarURIResolver.resolve(String,String): inputStream is null");  //$NON-NLS-1$
        }
        return result;
    }

    /**
     * Locates href files on lcasspath or in jar files on classpath.
     * @param href
     * @return InputStream
     */
    public InputStream getInputStream(String href) {
        InputStream inputStream = null;
        if (href == null) {
            System.err.println("JarURIResolver.getInputStream(String): href is null");  //$NON-NLS-1$
        }
        else {
            try {
                URL url = this.getClass().getResource(href); // need full path
                if(url == null) {
                    MessageFormat mf = new MessageFormat("JarURIResolver.getInputStream(String): url is null for {0}");  //$NON-NLS-1$
                    String[] mfArgs = new String[1];
                    mfArgs[0] = href;
                    System.err.println(mf.format(mfArgs));
                }
                else if (url.getProtocol().equals("jar")) {  //$NON-NLS-1$
                    JarURLConnection conn = (JarURLConnection)url.openConnection();
                    JarFile jarfile;
                    JarEntry jarEntry;
                    if (conn == null) {
                        System.err.println("JarURIResolver.getInputStream(String): conn is null");  //$NON-NLS-1$
                    }
                    jarfile = conn.getJarFile();
                    if (jarfile == null) {
                        System.err.println("JarURIResolver.getInputStream(String): jarfile is null");  //$NON-NLS-1$
                    }
                    jarEntry = conn.getJarEntry();
                    if (jarEntry == null) {
                        System.err.println("JarURIResolver.getInputStream(String): jarentry is null");  //$NON-NLS-1$
                    }
                    inputStream = jarfile.getInputStream(jarEntry);
                }
                else if (url.getProtocol().equals("file")){  //$NON-NLS-1$
                    inputStream = url.openStream();
                }
            } catch (IOException e) {
                System.err.print("JarURIResolver.getInputStream(String): Exception "); //$NON-NLS-1$
                System.err.println(e.getMessage());
            }
        }
        return inputStream;
    }
}
