/*
 * Created on Jun 16, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package COM.ibm.eannounce.catalog;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author David Bigelow
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Blobo  {
	
	   public byte[] m_baAttributeValue = null;
	   public String m_strTag = null;

	   public Blobo(String _strTag, byte[] baAttributeValue) {
		   m_baAttributeValue = baAttributeValue;
		   this.m_strTag = _strTag;
	   }

	   /**
		* Returns an InputStream to the <code>Blob</code>
		*
		* @exception IOException
		* @return InputStream
		*/
	   public final InputStream openBinaryStream() throws IOException {
		   return new ByteArrayInputStream(this.m_baAttributeValue);
	   }

	   /**
		* Return the size of the <code>Blob</code>
		*
		* @return int
		*/
	   public final int size() {
		  return m_baAttributeValue.length;
	   }

	   public final String toString() {
		   return " extension:" + m_strTag + " size:" + m_baAttributeValue.length;
	   }

   }
