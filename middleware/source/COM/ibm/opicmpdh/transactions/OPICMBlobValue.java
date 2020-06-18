//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: OPICMBlobValue.java,v $
// Revision 1.13  2008/01/31 21:29:04  wendy
// Cleanup RSA warnings
//
// Revision 1.12  2005/03/11 22:42:54  dave
// removing some auto genned stuff
//
// Revision 1.11  2005/01/27 04:02:36  dave
// removing automated readObject from Jtest
//
// Revision 1.10  2005/01/26 23:20:02  dave
// Jtest clean up
//
// Revision 1.9  2005/01/26 20:45:36  dave
// Jtest cleanup effort
//
// Revision 1.8  2001/08/22 16:53:14  roger
// Removed author RM
//
// Revision 1.7  2001/04/03 21:09:58  dave
// More diplay in OPICMFlagValue
//
// Revision 1.6  2001/04/03 20:42:14  dave
// Trace statements for the gernertateUpdate.. in PDHBlobAttribute
//
// Revision 1.5  2001/04/03 06:30:52  dave
// adding function to Blob Value and PDHGroup to handle Client Blob needs
//
// Revision 1.4  2001/04/03 06:19:56  dave
// added file manipulate to OPICMBlobValue
//
// Revision 1.3  2001/03/21 16:57:34  dave
// Added comments and methods to comform closer to OPICMTextValue
//
// Revision 1.2  2001/03/21 00:01:13  roger
// Implement java class file branding in getVersion method
//
// Revision 1.1  2001/03/20 06:33:54  dave
// New method to house basic byte arrays as addtribes of an PDHItem.
//
// Revision 1.2  2001/03/16 16:08:59  roger
// Added Log keyword, and standard copyright
//


package COM.ibm.opicmpdh.transactions;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.text.SimpleDateFormat;



/**
 * This object holds a PDH Blob value for a given NLSID.  This object is usually managed in an array
 * of items that vary by NLSID
 *
 * @author  David Bigelow
 * @version @date
*/

public class OPICMBlobValue extends Object implements Serializable, Cloneable {

    /**
    * @serial
    */
    static final long serialVersionUID = 230L;

    // Class variables
    private static final SimpleDateFormat SDTIMESTAMP =
        new SimpleDateFormat("yyyyMMddHHmmssSSS");

    private int m_iNLSID = 0;
    private String m_strBlobExtension = null;
    private byte[] m_abBlobValue = null;
    private String m_strFileName =
        SDTIMESTAMP.format(new Date()) + ".blob";
    transient private FileInputStream m_x = null;
    transient private FileOutputStream fosBlob = null;

    // Blob m_blobValue = null;


    /**
     * Main method which performs a simple test of this class
     *
     * @param arg
     */
    public static void main(String arg[]) {
    }

    /**
     * Creates the Value for the given NLSID.
     *
     * @param _i1
     * @param _s1
     * @param _ab1
     */
    public OPICMBlobValue(int _i1, String _s1, byte[] _ab1) {
        super();
        // Set from parms
        this.m_iNLSID = _i1;
        this.m_strBlobExtension = _s1;
        this.m_abBlobValue = _ab1;
    }

    /** Return thet setBlobExtension
    * @return the value of this object
    */
    public String getBlobExtension() {
        return m_strBlobExtension;
    }

    /**
     * sets the value of this Object's Blob Extension
     *
     * @param _s1
     */
    public void setBlobExtension(String _s1) {
        m_strBlobExtension = _s1;
    }

    /**
     * sets the value of this Blob's byte array
     *
     * @param _ab
     */
    public void setBlobValue(byte[] _ab) {
        m_abBlobValue = _ab;
    }

    /**
     * gets the value of this Blob's byte array
     *
     * @return byte[]
     */
    public byte[] getBlobValue() {
        return m_abBlobValue;
    }

    /**
     * getNLSID returns the NLSID of this object.
     * @return int
     */
    public int getNLSID() {
        return m_iNLSID;
    }

    /**
     * Display the object values for testing and debuging.
     *
     * @param out
     */
    public void display(java.io.PrintStream out) {
        out.print(this.m_iNLSID);
        out.print("BE:" + m_strBlobExtension);
        if (m_abBlobValue == null) {
            out.print("BV IS NULL");

        } else {
            out.println(":BVL:" + this.m_abBlobValue.length);
        }
        out.println(".");
    }

    /**
     * the date/time this class was generated
     * @return String
     */
    public String getVersion() {
        return "$Id: OPICMBlobValue.java,v 1.13 2008/01/31 21:29:04 wendy Exp $";
    }

    /**
     * load from File
     * @param strFileName
     * @throws java.io.IOException
     * @author Dave
     */
    public final void loadFromFile(String strFileName) throws IOException {

        // What happens if we leave a file open?
        FileInputStream fisBlob = null;
        int iSize = 0;

        try {

            fisBlob = new FileInputStream(strFileName);
            iSize = fisBlob.available();
            m_abBlobValue = new byte[iSize];
            fisBlob.read(m_abBlobValue);

        } finally {
            if (fisBlob != null) {
                fisBlob.close();
            }
            fisBlob = null;
        }

    }

    /**
     * Load the <code>Blob</code> from a <code>URL</code> (has trouble through MS proxy)
     * FTP is problematic due to Sun's use of the RETR command instead of GET command
     *
     * @exception IOException
     * @param strURL
     */
    public final void loadFromURL(URL strURL) throws IOException {

        // Try to read up to 32KB
        int READ_SIZE = 32 * 1024;

        // Data read in at max size
        byte[] baRead = new byte[READ_SIZE];

        // Stores the chunks
        Hashtable hashData = new Hashtable();

        // Counters
        int iBytesRead = 0;
        int iTotalBytes = 0;
        int iChunks = 0;

        int iDestOffset = 0;

        // Byte array of data size is actual amount read
        byte[] baChunk = null;

        // Open a stream to the URL
        InputStream isBlob = strURL.openStream();

        // Grab all the available data in chunks
        while (iBytesRead != -1) {
            // Try to read max we can store
            iBytesRead = isBlob.read(baRead, 0, baRead.length);
            // If we read something, store it as a chunk of the appropriate size in hashtable entry using chunk # as key
            if (iBytesRead > 0) {
                baChunk = new byte[iBytesRead];
                System.arraycopy(baRead, 0, baChunk, 0, baChunk.length);
                iTotalBytes += iBytesRead;
                hashData.put(new Integer(iChunks++), baChunk);
            }
        }

        // Close the stream
        isBlob.close();

        // Build a single byte array from all the chunks
        m_abBlobValue = new byte[iTotalBytes];


        for (int i = 0; i < iChunks; i++) {

            // Get next chunk using chunk # as key
            baChunk = (byte[]) hashData.get(new Integer(i));

            System.arraycopy(
                baChunk,
                0,
                m_abBlobValue,
                iDestOffset,
                baChunk.length);

            iDestOffset += baChunk.length;
        }
    }

    /**
     * Save the <code>Blob</code> to a file (yes, UNC works)
     *
     * @exception IOException
     * @param strFileName
     */
    public final void saveToFile(String strFileName) throws IOException {
        fosBlob = new FileOutputStream(strFileName);
        fosBlob.write(m_abBlobValue);
        fosBlob.close();
    }

    /**
     * Returns an InputStream to the <code>Blob</code>
     *
     * @exception IOException
     * @return InputStream
     */
    public final InputStream openBinaryStream() throws IOException {
        saveToFile(m_strFileName);
        m_x = new FileInputStream(m_strFileName);
        return m_x;
    }

    /**
    * Closes and disposes of the <code>Blob</code> InputStream
    * @exception IOException
    */
    public final void closeBinaryStream() throws IOException {
        File f = new File(m_strFileName);
        m_x.close();
        if (f.exists()) {
            f.delete();
        }
    }

    /**
     * Return the size of the <code>Blob</code>
     *
     * @return int
     */
    public final int size() {
        if (m_abBlobValue == null) {
            return 0;
        } else {
            return m_abBlobValue.length;
        }
    }
}
