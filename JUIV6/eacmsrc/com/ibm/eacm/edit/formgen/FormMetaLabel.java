//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.edit.formgen;

import COM.ibm.eannounce.objects.*;


/**
 * label used for meta information
 * @author Wendy Stimpson
 */
// $Log: FormMetaLabel.java,v $
// Revision 1.1  2012/09/27 19:39:23  wendy
// Initial code
//
public class FormMetaLabel extends FormLabel 
{
	private static final long serialVersionUID = 1L;
	
	protected static final int CAPABILITY_DESCRIPTION = 0;
	protected static final int CODE_DESCRIPTION = 1;
	protected static final int DEFAULT_DESCRIPTION = 2;
	protected static final int HELP_DESCRIPTION = 3;
	protected static final int LONG_DESCRIPTION = 4;
	protected static final int SHORT_DESCRIPTION = 5;
	protected static final int TYPE_DESCRIPTION = 6;
	
    private int description = 0;
    
    /**
     * 
     * @param _meta
     * @param _key
     * @param _description
     */
    protected FormMetaLabel(EANMetaAttribute _meta, String _key, int _description) {
    	super(_key);
        description =_description;
        refresh(_meta);
    }

    /**
     * refresh
     * @param _meta
     */
    protected void refresh(EANMetaAttribute _meta) {
    	if (description == CAPABILITY_DESCRIPTION) {
    		setText(_meta.getCapability());
    	} else if (description == CODE_DESCRIPTION) {
    		setText(_meta.getAttributeCode());
    	} else if (description == DEFAULT_DESCRIPTION) {
    		setText(_meta.getDefaultValue());
    	} else if (description == HELP_DESCRIPTION) {
    		setText(_meta.getMetaHelpValue().getHelpValueText());
    	} else if (description == LONG_DESCRIPTION) {
    		setText(_meta.getLongDescription());
    	} else if (description == SHORT_DESCRIPTION) {
    		setText(_meta.getShortDescription());
    	} else if (description == TYPE_DESCRIPTION) {
    		setText(_meta.getAttributeType());
    	}
    }
}
