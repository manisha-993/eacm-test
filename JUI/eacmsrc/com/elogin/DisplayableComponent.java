/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: DisplayableComponent.java,v $
 * Revision 1.1  2007/04/18 19:42:16  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:35  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:58:51  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/02 21:30:06  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:17  tony
 * JTest Formatting
 *
 * Revision 1.1.1.1  2004/02/10 16:59:23  tony
 * This is the initial load of OPICM
 *
 * Revision 1.13  2003/12/19 23:14:01  tony
 * acl20031219
 * updated logic so that on 'x' close click on longeditor
 * editing is completed.
 *
 * Revision 1.12  2003/12/01 19:46:27  tony
 * accessibility
 *
 * Revision 1.11  2003/12/01 17:46:08  tony
 * accessibility
 *
 * Revision 1.10  2003/07/10 20:11:29  tony
 * added shouldResetBusy Logic to assist in providing more
 * control of cursor busy.
 *
 * Revision 1.9  2003/06/05 20:15:23  tony
 * 51169
 *
 * Revision 1.8  2003/05/28 16:28:22  tony
 * 50924
 *
 * Revision 1.7  2003/04/16 17:39:31  tony
 * added setResizable to displayComponent.
 *
 * Revision 1.6  2003/04/11 20:02:24  tony
 * added copyright statements.
 *
 */
package com.elogin;
import java.awt.*;
import javax.swing.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public interface DisplayableComponent {
	/**
     * setParentDialog
     * @param _id
     * @author Anthony C. Liberto
     */
    void setParentDialog(InterfaceDialog _id);
	/**
     * getParentDialog
     * @return
     * @author Anthony C. Liberto
     */
    InterfaceDialog getParentDialog();
	/**
     * setTitle
     * @param _title
     * @author Anthony C. Liberto
     */
    void setTitle(String _title);
	/**
     * getTitle
     * @return
     * @author Anthony C. Liberto
     */
    String getTitle();
	/**
     * getMenuBar
     * @return
     * @author Anthony C. Liberto
     */
    JMenuBar getMenuBar();
	/**
     * showMe
     * @author Anthony C. Liberto
     */
    void showMe();
	/**
     * hideMe
     * @author Anthony C. Liberto
     */
    void hideMe();
	/**
     * activateMe
     * @author Anthony C. Liberto
     */
    void activateMe();
	/**
     * disposeDialog
     * @author Anthony C. Liberto
     */
    void disposeDialog();
	/**
     * isShowing
     * @return
     * @author Anthony C. Liberto
     */
    boolean isShowing();
	/**
     * setResizable
     * @param _b
     * @author Anthony C. Liberto
     */
    void setResizable(boolean _b);
	/**
     * isResizable
     * @return
     * @author Anthony C. Liberto
     */
    boolean isResizable();
	/**
     * getMinimumSize
     * @return
     * @author Anthony C. Liberto
     */
    Dimension getMinimumSize();
	/**
     * getPanelType
     * @return
     * @author Anthony C. Liberto
     */
    String getPanelType();
	/**
     * isPanelType
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    boolean isPanelType(String _s);
	/**
     * getIconName
     * @return
     * @author Anthony C. Liberto
     */
    String getIconName();
	/**
     * getSearchableObject
     * @return
     * @author Anthony C. Liberto
     */
    Object getSearchableObject();
	/**
     * toFront
     * @author Anthony C. Liberto
     */
    void toFront();						//51169
	/**
     * shouldResetBusy
     * @return
     * @author Anthony C. Liberto
     */
    boolean shouldResetBusy();
	/**
     * getAccessibleDescription
     * @return
     * @author Anthony C. Liberto
     */
    String getAccessibleDescription();	//access
	/**
     * hasCustomFocusPolicy
     * @return
     * @author Anthony C. Liberto
     */
    boolean hasCustomFocusPolicy();		//access
	/**
     * getFocusTraversalPolicy
     * @return
     * @author Anthony C. Liberto
     */
    FocusTraversalPolicy getFocusTraversalPolicy();	//access
	/**
     * canWindowClose
     * @return
     * @author Anthony C. Liberto
     */
    boolean canWindowClose();						//acl20031219
}
