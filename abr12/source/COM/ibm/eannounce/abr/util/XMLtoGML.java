// (c) Copyright International Business Machines Corporation, 2003
// All Rights Reserved.
//
//$Log: XMLtoGML.java,v $
//Revision 1.2  2011/01/21 12:31:28  guobin
//add comments log
//
//Revision 1.1.1.1  2003/06/03 19:02:25  dave
//new 1.1.1 abr 
//
//Revision 1.1  2003/05/08 18:50:27  bala
//Plug in for xml to gml...initial checkin
//
package COM.ibm.eannounce.abr.util;

// Imported TraX classes
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;

// Imported java classes
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

/**
 *  Description of the Class
 *
 *@author     Administrator
 *@created    May 6, 2003
 */
// XMLtoGML.java,v
public class XMLtoGML {
  /**
   *  XMLtoGML constructor.
   */
  public XMLtoGML() {
    super();
  }


  /**
   *  This method accepts a String with the RFA identifier and a StreamSource
   *  containing XML data. It applies an XSLT stylesheet (XML_to_GML.xslt) to
   *  the XML data to transform it into GML markup in the returned output
   *  stream. Creation date: (4/5/2002 4:15:49 PM)
   *
   *@param  _strRFAId                                  java.lang.String
   *@param  inputXML
   *      javax.xml.transform.stream.StreamSource
   *@return                                        java.io.OutputStream
   *@exception  TransformerException               Description of the Exception
   *@exception  TransformerConfigurationException  Description of the Exception
   *@exception  FileNotFoundException              Description of the Exception
   */
  public OutputStream transform(StreamSource inputXML)
       throws TransformerException, TransformerConfigurationException,
      FileNotFoundException {
    String inputXSLT = "XML_to_GML.xslt";
    /*
	* Instantiate a TransformerFactory.
	*/
    TransformerFactory tFactory = TransformerFactory.newInstance();

    /*
	* Use the TransformerFactory to process the stylesheet Source and
	* generate a Transformer.
	*/
    Transformer transformer = tFactory.newTransformer(new StreamSource(inputXSLT));
    
    /*
    * u can change this to read from a string using 
      Transformer transformer = tFactory.newTransformer(new StringReader(String s)); 
      I propose the xslt be stored in longtext in the pdh
    */

    /*
	* Use the Transformer to transform an XML Source and send the
	* output to a Result object.
	*/
    ByteArrayOutputStream gmlOS = new ByteArrayOutputStream();// output stream
    StreamResult gmlData = new StreamResult(gmlOS);// transformation result
    transformer.transform(inputXML, gmlData);// transform xml to gml

    return gmlData.getOutputStream();// return gml data in output stream
    //maybe u should return this back as a string using "gmlOS.toString()"
//    return gmlOS.toString();
  }
}


