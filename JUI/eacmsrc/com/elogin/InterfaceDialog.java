/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/12/05
 * @author Anthony C. Liberto
 *
 * $Log: InterfaceDialog.java,v $
 * Revision 1.1  2007/04/18 19:42:17  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:39  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:58:56  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/02 21:30:07  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:17  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:25  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2003/09/19 18:11:26  tony
 * 52321
 *
 * Revision 1.7  2003/06/05 20:15:23  tony
 * 51169
 *
 * Revision 1.6  2003/05/28 16:28:21  tony
 * 50924
 *
 * Revision 1.5  2003/04/10 20:06:23  tony
 * updated logic to allow for dialogs to properly
 * eminiate from the dialogs parent.
 *
 * Revision 1.4  2003/04/03 16:19:06  tony
 * changed refreshLookAndFeel to refreshAppearance()
 * updated logic to take into account displayed dialogs
 * as well.
 *
 * Revision 1.3  2003/03/11 00:33:23  tony
 * accessibility changes
 *
 * Revision 1.2  2003/03/07 21:40:47  tony
 * Accessibility update
 *
 * Revision 1.1.1.1  2003/03/03 18:03:40  tony
 * This is the initial load of OPICM
 *
 *
 */
package com.elogin;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public interface InterfaceDialog {
	/**
     * init
     * @author Anthony C. Liberto
     */
    void init();
	/**
     * dereference
     * @author Anthony C. Liberto
     */
    void dereference();
	/**
     * dereferenceFull
     * @author Anthony C. Liberto
     */
    void dereferenceFull();
	/**
     * construct
     * @param _dc
     * @author Anthony C. Liberto
     */
    void construct(DisplayableComponent _dc);
	/**
     * show
     * @param _dc
     * @author Anthony C. Liberto
     */
    void show(DisplayableComponent _dc);
	/**
     * hide
     * @author Anthony C. Liberto
     */
    void hide();
	/**
     * dispose
     * @author Anthony C. Liberto
     */
    void dispose();
	/**
     * pack
     * @author Anthony C. Liberto
     */
    void pack();
	/**
     * validate
     * @author Anthony C. Liberto
     */
    void validate();
	/**
     * repaintImmediately
     * @author Anthony C. Liberto
     */
    void repaintImmediately();
	/**
     * refreshAppearance
     * @author Anthony C. Liberto
     */
    void refreshAppearance();
	/**
     * getSearchObject
     * @return
     * @author Anthony C. Liberto
     */
    Object getSearchObject();			//50924
	/**
     * getSearchableObject
     * @return
     * @author Anthony C. Liberto
     */
    Object getSearchableObject();		//50924
	/**
     * toFront
     * @author Anthony C. Liberto
     */
    void toFront();						//51162
	/**
     * isShowing
     * @return
     * @author Anthony C. Liberto
     */
    boolean isShowing();				//52321
}
