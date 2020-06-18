//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: EANSimpleTableTemplate.java,v $
// Revision 1.4  2005/02/15 18:42:24  dave
// More JTest cleanup
//
// Revision 1.3  2004/10/21 16:49:54  dave
// trying to share compartor
//
// Revision 1.2  2003/03/10 17:13:07  gregg
// fit in changes to EANTableRowTemplate
//
// Revision 1.1  2003/03/07 22:13:19  gregg
// initial load
//
//

package COM.ibm.eannounce.objects;

/**
* Ensure that an object can be fit into a simple table structure.
* <BR>- Non-Editable Basis for all simple tables.
*/
public interface EANSimpleTableTemplate {

    /**
     */
    String CLASS_BRAND = "$Id: EANSimpleTableTemplate.java,v 1.4 2005/02/15 18:42:24 dave Exp $";

    /**
     * Rows should be stored internally as an <CODE>EANList</CODE> of <CODE>EANTableRowTemplates</CODE>
     *
     * @return EANList
     */
    EANList getRowList();

    /**
     * Columns should be stored internally as an <CODE>EANList</CODE> of <CODE>EANMetaFoundations</CODE>
     *
     * @return EANList
     */
    EANList getColumnList();

    /**
     * Enforce that rows implement <CODE>EANTableRowTemplate</CODE>
     *
     * @return EANTableRowTemplate
     * @param _strKey 
     */
    EANTableRowTemplate getRow(String _strKey);

}
