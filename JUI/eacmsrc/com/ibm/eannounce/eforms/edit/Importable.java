// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eannounce.eforms.edit;

import COM.ibm.eannounce.objects.EANBusinessRuleException;

/**********************************************************************************
 * Used to import from xsl file
 */
// $Log: Importable.java,v $
// Revision 1.1  2008/02/08 00:34:00  wendy
// Init for import of files
//
public interface Importable {
    /**
     * processXLSImport import from Excel xls ss
     * @param index int counter into number of rows in ss
     * @param attrCodes String[] attribute codes
     * @param attrValue String[], some may be null
     */
    EANBusinessRuleException processXLSImport(int index, String[] attrCodes, String[] attrValue);
    /**
     * importString import tab delimited file, this was the original form of import
     * @param _s String[] attribute codes
     */
    void importString(String[] _s);  
}