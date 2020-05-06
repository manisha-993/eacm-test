/*
 * Created on Dec 29, 2004
 *
 * Licensed Materials -- Property of IBM
 *
 * (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
 * The source code for this program is not published or otherwise divested of
 * its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
 *
 */


package com.ibm.transform.oim.eacm.xalan;


import javax.xml.transform.SourceLocator;

import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.dtm.ref.DTMDefaultBaseIterators;
import org.apache.xml.utils.FastStringBuffer;
import org.apache.xml.utils.SuballocatedIntVector;
import org.apache.xml.utils.XMLString;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;

import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EANTextAttribute;
import COM.ibm.eannounce.objects.MetaFlag;
import COM.ibm.eannounce.objects.XMLAttribute;


/**
 * This makes a Table look like DOM to an XSLT processor
 *
 * <pre>
 * $Log: TableDocument.java,v $
 * Revision 1.3  2006/10/19 21:29:43  chris
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
 * Revision 1.1  2005/02/23 21:13:03  chris
 * Initial XSL Report ABR Code
 *
 * </pre>
 *
 * @author cstolpe
 */
public class TableDocument extends DTMDefaultBaseIterators {
    /** Automatically generated javadoc for: MAXNODES */
    private static final int MAXNODES = 65535;
    private static final String DATA = "data";  //$NON-NLS-1$
    private static final String METADATA = "metadata";  //$NON-NLS-1$
    private static final String COLUMNHEADER = "column-header"; //$NON-NLS-1$
    private static final String ROWSET = "row-set"; //$NON-NLS-1$
    private static final String ROW = "row"; //$NON-NLS-1$
    private static final String COL = "col"; //$NON-NLS-1$
    private static final String COLTYPE = "type";  // only attribute of col //$NON-NLS-1$
    private static final String FLAGTYPE = "flag"; // only child of col other than text //$NON-NLS-1$
    private static final int ROOTINDEX = 0;
    private static final int DATAINDEX = 1;
    private static final int METADATAINDEX = 2;
    private static final int ROWSETINDEX = 3;
    // Namespace of this Extension element
    private static final String NAMESPACE = "";//"xalan://com.ibm.transform.oim.eacm.xalan.Eann"; namespace comparison made XPath test fail  //$NON-NLS-1$
    // DTM identifiers
    private int documentTypeID = DTM.NULL;
    private int textTypeID = DTM.NULL;
    private int dataTypeID = DTM.NULL;
    private int metaDataTypeID = DTM.NULL;
    private int columnHeaderTypeID = DTM.NULL;
    private int rowSetTypeID = DTM.NULL;
    private int rowTypeID = DTM.NULL;
    private int colTypeID = DTM.NULL;
    private int colAttributeTypeID = DTM.NULL;
    private int flagTypeID = DTM.NULL;
    // The table being wrapped by this document
    private Table table = null;
    private boolean isTooLarge = false;

    private int rowCount = -1; // not initialized yet
    private int colCount = -1; // not initialized yet
    private final int chsize = 2;
    private final int columnHeaderOffset = ROWSETINDEX + 1;
    private int rowOffset = -1; // not initialized yet
    // Node of cols will be added here. the index of them will be used to calculate row and column
    private SuballocatedIntVector cells = new SuballocatedIntVector();
    /**
     * Default constructor.
     *
     * @param mgr
     * @param ident
     */
    public TableDocument(DTMManager mgr, int ident) {
        // This is all they have for sql
        super(mgr,null,ident,null,mgr.getXMLStringFactory(),true);
        initializeTypes();
    }
    /**
     * Constructor when using a table
     *
     * @param mgr
     * @param ident
     * @param aTable
     */
    public TableDocument(DTMManager mgr, int ident, Table aTable) {
        // This is all they have for sql
        super(mgr,null,ident,null,mgr.getXMLStringFactory(),true);
        initializeTypes();
        setTable(aTable);
    }

    private void initializeTypes() {
        // Initialize the element types found in this document
        documentTypeID = m_expandedNameTable.getExpandedTypeID(NAMESPACE, "#root", DTM.DOCUMENT_NODE);  //$NON-NLS-1$
        textTypeID = m_expandedNameTable.getExpandedTypeID(NAMESPACE, "#text", DTM.TEXT_NODE);  //$NON-NLS-1$
        dataTypeID = m_expandedNameTable.getExpandedTypeID(NAMESPACE, DATA, DTM.ELEMENT_NODE);
        metaDataTypeID = m_expandedNameTable.getExpandedTypeID(NAMESPACE, METADATA, DTM.ELEMENT_NODE);
        columnHeaderTypeID = m_expandedNameTable.getExpandedTypeID(NAMESPACE, COLUMNHEADER, DTM.ELEMENT_NODE);
        rowSetTypeID = m_expandedNameTable.getExpandedTypeID(NAMESPACE, ROWSET, DTM.ELEMENT_NODE);
        rowTypeID = m_expandedNameTable.getExpandedTypeID(NAMESPACE, ROW, DTM.ELEMENT_NODE);
        colTypeID = m_expandedNameTable.getExpandedTypeID(NAMESPACE, COL, DTM.ELEMENT_NODE);
        colAttributeTypeID = m_expandedNameTable.getExpandedTypeID(NAMESPACE, COLTYPE, DTM.ATTRIBUTE_NODE);
        flagTypeID = m_expandedNameTable.getExpandedTypeID(NAMESPACE, FLAGTYPE, DTM.ELEMENT_NODE);
    }
    /**
     * Maps the node (index) to an element type, parent, previous sibling, and next sibling
     * @param node
     * @param expType
     * @param parentNode
     * @param prevSiblingNode
     */
    private void mapElement(int node, int expType, int parentNode, int prevSiblingNode) {
        // record the type for this node
        m_exptype.setElementAt(expType, node);
        // next sibling is always initially null
        m_nextsib.setElementAt(DTM.NULL, node);
        // record the previous sibling node
        m_prevsib.setElementAt(prevSiblingNode, node);
        // record the parent node
        m_parent.setElementAt(parentNode, node);
        // the first child is always initially null until one is added
        m_firstch.setElementAt(DTM.NULL, node);

        if (prevSiblingNode != DTM.NULL) {
            // When adding a sibling
            // if the previous sibling already has a sibling
            // then this is an insert so this node will get it's next sibling
            // from the previous sibling
            if (m_nextsib.elementAt(prevSiblingNode) != DTM.NULL) {
                m_nextsib.setElementAt(m_nextsib.elementAt(prevSiblingNode), node);
            }
            // The previous sibling gets this node as it's next sibling
            m_nextsib.setElementAt(node, prevSiblingNode);
        }
        // If this node is the first node make it the first child of the parent
        if ((parentNode != DTM.NULL) && (m_prevsib.elementAt(node) == DTM.NULL)) {
            m_firstch.setElementAt(node, parentNode);
        }
    }
    /**
     * Sets the table if not null and maps it to the DTM
     *
     * @param aTable
     */
    public void setTable(Table aTable) {
        table = aTable;
        if (table == null) {
            System.err.println("TableDocument.setTable(Table): table parameter is null");  //$NON-NLS-1$
        }
        else {
            int rowIndex;
            int rowPrevious = DTM.NULL;
            // Map the document to the table
            // Make it easy to translate an index to a row,col pair
            // Column Header's are in the even numbered indexes
            // Column Header text is in the odd numbered indexes
            rowCount = table.getRowCount();
            colCount = table.getColumnCount();
            rowOffset = columnHeaderOffset + (colCount * chsize);
            rowIndex = rowOffset;

            // Map the basic structure which is constant
            mapElement(ROOTINDEX, documentTypeID, DTM.NULL, DTM.NULL);
            mapElement(DATAINDEX, dataTypeID, ROOTINDEX, DTM.NULL);
            mapElement(METADATAINDEX, metaDataTypeID, DATAINDEX, DTM.NULL);
            mapElement(ROWSETINDEX, rowSetTypeID, DATAINDEX, METADATAINDEX);
            m_size = 4;
            // map the column headers
            for (int i = 0; i < colCount; i++) {
                int columnHeaderIndex = (i * chsize) + columnHeaderOffset;
                int columnPrevious = DTM.NULL;
                if (i > 0) {
                    columnPrevious = columnHeaderIndex - chsize;
                }
                // map column-header element
                mapElement(columnHeaderIndex, columnHeaderTypeID, METADATAINDEX, columnPrevious);
                // CHild axis traverser fails to work  if nodes are not indexed
                // map #text of column-header element
                mapElement(columnHeaderIndex + 1, textTypeID, columnHeaderIndex, DTM.NULL);
                m_size += chsize;
            }

            // map the rows and columns
            for (int r = 0; r < rowCount; r++) {
                int colPrevious = DTM.NULL;
                int nodesAdded = 1; // one for row
                // map row element
                mapElement(rowIndex, rowTypeID, ROWSETINDEX, rowPrevious);
                for (int c = 0; c < colCount; c++) {
                    int colIndex = rowIndex + nodesAdded;
                    Object cell;
                    // map col element
                    mapElement(colIndex, colTypeID, rowIndex, colPrevious);
                    nodesAdded++; // one for col element
                    cells.addElement(makeNodeHandle(colIndex));
                    // map type attribute
                    mapElement(colIndex + 1, colAttributeTypeID, colIndex, DTM.NULL);
                    nodesAdded++; // one fore attribute node
                    // add one if just #text and more if flag attribute 2 * flag count
                    cell = table.get(r,c);
                    if (cell == null) {
                        // Don't map an element for empty fields
                        // <col type="Not Populated"/>
                    }
                    else if (cell instanceof EANFlagAttribute) {
                        int flagPrevious = DTM.NULL;
                        // Flag Attributes have one ore more flag values
                        MetaFlag[] mfa = (MetaFlag[])((EANFlagAttribute) cell).get();
                        int flagIndex = colIndex + 2;
                        for (int i = 0; i < mfa.length; i++) {
                            if (mfa[i].isSelected()) {
                                mapElement(flagIndex, flagTypeID, colIndex, flagPrevious);
                                mapElement(flagIndex + 1, textTypeID, flagIndex, DTM.NULL);
                                flagPrevious = flagIndex;
                                flagIndex += 2;
                                nodesAdded += 2; // one for flag and one for #text
                            }
                        }
                    }
                    else if (cell instanceof EANTextAttribute || cell instanceof String) {
                        // map #text of col element
                        mapElement(colIndex + 2, textTypeID, colIndex, DTM.NULL);
                        nodesAdded++; // one for col #text
                    }
                    else {
                        // Don't map an element for unhandled types
                        // <col type="Unhandled Type"/>
                    }

                    colPrevious = colIndex;
                    colIndex += nodesAdded;
                }
                rowPrevious = rowIndex;
                rowIndex += nodesAdded; // Advance to next row index
                m_size += nodesAdded;
            }
            if (m_size > MAXNODES) {
                System.err.println("TableDocument.setTable(Table): Table is too large for DTM. It requires "+m_size+" nodes.");
                setTooLarge(true);
            }
        }
    }

    /**
     * Locates the attribute of the col element
     * @param nodeHandle
     * @return int
     */
    public int getFirstAttribute(int nodeHandle) {
    	int result = DTM.NULL;
        int nodeIndex = makeNodeIdentity(nodeHandle);
        if (nodeIndex != DTM.NULL) {
            if (getExpandedTypeID(nodeHandle) == colTypeID) {
                result = makeNodeHandle(nodeIndex + 1); // I always put attribute right after col
            }
        }
        return result;
    }
    /**
     *  (non-Javadoc)
     * @see org.apache.xml.dtm.ref.DTMDefaultBase#getNextNodeIdentity(int)
     * @param arg0
     * @return int
     */
    protected int getNextNodeIdentity(int arg0) {
        // This is all they have for sql
        // I assume this it the int of the next node at the level of arg0 in document order
        return DTM.NULL;
    }

    /**
     *  (non-Javadoc)
     * @see org.apache.xml.dtm.ref.DTMDefaultBase#nextNode()
     * @return boolean
     */
    protected boolean nextNode() {
        // This is all they have in sql
        // set current node to next node
        return false;
    }

    /**
     *  (non-Javadoc)
     * @see org.apache.xml.dtm.ref.DTMDefaultBase#getNumberOfNodes()
     * @return int
     */
    protected int getNumberOfNodes() {
        //Is this all nodes?
        return m_size;
    }


    /**
     *  (non-Javadoc)
     * @see org.apache.xml.dtm.DTM#getAttributeNode(int, java.lang.String, java.lang.String)
     * @param elementHandle
     * @param namespaceURI
     * @param name
     * @return int
     */
    public int getAttributeNode(int elementHandle, String namespaceURI, String name) {
    	int result = DTM.NULL;
        // This is all they have for sql
        //elementHandle - Handle of the node upon which to look up this attribute.
        //namespaceURI - The namespace URI of the attribute to retrieve, or null.
        //name - The local name of the attribute to retrieve.
        // No elements have attributes

        // Only supporting type on col at this type
        if(getExpandedTypeID(elementHandle) == colTypeID && "type".equals(name)) {  //$NON-NLS-1$
            int index = makeNodeIdentity(elementHandle);
            result = makeNodeHandle(index + 1);
        }
        return result;
    }

    /**
     *  (non-Javadoc)
     * @see org.apache.xml.dtm.DTM#getStringValue(int)
     * @param arg0
     * @return XMLString
     */
    public XMLString getStringValue(int arg0) {
        // Concatinate all the descendent text nodes
        // so #root and data are the same. it is the entire document
        // metadata is all the column-header
        // column-header is the #text
        // row-set is all the rows
        // row is all the col
        // col is the #text
        int index = makeNodeIdentity(arg0);
        FastStringBuffer fsb = new FastStringBuffer();
        // 0,1 everything
        // 2 all column-header
        // 3 all row
        switch (index) {
        case ROOTINDEX:
        case DATAINDEX:
            getMetaData(fsb);
            getRowSet(fsb);
            break;
        case METADATAINDEX:
            getMetaData(fsb);
            break;
        case ROWSETINDEX:
            getRowSet(fsb);
            break;
        default: {
                // a particular header, cell or a whole row
                if (index < rowOffset) {
                    // Header
                    int col;
                    index -= columnHeaderOffset; // normalize
                    // tag is even text is odd 1,3,5,7,9....
                    // 0 or 1, 2 or 3, 4 or 5, 6 or 7, 8 or 9 becomes 0,1,2,3,4
                    col = (index - (index % chsize)) / 2;
                    fsb.append(table.getColumnHeader(col));
                }
                else {
                    int type = getExpandedTypeID(arg0);
                    if (type == rowTypeID) {
                        // get first child col. it's index will tell you what row this is
                        int cellIndex = cells.indexOf(getFirstChild(arg0));
                        getRow((cellIndex - (cellIndex % colCount)) / colCount, fsb);
                    }
                    else if (type == colTypeID) {
                        // get index and calculate row/col
                        int cellIndex = cells.indexOf(arg0);
                        int col = cellIndex % colCount;
                        int row = (cellIndex - col) / colCount;
                        Object cell = table.get(row,col);
                        // Pull attribute and output it
                        if (cell != null) {
                            appendCellStringValue(cell, fsb);
                        }
                    }
                    else if (type == colAttributeTypeID) {
                        // attributes are not part of string value
                        fsb.append(getNodeValue(arg0));
                    }
                    else if (type == flagTypeID) {
                        // TODO abstract this out to a method it is used several times
                        // get cell
                        int parent = getParent(arg0);
                        int cellIndex = cells.indexOf(parent);
                        int col = cellIndex % colCount;
                        int row = (cellIndex - col) / colCount;
                        Object cell = table.get(row,col);
                        if (cell instanceof EANFlagAttribute) {
                            MetaFlag[] mfa = (MetaFlag[])((EANFlagAttribute) cell).get();
                            // flag normalized to parent col
                            int flagIndex = (index - makeNodeIdentity(parent)) - 1;
                            int selectedIndex = 0;
                            flagIndex = (flagIndex - (flagIndex % 2)) / 2;
                            for (int i = 0; i < mfa.length; i++) {
                                if (mfa[i].isSelected()) {
                                    if (selectedIndex == flagIndex) {
                                        fsb.append(mfa[i].getLongDescription());
                                    }
                                    selectedIndex++;
                                }
                            }
                        }
                    }
                    else if (type == textTypeID) {
                        // could be under col or flag
                        // get parent
                        int parent = getParent(arg0);
                        int parentType = getExpandedTypeID(parent);
                        if (parentType == colTypeID) {
                            // if col get cell and return String or EANAttribute.get() if noe EANFlag Attribute
                            int cellIndex = cells.indexOf(parent);
                            int col = cellIndex % colCount;
                            int row = (cellIndex - col) / colCount;
                            Object cell = table.get(row,col);
                            if (cell instanceof String) {
                                fsb.append((String) cell);
                            }
                            else if (cell instanceof EANTextAttribute) {
                                fsb.append((String)((EANTextAttribute) cell).get());
                            }
                        }
                        else if (parentType == flagTypeID) {
                            // if flag get parent cell normalize index to flag get EANFlagAttribute and get nth flag
                            int cellIndex;
                            int col;
                            int row;
                            Object cell;
                            parent = getParent(parent); //up to col
                            cellIndex = cells.indexOf(parent);
                            col = cellIndex % colCount;
                            row = (cellIndex - col) / colCount;
                            cell = table.get(row,col);
                            if (cell instanceof EANFlagAttribute) {
                                // e.g. when parent col is at index 5, flag tag is at 6 and text node is at 7
                                // e.g. when parent col is at index 5, flag tag is at 8 and text node is at 9
                                // e.g. when parent col is at index 5, flag tag is at 10 and text node is at 11
                                // normalize text from 7 to 0, 9 to 1, 11 to 2 ...
                                int flagIndex = (index - makeNodeIdentity(parent)) - 2;
                                MetaFlag[] mfa;
                                int selectedIndex;
                                flagIndex = (flagIndex - (flagIndex % 2)) / 2;
                                mfa = (MetaFlag[])((EANFlagAttribute) cell).get();
                                selectedIndex = 0;
                                for (int i = 0; i < mfa.length; i++) {
                                    if (mfa[i].isSelected()) {
                                        if (selectedIndex == flagIndex) {
                                            fsb.append(mfa[i].getLongDescription());
                                        }
                                        selectedIndex++;
                                    }
                                }
                            }
                        }
                        else {
                            System.err.println("Text nodes can ony be under col or flag elements");  //$NON-NLS-1$
                        }
                    }
                }
            }
        }
        return m_xstrf.newstr(fsb.toString());
    }
    private boolean appendCellStringValue(Object cell, FastStringBuffer fsb) {
    	boolean result = false;
        if (cell instanceof String) {
            fsb.append((String) cell);
        }
        else if (cell instanceof EANTextAttribute) {
            EANTextAttribute att = (EANTextAttribute) cell;
            fsb.append((String) att.get());
        }
        else if (cell instanceof EANFlagAttribute) {
            EANFlagAttribute att = (EANFlagAttribute) cell;
            MetaFlag[] mfa = (MetaFlag[]) att.get();
            for (int i = 0; i < mfa.length; i++) {
                if (mfa[i].isSelected()) {
                    fsb.append(mfa[i].getLongDescription());
                }
            }
        }
        else {
            System.err.print("Unhandled type: ");  //$NON-NLS-1$
            System.err.print(cell.getClass().getName());
            result =  false;
        }
        result = true;
        return result;
    }

    private void getRowSet(FastStringBuffer fsb) {
        for (int row = 0; row < rowCount; row++) {
            getRow(row, fsb);
        }
    }

    private void getRow(int row, FastStringBuffer fsb) {
        for (int col = 0; col < colCount; col++) {
            Object cell = table.get(row,col);
            if (cell != null) {
                appendCellStringValue(cell, fsb);
            }
        }
    }
    private void getMetaData(FastStringBuffer fsb) {
        for (int col = 0; col < colCount; col++) {
            fsb.append(table.getColumnHeader(col));
        }
    }

    /**
     *  (non-Javadoc)
     * @see org.apache.xml.dtm.DTM#getNodeName(int)
     * @param arg0
     * @return String
     */
    public String getNodeName(int arg0) {
        return getLocalNameFromExpandedNameID(getExpandedTypeID(arg0));
    }

    /**
     *  (non-Javadoc)
     * @see org.apache.xml.dtm.DTM#getNodeNameX(int)
     * @param arg0
     * @return String
     */
    public String getNodeNameX(int arg0) {
        return getNodeName(arg0);
    }

    /**
     *  (non-Javadoc)
     * @see org.apache.xml.dtm.DTM#getLocalName(int)
     * @param arg0
     * @return String
     */
    public String getLocalName(int arg0) {
        // Given a node handle, return its XPath-style localname.
        // (As defined in Namespaces,
        //  this is the portion of the name after any colon character).
        // Root node is data. it has metadata and row-set as children
        // metadata has column-header children
        // column-header children has text children
        // row-set has row children
        // row has col children
        // col has text children
        //The values of             nodeName
        //Attr                      name of attribute
        //CDATASection              "#cdata-section"
        //Comment                   "#comment"
        //Document                  "#document"
        //DocumentFragment          "#document-fragment"
        //DocumentType              document type name
        //Element                   tag name
        //Entity                    entity name
        //EntityReference           name of entity referenced
        //Notation                  notation name
        //ProcessingInstruction     target
        //Text                      "#text"
        return getLocalNameFromExpandedNameID(getExpandedTypeID(arg0));
    }

    /**
     *  (non-Javadoc)
     * @see org.apache.xml.dtm.DTM#getPrefix(int)
     * @param arg0
     * @return String
     */
    public String getPrefix(int arg0) {
        // null is all they have for sql because it was in the subclass
        return "eann";  //$NON-NLS-1$
    }

    /**
     *  (non-Javadoc)
     * @see org.apache.xml.dtm.DTM#getNamespaceURI(int)
     * @param arg0
     * @return String
     */
    public String getNamespaceURI(int arg0) {
        // This is all they have for sql
        // return "http://xml.apache.org/xalan/eann";
        return NAMESPACE;
    }

    /**
     * (non-Javadoc)
     * @see org.apache.xml.dtm.DTM#getNodeValue(int)
     * @param arg0
     * @return String
     */
    public String getNodeValue(int arg0) {
        /* This is DOM definition of function
         * Attr                  value of attribute
         * CDATASection          content of the CDATA Section
         * Comment               content of the comment
         * Document              null
         * DocumentFragment      null
         * DocumentType          null
         * Element               null
         * Entity                null
         * EntityReference       null
         * Notation              null
         * ProcessingInstruction entire content excluding the target
         * Text                  content of the text node
         */

        int index = makeNodeIdentity(arg0);


        // Only thing we have that can return are text values
        String value = "";  //$NON-NLS-1$
        //This document indexing scheme only works on a fixed structure
        // Juast adding flag to mix means we don't know what row number or column number just by the index
        if (index >= columnHeaderOffset && index < rowOffset) {
            // Could be looking for a header
            index -= columnHeaderOffset; // normalize
            // tag is even text is odd 1,3,5,7,9....
            if (index % chsize > 0) {
                index = (index - 1) / chsize; // 1,3,5,7,9 becomes 0,1,2,3,4
                value = table.getColumnHeader(index);
            }
        }
        else if (index >= rowOffset) {
            switch (getNodeType(arg0)) {
            case DTM.ATTRIBUTE_NODE: {
                    // get parent, determine row/col,
                    int cellIndex = cells.indexOf(getParent(arg0));
                    int col = cellIndex % colCount;
                    int row = (cellIndex - col) / colCount;
                    Object cell = table.get(row,col);
                    // F = instance of EANFlagAttribute
                    // T = instance of string
                    // X = instance of XMLAttribute
                    // All other EANTextAttribute are T
                    if (cell == null) {
                        // for null values
                        value = "Not Populated";  //$NON-NLS-1$
                    }
                    else if (cell instanceof EANFlagAttribute) {
                        value = "F";  //$NON-NLS-1$
                    }
                    else if (cell instanceof XMLAttribute) {
                        value = "X";  //$NON-NLS-1$
                    }
                    else if (cell instanceof String || cell instanceof EANTextAttribute) {
                        value = "T";  //$NON-NLS-1$
                    }
                    else {
                        value = "Unhandled Type"; //$NON-NLS-1$
                    }
                    break;
                }
            case DTM.TEXT_NODE: {
                    // TODO thie could be refactored and used in getStringValue
                    // get parent
                    int parent = getParent(arg0);
                    int parentType = getExpandedTypeID(parent);
                    if (parentType == colTypeID) {
                        // if col get cell and return String or EANAttribute.get() if noe EANFlag Attribute
                        int cellIndex = cells.indexOf(parent);
                        int col = cellIndex % colCount;
                        int row = (cellIndex - col) / colCount;
                        Object cell = table.get(row,col);
                        if (cell == null) {
                            System.err.println("TableDocument.getNodeValue(int): Somehow a #text node was added for a null attribute"); //$NON-NLS-1$
                            value = "";  //$NON-NLS-1$
                        }
                        else if (cell instanceof String) {
                            value = (String) cell;
                        }
                        else if (cell instanceof EANTextAttribute) {
                            value = (String)((EANTextAttribute) cell).get();
                        }
                    }
                    else if (parentType == flagTypeID) {
                        // if flag get parent cell normalize index to flag get EANFlagAttribute and get nth flag
                        int cellIndex;
                        int col;
                        int row;
                        Object cell;
                        parent = getParent(parent); // up to col
                        cellIndex = cells.indexOf(parent);
                        col = cellIndex % colCount;
                        row = (cellIndex - col) / colCount;
                        cell = table.get(row,col);
                        if (cell != null && cell instanceof EANFlagAttribute) {
                            // e.g. when parent col is at index 5, flag tag is at 6 and text node is at 7
                            // e.g. when parent col is at index 5, flag tag is at 8 and text node is at 9
                            // e.g. when parent col is at index 5, flag tag is at 10 and text node is at 11
                            int flagIndex = (index - makeNodeIdentity(parent)) - 2;
                            MetaFlag[] mfa;
                            int selectedIndex = 0;
                            flagIndex = (flagIndex - (flagIndex % 2)) / 2;
                            mfa = (MetaFlag[])((EANFlagAttribute) cell).get();
                            for (int i = 0; i < mfa.length; i++) {
                                if (mfa[i].isSelected()) {
                                    if (selectedIndex == flagIndex) {
                                        value = mfa[i].getLongDescription();
                                    }
                                    selectedIndex++;
                                }
                            }
                        }
                    }
                    else {
                        System.err.println("Text nodes can ony be under col or flag elements");  //$NON-NLS-1$
                    }
                    break;
                }
            default: {
                    // Only text elements and attributes have a node value
                }
            }
        }
        return value;
    }

    /**
     *  (non-Javadoc)
     * @see org.apache.xml.dtm.DTM#getDocumentTypeDeclarationSystemIdentifier()
     * @return String
     */
    public String getDocumentTypeDeclarationSystemIdentifier() {
        // This is all they have for sql
        return null;
    }

    /**
     *  (non-Javadoc)
     * @see org.apache.xml.dtm.DTM#getDocumentTypeDeclarationPublicIdentifier()
     * @return String
     */
    public String getDocumentTypeDeclarationPublicIdentifier() {
        // This is all they have for sql
        return "";  //$NON-NLS-1$
    }

    /**
     *  (non-Javadoc)
     * @see org.apache.xml.dtm.DTM#getElementById(java.lang.String)
     * @param arg0
     * @return int
     */
    public int getElementById(String arg0) {
        // This is all they have for sql
        // Not supporting ID's at this point
        return DTM.NULL;
    }

    /**
     *  (non-Javadoc)
     * @see org.apache.xml.dtm.DTM#getUnparsedEntityURI(java.lang.String)
     * @param arg0
     * @return String
     */
    public String getUnparsedEntityURI(String arg0) {
        // This is all they have for sql
        return "";  //$NON-NLS-1$
    }

    /**
     *  (non-Javadoc)
     * @see org.apache.xml.dtm.DTM#isAttributeSpecified(int)
     * @param arg0
     * @return boolean
     */
    public boolean isAttributeSpecified(int arg0) {
        // This is all they have for sql
        return false;
    }

    /**
     *  (non-Javadoc)
     * @see org.apache.xml.dtm.DTM#dispatchCharactersEvents(int, org.xml.sax.ContentHandler, boolean)
     * @param arg0
     */
    public void dispatchCharactersEvents(int arg0, ContentHandler arg1, boolean arg2) throws SAXException   {
        XMLString str = getStringValue(arg0);
        str = str.fixWhiteSpace(true, true, false);
        str.dispatchCharactersEvents(arg1);
    }

    /**
     *  (non-Javadoc)
     * @see org.apache.xml.dtm.DTM#dispatchToEvents(int, org.xml.sax.ContentHandler)
     */
    public void dispatchToEvents(int arg0, ContentHandler arg1) throws SAXException {
        // This is all they have for sql

    }

    /**
     *  (non-Javadoc)
     * @see org.apache.xml.dtm.DTM#getSourceLocatorFor(int)
     * @param arg0
     * @return SourceLocator
     */
    public SourceLocator getSourceLocatorFor(int arg0) {
        // This is all they have in sql
        return null;
    }

    /**
     *  (non-Javadoc)
     * @see org.apache.xml.dtm.DTM#getDeclHandler()
     * @return DeclHandler
     */
    public DeclHandler getDeclHandler() {
        return null;
    }

    /**
     *  (non-Javadoc)
     * @see org.apache.xml.dtm.DTM#getErrorHandler()
     * @return ErrorHandler
     */
    public ErrorHandler getErrorHandler() {
        // This is all they have for sql
        return null;
    }

    /**
     *  (non-Javadoc)
     * @see org.apache.xml.dtm.DTM#getDTDHandler()
     * @return DTDHandler
     */
    public DTDHandler getDTDHandler() {
        // This is all they have for sql
        return null;
    }

    /**
     *  (non-Javadoc)
     * @see org.apache.xml.dtm.DTM#getEntityResolver()
     * @return EntityResolver
     */
    public EntityResolver getEntityResolver() {
        // This is all they have for sql
        return null;
    }

    /**
     *  (non-Javadoc)
     * @see org.apache.xml.dtm.DTM#getLexicalHandler()
     * @return LexicalHandler
     */
    public LexicalHandler getLexicalHandler() {
        // This is all they have for sql
        return null;
    }

    /**
     *  (non-Javadoc)
     * @see org.apache.xml.dtm.DTM#getContentHandler()
     * @return ContentHandler
     */
    public ContentHandler getContentHandler() {
        // This is all they have for sql
        return null;
    }

    /**
     *  (non-Javadoc)
     * @see org.apache.xml.dtm.DTM#needsTwoThreads()
     * @return boolean
     */
    public boolean needsTwoThreads() {
        // This is all they have for sql
        return false;
    }

    /**
     *  (non-Javadoc)
     * @see org.apache.xml.dtm.DTM#setProperty(java.lang.String, java.lang.Object)
     * @param arg0
     * @param arg1
     */
    public void setProperty(String arg0, Object arg1) {
        // This is all they have for sql
    }

    /**
     * Returns true if there are too many nodes for a DTM
     * @return
     */
    public boolean isTooLarge() {
        return isTooLarge;
    }

    /**
     * Set isTooLarge
     * @param b
     */
    public void setTooLarge(boolean b) {
        isTooLarge = b;
    }

}
