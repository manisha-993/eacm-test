//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.edit.formgen;


import COM.ibm.eannounce.objects.*;

/**
 * label used for entityitem info in the form table
 * @author Wendy Stimpson
 */
// $Log: FormEntityLabel.java,v $
// Revision 1.1  2012/09/27 19:39:23  wendy
// Initial code
//
public class FormEntityLabel extends FormLabel 
{
	private static final long serialVersionUID = 1L;

	protected static final int ENTITY_ITEM_DESCRIPTION = 7;
	protected static final int ENTITY_ITEM_TYPE = 8;
	protected static final int ENTITY_ITEM_ID = 9;

    private int description = 0;
 

    /**
     * formLabel - used for entityitem info
     * @param _ei
     * @param _key
     * @param _description
     */
    protected FormEntityLabel(EntityItem _ei, String _key, int _description) {
        super(_key);
        description =_description;
        refresh(_ei);
    }

    /**
     * refresh
     * @param _item
     */
    protected void refresh(EntityItem _item) {
    	if (description == ENTITY_ITEM_DESCRIPTION) {
    		setText(_item.getLongDescription());
    	} else if (description == ENTITY_ITEM_TYPE) {
    		setText(_item.getEntityType());
    	} else if (description == ENTITY_ITEM_ID) {
    		setText(Integer.toString(_item.getEntityID()));
    	}
    }
}
