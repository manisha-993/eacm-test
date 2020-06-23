// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.edit;

import COM.ibm.eannounce.objects.EANBusinessRuleException;
/**********************************************************************************
 * Used to import from xsl file
 * @author Wendy Stimpson
 */
// $Log: Importable.java,v $
// Revision 1.2  2014/01/22 20:38:16  wendy
// RCQ 288700
//
// Revision 1.1  2012/09/27 19:39:19  wendy
// Initial code
//
public interface Importable {
    /**
     * processXLSImport import from Excel xls ss or from Apache OpenDocument ods file
     * @param index int counter into number of rows in ss
     * @param attrCodes String[] attribute codes
     * @param attrValue String[], some may be null
     */
    EANBusinessRuleException processXLSImport(int index, String[] attrCodes, String[] attrValue);
}
