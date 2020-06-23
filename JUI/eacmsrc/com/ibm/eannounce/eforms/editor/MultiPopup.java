/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: MultiPopup.java,v $
 * Revision 1.1  2007/04/18 19:44:34  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:13  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:37:58  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:04  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/05/25 20:59:57  tony
 * multiple flag enhancement
 *
 * Revision 1.3  2005/02/01 20:48:32  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 19:15:17  tony
 * JTest Format
 *
 * Revision 1.1.1.1  2004/02/10 16:59:42  tony
 * This is the initial load of OPICM
 *
 * Revision 1.13  2003/10/22 17:12:04  tony
 * 52674
 *
 * Revision 1.12  2003/10/21 15:53:56  tony
 * 52619
 *
 * Revision 1.11  2003/10/10 20:10:24  tony
 * 52499
 *
 * Revision 1.10  2003/09/12 20:16:18  tony
 * 52194
 *
 * Revision 1.9  2003/08/29 19:44:28  tony
 * 51981
 *
 * Revision 1.8  2003/07/15 18:24:15  joan
 * 51336
 *
 * Revision 1.7  2003/05/14 19:00:36  tony
 * changed layout
 *
 * Revision 1.6  2003/04/28 19:27:14  tony
 * adjusted rendering on multi-editor so
 * component underneath is not visible.
 *
 * Revision 1.5  2003/04/21 20:03:12  tony
 * enhanced scroll logic to improve flag editor.
 *
 * Revision 1.4  2003/04/18 15:42:41  tony
 * enhnaced multiEditor functionality.
 *
 * Revision 1.3  2003/04/17 23:13:25  tony
 * played around with editing Colors
 *
 * Revision 1.2  2003/03/21 20:54:32  tony
 * updated getBackground, getForeground, and getFont logic.
 * added logic so that each can be set for the component.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2002/11/07 16:58:28  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.editor;
import com.elogin.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import COM.ibm.eannounce.objects.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public interface MultiPopup {
	/**
     * getKeyListener
     * @return
     * @author Anthony C. Liberto
     */
    KeyListener getKeyListener();
	/**
     * setEditor
     * @param _editor
     * @author Anthony C. Liberto
     */
    void setEditor(MultiEditor _editor);
	/**
     * acquireFocus
     * @author Anthony C. Liberto
     */
    void acquireFocus();
	/**
     * setColumnHeader
     * @param _port
     * @author Anthony C. Liberto
     */
    void setColumnHeader(JViewport _port);
	/**
     * getScrollSize
     * @return
     * @author Anthony C. Liberto
     */
    Dimension getScrollSize();
	/**
     * getFlagCount
     * @return
     * @author Anthony C. Liberto
     */
    int getFlagCount();
	/**
     * refresh
     * @param _att
     * @author Anthony C. Liberto
     */
    void refresh(EANAttribute _att);
	/**
     * refresh
     * @param _flag
     * @author Anthony C. Liberto
     */
    void refresh(MetaFlag[] _flag);
	/**
     * cancelChanges
     * @author Anthony C. Liberto
     */
    void cancelChanges();
	/**
     * getMultipleSelection
     * @return
     * @author Anthony C. Liberto
     */
    MetaFlag[] getMultipleSelection();
	/**
     * removeAllFlags
     * @author Anthony C. Liberto
     */
    void removeAllFlags();
	/**
     * dereference
     * @author Anthony C. Liberto
     */
    void dereference();
	/**
     * setModalCursor
     * @param _b
     * @author Anthony C. Liberto
     */
    void setModalCursor(boolean _b);
	/**
     * setVisible
     * @param _b
     * @author Anthony C. Liberto
     */
    void setVisible(boolean _b);
	/**
     * showHide
     * @param _b
     * @author Anthony C. Liberto
     */
    void showHide(boolean _b);
	/**
     * getDisplayComponent
     * @return
     * @author Anthony C. Liberto
     */
    JComponent getDisplayComponent();
	/**
     * show
     * @param _c
     * @param _x
     * @param _y
     * @author Anthony C. Liberto
     */
    void show(Component _c, int _x, int _y);
	/**
     * requestFocus
     * @param _b
     * @return
     * @author Anthony C. Liberto
     */
    boolean requestFocus(boolean _b);
	/**
     * isShowing
     * @return
     * @author Anthony C. Liberto
     */
    boolean isShowing();
	/**
     * isVisible
     * @return
     * @author Anthony C. Liberto
     */
    boolean isVisible();
	/**
     * repaint
     * @author Anthony C. Liberto
     */
    void repaint();
	/**
     * requestFocus
     * @author Anthony C. Liberto
     */
    void requestFocus();
	/**
     * addButtons
     * @param _btnSave
     * @param _btnCancel
     * @author Anthony C. Liberto
     */
    void addButtons(EButton _btnSave, EButton _btnCancel);
	/**
     * processPopupKey
     * @param _ke
     * @author Anthony C. Liberto
     */
    void processPopupKey(KeyEvent _ke);							//52674

	/**
     * popupTextUpdate
     *
     * @param _s
     * @author Anthony C. Liberto
     */
    void popupTextUpdate(String _s);
}
