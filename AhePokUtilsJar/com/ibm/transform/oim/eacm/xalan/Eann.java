/*
 * Created on Dec 28, 2004
 *
 * Licensed Materials -- Property of IBM
 *
 * (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
 * The source code for this program is not published or otherwise divested of
 * its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
 *
 */


package com.ibm.transform.oim.eacm.xalan;


import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xalan.extensions.ExpressionContext;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.dtm.ref.DTMManagerDefault;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.apache.xpath.XPathContext;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EANTextAttribute;
import COM.ibm.eannounce.objects.MetaFlag;
import COM.ibm.eannounce.objects.XMLAttribute;

import com.ibm.transform.oim.eacm.xalan.table.*;



/**
 * Implements an XAlan Extension element that makes an Object that implements the Table interface look like an XML document.
 *
 * <pre>
 * $Log: Eann.java,v $
 * Revision 1.4  2011/04/28 22:29:48  wendy
 * SerializerToXML not available in jre 1.4.2 or greater
 *
 * Revision 1.3  2006/10/19 21:29:42  chris
 * Interface changes
 *
 * Revision 1.2  2006/01/26 15:28:14  wendy
 * AHE copyright
 *
 * Revision 1.1  2005/09/08 19:09:29  wendy
 * New pkg
 *
 * Revision 1.2  2005/04/18 15:13:55  chris
 * Switch to DOM when nodes exceed 65535.
 *
 * Revision 1.1  2005/02/23 21:13:02  chris
 * Initial XSL Report ABR Code
 *
 * </pre>
 *
 * @author cstolpe
 *
 */
public class Eann {
    private Table aTable = null;
    private TableDocument dtm = null;
    private boolean isDereferenced = false;
    private boolean isInitialized = false;
    /**
     * This constructor would be called by the XSLT processor using eann:new().
     * It creates a SimpleTable as the default table
     *
     */
    public Eann() {
        aTable = new SimpleTable();
    }
    /**
     * This constructor would be called by the XSLT processor using eann:new($tableRef).
     * $tableRef must implement the Table interface.
     * No methods of the table interface are called at this point.
     *
     * @param table
     */
    public Eann(Table table) {
        if (table != null) {
            aTable = table;
        }
        else {
            aTable = new SimpleTable();
            System.err.println("Eann(ExpressionContext, Table): Using TestTable for table");  //$NON-NLS-1$
        }
    }

    /**
     * This constructor would be called by the XSLT processor using eann:new('file').
     * It populates a SimpleTable using XML in the fileName.
     *
     * @param fileName
     */
    public Eann(String fileName) {
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = db.parse(fileName);
            Node data = doc.getFirstChild();
            NamedNodeMap nnm = data.getAttributes();
            NodeList headers = doc.getElementsByTagName("column-header");  //$NON-NLS-1$
            NodeList rows = doc.getElementsByTagName("row");  //$NON-NLS-1$
            int colCount = Integer.parseInt(nnm.getNamedItem("columnCount").getNodeValue());  //$NON-NLS-1$
            int rowCount = Integer.parseInt(nnm.getNamedItem("rowCount").getNodeValue());  //$NON-NLS-1$
            SimpleTable table = new SimpleTable(rowCount, colCount);

            aTable = table;
            for (int col = 0; col < colCount; col++) {
                table.setColumnHeader(col, headers.item(col).getFirstChild().getNodeValue());
            }
            for (int row = 0; row < rowCount; row ++) {
                Node rowNode = rows.item(row);
                NodeList cnl = null;
                rowNode.normalize();
                cnl = rowNode.getChildNodes();
                for (int col = 0; col < colCount; col++) {
                    Node column = cnl.item(col);
                    column = column.getFirstChild();
                    if (column.getNodeType() == Node.TEXT_NODE) {
                        table.set(row, col, column.getNodeValue());
                    }
                    else {
                        // This allows mw to stick xml into files
                        //removed from jre 1.4.2 SerializerToXML sxml = new SerializerToXML();
                        //sxml.setWriter(new StringWriter());
                        //sxml.serialize(column);
                        // TODO use output format to remove XML declaration and use a FACTORY
                        //table.set(row, col, sxml.getWriter().toString());
                        
                    	// jre 1.4.2 replacement
                    	StringWriter sw = new StringWriter();
                		XMLSerializer serializer = new XMLSerializer(new OutputFormat(Method.XML, "UTF-8", false ));
                		serializer.setOutputCharStream(sw);
                		serializer.serialize((Element)column);
                    	table.set(row, col, sw.toString());	
                    }
                }

            }
        } catch (IOException e) {
            System.err.print("Eann(ExpressionContext,String):");  //$NON-NLS-1$
            System.err.println(e.getMessage());
        } catch (ParserConfigurationException e) {
            System.err.print("Eann(ExpressionContext,String):");  //$NON-NLS-1$
            System.err.println(e.getMessage());
        } catch (FactoryConfigurationError e) {
            System.err.print("Eann(ExpressionContext,String):");  //$NON-NLS-1$
            System.err.println(e.getMessage());
        } catch (SAXException e) {
            System.err.print("Eann(ExpressionContext,String):");  //$NON-NLS-1$
            System.err.println(e.getMessage());
        }
        if (aTable == null) {
            aTable = new SimpleTable();
            System.err.println("Eann(ExpressionContext,String): Using TestTable for table");  //$NON-NLS-1$
        }
    }

    /**
     * This method is invoked by the XSLT processor using eann:getTableAsXML($instance)
     * Where $instance is an XSLT variable that was assigned using one of the eann:new() constructors
     *
     * @param exprContext
     * @return DTM representing the table as a DOM
     * @throws javax.xml.parsers.ParserConfigurationException
     */
    public Node getTableAsXML(ExpressionContext exprContext) throws ParserConfigurationException {
        DTMManager mgr = ((XPathContext.XPathExpressionContext) exprContext).getDTMManager();
        DTMManagerDefault mgrd = (DTMManagerDefault) mgr;
        int ident = mgrd.getFirstFreeDTMID();
        Node result = null;
        if (aTable instanceof Init) {
            Init tmp = (Init) aTable;
            if (!tmp.initialize()) {
                System.err.println("Eann.getTableAsXML(ExpressionContext): initialize failed");  //$NON-NLS-1$
            }
            isInitialized = true;
        }
        dtm = new TableDocument(mgr, ident << DTMManager.IDENT_DTM_NODE_BITS, aTable);
        if (dtm.isTooLarge()) {
            System.err.println("Eann.getTableAsXML(ExpressionContext): switching to DOM");  //$NON-NLS-1$
            result = getTableAsDOM(exprContext);
        }
        else {
			mgrd.addDTM(dtm, ident);
			result = dtm.getNode(dtm.getDocument());
        }

        return result;
    } 

    /**
     * This method is invoked by the XSLT processor using eann:getTableAsXML($instance)
     * Where $instance is an XSLT variable that was assigned using one of the eann:new() constructors
     *
     * @param exprContext
     * @param entityID
     * @param entityType
     * @return DTM representing the table as a DOM
     * @throws javax.xml.parsers.ParserConfigurationException
     */
    public Node getTableAsXML(ExpressionContext exprContext, String entityType, int entityID) throws ParserConfigurationException {
        if (aTable instanceof EntityParam) {
            EntityParam tmp = (EntityParam) aTable;
            tmp.setEntityType(entityType);
            tmp.setEntityID(entityID);
        }
        return getTableAsXML(exprContext);
    }

    /**
     * Calls the dereference method on the Table instance.
     * @return true if successful
     */
    public boolean dereference() {
    	boolean result = false;
        if (!isDereferenced && aTable instanceof PDHAccess) {
			PDHAccess tmp = (PDHAccess) aTable;
            if (!tmp.dereference()) {
                System.err.println("Eann.dereference(): failed");  //$NON-NLS-1$
            }
            else {
            	isDereferenced = true;
            	result = true;
                if (dtm != null) {
					result = dtm.getManager().release(dtm, true);
                }
                isDereferenced &= result;
            }
        }
        return result;
    }
    /**
     * Returns Table as a DOM object and dereferences it.
     * @param exprContext
     * @throws javax.xml.parsers.ParserConfigurationException
     * @return
     */
    public Node getTableAsDOM(ExpressionContext exprContext) throws ParserConfigurationException {
    	final String signature = ".getTableAsDOM(ExpressionContext): ";
        Document doc = null;
        if (exprContext == null) {
			System.err.print(getClass().getName());
			System.err.print(signature);
			System.err.println("ExpressionContext is null");  //$NON-NLS-1$
        }
        if (!isInitialized && aTable instanceof Init) {
            Init tmp = (Init) aTable;
            if (!tmp.initialize()) {
				System.err.print(getClass().getName());
				System.err.print(signature);
				System.err.print(tmp.getClass().getName());
				System.err.println(".initialize() returned false");  //$NON-NLS-1$
            }
        }
        if (aTable == null) {
			System.err.print(getClass().getName());
			System.err.print(signature);
            System.err.println("aTable is null");  //$NON-NLS-1$
        }
        else {
            // Map the document to the table
            // Make it easy to translate an index to a row,col pair
            // Column Header's are in the even numbered indexes
            // Column Header text is in the odd numbered indexes
            int rowCount = aTable.getRowCount();
            int colCount = aTable.getColumnCount();

            // Map the basic structure which is constant
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Element data;
            Element metadata;
            Element rowset;
            doc = builder.newDocument();
            data = doc.createElement("data");
            doc.appendChild(data);
            metadata = (Element) data.appendChild(doc.createElement("metadata"));
            rowset = (Element) data.appendChild(doc.createElement("row-set"));
            // map the column headers
            for (int i = 0; i < colCount; i++) {
                Element header = (Element) metadata.appendChild(doc.createElement("column-header"));
                header.appendChild(doc.createCDATASection(aTable.getColumnHeader(i)));
            }

            // map the rows and columns
            for (int r = 0; r < rowCount; r++) {
                // map row element
                Element row = (Element) rowset.appendChild(doc.createElement("row"));
                for (int c = 0; c < colCount; c++) {
                    Object cell = aTable.get(r,c);
                    // map col element
                    Element col = (Element) row.appendChild(doc.createElement("col"));
                    // map type attribute
                    Attr attribute = doc.createAttribute("type");
                    col.setAttributeNode(attribute);
                    if (cell == null) {
                        // Don't map an element for empty fields
                        // <col type="Not Populated"/>
                        attribute.setValue("Not Populated");
                    }
                    else if (cell instanceof EANFlagAttribute) {
                        // Flag Attributes have one ore more flag values
                        MetaFlag[] mfa = (MetaFlag[])((EANFlagAttribute) cell).get();
                        attribute.setValue("F");
                        for (int i = 0; i < mfa.length; i++) {
                            if (mfa[i].isSelected()) {
                                Element flag = (Element) row.appendChild(doc.createElement("flag"));
                                flag.appendChild(doc.createCDATASection(mfa[i].getLongDescription()));
                                col.appendChild(flag);
                            }
                        }
                    }
                    else if (cell instanceof XMLAttribute) {
                        attribute.setValue("X");
                        col.appendChild(doc.createCDATASection((String)((EANTextAttribute)cell).get()));
                    }
                    else if (cell instanceof EANTextAttribute) {
                        // map #text of col element
                        attribute.setValue("T");
                        col.appendChild(doc.createCDATASection((String)((EANTextAttribute)cell).get()));
                    }
                    else if (cell instanceof String) {
                        attribute.setNodeValue("T");
                        col.appendChild(doc.createCDATASection(cell.toString()));
                    }
                    else {
                        // Don't map an element for unhandled types
                        // <col type="Unhandled Type"/>
                        attribute.setValue("Unhandled Type");
                    }

                }
            }
        }
        dereference();
        return doc;
    }
}
