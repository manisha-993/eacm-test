//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: BlobDataSource.java,v $
// Revision 1.4  2006/01/03 21:54:43  dave
// <No Comment Entered>
//
// Revision 1.3  2003/06/27 23:00:34  bala
// plug in cvs log statements
//
package COM.ibm.opicmpdh.middleware;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.activation.DataSource;

/**
 *  Custom Datasource for sending contents of a Blob as a mail attachment
 *
 *@author     Bala
 *@created    June 27, 2003
 */
public class BlobDataSource implements DataSource {
  private byte[] m_data;
  private java.lang.String m_name;


  /**
   *  Creates a DataSource from an array of bytes
   *
   *@param  _data  Description of the Parameter
   *@param  _name  Description of the Parameter
   */
  public BlobDataSource(byte[] _data, String _name) {
    m_data = _data;
    m_name = _name;
  }


  /**
   *  Returns the content-type information required by a DataSource
   *  application/octet-stream in this case
   *
   *@return    The contentType value
   */
  public String getContentType() {
    return "application/octet-stream";
  }


  /**
   *@return                  InputStream from the DataSource
   *@exception  IOException  Description of the Exception
    *
   */
  public InputStream getInputStream() throws IOException {
    return new ByteArrayInputStream(m_data);
  }


  /**
   *
   *@return     the name of the DataSource
   */
  public String getName() {
    return m_name;
  }


  /**
   *  Returns an OutputStream from the DataSource
   *
   *@return     OutputStream Array of bytes converted into an OutputStream
   *@exception  IOException  Description of the Exception
   *
   */
  public OutputStream getOutputStream() throws IOException {
    OutputStream out = new ByteArrayOutputStream();
    out.write(m_data);
    return out;
  }

  public final String getVersion() {
    return new String("$Id: BlobDataSource.java,v 1.4 2006/01/03 21:54:43 dave Exp $");
  }

}


