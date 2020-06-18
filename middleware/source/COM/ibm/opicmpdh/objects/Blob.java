//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: Blob.java,v $
// Revision 1.25  2012/11/08 21:24:04  wendy
// moved reset() after flush(), improved error handling, removed unnecessary memory allocations
//
// Revision 1.24  2005/01/27 02:42:20  dave  
// Jtest format cleanup
//
// Revision 1.23  2001/09/20 17:16:26  roger
// Fixes
//
// Revision 1.22  2001/09/20 16:51:43  roger
// Use accessors for objects
//
// Revision 1.21  2001/09/19 15:32:32  roger
// Formatting
//
// Revision 1.20  2001/09/19 15:24:58  roger
// Remove constructors with Profile as parm
//
// Revision 1.19  2001/09/17 22:17:48  roger
// Remove protected from constructors
//
// Revision 1.18  2001/09/17 22:07:32  roger
// Needed import
//
// Revision 1.17  2001/09/17 22:00:00  roger
// Open up constructors and members
//
// Revision 1.16  2001/09/17 20:24:27  roger
// Use Profile for values of Enterprise, OPENID, TranID, etc
//
// Revision 1.15  2001/08/22 16:53:06  roger
// Removed author RM
//
// Revision 1.14  2001/06/27 21:19:24  roger
// No fishing
//
// Revision 1.13  2001/06/27 20:58:17  roger
// Don't fish for finally here
//
// Revision 1.12  2001/06/27 20:14:55  roger
// Added code to report if finally block is not executed - go fishing!
//
// Revision 1.11  2001/06/24 18:42:00  dave
// misc fixes for final push
//
// Revision 1.10  2001/03/26 16:46:03  roger
// Misc formatting clean up
//
// Revision 1.9  2001/03/21 00:01:11  roger
// Implement java class file branding in getVersion method
//
// Revision 1.8  2001/03/16 17:13:18  roger
// Needed ByteArray*Stream and Object*Stream imports
//
// Revision 1.7  2001/03/16 15:52:27  roger
// Added Log keyword
//


package COM.ibm.opicmpdh.objects;
  

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;


/**
 * Blob
 * @version @date
 * @see Attribute
 * @see ControlBlock
 * @see EntitiesAndRelator
 * @see Entity
 * @see Flag
 * @see LongText
 * @see MetaEntity
 * @see MultipleFlag
 * @see Relator
 * @see SingleFlag
 * @see Text
 */
public final class Blob extends Attribute  {

    // Class constants

    /**
     * @serial
     */
    static final long serialVersionUID = 10L;

    // Class variables
    private static final SimpleDateFormat SDTIMESTAMP = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    // Instance variables
    /**
     * FIELD
     */
    public String m_strBlobExtension = null;
    /**
     * FIELD
     */
    public byte[] m_baAttributeValue = null;
    private String m_strFILENAME = SDTIMESTAMP.format(new Date()) + ".blob";
    transient private FileInputStream m_x = null;
    //transient private FileOutputStream fosBlob = null;

    /**
     * Main method which performs a simple test of this class
     *
     * @param args 
     */
    public static void main(String args[]) {
    }

    /**
     * Construct a default <code>Blob</code>
     */
    public Blob() {
        this("", "", 0, "", new byte[0], "", 0, new ControlBlock());
    }

    /**
     * Construct a <code>Blob</code>
     *
     * @param strEnterprise
     * @param strEntityType
     * @param iEntityID
     * @param strAttributeCode
     * @param baAttributeValue
     * @param strBlobExtension
     * @param iNLSID 
     */
    public Blob(String strEnterprise, String strEntityType, int iEntityID, String strAttributeCode, byte[] baAttributeValue, String strBlobExtension, int iNLSID) {
        super(strEnterprise, strEntityType, iEntityID, strAttributeCode, "", iNLSID);

        m_baAttributeValue = baAttributeValue;
        m_strBlobExtension = strBlobExtension;
    }

    /**
     * Construct a <code>Blob</code>
     *
     * @param strEnterprise
     * @param strEntityType
     * @param iEntityID
     * @param strAttributeCode
     * @param baAttributeValue
     * @param strBlobExtension
     * @param iNLSID
     * @param cbControlBlock 
     */
    public Blob(String strEnterprise, String strEntityType, int iEntityID, String strAttributeCode, byte[] baAttributeValue, String strBlobExtension, int iNLSID, ControlBlock cbControlBlock) {
        super(strEnterprise, strEntityType, iEntityID, strAttributeCode, "", iNLSID, cbControlBlock);

        m_baAttributeValue = baAttributeValue;
        m_strBlobExtension = strBlobExtension;
    }

    /**
     * Construct a <code>Blob</code> from a file
     *
     * @exception IOException
     * @param strEnterprise
     * @param strEntityType
     * @param iEntityID
     * @param strAttributeCode
     * @param strFileName
     * @param strBlobExtension
     * @param iNLSID
     * @param cbControlBlock 
     */
    public Blob(String strEnterprise, String strEntityType, int iEntityID, String strAttributeCode, String strFileName, String strBlobExtension, int iNLSID, ControlBlock cbControlBlock) throws IOException {
        super(strEnterprise, strEntityType, iEntityID, strAttributeCode, "", iNLSID, cbControlBlock);

        m_strBlobExtension = strBlobExtension;
        loadFromFile(strFileName);
    }

    /**
     * Construct a <code>Blob</code> from a URL
     *
     * @exception IOException
     * @param strEnterprise
     * @param strEntityType
     * @param iEntityID
     * @param strAttributeCode
     * @param strURL
     * @param strBlobExtension
     * @param iNLSID
     * @param cbControlBlock 
     */
    public Blob(String strEnterprise, String strEntityType, int iEntityID, String strAttributeCode, URL strURL, String strBlobExtension, int iNLSID, ControlBlock cbControlBlock) throws IOException {
        super(strEnterprise, strEntityType, iEntityID, strAttributeCode, "", iNLSID, cbControlBlock);

        m_strBlobExtension = strBlobExtension;

        loadFromURL(strURL);
    }

    /**
     * Construct a <code>Blob</code> from an object (serialize and make Blob)
     *
     * @exception IOException
     * @param strEnterprise
     * @param strEntityType
     * @param iEntityID
     * @param strAttributeCode
     * @param objObject
     * @param strBlobExtension
     * @param iNLSID
     * @param cbControlBlock 
     */
    public Blob(String strEnterprise, String strEntityType, int iEntityID, String strAttributeCode, Object objObject, String strBlobExtension, int iNLSID, ControlBlock cbControlBlock) throws IOException {

        super(strEnterprise, strEntityType, iEntityID, strAttributeCode, "", iNLSID, cbControlBlock);

        m_strBlobExtension = strBlobExtension;

        ByteArrayOutputStream baosObject = new ByteArrayOutputStream();
        ObjectOutputStream oosObject = null;

        try {
        	oosObject = new ObjectOutputStream(baosObject);
        	oosObject.writeObject(objObject);
      //  	oosObject.reset(); move after flush
        	oosObject.flush();
          	oosObject.reset();
          	
           //not needed	m_baAttributeValue = new byte[baosObject.size()];
        	m_baAttributeValue = baosObject.toByteArray();
        } finally {
        	if (oosObject != null) {
        		try{
        			oosObject.close();
        		} catch (IOException ex) {
                    ex.printStackTrace();
                }
        		oosObject = null;
        	}

        	if (baosObject != null) {
        		try{
        			baosObject.close();
        		}catch (IOException ex) {
                    ex.printStackTrace();
                }
        		baosObject = null;
        	}
        }
    }

    /**
     * Construct a <code>Blob</code>
     *
     * @param strEnterprise
     * @param strEntityType
     * @param iEntityID
     * @param strAttributeCode
     * @param baAttributeValue
     * @param strBlobExtension
     * @param iNLSID
     * @param strValFrom
     * @param strValTo
     * @param strEffFrom
     * @param strEffTo
     * @param openID 
     */
    public Blob(String strEnterprise, String strEntityType, int iEntityID, String strAttributeCode, byte[] baAttributeValue, String strBlobExtension, int iNLSID, String strValFrom, String strValTo, String strEffFrom, String strEffTo, int openID) {
        super(strEnterprise, strEntityType, iEntityID, strAttributeCode, "", iNLSID, new ControlBlock(strValFrom, strValTo, strEffFrom, strEffTo, openID));

    //this is not needed, ties up memory    m_baAttributeValue = new byte[baAttributeValue.length];
        m_baAttributeValue = baAttributeValue;
        m_strBlobExtension = strBlobExtension;
    }

    /**
     * Load the <code>Blob</code> from a file (yes, UNC works)
     *
     * @exception IOException
     * @param strFileName 
     */
    public final void loadFromFile(String strFileName) throws IOException {
        FileInputStream fisBlob = null;
        int iSize = 0;
        try {
            fisBlob = new FileInputStream(strFileName);
            iSize = fisBlob.available();

            m_baAttributeValue = new byte[iSize];

            fisBlob.read(m_baAttributeValue);
        } finally {
            if (fisBlob != null) {
                try {
                    fisBlob.close();   
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                fisBlob = null;
            }
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
        m_baAttributeValue = new byte[iTotalBytes];

        for (int i = 0; i < iChunks; i++) {

            // Get next chunk using chunk # as key
            baChunk = (byte[]) hashData.get(new Integer(i));

            System.arraycopy(baChunk, 0, m_baAttributeValue, iDestOffset, baChunk.length);

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
    	FileOutputStream fosBlob = new FileOutputStream(strFileName);
    	try{
    		fosBlob.write(m_baAttributeValue);
    	}finally{
            if (fosBlob != null) {
                try {
                	fosBlob.close();   
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                fosBlob = null;
            }
    	}
    }

    /**
     * Returns an InputStream to the <code>Blob</code>
     *
     * @exception IOException
     * @return InputStream
     */
    public final InputStream openBinaryStream() throws IOException {
        saveToFile(m_strFILENAME);

        m_x = new FileInputStream(m_strFILENAME);

        return m_x;
    }

    /**
     * Closes and disposes of the <code>Blob</code> InputStream
     * @exception IOException
     */
    public final void closeBinaryStream() throws IOException {
        File f = new File(m_strFILENAME);
        try{
        	if(m_x!=null){
        		m_x.close();
        		m_x = null;
        	}
        }finally{
        	// make sure file is removed
        	if (f.exists()) {
        		f.delete();
        	}
        }
    }

    /**
     * Return the size of the <code>Blob</code>
     *
     * @return int
     */
    public final int size() {
        if (m_baAttributeValue == null) {
            return 0;
        } else {
            return m_baAttributeValue.length;
        }
    }

    /**
     * Returns the <code>Blob</code> as an <code>Object</code> iff the blob is a serialized Java object
     *
     * @exception IOException
     * @exception ClassNotFoundException
     * @return Object
     */
    public final Object asObject() throws IOException, ClassNotFoundException {
        ByteArrayInputStream baisObject = null;
        ObjectInputStream oisObject = null;

        try {
        	baisObject = new ByteArrayInputStream(this.m_baAttributeValue);
            oisObject = new ObjectInputStream(baisObject);
            return oisObject.readObject();
        } finally {
        	if(oisObject!=null){
        		try{
        			oisObject.close();
        		}catch (IOException ex) {
        			ex.printStackTrace();
        		}
        		oisObject = null;
        	}
        	if(baisObject!=null){
        		try{
        			baisObject.close();
        		}catch (IOException ex) {
        			ex.printStackTrace();
        		}
        		baisObject = null;
        	}
        }
    }

    /**
     * getBAAttributeValue
     * @return byte[]
     */
    public byte[] getBAAttributeValue() {
        return m_baAttributeValue;
    }

    /**
     * getBlobExtension
     * @return String
     */
    public String getBlobExtension() {
        return m_strBlobExtension;
    }

    /**
     * setBlobExtension
     *
     * @param _strBlobExtension 
     */
    public void setBlobExtension(String _strBlobExtension) {
        this.m_strBlobExtension = _strBlobExtension;
    }

    /**
     * The <code>Blob</code> as a String
     * @return <code>Blob</code> definition as a <code>String</code>
     */
    public final String toString() {
        return super.toString() + " extension:" + m_strBlobExtension + " size:" + size();
    }

    /**
     * Return the date/time this class was generated
     * @return the date/time this class was generated
     */
    public final String getVersion() {
        return "$Id: Blob.java,v 1.25 2012/11/08 21:24:04 wendy Exp $";
    }
}
