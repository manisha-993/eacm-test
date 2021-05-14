//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.editor;

import java.awt.Component;
import java.awt.event.KeyEvent;
import COM.ibm.eannounce.objects.MetaFlag;

/**
 * this interface is used for popupmenu and dialog for editing multiflags
 * @author Wendy Stimpson
 */
//$Log: MultiPopup.java,v $
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public interface MultiPopup {
	/**
     * set the attribute description
     * @param desc
     */
    void setDescription(String desc);
	/**
     * load the metaflags 
     * @param mf
     */
    void loadMetaFlags(MetaFlag[] mf);

	/**
     * get MetaFlags
     */
    MetaFlag[] getMetaFlags();

	/**
     * release memory
     */
    void dereference();

	/**
     * hide the popupo
     */
    void hidePopup();

	/**
     * show the popup
     * @param _c
     */
    void showPopup(Component _c);

	/**
     * processPopupKey
     * @param _ke
     */
    void processPopupKey(KeyEvent _ke);					

    /**
     * set typeahead label text
     * @param _s
     */
    void setLabelText(String _s);
    
    /** get current case sensitive typeahead label
     * @return
     */
    String getLabelText();
}
